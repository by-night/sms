// 引入vue
import Vue from 'vue'
import Vuex from 'vuex'

// 注册vue
Vue.use(Vuex)

// 状态
const state = {
  collapse: {},
  userinfo: JSON.parse(localStorage.getItem('userinfo'))
};

// mutations 主要用来操作 state
const mutations = {
  SAVE_COLLAPSE (state, collapse) {
    state.collapse.state = collapse.state
    state.collapse.width = collapse.width
  },
  SAVE_USERINFO (state, userinfo) {
    localStorage.setItem('userinfo', JSON.stringify(userinfo));
    state.userinfo = userinfo
  }
};


//创建store仓库暴露出去
export default new Vuex.Store({
  state,
  mutations
})
