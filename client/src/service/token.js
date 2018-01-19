/**
 * Created by yonghuapro on 2017/5/8.
 */

import {cookie} from './common.js'

let tokenInstance = "";
export default {
  setToken(token){
    tokenInstance = token;
    cookie.set("token",token,10);
  },
  getToken(){
    return tokenInstance||cookie.get("token");
  }
}
