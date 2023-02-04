package com.techelevator.controller;

import com.techelevator.dao.TradeDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Trade;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping("/trades")
public class TradeController {
    private TradeDao tradeDao;
    private UserDao userDao;

    public TradeController(TradeDao tradeDao, UserDao userDao) {
        this.tradeDao = tradeDao;
        this.userDao = userDao;
    }

    /**
     *
     * @param gameId the game's ID
     * @param trade accepts valid Trade object from client(tradeDate, sharePrice, numberOfShares, tradeTypeDesc, tickerSymbol)
     * @param principal gets principal user name to set organizer id
     * @return integer id of newly created game back to client
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value="/{gameId}")
    public int makeTrade(@PathVariable int gameId, @RequestBody Trade trade, Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());

        return tradeDao.makeTrade(userId, gameId, trade);

    }
}
