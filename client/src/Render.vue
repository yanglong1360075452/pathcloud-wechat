<template>
  <div class="main">

<!--     <div v-transfer-dom>
      <loading v-model="loading" text="loading"></loading>
    </div> -->

    <div class="main_loading" v-show="loading" v-transfer-dom>
      <span style="vertical-align:middle;display:inline-block;font-size:14px;">加载中&nbsp;</span>
      <inline-loading></inline-loading>
    </div>

    <router-view></router-view>

  </div>
</template>

<script>
  import {Loading,TransferDomDirective as TransferDom} from 'vux'
  import {mapState} from 'vuex'
  import { InlineLoading } from 'vux'

  export default {
    name: 'render',
    directives: {
      TransferDom
    },
    components: {Loading, InlineLoading},
    computed: mapState([
      // 映射 this.loading 为 store.state.loading
      'loading'
    ])

  }
</script>

<style lang="less">
  .main {
    height: 100%;
  }

  .main_loading{
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

</style>
