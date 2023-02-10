<template>
	<div id="" class="container">
		<h1 style="text-align: center">Demo Control Panel</h1>
		<button @click="spikeStock">GRGR TO THE MOOON!!!</button>
		<button @click="crashStock">Crash Stock NINJA</button>
		<button @click="applyVariance">Add variance to all stocks</button>
		<button @click="normalize">Return to normal</button>
	</div>
</template>

<script>
import SocketService from '../services/SocketService';
export default {
	name: "test",
	data() {
		return {
			connection: false,
			stompClient: SocketService.startConnection()
		}
	},
	created() {
		this.stompClient.connect({},
			() => {
				console.log("Connecting")
			},
			() => {
				console.log("error")
			}
		)
	},
	methods: {
		crashStock() {

		},
		spikeStock() {

		},
		applyVariance() {

		},
		normalize() {

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