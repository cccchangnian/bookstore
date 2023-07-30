<template>
  <v-container fluid>
    <v-row justify="center">
      <v-col sm="6" md="4">
        <v-card class="mx-auto">
          <v-img class="white--text align-end" height="200px" src="../../assets/user/top.jpeg">
            <v-card-title>Password</v-card-title>
          </v-img>
          <v-card-text>
            <v-form ref="passwordForm" v-model="valid" lazy-validation autocomplete="off">
              <!--用户名，目前只可读-->
              <v-row>
                <v-col sm="12">
                  <v-text-field v-model="user.username" :rules="rules.usernameRules" label="Username" readonly outlined></v-text-field>
                </v-col>
              </v-row>
              <!--密码-->
              <v-row>
                <v-col sm="12">
                  <v-text-field v-model="form.password" :rules="rules.passwordRules" label="Password" :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'" @click:append="show = !show" :type="show ? 'text': 'password'"></v-text-field>
                </v-col>
              </v-row>
              <!--确认密码-->
              <v-row>
                <v-col sm="12">
                  <v-text-field v-model="form.passwordConfirm" :rules="[rules.passwordConfirmRules.required, rules.passwordConfirmRules.confirm(form.password, form.passwordConfirm)]" label="Password Confirm" :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'" @click:append="show = !show" :type="show ? 'text': 'password'"></v-text-field>
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
  name: 'UserPassword',
  data: () => ({
    show: false,
    snackbar: false,
    valid: true,
    user: {
      username: '',
      _links: []
    },
    form: {
      password: '',
      passwordConfirm: ''
    },
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
      }
    }
  }),
  methods: {
    initialize () {
      this.$nextTick(() => {
        this.user = Object.assign({}, this.$store.state.user)
      })
    },
    update () {
      const valid = this.$refs.passwordForm.validate()
      if (valid) {
        this.axios.patch(this.user._links.user.href, {
          password: this.form.password
        }).then(() => {
          this.snackbar = true
          // 密码修改后跳转到登录界面
          this.$router.push({
            name: 'Login'
          })
          // 更新成功以后注销
          this.$store.commit('removeUser')
          this.$store.commit('removeToken')
        })
      }
    }
  },
  created () {
    this.initialize()
  }
}
</script>
