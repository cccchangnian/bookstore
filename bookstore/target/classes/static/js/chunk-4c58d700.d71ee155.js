(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4c58d700"],{"1f6c":function(t,e,i){"use strict";i("7e37");var a=i("a506"),s=i("c235"),o=i("1326"),n=i("19e6"),l=i("a7bb"),r=i("bcc5"),d=i("8e62"),c=i("dc09"),u=i("fdd6"),h=i("9c48"),m=i("5f1a"),p=i("4c55");const v=Object(m["a"])(n["a"],l["a"],d["a"],Object(r["a"])("radioGroup"),c["a"]);e["a"]=v.extend().extend({name:"v-radio",inheritAttrs:!1,props:{disabled:Boolean,id:String,label:String,name:String,offIcon:{type:String,default:"$radioOff"},onIcon:{type:String,default:"$radioOn"},readonly:Boolean,value:{default:null}},data:()=>({isFocused:!1}),computed:{classes(){return{"v-radio--is-disabled":this.isDisabled,"v-radio--is-focused":this.isFocused,...this.themeClasses,...this.groupClasses}},computedColor(){return u["a"].options.computed.computedColor.call(this)},computedIcon(){return this.isActive?this.onIcon:this.offIcon},computedId(){return o["a"].options.computed.computedId.call(this)},hasLabel:o["a"].options.computed.hasLabel,hasState(){return(this.radioGroup||{}).hasState},isDisabled(){return this.disabled||!!this.radioGroup&&this.radioGroup.isDisabled},isReadonly(){return this.readonly||!!this.radioGroup&&this.radioGroup.isReadonly},computedName(){return this.name||!this.radioGroup?this.name:this.radioGroup.name||"radio-"+this.radioGroup._uid},rippleState(){return u["a"].options.computed.rippleState.call(this)},validationState(){return(this.radioGroup||{}).validationState||this.computedColor}},methods:{genInput(t){return u["a"].options.methods.genInput.call(this,"radio",t)},genLabel(){return this.hasLabel?this.$createElement(a["a"],{on:{click:u["b"]},attrs:{for:this.computedId},props:{color:this.validationState,focused:this.hasState}},Object(h["s"])(this,"label")||this.label):null},genRadio(){const{title:t,...e}=this.attrs$;return this.$createElement("div",{staticClass:"v-input--selection-controls__input"},[this.$createElement(s["a"],this.setTextColor(this.validationState,{props:{dense:this.radioGroup&&this.radioGroup.dense}}),this.computedIcon),this.genInput({name:this.computedName,value:this.value,...e}),this.genRipple(this.setTextColor(this.rippleState))])},onFocus(t){this.isFocused=!0,this.$emit("focus",t)},onBlur(t){this.isFocused=!1,this.$emit("blur",t)},onChange(){this.isDisabled||this.isReadonly||this.isActive||this.toggle()},onKeydown:()=>{}},render(t){const e={staticClass:"v-radio",class:this.classes,on:Object(p["c"])({click:this.onChange},this.listeners$),attrs:{title:this.attrs$.title}};return t("div",e,[this.genRadio(),this.genLabel()])}})},"63cf":function(t,e,i){"use strict";i.r(e);var a=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("v-container",{attrs:{fluid:""}},[i("v-row",[i("v-col",{attrs:{cols:"12"}},[i("v-data-table",{staticClass:"elevation-1",attrs:{headers:t.headers,items:t.users},scopedSlots:t._u([{key:"top",fn:function(){return[i("v-toolbar",{attrs:{flat:""}},[i("v-toolbar-title",[t._v("用户信息管理")]),i("v-divider",{staticClass:"mx-4",attrs:{inset:"",vertical:""}}),i("v-spacer"),i("v-dialog",{attrs:{"max-width":"600px"},scopedSlots:t._u([{key:"activator",fn:function(e){var a=e.on,s=e.attrs;return[i("v-btn",t._g(t._b({staticClass:"mb-2",attrs:{color:"primary",dark:""},on:{click:function(e){t.state.create=!0}}},"v-btn",s,!1),a),[t._v(" New User ")])]}}]),model:{value:t.dialog,callback:function(e){t.dialog=e},expression:"dialog"}},[i("v-card",[i("v-card-title",[i("span",{staticClass:"headline"},[t._v(t._s(t.formTitle))])]),i("v-card-text",[i("v-form",{ref:"userForm",attrs:{"lazy-validation":"",autocomplete:"off"},model:{value:t.valid,callback:function(e){t.valid=e},expression:"valid"}},[i("v-row",[i("v-col",{attrs:{cols:"12",sm:"6"}},[i("v-text-field",{attrs:{rules:t.rules.usernameRules,label:"用户名"},model:{value:t.editedItem.username,callback:function(e){t.$set(t.editedItem,"username",e)},expression:"editedItem.username"}})],1),i("v-col",{attrs:{cols:"12",sm:"6"}},[i("v-text-field",{attrs:{type:"password",rules:t.rules.passwordRules,label:"密码"},model:{value:t.editedItem.password,callback:function(e){t.$set(t.editedItem,"password",e)},expression:"editedItem.password"}})],1)],1),i("v-row",[i("v-col",{attrs:{cols:"12",sm:"6"}},[i("v-switch",{attrs:{label:"Enabled: "+t.editedItem.enabled},model:{value:t.editedItem.enabled,callback:function(e){t.$set(t.editedItem,"enabled",e)},expression:"editedItem.enabled"}})],1),i("v-col",{attrs:{cols:"12",sm:"6"}},[i("v-text-field",{attrs:{label:"手机号码"},model:{value:t.editedItem.phone,callback:function(e){t.$set(t.editedItem,"phone",e)},expression:"editedItem.phone"}})],1)],1),i("v-row",[i("v-col",{attrs:{cols:"12"}},[i("v-radio-group",{attrs:{row:""},model:{value:t.editedItem.gender,callback:function(e){t.$set(t.editedItem,"gender",e)},expression:"editedItem.gender"}},[i("v-radio",{attrs:{label:"Unknown",color:"orange",value:0}}),i("v-radio",{attrs:{label:"Male",color:"primary",value:1}}),i("v-radio",{attrs:{label:"Female",color:"pink",value:2}})],1)],1)],1),i("v-row",[i("v-col",{attrs:{cols:"12"}},[i("v-text-field",{attrs:{rules:t.rules.emailRules,label:"邮箱"},model:{value:t.editedItem.email,callback:function(e){t.$set(t.editedItem,"email",e)},expression:"editedItem.email"}})],1)],1),i("v-row",[i("v-col",{attrs:{cols:"12"}},[i("v-text-field",{attrs:{rules:t.rules.locationRules,label:"所在地"},model:{value:t.editedItem.location,callback:function(e){t.$set(t.editedItem,"location",e)},expression:"editedItem.location"}})],1)],1),i("v-row",[i("v-col",{attrs:{cols:"12",sm:"6"}},[i("v-text-field",{attrs:{type:"date",label:"生日"},model:{value:t.editedItem.birthday,callback:function(e){t.$set(t.editedItem,"birthday",e)},expression:"editedItem.birthday"}})],1),i("v-col",{attrs:{cols:"12",sm:"6"}},[i("v-text-field",{attrs:{type:"date",label:"加入日期"},model:{value:t.editedItem.joinedDate,callback:function(e){t.$set(t.editedItem,"joinedDate",e)},expression:"editedItem.joinedDate"}})],1)],1)],1)],1),i("v-card-actions",[i("v-spacer"),i("v-btn",{attrs:{color:"blue darken-1",text:""},on:{click:t.close}},[t._v("Cancel")]),t.state.create?i("v-btn",{attrs:{disabled:!t.valid,color:"blue darken-1",text:""},on:{click:t.create}},[t._v("Create")]):t._e(),t.state.update?i("v-btn",{attrs:{disabled:!t.valid,color:"blue darken-1",text:""},on:{click:t.update}},[t._v("Update")]):t._e()],1)],1)],1),i("v-dialog",{attrs:{"max-width":"500px"},model:{value:t.dialogDelete,callback:function(e){t.dialogDelete=e},expression:"dialogDelete"}},[i("v-card",[i("v-card-title",{staticClass:"headline"},[t._v("Are you sure you want to delete this item?")]),i("v-card-actions",[i("v-spacer"),i("v-btn",{attrs:{color:"blue darken-1",text:""},on:{click:function(e){return t.closeDelete()}}},[t._v("Cancel")]),i("v-btn",{attrs:{color:"blue darken-1",text:""},on:{click:function(e){return t.deleteItemConfirm()}}},[t._v("OK")]),i("v-spacer")],1)],1)],1)],1)]},proxy:!0},{key:"item.actions",fn:function(e){var a=e.item;return[i("v-icon",{staticClass:"mr-2",attrs:{small:""},on:{click:function(e){return t.editItem(a)}}},[t._v(" mdi-pencil ")]),i("v-icon",{attrs:{small:""},on:{click:function(e){return t.deleteItem(a)}}},[t._v(" mdi-delete ")])]}},{key:"no-data",fn:function(){return[i("v-btn",{attrs:{color:"primary"},on:{click:t.initialize}},[t._v(" Reset ")])]},proxy:!0}])})],1)],1)],1)},s=[],o=(i("4b1c"),{name:"UserAdmin",data:function(){return{state:{create:!1,update:!1},valid:!0,dialog:!1,dialogDelete:!1,headers:[{text:"Username",align:"start",sortable:!1,value:"username"},{text:"Email",sortable:!1,value:"email"},{text:"Enabled",value:"enabled"},{text:"Joined Date",value:"joinedDate"},{text:"Actions",value:"actions",sortable:!1}],users:[],editedIndex:-1,editedItem:{username:"",password:"",enabled:!0,gender:"0",phone:"",email:"",icon:null,location:"",birthday:(new Date).toISOString().substr(0,10),joinedDate:(new Date).toISOString().substr(0,10)},defaultItem:{username:"",password:"",enabled:!0,gender:0,phone:"",email:"",icon:null,location:"",birthday:(new Date).toISOString().substr(0,10),joinedDate:(new Date).toISOString().substr(0,10)},rules:{usernameRules:[function(t){return!!t||"Username is none!"},function(t){return t.length>=1&&t.length<=20||"Username must be 1-20 characters!"}],passwordRules:[function(t){return!!t||"Password is none!"},function(t){return t.length>=6&&t.length<=30||"Password must be 6-30 characters!"}],emailRules:[function(t){return!!t||"Email is none!"},function(t){return/(\w+)@(\w+)\.(\w+)/.test(t)||"Email is not valid!"}],locationRules:[function(t){return!!t||"Location is none!"}]}}},computed:{formTitle:function(){return-1===this.editedIndex?"New User":"Edit User"}},watch:{dialog:function(t){t||this.close()},dialogDelete:function(t){t||this.closeDelete()}},methods:{initialize:function(){var t=this;this.axios.get("api/users").then((function(e){console.log(e);for(var i=e.data._embedded.users,a=0;a<i.length;++a)i[a].birthday=i[a].birthday?i[a].birthday.substr(0,10):null,i[a].joinedDate=i[a].joinedDate?i[a].joinedDate.substr(0,10):null;t.users=i})).catch((function(t){console.log(t)}))},editItem:function(t){this.editedIndex=this.users.indexOf(t),this.editedItem=Object.assign({},t),this.dialog=!0,this.state.update=!0},deleteItem:function(t){this.editedIndex=this.users.indexOf(t),this.editedItem=Object.assign({},t),this.dialogDelete=!0},deleteItemConfirm:function(){var t=this.users[this.editedIndex];this.axios.delete(t._links.self.href).then((function(t){console.log(t)})).catch((function(t){console.log(t)})),this.users.splice(this.editedIndex,1),this.closeDelete()},close:function(){var t=this;this.dialog=!1,this.$nextTick((function(){t.editedItem=Object.assign({},t.defaultItem),t.editedIndex=-1})),this.state.create=!1,this.state.update=!1},closeDelete:function(){var t=this;this.dialogDelete=!1,this.$nextTick((function(){t.editedItem=Object.assign({},t.defaultItem),t.editedIndex=-1}))},create:function(){var t=this,e=this.$refs.userForm.validate();e&&(this.axios.post("api/users",{username:this.editedItem.username,password:this.editedItem.password,enabled:this.editedItem.enabled,gender:parseInt(this.editedItem.gender),phone:this.editedItem.phone,email:this.editedItem.email,icon:this.editedItem.icon,location:this.editedItem.location,birthday:this.editedItem.birthday?this.editedItem.birthday:(new Date).toISOString().substr(0,10),joinedDate:this.editedItem.joinedDate?this.editedItem.joinedDate:(new Date).toISOString().substr(0,10)}).then((function(e){console.log(e),t.initialize()})).catch((function(t){console.log(t)})),this.close())},update:function(){var t=this,e=this.$refs.userForm.validate();if(e){var i=this.users[this.editedIndex];this.axios.patch(i._links.self.href,{username:this.editedItem.username,password:this.editedItem.password,enabled:this.editedItem.enabled,gender:parseInt(this.editedItem.gender),phone:this.editedItem.phone,email:this.editedItem.email,icon:this.editItem.icon,location:this.editedItem.location,birthday:this.editedItem.birthday?this.editedItem.birthday:(new Date).toISOString().substr(0,10),joinedDate:this.editedItem.joinedDate?this.editedItem.joinedDate:(new Date).toISOString().substr(0,10)}).then((function(e){console.log(e),t.initialize()})).catch((function(t){console.log(t)})),this.close()}}},created:function(){this.initialize()}}),n=o,l=i("4ac2"),r=i("7aad"),d=i.n(r),c=i("0455"),u=i("b6b0"),h=i("e8d7"),m=i("0e6e"),p=i("e903"),v=i("a2c4"),b=i("58a3"),f=i("910e"),g=i("fd12"),I=i("1a1e"),w=i("1f6c"),x=i("d07d"),y=i("a9b3"),S=i("dac5"),k=i("bac7"),C=i("d9dc"),D=i("25d6"),V=i("79a5"),_=Object(l["a"])(n,a,s,!1,null,null,null);e["default"]=_.exports;d()(_,{VBtn:c["a"],VCard:u["a"],VCardActions:h["a"],VCardText:h["c"],VCardTitle:h["d"],VCol:m["a"],VContainer:p["a"],VDataTable:v["a"],VDialog:b["a"],VDivider:f["a"],VForm:g["a"],VIcon:I["a"],VRadio:w["a"],VRadioGroup:x["a"],VRow:y["a"],VSpacer:S["a"],VSwitch:k["a"],VTextField:C["a"],VToolbar:D["a"],VToolbarTitle:V["a"]})},"6be6":function(t,e,i){},"6ee5":function(t,e,i){},"7e37":function(t,e,i){},"88aa":function(t,e,i){},bac7:function(t,e,i){"use strict";i("6ee5"),i("88aa");var a=i("fdd6"),s=i("1326"),o=i("2e45"),n=i("82d9"),l=i("e739"),r=i("9c48");e["a"]=a["a"].extend({name:"v-switch",directives:{Touch:o["a"]},props:{inset:Boolean,loading:{type:[Boolean,String],default:!1},flat:{type:Boolean,default:!1}},computed:{classes(){return{...s["a"].options.computed.classes.call(this),"v-input--selection-controls v-input--switch":!0,"v-input--switch--flat":this.flat,"v-input--switch--inset":this.inset}},attrs(){return{"aria-checked":String(this.isActive),"aria-disabled":String(this.isDisabled),role:"switch"}},validationState(){return this.hasError&&this.shouldValidate?"error":this.hasSuccess?"success":null!==this.hasColor?this.computedColor:void 0},switchData(){return this.setTextColor(this.loading?void 0:this.validationState,{class:this.themeClasses})}},methods:{genDefaultSlot(){return[this.genSwitch(),this.genLabel()]},genSwitch(){const{title:t,...e}=this.attrs$;return this.$createElement("div",{staticClass:"v-input--selection-controls__input"},[this.genInput("checkbox",{...this.attrs,...e}),this.genRipple(this.setTextColor(this.validationState,{directives:[{name:"touch",value:{left:this.onSwipeLeft,right:this.onSwipeRight}}]})),this.$createElement("div",{staticClass:"v-input--switch__track",...this.switchData}),this.$createElement("div",{staticClass:"v-input--switch__thumb",...this.switchData},[this.genProgress()])])},genProgress(){return this.$createElement(n["c"],{},[!1===this.loading?null:this.$slots.progress||this.$createElement(l["a"],{props:{color:!0===this.loading||""===this.loading?this.color||"primary":this.loading,size:16,width:2,indeterminate:!0}})])},onSwipeLeft(){this.isActive&&this.onChange()},onSwipeRight(){this.isActive||this.onChange()},onKeydown(t){(t.keyCode===r["y"].left&&this.isActive||t.keyCode===r["y"].right&&!this.isActive)&&this.onChange()}}})},d07d:function(t,e,i){"use strict";i("6ee5"),i("6be6");var a=i("1326"),s=i("e5f7"),o=i("413b"),n=i("5f1a");const l=Object(n["a"])(o["a"],s["a"],a["a"]);e["a"]=l.extend({name:"v-radio-group",provide(){return{radioGroup:this}},props:{column:{type:Boolean,default:!0},height:{type:[Number,String],default:"auto"},name:String,row:Boolean,value:null},computed:{classes(){return{...a["a"].options.computed.classes.call(this),"v-input--selection-controls v-input--radio-group":!0,"v-input--radio-group--column":this.column&&!this.row,"v-input--radio-group--row":this.row}}},methods:{genDefaultSlot(){return this.$createElement("div",{staticClass:"v-input--radio-group__input",attrs:{id:this.id,role:"radiogroup","aria-labelledby":this.computedId}},a["a"].options.methods.genDefaultSlot.call(this))},genInputSlot(){const t=a["a"].options.methods.genInputSlot.call(this);return delete t.data.on.click,t},genLabel(){const t=a["a"].options.methods.genLabel.call(this);return t?(t.data.attrs.id=this.computedId,delete t.data.attrs.for,t.tag="legend",t):null},onClick:s["a"].options.methods.onClick}})},fdd6:function(t,e,i){"use strict";i.d(e,"b",(function(){return l}));var a=i("1326"),s=i("8e62"),o=i("413b"),n=i("5f1a");function l(t){t.preventDefault()}e["a"]=Object(n["a"])(a["a"],s["a"],o["a"]).extend({name:"selectable",model:{prop:"inputValue",event:"change"},props:{id:String,inputValue:null,falseValue:null,trueValue:null,multiple:{type:Boolean,default:null},label:String},data(){return{hasColor:this.inputValue,lazyValue:this.inputValue}},computed:{computedColor(){if(this.isActive)return this.color?this.color:this.isDark&&!this.appIsDark?"white":"primary"},isMultiple(){return!0===this.multiple||null===this.multiple&&Array.isArray(this.internalValue)},isActive(){const t=this.value,e=this.internalValue;return this.isMultiple?!!Array.isArray(e)&&e.some(e=>this.valueComparator(e,t)):void 0===this.trueValue||void 0===this.falseValue?t?this.valueComparator(t,e):Boolean(e):this.valueComparator(e,this.trueValue)},isDirty(){return this.isActive},rippleState(){return this.isDisabled||this.validationState?this.validationState:void 0}},watch:{inputValue(t){this.lazyValue=t,this.hasColor=t}},methods:{genLabel(){const t=a["a"].options.methods.genLabel.call(this);return t?(t.data.on={click:l},t):t},genInput(t,e){return this.$createElement("input",{attrs:Object.assign({"aria-checked":this.isActive.toString(),disabled:this.isDisabled,id:this.computedId,role:t,type:t},e),domProps:{value:this.value,checked:this.isActive},on:{blur:this.onBlur,change:this.onChange,focus:this.onFocus,keydown:this.onKeydown,click:l},ref:"input"})},onBlur(){this.isFocused=!1},onClick(t){this.onChange(),this.$emit("click",t)},onChange(){if(!this.isInteractive)return;const t=this.value;let e=this.internalValue;if(this.isMultiple){Array.isArray(e)||(e=[]);const i=e.length;e=e.filter(e=>!this.valueComparator(e,t)),e.length===i&&e.push(t)}else e=void 0!==this.trueValue&&void 0!==this.falseValue?this.valueComparator(e,this.trueValue)?this.falseValue:this.trueValue:t?this.valueComparator(e,t)?null:t:!e;this.validate(!0,e),this.internalValue=e,this.hasColor=e},onFocus(){this.isFocused=!0},onKeydown(t){}}})}}]);
//# sourceMappingURL=chunk-4c58d700.d71ee155.js.map