package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class GameDTO
{
    @NotEmpty
    private String gameName;
    @NotEmpty
    private LocalDateTime endDate;
    @NotEmpty
    private int gameLengthDays;

    public String getGameName()
    {
        return gameName;
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }

    public LocalDateTime getEndDate()
    {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate)
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
