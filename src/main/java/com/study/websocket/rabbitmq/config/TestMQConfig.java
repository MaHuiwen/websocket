package com.study.websocket.rabbitmq.config;

import com.study.websocket.constans.MqConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestMQConfig {

    @Bean
    public DirectExchange testExchange() {
        return new DirectExchange(MqConstants.EXCHANGE_TEST);
    }

    @Bean
    public Queue testQueue() {
        return QueueBuilder.durable(MqConstants.QUEUE_TEST)
                .build();
    }

    @Bean
    public Binding testBinding() {
        return BindingBuilder
                .bind(testQueue())
                .to(testExchange())
                .with(MqConstants.QUEUE_TEST);
    }

}
