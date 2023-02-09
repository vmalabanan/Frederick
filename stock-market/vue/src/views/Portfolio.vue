<template>
  <div class="portfolio-screen-container">
    <GameOverScreen
      v-if="gameOver"
      :gameId="gameId"
      :leaderboardData="this.leaderboardData"
      class="game-over"
    />
    <div v-else class="portfolio-container">
      <div id="navbar">
        <drop-down :gameName="gameName" class="dropdown"></drop-down>
        <hamburger class="hamburger" :routeNames="hamburgerLinks"></hamburger>
      </div>
      <div class="portfolio-items-container">
        <div
          class="portfolio"
          @click.capture="switchView"
          :class="{ blurred: buySellCard.show }"
        >
          <game-account :accountValue="accountValue" />
          <line-chart
            :key="graphLabel"
            :styles="chartStyles"
            :dataPoints="getGraphDataPoints"
            :labels="getGraphXAxis"
            :graphLabel="this.graphLabel"
          />
          <leaderboard :gameId="gameId" :leaderboardData="leaderboardData" />
        </div>

        <div
          v-show="!onPortfolio"
          id="search"
          :class="{ blurred: buySellCard.show }"
          class="form-floating mb-3"
        >
          <input
            type="text"
            name="searchSymbol"
            @input="updateSearch"
            class="form-control"
            id="floatingInput"
            placeholder="GOOG"
            :value="search.input"
            autocomplete="off"
          />
          <label for="floatingInput">Search Stocks</label>
        </div>

        <div :class="{ blurred: buySellCard.show }">
          <stock-container
            @cardClick="updateGraphWith"
            v-model="buySellCard"
            :stocks="search.cards"
            class="stocks-search"
            v-show="!onPortfolio"
            :onPortfolio="false"
            :graphLabel="graphLabel"
          />
          <stock-container
            @cardClick="updateGraphWith"
            v-model="buySellCard"
            :stocks="this.$store.state.portfolio.cards"
            class="stocks-owned"
            v-show="onPortfolio"
            :onPortfolio="true"
            :graphLabel="graphLabel"
          />
        </div>

        <div :class="{ blurred: buySellCard.show }">
          <chat-container></chat-container>
        </div>

        <buy-stock v-show="buySellCard.show" v-model="buySellCard"></buy-stock>
      </div>
    </div>
  </div>
</template>

<script>
import ChatContainer from "../components/ChatContainer.vue";
import BuyStock from "../components/BuyStock.vue";
import Leaderboard from "../components/Leaderboard.vue";
import StockContainer from "../components/StockContainer.vue";
import GameAccount from "../components/GameAccount.vue";
import LineChart from "../components/LineChart.vue";
import GameOverScreen from "./GameOverScreen.vue";
import MarketDataService from "../services/MarketDataService";
import tradeService from "../services/TradeService";
import gameService from "../services/GamesService";
import SocketService from "../services/SocketService";
import Hamburger from "../components/Hamburger.vue";
import DropDown from "../components/DropDown.vue";

