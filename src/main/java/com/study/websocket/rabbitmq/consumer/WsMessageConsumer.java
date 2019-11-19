package com.study.websocket.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.study.websocket.constans.MqConstants;
import com.study.websocket.constans.SystemConstants;
import com.study.websocket.utils.ServerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * websocket消息推送消费者
 *
 * @author mhw
 * @version v1.0
 * @date 2019-11-19
 */
@Slf4j
@Component
public class WsMessageConsumer {

    @RabbitListener(queues = "${mq.queue.ws}")
    public void testConsume(String json, Message message, Channel channel) {
        try {
            log.info("ws消息推送，消费端测试，数据：【{}】", json);
        } finally {
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
