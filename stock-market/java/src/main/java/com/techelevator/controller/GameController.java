package com.techelevator.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techelevator.dao.GameDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Game;
import com.techelevator.model.GameDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
//base path: /games
@RequestMapping("/games")
public class GameController
{
    private GameDao gameDao;
    private UserDao userDao;

    public GameController(GameDao gameDao, UserDao userDao)
    {
        this.gameDao = gameDao;
        this.userDao = userDao;
    }

    /**
     *
     * @param gameDto accepts valid gameDTO object from client(name, endDate, length)
     * @param principal gets principal user name to set organizer id
     * @return integer id of newly created game back to client
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public int createGame(@Valid @RequestBody GameDTO gameDto, Principal principal)
    {
        int organizerId = userDao.findIdByUsername(principal.getName());
        boolean created = gameDao.create(gameDto.getGameName(), organizerId, gameDto.getEndDate(), gameDto.getGameLengthDays(), gameDto.getPlayers());
        if (created)
        {
            int newGameId = gameDao.findIdByName(gameDto.getGameName());
            return newGameId;
        }
        return 0;
//        HttpHeaders httpHeaders = new HttpHeaders();
//        return new ResponseEntity<>(new CreateResponse(newGameId), httpHeaders, HttpStatus.CREATED);

    }

    /**
     *
     * @return list of all games currently stored in db
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Game> listGames(Principal principal)
    {
        String username = principal.getName();
        int organizerId = userDao.findIdByUsername(username);
        return gameDao.findAll().stream().filter(game -> game.getOrganizerId() != organizerId).collect(Collectors.toList());
    }

    /**
     *
     * @param id game id as path variable for endpoint
     * @return game object with matching game id
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Game findGameById(@PathVariable int id)
    {
        return gameDao.getGameById(id);
    }

//    static class CreateResponse
//    {
//        private int gameId;
//
//        CreateResponse(int gameId)
//        {
//            this.gameId = gameId;
//        }
//
//        @JsonProperty("id")
//        int getGameId()
//        {
//            return gameId;
//        }
//
//        void setGameId(int gameId)
//        {
//            this.gameId = gameId;
//        }
//    }

}
