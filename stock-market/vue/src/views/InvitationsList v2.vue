<template>
  <div id="gamelist">
    <hamburger class="hamburger" :routeNames="hamburgerLinks"></hamburger>
    <p class="no-invites-msg" v-if="$store.state.acceptedGames.length === 0">
      No games to display
    </p>
    <div v-else class="gamelist-content">
      <div class="fish-container">
        <img
          class="fish-2"
          src="../img/goldfish-in-place.gif"
          alt="goldfish gif"
        />

        <img
          class="fish"
          src="../img/goldfish-in-place.gif"
          alt="goldfish gif"
        />
        <img
          class="fish-3"
          src="../img/goldfish-in-place.gif"
          alt="goldfish gif"
        />
      </div>

      <div class="all-button-container">
        <div class="gamelist-container">
          <button
            class="btn btn-lg btn-primary"
            v-for="(game, index) in $store.state.invitedGames"
            :key="index"
            :game="game"
            @click="viewGame(game.gameId)"
          >
            {{ game.gameName }}
          </button>
        </div>
        <button class="btn btn-lg btn-info" id="back" @click="$router.go(-1)">
          Back
        </button>
      </div>
    </div>
  </div>
</template>
<script>
import Hamburger from "../components/Hamburger.vue";

export default {
  name: "InvitationsList",
  components: {
    Hamburger,
  },
  data() {
    return {
      hamburgerLinks: ["home"],
    };
  },
  methods: {
    viewGame(gameId) {
      this.$router.push({
        name: "portfolio",
        params: { id: gameId },
      });
    },
  },
};
</script>

<style scoped>
#gamelist {
  background-color: #ffe9e1;
  height: 100vh;
}
.no-invites-msg {
  font-size: 2rem;
  text-align: center;
}
.gamelist-content {
  display: flex;
  /* flex-direction: column; */
  align-items: center;
  justify-content: center;
  gap: 2rem;
  margin-left: -10rem;
}
.fish-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-right: 2rem;
  margin-top: 4rem;
}

.fish {
  transform: rotate(7deg);
  position: relative;
  animation-name: float;
  animation-duration: 2s;
  animation-iteration-count: infinite;
  animation-timing-function: ease-in-out;
}

.fish-2,
.fish-3 {
  width: 30%;
  transform: rotate(5deg);
  margin-top: 3rem;
  margin-right: 5rem;
}
.gamelist-container {
  /* background-color: #ffb703; */
  /* width: 20%; */
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.all-button-container {
  /* margin-top: 5rem; */
  z-index: 10;
  display: flex;
  flex-direction: column;
  gap: 2rem;
  margin-top: 5rem;
}

button {
  border: none;
  box-shadow: 0.5rem 0.5rem #ffb703;
  color: #fff;
}

.btn-primary {
  background-color: #477fc9;
}

.btn-primary:hover {
  background-color: #1566d1;
  color: #000;
}

.btn-info {
  background-color: #8ecae6;
}

.btn-info:hover {
  background-color: #65bce4;
}

@keyframes float {
  0% {
    top: 0px;
  }

  25% {
    top: 20px;
  }

  50% {
    top: 30px;
  }

  75% {
    top: 20px;
  }

  100% {
    top: 0px;
  }
}
</style>
