<template>
    <div>
        <h1>You joined room: {{ currentRoom }}</h1>
        <button @click="handleLeave">Leave</button>
        <ul>
            <li v-for="(user, index) in users" :key=index>{{ user }}</li>
        </ul>

    </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp, { Client } from "webstomp-client";
export default {
    name: "testLobby",
    data() {
        return {
            currentRoom: this.$route.params.gameId,
            users: [],
            socket: SockJS,
            stompClient: Client,
        }
    },
    created() {
        this.socket = new SockJS("http://localhost:8080/");
        this.stompClient = Stomp.over(this.socket);
        this.stompClient.debug = () => { return; }
        this.stompClient.connect({},
            () => {
                // console.log(frame)
                this.connected = true


                // Let everyone know you joined the game
                this.stompClient.subscribe(`/topic/room-${this.currentRoom}/join`, resp => this.handleJoin(resp))
                this.stompClient.subscribe(`/topic/room-${this.currentRoom}/leave`, resp => this.handleJoin(resp))
                this.stompClient.send(`/app/room-${this.currentRoom}/join`, this.$store.state.user.username)
            },
            () => {
                //console.log(error);
                this.connected = false
            }
        )
    },
    methods: {
        handleJoin(resp) {
            console.log(resp)
            const body = JSON.parse(resp.body)
            const players = body.players;
            this.users = players;
        },
        handleLeave() {
            console.log("Disconnect", this.currentRoom)
            this.stompClient.send(`/app/room-${this.currentRoom}/leave`, this.$store.state.user.username)

        }
    }
}
</script>

<style>

</style>