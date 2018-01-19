// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import FastClick from 'fastclick'
import VueRouter from 'vue-router'
import routes from './router'
import { WechatPlugin } from 'vux'
import { AjaxPlugin } from 'vux'
import store from './store/'
import Render from './Render'
import httpinterceptor from './config/httpinterceptor'
import { XButton, Toast } from 'vux'
import './style/common.less'
import './style/reset.css'
import './assets/lib/zepto.js'
import './assets/lib/rem.js'
import './assets/font/iconfont.js'

import vueTap from 'v-tap';
Vue.use(vueTap);

import { Loadmore } from 'mint-ui';
import 'mint-ui/lib/style.css';
Vue.component(Loadmore.name, Loadmore);


Vue.use(AjaxPlugin)
Vue.use(VueRouter)
Vue.use(WechatPlugin)


const router = new VueRouter({
  routes
})


FastClick.attach(document.body);

Vue.config.productionTip = false;

/* eslint-disable no-new */
var app = new Vue({

  created(){

    httpinterceptor.init(this);

  },
  components: {
    XButton,Toast
  },
  router,
  store,
  render: h => h(Render)
}).$mount('#app-box')



