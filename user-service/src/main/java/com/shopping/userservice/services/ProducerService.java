package com.shopping.userservice.services;

import com.shopping.userservice.domains.AuthenticationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Value(value = "${kafka.userTopic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, AuthenticationUser> kafkaTemplate;

    public void sendMessage(AuthenticationUser user) {
        this.kafkaTemplate.send(topic,user);
    }
}
