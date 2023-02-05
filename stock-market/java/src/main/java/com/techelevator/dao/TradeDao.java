package com.techelevator.dao;


import com.techelevator.model.Trade;

public interface TradeDao
{
    void makeTrade(int userId, int gameId, Trade trade);
}
