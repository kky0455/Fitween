package chat.chat.controller;

import chat.chat.domain.ChatInfo;
import chat.chat.domain.ChatMessage;
import chat.chat.domain.Chating;
import chat.chat.domain.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ChatController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Chating chating(ChatMessage message) throws Exception {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분");
        String formatedNow = now.format(formatter);


        return new Chating( HtmlUtils.htmlEscape(message.getName()) +":" +HtmlUtils.htmlEscape(message.getInfo())+"("+formatedNow+")");
    }


//
}
