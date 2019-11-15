package com.study.websocket.rabbitmq.config;

import com.study.websocket.constans.MqConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BindingConfig {

    private final ExchangeConfig exchangeConfig;
    private final QueueConfig queueConfig;

    @Autowired
    public BindingConfig(ExchangeConfig exchangeConfig, QueueConfig queueConfig) {
        this.exchangeConfig = exchangeConfig;
        this.queueConfig = queueConfig;
    }

    @Bean
    public Binding testBinding() {
        return BindingBuilder
                .bind(queueConfig.testQueue())
                .to(exchangeConfig.testExchange())
                .with(MqConstants.QUEUE_TEST);
    }
}
