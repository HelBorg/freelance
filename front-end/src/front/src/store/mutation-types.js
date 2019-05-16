export const LOGIN = 'LOGIN'
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS'
export const LOGIN_WRONG_CREDENTIALS = 'LOGIN_WRONG_CREDENTIALS'
export const LOGIN_ERROR = 'LOGIN_ERROR'
export const LOGOUT = 'LOGOUT'
export const HEADER =
  {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.getItem('JWT')
  }

