package com.techelevator.dao;

import java.util.List;

import com.techelevator.dao.ApiStockDao.FullStockHistoryWrap;
import com.techelevator.dao.ApiStockDao.StockApi;
import com.techelevator.dao.ApiStockDao.StockHistory;

public interface StockDao {
    public FullStockHistoryWrap getHistoricalDailyBySymbol(String symbol, String from, String to);

    public StockHistory[] getHistoricalDataBySymbol(String duration, String symbol);

    public List<StockApi> getQuote(String symbol);

    public StockApi[] searchSymbol(String query);
}
