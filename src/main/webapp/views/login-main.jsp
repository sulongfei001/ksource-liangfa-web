<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%
	String _RndData = com.IA300.util.util.GenerateRandomString();
	session.setAttribute("Random", _RndData);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>新乡市两法衔接信息共享平台</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/login/xxlogin.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/login/login-dialog.css"/>

<%-- <link type="text/css" rel="stylesheet" href="${path }/resources/css/login/login2.css"/> --%>
<link type="text/css" rel="stylesheet" href="${path }/resources/artdialog6/ui-dialog.css"/>
<link type="text/css" rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-violet/tip-violet.css"/>
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.js"></script>
<script type="text/javascript" src="${path }/resources/iakey/IA300ClientJavascript.js"></script>
<script type="text/javascript" src="${path }/resources/script/checkBrower.js"></script>
<script type="text/javascript" src="${path }/resources/artdialog6/dist/dialog.js"></script>

<style type="text/css" >

</style>
	<script language="javascript" type="text/javascript">
	$(function(){
		getSrceenWH();
		resizeWindow();
		//关闭弹窗
		$('.claseDialogBtn').click(function(){
			$('#dialogBg').fadeOut(300,function(){
				$('#dialog').addClass('bounceOutUp').fadeOut();
			});
		});
		
		$('#btn_login').bind('keydown',function(event) {
		    if(event.keyCode==13){
		            $('#btn_login').click();
		   }  
		});
		
	});
	
	function resizeWindow(){
	    var marginleft = ($(window).width()-945)/2+"px";
	    $(".denglu01").css("margin-left",marginleft);
	}

	var w,h,className;
	function getSrceenWH(){
		w = $(window).width();
		h = $(window).height();
		$('#dialogBg').width(w).height(h);
	}

  	window.onresize = function(){ 
		getSrceenWH();
		resizeWindow();
	}

	//$(window).resize();  
	window.navigator.userAgent
	
	function isIE() { //ie?  
	    if (!!window.ActiveXObject || "ActiveXObject" in window)  
	        return true;  
	    else  
	        return false;  
	}

	if(!isIE()){
	   window.location.href="${path}/views/fftoie.jsp";
	}
	
	var flag = false;
	
	   //页面加载时调用此函数方法
    function OnPageLoad() {
        var browser = DetectBrowser();
        if (browser == "Unknown") {
            createLoginTip("不支持该浏览器， 如果您在使用傲游或类似浏览器，请切换到IE模式");
            return false;
        }
        
        //createElementIA300() 对本页面加入IA300插件
        createElementIA300();
        //DetectActiveX() 判断IA300Clinet是否安装
        var create = DetectIA300Plugin();
        if (create == false) {
            dialog({
                title:"提示",
                content:"UKEY插件未安装!<a href='${path}/views/solve_tip.jsp#seq_33'><font color='blue'>处理方法<font>；</a>",
                zIndex:1001,
                align: 'right',
                onshow:function(){
                },
                onclose:function(){
                },
                button:[
                        {
                            value:'关闭',
                            callback:function(){
                            }
                        }
                ]
            }).showModal(document.getElementById("dialog"));
            return false;
        }
    }
	   
    
    function gotologin(){
        $('#dialogBg').fadeIn(300);
        //window.location = "${path }/views/login-iakey.jsp";
        $('#dialog').removeAttr('class').addClass('animated bounceIn').fadeIn();
        setTimeout(checkUkeyExist,1000);
    }
	   
    function checkUkeyExist(){
        if(IA300_CheckExist()>=1){
              flag = true;
          }
          if(flag){
              //createLoginTip("请输入密码、验证码后登陆。<br/>"+getBrowser());
              var name = IA300_GetName();
              if(name == null || name == ''){
            	  name = IA300_GetOther();
            	  if(name == null || name == ''){
            		  name = '已从设备读取';
            	  }
              }
              $('#userId').val(name);
              document.getElementById('pwd').focus();
          }else{
              dialog({
                  title:"提示",
                  content:"1.请确认您的电脑上是否安装了<strong>腾讯电脑管家</strong>、<strong>百度安全卫士</strong>，如果有请卸载；<br/>2.请确认您的UKEY已插入电脑，您的两法页面是<strong>自动弹出</strong>的。如果不是，请<br/><p style='text-indent:0.8em;'>进行处理<a href='${path}/views/solve_tip.jsp#seq_2'><font color='blue'>处理方法<font>；</a></p>",
                  zIndex:1001,
                  align: 'right',
                  onshow:function(){
                  },
                  onclose:function(){
                  }, 
                  button:[
                          {
                              value:'已解决',
                              callback:function(){
                                  if(IA300_CheckExist()<1){
                                      _alertSec();
                                  }
                              }
                          }
                  ] 
              }).showModal(document.getElementById("dialog"));
          }
  }	   
	
	//第二次弹框
	function _alertSec(){
		dialog({
			title:"提示",
			content:"1.IE浏览器本身设置阻止了ukey的识别<a href='${path}/views/solve_tip.jsp#seq_3' target='_blank'><font color='blue'>处理方法</font>；</a><br/>"
			+"2.<strong>IE浏览器缓存</strong>造成账号密码不读取<a href='${path}/views/solve_tip.jsp#seq_4' target='_blank'><font color='blue'>处理方法</font>；</a><br/>"
			+"3.电脑上安装的<strong>浏览器插件</strong>阻止了ukey的识别<a href='${path}/views/solve_tip.jsp#seq_5' target='_blank'><font color='blue'>处理方法</font>；</a><br/>",
			zIndex:1001,
			align:'right',
			onshow:function(){
			},
			onclose:function(){
			},
			button:[{
	        	value:'已解决',
	        	callback:function(){
	        		 if(IA300_CheckExist()<1){
	        			_alertThird();
	        		 }
	        		} 
	       		}
			] 
		}).showModal(document.getElementById("dialog"));
	}
	//第三次弹框
	function _alertThird(){
		dialog({
			title:"提示",
			content:"ukey未识别请联系管理员<strong>0371-55397576</strong>，<strong>0371-55372989</strong>",
			zIndex:1001,
			align:'right',
			onshow:function(){
			},
			onclose:function(){
			},
			button:[{
				value:'关闭',
				callback:function(){
				}
			}]
		}).showModal(document.getElementById("dialog"));
	}
	/* -----------------end---------------------- */
	if(top!=this){
		top.location="${path}/views/login-main.jsp";
	}

	//点击登录按钮时调用此函数方法
	function OnLogon() {
		if (flag) {
			//获取页面文本框的密码
			var pwd = document.getElementById("pwd").value;
			if (pwd == "") {
				createLoginTip("密码不能为空!");
				return false;
			}
			//检测IA300是否存在
			if (IA300_CheckExist() < 1) {
				createLoginTip("未找到UKEY,请插上UKEY后再点击登录");
				return false;
			}
			//打开USB Key,sIAPWD为USB Key的用户密码  
			var retVal = IA300_CheckPassword(pwd);
			if (retVal != 0) {
				//IA300_GetLastError 为封装到JS文件的获取错误信息的方法,返回错误信息,根据错误信息到帮助文档查询具体错误
				createLoginTip("您输入的密码不正确，请重新输入。<br/>如果您忘记密码，请点击【忘记密码】按钮，重置您的登录密码");
				return false;
			} else {
				//获取本Key的唯一硬件ID
				var uid = IA300_GetHardwareId();
				if (uid == "") {
					createLoginTip("获取UKEY硬件ID失败，错误码：" + IA300_GetLastError());
				}
				document.getElementById("uid").value = uid;
				//获得随机数
				var randomMessageFromServer = "<%=_RndData%>";
				//进行客户端摘要的计算，randomMessageFromServer为随机数，可随机长度
				var ClientDigest = IA300_CalculateClientHash(randomMessageFromServer);
				if (ClientDigest != "") {
					document.getElementById("ClientDigest").value = ClientDigest;
					//return true;
				} else {
					createLoginTip("UKEY计算摘要失败，错误码：" + IA300_GetLastError());
					return false;
				}
			}
			$("#form").submit();
		} else {
			createLoginTip("请正确安装插件后使用！");
			return false;
		}
	}
	
	function flushSecurityCode(){
		$('#securityCodeImg').attr('src','${path }/system/authority/securityCode?'+(new Date().getTime()));
	}	
	String.prototype.trim=function(){
	    return this.replace(/(^\s*)|(\s*$)/g, '');
	}
	function createLoginTip(content){
		 dialog({
			title:"提示",
			content:content,
			zIndex:1001,
			align: 'right',
			okValue:"关闭",
			ok:function(){
				$("#loginBtn").attr("disabled",false);
			},
			onshow:function(){
			},
			onclose:function(){
			}
		}).showModal(document.getElementById("dialog"));
		 //调用此方法时，将登录按钮禁用，防止多次调用，大量幕布跌在一起屏幕变黑
		 $("#loginBtn").attr("disabled",true);
	}
	//申请重置密码
	function RemoteResetUserPin_Request() {
		if(confirm("是否重置密码？")){
			if(flag) {
				if(IA300_CheckExist() < 1){
					alert("请插入USB Key");
					return false;
				}
		        var uid = IA300_GetHardwareId();
		        if(uid=="") {
		         	alert("获取硬件ID失败！");
		         	return false;
		        }
		        //document.getElementById("uid").value = uid;
		        
		        var RequestInfo = IA300_ResetPasswordRequest();
		        if(RequestInfo == ""){
		        	alert("申请失败，错误码："+IA300_GetLastError());
		        	return false;
		        }
		        //document.getElementById("RequestInfo").value = RequestInfo;
		        $.post("${path}/system/authority/remoteResetUserPin",
		        		{"uid":uid,"RequestInfo":RequestInfo,"defaultPassWord":"000000"},
		        		function(data){
		        			if(data.result){
		        				//进行对密码重置的操作,完成操作后的密码为自定义的新密码 IA300_ResetPassword 为封装到JS文件的进行密码重置方法
		        		        var retVal = IA300_ResetPassword(data.msg);
		        		        if (retVal == 0){
		        					alert( "您的密码已重置！新密码为：000000 请重新登录谢谢！");
		        				}
		        				else{
		        					alert( "用户密码重置失败，请重新申请！Error:"+ IA300_GetLastError());
		        				}		        				
		        			}else{
		        				alert(data.msg);
		        			}
		        		}
		        );
			} else {
				alert("请正常安装插件后使用！");
				return false;
			}
		}
	}
	
