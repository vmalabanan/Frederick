package com.techelevator.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class GameDTO
{
    @NotEmpty
    private String gameName;

    @Min(value = 1)
    private int gameLengthDays;

    private String[] players;

    public String getGameName()
    {
        return gameName;
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }

    public int getGameLengthDays()
    {
        return gameLengthDays;
    }

    public void setGameLengthDays(int gameLengthDays)
    {
        this.gameLengthDays = gameLengthDays;
    }

    public String[] getPlayers() {
        return players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }
}
