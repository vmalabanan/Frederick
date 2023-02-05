package com.techelevator.dao;


import com.techelevator.model.Trade;

import java.math.BigDecimal;

public interface TradeDao
{
    BigDecimal makeTrade(int userId, int gameId, Trade trade);
}
