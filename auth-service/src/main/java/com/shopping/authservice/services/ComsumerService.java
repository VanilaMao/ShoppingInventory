package com.shopping.authservice.services;

import com.shopping.authservice.domains.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ComsumerService {
    @KafkaListener(topics = "${kafka.userTopic}", groupId = "${kafka.groupId}")
    public void recieveData(User user) {
        System.out.print(user.getName());
    }
}
