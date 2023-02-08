package com.techelevator.controller;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class StockController {
    private final String BASE_URL = "https://financialmodelingprep.com/api/v3/";
    private final String API_KEY = "apikey=4694ab156015fcae69356511ac9a8a36";
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/historical-price-full/{symbol}")
    public void getHistoricalDailyBySymbol(@PathVariable String symbol, @RequestParam String from,
            @RequestParam String to) {

    }

    @GetMapping("/historical-chart/{duration}/{symbol}")
    public void getHistoricalDataBySymbol(String duration) {

    }

    @GetMapping("/quote/{symbol}")
    public void getQuote(@PathVariable String Symbol) {

    }

    @GetMapping("/search")
    public void searchSymbol(@RequestParam String query) {

    }

}
