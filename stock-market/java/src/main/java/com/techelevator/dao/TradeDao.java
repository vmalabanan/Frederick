package com.techelevator.dao;

import com.techelevator.model.Portfolio;
import com.techelevator.model.PortfolioDTO;
import com.techelevator.model.Trade;
import java.math.BigDecimal;
import java.util.List;

public interface TradeDao
{
    BigDecimal makeTrade(int userId, int gameId, Trade trade);

    Portfolio getCurrentPortfolio(int userId, int gameId);

    Portfolio getPortfolioByDay(int day, int userId, int gameId);

    List<Portfolio> getPortfolioHistory(int userId, int gameId);

    List<PortfolioDTO> getPortfolioHistoryAllPlayers(int gameId);


}
