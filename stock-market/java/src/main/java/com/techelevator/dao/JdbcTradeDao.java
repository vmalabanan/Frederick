package com.techelevator.dao;

import com.techelevator.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcTradeDao implements TradeDao {
    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;
    private CashDao cashDao;
    private GameDao gameDao;

    private static final Logger log = LoggerFactory.getLogger(JdbcTradeDao.class);

    public JdbcTradeDao(JdbcTemplate jdbcTemplate, UserDao userDao, CashDao cashDao, GameDao gameDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
        this.cashDao = cashDao;
        this.gameDao = gameDao;
    }

    @Override
    public Portfolio makeTrade(int userId, int gameId, Trade trade) {
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
        BigDecimal tradeValue = tradeTypeId == 1
                ? trade.getSharePrice().multiply(BigDecimal.valueOf(trade.getNumberOfShares() * -1))
                : trade.getSharePrice().multiply(BigDecimal.valueOf(trade.getNumberOfShares()));

        sql = "BEGIN; " +
                "INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP); " +
                "INSERT INTO cash (game_id, user_id, amount, effective_date) " +
                "VALUES (?, ?, (SELECT amount + ? FROM cash " +
                "WHERE game_id = ? " +
                "AND user_id = ? " +
                "ORDER BY effective_date DESC " +
                "LIMIT 1) " +
                ", CURRENT_TIMESTAMP);" +
                "COMMIT;";

        jdbcTemplate.update(sql, gameId, userId, stockId, tradeTypeId, trade.getNumberOfShares(), trade.getSharePrice(),
                gameId, userId, tradeValue,
                gameId,
                userId);

        // return current portfolio
        return getCurrentPortfolio(userId, gameId);

    }

    @Override
    public Portfolio getCurrentPortfolio(int userId, int gameId) {
        Portfolio portfolio = new Portfolio();

        // set current cash
        log.debug("Setting Cash for UserId:{} in Game: {}", gameId, userId);
        portfolio.setCash(cashDao.getCash(gameId, userId));

        // sql query to get current stock holdings
        String sql = "SELECT s.ticker_symbol " +
                ", (SUM (CASE WHEN t.trade_type_id = 1 THEN t.number_of_shares END) - SUM (CASE WHEN t.trade_type_id = 2 THEN t.number_of_shares ELSE 0 END)) as total_shares "
                +
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
    public List<PortfolioDTO> getCurrentPortfolioAllPlayers(int gameId) {
        List<PortfolioDTO> portfolioAllPlayers = new ArrayList<>();

        // get list of all players
        List<User> users = userDao.getAllUsersByGame(gameId);

        for (User user : users) {
            // get each player's portfolio
            Portfolio portfolio = getCurrentPortfolio(Math.toIntExact(user.getId()), gameId);

            // create/set portfolioDTO object (portfolioDTO is an object comprised of
            // username + portfolio)
            PortfolioDTO portfolioDTO = new PortfolioDTO();
            portfolioDTO.setUsername(user.getUsername());
            portfolioDTO.setPortfolio(portfolio);

            // push portfolioDTO to portfolioAllPlayers
            portfolioAllPlayers.add(portfolioDTO);

        }

        return portfolioAllPlayers;
    }

    @Override
    public List<Portfolio> getPortfolioHistory(int userId, int gameId) {
        List<Portfolio> portfolioHistory = new ArrayList<>();
        // check whether the game has ended
        boolean isGameEnded = gameDao.isGameEnded(gameId);

        // get the game's length
        String sql = "SELECT game_length_days " +
                "FROM games " +
                "WHERE game_id = ?;";

        Integer gameLengthDays = jdbcTemplate.queryForObject(sql, Integer.class, gameId);

        // get the days that have passed since the game started
        sql = "SELECT DATE_PART('day', CURRENT_TIMESTAMP - (SELECT start_date FROM GAMES WHERE game_id = ?));";

        Integer daysSinceGameStart = jdbcTemplate.queryForObject(sql, Integer.class, gameId);

        // set # days to get portfolio history
        // if the game has ended, set = game's length
        // if it hasn't ended, set = days since game started
        int days = isGameEnded ? gameLengthDays : daysSinceGameStart;

        // for # days, set portfolio holdings (as of end of 24-hour period)
        for (int i = 1; i <= days; i++) {
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

        log.debug("Getting results");

        // set stock holdings
        String sql = "SELECT s.ticker_symbol " +
                ", (SUM (CASE WHEN t.trade_type_id = 1 THEN t.number_of_shares END) - SUM (CASE WHEN t.trade_type_id = 2 THEN t.number_of_shares ELSE 0 END)) as total_shares "
                +
                "FROM trades as t " +
                "JOIN stocks as s " +
                "ON t.stock_id = s.stock_id " +
                "WHERE t.game_id = ? " +
                "AND t.user_id = ? " +
                "AND t.trade_date < (SELECT start_date FROM GAMES " +
                "WHERE game_id = ?) + INTERVAL '" + day + " days' " +
                "GROUP BY s.ticker_symbol;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, gameId, userId, gameId);

        while (results.next()) {
            Stock stock = mapRowToStock(results);
            portfolio.getStocks().add(stock);
        }

        return portfolio;
    }

    @Override
    public List<PortfolioHistoryDTO> getPortfolioHistoryAllPlayers(int gameId) {
        List<PortfolioHistoryDTO> portfolioHistoryAllPlayers = new ArrayList<>();

        // get list of all players
        List<User> users = userDao.getAllUsersByGame(gameId);

        for (User user : users) {
            // get each player's portfolio history
            List<Portfolio> portfolioHistory = getPortfolioHistory(Math.toIntExact(user.getId()), gameId);

            // create/set portfolioHistoryDTO object (portfolioHistoryDTO is an object
            // comprised of username + List<Portfolio>)
            PortfolioHistoryDTO portfolioHistoryDTO = new PortfolioHistoryDTO();
            portfolioHistoryDTO.setUsername(user.getUsername());
            portfolioHistoryDTO.setPortfolioHistory(portfolioHistory);

            // push portfolioHistoryDTO to portfolioHistoryAllPlayers
            portfolioHistoryAllPlayers.add(portfolioHistoryDTO);
        }

        return portfolioHistoryAllPlayers;
    }

    private Stock mapRowToStock(SqlRowSet rs) {
        String tickerSymbol = rs.getString("ticker_symbol");
        int totalShares = rs.getInt("total_shares");

        return new Stock(tickerSymbol, totalShares);
    }
}
