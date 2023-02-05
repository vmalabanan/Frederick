<template>
	<div id="stocks">
		<stock-card v-for="(stock, index) in stocks" :key="index" v-bind="stock"
			@click.native="handleClick(stock.symbol)" v-model="buySellCard" />
	</div>
</template>

<script>
import StockCard from './StockCard.vue'
export default {
	name: 'StockContainer',
	components: { StockCard },
	props: ['stocks', 'value'],
	emits: ['cardClick'],
	data() {
		return {
		}
	},
	methods: {
		handleClick(symbol) {
			this.$emit('cardClick', symbol)
		},
	},
	computed: {
		buySellCard: {
			get() {
				return this.value
			},
			set(value) {
				this.$emit('input', value)
			}
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