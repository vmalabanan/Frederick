import axios from 'axios';

export default {
    add(game) {
        return axios.post('/games', game)
    },

    getGames() {
        return axios.get('/games')
    },

    getInvitedGames() {
        return axios.get('/games/invited')
    },

    getAcceptedGames() {
        return axios.get('/games/accepted')
    },

    getRejectedGames() {
        return axios.get('/games/rejected')
    },

    updateInvitationStatus(invitation) {
        return axios.put('/games', invitation);
    },


}