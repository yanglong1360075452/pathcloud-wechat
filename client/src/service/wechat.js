/**
 * Created by yonghuapro on 2017/4/27.
 */
import Vue from 'vue'

export default {
  getAuthByCode(code){
    return Vue.http.get("/wechat/auth/"+code)
  },
  getJsConfig(){
    return Vue.http.get("/auth/jsconfig")
  }
}
