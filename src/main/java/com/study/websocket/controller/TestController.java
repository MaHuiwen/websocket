package com.study.websocket.controller;

import com.alibaba.fastjson.JSON;
import com.study.websocket.bean.SendMessageDTO;
import com.study.websocket.rabbitmq.producer.TestProducer;
import com.study.websocket.rabbitmq.producer.WsMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Value("${mq.queue.ws}")
    public String queueName;
    @Value("${server.port}")
    public String port;


    private final TestProducer testProducer;
    private final WsMessageProducer wsMessageProducer;

    @Autowired
    public TestController(TestProducer testProducer, WsMessageProducer wsMessageProducer) {
        this.testProducer = testProducer;
        this.wsMessageProducer = wsMessageProducer;
    }

    @GetMapping(value = "/nginx/{message}")
    public Object testNginx(@PathVariable String message) {
        log.info("nginx负载均衡测试，接收到消息：{}", message);
        return queueName + ":success";
    }

    @PostMapping(value = "/mq")
    public Object testMq(@RequestBody String message) {
        log.info("消息队列测试，接收到消息：{}", message);
        testProducer.sendToTestQueue(message);
        return "success";
    }


    @PostMapping(value = "/mq/consumer")
    public Object sendWebsocketMessage(@RequestBody SendMessageDTO dto) {
        log.info("发送ws消息，接收参数：{}", JSON.toJSON(dto));
        wsMessageProducer.sendToQueue(port, JSON.toJSONString(dto));
        return "success";
    }


}
