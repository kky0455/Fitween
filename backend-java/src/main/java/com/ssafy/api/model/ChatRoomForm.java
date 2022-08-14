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

    private String lastSederId;

    private Integer notReadCount;

    private String user1Nickname;

    private String user2Nickname;


    //     빌더 패턴으로 객체 생성! 생성자의 변형. 입력 순서가 일치하지 않아도 됨.
    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .roomId(roomId)
                .user1Id(user1Id)
                .user2Id(user2Id)
                .lastChat(lastchat)
                .notReadCount(notReadCount)
                .lastSenderId(lastSederId)
                .user1Nickname(user1Nickname)
                .user2Nickname(user2Nickname)
                .build();
    }

}
