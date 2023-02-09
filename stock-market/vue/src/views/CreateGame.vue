<template>
  <div id="create">
    <hamburger class="hamburger" :routeNames="hamburgerLinks"></hamburger>
    <div class="alert alert-danger" role="alert" v-if="createGameErrors">
      {{ createGameErrorMsg }}
    </div>
    <div class="create-game-content">
      <img
        class="fish"
        src="../img/fish-moving-eyes.gif"
        alt="floating fish with bubbles"
      />
      <div class="form-and-buttons-container">
        <form class="form-create-game" @submit.prevent="createGame">
          <div class="form-floating mb-3">
            <input
              type="text"
              class="form-control"
              id="floatingInputGameName"
              placeholder="Game Name"
              v-model="game.gameName"
              required
            />
            <label for="floatingInputGameName">Game Name</label>
          </div>

          <div class="form-floating">
            <select
              class="form-select"
              name="length"
              id="floatingSelectLength"
              aria-label="Floating label select example"
              v-model.number="game.gameLengthDays"
              required
            >
              <option selected>Select an option</option>
              <option value="7">7 days</option>
            </select>
            <label for="floatingSelectLength">Game Length</label>
          </div>

          <Users />
          <button
            class="btn btn-lg btn-primary btn-block create-game"
            type="submit"
          >
            Create Game
          </button>
        </form>
        <button class="btn btn-lg btn-warning back" @click="$router.go(-1)">
          Back
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import gamesService from "../services/GamesService.js";
import Users from "../components/Users.vue";
import Hamburger from "../components/Hamburger.vue";

export default {
  name: "create",
  components: {
    Users,
    Hamburger,
  },
  data() {
    return {
      game: {
        gameName: "",
        gameLengthDays: 0,
        endDate: 0,
        players: [],
      },
      createGameErrors: false,
      createGameErrorMsg: "There were problems creating a game.",
      hamburgerLinks: ["home"],
    };
  },
  methods: {
    createGame() {
      // set end date
      const endDate = new Date();
      endDate.setDate(endDate.getDate() + this.game.gameLengthDays);
      this.game.endDate = endDate;

      // set players
      this.game.players = this.$store.state.selectedUsers;

      // send game info to back end
      gamesService
        .add(this.game)
        .then((response) => {
          if (response.status === 201) {
            // set game ID with info from backend
            const id = response.data;

            // redirect user to portfolio screen for newly created game
            this.$router.push({ name: "portfolio", params: { id: id } });
          }
        })
        .catch((error) => {
          const response = error.response;
          if (response.status === 400) {
            this.createGameErrors = true;
            this.createGameErrorMsg = "Bad Request: Game Creation Errors";
          }
        });
    },
  },
};
</script>

<style scoped>
#create {
  /* margin-top: 2rem; */
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #3483ee;
}

.create-game-content {
  display: flex;
  align-items: center;
}

.form-and-buttons-container {
  /* width: 200%; */
}

.form-create-game {
  display: flex;
  flex-direction: column;
  width: 200%;
}

.form-floating {
  margin-bottom: 1rem;
}

.create-game {
  margin: 1rem 0;
}

.back {
  width: 30%;
}
</style>
