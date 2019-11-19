package com.study.websocket.rabbitmq.config;

import com.study.websocket.constans.MqConstants;
import com.study.websocket.utils.ServerUtils;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WsMessageConfig {

    @Value("${mq.queue.ws}")
    public String queueName;
    @Value("${server.port}")
    private String port;

    @Bean
    public DirectExchange wsMessageExchange() {
        return new DirectExchange(MqConstants.EXCHANGE_WS_MESSAGE);
    }

    @Bean
    public Queue wsMessageQueue() {
        return QueueBuilder.durable(queueName)
                .build();
    }

    @Bean
    public Binding wsMessageBinding() {
        return BindingBuilder
                .bind(wsMessageQueue())
                .to(wsMessageExchange())
                .with(port);
    }
}
