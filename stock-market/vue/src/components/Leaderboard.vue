<template>
	<div id="leaderboard"></div>
</template>


<script>
import tradeService from "../services/TradeService"
// import MarketDataService from "../services/MarketDataService";

export default {
	name: "Leaderboard",
	props: ["gameId"],
	data() {
		return {};
	},
	created() {
		tradeService.getPortfolioAllPlayers(this.gameId).then(resp => {
			const playerPortfoliosArr = resp.data;
			// TODO:
			// iterate through playerPortfoliosArr and calculate portfolio value
			// i.e., cash + (numberOfShares * current price) for each stock

			// push each player's username and portfolio value to an object 
			// where key = username, value = portfolio value

			// then sort the portfolio values in descending order

			// and display the leaderboard
			// with rank, username, portfolio value


			// also--should I move this out of created and into methods?
			// should this method be called every time someone makes a trade?
			// or only on page load?
			console.log(playerPortfoliosArr);
		})
	},
	methods: {},
};
</script>

<style scoped>
#leaderboard {
	flex-basis: 20%;
	background-color: #246e93;
	border-radius: 20px;
	box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}
</style>