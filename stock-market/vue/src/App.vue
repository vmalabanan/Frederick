<template>
  <div id="app">
    <Header class="navbar navbar-expand-ig bd-navbar sticky-top" />
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
import Header from './components/Header.vue';
export default {
  components: {
    Header,
  }

}
</script>
