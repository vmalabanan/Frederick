<template>
	<div id="" class="container">
		<h1 style="text-align: center">Demo Control Panel</h1>
		<button @click="pumpStock">{{ !this.pump ? "GRGR TO THE MOOON!!!" : "Normalize GRGR" }}</button>
		<button @click="crashStock">{{!this.crash ? "Crash Stock NINJA" : "Normalize GRGR"}}</button>
		<button @click="applyVariance">{{ !this.variance ? "Add variance to all stocks" : "Remove Variance" }}</button>
		<button @click="normalize">Return to normal</button>
		<stock-card v-bind="this.GRGO" />
		<stock-card v-bind="this.NNJA" />
	</div>
</template>

<script>
import StockCard from '../components/StockCard.vue';
import SocketService from '../services/SocketService';
export default {
	components: { StockCard },
	name: "test",
	data() {
		return {
			connection: false,
			stompClient: SocketService.startConnection(),
			GRGO: {},
			NNJA: {},
			variance: false,
			crash: false,
			pump: false,
		}
	},
	created() {
		this.stompClient.connect({},
			() => {
				console.log("Connecting")
				this.stompClient.subscribe("/topic/update", resp => this.handleCard(resp))
			},
			() => {
				console.log("error")
			}
		)
	},
	methods: {
		handleCard(resp) {
			const data = JSON.parse(resp.body)
			data.forEach(s => {
				if (s.symbol == "GRGO") {
					this.GRGO = s
				}
				if (s.symbol == "NNJA") {
					this.NNJA = s
				}


			});
		},
		crashStock() {
			this.crash = !this.crash
			this.stompClient.send("/app/crash", this.crash)

		},
		pumpStock() {
			this.pump = !this.pump
			this.stompClient.send("/app/pump", this.pump)
		},
		applyVariance() {
			this.variance = !this.variance
			this.stompClient.send("/app/variant", this.variance);

		},
		normalize() {
			this.crash = false
			this.variance = false
			this.pump = false

			this.stompClient.send("/app/variant", this.variance);
			this.stompClient.send("/app/crash", this.pump)
			this.stompClient.send("/app/crash", this.crash)


		},
	}
};
</script>

<style scoped>
div {
	margin-top: 10%;
	display: flex;
	flex-direction: column;
	align-content: center;
}

button {
	padding: 10px;
	margin: 10px;
	border-radius: 15px;
	border: none;
	color: black;
	font-weight: 400;
	background-color: cornflowerblue;
}
</style>