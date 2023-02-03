<template>
  <div class="options-container">
    <div class="options d-grid gap-3 col-4 mx-auto">
      <button class="btn btn-lg btn-primary" @click="createPage">
        Host Game
      </button>
      <button
        class="btn btn-lg btn-warning"
        v-if="$store.state.invitedGames.length > 0"
        @click="invitationsList"
      >
        Game Invites
      </button>
      <button
        class="btn btn-lg btn-info"
        v-if="$store.state.acceptedGames.length > 0"
        @click="portfolio"
      >
        My Games
      </button>
    </div>
  </div>
</template>

<script>
import gamesService from "../services/GamesService.js";

export default {
  name: "menu",
  methods: {
    createPage() {
      this.$router.push({ name: "createGame" });
    },
    invitationsList() {
      this.$router.push({ name: "invitationsList" });
    },
    portfolio() {
      const firstGame = this.$store.state.acceptedGames[0];
      this.$router.push({
        name: "portfolio",
        params: { id: firstGame.gameId }
      }); 
    }
  },
  created() {
    // get list of game invitations and add to store
    gamesService.getInvitedGames().then(resp => {
      this.$store.commit("SET_INVITED_GAMES", resp.data);
    });

    // get list of all accepted games and add to store
    gamesService.getAcceptedGames().then(resp => {
      this.$store.commit("SET_ACCEPTED_GAMES", resp.data);
    });
  }
};
</script>

<style>
.options-container {
  display: flex;
  justify-content: center;
  margin-top: 4rem;
}

.options {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
</style>
