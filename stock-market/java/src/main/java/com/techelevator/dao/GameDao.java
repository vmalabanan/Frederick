package com.techelevator.dao;

import com.techelevator.model.Game;

import java.time.LocalDateTime;
import java.util.List;

public interface GameDao
{

    List<Game> getAllGames(int userId);
    List<Game> getInvitedGames(int userId);
    List<Game> getAcceptedGames(int userId);
    List<Game> getRejectedGames(int userId);

    Game getGameById(int gameId);

    Game findByGameName(String gameName);

//    int findIdByName(String gameName);

    int create(String gameName, int organizerId, LocalDateTime endDate, int gameLengthDays, String[] players);


    boolean delete(int gameId);

}
