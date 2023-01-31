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
          v-model.number="game.length"
          required
        >
          <!-- We only have one game length option for now -->
          <option disabled value="">Please select an option</option>
          <option value="7">7 days</option>
        </select>
        <label for="length" class="sr-only">Game Length</label>
      </div>
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
import gamesService from "../services/GamesService.js";

export default {
  name: "create",
  data() {
    return {
      game: {
        gameName: "",
        length: 0,
        endDate: 0,
      },
      createGameErrors: false,
      createGameErrorMsg: "There were problems creating a game."
    };
  },
  methods: {
    createGame() {
      // set end date
      const endDate = new Date();
      endDate.setDate(endDate.getDate() + this.game.length);

      // send game info to back end
      gamesService.add(this.game).then(response => {
        if (response.status === 201) {
          // set game ID with info from backend

          const id = response.data.id;
          console.log("response data: " + response.data); // just checking what response data is

          // redirect user to portfolio screen for newly created game
          this.$router.push({ name: "portfolio", params: { id: id } });
        }
      })
      .catch(error => {
            const response = error.response;
            if (response.status === 400) {
              this.createGameErrorMsg = "Bad Request: Game Creation Errors";
            }
      })
    }
  },

};
</script>

<style scoped>
#create {
  margin-top: 2rem;
  display: flex;
  width: 100vw;
  justify-content: center;
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
