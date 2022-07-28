package com.ssafy.api.controller;

import com.ssafy.api.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    //message -> 단체전달
    @MessageMapping("/private-message")
    public Message receiverPrivateMessage(@Payload Message message){
        simpMessagingTemplate.convertAndSendToUser(message.getRoomId(),"/private",message);
        simpMessagingTemplate.
        return message;

        //sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(),message);


    }



}
