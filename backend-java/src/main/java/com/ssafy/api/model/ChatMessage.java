package com.ssafy.api.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    @Id

    //채팅방 ID
    @Column
    private String roomId;
    //보내는 사람
    @Column
    private String senderId;
    // 받는 사람
    @Column
    private String receiverId;
    //내용
    @Column
    private String message;
    //메세지 시각
    @Column
    private LocalTime sendtime;
    //메세지 날짜
    @Column
    private LocalDate senddate;

    @Builder // 빌더 패턴 적용! 추후 설명..!
    public ChatMessage(String roomId, String senderId, String receiverId, String message) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }



}
