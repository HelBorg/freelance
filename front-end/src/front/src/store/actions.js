import fetch from 'isomorphic-fetch'
import * as types from './mutation-types'
import router from '../router'

const login = ({ commit }, creds) => {
  commit(types.LOGIN) // show spinner
  return fetch('/api/v1/singin', {
    method: 'POST',
    headers: {
      'Content-Type' : 'application/json'
    },
    body: JSON.stringify(creds)
  })
}

const logout = ({ commit }) => {
  commit(types.LOGOUT)
  localStorage.removeItem('JWT')
  router.push('/login')
}

export default {
  [types.LOGIN]: login,
  [types.LOGOUT]: logout,
}

