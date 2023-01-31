<template>
    <div id="GameList">
        <div class="card w-50">
            <div class="card-header" style="background-color: #FFB703; border-radius:25px; height: 60px">
                <span>Game Name</span>
                <span>Type</span>
                <span>Length</span>
                <i class="fa-sharp fa-solid fa-xmark-large"></i>
            </div>
            <div id="games" class="card-body">
                <Game name="MANGA FTW" type="Normal" duration="1 Week" />
                <Game name="MANGA FTW" type="Normal" duration="1 Week" />
                <Game name="MANGA FTW" type="Normal" duration="1 Week" />
            </div>
            <div class="card-footer" style="background-color: #FFB703; border-radius:25px; height: 60px">
            </div>
        </div>
    </div>
</template>
<script>
import Game from '../components/Game.vue';
export default {
    name: "GameList",
    components: {
        Game,
    }
}
</script>

<style>
div#GameList {
    display: flex;
    height: 100vh;
    display: flex;
    align-items: center;
}


div#GameList>div {
    background-color: #8ECAE6;
    border-radius: 25px;
    margin: 0 auto;
    border: none;
}

div.card-header {
    display: flex;
    justify-content: space-evenly;
    align-items: center;
}
</style>5