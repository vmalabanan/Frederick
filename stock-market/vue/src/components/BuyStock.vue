<template>
  <div class="card">
    <div class="card-body">
      <div class="card-title">
        <h2>{{ buySellCard.symbol }}</h2>
        <h2>{{ buySellCard.buySell ? "Buy Stocks" : "Sell Stocks" }}</h2>
      </div>
      <h4>${{ stockPrice }}</h4>
      <div class="qty-form">
        <div class="qty-labels">
          <label for="qty">Shares</label>
          <label class="cost" for="qty">Cost</label>
        </div>
        <div class="shares">
          <input
            type="number"
            id="qty"
            v-model="qty"
            min="1"
            oninput="this.value = Math.abs(this.value)"
          />
          <div class="price">${{ getTotalPrice }}</div>
        </div>

        <div class="buttons">
          <button class="btn btn-lg cancel" @click.prevent="cancel">
            Cancel
          </button>
          <button
            class="btn btn-lg confirm"
            @click.prevent="confirm()"
            :class="{ disabled: !isValidTrade }"
          >
            Confirm
          </button>
        </div>
        <p id="invalid" v-show="!isValidTrade">-- Invalid Trade --</p>
      </div>
    </div>
  </div>
</template>

<script>
import tradeService from "../services/TradeService";
export default {
  name: "BuyStock",
  props: ["value"],
  data() {
    return {
      qty: 1,
    };
  },
  methods: {
    cancel() {
      this.buySellCard.show = false;
      this.qty = 1;
    },
    confirm() {
      const gameId = this.$route.params.id;
      const buySell = this.buySellCard.buySell ? "Buy" : "Sell";
      const price = this.stockPrice;
      const symbol = this.buySellCard.symbol;
      const trade = {
        sharePrice: price,
        numberOfShares: this.qty,
        tradeTypeDesc: buySell,
        tickerSymbol: symbol,
      };
      tradeService.saveTrade(gameId, trade).then((response) => {
        if (response.status == 200) {
          alert("Trade Successful");
          this.$store.commit("SET_CASH", response.data.cash);
          const symbols = response.data.stocks.map(
            (stock) => stock.tickerSymbol
          );
          this.$store.commit("SET_PORTFOLIO_SYMBOLS", symbols);
          const trades = response.data.stocks;
          this.$store.commit("SET_PORTFOLIO_TRADES", trades);
        } else {
          alert("Trade Failed, Try Again");
        }
      });
      this.buySellCard.show = false;
      this.qty = 1;
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
    getTotalPrice() {
      const price = this.buySellCard.price * this.qty;
      return price.toFixed(2);
    },
    stockPrice() {
      return this.buySellCard.price.toFixed(2);
    },
    isValidTrade() {
      const buySell = this.buySellCard.buySell;
      if (buySell) {
        return this.$store.state.accountCash > this.getTotalPrice;
      } else {
        const symbol = this.buySellCard.symbol;
        if (!this.$store.state.portfolio.symbols.includes(symbol)) {
          return false;
        }
        const index = this.$store.state.portfolio.symbols.indexOf(symbol);
        const sharesAlreadyPurchased =
          this.$store.state.portfolio.trades[index].numberOfShares;
        return this.qty <= sharesAlreadyPurchased;
      }
    },
  },
};
</script>

<style scoped>
.card {
  width: 350px;
  height: 250px;
  background-color: #3c4d74;
  color: #fff;
  /* border-radius: 25px; */
  border-radius: 0.5rem;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
  position: fixed;
  top: 50%;
  left: 50%;
  z-index: 1;
  -webkit-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}

.card-title {
  display: flex;
  justify-content: space-between;
}

.qty-labels {
  display: flex;
  justify-content: space-between;
}

.qty-form {
  display: flex;
  flex-direction: column;
}

input {
  border-radius: 20px;
  width: 100%;
}

.price {
  margin-top: -27px;
  margin-right: 20px;
  text-align: right;
  color: rgb(172, 171, 171);
}

.cancel {
  background-color: #ffb703;
  color: white;
}

.confirm {
  background-color: #fb8500;
  color: white;
}

button {
  /* border-radius: 20px; */
  border-radius: 0.5rem;
}

.buttons {
  margin-top: 20px;
  display: flex;
  justify-content: space-evenly;
}

.cancel {
  background-color: #8892ab;
  border: none;
  border-radius: 0.5rem;
  color: white;
}

.cancel:hover {
  background-color: #656e8e;
  color: white;
}

.confirm {
  color: white;
  background-color: #223e7e;
  border: none;
  border-radius: 0.5rem;
}

.confirm:hover {
  background-color: #072b80;
  color: white;
}

#invalid {
  text-align: center;
  font-weight: bold;
  color: red;
}
</style>
