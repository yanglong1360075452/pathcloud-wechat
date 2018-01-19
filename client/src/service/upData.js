import Vue from 'vue'
export default {
  add_applicant(obj){ // 添加申请人
    return Vue.http.post('/wechat/applicant',obj)
  },
  routine(data){ // 申请
    return Vue.http.post('/wechat/application',data)
  },
  up_applicant(obj){ // 修改申请人
    return Vue.http.put('/wechat/applicant',obj)
  },
  up_specialDye(data){ // 提交蜡块
    return Vue.http.post('/wechat/dye',data)
  },
  sign_out(){ // 退出登录
    return Vue.http.post('/wechat/logout',{})
  },
  opinion(data){ // 意见反馈
    return Vue.http.post('/wechat/user/feedback',data)
  },
  received(){ // 修改是否接收通知
    return Vue.http.post('/wechat/user/received',{})
  },
  login_info(data){ // 个人资料
    return Vue.http.post('/wechat/user',data)
  },
  cancel_pre(id){ // 取消预约
    return Vue.http.post('/wechat/application/cancel/'+id,{})
  },
  application_type2(data){ // 创建冰冻预约
    return Vue.http.post('/wechat/application',data)
  }
}


