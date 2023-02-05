package com.techelevator.dao;

import com.techelevator.model.Trade;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
    public BigDecimal makeTrade(int userId, int gameId, Trade trade) {
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

        // insert trade into trades table and update user's cash
        // calculate trade value; if a Buy transaction, multiply by -1
        BigDecimal tradeValue = tradeTypeId == 1 ? trade.getSharePrice().multiply(BigDecimal.valueOf(trade.getNumberOfShares() * -1)) : trade.getSharePrice().multiply(BigDecimal.valueOf(trade.getNumberOfShares()));

        sql = "BEGIN; " +
                "INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?); " +
                "UPDATE CASH " +
                "SET amount = ( " +
                    "SELECT amount + ? FROM CASH " +
                    "WHERE game_id = ? " +
                    "AND user_id = ? " +
                ") " +
                "WHERE game_id = ? " +
                "AND user_id = ?; " +
                "COMMIT;";

        jdbcTemplate.update(sql, gameId, userId, stockId, tradeTypeId, trade.getNumberOfShares(), trade.getSharePrice(), trade.getTradeDate(),
                            tradeValue, gameId, userId,
                            gameId, userId);

        // get cash
        return cashDao.getCash(gameId, userId);

    }
}
