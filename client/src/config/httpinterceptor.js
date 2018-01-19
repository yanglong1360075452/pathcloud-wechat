/**
 * Created by yonghuapro on 2017/4/26.
 */
import config from './index'
import tokenService from '../service/token'


export default {
  init({$store,$router,$http}){

    $http.defaults.baseURL = config.apiUrl;
    $http.defaults.timeout = 20000;
    $http.defaults.headers.Accept='application/json';
    $http.defaults.headers['Content-Type']='application/json;charset=UTF-8';


    $http.interceptors.request.use(function (config) {
      // Do something before request is sent

      let token = tokenService.getToken();
      // let token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6Mywib3BlbklkIjoibzk4bGZ3clhUSFlmTWE3b2lQS1ZNOHNLZzVTcyIsImlhdCI6MTQ5Mzg2OTYxOCwiZXhwIjoxNTE5Nzg5NjE4fQ.fEjf23tUViBXNG3hYz2M9vjTeMpmC1G24PY9RylbAtw";

      // console.log("header",config.headers,"token",token)

      config.headers.common['Authorization'] =token;


      $store.commit('startLoading');
      // console.log("$store.loading",$store.state.loading)

      return config;
    }, function (error) {
      // Do something with request error
      return Promise.reject(error);
    });

    // Add a response interceptor
    $http.interceptors.response.use(function (response) {
      // Do something with response data
      $store.commit('stopLoading');
      // console.log("$store.loading",$store.state.loading)

      if(response.data.code === 0){
        return response.data.data;
      }else{
        return Promise.reject(response.data.reason);
      }


    }, function (error) {
      // Do something with response error
      // console.log(error.response);

      $store.commit('stopLoading');
      // console.log("$store.loading",$store.state.loading)

      if(error.response.status === 401){

        $router.push("login"); //请求状态是 401 的时候返回到的页面， 这个页面是在路由如 src\router\index.js 中 path 定义的。
      }

      return Promise.reject(error.response);
    });
  }
}

