/**
 * Created by yonghuapro on 2017/5/8.
 */
import Vue from 'vue'

let user = {};
export default {
  setUserInfo(userInfo){
    user = userInfo;
  },
  getUserInfo(){
    return user;
  },
  login(data){
    return Vue.http.post('/wechat/login',data)
  },
  getApplicant(data){
    return Vue.http.get('/wechat/applicant',{params:data})
  },
  delApplicant(id){
    return Vue.http.delete('/wechat/applicant/'+id)
  }
}
