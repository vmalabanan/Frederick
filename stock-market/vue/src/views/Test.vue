<template>
	<div>
		<div id="main-content" class="container">
			<div class="row">
				<div class="col-md-6">
					<form class="form-inline">
						<div class="form-group">
							<label for="connect">WebSocket connection:</label>
							<button id="connect" class="btn btn-default" type="submit" :disabled="connected == true"
								@click.prevent="connect">Connect</button>
							<button id="disconnect" class="btn btn-default" type="submit" :disabled="connected == false"
								@click.prevent="disconnect">Disconnect
							</button>
						</div>
					</form>
				</div>
				<div class="col-md-6">
					<form class="form-inline">
						<div class="form-group">
							<label for="name">Say Hello!</label>
							<input type="text" id="name" class="form-control" v-model="sendMessage">
						</div>
						<button id="send" class="btn btn-default" type="submit" @click.prevent="send">Send</button>
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table id="conversation" class="table table-striped">
						<thead>
							<tr>
								<th>Chat</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="item in receivedMessages" :key="item">
								<td>{{ item }}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
	name: "test",
	data() {
		return {
			socket: SockJS,
			receivedMessages: [],
			sendMessage: "",
			connected: false
		};
	},
	created() {
		this.socket = new SockJS("http://localhost:8080/");
		this.socket.onopen = function () {
			console.log('connected!')
			this.connect = true
		}
	},
	methods: {
		send() {
			console.log("Send message:" + this.sendMessage);
			if (this.stompClient && this.stompClient.connected) {
				const msg = { fromUser: this.$store.state.user.username, content: this.sendMessage };
				this.stompClient.send("/app/game", JSON.stringify(msg), {});
			}
		},
		connect() {
			this.socket = new SockJS("http://localhost:8080/");
			this.stompClient = Stomp.over(this.socket);
			this.stompClient.connect(
				{},
				frame => {
					this.connected = true;
					console.log(frame);
					this.stompClient.subscribe("/topic/chat", tick => {
						const body = JSON.parse(tick.body)
						this.receivedMessages.push(`${body.fromUser}: ${body.content}`);
					});
				},
				error => {
					console.log(error);
					this.connected = false;
				}
			);
		},
		disconnect() {
			if (this.stompClient) {
				this.stompClient.disconnect();
			}
			this.connected = false;
		},
		tickleConnection() {
			this.connected ? this.disconnect() : this.connect();
		}
	},
	mounted() {
		// this.connect();
	}
};
</script>

<style scoped>

</style>