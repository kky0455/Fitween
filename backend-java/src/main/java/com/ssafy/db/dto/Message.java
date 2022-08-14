package com.ssafy.db.dto;

import lombok.Data;

@Data
public class Message {

    private StatusEnum status;
    private String responseType;
    private String userId;

    public Message(){
        this.status = StatusEnum.BAD_REQUEST;
        this.userId = null;
        this.responseType = null;
    }
}
