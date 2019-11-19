package com.study.websocket.service;

/**
 * 用于操作存放在redis中的websocket连接信息
 *
 * @author mhw
 * @version v1.0
 * @date 2019-11-15
 */
public interface WsMessageService {

    /**
     * 向用户推送ws消息
     *
     * @param
     * @return
     */
    void sendMessageToMQ(String sid, String message);
}
