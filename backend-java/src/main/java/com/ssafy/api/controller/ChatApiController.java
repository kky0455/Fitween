package com.ssafy.api.controller;


import com.ssafy.api.model.ChatMessageForm;
import com.ssafy.api.model.ChatMessage;
import com.ssafy.db.repository.ChatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ChatApiController {
    @Autowired // 리파지터리 객체를 알아서 가져옴! 자바는 new ArticleRepository() 해야 했음!
    private ChatRepository chatRepository;
    @PostMapping("/chat/test") // Post 요청이 "/api/articles" url로 온다면, 메소드 수행!
    public String create(@RequestBody ChatMessageForm chatForm) {// JSON 데이터를 받아옴!
        log.info(chatForm.toString());// 받아온 데이터 확인!
//        // dto(데이터-전달-객체)를 entity(db-저장-객체)로 변경
        ChatMessage chatMessage = chatForm.toEntity();
//        // 리파지터리에게(db-관리-객체) 전달
        ChatMessage saved = chatRepository.save(chatMessage);
        log.info(saved.toString());
//         저장 엔티티의 id(PK)값 반환!
        return saved.getRoomId()+saved.getReceiverId()+saved.getSenderId();
    }
}



