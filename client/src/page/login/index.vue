<template>
    <div id="home">
        <div id="status_bar"></div>
        <div id="main">
          <div class="enter_log">

          </div>
          <div class="input_box">
            <label for="username" class="name">


            <x-input v-model="userInfo.username" class="weui-cell_vcode" placeholder="请输入用户名">
              <div class="ico_left name" slot="label" ></div>
            </x-input>


            <!-- <i></i><input @click="testFn" id="username" placeholder="请输入用户名" v-model="userInfo.username"> -->
            </label>
            <label for="password" class="password">

            <x-input v-model="userInfo.password" :type="input_type" :show-clear="false" class="weui-cell_vcode" placeholder="请输入密码">
              <div class="ico_left pass" slot="label" ></div>
              <div @click="switch_password" :class="showpass ? 'showpass' : 'showpass2'" class=" ico_left showpass" slot="right" ></div>
            </x-input>

            <!-- <i></i><i class="v_password iconfont">&#xe65a;</i><input id="password" placeholder="请输入密码" v-model="userInfo.password"> -->

            </label>
          </div>
          <div class="enter submit" @click="submit">
            登录
          </div>

          <Toast v-if="show_Toast" v-model="show_Toast" type="text">{{text_Toast}}</Toast>
        </div>
    </div>
</template>

<script>


  import userService from '../../service/user.js'
  import tokenService from '../../service/token.js'
  import loginService from '../../service/login.js'
  import iconSvg from '../../directive/icon-svg.vue'
  import {XInput, dateFormat, Toast } from 'vux'

  export default {
    name: 'index',
    data (){
      return {
        input_type:'password',
        showpass:true,
        show_Toast:false,
        text_Toast:'',
        userInfo:{
          username:'', // luomo
          password:'', // 1111
          openId:''
        }
      }
    },
    created(){
      // console.log(this.userInfo.openId)
      // console.log(this.userInfo.openId ? '未绑定' : '已绑定')
    },
    components:{XInput, iconSvg, Toast},
    methods:{
      submit () {
        this.userInfo.openId = 'openId'  // this.$store.state.openId; // 异步取得的 oppenId
        if(this.userInfo.password == '' || this.userInfo.username == ''){
          this.text_Toast="请输入用户和密码"
          this.show_Toast=true
          return
        }



        //loginService.login_fn({username:this.username,password:this.password});
        //location='#/app/routine';
        //console.log(this.$router);



        // 1. 临时通过
        // this.$router.push({name:'application',params:{type:'normal'}});

        // 2. 下面用来开启登录验证
        loginService.login_fn(this.userInfo).then(token => {
          console.log("token",token.token);
          tokenService.setToken(token.token);
          this.$router.push({name:'application',params:{type:'normal'}});
        },res=>{
          console.log('res', res)
          this.show_Toast=true
          this.text_Toast=res
        })

      },
      switch_password(){
        this.showpass=!this.showpass
          console.log(this.input_type)
        if(this.input_type=='password'){
          this.input_type='';
        }else{
          this.input_type='password'
        }
      }
    }
  }



</script>


<style lang="less" scoped>

  /*页面主体*/
  #main{
    overflow: hidden;
  }
  /* 登录logo */
  .enter_log{
    background: url(../../assets/img/enter_log.png);
    background-size: 100% 100%;
    width: 4.68rem;
    height: 1.77rem;
    margin: auto;
    margin-top: 2.01rem;
  }
  /*输入控件*/
  .input_box{
    overflow: hidden;
    width: 100%;
    .ico_left{
      width: 0.47rem;
      height: 0.47rem;
      margin-right: 0.1rem;
      -webkit-background-size: 100%;
      background-size: 100%;
      background-repeat: no-repeat;
    }
    .ico_left.name{
      background-image:url(../../assets/img/user.png);
    }
    .ico_left.pass{
      background-image:url(../../assets/img/pass.png);
    }
    .ico_left.showpass{
      background-image:url(../../assets/img/showpass.png);
    }
    .ico_left.showpass2{
      background-image:url(../../assets/img/showpass2.png);
    }
    label{
      display: block;
      height: 0.9rem;
      line-height: 0.9rem;
      width: 100%;
      background: #fff;
      position: relative;
    }
    label:first-child{
      border-bottom: 1px solid #dddddd;
    }


  }
  /*登录*/
  .enter{
    width: 6.9rem;
    height: 0.88rem;
    line-height: 0.88rem;
    color: #fff;
    background: #32b4c2;
    text-align: center;
    margin: auto;
    margin-top: 0.59rem;
    border-radius: 0.08rem;
    letter-spacing:0.08rem;
  }


</style>
