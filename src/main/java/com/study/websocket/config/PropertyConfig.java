package com.study.websocket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class PropertyConfig {

    public static String SERVER_PORT;
    public static String QUEUE_WS_MESSAGE;

    @Value("${server.port}")
    public void setServerPort(String serverPort) {
        SERVER_PORT = serverPort;
    }

    @Value("${mq.queue.ws.message}")
    public void setQueueWsMessage(String queueWsMessage) {
        QUEUE_WS_MESSAGE = queueWsMessage;
    }
}
