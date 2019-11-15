package com.study.websocket.websocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{sid}")
@Component
@Slf4j
public class WebsocketServer {
    // 在线人数
    private static int onlineCount = 0;

    // session map, key为sid
    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        log.info("用户【{}】建立连接", sid);
        addOnlineCount();
        sessionMap.put(sid, session);
        try {
            sendMessage(session, "连接成功");
        } catch (Exception e) {
            log.error("websocket IO异常");
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("sid") String sid) {
        log.info("用户【{}】关闭连接", sid);
        subOnlineCount();
        sessionMap.remove(sid);
    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("sid") String sid) {
        log.info("收到来自用户【{}】的消息：【{}】", sid, message);
        sendMessage(session, "收到消息:" + message);
    }

    @OnError
    public void OnError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    public void sendMessage(String sid, String message) {
        Session session = sessionMap.get(sid);
        if (session == null) {
            log.info("用户【{}】不在线", sid);
            return;
        }
        this.sendMessage(session, message);
    }

    private void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发送给用户【{}】的消息异常", getUidBySession(session));
        }

    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        WebsocketServer.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        WebsocketServer.onlineCount--;
    }

    /**
     * 根据WebSocketSession获取请求路径，进而获取token
     * @param session
     * @return
     */
    private String getUidBySession(Session session) {
        String path = session.getRequestURI().getPath();
        int index = StringUtils.lastIndexOf(path, "/");
        if (index < 0) {
            log.error("[WebSocket]--websocket请求路径：{},非法", path);
            throw new RuntimeException("websocket请求路径非法");
        }
        return StringUtils.substring(path, index + 1);
    }
}
