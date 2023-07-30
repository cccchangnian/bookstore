<template>
  <!--图书租借管理，这里会显示借的图书，点击归还会跳转到订单支付页面-->
  <v-container fluid>
    <v-row>
      <v-col cols="12">
        <v-card v-for="(item, i) in orders" :key="i" class="my-4">
          <!--订单号-->
          <v-card-title>{{ item.id }}</v-card-title>
          <!--订单交易时间-->
          <v-card-subtitle>{{ item.createdTime }}</v-card-subtitle>
          <!--订单收货地址-->
          <v-card-text>
            <span v-if="item.totalPrice == 0" class="ml-1">租借中</span>
            <span v-else class="ml-1">已归还</span>
          </v-card-text>
          <!--动作-->
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn v-if="item.totalPrice == 0" color="primary" small link :to="{ name: 'BookRentOrderDetail', params: { orderId: item.id }}">
              支付订单
            </v-btn>
            <v-btn v-else color="success" small>
              支付完成
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'BookRentManagement',
  data: () => ({
    orders: []
  }),
  methods: {
    initialize () {
      this.getOrders()
    },
    getOrders () {
      // 获取自己的所有订单
      this.axios.get(
        '/api/rentedBookOrders/search/findByOwnerId',
        {
          params: {
            ownerId: this.$route.params.id
          }
        }
      ).then(response => {
        this.orders = response.data._embedded.rentedBookOrders
      })
    }
  },
  created () {
    this.initialize()
  }
}
</script>
