<template>
  <div id="stock" class="card" :class="{ active: isActive }">
    <div class="card-body">
      <h4 class="card-title">
        <div class="header-container">
          <span id="stock-code">{{ symbol }}</span>
          <span id="qty-owned" v-show="getQuantityOwned() > 0">
            {{ getQuantityOwned() }}</span
          >
        </div>
        <p class="stock-name" v-if="name.length > 20">{{ name.substring(0,20) }}</p>
        <p class="stock-name" v-else>{{ name }}</p>
      </h4>
      <p class="stock-price">${{ price.toFixed(2) }}</p>
      <p
        :class="
          this.changesPercentage < 0 ? 'change negative' : 'change positive'
        "
      >
        {{ getChange() }}%
      </p>
      <div class="buy-sell">
        <button class="btn" id="buy" @click.stop.prevent="setStockInfo(true)">
          Buy
        </button>
        <button class="btn" id="sell" @click.stop.prevent="setStockInfo(false)">
          Sell
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "StockCard",
  props: {
    symbol: { type: String, default: "" },
    name: { type: String, default: "" },
    price: { type: Number, default: 0 },
    changesPercentage: { type: Number, default: 0 },
    value: {},
    isActive: {},
  },
  emit: ["cardButtonClick"],
  data() {
    return {};
  },
  methods: {
    getChange() {
      const changesPercentage = this.changesPercentage.toFixed(2);
      if (changesPercentage.startsWith("-")) {
        return changesPercentage;
      }
      return "+" + changesPercentage;
    },
    setStockInfo(buy) {
      this.buySellCard = {
        show: true,
        price: this.price,
        symbol: this.symbol,
        buySell: buy,
      };
    },
    getQuantityOwned() {
      if (!this.$store.state.portfolio.symbols.includes(this.symbol)) {
        return 0;
      }
      const index = this.$store.state.portfolio.symbols.indexOf(this.symbol);
      const sharesAlreadyPurchased =
        this.$store.state.portfolio.trades[index].numberOfShares;
      return sharesAlreadyPurchased;
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
  created() {},
};
</script>

<style scoped>
#stock {
  display: flex;
  /* min-width: 200px; */
  min-width: 180px;

  /* min-height: 160px; */
  min-height: 144px;
  /* margin: 10px; */
  margin: 9px;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
  border-radius: 25px;
  cursor: pointer;
  border: none;
}

.card-title {
  margin-bottom: 0;
}

.card-body {
  display: flex;
  flex-direction: column;
}

.stock-name {
  color: #808080;
  margin-bottom: 0px;
  font-weight: 400;
  font-size: 0.9rem;
}

.stock-price {
  font-style: normal;
  font-weight: 400;
  /* font-size: 36px; */
  font-size: 1.85rem;

  margin-bottom: -0.25rem;
}

.change {
  color: #808080;
  /* font-size: 25px; */
  font-size: 22px;

  font-weight: 500;
}

.positive {
  color: #1fcc92;
}

.negative {
  color: #e54322;
}

.buy-sell {
  display: flex;
  justify-content: left;
  gap: 20px;
  margin-top: -10px;
}

#buy,
#sell {
  border-radius: 0.25rem;
  font-size: 0.9rem;
}

#buy {
  background-color: #1fcc92;
}

#sell {
  background-color: #e54322;
  color: white;
}

buy-stock {
  position: absolute;
  top: 50%;
  left: 50%;
}

.active {
  background-color: #d0dbf7;
}

.header-container {
  display: flex;
  justify-content: space-between;
}

#qty-owned {
  font-size: 15px;
  border-radius: 5rem;
  width: 3rem;
  font-weight: 400;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  background-color: #3c4d74;
  padding: 5px;
}
</style>
