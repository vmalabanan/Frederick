package com.techelevator.controller;

import com.techelevator.dao.TradeDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Portfolio;
import com.techelevator.model.PortfolioDTO;
import com.techelevator.model.PortfolioHistoryDTO;
import com.techelevator.model.Trade;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
     * @param gameId    the game's ID
     * @param trade     accepts valid Trade object from client(tradeDate,
     *                  sharePrice, numberOfShares, tradeTypeDesc, tickerSymbol)
     * @param principal gets principal user name to set organizer id
     */

    // Should the path actually be /{gameId}/{username} ?
    @PostMapping(value = "/{gameId}")
    public Portfolio makeTrade(@PathVariable int gameId, @RequestBody Trade trade, Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());

        return tradeDao.makeTrade(userId, gameId, trade);
    }

    // Should the path actually be /{gameId}/{username} ?
    @GetMapping(value = "/{gameId}")
    public Portfolio getCurrentPortfolio(@PathVariable int gameId, Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());

        return tradeDao.getCurrentPortfolio(userId, gameId);
    }

    // Should the path actually be /{gameId} ?
    @GetMapping(value = "/{gameId}/all")
    public List<PortfolioDTO> getCurrentPortfolioAllPlayers(@PathVariable int gameId, Principal principal) {
        // int userId = userDao.findIdByUsername(principal.getName());

        return tradeDao.getCurrentPortfolioAllPlayers(gameId);
    }

    // Should the path actually be /{gameId}/{username}/{day} ?
    @GetMapping(value = "/{gameId}/{day}")
    public Portfolio getPortfolioByDay(@PathVariable int gameId, @PathVariable int day, Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());

        return tradeDao.getPortfolioByDay(day, userId, gameId);
    }

    // Should the path actually be /{gameId}/history/{username} ?
    @GetMapping(value = "/{gameId}/history")
    public List<Portfolio> getPortfolioHistory(@PathVariable int gameId, Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());

        return tradeDao.getPortfolioHistory(userId, gameId);
    }

    // Should the path actually be /{gameId}/history ?
    @GetMapping(value = "/{gameId}/history/all")
    public List<PortfolioHistoryDTO> getPortfolioHistoryAllPlayers(@PathVariable int gameId) {

        return tradeDao.getPortfolioHistoryAllPlayers(gameId);
    }

}
