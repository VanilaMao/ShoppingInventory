package com.shopping.authservice.services;

import com.shopping.authservice.entities.User;
import com.shopping.authservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @KafkaListener(topics = "${kafka.userTopic}", groupId = "${kafka.groupId}")
    public void receiveData(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        System.out.print(user.getName());
    }
}
