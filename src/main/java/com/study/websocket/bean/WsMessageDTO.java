package com.study.websocket.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsMessageDTO {

    /** 推送给的用户*/
    private String sid;

    /** 推送的消息*/
    private String msg;
}
