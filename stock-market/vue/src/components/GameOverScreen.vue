<template>
  <div class="game-over">
    <div class="fishbowl-game-over-container">
      <img
        class="game-over-bubble"
        src="../img/gameover-no-bg.png"
        alt="game over graphic"
      />
      <img
        class="two-goldfish"
        src="../img/two-goldfish.gif"
        alt="two goldfish swimming in a bowl"
      />
    </div>
    <div class="leaderboard-button-container">
      <div class="leaderboard-container">
        <leaderboard :gameId="gameId" class="leaderboard" />
      </div>
      <button class="btn btn-primary" @click="handleClick">Menu</button>
    </div>
  </div>
</template>

<script>
import Leaderboard from "../components/Leaderboard.vue";

export default {
  name: "GameOverScreen",
  props: ["gameId"],
  components: { Leaderboard },
  methods: {
    handleClick() {
      this.$router.push({ name: "menu" });
    },
  },
};
</script>

<style scoped>
.game-over {
  height: 100vh;
  background-color: #ffe9e1;
  display: flex;
  align-items: center;
  gap: 3rem;
  position: fixed;
  top: 0;
}

.fishbowl-game-over-container {
  display: flex;
  flex-direction: column;
  width: 65%;
  align-items: center;
}

.game-over-bubble {
  width: 40%;
  opacity: 0.85;
  position: relative;
  animation-name: float;
  animation-duration: 2s;
  animation-iteration-count: infinite;
  animation-timing-function: ease-in-out;
  transform: rotate(8deg);

  margin-left: 20rem;
}

.two-goldfish {
  width: 65%;
  margin-top: -5rem;
}

.leaderboard-container {
  margin-top: 7rem;
}

.leaderboard {
  width: 150%;
}

.leaderboard-button-container {
  display: flex;
  flex-direction: column;
  gap: 3rem;
  margin-left: -20rem;
  /* align-items: center; */
}

.btn {
  /* margin-top: -12rem; */
  width: 15rem;
  font-size: 1.25rem;
  width: 150%;
}

@keyframes float {
  0% {
    top: 0px;
  }
  25% {
    top: 10px;
  }
  50% {
    top: 20px;
  }
  75% {
    top: 10px;
  }
  100% {
    top: 0px;
  }
}
</style>
