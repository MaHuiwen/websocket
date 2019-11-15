package com.study.websocket.rabbitmq.config;

import com.study.websocket.constans.MqConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Bean
    public Queue testQueue() {
        return QueueBuilder.durable(MqConstants.QUEUE_TEST)
                .build();
    }
}
