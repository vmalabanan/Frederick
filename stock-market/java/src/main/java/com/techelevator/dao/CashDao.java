package com.techelevator.dao;


import java.math.BigDecimal;


public interface CashDao
{

    BigDecimal getCash(int gameId, int userId);

    void setStartingCash(int gameId, int userId);

}
