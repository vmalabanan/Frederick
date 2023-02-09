<template>
  <div>
    <div class="dropdown">
      <button
        class="btn btn-secondary btn-lg dropdown-toggle"
        type="button"
        data-bs-toggle="dropdown"
        aria-expanded="false"
        @click="showAllGames()"
      >
        {{ gameName }}
      </button>
      <ul class="list-group" v-show="showGames">
        <li
          v-for="game in myGames"
          :key="game.id"
          @click="goToGame(game.gameId)"
          class="list-group-item"
        >
          {{ game.gameName }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import gamesService from "../services/GamesService";

export default {
  name: "dropdown",
  props: ["gameName"],
  data() {
    return {
      myGames: [],
      showGames: false,
    };
  },
  created() {
    gamesService.getAcceptedGames().then((response) => {
      this.myGames = response.data;
      console.log("this.myGames length: " + this.myGames.length);
    });
  },
  methods: {
    goToGame(gameId) {
      this.$router.push({ name: "portfolio", params: { id: gameId } });
      this.$router.go();
    },
    showAllGames() {
      this.showGames = !this.showGames;
    },
  },
};
</script>

<style scoped>
button {
  background: #fff;
  color: #fb8500;
  width: 20rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border: 2px solid;
  position: fixed;
}

button:hover {
  background: #ffb703;
}

.list-group {
  list-style: none;
  position: absolute;
  font-size: 1rem;
  width: 20rem;
  margin-top:3rem;
}

.list-group-item {
  background: #ffb703;
}

.list-group-item:hover {
  background: #fb8500;
  cursor: pointer;
}
</style>
