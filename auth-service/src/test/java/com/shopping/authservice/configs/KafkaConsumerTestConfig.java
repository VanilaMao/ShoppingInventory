package com.shopping.authservice.configs;

import com.shopping.authservice.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;

@TestConfiguration
public class KafkaConsumerTestConfig {

    @Autowired
    private EmbeddedKafkaBroker kafkaEmbedded;

    @Bean
    @Primary
    public ConsumerFactory<String, User> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(KafkaTestUtils.consumerProps("auth","true",kafkaEmbedded));
    }

    @Bean
    @Primary
    public ConcurrentKafkaListenerContainerFactory<String, User> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, User> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
