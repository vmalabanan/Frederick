<template>
	<div class="menu">
    <hamburger class="hamburger"></hamburger>
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

</template>

<script>
import gamesService from "../services/GamesService.js";
import hamburger from "../components/Hamburger.vue"

export default {
	name: "menu",
  components: {
    hamburger
  },
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
.options {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  /* width: 20%; */
}



</style>
