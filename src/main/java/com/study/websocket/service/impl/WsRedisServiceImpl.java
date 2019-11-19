package com.study.websocket.service.impl;

import com.study.websocket.constans.SystemConstants;
import com.study.websocket.service.WsRedisService;
import com.study.websocket.utils.ServerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WsRedisServiceImpl implements WsRedisService {

    @Value("${server.port}")
    private String port;

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public WsRedisServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveServerInfo(String sid) {
        log.info("用户【{}】的端口为【{}】", sid, port);
        // todo:是否应该id+port组成一个key
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(SystemConstants.KEY_WEBSOCKET_SERVER, sid, port);
    }

    @Override
    public void deleteServerInfo(String sid) {
        log.info("删除用户【{}】的端口信息", sid);
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(SystemConstants.KEY_WEBSOCKET_SERVER, sid);
    }

    @Override
    public String getServerInfoBySid(String sid) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String port = hashOperations.get(SystemConstants.KEY_WEBSOCKET_SERVER, sid);
        log.info("用户【{}】连接的端口是【{}】", sid, port);
        return port;
    }
}
