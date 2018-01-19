<template>
  <div>

    <div class="page-loadmore-wrapper" ref="wrapper" :style="{ height: wrapperHeight + 'px' }">





      <userName :name="cur_name"></userName>

      <div class="editMain">
        <div class="dataList_box dataList_box_min">
          <div class="dataList">

            <group>

              <popup-picker v-model="user_yeType.specialDye" show-name :columns="1" title="染色类别" placeholder="请点击选择染色类别" :data="dyeTypeData"  value-text-align="left"></popup-picker>

              <x-textarea v-model="user_yeType.ihcMarker"  class="asterisk" title="试剂/抗体名称" placeholder="不同标记物请用; 隔开" :show-counter="false" :rows="1" autosize></x-textarea>
              <x-textarea v-model="user_yeType.note"  class="remarks" title="备注" placeholder="点击输入备注" :show-counter="false" :rows="1" autosize></x-textarea>


            </group>
          </div>
        </div>
      </div>
      <toast @on-hide="onHide" v-model="toastShow" type="text" :time="1000">{{toastText}}</toast>


      <div class="select_date_bar">
        <div class="today"><span :class="{cur:select_date==1}" @click="select_date_fn(1)">最近一个月</span></div>
        <div class="today"><span :class="{cur:select_date==2}" @click="select_date_fn(2)">最近三个月</span></div>
        <div class="today"><span :class="{cur:select_date==3}" @click="select_date_fn(3)">最近一年</span></div>
      </div>





      <Toast v-model="show_Toast" type="text">{{Toast_txt}}</Toast>


      <mt-loadmore
        bottomPullText="上拉加载更多"
        bottomDropText="释放开始加载"
        @top-status-change="handleTopChange"
        :bottom-method="loadBottom"
        @bottom-status-change="handleBottomChange"
        :bottom-all-loaded="allLoaded"
        ref="loadmore">

        <div class="dataBox">

          <checker v-model="checkbox_val" type="checkbox" default-item-class="checkbox_ico" selected-item-class="checkbox_ico_act">
            <checker-item class="" :value="item.blockId" v-for="(item, index) in blocks" :key="index">

              <div class="item">
                <div class="textBox">
                  <div class="textItem pathologySerialNumber">
                    <span class="name">病理号:</span><span class="val">{{item.pathologySerialNumber}}</span>
                  </div>
                  <div class="textItem subId">
                    <span class="name">蜡块号:</span><span class="val">{{item.subId}}</span>
                  </div>
                  <div class="textItem count">
                    <span class="name">组织数:</span><span class="val">{{item.count}}</span>
                  </div>
                  <div class="textItem biaoshiDesc">
                    <span class="name">取材标识:</span><span class="val">{{item.biaoshiDesc}}</span>
                  </div>
                  <div class="textItem bodyPart">
                    <span class="name">取材部位:</span><span class="val">{{item.bodyPart}}</span>
                  </div>

                </div>
              </div>

            </checker-item>
          </checker>


        </div>

        <div :class="{allLoaded:allLoaded}" v-show="allLoaded">没有更多了</div>

      </mt-loadmore>

    </div>



    <div class="sum_next">
      <div class="sum">
        <span>合计:</span><span class="val">{{checkbox_val.length}}个</span>
      </div>
      <div class="next" @click="save">
        <span>确定提交</span>
      </div>
    </div>

    <div class="loadmore_zw">
    </div>


  </div>
