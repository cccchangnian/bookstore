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
                  <v-text-field :value="order.buyer.username" readonly label="用户名" :rules="rules.usernameRules"></v-text-field>
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
              <!--总交易金额-->
              <v-row>
                <v-col>
                  <v-text-field :value="order.totalPrice" readonly label="总金额" :rules="rules.totalPriceRules"></v-text-field>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'BookSaleOrderDetail',
  data: () => ({
    valid: true,
    // 所有商品
    items: [],
    // 订单
    order: {
      id: '',
      totalPrice: '',
      createdTime: '',
      phone: '',
      location: '',
      buyer: {
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
        '/api/bookOrders/' + this.$route.params.orderId,
        {
          params: {
            projection: 'inlineBuyer'
          }
        }
      ).then(response => {
        console.log(response.data)
        this.order = response.data
      })
    }
  },
  created () {
    this.initialize()
  }
}
</script>
