<template>
  <div>
    <div class="foot_nav footer">
      <router-link :class="{'router-link-active':isApply}" :to="{name:'application',params:{type:'normal'}}"><i></i><span>申请</span></router-link>
      <router-link :to="{name:'search'}"><i></i><span>查看</span></router-link>
      <router-link :to="{name:'userInfo'}"><i></i><span>我</span></router-link>
    </div>
    <router-view></router-view>
  </div>
</template>
<script>
  export default{
    data(){
      return{
        isApply:true
      }
    },
    watch: {
      // 如果路由有变化，会再次执行该方法
      '$route': 'isApplyFn'
    },
    methods:{
      isApplyFn(){
        var b_isApply = /\/apply\//.test(this.$route.path);
        this.isApply=b_isApply;
        // console.log(b_isApply, this.isApply=b_isApply ,'路由变化')
      }
    }
  }
</script>
<style lang="less" scoped>


</style>
