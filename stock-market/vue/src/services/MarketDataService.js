import axios from 'axios';
const http = axios.create({
	baseURL: "https://financialmodelingprep.com/api/v3/"
});

const API_KEY = "apikey=4694ab156015fcae69356511ac9a8a36"

Date.prototype.toApiString = function () {
	let result = this.toISOString();
	return result.substring(0, result.indexOf("T"));
}


export default {
	//gets minimum last 6 days of data
	getHistoricalDailyDataBySymbol(fromDate, endDate, ...symbols) {
		let url = `historical-price-full/${symbols.join(",")}?from=${fromDate.toApiString()}&to=${endDate.toApiString()}&${API_KEY}`
		return http.get(url)
	},

	getHistoricalHourDataBySymbol(symbol) {
		let week = new Date();
		week.setDate(week.getDate() - 8)
		let url = `historical-chart/1hour/${symbol}?from=${week.toApiString()}&to=${new Date().toApiString()}&${API_KEY}`
		return http.get(url)
	},

	getHistoricalMinuteDataBySymbol(symbol) {
		let week = new Date();
		week.setDate(week.getDate() - 8)
		let url = `historical-chart/1min/${symbol}?from=${week.toApiString()}&to=${new Date().toApiString()}&${API_KEY}`
		return http.get(url)
	},

	getRealTimeStockPrice(...symbols) {
		let url = `quote/${symbols.join(",")}?${API_KEY}`
		return http.get(url)
	},

	searchTicker(symbol) {
		let url = `search?query=${symbol}&limit=5&exchange=NASDAQ&${API_KEY}`
		return http.get(url)
	}

}
