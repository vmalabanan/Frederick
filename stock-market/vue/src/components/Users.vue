<template>
  <div>
    <label for="userList" class="sr-only">Players to invite:</label>

    <select
      name="userList"
      id="userList"
      class="form-control"
      v-model="selectedUsers"
      v-on:change="addSelectedUsersToStore"
      multiple
    >
      <option v-for="user in users" v-bind:key="user">{{ user }}</option>
    </select>
  </div>
</template>

<script>
import userService from "../services/UserService.js";

export default {
  name: "users",
  data() {
    return {
      users: [],
      selectedUsers: [],
    };
  },
  methods: {
    getUsers() {
      userService.getAllUsers().then((response) => {
        this.users = response.data;
      });
    },
    addSelectedUsersToStore() {
      this.$store.commit("SET_SELECTED_USERS", this.selectedUsers);
    },
  },
  created() {
    this.getUsers();
  },
};
</script>

<style scoped>
select {
  background-color: #3483ee;
  color: #fff;
}

select:focus {
  background-color: #4890f5;
}
</style>
