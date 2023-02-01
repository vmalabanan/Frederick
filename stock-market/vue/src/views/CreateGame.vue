<template>
  <div id="create">
    <div class="alert alert-danger" role="alert" v-if="createGameErrors">
      {{ createGameErrorMsg }}
    </div>
    <form class="form-create-game" @submit.prevent="createGame">
      <div class="form-floating">
        <input
          type="text"
          id="gameName"
          class="form-control"
          placeholder="Game Name"
          v-model="game.gameName"
          required
          autofocus
        />
        <label for="gameName" class="sr-only">Game Name</label>
      </div>

      <div class="form-floating">
        <select
          name="length"
          id="length"
          class="form-control"
          v-model.number="game.gameLengthDays"
          required
        >
          <!-- We only have one game length option for now -->
          <option disabled value="">Please select an option</option>
          <option value="7">7 days</option>
        </select>
        <label for="length" class="sr-only">Game Length</label>
      </div>

      <Users />
      <button
        class="btn btn-lg btn-primary btn-block create-game"
        type="submit"
      >
        Create Game
      </button>
    </form>
  </div>
</template>

<script>
import gamesService from "../services/GamesService.js"
import Users from "../components/Users.vue"

export default {
  name: "create",
  components: {
    Users
  },
  data() {
    return {
      game: {
        gameName: "",
        gameLengthDays: 0,
        endDate: 0,
        players: {

        }
      },
      createGameErrors: false,
      createGameErrorMsg: "There were problems creating a game."
    };
  },
  methods: {
    createGame() {
      // set end date
      const endDate = new Date();
      endDate.setDate(endDate.getDate() + this.game.gameLengthDays);
      this.game.endDate = endDate;

      // set players
      // this.game.players = Users.

      console.log("players: " + this.game.players);
      console.log("selected users" + Users.getSelectedUsers());
      // send game info to back end
      gamesService.add(this.game).then(response => {
        if (response.status === 201) {
          // set game ID with info from backend
          const id = response.data;

          // redirect user to portfolio screen for newly created game
          this.$router.push({ name: "portfolio", params: { id: id } });
        }
      })
      .catch(error => {
            const response = error.response;
            if (response.status === 400) {
              this.createGameErrors = true;
              this.createGameErrorMsg = "Bad Request: Game Creation Errors";
            }
      })
    },

    getUsers() {

    }
  },

};
</script>

<style scoped>
#create {
  margin-top: 2rem;
  display: flex;
  flex-direction: column;
  width: 100vw;
  justify-content: center;
  align-items: center;
}
.form-create-game {
  display: flex;
  flex-direction: column;
  width: 30%;
}

.form-floating {
  margin-bottom: 1rem;
}

.create-game {
  margin: 0.25rem;
}
</style>
