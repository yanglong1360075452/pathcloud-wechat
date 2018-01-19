<template>
  <div class="max_box">
    <x-header :left-options="{backText:''}">
      <span>个人资料</span>
      <a @click="sign_out" slot="right">注销</a>
    </x-header>
    <div class="input_box">
      <!-- <div class="head">
        <div class="title">头像</div>
        <div class="img"><i></i></div>
      </div> -->

      <div :class=" isShow ? 'viewMain' : 'editMain' ">
        <div class="dataList_box">
          <div class="dataList">
            <group>


              <x-input class="asterisk" :required="true" :disabled=" isShow ? true : false " :title="applicant_title.firstName" :placeholder="placeholder.firstName" v-model="applicant2sevice.firstName"></x-input>

              <popup-picker show-name :columns="1" class="asterisk" :required="true" v-show="!isShow" :title="applicant_title.identity" :placeholder="placeholder.identity" :data="applicant_info_identity_arr" v-model="applicant2sevice.identity" value-text-align="left"></popup-picker>


              <x-input :disabled=" isShow ? true : false " :title="applicant_title.tutor" :placeholder="placeholder.tutor" v-model="applicant2sevice.tutor"></x-input>

              <x-input :disabled=" isShow ? true : false " :title="applicant_title.faculty" :placeholder="placeholder.faculty" v-model="applicant2sevice.faculty"></x-input>
              <x-input :disabled=" isShow ? true : false " :title="applicant_title.specialty" :placeholder="placeholder.specialty" v-model="applicant2sevice.specialty"></x-input>
              <x-input type="number" :disabled=" isShow ? true : false " :title="applicant_title.studentNumber" :placeholder="placeholder.studentNumber" v-model="applicant2sevice.studentNumber"></x-input>
              <div class="zw"></div>
              <x-input :disabled=" isShow ? true : false " :title="applicant_title.unit" :placeholder="placeholder.unit" v-model="applicant2sevice.unit"></x-input>

              <popup-picker show-name :columns="1" class="asterisk" :required="true" v-show="!isShow" :title="applicant_title.departments" :placeholder="placeholder.departments" :data="applicant_info_departments_arr" v-model="applicant2sevice.departments" value-text-align="left"></popup-picker>


              <x-input type="number" :disabled=" isShow ? true : false " :title="applicant_title.wno" :placeholder="placeholder.wno" v-model="applicant2sevice.wno"></x-input>
              <div class="zw"></div>
              <x-input :disabled=" isShow ? true : false " :title="applicant_title.taskName" :placeholder="placeholder.taskName" v-model="applicant2sevice.taskName"></x-input>

              <popup-picker show-name :columns="1" v-show="!isShow" :title="applicant_title.taskType" :placeholder="placeholder.taskType" :data="applicant_info_taskType_arr" v-model="applicant2sevice.taskType" value-text-align="left"></popup-picker>

              <x-input class="asterisk" :required="true" :disabled=" isShow ? true : false " :title="applicant_title.projectCode" :placeholder="placeholder.projectCode" v-model="applicant2sevice.projectCode"></x-input>
              <x-input type="number" class="asterisk" :required="true" :disabled=" isShow ? true : false " :title="applicant_title.phone" :placeholder="placeholder.phone" v-model="applicant2sevice.phone"></x-input>


            </group>
          </div>
        </div>
      </div>

    </div>
    <button class="submit" @click="save">保存</button>

    <toast v-model="toastShow" type="text">{{toastText}}</toast>

  </div>
</template>
<style lang="less">
  body {
    .input_box{
      .weui-cells{
        margin-top: 0;
        font-size: .3rem;
      }
      .weui-label{
        width: 2.3rem !important;
      }
    }
  }
