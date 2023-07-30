<template>
  <v-container fluid>
    <!--购物车列表-->
    <v-row>
      <v-col>
        <v-card v-for="(item, i) in items" :key="i" class="mb-6">
          <v-row>
            <!--图书图标-->
            <v-col sm="4" class="text-center">
              <v-img v-if="item.icon" :src="item.icon" alt="图书图标" width="160" height="200" class="mx-auto mt-3 rounded"></v-img>
              <v-img v-else src="../../assets/sale/book.jpeg" alt="图书图标" width="150" height="150" class="mx-auto mt-3 rounded"></v-img>
              <!--价格-->
              <div v-if="item.price" class="mt-4">
                <span>{{ item.price.toFixed(2) }}</span>
                <span class="ml-1">RMB</span>
              </div>
            </v-col>
            <!--图书信息，包括图书名，文字介绍，评级，作者等-->
            <v-col sm="8">
              <v-card-title>{{ item.bookName }}</v-card-title>
              <v-card-subtitle>
                <v-icon class="mx-2" size="15">mdi-pencil</v-icon>
                <span>{{ item.author }}</span>
              </v-card-subtitle>
              <v-card-text>
                <!--评分-->
                <v-row align="center" class="mx-0">
                  <v-rating
                    :value="item.rating * 0.5"
                    color="amber"
                    dense
                    half-increments
                    readonly
                    size="16"
                  ></v-rating>
                  <div class="grey--text ms-4">
                    {{ item.rating.toFixed(2) }}
                  </div>
                </v-row>
                <!--简介-->
                <div class="mt-4">{{ item.description }}</div>
                <!--订购数量-->
                <div class="mt-4">
                  购买数量：{{ item.amount }}
                </div>
              </v-card-text>
            </v-col>
          </v-row>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn small color="error" @click="deleteItem(item)">Delete</v-btn>
            <v-btn small color="secondary" :to="{ name: 'BookSaleDetail', params: { inventoryId: item.inventoryId }}">
              Learn More
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <!--购买-->
    <v-row>
      <v-col>
        <v-card>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="warning" :disabled="items.length == 0" link :to="{ name: 'BookSaleOrder' }">
              立即购买
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'BookSaleShoppingCart',
  data: () => ({
    // 删除对话框
    deleteDialog: false,
    // 商品清单
    items: []
  }),
  methods: {
    initialize () {
      // 初始化购物车
      this.getItems()
    },
    getItems () {
      // 获取所有待购买的商品
      this.axios.get(
        this.$store.state.user._links.user.href + '/shoppingCart'
      ).then(response => {
        this.items = response.data
      })
    },
    deleteItem (item) {
      // 删除一个商品
      this.axios.delete(
        this.$store.state.user._links.user.href + '/shoppingCart',
        {
          data: {
            inventoryId: item.inventoryId
          }
        }
      ).then(() => {
        // 不需要重新获取，直接在前端数组中去除即可
        this.items.splice(this.items.indexOf(item), 1)
        // 关闭对话框
        this.deleteDialog = false
      })
    }
  },
  created () {
    this.initialize()
  }
}
</script>
