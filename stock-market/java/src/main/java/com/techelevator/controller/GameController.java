package com.techelevator.controller;

import com.techelevator.dao.GameDao;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/games")
public class GameController
{
    private GameDao gameDao;

    public GameController(GameDao gameDao) {
        this.gameDao = gameDao;
    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(value = "/created", method = RequestMethod.POST)
//    public void createGame(@Valid @RequestBody Game gameDto) {
//
//    }


}
