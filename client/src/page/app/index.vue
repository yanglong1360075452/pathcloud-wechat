<template>

  <div class="main">
    <router-view v-show="is_wx"></router-view>
    <Toast v-if="Toast_show" type="text" v-model="Toast_show">{{Toast_text}}</Toast>
  </div>
</template>

<script>
// 注： 初始页面必须是 http://localhost:8080/#/app/login
// 否则跳转路由时将失去微信的 code ( code 只能使用一次)
// 导致页面取不到 code 而出错

  import wechatService from '../../service/wechat'
  import userService from '../../service/user'
  import {cookie, locationTool} from '../../service/common'
  import { Toast } from 'vux'

  export default {
    name: 'app',
    data(){
      return{
        Toast_show:false,
        Toast_text:'',
        is_wx:true, // 服务器为 false
      }
    },
    async created() {
      var vm=this;

      // try {

      //   // 微信授权
      //   // 接口调用
      //      let code = locationTool.getParams("code");
      //      let data = await wechatService.getAuthByCode(code);
      //      console.log("data", data);
      //      // userService.setUserInfo(data);
      //      if(data.openId){
      //       console.log('第一次绑定')
      //       vm.is_wx=true
      //       console.log('openId', data.openId)
      //       vm.$store.commit('openId',data.openId)
      //       this.$router.push({path:'/app/login'})
      //      }

      //      if(data.token){
      //       console.log('已绑定')
      //       console.log('token', data.token)
      //       vm.$store.commit('token',data.token)
      //       vm.is_wx=true
      //       this.$router.push({path:'/app/main/apply/application/normal'})
      //      }

      // } catch (err) {
      //   console.log("err", err);
      //   console.log('授权失败');
      //   vm.Toast_text='授权失败'
      //   vm.Toast_show=true
      //   vm.is_wx=false
      // }




    },
    components:{
      Toast
    },
    mounted(){


    },


  }
</script>
