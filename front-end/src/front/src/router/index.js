import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/homePage/Home'
import SingUp from '../components/SingUp'
import Task from '../components/Task/Task'
import Login from '../components/Login'
import store from '../store'
import * as types from '../store/mutation-types'
import User from "../components/User"
import Tasks from "../components/tasks/Tasks"
import Users from "../components/Users";

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

Vue.use(Router)

const router = new Router({
  mode: 'hash',
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
      name: 'home',
      component: Home,
      beforeEnter: requireAuth
    },
    {
      path: '/tasks/:pageName',
      name: 'tasks',
      component: Tasks,
    },
    {
      path: '/task/:id',
      name: 'Task',
      component: Task,

    },
    {
      path: '/user/:id',
      name: 'User',
      component: User
    },{
      path: '/users',
      name: 'Users',
      component: Users
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
