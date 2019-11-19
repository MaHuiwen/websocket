package com.study.websocket.constans;

import com.study.websocket.utils.ServerUtils;
import org.springframework.beans.factory.annotation.Value;

public class SystemConstants {

    @Value("${server.port}")
    private static String port;

    public static final String KEY_WEBSOCKET_SERVER = "KEY_WEBSOCKET_SERVER";
    public static final String SERVER_PORT = port;
}
