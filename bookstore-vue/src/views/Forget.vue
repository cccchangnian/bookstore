<template>
  <v-container fluid>
    <v-row>
      <v-col cols="12">
        <!--步骤条-->
        <v-stepper v-model="e">
          <v-stepper-header>
            <template v-for="(item, n) in steps">
              <v-stepper-step :key="`${n+1}-step`" :complete="e > n + 1" :step="n+1">
                {{ item.name }}
              </v-stepper-step>
              <v-divider v-if="n + 1 !== steps.length" :key="n+1"></v-divider>
            </template>
          </v-stepper-header>
          <v-stepper-items>
            <!--输入用户名-->
            <v-stepper-content step="1">
              <v-form ref="usernameForm" v-model="validUsernameForm" lazy-validation autocomplete="off">
                <!--用户名-->
                <v-row>
                  <v-col sm="12">
                    <v-text-field v-model="form.username" label="Username" :rules="rules.usernameRules" :error-messages="usernameError"></v-text-field>
                  </v-col>
                </v-row>
              </v-form>
              <v-btn :disabled="!validUsernameForm" color="primary" @click="validateUsernameForm()">Next</v-btn>
            </v-stepper-content>
            <!--用户名如果存在，判断邮箱存不存在，在这里修改密码-->
            <v-stepper-content step="2">
              <v-form ref="emailForm" v-model="validEmailForm" lazy-validation autocomplete="off">
                <!--用户名-->
                <v-row>
                  <v-col sm="12">
                    <v-text-field v-model="form.username" label="Username" readonly :rules="rules.usernameRules"></v-text-field>
                  </v-col>
                </v-row>
                <!--邮箱，如果没有提示错误-->
                <v-row>
                  <v-col sm="12">
                    <v-text-field ref="email" v-if="form.email" v-model="form.email" label="Email" readonly :rules="rules.emailRules"></v-text-field>
                    <v-alert v-else type="error">This user has no email!</v-alert>
                  </v-col>
                </v-row>
                <!--验证码填写和发送按钮-->
                <v-row v-if="form.email">
                  <v-col sm="8">
                    <v-text-field v-model="verificationCode" :rules="[rules.verificationCodeRules.required, rules.verificationCodeRules.notSend(reverification)]" label="Verification Code" outlined :error-messages="verificationCodeError"></v-text-field>
                  </v-col>
                  <!--验证码发送按钮-->
                  <v-col sm="4" class="text-center">
                    <v-btn :disabled="!reverification" color="warning" @click="sendVerificationCode()" text>send</v-btn>
                    <v-card-text>{{ message }}</v-card-text>
                  </v-col>
                </v-row>
                <!--密码和确认密码-->
                <v-row v-if="form.email">
                  <v-col sm="12">
                    <v-text-field v-model="form.password" :rules="rules.passwordRules" label="Password" :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'" @click:append="show = !show" :type="show ? 'text': 'password'"></v-text-field>
                  </v-col>
                </v-row>
                <v-row v-if="form.email">
                  <v-col sm="12">
                    <v-text-field v-model="form.passwordConfirm" :rules="[rules.passwordConfirmRules.required, rules.passwordConfirmRules.confirm(form.password, form.passwordConfirm)]" label="Password Confirm" :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'" @click:append="show = !show" :type="show ? 'text': 'password'"></v-text-field>
                  </v-col>
                </v-row>
              </v-form>
              <v-btn v-if="form.email" :disabled="!validEmailForm" color="primary" class="mr-2" @click="validateEmailForm()">Confirm</v-btn>
              <v-btn color="red" @click="e = e - 1">Last</v-btn>
            </v-stepper-content>
          </v-stepper-items>
        </v-stepper>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'Forget',
  data: () => ({
    e: 1,
    steps: [
      {
        name: 'Username'
      },
      {
        name: 'Reset'
      }
    ],
    validUsernameForm: true,
    validEmailForm: true,
    show: false,
    form: {
      username: '',
      email: '',
      password: '',
      passwordConfirm: ''
    },
    // 仅通过前端无法得知的用户名错误
    usernameError: '',
    verificationCode: '',
    time: 180,
    reverification: true,
    // 仅仅通过前端无法得知的错误
    verificationCodeError: '',
    rules: {
      usernameRules: [
        v => !!v || 'Username is none!',
        v => ((v || '').length >= 1 && (v || '').length <= 20) || 'Username must be 1-20 characters!'
      ],
      passwordRules: [
        v => !!v || 'Password is none!',
        v => ((v || '').length >= 6 && (v || '').length <= 30) || 'Password must be 6-30 characters!'
      ],
      passwordConfirmRules: {
        required: v => !!v || 'Password Confirm is none!',
        confirm: (password, passwordConfirm) => password === passwordConfirm || 'Password is not confirmed!'
      },
      emailRules: [
        v => /^[a-zA-Z0-9]+@[a-zA-Z]+\.[a-zA-Z]+$/.test(v) || 'Not a valid email!'
      ],
      verificationCodeRules: {
        notSend: (reverification) => reverification === false || 'Verification Code has not been sent yet!',
        required: v => !!v || 'Verification Code is none!'
      }
    }
  }),
  computed: {
    message () {
      return this.time + ' later'
    }
  },
  watch: {
    steps (val) {
      if (this.e > val) {
        this.e = val
      }
    },
    'form.username': function () {
      this.usernameError = ''
    },
    verificationCode: function () {
      this.verificationCodeError = ''
    }
  },
  methods: {
    validateUsernameForm () {
      const valid = this.$refs.usernameForm.validate()
      if (valid) {
        this.axios.get(this.$store.getters.findUserByUsername(this.form.username))
          .then(response => {
            // 用户名存在，到下一步，并且设置邮箱
            this.e += 1
            this.form.email = response.data.email
          }).catch(() => {
            this.usernameError = 'Username is not exists!'
          })
      }
    },
    validateEmailForm () {
      const valid = this.$refs.emailForm.validate()
      if (valid) {
        this.axios.post('/api/password/retrieve', {
          username: this.form.username,
          password: this.form.password,
          email: this.form.email,
          verificationCode: this.verificationCode
        }).then(() => {
          // 验证成功，并且密码已经重新设置，跳转到登录页面
          this.$router.push({
            name: 'Login'
          })
        }).catch(() => {
          // 验证码不正确，设置错误信息
          this.verificationCodeError = 'Verification Code is not correct!'
        })
      }
    },
    sendVerificationCode () {
      // 首先判断邮箱格式是否正确
      const valid = this.$refs.email.validate()
      if (valid) {
        if (this.reverification) {
          // 发送验证码成功设置不可再发
          this.restrictSend()
          this.axios.post(this.$store.getters.email, {
            email: this.form.email
          }).then(() => {
          })
        }
      }
    },
    restrictSend () {
      this.time = 180
      this.reverification = false
      const interval = setInterval(() => {
        if (this.time > 0) {
          this.time -= 1
        }
      }, 1000)
      setTimeout(() => {
        clearInterval(interval)
        this.reverification = true
      }, 180000)
    }
  }
}
</script>
