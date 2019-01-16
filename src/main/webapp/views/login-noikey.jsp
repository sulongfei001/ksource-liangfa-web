<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>新乡市两法衔接信息共享平台</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/login/xxlogin.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/login/login-dialog.css"/>
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<!--[if lt IE 9]>
<script src="${path }/resources/html5/html5.js"></script> 
<script src="${path }/resources/html5/css3-mediaqueries.js"></script> 
<![endif]-->

<script type="text/javascript">

//限制只能IE浏览器登陆
/* function isIE() { //ie?  
    if (!!window.ActiveXObject || "ActiveXObject" in window)  
        return true;  
    else  
        return false;  
} 

 if(!isIE()){
   window.location.href="${path}/views/fftoie.jsp";
 } */


function gotologin(){
	$('#dialogBg').fadeIn(300);
	$('#dialog').removeAttr('class').addClass('animated bounceIn').fadeIn();
	//用户名获取光标
	$("input[tabindex='1']").focus();
}

var w,h,className;
function getSrceenWH(){
	w = $(window).width();
	h = $(window).height();
	$('#dialogBg').width(w).height(h);
}
function resizeWindow(){
    var marginleft = ($(window).width()-945)/2+"px";
    $(".denglu01").css("margin-left",marginleft);
}

$(window).resize(resizeWindow);  

$(function(){
	getSrceenWH();
	resizeWindow();
	
	//关闭弹窗
	$('.claseDialogBtn').click(function(){
		$('#dialogBg').fadeOut(300,function(){
			$('#dialog').addClass('bounceOutUp').fadeOut();
		});
	});
	
	if("${fromView}" == "checkPwdAndUserName"){
		gotologin();
	} 
});
//刷新验证码
function flushSecurityCode(){
	$('#securityCodeImg').attr('src','${path }/system/authority/securityCode?curTTT='+(new Date().getTime()));
	return;
}

</script>
</head>
<body>
	<div class="box">
        <div class="top">           
        <div class="loginlogo"><img src="${path }/resources/images/login/loginlogo.png" /></div>
        </div>
        <!--top end-->
        <div class="cen">
       		<div class="cenall">
        	<div id="denglu01" class="cencontdl denglu01"  onclick="gotologin()" >
       	    	<img src="${path }/resources/images/login/zfdenglu01.png" />
            </div>
            <!--cencontdl end-->
            
            <div class="cencontdl denglu02"  onclick="gotologin()">
       	    	<img src="${path }/resources/images/login/gadenglu02.png" />
            </div>
            <!--cencontdl end-->
            
            <div class="cencontdl denglu03"  onclick="gotologin()">
       	    	<img src="${path }/resources/images/login/jcdenglu03.png" />
            </div>
            <!--cencontdl end-->
            
            <div class="cencontdl denglu04"  onclick="gotologin()">
       	    	<img src="${path }/resources/images/login/fydenglu04.png" />
            </div>
            <!--cencontdl end-->
            
            <div class="cencontdl denglu05"  onclick="gotologin()">
       	    	<img src="${path }/resources/images/login/fzdenglu05.png" />
            </div>
            <!--cencontdl end-->
        </div>
        </div>
        <!--cen end-->
    </div> 
    	<div id="dialogBg"></div>
		<div id="dialog" class="animated">			
			<div class="dialogTop">
				<div class="logindengl">用户登录</div>
				<div class="loginguanbi"><a href="javascript:;" class="claseDialogBtn"></a></div>
			</div>
			<form action="${path}/system/authority/checkPwdAndUserName" method="post" >	
			<div class="xxlfcLogin">
			<span style="margin-left:20px;font-size: 12px;color: red;padding-top: 5px;" id="error">${error }</span>
		      <div class="loginul">
		        <ul>
		          <li><span class="fontspan">用户名：</span>
		            <input class="formInput" tabindex="1"  id="userId" name="userId" type="text">
		            </input>
		          </li>
		          <li><span class="fontspan">密<span style="margin-left:14px;">码</span>：</span>
		            <input class="formInput" tabindex="2" title="请输入密码"  id="pwd" name="pwd" type="password" class="text"/>
		          </li>
		          
		          <li>               
		            <span class="fontspan">验证码：</span>
				    <input class="formInput" tabindex="3" name="securityCode" id="securityCode" type="text"/>
				    <a href="javascript:flushSecurityCode();" class="hyzhangdiv"><img id="securityCodeImg" src="${path }/system/authority/securityCode" /> </a>    
		          </li>
		        </ul>
		      </div>
		      <!--loginul end-->
		      <div class="clear"></div>
		      <div class="buttonDiv">
		            	<input id="loginBtn"  type="submit" value="" class="btn_login" />	
		      </div>        
		    </div>
		    <!--xxlfcLogin end-->    
			
			</form>
		</div>
		
 	<object id="TANGER_OCX" classid="clsid:A64E3073-2016-4baf-A89D-FFE1FAA10EC2" codebase="${path }/resources/office/ofctnewclsid.cab#version=5,0,2,9" width="1" height="1">
		<param name="MakerCaption" value="河南金明源信息技术有限公司">
		<param name="MakerKey" value="BD99672DF8C7F3BFB1AAAF2B605E32A7761FB212">
		<param name="ProductCaption" value="新乡市人民检察院"> 
		<param name="ProductKey" value="28A3078347E1459696E4BE3F6FF0B40A98BD55D4">
	</object>
</body>
</html>
