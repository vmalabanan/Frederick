<template>
  <div id="main-content">
    <div id="chat-log">
      <table>
        <tbody>
          <tr v-for="item in receivedMessages" :key="item">
            <td>{{ item }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="button-container">
      <input type="text" v-model="sendMessage" />
      <button @click="send" class="btn btn-primary">Send Message</button>
      <button @click="clearChat()" class="btn btn-light">Clear Chat</button>
    </div>
  </div>
</template>

<script>
import socketService from "../services/SocketService";

export default {
  name: "test",
  data() {
    return {
      receivedMessages: [],
      sendMessage: "",
      stompClient: socketService.startConnection(),
      connection: false,
      showChat: false,
    };
  },
  created() {
    this.stompClient.connect(
      {},
      () => {
        // console.log(frame)
        this.connected = true;
        this.stompClient.subscribe("/topic/chat", (resp) =>
          this.handleMessage(resp)
        );
      },
      () => {
        // console.log(error);
        this.connected = false;
      }
    );
  },
  beforeUnMount() {
    this.disconnect();
  },
  methods: {
    handleMessage(resp) {
      const body = JSON.parse(resp.body);
      this.receivedMessages.push(`${body.fromUser}: ${body.content}`);
    },
    send() {
      console.log("Send message:" + this.sendMessage);
      if (
        this.stompClient &&
        this.stompClient.connected &&
        this.sendMessage.trim().length != 0
      ) {
        const msg = {
          fromUser: this.$store.state.user.username,
          content: this.sendMessage,
        };
        this.stompClient.send("/app/game", JSON.stringify(msg), {});
      } else {
        const msg = "Error: Message cannot be empty";
        this.receivedMessages.push(msg);
      }
      this.sendMessage = "";
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
    toggleChatLog() {
      this.showChat = !this.showChat;
    },
    clearChat() {
      this.receivedMessages = [];
    },
  },
};
</script>

<style scoped>
#main-content {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

#chat-log {
  overflow-y: auto;
  background-color: white;
  /* border-radius: 20px; */
  border-radius: 0.5rem;

  /* height: 400px; */
  /* min-height: 360px; */
  height: 40vh;

  display: flex;
  flex-direction: column-reverse;
}

.button-container {
  display: flex;
  justify-content: center;
  gap: 15px;
}

.btn-light {
  background-color: #8892ab;
  border: none;
  border-radius: 0.5rem;
  color: white;
}

.btn-light:hover {
  background-color: #656e8e;
  color: white;
}

.btn-primary {
  color: white;
  background-color: #3c4d74;
  border: none;
  border-radius: 0.5rem;
}

.btn-primary:hover {
  background-color: #223e7e;
  color: white;
}

input {
  width: 500px;
}

td {
  padding-left: 10px;
  padding-bottom: 10px;
}
</style>
