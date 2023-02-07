<template>
  <div id="game">
    <div class="alert alert-danger" role="alert" v-if="respondToInvitationErrors">
      {{ respondToInvitationErrorMsg }}
    </div>
    <span id="name" style="width: 200px">{{ game.gameName }}</span>
    <span id="type" style="width: 60px">Normal</span>
    <span id="length" style="width: 60px">{{ game.gameLengthDays }} days</span>
    <div id="button-container">
      <button id="view-game" @click="viewGame">View Game</button>
    </div>
  </div>
</template>

<script>

export default {
  name: "GameInvite",
  props: ["game"],
  methods: {
    viewGame() {
      this.$router.push({
        name: "portfolio", params: { id: this.game.gameId }
      });
    }
  }
};
</script>

<style>
div#game {
  display: flex;
  background-color: #023047;
  border-radius: 15px;
  height: 60px;
  color: white;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  margin: 10px;
}

div#button-container>button#view-game {
  background-color: #fb8500;
  border-radius: 15px;
  border: none;
  width: 100px;
  height: 50px;
  margin-right: 20px;
}
</style>
