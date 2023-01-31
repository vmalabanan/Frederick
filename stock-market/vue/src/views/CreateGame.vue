<template>
  <div>
    <form class="form-create-game" @submit.prevent="createGame">
      <div class="form-floating">
        <input
          type="text"
          id="gameName"
          class="form-control"
          placeholder="Game Name"
          v-model="game.gameName"
          required
          autofocus
        />
        <label for="gameName" class="sr-only">Game Name</label>
      </div>

       <div class="form-floating">
        <input
          type="text"
          id="gameName"
          class="form-control"
          placeholder="Game Name"
          v-model="game.gameName"
          required
          autofocus
        />
        <label for="gameName" class="sr-only">Game Name</label>
      </div>
    </form>
  </div>
</template>

<script>
import gamesService from "../services/GamesService.js";

export default {
  name: "create",
  data() {
    return {
      game: {
        id: 0,
        gameName: "",
        length: 0,
        endDate: 0
      }
    };
  },
  methods: {
    createGame() {
      // send game info to back end
      gamesService.add(this.game).then(console.log("x")); // set game ID with info from backend

      // redirect user to portfolio screen for newly created game
      this.$router.push({ name: "portfolio", params: { id: this.game.id } }); // need to get game id from the backend
    }
  }
};
</script>

<style></style>
