package com.zq;

import java.util.stream.Stream;

public class SendMessageApplication {

    public static void main(String[] args) throws Exception {
        // 文件地址
        String filePath = "C:\\Users\\ZQ\\Desktop\\EI_2024-02-21.csv";
        // kafka topic
        String topic = "events";
        // kafka borker地址
        String broker = "192.168.10.102:9092";

        Stream.generate(new csvFileReader(filePath))
                .sequential()
                .forEachOrdered(new KafkaProducer(topic, broker));
    }
}

