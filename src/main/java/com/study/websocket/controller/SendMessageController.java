package com.study.websocket.controller;

import com.alibaba.fastjson.JSON;
import com.study.websocket.bean.SendMessageDTO;
import com.study.websocket.rabbitmq.producer.WsMessageProducer;
import com.study.websocket.service.WsMessageService;
import com.study.websocket.websocket.WebsocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/message")
@Slf4j
public class SendMessageController {

    private final WebsocketServer websocketServer;
    private final WsMessageProducer wsMessageProducer;
    private final WsMessageService wsMessageService;

    @Autowired
    public SendMessageController(WebsocketServer websocketServer, WsMessageProducer wsMessageProducer, WsMessageService wsMessageService) {
        this.websocketServer = websocketServer;
        this.wsMessageProducer = wsMessageProducer;
        this.wsMessageService = wsMessageService;
    }

    @PostMapping(value = "/send/local")
    public Object sendMessage(@RequestBody SendMessageDTO dto) {
        log.info("发送ws消息，接收参数：{}", JSON.toJSON(dto));
        websocketServer.sendMessage(dto.getSid(), dto.getMessage());
        return "success";
    }

    @PostMapping(value = "/send/mq")
    public Object sendWebsocketMessage(@RequestBody SendMessageDTO dto) {
        log.info("发送ws消息，接收参数：{}", JSON.toJSON(dto));
        wsMessageService.sendMessageToMQ(dto.getSid(), dto.getMessage());
        return "success";
    }


}
