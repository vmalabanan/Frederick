<template>
	<div id="app">
		<header class="navbar navbar-expand-ig bd-navbar sticky-top" v-if="this.$route.name !== 'gameOver'">
			<img src="./img/frederick_logo.svg" alt="Frederick the goldfish!" />
			<active-games v-if="this.$route.name == 'portfolio'"></active-games>
			<span>{{ this.header[this.$route.name] }}</span>
			<div id="nav" v-if="$store.state.token != ''">
				<router-link tag="span" v-bind:to="{ name: 'menu' }" v-if="this.$route.name == 'portfolio'">Game
					Menu</router-link>
				<router-link tag="span" v-bind:to="{ name: 'home' }">Logout</router-link>
			</div>
			<img style="transform: scaleX(-1)" src="./img/frederick_logo.svg" alt="Frederick the goldfish!" />
		</header>

		<!-- <div id="nav">
	<router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
	<router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
	</div> -->
		<router-view />
		<footer class="fixed-bottom px-4 py-4" style="background-color: #ffb703"
			v-if="this.$route.name !== 'gameOver'" />
	</div>
</template>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
<script>
import gamesService from "./services/GamesService"
import ActiveGames from "./components/ActiveGames.vue"

export default {
	name: 'App',
	components: {ActiveGames},
	data() {
		return {
			header: {
				home: 'Welcome',
				createGame: 'Create a Game',
				invitationsList: 'Join a Game',
				gamesList: "Join a Game",
				menu: 'Game Menu',
				portfolio: ''
			}
		}
	},
	watch: {
	$route (to, from) {this.getGameName() }
	},
	created() {
		this.getGameName()
	},
	methods: {
		getGameName() {
			if (this.$route.name == 'portfolio') {
				const gameId = this.$route.params.id
				gamesService.getGameById(gameId).then(resp => { this.header.portfolio = resp.data.gameName})
			}
		}
	},
}
</script>

<style >
header {
	background-color: #023047;
	color: #fb8500;
	font-size: 2.5rem;
	font-weight: bold;
}

#nav>span {
	cursor: pointer;
}
</style>
