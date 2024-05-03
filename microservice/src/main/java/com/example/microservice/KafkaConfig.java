package com.example.microservice;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic customerTopic() {
        return TopicBuilder.name("customer-topic")
                .partitions(5)
                .replicas(1)
                .compact()
                .build();
    }
}
