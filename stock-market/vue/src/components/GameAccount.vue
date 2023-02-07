<template>
  <div id="account">
    <h4 class="labels">Account Value</h4>
    <p class="values">{{ getAccountValue }}</p>
    <h4 class="labels">ROI</h4>
    <p class="values">+ 7.2%</p>
    <h4 class="labels">Cash</h4>
    <p class="values">{{ getCash }}</p>
    <button class="btn btn-lg" @click="handleClick">
      {{ this.buttonText }}
    </button>
  </div>
</template>

<script>
import cashService from "../services/CashService";

export default {
  name: "GameAccount",
  data() {
    return {
      buttonText: "View Stocks",
      accountValue: 0
    };
  },
  methods: {
    handleClick() {
      //change router later
      if (this.buttonText == "View Stocks") {
        this.buttonText = "View Portfolio";
      } else {
        this.buttonText = "View Stocks";
      }
    },
    setCash() {
      const gameId = this.$route.params.id;
      cashService.getCashByGameId(gameId).then((response) => {
        this.$store.commit("SET_CASH", response.data);
      });
    },
    getCashString(cash) {
      let formatting_options = {
        style: "currency",
        currency: "USD",
        minimumFractionDigits: 2,
      };

      let dollarString = new Intl.NumberFormat("en-US", formatting_options);
      let cashString = dollarString.format(cash);
      return cashString
    },
    getROI() {

    },

  },
  computed: {
    getAccountValue() {
      let sum = 0
      const trades = this.$store.state.portfolio.trades
      const cards = this.$store.state.portfolio.cards
      
      trades.forEach(trade => {
        const symbol = trade.tickerSymbol
        const qty = trade.numberOfShares
        if (qty > 0) {
          const currentPrice = cards.find(card => card.symbol == symbol).price
          sum += (qty * currentPrice)
        }
      })
      const totalValue = sum + this.$store.state.accountCash
      return this.getCashString(totalValue)
    },
    getCash() {
      return this.getCashString(this.$store.state.accountCash)
    }
  },
  created() {
    this.setCash()
  },
};
</script>

<style scoped>
#account {
  flex-basis: 20%;
  height: auto;
  background-color: #246e93;
  display: flex;
  flex-direction: column;
  padding: 10px 20px;
  border-radius: 20px;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}

.labels {
  color: #ffb703;
}

.values {
  display: flex;
  justify-content: center;
  align-items: center;
  color: whitesmoke;
  background-color: #023047;
  height: 40px;
  border-radius: 20px;
}

button {
  margin-top: 40px;
  background-color: #fb8500;
  color: white;
  border-radius: 20px;
}

button:hover {
  background-color: #fb8500;
  color: white;
  border: 2px solid rgb(68, 68, 68);
}
</style>