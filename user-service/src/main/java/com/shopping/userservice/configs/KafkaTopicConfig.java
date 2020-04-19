package com.shopping.userservice.configs;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Configuration
public class KafkaTopicConfig {
    @Value(value="${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value="${kafka.userTopic}")
    private String userTopic;

    @Value(value="${kafka.connection.timeout}")
    private int timeout;

    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String,Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Autowired
    @Bean
    public NewTopic user(KafkaAdmin admin) throws  Exception{
        try{
            AdminClient client = AdminClient.create(admin.getConfig());
            if(client.listTopics(new ListTopicsOptions().timeoutMs(timeout)).names().get().stream().anyMatch(name->userTopic.equals(name))){
                return null;
            }else{
                return TopicBuilder.name(userTopic).replicas(1).partitions(1).build();
            }
        }catch(ExecutionException| KafkaException ex){

            return null;
        }
    }
}