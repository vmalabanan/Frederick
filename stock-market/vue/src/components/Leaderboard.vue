<template>
  <div id="leaderboard">
    <ul class="list-group list-group-horizontal">
      <li class="list-group-item flex-fill leaderboard-title">Leaderboard</li>
    </ul>
    <ul class="list-group list-group-horizontal">
      <li class="list-group-item col-1">#</li>
      <li class="list-group-item col-2">Player</li>
      <li class="list-group-item col-3">Portfolio Value</li>
    </ul>
    <ul class="list-group list-group-horizontal" v-for="(data, index) in this.leaderboardData" :key="index">
      <li class="list-group-item col-1 data">
        {{ index + 1 }}
      </li>
      <li class="list-group-item col-2 data">
        {{ data[0] }}
      </li>

      <li class="list-group-item col-3 data">
        ${{ data[1] }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "Leaderboard",
  props: ["gameId", "leaderboardData"],
};
</script>

<style scoped>
#leaderboard {
  flex-basis: 20%;
  background-color: #ffb703;
  border-radius: 20px;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}

li {
  background: none;
}

ul:nth-child(even) {
  background: #fb8500;
}

.list-group-item {
  font-size: 1.15rem;
}

.leaderboard-title {
  text-align: center;
  font-size: 2rem;
  background-color: #fb8500;
}

.col-1 {
  width: 10%;
}

.col-2 {
  width: 46%;
}

.col-3 {
  width: 44%;
}
</style>
