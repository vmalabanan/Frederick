<template>
  <div id="GameList">
    <hamburger class="hamburger" :routeNames="hamburgerLinks"></hamburger>

    <p class="no-invites-msg" v-if="$store.state.acceptedGames.length === 0">
      No games to display
    </p>
    <div class="card w-50" v-else>
      <div
        class="card-header"
        style="background-color: #ffb703; border-radius: 25px; height: 60px"
      >
        <span style="width: 330px">Game Name</span>
        <span style="width: 170px">Type</span>
        <span style="width: 200px">Length</span>
        <span style="width: 100px"></span>
      </div>
      <div id="games" class="card-body">
        <Game
          v-for="(game, index) in $store.state.acceptedGames"
          :key="index"
          :game="game"
        />
      </div>
      <div
        class="card-footer"
        style="background-color: #ffb703; border-radius: 25px; height: 60px"
      />
    </div>
    <button class="btn btn-lg btn-info" id="back" @click="$router.go(-1)">
      Back
    </button>
  </div>
</template>
<script>
import Game from "../components/Game.vue";
import Hamburger from "../components/Hamburger.vue";

export default {
  name: "GamesList",
  components: {
    Game,
    Hamburger,
  },
  data() {
    return {
      hamburgerLinks: ["home"],
    };
  },
};
</script>

<style scoped>
#GameList {
	
}
div#GameList {
  display: flex;
  flex-direction: column;
  align-items: center;
  /* margin-top: 5%; */
}

div#GameList > div {
  background-color: #8ecae6;
  border-radius: 25px;
  margin: 0 auto;
  border: none;
}

div.card-header {
  display: flex;
  justify-content: space-around;
  align-items: center;
}

div#GameList > #back {
  /* margin-top: 20px; */
}
</style>
