package com.ssafy.api.controller;

import com.ssafy.api.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/chat/message")
    public void enter(ChatMessage message) {
        //System.out.println(message.getRoomId() + message.getSender() + message.getMessage());

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        message.setSendtime(time);
        message.setSenddate(date);


        String roomId = message.getRoomId();
        if(roomId == null){
           String newroomId = makeRoom(message.getSenderId(), message.getReceiverId()) ;
           message.setRoomId(newroomId);
            sendingOperations.convertAndSend("/topic/chat/room/"+newroomId,message);


        }
        else{
            sendingOperations.convertAndSend("/topic/chat/room/"+roomId,message);

        }

        sendingOperations.convertAndSend("/topic/chat/wait/"+message.getReceiverId(),message);




       // sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(),message);
    }
    //@MessageMapping("/chat/")

    //1.senderId 와  receiverId , 채팅메시지 를 받는다
    //2.그 senderId,receiverId로 db (chatRoom Table)에서 둘의 대화방이 있는지 검사한다.
    //없다면 새로운 대화방을 만든다 (DB까지) -> receiverId로 채팅을 보낸다.

    //있다면 그  방에 채팅을 보낸다.


    //senderId와 receiverId의 방이 있다면 true 없으면 false 리턴
    //senderId -> 보낸 사람 receiverId -> 받는 사람
    // chatRoom table에서 senderId와 receiverId가 같이 있는 튜플을 발견하면 그 튜플의 roomId를 리턴
    // 없으면 0리턴
    public int CheckRoom(String senderId, String receiverId){

        if(senderId.equals("true")){
            return 1;
        }
        else{
            return -1;
        }

    }

    public String makeRoom(String senderId, String receiverId){
        return "2";

    }
}


