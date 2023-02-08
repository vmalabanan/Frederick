<template>
  <div>
    <img src="../img/goldfish.gif" />
    <h1>Game Over!</h1>
    <leaderboard :gameId="555" />
    <button class="btn btn-lg btn-primary" @click="menu">
      Main Menu
    </button>
  </div>
</template>

<script>
import Leaderboard from "../components/Leaderboard.vue";

export default {
  name: "GameOverScreen",
  components: { Leaderboard },
  methods: {
    menu() {
      this.$router.push({ name: "menu" });
    }
  }
};
</script>

<style scoped>
div {
  height: 100vh;
  background-color: #f2dff3;
  display: flex;
  align-items: center;
  opacity: 0.75;
}

img {
  height: 75%;
  opacity: 0.75;
}

h1 {
  font-size: 6rem;
  color: #219ebc;
}
</style>
