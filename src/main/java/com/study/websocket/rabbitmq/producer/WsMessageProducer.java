package com.study.websocket.rabbitmq.producer;

import com.study.websocket.constans.MqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * websocket消息推送生产者
 *
 * @author mhw
 * @version v1.0
 * @date 2019-11-19
 */
@Component
@Slf4j
public class WsMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public WsMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToQueue(String routingKey, String msgJSON) {
        log.info("路由【{}】发送消息：【{}】", routingKey, msgJSON);
        rabbitTemplate.convertAndSend(MqConstants.EXCHANGE_WS_MESSAGE, routingKey, msgJSON);
    }
}