<template>
  <div id="app">
    <header class="navbar navbar-expand-ig bd-navbar sticky-top">
      <img src="./img/frederick_logo.svg" alt="Frederick the goldfish!" />
      <span>{{ this.header[this.$route.name] }}</span>
      <img style="transform: scaleX(-1);" src="./img/frederick_logo.svg" alt="Frederick the goldfish!" />
    </header>


    <!-- <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    </div> -->
    <router-view />
    <footer class="fixed-bottom px-4 py-4" style="background-color: #FFB703;" />
  </div>
</template>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
<script>
export default {
  name: 'App',
  data() {
    return {
      header: {
        home: 'Welcome'
      },
    }
  },
}
</script>

<style >
header {
  background-color: #023047;
  color: #FB8500;
  font-size: 60px;
  font-weight: bold;
}
</style>
