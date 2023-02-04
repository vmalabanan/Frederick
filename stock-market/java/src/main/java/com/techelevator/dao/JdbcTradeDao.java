package com.techelevator.dao;

import com.techelevator.model.Trade;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JdbcTradeDao implements TradeDao
{
    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;
    private CashDao cashDao;

    public JdbcTradeDao(JdbcTemplate jdbcTemplate, UserDao userDao, CashDao cashDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
        this.cashDao = cashDao;
    }

    @Override
    public int makeTrade(int userId, int gameId, Trade trade) {
        // insert tickerSymbol into stock table if doesn't already exist
        String tickerSymbol = trade.getTickerSymbol();
        String sql = "INSERT INTO stocks (ticker_symbol)  " +
                "VALUES (?) " +
                "ON CONFLICT DO NOTHING;";

        jdbcTemplate.update(sql, tickerSymbol);

        // get the stockId
        sql = "SELECT stock_id FROM stocks " +
                "WHERE ticker_symbol = ?;";

        Integer stockId = jdbcTemplate.queryForObject(sql, Integer.class, tickerSymbol);

        // get tradeTypeId based on tradeTypeDesc
        String tradeTypeDesc = trade.getTradeTypeDesc();

        sql = "SELECT trade_type_id FROM trade_type " +
                "WHERE trade_type_desc = ?;";

        Integer tradeTypeId = jdbcTemplate.queryForObject(sql, Integer.class, tradeTypeDesc);

        // insert trade into trades table
        sql = "INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "RETURNING trade_id;";

        Integer tradeId = jdbcTemplate.queryForObject(sql, Integer.class, gameId, userId, stockId, tradeTypeId, trade.getNumberOfShares(), trade.getSharePrice(), trade.getTradeDate());

        return tradeId;
    }
}
