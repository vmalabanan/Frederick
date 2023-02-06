package com.techelevator.model;

public class Stock {
    String tickerSymbol;
    int numberOfShares;

    public Stock(String tickerSymbol, int numberOfShares) {
        this.tickerSymbol = tickerSymbol;
        this.numberOfShares = numberOfShares;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }
}
