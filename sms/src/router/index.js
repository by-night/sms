import Vue from 'vue'
import Router from 'vue-router'
import login from '../components/login/login'
import home from '../components/home'
import score from '../components/score/score'
import course from '../components/course/course'
import registered from '../components/registered/index'
import dashboard from '../components/dashboard/index'
import account from '../components/account/account'
import student from '../components/student/student'
import teacher from '../components/teacher/teacher'

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
          meta: { requireAuth: true },
          component: dashboard
        },
        {
          path: '/score',
          name: '成绩查询',
          meta: { requireAuth: true },
          component: score,
        }, {
        //   path: '/point',
        //   name: '绩点查询',
        //   meta: { requireAuth: true },
        //   component: point,
        // }, {
          path: '/account',
          name: '账号管理',
          meta: { requireAuth: true, level: 0},
          component: account,
        }, {
          path: '/course',
          name: '课程录入',
          meta: { requireAuth: true, level: 0},
          component: course,
        }, {
          path: '/student',
          name: '学生用户',
          meta: { requireAuth: true, level: 0},
          component: student,
        }, {
          path: '/teacher',
          name: '教师用户',
          meta: { requireAuth: true, level: 0},
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
