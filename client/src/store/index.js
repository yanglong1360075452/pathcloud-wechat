/**
 * Created by yonghuapro on 2017/4/28.
 */
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);


export default new Vuex.Store({
  state:{
    loading: false,
    token:undefined,
    openId:undefined,
    department:null,
    applicant:{}, // 申请人相关
    allSample:[], // 样本
    specialDye:[], // 染色申请
    info_list:{}, // 申请信息列表
    login_info:{}, // 当前登录人的信息
    login_info_old:{}, // 旧数据，用来保存用户当前信息，在没有修改时不发送提交请求

  },
  mutations: {
    token (state, data) {
      state.token = data
    },
    openId (state, data) {
      state.openId = data
    },
    startLoading (state) {
      state.loading = true
    },
    stopLoading (state) {
      state.loading = false
    },
    update_department (state, data) {
      state.department = data
    },
    applicant(state, data){
      state.applicant=data
    },
    allSample(state, data){
      state.allSample=data
    },
    specialDye(state, data){
      state.specialDye=data
    },
    info_list(state, data){
      state.info_list=data
    },
    login_info_old(state, data){
      state.login_info_old=data
    },
  }
})
