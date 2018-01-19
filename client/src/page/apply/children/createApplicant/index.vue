<template>
  <div>
    <x-header :left-options="{backText:''}">
      <span>{{isShow ? '信息预览' : '申请人基本信息'}}</span>
      <a @click="isShow=!isShow" slot="right">{{isShow ? '编辑' : '预览'}}</a>
    </x-header>

    <div :class=" isShow ? 'viewMain' : 'editMain' ">
      <div class="dataList_box">
        <div class="dataList">
        <!-- {{applicant_info_departments_arr}} -->
          <group>


            <x-input class="asterisk" :required="true" :disabled=" isShow ? true : false " :title="applicant_title.firstName" :placeholder="placeholder.firstName" v-model="applicant2sevice.firstName"></x-input>

            <popup-picker show-name :columns="1" class="asterisk" :required="true" v-show="!isShow" :title="applicant_title.identity" :placeholder="placeholder.identity" :data="applicant_info_identity_arr" v-model="applicant2sevice.identity" value-text-align="left"></popup-picker>
            <!-- <x-input class="asterisk" v-show="isShow"  :title="applicant_title.identity"  :disabled=" isShow ? true : false " v-model="identity[0]"></x-input> -->
            <div v-if="isShow"  class="asterisk weui-cell vux-tap-active weui-cell_access"><div class="weui-cell__hd"><label class="weui-label" >{{applicant_title.identity}}</label></div> <div class="vux-cell-primary"><div class="vux-popup-picker-select" style="text-align: left;"><span class="vux-popup-picker-value">
              {{ applicant2sevice.identity.length>0?applicant_info_identity_arr[applicant2sevice.identity].name:''}}
            </span> </div></div></div>



            <x-input :disabled=" isShow ? true : false " :title="applicant_title.tutor" :placeholder="placeholder.tutor" v-model="applicant2sevice.tutor"></x-input>

            <x-input :disabled=" isShow ? true : false " :title="applicant_title.faculty" :placeholder="placeholder.faculty" v-model="applicant2sevice.faculty"></x-input>
            <x-input :disabled=" isShow ? true : false " :title="applicant_title.specialty" :placeholder="placeholder.specialty" v-model="applicant2sevice.specialty"></x-input>
            <x-input type="number" :disabled=" isShow ? true : false " :title="applicant_title.studentNumber" :placeholder="placeholder.studentNumber" v-model="applicant2sevice.studentNumber"></x-input>
            <div class="zw"></div>
            <x-input :disabled=" isShow ? true : false " :title="applicant_title.unit" :placeholder="placeholder.unit" v-model="applicant2sevice.unit"></x-input>

            <popup-picker show-name :columns="1" class="asterisk" :required="true" v-show="!isShow" :title="applicant_title.departments" :placeholder="placeholder.departments" :data="applicant_info_departments_arr" v-model="applicant2sevice.departments" value-text-align="left"></popup-picker>
            <!-- <x-input class="asterisk" v-show="isShow"  :title="applicant_title.departments"  :disabled=" isShow ? true : false " v-model="departments[0]"></x-input> -->
            <div v-if="isShow"  class="asterisk weui-cell vux-tap-active weui-cell_access"><div class="weui-cell__hd"><label class="weui-label" >{{applicant_title.departments}}</label></div> <div class="vux-cell-primary"><div class="vux-popup-picker-select" style="text-align: left;"><span class="vux-popup-picker-value">
              <!-- {{applicant2sevice.departments.length>0}} -->
              {{ applicant2sevice.departments.length>0 ? applicant_info_departments_arr[applicant2sevice.departments-1].name : '' }}
            </span> </div></div></div>



            <x-input type="number" :disabled=" isShow ? true : false " :title="applicant_title.wno" :placeholder="placeholder.wno" v-model="applicant2sevice.wno"></x-input>
            <div class="zw"></div>
            <x-input :disabled=" isShow ? true : false " :title="applicant_title.taskName" :placeholder="placeholder.taskName" v-model="applicant2sevice.taskName"></x-input>

            <popup-picker show-name :columns="1" v-show="!isShow" :title="applicant_title.taskType" :placeholder="placeholder.taskType" :data="applicant_info_taskType_arr" v-model="applicant2sevice.taskType" value-text-align="left"></popup-picker>
            <!-- <x-input v-show="isShow"  :title="applicant_title.taskType"  :disabled=" isShow ? true : false " v-model="taskType[0]"></x-input> -->
            <div v-if="isShow"  class=" weui-cell vux-tap-active weui-cell_access"><div class="weui-cell__hd"><label class="weui-label" >{{applicant_title.taskType}}</label></div> <div class="vux-cell-primary"><div class="vux-popup-picker-select" style="text-align: left;"><span class="vux-popup-picker-value">

                <div v-if="is_null_taskType">
                  <!-- 当 taskType 为 null 时，不加载数据 -->
                </div>
                <div v-if="!is_null_taskType">
                  <!-- 不数据时才加载 -->
                  {{ applicant2sevice.taskType.length>0?applicant_info_taskType_arr[applicant2sevice.taskType].name:''}}
                </div>

              <!-- {{ (applicant2sevice.taskType[0] == 'null') || applicant2sevice.taskType.length>0 ? applicant_info_taskType_arr[applicant2sevice.taskType].name:'' }} -->

              <!-- {{ applicant2sevice.taskType.length>0?applicant_info_taskType_arr[applicant2sevice.taskType].name:''}} -->
            </span> </div></div></div>


            <x-input class="asterisk" :required="true" :disabled=" isShow ? true : false " :title="applicant_title.projectCode" :placeholder="placeholder.projectCode" v-model="applicant2sevice.projectCode"></x-input>
            <x-input type="number" class="asterisk" :required="true" :disabled=" isShow ? true : false " :title="applicant_title.phone" :placeholder="placeholder.phone" v-model="applicant2sevice.phone"></x-input>


          </group>
        </div>
      </div>
    </div>
    <div class="footer btn" @click="save">
      <span>{{isShow ? '确认提交' : '保存' }}</span>
    </div>
    <toast v-model="toastShow" type="text">{{toastText}}</toast>
  </div>
