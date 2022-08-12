package com.ssafy.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

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
    private Integer isRead;

    //메세지 날짜
    @Column(columnDefinition = "TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul")
    @UpdateTimestamp
    private LocalDateTime senddatetime;

    @Builder // 빌더 패턴 적용! 추후 설명..!
    public ChatMessage(String roomId, String senderId, String receiverId, String message,Integer isRead) {
        //this.id
        this.roomId = roomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.isRead = isRead;


    }



}
