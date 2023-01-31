package com.techelevator.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Game
{
    private int gameId;
    private String game_name;
    private int organizer_id;
    private LocalDateTime end_date;
    private int gameLengthDays;

    public Game(int gameId, String game_name, int organizer_id, LocalDateTime end_date, int gameLengthDays)
    {
        this.gameId = gameId;
        this.game_name = game_name;
        this.organizer_id = organizer_id;
        this.end_date = end_date;
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

    public String getGame_name()
    {
        return game_name;
    }

    public void setGame_name(String game_name)
    {
        this.game_name = game_name;
    }

    public int getOrganizer_id()
    {
        return organizer_id;
    }

    public void setOrganizer_id(int organizer_id)
    {
        this.organizer_id = organizer_id;
    }

    public LocalDateTime getEnd_date()
    {
        return end_date;
    }

    public void setEnd_date(LocalDateTime end_date)
    {
        this.end_date = end_date;
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
