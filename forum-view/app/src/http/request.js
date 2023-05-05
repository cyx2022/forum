import axios from 'axios'

const http = axios.create({
    // 通用请求的地址前缀
    baseURL: '/api',
    timeout: 10000000, // 超时时间
    headers:{
      // 'Content-Type':'application/x-www-form-urlencoded'
      // 'Content-Type':'application/json;charset=UTF-8'
    },
    withCredentials:true,
})

// 添加请求拦截器
http.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    // config.headers.Authorization = window.sessionStorage.getItem('token')
    let access_token = localStorage.getItem("access_token") ? JSON.parse(localStorage.getItem("access_token")) : null
    // config.headers['Content-Type'] = 'application/json'
    if(access_token){
      // 携带访问token
      config.headers['access_token']=access_token;
    }
    return config;
  }, function (error) {
    // 对请求错误做些什么 
    return Promise.reject(error);
  });

// 添加响应拦截器
http.interceptors.response.use(function (response) {
    // 对响应数据做点什么
    return response;
  }, function (error) {
    // 对响应错误做点什么
    return Promise.reject(error);
  });

export default http