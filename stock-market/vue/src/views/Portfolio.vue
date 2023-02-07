<template>
	<div class="portfolio-container">
		<div class="portfolio" @click.capture="switchView" :class="{ blurred: buySellCard.show }">
			<game-account />
			<line-chart :key="tempKey" :styles="chartStyles" :dataPoints="graphData.dataPoints"
				:labels="graphData.time" />
			<leaderboard />
		</div>

		<div v-show="!onPortfolio" id="search" :class="{ blurred: buySellCard.show }" class="form-floating mb-3">
			<input type="text" name="searchSymbol" @input="updateSearch" class="form-control" id="floatingInput"
				placeholder="GOOG">
			<label for="floatingInput">Search Stocks</label>
		</div>

		<div :class="{ blurred: buySellCard.show }">
			<stock-container @cardClick="updateGraphWith" v-model="buySellCard" :stocks="search.cards"
				class="stocks-search" v-show="!onPortfolio" :onPortfolio="false" />
			<stock-container @cardClick="updateGraphWith" v-model="buySellCard" :stocks="portfolio.cards"
				class="stocks-owned" v-show="onPortfolio" :onPortfolio="true" />
		</div>

		<buy-stock v-show="buySellCard.show" v-model="buySellCard"></buy-stock>
	</div>
</template>

<script>
import BuyStock from "../components/BuyStock.vue"
import Leaderboard from "../components/Leaderboard.vue"
import StockContainer from "../components/StockContainer.vue";
import GameAccount from "../components/GameAccount.vue";
import LineChart from "../components/LineChart.vue";
import MarketDataService from "../services/MarketDataService";
import tradeService from "../services/TradeService"

export default {
	name: "portfolio",
	components: { LineChart, GameAccount, StockContainer, Leaderboard, BuyStock },
	data() {
		return {
			tempKey: "HBI",
			onPortfolio: true,

			search: {
				input: "",
				symbols: [],
				cards: []
			},

			portfolio: {
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

			}
		}
	},
	methods: {
		updateGraphWith(symbol) {
			const graphData = this.graphData
			this.tempKey = symbol;
			if (graphData.dataPoints.length > 0) {
				graphData.dataPoints = []
				graphData.time = []
				graphData.labels = []
			}
			MarketDataService.getHistoricalMinuteDataBySymbol(this.tempKey).then(resp => {
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
			if (text == "View Stocks" || text == "View Portfolio") {
				this.onPortfolio = !this.onPortfolio;
				if (this.onPortfolio) {
					this.updateGraphWith('HBI')
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
		const gameId = this.$route.params.id
		tradeService.getPortfolio(gameId).then(resp => {
			const symbols = resp.data.stocks.map(stock => stock.tickerSymbol)
			this.$store.commit("SET_PORTFOLIO_SYMBOLS", symbols)
			const trades = resp.data.stocks
			this.$store.commit("SET_PORTFOLIO_TRADES", trades)
		})

		this.updateGraphWith(this.tempKey)
		MarketDataService.getRealTimeStockPrice(this.$store.state.portfolio.symbols).then(resp => {
			this.portfolio.cards = resp.data;
		})
		setInterval(() => {
			// const allSymbols = this.$store.state.portfolio.symbols.concat(this.search.symbols)
			if (this.search.symbols)
			{
				MarketDataService.getRealTimeStockPrice(this.search.symbols).then(resp => {
					const data = resp.data
					// optimized
					this.search.cards = data.filter(stock => this.search.symbols.includes(stock.symbol))
				})
			}


			MarketDataService.getRealTimeStockPrice(this.$store.state.portfolio.symbols).then(resp => {
				const data = resp.data
				// optimized
				this.portfolio.cards = data.filter(stock => {
					if(this.$store.state.portfolio.symbols.includes(stock.symbol)) {
						const index = this.$store.state.portfolio.symbols.indexOf(stock.symbol)
						const sharesOwned = this.$store.state.portfolio.trades[index].numberOfShares
						return sharesOwned > 0
					}
					return false
					})
			})
	


			MarketDataService.getRealTimeStockPrice(this.tempKey).then(resp => {
				const data = resp.data[0]
				this.graphData.dataPoints.push(data.price)
				this.graphData.time.push(data.earningsAnnouncement)
			})

		}, 5 * 1000)
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