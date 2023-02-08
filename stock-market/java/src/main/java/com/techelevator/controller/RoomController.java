package com.techelevator.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.techelevator.dao.StockDao;
import com.techelevator.dao.TradeDao;
import com.techelevator.model.InviteSIMP;
import com.techelevator.model.LobbySIMP;
import com.techelevator.model.PortfolioDTO;
import com.techelevator.model.Stock;

@Controller
public class RoomController {
    // @MessageMapping("/game")
    // @SendTo("/topic/chat")
    // public Message broadcastMessage(Message message) {
    // return message;
    // }

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);
    private final SimpMessagingTemplate simpMessagingTemplate;

    private static Map<String, List<String>> rooms = new HashMap<>();
    private TradeDao tradeDao;
    private StockDao stockDao;

    @Autowired
    public RoomController(StockDao stockDao, TradeDao tradeDao, SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.tradeDao = tradeDao;
        this.stockDao = stockDao;
    }

    @MessageMapping("/room-{gameId}/join")
    public LobbySIMP joinRoom(@DestinationVariable String gameId, String username) {
        log.debug("[Room {} JOIN]: {}", gameId, username);
        if (!rooms.containsKey(gameId)) {
            rooms.put(gameId, new ArrayList<String>());
        }
        List<String> players = rooms.get(gameId);
        if (players.contains(username)) {
            log.debug("[Room {}] {} already exist", gameId, username);
            return new LobbySIMP(gameId, players);
        }
        players.add(username);
        rooms.put(gameId, players);
        log.debug("[Room {}]: {} players ", gameId, players);
        return new LobbySIMP(gameId, players);
    }

    @MessageMapping("room-{gameId}/invite")
    public void inviteUser(@DestinationVariable String gameId, @Payload InviteSIMP invite) {

    }

    @MessageMapping("room-{gameId}/leave")
    public LobbySIMP leaveRoom(@DestinationVariable String gameId, String username) {
        log.debug("[Room {} LEAVE]: {} Left", gameId, username);
        List<String> players = rooms.get(gameId);
        players.remove(username);
        rooms.put(gameId, players);
        log.debug("[Room {}]: {} players ", gameId, players);
        return new LobbySIMP(gameId, players);
    }

    private List<String> getOwnedSymbols(String gameId) {
        List<String> allSymbols = new ArrayList<>();
        Integer id = Integer.parseInt(gameId);
        List<PortfolioDTO> portfolios = tradeDao.getCurrentPortfolioAllPlayers(id);
        for (PortfolioDTO portfolio : portfolios) {
            portfolio.getPortfolio().getStocks().stream().forEach(s -> allSymbols.add(s.getTickerSymbol()));
        }
        return allSymbols;
    }

    @Scheduled(fixedRate = 5000)
    public void stockUpdatePerSec() {
        List<String> allSymbols = new ArrayList<>();
        for (String gameId : rooms.keySet()) {
            List<String> members = rooms.get(gameId);
            if (members.size() == 0) {
                continue;
            }
            allSymbols = Stream.concat(allSymbols.stream(), getOwnedSymbols(gameId).stream())
                    .collect(Collectors.toList());
        }
        if (allSymbols.size() == 0) {
            return;
        }
        log.debug("[PORTFOLIO UPDATE]: {}", allSymbols);
        simpMessagingTemplate.convertAndSend("/topic/update", stockDao.getQuote(String.join(",", allSymbols)));
    }

    // payload:
    // {
    // GameId: Number
    // Stocks: []
    // }

}