</template>
<script>

  import {XTextarea,XHeader,XInput,PopupPicker} from 'vux'
  import getDataService from '../../../service/getData.js'
  import upDataSevice from '../../../service/upData.js'

  import msgTitle from '../../../components/title.vue'
  import userName from '../../../components/userName.vue'
  // import specialDye2 from '../specialDye/specialDye2.vue'
  import { Toast, Checker, CheckerItem, Group, Checklist, Cell, Divider, XButton } from 'vux'
  import serviceGetData from '../../../service/getData.js'

  export default {
    components:{
      msgTitle, userName, Checker, CheckerItem,Toast,XTextarea,XHeader,XInput,PopupPicker,
      Group,
      Checklist,
      Cell,
      Divider,
      XButton,
    },
    created(){
      for (let i = 1; i <= 20; i++) {
        this.list.push(i);
      }


      getDataService.dyeType().then(res=>{
        var arr=[];
        for(var i in res){
          arr.push({value:''+res[i].code , name:res[i].name});
        }
        this.dyeTypeData = arr


      },res=>console.log(res))

      try{
        // console.log(this.$store.state.applicant.default,1111)
        if(this.$store.state.applicant.default){ // 初始申请人存在
          this.cur_name = this.$store.state.applicant.default.firstName
          return
        }else{

          // 获取默认申请人
          serviceGetData.applicant_default_name({isSelf:true}).then(res=>{
            console.log(res.data[0])
            this.$store.state.applicant.default=res.data[0];
            this.cur_name=res.data[0].firstName
          },res=>console.log(res))

        }
      }catch(e){
        console.log('出错',e)
      }

    },
    mounted(){

      console.log('deviceid: ', this.data=this.$route.query.routeParams);

      var vm=this;
      var start = async function () {
          // var createTimeEnd=(+new Date());
          // var createTimeStart=(+new Date())-{1:2592000000, 2:7776000000, 3:31536000000}[vm.select_date-1]; // 默认获取最近一个月数据
          var data=await serviceGetData.blocks({
            createTimeEnd:(+new Date()),
            createTimeStart:(+new Date())-{1:2592000000, 2:7776000000, 3:31536000000}[vm.select_date], // 默认获取最近一个月数据
            length:vm.select_length,
            page:vm.select_page
          });
          vm.checkbox_val=[] // [data.data[0].blockId] // 默认值
          vm.blocks=data.data;
          vm.data_total=data.total;



          vm.$nextTick(function () {
            vm.wrapperHeight = $('.page-loadmore-wrapper').height();
            // this.wrapperHeight = document.documentElement.clientHeight - this.$refs.wrapper.getBoundingClientRect().top;
            console.log($('.page-loadmore-wrapper').height(),1) // => '更新完成'
          })

      };

      start();
    },
    methods: {
      click_item(par){
        console.log(par.index)
      },
      handleTopChange(status) {
        this.moveTranslate = 1;
        this.topStatus = status;
      },
      handleBottomChange(status) {
        this.bottomStatus = status;
      },
      loadBottom() {
        console.log('loadBottom')
        setTimeout(() => {
          var local_length=this.blocks.length; // 本地数据数量
          console.log(local_length , this.data_total)
          if (local_length < this.data_total) {

            this.select_page++
            var vm=this;
            var start = async function () {
                // var createTimeEnd=(+new Date());
                // var createTimeStart=(+new Date())-{1:2592000000, 2:7776000000, 3:31536000000}[vm.select_date-1]; // 默认获取最近一个月数据
                var data=await serviceGetData.blocks({
                  createTimeEnd:(+new Date()),
                  createTimeStart:(+new Date())-{1:2592000000, 2:7776000000, 3:31536000000}[vm.select_date], // 默认获取最近一个月数据
                  length:vm.select_length,
                  page:vm.select_page
                });

                // vm.checkbox_val=[] // [data.data[0].blockId] // 默认值
                vm.blocks = vm.blocks.concat(data.data)
                vm.data_total=data.total;

            };

            start();


          } else {
            this.allLoaded = true;
          }
          try {
            this.$refs.loadmore.onBottomLoaded();
          } catch(err) {
            console.log('页面切出，this.$refs.loadmore.onBottomLoaded(); ')
          }
        }, 1500);
      },
      select_date_fn(date){
        this.select_date=date // 设置选择的时间
        this.blocks=[]; // 清空之前的数据
        this.select_page=1; // 重置页数
        this.allLoaded=false; // 可上拉状态

        var vm=this;
        var start = async function () {
            // var createTimeEnd=(+new Date());
            // var createTimeStart=(+new Date())-{1:2592000000, 2:7776000000, 3:31536000000}[vm.select_date-1]; // 默认获取最近一个月数据
            var data=await serviceGetData.blocks({
              createTimeEnd:(+new Date()),
              createTimeStart:(+new Date())-{1:2592000000, 2:7776000000, 3:31536000000}[vm.select_date], // 默认获取最近一个月数据
              length:vm.select_length,
              page:vm.select_page
            });
            vm.checkbox_val=[] // [data.data[0].blockId] // 默认值
            vm.blocks=data.data;
            vm.data_total=data.total;

        };

        start();

      },
      change (val) {
        console.log('change', val)
      },
      next(){
        var checkbox_val = this.checkbox_val;
        if( checkbox_val.length == 0 ){
          this.show_Toast=true
          this.Toast_txt='请选择蜡块'
        }else{

          // 保存所选选择蜡块
            var blocks = this.blocks;
            console.log(blocks,222)

            if( blocks.length > 0 ){

              var arr=[]

              for(var i=0 ; i <= checkbox_val.length ; i++){
                for(var j=0; j <= blocks.length-1; j++){
                  if( checkbox_val[i] == blocks[j].blockId){
                    arr.push(blocks[j])
                  }
                }
              }

            }

            this.$store.commit('specialDye',[arr])


          // 带参数跳转
            // this.$router.push({
            //       name: 'specialDye2',
            //       query: {
            //         // routeParams: this.checkbox_val
            //       }
            //   })
        }

      },

      onNoError () {
        this.error = null
      },
      onError (errors) {
        console.log(errors)
        this.error = errors
      },

      onHide(){
        // this.$router.push({name:"addApplicant"});
      },
      save(){
        this.next()

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
                specialDye: (this.user_yeType.specialDye)[0] ? parseInt((this.user_yeType.specialDye)[0]) : '' , // #染色类别 , 三目运算符处理当用户没有选择时为空数组时的 NAN
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

         // 发送数据
          upDataSevice.up_specialDye(toService).then(res=>{
            this.toastText='已提交';
            this.toastShow=true;

            this.select_date_fn(this.select_date)

            // location.reload()
            // history.go(-1)
          },res=>{

            this.toastText='记录已存在';
            this.toastShow=true;
            console.log(res)
          })


        }
      }

    },
    data () {
      return {
        list: [],
        allLoaded: false,
        bottomStatus: '',
        wrapperHeight: '',

        select_date:1, //  1 最近一月
        select_page:1, // 页
        select_length:10, // 每页内容
        data_total:0, // 总共内容

        cur_name:'',
        error: '',
        blocks: [], // 蜡块数据
        checkbox_val: [], // 蜡块默认值
        show_Toast: false,
        Toast_txt:'',


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
    computed:{
      checkbox_val_more(){},
      ihcMarker_arr(){ // 染色要求
      }
    }
  }

</script>
<style lang="less" scoped>


  .dataList_box.dataList_box_min {
    overflow: hidden;
    margin-bottom: 0;
  }

  .allLoaded{
    text-align: center;
    color: #ccc;
  }
  .select_date_bar{
    margin-bottom: 0.1rem;
    background: #fff;
    >div{
      float: left;
      width: 33.3%;
      color: #000;
      span{
        display: inline-block;
        border-bottom: 2px solid #fff;
        padding-left: 0.1rem;
        padding-right: 0.1rem;
        height: 0.48rem;
      }
      span.cur{
        border-bottom: 2px solid #83d5e0;
        color: #83d5e0;
      }
    }
  }
  .sum_next{ /* 合计、下一步 */
    height: 1.06rem;
    line-height: 1.06rem;
    text-align: center;
    background: #fff;
    position: fixed;
    z-index: 99;


    width: 100%;
    bottom: 1rem;
    border-top: 0.01rem solid #aaaaaa;
    .sum{
      float: left;
      height: 100%;
      width: 34%;
      .val{
        color: #0ab6ca;
      }
    }
    .next{
      float: right;
      width: 64%;
      height: 100%;
      background: #0ab6ca;
      color: #fff;
    }
  }
  .dateBar{
    height: 0.54rem;
    line-height: 0.54rem;
    text-align: center;
    background: #83d5e0;
    color: #fff;
    font-size: 0.25rem;
    margin-top: 0.3rem;
    margin-bottom: 0.1rem;
    i{
      display: inline-block;
      height: 0.12rem;
      width: 0.12rem;
      border-bottom: 2px solid #fff;
      border-left: 2px solid #fff;
      -webkit-transform: rotate(315deg);
      transform: rotate(315deg);
      margin-bottom:0.02rem;
      margin-left: 0.03rem;
    }
  }
  .dataBox{
    background: #fff;
    overflow: hidden;
    /*margin-bottom: 2.8rem;*/
    .vux-checker-item{
      height: 1.23rem;
      border-bottom: 0.01rem solid #f3f3f3;
      width: 100%;
      overflow: hidden;
      .textBox{
        padding-top: 0.2rem;
        line-height: 0.4rem;
        color: #a5afb0;
        font-size: 0.25rem;
        .textItem .val{
          color: #000;
        }
        .textItem{
          float: left;
          overflow: hidden;
        }
        .subId{
          width: 1.71rem;
        }
        .subId .val{
          color: #f74830;
        }
        .pathologySerialNumber, .biaoshiDesc{
          width: 3rem;
          white-space: nowrap;
        }
        .pathologySerialNumber .val{
          color: #f74830;
        }
      }
    }
  }
</style>
<style lang="less" scope>
  body .vux-x-input .weui-icon {
    padding-left: 5px;
    padding-right: 10px;
  }
  .loadmore_zw{ /* 点位 */
    height: 2.2rem;
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
