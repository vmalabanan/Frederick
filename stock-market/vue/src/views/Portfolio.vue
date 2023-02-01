<template>
  <div class="portfolio-container">
    <div class="portfolio">
      <game-account></game-account>
      <line-chart :styles="chartStyles" :plugins="chartPlugins"></line-chart>
      <leaderboard></leaderboard>
    </div>
    <stock-container></stock-container>
  </div>
</template>

<script>
import Leaderboard from "../components/Leaderboard.vue"
import StockContainer from "../components/StockContainer.vue";
import GameAccount from "../components/GameAccount.vue";
import LineChart from "../components/LineChart.vue";

export default {
  name: "portfolio",
  components: { LineChart, GameAccount, StockContainer, Leaderboard },
  data() {
    return {};
  },
  computed: {
    //change chart css here
    chartStyles() {
      return {
        backgroundColor: "#8ECAE6",
        borderRadius: "20px",
      };
    },
  },
};
</script>

<style scoped>
.portfolio-container {
  padding: 20px 20px 0px 20px;
  margin-bottom: 10px;
}

.portfolio {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

</style>