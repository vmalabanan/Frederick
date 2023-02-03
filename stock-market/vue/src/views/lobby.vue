<template>
	<div>
		<h1>This is the current lobby for {{ lobbyName }}</h1>
		<div>{{ Username }}</div>


		<button>Start Game</button>
		<button>leave Game</button>

	</div>
</template>

<script>
export default {
	name: "lobby",
	data() {
		return {
			lobbyName: "Example",
			username: "Example"
		}

	}
}
</script>

<style>

</style>