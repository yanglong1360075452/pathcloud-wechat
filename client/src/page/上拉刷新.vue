<template>
  <!-- 组件测试 -->
  <div class="test">
    <scroller lock-x scrollbar-y use-pullup :pullup-config="pullupConfig2" height="200px" ref="demo2" @on-pullup-loading="load2">
      <div class="box2">
        <p v-for="i in n2">占位 {{i}}</p>
      </div>
    </scroller>
  </div>
</template>
<script>
import { Scroller, Divider, Spinner } from 'vux'
export default {
  components: {
    Scroller,
    Divider,
    Spinner
  },
  methods: {
    load2 () {
      setTimeout(() => {
        this.n2 += 10
        setTimeout(() => {
          this.$refs.demo2.donePullup()
        }, 100)
        if (this.n2 === 30) { // unload plugin
          setTimeout(() => {
            this.$refs.demo2.disablePullup()
          }, 100)
        }
      }, 2000)
    },
  },
  data () {
    return {

        demo4Value: {
          pullupStatus: 'default'
        },
        n2: 10,
        demo3Value: {
          pullupStatus: ''
        },
        pullupConfig2: {
          content: '上拉加载更多',
          downContent: '松开进行加载',
          upContent: '上拉加载更多',
          loadingContent: '加载中...'
        }
    }
  },
  // format: function (value, name) {
  //    return 11
  //  }
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
    background: rgba(0,0,0,.5)
  }
  .test{
    padding-top: 50px;
    padding-bottom: 50px;
    border-bottom: 1px solid #000;
    border-top: 1px solid #000;
    background: #ccc;
  }
  .select_time{
    position: fixed;
    bottom: 0;
    width: 100%;
    li{
      width: 16.67%;
      float: left;
      text-align: center;
      height: 0.92rem;
      line-height: 0.92rem;
      margin-top: 0.09rem;
      margin-bottom: 0.09rem;
      color: #cfcfd4;
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
<style scoped></style>
