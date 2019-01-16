//公用js
String.prototype.trim=function(){
	return jQuery.trim(this);
};
String.prototype.startWith=function(str){     
	  var reg=new RegExp("^"+str);     
	  return reg.test(this);        
	}  

String.prototype.endWith=function(str){     
var reg=new RegExp(str+"$");     
return reg.test(this);        
}

function trimEndChart(str,char){
	if(str != null && char !=null){
		while(str.endWith(char)){
			str = str.substring(0,str.length-char.length);
		}
	}
	return str;
}

var jqueryUtil={};

/**
 * 把<标记修改为&lt;>标记修改为&gt;依赖于sugar库
 * @param a  字符数组 要修改值的组件的id.
 */
jqueryUtil.changeHtmlFlag=function(a){
	for(var i=0;i<a.length;i++){
		var ele = jQuery('#'+a[i]);
		if(ele.length===0){
			ele=jQuery('[name="'+a[i]+'"]');
		}
		if(ele.length!==0){
			ele.removeAttr('name');
			ele.parents('form').append('<input type="hidden" name="'+a[i]+'"/>');
		    jQuery('[name="'+a[i]+'"]').val(ele.val().escapeHTML());
		}
	}

};
//模拟window.alert
jqueryUtil.alert=function(msg,title,style){
		style=style?style:"";
 		title=title?title:'提示信息';
 		jQuery("<div title='"+title+"' class='"+style+"'><p><span class='ui-icon ui-icon-alert' style='float:left; margin:0 7px 20px 0;'></span>"+msg+"</div>").dialog({
	 		resizable:false,
	 		modal:true,
	 		show:'slide',
	 		buttons: {
				'确定': function() {
					jQuery(this).dialog('close');
				}
			}
 		});
 };
