package com.techelevator.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ApiStockDao implements StockDao {
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://financialmodelingprep.com/api/v3";
    private static final String API_KEY = "apikey=4694ab156015fcae69356511ac9a8a36";
    private static final Logger log = LoggerFactory.getLogger(ApiStockDao.class);

    public FullStockHistoryWrap getHistoricalDailyBySymbol(String symbol, String from, String to) {
        String uri = String.format("%s/historical-price-full/%s?from=%s&to=%s&%s", BASE_URL, symbol, from, to, API_KEY);
        log.debug("[Getting History Price Full] Making a call to {}", uri);
        return restTemplate.getForObject(uri, FullStockHistoryWrap.class);
    }

    public StockHistory[] getHistoricalDataBySymbol(String duration, String symbol) {
        String uri = String.format("%s/historical-chart/%s/%s?%s", BASE_URL, duration, symbol, API_KEY);
        log.debug("[Getting History By {}] Making a call to {}", duration, uri);
        return restTemplate.getForObject(uri, StockHistory[].class);
    }

    public List<Stock> getQuote(String symbol) {
        String uri = String.format("%s/quote/%s?%s", BASE_URL, symbol, API_KEY);
        log.debug("[Get Quote] Making a call to {}", uri);
        List<Stock> data = Arrays.asList(restTemplate.getForObject(uri, Stock[].class));
        return data;
    }

    public Stock[] searchSymbol(String query) {
        String uri = String.format("%s/search?query=%s&limit=5&exchange=NASDAQ&%s", BASE_URL, query, API_KEY);
        log.debug("[Search Symbol] Making a call to {}", uri);
        return restTemplate.getForObject(uri, Stock[].class);
    }

    public static class FullStockHistoryWrap {
        private FullStockHistory[] historicalStockList;

        public FullStockHistory[] getHistoricalStockList() {
            return this.historicalStockList;
        }

        public void setHistoricalStockList(FullStockHistory[] historicalStockList) {
            this.historicalStockList = historicalStockList;
        }

    }

    public static class FullStockHistory {
        private String symbol;
        private StockHistory[] historical;

        public String getSymbol() {
            return this.symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public StockHistory[] getHistorical() {
            return this.historical;
        }

        public void setHistorical(StockHistory[] historical) {
            this.historical = historical;
        }

    }

    public static class StockHistory {
        private String date;
        private BigDecimal low;

        public String getDate() {
            return this.date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public BigDecimal getLow() {
            return this.low;
        }

        public void setLow(BigDecimal low) {
            this.low = low;
        }

    }

    public static class Stock {
        private String symbol;
        private String name;
        private BigDecimal price;
        private BigDecimal changesPercentage;

        public BigDecimal getChangesPercentage() {
            return this.changesPercentage;
        }

        public void setChangesPercentage(BigDecimal changesPercentage) {
            this.changesPercentage = changesPercentage;
        }

        public Stock() {

        }

        public String getSymbol() {
            return this.symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getPrice() {
            return this.price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

    }
}
