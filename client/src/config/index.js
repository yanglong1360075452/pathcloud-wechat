/**
 * Created by yonghuapro on 2017/4/26.
 */
const config={
  // apiUrl:"http://zyhtest.ngrok.cc/api",
  // apiUrl:"http://coupon.wizinno.com/api",
  // apiUrl:"http://localhost:8001/api",
  // apiUrl:"http://localhost:7080/api",

  // apiUrl:"api",
  // apiUrl:"http://114.115.151.145/pathcloud-wechat/api",
  apiUrl: location.hostname =='localhost' || window.location.hostname == 'h5demo.wizinno.com' ? 'http://114.115.148.180/pathcloud-wechat/api' : 'api'
  // apiUrl:"http://pathtraq-crm.wizinno.com/pathcloud-wechat/api",

};

export default config
