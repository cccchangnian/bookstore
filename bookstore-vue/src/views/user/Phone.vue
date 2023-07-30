<template>
  <v-container fluid>
    <v-row justify="center">
      <v-col sm="6" md="4">
        <v-card class="mx-auto">
          <v-img class="white--text align-end" height="200px" src="../../assets/user/top.jpeg">
            <v-card-title>Phone</v-card-title>
          </v-img>
          <v-card-text>
            <v-form ref="phoneForm" v-model="valid" lazy-validation>
              <!--收货地址-->
              <v-row>
                <v-col sm="12">
                  <v-text-field v-model="user.phone" :rules="rules.phoneRules" label="Phone" outlined></v-text-field>
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
  name: 'UserPhone',
  data: () => ({
    snackbar: false,
    valid: true,
    user: {
      phone: ''
    },
    rules: {
      phoneRules: [
        v => !!v || 'Phone is none!'
      ]
    }
  }),
  methods: {
    initialize () {
      this.$nextTick(() => {
        this.user = Object.assign({}, this.$store.state.user)
      })
    },
    update () {
      const valid = this.$refs.phoneForm.validate()
      if (valid) {
        this.axios.patch(this.user._links.user.href, {
          phone: this.user.phone
        }).then(response => {
          this.$store.dispatch('setUser', response.data)
          this.snackbar = true
        })
      }
    }
  },
  created () {
    this.initialize()
  }
}
</script>
