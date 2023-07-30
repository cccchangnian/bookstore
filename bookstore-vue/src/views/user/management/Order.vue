<template>
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
            <span>{{ item.totalPrice }}</span>
            <span class="ml-1">RMB</span>
          </v-card-text>
          <!--动作-->
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" small link :to="{ name: 'BookSaleOrderDetail', params: { orderId: item.id }}">
              Learn More
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'BookSaleOrderManagement',
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
        '/api/bookOrders/search/findByBuyerId',
        {
          params: {
            id: this.$route.params.id
          }
        }
      ).then(response => {
        this.orders = response.data._embedded.bookOrders
      })
    }
  },
  created () {
    this.initialize()
  }
}
</script>
