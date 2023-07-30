<template>
  <v-container fluid>
    <!--结算功能，生成一张表单就可以了-->
    <v-row>
      <v-col>
        <v-card>
          <v-card-title>订单编号：{{ order.id }}</v-card-title>
          <v-card-text>
            <v-form ref="orderForm" v-model="valid" lazy-validation autocomplete="off">
              <!--用户名-->
              <v-row>
                <v-col>
                  <v-text-field :value="order.owner.username" readonly label="用户名" :rules="rules.usernameRules"></v-text-field>
                </v-col>
              </v-row>
              <!--手机号码-->
              <v-row>
                <v-col>
                  <v-text-field :value="order.phone" readonly label="收件人手机号码" :rules="rules.phoneRules"></v-text-field>
                </v-col>
              </v-row>
              <!--收货地址-->
              <v-row>
                <v-col>
                  <v-text-field v-model="order.location" readonly label="收货地址（请填写具体的地址，否则交易无效）" :rules="rules.locationRules"></v-text-field>
                </v-col>
              </v-row>
              <!--总交易金额，通过计算得出-->
              <v-row>
                <v-col>
                  <v-text-field :value="order.totalPrice" readonly label="总金额" :rules="rules.totalPriceRules"></v-text-field>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
          <!--支付尾款-->
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn :disabled="!valid" color="primary" @click="updateRent()">
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
  name: 'BookRentOrderDetail',
  data: () => ({
    // 提示
    snackbar: false,
    valid: true,
    // 所有商品
    items: [],
    // 订单
    order: {
      id: '',
      totalPrice: '',
      price: '',
      createdTime: '',
      phone: '',
      location: '',
      owner: {
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
      this.getOrder()
    },
    getOrder () {
      // 获取指定订单
      this.axios.get(
        '/api/rentedBookOrders/' + this.$route.params.orderId,
        {
          params: {
            projection: 'inlineOwner'
          }
        }
      ).then(response => {
        this.order = response.data
        // 计算相差周数
        const delta = parseInt(new Date() - new Date(this.order.createdTime)) / 1000 / 86400 / 7
        // 总金额为周数乘以每周金额
        this.order.totalPrice = (delta * parseFloat(this.order.price)).toFixed(2)
        if (parseFloat(this.order.totalPrice) < parseFloat(this.order.price)) {
          this.order.totalPrice = this.order.price
        }
      })
    },
    updateRent () {
      const valid = this.$refs.orderForm.validate()
      if (valid) {
        this.axios.patch(
          this.order._links.self.href,
          {
            totalPrice: this.order.totalPrice
          }
        ).then(() => {
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
