$(function(){   
     //判断浏览器是否支持placeholder属性
      var supportPlaceholder='placeholder'in document.createElement('input');
      placeholder=function(input){ 
         var text = input.attr('placeholder'),
         defaultValue = input.defaultValue;
         if(!defaultValue){ 
             input.val(text).addClass("phcolor");
         } 
         input.focus(function(){
             if(input.val() == text){
                 $(this).val("");
             }
        });
         input.blur(function(){
             if(input.val() == ""){             
                 $(this).val(text).addClass("phcolor");
             }
         });
         //输入的字符不为灰色
         input.keydown(function(){
             $(this).removeClass("phcolor");
         });
     };
     //var flag = $('input').attr("placeholder")==undefined;
     //当浏览器不支持placeholder属性时，调用placeholder函数
     if(!supportPlaceholder){
    	 var flag;
         $('input').each(function(){
             text = $(this).attr("placeholder");
             flag = text==undefined;
             //有placeholder这个属性才会执行该函数
             if($(this).attr("type") == "text" && !flag){
                 placeholder($(this));
             }
         });
     }
 
 });