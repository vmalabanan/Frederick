<template>
    <div id="stock" class="card">
        <div class="card-body">
            <h4 class="card-title">
                <span id="stock-code">{{ code }}</span>
                <p class="stock-name">{{ name }}</p>
            </h4>
            <p class="stock-price">${{ mp }}</p>
            <p :class="this.change.startsWith('-') ? 'change negative' : 'change positive'">{{ getChange() }}</p>
        </div>
    </div>
</template>

<script>
export default {
    name: 'StockCard',
    props: ['code', 'mp', 'name', 'change'],
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
#stock {
    display: flex;
    width: 200px;
    height: 200px;
    box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25),
        0 10px 10px rgba(0, 0, 0, 0.22);
    border-radius: 25px;
}

.card-title {
    margin-bottom: 0px;
}

.card-body {
    display: flex;
    flex-direction: column;

}

.stock-name {
    color: #808080;
    margin-bottom: 0px;
    font-weight: 400;
}

.stock-price {
    font-style: normal;
    font-weight: 400;
    font-size: 36px;
}

.change {
    color: #808080;
    font-size: 25px;
    font-weight: 500;
}

.positive {
    color: #1FCC92;
}

.negative {
    color: #E54322
}
</style>