//todo
import axios from 'axios';

export default {
    getCashByGameId(gameId) {
        return axios.get("/cash/" + gameId)
    }
}