package com.teja.employeeproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teja.employeeproducer.entity.Employee;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EmployeeService {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    public void createEmployee(Employee employee) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProducerRecord producerRecord = new ProducerRecord("my-topic", employee.getId(), objectMapper.writeValueAsString(employee));
        CompletableFuture<SendResult<String,String>> completableFuture = kafkaTemplate.send(producerRecord);
        completableFuture.whenComplete((sendResult,throwable)->{
            if(throwable!=null){
                failure();
            }
            else{
                success(sendResult);
            }
        });
    }
    private void failure(){
        System.out.println("event failure");
    }
    private void success(SendResult<String,String> sendResult){
        System.out.println("event success "+sendResult);
    }
}
