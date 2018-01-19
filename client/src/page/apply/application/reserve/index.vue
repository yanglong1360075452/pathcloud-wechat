<template>
  <div>

    <x-header :left-options="{backText:''}">
      <span>选择预约时间</span>
    </x-header>

    <div class="select_date_bar">
      <div class="last"><span @click="lastDay" v-show="!(dateValue==curDate)">前一天</span></div>
      <div class="today">
        <group>
          <calendar @on-change="calendar_change_fn" class="calendar" :weeks-list="['日', '一', '二', '三', '四', '五', '六 ']" :disable-past="true" :highlight-weekend="true" :hide-week-list="false" v-model="dateValue" :title="''" disable-past></calendar>
          <!-- <div class="dateText">
            <span class="day">{{dateText}}</span><i></i>
          </div> -->
        </group>
      </div>
      <div class="next" @click="nextDay"><span>后一天</span></div>
    </div>


    <!-- 时间选择 -->


    <!-- 机器选择 -->
    <div class="select_machine" v-if="select_machine_load">
      <checker type="checkbox" v-model="o_select_machine" default-item-class="default-item-class" selected-item-class="selected-item-class">
        <checker-item class="machine" :key="1" :value="1">
          <div class="machine_name">1 号机</div>
          <div @click.stop="click_machine(1)" class="time_info">
            <semih :sevOtherArr="sevOtherArr1" @myArr_service="getMyArr_service1"></semih>
          </div>
        </checker-item>
        <checker-item class="machine" :key="2" :value="2">
          <div class="machine_name">2 号机</div>
          <div @click.stop="click_machine(2)" class="time_info">
            <semih :sevOtherArr="sevOtherArr2" @myArr_service="getMyArr_service2"></semih>
          </div>
        </checker-item>
      </checker>
    </div>

    <toast @on-hide="re_page_fn" v-model="toast_load" v-if="toast_load" type="text" :time="1000">{{toast_text}}</toast>

    <button class="submit" @click="submit">提交</button>


  </div>
</template>
<style lang="less" scope>


  /* 机器 */
  .vux-checker-item.machine.vux-tap-active.selected-item-class .machine_name::after{
    background-image: url(../../../../assets/img/select2.png);
  }
  .machine{
    height: 0.92rem;
    line-height: 0.92rem;
    width: 100%;
    border: 1px solid #dadada;
    border-left: 0;
    border-right: 0;
    background: #fff;
    position: relative;
    margin-bottom: 0.3rem;
    .time_info::after{
      content:'';
      width: 0.13rem;
      height: 0.23rem;
      display: inline-block;
      position: absolute;
      right: 0.48rem;
      top: 0.33rem;
      background: url(../../../../assets/img/Arrow.png) no-repeat;
      -webkit-background-size: 100%;
      background-size: 100%;

    }
    .machine_name{
      width: 2.6rem;
      height: 100%;
      float: left;
      text-indent: 1.2rem;
    }
    .machine_name::after{
      width: 0.4rem;
      height: 0.4rem;
      display: inline-block;
      position: absolute;
      top: 0.25rem;
      left: 0.39rem;
      background: url(../../../../assets/img/select1.png) no-repeat;
      -webkit-background-size: 100%;
      background-size: 100%;
      content: " ";
    }
  }
  .submit{
    margin-top: 0.3rem;
  }
  body .calendar:after{
      margin: 0;
      padding: 0;
      width: 0;
      height: 0;
      border: 0;
    }
  body{
    .is-weekend-highlight td.is-week-list-0, .is-weekend-highlight td.is-week-list-6, .is-weekend-highlight td.is-week-0, .is-weekend-highlight td.is-week-6{
      color: #c0c0c0;
    }
    .vux-prev-icon, .vux-next-icon{
      border-color: #fff;
    }
    .calendar-header{
      background: #0ab6ca;
    }
    .calendar-title{
      color: #fff;
    }
    .calendar-year > span, .calendar-month > span{
    }
    .inline-calendar table{
      color: #000;
    }
    .inline-calendar td.is-today, .inline-calendar td.is-today.is-disabled{
      color: #0ab6ca;
    }
    .inline-calendar td.current > span{
      background: #0ab6ca;
    }
  }
  body .weui-cells.vux-no-group-title{
    margin: 0;
  }

  .select_date_bar .weui-cell.vux-tap-active.weui-cell_access{
    background: #83d5e0;
    padding:0;
    font-size: 0.25rem;
    height: 0.54rem;
    .weui-cell__ft.vux-cell-primary{
      text-align: center;
    }
    .vux-label{
      color: #fff;
    }
    .vux-cell-primary{
      color: #fff;
    }
    .weui-cell__ft:after{
      border-color: #fff;
      display: inline-block;
      height: 0.12rem;
      width: 0.12rem;
      border: 0;
      position: relative;
      border-bottom: 2px solid #fff;
      border-left: 2px solid #fff;
      -webkit-transform: rotate(315deg);
      transform: rotate(315deg);
      margin-bottom: 0.02rem;
      margin-left: 0.03rem;
    }
  }




