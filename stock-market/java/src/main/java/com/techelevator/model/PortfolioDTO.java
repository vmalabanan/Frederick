package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class PortfolioDTO {
    String username;
    List<Portfolio> portfolioHistory = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Portfolio> getPortfolioList() {
        return portfolioHistory;
    }
}
