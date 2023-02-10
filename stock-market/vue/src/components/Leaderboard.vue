<template>
  <div id="leaderboard">
    <ul class="list-group list-group-horizontal">
      <li class="list-group-item flex-fill leaderboard-title">Leaderboard</li>
    </ul>
    <ul class="list-group list-group-horizontal">
      <li class="list-group-item col-1 leaderboard-subtitle">#</li>
      <li class="list-group-item col-2 leaderboard-subtitle">Player</li>
      <li class="list-group-item col-3 leaderboard-subtitle">
        Portfolio Value
      </li>
    </ul>
    <ul class="list-group list-group-horizontal" v-for="(data, index) in this.leaderboardData" :key="index">
      <li class="list-group-item col-1 data">
        {{ index + 1 }}
      </li>
      <li class="list-group-item col-2 data" v-if="data[0] == $store.state.user.username" style="font-weight: bold">
        {{ data[0] + " (you)" }}
      </li>
      <li class="list-group-item col-2 data" v-else>
        {{ data[0] }}
      </li>
      <li class="list-group-item col-3 data">
        {{ getCashString(data[1]) }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "Leaderboard",
  props: ["gameId", "leaderboardData"],
  methods: {
    getCashString(cash) {
      let formatting_options = {
        style: "currency",
        currency: "USD",
        minimumFractionDigits: 2,
      };
      let dollarString = new Intl.NumberFormat("en-US", formatting_options);
      let cashString = dollarString.format(cash);
      return cashString;
    },
  },
};
</script>

<style scoped>
#leaderboard {
  flex-basis: 20%;
  background-color: #fff;
  /* border-radius: 20px; */
  border-radius: 0.5rem;

  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}

li {
  background: none;
}

ul:nth-child(even) {
  background: #3c4d74;
}

ul:nth-child(even) li {
  color: #fff;
}

.list-group {
  border-radius: 0;
}

.list-group-item {
  font-size: 1.15rem;
  border: none;
  border-radius: 0.5rem;
}

.leaderboard-title {
  text-align: center;
  font-size: 1.25rem;
  background-color: #fff;
  font-weight: 600;
}

.leaderboard-subtitle {
  font-weight: 600;
}

.col-1 {
  width: 10%;
}

.col-2 {
  width: 44%;
}

.col-3 {
  width: 46%;
}
</style>
