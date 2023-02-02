<template>
    <div class="card">
        <div class="card-body">
            <div class="card-title">
                <h2>{{ stock.code }}</h2>
                <h2>Buy Stock</h2>
            </div>
            <h4>${{ stock.mp }}</h4>
            <div class="qty-form">
                <div class="qty-labels">
                    <label for="qty">Shares</label>
                    <label class="cost" for="qty">Cost</label>
                </div>
                <input type="number" id="qty">
            </div>

            <button class="btn btn-lg">Cancel</button>
            <button class="btn btn-lg">Confirm</button>
        </div>
    </div>
</template>

<script>
export default {
    name: 'BuyStock',
    props: ['stock'],
    data() {
        return {
        }
    },
    methods: {
        getChange() {
            if (this.change.startsWith('-')) {
                return this.change;
            }
            return "+" + this.change;
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

.container {
    display: flex;
    flex-direction: column;

}

.card-title {
    display: flex;
    justify-content: space-between;
}
.positive {
    color: #1FCC92;
}

.negative {
    color: #E54322
}


</style>