package com.techelevator.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/game")
    @SendTo("/topic/chat")
    public Message broadcastMessage(Message message) {
        return message;
    }

    public static class Message {
        private String fromUser;
        private String content;

        public Message(String fromUser, String content) {
            this.fromUser = fromUser;
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFromUser() {
            return fromUser;
        }

        public void setFromUser(String fromUser) {
            this.fromUser = fromUser;
        }
    }
}
