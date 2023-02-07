package com.techelevator.model;

public class InviteSIMP {
    private String inviteUser;
    private String gameId;

    public InviteSIMP(String inviteUser, String gameId) {
        this.inviteUser = inviteUser;
        this.gameId = gameId;
    }

    public String getInviteUser() {
        return this.inviteUser;
    }

    public void setInviteUser(String inviteUser) {
        this.inviteUser = inviteUser;
    }

    public String getGameId() {
        return this.gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

}
