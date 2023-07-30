<template>
  <v-container fluid>
    <v-row justify="center">
      <v-col sm="6" md="4">
        <v-card class="mx-auto">
          <v-img class="white--text align-end" height="200px" src="../../assets/user/top.jpeg">
            <v-card-title>Safety</v-card-title>
          </v-img>
          <v-card-text>
            <v-form ref="safetyForm" v-model="valid" lazy-validation autocomplete="off">
              <!--用户名，目前只可读-->
              <v-row>
                <v-col sm="12">
                  <v-text-field v-model="user.username" :rules="rules.usernameRules" label="Username" readonly outlined></v-text-field>
                </v-col>
              </v-row>
              <!--邮箱-->
              <v-row>
                <v-col sm="12">
                  <v-text-field ref="email" v-model="user.email" :rules="rules.emailRules" label="Email"></v-text-field>
                </v-col>
              </v-row>
              <!--验证码-->
              <v-row>
                <v-col sm="8">
                  <v-text-field v-model="verificationCode" :rules="rules.verificationCodeRules" label="Verification Code" outlined :error-messages="verificationCodeError"></v-text-field>
                </v-col>
                <!--验证码发送按钮-->
                <v-col sm="4">
                  <v-btn :disabled="!reverification" color="warning" @click="sendVerificationCode()" text>send</v-btn>
                  <p class="text-center">{{ message }}</p>
                </v-col>
              </v-row>
              <!--保存按钮-->
              <v-btn :disabled="!valid" color="primary" class="mr-4" @click="update()">Update</v-btn>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
    <v-snackbar v-model="snackbar">
      Information is successfully update!
      <template v-slot:action="{ attrs }">
        <v-btn color="pink" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
      </template>
    </v-snackbar>
  </v-container>
</template>

<script>
export default {
  name: 'UserSafety',
  data: () => ({
    snackbar: false,
    valid: true,
    user: {
      username: '',
      email: '',
      _links: []
    },
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
      emailRules: [
        v => /^[a-zA-Z0-9]+@[a-zA-Z]+\.[a-zA-Z]+$/.test(v) || 'Not a valid email!'
      ],
      verificationCodeRules: [
        v => !!v || 'Verification Code is none!'
      ]
    }
  }),
  computed: {
    message () {
      return this.time + ' later'
    }
  },
  methods: {
    initialize () {
      this.$nextTick(() => {
        this.user = Object.assign({}, this.$store.state.user)
        this.$refs.safetyForm.validate()
      })
    },
    update () {
      const valid = this.$refs.safetyForm.validate()
      if (valid) {
        this.axios.patch(this.user._links.user.href + '/email', {
          email: this.user.email,
          verificationCode: this.verificationCode
        }).then(response => {
          // 更新成功以后重新设置state.user
          this.$store.dispatch('setUser', response.data)
          this.snackbar = true
        }).catch(() => {
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
          this.axios.post('/api/email', {
            email: this.user.email
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
  },
  created () {
    this.initialize()
  },
  watch: {
    verificationCode: function () {
      this.verificationCodeError = ''
    }
  }
}
</script>
