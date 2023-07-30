(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d21f46f"],{d8af:function(t,e,a){"use strict";a.r(e);var o=function(){var t=this,e=t.$createElement,o=t._self._c||e;return o("v-container",{attrs:{fluid:""}},[o("v-row",[o("v-col",{attrs:{cols:"12"}},[o("v-sheet",{attrs:{elevation:"6"}},[o("v-tabs",{attrs:{"next-icon":"mdi-arrow-right-bold-box-outline","prev-icon":"mdi-arrow-left-bold-box-outline","show-arrows":""}},[o("v-tabs-slider",{attrs:{color:"#212121"}}),t._l(t.categories,(function(e){return o("v-tab",{key:e.id,attrs:{link:"",to:{name:"BookSaleList",params:{categoryId:e.id}}},on:{click:function(a){return t.open("BookSaleList",e.id)}}},[t._v(" "+t._s(e.name)+" ")])}))],2)],1)],1)],1),o("v-row",[o("v-col",{attrs:{sm:"8"}},[t._l(t.bookInventories,(function(e,s){return o("v-card",{directives:[{name:"show",rawName:"v-show",value:!t.model,expression:"!model"}],key:s,staticClass:"mb-6"},[o("v-row",[o("v-col",{staticClass:"text-center",attrs:{sm:"4"}},[e.icon?o("v-img",{staticClass:"mx-auto mt-3 rounded",attrs:{src:e.icon,alt:"图书图标",width:"160",height:"200"}}):o("v-img",{staticClass:"mx-auto mt-3 rounded",attrs:{src:a("dd0f"),alt:"图书图标",width:"150",height:"150"}}),e.price?o("div",{staticClass:"mt-4"},[o("span",[t._v(t._s(e.price.toFixed(2)))]),o("span",{staticClass:"ml-1"},[t._v("RMB")])]):t._e()],1),o("v-col",{attrs:{sm:"8"}},[o("v-card-title",[t._v(t._s(e.bookName))]),o("v-card-subtitle",[o("v-icon",{staticClass:"mx-2",attrs:{size:"15"}},[t._v("mdi-pencil")]),o("span",[t._v(t._s(e.author))]),o("v-icon",{staticClass:"mx-2",attrs:{size:"15"}},[t._v("mdi-store")]),o("span",[t._v(t._s(e.amount))])],1),o("v-card-text",[o("v-row",{staticClass:"mx-0",attrs:{align:"center"}},[o("v-rating",{attrs:{value:.5*e.rating,color:"amber",dense:"","half-increments":"",readonly:"",size:"16"}}),o("div",{staticClass:"grey--text ms-4"},[t._v(" "+t._s(e.rating.toFixed(2))+" ")])],1),o("div",{staticClass:"mt-4"},[t._v(t._s(e.description))])],1)],1)],1),o("v-card-actions",[o("v-spacer"),o("v-btn",{attrs:{small:"",color:"secondary",to:{name:"BookSaleDetail",params:{inventoryId:e.id}}}},[t._v(" Learn More ")])],1)],1)})),o("v-row",{directives:[{name:"show",rawName:"v-show",value:t.model,expression:"model"}]},t._l(t.bookInventories,(function(e,s){return o("v-col",{key:s,attrs:{sm:"4"}},[o("v-card",{staticClass:"text-center",attrs:{link:"",to:{name:"BookSaleDetail",params:{inventoryId:e.id}}}},[e.icon?o("v-img",{staticClass:"mx-auto mt-3 rounded",attrs:{src:e.icon,alt:"图书图标",width:"160",height:"200"}}):o("v-img",{staticClass:"mx-auto mt-3 rounded",attrs:{src:a("dd0f"),alt:"图书图标",width:"150",height:"150"}}),e.price?o("div",{staticClass:"mt-4"},[o("span",[t._v(t._s(e.price.toFixed(2)))]),o("span",{staticClass:"ml-1"},[t._v("RMB")])]):t._e()],1)],1)})),1),t.page.totalPages?o("v-pagination",{attrs:{length:t.page.totalPages,"total-visible":5},on:{previous:function(e){return t.last(t.page)},next:function(e){return t.next(t.page)},input:function(e){return t.appointed(t.page)}},model:{value:t.page.number,callback:function(e){t.$set(t.page,"number",e)},expression:"page.number"}}):t._e()],2),o("v-col",{attrs:{sm:"4"}},[o("v-switch",{staticClass:"ml-2",attrs:{inset:"",label:"切换视图",color:"primary"},model:{value:t.model,callback:function(e){t.model=e},expression:"model"}}),o("v-card",[o("v-card-title",[o("span",[t._v("热销图书")]),o("v-icon",{staticClass:"ml-2"},[t._v("mdi-fire")])],1),o("v-card-subtitle",[t._v("Book Recommand")]),o("v-card-text",[o("v-list",{attrs:{"two-line":""}},t._l(t.hotBookInventories,(function(e,a){return o("v-list-item",{key:a,attrs:{link:"",to:{name:"BookSaleDetail",params:{inventoryId:e.id}}}},[o("v-list-item-content",[o("v-list-item-title",[t._v(" "+t._s(e.bookName)+" ")]),o("v-list-item-subtitle",{staticClass:"mt-1"},[o("v-rating",{attrs:{value:.5*e.rating,color:"purple",dense:"","half-increments":"",readonly:"",size:"14"}})],1)],1)],1)})),1)],1)],1)],1)],1)],1)},s=[],i={name:"BookSaleList",data:function(){return{model:!1,categories:[],bookInventories:[],hotBookInventories:[],page:{size:10,totalElements:0,totalPages:0,number:0}}},methods:{initialize:function(){this.getCategories(),this.getLatestBookInventories(),this.getHotestBookInventories()},getCategories:function(){var t=this;this.axios.get("/api/bookCategories").then((function(e){t.categories=e.data._embedded.bookCategories}))},getLatestBookInventories:function(){var t=this;this.axios.get("/api/bookInventories/search/findByCategoryId",{params:{categoryId:this.$route.params.categoryId}}).then((function(e){t.bookInventories=e.data._embedded.bookInventories,t.page=e.data.page,t.page.number+=1}))},getHotestBookInventories:function(){var t=this;this.axios.get("/api/bookInventories/search/findByCategoryId",{params:{categoryId:this.$route.params.categoryId,sort:"rating,desc",projection:"inlineCategory",size:10}}).then((function(e){t.hotBookInventories=e.data._embedded.bookInventories}))},next:function(t){var e=this;this.axios.get("/api/bookInventories/search/findByCategoryId",{params:{page:t.number,categoryId:this.$route.params.categoryId}}).then((function(t){e.bookInventories=t.data._embedded.bookInventories,e.page=t.data.page,e.page.number+=1}))},last:function(t){var e=this;this.axios.get("/api/bookInventories/search/findByCategoryId",{params:{page:t.number-2,categoryId:this.$route.params.categoryId}}).then((function(t){e.bookInventories=t.data._embedded.bookInventories,e.page=t.data.page,e.page.number+=1}))},appointed:function(t){var e=this;this.axios.get("/api/bookInventories/search/findByCategoryId",{params:{page:t.number-1,categoryId:this.$route.params.categoryId}}).then((function(t){e.bookInventories=t.data._embedded.bookInventories,e.page=t.data.page,e.page.number+=1}))},open:function(t,e){this.getLatestBookInventories(),this.getHotestBookInventories()}},created:function(){this.initialize()}},n=i,r=a("4ac2"),c=a("7aad"),d=a.n(c),l=a("0455"),v=a("b6b0"),m=a("e8d7"),u=a("0e6e"),g=a("e903"),p=a("1a1e"),b=a("c9a1"),h=a("2d3f"),I=a("a07a"),k=a("377a"),f=a("7cb6"),_=a("95db"),C=a("a9b3"),y=a("0fe9"),x=a("dac5"),w=a("bac7"),B=a("1856"),V=a("4b75"),L=a("0c88"),S=Object(r["a"])(n,o,s,!1,null,null,null);e["default"]=S.exports;d()(S,{VBtn:l["a"],VCard:v["a"],VCardActions:m["a"],VCardSubtitle:m["b"],VCardText:m["c"],VCardTitle:m["d"],VCol:u["a"],VContainer:g["a"],VIcon:p["a"],VImg:b["a"],VList:h["a"],VListItem:I["a"],VListItemContent:k["a"],VListItemSubtitle:k["b"],VListItemTitle:k["c"],VPagination:f["a"],VRating:_["a"],VRow:C["a"],VSheet:y["a"],VSpacer:x["a"],VSwitch:w["a"],VTab:B["a"],VTabs:V["a"],VTabsSlider:L["a"]})}}]);
//# sourceMappingURL=chunk-2d21f46f.609cce06.js.map