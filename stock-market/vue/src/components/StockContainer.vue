<template>
	<div id="stocks">
		<stock-card v-for="(stock, index) in stocks" :key="index" v-bind="stock"
			@click.native="handleChange(stock.symbol)" />
	</div>
</template>

<script>
import StockCard from './StockCard.vue'
export default {
	name: 'StockContainer',
	components: { StockCard },
	props: ['stocks'],
	emits: ['currentChanged'],
	data() {
		return {
		}
	},
	methods: {
		handleChange(symbol) {
			this.$emit('currentChanged', symbol)
		}

	}
}
</script>

<style scoped>
#stocks {
	display: flex;
	flex-wrap: wrap;
	align-content: flex-start;
	background-color: #023047;
	min-height: 500px;
	width: 100%;
	box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25),
		0 10px 10px rgba(0, 0, 0, 0.22);
	border-radius: 20px;
	margin-bottom: 20%;
}
</style>