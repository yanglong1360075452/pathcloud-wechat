<template>
  <div>

    <x-header :left-options="{backText:'', showBack:false}">
      <div slot="right">
        <search
          class="search_vux"
          :results="results"
          placeholder="请输入病理号进行查询"
          :autoFixed="false"
          v-model="value"
          @on-change="getResult"
          @on-focus="focus_search_fn"
          @on-cancel="cancel_search_fn"
          @on-result-click="resultClick"
          ref="search"></search>
      </div>
    </x-header>

    <div class="search_bar">
      <!-- <i name="返回"></i> -->
    </div>


    <!-- 筛选排序 -->
    <div  v-show="show_focus_search_fn" class="filter_sort">
      <div class="filter">
        <div class="menu triangle_i" @click="showContent=!showContent">
          <span><em>筛选</em><i></i></span>
        </div>
        <div class="content" @click="showContent=!showContent" v-show="showContent">
          <div class="box" @click.stop="''">
            <!-- {{user_status}} {{user_type}} {{user_time}} -->
            <div class="conditio">


              <checker class="dl" v-model="user_time">
                <div class="dt">时间</div>
                <div @click="user_time='1'" class="dd" :class="{cur : user_time == 1}"><checker-item :value="1">最近一个月</checker-item></div >
                <div @click="user_time='2'" class="dd" :class="{cur : user_time == 2}"><checker-item :value="2">最近三个月</checker-item></div >
                <div @click="user_time='3'" class="dd" :class="{cur : user_time == 3}"><checker-item :value="3">最近一年</checker-item></div >
              </checker>

              <checker class="dl" v-model="user_status">
                <div class="dt">状态</div>
                <div class="dd" :class="{cur : user_status == 1}"><checker-item :value="1">处理中</checker-item></div >
                <div class="dd" :class="{cur : user_status == 2}"><checker-item :value="2">已完成</checker-item></div >
              </checker>

              <checker class="dl" v-model="user_type">
                <div class="dt">类型</div>
                <div class="dd" :class="{cur : user_type == 1}"><checker-item :value="1">常规染色</checker-item></div >
                <div class="dd" :class="{cur : user_type == 2}"><checker-item :value="2">冰冻预约</checker-item></div >
                <div class="dd" :class="{cur : user_type == 3}"><checker-item :value="3">染色申请</checker-item></div >
              </checker>


            </div>
            <div class="yes_no">
              <button class="clear reset" @click="reset">重置</button>
              <button class="confirm" @click="confirm">确定</button>
            </div>


          </div>
        </div>

      </div>
      <div class="sort">

        <div class="menu triangle_i" @click="switch_sort">
          <span><em>{{ user_sort==1 ? '排序' : '排序' }}</em><i :class="{ switch: user_sort==1 }"></i></span>
        </div>

      </div>

      <!-- 当前筛选标签 -->
      <div class="cur_filter">
        <span> {{ ['最近一个月', '最近三个月', '最近一年'][user_time-1] }}</span>
        <span v-show="user_status!='' "> {{ ['处理中', '已完成'][user_status-1] }}</span>
        <span v-show="user_type!='' " > {{ ['常规染色', '冰冻预约', '染色申请'][user_type-1] }}</span>
      </div>
    </div>


    <div class="scroller_box" :class="{search:!show_focus_search_fn}">
      <div class="page-loadmore-wrapper" ref="wrapper" :style="{ height: wrapperHeight + 'px' }">

        <mt-loadmore
          bottomPullText="上拉加载更多"
          bottomDropText="释放开始加载"
          :top-method="loadTop"
          @top-status-change ="handleTopChange"
          :bottom-method="loadBottom"
          @bottom-status-change="handleBottomChange"
          :bottom-all-loaded="allLoaded"
          ref="loadmore">

          <div class="page-loadmore-list">
            <!-- 如果注释这个ui会执行loadBottom，修改样式也会，在其中添加内容也会。页面跳动与执行loadBottom有关。 -->
            <!-- 与其中v-for绑定的list值有关，如果使用其他值会执行loadBottom -->
            <!-- list在created时push入内容，如果在那时不push也会 -->
            <!-- 注释.page-loadmore-listitem样式，也会 -->
            <!-- 组件是根据li高度判断上拉的，所以需要给li高度 -->
            <!-- 使用自己的ul会自动跳 -->
            <ul v-tap="{ methods : click_item2, index : index, aid_aiId:item.aid || item.aiId, text: item.astatusDesc, type:['常规染色','冰冻预约','染色申请'].indexOf(item.astatusDesc)+1 }" @click_back="click_item(index, item.aid || item.aiId, item.astatusDesc, ['常规染色','冰冻预约','染色申请'].indexOf(item.astatusDesc)+1 )" v-for="(item, index) in info_list.data" class="data_item data_box" :index="index + ' ' + item.aid" :key="index">
              <li class="li l1">
                <span class="title_time">{{item.astatus == 30 ? "预约时间" : "申请时间"}}：</span>
                <span v-show="item.astatus != 30 && item.astatusDesc!='染色申请'" class="title_time_data">{{item.applicationTime | dateFormat('YYYY-MM-DD HH:mm')}}<!-- 申请时间 2017-05-24 17:33 --></span>
                <span v-show="item.astatusDesc=='染色申请'" class="title_time_data">{{item.aiApplicationTime | dateFormat('YYYY-MM-DD HH:mm')}}<!-- 申请时间 2017-05-24 17:33 --></span>


                <span v-show="item.astatus == 30" class="title_time_data">
                  <em style="display:block; font-style:normal">{{item.bookingStartTime | dateFormat('YYYY-MM-DD')}}</em>
                  <em style="display:block; font-style:normal">{{item.bookingStartTime | dateFormat('HH:mm')}} - {{item.bookingEndTime | dateFormat('HH:mm')}}<!-- 预约时间 2017-05-24 17:33 --></em>
                </span>
                <span v-show="item.astatusDesc" class="type">{{item.astatusDesc}} <!-- 申请类型 --></span>
              </li>
              <li  v-show="item.astatus != 30" class="li l2">
                <span class="title_time">{{item.astatus === null ? '病理号' : '申请号'}}：</span>
                <span class="title_time_data">{{ item.astatus === null ? item.pserialNumber : item.aserialNumber }} <!-- 申请号 --></span>

              </li>
              <li class="li l3">
                <span class="title_time">{{item.astatus == 30 ? "冰冻机" : "病理号"}}：</span>
                <span class="title_time_data">{{ item.astatus == 30 ? item.instrumentIdDesc : (item.astatus == null ? '......' : item.pserialNumber) }} <!-- 机器名、病理号 --></span>
                <span v-show="item.astatus != 30 " class="type">{{ item.pstatusDesc }} <!-- 处理状态 --></span>
                <span v-show="item.astatus == 30" v-tap="{ methods : click_pre, index:index, aid : item.aid  }" @click_back.stop="click_pre(index, item.aid, $event)"  class="type cancel">取消预约</span>
              </li>
            </ul>

          </div>



          <div class="no_moer" v-show="allLoaded">
            <p>没有更多了</p>
          </div>

        </mt-loadmore>

          <div class="zw">
            <p>占位</p>
            <p>占位</p>
          </div>


      </div>
    </div>



    <!-- 空内容 -->
    <div class="null" v-show="!info_list.total && !$store.state.loading"> <!-- 为了不与 loading 显示重叠 -->
      <div class="tips">还没有内容</div>
    </div>



    <div v-transfer-dom>
      <confirm v-model="confirm_show"
      title="温馨提示"
      @on-cancel="confirm_show=false"
      @on-confirm="cancel_pre_ok">
        <p style="text-align:center;">您确定要取消预约吗？</p>
      </confirm>
    </div>


    <toast v-if="toast_load" v-model="toast_load" type="text">{{toast_text}}</toast>
    <router-view></router-view>

  </div>
