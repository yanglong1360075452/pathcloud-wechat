<template>
  <div>

    <x-header :left-options="{showBack: false}">
      <span>我的</span>
    </x-header>

    <!-- <div class="blur">
      <div class="center">
        <div class="img"></div>
        <span class="userName">{{firstName}}</span>
      </div>
    </div> -->

    <group class="userInfo_group">

      <router-link tag="div" class="router-link" :to="{name:'personal'}">
        <div class="vux-x-switch weui-cell weui-cell_switch margin_bottom "> <div class="weui-cell__bd"> <label class="weui-label" style="">个人资料</label> </div> <div class="weui-cell__ft"><input type="checkbox" class="weui-switch hide"></div> </div>
      </router-link>

      <router-link tag="div" class="router-link" :to="{name:'opinion'}">
        <div class="vux-x-switch weui-cell weui-cell_switch"> <div class="weui-cell__bd"> <label class="weui-label" style="">意见反馈</label> </div> <div class="weui-cell__ft"><input type="checkbox" class="weui-switch hide"></div> </div>
      </router-link>

      <!-- <div  class="router-link">
        <div class="vux-x-switch weui-cell weui-cell_switch">
          <div class="weui-cell__bd"><label class="weui-label">接收通知</label></div>
          <div class="weui-cell__ft"><input @click="switch_my" type="checkbox" v-model="switch_val" class="weui-switch"></div>
        </div>
      </div> -->

      <router-link tag="div" class="router-link" :to="{name:'about'}">
        <div @click="to_about_fn" class="vux-x-switch weui-cell weui-cell_switch"> <div class="weui-cell__bd"> <label class="weui-label" style="">关于</label> </div> <div class="weui-cell__ft"><input type="checkbox" class="weui-switch hide"></div> </div>
      </router-link>

    </group>

    <router-view></router-view>

    <toast @on-hide="onHide" v-model="toast_show" type="text">{{toast_text}}</toast>


  </div>
</template>
<script>
  import {XHeader } from 'vux'
  import { XSwitch, Group, Toast } from 'vux'
  import serviceGetData from '../../service/getData.js'
  import serviceUpData from '../../service/upData.js'

  import { Cell, Checklist } from 'mint-ui';
  console.log(1,Cell)

  export default {
    components:{
      XHeader,Cell,
      XSwitch,
      Group,
      Toast
    },
    created(){
      serviceGetData.received().then(res=>{
        this.switch_val=res
        console.log(res)
      })

      serviceGetData.login_info().then(res=>{
        this.firstName=res.firstName // 当前登录的人名
        console.log(111, res)
      },res=>console.log(res))

    },
    methods:{
      to_about_fn(){
        this.$router.push({path:"/app/main/userInfo/about"})
      },
      switch_my(){
        serviceUpData.received({}).then(res=>{
          serviceGetData.received().then(res=>{
            this.switch_val=res
            if(res){
              // this.toast_text = '当前已打开通知'
            }else{
              // this.toast_text = '当前已关闭通知'
            }
            // this.toast_show = true
          })
        },()=>{
          serviceGetData.received().then(res=>{
            this.switch_val=res
          })
        })
      },
      onHide(){
        if(this.var_temp=='ok_submit'){
          this.$router.push({path:"/app/main/userInfo"})
        }
      },

    },
    data () {
      return {
        firstName:'', // 当前登录的人名
        switch_val: true,
        toast_show:false,
        toast_text:'',
        var_temp:'',
      }
    }
  }
</script>
<style lang="less">
  body .userInfo_group{
    margin-top: 1.2rem;
    .vux-x-switch.weui-cell_switch{
      border-bottom: 0.01rem solid #dadada;
    }
    .margin_bottom{
      /*border-bottom: 0.2rem solid #f3f3f3 !important;*/
    }
    .weui-cell:before{
      left: 0;
    }
    .router-link{

    }
    .vux-x-switch::after{
      content: " ";
      position: absolute;
      left: 7.6px;
      bottom: 8.7px;
      right: 0;
      width: 0.54rem;
      height: 0.54rem;
      background-repeat: no-repeat;

      -webkit-background-size: 100%;
      background-size: 100%;
    }
    .weui-label{
      text-indent: 0.63rem;
      width: auto !important;
    }
    .router-link:nth-child(1) .vux-x-switch::after{
      background-image: url(../../assets/img/personal.png);
    }
    .router-link:nth-child(2) .vux-x-switch::after{
      background-image: url(../../assets/img/Opinion.png);
    }
    .router-link:nth-child(3) .vux-x-switch::after{
      background-image: url(../../assets/img/notice.png);
    }
    .router-link:nth-child(4) .vux-x-switch::after{
      background-image: url(../../assets/img/about.png);
    }
    .vux-no-group-title{
      margin-top: 0;
    }
    .weui-cells{
      margin-top: 0;
    }
    .weui-switch.hide{
      visibility: hidden;
    }
  }
</style>
<style lang="less" scoped>
  /* 模糊头像 */
  .blur{
    margin-top: .9rem;
    background: #ccc;
    text-align: center;
    height: 2.66rem;
    padding-top: 1px solid #aaaaaa;
    background: url(../../assets/img/blur_bg.jpg) no-repeat;
    -webkit-background-size: 100%;
    background-size: 100%;
    .center{
      margin-top: 0.5rem;
      display: inline-block;
      .img{
        width: 1.14rem;
        height: 1.14rem;
        background: url(../../assets/img/head_img.png) no-repeat;
        -webkit-background-size: 100%;
        background-size: 100%;
        border-radius: 50%;
        margin: auto;
      }
      .userName{
        display: inline-block;
        height: 0.8rem;
        line-height: 0.8rem;
        color: #fff;
        font-size: 0.26rem;
      }
    }
  }


</style>
