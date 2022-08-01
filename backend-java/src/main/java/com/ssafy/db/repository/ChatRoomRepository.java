package com.ssafy.db.repository;

import com.ssafy.api.model.ChatRoom;
import org.springframework.data.repository.CrudRepository;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, Long> {}