package com.techelevator.controller;

import com.techelevator.dao.GameDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Game;
import com.techelevator.model.GameDTO;
import com.techelevator.model.Invitation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
// base path: /games
@RequestMapping("/games")
public class GameController {
    private GameDao gameDao;
    private UserDao userDao;

    public GameController(GameDao gameDao, UserDao userDao) {
        this.gameDao = gameDao;
        this.userDao = userDao;
    }

    /**
     *
     * @param gameDto   accepts valid gameDTO object from client(name, endDate,
     *                  length)
     * @param principal gets principal user name to set organizer id
     * @return integer id of newly created game back to client
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public int createGame(@Valid @RequestBody GameDTO gameDto, Principal principal) {
        int organizerId = userDao.findIdByUsername(principal.getName());

        return gameDao.create(gameDto.getGameName(), organizerId, gameDto.getEndDate(), gameDto.getGameLengthDays(),
                gameDto.getPlayers());

    }

    /**
     *
     * @return list of ALL games associated with a user (game status = "Invited,"
     *         "Accepted," "Rejected")
     */

    @GetMapping
    public List<Game> getAllGames(Principal principal) {
        String username = principal.getName();
        int organizerId = userDao.findIdByUsername(username);
        return gameDao.getAllGames(organizerId);
    }

    /**
     *
     * @return list of user's games with invitation status = "Invited"
     */
    @GetMapping(value = "/invited")
    public List<Game> getInvitedGames(Principal principal) {
        String username = principal.getName();
        int organizerId = userDao.findIdByUsername(username);
        return gameDao.getInvitedGames(organizerId);
    }

    /**
     *
     * @return list of user's games with invitation status = "Rejected"
     */
    @GetMapping(value = "/rejected")
    public List<Game> getRejectedGames(Principal principal) {
        String username = principal.getName();
        int organizerId = userDao.findIdByUsername(username);
        return gameDao.getRejectedGames(organizerId);
    }

    /**
     *
     * @return list of user's games with invitation status = "Accepted"
     */
    @GetMapping(value = "/accepted")
    public List<Game> getAcceptedGames(Principal principal) {
        String username = principal.getName();
        int organizerId = userDao.findIdByUsername(username);
        return gameDao.getAcceptedGames(organizerId);
    }

    /**
     *
     * @param invitation Invitation object (only gameId is required)
     * @param principal  logged in user
     */
    @PutMapping
    public void updateInvitationStatus(@RequestBody Invitation invitation, Principal principal) { // should I add gameId
                                                                                                  // to path?
        int userId = userDao.findIdByUsername(principal.getName());

        gameDao.updateInvitationStatus(invitation, userId);
    }

    /**
     *
     * @param id game id as path variable for endpoint
     * @return game object with matching game id
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Game findGameById(@PathVariable int id) {
        return gameDao.getGameById(id);
    }

}
