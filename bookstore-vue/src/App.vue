<template>
  <!--v-app，有且只有一个-->
  <v-app id="app">
    <!--导航侧栏，clipped属性用于将侧栏置于应用栏下方，drawer用于侧栏的显示和隐藏-->
    <v-navigation-drawer app clipped v-model="drawer">
      <!--左上方的头像、用户名和邮箱的显示-->
      <v-list>
        <v-list-item two-line>
          <!--头像-->
          <v-list-item-avatar>
            <v-img v-if="$store.state.user.icon" :src="$store.state.user.icon" alt="头像"></v-img>
            <v-img v-else src="./assets/logo.png" alt="头像"></v-img>
          </v-list-item-avatar>
          <!--简介-->
          <v-list-item-content>
            <!--用户名-->
            <v-list-item-title v-if="$store.state.user.username">{{ $store.state.user.username }}</v-list-item-title>
            <v-list-item-title v-else>游客</v-list-item-title>
            <!--邮箱-->
            <v-list-item-subtitle v-if="$store.state.user.email">{{ $store.state.user.email }}</v-list-item-subtitle>
            <v-list-item-subtitle v-else>example@mail.com</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-list>
      <!--一条分割线-->
      <v-divider></v-divider>
      <!--导航列表-->
      <v-list nav dense>
        <!--主页-->
        <v-list-item link @click="open('Home')">
          <!--mdi图标，home-->
          <v-list-item-icon>
            <v-icon>mdi-home</v-icon>
          </v-list-item-icon>
          <!--文字说明-->
          <v-list-item-title>主页</v-list-item-title>
        </v-list-item>
        <!--书店销售主页，可以通过这个页面跳转到其它分类-->
        <v-list-item link @click="open('BookSaleHome')">
          <!--mdi图标，小商店-->
          <v-list-item-icon>
            <v-icon>mdi-store</v-icon>
          </v-list-item-icon>
          <!--文字说明-->
          <v-list-item-title>书店</v-list-item-title>
        </v-list-item>
        <!--租借主页，可以通过这个页面跳转到其它分类-->
        <v-list-item link @click="open('BookRentHome')">
          <!--mdi图标，小商店-->
          <v-list-item-icon>
            <v-icon>mdi-timer</v-icon>
          </v-list-item-icon>
          <!--文字说明-->
          <v-list-item-title>租借</v-list-item-title>
        </v-list-item>
        <!--个人管理界面，没有登录时是隐藏的-->
        <v-list-group v-if="$store.state.user.username !== ''" prepend-icon="mdi-account" no-action>
          <template v-slot:activator>
            <v-list-item-content>
              <v-list-item-title>我的</v-list-item-title>
            </v-list-item-content>
          </template>
          <!--个人信息部分-->
          <v-list-group no-action sub-group v-for="(item, i) in userInfos" :key="i">
            <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title>{{ item.title }}</v-list-item-title>
              </v-list-item-content>
            </template>
            <!--个人信息部分子菜单-->
            <v-list-item v-for="(subItem, j) in item.items" :key="j" link @click="openDetail(subItem.target, $store.state.user.id)">
              <v-list-item-title>{{ subItem.title }}</v-list-item-title>
              <v-list-item-icon>
                <v-icon>{{ subItem.icon }}</v-icon>
              </v-list-item-icon>
            </v-list-item>
          </v-list-group>
        </v-list-group>
      </v-list>
      <!--导航侧栏底部插槽-->
      <template v-slot:append>
        <!--登录按钮-->
        <div v-if="$store.state.user.username === ''" class="pa-2">
          <v-btn block color="primary" @click="open('Login')">登录</v-btn>
        </div>
        <!--注册按钮-->
        <div v-if="$store.state.user.username === ''" class="pa-2 mb-3">
          <v-btn block color="success" @click="open('Register')">注册</v-btn>
        </div>
        <!--注销按钮，登录成功后才可见-->
        <div v-if="$store.state.user.username !== ''" class="pa-2 mb-3">
          <v-btn block color="error" @click="logout()">注销</v-btn>
        </div>
      </template>
    </v-navigation-drawer>

    <!--顶部导航栏，clipped-left用于控制导航侧栏在左边-->
    <v-app-bar app clipped-left dense color="#212121">
      <!--顶部导航栏左侧的一个小按钮，点击之后会显示或隐藏导航侧栏-->
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" color="white"></v-app-bar-nav-icon>
    </v-app-bar>

    <!--页面主要内容部分，基本上以视图替换的形式体现-->
    <v-main>
      <!--结合v-router，修改index.js文件，可以进行视图的替换-->
      <router-view></router-view>
    </v-main>

  </v-app>
</template>

<script>

export default {
  name: 'App',

  data: () => ({
    // 控制导航侧栏的显示和隐藏
    drawer: true,
    userInfos: [
      {
        title: '个人信息',
        items: [
          {
            title: '基本信息',
            target: 'UserBaseInformation',
            icon: 'mdi-account'
          },
          {
            title: '头像修改',
            target: 'UserIcon',
            icon: 'mdi-image'
          },
          {
            title: '密码修改',
            target: 'UserPassword',
            icon: 'mdi-lock'
          },
          {
            title: '账户安全',
            target: 'UserSafety',
            icon: 'mdi-email'
          },
          {
            title: '收货地址',
            target: 'UserLocation',
            icon: 'mdi-car'
          },
          {
            title: '手机号码',
            target: 'UserPhone',
            icon: 'mdi-phone'
          }
        ]
      },
      {
        title: '个人管理',
        items: [
          {
            title: '购物车',
            target: 'BookSaleShoppingCart',
            icon: 'mdi-book'
          },
          {
            title: '我的订单',
            target: 'BookSaleOrderManagement',
            icon: 'mdi-domain'
          },
          {
            title: '租借管理',
            target: 'BookRentManagement',
            icon: 'mdi-domain'
          }
          /* {
            title: '预定管理',
            target: 'BookReserveManagement',
            icon: 'mdi-pencil'
          } */
        ]
      }
    ]
  }),
  methods: {
    // 通过视图的名字跳转到特定视图，一般用作主页，登录页，注册页等
    open (name) {
      // 需要先判断当前路由名称是否和目标路由一致，如果一致就不跳转
      if (this.$route.name !== name) {
        this.$router.push({
          name: name
        })
      }
    },
    // 跳转到具体的视图，一般用作详情页，用户页面等需要id的情况
    openDetail (name, id) {
      // 需要先判断当前路由名称是否和目标路由一致，如果一致就不跳转
      if (this.$route.name !== name) {
        this.$router.push({
          name: name,
          params: {
            id: id
          }
        })
      }
    },
    // 注销
    logout () {
      // 清除用户
      this.$store.commit('removeUser')
      // 同时清除token
      this.$store.commit('removeToken')
      // 注销以后跳转到主页
      if (this.$route.name !== 'Home') {
        this.$router.push({
          name: 'Home'
        })
      }
    }
  },
  created () {
    // 初始化时加载user
    this.$store.commit('loadUser')
  }
}
</script>
