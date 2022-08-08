package com.ssafy.db.repository;

import com.ssafy.api.model.ChatRoom;
import org.kurento.client.internal.server.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

}