import axios from 'axios';

export default {

    getGames(user) {
        return axios.get('/games', user)
    },

    add(game) {
        return axios.post('/games', game)
    }

}