package com.techelevator.model;

public class Message {

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

    private String fromUser;

    public Message() {
    }

}
