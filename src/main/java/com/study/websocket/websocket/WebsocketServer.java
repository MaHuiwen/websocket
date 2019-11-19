package com.study.websocket.websocket;

import com.study.websocket.config.PropertyConfig;
import com.study.websocket.rabbitmq.producer.WsForcedCloseProducer;
import com.study.websocket.service.WsRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private static WsForcedCloseProducer wsForcedCloseProducer;

    // session map, key为sid
    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    // 使用@ServerEndpoint注解后，无法用构造器注入。
    // 因为注入的bean是单例，但是此类是每个连接都会创建一个实例（来源于网上）。
    @Autowired
    public void setWsRedisService(WsRedisService wsRedisService, WsForcedCloseProducer wsForcedCloseProducer) {
        WebsocketServer.wsRedisService = wsRedisService;
        WebsocketServer.wsForcedCloseProducer = wsForcedCloseProducer;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        if (StringUtils.isBlank(sid)) {
            log.info("sid为空，不建立连接");
            return;
        }

        String oldServerPort = wsRedisService.getServerInfoBySid(sid);
        if (StringUtils.equals(oldServerPort, PropertyConfig.SERVER_PORT)) {
            // 如果用户已经连接，且为此服务，则直接通知下线
            this.beForcedClose(sid);
        } else if (StringUtils.isNotBlank(oldServerPort)) {
            // 如果用户连接的是其他服务，则发送至MQ通知其离线
            wsForcedCloseProducer.sendToQueue(oldServerPort, sid, "");
        }

        //建立连接
        log.info("用户【{}】建立连接", sid);
        wsRedisService.saveServerInfo(sid);
        sessionMap.put(sid, session);
        this.sendMessage(session, "连接成功");
    }

    @OnClose
    public void onClose(Session session, @PathParam("sid") String sid) {
        this.closeConnection(sid);
    }

    /**
     * 用户被强制挤下线
     * 不删除redis中的数据
     */
    public void beForcedClose(String sid) {
        try {
            Session oldSession = sessionMap.get(sid);
            if (oldSession != null) {
                log.info("用户已经连接，且为此服务【{}】，直接通知下线", PropertyConfig.SERVER_PORT);
                this.sendMessage(oldSession, "用户" + sid + "在其他地方登录");
                oldSession.close();
                sessionMap.remove(sid);
            } else {
                log.warn("此服务中不存在此用户的session");
            }
        } catch (IOException e) {
            log.error("用户被强制挤下线，处理异常", e);
        }

    }

    private void closeConnection(String sid) {
        log.info("用户【{}】关闭连接", sid);
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
