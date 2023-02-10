<template>
  <div id="stock-container">
    <h2 v-show="onPortfolio">My Portfolio</h2>
    <div id="stocks">
      <stock-card
        v-for="(stock, index) in stocks"
        :key="index"
        v-bind="stock"
        @click.native="handleClick(stock.symbol)"
        v-model="buySellCard"
        :isActive="graphLabel === stock.symbol"
      />
    </div>
  </div>
</template>

<script>
import StockCard from "./StockCard.vue";
export default {
  name: "StockContainer",
  components: { StockCard },
  props: ["stocks", "value", "onPortfolio", "graphLabel"],
  emits: ["cardClick"],
  data() {
    return {
      activeIndex: null,
    };
  },
  methods: {
    handleClick(symbol) {
      this.$emit("cardClick", symbol);
    },
  },
  computed: {
    buySellCard: {
      get() {
        return this.value;
      },
      set(value) {
        this.$emit("input", value);
      },
    },
  },
};
</script>

<style scoped>
#stocks {
  display: flex;
  flex-wrap: wrap;
  align-content: flex-start;
  border-radius: 0.5rem;
  margin-bottom: 20%;
  height: 40vh;
  overflow-y: auto;
  align-items: center;
  padding-left: 1.9rem;
}

#stock-container {
  background-color: #fff;

  min-height: 46vh;
  width: 62.5vw;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
  border-radius: 0.5rem;
}
h2 {
  display: flex;
  justify-content: center;
  color: #000;
  padding-top: 10px;
  font-weight: bold;
}
</style>
