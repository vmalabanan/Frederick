<template>
	<div id="main-content" class="container">
		<div v-for="(content, index) in contents" v-text="content" :key="index" />
	</div>
</template>

<script>
import SocketService from '../services/SocketService';
export default {
	name: "test",
	data() {
		return {
			connection: false,
			stompClient: SocketService.startConnection(),
			contents: [],
		}
	},
	created() {
		this.stompClient.connect({},
			() => {
				console.log("Connecting")
				this.stompClient.subscribe('/topic/test', resp => this.handleMessage(resp))
				this.connection = true
			},
			() => {
				this.connection = false
			}
		)
	},
	methods: {
		handleMessage(resp) {
			this.contents.push(resp.body)
		},
		send() {
			console.log("Send message:" + this.sendMessage);
			if (this.stompClient && this.stompClient.connected) {
				const msg = { fromUser: this.$store.state.user.username, content: this.sendMessage };
				this.stompClient.send("/app/game", JSON.stringify(msg), {});
			}
		}
	}
};
</script>

<style scoped>

</style>