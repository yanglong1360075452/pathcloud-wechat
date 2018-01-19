<template>
  <div>
    <div class="top_nav">
      <!--
        /apply/routine 不能写成 apply/routine 否则会被解析成 #/apply/apply/frozen
       -->
      <div class="nav">
        <ul>
          <router-link v-for="(item, index) in navs" tag="li" :to="item.link" :key="index"><span>{{item.name}}</span></router-link>
        </ul>
      </div>
      <div class="content">

      </div>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
  import { Tab, TabItem } from 'vux'

  export default {
    created(){
      this.navs.forEach(item => {
        if(location.href.indexOf(item.path)>-1){
          item.isChoosed = true;
        }
      })
    },

    data(){
      return {
        navs:[{
          name:"常规染色",
          path:"/normal",
          isChoosed:false,
          link:{name:'application',params:{type:'normal'}}
        },{
          name:"冰冻预约",
          path:"/forzen",
          isChoosed:false,
          link:{name:'application',params:{type:'forzen'}}

        },{
          name:"染色申请",
          path:"/specialDye",
          isChoosed:false,
          link:{name:'specialDye'}

        }]
      }
    },
    components: {
      Tab,
      TabItem
    }
  }
</script>

<style lang="less" scoped>
  .nav{
    width: 100%;
    height: 0.87rem;
    background: #fff;
    border: 1px solid #e1e1e1;
    border-top: 0;
    border-bottom: 0;

    position: fixed;
    top: 0;
    z-index: 9;
  }
  .top_nav{
    background: #ccc;
    height: 0.87rem;

    /*导航*/
    .nav{
      width: 100%;
      background: #fff;
      border: 1px solid #e1e1e1;
      border-top: 0;
      border-bottom: 0;

      position: fixed;
      top: 0;
    }
    .nav ul{
      overflow: hidden;
    }
    .nav li{
      width: 33%;
      float: left;
      text-align: center;
      line-height: 0.87rem;
    }
    .nav li span{
      display: inline-block;
      height: 0.81rem;
      line-height: 0.83rem;
      border-bottom: 0.04rem solid #fff;
    }
    .nav .router-link-active span{
      border-bottom-color: #32b4c2;
      color: #32b4c2;
    }
    .nav li a{
      display: block;
      width: 100%;
      height: 100%;
    }
  }
</style>
