<template>
	<div id="stock" class="card">
		<div class="card-body">
			<h4 class="card-title">
				<span id="stock-code">{{ symbol }}</span>
				<p class="stock-name">{{ name }}</p>
			</h4>
			<p class="stock-price">${{ price.toFixed(2) }}</p>
			<p :class="this.changesPercentage < 0 ? 'change negative' : 'change positive'">{{ getChange() }}%
			</p>
			<div class="buy-sell">
				<button class="btn" id="buy" @click.prevent="setStockInfo(true)">Buy</button>
				<button class="btn" id="sell" @click.prevent="setStockInfo(false)">Sell</button>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	name: 'StockCard',
	props: {
		symbol: { type: String, default: "" },
		name: { type: String, default: "" },
		price: { type: Number, default: 0 },
		changesPercentage: { type: Number, default: 0 },
	},
	data() {
		return {
		}
	},
	methods: {
		getChange() {
			const changesPercentage = this.changesPercentage.toFixed(2)
			if (changesPercentage.startsWith('-')) {
				return changesPercentage;
			}
			return "+" + changesPercentage;
		},
		setStockInfo(buy) {
			let stockInfo = {
				price: this.price,
				symbol: this.symbol,
				buy: buy
			}
			this.$store.commit("SET_STOCK_INFO", stockInfo)
			this.$store.commit("TOGGLE_BUY_SELL_FORM")
		}
	},
	computed: {

	}
}
</script>

<style scoped>
#stock {
	display: flex;
	min-width: 200px;
	min-height: 160px;
	margin: 10px;
	box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25),
		0 10px 10px rgba(0, 0, 0, 0.22);
	border-radius: 25px;
	cursor: pointer;
}

.card-title {
	margin-bottom: 0;
}

.card-body {
	display: flex;
	flex-direction: column;

}

.stock-name {
	color: #808080;
	margin-bottom: 0px;
	font-weight: 400;
	font-size: 16px;
}

.stock-price {
	font-style: normal;
	font-weight: 400;
	font-size: 36px;
	margin-bottom: 0px;
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

.buy-sell {
	display: flex;
	justify-content: left;
	gap: 20px;
	margin-top: -10px;
}

#buy, #sell {
	border-radius: 10px;
}

#buy {
	background-color: #1FCC92;
}

#sell {
	background-color: #E54322;
	color: white;
}

buy-stock {
	position: absolute;
	top: 50%;
	left: 50%;
}
</style>