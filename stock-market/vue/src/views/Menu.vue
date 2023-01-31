<template>
  <div class="options-container">
    <div class="options">
      <router-link :to="{ name: 'create' }">
        <button class="btn btn-lg btn-primary btn-block">Host Game</button>
      </router-link>
      <router-link :to="{ name: 'create' }">
        <button class="btn btn-lg btn-primary btn-block">Join Game</button>
      </router-link>
      <router-link :to="{ name: 'portfolio' }">
        <button class="btn btn-lg btn-primary btn-block">My Games</button>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "menu"
};
</script>

<style>

.options-container {
  display: flex;
  justify-content: center;
  margin-top: 2rem;

}

.options {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  /* justify-content: center; */
}
</style>
