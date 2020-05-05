// 注册全局首位，超时退出

import router from './index'
// 引入main.js文件，可以调用全局this
// import that from '../main'
import { Message } from 'element-ui';

let routeName = null;
router.beforeEach((to, from, next) => {
  routeName = localStorage.getItem('cookiesName');
  // 需要路由守卫时，meta: {requireAuth = true}
  if (to.matched.some(record => record.meta.requireAuth)) {
    // cookie失效时
    hasCookie(next);

    // 有权限才可访问
    hasPower(to, next);

    // 不需要路由守卫时
  } else {
    // 无效路由时
    if (to.matched.length === 0) {
      routeFail(to, next);
    } else {
      // 路由有效时
      routeSuccess(to, next);
    }
  }
});

function hasCookie(next) {
  if ($cookies.get(routeName) === null) {
    Message.warning({
      message: '身份已过期，请重新登录'
    });
    setTimeout(function () {
      next({
        path: '/login',
        query: { redirect: to.fullPath } // 把要跳转的地址作为参数传到下一步
      })
    }, 800)
  } else {
    next()
  }
}
function hasPower(to, next) {
  if (to.meta.level !== undefined) {
    if (to.meta.level === $cookies.get(routeName).level) {
      next()
    } else {
      next({path: '/dashboard'});
    }
  }
}
function routeFail(to, next) {
  if (to.path === '/') {
    if ($cookies.get(routeName) === null) {
      next({
        path: '/login'
      })
    } else {
      next({
        path: '/dashboard'
      })
    }
  } else {
    next({
      path: '/404',
    })
  }
}
function routeSuccess(to, next) {
  if (to.query && to.query.redirect) {
    let bool = $cookies.get(routeName) === null || $cookies.get(routeName).username !== routeName;
    if (!bool) {
      // cookie 未过期时
      next({path: to.query.redirect})
    } else {
      next()
    }
  } else {
    next() // 确保一定要调用 next()
  }
}
