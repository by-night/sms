// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import store from './vuex/store'
import VCharts from 'v-charts'
import axiosHelper from '../src/axios/axiosHelper'
import Util from './common/js/utils'
import './router/route'
import './common/css/common.css'
import './common/js/drag'
// 引入element-ui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueCookies from 'vue-cookies'
import 'jquery'
Vue.config.productionTip = false;
Vue.use(VCharts);
Vue.use(ElementUI);
Vue.use(Util);
Vue.use(VueCookies);
Vue.prototype.axios = axios;
Vue.prototype.axiosHelper = axiosHelper;
/* eslint-disable no-new */
let vue = new Vue({
  el: '#app',
  router,
  store,
  components: {App},
  template: '<App/>'
});
// 暴露出去，外部js文件需要this时，引用main.js即可使用
export default vue
