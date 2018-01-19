<template>
  <!-- 组件测试 -->
  <div class="test">
  服务器： {{myArr_service}} <br>
  预显示： {{myArr_service | toSemih(a_semih)}} <br>
  <span @click="b_shade_box=!b_shade_box">
    显示： {{myArr_service | toSemih(a_semih)}} <br>
  </span>



    <div class="shade_box" v-show="b_shade_box">
      <div class="select_time">
        <div class="top_title">
          <div class="cancel" @click="cancel"><span>取消</span></div>
          <div class="title" v-show="myArr_service.length" @click="myArr=[]"><span>重置</span></div>
          <div class="confirm" v-show="myArr_service.length" @click="confirm"><span>确定</span></div>
        </div>
        <div class="time_box">
          <ul>
            <li @click="select_fn(index)" :class="{
              'others' : others[index]==index,
              'myArr' : myArr[index]==index
            }" v-show="index > 8*2-1 && index <= 18*2+1" v-for="(item, index) in a_semih" :key="index"><span>{{item}}</span></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default{
  data(){
    return{
      a_semih : [], // 半小时
      start : -1 , // 暂存开始点击位置
      end : -1 , // 暂存结束点击位置
      others:[],  // 已经被预约时间，转换完整数组
      myArr:[], // 自己选择的时间
      b_shade_box:false,
    }
  },
  filters: {
    toSemih(arr, a_semih){
      if(!arr.length){
        return '请点击选择时间'
      }
      // return a_semih[arr[0]-1]+" - "+a_semih[arr[arr.length-1]] // 8:00 - 8:30
      // return a_semih[arr[0]]+" - "+a_semih[arr[arr.length-1]] // 17 8:30 - 8:30
      return a_semih[arr[0]]+" - "+a_semih[arr[arr.length-1]+1] // 8:30 - 9:00
    }
  },
  created(){
    // others
      var arr1=[16,20,21,24,25,26];
      this.others = arr2arr(arr1)

    // a_semih
      var timeBlock=[];
      for(var i=0;i<=23;i++){
        timeBlock.push({
          index:i, // 小时，或方块索引，共 24
          semih:[i*2, i*2+1], // 半小时，服务器传回时间，0代表0:00-0:30 共 48
          class:['left', 'right'], // 是起始还是结束，class ， index 为偶数时为 left
        })
        timeBlock[i].info=null;
        timeBlock[i].txt=[];
        timeBlock[i].txt.push(i+':00');
        timeBlock[i].txt.push(i+':30');
      }

      var a_semih=[];
      for(var i=0; i<=timeBlock.length-1; i++){
        a_semih.push(timeBlock[i].txt[0])
        a_semih.push(timeBlock[i].txt[1])
      }
      this.a_semih = a_semih


  },
  methods:{
    confirm(){ // 确定选择的时间
      this.b_shade_box=!this.b_shade_box;
    },
    cancel(){ // 取消
      this.b_shade_box=!this.b_shade_box;
    },
    select_fn(index){

      var others = this.others
      for(var i=0;i<=others.length;i++){ // 是否点中别人的时间
        if(index == others[i]){
          console.log('不能点')
          return
        }
      }


      if( this.start < 0 && this.end < 0 ){ // 第一次点击
        // 重置点击
          this.start=index // 保存第一次点击位置
          this.end=-1
          this.myArr=[]
          this.$set(this.myArr,index,index)

      }else if( this.start >= 0 && this.end < 0){ // 第二次点击
        console.log('第二次点击，上次点击', this.start)

        // 是否是别人的时间
          for(var i=0;i<=others.length;i++){
            if( (others[i] > this.start && others[i] < index) || (others[i] < this.start && others[i] > index)){ // 跨越
              console.log('不能跨越别人的时间段')
              // 重置点击
                this.start=index // 保存第一次点击位置
                this.end=-1
                this.myArr=[]
                this.$set(this.myArr,index,index)

              return
            }
          }

        // 更新数据
          this.$set(this.myArr,index,index)
          this.end=index

        // 变为连续数组
          var abs = [this.start, this.end];
          abs.sort(function(a, b){return a - b});
          for(var i=abs[0]; i<=abs[1]; i++){
            this.$set(this.myArr,i,i)
          }

      }else if( this.start >=0 && this.end >= 0){ // 第三次点击
        console.log('第三次点击，作为第一次')
        this.start=index // 保存第一次点击位置
        this.end=-1
        this.myArr=[]
        this.$set(this.myArr,index,index)
      }




    }
  },
  computed: {
    myArr_service(){
      var arr=this.myArr;
      var arr2=[];
      for(var i=0; i<arr.length; i++){
        if(arr[i]>=0){
          arr2.push(arr[i])
        }
      }

      return arr2
    }
  }

}


  function arr2arr(arr){
    for(var i=0;i<=arr.length;i++){
      arr[arr[i]] = arr[i]
    }
    return arr
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
<style lang="less" scoped>
  /* 测试页 */
  ul,li{
    margin: 0;
    padding: 0;
  }
  .shade_box{
    position: absolute;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0,0,0,.5)
  }
  .test{
    padding-top: 50px;
    padding-bottom: 50px;
    border-bottom: 1px solid #000;
    border-top: 1px solid #000;
    background: #ccc;
  }
</style>
