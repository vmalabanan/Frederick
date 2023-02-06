package com.techelevator.controller;

import com.techelevator.dao.TradeDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Portfolio;
import com.techelevator.model.Stock;
import com.techelevator.model.Trade;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

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
     */
    @PostMapping(value="/{gameId}")
    public BigDecimal makeTrade(@PathVariable int gameId, @RequestBody Trade trade, Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());

        return tradeDao.makeTrade(userId, gameId, trade);
    }

    @GetMapping(value="/{gameId}")
    public Portfolio getCurrentPortfolio(@PathVariable int gameId, Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());

        return tradeDao.getCurrentPortfolio(userId, gameId);
    }

    @GetMapping(value="/{gameId}/history")
    public List<Portfolio> getTradesHistory(@PathVariable int gameId, Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());

        return tradeDao.getTradesHistory(userId, gameId);
    }


}
