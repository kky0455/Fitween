package com.ssafy.db.repository;

import com.ssafy.api.model.ChatMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRepository extends CrudRepository<ChatMessage, Long>{

    @Query(value = "SELECT t FROM ChatMessage t where t.roomId = ?1 ")
    List<ChatMessage> findLogByUser(String roomId);
}


