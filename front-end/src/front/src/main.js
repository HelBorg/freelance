import Vue from 'vue'
import App from './App'
import router from './router/index'

import BootstrapVue from 'bootstrap-vue'
import store from './store'

import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap-vue/dist/bootstrap-vue.css"

Vue.use(BootstrapVue, router)
// Vue.config.productionTip = false

new Vue({
  store,
  el: '#app',
  router,
  template: '<App/>',
  components: {
    App
  }
});
