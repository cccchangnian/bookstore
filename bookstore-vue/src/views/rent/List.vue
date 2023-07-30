<template>
  <v-container fluid>
    <!--导航栏-->
    <v-row>
      <v-col cols="12">
        <v-sheet elevation="6">
          <v-tabs next-icon="mdi-arrow-right-bold-box-outline" prev-icon="mdi-arrow-left-bold-box-outline" show-arrows>
            <v-tabs-slider color="#212121"></v-tabs-slider>
            <v-tab v-for="item in categories" :key="item.id" link :to="{ name : 'BookRentList', params: { categoryId: item.id }}" @click="open('BookRentList', item.id)">
              {{ item.name }}
            </v-tab>
          </v-tabs>
        </v-sheet>
      </v-col>
    </v-row>
    <!--图书和热销（对应分类）-->
    <v-row>
      <v-col sm="8">
        <v-card v-show="!model" v-for="(item, i) in bookInventories" :key="i" class="mb-6">
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
                <v-icon class="mx-2" size="15">mdi-store</v-icon>
                <span>{{ item.amount }}</span>
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
              </v-card-text>
            </v-col>
          </v-row>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn small color="secondary" :to="{ name: 'BookRentDetail', params: { inventoryId: item.id }}">
              Learn More
            </v-btn>
          </v-card-actions>
        </v-card>
        <v-row v-show="model">
          <v-col sm="4" v-for="(item, i) in bookInventories" :key="i">
            <v-card class="text-center" link :to="{ name: 'BookRentDetail', params: { inventoryId: item.id }}">
              <v-img v-if="item.icon" :src="item.icon" alt="图书图标" width="160" height="200" class="mx-auto mt-3 rounded"></v-img>
              <v-img v-else src="../../assets/sale/book.jpeg" alt="图书图标" width="150" height="150" class="mx-auto mt-3 rounded"></v-img>
              <!--价格-->
              <div v-if="item.price" class="mt-4">
                <span>{{ item.price.toFixed(2) }}</span>
                <span class="ml-1">RMB</span>
              </div>
            </v-card>
          </v-col>
        </v-row>
        <!--分页，v-if可以让分页加载的更自然，不会出现什么都没有却有分页的情况-->
        <v-pagination v-if="page.totalPages" v-model="page.number" :length="page.totalPages" :total-visible="5" v-on:previous="last(page)" v-on:next="next(page)" v-on:input="appointed(page)"></v-pagination>
      </v-col>
      <!--热销图书-->
      <v-col sm="4">
        <v-switch v-model="model" inset label="切换视图" color="primary" class="ml-2"></v-switch>
        <v-card>
          <v-card-title>
            <span>热租图书</span>
            <v-icon class="ml-2">mdi-fire</v-icon>
          </v-card-title>
          <v-card-subtitle>Book Recommand</v-card-subtitle>
          <v-card-text>
            <v-list two-line>
              <v-list-item v-for="(item, i) in hotBookInventories" :key="i" link :to="{ name: 'BookRentDetail', params: { inventoryId: item.id }}">
                <v-list-item-content>
                  <v-list-item-title>
                    {{ item.bookName }}
                  </v-list-item-title>
                  <v-list-item-subtitle class="mt-1">
                    <v-rating :value="item.rating * 0.5" color="purple" dense half-increments readonly size="14">
                    </v-rating>
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'BookRentList',
  data: () => ({
    // 切换视图
    model: false,
    // 各种分类
    categories: [],
    // 图书
    bookInventories: [],
    // 热销图书
    hotBookInventories: [],
    // 记录当前是在第几页
    page: {
      size: 10,
      totalElements: 0,
      totalPages: 0,
      number: 0
    }
  }),
  methods: {
    initialize () {
      // 初始化页面，调用多个方法
      this.getCategories()
      this.getLatestBookInventories()
      this.getHotestBookInventories()
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
        '/api/rentedBookInventories/search/findByCategoryId',
        {
          params: {
            categoryId: this.$route.params.categoryId,
            size: 12
          }
        }
      ).then(response => {
        this.bookInventories = response.data._embedded.rentedBookInventories
        this.page = response.data.page
        this.page.number += 1
      })
    },
    getHotestBookInventories () {
      // 获取热门（评分价高的几本图书）
      this.axios.get(
        '/api/rentedBookInventories/search/findByCategoryId',
        {
          params: {
            categoryId: this.$route.params.categoryId,
            projection: 'inlineCategory',
            sort: 'rating,desc'
          }
        }
      ).then(response => {
        this.hotBookInventories = response.data._embedded.rentedBookInventories
      })
    },
    next (page) {
      // 下一页
      this.axios.get(
        '/api/rentedBookInventories/search/findByCategoryId',
        {
          params: {
            categoryId: this.$route.params.categoryId,
            page: page.number
          }
        }
      ).then(response => {
        this.bookInventories = response.data._embedded.rentedBookInventories
        this.page = response.data.page
        this.page.number += 1
      })
    },
    last (page) {
      // 上一页
      this.axios.get(
        '/api/rentedBookInventories/search/findByCategoryId',
        {
          params: {
            categoryId: this.$route.params.categoryId,
            page: page.number - 2
          }
        }
      ).then(response => {
        this.bookInventories = response.data._embedded.rentedBookInventories
        this.page = response.data.page
        this.page.number += 1
      })
    },
    appointed (page) {
      // 指定页
      this.axios.get(
        '/api/rentedBookInventories/search/findByCategoryId',
        {
          params: {
            categoryId: this.$route.params.categoryId,
            page: page.number - 1
          }
        }
      ).then(response => {
        this.bookInventories = response.data._embedded.rentedBookInventories
        this.page = response.data.page
        this.page.number += 1
      })
    },
    open (name, categoryId) {
      // 跳转路由，并且更换内容
      // this.$router.push({
      //   name: name,
      //   params: {
      //     categoryId: categoryId
      //   }
      // })
      this.getLatestBookInventories()
      this.getHotestBookInventories()
    }
  },
  created () {
    // 页面加载时，调用初始化
    this.initialize()
  }
}
</script>
