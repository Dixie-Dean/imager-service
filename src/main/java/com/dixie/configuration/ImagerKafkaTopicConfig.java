package com.dixie.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class ImagerKafkaTopicConfig {

    @Bean
    public NewTopic requestIdTopic() {
        return TopicBuilder.name("request-id-topic").partitions(3).build();
    }

    @Bean
    public NewTopic imagerResponseTopic() {
        return TopicBuilder.name("imager-response-topic").partitions(3).build();
    }
}
