<template>
  <v-container fluid>
    <v-row>
      <v-col cols="12">
        <v-data-table :headers="headers" :items="authorities" class="elevation-1">
          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title>用户权限管理</v-toolbar-title>
              <v-divider class="mx-4" inset vertical></v-divider>
              <v-spacer></v-spacer>
              <!--对话框，用于修改或者新建-->
              <v-dialog v-model="dialog" max-width="600px">
                <template v-slot:activator="{ on, attrs }">
                  <!--点击这个按钮的时候，将状态切换为创建-->
                  <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on" @click="state.create=true">
                    New Authority
                  </v-btn>
                </template>
                <v-card>
                  <v-card-title>
                    <span class="headline">{{ formTitle }}</span>
                  </v-card-title>
                  <v-card-text>
                    <v-form ref="authorityForm" v-model="valid" lazy-validation autocomplete="off">
                      <!--用户名-->
                      <v-row>
                        <v-col cols="12">
                          <v-text-field v-model="editedItem.user.id" :rules="rules.userRules" label="User"></v-text-field>
                        </v-col>
                      </v-row>
                      <!--权限名-->
                      <v-row>
                        <v-col cols="12">
                          <v-text-field v-model="editedItem.authority" :rules="rules.authorityRules" label="Authority"></v-text-field>
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
  name: 'AuthorityAdmin',
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
        text: 'User',
        align: 'start',
        sortable: false,
        value: 'user.id'
      },
      {
        text: 'Authority',
        align: 'start',
        sortable: false,
        value: 'authority'
      },
      {
        text: 'Actions',
        value: 'actions',
        sortable: false
      }
    ],
    authorities: [],
    editedIndex: -1,
    editedItem: {
      user: {
        id: 0
      },
      authority: ''
    },
    defaultItem: {
      user: {
        id: 0
      },
      authority: ''
    },
    rules: {
      userRules: [
        v => !!v || 'User is none!',
        v => v > 0 || 'Please input a valid user id!'
      ],
      authorityRules: [
        v => !!v || 'Authority is none!'
      ]
    }
  }),
  computed: {
    formTitle () {
      return this.editedIndex === -1 ? 'New Authority' : 'Edit Authority'
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
      // 获取全部用户权限（分页默认为10个一页）
      this.axios.get('api/authorities', {
        params: {
          projection: 'inlineUser'
        }
      })
        .then(response => {
          console.log(response)
          // 设置用户权限列表
          this.authorities = response.data._embedded.authorities
        }).catch(error => {
          console.log(error)
        })
    },
    editItem (item) {
      this.editedIndex = this.authorities.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
      this.state.update = true
    },
    deleteItem (item) {
      this.editedIndex = this.authorities.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialogDelete = true
    },
    deleteItemConfirm () {
      const authority = this.authorities[this.editedIndex]
      this.axios.delete(authority._links.self.href)
        .then(response => {
          console.log(response)
        }).catch(error => {
          console.log(error)
        })
      this.authorities.splice(this.editedIndex, 1)
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
      const valid = this.$refs.authorityForm.validate()
      if (valid) {
        this.axios.post('api/authorities', {
          user: 'api/users/' + this.editedItem.user.id,
          authority: this.editedItem.authority
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
      const valid = this.$refs.authorityForm.validate()
      if (valid) {
        const authority = this.authorities[this.editedIndex]
        this.axios.patch(authority._links.self.href, {
          user: 'api/users/' + this.editedItem.user.id,
          authority: this.editedItem.authority
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
