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
        String sql = "SELECT cash FROM cash " +
                "WHERE game_id = ? " +
                "AND user_id = ?;";

        return jdbcTemplate.queryForObject(sql, BigDecimal.class, gameId, userId);
    }

    @Override
    public void setStartingCash(int gameId, int userId) {
        String sql = "INSERT INTO cash (game_id, user_id, cash) VALUES (?, ?, 100000);";
        jdbcTemplate.update(sql, gameId, userId);
    }
}
