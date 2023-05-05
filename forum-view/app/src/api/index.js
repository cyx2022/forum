import http from '../http/request'

// 请求验证码
export function getCode() {
    return new Promise((resolve, reject) => {
      http.get("/auth/captcha").then((res) => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  }


  export function login({
    data,
  }) {
    return new Promise((resolve, reject) => {
      http.post("/auth/login",data)
      .then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  }

  
  export function register({
    data = {},
  }) {
    return new Promise((resolve, reject) => {
      http.post("/auth/register",data)
      .then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  }

  




