package com.techelevator.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;


@Service
public class JdbcCashDao implements CashDao
{
    private JdbcTemplate jdbcTemplate;

    public JdbcCashDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getCash(int gameId, int userId) {
        String sql = "SELECT amount FROM cash " +
                "WHERE game_id = ? " +
                "AND user_id = ? " +
                "ORDER BY effective_date DESC " +
                "LIMIT 1;";

        return jdbcTemplate.queryForObject(sql, BigDecimal.class, gameId, userId);
    }

    @Override
    public void setStartingCash(int gameId, int userId) {
        String sql = "INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (?, ?, 100000, CURRENT_TIMESTAMP);";

        jdbcTemplate.update(sql, gameId, userId);

    }


}
