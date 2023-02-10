<template>
  <div id="gamelist">
    <hamburger class="hamburger" :routeNames="hamburgerLinks"></hamburger>
    <!-- <p class="no-invites-msg" v-if="$store.state.acceptedGames.length === 0">
      No games to display
    </p> -->
    <div class="gamelist-content">
      <div class="airplane-container">
        <img src="../img/airplane.gif" alt="paper airplane gif " />
      </div>

      <div class="all-button-container">
        <div class="gamelist-container">
          <game-invite v-for="(game, index) in $store.state.invitedGames" :key="index" :game="game"></game-invite>
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
import GameInvite from "../components/GameInvite.vue";
import SocketService from '../services/SocketService';
import gamesService from "../services/GamesService.js";

export default {
  name: "InvitationsList",
  components: {
    Hamburger,
    GameInvite,
  },
  data() {
    return {
      connection: false,
      stompClient: SocketService.startConnection(),
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
    handleInvite(resp) {
      console.log(resp.body)
      if (resp.body == this.$store.state.user.username) {
        gamesService.getInvitedGames().then((resp) => {
          this.$store.commit("SET_INVITED_GAMES", resp.data);
        });
      }
    },
  },
  created() {
    this.stompClient.connect({},
      () => {
        console.log("Connecting")
        this.stompClient.subscribe("/topic/invite", resp => this.handleInvite(resp))
      },
      () => {
        console.log("error")
      }
    )

  }
};
</script>

<style scoped>
#gamelist {
  background-color: #ffcccc;
  height: 100vh;
}

.no-invites-msg {
  font-size: 2rem;
  text-align: center;
}

.gamelist-content {
  height: 90vh;
  display: flex;
  /* flex-direction: column; */
  align-items: center;
  justify-content: center;
  gap: 2rem;
  margin-left: -10rem;
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
  box-shadow: 0.5rem 0.5rem #fc7c31;
  color: #fff;
}


.btn-info {
  background-color: #04c2fc;
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
