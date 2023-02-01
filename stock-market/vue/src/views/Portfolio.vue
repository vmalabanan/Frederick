<template>
  <div class="portfolio">
    <game-account></game-account>
    <line-chart :styles="chartStyles" :plugins="chartPlugins"></line-chart>
  </div>

</template>

<script>
import GameAccount from "../components/GameAccount.vue"
import LineChart from "../components/LineChart.vue";

export default {
  name: "portfolio",
  components: { LineChart, GameAccount },
  data() {
    return {
    };
  },
  computed: {
    //change chart css here
    chartStyles() {
      return {
        backgroundColor: '#8ECAE6',
        flexBasis: '50%'
      }
    }
  }
};
</script>

<style scoped>
.portfolio {
  display: flex;
  gap: 20px;
  padding-top: 20px;
  padding-left: 40px;
}


</style>