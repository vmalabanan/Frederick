<template>
	<div class="portfolio-container">
		<div class="portfolio" @click.capture="switchView">
			<game-account />
			<line-chart :styles="chartStyles" :dataPoints="graphData.dataPoints" :labels="graphData.labels" />
			<leaderboard />
		</div>
		<div v-show="!onPortfolio" id="search">
			<label for="search">Search</label>
			<input type="text" name="searchSymbol" @input="updateSearch"><br><br>
		</div>
    <stock-container :stocks="search.cards" class="stocks-search" v-show="!onPortfolio" />
    <stock-container :stocks="portfolio.cards" class="stocks-owned" v-show="onPortfolio" />
	</div>
</template>

<script>
import Leaderboard from "../components/Leaderboard.vue"
import StockContainer from "../components/StockContainer.vue";
import GameAccount from "../components/GameAccount.vue";
import LineChart from "../components/LineChart.vue";
import MarketDataService from "../services/MarketDataService";

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
		MarketDataService.getRealTimeStockPrice(this.portfolio.symbols).then(resp => {
			this.portfolio.cards = resp.data;
		})
		setInterval(() => {
			const allSymbols = this.portfolio.symbols.concat(this.search.symbols)
			MarketDataService.getRealTimeStockPrice(allSymbols).then(resp => {
				const data = resp.data
				this.search.cards = data.filter(stock => this.search.symbols.includes(stock.symbol))
				this.portfolio.cards = data.filter(stock => this.portfolio.symbols.includes(stock.symbol))
			})
		}, 60 * 1000)
	},
	data() {
		return {
			onPortfolio: true,
			date: 0,

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

		}
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
</style>