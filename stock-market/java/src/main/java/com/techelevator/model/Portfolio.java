package com.techelevator.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    List<Stock> stocks = new ArrayList<>();
    BigDecimal cash;

    public List<Stock> getStocks() {
        return stocks;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }
}