</style>
<style lang="less" scope>
  body .vux-x-input .weui-icon {
    padding-left: 5px;
    padding-right: 10px;
  }
  .viewMain{
    .dataList_box .weui-cell{
      height: 0.65rem;
      line-height: 0.65rem;
      font-size: 0.26rem;
    }
    .zw{
      height: 0.02rem !important;
    }
    .weui-label{
      color: #999;
    }
    .weui-input{
      color: #000;
      font-size: 0.27rem;
    }
    .vux-cell-box:before{
      border: 0;
    }
    .weui-cell:before{
      border: 0;
    }
    .asterisk::after{
      top: 0.26rem;
    }
  }
  .isHide{
    display: none;
  }
  .dataList_box{
    overflow: hidden;
    margin-bottom: 1.5rem;
    .zw{
      height: 0.3rem;
      background: #f3f3f3;
    }
    .weui-cells{
      font-size: 0.3rem;
    }
    .weui-cell{
        margin: 0;padding: 0;
        height: 0.92rem;
        line-height: 0.92rem;
      }
    .dataList{
      overflow: hidden;
      background: #fff;
    }
    .weui-cell:before, .vux-cell-box:before{
      left: 0 ;
    }
    .weui-cell__hd{
      width: 1.82rem ;
      padding-left: 0.48rem;
      overflow: hidden;
    }
    .weui-cells{
      margin-top: 0 ;
    }
    .weui-cell_access .weui-cell__ft:after{
      right: .5rem;
    }

  }
  .XHeader .vux-header{
    position: fixed;
    top: 0;
    z-index: 9;
  }
</style>

<style lang="less" scoped>
  .max_box{
    width: 100%;
    background: #f3f3f3;
    position: absolute;
    top: 0;
    z-index: 99;

    .submit{
      margin-top: 0;
    }
    .input_box{
      margin-top: 1.3rem;
      background: #fff;
      /*overflow: hidden;*/
      .head{
        height: 1.65rem;
        line-height: 1.65rem;
        background: #fff;
        margin-left: 0.3rem;
        /*border-bottom: #dadada 0.01rem solid;*/

        .title{
          width: 2.43rem;
          float: left;
        }
        .img{
          margin-top: 0.23rem;
          margin-right: 0.3rem;
          float: right;
          width: 1.2rem;
          height: 1.2rem;
          background: url(../../assets/img/head_img.png) no-repeat;
          -webkit-background-size: 100%;
          background-size: 100%;
        }
      }

    }
  }
</style>

<script>
import serviceUpData from '../../service/upData.js'

import upDataService from '../../service/upData.js'
import {Toast,XHeader, Checker, CheckerItem} from 'vux'
import { Group, Cell, XInput, Selector, PopupPicker, Datetime, XNumber, ChinaAddressData, XAddress, XTextarea } from 'vux'
import getDataService from '../../service/getData.js'

