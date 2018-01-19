import Vue from 'vue'
// import Api from '../config/index.js'


// const apiUrl=Api.apiUrl
export default{
  login_fn(data){
    return Vue.http.post('/wechat/login',data)
  }
}
