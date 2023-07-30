<template>
  <v-container fluid>
    <v-row justify="center">
      <v-col sm="6" md="4">
        <v-card class="mx-auto">
          <v-img class="white--text align-end" height="200px" src="../../assets/user/top.jpeg">
            <v-card-title>Head Portrait</v-card-title>
          </v-img>
          <v-card-text>
            <v-form ref="iconForm" v-model="valid" lazy-validation>
              <!--头像预览-->
              <v-row justify="center">
                <v-col sm="8" style="text-align: center;">
                  <v-avatar width="150px" height="150px" class="elevation-8">
                    <v-img ref="image" v-if="user.icon" :src="user.icon" alt="头像"></v-img>
                    <v-img ref="image" v-else src="../../assets/logo.png" alt="头像"></v-img>
                  </v-avatar>
                </v-col>
              </v-row>
              <v-row>
                <v-col sm="12">
                  <v-file-input v-model="file" outlined dense placeholder="Can only accept png, jpeg, gif" @change="preview" :rules="rules.iconRules" accept="image/png, image/jpeg, image/jpg, image/gif"></v-file-input>
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
  name: 'UserIcon',
  data: () => ({
    snackbar: false,
    valid: true,
    user: {
      username: '',
      icon: null,
      _links: []
    },
    file: null,
    rules: {
      iconRules: [
        v => !!v || 'File is none!',
        v => /\w+\.[jpeg, png, gif, jpg]/.test((v == null ? '' : v.name)) || 'Can only accept png, jpeg, gif',
        v => (!!v && v.size < 1048576) || 'File size must not exceed 1MB'
      ]
    }
  }),
  computed: {
    image: function () {
      return this.user.icon
    }
  },
  methods: {
    initialize () {
      this.$nextTick(() => {
        this.user = Object.assign({}, this.$store.state.user)
      })
    },
    update () {
      const valid = this.$refs.iconForm.validate()
      if (valid) {
        const formData = new FormData()
        formData.append('file', this.file)
        this.axios.patch(
          this.user._links.user.href + '/icon',
          formData
        ).then(response => {
          // 更新成功以后重新设置state.user
          this.$store.dispatch('setUser', response.data)
          this.initialize()
          this.snackbar = true
        })
      }
    },
    preview (file) {
      if (file) {
        const reader = new FileReader()
        reader.readAsDataURL(file)
        const vm = this
        reader.onload = function (e) {
          vm.user.icon = this.result
        }
      }
    }
  },
  created () {
    this.initialize()
  }
}
</script>
