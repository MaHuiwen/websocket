package com.study.websocket.bean;

import lombok.Data;

@Data
public class SendMessageDTO {
    private String sid;

    private String message;
}
