package com.ssafy.db.repository;

import com.ssafy.api.model.ChatMessage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;


import java.util.List;

public interface ChatRepository extends CrudRepository<ChatMessage, Long>{

    @Query(value = "SELECT t FROM ChatMessage t where t.roomId = ?1 ")
    List<ChatMessage> findLogByUser(String roomId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ChatMessage t set isRead = 0 where roomId = ?1 and receiverId = ?2")
    public void updateIsRead(String roomId, String receiverId);


}


