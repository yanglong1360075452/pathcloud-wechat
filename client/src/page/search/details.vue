<template>
  <div class="max_box">
    <x-header :left-options="{backText:''}">
      <span>信息详情</span>
    </x-header>
    <div class="page_box">

      <div class="data_box">
        <!-- 1 常规染色 -->
        <div v-if="show_type==1 && !(type1_data===null) && !(type1_data.length==0)" v-show="show_type==1" class="type1">
          <ul>
            <li v-for="(item, index) in type1_data" :key="index">
              <div class="row r1">
                <div class="col c1"><span>蜡块号：</span><span>{{item.blockSubId}}</span></div>
                <div class="col c2"><span>玻片号：</span><span>{{item.slideSubId}}</span></div>
                <div class="col c3"><span>事件：</span><span>{{item.operationDesc}}</span><em @click="click_em_fn(index)"><i :class="{show_r3r4:show_r3r4 && click_index==index}"></i></em></div>
              </div>
              <div class="row r2">
                <div class="col c1"><span>操作时间：</span><span>{{item.operationTime | dateFormat('YYYY-MM-DD HH:mm')}}</span></div>
              </div>
              <div v-show="show_r3r4 && click_index==index" class="row r3">
                <div class="col c3"><span>操作人：</span><span>{{item.operator}}</span></div>
              </div>
              <div v-show="show_r3r4 && click_index==index" class="row r4">
                <div class="col c4"><span>备注：</span><span>{{item.note}}</span></div>
              </div>
            </li>
          </ul>
        </div>
        <!-- 2 冰冻预约 -->
        <div v-if="show_type==2 && !(type1_data===null) && !(type1_data.length==0)" v-show="show_type==2" class="type2 type3 type1">

          <ul>
            <li v-for="(item, index) in [type1_data]" :key="index">

              <div class="row r2">
                <div class="col c1"><span>预约时间：</span><span>
                  {{item.books[0].freezeStartTime | dateFormat('YYYY-MM-DD　HH:mm')}} -
                  {{item.books[0].freezeEndTime | dateFormat('HH:mm')}}
                </span></div>
              </div>
              <div class="row r1">
                <div class="col c1"><span>冰冻切片机：</span><span class="c000">切片机{{{1:'一', 2:'二'}[item.books[0].instrumentId]}}</span></div>
              </div>
              <div class="row r1" v-for="(samples_item, samples_item_index) in item.samples" :key="samples_item_index">
                <div class="col c1"><span>样本名称：　</span><span class="c000">{{samples_item.name}}</span></div>
              </div>
            </li>
          </ul>
        </div>
        <!-- 3 染色申请 -->
        <div v-if="show_type==3 && !(type1_data===null) && !(type1_data.length==0)" v-show="show_type==3" class="type3 type1">

          <ul>
            <li v-for="(item, index) in type1_data" :key="index">
              <div class="row r1">
                <div class="col c1"><span>病理号：</span><span>{{item.pathNo}}</span></div>
                <div class="col c2"><span>蜡块号：</span><span>{{item.blockSubId}}</span></div>
                <div class="col c2"><span>玻片号：</span><span>{{item.slideSubId}}</span></div>
              </div>
              <div class="row r1">
                <div class="col c1"><span>染色类别：</span><span>{{item.dyeCategoryDesc}}</span></div>
                <div class="col c2 c2_1"><span>试剂/抗体名称：</span><span>{{item.ihcMarker}}</span></div>
              </div>
              <div class="row r2">
                <div class="col c1"><span>操作时间：</span><span>{{item.operateTime | dateFormat('YYYY-MM-DD HH:mm')}}</span></div>
              </div>
            </li>
          </ul>

        </div>
      </div>

      <!-- 空内容 -->
      <div class="null" v-show="type1_data===null || type1_data.length==0">
        <div class="tips">还没有内容</div>
      </div>

    </div>
  </div>
</template>

<style>
  .main{
    background: #f0f0f0;
  }
