<template>
  <form id="register" class="form flex-column" @submit.prevent="register">
    <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
    <div class="alert alert-danger" role="alert" v-if="registrationErrors">
      {{ registrationErrorMsg }}
    </div>
    <div class="form-floating">
      <input
        type="text"
        id="sign-in-username"
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
        type="text"
        id="firstName"
        class="form-control"
        placeholder="First Name"
        v-model="user.firstName"
        required
        autofocus
      />
      <label for="firstName">First Name</label>
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
      <label for="lastName">Last Name</label>
    </div>

    <div class="form-floating">
      <input
        type="password"
        id="sign-in-password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <label for="password">Password</label>
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
      <label for="confirmPassword">Confirm Password</label>
    </div>

    <button class="btn btn-lg btn-primary" type="submit">Create Account</button>
  </form>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "register",
  props: ["value"],
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmPassword: "",
        firstName: "",
        lastName: "",
        role: "user",
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  computed: {
    registration: {
      get() {
        return this.value;
      },
      set(value) {
        this.$emit("input", value);
      },
    },
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
        authService
          .register(this.user)
          .then((response) => {
            const status = response.status;
            if (status == 201) {
              this.registration = {
                isRPanelActive: false,
                isRegSuccessful: true,
              };
            }
          })
          .catch((error) => {
            console.log(error);
            const response = error.response;
            const status = response.status;
            this.registrationErrors = true;
            if (status == 400) {
              this.registrationErrorMsg = "Bad Request: User already exist";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
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

#register {
  /* margin-top: 2rem; */
  display: flex;
  /* max-width: 50%; */
  justify-content: center;
  align-items: center;
}

.form-register {
  display: flex;
  flex-direction: column;
}

input {
  margin-bottom: 1rem;
  /* width: 150%; */
}

.create-account {
  margin: 0.25rem;
}
</style>
