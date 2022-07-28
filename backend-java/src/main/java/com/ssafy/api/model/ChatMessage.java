package com.ssafy.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;
    //채팅방 ID
    private String roomId;
    //보내는 사람
    private String senderId;
    // 받는 사람
    private String receiverId;
    //내용
    private String message;
    //메세지 시각
    private LocalTime sendtime;
    //메세지 날짜
    private LocalDate senddate;

}
