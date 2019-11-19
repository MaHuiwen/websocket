package com.study.websocket.rabbitmq.producer;

import com.alibaba.fastjson.JSON;
import com.study.websocket.bean.WsMessageDTO;
import com.study.websocket.constans.MqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * websocket关闭连接通知推送生产者
 *
 * @author mhw
 * @version v1.0
 * @date 2019-11-19
 */
@Component
@Slf4j
public class WsForcedCloseProducer {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public WsForcedCloseProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToQueue(String routingKey, String sid, String msgJSON) {
        log.info("路由【{}】给用户【{}】发送消息：【{}】", routingKey, sid, msgJSON);
        WsMessageDTO wsMessageDTO = new WsMessageDTO(sid, msgJSON);
        rabbitTemplate.convertAndSend(MqConstants.EXCHANGE_WS_CLOSE, routingKey, JSON.toJSONString(wsMessageDTO));
    }
}
