<template>
	<div class="portfolio-container">
		<div class="portfolio" @click.capture="switchView" :class="{ blurred: buySellCard.show }">
			<game-account />
			<line-chart :key="tempKey" :styles="chartStyles" :dataPoints="graphData.dataPoints"
				:labels="graphData.time" />
			<leaderboard />
		</div>

		<div v-show="!onPortfolio" id="search" :class="{ blurred: buySellCard.show }">
			<label for="search">Search</label>
			<input type="text" name="searchSymbol" @input="updateSearch"><br><br>
		</div>
		<div :class="{ blurred: buySellCard.show }">
			<stock-container @cardClick="updateGraphWith" v-model="buySellCard" :stocks="search.cards"
				class="stocks-search" v-show="!onPortfolio" />
			<stock-container @cardClick="updateGraphWith" v-model="buySellCard" :stocks="portfolio.cards"
				class="stocks-owned" v-show="onPortfolio" />
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
				symbols: ['META', 'AAPL', 'NFLX', 'GOOG', 'AMZN', 'HBI'],
				cards: [],
			},

			graphData: {
				dataPoints: [],
				time: [],
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
			}
			MarketDataService.getHistoricalMinuteDataBySymbol(this.tempKey).then(resp => {
				const data = resp.data;
				data.reverse().forEach(d => {

					graphData.dataPoints.push(d.low)
					graphData.time.push(d.date)
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
		this.updateGraphWith(this.tempKey)
		MarketDataService.getRealTimeStockPrice(this.portfolio.symbols).then(resp => {
			this.portfolio.cards = resp.data;
		})
		setInterval(() => {
			const allSymbols = this.portfolio.symbols.concat(this.search.symbols)
			MarketDataService.getRealTimeStockPrice(allSymbols).then(resp => {
				const data = resp.data
				// optimized
				this.search.cards = data.filter(stock => this.search.symbols.includes(stock.symbol))
				this.portfolio.cards = data.filter(stock => this.portfolio.symbols.includes(stock.symbol))
			})

			MarketDataService.getRealTimeStockPrice(this.tempKey).then(resp => {
				const data = resp.data[0]
				this.graphData.dataPoints.push(data.price)
				this.graphData.time.push(data.earningsAnnouncement)
			})

		}, 1 * 1000)
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
	filter: blur(10px)
}
</style>