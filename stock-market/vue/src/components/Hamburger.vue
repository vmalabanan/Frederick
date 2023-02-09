<template>
  <div>
    <div class="dropdown">
      <a @click="toggleShowMenu()"><i class="fa-solid fa-bars"></i></a>
      <ul class="list-group" v-show="showMenu">
        <li
          v-for="(route, index) in routeNames"
          :key="index"
          @click="handleClick(route)"
          class="list-group-item"
        >
          {{ route === "home" ? "logout" : route }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "hamburger",
  props: ["routeNames"],
  data() {
    return {
      showMenu: false,
    };
  },
  methods: {
    handleClick(routeName) {
      this.$router.push({ name: routeName });
    },

    toggleShowMenu() {
      this.showMenu = !this.showMenu;
    },
  },
};
</script>

<style scoped>
.dropdown {
  position: fixed;
  right: 2rem;
  margin-top: 0.75rem;
  font-size: 2.5rem;
}

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
  border: none;
}

.list-group-item:hover {
  background: #fb8500;
  cursor: pointer;
}
</style>
