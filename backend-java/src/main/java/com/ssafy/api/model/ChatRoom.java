package com.ssafy.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {
    @Id

    private String roomId;
    private String senderId;
    private String receiverId;

    private String lastChat;


    public static ChatRoom create(String sender,String receiver) {
        ChatRoom room = new ChatRoom();
        room.senderId = sender;
        room.receiverId = receiver;
        room.roomId = UUID.randomUUID().toString();

        return room;
    }

    @Builder // 빌더 패턴 적용! 추후 설명..!
    public ChatRoom(String roomId, String senderId, String receiverId) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
}