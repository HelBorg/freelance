import Vue from 'vue'
import App from './App'
import router from './router/index'

import BootstrapVue from 'bootstrap-vue'
import store from './store'
import crypt from '../node_modules/crypto-js'

import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap-vue/dist/bootstrap-vue.css"

Vue.use(BootstrapVue, crypt);
Vue.config.productionTip = false;
Vue.config.devtools = true;

new Vue({
  store,
  el: '#app',
  router,
  template: '<App/>',
  components: {
    App
  }
});