export default {
  name: "portfolio",
  components: {
    LineChart,
    GameAccount,
    StockContainer,
    Leaderboard,
    BuyStock,
    ChatContainer,
    GameOverScreen,
    Hamburger,
    DropDown,
  },
  data() {
    return {
      gameOver: false,
      gameId: this.$route.params.id,
      gameName: "",
      hamburgerLinks: ["menu", "home"],
      onPortfolio: true,
      stompClient: SocketService.startConnection(),
      connection: false,
      search: {
        input: "",
        symbols: [],
        cards: [],
      },
      graphData: [
        {
          dataPoints: [],
          time: [],
        },
        {
          portfolioDataPoints: [],
          portfolioTime: [],
          portfolioLabels: [],
        },
      ],
      buySellCard: {
        show: false,
        price: 0.0,
        symbol: "",
        buySell: false,
      },

      graphLabel: {},
      tradeSnapshots: [],
      leaderboardData: [],
      accountValue: 0,
    };
  },
  methods: {
    isGameOver() {
      gameService.isGameOver(this.gameId).then((resp) => {
        this.gameOver = resp.data;
        // this.gameOver = true; // for testing
      });
    },
    getGameName() {
      const gameId = this.$route.params.id;
      gameService.getGameById(gameId).then((resp) => {
        this.gameName = resp.data.gameName;
      });
    },

    createPortfolioGraph() {
      this.graphLabel = "My Portfolio";
      for (let i = 0; i < 100; i++) {
        this.graphData[1].portfolioDataPoints.push(100000);
        this.graphData[1].portfolioTime.push(new Date());
      }
    },
    updatePortfolioGraph(value) {
      this.graphData[1].portfolioDataPoints.push(value);
      this.graphData[1].portfolioTime.push(new Date());
    },
    updateGraphWith(symbol) {
      const graphData = this.graphData[0];
      this.graphLabel = symbol;
      if (graphData.dataPoints.length > 0) {
        graphData.dataPoints = [];
        graphData.time = [];
      }
      MarketDataService.getHistoricalMinuteDataBySymbol(symbol).then((resp) => {
        const data = resp.data;
        data.reverse().forEach((d) => {
          graphData.dataPoints.push(d.low);
          graphData.time.push(d.date);
        });
      });
    },
    showCard(state, stockInfo) {
      this.showBuySellCard = state;
      this.stockInfo = stockInfo;
    },
    switchView(event) {
      // Used innerText to be more specific of where the even it coming from
      const text = event.target.innerText;
      if (text == "Buy Stocks" || text == "View Portfolio") {
        this.onPortfolio = !this.onPortfolio;
        if (this.onPortfolio) {
          this.graphLabel = "My Portfolio";
          this.search.symbols = [];
          this.search.input = "";
        }
      }
    },
    updateSearch(event) {
      this.search.input = event.target.value;
      if (this.search.input == "") {
        return;
      }
      MarketDataService.searchTicker(this.search.input).then((resp) => {
        const data = resp.data;
        this.search.symbols = data.map((stock) => stock.symbol);
        MarketDataService.getRealTimeStockPrice(this.search.symbols).then(
          (resp) => {
            this.search.cards = resp.data;
          }
        );
      });
    },
    getSearchCards() {
      console.log("Updating Search cards");
      MarketDataService.getRealTimeStockPrice(this.search.symbols).then(
        (resp) => {
          const data = resp.data;
          this.search.cards = data.filter((stock) =>
            this.search.symbols.includes(stock.symbol)
          );
        }
      );
    },
    addRealTimeDataToGraph(graphData) {
      this.graphData[0].dataPoints.push(graphData.price);
      this.graphData[0].time.push(new Date());
    },
    getPortfolioCards(resp) {
      const data = JSON.parse(resp.body);
      const filteredData = data.filter((stock) => {
        if (this.$store.state.portfolio.symbols.includes(stock.symbol)) {
          const index = this.$store.state.portfolio.symbols.indexOf(
            stock.symbol
          );
          const sharesOwned =
            this.$store.state.portfolio.trades[index].numberOfShares;
          return sharesOwned > 0;
        }
        return false;
      });
      this.$store.commit("SET_PORTFOLIO_CARDS", filteredData);

      if (this.graphLabel != "My Portfolio") {
        const graphData = data.find(stock => stock.symbol == this.graphLabel)
        this.addRealTimeDataToGraph(graphData)
      }
    },
    getPlayersAccountWorth(resp) {
      const data = JSON.parse(resp.body);
      const playersValues = data.filter((d) => d.gameId == this.gameId)[0]
        .players;

      this.accountValue = playersValues[this.$store.state.user.username];
      this.updatePortfolioGraph(this.accountValue);

      let items = Object.keys(playersValues).map((key) => {
        return [key, playersValues[key]];
      });
      items.sort((first, second) => {
        return first[1] - second[1];
      });
      this.leaderboardData = items.reverse();
    },
  },
  created() {
    this.isGameOver();
    this.createPortfolioGraph();
    this.getGameName();

    this.stompClient.connect(
      {},
      () => {
        console.log("Connecting");
        this.stompClient.subscribe(`/topic/room-${this.gameId}/join`, () => {
          return;
        });

        this.stompClient.subscribe(`/topic/update`, (resp) =>
          this.getPortfolioCards(resp)
        );
        this.stompClient.subscribe(`/topic/room-${this.gameId}/join`, (resp) =>
          console.log(resp.body)
        );
        this.stompClient.subscribe(`/topic/leaderboard`, (resp) =>
          this.getPlayersAccountWorth(resp)
        );

        this.stompClient.send(
          `/app/room-${this.gameId}/join`,
          this.$store.state.user.username
        );
        this.connection = true;
      },
      () => {
        this.connection = false;
        console.log("error");
      }
    );

    tradeService.getPortfolio(this.gameId).then((resp) => {
      const symbols = resp.data.stocks.map((stock) => stock.tickerSymbol);
      this.$store.commit("SET_PORTFOLIO_SYMBOLS", symbols);
      const trades = resp.data.stocks;
      this.$store.commit("SET_PORTFOLIO_TRADES", trades);
    });

    if (this.$store.state.portfolio.symbols.length > 0) {
      MarketDataService.getRealTimeStockPrice(
        this.$store.state.portfolio.symbols
      ).then((resp) => {
        // this.portfolio.cards = resp.data;
        //testing
        const cardData = resp.data;
        this.$store.commit("SET_PORTFOLIO_CARDS", cardData);
      });
    }

    setInterval(() => {
      if (this.search.symbols.length > 0) {
        this.getSearchCards();
      }

      // if (this.graphLabel != "My Portfolio") {
      //   MarketDataService.getRealTimeStockPrice(this.graphLabel).then(
      //     (resp) => {
      //       const data = resp.data[0];
      //       this.graphData[0].dataPoints.push(data.price);
      //       this.graphData[0].time.push(new Date());
      //     }
      //   );
      // }
    }, 6 * 1000);
  },
  computed: {
    chartStyles() {
      return {
        //change chart css here
        backgroundColor: "#fff",
        // borderRadius: "20px",
      };
    },
    getGraphDataPoints() {
      if (this.graphLabel == "My Portfolio") {
        return this.graphData[1].portfolioDataPoints;
      } else {
        return this.graphData[0].dataPoints;
      }
    },
    getGraphXAxis() {
      if (this.graphLabel == "My Portfolio") {
        return this.graphData[1].portfolioTime;
      } else {
        return this.graphData[0].time;
      }
    },
  },
};
</script>

<style scoped>
.portfolio-screen-container {
	height: 100%;
/* margin-top: -3rem; */
  background: linear-gradient(
    to right,
    #ff4e50,
    #f9d423
  ); 
}
#navbar {
  position: fixed;
  top: 0;
  width: 100%;
  transition: top 0.7s;
  z-index: 100;
  display: flex;
  align-items: flex-start;
  justify-content: center;
}

.dropdown {
  margin-top: 0.5rem;
}

.portfolio-items-container {
  padding: 4rem 20px 0px 20px;
  margin-bottom: 10px;
}

.portfolio {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.blurred {
  background-color: #ccc;
  filter: blur(20px);
  opacity: 60%;
}

div#search {
  border: 5px solid #023047;
  border-radius: 10px;
  width: 90%;
  margin-left: auto;
  margin-right: auto;
}

.game-over {
  position: fixed;
  width: 100%;
  height: 100%;
  z-index: 10;
}
</style>