</template>

<style lang="less" ></style>

<style lang="less" scoped>
  .scroller_box{
    /*margin-bottom: 4rem;*/
    .no_moer{
       text-align: center;
       color: #ccc;
    }
    .zw{
      color: #f0f0f0;
    }
  }
  .scroller_box.search{
    margin-top: 2rem;
  }
  .search_bar{
    margin-top: 1rem;
  }
  .vux-search-box{
    width: 6.0rem;
  }
</style>
<style lang="less" scoped>


  /* 筛选排序 */
    .filter_sort{
      overflow: hidden;
      line-height: 0.68rem;
      background: #fff;
      border: 1px solid #f3f3f3;

      /* 筛选 */
      .filter{
        float: left;
        width: 50%;
        text-align: center;
        span{
          color: #a5afb0;
          color: #0ab4cb;
          em{
            font-style: normal;
          }
        }
        .content{
          top: 0;
          z-index: 99;
          position: fixed;
          width: 100%;
          height: 100%;
          background: rgba(0,0,0,.5);
          .box{
            background: #fff;
            /* 条件 */
            .conditio{
              margin-bottom: 0.23rem;
              text-align: left;
              .dl{
                display: block;
                padding-left: 0.54rem;
                padding-top: 0.24rem;
                border-bottom: 0.01rem solid #f3f3f3;
                .dt{
                  display: block;
                  color: #a5afb0;
                }
                .dd{
                  display: inline-block;
                  height: 0.56rem;
                  line-height: 0.56rem;
                  padding: 0.14rem;
                  padding-top: 0;
                  padding-bottom: 0;
                  margin-right: 0.4rem;
                  font-size: 0.26rem;
                  border: 0.01rem solid #f3f3f3;
                  border-radius: 0.06rem;
                  margin-bottom: 0.35rem;
                }
                .dd.cur{
                  background: #0ab6ca;
                  border-color: #0ab6ca;
                  color: #fff;
                }
              }
            }
            /* 按钮 */
            .yes_no{
              button{
                width: 3.4rem;
                height: 0.8rem;
                color: #fff;
                background: #0ab6ca;
                border-radius: 0.06rem;
                margin-bottom: 0.2rem;
              }
              .clear{
                background: #999999;
              }
            }
          }
        }
      }
      /* 选择的标签 */
      .cur_filter{
        width: 100%;
        height: 0.72rem;
        background: #f3f3f3;
        float: left;
        color: #c2c2c2;
        font-size: 0.23rem;
        text-align: center;
        span{
          display: inline-block;
          height: 0.42rem;
          padding-top: 0.1rem;
          padding-left: 0.2rem;
          padding-right: 0.2rem;
          line-height: 0.36rem;
          border: 1px solid #e4e4e4;
          margin-left:0.1rem;
          margin-right: 0.1rem;
          vertical-align: middle;
        }
      }
      /* 排序 */
      .sort{
        float: left;
        width: 50%;
        text-align: center;

        .triangle_i i.switch{
          -webkit-transform: rotate(225deg);
          transform: rotate(44.4deg);
          margin-bottom: -0.06rem;
        }

        span{
          color: #a5afb0;
          color: #0ab4cb;
          em{
            font-style: normal;
          }
        }
      }
    }

  /* 申请项目 */

    .data_all{
      overflow: hidden;
      margin-bottom: 1.5rem;
    }
    .data_box{ /* 数据信息 */

      overflow: hidden;
      background: #fff;
      margin-bottom: 0.2rem;


      float: left;
      overflow: hidden;
      padding-top: 0.20rem;
      padding-bottom: .2rem;
      margin-bottom: 0.20rem;
      .li{
        display: block;
        overflow: hidden;
        float: left;
        width: 100%;
        /*margin-top: 0.05rem;
        margin-bottom: 0.05rem;*/
        min-height: 0.64rem;
        line-height: 0.64rem;
      }
      .li .type{
        float: right;
        margin-right: 0.33rem;
        padding-left: 0.12rem;
        padding-right: 0.12rem;
        display: inline-block;
      }
      .title_time{
        color: #a5afae;
        width: 2.25rem;
        float: left;
        text-indent: 0.25rem;
        display: inline-block;
      }

      .title_time_data{
        display: inline-block;
        float: left;
        width: 2.8rem;
        font-size: 0.24rem;
        /*padding-top: .1rem;*/
      }

      .l1 .title_time_data{
        color: #f74930;
      }
      .l1 .type,
      .l3 .cancel{
        display: inline-block;
        border: 1px solid #0ab6ca;
        height: 0.46rem;
        line-height: 0.46rem;
        border-radius: 0.24rem;
        color: #0ab6ca;
        padding-left: 0.12rem;
        padding-right: 0.12rem;
      }
      .l3 .type{
        color: #f74930;
        float: right;
        margin-right: 0.33rem;
      }
      .l3 .cancel{
        color: #fff;
        background: #0ab6ca;
        padding-left: 0.24rem;
        padding-right: 0.24rem;
        height: 0.5rem;
        line-height: 0.5rem;
        border-radius: 0.08rem;
      }
    }

