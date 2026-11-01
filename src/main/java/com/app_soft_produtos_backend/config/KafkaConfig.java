package com.app_soft_produtos_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJacksonJsonMessageConverter;

@Configuration
public class KafkaConfig {

    @Bean
    public RecordMessageConverter messageConverter() {
        return new StringJacksonJsonMessageConverter();
    }
}