</template>
<script>
  import upDataService from '../../../../service/upData.js'
  import {Toast,XHeader, Checker, CheckerItem} from 'vux'
  import { Group, Cell, XInput, Selector, PopupPicker, Datetime, XNumber, ChinaAddressData, XAddress, XTextarea } from 'vux'
  import getDataService from '../../../../service/getData.js'

  export default{
    data(){
      return {
        is_null_taskType:false,
        isShow: false,
        departments:[], // 科室 v-model
        identity:[], // 身份 v-model
        taskType:[], // 课题类型 v-model
        is_load:false, // 是否开始渲染 v-if ，如果数据还没有请求出来就渲染，那 {{}} 中有引用到这个数据时页面就会出错。
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

      console.log(this.$route.query.id)
      if( this.$route.query.id ){ // 如果是修改信息页面，才查询信息
        getDataService.one_applicant_info( this.$route.query.id ).then(res=>{
          console.log(111, res)

          var taskType = [res.taskType+''] // 由于这个值不是必选的，用户没有选择时服务器返回 null ，所以需要特殊处理
          if( res.taskType === null){
            this.is_null_taskType=true
            taskType = []
          }


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
            taskType: taskType,
            projectCode: res.projectCode,
          }

        },res=>console.log(res))
        // {{api}}/wechat/applicant/67
      }


      getDataService.get_departments().then(res=>{
        // this.$store.commit('update_department',res);

           var departments=[];
           for(var i in res){
             departments.push({value:''+res[i].code , name:res[i].name});
           }
           this.applicant_info_departments_arr = departments
           // this.is_load = false ; // 允许渲染页面
           // console.log(1,this.is_load )
           // console.log(this.applicant_info_departments_arr)
           // this.is_load = true ; // 允许渲染页面
           // console.log(1,this.is_load )
      },err=>console.log(err))

    },
    watch: {
      'applicant_info_departments_arr': 'applicant_info_departments_arr_fn',
      'applicant_info_identity_arr': 'applicant_info_identity_arr_fn',
      'applicant_info_taskType_arr': 'applicant_info_taskType_arr_fn'
    },
    mounted (){
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
      applicant_info_departments_arr_fn(){
        // console.log(this.applicant_info_departments_arr)
      },
      applicant_info_identity_arr_fn(){
        console.log('applicant_info_identity_arr', this.applicant_info_identity_arr)
        this.is_load=this.isShow
      },
      applicant_info_taskType_arr_fn(){
        // console.log(this.applicant_info_taskType_arr)
        console.log('applicant_info_identity_arr', this.applicant_info_identity_arr)
        this.is_load=this.isShow
      },

      save(){
        var obj=this.applicant2sevice;

        if(obj.firstName=='' || obj.phone=='' || obj.projectCode=='' || obj.departments.length==0 || obj.identity.length==0){
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

          // 通过 url 判断是修改操作还是新建
            if( this.$route.query.id || this.$route.query.id == 0 ){ // 是修改
              obj2.id = this.$route.query.id
              upDataService.up_applicant(obj2).then((response) => {
                  // 这里 response 可以被拦截器过渡，返回的值是拦截器中 return 的

                  // this.toastText='已修改';
                  // this.toastShow=true;
                  this.$router.push({name:"addApplicant"});
              });

            }else{


              upDataService.add_applicant(obj2).then((response) => {
                  // 这里 response 可以被拦截器过渡，返回的值是拦截器中 return 的

                  this.toastText='已添加';
                  this.toastShow=true;
                  this.$router.push({name:"addApplicant"});

              });

            }


        }

      }
    }
  }
</script>
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

