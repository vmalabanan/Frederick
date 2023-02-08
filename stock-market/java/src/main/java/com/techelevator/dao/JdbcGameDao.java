package com.techelevator.dao;

import com.techelevator.model.Game;
import com.techelevator.model.Invitation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class JdbcGameDao implements GameDao
{
    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;
    private CashDao cashDao;

    public JdbcGameDao(JdbcTemplate jdbcTemplate, UserDao userDao, CashDao cashDao)
    {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
        this.cashDao = cashDao;
    }

    @Override
    public List<Game> getAllGames(int userId)
    {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT g.game_id " +
                ", g.game_name " +
                ", g.organizer_id " +
                ", g.start_date " +
                ", g.end_date " +
                ", g.game_length_days " +
                "FROM games as g " +
                "JOIN games_users as gu " +
                "ON g.game_id = gu.game_id " +
                "WHERE gu.user_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next())
        {
            Game game = mapRowToGame(results);
            games.add(game);
        }
        return games;
    }

    // private helper function
    private List<Game> getFilteredGames(int userId, int invitationStatusId)
    {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT g.game_id " +
                ", g.game_name " +
                ", g.organizer_id " +
                ", g.end_date " +
                ", g.start_date " +
                ", g.game_length_days " +
                "FROM games as g " +
                "JOIN games_users as gu " +
                "ON g.game_id = gu.game_id " +
                "WHERE gu.user_id = ?" +
                "AND gu.invitation_status_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, invitationStatusId);
        while (results.next())
        {
            Game game = mapRowToGame(results);
            games.add(game);
        }
        return games;
    }

    @Override
    public List<Game> getInvitedGames(int userId) {
        return getFilteredGames(userId, 1);
    }

    @Override
    public List<Game> getAcceptedGames(int userId) {
        return getFilteredGames(userId, 2);
    }

    @Override
    public List<Game> getRejectedGames(int userId) {
        return getFilteredGames(userId, 3);
    }

    @Override
    public void updateInvitationStatus(Invitation invitation, int userId) {
        String sql = "UPDATE games_users " +
                "SET invitation_status_id = ? " +
                "WHERE game_id = ? " +
                "AND user_id = ?;";

        jdbcTemplate.update(sql, invitation.getInvitationStatusId(), invitation.getGameId(), userId);

        // if invitation is accepted (invitation_status_id = 2)
        // set starting cash = 100,000
        if (invitation.getInvitationStatusId() == 2) {
            cashDao.setStartingCash(invitation.getGameId(), userId);
        }
    }

    @Override
    public Game getGameById(int gameId)
    {
        String sql = "SELECT * FROM games WHERE game_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, gameId);
        if(results.next())
        {
            return mapRowToGame(results);
        }
        else
        {
            throw new RuntimeException("gameId " + gameId + " was not found.");
        }
    }

    @Override
    public Game findByGameName(String gameName)
    {
        String sql = "SELECT * FROM games WHERE game_name = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, gameName);
        if (results.next())
        {
            return mapRowToGame(results);
        }
        else
        {
            throw new RuntimeException("Game was not found");
        }
    }

    @Override
    public int create(String gameName, int organizerId, int gameLengthDays, String[] players)
    {
        // sql query to insert data into games table
        String sql = "INSERT INTO games (game_name, organizer_id, start_date, end_date, game_length_days) " +
                "VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '" + gameLengthDays + " days', ?) RETURNING game_id";

        // run query and get back gameId
        Integer gameId = jdbcTemplate.queryForObject(sql, Integer.class, gameName, organizerId, gameLengthDays);

        // add organizer to games_users table and set invitation_status to 2 (Accepted)
        sql = "INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (?, ?, ?);";
        jdbcTemplate.update(sql, gameId, organizerId, 2);

        // set organizer's starting cash to 100,000
        cashDao.setStartingCash(gameId, organizerId);

        // for each player in players, add to games_users table and set invitation_status to 1 (Invited)
        for (String player : players) {
            int playerID = userDao.findIdByUsername(player);
            sql = "INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (?, ?, ?);";
            jdbcTemplate.update(sql, gameId, playerID, 1);
        }

        if (gameId != null) {
            return gameId;
        }

        return 0;
    }

    @Override
    public boolean delete(int gameId)
    {
        String sql = "DELETE FROM games WHERE game_id = ?";
        int deleted = jdbcTemplate.update(sql, gameId);
        return deleted > 0;
    }

    @Override
    public boolean isGameEnded(int gameId) {
        String sql = "SELECT CURRENT_TIMESTAMP > end_date FROM GAMES WHERE game_id = ?;";
        Boolean isGameEnded = jdbcTemplate.queryForObject(sql, Boolean.class, gameId);

        return isGameEnded;

    }

    private Game mapRowToGame(SqlRowSet rs)
    {
        int gameId = rs.getInt("game_id");
        String gameName = rs.getString("game_name");
        int organizerId = rs.getInt("organizer_id");
        LocalDateTime startDate = rs.getTimestamp("start_date").toLocalDateTime();
        LocalDateTime endDate = rs.getTimestamp("end_date").toLocalDateTime();
        int gameLengthDays = rs.getInt("game_length_days");

        return new Game(gameId, gameName, organizerId, startDate, endDate, gameLengthDays);
    }
}
