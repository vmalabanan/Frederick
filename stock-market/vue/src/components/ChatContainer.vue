<template>
	<div class="container">
		<h2>Chat with Friends</h2>
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
	background-color: #8ECAE6;
	height: 500px;
	min-width: 100%;
	border-radius: 20px;
	display: flex;
	flex-direction: column;
}

h2 {
	color: #023047;
	display: flex;
	justify-content: center;
	text-decoration: underline;
	padding-top: 10px;
	font-weight: bold;
}
</style>