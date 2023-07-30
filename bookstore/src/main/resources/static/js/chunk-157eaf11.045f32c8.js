(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-157eaf11"],{"1e87":function(t,e,i){},"22e6":function(t,e,i){},"2fbc":function(t,e,i){"use strict";i("1e87");var s=i("0fe9"),a=i("a7bb"),n=i("dc09"),l=i("5fc5"),o=i("8005"),r=i("5f1a"),c=i("9c48"),h=i("a817");e["a"]=Object(r["a"])(s["a"],a["a"],l["a"],Object(o["b"])(["absolute","bottom","left","right","top"])).extend({name:"v-snackbar",props:{app:Boolean,centered:Boolean,contentClass:{type:String,default:""},multiLine:Boolean,text:Boolean,timeout:{type:[Number,String],default:5e3},transition:{type:[Boolean,String],default:"v-snack-transition",validator:t=>"string"===typeof t||!1===t},vertical:Boolean},data:()=>({activeTimeout:-1}),computed:{classes(){return{"v-snack--absolute":this.absolute,"v-snack--active":this.isActive,"v-snack--bottom":this.bottom||!this.top,"v-snack--centered":this.centered,"v-snack--has-background":this.hasBackground,"v-snack--left":this.left,"v-snack--multi-line":this.multiLine&&!this.vertical,"v-snack--right":this.right,"v-snack--text":this.text,"v-snack--top":this.top,"v-snack--vertical":this.vertical}},hasBackground(){return!this.text&&!this.outlined},isDark(){return this.hasBackground?!this.light:n["a"].options.computed.isDark.call(this)},styles(){if(this.absolute)return{};const{bar:t,bottom:e,footer:i,insetFooter:s,left:a,right:n,top:l}=this.$vuetify.application;return{paddingBottom:Object(c["g"])(e+i+s),paddingLeft:this.app?Object(c["g"])(a):void 0,paddingRight:this.app?Object(c["g"])(n):void 0,paddingTop:Object(c["g"])(t+l)}}},watch:{isActive:"setTimeout",timeout:"setTimeout"},mounted(){this.isActive&&this.setTimeout()},created(){this.$attrs.hasOwnProperty("auto-height")&&Object(h["e"])("auto-height",this),0==this.timeout&&Object(h["d"])('timeout="0"',"-1",this)},methods:{genActions(){return this.$createElement("div",{staticClass:"v-snack__action "},[Object(c["s"])(this,"action",{attrs:{class:"v-snack__btn"}})])},genContent(){return this.$createElement("div",{staticClass:"v-snack__content",class:{[this.contentClass]:!0},attrs:{role:"status","aria-live":"polite"}},[Object(c["s"])(this)])},genWrapper(){const t=this.hasBackground?this.setBackgroundColor:this.setTextColor,e=t(this.color,{staticClass:"v-snack__wrapper",class:s["a"].options.computed.classes.call(this),style:s["a"].options.computed.styles.call(this),directives:[{name:"show",value:this.isActive}],on:{mouseenter:()=>window.clearTimeout(this.activeTimeout),mouseleave:this.setTimeout}});return this.$createElement("div",e,[this.genContent(),this.genActions()])},genTransition(){return this.$createElement("transition",{props:{name:this.transition}},[this.genWrapper()])},setTimeout(){window.clearTimeout(this.activeTimeout);const t=Number(this.timeout);this.isActive&&![0,-1].includes(t)&&(this.activeTimeout=window.setTimeout(()=>{this.isActive=!1},t))}},render(t){return t("div",{staticClass:"v-snack",class:this.classes,style:this.styles},[!1!==this.transition?this.genTransition():this.genWrapper()])}})},"49f7":function(t,e,i){"use strict";var s=i("d9dc");e["a"]=s["a"]},5468:function(t,e,i){t.exports=i.p+"img/top.034f91f5.jpeg"},6599:function(t,e,i){"use strict";i("22e6");var s=i("5f1a"),a=i("82d9"),n=i("c235"),l=i("a7bb"),o=i("bcc5"),r=i("dc09"),c=i("5fc5"),h=i("4d10"),u=i("9731"),p=i("a817");e["a"]=Object(s["a"])(l["a"],u["a"],h["a"],r["a"],Object(o["a"])("chipGroup"),Object(c["b"])("inputValue")).extend({name:"v-chip",props:{active:{type:Boolean,default:!0},activeClass:{type:String,default(){return this.chipGroup?this.chipGroup.activeClass:""}},close:Boolean,closeIcon:{type:String,default:"$delete"},closeLabel:{type:String,default:"$vuetify.close"},disabled:Boolean,draggable:Boolean,filter:Boolean,filterIcon:{type:String,default:"$complete"},label:Boolean,link:Boolean,outlined:Boolean,pill:Boolean,tag:{type:String,default:"span"},textColor:String,value:null},data:()=>({proxyClass:"v-chip--active"}),computed:{classes(){return{"v-chip":!0,...h["a"].options.computed.classes.call(this),"v-chip--clickable":this.isClickable,"v-chip--disabled":this.disabled,"v-chip--draggable":this.draggable,"v-chip--label":this.label,"v-chip--link":this.isLink,"v-chip--no-color":!this.color,"v-chip--outlined":this.outlined,"v-chip--pill":this.pill,"v-chip--removable":this.hasClose,...this.themeClasses,...this.sizeableClasses,...this.groupClasses}},hasClose(){return Boolean(this.close)},isClickable(){return Boolean(h["a"].options.computed.isClickable.call(this)||this.chipGroup)}},created(){const t=[["outline","outlined"],["selected","input-value"],["value","active"],["@input","@active.sync"]];t.forEach(([t,e])=>{this.$attrs.hasOwnProperty(t)&&Object(p["a"])(t,e,this)})},methods:{click(t){this.$emit("click",t),this.chipGroup&&this.toggle()},genFilter(){const t=[];return this.isActive&&t.push(this.$createElement(n["a"],{staticClass:"v-chip__filter",props:{left:!0}},this.filterIcon)),this.$createElement(a["b"],t)},genClose(){return this.$createElement(n["a"],{staticClass:"v-chip__close",props:{right:!0,size:18},attrs:{"aria-label":this.$vuetify.lang.t(this.closeLabel)},on:{click:t=>{t.stopPropagation(),t.preventDefault(),this.$emit("click:close"),this.$emit("update:active",!1)}}},this.closeIcon)},genContent(){return this.$createElement("span",{staticClass:"v-chip__content"},[this.filter&&this.genFilter(),this.$slots.default,this.hasClose&&this.genClose()])}},render(t){const e=[this.genContent()];let{tag:i,data:s}=this.generateRouteLink();s.attrs={...s.attrs,draggable:this.draggable?"true":void 0,tabindex:this.chipGroup&&!this.disabled?0:s.attrs.tabindex},s.directives.push({name:"show",value:this.active}),s=this.setBackgroundColor(this.color,s);const a=this.textColor||this.outlined&&this.color;return t(i,this.setTextColor(a,s),e)}})},ebe8:function(t,e,i){},f6ca:function(t,e,i){"use strict";i.r(e);var s=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("v-container",{attrs:{fluid:""}},[s("v-row",{attrs:{justify:"center"}},[s("v-col",{attrs:{sm:"6",md:"4"}},[s("v-card",{staticClass:"mx-auto"},[s("v-img",{staticClass:"white--text align-end",attrs:{height:"200px",src:i("5468")}},[s("v-card-title",[t._v("Head Portrait")])],1),s("v-card-text",[s("v-form",{ref:"iconForm",attrs:{"lazy-validation":""},model:{value:t.valid,callback:function(e){t.valid=e},expression:"valid"}},[s("v-row",{attrs:{justify:"center"}},[s("v-col",{staticStyle:{"text-align":"center"},attrs:{sm:"8"}},[s("v-avatar",{staticClass:"elevation-8",attrs:{width:"150px",height:"150px"}},[t.user.icon?s("v-img",{ref:"image",attrs:{src:t.user.icon,alt:"头像"}}):s("v-img",{ref:"image",attrs:{src:i("cf05"),alt:"头像"}})],1)],1)],1),s("v-row",[s("v-col",{attrs:{sm:"12"}},[s("v-file-input",{attrs:{outlined:"",dense:"",placeholder:"Can only accept png, jpeg, gif",rules:t.rules.iconRules,accept:"image/png, image/jpeg, image/jpg, image/gif"},on:{change:t.preview},model:{value:t.file,callback:function(e){t.file=e},expression:"file"}})],1)],1),s("v-btn",{staticClass:"mr-4",attrs:{disabled:!t.valid,color:"primary"},on:{click:function(e){return t.update()}}},[t._v("Update")])],1)],1)],1)],1)],1),s("v-snackbar",{scopedSlots:t._u([{key:"action",fn:function(e){var i=e.attrs;return[s("v-btn",t._b({attrs:{color:"pink",text:""},on:{click:function(e){t.snackbar=!1}}},"v-btn",i,!1),[t._v("Close")])]}}]),model:{value:t.snackbar,callback:function(e){t.snackbar=e},expression:"snackbar"}},[t._v(" Information is successfully update! ")])],1)},a=[],n=(i("f2cf"),{name:"UserIcon",data:function(){return{snackbar:!1,valid:!0,user:{username:"",icon:null,_links:[]},file:null,rules:{iconRules:[function(t){return!!t||"File is none!"},function(t){return/\w+\.[jpeg, png, gif, jpg]/.test(null==t?"":t.name)||"Can only accept png, jpeg, gif"},function(t){return!!t&&t.size<1048576||"File size must not exceed 1MB"}]}}},computed:{image:function(){return this.user.icon}},methods:{initialize:function(){var t=this;this.$nextTick((function(){t.user=Object.assign({},t.$store.state.user)}))},update:function(){var t=this,e=this.$refs.iconForm.validate();if(e){var i=new FormData;i.append("file",this.file),this.axios.patch(this.user._links.user.href+"/icon",i).then((function(e){t.$store.dispatch("setUser",e.data),t.initialize(),t.snackbar=!0}))}},preview:function(t){if(t){var e=new FileReader;e.readAsDataURL(t);var i=this;e.onload=function(t){i.user.icon=this.result}}}},created:function(){this.initialize()}}),l=n,o=i("4ac2"),r=i("7aad"),c=i.n(r),h=i("7100"),u=i("0455"),p=i("b6b0"),d=i("e8d7"),v=i("0e6e"),g=i("e903"),f=(i("ebe8"),i("49f7")),m=i("6599"),b=i("9c48"),y=i("a817"),k=i("4c55"),C=f["a"].extend({name:"v-file-input",model:{prop:"value",event:"change"},props:{chips:Boolean,clearable:{type:Boolean,default:!0},counterSizeString:{type:String,default:"$vuetify.fileInput.counterSize"},counterString:{type:String,default:"$vuetify.fileInput.counter"},hideInput:Boolean,placeholder:String,prependIcon:{type:String,default:"$file"},readonly:{type:Boolean,default:!1},showSize:{type:[Boolean,Number],default:!1,validator:t=>"boolean"===typeof t||[1e3,1024].includes(t)},smallChips:Boolean,truncateLength:{type:[Number,String],default:22},type:{type:String,default:"file"},value:{default:void 0,validator:t=>Object(b["H"])(t).every(t=>null!=t&&"object"===typeof t)}},computed:{classes(){return{...f["a"].options.computed.classes.call(this),"v-file-input":!0}},computedCounterValue(){const t=this.isMultiple&&this.lazyValue?this.lazyValue.length:this.lazyValue instanceof File?1:0;if(!this.showSize)return this.$vuetify.lang.t(this.counterString,t);const e=this.internalArrayValue.reduce((t,{size:e=0})=>t+e,0);return this.$vuetify.lang.t(this.counterSizeString,t,Object(b["w"])(e,1024===this.base))},internalArrayValue(){return Object(b["H"])(this.internalValue)},internalValue:{get(){return this.lazyValue},set(t){this.lazyValue=t,this.$emit("change",this.lazyValue)}},isDirty(){return this.internalArrayValue.length>0},isLabelActive(){return this.isDirty},isMultiple(){return this.$attrs.hasOwnProperty("multiple")},text(){return this.isDirty||!this.isFocused&&this.hasLabel?this.internalArrayValue.map(t=>{const{name:e="",size:i=0}=t,s=this.truncateText(e);return this.showSize?`${s} (${Object(b["w"])(i,1024===this.base)})`:s}):[this.placeholder]},base(){return"boolean"!==typeof this.showSize?this.showSize:void 0},hasChips(){return this.chips||this.smallChips}},watch:{readonly:{handler(t){!0===t&&Object(y["b"])("readonly is not supported on <v-file-input>",this)},immediate:!0},value(t){const e=this.isMultiple?t:t?[t]:[];Object(b["j"])(e,this.$refs.input.files)||(this.$refs.input.value="")}},methods:{clearableCallback(){this.internalValue=this.isMultiple?[]:null,this.$refs.input.value=""},genChips(){return this.isDirty?this.text.map((t,e)=>this.$createElement(m["a"],{props:{small:this.smallChips},on:{"click:close":()=>{const t=this.internalValue;t.splice(e,1),this.internalValue=t}}},[t])):[]},genControl(){const t=f["a"].options.methods.genControl.call(this);return this.hideInput&&(t.data.style=Object(k["d"])(t.data.style,{display:"none"})),t},genInput(){const t=f["a"].options.methods.genInput.call(this);return delete t.data.domProps.value,delete t.data.on.input,t.data.on.change=this.onInput,[this.genSelections(),t]},genPrependSlot(){if(!this.prependIcon)return null;const t=this.genIcon("prepend",()=>{this.$refs.input.click()});return this.genSlot("prepend","outer",[t])},genSelectionText(){const t=this.text.length;return t<2?this.text:this.showSize&&!this.counter?[this.computedCounterValue]:[this.$vuetify.lang.t(this.counterString,t)]},genSelections(){const t=[];return this.isDirty&&this.$scopedSlots.selection?this.internalArrayValue.forEach((e,i)=>{this.$scopedSlots.selection&&t.push(this.$scopedSlots.selection({text:this.text[i],file:e,index:i}))}):t.push(this.hasChips&&this.isDirty?this.genChips():this.genSelectionText()),this.$createElement("div",{staticClass:"v-file-input__text",class:{"v-file-input__text--placeholder":this.placeholder&&!this.isDirty,"v-file-input__text--chips":this.hasChips&&!this.$scopedSlots.selection}},t)},genTextFieldSlot(){const t=f["a"].options.methods.genTextFieldSlot.call(this);return t.data.on={...t.data.on||{},click:()=>this.$refs.input.click()},t},onInput(t){const e=[...t.target.files||[]];this.internalValue=this.isMultiple?e:e[0],this.initialValue=this.internalValue},onKeyDown(t){this.$emit("keydown",t)},truncateText(t){if(t.length<Number(this.truncateLength))return t;const e=Math.floor((Number(this.truncateLength)-1)/2);return`${t.slice(0,e)}…${t.slice(t.length-e)}`}}}),$=i("fd12"),x=i("c9a1"),S=i("a9b3"),w=i("2fbc"),j=Object(o["a"])(l,s,a,!1,null,null,null);e["default"]=j.exports;c()(j,{VAvatar:h["a"],VBtn:u["a"],VCard:p["a"],VCardText:d["c"],VCardTitle:d["d"],VCol:v["a"],VContainer:g["a"],VFileInput:C,VForm:$["a"],VImg:x["a"],VRow:S["a"],VSnackbar:w["a"]})}}]);
//# sourceMappingURL=chunk-157eaf11.045f32c8.js.map