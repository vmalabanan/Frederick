import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if (currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    selectedUsers: [],
    invitedGames: [],
    acceptedGames: [],
    showBuyCard: false,
    stockInfo: {
    },
    currentStock: 'portfolio',
    registrationStatus: {
      isRPanelActive: false,
      isRegSuccessful: false,
    },
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user', JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    SWITCH_PANEL(state) {
      state.registrationStatus.isRPanelActive = !state.registrationStatus.isRPanelActive;
    },
    REG_SUCCESSFUL(state) {
      state.registrationStatus.isRegSuccessful = true;
    },
    SET_SELECTED_USERS(state, users) {
      state.selectedUsers = users;
    },
    SET_INVITED_GAMES(state, games) {
      state.invitedGames = games;
    },
    SET_ACCEPTED_GAMES(state, games) {
      state.acceptedGames = games;
    },
    TOGGLE_BUY_SELL_FORM(state) {
      state.showBuyCard = !state.showBuyCard
    },
    SET_STOCK_INFO(state, stockInfo) {
      state.stockInfo = stockInfo
    }
  }
})
