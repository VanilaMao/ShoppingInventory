package com.shopping.userservice.configs;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    @Value(value="${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value="${kafka.userTopic}")
    private String userTopic;

    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String,Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Autowired
    @Bean
    public NewTopic user(KafkaAdmin admin) throws  Exception{
        AdminClient client = AdminClient.create(admin.getConfig());
        if(client.listTopics().names().get().stream().anyMatch(name->userTopic.equals(name))){
            return null;
        }else{
            return TopicBuilder.name(userTopic).replicas(1).partitions(1).build();
        }
    }
}