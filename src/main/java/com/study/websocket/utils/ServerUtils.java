package com.study.websocket.utils;

import org.springframework.beans.factory.annotation.Value;

public class ServerUtils {

    @Value("${server.port}")
    private static String port;

    public static String getServerPort() {
        return port;
    }
}
