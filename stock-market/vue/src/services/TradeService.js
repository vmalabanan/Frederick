//todo
import axios from 'axios';

export default {
    saveTrade(gameId, trade) {
        return axios.post("/trades/" + gameId, trade)
    },

    getPortfolio(gameId) {
        return axios.get("/trades/" + gameId)
    },

    getPortfolioAllPlayers(gameId) {
        return axios.get("/trades" + gameId + "/all")
    },

    getPortfolioHistory(gameId) {
        return axios.get("/trades" + gameId + "/history")
    },

    getPortfolioHistoryAllPlayers(gameId) {
        return axios.get("/trades" + gameId + "/history/all")
    }
}