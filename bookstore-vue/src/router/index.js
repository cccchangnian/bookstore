import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    // 登录视图
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    // 注册视图
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    // 销售主页
    path: '/sale',
    name: 'BookSaleHome',
    component: () => import('../views/sale/Home.vue')
  },
  {
    // 销售列表页
    path: '/sale/:categoryId',
    name: 'BookSaleList',
    component: () => import('../views/sale/List.vue')
  },
  {
    // 销售详情页
    path: '/sale/inventories/:inventoryId',
    name: 'BookSaleDetail',
    component: () => import('../views/sale/Detail.vue')
  },
  {
    // 销售支付页
    path: '/sale/order',
    name: 'BookSaleOrder',
    component: () => import('../views/sale/Order.vue')
  },
  {
    // 销售临时支付页
    path: '/sale/order/temp',
    name: 'BookSaleTempOrder',
    component: () => import('../views/sale/TempOrder.vue')
  },
  {
    // 租借主页
    path: '/rent',
    name: 'BookRentHome',
    component: () => import('../views/rent/Home.vue')
  },
  {
    // 租借列表页
    path: '/rent/:categoryId',
    name: 'BookRentList',
    component: () => import('../views/rent/List.vue')
  },
  {
    // 租借详情页
    path: '/rent/inventories/:inventoryId',
    name: 'BookRentDetail',
    component: () => import('../views/rent/Detail.vue')
  },
  {
    // 租借临时支付页
    path: '/rent/order/temp',
    name: 'BookRentTempOrder',
    component: () => import('../views/rent/TempOrder.vue')
  },
  {
    // 用户后台管理
    path: '/admin/users',
    name: 'UserAdmin',
    component: () => import('../views/admin/user/User.vue')
  },
  {
    // 用户权限后台管理
    path: '/admin/authorities',
    name: 'AuthorityAdmin',
    component: () => import('../views/admin/user/Authority.vue')
  },
  {
    // 用户个人基本信息
    path: '/:id/information',
    name: 'UserBaseInformation',
    component: () => import('../views/user/BaseInformation.vue')
  },
  {
    // 用户头像
    path: '/:id/icon',
    name: 'UserIcon',
    component: () => import('../views/user/Icon.vue')
  },
  {
    // 用户密码修改
    path: '/:id/password',
    name: 'UserPassword',
    component: () => import('../views/user/Password.vue')
  },
  {
    // 用户账户安全
    path: '/:id/safety',
    name: 'UserSafety',
    component: () => import('../views/user/Safety.vue')
  },
  {
    // 用户收货地址
    path: '/:id/location',
    name: 'UserLocation',
    component: () => import('../views/user/Location.vue')
  },
  {
    // 用户手机号码
    path: '/:id/phone',
    name: 'UserPhone',
    component: () => import('../views/user/Phone.vue')
  },
  {
    // 购物车
    path: '/:id/sale/shoppingCart',
    name: 'BookSaleShoppingCart',
    component: () => import('../views/shoppingcart/BookSale.vue')
  },
  {
    // 订单管理（实际上只能看）
    path: '/:id/sale/orders',
    name: 'BookSaleOrderManagement',
    component: () => import('../views/user/management/Order.vue')
  },
  {
    // 销售订单详情
    path: '/orders/sale/:orderId',
    name: 'BookSaleOrderDetail',
    component: () => import('../views/user/management/OrderDetail.vue')
  },
  {
    // 租借管理，在这里可以进行对未归还图书的支付操作
    path: '/:id/rent/orders',
    name: 'BookRentManagement',
    component: () => import('../views/user/management/RentOrder.vue')
  },
  {
    // 租借订单详情
    path: '/orders/rent/:orderId',
    name: 'BookRentOrderDetail',
    component: () => import('../views/user/management/RentOrderDetail.vue')
  },
  {
    // 预定管理
    path: '/:id/reserve',
    name: 'BookReserveManagement',
    component: () => import('../views/user/management/Reserve.vue')
  },
  {
    // 租借订单详情
    path: '/reserve/:reserveId',
    name: 'BookReserveDetail',
    component: () => import('../views/user/management/ReserveDetail.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
