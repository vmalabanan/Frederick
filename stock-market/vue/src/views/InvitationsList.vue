<template>
    <div id="GameList">
        <div class="card w-50">
            <div class="card-header" style="background-color: #FFB703; border-radius:25px; height: 60px">
                <span style="width: 200px">Game Name</span>
                <span style="width: 60px">Type</span>
                <span style="width: 60px">Length</span>
                <span style="width: 100px"></span>
            </div>
            <div id="games" class="card-body">
                <Game v-for="(game, index) in games" :key="index" :game="game" />
            </div>
            <div class="card-footer" style="background-color: #FFB703; border-radius:25px; height: 60px" />
        </div>
    </div>
</template>
<script>
import Game from '../components/Game.vue';
import GamesService from '../services/GamesService';
export default {
    name: "InvitationsList",
    components: {
        Game,
    },
    data() {
        return {
            games: []
        }
    },
    created() {
        GamesService.getGames().then(resp => {
            this.games = resp.data
        })
    }
}
</script>

<style>
div#GameList {
    display: flex;
    align-items: center;
    margin-top: 5%;
}


div#GameList>div {
    background-color: #8ECAE6;
    border-radius: 25px;
    margin: 0 auto;
    border: none;
}

div.card-header {
    display: flex;
    justify-content: space-around;
    align-items: center;
}
</style>