if(jQuery.validator && jQuery.Poshytip){
	//验证自定义方法
	// number精度验证（默认精度为9,4）  
	jQuery.validator.addMethod("appNumber", function(value, element,param) {
		if(this.optional(element)){
			return true;
		}
		var result=false;
		var intPart=9;
		var decimalPart=4;
		if(param.constructor == Array){
			intPart=param[0]?param[0]:intPart;
			decimalPart=param[1]?param[1]:decimalPart;
		}
		value=value.trim();
//		if(!/^(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value)){
//			return false;
//		}
		 if(!/^\d+(?:\.\d+)?$/.test(value)){                        
			 return false;
         }
		var splitValue=value.split(".");
		if(splitValue[0].length<=intPart){
			if(splitValue[1]){
				return splitValue[1].length<=decimalPart;
			}
			return true;
		}else{
			return false;
		}
	}, "请输入数字(整数最多9位，小数最多4位)");  
	
	jQuery.validator.addMethod("account", function(value, element) {   
		  return this.optional(element) || /^[a-zA-Z]\w{5,15}$/.test(value);   
		}, "英文字母开头、可包含数字和下划线,6~16位"); 
	
	// 手机号码验证   
	jQuery.validator.addMethod("isTel", function(value, element) {   
	  var length = value.length;   
	  return this.optional(element) || 
	  	//验证手机    13*,158,159 开头
	  (/^((13\d)|(15\d)|(18\d))\d{8}$/.test(value))||
	  	//验证电话        "兼容格式: 国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位)"  
	  (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(value));   
	}, "请正确填写电话或手机号码");  
	//身份证验证
	jQuery.validator.addMethod("isIDCard", function(value, element) {   
		  return this.optional(element) || checkIDCard(value);   
		}, "请填写正确的身份证号"); 
	//案件上传附件后缀格式验证
	jQuery.validator.addMethod("isUploadFileExt", function(value, element) {   
		  return this.optional(element) || isUploadFileExt(value);   
		}, "不允许的文件格式,只允许上传'.doc','.xls','.ppt','.pdf','.txt'"); 
	//上传文件文件名长度验证
	jQuery.validator.addMethod("uploadFileLength", function(value, element,param) {   
		  return this.optional(element) || checkResouceFile(value,param);   
		}, "无效的文件路径"); 
	//案件添加时的证件编号验证(根据所选择的证件编号类型，而决定不同验证方法)
	jQuery.validator.addMethod("baseTo", function(value, element, param) {
		var typeValue = jQuery(param).val();
		if(typeValue == 1){
			return this.optional(element) || checkIDCard(value);   
		}
		if(typeValue == 2){
			return this.optional(element) || /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
		}
		return true;  //如果不是1,也不是2就返回true
		}, "请填写正确的证件编号"); 
	
	function isUploadFileExt(value){
	    var extArray=['doc','xls','ppt','pdf','txt'];
		var point = value.lastIndexOf(".");     
		var type =  value.substr(point+1);   
		for(var i=0; i<extArray.length;i++){
			if(extArray[i]===type)return true;
		}
		return false;
	}
	function checkResouceFile(value,param){
	 	 var temp = value.split("\\");
		   var fileName=temp[temp.length-1];
		   if(fileName.length>param){
		     return false;
		   }
		   return true;
	}
	
	function checkIDCard(idCard) 
	{

	//判断身份证号码格式函数
	//公民身份号码是特征组合码，
	//排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码

	//身份证号码长度判断
	if(idCard.length<15||idCard.length==16||idCard.length==17||idCard.length>18)
	{
	 return false;
	}

	//身份证号码最后一位可能是超过100岁老年人的X
	//所以排除掉最后一位数字进行数字格式测试
	//全部换算成17位数字格式

	var Ai;
	if(idCard.length==18)
	{
	//将身份证中的大写字母转换为小写字母
	idCard = idCard.toLowerCase();
	Ai = idCard.substr(0,17);
	}
	else
	{
	Ai =idCard.substr(0,6)+"19"+idCard.substr(6,9);
	}
	 
	if(isNumeric(Ai)==false)
	{
	return false;
	}

	var strYear,strMonth,strDay,strBirthDay;
	strYear = Ai.substr(6,4); 
	strMonth = Ai.substr(10,2);
	strDay = Ai.substr(12,2);

	if (isValidDate(strYear,strMonth,strDay)==false)
	{
	return false;
	}

	var arrVerifyCode = new Array("1","0","x","9","8","7","6","5","4","3","2");
	var Wi = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2);


	var TotalmulAiWi=0;
	for (var loop=0; loop<Ai.length;loop++)
	 { 
	    TotalmulAiWi += parseInt(Ai.substr(loop,1)) * Wi[loop];
	  }

	var modValue =parseInt(TotalmulAiWi,10)%11 ;
	var strVerifyCode = arrVerifyCode[modValue];

	Ai = Ai + strVerifyCode;

	if((idCard.length== 18)&&(idCard!=Ai))
	{
	return false;
	}
	return true;
	}
	function getBithdayAndSexFromIds(ids){
        var year, month, day, birthday, sexNumber,sex;
        //********************************************身份证为15位
        if (ids.length == 15) {
            year = ids.substr(6, 2);
            month = ids.substr(8, 2);
            day = ids.substr(10, 2);
            birthday = "19"+year + "-" + month + "-" + day;
            sexNumber = ids.substr(13, 1);
            if (sexNumber % 2 == 0) 
                sex = "女";                    
            else 
                sex = "男";                                    
        }                
        //********************************************身份证为18位
        if (ids.length == 18) {
            year = ids.substr(6, 4);
            month = ids.substr(10, 2);
            day = ids.substr(12, 2);
            birthday = year + "-" + month + "-" + day;
            sexNumber = ids.substr(16, 1);
            if (sexNumber % 2 == 0) 
                sex = "女";                    
            else 
                sex = "男";                   
        }
        var bithdayAndSexArray = new Array(birthday,sex);
		return 	bithdayAndSexArray;
    }
	function isNumeric(oNum) 
	{ 
	  if(!oNum) return false; 
	  var strP=/^\d+(.\d+)?$/; 
	  if(!strP.test(oNum)) return false; 
	  try{ 
	  if(parseFloat(oNum)!=oNum) return false; 
	  } 
	  catch(ex) 
	  { 
	   return false; 
	  } 
	  return true; 
	}

	function isValidDate(sYear, sMonth, sDay)
	{
	    
	    var nYear  = parseInt(sYear,  10);
	    var nMonth = parseInt(sMonth, 10);
	    var nDay   = parseInt(sDay,   10);

	    if(sYear=="" &&  sMonth=="" && sDay=="")
	    {
	        return true;
	    }

	    if(sYear=="" || sMonth=="" || sDay=="")
	    {
	        return false;
	    }
	    
	    if(nMonth < 1 || 12 < nMonth)
	    {
	        return false;
	    }
	    if(nDay < 1 || 31 < nDay)
	    {
	        return false;
	    }

	    if(nMonth == 2)
	    {
	        if((nYear % 400 == 0) || (nYear % 4 == 0) && (nYear % 100 != 0))
	        {
	            if((nDay < 1) || (nDay > 29))
	            {
	                return false;
	            }
	        }
	        else 
	        {
	            if((nDay < 1) || (nDay > 28))
	            {
	                return false;
	            }
	        }
	    }
	    else if((nMonth == 1)  || 
	            (nMonth == 3)  || 
	            (nMonth == 5)  || 
	            (nMonth == 7)  || 
	            (nMonth == 8)  || 
	            (nMonth == 10) || 
	            (nMonth == 12))
	    {
	        if((nDay < 1) || (31 < nDay))
	        {
	            return false;
	        }
	    }
	    else 
	    {
	        if((nDay < 1) || (30 < nDay))
	        {
	            return false;
	        }
	    }

	    return true;
	}
	// -------------------------身份证验证函数结束------------------//
	/**
	 *封装jquery.validate校验框架 options{debug,form,rules,messages,submitHandler(form),blockUI}
	 *依赖jquery-validation-1.8.1、poshytip-1.1、blockUI
	 *
	 */
	jqueryUtil.formValidate=function(_options){
		var _rules=_options.rules;
		var _messages=_options.messages;
		var _debug=_options.debug?_options.debug:false;
		var _formSelector="#"+_options.form;
		
		//默认显示blockUI效果
		var _blockUI=true;
		var _blockUIMsg="正在执行操作...";
		if(_options.blockUI==false){
			_blockUI=false;
		}else if(typeof _options.blockUI=='string'){_blockUIMsg=_options.blockUI;};
		//自定义错误回调函数 _invalidHandler
		var _invalidHandler=false;
		//自定义提交事件
		var _submitHandler = false;
		if(typeof _options.submitHandler == "function"){
			if(_blockUI){
				_submitHandler=function(form){
					if((_options.submitHandler)(form)!==false){
						jQuery.blockUI({
							css: {
								width:'220px',
								border: 'none',
								padding:'0px',
								'-webkit-border-radius': '10px', 
								'-moz-border-radius': '10px', 
								opacity: .8,
								background: '#F0F0F0'
							},
							message: _blockUIMsg+'<img src="'+top.APP_PATH+'/resources/images/loadinfo_net.gif">'
						});
					}
				};
			}else{
				_submitHandler=_options.submitHandler;
			}
		}else if(_blockUI){
			_submitHandler=function(form){
				jQuery.blockUI({
					css: {
						width:'220px',
						border: 'none',
						padding:'0px',
						'-webkit-border-radius': '10px', 
						'-moz-border-radius': '10px', 
						opacity: .8,
						background: '#F0F0F0'
					},
					message: _blockUIMsg+'<img src="'+top.APP_PATH+'/resources/images/loadinfo_net.gif">'
				});
				form.submit();
			};
		}
		if(typeof _options.invalidHandler == "function"){
			_invalidHandler=_options.invalidHandler;
		}
		var MySetting={
				debug:_debug,
				onfocusout:false,
				onkeyup: false,
				onclick:false,
				errorPlacement: jqueryUtil.errorPlacement,
				success: jqueryUtil.success,
				rules: _rules,
				messages:_messages
			};
		if(_submitHandler){
			MySetting.submitHandler=_submitHandler;
		}
		if(_invalidHandler){
			MySetting.invalidHandler=_invalidHandler;
		}
		var validator=jQuery(_formSelector).validate(MySetting);
		return validator;
	};
}
jqueryUtil.success=function(label,a) {
	var targetElement = label.data('targetElement');
	if(targetElement){
		targetElement.data('poshytipEd',null);
		targetElement.poshytip('destroy');
		if(targetElement.hasClass('tipForSelect__')){//删除select框或file的提示信息，释放内存
			var selectElement = targetElement.data('tipFor');
			selectElement.unbind('focus',selectElement.data('tipFocusFn')).unbind('blur',selectElement.data('tipblurFn'));
			selectElement.data('focus',null);
			selectElement.data('blur',null);
			targetElement.data('tipFor',null);
			targetElement.remove();
		}
		//此处理放在最后，防止位置的错误
		label.data('targetElement',null);
	}
};
jqueryUtil.errorPlacement=function(error, element) {
	var tipOpt={
			content:error,
			className: 'tip-yellowsimple',
			alignTo: 'target',
			alignX: 'right',
			alignY: 'center',
			offsetX:5
		};
		var targetElement = element;
		if(targetElement.is("select") || targetElement.is(":file")){//如果是下拉框或file，将校验提示放在下拉框的后面的span上，防止下拉框在现实提示信息的时候不能进行下拉选择
			targetElement =  element.next('.tipForSelect__');
			if(targetElement.size()==0){
				element.after('<span class="tipForSelect__">&nbsp;</span>');//空span，但是sapn内必须有内容（&nbsp;）
				targetElement =  element.next('.tipForSelect__');
				var focus=function(){
					targetElement.poshytip('show');
				};
				var blur=function(){
					targetElement.poshytip('hide');
				};
				element.bind({
					focus:focus,blur:blur
				});
				element.data('tipFocusFn',focus);
				element.data('tipblurFn',blur);
			}
			targetElement.data('tipFor',element);
		}else if(targetElement.is(':radio') || targetElement.is(':checkbox')){//如果是radio或checkbox,将错误信息添加当前元素的父结点后面
			targetElement =  element.parent('.parentWrap').next('.tipForSelect__'); 
			if(targetElement.size()==0){
				element.parent('.parentWrap').after('<span class="tipForSelect__">&nbsp;</span>');//空span，但是sapn内必须有内容（&nbsp;）
				targetElement =  element.parent('.parentWrap').next('.tipForSelect__');
				var focus=function(){
					targetElement.poshytip('show');
				};
				var blur=function(){
					targetElement.poshytip('hide');
				};
				element.parent('.parentWrap').bind({
					focus:focus,blur:blur
				});
				element.parent('.parentWrap').data('tipFocusFn',focus);
				element.parent('.parentWrap').data('tipblurFn',blur);
				targetElement.data('tipFor',element.parent('.parentWrap'));
			}else{
				tipOpt.showOn='focus';
			}
		}
		else{
			tipOpt.showOn='focus';
		}
		
		if(targetElement.data('poshytipEd')){
			targetElement.poshytip('update', error);
		}else{
			targetElement.poshytip('destroy');
			targetElement.data('poshytipEd','1');
			targetElement.poshytip(tipOpt);
		}
		if(!error.data('targetElement')){
			error.data('targetElement',targetElement);
		}
	};

