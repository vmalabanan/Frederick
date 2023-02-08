<template>
  <div>
    <div class="dropdown">
      <a @click="toggleShowMenu()"><i class="fa-solid fa-bars"></i></a>
      <ul class="list-group" v-show="showMenu">
        <li @click="menu()" class="list-group-item">Game Menu</li>
        <li @click="home()" class="list-group-item">Home</li>
      </ul>
    </div>
  </div>
</template>

<script>
import gamesService from "../services/GamesService";

export default {
  name: "hamburger",
  props: ["gameName"],
  data() {
    return {
      myGames: [],
      showMenu: false,
    };
  },
  created() {
    gamesService.getAcceptedGames().then((response) => {
      this.myGames = response.data;
      console.log("this.myGames length: " + this.myGames.length);
    });
  },
  methods: {
    menu() {
      this.$router.push({ name: "menu" });
    },
    home() {
      this.$router.push({ name: "home" });
    },
    toggleShowMenu() {
      this.showMenu = !this.showMenu;
    },
  },
};
</script>

<style scoped>
a:hover {
  cursor: pointer;
}

.list-group {
  list-style: none;
  position: absolute;
  right: 0.2rem;
  font-size: 1rem;
  width: 20rem;
}

.list-group-item {
  background: #ffb703;
}

.list-group-item:hover {
  background: #fb8500;
  cursor: pointer;
}
</style>
