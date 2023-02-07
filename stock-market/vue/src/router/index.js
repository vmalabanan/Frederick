
import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Logout from '../views/Logout.vue'
import Registration from '../views/Registration'
import InvitationsList from '../views/InvitationsList.vue'
import store from '../store/index'
import Menu from '../views/Menu.vue'
import CreateGame from '../views/CreateGame.vue'
import Portfolio from '../views/Portfolio.vue'
import Users from '../components/Users.vue'
import Test from '../views/Test.vue'
import TestLobby from '../views/TestLobby.vue'

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/registration",
      name: "registration",
      component: Registration,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/menu",
      name: "menu",
      component: Menu,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/create-game",
      name: "createGame",
      component: CreateGame,
      meta: {
        requiresAuth: true
      }
    },

    {
      path: "/games/invited",
      name: "invitationsList",
      component: InvitationsList,
      meta: {
        requiresAuth: true
      }
    },

    {
      path: "/games/:id",
      name: "portfolio",
      component: Portfolio,
      meta: {
        requiresAuth: false
      }
    },

    //  do I need the users path here?
    {
      path: "/users",
      name: "users",
      component: Users,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/test",
      name: "test",
      component: Test,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/test-lobby/:gameId",
      name: "test-lobby ",
      component: TestLobby,
      meta: {
        requiresAuth: false
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;