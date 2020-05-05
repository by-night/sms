// 引入vue
import Vue from 'vue'
import Vuex from 'vuex'

// 注册vue
Vue.use(Vuex);

// 状态
const state = {
  silent: null,
  collapse: {},
  userInfo: JSON.parse(localStorage.getItem('userInfo')),
};

// mutations 主要用来操作 state
const mutations = {
  SAVE_SILENT (state, silent) {
    state.silent = silent;
  },
  SAVE_COLLAPSE (state, collapse) {
    state.collapse = Object.assign({}, collapse);
  },
  SAVE_USERINFO (state, userInfo) {
    localStorage.setItem('userInfo', JSON.stringify(userInfo));
    state.userInfo = userInfo
  },
};


//创建store仓库暴露出去
export default new Vuex.Store({
  state,
  mutations
})
