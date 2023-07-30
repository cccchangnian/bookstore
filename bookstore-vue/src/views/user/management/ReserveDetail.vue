<template>
  <v-container fluid>
    <!--结算功能，生成一张表单就可以了-->
    <v-row>
      <v-col>
        <v-card>
          <v-card-title>订单编号：{{ reserve.id }}</v-card-title>
          <v-card-text>
            <v-form ref="reserveForm" v-model="valid" lazy-validation autocomplete="off">
              <!--用户名-->
              <v-row>
                <v-col>
                  <v-text-field :value="reserve.user.username" readonly label="用户名" :rules="rules.usernameRules"></v-text-field>
                </v-col>
              </v-row>
              <!--手机号码-->
              <v-row>
                <v-col>
                  <v-text-field v-model="reserve.phone" label="收件人手机号码" :rules="rules.phoneRules"></v-text-field>
                </v-col>
              </v-row>
              <!--收货地址-->
              <v-row>
                <v-col>
                  <v-text-field v-model="reserve.location" label="收货地址（请填写具体的地址，否则交易无效）" :rules="rules.locationRules"></v-text-field>
                </v-col>
              </v-row>
              <!--总交易金额-->
              <v-row>
                <v-col>
                  <v-text-field :value="reserve.totalPrice" readonly label="总金额" :rules="rules.totalPriceRules"></v-text-field>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
          <!--支付尾款-->
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn :disabled="!valid" color="primary" @click="updateReserve()">
              立即支付
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <!--显示支付结果-->
    <v-snackbar v-model="snackbar">
      支付成功！
      <template v-slot:action="{ attrs }">
        <v-btn color="pink" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
      </template>
    </v-snackbar>
  </v-container>
</template>

<script>
export default {
  name: 'BookReserveDetail',
  data: () => ({
    // 支付消息条
    snackbar: false,
    valid: true,
    // 所有商品
    items: [],
    // 订单
    reserve: {
      id: '',
      totalPrice: '',
      createdTime: '',
      phone: '',
      location: '',
      user: {
        username: ''
      }
    },
    rules: {
      usernameRules: [
        v => !!v || '用户名不能为空'
      ],
      phoneRules: [
        v => !!v || '手机号不能为空'
      ],
      locationRules: [
        v => !!v || '收货地址不能为空'
      ],
      totalPriceRules: [
        v => parseFloat(v) >= 0 || '不是一个合法的金额'
      ]
    }
  }),
  methods: {
    initialize () {
      this.getReserve()
    },
    getReserve () {
      // 获取指定订单
      this.axios.get(
        '/api/bookReserves/' + this.$route.params.reserveId,
        {
          params: {
            projection: 'inlineUser'
          }
        }
      ).then(response => {
        this.reserve = response.data
      })
    },
    updateReserve () {
      const valid = this.$refs.reserveForm.validate()
      if (valid) {
        // 更新预定信息，付尾款
        const valid = this.$refs.reserveForm.validate()
        if (valid) {
          this.axios.patch(
            this.reserve._links.self.href,
            {
              location: this.reserve.location,
              phone: this.reserve.phone
            }
          ).then(() => {
            this.snackbar = true
          })
        }
      }
    }
  },
  created () {
    this.initialize()
  }
}
</script>
