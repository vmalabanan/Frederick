<template>
  <div class="home">
    <div class="images-container">
      <img
        class="apple"
        src="../img/apple.png"
        alt="Apple logo"
      />
      <img class="fishbowl" src="../img/cutefish-fish.gif" alt="goldfish" />
      <img class="netflix" src="../img/netflix.png" alt="Netflix logo" />
    </div>
    <button class="btn btn-warning" @click="handleClick">Let's Go</button>
  </div>
  <!-- <div class="home m-0 p-0 h ">
		
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
				aliquam. Morbi sed et malesuada ultricies phasellus eget. Consequat eget dignissim dui at sagittis.
				Quisque quis
				mauris
				id dapibus. Ut purus aliquet convallis aliquet et nisl. Diam facilisis volutpat ornare.</p>
			<div id='button' class="text-center">
				<button id="login" @click="handleClick">Let's Go</button>
			</div>
		</div>
	</div> -->
</template>

<script>
export default {
  name: "home",
  methods: {
    handleClick() {
      this.$router.push({ name: "registration" });
    },
  },
  created() {
    this.$store.commit("LOGOUT");
  },
};
</script>

<style scoped>
.home {
  background-color: #04c2fc;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  /* justify-content: center; */
}

.images-container {
  display: flex;
  align-items: center;
  /* justify-content: center; */
}

.fishbowl {
  height: 70%;
}

.apple {
  height: 30%;
  margin-right: -7rem;
  z-index: 10;
}

.netflix {
  height: 30%;
  margin-left: -13rem;
}

.btn {
	margin-top: -12rem;
	width: 15rem;
	font-size: 1.25rem;
}
/* div.home {
  background-image: url('https://i.gifer.com/9IeY.gif'); */
/* background-size: cover;
  height: 100vh;
  display: flex;
  align-items: center;
}

div.home > div.card {
  background-color: #8ecae6;
  margin: 0 auto;
  border: none;
}

div.card > div#developers {
  display: flex;
  justify-content: space-evenly;
}

div.card > div#developers > img {
  border-radius: 50%;
  width: 30%;
  padding: 20px;
}

div#button > button#login {
  height: 50px;
  width: 200px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  background: #ffb703;
  border: none;
  border-radius: 15px;
  color: white;
} */
</style>
