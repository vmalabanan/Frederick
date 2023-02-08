<template>
  <div class="game-over">
    <img src="../img/goldfish.gif" />
    <div class="game-over-leaderboard-container">
      <h1>Game Over!</h1>
      <div class="leaderboard-container"><leaderboard :gameId="gameId" /></div>
    </div>
  </div>
</template>

<script>
import Leaderboard from "../components/Leaderboard.vue";

export default {
  name: "GameOverScreen",
  props: ["gameId"],
  components: { Leaderboard }
};
</script>

<style scoped>
.game-over {
  height: 100vh;
  background-color: #f2dff3;
  display: flex;
  opacity: 0.9;
  gap: 3rem;
}

img {
  height: 75%;
  opacity: 0.9;
  margin-top: 1rem;
}

h1 {
  font-size: 6rem;
  color: #219ebc;
}

.game-over-leaderboard-container {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  margin-top: 4rem;
}
</style>