</style>

<script>
  import semih from '../../../../components/semih.vue'
  import {Toast, XHeader, XInput, Checklist, Checker, CheckerItem, Group, Calendar, Cell } from 'vux'
  import service_getData from '../../../../service/getData.js'
  import service_upData from '../../../../service/upData.js'

  export default{
    components:{
      semih,
      XHeader, XInput, Checker, CheckerItem, Group, Calendar, Cell, Checklist,Toast
    },
    filters: {
      toSemih(index, timeDate){
        if(index == -1){
          return '--'
        }
        return timeDate[index]
      }
    },
    data(){
      return{
        select_machine_load:false, // 是否加载机器选择组件
        b_shade_box:true, // 显示时间选择器
        dateValue: GetDateStr(new Date(), 0), // 'TODAY', // 日期选择组件默认时间，当天
        o_select_machine:[1], // 选择的机器
        toast_load:false,
        re_page:false, // 是否返回页面
        toast_text:'', // 提醒文字
        myArr_service1:[], // 机器1的时间选择结果
        myArr_service2:[], // 机器1的时间选择结果
        sevOtherArr1:[], // 被预约的时间 [16,20,24,25,26]
        sevOtherArr2:[],
        samples:[], // 所填写的样本
        books: [
                  { // 若申请为冰冻预约，需传此项
                    "freezeStartTime":149839898877, // 冰冻预约日期
                    "instrumentId": 1, // 机器号
                    "startIndex":-1,
                    "endIndex":-1,
                    "cells": [
                             16,17 // 预约时间 0代表0:00-0:30 1代表0:30-1:00 以此类推
                      ]
                  },
                  {
                    "freezeStartTime":149839898877,
                    "instrumentId": 2,
                    "startIndex":-1,
                    "endIndex":-1,
                    "cells": [
                       16,17
                    ]
                  }
              ]
      }
    },
    created(){

      // 默认进入页面查询当天
        service_getData.booking({ // 默认进入页面查询当天
          timeStart: +new Date().setHours(0, 0, 0, 0),
          timeEnd: +new Date().setHours(24, 0, 0, 0)
        }).then(res=>{
          var date1=[];
          var date2=[];
          for(var i=0;i<res.length;i++){
            if( res[i].instrumentId == '1' ){ // 如果是切片机1就把时间放到数组1中
              for(var j=0; j<res[i].bookingDto.length; j++){
                // console.log(1,res[i].bookingDto[j])
                if( res[i].bookingDto[j].booked ){ // 如果已被预约，就把时间段放入数组
                  date1.push(res[i].bookingDto[j].timeflag)
                }
              }
            }
            if( res[i].instrumentId == '2' ){ //
              for(var j=0; j<res[i].bookingDto.length; j++){
                // console.log(1,res[i].bookingDto[j])
                if( res[i].bookingDto[j].booked ){ // 如果已被预约，就把时间段放入数组
                  date2.push(res[i].bookingDto[j].timeflag)
                }
              }
            }
          }

          this.sevOtherArr1=date1 // [16,17,19]
          this.sevOtherArr2=date2
          this.select_machine_load=true
        })

      // 获取所地填写的申请
        var samples = this.$store.state.allSample
        for(var i=0;i<samples.length;i++){
          this.samples.push({
            name:samples[i].name,
            category:samples[i].key.key,
          })
        }
    },
    computed:{
      curDate(){
        return GetDateStr(new Date(), 0)
      },
      dateText(){
        var mydate=new Date(this.dateValue);
        var mydate2=
        (mydate.getMonth()+1)+"月"+
        mydate.getDate()+"日 "+
        " 星期"+['日','一','二','三','四','五','六'][mydate.getDay()];
        console.log(mydate2);
        return mydate2
      },
      day(){
        var data=new Date();
        data.getMonth()+1;
        var day=data.getMonth()+1+''
        return data.getMonth()+1
      },
      week(){
        var data=new Date();
        return data.getMonth()+1
      },
    },
    methods:{
      re_page_fn(){
        if( this.re_page ){
          this.$router.push({name:'application',params:{type:'forzen'}});
        }
      },
      calendar_change_fn(){
        this.select_machine_load=false

        service_getData.booking({ // 查询当前所选的日期的机器时间预约状态
          timeStart: +new Date(this.dateValue).setHours(0, 0, 0, 0),
          timeEnd: +new Date(this.dateValue).setHours(24, 0, 0, 0)
        }).then(res=>{
          var date1=[];
          var date2=[];
          for(var i=0;i<res.length;i++){
            if( res[i].instrumentId == '1' ){ // 如果是切片机1就把时间放到数组1中
              for(var j=0; j<res[i].bookingDto.length; j++){
                // console.log(1,res[i].bookingDto[j])
                if( res[i].bookingDto[j].booked ){ // 如果已被预约，就把时间段放入数组
                  date1.push(res[i].bookingDto[j].timeflag)
                }
              }
            }
            if( res[i].instrumentId == '2' ){ //
              for(var j=0; j<res[i].bookingDto.length; j++){
                // console.log(1,res[i].bookingDto[j])
                if( res[i].bookingDto[j].booked ){ // 如果已被预约，就把时间段放入数组
                  date2.push(res[i].bookingDto[j].timeflag)
                }
              }
            }
          }

          this.sevOtherArr1=date1
          this.sevOtherArr2=date2
          this.select_machine_load=true
        })

      },
      getMyArr_service1(res){
        // 提交前删除数组最后一项，以符合服务器规定
          // var temp=res.slice(0)
          // temp.length=temp.length-1
          // this.myArr_service1 = temp

        // 不删除，新方案
          this.myArr_service1 = res
      },
      getMyArr_service2(res){
        // 提交前删除数组最后一项，以符合服务器规定
        var temp=res.slice(0)
        temp.length=temp.length-1
        this.myArr_service2 = temp
      },
      click_machine(item){
        console.log(item)
      },
      lastDay(){
        this.dateValue=GetDateStr(new Date(this.dateValue), -1);
      },
      nextDay(){
        this.dateValue=GetDateStr(new Date(this.dateValue), 1);
      },
      submit(){
        console.log(this.o_select_machine[0]==1, this.myArr_service1.length>0)
        var books=[];

        // 判断所选机器和时间
          if(this.o_select_machine.length==2 && this.myArr_service1.length>0  && this.myArr_service2.length>0 ){
            // alert('1,2')
            books=[
              {
                "freezeStartTime":+new Date(this.dateValue), // 冰冻预约日期
                "instrumentId": 1, // 机器号
                  "cells": this.myArr_service1 // 预约时间 0代表0:00-0:30 1代表0:30-1:00 以此类推
              },
              {
                "freezeStartTime":+new Date(this.dateValue),
                  "instrumentId": 2,
                  "cells": this.myArr_service2
              }
            ]

          }else{
            if(this.o_select_machine[0]==1 && this.myArr_service1.length>0){
              // alert('1')

              books=[
                {
                  "freezeStartTime":+new Date(this.dateValue),
                  "instrumentId": 1,
                    "cells": this.myArr_service1
                }
              ]

            }else if(this.o_select_machine[0]==2 && this.myArr_service2.length>0){
              // alert('2')

              books=[
                {
                  "freezeStartTime":+new Date(this.dateValue),
                  "instrumentId": 2,
                    "cells": this.myArr_service2
                }
              ]

            }else{
              this.toast_text='请选择对应的机器和时间'
              this.toast_load=true
              return
            }

          }

        var id=null;

        try{
          id = this.$store.state.applicant.default.id
        }catch(e){
          this.toast_text='由于刷新或网络原因导致申请人信息丢失，请重新选择'
          this.toast_load=true
          return
        }


      var obj={
            "applicantId": id, // #申请者ID
            "researchType":2, // # 1-常规染色 2-冰冻预约
            "samples": this.samples,
            "books": books
          }
      console.log(obj)
        // 发送请求
          service_upData.application_type2(obj).then(res=>{
            this.toast_text='提交成功'
            this.toast_load=true
            this.re_page=true
          },res=>{
            this.toast_text='提交失败'
            this.toast_load=true
          })

      }
    },
    mounted: function () {}
  }
  function GetDateStr(d1,AddDayCount) {
    var dd = d1;
      dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
      var y = dd.getFullYear();
      var m = dd.getMonth()+1;//获取当前月份的日期
      var d = dd.getDate();
      y<=9 ? y='0'+y : '';
      m<=9 ? m='0'+m : '';
      d<=9 ? d='0'+d : '';
      return y+"-"+m+"-"+d;
  }
</script>
<style lang="less" scoped>
  ul,li{
    margin: 0;
    padding: 0;
  }
  .shade_box{
    position: absolute;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0,0,0,.5);
    z-index: 99;
  }
  .select_time{
    position: fixed;
    bottom: 0;
    width: 100%;
    li{
      width: 16.66%;
      float: left;
      text-align: center;
      height: 0.92rem;
      line-height: .92rem;
      margin-top: 0.09rem;
      margin-bottom: 0.09rem;
      position: relative;
    }
    .others{
      color: #ccc;
    }
    .myArr{
      background: #83d5e0;
      color: #fff;
    }
    .myMid{
      background: #ccc;
    }
    .top_title{
      height: 0.87rem;
      line-height: 0.87rem;
      background: #f7f7f7;
      border-bottom: 1px solid #dbdbdb;
      text-align: center;
      .cancel{
        float: left;
        width: 20%;
      }
      .confirm{
        float: right;
        width: 20%;
      }
      .title{
        float: left;
        width: 60%;
      }
    }
    .time_box{
      background: #fff;
      overflow: hidden;
    }
  }
</style>
