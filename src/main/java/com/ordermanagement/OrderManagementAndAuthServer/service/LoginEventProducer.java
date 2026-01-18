package com.ordermanagement.OrderManagementAndAuthServer.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public LoginEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(String username) {
        kafkaTemplate.send("login-events", username + " logged in");
    }
}
