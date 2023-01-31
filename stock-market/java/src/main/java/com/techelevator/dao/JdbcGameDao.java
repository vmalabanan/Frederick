package com.techelevator.dao;

import com.techelevator.model.Game;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcGameDao implements GameDao
{
    private JdbcTemplate jdbcTemplate;

    public JdbcGameDao(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Game> findAll()
    {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT + FROM games";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next())
        {
            Game game = mapRowToGame(results);
            games.add(game);
        }
        return games;
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
    public int findIdByName(String gameName)
    {
        String sql = "SELECT game_id FROM games WHERE game_name = ?";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, gameName);
        if (id != null)
        {
            return id;
        }
        else
        {
            throw new RuntimeException("Game was not found");
        }
    }

    @Override
    public boolean create(String gameName, int organizerId, LocalDateTime endDate, int gameLengthDays)
    {
        String sql = "INSERT INTO games (game_name, organizer_id, end_date, game_length_days " +
                     "(?, ?, ?, ?)";

        int created = jdbcTemplate.update(sql, gameName, organizerId, endDate, gameLengthDays);
        return created > 0;
    }

    @Override
    public boolean delete(int gameId)
    {
        String sql = "DELETE FROM games WHERE game_id = ?";
        int deleted = jdbcTemplate.update(sql, gameId);
        return deleted > 0;
    }

    private Game mapRowToGame(SqlRowSet rs)
    {
        int gameId = rs.getInt("game_id");
        String gameName = rs.getString("game_name");
        int organizerId = rs.getInt("organizer_id");
        LocalDateTime endDate = rs.getTimestamp("end_date").toLocalDateTime();
        int gameLengthDays = rs.getInt("game_length_days");

        return new Game(gameId, gameName, organizerId, endDate, gameLengthDays);
    }
}
