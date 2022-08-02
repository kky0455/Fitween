package com.ssafy.db.repository;

import com.ssafy.api.model.ChatRoom;
import org.kurento.client.internal.server.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface ChatRoomRepository extends CrudRepository<ChatRoom, Long> {

    @Query(value = "SELECT roomId FROM ChatRoom t where (t.senderId= ?1 and t.receiverId = ?2)or(t.senderId= ?2 and t.receiverId = ?1)")
    String findByUser1and2(String senderId,String receiverId);

    @Query(value = "SELECT t FROM ChatRoom t where t.senderId= ?1 or t.receiverId = ?1")
    List<ChatRoom> findRoomByUser(String senderId);

}