jQuery(function(){

	//按钮样式
	//jQuery("input:button,input:reset,input:submit,button").not('.noJbu').button();
	
	/**给按钮增加效果，这段代码可重构*/
	//1、处理正常按钮
	jQuery("input:button[class='btn_small'],input:reset[class='btn_small'],input:submit[class='btn_small'],button[class='btn_small']").mouseover(function(){
		jQuery(this).attr("class","btn_small_onmouseover");
	});
	jQuery("input:button[class='btn_small'],input:reset[class='btn_small'],input:submit[class='btn_small'],button[class='btn_small']").mouseout(function(){
		jQuery(this).attr("class","btn_small");
	});
	//2、处理大一点的按钮
	jQuery("input:button[class='btn_big'],input:reset[class='btn_big'],input:submit[class='btn_big'],button[class='btn_big']").mouseover(function(){
		jQuery(this).attr("class","btn_big_onmouseover");
	});
	jQuery("input:button[class='btn_big'],input:reset[class='btn_big'],input:submit[class='btn_big'],button[class='btn_big']").mouseout(function(){
		jQuery(this).attr("class","btn_big");
	});
	
	
	//组件提示信息
	if(jQuery.Poshytip){
		jQuery('[poshytip]').poshytip({
			className: 'tip-yellowsimple',
			showOn: 'focus',
			alignTo: 'target',
			alignX: 'right',
			alignY: 'center',
			offsetX: 5
		});
	}
	//公用ajax操作等待提醒
	var ajaxBusy = jQuery("#ajaxBusy");
	if(!ajaxBusy || ajaxBusy.attr('id')!='ajaxBusy'){
		jQuery("body").append('<div id="ajaxBusy" align="center">正在执行请求...<img src="'+top.APP_PATH+'/resources/images/loadinfo_net.gif"></div>');
	}
	jQuery('#ajaxBusy').css( {
		display : "none",
		margin : "0px",
		position : "absolute",
		right : "10px",
		top : "5px",
		width:'220px',
		border: 'none',
		padding:'0px',
		'-webkit-border-radius': '10px', 
		'-moz-border-radius': '10px', 
		opacity: .8,
		background: '#F0F0F0',
		"z-index":99999
	});
	// Ajax activity indicator bound 
	// to ajax start/stop document events
	jQuery(document).ajaxStart(function(evt, request, settings) {
		jQuery('#ajaxBusy').show();
		//tar=evt.target;
		//alert(jQuery(tar).text());
	}).ajaxStop(function(evt, request, settings) {
		jQuery('#ajaxBusy').hide();
	});
	
	//ajax 全局配置
	jQuery.ajaxSetup({
		cache: false,
		type: "POST",
		error:function (XMLHttpRequest, textStatus, errorThrown) {
		    // 通常 textStatus 和 errorThrown 之中
		    // 只有一个会包含信息
		    //alert(this); // 调用本次AJAX请求时传递的options参数
		    //alert(textStatus);
		} 
	});
	 //修正ie6下上传组件框可自由输入文本(只针对ie6)
/*   if (jQuery.browser.msie && (jQuery.browser.version == "6.0") && !jQuery.support.style) {
   		//禁止输入
        $('input[type=file]').bind('keypress',function(){
            return false;
        });
        //禁止粘贴
        $('input[type=file]').bind('paste',function(){
            return false;
        });
    }*/
});
//去除树的最后一个符号
function trimSufffix(str,suffix){
	var last=str.substring(str.length-1,str.length);
	if(last == suffix){
		str = str.substring(0,str.length-1);
	}
	return str;
}

$.format = function (source, params) { 
	if (arguments.length == 1) 
	return function () { 
	var args = $.makeArray(arguments); 
	args.unshift(source); 
	return $.format.apply(this, args); 
	}; 
	if (arguments.length > 2 && params.constructor != Array) { 
	params = $.makeArray(arguments).slice(1); 
	} 
	if (params.constructor != Array) { 
	params = [params]; 
	} 
	$.each(params, function (i, n) { 
	source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n); 
	}); 
	return source; 
	}; 
