<template>
  <form id="register" class="form flex-column" @submit.prevent="register">
    <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
    <div class="alert alert-danger" role="alert" v-if="registrationErrors">
      {{ registrationErrorMsg }}
    </div>
    <div class="form-floating">
      <input type="text" id="sign-in-username" class="form-control" placeholder="Username" v-model="user.username"
        required autofocus />
      <label for="username" class="sr-only">Username</label>
    </div>

    <div class="form-floating">
      <input type="text" id="firstName" class="form-control" placeholder="First Name" v-model="user.firstName" required
        autofocus />
      <label for="firstName" class="sr-only">First Name</label>
    </div>

    <div class="form-floating">
      <input type="text" id="lastName" class="form-control" placeholder="Last Name" v-model="user.lastName" required
        autofocus />
      <label for="lastName" class="sr-only">Last Name</label>
    </div>

    <div class="form-floating">
      <input type="password" id="sign-in-password" class="form-control" placeholder="Password" v-model="user.password"
        required />
      <label for="password" class="sr-only">Password</label>
    </div>

    <div class="form-floating">
      <input type="password" id="confirmPassword" class="form-control" placeholder="Confirm Password"
        v-model="user.confirmPassword" required />
      <label for="confirmPassword" class="sr-only">Confirm Password</label>
    </div>

    <button class="btn-lg create-account" type="submit">
      Create Account
    </button>
  </form>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "register",
  props: ['value'],
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
  computed: {
    registration: {
      get() {
        return this.value
      },
      set(value) {
        this.$emit('input', value)
      }
    }

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
            const status = response.status
            if (status == 201) {
              this.registration = {
                isRPanelActive: true,
                isRegSuccessful: true
              }
            }
          })
          .catch(error => {
            console.log(error)
            const response = error.response;
            const status = response.status
            this.registrationErrors = true;
            if (status == 400) {
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

form#register {
  margin-top: 2rem;
  display: flex;
  max-width: 50%;
  justify-content: center;
}

button :hover {
  background-color: #FB8500;
}

.form-register {
  display: flex;
  flex-direction: column;
}

input {
  margin-bottom: 1rem;
}

.create-account {
  margin: .25rem;
}
</style>
