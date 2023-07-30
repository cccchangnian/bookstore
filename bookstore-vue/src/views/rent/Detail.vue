<template>
  <v-container fluid>
    <!--图书详情-->
    <v-row>
      <v-col>
        <v-card class="my-2">
          <v-row>
            <!--图书图标-->
            <v-col sm="4" class="text-center">
              <v-img v-if="bookInventory.icon" :src="bookInventory.icon" width="160" height="200" class="mx-auto mt-3 rounded"></v-img>
              <v-img v-else src="../../assets/sale/book.jpeg" alt="图书图标" width="150" height="150" class="mx-auto mt-3 rounded"></v-img>
              <!--价格-->
              <div v-if="bookInventory.price" class="mt-4">
                <span>{{ bookInventory.price.toFixed(2) }}</span>
                <span class="ml-1">RMB</span>
              </div>
            </v-col>
            <!--图书信息，包括图书名，文字介绍，评级，作者等-->
            <v-col sm="8">
              <v-card-title>{{ bookInventory.bookName }}</v-card-title>
              <v-card-subtitle class="mt-1">
                <v-icon class="mx-2" size="18">mdi-pencil</v-icon>
                <span style="font-size: 18px">{{ bookInventory.author }}</span>
                <v-icon class="mx-2" size="18">mdi-store</v-icon>
                <span style="font-size: 18px">{{ bookInventory.amount }}</span>
                <span v-if="bookInventory.amount === 0" style="font-size: 18px" class="ml-2">(库存暂缺)</span>
              </v-card-subtitle>
              <v-card-text class="mt-1">
                <!--评分-->
                <v-row align="center" class="mx-0">
                  <v-rating
                    :value="bookInventory.rating * 0.5"
                    color="amber"
                    dense
                    half-increments
                    readonly
                    size="18"
                  ></v-rating>
                  <div class="grey--text ms-4">
                    {{ bookInventory.rating.toFixed(2) }}
                  </div>
                </v-row>
                <!--简介-->
                <div class="mt-4">{{ bookInventory.description }}</div>
              </v-card-text>
            </v-col>
          </v-row>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn icon>
              <v-icon>mdi-heart</v-icon>
            </v-btn>
            <v-btn icon>
              <v-icon>mdi-star</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <!--购买数量、购买按钮、加入购物车按钮-->
    <v-row>
      <v-col>
        <v-card>
          <v-card-actions>
            <!--减按钮-->
            <v-btn small @click="minus()">
              <v-icon>mdi-minus</v-icon>
            </v-btn>
            <!--数量-->
            <v-chip
              class="ma-2"
              color="green"
              text-color="white"
            >
              <v-avatar
                left
                class="green darken-4"
              >
                {{ number }}
              </v-avatar>
              numbers
            </v-chip>
            <!--加按钮-->
            <v-btn small @click="plus()">
              <v-icon>mdi-plus</v-icon>
            </v-btn>
            <!--以下都是需要登录后才可见-->
            <v-btn v-if="bookInventory.amount > 0" class="warning" small :disabled="$store.state.accessToken === ''" @click="tempShoppingCart()">
              租借
            </v-btn>
            <v-btn v-else class="error" small>
              库存暂缺，无法租借
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <!--评论，需要登录后才能发表-->
    <!-- <v-row>
      <v-col>
        <v-form v-if="$store.state.accessToken !== ''">
          <v-textarea outlined></v-textarea>
          <v-btn color="primary">Comment</v-btn>
        </v-form>
        <v-alert v-else type="warning">请先登录后再发表评论！</v-alert>
      </v-col>
    </v-row> -->
    <!--提示，用于反馈加入购物车是否成功-->
    <v-snackbar
      v-model="snackbar"
    >
      加入购物车成功！
      <template v-slot:action="{ attrs }">
        <v-btn
          color="pink"
          text
          v-bind="attrs"
          @click="snackbar = false"
        >
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </v-container>
</template>

<script>
export default {
  name: 'BookRentDetail',
  data: () => ({
    // 用于提示
    snackbar: false,
    // 弹出对话框，显示购物车的确定加入购物车
    shoppingcartDialog: false,
    // 想购买图书的数量
    number: 1,
    // 图书库存，主要还是用的这个
    bookInventory: {
      id: 0,
      bookName: '',
      author: '',
      description: '',
      price: '',
      rating: 0.0,
      amount: 0,
      icon: ''
    }
  }),
  methods: {
    initialize () {
      this.getInventory()
    },
    getInventory () {
      // 获取特定的库存
      this.axios.get(
        '/api/rentedBookInventories/' + this.$route.params.inventoryId,
        {
          params: {
            projection: 'inlineCategory'
          }
        }
      ).then(response => {
        this.bookInventory = response.data
      })
    },
    minus () {
      if (this.number > 1) {
        this.number -= 1
      }
    },
    plus () {
      if (this.number < this.bookInventory.amount) {
        this.number += 1
      }
    },
    tempShoppingCart () {
      // 临时购物车，租借就相当于把商品加入临时购物车，然后跳转到订单页面，对临时购物车进行结算，租借的临时购物车不等同于立即购买的临时购物车
      this.axios.post(
        this.$store.state.user._links.user.href + '/rent/tempShoppingCart',
        {
          inventoryId: this.bookInventory.id,
          amount: this.number
        }
      ).then(response => {
        // 加入成功后，直接跳转到订单支付界面，此处支付不进行实际的支付，只会显示每周需要支付的金额，后续还书的时候会找到相应订单对金额补全
        this.$router.push({
          name: 'BookRentTempOrder'
        })
      })
    }
  },
  created () {
    this.initialize()
  }
}
</script>
