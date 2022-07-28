package com.ssafy.api.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    private String senderId;
    private String receiverId;
    private String message;
    private String date;
    // private String status;
    private String roomId;



}
