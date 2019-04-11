import Vue from 'vue'
import Router from 'vue-router'
import Vuex from 'vuex'
import axios from 'axios'
import VueAxios from 'vue-axios'
import Home from '@/components/Home'
import SingUp from '@/components/SingUp'
import Task from '@/components/Task'
import Login from '@/components/Login'

const hasToken = (to, from, next) => {
  const token = localStorage.getItem('JWT')
  const username = localStorage.getItem('username')
  if (token) {
    store.commit(types.LOGIN_SUCCESS, { token, username })
    router.push('/home')
  } else {
    next()
  }
}

const requireAuth = (to, from, next) => {
  if (store.getters.isLoggedIn) {
    next()
  } else {
    router.push('/')
  }
}

Vue.use(Router, Vuex, axios)

export default new Router({
  mode: 'history',
  routes: [{
    path: '/',
    name: 'login',
    component: Login,
    beforeEnter: hasToken

  },
    {
      path: '/task',
      name: 'task',
      component: Task
    },
    {
      path: '/home',
      name: 'home',
      component: Home,
      beforeEnter: requireAuth

    },
    {
    path: '/singup',
    name: 'singup',
    component: SingUp
  },
    {
    path: '*',
    redirect: '/login'
    // name: 'error',
    // component: ErrorPage
  }]
})
