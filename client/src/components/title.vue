<template>
  <div class="essential">
    <div class="title_line">
      <div class="title"><i></i><span v-text="text"></span></div>
    </div>
  </div>
</template>
<script>
  export default {
    props: {
      text: [String]
    }
  }
</script>

<style lang="less" scoped>
  .essential{
    /*基本信息*/
    .title_line{
      margin-top: 0.1rem;
    }
    .title_line{
      height: 0.54rem;
      line-height: 0.64rem;
      color: #fff;
      position: relative;
      background: #fff;
      text-align: center;
      border-bottom: 1px solid #dadada;
    }
    .title_line .title{
      position: absolute;
      left: 0.24rem;
      width: 1.72rem;
      height: 0.64rem;
      background: #85d6df;
      top: -0.1rem;
    }
    .title_line .title i{
      display: block;
      position: absolute;
      width: 0.04rem;
      height: 0.1rem;
      background: url(../assets/img/msg_box_title.jpg) no-repeat;
      -webkit-background-size: 100%;
      background-size: 100%;
      top: 0;
      left: -0.04rem;
    }

  }
</style>
