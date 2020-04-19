package com.shopping.authservice.configs;

import com.shopping.authservice.entities.User;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@TestConfiguration
public class ConsumerTestConfig {

    @Bean
    public ConsumerFactory<String, User> consumerFactory(){
        return null;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, User> kafkaListenerContainerFactory(){
        return null;
    }
}
