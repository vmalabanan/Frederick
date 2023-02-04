package com.techelevator.dao;


import com.techelevator.model.Trade;

public interface TradeDao
{
    int makeTrade(int userId, int gameId, Trade trade);
}
