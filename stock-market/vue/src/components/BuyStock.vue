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
                    <input type="number" id="qty" v-model="qty" min="1" oninput="this.value = Math.abs(this.value)">
                    <div class="price">${{ getTotalPrice }}</div>
                </div>

                <div class="buttons">
                    <button class="btn btn-lg cancel" @click.prevent="cancel">Cancel</button>
                    <button class="btn btn-lg confirm" @click.prevent="confirm()" :class="{disabled : !isValidTrade}">Confirm</button>
                </div>
                <p id="invalid" v-show="!isValidTrade">-- Invalid Trade --</p>
            </div>
        </div>
    </div>
</template>

<script>
import tradeService from "../services/TradeService"
export default {
    name: 'BuyStock',
    props: ["value"],
    data() {
        return {
            qty: 1
        }
    },
    methods: {
        cancel() {
            this.buySellCard.show = false
            this.qty = 1;
        },
        confirm() {
            const gameId = this.$route.params.id
            const date = new Date()
            const buySell = this.buySellCard.buySell ? "Buy" : "Sell"
            const price = this.stockPrice
            const symbol = this.buySellCard.symbol
            const trade = {
                tradeDate: date,
                sharePrice: price,
                numberOfShares: this.qty,
                tradeTypeDesc: buySell,
                tickerSymbol: symbol
            }
            tradeService.saveTrade(gameId, trade).then(response => {
                if (response.status == 200) {
                    alert("Trade Successful")
                    this.$store.commit("SET_CASH", response.data)
                }
                else {
                    alert("Trade Failed, Try Again")
                }
            })
            this.buySellCard.show = false
            this.qty = 1
        },

    },
    computed: {
        buySellCard: {
            get() {
                return this.value
            },
            set(value) {
                this.$emit('input', value)
            },
        },
        getTotalPrice() {
            const price = this.buySellCard.price * this.qty
            return price.toFixed(2)
        },
        stockPrice() {
            return this.buySellCard.price.toFixed(2)
        },
        isValidTrade() {
            const buySell = this.buySellCard.buySell
            if (buySell) {
                return this.$store.state.accountCash > this.getTotalPrice
            }
            //implement selling
            return true
        }
    }
}
</script>

<style scoped>
.card {
    width: 350px;
    height: 250px;
    background-color: #8ECAE6;
    border-radius: 25px;
    box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25),
        0 10px 10px rgba(0, 0, 0, 0.22);
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
    background-color: #FFB703;
    color: white;
}

.confirm {
    background-color: #FB8500;
    color: white;
}

button {
    border-radius: 20px;
}

.buttons {
    margin-top: 20px;
    display: flex;
    justify-content: space-evenly;
}

#invalid {
    text-align: center;
    font-weight: bold;
    color: red;
}
</style>