package com.techelevator.dao;

import com.techelevator.model.Game;

import java.time.LocalDateTime;
import java.util.List;

public interface GameDao
{

    List<Game> findAll();

    Game getGameById(int gameId);

    Game findByGameName(String gameName);

    int findIdByName(String gameName);

//    boolean create(String gameName, int organizerId, LocalDateTime endDate, int gameLengthDays);

    boolean create(String gameName, int organizerId, LocalDateTime endDate, int gameLengthDays, String[] players);


    boolean delete(int gameId);

}
