import axios from 'axios';
import { Message } from 'element-ui';

axios.defaults.timeout = 5000; //超时终止请求
axios.defaults.baseURL ='http://localhost:8443/'; //配置请求地址


//http request 拦截器
axios.interceptors.request.use(
  config => {
    // const token = getCookie('名称');注意使用的时候需要引入cookie方法，推荐js-cookie
    config.data = JSON.stringify(config.data);
    config.headers = {
      /*'Content-Type':'application/x-www-form-urlencoded'*/
      'Content-Type':'application/json;charset=UTF-8'
    }
    // if(token){
    //   config.params = {'token':token}
    // }
    return config;
  },
  error => {
    return Promise.reject(err);
  }
);


//http response 拦截器

axios.interceptors.response.use(
  response => {
    if(response.data.errCode ==2){
      router.push({
        path:"/login",
        querry:{redirect:router.currentRoute.fullPath}//从哪个页面跳转
      })
    }
    return response;
  },
  error => {
    Message({
      Message:error.message,
      type:'error',
      duration:5*1000
    });
    return Promise.reject(error)
  }
);

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
