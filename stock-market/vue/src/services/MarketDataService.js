import axios from 'axios';

const http = axios.create({
	baseURL: "https://financialmodelingprep.com/api/v3/"
});

const API_KEY = "apikey=4694ab156015fcae69356511ac9a8a36"
export default {

	//gets minimum last 6 days of data
	getHistoricalDailyDataBySymbol(symbol) {
		let url = "historical-price-full/" + symbol + "?from=";
		let today = new Date();
		let week = new Date();
		week.setDate(week.getDate() - 8)
		let todayString = today.toISOString()
		let delimiter = todayString.indexOf("T")
		const endDate = todayString.substring(0, delimiter)
		let fromString = week.toISOString()
		delimiter = fromString.indexOf("T")
		const fromDate = fromString.substring(0, delimiter)
		url += fromDate + "&to=" + endDate + "&" + API_KEY
		return http.get(url)
	},

    getHistoricalHourDataBySymbol(symbol) {
		let url = "historical-chart/1hour/" + symbol + "?from=";
		let today = new Date();
		let week = new Date();
		week.setDate(week.getDate() - 8)
		let todayString = today.toISOString()
		let delimiter = todayString.indexOf("T")
		const endDate = todayString.substring(0, delimiter)
		let fromString = week.toISOString()
		delimiter = fromString.indexOf("T")
		const fromDate = fromString.substring(0, delimiter)
		url += fromDate + "&to=" + endDate + "&" + API_KEY
		return http.get(url)
	},

    getHistoricalMinuteDataBySymbol(symbol) {
		let url = "historical-chart/1min/" + symbol + "?from=";
		let today = new Date();
		let week = new Date();
		week.setDate(week.getDate() - 8)
		let todayString = today.toISOString()
		let delimiter = todayString.indexOf("T")
		const endDate = todayString.substring(0, delimiter)
		let fromString = week.toISOString()
		delimiter = fromString.indexOf("T")
		const fromDate = fromString.substring(0, delimiter)
		url += fromDate + "&to=" + endDate + "&" + API_KEY
		return http.get(url)
	},

	getRealTimeStockPrice(...symbols) {
		let url = "quote/"
		symbols.forEach(symbol => {
			url += symbol + ","
		})
		url += "?" + API_KEY
		return http.get(url)
	},

	searchTicker(symbol) {
		let url = "search?query=" + symbol + "&limit=5&exchange=NASDAQ&" + API_KEY
		return http.get(url)
	}

}
