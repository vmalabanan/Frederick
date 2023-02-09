<template>
	<div class="container">
		<!-- <h2>Chat with Friends</h2> -->
		<chat-menu></chat-menu>
	</div>
</template>

<script>
import ChatMenu from "./ChatMenu.vue"

export default {
	name: "ChatContainer",
	components: { ChatMenu },
	data() {
		return {
		}
	}
}
</script>

<style scoped>
.container {
	margin-top: 20px;
	margin-bottom: 20px;
	background-color: #fff;
	height: 500px;
	/* width: 50%; */
	/* border-radius: 20px; */
	display: flex;
	flex-direction: column;
		box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25),
		0 10px 10px rgba(0, 0, 0, 0.22);
}

h2 {
	color: #000;
	display: flex;
	justify-content: center;
	/* text-decoration: underline; */
	/* padding-top: 10px; */
	/* font-weight: bold; */
}
</style>