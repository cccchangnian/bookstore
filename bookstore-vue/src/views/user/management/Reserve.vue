<template>
  <v-container fluid>
    <v-row>
      <v-col cols="12">
        <v-card v-for="(item, i) in reserves" :key="i" class="my-4">
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
            <v-btn color="primary" small link :to="{ name: 'BookReserveDetail', params: { reserveId: item.id }}">
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
  name: 'BookReserveManagement',
  data: () => ({
    reserves: []
  }),
  methods: {
    initialize () {
      this.getReserves()
    },
    getReserves () {
      // 获取自己的所有订单
      this.axios.get(
        '/api/bookReserves/search/findByUserId',
        {
          params: {
            id: this.$route.params.id
          }
        }
      ).then(response => {
        this.reserves = response.data._embedded.bookReserves
      })
    }
  },
  created () {
    this.initialize()
  }
}
</script>
