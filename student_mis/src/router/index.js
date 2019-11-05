import Vue from 'vue'
import Router from 'vue-router'
import login from '../components/login/login'
import home from '../components/home'
import list from '../components/list/list'
import edit from '../components/edit/edit'
import first from '../components/first/first'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/',
      name: 'home',
      component: home,
      redirect: '/home',
      children: [
        {
          path: '/home',
          name: '主页',
          component: first
        },
        {
          path: '/list',
          name: '第一',
          component: list,
        }, {
          path: '/edit',
          name: '第二',
          component: edit,
        }
      ]
    }
  ]
})
