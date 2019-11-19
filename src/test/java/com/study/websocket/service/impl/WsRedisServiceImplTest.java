package com.study.websocket.service.impl;


import com.study.websocket.service.WsRedisService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WsRedisServiceImplTest {

    @Autowired
    private WsRedisService wsRedisService;

    @Test
    void saveServerInfo() {
        wsRedisService.saveServerInfo("123");
    }

    @Test
    void deleteServerInfo() {
    }

    @Test
    void getServerInfoBySid() {
    }
}