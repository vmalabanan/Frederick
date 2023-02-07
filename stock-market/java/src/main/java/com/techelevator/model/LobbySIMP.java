package com.techelevator.model;

import java.util.List;

public class LobbySIMP {
    private String gameId;
    private List<String> players;

    public LobbySIMP(String gameId, List<String> players) {
        this.gameId = gameId;
        this.players = players;

    }

    public String getGameId() {
        return this.gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public List<String> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

}
