import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import axios from 'axios'
import VueAxios from 'vue-axios'
import VueCookies from 'vue-cookies'

// 使用Vue.use的方式装载组件可以不破坏Vue的原型属性
// axios.defaults.baseURL = 'http://127.0.0.1:9001/'
axios.defaults.baseURL = 'http://localhost:9001/'
Vue.use(VueAxios, axios)

// 使用vue.use添加vue-cookies
Vue.use(VueCookies)

Vue.config.productionTip = false

Vue.axios.interceptors.request.use(
  function (config) {
    if (Vue.$cookies.isKey('accessToken')) {
      config.headers.Authorization = 'Bearer ' + Vue.$cookies.get('accessToken')
    }
    return config
  },
  function (error) {
    return Promise.reject(error)
  }
)

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
