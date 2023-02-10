<template>
  <div class="game-over">
    <div class="nav">
      <drop-down :gameName="gameName" class="dropdown"></drop-down>
      <hamburger class="hamburger" :routeNames="hamburgerLinks"></hamburger>
    </div>
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
        <leaderboard
          :gameId="gameId"
          :leaderboardData="this.leaderboardData"
          class="leaderboard"
        />
      </div>
    </div>
  </div>
</template>
<script>
import Leaderboard from "../components/Leaderboard.vue";
import Hamburger from "../components/Hamburger.vue";
import DropDown from "../components/DropDown.vue";
import gameService from "../services/GamesService";

export default {
  name: "GameOverScreen",
  props: ["leaderboardData"],
  components: { Leaderboard, Hamburger, DropDown },
  data() {
    return {
      gameId: this.$route.params.id,
      hamburgerLinks: ["menu", "home"],
      gameName: "",
    };
  },
  created() {
    this.getGameName();
  },
  methods: {
    handleClick() {
      this.$router.push({ name: "menu" });
    },
    getGameName() {
      const gameId = this.$route.params.id;
      gameService.getGameById(gameId).then((resp) => {
        this.gameName = resp.data.gameName;
      });
    },
  },
};
</script>

<style scoped>


.nav {
  position: fixed;
  top: 0;
  width: 100%;
  transition: top 0.7s;
  z-index: 100;
  display: flex;
  align-items: flex-start;
  justify-content: center;
}

.dropdown {
  margin-top: 0.5rem;
  margin-left: -24.5rem;
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
  margin-top: -6rem;
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
