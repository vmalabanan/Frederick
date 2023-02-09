<template>
  <div class="login-container">
    <img src="../img/stars.gif" alt="stars gif" class="stars stars-left" />
    <div class="text-center login">
      <div
        class="container"
        id="container"
        :class="{ 'right-panel-active': registration.isRPanelActive }"
      >
        <Register
          class="form-container sign-up-container"
          v-model="registration"
        />
        <Login
          class="form-container sign-in-container"
          :isRegSuccessful="registration.isRegSuccessful"
        />
        <div class="overlay-container">
          <div class="overlay">
            <div class="overlay-panel overlay-left">
              <h1>Frederick Missed You</h1>
              <p>
                login with your personal info to start trading with frederick
              </p>
              <button
                @click="handleScreenChange"
                class="btn btn-lg btn-primary ghost"
              >
                Sign In
              </button>
            </div>
            <div class="overlay-panel overlay-right">
              <h1>Hey, Swimmer</h1>
              <p>
                Put on your flippers and start trading stock with frederick
                today!
              </p>
              <button
                @click="handleScreenChange"
                class="btn btn-lg btn-primary ghost"
                id="signUp"
              >
                Sign Up
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <img src="../img/stars.gif" alt="stars gif" class="stars stars-right" />
  </div>
</template>

<script>
import Login from "../components/Login.vue";
import Register from "../components/Register.vue";
export default {
  name: "Registration",
  components: {
    Login,
    Register,
  },
  data() {
    return {
      registration: {
        isRPanelActive: false,
        isRegSuccessful: false,
      },
    };
  },
  methods: {
    handleScreenChange() {
      this.registration.isRPanelActive = !this.registration.isRPanelActive;
    },
  },
};
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  align-items: center;
  /* justify-content: space-around; */

  /* background-color: #f5e1af; */
  background-color: #ffe9e1;
}

.stars-left {
  padding-left: 5rem;
  margin-right: -5rem;
  width: 25%;
}

.stars-right {
  padding-right: 5rem;
  margin-left: -5rem;
  width: 25%;
}

.login {
  /* margin-top: 2rem; */
  display: flex;
  width: 100vw;
  justify-content: center;
}

button {
  border: none;
  box-shadow: 0.5rem 0.5rem #477fc9;
  color: #fff;
}

.btn-primary {
  background-color: #477fc9;
}

.btn-primary:hover {
  background-color: #1566d1;
  color: #000;
}

button.ghost {
  background-color: #fc7c31;
  border-color: white;
  /* margin-top: .5rem; */
}

/* Thank you https: florin-pop */
.container {
  /* background-color: #8ecae6; */
  background-color: #04c2fc;

  /* border-radius: 25px; */
  /* box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22); */
  box-shadow: 1.5rem 1.5rem #ffb703;

  position: relative;
  overflow: hidden;
  width: 75%;
  height: 70vh;
}

.form-container {
  position: absolute;
  top: 0;
  height: 100%;
  transition: all 0.6s ease-in-out;
}

.sign-in-container {
  /* background: #8ecae6; */
  background-color: #04c2fc;

  left: 0;
  width: 50%;
  z-index: 2;
}

.container.right-panel-active .sign-in-container {
  transform: translateX(100%);
}

.sign-up-container {
  /* background: #8ecae6; */
  background-color: #04c2fc;

  left: 0;
  width: 50%;
  opacity: 0;
  z-index: 1;
}

.container.right-panel-active .sign-up-container {
  transform: translateX(100%);
  opacity: 1;
  z-index: 5;
  animation: show 0.6s;
}

@keyframes show {
  0%,
  49.99% {
    opacity: 0;
    z-index: 1;
  }

  50%,
  100% {
    opacity: 1;
    z-index: 5;
  }
}

.overlay-container {
  position: absolute;
  top: 0;
  left: 50%;
  width: 50%;
  height: 100%;
  overflow: hidden;
  transition: transform 0.6s ease-in-out;
  z-index: 100;
}

.container.right-panel-active .overlay-container {
  transform: translateX(-100%);
}

.overlay {
  background: #023047;
  /* background: linear-gradient(to right, #32a9e0, #023047); */
  background-repeat: no-repeat;
  background-size: cover;
  background-position: 0 0;
  color: #ffffff;
  position: relative;
  left: -100%;
  height: 100%;
  width: 200%;
  transform: translateX(0);
  transition: transform 0.6s ease-in-out;
}

.container.right-panel-active .overlay {
  transform: translateX(50%);
}

.overlay-panel {
  position: absolute;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0 40px;
  text-align: center;
  top: 0;
  height: 100%;
  width: 50%;
  transform: translateX(0);
  transition: transform 0.6s ease-in-out;
}

.overlay-left {
  transform: translateX(-20%);
}

.container.right-panel-active .overlay-left {
  transform: translateX(0);
}

.overlay-right {
  right: 0;
  transform: translateX(0);
}

.container.right-panel-active .overlay-right {
  transform: translateX(20%);
}

@keyframes float {
  0% {
    top: 0px;
  }

  25% {
    top: 20px;
  }

  50% {
    top: 30px;
  }

  75% {
    top: 20px;
  }

  100% {
    top: 0px;
  }
}

@keyframes float-rev {
  0% {
    top: 3px;
  }

  25% {
    top: 20px;
  }

  50% {
    top: 0px;
  }

  75% {
    top: 20px;
  }

  100% {
    top: 3px;
  }
}
</style>
