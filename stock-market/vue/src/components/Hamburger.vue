<template>
  <div>
    <div class="dropdown">
      <a @click="toggleShowMenu()"><i class="fa-solid fa-bars"></i></a>
      <ul class="list-group" v-show="showMenu">
        <li @click="menu()" class="list-group-item">Menu</li>
        <li @click="home()" class="list-group-item">Logout</li>
      </ul>
    </div>
  </div>
</template>

<script>

export default {
  name: "hamburger",
  props: ["gameName"],
  data() {
    return {
      showMenu: false,
    };
  },
  methods: {
    menu() {
      this.$router.push({ name: "menu" });
    },
    home() {
      this.$router.push({ name: "home" });
    },
    toggleShowMenu() {
      this.showMenu = !this.showMenu;
    },
  },
};
</script>

<style scoped>
a:hover {
  cursor: pointer;
}

.list-group {
  list-style: none;
  position: absolute;
  right: 0.05rem;
  font-size: 1rem;
  width: 10rem;
}

.list-group-item {
  background: #ffb703;
}

.list-group-item:hover {
  background: #fb8500;
  cursor: pointer;
}
</style>
