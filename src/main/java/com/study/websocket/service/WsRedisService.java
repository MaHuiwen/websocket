package com.study.websocket.service;

public interface WsRedisService {

    /**
     * 保存ws连接的服务信息
     *
     * @param
     * @return
     */
    void saveServerInfo(String sid);

    /**
     * 删除ws连接的服务信息
     *
     * @param
     * @return
     */
    void deleteServerInfo(String sid);

    /**
     * 通过用户信息获取连接的端口
     *
     * @param
     * @return
     */
    String getServerInfoBySid(String sid);
}
