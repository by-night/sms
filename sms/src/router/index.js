import Vue from 'vue'
import Router from 'vue-router'
import login from '../components/login/login'
import home from '../components/home'
import list from '../components/list/list'
import edit from '../components/edit/edit'
import first from '../components/first/first'
import registered from '../components/registered/registered'
import dashboard from '../components/dashboard/dashboard'
import setting from '../components/setting/setting'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: login,
    }, {
      path: '/home',
      name: 'home',
      component: home,
      redirect: '/dashboard',
      children: [

        {
          path: '/dashboard',
          name: '主页',
          component: dashboard
        },
        {
          path: '/home',
          name: '第三',
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
        }, {
          path: '/setting',
          name: '账号管理',
          component: setting,
        }
      ]
    }, {
      path: '/registered',
      name: '注册',
      component: registered
    }
  ]
})