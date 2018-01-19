import Vue from 'vue'
export default {
  get_departments(){ // 科室列表
    return Vue.http.get('/wechat/applicant/department')
  },
  blocks(data){ // 蜡块列表
    return Vue.http.get('/wechat/dye/blocks',{params:data})
  },
  dyeType(data){ // 染色类别列表
    return Vue.http.get('/wechat/dye/type',{params:data})
  },
  applicant_default_name(data){ // 默认申请人
    return Vue.http.get('/wechat/applicant',{params:data})
  },
  one_applicant_info(id){ // 单个申请人
    return Vue.http.get('/wechat/applicant/'+id)
  },
  info_list(data){ // 查看申请列表
    return Vue.http.get('/wechat/application',{params:data})
  },
  received(data){ // 获取是否接收通知
    return Vue.http.get('/wechat/user/received',{params:data})
  },
  login_info(){ // 个人资料
    return Vue.http.get('/wechat/user')
  },
  booking(date){ // 获取冰冻预约列表
    return Vue.http.get('/wechat/application/booking',{params:date})
  },
  details(data){ // 获取冰冻预约列表
    return Vue.http.get('/wechat/application/detail',{params:data})
  }

}
