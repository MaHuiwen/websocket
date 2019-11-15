package com.study.websocket.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.study.websocket.constans.MqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * 消费者测试
 *
 * @author mhw
 * @version v1.0
 * @date 2019-11-15
 */
@Slf4j
@Component
public class TestConsumer {

    @RabbitListener(queues = {MqConstants.QUEUE_TEST})
    public void testConsume(String json, Message message, Channel channel) {
        try {
            log.info("消费端测试，数据：【{}】", json);
        } finally {
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
