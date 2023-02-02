package com.techelevator.model;


public class Invitation {
    private int gameId;
    private int userId;
    private int invitationStatusId;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getInvitationStatusId() {
        return invitationStatusId;
    }

    public void setInvitationStatusId(int invitationStatusId) {
        this.invitationStatusId = invitationStatusId;
    }
}
