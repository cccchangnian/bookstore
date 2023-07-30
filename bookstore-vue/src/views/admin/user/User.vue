<template>
  <v-container fluid>
    <v-row>
      <v-col cols="12">
        <v-data-table :headers="headers" :items="users" class="elevation-1">
          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title>用户信息管理</v-toolbar-title>
              <v-divider class="mx-4" inset vertical></v-divider>
              <v-spacer></v-spacer>
              <!--对话框，用于修改或者新建-->
              <v-dialog v-model="dialog" max-width="600px">
                <template v-slot:activator="{ on, attrs }">
                  <!--点击这个按钮的时候，将状态切换为创建-->
                  <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on" @click="state.create=true">
                    New User
                  </v-btn>
                </template>
                <v-card>
                  <v-card-title>
                    <span class="headline">{{ formTitle }}</span>
                  </v-card-title>
                  <v-card-text>
                    <v-form ref="userForm" v-model="valid" lazy-validation autocomplete="off">
                      <!--用户名和密码-->
                      <v-row>
                        <v-col cols="12" sm="6">
                          <v-text-field v-model="editedItem.username" :rules="rules.usernameRules" label="用户名"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6">
                          <v-text-field type="password" v-model="editedItem.password" :rules="rules.passwordRules" label="密码"></v-text-field>
                        </v-col>
                      </v-row>
                      <!--用户的状态，用以禁用用户-->
                      <v-row>
                        <v-col cols="12" sm="6">
                          <v-switch v-model="editedItem.enabled" :label="`Enabled: ${editedItem.enabled}`"></v-switch>
                        </v-col>
                        <!--手机号码-->
                        <v-col cols="12" sm="6">
                          <v-text-field v-model="editedItem.phone" label="手机号码"></v-text-field>
                        </v-col>
                      </v-row>
                      <!--用户性别-->
                      <v-row>
                        <v-col cols="12">
                          <v-radio-group v-model="editedItem.gender" row>
                            <v-radio label="Unknown" color="orange" :value="0"></v-radio>
                            <v-radio label="Male" color="primary" :value="1"></v-radio>
                            <v-radio label="Female" color="pink" :value="2"></v-radio>
                          </v-radio-group>
                        </v-col>
                      </v-row>
                      <!--用户邮箱-->
                      <v-row>
                        <v-col cols="12">
                          <v-text-field v-model="editedItem.email" :rules="rules.emailRules" label="邮箱"></v-text-field>
                        </v-col>
                      </v-row>
                      <!--用户收集号码-->
                      <v-row>
                        <v-col cols="12">
                          <v-text-field v-model="editedItem.location" :rules="rules.locationRules" label="所在地"></v-text-field>
                        </v-col>
                      </v-row>
                      <!--用户生日和加入日期-->
                      <v-row>
                        <v-col cols="12" sm="6">
                          <v-text-field type="date" v-model="editedItem.birthday" label="生日"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6">
                          <v-text-field type="date" v-model="editedItem.joinedDate" label="加入日期"></v-text-field>
                        </v-col>
                      </v-row>
                    </v-form>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                    <!--判断是创建还是删除-->
                    <v-btn v-if="state.create" :disabled="!valid" color="blue darken-1" text @click="create">Create</v-btn>
                    <v-btn v-if="state.update" :disabled="!valid" color="blue darken-1" text @click="update">Update</v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
              <!--删除对话框，用以再次确认-->
              <v-dialog v-model="dialogDelete" max-width="500px">
                <v-card>
                  <v-card-title class="headline">Are you sure you want to delete this item?</v-card-title>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="closeDelete()">Cancel</v-btn>
                    <v-btn color="blue darken-1" text @click="deleteItemConfirm()">OK</v-btn>
                    <v-spacer></v-spacer>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </v-toolbar>
          </template>
          <template v-slot:item.actions="{ item }">
            <v-icon small class="mr-2" @click="editItem(item)">
              mdi-pencil
            </v-icon>
            <v-icon small @click="deleteItem(item)">
              mdi-delete
            </v-icon>
          </template>
          <template v-slot:no-data>
            <v-btn color="primary" @click="initialize">
              Reset
            </v-btn>
          </template>
        </v-data-table>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'UserAdmin',
  data: () => ({
    state: {
      create: false,
      update: false
    },
    valid: true,
    dialog: false,
    dialogDelete: false,
    headers: [
      {
        text: 'Username',
        align: 'start',
        sortable: false,
        value: 'username'
      },
      {
        text: 'Email',
        sortable: false,
        value: 'email'
      },
      {
        text: 'Enabled',
        value: 'enabled'
      },
      {
        text: 'Joined Date',
        value: 'joinedDate'
      },
      {
        text: 'Actions',
        value: 'actions',
        sortable: false
      }
    ],
    users: [],
    editedIndex: -1,
    editedItem: {
      username: '',
      password: '',
      enabled: true,
      gender: '0',
      phone: '',
      email: '',
      icon: null,
      location: '',
      birthday: new Date().toISOString().substr(0, 10),
      joinedDate: new Date().toISOString().substr(0, 10)
    },
    defaultItem: {
      username: '',
      password: '',
      enabled: true,
      gender: 0,
      phone: '',
      email: '',
      icon: null,
      location: '',
      birthday: new Date().toISOString().substr(0, 10),
      joinedDate: new Date().toISOString().substr(0, 10)
    },
    rules: {
      usernameRules: [
        v => !!v || 'Username is none!',
        v => (v.length >= 1 && v.length <= 20) || 'Username must be 1-20 characters!'
      ],
      passwordRules: [
        v => !!v || 'Password is none!',
        v => (v.length >= 6 && v.length <= 30) || 'Password must be 6-30 characters!'
      ],
      emailRules: [
        v => !!v || 'Email is none!',
        v => /(\w+)@(\w+)\.(\w+)/.test(v) || 'Email is not valid!'
      ],
      locationRules: [
        v => !!v || 'Location is none!'
      ]
    }
  }),
  computed: {
    formTitle () {
      return this.editedIndex === -1 ? 'New User' : 'Edit User'
    }
  },
  watch: {
    dialog (val) {
      val || this.close()
    },
    dialogDelete (val) {
      val || this.closeDelete()
    }
  },
  methods: {
    initialize () {
      // 获取全部用户（分页默认为10个一页）
      this.axios.get('api/users')
        .then(response => {
          console.log(response)
          // 设置用户列表
          const data = response.data._embedded.users
          for (var i = 0; i < data.length; ++i) {
            data[i].birthday = data[i].birthday ? data[i].birthday.substr(0, 10) : null
            data[i].joinedDate = data[i].joinedDate ? data[i].joinedDate.substr(0, 10) : null
          }
          this.users = data
        }).catch(error => {
          console.log(error)
        })
    },
    editItem (item) {
      this.editedIndex = this.users.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
      this.state.update = true
    },
    deleteItem (item) {
      this.editedIndex = this.users.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialogDelete = true
    },
    deleteItemConfirm () {
      const user = this.users[this.editedIndex]
      this.axios.delete(user._links.self.href)
        .then(response => {
          console.log(response)
        }).catch(error => {
          console.log(error)
        })
      this.users.splice(this.editedIndex, 1)
      this.closeDelete()
    },
    close () {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
      this.state.create = false
      this.state.update = false
    },
    closeDelete () {
      this.dialogDelete = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },
    create () {
      const valid = this.$refs.userForm.validate()
      if (valid) {
        this.axios.post('api/users', {
          username: this.editedItem.username,
          password: this.editedItem.password,
          enabled: this.editedItem.enabled,
          gender: parseInt(this.editedItem.gender),
          phone: this.editedItem.phone,
          email: this.editedItem.email,
          icon: this.editedItem.icon,
          location: this.editedItem.location,
          birthday: this.editedItem.birthday ? this.editedItem.birthday : new Date().toISOString().substr(0, 10),
          joinedDate: this.editedItem.joinedDate ? this.editedItem.joinedDate : new Date().toISOString().substr(0, 10)
        }
        ).then(response => {
          console.log(response)
          this.initialize()
        }).catch(error => {
          console.log(error)
        })
        this.close()
      }
    },
    update () {
      const valid = this.$refs.userForm.validate()
      if (valid) {
        const user = this.users[this.editedIndex]
        this.axios.patch(user._links.self.href, {
          username: this.editedItem.username,
          password: this.editedItem.password,
          enabled: this.editedItem.enabled,
          gender: parseInt(this.editedItem.gender),
          phone: this.editedItem.phone,
          email: this.editedItem.email,
          icon: this.editItem.icon,
          location: this.editedItem.location,
          birthday: this.editedItem.birthday ? this.editedItem.birthday : new Date().toISOString().substr(0, 10),
          joinedDate: this.editedItem.joinedDate ? this.editedItem.joinedDate : new Date().toISOString().substr(0, 10)
        }
        ).then(response => {
          console.log(response)
          this.initialize()
        }).catch(error => {
          console.log(error)
        })
        this.close()
      }
    }
  },
  created () {
    this.initialize()
  }
}
</script>
