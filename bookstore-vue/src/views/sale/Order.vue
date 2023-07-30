<template>
  <v-container fluid>
    <!--结算功能，生成一张表单就可以了-->
    <v-row>
      <v-col>
        <v-card>
          <v-card-title>订单编号：{{ form.orderId }}</v-card-title>
          <v-card-text>
            <v-form ref="orderForm" v-model="valid" lazy-validation autocomplete="off">
              <!--用户名-->
              <v-row>
                <v-col>
                  <v-text-field :value="form.user.username" readonly label="用户名" :rules="rules.usernameRules"></v-text-field>
                </v-col>
              </v-row>
              <!--手机号码-->
              <v-row>
                <v-col>
                  <v-text-field v-model="form.user.phone" label="收件人手机号码" :rules="rules.phoneRules"></v-text-field>
                </v-col>
              </v-row>
              <!--收货地址-->
              <v-row>
                <v-col>
                  <v-text-field v-model="form.user.location" label="收货地址（请填写具体的地址，否则交易无效）" :rules="rules.locationRules"></v-text-field>
                </v-col>
              </v-row>
              <!--总交易金额-->
              <v-row>
                <v-col>
                  <v-text-field :value="form.totalPrice" label="总金额" :rules="rules.totalPriceRules"></v-text-field>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
          <!--交互按钮-->
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn :disabled="!valid" color="success" @click="order()">
              确认支付
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <!--显示支付结果-->
    <v-snackbar v-model="snackbar">
      支付成功！
      <template v-slot:action="{ attrs }">
        <v-btn color="pink" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
      </template>
    </v-snackbar>
  </v-container>
</template>

<script>
export default {
  name: 'BookSaleOrder',
  data: () => ({
    valid: true,
    snackbar: false,
    // 所有商品
    items: [],
    form: {
      orderId: '',
      user: {
        id: 0,
        username: '',
        phone: '',
        location: ''
      },
      totalPrice: ''
    },
    rules: {
      usernameRules: [
        v => !!v || '用户名不能为空'
      ],
      phoneRules: [
        v => !!v || '手机号不能为空'
      ],
      locationRules: [
        v => !!v || '收货地址不能为空'
      ],
      totalPriceRules: [
        v => parseFloat(v) >= 0 || '不是一个合法的金额'
      ]
    }
  }),
  methods: {
    initialize () {
      this.form.user = Object.assign({}, this.$store.state.user)
      // 生成订单id
      this.generatedOrderId()
      this.getItems()
    },
    generatedOrderId () {
      // 生成一个订单id，使用雪花算法
      class Snowflake {
        constructor (_workerId, _dataCenterId, _sequence) {
          this.twepoch = 1288834974657n
          // this.twepoch = 0n
          this.workerIdBits = 5n
          this.dataCenterIdBits = 5n
          this.maxWrokerId = -1n ^ (-1n << this.workerIdBits) // 值为：31
          this.maxDataCenterId = -1n ^ (-1n << this.dataCenterIdBits) // 值为：31
          this.sequenceBits = 12n
          this.workerIdShift = this.sequenceBits // 值为：12
          this.dataCenterIdShift = this.sequenceBits + this.workerIdBits // 值为：17
          this.timestampLeftShift = this.sequenceBits + this.workerIdBits + this.dataCenterIdBits // 值为：22
          this.sequenceMask = -1n ^ (-1n << this.sequenceBits) // 值为：4095
          this.lastTimestamp = -1n
          // 设置默认值,从环境变量取
          this.workerId = 1n
          this.dataCenterId = 1n
          this.sequence = 0n
          if (this.workerId > this.maxWrokerId || this.workerId < 0) {
            throw new Error(`_workerId must max than 0 and small than maxWrokerId-[${this.maxWrokerId}]`)
          }
          if (this.dataCenterId > this.maxDataCenterId || this.dataCenterId < 0) {
            throw new Error(`_dataCenterId must max than 0 and small than maxDataCenterId-[${this.maxDataCenterId}]`)
          }

          this.workerId = _workerId
          this.dataCenterId = _dataCenterId
          this.sequence = _sequence
        }

        tilNextMillis (lastTimestamp) {
          let timestamp = this.timeGen()
          while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen()
          }
          // eslint-disable-next-line no-undef
          return BigInt(timestamp)
        }

        timeGen () {
          // eslint-disable-next-line no-undef
          return BigInt(Date.now())
        }

        nextId () {
          let timestamp = this.timeGen()
          if (timestamp < this.lastTimestamp) {
            throw new Error(`Clock moved backwards. Refusing to generate id for ${this.lastTimestamp - timestamp}`)
          }
          if (this.lastTimestamp === timestamp) {
            this.sequence = (this.sequence + 1n) & this.sequenceMask
            if (this.sequence === 0n) {
              timestamp = this.tilNextMillis(this.lastTimestamp)
            }
          } else {
            this.sequence = 0n
          }
          this.lastTimestamp = timestamp
          return ((timestamp - this.twepoch) << this.timestampLeftShift) |
            (this.dataCenterId << this.dataCenterIdShift) |
            (this.workerId << this.workerIdShift) |
            this.sequence
        }
      }
      const snowflake = new Snowflake(1n, 1n, 0n)
      this.form.orderId = 'BO' + snowflake.nextId().toString()
    },
    getItems () {
      // 获取所有商品，简略显示，并且计算总价
      this.axios.get(
        this.$store.state.user._links.user.href + '/shoppingCart'
      ).then(response => {
        this.items = response.data
        let totalPrice = 0
        this.items.forEach((value) => {
          totalPrice += value.price * value.amount
        })
        this.form.totalPrice = totalPrice.toFixed(2)
      })
    },
    order () {
      // 提交订单，进行支付，实际上这里只是简单的保存一下交易信息
      const valid = this.$refs.orderForm.validate()
      if (valid) {
        this.axios.post(
          '/api/bookOrders',
          {
            id: this.form.orderId,
            totalPrice: this.form.totalPrice,
            buyer: this.$store.state.user._links.user.href,
            phone: this.form.user.phone,
            location: this.form.user.location
          }
        ).then(() => {
          // 支付成功后，显示支付成功信息
          this.snackbar = true
        })
      }
    }
  },
  created () {
    this.initialize()
  }
}
</script>
