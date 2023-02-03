<template>
    <div class="card">
        <div class="card-body">
            <div class="card-title">
                <h2>{{ stockSymbol }}</h2>
                <h2>{{ getBuySell }}</h2>
            </div>
            <h4>${{ stockPrice }}</h4>
            <div class="qty-form">
                <div class="qty-labels">
                    <label for="qty">Shares</label>
                    <label class="cost" for="qty">Cost</label>
                </div>
                <div class="shares">
                    <input type="number" id="qty" v-model="qty" min="0" oninput="this.value = Math.abs(this.value)">
                    <div class="price">${{ getTotalPrice }}</div>
                </div>
                
                <div class="buttons">
                    <button class="btn btn-lg cancel" @click.prevent="cancel()">Cancel</button>
                    <button class="btn btn-lg confirm">Confirm</button>
                </div>

            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'BuyStock',
	props: ["price", 'symbol', "buySell"],
    data() {
        return {
            qty: "",
            showForm: true
        }
    },
    methods: {
        cancel() {
            this.$store.commit("TOGGLE_BUY_SELL_FORM")
        }
    },
    computed: {
        getTotalPrice() {
            const price = this.$store.state.stockInfo.price * this.qty
            return price.toFixed(2)
        },

        getBuySell() {
            if (this.$store.state.stockInfo.buy) {
                return "Buy Stocks"
            }
            return "Sell Stocks"
        },

        stockPrice() {
            return this.$store.state.stockInfo.price.toFixed(2)
        },

        stockSymbol() {
            return this.$store.state.stockInfo.symbol
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
    position:fixed;
	top:50%;
	left:50%;
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

</style>