<template>
    <div class="card">
        <div class="card-body">
            <div class="card-title">
                <h2>{{ symbol }}</h2>
                <h2>{{ getBuySell }}</h2>
            </div>
            <h4>${{ price }}</h4>
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
                    <button class="btn btn-lg cancel">Cancel</button>
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
    },
    computed: {
        getTotalPrice() {
            const price = this.price * this.qty
            return price.toFixed(2)
        },

        getBuySell() {
            if (this.buySell) {
                return "Buy Stocks"
            }
            return "Sell Stocks"
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