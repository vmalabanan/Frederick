package com.techelevator.model;
import java.time.LocalDateTime;

public class Game
{
    private int gameId;
    private String gameName;
    private int organizerId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int gameLengthDays;

    public Game(int gameId, String gameName, int organizerId, LocalDateTime startDate, LocalDateTime endDate, int gameLengthDays) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.organizerId = organizerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.gameLengthDays = gameLengthDays;
    }

    public int getGameId()
    {
        return gameId;
    }

    public void setGameId(int gameId)
    {
        this.gameId = gameId;
    }

    public String getGameName()
    {
        return gameName;
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }

    public int getOrganizerId()
    {
        return organizerId;
    }

    public void setOrganizerId(int organizerId)
    {
        this.organizerId = organizerId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getEndDate()
    {
        return endDate;
    }

    public void setEnd_date(LocalDateTime endDate)
    {
        this.endDate = endDate;
    }

    public int getGameLengthDays()
    {
        return gameLengthDays;
    }

    public void setGameLengthDays(int gameLengthDays)
    {
        this.gameLengthDays = gameLengthDays;
    }
}
