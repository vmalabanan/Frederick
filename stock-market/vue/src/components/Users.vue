<template>
  <div>
    <label for="userList" class="sr-only">Players</label>

    <select
      name="userList"
      id="userList"
      class="form-control"
      v-model="selectedUsers"
      multiple
    >
      <option v-for="user in users" v-bind:key="user.id" value="user">{{
        user
      }}</option>
    </select>
  </div>
</template>

<script>
import userService from "../services/UserService.js";

export default {
  name: "users",
  data() {
    return {
      users: {},
      selectedUsers: {}
    };
  },
  methods: {
    getUsers() {
      userService.getAllUsers().then(response => {
        this.users = response.data;
      });
    },
    getSelectedUsers() {
      return this.selectedUsers;
    }
  },
  created() {
    this.getUsers();
    console.log("users: " + this.users)
  }
};
</script>

<style></style>
