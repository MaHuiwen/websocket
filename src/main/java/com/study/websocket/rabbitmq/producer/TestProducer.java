package com.study.websocket.rabbitmq.producer;

import com.study.websocket.constans.MqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 生产者测试
 *
 * @author mhw
 * @version v1.0
 * @date 2019-11-15
 */
@Component
@Slf4j
public class TestProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TestProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToTestQueue(String msgJSON) {
        log.info("消息生产者，发送数据到测试队列，内容：【{}】", msgJSON);
        rabbitTemplate.convertAndSend(MqConstants.EXCHANGE_TEST, MqConstants.QUEUE_TEST, msgJSON);
    }
}
