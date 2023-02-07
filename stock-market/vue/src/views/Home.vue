<template>
  <div class="home m-0 p-0 h ">
    <div class="card w-50 px-5 py-5">
      <div id="developers" class="container">
        <img src="../img/NyeinChan.jpg" />
        <img src="../img/NyeinChan.jpg" />
        <img src="../img/NyeinChan.jpg" />
      </div>
      <p class="text-center">Lorem ipsum dolor sit amet consectetur. Amet ut lorem consequat leo rhoncus
        gravida. Volutpat
        eleifend
        imperdiet in sed
        aliquam. Morbi sed et malesuada ultricies phasellus eget. Consequat eget dignissim dui at sagittis. Quisque quis
        mauris
        id dapibus. Ut purus aliquet convallis aliquet et nisl. Diam facilisis volutpat ornare.</p>
      <div id='button' class="text-center">
        <button id="login" @click="handleClick">Let's Go</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "home",
  methods: {
    handleClick() {
      this.$router.push({ name: 'registration' });
    }
  },
  created() {
    this.$store.commit("LOGOUT");
  }
}
</script>

<style>
div.home {
  background-image: url('https://i.gifer.com/9IeY.gif');
  background-size: cover;
  height: 100vh;
  display: flex;
  align-items: center;
}

div.home>div.card {
  background-color: #8ECAE6;
  margin: 0 auto;
  border: none;
}

div.card>div#developers {
  display: flex;
  justify-content: space-evenly;
}

div.card>div#developers>img {
  border-radius: 50%;
  width: 30%;
  padding: 20px;
}


div#button>button#login {
  height: 50px;
  width: 200px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  background: #FFB703;
  border: none;
  border-radius: 15px;
  color: white
}
</style>
