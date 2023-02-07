<template>
  <div id="leaderboard">hello!</div>
</template>

import tradeService from "../services/TradeService"

<script>
export default {
  name: "Leaderboard",
  data() {
    return {};
  },
  created() {

  },
  methods: {},
};
</script>

<style scoped>
    #leaderboard {
        flex-basis: 20%;
        background-color: #246e93;
        border-radius: 20px;
        box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
    }
</style>