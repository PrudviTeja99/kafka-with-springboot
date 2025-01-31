package com.teja.employeeconsumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeConsumer {
    @KafkaListener(topics = {"my-topic"})
    public void onMessage(ConsumerRecord<String,String> consumerRecord){
        System.out.println(consumerRecord.value());
    }
}
