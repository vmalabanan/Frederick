import axios from 'axios';

export default {

    getGames() {
        return axios.get('/games')
    },

    add(game) {
        return axios.post('/games', game)
    }
}