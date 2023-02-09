<template>
  <div class="home">
    <div class="images-container">
      <img class="apple" src="../img/apple.png" alt="Apple logo" />
      <img class="fishbowl" src="../img/cutefish-fish.gif" alt="goldfish" />
      <img class="netflix" src="../img/netflix.png" alt="Netflix logo" />
    </div>
    <button class="btn btn-warning" @click="handleClick">Let's Go</button>
  </div>
</template>

<script>
export default {
  name: "home",
  methods: {
    handleClick() {
      this.$router.push({ name: "registration" });
    },
  },
  created() {
    this.$store.commit("LOGOUT");
  },
};
</script>

<style scoped>
.home {
  background-color: #04c2fc;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.images-container {
  display: flex;
  align-items: center;
}

.fishbowl {
  height: 70%;
}

.apple {
  height: 30%;
  margin-right: -7rem;
  z-index: 10;
}

.netflix {
  height: 30%;
  margin-left: -13rem;
}

.btn {
  margin-top: -12rem;
  width: 15rem;
  font-size: 1.25rem;
  box-shadow: 0.5rem 0.5rem #fc7c31;
}
</style>
