<template>
  <div>
    <p>文字</p>
    <p>文字</p>
    <p>文字</p>
    <div class="page-loadmore">
      <p>文字</p>
      <p>文字2</p>
      <p>文字</p>
      <p>文字3</p>
      <div class="page-loadmore-wrapper" ref="wrapper" :style="{ height: wrapperHeight + 'px' }">


      <p>文字</p>
      <p>文字2</p>
      <p>文字</p>
      <p>文字3</p>


        <mt-loadmore
          bottomPullText="上拉加载更多"
          bottomDropText="释放开始加载"
          @top-status-change="handleTopChange"
          :bottom-method="loadBottom"
          @bottom-status-change="handleBottomChange"
          :bottom-all-loaded="allLoaded"
          ref="loadmore">

          <ul class="page-loadmore-list">
            <li v-for="item in list" class="page-loadmore-listitem">{{ item }}</li>
          </ul>

          <span v-show="allLoaded">没有更多了</span>

        </mt-loadmore>

      </div>
    </div>
  </div>
</template>

<style>

  .page-loadmore .mint-spinner {
      display: inline-block;
      vertical-align: middle;
  }
  .page-loadmore-desc {
      text-align: center;
      color: #666;
      padding-bottom: 5px;
  }
  .page-loadmore-desc:last-of-type {
      border-bottom: solid 1px #eee;
  }
  .page-loadmore-listitem {
      height: 50px;
      line-height: 50px;
      border-bottom: solid 1px #eee;
      text-align: center;
  }
  .page-loadmore-listitem:first-child {
      border-top: solid 1px #eee;
  }
  .page-loadmore-wrapper {
      overflow: scroll;
  }
  .mint-loadmore-bottom span {
      display: inline-block;
      -webkit-transition: .2s linear;
      transition: .2s linear;
      vertical-align: middle
  }
  .mint-loadmore-bottom span.is-rotate {
      -webkit-transform: rotate(180deg);
              transform: rotate(180deg);
  }

</style>

<script >
  export default {
    data() {
      return {
        list: [],
        allLoaded: false,
        bottomStatus: '',
        wrapperHeight: 0
      };
    },

    methods: {
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
        console.log('loadBottom')
        setTimeout(() => {
          let lastValue = this.list[this.list.length - 1];
          if (lastValue < 40) {
            for (let i = 1; i <= 10; i++) {
              this.list.push(lastValue + i);
            }
          } else {
            this.allLoaded = true;
          }
          this.$refs.loadmore.onBottomLoaded();
        }, 1500);
      }
    },

    created() {
      for (let i = 1; i <= 20; i++) {
        this.list.push(i);
      }
    },

    mounted() {
      this.wrapperHeight = document.documentElement.clientHeight - this.$refs.wrapper.getBoundingClientRect().top;
    }
  };
</script>
