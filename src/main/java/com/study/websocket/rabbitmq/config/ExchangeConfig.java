package com.study.websocket.rabbitmq.config;

import com.study.websocket.constans.MqConstants;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfig {

    @Bean
    public DirectExchange testExchange() {
        return new DirectExchange(MqConstants.EXCHANGE_TEST);
    }
}
