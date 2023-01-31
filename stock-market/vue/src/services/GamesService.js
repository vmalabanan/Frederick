import axios from 'axios';

export default {

    getGames(user) {
        return axios.get('/games', user)
    },

}