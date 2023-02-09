<template>
	<div id="account">
		<h4 class="labels">Account Value</h4>
		<p class="values">{{ getCashString(this.accountValue) }}</p>
		<h4 class="labels">Return on Investment</h4>
		<p class="values" :class="this.getChangePercentage < 0 ? 'negative' : 'positive'">{{ getChange() }}</p>
		<h4 class="labels">Cash</h4>
		<p class="values">{{ getCash }}</p>
		<button class="btn btn-lg" @click="handleClick">
			{{ this.buttonText }}
		</button>
	</div>
</template>

<script>
import cashService from "../services/CashService";

export default {
	name: "GameAccount",
	props: ["accountValue"],
	data() {
		return {
			buttonText: "Buy Stocks"
		};
	},
	methods: {
		handleClick() {
			//change router later
			if (this.buttonText == "Buy Stocks") {
				this.buttonText = "View Portfolio";
			} else {
				this.buttonText = "Buy Stocks";
			}
		},
		setCash() {
			const gameId = this.$route.params.id;
			cashService.getCashByGameId(gameId).then((response) => {
				this.$store.commit("SET_CASH", response.data);
			});
		},
		getCashString(cash) {
			let formatting_options = {
				style: "currency",
				currency: "USD",
				minimumFractionDigits: 2,
			};

			let dollarString = new Intl.NumberFormat("en-US", formatting_options);
			let cashString = dollarString.format(cash);
			return cashString
		},
		getChange() {
			if (this.getChangePercentage < 0) {
				return this.getChangePercentage + "%"
			}
			return "+" + this.getChangePercentage + "%"
		}

	},
	computed: {
		getCash() {
			return this.getCashString(this.$store.state.accountCash)
		},
		getChangePercentage() {
			const STARTING_CASH = 100000.00
			const net = this.accountValue - STARTING_CASH
			const percentage = (net / STARTING_CASH * 100).toFixed(2)
			return percentage

		},
	},
	created() {
		this.setCash()
	},
};
</script>

<style scoped>
#account {
	flex-basis: 20%;
	height: auto;
	background-color: #fff;
	display: flex;
	flex-direction: column;
	padding: 10px 20px;
	/* border-radius: 20px; */
	box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}

h4 {
	font-size: 1.25rem;
}

.labels {
	color: #000;
}

.values {
	display: flex;
	justify-content: center;
	align-items: center;
	color: #fff;
	background-color: #f95a1b;
	height: 40px;
	border-radius: 0.25rem;
}

button {
	margin-top: 40px;
	background-color: #fb8500;
	color: white;
	border-radius: 0.25rem;;
}

button:hover {
	background-color: #fb8500;
	color: white;
	/* border: 2px solid rgb(68, 68, 68); */
}

.positive {
	/* color: #1FCC92; */
	font-size: 20px;
}

.negative {
	/* color: #E54322; */
	font-size: 20px;
}
</style>