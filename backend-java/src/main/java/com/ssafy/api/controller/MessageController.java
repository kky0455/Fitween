package com.ssafy.api.controller;

import com.ssafy.api.model.ChatMessageForm;
import com.ssafy.api.model.ChatMessage;
import com.ssafy.api.model.ChatRoom;
import com.ssafy.api.model.ChatRoomForm;
import com.ssafy.db.repository.ChatRepository;

import com.ssafy.db.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MessageController {


    private final SimpMessageSendingOperations sendingOperations;
    
    @GetMapping("/chat/findRoom") //
    public String findRoom(String receiverId,String senderId){
        String roomnum = findRoomfunc(receiverId,senderId);
        if (roomnum==null){
            return "-1";
        }
        else{
            return roomnum;

        }

    }
    //진합 확인 후에 열기
    @GetMapping("/chat/log")
    public List<ChatMessage> enter(String roomId){
        return logMessage(roomId);
    }

    @GetMapping("/chat/makeRoom")
    public String roomnum(String receiverId,String senderId){
        String room = findRoomfunc(receiverId,senderId);
        if( room==null){
            return makeRoom(receiverId,senderId).getRoomId();
        }
        else{
            return room;
        }
    }


    //
    //
    //나중에 개인 확인하는 토큰 보내줘야함
    @GetMapping("/chat/roomList")
    public List<ChatRoom>  RoomList(String userId){
        //lastChat 업데이트해서 저장

        return roomList(userId);

    }
    //메세지를 읽었을때 메세지들을 전부 0으로 만들어야함
    // receiver가 접근했을때 senderId-receiverId 가 매칭된 채팅로그의 1을 0으로 바꾼다다
    // 채팅로그에 roomidx와 입장한 유저의 아이디가 receiverId인 모든 로그의 isRead를 0으로 바꾼다.
    @PutMapping("/chat/doRead")
    public void read(@RequestBody Map<String,String> body){
        isReadUpdate(body.get("roomId"),body.get("userId"));
        notRead(body.get("senderId"), body.get("userId"),body.get("roomId"));
    }


    @MessageMapping("/chat/message")
    public void chat(ChatMessage message) {

        String roomnum = CheckRoom(message.getSenderId(),message.getReceiverId());

        if (roomnum == null){
            message.setRoomId(makeRoom(message.getSenderId(), message.getReceiverId()).getRoomId());
        }
        else{

            message.setRoomId(roomnum);
        }
        ZonedDateTime datetime =  ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        //LocalDateTime datetime = LocalDateTime.now();
        message.setSenddatetime(datetime);
        Integer isRead = 1;
        // if(subscribe == True){
        //      isRead = 0
        // }
        message.setIsRead(isRead);
        saveMessage(message.getRoomId(), message.getSenderId(), message.getReceiverId(), message.getMessage(),message.getIsRead());

        updateLastChat(message.getRoomId(),message.getMessage(),message.getSenddatetime(), message.getSenderId(), message.getReceiverId());
        //notRead(message.getSenderId(), message.getReceiverId(), message.getRoomId());
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(),message); // 메세지 알림 보내기
        sendingOperations.convertAndSend("/topic/chat/wait/"+message.getReceiverId(),message); // 메세지 보내기


        //saveMessage(message.getRoomId(), message.getSenderId(), message.getReceiverId(), message.getMessage());
       // sendingOperations.convertAndSend("/topic/chat/wait/"+message.getReceiverId(),message);

        //
//        log.info(chatForm.toString());// 받아온 데이터 확인!
////        // dto(데이터-전달-객체)를 entity(db-저장-객체)로 변경
//        ChatMessage chatMessage = chatForm.toEntity();
////        // 리파지터리에게(db-관리-객체) 전달
//        ChatMessage saved = chatRepository.save(chatMessage);
//        log.info(saved.toString());
////         저장 엔티티의 id(PK)값 반환!
//        return saved.getRoomId()+saved.getReceiverId()+saved.getSenderId();



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
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    public String CheckRoom(String user1Id, String user2Id){
        String roomnum = chatRoomRepository.findByUser1and2(user1Id,user2Id);
        if(roomnum == null){
            return null;
        }
        else{
            return roomnum;
        }

    }


    public ChatRoom makeRoom(String senderId, String receiverId){
        ChatRoom newroom = new ChatRoom();
        ChatRoomForm chatRoomForm = new ChatRoomForm();
        newroom = newroom.create(senderId,receiverId);


        chatRoomForm.setUser1Id(newroom.getUser1Id());
        chatRoomForm.setUser2Id(newroom.getUser2Id());
        chatRoomForm.setRoomId(newroom.getRoomId());
        chatRoomForm.setUser1Nickname(chatRoomRepository.setUserNickname(newroom.getUser1Id()));
        chatRoomForm.setUser2Nickname(chatRoomRepository.setUserNickname(newroom.getUser2Id()));

        ChatRoom chatRoom = chatRoomForm.toEntity();
        //ChatRoom saved = chatRoomRepository.save(chatRoom);
        chatRoomRepository.save(chatRoom);


    return newroom;

    }
    @Autowired
    private ChatRepository chatMessageRepository;
    public void saveMessage(String roomId,String senderId,String receiverId,String message,Integer isRead){

        ChatMessageForm chatMessageForm = new ChatMessageForm();
        chatMessageForm.setMessage(message);
        chatMessageForm.setRoomId(roomId);
        chatMessageForm.setSenderId(senderId);
        chatMessageForm.setReceiverId(receiverId);
        chatMessageForm.setIsRead(isRead);
        ChatMessage chatMessage = chatMessageForm.toEntity();
        chatMessageRepository.save(chatMessage);


    }


    public List<ChatMessage> logMessage(String roomId){
        List<ChatMessage> logcon =  chatMessageRepository.findLogByUser(roomId);

        return logcon;
    }

    public List<ChatRoom> roomList(String user1){
        List<ChatRoom> rooms = chatRoomRepository.findRoomByUser(user1);
        return rooms;

    }

    public String findRoomfunc(String user1,String user2){
        String roomNum = chatRoomRepository.findByUser1and2(user1,user2);
        return roomNum;

    }
    public void updateLastChat(String roomId,String message,ZonedDateTime dateTime,String senderId,String receiverId){
        chatRoomRepository.updateLastChat(roomId,message);
        chatRoomRepository.updateLastChatTime(roomId,dateTime);
        chatRoomRepository.updateLastSenderId(roomId,senderId);
        chatRoomRepository.notReadMessage(senderId,receiverId,roomId);

    }

    public void notRead(String senderId,String receiverId,String roomId){
        chatRoomRepository.notReadMessage(senderId,receiverId,roomId);

    }

    public void isReadUpdate(String roomId,String receiverdId){
        chatMessageRepository.updateIsRead(roomId,receiverdId);

    }




}



