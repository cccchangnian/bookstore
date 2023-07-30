<template>
  <!--建议使用v-container来管理-->
  <v-container fluid>
    <!--一个小卡片-->
    <v-row justify="center">
      <!--细节控制，不然卡片的大小会不自然-->
      <v-col md="7">
        <v-card class="mx-auto my-4">
          <v-row no-gutters>
            <!--左边放图片-->
            <v-col sm="6" class="py-0">
              <v-sheet>
                <v-img class="white--text rounded" src="../assets/user/left.png" height="450">
                  <v-card-title>Login</v-card-title>
                </v-img>
              </v-sheet>
            </v-col>
            <!--右边放表单-->
            <v-col sm="6">
              <v-sheet height="450" class="py-2">
                <!--表单填写部分-->
                <v-card-text>
                  <!--ref是vue特有的别名，无需通过id来获取元素-->
                  <v-form ref="loginForm" v-model="valid" lazy-validation autocomplete="off">
                    <!--用户名-->
                    <v-row>
                      <v-col>
                        <v-text-field v-model="form.username" :rules="rules.usernameRules" label="用户名" solo></v-text-field>
                      </v-col>
                    </v-row>
                    <!--密码-->
                    <v-row>
                      <v-col>
                        <v-text-field v-model="form.password" :rules="rules.passwordRules" label="密码" :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'" @click:append="show = !show" :type="show ? 'text': 'password'" solo></v-text-field>
                      </v-col>
                    </v-row>
                    <!--同意条款-->
                    <v-row>
                      <v-col>
                        <v-checkbox v-model="form.rememberMe" :rules="rules.rememberRules">
                          <template v-slot:label>
                            <div>我同意bookstore的使用条款</div>
                          </template>
                        </v-checkbox>
                      </v-col>
                    </v-row>
                    <!--忘记密码-->
                    <v-row>
                      <v-col class="text-center">
                        <a>忘记密码？</a>
                      </v-col>
                    </v-row>
                  </v-form>
                </v-card-text>
                <!--动作执行按钮-->
                <v-card-actions class="px-4">
                  <v-btn :disabled="!valid" block color="primary" @click="login()">Login</v-btn>
                </v-card-actions>
              </v-sheet>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
    <!--错误提示，用于反馈创建用户时的错误-->
    <v-snackbar
      v-model="snackbar"
    >
      用户名或密码不正确！
      <template v-slot:action="{ attrs }">
        <v-btn
          color="pink"
          text
          v-bind="attrs"
          @click="snackbar = false"
        >
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </v-container>
</template>

<script>
export default {
  name: 'Login',
  data: () => ({
    // 用于验证表单内容填写是否正确
    valid: true,
    // 用于控制密码是否以可见的形式显示
    show: false,
    // 用于控制消息条是否显示
    snackbar: false,
    // 表单填写的内容
    form: {
      username: '',
      password: '',
      rememberMe: false
    },
    // 验证规则
    rules: {
      usernameRules: [
        v => !!v || '用户名不能为空',
        v => ((v || '').length >= 1 && (v || '').length <= 20) || '用户名必须在1-20个字符以内'
      ],
      passwordRules: [
        v => !!v || '密码不能为空',
        v => ((v || '').length >= 6 && (v || '').length <= 30) || '密码必须在6-30个字符以内'
      ],
      rememberRules: [
        v => v === true || '请勾选此项'
      ]
    }
  }),
  methods: {
    // 登录功能
    login () {
      // 首先验证表单填写的正确性
      const valid = this.$refs.loginForm.validate()
      if (valid) {
        // 向授权服务器发送请求，获取access token，请求时x-www-form-urlencoded格式
        const qs = require('qs')
        const data = qs.stringify({
          client_id: 'bookstore',
          client_secret: '123456',
          grant_type: 'password',
          username: this.form.username,
          password: this.form.password
        })
        this.axios.post('oauth/token', data)
          .then(response => {
            // 授权成功，设置token
            this.$store.commit('setToken', response.data)
            // 并且获取用户详情，这里不需要验证access token以获取用户名，因为用户名已有
            this.axios.get('api/users/search/findByUsername', {
              params: {
                username: this.form.username
              }
            }).then(response => {
              // 异步设置user
              this.$store.dispatch('setUser', response.data)
              // 设置完成后，跳转到主页
              this.$router.push({
                name: 'Home'
              })
            }).catch(() => {
              // 用户认证错误，显示消息条
              this.snackbar = true
            })
          }).catch(() => {
            this.snackbar = true
          })
      }
    }
  }
}
</script>
