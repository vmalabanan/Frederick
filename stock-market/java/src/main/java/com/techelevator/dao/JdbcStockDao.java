package com.techelevator.dao;

import com.techelevator.model.Stock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

//for testing only -- to be deleted later
@Service
public class JdbcStockDao implements StockDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcStockDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Stock getStockByTicker(String ticker) {
        Stock stock = new Stock();

        String sql = "SELECT name " +
                ", ticker " +
                ", price " +
                ", change " +
                "FROM stocks " +
                "WHERE ticker = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, ticker);

        if (results.next()) {
            stock = mapRowToStock(results);
        }

        return stock;
    }


    private Stock mapRowToStock(SqlRowSet results) {
        Stock stock = new Stock();
        stock.setName(results.getString("name"));
        stock.setTicker(results.getString("ticker"));
        stock.setPrice(results.getBigDecimal("price"));
        stock.setChange(results.getBigDecimal("change"));

        return stock;
    }
}
