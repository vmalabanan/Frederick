<template>
  <div id="GameList">
    <p class="no-invites-msg" v-if="$store.state.invitedGames.length === 0">No invitations to display</p>  
    <div class="card w-50" v-else>
      <div 
        class="card-header"
        style="background-color: #FFB703; border-radius:25px; height: 60px"
      >
        <span style="width: 140px">Game Name</span>
        <span style="width: 40px">Type</span>
        <span style="width: 130px">Length</span>
        <span style="width: 100px"></span>
      </div>
      <div id="games" class="card-body">
        <Game
          v-for="(game, index) in $store.state.invitedGames"
          :key="index"
          :game="game"
        />
      </div>
      <div
        class="card-footer"
        style="background-color: #FFB703; border-radius:25px; height: 60px"
      />
    </div>
    <button class="btn btn-lg btn-info" id="back" @click="$router.go(-1)">
      Back
    </button>
  </div>
</template>
<script>
import Game from "../components/Game.vue";
export default {
  name: "InvitationsList",
  components: {
    Game
  }
};
</script>

<style>
div#GameList {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 5%;
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
    margin-top: 20px;
}
</style>
