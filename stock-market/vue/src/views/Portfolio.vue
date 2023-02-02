<template>
  <div class="portfolio-container">
    <div class="portfolio" @click.capture="switchView">
      <game-account />
      <line-chart :styles="chartStyles"></line-chart>
      <leaderboard />
    </div>
    <stock-container :stocks="stocks" class="stocks-search" v-show="!onPortfolio" />
    <stock-container class="stocks-owned" v-show="onPortfolio" />
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
  methods: {
    switchView(event) {
      // Used innerText to be more specific of where the even it coming from
      const text = event.target.innerText;
      if (text == "View Stocks" || text == "View Portfolio") {
        this.onPortfolio = !this.onPortfolio;
      }
    }
  },
  data() {
    return {
      onPortfolio: false,
      stocks: [
        {
          code: 'AAPL',
          name: 'Apple Inc',
          previousClose: 140,
          mp: 145.93,
          change: '1.00%',
        },
        {
          code: 'AAPL',
          name: 'Apple Inc',
          previousClose: 140,
          mp: 145.93,
          change: '1.00%',
        },
        {
          code: 'AAPL',
          name: 'Apple Inc',
          previousClose: 140,
          mp: 145.93,
          change: '1.00%',
        },
        {
          code: 'AAPL',
          name: 'Apple Inc',
          previousClose: 140,
          mp: 145.93,
          change: '1.00%',
        },
        {
          code: 'AAPL',
          name: 'Apple Inc',
          previousClose: 140,
          mp: 145.93,
          change: '1.00%',
        },
        {
          code: 'AAPL',
          name: 'Apple Inc',
          previousClose: 140,
          mp: 145.93,
          change: '1.00%',
        },
        {
          code: 'AAPL',
          name: 'Apple Inc',
          previousClose: 140,
          mp: 145.93,
          change: '1.00%',
        },
        {
          code: 'AAPL',
          name: 'Apple Inc',
          previousClose: 140,
          mp: 145.93,
          change: '1.00%',
        }
      ]
    };
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