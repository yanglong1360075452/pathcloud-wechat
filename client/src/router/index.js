import App from '../page/app'

export default [
    {

      path: '/app',
      component: App, // 打开任一页面都必须先做微信授权，所以应将授权放在根路由中
      children: [
        // {
        //   path: 'index',
        //   name: 'home',
        //   // require.ensure 是 Webpack 的特殊语法，用来设置 code-split point 惰加载,第三个参数作为 chunk 的名称 作为同一个chunk加载
        //   component: r => require.ensure([], () => r(require('../page/index')), 'index')
        // },
        { // 登录
          path: 'login',
          component: r => require.ensure([], () => r(require('../page/login')), 'login')
        },
        { // 除登录外所有页面的统一父路由，放底部三个tab
          path: 'main',
          component: r => require.ensure([], () => r(require('../page/main')), 'main'),
          //主要分为 申请、查询、我 三大块
          children: [
            { // 申请，在这里写上面三个tab
              path: 'apply',
              name: 'apply',
              component: r => require.ensure([], () => r(require('../page/apply')), 'apply'),
              children: [
                {
                  path: 'application/:type',//type区分常规染色 冰冻预约
                  name: 'application',//方便写路由跳转
                  component: r => require.ensure([], () => r(require('../page/apply/application')), 'application'),
                },
                {
                  path: 'specialDye', // 染色申请
                  name: 'specialDye',
                  component: r => require.ensure([], () => r(require('../page/apply/specialDye')), 'specialDye'),

                },
                {
                  path: 'specialDye2',
                  name: 'specialDye2',
                  component: r => require.ensure([], () => r(require('../page/apply/specialDye/specialDye2.vue')), 'specialDye2'),
                },
                {
                  path: 'addApplicant', //添加申请人
                  name:"addApplicant",
                  component: r => require.ensure([], () => r(require('../page/apply/children/addApplicant')), 'applicant'),
                },
                {
                  path: 'createApplicant', //新建申请人
                  name: 'createApplicant',
                  component: r => require.ensure([], () => r(require('../page/apply/children/createApplicant')), 'applicant'),
                },
                {
                  path: 'applicantInfo', //申请人信息
                  component: r => require.ensure([], () => r(require('../page/apply/children/applicantInfo')), 'applicant'),
                },
                {
                  path: 'reserve', //冰冻预约页面
                  name: 'reserve',
                  component: r => require.ensure([], () => r(require('../page/apply/application/reserve')), 'reserve'),
                }
              ]
            },
            { // 查看
              path: 'search',
              name: 'search',
              component: r => require.ensure([], () => r(require('../page/search')), 'search'),
              children:[
                { // 申请信息详情
                  path: 'details',
                  name: 'details',
                  component: r => require.ensure([], () => r(require('../page/search/details.vue')), 'details'),
                }
              ]
            },
            { // 我
              path: 'userInfo',
              name: 'userInfo',
              component: r => require.ensure([], () => r(require('../page/userInfo')), 'userInfo'),
              children:[
                { // 个人资料
                  path: 'personal',
                  name: 'personal',
                  component: r => require.ensure([], () => r(require('../page/userInfo/personal.vue')), 'personal'),
                },
                { // 意见反馈
                  path: 'opinion',
                  name: 'opinion',
                  component: r => require.ensure([], () => r(require('../page/userInfo/opinion.vue')), 'opinion'),
                },
                { // 关于
                  path: 'about',
                  name: 'about',
                  component: r => require.ensure([], () => r(require('../page/userInfo/about.vue')), 'about'),
                },
              ]
            },
          ]
        },
        { // 组件测试页面
          path: 'component_test',
          component: r => require.ensure([], () => r(require('../page/component_test.vue')), 'component_test')
        },
      ]
    },

    { path: '*', redirect: '/app/login' }

  ]
