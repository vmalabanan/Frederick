<template>
     <header
      class="navbar navbar-expand-ig bd-navbar"
      id="navbar"
      v-if="this.$route.name !== 'registration' && this.$route.name !== 'home'"
    >
      <!-- <img src="./img/frederick_logo.svg" alt="Frederick the goldfish!" /> -->
      <!-- <img src="./img/frederick-small.png" alt="Frederick the goldfish!" /> -->
      <img src="../img/frederick-med.png" alt="Frederick the goldfish!" />

      <drop-down
        v-if="this.$route.name == 'portfolio'"
        :gameName="header.portfolio"
        class="dropdown"
      ></drop-down>
      <!-- <active-games v-if="this.$route.name == 'portfolio'"></active-games> -->
      <!-- <span v-if="this.$route.name !== 'portfolio'">{{
        this.header[this.$route.name]
      }}</span> -->
      <!-- <div id="nav" v-if="$store.state.token != ''"> -->
      <!-- <router-link tag="span" v-bind:to="{ name: 'menu' }" v-if="this.$route.name == 'portfolio'">Game Menu</router-link>
        <router-link tag="span" v-bind:to="{ name: 'home' }">Logout</router-link> -->
      <hamburger
        class="hamburger"
        v-if="this.$route.name !== 'registration' && this.$route.name !== 'home'"
      ></hamburger>
      <!-- </div> -->
      <!-- <img
        style="transform: scaleX(-1)"
        src="./img/frederick_logo.svg"
        alt="Frederick the goldfish!"
      /> -->
    </header>
</template>

<script>
export default {
    name: "navbar"
}
</script>

<style>
#navbar {
  background-color: #8ECAE6;
  color: #fb8500;
  font-size: 2.5rem;
  font-weight: bold;
  height: 4.3rem;
  box-shadow: 0 10px 25px 0 rgba(0, 0, 0, 0.3);
  transition: top 0.6s;
  position: absolute;
  top: 0;
  width: 100%;
  z-index: 150;
}

#nav > span {
  cursor: pointer;
}

img {
  margin: -1.05rem 0 0 1rem;
}

.dropdown {
  margin-top: -0.95rem;
}
.hamburger {
  margin-right: 2rem;
  margin-top: -0.75rem;
}
</style>