</style>
<style lang="less" scoped>
  .max_box{
    width: 100%;
    /*height: 100%;*/
    min-height: 100%;
    /*position: fixed;*/
    position: absolute;
    overflow: hidden;
    background: #f3f3f3;
    top: 0;
    z-index: 99;
    .page_box{
      overflow: hidden;
      margin-top: 1.3rem;
      .type1{
        li{
          background: #fff;
          color: #a5afb0;
          font-size: 0.26rem;
          padding: 0.2rem;
          margin-bottom: 0.3rem;
          .row{
            line-height: 0.5rem;
            overflow: hidden;
          }
          .r2 span:nth-child(1),
          .r3 span:nth-child(1),
          .r4 span:nth-child(1){
            display: inline-block;
            width: 1.7rem;

          }
          .r2 span:nth-child(2){
            color: #f74830;
          }
          .r3 span:nth-child(2){
            color: #000;
          }
          .r1 .c1,
          .r1 .c2,
          .r1 .c3{
            float: left;
            width: 2.2rem;
          }
          .r1 .c3{
            width: 2.6rem;
            i{
              display: inline-block;
              width: 0.13rem;
              height: 0.13rem;
              border: 1px solid #0ab6ca;
              border-width: 1px 0 0 1px;
              -webkit-transform: rotate(225deg);
              transform: rotate(225deg);
              margin-bottom: .08rem;
              margin-left: .1rem;
            }
            i.show_r3r4{
              -webkit-transform: rotate(43.4deg);
              transform: rotate(43.4deg);
              margin-bottom: 0;
              margin-left: .1rem;
            }
            em{
              display: inline-block;
              width: 0.3rem;
              float: right;
            }
          }
        }
      }
      .type2{
        .c000{
          color: #000 !important;
        }
      }
      .type3{
        .row{
          .col{
            width: 1.7rem !important;
          }
          .c1{
            width: 100% !important;
          }
          .c2_1{
            width: 3.8rem !important;
          }
        }
        .r2{
          width: 100% !important;
        }
        .r2 .c1{
          width: 100% !important;
        }
      }
      .null{
        text-align: center;
        color: #ccc;
        /*height: 4rem;*/
        /*line-height: 4rem;*/


        color: #ccc;
        text-align: center;
        width: 50%;
        margin: auto;
        position: absolute;
        position: fixed;
        top: 50%; left: 50%;
        -webkit-transform: translate(-50%,-50%);
            -ms-transform: translate(-50%,-50%);
                transform: translate(-50%,-50%);


      }
    }
  }
</style>

<script>
  import {Toast,XHeader, Checker, CheckerItem} from 'vux'
  import { dateFormat } from 'vux'
  import getData from '../../service/getData.js'
  export default{
    created(){
      getData.details({
        id:this.query.aid_aiId,
        type:this.query.type
      }).then(res=>{
        this.type1_data=res
        this.data_box_load=true
        console.log('详情数据',res)

      // 1 常规染色
        // this.type1_data=[{
        //                     "blockSubId": "1", // 蜡块号
        //                     "slideSubId": null,// 玻片号
        //                     "operation": 1,
        //                     "operationDesc": "取材",// 事件名称
        //                     "operator": "管理员",// 操作者
        //                     "operationTime": 1494224125000,// 操作时间
        //                     "note": "备注备注备注备注备注备注备注备注备注备注", // 备注
        //                     "noteType": null // 备注类型
        //                 }]

      // 2 冰冻预约
        // this.type1_data={
        //         "samples": [
        //             {
        //                 "id": 25,
        //                 "applicationId": 22,
        //                 "serialNumber": "SA17000025",
        //                 "name": "fff",
        //                 "category": 1,
        //                 "categoryName": "大样本", // #样本名称
        //                 "createBy": 2,
        //                 "updateBy": 2,
        //                 "createTime": 1496910280000,
        //                 "updateTime": 1496910280000,
        //                 "registerUser": null,
        //                 "registerTime": null
        //             }
        //         ],
        //         "books": [
        //             {
        //                 "freezeStartTime": 1496881800000, // #预约开始时间
        //                 "cells": null,
        //                 "freezeEndTime": 1496885400000, // #预约结束时间
        //                 "instrumentId": 1 // #冰冻切片机
        //             }
        //         ]
        //     }

      // 3 染色申请
        // this.type1_data=[
        //         {
        //             "id": 9,
        //             "pathNo": "Z17000001", // #病理号
        //             "blockSubId": "2", // #蜡块号
        //             "slideSubId": "1", // #玻片号
        //             "dyeCategory": 0,
        //             "dyeCategoryDesc": "HE", // #染色类别
        //             "ihcMarker": null, // #试剂抗体名称
        //             "operateTime": 1496814549000 // #操作时间
        //         }
        //     ]


      },res=>{
        console.log(res)
      })
    },
    data(){
      return{
        // show_more:false, // 展开与折叠更多
        data_box_load:false, // 是否加载数据 dom
        click_index:-1, // 当前点击有的项
        show_type:this.$route.query.type, // 常规染色 冰冻预约 染色申请
        show_r3r4:false, // 显示第三行和第四行
        query: this.$route.query, // 查询内容
        type1_data:null, // 常规染色数据
        type2_data:null, // 常规染色数据
        type3_data:null, // 常规染色数据
      }
    },
    components:{
      XHeader
    },
    methods:{
      click_em_fn(index){
        this.click_index=index
        this.show_r3r4=!this.show_r3r4
      }
    },
    filters:{
      dateFormat(par,form){
        return dateFormat(new Date(par), form)
      }
    },
  }
  // console.log(aa)
</script>
