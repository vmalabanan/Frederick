<template>
  <div id="app">
    <header
      class="navbar navbar-expand-ig bd-navbar sticky-top"
      v-if="this.$route.name !== 'gameOver'"
    >
      <!-- <img src="./img/frederick_logo.svg" alt="Frederick the goldfish!" /> -->
      <!-- <img src="./img/frederick-small.png" alt="Frederick the goldfish!" /> -->
      <img src="./img/frederick-med.png" alt="Frederick the goldfish!" />

      <drop-down
        v-if="this.$route.name == 'portfolio'"
        :gameName="header.portfolio"
        class="dropdown"
      ></drop-down>
      <!-- <active-games v-if="this.$route.name == 'portfolio'"></active-games> -->
      <span v-if="this.$route.name !== 'portfolio'">{{
        this.header[this.$route.name]
      }}</span>
      <!-- <div id="nav" v-if="$store.state.token != ''"> -->
      <!-- <router-link tag="span" v-bind:to="{ name: 'menu' }" v-if="this.$route.name == 'portfolio'">Game Menu</router-link>
        <router-link tag="span" v-bind:to="{ name: 'home' }">Logout</router-link> -->
      <hamburger class="hamburger"></hamburger>
      <!-- </div> -->
      <!-- <img
        style="transform: scaleX(-1)"
        src="./img/frederick_logo.svg"
        alt="Frederick the goldfish!"
      /> -->
    </header>

    <!-- <div id="nav">
	<router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
	<router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
	</div> -->
    <router-view />
    <footer
      class="fixed-bottom px-4 py-4"
      style="background-color: #ffb703"
      v-if="this.$route.name !== 'gameOver'"
    />
  </div>
</template>

<script
  src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
  integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
  crossorigin="anonymous"
></script>
<script>
import gamesService from "./services/GamesService";
import ActiveGames from "./components/ActiveGames.vue";
import DropDown from "./components/DropDown.vue";
import Hamburger from "./components/Hamburger.vue";

export default {
  name: "App",
  data() {
    return {
      header: {
        home: "Welcome",
        createGame: "Create a Game",
        invitationsList: "Join a Game",
        gamesList: "Join a Game",
        menu: "Game Menu",
        portfolio: "",
      },
    };
  },
  watch: {
    $route(to, from) {
      this.getGameName();
    },
  },
  created() {
    this.getGameName();
  },
  methods: {
    getGameName() {
      if (this.$route.name == "portfolio") {
        const gameId = this.$route.params.id;
        gamesService.getGameById(gameId).then((resp) => {
          this.header.portfolio = resp.data.gameName;
        });
      }
    },
  },
  components: { ActiveGames, DropDown, Hamburger },
};
</script>

<style scoped>
header {
  background-color: #023047;
  color: #fb8500;
  font-size: 2.5rem;
  font-weight: bold;
  height: 4.3rem;
  box-shadow: 0 10px 25px 0 rgba(0, 0, 0, 0.3);
  transition: top 0.3s;
}

#nav > span {
  cursor: pointer;
}

img {
  margin: -1.05rem 0 0 1rem;
}

.dropdown {
  margin-top: -0.95rem;
}
.hamburger {
  margin-right: 2rem;
  margin-top: -0.75rem;
}
</style>
