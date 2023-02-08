import axios from 'axios';


Date.prototype.toApiString = function () {
	let result = this.toISOString();
	return result.substring(0, result.indexOf("T"));
}


export default {
	//gets minimum last 6 days of data
	getHistoricalDailyDataBySymbol(fromDate, endDate, ...symbols) {
		let url = `/api/historical-price-full/${symbols.join(",")}?from=${fromDate.toApiString()}&to=${endDate.toApiString()}`
		return axios.get(url)
	},

	getHistoricalHourDataBySymbol(symbol) {
		let week = new Date();
		week.setDate(week.getDate() - 8)
		let url = `/api/historical-chart/1hour/${symbol}?from=${week.toApiString()}&to=${new Date().toApiString()}`
		return axios.get(url)
	},

	getHistoricalMinuteDataBySymbol(symbol) {
		let week = new Date();
		week.setDate(week.getDate() - 8)
		let url = `/api/historical-chart/1min/${symbol}?from=${week.toApiString()}&to=${new Date().toApiString()}`
		return axios.get(url)
	},

	getRealTimeStockPrice(...symbols) {
		let url = `/api/quote/${symbols.join(",")}`
		return axios.get(url)
	},

	searchTicker(symbol) {
		let url = `/api/search?query=${symbol}`
		return axios.get(url)
	}

}
