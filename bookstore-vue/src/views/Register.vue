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
                <v-img class="white--text rounded" src="../assets/user/left.png" height="480">
                  <v-card-title>Register</v-card-title>
                </v-img>
              </v-sheet>
            </v-col>
            <!--右边放表单-->
            <v-col sm="6">
              <v-sheet height="480">
                <!--表单填写部分-->
                <v-card-text>
                  <!--ref是vue特有的别名，无需通过id来获取元素-->
                  <v-form ref="registerForm" v-model="valid" lazy-validation autocomplete="off">
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
                    <!--确认密码-->
                    <v-row>
                      <v-col>
                        <v-text-field v-model="form.passwordConfirm" :rules="[rules.passwordConfirmRules.required, rules.passwordConfirmRules.equal(form.password, form.passwordConfirm)]" label="确认密码" :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'" @click:append="show = !show" :type="show ? 'text': 'password'" solo></v-text-field>
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
                  </v-form>
                </v-card-text>
                <!--动作执行按钮-->
                <v-card-actions class="px-4">
                  <v-btn :disabled="!valid" block color="primary" @click="register()">Register</v-btn>
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
      用户名已存在！
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
  name: 'Register',
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
      passwordConfirm: '',
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
      passwordConfirmRules: {
        required: v => !!v || '请输入确认密码',
        equal: (password, passwordConfirm) => password === passwordConfirm || '两次密码不一致'
      },
      rememberRules: [
        v => v === true || '请勾选此项'
      ]
    }
  }),
  methods: {
    // 注册功能
    register () {
      // 点击注册按钮时首先验证表单填写的正确性
      const valid = this.$refs.registerForm.validate()
      if (valid) {
        // 用vue-axios发送post请求
        this.axios.post('api/users', {
          username: this.form.username,
          password: this.form.password
        }).then(() => {
          // 注册成功，跳转到登录页面
          this.$router.push({
            name: 'Login'
          })
        }).catch(() => {
          // 发生请求错误，此处错误可能是因为用户名已存在，也有可能是提交的数据不符合要求，也有可能是服务器内部错误
          // 当返回的状态码是409时，说明用户名已存在
          // 显示错误消息
          this.snackbar = true
        })
      }
    }
  }
}
</script>
