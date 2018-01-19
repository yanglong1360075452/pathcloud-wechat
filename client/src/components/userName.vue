<template>
  <div class="personal_info">
    <msgTitle text="基本信息"></msgTitle>
    <div class="msg_line">
      <div class="user_name"><i class="asterisk"></i><span></span>{{name}}</div>
      <div @click="addApplicant" class="up_my"></div>
    </div>
  </div>
</template>
<script>
  import msgTitle from '../components/title.vue'

  export default {
    components:{
      msgTitle
    },
    props: {
      text: [String],
      name: [String]
    },
    methods:{
      addApplicant (){
        this.$router.push({name:'addApplicant'});
      }
    }
  }
</script>
<style lang="less" scoped>
  .msg_line{

    height: 0.94rem;
    line-height: 0.94rem;
    border-bottom: 1px solid #dadada;
    background: #fff;
    position: relative;
    padding-left: 0.49rem;

    .user_name{
      color: #b2c9cb;
      width: 1.86rem;
    }
    .up_my{
      width: 1.31rem;
      height: 0.94rem;
      position: absolute;
      right: 0;
      top: 0;
      background: url(../assets/img/up_my.jpg) no-repeat;
      -webkit-background-size: 100%;
      background-size: 100%;
    }
  }
</style>
