<template>
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
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
        <label for="username" class="sr-only">Username</label>
      </div>

       <div class="form-floating">
        <input
          type="text"
          id="firstName"
          class="form-control"
          placeholder="First Name"
          v-model="user.firstName"
          required
          autofocus
        />
        <label for="firstName" class="sr-only">First Name</label>
      </div>

       <div class="form-floating">
        <input
          type="text"
          id="lastName"
          class="form-control"
          placeholder="Last Name"
          v-model="user.lastName"
          required
          autofocus
        />
        <label for="lastName" class="sr-only">Last Name</label>
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
        <label for="password" class="sr-only">Password</label>
      </div>

      <div class="form-floating">
        <input
          type="password"
          id="confirmPassword"
          class="form-control"
          placeholder="Confirm Password"
          v-model="user.confirmPassword"
          required
        />
        <label for="confirmPassword" class="sr-only">Confirm Password</label>
      </div>
    
      <button class="btn btn-lg btn-primary btn-block create-account" type="submit">
        Create Account
      </button>
        <router-link :to="{ name: 'login' }">Have an account?</router-link>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "register",
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmPassword: "",
        firstName: "",
        lastName: "",
        role: "user"
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user."
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
        authService
          .register(this.user)
          .then(response => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" }
              });
            }
          })
          .catch(error => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    }
  }
};
</script>

<style scoped>
#register {
  margin-top: 2rem;
  display: flex;
  width: 100vw;
  justify-content: center;
}
.form-register {
  display: flex;
  flex-direction: column;
  width: 30%;
}

input {
  margin-bottom: 1rem;
}

.create-account {
  margin: .25rem;
}
</style>
