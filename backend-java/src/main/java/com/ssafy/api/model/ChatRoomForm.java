package com.ssafy.api.model;

import lombok.Data;

@Data
public class ChatRoomForm {
    private String roomId;
    //보내는 사람
    private String senderId;
    // 받는 사람
    private String receiverId;
    //내용

    //     빌더 패턴으로 객체 생성! 생성자의 변형. 입력 순서가 일치하지 않아도 됨.
    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .roomId(roomId)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
    }

}
