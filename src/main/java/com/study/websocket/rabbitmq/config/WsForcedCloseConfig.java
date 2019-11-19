package com.study.websocket.rabbitmq.config;

import com.study.websocket.constans.MqConstants;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用户被迫下限通知
 *
 * @author mhw
 * @version v1.0
 * @date 2019-11-19
 */
@Configuration
public class WsForcedCloseConfig {

    @Value("${mq.queue.ws.close}")
    public String queueName;
    @Value("${server.port}")
    private String port;

    @Bean
    public DirectExchange wsForcedCloseExchange() {
        return new DirectExchange(MqConstants.EXCHANGE_WS_CLOSE);
    }

    @Bean
    public Queue wsForcedCloseQueue() {
        return QueueBuilder.durable(queueName)
                .build();
    }

    @Bean
    public Binding wsForcedCloseBinding() {
        return BindingBuilder
                .bind(wsForcedCloseQueue())
                .to(wsForcedCloseExchange())
                .with(port);
    }
}
