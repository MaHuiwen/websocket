package com.study.websocket.service.impl;

import com.study.websocket.rabbitmq.producer.WsMessageProducer;
import com.study.websocket.service.WsMessageService;
import com.study.websocket.service.WsRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WsMessageServiceImpl implements WsMessageService {

    private final WsRedisService wsRedisService;
    private final WsMessageProducer wsMessageProducer;

    @Autowired
    public WsMessageServiceImpl(WsRedisService wsRedisService, WsMessageProducer wsMessageProducer) {
        this.wsRedisService = wsRedisService;
        this.wsMessageProducer = wsMessageProducer;
    }

    @Override
    public void sendMessageToMQ(String sid, String message) {
        String port = wsRedisService.getServerInfoBySid(sid);
        wsMessageProducer.sendToQueue(port, sid, message);
    }
}
