import Vue from 'vue'
import Vuex from 'vuex'
import actions from './actions'
import * as getters from './getters'
import mutations from './mutations'
import axios from 'axios'

Vue.use(Vuex,axios)

const state = {
  auth: {
    isLoggedIn: false,
    pending: false,
    token: null,
    username: null
  }
}

const options = {
  state,
  mutations,
  actions,
  getters
}

const store = new Vuex.Store(options)

export default store