</style>
<style lang="less">
  body{
    .vux-search-box{
      .weui-search-bar{
        padding: 0;
        top: -0.09rem;
        background: none;
        .weui-search-bar__box .weui-search-bar__input{
          color: #aaaaaa;
          font-size: 0.26rem;
        }
        .weui-search-bar__label .weui-icon-search{
          margin-left: 0.2rem;
        }
        .weui-search-bar__label span{
          color: #aaaaaa;
          font-size: 0.26rem;
        }
        .weui-search-bar__label{
          text-align: left;
          background: #f3f3f3;
          color: #a9a9a9;
        }

      }

    }
    .weui-search-bar:after,
    .weui-search-bar:before{
      border: 0;
    }
  }
</style>
<script>

  import { Scroller, Divider, Spinner } from 'vux'
  import { Search, XHeader } from 'vux'
  import { Checker, CheckerItem } from 'vux'
  import serviceGetData from '../../service/getData.js'
  import { dateFormat } from 'vux'
  import { Toast, Confirm, Group, XSwitch, XButton, TransferDomDirective as TransferDom } from 'vux'
  import { XDialog } from 'vux'
  import service_upData from '../../service/upData.js'


  export default {
    directives: {
        TransferDom
    },
    components: {
      Search, Scroller, Divider, Spinner,
      XHeader, Checker, CheckerItem, Confirm, XDialog, Toast
    },
    created(){
      this.list=[1,2] // 如果没有这个值不以上拉


      // 查询数据要求
        // page | int | 页数
        // length | int | 每页记录数
        // createTimeStart | Long | 申请开始时间
        // createTimeEnd | Long | 申请结束时间
        // status | int | 1 代表处理中，2代表已完成
        // type | int | 1 代表常规染色 ，2代表 冰冻预约 ，3代表染色申请，什么都不传是全部
        // filter | String | 病理号
        // sort | int | 1 代表升序，2代表降序


      serviceGetData.info_list({
        createTimeEnd:(+new Date()),
        createTimeStart:(+new Date())-{1:2592000000, 2:7776000000, 3:31536000000}[this.user_time], // 默认获取最近一个月数据
        type:(this.user_type==4 ? '' : this.user_type), // 默认状态 处理中
        status:this.user_status, // 1 代表处理中，2代表已完成
        filter:this.filter, // 病理号
        page:this.user_page, // 数据页数
        sort:this.user_sort, // 1 代表升序，2代表降序
        length:this.user_length, // 每页记录数

      }).then(res=>{
        this.info_list = res
        this.wrapperHeight = document.documentElement.clientHeight - this.$refs.wrapper.getBoundingClientRect().top;

        // this.$store.state.info_list = res
      },res=>console.log(res))


    },
    mounted() {
      // this.wrapperHeight = document.documentElement.clientHeight - this.$refs.wrapper.getBoundingClientRect().top;
    },
    filters:{
      dateFormat(par,form){
        return dateFormat(new Date(par), form)
      }
    },
    data () {
      return {
        show_focus_search_fn:true,
        // n: 10,

        list: [],
        allLoaded: false,
        bottomStatus: '',
        wrapperHeight: 0,

        no_moer:false, // 没有更多
        value: '',

        user_time:1, // 默认时间 最近一个月
        user_type:'', // 1 代表常规染色 ，2代表 冰冻预约 ，3代表染色申请，什么都不传是全部
        user_status:'', // 1 代表处理中，2代表已完成，什么都不传是全部
        user_filter:'', // 病理号
        user_page:1, // 数据页数
        user_sort:2, // 1 代表升序，2代表降序
        user_length:10, // 每页记录数

        // user_page_load:1, // 分布器自增的页码
        filter_status:null,
        filter_type:null,
        toast_load:false,
        results: [], // 搜索条搜索结果
        data_index:-1,// 当前点击的数组位置
        info_list:{}, // 申请信息列表
        confirm_show:false, // 取消预约提示
        showContent:false, // 筛选
        cancel_pre_id:'', // 取消预约

      }
    },
    computed:{
      to_user_time(){
        var mydate = new Date();
        return {one_month:mydate-2592000000, three_months:mydate-7776000000, a_year:mydate-31536000000}
      }
    },
    methods:{
      handleTopChange(status) {
        this.moveTranslate = 1;
        this.topStatus = status;
      },
      handleBottomChange(status) {
        this.bottomStatus = status;
      },
      loadTop() {


        this.user_page=1 // 重置第一页
        console.log('上拉刷新中')
        setTimeout(() => {

          serviceGetData.info_list({
            createTimeEnd:(+new Date()),
            createTimeStart:(+new Date())-{1:2592000000, 2:7776000000, 3:31536000000}[this.user_time], // 默认获取最近一个月数据
            type:(this.user_type==4 ? '' : this.user_type), // 默认状态 处理中
            status:this.user_status, // 1 代表处理中，2代表已完成
            filter:this.filter, // 病理号
            page:this.user_page, // 数据页数
            sort:this.user_sort, // 1 代表升序，2代表降序
            length:this.user_length, // 每页记录数

          }).then(res=>{
            this.info_list = res
            this.$store.state.info_list = res
            this.$refs.loadmore.onTopLoaded();
            console.log('上拉刷新完成')
            this.allLoaded = false;

          },res=>console.log(res))


        }, 1000);

      },
      loadBottom() {
        console.log('loadBottom')

        setTimeout(() => {
          if( this.info_list.data.length<this.info_list.total ){

            this.user_page++
            console.log('ld',this.user_page, this.info_list.data.length, this.info_list.total)

            serviceGetData.info_list({
              createTimeEnd:(+new Date()),
              createTimeStart:(+new Date())-{1:2592000000, 2:7776000000, 3:31536000000}[this.user_time], // 默认获取最近一个月数据
              type:(this.user_type==4 ? '' : this.user_type), // 默认状态 处理中
              status:this.user_status, // 1 代表处理中，2代表已完成
              filter:this.filter, // 病理号
              page:this.user_page, // 数据页数
              sort:this.user_sort, // 1 代表升序，2代表降序
              length:this.user_length, // 每页记录数
            }).then(res=>{
              this.info_list.data = this.info_list.data.concat(res.data)
              this.$store.state.info_list.data = this.info_list.data
            },res=>console.log(res))
          }else{
            this.allLoaded = true;
          }

          try {
            this.$refs.loadmore.onBottomLoaded();
          } catch(err) {
            console.log('页面切出，this.$refs.loadmore.onBottomLoaded(); ')
          }


        }, 1000)

        // let lastValue = this.list[this.list.length - 1];
        // if (lastValue < 40) {
        //   for (let i = 1; i <= 10; i++) {
        //     this.list.push(lastValue + i);
        //   }
        // } else {
        //   this.allLoaded = true;
        // }


      },

      focus_search_fn(){
        console.log('jd')
      },
      cancel_search_fn(){
        console.log('取消搜索')
        this.show_focus_search_fn=true

        if(this.user_time==''){
          alert('必须选择一个时间')
          return
        }

        // 重置为第一页并开启上拉功能
          this.user_page=1
          this.allLoaded = false;

        serviceGetData.info_list({
          createTimeEnd:(+new Date()),
          createTimeStart:(+new Date())-{1:2592000000, 2:7776000000, 3:31536000000}[this.user_time], // 默认获取最近一个月数据
          type:(this.user_type==4 ? '' : this.user_type), // 默认状态 处理中
          status:this.user_status, // 1 代表处理中，2代表已完成
          filter:this.filter, // 病理号
          page:this.user_page, // 数据页数
          sort:this.user_sort, // 1 代表升序，2代表降序
          length:this.user_length, // 每页记录数

        }).then(res=>{
          this.info_list = res
          this.$store.state.info_list = res
        },res=>console.log(res))



      },

      cancel_pre_ok(){
        service_upData.cancel_pre(this.cancel_pre_id).then(res=>{
          this.toast_text='已取消'
          this.toast_load=true
          this.info_list.data.splice(this.data_index,1); // 删除本地数据
        },res=>{
          this.toast_text='失败'
          this.toast_load=true
        })
      },
      click_item2(params){
        // console.log(2,params.index, params.aid_aiId, params.text, params.type)

        this.$router.push({name:'details',query:{index:params.index, aid_aiId:params.aid_aiId, text:params.text, type:params.type}})

      },
      click_item(index, aid_aiId, text, type){
        console.log(1,index, aid_aiId, text, type)
        return
        this.$router.push({name:'details',query:{index:index, aid_aiId:aid_aiId, text:text, type:type}})
        console.log(index, aid_aiId, type)
      },
      click_pre(params){
        console.log(params.event)
        console.log(params.event.stopPropagation())
        this.data_index = params.index // 当前点击的数据位置
        this.cancel_pre_id=params.aid
        this.confirm_show=true
      },

      resultClick(item){
        console.log(item)
        this.show_focus_search_fn=false
        serviceGetData.info_list({
          createTimeEnd:(+new Date()),
          createTimeStart:(new Date() - 31536000000), // 默认获取一年前数据,
          // status:this.user_status,
          // type:this.user_type == 4 ? '' : this.user_type ,
          sort:this.user_sort,
          filter:item.myval,
        }).then(res=>{
            this.info_list = res
            this.$store.state.info_list = res
        },res=>console.log(res))

        // this.info_list.data=[]
        // alert( JSON.stringify(item) )
      },
      getResult (val) {
        this.results = val ? getResult(this.value) : []
      },
      switch_sort(){

        var createTimeEnd=(+new Date());
        var createTimeStart=(new Date() - 31536000000); // 默认获取一年前数据

        this.user_sort = this.user_sort==1 ? 2 : 1;

        serviceGetData.info_list({
          createTimeEnd:(+new Date()),
          createTimeStart:(+new Date())-{1:2592000000, 2:7776000000, 3:31536000000}[this.user_time], // 默认获取最近一个月数据
          type:(this.user_type==4 ? '' : this.user_type), // 默认状态 处理中
          status:this.user_status, // 1 代表处理中，2代表已完成
          filter:this.filter, // 病理号
          page:this.user_page, // 数据页数
          sort:this.user_sort, // 1 代表升序，2代表降序
          length:this.user_length, // 每页记录数

        }).then(res=>{
          this.info_list = res
          this.$store.state.info_list = res
        },res=>console.log(res))


      },
      reset(){
        this.user_time=1; // 默认时间 最近一个月
        this.user_type=''; // 默认状态 处理中
        this.user_status=''; // 1 代表处理中，2代表已完成
        this.user_filter=''; // 病理号
        this.user_page=1; // 数据页数
        this.user_sort=2; // 1 代表升序，2代表降序
        this.user_length=10; // 每页记录数
      },
      confirm(){
        if(this.user_time==''){
          alert('必须选择一个时间')
          return
        }
        this.showContent=!this.showContent

        // 重置为第一页并开启上拉功能
          this.user_page=1
          this.allLoaded = false;

        serviceGetData.info_list({
          createTimeEnd:(+new Date()),
          createTimeStart:(+new Date())-{1:2592000000, 2:7776000000, 3:31536000000}[this.user_time], // 默认获取最近一个月数据
          type:(this.user_type==4 ? '' : this.user_type), // 默认状态 处理中
          status:this.user_status, // 1 代表处理中，2代表已完成
          filter:this.filter, // 病理号
          page:this.user_page, // 数据页数
          sort:this.user_sort, // 1 代表升序，2代表降序
          length:this.user_length, // 每页记录数

        }).then(res=>{
          this.info_list = res
          this.$store.state.info_list = res
        },res=>console.log(res))


      },
    }
  }



  function getResult (val) {
    $('.weui-cells.vux-search_show').show()
    // .prepend(`
    //     <div class="getResult_xw"></div>

    //   `);
    let rs = [{
      title: `点击查询`,
      myval: val
    }]
    // for (let i = 0; i < 20; i++) {
    //   rs.push({
    //     title: `${val} result: ${i + 1} `,
    //     other: i
    //   })
    // }
    return rs
  }







</script>
