<template>
    <div class="dropdown">
        <button class="dropbtn" @click="showAllGames()">My Games</button>
        <div class="dropdown-content">
            <div class="dropdown-item" v-for="game in myGames" :key="game.id" v-show="showGames"  @click="goToGame(game.gameId)">
                {{ game.gameName }}
            </div>
        </div>
    </div>
</template>

<script>
import gamesService from "../services/GamesService"
export default {
    name: "ActiveGames",
    data() {
        return {
            myGames: [],
            showGames: false
        }
    },
    created() {
        gamesService.getAcceptedGames().then(response => {
            this.myGames = response.data
        })
    },
    methods: {
        goToGame(gameId) {
            this.$router.push({ name: 'portfolio', params: { id : gameId} })
            this.$router.go()
        },
        showAllGames() {
            this.showGames = !this.showGames
        }
    }
}
</script>

<style scoped>
.dropbtn {
    background: none;
    border: none;
    color: #FB8500;
    font-weight: bold;
}

.dropdown {
    cursor: pointer;
}

.dropdown-content {
    position: absolute;
    top: 90px;
    display: flex;
    flex-direction: column;
    background-color: rgba(2, 48, 71, 0.9);
    border-radius: 15px;
    border: 2px solid black;
}

.dropdown-item {
    color: whitesmoke;
    padding: 10px;
    text-decoration: underline;
    text-align: center;
}

</style>