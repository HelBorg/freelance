import Vue from 'vue'
import Router from 'vue-router'
import Vuex from 'vuex'
import axios from 'axios'
import Home from '@/components/Home'
import SingUp from '@/components/SingUp'
import Task from '@/components/Task'


Vue.use(Router, Vuex, axios)

export default new Router({
  mode: 'history', //URL不顯示#字號
  routes: [{
    path: '/index',
    name: 'index',
    component: Home
  },
    {
      path: '/task',
      name: 'task',
      component: Task,
    },
    {
    path: '/singup',
    name: 'singup',
    component: SingUp,
  },
    {
    path: '*',
    redirect: '/index'
    // name: 'error',
    // component: ErrorPage
  }]
})
