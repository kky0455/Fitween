package com.ssafy.db.repository;

import com.ssafy.api.model.ChatMessage;
import com.ssafy.api.model.ChatRoom;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ssafy.db.entity.User;

import java.time.LocalDateTime;
import java.util.List;


@Repository

public interface ChatRoomRepository extends CrudRepository<ChatRoom, Long> {

    @Query(value = "SELECT roomId FROM ChatRoom t where (t.user1Id= ?1 and t.user2Id = ?2)or(t.user1Id= ?2 and t.user2Id = ?1)")
    String findByUser1and2(String user1Id,String user2Id);

    @Query(value = "SELECT t FROM ChatRoom t where t.user1Id= ?1 or t.user2Id = ?1")
    List<ChatRoom> findRoomByUser(String user1Id);


    @Modifying
    @Transactional
    @Query(value = "UPDATE ChatRoom t set lastChat = ?2 where roomId = ?1")
    public void updateLastChat(String roomId, String message);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ChatRoom t set lastChatTime = ?2 where roomId = ?1")
    public void updateLastChatTime(String roomId,LocalDateTime dateTime);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ChatRoom r set notReadCount = (SELECT count(*) FROM ChatMessage t where t.senderId= ?1 and t.receiverId = ?2 and t.isRead = 1) where roomId = ?3")
    public void notReadMessage(String senderId,String receiverId,String roomId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ChatRoom t set lastSenderId = ?2 where roomId = ?1")
    public void updateLastSenderId(String roomId,String senderId);


    @Modifying
    @Transactional
    @Query(value = "UPDATE ChatRoom t set  t.user1Nickname = (SELECT nickname from User k where k.userId = ?1) where roomId = ?2")
    public void setUser1Nickname(String user1Id,String roomId);
    @Modifying
    @Transactional
    @Query(value = "UPDATE ChatRoom t set  t.user2Nickname = (SELECT nickname FROM User k where k.userId = ?1) where roomId = ?2")
    public void setUser2Nickname(String user2Id,String roomId);



}