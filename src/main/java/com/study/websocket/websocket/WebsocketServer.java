package com.study.websocket.websocket;

import com.study.websocket.service.WsRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{sid}")
@Component
@Slf4j
public class WebsocketServer {

    private static WsRedisService wsRedisService;

    // 在线人数
    private static int onlineCount = 0;
    // session map, key为sid
    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    @Autowired
    public void setWsRedisService(WsRedisService wsRedisService) {
        WebsocketServer.wsRedisService = wsRedisService;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        if (StringUtils.isBlank(sid)) {
            log.info("sid为空，不建立连接");
            return;
        }

        log.info("用户【{}】建立连接", sid);
        wsRedisService.saveServerInfo(sid);
        addOnlineCount();
        sessionMap.put(sid, session);
        try {
            this.sendMessage(session, "连接成功");
        } catch (Exception e) {
            log.error("websocket IO异常");
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("sid") String sid) {
        log.info("用户【{}】关闭连接", sid);
        wsRedisService.deleteServerInfo(sid);
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
            log.info("用户【{}】不在此服务上", sid);
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
