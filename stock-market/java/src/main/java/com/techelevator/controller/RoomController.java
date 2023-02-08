package com.techelevator.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.techelevator.model.InviteSIMP;
import com.techelevator.model.LobbySIMP;

@Controller
public class RoomController {
    // @MessageMapping("/game")
    // @SendTo("/topic/chat")
    // public Message broadcastMessage(Message message) {
    // return message;
    // }

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    private static Map<String, List<String>> rooms = new HashMap<>();

    @MessageMapping("/room-{gameId}/join")
    public LobbySIMP joinRoom(@DestinationVariable String gameId, String username) {
        log.debug("Room {}: {} joined", gameId, username);
        if (!rooms.containsKey(gameId)) {
            rooms.put(gameId, new ArrayList<String>());
        }
        List<String> players = rooms.get(gameId);
        if (players.contains(username)) {
            log.debug("Room has {} players ", players);
            return new LobbySIMP(gameId, players);
        }
        players.add(username);
        rooms.put(gameId, players);
        log.debug("Room has {} players ", players);
        return new LobbySIMP(gameId, players);
    }

    @MessageMapping("room-{gameId}/invite")
    public void inviteUser(@DestinationVariable String gameId, @Payload InviteSIMP invite) {

    }

    @MessageMapping("room-{gameId}/leave")
    public LobbySIMP leaveRoom(@DestinationVariable String gameId, String username) {
        log.debug("Room {}: {} Left", gameId, username);
        List<String> players = rooms.get(gameId);
        players.remove(username);
        rooms.put(gameId, players);
        log.debug("Room has {} players ", players);
        return new LobbySIMP(gameId, players);
    }

    @Scheduled(fixedRate = 5000)
    public void updateGame() {
    }

    @SendTo("room-{gameId}/leaderboard")
    public void leaderboardUpdate() {

    }

    @SendTo("room-{gameId}/stocks")
    public void stockUpdate() {

    }

    @SendTo("room-{gameId}/chat")
    public void chatUpdate() {

    }

}