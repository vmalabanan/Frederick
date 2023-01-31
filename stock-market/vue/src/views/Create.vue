<!-- eslint-disable vue/script-setup-uses-vars -->
<template>
  <div>
    <h1>This is the Create Game page!</h1>
    <form class="form-create-game" @submit.prevent="createGame"></form>
  </div>
</template>

<script>
export default {
  name: "create",
  methods: {
    createGame() {
      this.$router.push({ name: "portfolio" }); // add portfolio id here and to router list

      // I also need to push info from here to the backend so it gets stored to the db

    }
  }
};
</script>

<style>

</style>
