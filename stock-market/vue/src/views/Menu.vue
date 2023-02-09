<template>
  <div class="menu">
    <hamburger class="hamburger" :routeNames="hamburgerLinks"></hamburger>
    <div class="image-and-button-container">
      <div class="images-container">
        <img class="dollar dollar-1" src="../img/dollar.png" alt="dollar" />
        <div class="fishbowl-and-dollar-container">
          <img class="dollar dollar-2" src="../img/dollar.png" alt="dollar" />
          <img
            class="fishbowl"
            src="../img/goldfish.gif"
            alt="goldfish in bowl gif"
          />
        </div>
        <img class="dollar dollar-3" src="../img/dollar.png" alt="dollar" />
      </div>
      <div class="button-container">
        <button class="btn btn-lg btn-primary" @click="createPage">
          Host Game
        </button>
        <button
          class="btn btn-lg btn-warning"
          v-if="$store.state.invitedGames.length > 0"
          @click="invitationsList"
        >
          Game Invites
        </button>
        <button
          class="btn btn-lg btn-info"
          v-if="$store.state.acceptedGames.length > 0"
          @click="gamesList"
        >
          My Games
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import gamesService from "../services/GamesService.js";
import hamburger from "../components/Hamburger.vue";

export default {
  name: "menu",
  components: {
    hamburger,
  },
  data() {
    return {
      hamburgerLinks: ["home"]
    }
  },
  methods: {
    createPage() {
      this.$router.push({ name: "createGame" });
    },
    invitationsList() {
      this.$router.push({ name: "invitationsList" });
    },
    gamesList() {
      this.$router.push({ name: "gamesList" });
    },
  },
  created() {
    // get list of game invitations and add to store
    gamesService.getInvitedGames().then((resp) => {
      this.$store.commit("SET_INVITED_GAMES", resp.data);
    });

    // get list of all accepted games and add to store
    gamesService.getAcceptedGames().then((resp) => {
      this.$store.commit("SET_ACCEPTED_GAMES", resp.data);
    });
  },
};
</script>

<style scoped>
.menu {
  height: 100vh;
  font-size: 2.5rem;
  background-color: #f2dff3;
}

.image-and-button-container {
  display: flex;
  align-items: center;
  justify-content: center;
}

.images-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 5rem;
  margin-left: -24rem;
}

.fishbowl-and-dollar-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.fishbowl {
  transform: rotate(-2deg);
  opacity: 0.8;
  height: 55vh;
}

.dollar {
  transform: rotate(8deg);
  width: 11%;
  z-index: 10;
  position: relative;
  animation-name: float;
  animation-duration: 2s;
  animation-iteration-count: infinite;
  animation-timing-function: ease-in-out;
}

.dollar-1 {
  margin-right: -10rem;
  margin-bottom: 7rem;
}

.dollar-2 {
  transform: rotate(16deg);
  width: 20%;
  margin-top: 2rem;
}

.dollar-3 {
  margin-left: -11rem;
  margin-bottom: 12rem;
}

.button-container {
  width: 10%;
  display: flex;
  flex-direction: column;
  gap: 2rem;
  z-index: 10;
  margin-top: 14rem;
  margin-left: -23rem;
  /* margin-top: 2rem; */
  /* margin-left: -16rem; */
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

.btn-warning {
  background-color: #fc7c31;
}

.btn-warning:hover {
  background-color: #fc650e;
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
