package com.ssafy.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor

public class ChatRoom {
    @Id

    private String roomId;
    @Column
    private String user1Id;
    @Column
    private String user2Id;

    @Column
    private String lastChat;
    @Column
    private LocalDateTime lastChatTime;

    @Column
    private String lastSenderId;

    @Column
    private Integer notReadCount;


    public static ChatRoom create(String user1Id,String user2Id) {
        ChatRoom room = new ChatRoom();
        room.user1Id = user1Id;
        room.user2Id = user2Id;
        room.roomId = UUID.randomUUID().toString();

        return room;
    }

    @Builder // 빌더 패턴 적용! 추후 설명..!
    public ChatRoom(String roomId, String user1Id, String user2Id,String lastChat,Integer notReadCount,String lastSenderId) {
        this.roomId = roomId;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.lastChat = lastChat;
        this.lastSenderId = lastSenderId;
        this.notReadCount= notReadCount;
    }
}