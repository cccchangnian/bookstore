<template>
  <v-container fluid>
    <v-row justify="center">
      <v-col sm="6" md="4">
        <v-card class="mx-auto">
          <v-img class="white--text align-end" height="200px" src="../../assets/user/top.jpeg">
            <v-card-title>Base Information</v-card-title>
          </v-img>
          <v-card-text>
            <v-form ref="baseInformationForm" v-model="valid" lazy-validation>
              <!--用户名，目前只可读-->
              <v-row>
                <v-col sm="12">
                  <v-text-field v-model="user.username" :rules="rules.usernameRules" label="Username" readonly outlined></v-text-field>
                </v-col>
              </v-row>
              <!--用户的真实姓名-->
              <v-row>
                <v-col sm="6">
                  <v-text-field v-model="user.firstName" :rules="rules.firstNameRules" label="First Name"></v-text-field>
                </v-col>
                <v-col sm="6">
                  <v-text-field v-model="user.lastName" :rules="rules.lastNameRules" label="Last Name"></v-text-field>
                </v-col>
              </v-row>
              <!--性别-->
              <v-row>
                <v-col sm="12">
                  <v-radio-group v-model="user.gender" row>
                    <v-radio label="Unknown" color="orange" :value="0"></v-radio>
                    <v-radio label="Male" color="primary" :value="1"></v-radio>
                    <v-radio label="Female" color="pink" :value="2"></v-radio>
                  </v-radio-group>
                </v-col>
              </v-row>
              <!--生日-->
              <v-row>
                <v-col sm="12">
                  <v-menu ref="birthdayMenu" v-model="birthdayMenu" :close-on-content-click="false" transition="scale-transition" offset-y absolute>
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field :value="user.birthday" label="Birthday" prepend-icon="mdi-calendar" readonly v-bind="attrs" v-on="on"></v-text-field>
                    </template>
                    <v-date-picker ref="birthdayPicker" v-model="user.birthday" :max="new Date().toISOString().substr(0, 10)" min="1950-01-01"></v-date-picker>
                  </v-menu>
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
  name: 'UserBaseInformation',
  data: () => ({
    snackbar: false,
    valid: true,
    user: {
      username: '',
      firstName: '',
      lastName: '',
      gender: 0,
      birthday: null,
      _links: []
    },
    birthdayMenu: false,
    rules: {
      usernameRules: [
        v => !!v || 'Username is none!',
        v => ((v || '').length >= 1 && (v || '').length <= 20) || 'Username must be 1-20 characters!'
      ],
      firstNameRules: [
        v => !!v || 'First Name is none!',
        v => ((v || '').length >= 1 && (v || '').length <= 20) || 'First Name must be 1-20 characters!'
      ],
      lastNameRules: [
        v => !!v || 'Last Name is none!',
        v => ((v || '').length >= 1 && (v || '').length <= 50) || 'Last Name must be 1-50 characters!'
      ]
    }
  }),
  methods: {
    initialize () {
      this.$nextTick(() => {
        this.user = Object.assign({}, this.$store.state.user)
      })
    },
    saveBirthday (birthday) {
      this.$refs.birthdayMenu.save(birthday)
    },
    update () {
      const valid = this.$refs.baseInformationForm.validate()
      if (valid) {
        this.axios.patch(this.user._links.user.href, {
          firstName: this.user.firstName,
          lastName: this.user.lastName,
          gender: this.user.gender,
          birthday: this.user.birthday
        }).then(response => {
          // 更新成功以后重新设置state.user
          this.$store.dispatch('setUser', response.data)
          this.snackbar = true
        })
      }
    }
  },
  watch: {
    // birthdayMenu (val) {
    //   val && setTimeout(() => (this.$refs.birthdayPicker.activePicker = 'YEAR'))
    // }
  },
  created () {
    this.initialize()
  }
}
</script>
