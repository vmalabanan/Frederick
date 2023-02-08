<template>
  <div id="leaderboard">
    <ul class="list-group list-group-horizontal">
      <li class="list-group-item flex-fill leaderboard-title">Leaderboard</li>
    </ul>
    <ul class="list-group list-group-horizontal">
      <li class="list-group-item col-1">#</li>
      <li class="list-group-item col-2">Player</li>
      <li class="list-group-item col-3">Portfolio Value</li>
    </ul>
    <ul
      class="list-group list-group-horizontal"
      v-for="(value, key, index) in rankedPortfolios"
      :key="key"
    >
      <li class="list-group-item col-1 data">
        {{ index + 1 }}
      </li>
      <li class="list-group-item col-2 data">
        {{ key }}
      </li>

      <li class="list-group-item col-3 data">
        ${{ value.toLocaleString("en-US") }}
      </li>
    </ul>
  </div>
</template>

<script>
import tradeService from "../services/TradeService";
import marketDataService from "../services/MarketDataService";

export default {
  name: "Leaderboard",
  props: ["gameId"],
  data() {
    return {
      portfolioValues: {},
      rankedPortfolios: {},
      polling: null,
    };
  },
  created() {
    // build leaderboard immediately on creation
    this.buildLeaderboard();
    // also start polling for leaderboard data
    this.buildLeaderboardWithPolling();
  },
  beforeDestroy() {
    clearInterval(this.polling);
  },
  methods: {
    buildLeaderboard() {
      tradeService.getPortfolioAllPlayers(this.gameId).then((resp) => {
        const playerPortfoliosArr = resp.data;

        // set to store the holdings of all players
        const stocks = new Set();

        // object to store stock symbols (key) and the current price (value)
        const stocksCurrentPrice = {};

        // object to store each player's stock holdings
        const stockHoldings = {};

        // iterate through playerPortfoliosArr
        playerPortfoliosArr.forEach((playerPortfolio) => {
          // store each player's cash in portfolioValues
          this.portfolioValues[playerPortfolio.username] =
            playerPortfolio.portfolio.cash;

          stockHoldings[playerPortfolio.username] = {};

          // iterate through player's stocks
          playerPortfolio.portfolio.stocks.forEach((stock) => {
            // add each unique tickerSymbol to stocks
            stocks.add(stock.tickerSymbol);
            // add tickerSymbol and numberOfShares to stockHoldings
            stockHoldings[playerPortfolio.username][stock.tickerSymbol] =
              stock.numberOfShares;
          });
        });

        // make a call to the API to get current price for each stock
        marketDataService.getRealTimeStockPrice(...stocks).then((resp) => {
          const realTimeStockPriceArr = resp.data;

          realTimeStockPriceArr.forEach((realTimeStockPrice) => {
            stocksCurrentPrice[realTimeStockPrice.symbol] =
              realTimeStockPrice.price;
          });

          // calculate portfolio value for each player
          // iterate through all players
          const players = Object.keys(stockHoldings);
          players.forEach((player) => {
            // iterate through player's stocks
            const stocks = Object.keys(stockHoldings[player]);

            stocks.forEach((stock) => {
              // calculate stock values
              const currentPrice = stocksCurrentPrice[stock];
              const numberOfShares = stockHoldings[player][stock];

              // add result to portfolio value
              this.portfolioValues[player] += currentPrice * numberOfShares;
            });
          });

          // sort portfolios in descending order
          this.rankedPortfolios = Object.fromEntries(
            Object.entries(this.portfolioValues).sort(([, a], [, b]) => b - a)
          );
        });
      });
    },
    buildLeaderboardWithPolling() {
      this.polling = setInterval(() => {
        this.buildLeaderboard();
      }, 6000);
    },
  },
};
</script>

<style scoped>
#leaderboard {
  flex-basis: 20%;
  background-color: #ffb703;
  border-radius: 20px;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}

li {
  background: none;
}

ul:nth-child(even) {
  background: #fb8500;
}

.list-group-item {
  font-size: 1.15rem;
}

.leaderboard-title {
  text-align: center;
  font-size: 2rem;
  background-color: #fb8500;
}

.col-1 {
  width: 10%;
}

.col-2 {
  width: 46%;
}

.col-3 {
  width: 44%;
}

</style>
