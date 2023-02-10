<template>
  <div id="game">
    <div
      class="alert alert-danger"
      role="alert"
      v-if="respondToInvitationErrors"
    >
      {{ respondToInvitationErrorMsg }}
    </div>
    <span id="name" style="width: 14rem">{{ game.gameName }}</span>
    <div id="button-container">
      <button class="btn btn-lg btn-primary" id="join" @click="joinGame">
        Join
      </button>
      <button class="btn btn-lg btn-warning" id="decline" @click="declineGame">
        Decline
      </button>
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
        invitationStatusId: 0,
      },
      respondToInvitationErrors: false,
      respondToInvitationErrorMsg:
        "There were problems responding to the invitation.",
    };
  },
  methods: {
    updateInvitationStatus(invitationStatusId) {
      // set invitationStatusId
      this.invitation.invitationStatusId = invitationStatusId;
 
      // send invitation object to backend
      gamesService
        .updateInvitationStatus(this.invitation)
        .then((response) => {
          if (response.status === 200) {
            // if player joins game, redirect to portfolio screen for game
            if (invitationStatusId === 2) {
              this.$router.push({
                name: "portfolio",
                params: { id: this.game.gameId },
              });
            }
            // if player declines game, update list
            else if (invitationStatusId === 3) {
              this.getInvitationsList();
            }
          }
        })
        .catch((error) => {
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
      gamesService.getInvitedGames().then((resp) => {
        this.$store.commit("SET_INVITED_GAMES", resp.data);
      });
    },
  },
};
</script>

<style scoped>
div#game {
  display: flex;
  background-color: #191616;
  border-radius: 0.5rem;
  height: 3.5rem;
  color: white;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
}

#name {
  font-size: 1.5rem;
}

button {
  margin: 1rem;

}
.btn-primary {
  background-color: #477fc9;
  border: none;
  color: #000;
}

.btn-primary:hover {
  background-color: #1566d1;
  color: #000;
}

.btn-warning {
  background-color: #fc7c31;
  border: none;
}

.btn-warning:hover {
  background-color: #fc650e;
}
</style>
