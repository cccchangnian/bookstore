<template>
  <!--建议使用v-container来控制布局-->
  <v-container fluid>
    <!--横幅-->
    <v-row>
      <v-col>
        <v-banner two-line>
          <!--小图标-->
          <v-avatar slot="icon" color="deep-purple accent-4" size="40">
            <v-icon icon="mdi-lock" color="white">mdi-book-open-variant</v-icon>
          </v-avatar>
          <!--文字介绍-->
          欢迎来到 -----智云书店-----
          <template v-slot:actions v-if="$store.state.accessToken === ''">
            <v-btn text color="deep-purple accent-4" @click="open('Login')">登录</v-btn>
            <v-btn text color="deep-purple accent-4" @click="open('Register')">注册</v-btn>
          </template>
          <template v-slot:actions v-else>
            <v-btn text color="deep-purple accent-4" :to="{ name : 'BookSaleHome' }">Learn More</v-btn>
          </template>
        </v-banner>
      </v-col>
    </v-row>
    <!--轮播图-->
    <v-row>
      <v-col>
         <v-carousel cycle class="rounded" hide-delimiter-background show-arrows-on-hover>
          <v-carousel-item
            v-for="(item,i) in imageList"
            :key="i"
            :src="item.src"
          >
            <v-row
              class="fill-height"
              align="center"
              justify="center"
            >
              <div class="display-3 white--text">
                Buy Now
              </div>
            </v-row>
          </v-carousel-item>
        </v-carousel>
      </v-col>
    </v-row>
    <!--热销图书横幅-->
    <v-row>
      <v-col>
        <v-banner two-line>
          <!--小图标-->
          <v-avatar slot="icon" color="blue accent-4" size="40">
            <v-icon icon="mdi-book" color="white">mdi-book</v-icon>
          </v-avatar>
          <!--文字介绍-->
          热销图书！
        </v-banner>
      </v-col>
    </v-row>
    <!--热销图书列表-->
    <v-row>
      <v-col cols="12" sm="4" md="3" v-for="(item, i ) in bookInventoryList" :key="i">
        <v-card class="mx-auto" max-width="350" min-height="550" link :to="{ name: 'BookSaleDetail', params: { inventoryId: item.id }}">
          <v-img v-if="item.icon" :src="item.icon" width="150" height="150" class="mx-auto">
          </v-img>
          <v-img v-else src="../assets/sale/book.jpeg" width="150" height="150" class="mx-auto">
          </v-img>
          <v-card-title>{{ item.bookName }}</v-card-title>
          <v-card-text>
            <!--评分-->
            <v-row align="center" class="mx-0">
              <v-rating
                :value="item.rating * 0.5"
                color="amber"
                dense
                half-increments
                readonly
                size="14"
              ></v-rating>
              <div class="grey--text ms-4">
                {{ item.rating.toFixed(2) }}
              </div>
            </v-row>
            <!--作者-->
            <div class="my-4 subtitle-1">
              作者：{{ item.author }}
            </div>
            <!--简介-->
            <div>{{ item.description }}</div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>

export default {
  name: 'Home',
  data: () => ({
    // 轮播图的图片列表
    imageList: [
      {
        src: require('../assets/home/background1.png')
      },
      {
        src: require('../assets/home/background2.png')
      },
      {
        src: require('../assets/home/background3.png')
      }
    ],
    // 热销图书列表
    bookInventoryList: []
  }),
  methods: {
    initialize () {
      this.getHotestBookInventories()
    },
    // 通过视图的名字跳转到特定视图，一般用作主页，登录页，注册页等
    open (name) {
      // 需要先判断当前路由名称是否和目标路由一致，如果一致就不跳转
      if (this.$route.name !== name) {
        this.$router.push({
          name: name
        })
      }
    },
    getHotestBookInventories () {
      // 获取热门畅销图书
      this.axios.get(
        '/api/bookInventories',
        {
          params: {
            projection: 'inlineCategory',
            sort: 'rating,desc',
            size: 8
          }
        }
      ).then(response => {
        this.bookInventoryList = response.data._embedded.bookInventories
      })
    }
  },
  created () {
    this.initialize()
  }
}
</script>
