package com.dixie.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class ImagerKafkaTopicConfig {

    @Bean
    public NewTopic imagerServiceTopic() {
        return TopicBuilder.name("imager-service").partitions(3).build();
    }

}
