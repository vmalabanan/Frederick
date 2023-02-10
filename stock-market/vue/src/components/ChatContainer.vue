<template>
  <div class="container">
    <!-- <h2>Chat with Friends</h2> -->
    <chat-menu></chat-menu>
  </div>
</template>

<script>
import ChatMenu from "./ChatMenu.vue";

export default {
  name: "ChatContainer",
  components: { ChatMenu },
  data() {
    return {};
  },
};
</script>

<style scoped>
.container {
  /* margin-top: 20px; */
  /* margin-bottom: 20px; */
  background-color: #fff;
  /* min-height: 500px; */
  /* min-height: 450px; */
  min-height: 46vh;

  width: 30vw;
  /* width: 50%; */
  /* border-radius: 20px; */
  border-radius: 0.5rem;

  display: flex;
  flex-direction: column;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
  margin-bottom: 1.75rem;
}

h2 {
  color: #000;
  display: flex;
  justify-content: center;
  /* text-decoration: underline; */
  /* padding-top: 10px; */
  /* font-weight: bold; */
}
</style>
