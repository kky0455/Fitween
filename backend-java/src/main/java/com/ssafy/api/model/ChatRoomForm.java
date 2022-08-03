package com.ssafy.api.model;

import lombok.Data;

@Data
public class ChatRoomForm {
    private String roomId;
    //보내는 사람
    private String user1Id;
    // 받는 사람
    private String user2Id;
    //내용
    private String lastchat;

    //     빌더 패턴으로 객체 생성! 생성자의 변형. 입력 순서가 일치하지 않아도 됨.
    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .roomId(roomId)
                .user1Id(user1Id)
                .user2Id(user2Id)
                .lastChat(lastchat)
                .build();
    }

}
