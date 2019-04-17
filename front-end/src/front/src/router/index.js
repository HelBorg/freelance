import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import SingUp from '@/components/SingUp'
import Task from '@/components/Task'
import Login from '@/components/Login'
import store from '../store'
import * as types from '../store/mutation-types'
import axios from '../../axios/index'


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

Vue.use(Router,axios)

const router = new Router({
  routes: [
    {
      path: '/',
      alias: '/login',
      name: 'Login',
      component: Login,
      beforeEnter: hasToken
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      beforeEnter: requireAuth
    },
    {
      path: '/task/new',
      name: 'Task',
      component: Task,
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
    }
  ]
})
export default router
