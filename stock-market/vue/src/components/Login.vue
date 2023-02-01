<template>
  <form id="login" class="form form-signin" @submit.prevent="login">
    <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
    <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
      Invalid username and password!
    </div>
    <div class="alert alert-success" role="alert" v-if="this.$route.query.registration">
      Thank you for registering, please sign in.
    </div>

    <div class="form-floating">
      <input type="text" id="username" class="form-control" placeholder="Username" v-model="user.username" required
        autofocus />
      <label for="username" class="sr-only">Username</label>
    </div>

    <div class="form-floating">
      <input type="password" id="password" class="form-control" placeholder="Password" v-model="user.password"
        required />
      <label for="password" class="sr-only">Password</label>
    </div>

    <button class="btn-lg" type="submit">Sign in</button>
  </form>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push({ name: 'menu' });
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>
button {
  border-radius: 20px;
  border: 1px solid #FB8500;
  background-color: #FB8500;
  color: #FFFFFF;
  font-size: 20px;
  font-weight: bold;
  padding: 12px 45px;
  letter-spacing: 1px;
  text-transform: uppercase;
  transition: transform 80ms ease-in;
}

button :hover {
  background-color: #FB8500;
}

form#login {
  max-width: 50%;
  display: flex;
  justify-content: center;
}

.form-signin {
  display: flex;
  flex-direction: column;
}

input {
  margin-bottom: 1rem;
}

.submit {
  margin: .25rem;
}
</style>
