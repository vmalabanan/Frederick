<template>
  <div id="app">
    <Header class="navbar navbar-expand-ig bd-navbar sticky-top" />
    <!-- <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    </div> -->
    <router-view />
    <footer class="sticky-bottom" />
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

<style>
footer {
  position: absolute;
  bottom: 0;
  width: 100vw;
  background-color: #FFB703;
  padding: 30px
}

button {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  background: #FFB703;
  border: none;
  border-radius: 15px;
  color: white
}
</style>
