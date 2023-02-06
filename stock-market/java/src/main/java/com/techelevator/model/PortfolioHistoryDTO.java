package com.techelevator.model;


import java.util.ArrayList;
import java.util.List;

// used when sending all users' portfolio histories back to the client
public class PortfolioHistoryDTO {
    String username;
    List<Portfolio> portfolioHistory = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Portfolio> getPortfolioHistory() {
        return portfolioHistory;
    }
}
