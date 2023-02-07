<template>
  <div id="game">
    <div
      class="alert alert-danger"
      role="alert"
      v-if="respondToInvitationErrors"
    >
      {{ respondToInvitationErrorMsg }}
    </div>
    <span id="name" style="width: 200px">{{ game.gameName }}</span>
    <span id="type" style="width: 60px">Normal</span>
    <span id="length" style="width: 60px">{{ game.gameLengthDays }} days</span>
    <div id="button-container">
      <button id="join" @click="joinGame">Join</button>
      <button id="decline" @click="declineGame">Decline</button>
    </div>
  </div>
</template>

<script>
import gamesService from "../services/GamesService.js";

export default {
  name: "GameInvite",
  props: ["game"],
  data() {
    return {
      invitation: {
        gameId: this.game.gameId,
        invitationStatusId: 0
      },
      respondToInvitationErrors: false,
      respondToInvitationErrorMsg:
        "There were problems responding to the invitation."
    };
  },
  methods: {
    updateInvitationStatus(invitationStatusId) {
      // set invitationStatusId
      this.invitation.invitationStatusId = invitationStatusId;

      // send invitation object to backend
      gamesService
        .updateInvitationStatus(this.invitation)
        .then(response => {
          if (response.status === 200) {
            // if player joins game, redirect to portfolio screen for game
            if (invitationStatusId === 2) {
              this.$router.push({
                name: "portfolio",
                params: { id: this.game.gameId }
              });
            }
            // if player declines game, update list 
            else if (invitationStatusId === 3) {
              this.getInvitationsList();
            }
          }
        })
        .catch(error => {
          const response = error.response;
          if (response.status === 400) {
            this.respondToInvitationErrors = true;
            this.respondToInvitationErrorMsg =
              "Bad Request: Invitation Response Errors";
          }
        });
    },
    joinGame() {
      this.updateInvitationStatus(2);
    },
    declineGame() {
      this.updateInvitationStatus(3);
    },
    getInvitationsList() {
      // get list of game invitations and add to store
      gamesService.getInvitedGames().then(resp => {
        this.$store.commit("SET_INVITED_GAMES", resp.data);
      });
    }
  }
};
</script>

<style>
div#game {
  display: flex;
  background-color: #023047;
  border-radius: 15px;
  height: 60px;
  color: white;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  margin: 10px;
}

div#button-container > button#join {
  background-color: #fb8500;
  border-radius: 15px;
  border: none;
  width: 100px;
  height: 50px;
  margin-right: 20px;
}

div#button-container > button#decline {
  background-color: #219ebc;
  border-radius: 15px;
  border: none;
  width: 100px;
  height: 50px;
}
</style>
