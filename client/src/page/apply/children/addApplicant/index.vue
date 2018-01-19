<template>
  <div>
    <!-- Xheader -->
    <x-header :left-options="{backText:''}"><span>申请人</span><a slot="right" @click="confirm">确定</a></x-header>

    <div>
      <div class="page-loadmore">
        <div class="page-loadmore-wrapper" ref="wrapper" :style="{ height: wrapperHeight + 'px' }">
          <mt-loadmore
            bottomPullText="上拉加载更多"
            bottomDropText="释放开始加载"
            @top-status-change="handleTopChange"
            :bottom-method="loadBottom"
            @bottom-status-change="handleBottomChange"
            :bottom-all-loaded="allLoaded"
            ref="loadmore">

            <div class="dataList">
              <checker v-model="defaultVal" default-item-class="default-item-class" selected-item-class="selected-item-class">
                <checker-item  @on-item-click="onItemClick" :value="item" v-for="(item, index) in applicant_data" :key="item.id">
                  <div class="ico_box">
                    <i class="yb_ico"></i>
                  </div>
                  <div class="name_subject">
                    <span class="name">{{item.firstName}}</span>
                    <span class="subject">{{item.departmentsDesc}}</span>
                  </div>
                  <div class="education">
                    <span>{{item.identityDesc}}</span>
                  </div>
                  <div class="button_box">
                    <i class="del" @click="del(item.id, index)"></i>
                    <router-link tag="i" class="edit" :to="{path:'createApplicant',query:{id: item.id}}"></router-link>
                  </div>
                </checker-item>
              </checker>
            </div>


            <div class="no_moer" v-show="allLoaded">
              <p>没有更多了</p>
            </div>

          </mt-loadmore>

          <div class="zw">
            <p>&nbsp;</p>
            <p>&nbsp;</p>
          </div>

        </div>
      </div>
    </div>


    <!-- 数据列表 -->
    <div class="dataList" v-if="false">
      <checker v-model="defaultVal" default-item-class="default-item-class" selected-item-class="selected-item-class">
        <checker-item  @on-item-click="onItemClick" :value="item" v-for="(item, index) in applicant_data" :key="item.id">
          <div class="ico_box">
            <i class="yb_ico"></i>
          </div>
          <div class="name_subject">
            <span class="name">{{item.firstName}}</span>
            <span class="subject">{{item.departmentsDesc}}</span>
          </div>
          <div class="education">
            <span>{{item.identityDesc}}</span>
          </div>
          <div class="button_box">
            <i class="del" @click="del(item.id, index)"></i>
            <router-link tag="i" class="edit" :to="{path:'createApplicant',query:{id: item.id}}"></router-link>
          </div>
        </checker-item>
      </checker>
    </div>


    <!-- 底部 -->
    <div class="footer btn" @click="createApplicant">
      <span>新建申请人</span>
    </div>

    <toast v-if="toastShow" v-model="toastShow" type="text" :time="1000">{{toastText}}</toast>
    <toast v-if="showSuccess" v-model="showSuccess" :time="1000">成功</toast>
    <actionsheet v-model="show_action" :menus="actionsheetCfg" @on-click-menu-delete="onDelete" :show-cancel="true"></actionsheet>

  </div>
