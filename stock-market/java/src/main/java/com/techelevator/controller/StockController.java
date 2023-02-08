package com.techelevator.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.techelevator.dao.ApiStockDao;
import com.techelevator.dao.StockDao;
import com.techelevator.dao.ApiStockDao.FullStockHistoryWrap;
import com.techelevator.dao.ApiStockDao.Stock;
import com.techelevator.dao.ApiStockDao.StockHistory;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class StockController {
	private StockDao stockDao;

	public StockController(StockDao stockDao) {
		this.stockDao = stockDao;
	}

	@GetMapping("/historical-price-full/{symbol}")
	public FullStockHistoryWrap getHistoricalDailyBySymbol(@PathVariable String symbol, @RequestParam String from,
			@RequestParam String to) {

		return stockDao.getHistoricalDailyBySymbol(symbol, from, to);
	}

	@GetMapping("/historical-chart/{duration}/{symbol}")
	public StockHistory[] getHistoricalDataBySymbol(@PathVariable String duration, @PathVariable String symbol) {
		return stockDao.getHistoricalDataBySymbol(duration, symbol);
	}

	@GetMapping("/quote/{symbol}")
	public Stock[] getQuote(@PathVariable String symbol) {
		return stockDao.getQuote(symbol);
	}

	@GetMapping("/quote/")
	public Stock[] getQuote() {
		return stockDao.getQuote("");
	}

	@GetMapping("/search")
	public Stock[] searchSymbol(@RequestParam String query) {
		return stockDao.searchSymbol(query);
	}

}
