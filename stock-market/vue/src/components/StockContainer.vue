<template>
	<div id="stock-container">
		<h2 v-show="onPortfolio">My Portfolio</h2>
		<div id="stocks">
			<stock-card v-for="(stock, index) in stocks" :key="index" v-bind="stock"
				@click.native="handleClick(stock.symbol)" v-model="buySellCard" :isActive="activeIndex === index" @onToggle="onToggle(index)"/>
		</div>
	</div>

</template>

<script>
import StockCard from './StockCard.vue'
export default {
	name: 'StockContainer',
	components: { StockCard },
	props: ['stocks', 'value', 'onPortfolio'],
	emits: ['cardClick'],
	data() {
		return {
			activeIndex: null
		}
	},
	methods: {
		handleClick(symbol) {
			this.$emit('cardClick', symbol)
		},
		onToggle(index) {
			if (this.activeIndex === index) {
				this.activeIndex = null;
			}
			else {
				this.activeIndex = index;
			}
		}
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


	border-radius: 20px;
	margin-bottom: 20%;
}

#stock-container {
	background-color: #023047;
	min-height: 500px;
	width: 100%;
	box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25),
		0 10px 10px rgba(0, 0, 0, 0.22);
}
h2 {
	display: flex;
	justify-content: center;
	color: #fb8500;
	padding-top: 10px;
	text-decoration: underline;
	font-weight: bold;
}
</style>