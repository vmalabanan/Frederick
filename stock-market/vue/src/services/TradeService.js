//todo
import axios from 'axios';

export default {
    saveTrade(gameId, trade) {
        return axios.post("/trades/" + gameId, trade)
    }
}