</template>
<script>


  import {Spinner, Actionsheet, Scroller, Toast,XHeader, Checker, CheckerItem} from 'vux'
  import userService from '../../../../service/user.js'
  import getDataService from '../../../../service/getData.js'
  import upDataService from '../../../../service/upData.js'

  export default{
    components:{
      XHeader, Scroller, Toast, Checker, CheckerItem, Actionsheet, Spinner
    },
    mounted(){

    },
    created(){
      for (let i = 1; i <= 20; i++) {
        this.list.push(i);
      }

      userService.getApplicant({
        page:this.query_page,
        length:this.query_length
      }).then((res)=>{ // 读取申请人列表
        console.log(this.query_total=res.total)
        this.applicant_data=res.data;
        this.wrapperHeight = document.documentElement.clientHeight - this.$refs.wrapper.getBoundingClientRect().top;

        try{
            // console.log(this.$store.state.applicant.default,1111)
            if(this.$store.state.applicant.default){ // 初始申请人存在
              console.log('初始申请人存在')

              this.defaultVal=this.$store.state.applicant.default;
              // this.cur_name = this.$store.state.applicant.default.firstName
              return
            }else{

              this.defaultVal=res.data[0];

            }




        }catch(e){
            console.log('出错',e)
        }

      },function (err){
        console.log(err);
      });


    },
    data (){
      return {
        list: [],
        allLoaded: false,
        bottomStatus: '',
        wrapperHeight: 0,

        query_page : 1, // 页数
        query_length : 10, // 每页记录数
        query_isSelf : true, // 是否是当前用户
        query_total : -1 , // 服务器数据总量

        defaultVal: {},
        applicant_data: [],
        toastShow:false,
        toastText:'',
        show_action:false, // 显示删除提示
        showSuccess:false,
        del_obj:{}, // 要删除的申请人
        actionsheetCfg:{
          'title.noop': '确定删除吗？',
           delete: '<span style="color:red">确定</span>'
        }
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

        console.log('上拉刷新中')
        setTimeout(() => {
          // let firstValue = this.list[0];
          // for (let i = 1; i <= 10; i++) {
          //   this.list.unshift(firstValue - i);
          // }
          this.$refs.loadmore.onTopLoaded();
          console.log('上拉刷新完成')
        }, 1500);

      },
      loadBottom() {
        console.log('loadBottom',this.applicant_data.length <= this.query_total, this.applicant_data.length ,this.query_total)

        if ( this.applicant_data.length < this.query_total ) {
          this.query_page++;
          userService.getApplicant({
            page: this.query_page,
            length:this.query_length
          }).then((res)=>{ // 读取申请人列表
            // this.applicant_data=res.data;
            this.applicant_data = this.applicant_data.concat(res.data)
            try{
                // console.log(this.$store.state.applicant.default,1111)
                if(this.$store.state.applicant.default){ // 初始申请人存在
                  console.log('初始申请人存在')

                  this.defaultVal=this.$store.state.applicant.default;

                  // this.cur_name = this.$store.state.applicant.default.firstName
                  return
                }else{

                  this.defaultVal=res.data[0];

                }




            }catch(e){
                console.log('出错',e)
            }

          },function (err){
            console.log(err);
          });
        } else {
          this.allLoaded = true;
        }
        this.$refs.loadmore.onBottomLoaded();



      },



      createApplicant(){
        this.$router.push({name:"createApplicant"});
      },
      onItemClick (value, disabled) {
        console.log(this.disabled, this.showPopup, value, disabled)
        this.defaultVal = value
      },
      confirm(){
        console.log(11)
        if(this.defaultVal==''){
          // alert('请选择申请人')
          this.toastText='请选择申请人'
          this.toastShow=true
          return
        }
        this.$store.commit('applicant',{default: this.defaultVal})
        window.history.back()
        // this.$router.push({path:"/app/main/apply/application/normal"})
      },

      onDelete () {
        var id=this.del_obj.id
        var index=this.del_obj.index

        // 如果删除的是默认申论人，则更新 store
          if(id == this.$store.state.applicant.default.id){
            this.toastText='不可删除当前所选申请人'
            this.toastShow=true
            // alert('不可删除当前所选人')
            // this.$store.commit('applicant',{})
          }else{

            userService.delApplicant(id).then(res=>{
              this.showSuccess=true
              console.log(id,index)
              this.applicant_data.splice(index,1);
              this.defaultVal=this.$store.state.applicant.default;

            },function (err){
              console.log(err);
            });

          }




      },
      del(id,index){
        this.show_action=true // 显示删除确认
        this.del_obj.id=id
        this.del_obj.index=index

      },
      edit(id,index){
        console.log('编辑');

      }

    }

  }
</script>
<style lang="less" scoped>

  .dataList{
    overflow: hidden;
    background: #fff;
  }
  .dataList{
    overflow: hidden;
    /*margin-bottom: 1.5rem;*/
  }
  .vux-tap-active:active{
    background: #fff;
  }
  .vux-checker-item{
    height: 1.18rem;
    width: 96%;
    border-bottom: 0.01rem solid #dadada;
    float: right;
    .ico_box{
      width: 0.7rem;
      float: left;
      height: 100%;
      .yb_ico{
        margin-top: 0.4rem;
      }
    }
    .button_box{
      width: 33%;
      height: 100%;
      float: right;
      .del, .edit{
        width: 0.57rem;
        height: 0.57rem;
        display: block;
        float: left;
        margin-top: 0.3rem;
        margin-left: 0.38rem;
        -webkit-background-size: 100%;
        background-size: 100%;
        background-repeat: no-repeat;

      }
      .del{
        background-image: url(../../../../assets/img/del2.jpg);
      }
      .edit{
        background-image: url(../../../../assets/img/edit2.jpg);
      }
    }
    .name_subject{
      width: 27.6%;
      overflow: hidden;
      float: left;
      margin-top: 0.2rem;
      white-space: nowrap;
      overflow: hidden;
      .name{
        display: block;
        font-size: 0.27rem;
        overflow: hidden;
        white-space: nowrap;
        -ms-text-overflow: ellipsis;
        text-overflow: ellipsis;
      }
    }

    .subject{
      display: block;
      color: #8798ae;
      font-size: 0.23rem;
      float: left;
    }
    .education{
      float: left;
      height: 100%;
      line-height: 1.18rem;
      width: 27%;
      color: #f74830;
      font-size: 0.26rem;
    }

  }
  .default-item-class{
    .yb_ico{
      display: inline-block;
      width: 0.4rem;
      height: 0.4rem;
      -webkit-background-size: 100%;
      background: url(../../../../assets/img/yb_no_cur.jpg);
      background-size: 100%;
      vertical-align: text-bottom;
      margin-right: 0.07rem;
    }
  }
  .selected-item-class{
    color: #424242 !important;
    .yb_ico{
      background: url(../../../../assets/img/yb_cur.jpg);
      background-size: 100% 100%;
    }
  }

</style>
