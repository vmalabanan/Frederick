package com.techelevator.controller;

import com.techelevator.dao.StockDao;
import com.techelevator.model.Stock;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/stocks")
@PreAuthorize("isAuthenticated()")
public class StockController {
    private StockDao stockDao;

    public StockController(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @GetMapping(value = "/{ticker}")
    public Stock getStockByTicker(@PathVariable String ticker) {
        return stockDao.getStockByTicker(ticker);
    }
}
