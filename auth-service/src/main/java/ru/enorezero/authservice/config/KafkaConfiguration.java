package ru.enorezero.authservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
public class KafkaConfiguration {

    @Value("${topic.name}")
    private String topic;

    @Bean
    NewTopic newTopic(){
        return new NewTopic(topic,1,(short)1);
    }
}