</script>
<body onLoad="javascript:OnPageLoad();">
  <div class="box">
        <div class="top">           
            <div class="loginlogo"><img src="${path }/resources/images/login/loginlogo.png" /></div>
        </div>
        <!--top end-->
        <div class="cen">
       		<div class="cenall">
        	<div class="cencontdl denglu01"  onclick="gotologin()" >
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
	<%-- <form action="${path}/system/authority/login-noikey" method="post" > --%>
	    <form id="form" action="${path}/system/authority/login-iakey" method="post">
	    
		<input id="uid" type="hidden" name="uid">
		<input type="hidden" id="ClientDigest" name="ClientDigest">
		<input type="hidden" id="RequestInfo" name="RequestInfo">
		<div class="xxlfcLogin">
	       <div class="loginul">
	       		<ul>
		          <li><span class="fontspan">用户名：</span>
		            <input class="formInput" tabindex="1"  id="userId" readonly="readonly" value="" type="text"/>
		            </input>
		          </li>
		          <li><span class="fontspan">密<span style="margin-left:14px;">码</span>：</span>
		            <input class="formInput" tabindex="2"  id="pwd" value="" type="password" class="text"/>
		          </li>
		          
		          <li><span class="fontspan">验证码：</span>
		            <input class="formInput" name="securityCode" id="securityCode" type="text" tabindex="3" >
		            <a href="javascript:flushSecurityCode()" class="hyzhangdiv">
		            <img id="securityCodeImg" src="${path }/system/authority/securityCode" /> 
	           		</a>
	          	  </li>
			    </ul>
	       </div>
	       <!--loginul end-->
		   <div class="clear"></div>
		   <div class="buttonDiv">
			    <input id="loginBtn"  type="button" value="" class="btn_login" onclick="OnLogon();"/>	
	      		<a href="javascript:RemoteResetUserPin_Request()"  target="_self" class="forgotpassw">忘记密码</a>
		   </div>  
	    </div>
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

