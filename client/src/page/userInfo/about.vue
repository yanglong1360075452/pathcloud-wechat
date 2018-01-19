<template>
  <div class="max_box">
    <x-header :left-options="{backText:''}">
      <span>关于</span>
    </x-header>
    <div class="page_box">
      <div class="logo">

      </div>
      <div class="describe">
        <p>PathTraq 是一套全流程追踪和质控管理系统，覆盖病理科每个工位。从临床病理申请到样本存档，对样本的每一步操作都有详细的记录。确保每个样本都能被正确、及时地处理。系统支持定制化，模块化，满足科室特殊需求。实现病理科集成化管理、规范化操作、智能化监控。</p>
      </div>

      <div class="qrcode">
        <span>微信公众号PathTraq</span>
        <div class="qrcode_img">

        </div>
      </div>
    </div>
  </div>
</template>
<style lang="less" scoped>
  .max_box{
    width: 100%;
    height: 100%;
    background: #ffffff;
    position: fixed;
    top: 0;
    z-index: 99;
    .page_box{
      overflow: hidden;
      margin-top: 1.3rem;
    }
    .page_box .logo{
      width: 4.68rem;
      height: 1.77rem;
      margin: auto;
      background: url(../../assets/img/enter_log.png) no-repeat;
      -webkit-background-size: 100%;
      background-size: 100%;
      margin-bottom: 0.35rem;
    }
    .describe{
      margin: auto;
      width: 6.35rem;
      font-size: 0.35rem;
      text-indent: 2em;
      margin-bottom: 0.93rem;
    }
    .qrcode{
      margin: auto;
      width: 6.35rem;
      text-align: center;
      color: #0ab6ca;
      font-size: 0.26rem;
      .qrcode_img{
        width: 2.15rem;
        height: 2.15rem;
        background: url(../../assets/img/qrcode.jpg) no-repeat;
        -webkit-background-size: 100%;
        background-size: 100%;
        margin: auto;
        margin-top: 0.10rem;
      }

    }
  }
</style>

<script>
  import {Toast,XHeader, Checker, CheckerItem} from 'vux'
  export default{
    components:{
      XHeader
    }
  }
  // console.log(aa)
</script>
