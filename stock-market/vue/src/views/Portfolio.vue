<template>
	<div class="portfolio-container">
		<div class="portfolio" @click.capture="switchView" :class="{ blurred: buySellCard.show }">
			<game-account />
			<line-chart :key="graphLabel" :styles="chartStyles" :dataPoints="graphData.dataPoints" :labels="graphData.time"
				:graphLabel="graphLabel" />
			<leaderboard :gameId="gameId" />
		</div>

		<div v-show="!onPortfolio" id="search" :class="{ blurred: buySellCard.show }" class="form-floating mb-3">
			<input type="text" name="searchSymbol" @input="updateSearch" class="form-control" id="floatingInput"
				placeholder="GOOG">
			<label for="floatingInput">Search Stocks</label>
		</div>

		<div :class="{ blurred: buySellCard.show }">
			<stock-container @cardClick="updateGraphWith" v-model="buySellCard" :stocks="search.cards"
				class="stocks-search" v-show="!onPortfolio" :onPortfolio="false" />
			<stock-container @cardClick="updateGraphWith" v-model="buySellCard"
				:stocks="this.$store.state.portfolio.cards" class="stocks-owned" v-show="onPortfolio"
				:onPortfolio="true" />
		</div>


		<div :class="{ blurred: buySellCard.show }">
			<chat-container></chat-container>
		</div>

		<buy-stock v-show="buySellCard.show" v-model="buySellCard"></buy-stock>

	</div>
</template>

<script>
import ChatContainer from "../components/ChatContainer.vue"
import BuyStock from "../components/BuyStock.vue"
import Leaderboard from "../components/Leaderboard.vue"
import StockContainer from "../components/StockContainer.vue";
import GameAccount from "../components/GameAccount.vue";
import LineChart from "../components/LineChart.vue";
import MarketDataService from "../services/MarketDataService";
import tradeService from "../services/TradeService"

export default {
	name: "portfolio",
	components: { LineChart, GameAccount, StockContainer, Leaderboard, BuyStock, ChatContainer },
	data() {
		return {
			gameId: this.$route.params.id,

			onPortfolio: true,

			search: {
				input: "",
				symbols: [],
				cards: []
			},


			graphData: {
				dataPoints: [],
				time: [],
				labels: []
			},

			buySellCard: {
				show: false,
				price: 0.0,
				symbol: "",
				buySell: false,
			},

			graphLabel: {},

			tradeSnapshots: []
		}
	},
	methods: {
		getPortfolioGraph() {
			// let data = [];
			// let week = new Date();
			// let today = new Date();
			// week.setDate(week.getDate() - 8);
			// MarketDataService.getHistoricalDailyDataBySymbol(week, today, this.$store.state.portfolio.symbols).then(resp => {
			// 	data = resp.data;
			// 	console.log(data)

			// })
			// return data;
			this.graphLabel = "My Portfolio"
			this.graphData.dataPoints = []
			this.graphData.time = []
			this.graphData.dataPoints.push(100000)
			this.graphData.dataPoints.push(100000)
			this.graphData.dataPoints.push(100000)
			this.graphData.dataPoints.push(100000)
			this.graphData.dataPoints.push(100000)
			this.graphData.dataPoints.push(100000)
			this.graphData.dataPoints.push(100000)
			this.graphData.time.push(new Date())
			this.graphData.time.push(new Date())
			this.graphData.time.push(new Date())
			this.graphData.time.push(new Date())
			this.graphData.time.push(new Date())
			this.graphData.time.push(new Date())
			this.graphData.time.push(new Date())
			// this.graphData.labels.push(100000)


		},
		updateGraphWith(symbol) {
			const graphData = this.graphData
			this.graphLabel = symbol
			if (graphData.dataPoints.length > 0) {
				graphData.dataPoints = []
				graphData.time = []
				graphData.labels = []
			}
			MarketDataService.getHistoricalMinuteDataBySymbol(symbol).then(resp => {
				const data = resp.data;
				data.reverse().forEach(d => {

					graphData.dataPoints.push(d.low)
					graphData.time.push(d.date)
					graphData.labels.push(d.low)
				});

			})
		},
		showCard(state, stockInfo) {
			this.showBuySellCard = state;
			this.stockInfo = stockInfo
		},
		switchView(event) {
			// Used innerText to be more specific of where the even it coming from
			const text = event.target.innerText;
			if (text == "Buy Stocks" || text == "View Portfolio") {
				this.onPortfolio = !this.onPortfolio;
				if (this.onPortfolio) {
					// this.updateGraphWith('portfolio')
					this.getPortfolioGraph()
					this.search.symbols = []
				}
			}
		},
		updateSearch(event) {
			const symbol = event.target.value;
			if (symbol == '') {
				return;
			}
			MarketDataService.searchTicker(symbol).then(resp => {
				const data = resp.data;
				this.search.symbols = data.map(stock => stock.symbol);
				MarketDataService.getRealTimeStockPrice(this.search.symbols).then(resp => { this.search.cards = resp.data });
			});
		},
		getHistoricalData() {
			this.graphData.dataPoints = []
			this.time = []
			MarketDataService.getHistoricalMinuteDataBySymbol()
		}
	},
	created() {
		tradeService.getPortfolio(this.gameId).then(resp => {
			const symbols = resp.data.stocks.map(stock => stock.tickerSymbol)
			this.$store.commit("SET_PORTFOLIO_SYMBOLS", symbols)
			const trades = resp.data.stocks
			this.$store.commit("SET_PORTFOLIO_TRADES", trades)
		})

		// tradeService.getPortfolioHistory(this.gameId).then(resp => {
		// 	this.tradeSnapshots = resp.data
		// })
		this.getPortfolioGraph()


		// this.updateGraphWith(this.tempKey)
		MarketDataService.getRealTimeStockPrice(this.$store.state.portfolio.symbols).then(resp => {
			// this.portfolio.cards = resp.data;
			//testing
			const cardData = resp.data
			this.$store.commit("SET_PORTFOLIO_CARDS", cardData)
		})
		setInterval(() => {
			// const allSymbols = this.$store.state.portfolio.symbols.concat(this.search.symbols)



			if (this.search.symbols) {
				MarketDataService.getRealTimeStockPrice(this.search.symbols).then(resp => {
					const data = resp.data
					this.search.cards = data.filter(stock => this.search.symbols.includes(stock.symbol))
				})
			}


			MarketDataService.getRealTimeStockPrice(this.$store.state.portfolio.symbols).then(resp => {
				const data = resp.data
				const filteredData = data.filter(stock => {
					if (this.$store.state.portfolio.symbols.includes(stock.symbol)) {
						const index = this.$store.state.portfolio.symbols.indexOf(stock.symbol)
						const sharesOwned = this.$store.state.portfolio.trades[index].numberOfShares
						return sharesOwned > 0
					}
					return false
				})
				this.$store.commit("SET_PORTFOLIO_CARDS", filteredData)
			})


			// if ((this.graphLabel != "My Portfolio")) {
			// 	MarketDataService.getRealTimeStockPrice(this.graphLabel).then(resp => {
			// 			const data = resp.data[0];
			// 			this.graphData.dataPoints.push(data.price)
			// 			this.graphData.time.push(data.earningsAnnouncement)
			// 	})
			// }
			// else {

			// }


		}, 6 * 1000)
	},
	computed: {
		chartStyles() {
			return {
				//change chart css here
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

</style>