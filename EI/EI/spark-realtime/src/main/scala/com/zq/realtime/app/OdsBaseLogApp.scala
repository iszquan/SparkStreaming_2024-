package com.zq.realtime.app

import com.alibaba.fastjson.serializer.SerializeConfig
import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import com.zq.realtime.bean.PageLog
import com.zq.realtime.util.{MyKafkaUtils, MyOffsetsUtils}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{HasOffsetRanges, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 日志数据的消费分流
 * 1. 准备实时处理环境 StreamingContext
 *
 * 2. 从Kafka中消费数据
 *
 * 3. 处理数据
 *     3.1 转换数据结构
 *           专用结构  Bean
 *           通用结构  Map JsonObject
 *
 * 4. 写出到DWD层
 */
object OdsBaseLogApp {
  def main(args: Array[String]): Unit = {
    //1. 准备实时环境
    //TODO 注意并行度与Kafka中topic的分区个数的对应关系
    val sparkConf: SparkConf = new SparkConf().setAppName("ods_events_app").setMaster("local[4]")
    val ssc: StreamingContext = new StreamingContext(sparkConf, Seconds(5))

    //2. 从kafka中消费数据
    val topicName: String = "events" //对应生成器配置中的主题名
    val groupId: String = "ODS_EVENTS_GROUP"

    //TODO  从Redis中读取offset， 指定offset进行消费
    val offsets: Map[TopicPartition, Long] = MyOffsetsUtils.readOffset(topicName, groupId)

    var kafkaDStream: InputDStream[ConsumerRecord[String, String]] = null
    if (offsets != null && offsets.nonEmpty) {
      //指定offset进行消费
      kafkaDStream =
        MyKafkaUtils.getKafkaDStream(ssc, topicName, groupId, offsets)

    } else {
      //默认offset进行消费
      kafkaDStream =
        MyKafkaUtils.getKafkaDStream(ssc, topicName, groupId)

    }

    // TODO 补充: 从当前消费到的数据中提取offsets , 不对流中的数据做任何处理.
    var offsetRanges: Array[OffsetRange] = null
    val offsetRangesDStream: DStream[ConsumerRecord[String, String]] = kafkaDStream.transform(
      rdd => {
        offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges // 在哪里执行? driver
        rdd
      }
    )

    //kafkaDStream.print(100)
    //3. 处理数据
    //3.1 转换数据结构
    val jsonObjDStream: DStream[JSONObject] = offsetRangesDStream.map(
      consumerRecord => {
        //获取ConsumerRecord中的value,value就是日志数据
        val log: String = consumerRecord.value()
        //转换成Json对象
        val jsonObj: JSONObject = JSON.parseObject(log)
        //返回
        jsonObj
      }
    )
//     jsonObjDStream.print(1000)

    //3.2 分流
    val DWD_EVENTS_TOPIC: String = "DWD_EVENTS_TOPIC"

    jsonObjDStream.foreachRDD(
      rdd => {

        // 按分区执行
        rdd.foreachPartition(
          jsonObjIter => {
            for (jsonObj <- jsonObjIter) {
              //分流过程
              val num: String = jsonObj.getString("num")
              val work_name: String = jsonObj.getString("work_name")
              val location: String = jsonObj.getString("location")
              val salary: String = jsonObj.getString("salary")
              val work_year: String = jsonObj.getString("work_year")
              val educate: String = jsonObj.getString("educate")
              val company_name: String = jsonObj.getString("company_name")
              val company_type: String = jsonObj.getString("company_type")
              val financing_conditions: String = jsonObj.getString("financing_conditions")
              val company_size: String = jsonObj.getString("company_size")
              val city: String = jsonObj.getString("city")
              val nums: String = jsonObj.getString("nums")

              //封装成PageLog
              var pageLog =
                PageLog(num, work_name, location, salary, work_year, educate, company_name, company_type, financing_conditions, company_size, city, nums)
              //发送到DWD_PAGE_LOG_TOPIC
              MyKafkaUtils.send(DWD_EVENTS_TOPIC, JSON.toJSONString(pageLog, new SerializeConfig(true)))

            }
            // foreachPartition里面:  Executor段执行， 每批次每分区执行一次
            //刷写Kafka
            MyKafkaUtils.flush()
          }
        )

        MyOffsetsUtils.saveOffset(topicName, groupId, offsetRanges)
      }
    )
    ssc.start()
    ssc.awaitTermination()
  }
}

