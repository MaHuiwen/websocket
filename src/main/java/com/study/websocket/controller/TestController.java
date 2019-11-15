package com.study.websocket.controller;

import com.study.websocket.rabbitmq.producer.TestProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/test")
public class TestController {

    private final TestProducer testProducer;

    @Autowired
    public TestController(TestProducer testProducer) {
        this.testProducer = testProducer;
    }

    @GetMapping(value = "/nginx/{message}")
    public Object testNginx(@PathVariable String message) {
        log.info("nginx负载均衡测试，接收到消息：{}", message);
        return "success";
    }

    @PostMapping(value = "/mq")
    public Object testMq(@RequestBody String message) {
        log.info("消息队列测试，接收到消息：{}", message);
        testProducer.sendToTestQueue(message);
        return "success";
    }

}
