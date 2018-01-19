

export const ipt_text = {
  ipt_text: function(select){
    /**
     * 当 input 内容未输入或未修改时切换不同样式
     * 使用方法，在 input 标签上使用 t_val="原数据" 即可。
     */
    $(select).each(function(i,j){
      console.log(i,j)
      if($(j).attr('t_val')){
        if($(j).val==$(j).attr('t_val') || $(j).val() == ''){
          $(j).val($(j).attr('t_val'));
        }
      }
    })
    $(select).focus(function(){
      if( $(this).val() == $(this).attr('t_val') || $(this).val() == '' ){
        $(this).val('');
        $(this).css('color','#424242');
      }else{

      }
    })
    $(select).blur(function(){
      /*如果为空或未修改，就还原样式*/
      if( $(this).val() == $(this).attr('t_val') || $(this).val() == '' ){
        $(this).val($(this).attr('t_val'));
        $(this).css('color','#cfcfd4');
      }else{

      }
    })
  }
};

