import axios from 'axios';
import { Message } from 'element-ui';

axios.defaults.timeout = 5000; //超时终止请求
axios.defaults.baseURL ='http://localhost:8443/'; //配置请求地址


// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
// axios.defaults.withCredentials = true;
//
// // var loadingInstance
// axios.interceptors.request.use(config => {
//   //Ajax请求执行该方法，请求带上token
//   var token = $cookies.get('userToken');
//   // var host = cookie.getCookie('host');
//   if (token !== null && token !== undefined && token !== '') {
//     config.headers['Authorization'] = 'Bearer '+token;
//     var host = window.location.host;
//     config.headers['appHost'] = host;
//   }
//   //全局配置，get请求加时间戳
//   if (config.method.toLowerCase() === "get") {
//     config.url += config.url.match(/\?/) ? "&" : "?";
//     config.url += "_dc=" + new Date().getTime();
//   }
//   // loadingInstance = Loading.service({ fullscreen: true })
//   return config;
// }, error => {  //请求错误处理
//   // loadingInstance.close()
//   Message.error({
//     message: '加载超时'
//   });
//   return Promise.reject(error);
// });
//
// var count = 0;
// axios.interceptors.response.use(response => {
//     // loadingInstance.close()
//     // 判断一下响应中是否有 token，如果有就直接使用此 token 替换掉本地的 token。你可以根据你的业务需求自己编写更新 token 的逻辑
//     var token = response.headers.freshtoken;
//     console.log(refreshToken())
//     if (token) {
//       // 如果 header 中存在 token，那么触发 refreshToken 方法，替换本地的 token
//       $cookies.set('userToken', token);
//     }
//     // var userInfo = response.headers.userinfo;
//     // if (userInfo) {
//     //   cookie.setCookie('userInfo', decodeURIComponent(userInfo));
//     // }
//     return response;
//   },
//   error => {
//     if (error.response.status === 401) {// eslint-disable-next-line no-console
//       if (count === 0) {
//         count = count + 1;
//
//       } else if (count > 0) {
//         return null;
//       }
//       //eslint-disable-next-line no-debugger
//       // debugger
//       Message.error("身份信息超时，请重新登录！", {icon: 1, time: 2000});
//       $cookies.remove('userToken');
//       setTimeout(function () {
//         //跳转到登录页
//         var host = window.location.host;
//         // if (host.indexOf("http://") == -1) {
//         //   host = 'http://' + host;
//         // }
//         window.location.href = host;
//       }, 3000);
//     }
//     return Promise.reject(error);
//   });


// //http request 拦截器
// axios.interceptors.request.use(
//   config => {
//     // const token = getCookie('名称');注意使用的时候需要引入cookie方法，推荐js-cookie
//     config.data = JSON.stringify(config.data);
//     config.headers = {
//       /*'Content-Type':'application/x-www-form-urlencoded'*/
//       'Content-Type':'application/json;charset=UTF-8'
//     }
//     // if(token){
//     //   config.params = {'token':token}
//     // }
//     return config;
//   },
//   error => {
//     return Promise.reject(err);
//   }
// );
//
//
// //http response 拦截器
//
// axios.interceptors.response.use(
//   response => {
//     if(response.data.errCode === 2){
//       router.push({
//         path:"/",
//         querry:{redirect:router.currentRoute.fullPath}//从哪个页面跳转
//       })
//     }
//     return response;
//   },
//   error => {
//     Message({
//       Message:error.message,
//       type:'error',
//       duration:5*1000
//     });
//     return Promise.reject(error)
//   }
// );

export default axios; //暴露axios实例
//
// /**
//  * 封装get方法
//  * @param url
//  * @param data
//  * @returns {Promise}
//  */
//
// export function fetch(url,params={}){
//   return new Promise((resolve,reject) => {
//     axios.get(url,{
//       params:params
//     })
//       .then(response => {
//         resolve(response.data);
//       })
//       .catch(err => {
//         reject(err)
//       })
//   })
// }
//
// /**
//  * 封装post请求
//  * @param url
//  * @param data
//  * @returns {Promise}
//  */
//
// export function post(url,data = {}){
//   return new Promise((resolve,reject) => {
//     axios.post(url,data)
//       .then(response => {
//         resolve(response.data);
//       },err => {
//         reject(err)
//       })
//   })
// }
//
// /**
//  * 封装patch请求
//  * @param url
//  * @param data
//  * @returns {Promise}
//  */
//
// export function patch(url,data = {}){
//   return new Promise((resolve,reject) => {
//     axios.patch(url,data)
//       .then(response => {
//         resolve(response.data);
//       },err => {
//         reject(err)
//       })
//   })
// }
//
// /**
//  * 封装put请求
//  * @param url
//  * @param data
//  * @returns {Promise}
//  */
//
// export function put(url,data = {}) {
//   return new Promise((resolve, reject) => {
//     axios.put(url, data)
//       .then(response => {
//         resolve(response.data);
//       }, err => {
//         reject(err)
//       })
//   })
// }
