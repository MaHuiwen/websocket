package com.study.websocket.rabbitmq.consumer;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.study.websocket.bean.WsMessageDTO;
import com.study.websocket.websocket.WebsocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final WebsocketServer websocketServer;

    @Autowired
    public WsMessageConsumer(WebsocketServer websocketServer) {
        this.websocketServer = websocketServer;
    }


    @RabbitListener(queues = "${mq.queue.ws}")
    public void testConsume(String json, Message message, Channel channel) {
        try {
            log.info("ws消息推送，消费端测试，数据：【{}】", json);
            WsMessageDTO wsMessageDTO = JSON.parseObject(json, WsMessageDTO.class);
            websocketServer.sendMessage(wsMessageDTO.getSid(), wsMessageDTO.getMsg());
        } finally {
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
