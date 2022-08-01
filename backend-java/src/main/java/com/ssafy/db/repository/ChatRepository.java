package com.ssafy.db.repository;

import com.ssafy.api.model.ChatMessage;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<ChatMessage, Long>{}


