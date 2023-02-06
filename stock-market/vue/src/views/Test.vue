<template>
	<div id="main-content" class="container">
		<table>
			<tbody>
				<tr v-for="item in receivedMessages" :key="item">
					<td>{{ item }}</td>
				</tr>
			</tbody>
		</table>
		<input type="text" v-model="sendMessage" />
		<button @click="send">Send Message</button>
	</div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp, { Client } from "webstomp-client";

export default {
	name: "test",
	data() {
		return {
			socket: SockJS,
			stompClient: Client,
			receivedMessages: [],
			sendMessage: "",
			connected: false
		};
	},
	created() {
		this.socket = new SockJS("http://localhost:8080/");
		this.stompClient = Stomp.over(this.socket);
		this.stompClient.connect({},
			frame => {
				console.log(frame)
				this.connected = true
				this.stompClient.subscribe('/topic/chat', resp => this.handleMessage(resp))
			},
			error => {
				console.log(error);
				this.connected = false
			}
		)
	},
	beforeUnMount() {
		this.disconnect()
	},
	methods: {
		handleMessage(resp) {
			const body = JSON.parse(resp.body)
			this.receivedMessages.push(`${body.fromUser}: ${body.content}`);
		},
		send() {
			console.log("Send message:" + this.sendMessage);
			if (this.stompClient && this.stompClient.connected) {
				const msg = { fromUser: this.$store.state.user.username, content: this.sendMessage };
				this.stompClient.send("/app/game", JSON.stringify(msg), {});
			}
		},
		disconnect() {
			if (this.stompClient) {
				this.stompClient.disconnect();
			}
			this.connected = false;
		},
	}
};
</script>

<style scoped>

</style>