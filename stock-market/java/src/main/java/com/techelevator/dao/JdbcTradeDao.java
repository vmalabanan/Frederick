package com.techelevator.dao;

import com.techelevator.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

        // get current cash
        return cashDao.getCash(gameId, userId);

    }

    @Override
    public Portfolio getCurrentPortfolio(int userId, int gameId) {
        Portfolio portfolio = new Portfolio();

        // set current cash
        portfolio.setCash(cashDao.getCash(gameId, userId));

        // sql query to get current stock holdings
        String sql = "SELECT s.ticker_symbol " +
                ", (SUM (CASE WHEN t.trade_type_id = 1 THEN t.number_of_shares END) - SUM (CASE WHEN t.trade_type_id = 2 THEN t.number_of_shares ELSE 0 END)) as total_shares " +
                "FROM trades as t " +
                "JOIN stocks as s " +
                "ON t.stock_id = s.stock_id " +
                "WHERE t.game_id = ? " +
                "AND t.user_id = ? " +
                "AND t.trade_date < CURRENT_TIMESTAMP " +
                "GROUP BY s.ticker_symbol;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, gameId, userId);

        while (results.next()) {
            Stock stock = mapRowToStock(results);
            portfolio.getStocks().add(stock);
        }

        return portfolio;
    }

    @Override
    public List<Portfolio> getPortfolioHistory(int userId, int gameId) {
        List<Portfolio> portfolioHistory = new ArrayList<>();

        // for days 1-7, set portfolio holdings (as of end of day)
        for (int i = 1; i <= 7; i++) {
            Portfolio portfolio = getPortfolioByDay(i, userId, gameId);

            portfolioHistory.add(portfolio);

        }

        return portfolioHistory;
    }

    @Override
    public Portfolio getPortfolioByDay(int day, int userId, int gameId) {
        Portfolio portfolio = new Portfolio();

        // set cash
        portfolio.setCash(cashDao.getCashByDay(day, gameId, userId));

        // set stock holdings
        String sql = "SELECT s.ticker_symbol " +
                ", (SUM (CASE WHEN t.trade_type_id = 1 THEN t.number_of_shares END) - SUM (CASE WHEN t.trade_type_id = 2 THEN t.number_of_shares ELSE 0 END)) as total_shares " +
                "FROM trades as t " +
                "JOIN stocks as s " +
                "ON t.stock_id = s.stock_id " +
                "WHERE t.game_id = ? " +
                "AND t.user_id = ? " +
                "AND t.trade_date < (SELECT start_date FROM GAMES " +
                                    "WHERE game_id = ?) + INTERVAL '" + day + " days' " +
                "GROUP BY s.ticker_symbol;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, gameId, userId, gameId);

            while (results.next()) {
                Stock stock = mapRowToStock(results);
                portfolio.getStocks().add(stock);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return portfolio;
    }

    @Override
    public List<PortfolioDTO> getPortfolioHistoryAllPlayers(int gameId) {
        return null;
    }

    private Stock mapRowToStock(SqlRowSet rs)
    {
        String tickerSymbol = rs.getString("ticker_symbol");
        int totalShares = rs.getInt("total_shares");

        return new Stock(tickerSymbol, totalShares);
    }
}
