import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 获取部分请求需要用到的认证，目前是access token
    accessToken: '',
    refreshToken: '',
    // 代表用户自身，最终不一定会以这种方式呈现
    user: {
      id: null,
      username: '',
      password: '',
      enabled: true,
      firstName: '',
      lastName: '',
      gender: 0,
      phone: '',
      email: '',
      icon: '',
      location: '',
      birthday: new Date().toISOString().substr(0, 10),
      joinedDate: new Date().toISOString().substr(0, 10),
      // 和用户相关的一些链接，比如用户自身的描述
      _links: []
    }
  },
  mutations: {
    // 初始化用户
    initUser (state) {
      state.user = {
        id: null,
        username: '',
        password: '',
        enabled: true,
        firstName: '',
        lastName: '',
        gender: 0,
        phone: '',
        email: '',
        icon: '',
        location: '',
        birthday: new Date().toISOString().substr(0, 10),
        joinedDate: new Date().toISOString().substr(0, 10),
        // 和用户相关的一些链接，比如用户自身的描述
        _links: []
      }
    },
    // 登录后设置user
    setUser (state, user) {
      // 设置用户信息
      state.user = user
      // 将用户生日修改为正确格式
      state.user.birthday = state.user.birthday == null ? null : state.user.birthday.substr(0, 10)
      // 将user存储在cookies中
      Vue.$cookies.set('user', state.user, 7200)
    },
    // 注销后清除user
    removeUser (state) {
      this.commit('initUser')
      // 清除cookies
      if (Vue.$cookies.isKey('user')) {
        Vue.$cookies.remove('user')
      }
    },
    // 初始化token
    initToken (state) {
      state.accessToken = ''
      state.refreshTOken = ''
    },
    // 设置token
    setToken (state, data) {
      state.accessToken = data.access_token
      state.refreshToken = data.refresh_token
      Vue.$cookies.set('accessToken', state.accessToken, 7200)
      Vue.$cookies.set('refreshToken', state.refreshToken, 86400)
    },
    // 清除token
    removeToken (state) {
      this.commit('initToken')
      // 清除缓存
      if (Vue.$cookies.isKey('accessToken')) {
        Vue.$cookies.remove('accessToken')
      }
      if (Vue.$cookies.isKey('refreshToken')) {
        Vue.$cookies.remove('refreshToken')
      }
    },
    // 页面加载时获取用户
    loadUser (state) {
      if (Vue.$cookies.isKey('user')) {
        state.user = Vue.$cookies.get('user')
        // 同时获取accessToken和refreshToken
        if (Vue.$cookies.isKey('accessToken')) {
          state.accessToken = Vue.$cookies.get('accessToken')
        }
        if (Vue.$cookies.isKey('refreshToken')) {
          state.refreshToken = Vue.$cookies.get('refreshToken')
        }
      } else if (Vue.$cookies.isKey('refreshToken')) {
        // access token过期，但是可以刷新token
        const refreshToken = Vue.$cookies.get('refreshToken')
        const qs = require('qs')
        const data = qs.stringify({
          refresh_token: refreshToken,
          client_id: 'bookstore',
          client_secret: '123456',
          grant_type: 'refresh_token'
        })
        Vue.axios.post('oauth/token', data)
          .then(response => {
            // 成功刷新令牌，调用checkAccessToken获取用户信息
            this.commit('setToken', response.data)
            this.commit('checkAccessToken', response.data.access_token)
          })
      } else {
        this.commit('initUser')
      }
    },
    checkAccessToken (state, accessToken) {
      // 首先检查access_token的有效性
      Vue.axios.get('oauth/check_token', {
        params: {
          token: accessToken
        }
      }).then(response => {
        // 请求成功，判断active是否为true，获取username
        if (response.data.active) {
          const username = response.data.user_name
          // 继续发送请求，获取具体用户信息
          Vue.axios.get('api/users/search/findByUsername', {
            params: {
              username: username
            }
          }).then(response => {
            this.commit('setUser', response.data)
          })
        }
      })
    }
  },
  actions: {
    // 设置user的异步方法，可以明显的感觉到延迟降低
    setUser (context, user) {
      context.commit('setUser', user)
    },
    // 设置token的异步方法
    setToken (context, data) {
      context.commit('setToken', data)
    }
  },
  modules: {
  }
})
