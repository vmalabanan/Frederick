<template>
  <div class="portfolio">
    <div class="chart">
      <line-chart :styles="chartStyles" :plugins="chartPlugins"></line-chart>
    </div>
  </div>

</template>

<script>

import LineChart from "../components/LineChart.vue";

export default {
  name: "portfolio",
  components: { LineChart },
  data() {
    return {
    };
  },
  computed: {
    //change chart css here
    chartStyles() {
      return {
        backgroundColor: '#8ECAE6',
      }
    }
  }
};
</script>

<style scoped>
.portfolio {
  display: flex;
  justify-content: center;
}

.chart {
  padding-top: 20px;
  width: 50%;
}
</style>