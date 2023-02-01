<template>
    <div>
        <StockCard v-bind='stock' />
    </div>
</template>

<script>
import StockCard from '../components/StockCard.vue';
export default {
    components: { StockCard },
    data() {
        return {
            stock: {
                code: 'AAPL',
                name: 'Apple Inc',
                previousClose: 140,
                mp: 145.93,
                change: '1.00%',
            }
        }
    },
    created() {
        setInterval(() => {
            this.stock.mp = this.getRandom(130, 150).toFixed(2);
            this.stock.change = this.getChangePercent(this.stock.mp, this.stock.previousClose)
            console.log(this.stock.change);
        }, 1 * 1000)
    },
    methods: {
        getRandom(min, max) {
            return Math.random() * (max - min) + min;
        },
        getChangePercent(price, previousClose) {
            let change = price - previousClose;
            const changePercent = change / previousClose;
            return changePercent.toFixed(2) + "%"

        }
    }
}
</script>

<style>

</style>