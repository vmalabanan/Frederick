package com.techelevator.model;

// used when sending all users' current portfolios back to the client
public class PortfolioDTO {
    String username;
    Portfolio portfolio;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
