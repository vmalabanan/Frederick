package com.techelevator.controller;
import com.techelevator.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/game")
    @SendTo("/topic/chat")
    public Message broadcastMessage(Message message)  {
        return message;
    }
}
