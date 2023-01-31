package com.techelevator.controller;

import com.techelevator.dao.GameDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Game;
import com.techelevator.model.GameDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
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

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public int createGame(@Valid @RequestBody GameDTO gameDto, Principal principal)
    {
        int organizerId = userDao.findIdByUsername(principal.getName());
        boolean created = gameDao.create(gameDto.getGameName(), organizerId, gameDto.getEndDate(), gameDto.getGameLengthDays());
        return created ? gameDao.findIdByName(gameDto.getGameName()) : 0;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Game> listGames()
    {
        return gameDao.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Game findGameById(@PathVariable int id)
    {
        return gameDao.getGameById(id);
    }

}
