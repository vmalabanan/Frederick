<template>
  <div class="game-over">
    <hamburger class="hamburger"></hamburger>
    <div class="game-over-content">
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
      <div class="leaderboard-container">
        <leaderboard :gameId="gameId" class="leaderboard" />
      </div>
    </div>
  </div>
</template>

<script>
import Leaderboard from "../components/Leaderboard.vue";
import Hamburger from "./Hamburger.vue";

export default {
  name: "GameOverScreen",
  props: ["gameId"],
  components: { Leaderboard, Hamburger },
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
  width: 100vw;
  position: fixed;
  top: 0;
  font-size: 2.5rem;
}

.hamburger {
  position: fixed;
  right: 2rem;
  margin-top: 0.75rem;

}

.game-over-content {
  height: 100%;
  background-color: #ffe9e1;
  display: flex;
  align-items: center;
  gap: 3rem;
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
  margin-left: -20rem;
}

.leaderboard {
  width: 150%;
  box-shadow: 1rem 1rem #69bdee;
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
