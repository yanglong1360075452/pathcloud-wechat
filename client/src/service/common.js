/**
 * Created by yonghuapro on 2017/4/30.
 */

export const cookie = {
  set: function (name, value, days) {

    var d = new Date;

    d.setTime(d.getTime() + 24*60*60*1000*days);

    window.document.cookie = name + "=" + value + ";path=/;expires=" + d.toGMTString();

  },

  get: function (name) {

    var v = window.document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');

    return v ? v[2] : null;

  },

  delete: function (name) {

    this.set(name, '', -1);

  }
};


export const locationTool = {
  getParams(name){
    function UrlSearch()
    {
      var name,value;
      var str=location.href; //取得整个地址栏
      var num=str.indexOf("?")
      str=str.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]

      var arr=str.split("&"); //各个参数放到数组里
      for(var i=0;i < arr.length;i++){
        num=arr[i].indexOf("=");
        if(num>0){
          name=arr[i].substring(0,num);
          value=arr[i].substr(num+1);
          this[name]=value;
        }
      }
    }
    var Request=new UrlSearch(); //实例化
    return Request[name];

  }
};
