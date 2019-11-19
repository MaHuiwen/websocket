package com.study.websocket.constans;

import com.study.websocket.utils.ServerUtils;

/**
 * 消息队列常量类
 *
 * @author mhw
 * @version v1.0
 * @date 2019-11-15
 */
public class MqConstants {

    /** 测试队列、交换器名称*/
    public static final String EXCHANGE_TEST = "exchange.test";
    public static final String QUEUE_TEST = "queue.test";

    /** websocket消息推送交换器名称、队列名称前缀*/
    public static final String EXCHANGE_WS_MESSAGE = "exchange.ws.message";
    public static final String QUEUE_WS_MESSAGE = "queue.ws.message." + ServerUtils.getServerPort();

    /** websocket被挤下线消息队列、交换器名称*/
    public static final String EXCHANGE_WS_CLOSE = "exchange.ws.close";
    public static final String QUEUE_WS_CLOSE_PREFIX = "queue.ws.message.";
}
