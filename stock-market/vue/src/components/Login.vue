<template>
  <form id="login" class="form form-signin" @submit.prevent="login">
    <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
    <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
      Invalid username and password!
    </div>
    <div class="alert alert-success" role="alert" v-if="isRegSuccessful">
      Thank you for registering, please sign in.
    </div>

    <div class="form-floating">
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <label for="username">Username</label>
    </div>

    <div class="form-floating">
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <label for="password">Password</label>
    </div>

    <button class="btn btn-lg btn-primary" type="submit">Sign in</button>
  </form>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  props: ["isRegSuccessful"],
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push({ name: "menu" });
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>

<style scoped>
button {
  border: none;
  box-shadow: 0.5rem 0.5rem #ffb703;
  color: #fff;
}

.btn-primary {
  background-color: #477fc9;
}

.btn-primary:hover {
  background-color: #1566d1;
  color: #000;
}

form#login {
  /* display: flex; */
  justify-content: center;
}

.form-signin {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form-floating {
  /* width: 100%; */
  align-self: center;
  justify-self: center;
}

input {
  margin-bottom: 1rem;
  width: 100%;
  /* margin-left: 2.75rem; */
}

.submit {
  margin: 0.25rem;
}
</style>
