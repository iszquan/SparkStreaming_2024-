package com.zq.realtime.app

import java.text.SimpleDateFormat
import java.time.{LocalDate, Period}
import java.{lang, util}
import java.util.Date
import com.alibaba.fastjson.{JSON, JSONObject}
import com.zq.realtime.bean.{DauInfo, PageLog}
import com.zq.realtime.util.{MyBeanUtils, MyEsUtils, MyKafkaUtils, MyOffsetsUtils, MyRedisUtils}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{HasOffsetRanges, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import redis.clients.jedis.{Jedis, Pipeline}

import scala.collection.mutable.ListBuffer

/**

  *
  * 1. 准备实时环境
  * 2. 从Redis中读取偏移量
  * 3. 从kafka中消费数据
  * 4. 提取偏移量结束点
  * 5. 处理数据
  *     5.1 转换数据结构
  *     5.2 去重
  *     5.3 维度关联
  * 6. 写入ES
  * 7. 提交offsets
  */
object DwdDauApp {

  def main(args: Array[String]): Unit = {
    //0.还原状态
    revertState()

    //1. 准备实时环境
    val sparkConf: SparkConf = new SparkConf().setAppName("dwd_dau_app").setMaster("local[4]")
    val ssc: StreamingContext = new StreamingContext(sparkConf,Seconds(5))

    //2. 从Redis中读取offset
    val topicName : String = "DWD_EVENTS_TOPIC"
    val groupId : String = "DWD_DAU_GROUP"
    val offsets: Map[TopicPartition, Long] = MyOffsetsUtils.readOffset(topicName, groupId)

    //3. 从Kafka中消费数据
    var kafkaDStream: InputDStream[ConsumerRecord[String, String]] = null
    if(offsets != null && offsets.nonEmpty){
      kafkaDStream = MyKafkaUtils.getKafkaDStream(ssc,topicName,groupId,offsets)
    }else {
      kafkaDStream = MyKafkaUtils.getKafkaDStream(ssc,topicName,groupId)
    }

    //4. 提取offset结束点
    var offsetRanges: Array[OffsetRange] = null
    val offsetRangesDStream: DStream[ConsumerRecord[String, String]] = kafkaDStream.transform(
      rdd => {
        offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
        rdd
      }
    )
    //5. 处理数据
    // 5.1 转换结构
    val pageLogDStream: DStream[PageLog] = offsetRangesDStream.map(
      consumerRecord => {
        val value: String = consumerRecord.value()
        val pageLog: PageLog = JSON.parseObject(value, classOf[PageLog])
        pageLog
      }
    )

//  pageLogDStream.print(10)



    //写入到OLAP中
    //按照天分割索引，通过索引模板控制mapping、settings、aliases等.
    //准备ES工具类
    pageLogDStream.foreachRDD(
      rdd => {
        rdd.foreachPartition(
          dauInfoIter => {
            val docs: List[(String, PageLog)] =
              dauInfoIter.map( dauInfo=> (dauInfo.num , dauInfo)).toList
            if(docs.size >  0 ){
              // 索引名
              val indexName : String = s"ei_dau_info"
              //写入到ES中
              MyEsUtils.bulkSave(indexName , docs)
            }
          }
        )
        //提交offset
        MyOffsetsUtils.saveOffset(topicName, groupId , offsetRanges)
      }
    )

    ssc.start()
    ssc.awaitTermination()
  }
//
//  /**
//    * 状态还原
//    *
//    * 在每次启动实时任务时， 进行一次状态还原。 以ES为准, 将所以的mid提取出来，覆盖到Redis中.
//    */
//
  def revertState(): Unit ={
    //从ES中查询到所有的mid
    val date: LocalDate = LocalDate.now()
    val indexName : String = s"ei_dau_info"
    val fieldName : String = "num"
    val nums: List[ String ] = MyEsUtils.searchField(indexName , fieldName)
    //删除redis中记录的状态（所有的mid）
    val jedis: Jedis = MyRedisUtils.getJedisFromPool()
    val redisDauKey : String = s"DAU:$date"
    jedis.del(redisDauKey)
    //将从ES中查询到的mid覆盖到Redis中
    if(nums != null && nums.size > 0 ){
      /*for (mid <- mids) {
        jedis.sadd(redisDauKey , mid )
      }*/
      val pipeline: Pipeline = jedis.pipelined()
      for (num <- nums) {
        pipeline.sadd(redisDauKey , num )  //不会直接到redis执行
      }

      pipeline.sync()  // 到redis执行
    }

    jedis.close()
  }
}
