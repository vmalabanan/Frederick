<template>
	<div class="options-container">
		<div class="options-holder">
			<div class="options d-grid col-4 mx-auto">
				<button class="btn btn-lg btn-primary" @click="createPage">
					Host Game
				</button>
				<button class="btn btn-lg btn-warning" v-if="$store.state.invitedGames.length > 0"
					@click="invitationsList">
					Game Invites
				</button>
				<button class="btn btn-lg btn-info" v-if="$store.state.acceptedGames.length > 0" @click="gamesList">
					My Games
				</button>
			</div>
		</div>
	</div>

</template>

<script>
import gamesService from "../services/GamesService.js";

export default {
	name: "menu",
	methods: {
		createPage() {
			this.$router.push({ name: "createGame" });
		},
		invitationsList() {
			this.$router.push({ name: "invitationsList" });
		},
		gamesList() {
			this.$router.push({ name: "gamesList" });
		}
	},
	created() {
		// get list of game invitations and add to store
		gamesService.getInvitedGames().then(resp => {
			this.$store.commit("SET_INVITED_GAMES", resp.data);
		});

		// get list of all accepted games and add to store
		gamesService.getAcceptedGames().then(resp => {
			this.$store.commit("SET_ACCEPTED_GAMES", resp.data);
		});
	}
};
</script>

<style scoped>
.options-container {
	margin-top: 4rem;
	display: flex;
	justify-content: center;
}

.options-holder {
	width: 900px;
	background-color: #8ECAE6;
	border-radius: 25px;
	height: 800px;
	display: flex;
}

.options {
	display: flex;
	flex-direction: column;
	gap: 30px;
	height: 500px;
	width: 500px;
	align-self: center;
}

button {
	font-size: 50px;
	border-radius: 20px;
}
</style>
