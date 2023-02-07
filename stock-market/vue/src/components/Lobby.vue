<template>
	<div id="lobby">
		<div class="card w-50">
			<div class="card-header" style="background-color: #FFB703; border-radius:25px; height: 60px">
				<span style="width: 330px">Username</span>
				<span style="width: 330px">Status</span>
				<span style="width: 100px"></span>
			</div>
			<div id="users" class="card-body">
				<div id="user" v-for="(user, index) in users" :key="index">
					<span style="width: 330px" id="username">{{ user }}</span>
					<span style="width: 330px" id="status">Joined</span>
					<span style="width: 100px"></span>
				</div>
			</div>
			<div class="card-footer" style="background-color: #FFB703; border-radius:25px; height: 60px" />
		</div>
		<button v-text="true ? 'Leave' : 'Join'" class="btn btn-lg btn-info" id="back" @click="handleBack" />
	</div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp, { Client } from "webstomp-client";
export default {
	name: "lobby",
	data() {
		return {
			leave: "leave",
			currentRoom: this.$route.params.gameId,
			users: [],
			socket: SockJS,
			stompClient: Client,
		}
	},
	created() {
		this.socket = new SockJS("http://localhost:8080/");
		this.stompClient = Stomp.over(this.socket);
		this.stompClient.debug = () => { return; }
		this.stompClient.connect({},
			() => {
				// console.log(frame)
				this.connected = true


				// Let everyone know you joined the game
				this.stompClient.subscribe(`/topic/room-${this.currentRoom}/join`, resp => this.handleJoin(resp))
				this.stompClient.subscribe(`/topic/room-${this.currentRoom}/leave`, resp => this.handleJoin(resp))
				this.stompClient.send(`/app/room-${this.currentRoom}/join`, this.$store.state.user.username)
			},
			() => {
				//console.log(error);
				this.connected = false
			}
		)
	},
	methods: {
		handleJoin(resp) {
			console.log(resp)
			const body = JSON.parse(resp.body)
			const players = body.players;
			this.users = players;
		},
		handleBack() {
			console.log("Disconnect from:", this.currentRoom)
			this.stompClient.send(`/app/room-${this.currentRoom}/leave`, this.$store.state.user.username)
			this.$router.go(-1)
		}
	},
}
</script>

<style>
div#lobby {
	display: flex;
	flex-direction: column;
	align-items: center;
	margin-top: 5%;
}

div#lobby>div {
	background-color: #8ecae6;
	border-radius: 25px;
	margin: 0 auto;
	border: none;
}

div.card-header {
	display: flex;
	justify-content: space-around;
	align-items: center;
}

div#lobby>#back {
	margin-top: 20px;
}

div#user {
	display: flex;
	justify-content: center;
	align-items: flex-start;
	flex-wrap: nowrap;
	background-color: #023047;
	border-radius: 15px;
	height: 60px;
	color: white;
	padding: 20px;
	margin: 10px;
}
</style>