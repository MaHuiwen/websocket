package com.study.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @GetMapping(value = "/nginx/{message}")
    public Object testNginx(@PathVariable String message) {
        log.info("nginx负载均衡测试，接收到消息：{}", message);
        return "success";
    }

}
