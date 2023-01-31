<template>
    <div id="game">
        <span id="name">{{ this.name }}</span>
        <span id="type">{{ this.type }}</span>
        <span id="length">{{ this.duration }}</span>
        <button id="join" @click="joinGame">Join</button>
    </div>
</template>

<script>
export default {
    name: 'Game',
    props: {
        name: String,
        type: String,
        duration: String,
    },
    methods: {
        joinGame() {
            console.log("Joining Game")
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