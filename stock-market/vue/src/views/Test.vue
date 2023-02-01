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
                mp: '145.93',
                change: '1.00%',
            }
        }
    }
}
</script>

<style>

</style>