package com.study.websocket.controller;

import com.alibaba.fastjson.JSON;
import com.study.websocket.bean.SendMessageDTO;
import com.study.websocket.websocket.WebsocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/message")
@Slf4j
public class SendMessageController {

    private final WebsocketServer websocketServer;

    @Autowired
    public SendMessageController(WebsocketServer websocketServer) {
        this.websocketServer = websocketServer;
    }

    @PostMapping(value = "/send")
    public Object sendMessage(@RequestBody SendMessageDTO dto) {
        log.info("发送ws消息，接收参数：{}", JSON.toJSON(dto));
        websocketServer.sendMessage(dto.getSid(), dto.getMessage());
        return "success";
    }


}
