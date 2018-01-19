<template>
  <div class="max_box">
    <x-header :left-options="{backText:''}">
      <span>意见反馈</span>
    </x-header>
    <div class="input_box">

      <group title="您的联系方式">
        <x-input type="number" v-model="contactWay" placeholder="请输入您的联系方式"></x-input>
      </group>
      <group title="意见反馈">
        <x-textarea v-model="feedback" placeholder="请提出您的宝贵意见，我们会做的更好" :show-counter="false" :rows="1" autosize></x-textarea>
      </group>

    </div>
    <button class="submit" @click="submit">提交</button>
    <toast @on-hide="onHide" v-model="toast_show" type="text">{{toast_text}}</toast>
  </div>
</template>
<style lang="less">
  body {
    .input_box{
      .weui-cells__title{
        color: #000;
        font-size: 0.3rem;
      }
      .weui-cells{
        margin-top: 0;
        font-size: .3rem;
      }
      .weui-label{
        width: 2.3rem !important;
      }
      .weui-cell.vux-x-textarea{
        min-height: 4.38rem;
      }
    }
  }
</style>

<style lang="less" scoped>
  .max_box{
    width: 100%;
    height: 100%;
    background: #f3f3f3;
    position: absolute;
    top: 0;
    z-index: 99;

    .submit{
      margin-top: 1.4rem;
    }
    .input_box{
      margin-top: 1.3rem;
      overflow: hidden;
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
  import {XInput, XTextarea, PopupPicker, Group, Toast, XHeader} from 'vux'
  import serviceUpData from '../../service/upData.js'
  export default{
    components:{
      XHeader,
      XInput, PopupPicker, XTextarea, Group, Toast
    },
    data(){
      return{
        toast_text:'',
        toast_show:false,
        contactWay:'',
        feedback:'',
        var_temp:'',
      }
    },
    methods:{
      onHide(){
        if(this.var_temp=='ok_submit'){
          this.$router.push({path:"/app/main/userInfo"})
        }
      },
      submit(){
        if( this.contactWay=='' || this.feedback=='' ){
          this.toast_text='请填写完整信息'
          this.toast_show=true
        }else{
          serviceUpData.opinion({"contactWay":this.contactWay, "feedback":this.feedback}).then(res=>{
            this.toast_text='已提交'
            this.toast_show=true
            this.var_temp='ok_submit'
          },res=>console.log(res))
        }
      }
    }
  }
</script>
