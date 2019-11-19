package com.study.websocket.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServerUtils {

    // todo:静态变量无法注入
    @Value("${server.port}")
    private static String port;

    public static String getServerPort() {
        log.info("端口号：{}", port);
        return port;
    }
}
