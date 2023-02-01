<template>
    <div id="game">
        <span id="name" style="width: 200px">{{ game.gameName }}</span>
        <span id="type" style="width: 60px">Normal</span>
        <span id="length" style="width: 60px">{{ game.gameLengthDays }} days</span>
        <button id="join" @click="joinGame">Join</button>
    </div>
</template>

<script>
export default {
    name: 'Game',
    props: ['game'],
    methods: {
        joinGame() {
            this.$router.push({ name: "portfolio", params: { id: this.game.gameId } })
        }
    }
}
</script>

<style>
div#game {
    display: flex;
    background-color: #023047;
    border-radius: 15px;
    height: 60px;
    color: white;
    justify-content: space-between;
    align-items: center;
    padding: 20px;
    margin: 10px;
}

div#game>button#join {
    background-color: #FB8500;
    border-radius: 15px;
    border: none;
    width: 100px;
    height: 50px;
}
</style>