export default{
  data(){
    return {
      isShow: false,
      departments:[], // 科室 v-model
      identity:[], // 身份 v-model
      taskType:[], // 课题类型 v-model
      applicant2sevice:{
        firstName: '',
        phone: '',
        departments: [],
        identity: [],
        tutor: '',
        faculty: '',
        specialty: '',
        studentNumber: '',
        unit: '',
        wno: '',
        taskName: '',
        taskType: [],
        projectCode: '',
      },
      applicant2sevice_old:{}, // 旧数据，用来保存用户当前信息，在没有修改时不发送提交请求
      toastText:'完成',
      toastShow:false,
      applicant_info_departments_arr:[],
      applicant_title:{
        "firstName":"姓名",
        "phone":"联系电话",
        "departments":"科室",
        "identity": "身份", // 0-本科生 1-硕士研究生 2-博士研究生 3-博士后 4-临床医生 5-研究人员 6-技术人员 7-其他
        "tutor": "导师",
        "faculty": "院系",
        "specialty": "专业",
        "studentNumber": "学号",
        "unit": "单位",
        "wno": "职工号",
        "taskName": "课题名称",
        "taskType": "课题类型", // 0-国家自然科学基金 1-国家科技部课题 2-省部级计划课题 3-横向课题 4-其他
        "projectCode": "项目代码"
      }
    }
  },
  created(){
    getDataService.login_info().then(res=>{
      console.log(111, res)

      console.log(this.applicant2sevice)
      this.applicant2sevice={
        firstName: res.firstName,
        phone: res.phone,
        departments: [res.departments+''],
        identity: [res.identity+''],
        tutor: res.tutor,
        faculty: res.faculty,
        specialty: res.specialty,
        studentNumber: res.studentNumber,
        unit: res.unit,
        wno: res.wno,
        taskName: res.taskName,
        taskType: [res.taskType+''],
        projectCode: res.projectCode,
      }

      this.applicant2sevice_old = this.applicant2sevice // 保存以比较是否修改过

    },res=>console.log(res))



  },
  mounted (){
    getDataService.get_departments().then(res=>{
      // this.$store.commit('update_department',res);

         var departments=[];
         for(var i in res){
           departments.push({value:''+res[i].code , name:res[i].name});
         }
         this.applicant_info_departments_arr = departments

    },err=>console.log(err))
  },
  computed:{
    applicant_info_identity_arr(){
       return [{value:'0',name:'本科生'}, {value:'1',name:'硕士研究生'}, {value:'2',name:'博士研究生'}, {value:'3',name:'博士后'}, {value:'4',name:'临床医生'}, {value:'5',name:'研究人员'}, {value:'6',name:'技术人员'}, {value:'7',name:'其他'}]
    },
    applicant_info_taskType_arr(){
       return [{value:'0',name:'国家自然科学基金'}, {value:'1',name:'国家科技部课题'}, {value:'2',name:'省部级计划课题'}, {value:'3',name:'横向课题'}, {value:'4',name:'其他'}]
    },
    placeholder(){
      if(!this.isShow){

        return {
          "firstName":"请点击输入申请人"+this.applicant_title.firstName,
          "phone":"请点击输入"+this.applicant_title.phone,
          "departments":"请点击选择申请人"+this.applicant_title.departments,
          "identity": "请点击选择申请人"+this.applicant_title.identity,
          "tutor": "请点击输入申请人"+this.applicant_title.tutor+"姓名",
          "faculty": "请点击输入申请人"+this.applicant_title.faculty,
          "specialty": "请点击输入申请人"+this.applicant_title.specialty,
          "studentNumber": "请点击输入申请人"+this.applicant_title.studentNumber,
          "unit": "请点击输入申请人"+this.applicant_title.unit,
          "wno": "请点击输入申请人"+this.applicant_title.wno,
          "taskName": "请点击输入"+this.applicant_title.taskName,
          "taskType": "请点击选择"+this.applicant_title.taskType,
          "projectCode": "请点击输入"+this.applicant_title.projectCode
        }
      }else{
        return {}
      }
    }
  },
  components:{
    Toast,
    XHeader, Checker, CheckerItem, Group, Cell, XInput, Selector, PopupPicker, XAddress, Datetime, XNumber, XTextarea
  },
  filters: {
            val2name : function(arr,val){
              // for(var i=0;i<=arr.length;i++){
              //   console.log(arr[i].code)
              // }
              return name
            }
          },
  methods:{
    sign_out(){
      serviceUpData.sign_out().then(res=>{
        // this.$router.push({path:"/app/login"})
        window.location.href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9ff41a08eb9923b0&redirect_uri=http%3a%2f%2fpathtraq-crm.wizinno.com%2fpathcloud-wechat%2findex.html&response_type=code&scope=snsapi_base&state=123#wechat_redirect'
      },res=>console.log(res))
    },
    save(){


      var obj=this.applicant2sevice;

      if(obj.firstName==='' || obj.phone==='' || obj.projectCode==='' || obj.departments.length==0 || obj.identity.length==0){
        this.toastText='请填写必填项';
        this.toastShow=true;
      }else{

        var obj2={};
        for(var p in obj) { // 复制对象，转换为服务器接受的形式
          var name=p;//属性名称
          var value=obj[p];//属性对应的值
          obj2[name]=obj[p];
        }

        obj2.departments=obj2.departments[0]
        obj2.identity=obj2.identity[0]
        obj2.taskType=obj2.taskType[0]



        console.log(obj2)
        // 发送修改请求
          upDataService.login_info(obj2).then(res=>{
            this.toastText='完成';
            this.toastShow=true;

            this.$store.state.applicant.default.firstName=this.applicant2sevice.firstName
            console.log(this.$store.state.applicant.default.firstName)
            // location.reload()

          },res=>{
            console.log(res)
            this.toastText='修改失败';
            this.toastShow=true;
          })
      }

    }
  }
}

</script>
