import axios from 'axios';
import { Message } from 'element-ui';

axios.defaults.timeout = 5000; //超时终止请求
axios.defaults.baseURL ='http://localhost:8443/'; //配置请求地址


axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
axios.defaults.withCredentials = true;

// var loadingInstance
axios.interceptors.request.use(config => {
  //Ajax请求执行该方法，请求带上token
  var token = localStorage.getItem('userToken');
  const refreshToken = localStorage.getItem('refreshToken');
  if (token !== null && token !== undefined && token !== '') {
    config.headers.Authorization = token;
    var host = window.location.host;
    config.headers['appHost'] = host;
  }
  if (refreshToken !== null && refreshToken !== undefined && refreshToken !== '') {
    config.headers.freshToken = refreshToken;
  }
  //全局配置，get请求加时间戳
  if (config.method.toLowerCase() === "get") {
    config.url += config.url.match(/\?/) ? "&" : "?";
    config.url += "_dc=" + new Date().getTime();
  }
  // loadingInstance = Loading.service({ fullscreen: true })
  return config;
}, error => {  //请求错误处理
  // loadingInstance.close()
  Message.error({
    message: '加载超时'
  });
  return Promise.reject(error);
});
//
var count = 0;
axios.interceptors.response.use(response => {
    // loadingInstance.close()
    // 判断一下响应中是否有 token，如果有就直接使用此 token 替换掉本地的 token。你可以根据你的业务需求自己编写更新 token 的逻辑
    // var token = response.headers.freshToken;
    // if (token) {
    //   // 如果 header 中存在 token，那么触发 refreshToken 方法，替换本地的 token
    //   $cookies.set('userToken', token);
    //   config.headers.Authorization = token;
    // }
    return response;
  },
  error => {
    if (error.response.status === 401) {// eslint-disable-next-line no-console
      if (count === 0) {
        count = count + 1;

      } else if (count > 0) {
        return null;
      }
      // debugger
      Message.error("身份信息超时，请重新登录！", {icon: 1, time: 2000});
      $cookies.remove('userToken');
      setTimeout(function () {
        //跳转到登录页
        // var host = window.location.host + '/login';
        // if (host.indexOf("http://") == -1) {
        //   host = 'http://' + host;
        // }
        // window.location.href = host;

        window.location.href = '/#/login';
      }, 3000);
    }
    return Promise.reject(error);
  });

export default axios; //暴露axios实例
