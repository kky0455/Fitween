package com.ssafy.api.model;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Data
public class ChatMessageForm {
    private long id;
    private String roomId;
    //보내는 사람
    private String senderId;
    // 받는 사람
    private String receiverId;
    //내용
    private String message;

    private ZonedDateTime senddatetime;

    private Integer isRead;

//     빌더 패턴으로 객체 생성! 생성자의 변형. 입력 순서가 일치하지 않아도 됨.
    public ChatMessage toEntity() {
        return ChatMessage.builder()
                .roomId(roomId)
                .senderId(senderId)
                .receiverId(receiverId)
                .message(message)
                .isRead(isRead)
                .build();
    }

}
