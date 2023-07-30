<template>
  <v-container fluid>
    <!--一个小横幅，说明这里是用作图书销售的-->
    <v-row>
      <v-col cols="12">
        <v-banner two-line>
          <!--小图标-->
          <v-avatar slot="icon" color="deep-purple accent-4" size="40">
            <v-icon icon="mdi-lock" color="white">mdi-book</v-icon>
          </v-avatar>
          <!--文字介绍-->
          你想要的书这里都有！
        </v-banner>
      </v-col>
    </v-row>
    <!--新品上架-->
    <v-row>
      <v-col cols="12">
        <!--新品上架的文字-->
        <v-row justify="center">
          <div class="display-2 orange--text text-center">新书销售</div>
        </v-row>
        <!--小字，图书推荐-->
        <v-row justify="center">
          <div class="grey--text">智云书店为您推荐以下图书</div>
        </v-row>
        <!--图书，只展示四本-->
        <v-row>
          <v-col sm="2" v-for="(item, i) in bookInventories" :key="i">
            <v-sheet @click="openDetail(item.id)">
              <v-img v-if="item.icon" :src="item.icon" alt="销售图书" width="180" height="220" class="mx-auto"></v-img>
              <v-img v-else src="../../assets/sale/book.jpeg" alt="销售图书" width="180" height="220" class="mx-auto"></v-img>
              <div class="text-center mt-2"><span>{{ item.price }}</span><span class="ml-1">RMB PER WEEK</span></div>
            </v-sheet>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
    <!--卖家评论-->
    <v-row>
      <v-col cols="12">
        <v-timeline :dense="$vuetify.breakpoint.smAndDown">
          <v-timeline-item
            v-for="(item, i) in users"
            :key="i"
            large
          >
            <template v-slot:icon>
              <v-avatar>
                <img src="../../assets/logo.png">
              </v-avatar>
            </template>
            <template v-slot:opposite>
              <span>{{ item.username }}</span>
            </template>
            <v-card class="elevation-2">
              <v-card-title class="text-h5">
                {{ item.title }}
              </v-card-title>
              <v-card-text>{{ item.comment }}</v-card-text>
            </v-card>
          </v-timeline-item>
        </v-timeline>
      </v-col>
    </v-row>
    <!--分类-->
    <v-row>
      <v-col>
        <v-row>
          <div class="display-2 ml-2">更多分类</div>
        </v-row>
        <v-row>
          <v-col sm="2" v-for="(item, i) in categories" :key="i">
            <v-btn outlined color="primary" width="100" link :to="{ name: 'BookRentList', params: { categoryId: item.id }}">{{ item.name }}</v-btn>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'BookRentHome',
  data: () => ({
    // 各种分类
    categories: [],
    // 图书
    bookInventories: [],
    // 用户评论
    users: [
      {
        username: ' ',
        title: '智云书店：开启阅读新时代！',
        comment: '智云书店，带您进入一个全新的阅读时代，在这里，我们不仅为您提供最新最热门的图书作品，更重要的是，我们将借助智能技术，为您提供个性化、精准推荐，让您的阅读体验更加丰富多彩！'
      },
      {
        username: ' ',
        title: '智云书店：点亮独特思考之路！',
        comment: '在智云书店，我们希望将拥有智慧与知识的喜悦带给每一个读者。不论您是沉醉于经典文学作品的情怀，还是对科学、历史、社会等方面的探索充满好奇，我们相信，在您与文字相遇的瞬间，智慧之火将在您心中燃起，点亮您独特的思考之路！'
      }
    ]
  }),
  methods: {
    initialize () {
      // 初始化页面，调用多个方法
      this.getCategories()
      this.getLatestBookInventories()
    },
    getCategories () {
      // 获取所有分类
      this.axios.get(
        '/api/rentedBookCategories'
      ).then(response => {
        this.categories = response.data._embedded.rentedBookCategories
      })
    },
    getLatestBookInventories () {
      // 获取最新录入的图书
      this.axios.get(
        '/api/rentedBookInventories',
        {
          params: {
            size: 6
          }
        }
      ).then(response => {
        this.bookInventories = response.data._embedded.rentedBookInventories
      })
    },
    openDetail (id) {
      this.$router.push({
        name: 'BookRentDetail',
        params: {
          inventoryId: id
        }
      })
    }
  },
  created () {
    // 页面加载时，调用初始化
    this.initialize()
  }
}
</script>
