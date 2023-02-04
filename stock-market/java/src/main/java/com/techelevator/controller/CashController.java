package com.techelevator.controller;

import com.techelevator.dao.CashDao;
import com.techelevator.dao.UserDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.security.Principal;


@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")

@RequestMapping("/cash")
public class CashController {
    private CashDao cashDao;
    private UserDao userDao;

    public CashController(CashDao cashDao, UserDao userDao) {
        this.cashDao = cashDao;
        this.userDao = userDao;

    }

    /**
     *
     * @return cash associated with user for a particular game
     */

    @GetMapping(value = "/{gameId}")
    public BigDecimal getCash(@PathVariable int gameId, Principal principal) {
        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        return cashDao.getCash(gameId, userId);
    }


}
