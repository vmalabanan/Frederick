<template>
  <div class="options-container">
    <div class="options d-grid gap-3 col-4 mx-auto">
      <button class="btn btn-lg btn-primary" @click="createPage">
        Host Game
      </button>
      <button class="btn btn-lg btn-warning" @click="gameList">Game Invites</button>
      <button class="btn btn-lg btn-info" @click="portfolio">My Games</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "menu",
  methods: {
    createPage() {
      this.$router.push({ name: "createGame" });
    },
    gameList() {
      this.$router.push({ name: "invitationsList" });
    },
    portfolio() {
      this.$router.push({ name: "portfolio" }); // add id param here. should default to one of the player's games
    }
  }
};
</script>

<style>
.options-container {
  display: flex;
  justify-content: center;
  margin-top: 4rem;
}

.options {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
</style>
