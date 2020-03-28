// 注册全局首位，超时退出

import router from './index'
// 引入main.js文件，可以调用全局this
import that from '../main'

router.beforeEach((to, from, next) => {
  let routeName = localStorage.getItem('cookiesName');
  // 需要路由守卫时，meta: {requireAuth = true}
  if (to.matched.some(record => record.meta.requireAuth)) {
    // cookie失效时
    if ($cookies.get(routeName) === null) {
      that.$message.warning({
        message: '身份已过期，请重新登录'
      });
      setTimeout(function () {
        next({
          path: '/',
          query: { redirect: to.fullPath } // 把要跳转的地址作为参数传到下一步
        })
      }, 800)
    } else {
      next()
    }
    // 不需要路由守卫时
  } else {
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
});
