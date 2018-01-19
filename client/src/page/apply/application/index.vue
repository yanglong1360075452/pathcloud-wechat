<template>
  <div>

    <userName :name="cur_name"></userName>

    <div class="personal_info">
      <msgTitle text="样本信息"></msgTitle>

      <swipeout v-for="(item, index) in allSample" :key="index">
        <swipeout-item :disabled="index == 0" transition-mode="follow">
          <div slot="right-menu">
            <swipeout-button @click.native="delSample (index)" type="warn">删除</swipeout-button>
          </div>
          <div slot="content" class="demo-content vux-1px-t">
              <sampleItem :obj="item"></sampleItem>
          </div>
        </swipeout-item>
      </swipeout>


    </div>


    <div class="addSample">
      <div @click="addSample"><i></i><span>添加样本</span></div>
    </div>
    <button class="submit" @click="submit">{{isNormal? "提交":"下一步"}}</button>

    <toast v-if="showPositionValue" v-model="showPositionValue" type="text">{{toast_text}}</toast>

    <router-view></router-view>
  </div>

</template>
<style lang="less">
</style>
<style lang="less" scoped>
  /*添加样本*/
    .addSample{
      width: 100%;
      height: 1.73rem;
      position:relative;
    }
    .sampleItem{
      margin-bottom: 0.1rem;
    }
    .addSample>div{
      height: 0.33rem;
      line-height: 0.33rem;
      position:absolute;
      top:50%;
      left:50%;
      transform:translate(-50%,-50%);
      color: #0ab6ca;
    }
    .addSample>div i{
      display: inline-block;
      vertical-align: top;
      width: 0.33rem;
      height: 0.33rem;
      margin-right: 0.11rem;
      background: url(../../../assets/img/add_yb.jpg) no-repeat;
      -webkit-background-size: 100%;
      background-size: 100%;
    }
    .sampleItem{
      margin-bottom: .3rem;
    }


</style>
<script>

  import msgTitle from '../../../components/title.vue'
  import sampleItem from '../../../components/sampleItem.vue'
  import userName from '../../../components/userName.vue'
  import { Swipeout, Toast, SwipeoutItem, SwipeoutButton, XButton } from 'vux'
  import serviceGetData from '../../../service/getData.js'
  import serviceupData from '../../../service/upData.js'
  export default {
    name: 'index',
    data (){
      return {
        toast_text:'', // 操作提示
        cur_name:'', // 默认申请人
        position: 'default',
        showPositionValue: false,
        allSample:[{name:"",key:{key:'1',value:"大样本"}}] // 默认样本
      }
    },
    created(){

      try{
        // console.log(this.$store.state.applicant.default,1111)
        if(this.$store.state.applicant.default){ // 初始申请人存在
          this.cur_name = this.$store.state.applicant.default.firstName
          return
        }else{

          // 获取默认申请人
          serviceGetData.applicant_default_name({isSelf:true}).then(res=>{
            console.log(res.data[0])
            this.$store.state.applicant.default=res.data[0];
            this.cur_name=res.data[0].firstName
          },res=>console.log(res))

        }
      }catch(e){
        console.log('出错',e)
      }

    },
    components:{
      msgTitle, sampleItem, userName, Toast,
      Swipeout, SwipeoutItem, SwipeoutButton, XButton
    },
    methods:{
      delSample (index) { // 删除样本
        console.log('xx', this.allSample, index);
        this.allSample.splice(index, 1)
        console.log('xx', this.allSample, index);
      },
      addSample(){ // 添加样本
         this.allSample.push({name:"",key:{key:'1',value:"大样本"}})
      },
      submit(){ // 提交样本
        var allSample = this.allSample;
        this.$store.commit('allSample',allSample)

        for(var i in allSample){
          if(!(allSample[i].name.replace(/(^\s*)|(\s*$)/g, "")) != ""  || allSample[i].key==''){
            this.toast_text = '请填写名称和类别'
            this.showPositionValue = true
            return
          }
        }


        if(this.isNormal){ // 如果当前路由是提交，而不是下一步


          var applicantId=this.$store.state.applicant.default.id; // 申请者ID
          var researchType=1; // 常规染色

          var samples=[];
          for(var j in allSample){
            samples.push({
              name : allSample[j].name,
              category : allSample[j].key.key
            })
          }

          serviceupData.routine(
              {
                applicantId : applicantId,
                researchType : researchType,
                samples : samples
              }
            ).then(res=>{
              console.log(res)
              this.toast_text = '提交成功'
              this.showPositionValue = true
            })


        }else{ // 下一步
          this.$router.push({name:"reserve"})
        }
      }
    },
    computed: {
      isNormal: function () {
        return this.$route.params.type === "normal"
      }
    }
  }

</script>
