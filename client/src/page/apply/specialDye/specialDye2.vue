<template>
  <div>
    <!-- <x-header :left-options="{backText:''}">
      <span>选择染色类别和试剂</span>
    </x-header> -->

    <div class="editMain">
      <div class="dataList_box">
        <div class="dataList">

          <group>

            <popup-picker v-model="user_yeType.specialDye" show-name :columns="1" title="染色类别" placeholder="请点击选择染色类别" :data="dyeTypeData"  value-text-align="left"></popup-picker>

            <x-textarea v-model="user_yeType.ihcMarker"  class="asterisk" title="试剂/抗体名称" placeholder="不同标记物请用; 隔开" :show-counter="false" :rows="1" autosize></x-textarea>
            <x-textarea v-model="user_yeType.note"  class="remarks" title="备注" placeholder="请输入" :show-counter="false" :rows="1" autosize></x-textarea>


          </group>
        </div>
      </div>
    </div>
    <!-- <div class="footer btn" @click="save">
      <span>确认提交</span>
    </div> -->
    <toast @on-hide="onHide" v-model="toastShow" type="text" :time="1000">{{toastText}}</toast>
  </div>
</template>
<script>
  import {Toast,XTextarea,Group,XHeader,Checker,XInput,PopupPicker} from 'vux'
  import getDataService from '../../../service/getData.js'
  import upDataSevice from '../../../service/upData.js'
  export default{
    components:{
      Toast,Group,XHeader,Checker,XInput,PopupPicker,XTextarea
    },
    created(){


      getDataService.dyeType().then(res=>{
        var arr=[];
        for(var i in res){
          arr.push({value:''+res[i].code , name:res[i].name});
        }
        this.dyeTypeData = arr

      },res=>console.log(res))
    },
    data(){
      return {
        data:null,
        dyeTypeData:[], // 服务器获取的染色类别列表
        user_yeType:{
            // "applicantId":1, // 申请者ID
            "specialDye":[], // 用户选择的染色类别
            "ihcMarker":'', // 染色要求
            "note":"", // 备注
          },
        toastText:'完成',
        toastShow:false,
      }
    },
    mounted (){

      console.log(2222)
      console.log('deviceid: ', this.data=this.$route.query.routeParams);
    },
    computed:{
      ihcMarker_arr(){ // 染色要求
      }
    },
    methods:{

      onHide(){
        // this.$router.push({name:"addApplicant"});
      },
      save(){
        console.log(this.user_yeType)

        if( this.user_yeType.ihcMarker == ''){

          this.toastText='请填写必填项';
          this.toastShow=true;
        }else{

          var applicantId = this.$store.state.applicant.default.id
          var specialDye_arr = this.$store.state.specialDye[0]

          // 染色要求处理为数组
            var ihcMarker_arr=[];
            var str = this.user_yeType.ihcMarker; // 输入的染色要求字符串
            str = str.replace(/\s+/g, ' ') // 删除多于空格
            .replace(/[；;]+/g, ';')　// 把全半角；;转为半角;
            .replace(/; /g, ';') // 删除分号前穿梭
            .replace(/ ;/g, ';') // 删除分号后空格
            .replace(/;+/g, ';') // 删除多于分号
            var ihcMarker_arr = str.split(';')


          // 处理所需蜡块数组
            var ihcBlocks_arr = [];
            for(var i=0; i<= specialDye_arr.length-1; i++){
              var data = specialDye_arr[i];
              ihcBlocks_arr.push({
                serialNumber : (data.pathologySerialNumber), // #病理号
                subId: (data.subId)+'', // #蜡块号
                specialDye: parseInt((this.user_yeType.specialDye)[0]), // #染色类别
                ihcMarker: ihcMarker_arr, // #染色要求
                note: (this.user_yeType.note) // #备注

              })
            }

         // 最终要求的对象
            var toService={
              "applicantId": applicantId,
              "ihcBlocks":ihcBlocks_arr
            }

            console.log(toService)
         // 发送数组
          upDataSevice.up_specialDye(toService).then(res=>{
            this.toastText='已提交';
            this.toastShow=true;
            // history.go(-1)
          },res=>{

            this.toastText='失败';
            this.toastShow=true;
            console.log(res)
          })


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

  .dataList{
    overflow: hidden;
    background: #fff;
    margin-top: 0.14rem !important;
  }
  .dataList_box{
    overflow: hidden;
    /*margin-bottom: 1.5rem;*/

    .vux-x-textarea{
      height: auto !important;
    }
    .remarks{
      min-height: 1.4rem !important;
      .weui-textarea{
        padding-top: .24rem;
        line-height: 1.5;
      }
    }
    .weui-cells{
      font-size: 0.3rem;
    }
    .weui-cell{
        margin: 0;padding: 0;
        height: 0.92rem;
        line-height: 0.92rem;
      }
    .weui-cell:before, .vux-cell-box:before{
      left: 0 ;
    }
    .weui-cell__hd{
      width: 2.6rem ;
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

