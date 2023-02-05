<template>
	<div>
		<h1>Chat Client</h1>
		<br />

		<h1>{{ socketMessage }}</h1>
		<input id="msg" class="input-xlarge" type="text" v-model="message" />
		<button type="button" @click="sendMessage" class="btn" id="send">Send</button>
	</div>
</template>

<script>
import io from "socket.io-client"
export default {
	name: "test",
	data() {
		return {
			socket: {},
			message: "",
			socketMessage: "exzadsgfghj"
		}
	},
	sockets: {
		chat(data) {
			this.socketMessage = data
		}
	},
	methods: {
		sendMessage() {
			this.socket.emit('chat', this.message)
		}
	},
	created() {
		this.socket = io("http://localhost:9090", {
			transports: ['polling', 'websocket']
		});
	}
}
</script>

<style>
body {
	padding: 20px;
}

#console {
	height: 400px;
	overflow: auto;
}

.username-msg {
	color: orange;
}

.connect-msg {
	color: green;
}

.disconnect-msg {
	color: red;
}
</style>