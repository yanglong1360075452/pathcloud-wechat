<template>
  <div class="">
    <x-header :left-options="{backText:''}"><span>{{titleText}}</span><a slot="right">{{rightText}}</a></x-header>
  </div>

</template>
<script>
  import {XHeader, Checker, CheckerItem} from 'vux'
  export default{
    components:{
      XHeader, Checker, CheckerItem
    },
    props:{
      titleText:[String], rightText:[String],
    }
  }
</script>
<style lang="less">
  .wh100{
    position: absolute;
    width: 100%;
    height: 100%;
    background: #f3f3f3;
    top: 0;
    z-index: 10;
  }

</style>
