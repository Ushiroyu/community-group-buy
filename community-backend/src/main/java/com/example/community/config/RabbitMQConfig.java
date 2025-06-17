package com.example.community.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String ORDER_QUEUE = "order.created";

    @Bean
    public Queue orderQueue() {
        return new Queue(ORDER_QUEUE, true);
    }
}