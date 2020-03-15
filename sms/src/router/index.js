import Vue from 'vue'
import Router from 'vue-router'
import login from '../components/login/login'
import home from '../components/home'
import score from '../components/score/score'
import point from '../components/point/point'
import course from '../components/course/course'
import registered from '../components/registered/index'
import dashboard from '../components/dashboard/dashboard'
import student from '../components/setting/student'
import teacher from '../components/setting/teacher'

// 导航栏点击点击多次报错警告处理
const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
};


Vue.use(Router);
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
          path: '/score',
          name: '成绩查询',
          component: score,
        }, {
          path: '/point',
          name: '绩点查询',
          component: point,
        }, {
          path: '/student',
          name: '学生账号',
          component: student,
        }, {
          path: '/course',
          name: '课程录入',
          component: course,
        }, {
          path: '/teacher',
          name: '教师账号',
          component: teacher,
        }
      ]
    }, {
      path: '/registered',
      name: '注册',
      component: registered
    }
  ]
})
