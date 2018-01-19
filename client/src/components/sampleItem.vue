<template>
  <div class="sampleItem">
    <div class="msg_line">

      <!--
        <div class="yb_name"><i class="must"></i>样本名称</div>
        <input type="text" placeholder="请输入样本名称">
        -->

      <group>
        <x-input title="样本名称" placeholder="请输入" v-model="obj.name"></x-input>
      </group>
    </div>
    <div class="msg_line">

        <div class="yb_name"><i></i><span>样本类别</span></div>

        <checker v-model="obj.key" default-item-class="default-item-class" selected-item-class="selected-item-class">
          <checker-item  @on-item-click="onItemClick" :value="item" v-for="(item, index) in items1" :key="index"><i class="yb_ico"></i><span>{{item.value}}</span></checker-item>
        </checker>
    </div>
  </div>
</template>
<script>
  import { Checker, CheckerItem, XInput, Group} from 'vux'

  export default {
    props: {
      text: [String],
      obj:[Object]
    },
    components:{
      Checker, CheckerItem, XInput, Group
    },
    data (){
      return {

        defaultVal: {key: '1', value: '大样本'},
        samplename:"",
        items1: [{
          key: '1',
          value: '大样本'
        }, {
          key: '2',
          value: '小样本'
        }],

      }
    },
    methods:{

      onItemClick (value, disabled) {
        console.log(value, disabled)
        if (!this.disabled) {
          this.showPopup = false
        }
      }

    }
  }

</script>
<style lang="less">
  .sampleItem .msg_line{
    .weui-cells{

      font-size: 0.3rem;
      &:before{
          border: none;
        }
      &:after{
          border: none;
        }
    }

    .weui-cell{
      padding: 0 !important;
      line-height: 0.94rem !important;
    }
    .weui-input{
      padding-bottom: 0.05rem !important;
    }
    .weui-icon-clear{
      font-size: .3rem !important;
    }
    .vux-tap-active:active{
      background: #fff !important;
    }
  }
</style>
<style lang="less" scoped>
  /* 基本信息 */
  .vux-checker-box>div{
    margin: 0.27rem;
    margin-top: 0;
    margin-bottom: 0;
    color: #9c9c9c;
  }
  .default-item-class{
    .yb_ico{
      display: inline-block;
      width: 0.4rem;
      height: 0.4rem;
      -webkit-background-size: 100%;
      background: url(../assets/img/yb_no_cur.jpg);
      background-size: 100%;
      vertical-align: text-bottom;
      margin-right: 0.07rem;
    }
  }
  .selected-item-class{
    color: #424242 !important;
    .yb_ico{
      background: url(../assets/img/yb_cur.jpg);
      background-size: 100% 100%;
    }
  }

  .sampleItem{

    .msg_line{
      height: 0.94rem;
      line-height: 0.94rem;
      border-bottom: 1px solid #dadada;
      background: #fff;
      position: relative;
      padding-left: 0.49rem;
    }
    .msg_line:nth-child(2){
      border: 0;
    }
    .msg_line .user_name{
      color: #b2c9cb;
      width: 1.86rem;
    }
    .msg_line i.must{
      position: absolute;
      left: 0.34rem;
      top: 0.36rem;
      width: 0.14rem;
      height: 0.14rem;
      background: url(../assets/img/user_name_i.jpg);
      -webkit-background-size: 100%;
      background-size: 100%;
    }
    .msg_line .up_my{
      width: 1.31rem;
      height: 0.94rem;
      position: absolute;
      right: 0;
      top: 0;
      background: url(../assets/img/up_my.jpg) no-repeat;
      -webkit-background-size: 100%;
      background-size: 100%;
    }
    .msg_line .yb_name{
      width: 1.86rem;
      float: left;
    }
    .msg_line input{
      display: block;
      width: 5.0rem;
      float: left;
      height: 0.94rem;
      color: #424242;
    }
    .big_yb{
      height: 0.4rem;
    }
    /*删除样本*/
    .can_del{
      position: relative;
      overflow: hidden;
      margin-top: 0.3rem;
      height: 1.89rem;
    }
    .can_del .msg_box{
      margin-top: 0;
    }
    .can_del .del{
      width: 1.48rem;
      height: 1.89rem;
      background: #fff;
      background: #e84e40;
      right: -148rem;
      top: 0;
    }
    .can_del .msg_box{
      transition:all .3s;
      -moz-transition:all .3s; /* Firefox 4 */
      -webkit-transition:all .3s; /* Safari and Chrome */
      -o-transition:all .3s; /* Opera */
      height: 1.89rem;
      width: 100%; /* 为实现滑动效果，要用js转100%转成当前窗口宽度像素 */
      float: left;
      /* margin-left: -1.48rem; */ /* 显示删除按钮 */
    }
    .can_del .msg_box.del_btn{
      margin-left: -1.48rem; /* 显示删除按钮 */
    }
    .can_del .del{
      float: left;
      color: #fff;
    }
    .show_del_box{
      width: 9999px;
      height: 1.89rem;
      overflow: hidden;
    }

  }


</style>
<style lang="less"></style>
