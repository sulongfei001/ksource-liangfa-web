<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%
	String _RndData = com.IA300.util.util.GenerateRandomString();
	session.setAttribute("Random", _RndData);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新乡市两法衔接信息共享平台</title>
<link type="text/css" rel="stylesheet" href="${path }/resources/css/login/login2.css"/>
<link type="text/css" rel="stylesheet" href="${path }/resources/artdialog6/ui-dialog.css"/>
<link type="text/css" rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-violet/tip-violet.css"/>
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.js"></script>
<script type="text/javascript" src="${path }/resources/iakey/IA300ClientJavascript.js"></script>
<script type="text/javascript" src="${path }/resources/script/checkBrower.js"></script>
<script type="text/javascript" src="${path }/resources/artdialog6/dist/dialog.js"></script>
<style type="text/css" >
.tip-violet table{
table-layout : auto;
}
.buttonDiv {
	bottom:60px;
}
input.formInput{ 
	vertical-align: middle; 
}
#securityCodeImg {
	vertical-align:middle;
	margin-top:-3px;
}
</style>
	<script language="javascript" type="text/javascript">
	
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
	
	$(function(){
		$('#btn_login').bind('keydown',function(event) {
		    if(event.keyCode==13){
		            $('#btn_login').click();
		   }  
		});
		//定时检查ukey是否被拔出
		//IA300_StartCheckTimer(5000,"UKEY已从设备中移出","${path}/views/login-iakey.jsp",1);
		//错误信息提示
		<c:if test="${error!=null}">
		createLoginTip('${error}');
		</c:if>
		var width = $(".login").css("width");
	});
	
	if(top!=this){
		top.location="${path}/views/login-iakey.jsp";
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
			}).showModal(document.getElementById("login_table"));
			return false;
		}
		setTimeout("checkUkeyExist()",1000);
	}
	
	function checkUkeyExist(){
	      if(IA300_CheckExist()>=1){
	            flag = true;
	        }
	        if(flag){
	            //createLoginTip("请输入密码、验证码后登陆。<br/>"+getBrowser());
	            $('#userNameC').val('已从设备读取');
	            document.getElementById('userpin').focus();
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
	            }).showModal(document.getElementById("login_table"));
	        }
	}
	
	//第二次弹框
	function _alertSec(){
		dialog({
			title:"提示",
			content:"1.IE浏览器本身设置阻止了ukey的识别<a href='${path}/views/solve_tip.jsp#seq_3'><font color='blue'>处理方法</font>；</a><br/>"
			+"2.<strong>IE浏览器缓存</strong>造成账号密码不读取<a href='${path}/views/solve_tip.jsp#seq_4'><font color='blue'>处理方法</font>；</a><br/>"
			+"3.电脑上安装的<strong>浏览器插件</strong>阻止了ukey的识别<a href='${path}/views/solve_tip.jsp#seq_5'><font color='blue'>处理方法</font>；</a><br/>",
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
		}).showModal(document.getElementById("login_table"));
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
		}).showModal(document.getElementById("login_table"));
	}
	//点击登录按钮时调用此函数方法
	function OnLogon() {
		if (flag) {
			//获取页面文本框的密码
			var userpin = document.getElementById("userpin").value;
			if (userpin == "") {
				createLoginTip("密码不能为空!");
				return false;
			}
			//检测IA300是否存在
			if (IA300_CheckExist() < 1) {
				createLoginTip("未找到UKEY,请插上UKEY后再点击登录");
				return false;
			}
			//打开USB Key,sIAPWD为USB Key的用户密码  
			var retVal = IA300_CheckPassword(userpin);
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
	/* function createLoginTip(content){
		var element=$('#tipTr');
		if(element.data('poshytipEd')){
			element.poshytip('update', content);
		}else{
			element.poshytip({
				content:content,
				className: 'tip-violet',
				showOn: 'none',
				alignTo: 'target',
				alignX: 'inner-right',
				offsetX:-80
				
			}).poshytip('show');
			element.data('poshytipEd',1);
		}
	}  */
	function createLoginTip(content){
		 dialog({
			title:"提示",
			content:content,
			zIndex:1001,
			align: 'right',
			okValue:"关闭",
			ok:function(){},
			onshow:function(){
			},
			onclose:function(){
			}
		}).showModal(document.getElementById("login_table"));
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
<body onLoad="javascript:OnPageLoad();" style="background-color: rgb(182, 222, 242);">
<div class="xinxlfbox">
  <div class="xinxlftop">
    <div class="tlogoimg"><img src="${path }/resources/images/xinxloginlogo.png" /></div>
  </div>
  <!--xinxlfcen end-->
  <div class="clear"></div>
  <div class="xinxlfcen">
  <form id="form1" action="${path}/system/authority/login-iakey" method="post">
	<input id="uid" type="hidden" name="uid">
	<input type="hidden" id="ClientDigest" name="ClientDigest">
	<input type="hidden" id="RequestInfo" name="RequestInfo">
    <div class="xxlfcLogin">
      <div class="loginul">
        <ul>
          <li><span class="fontspan">用户名：</span>
            <input class="formInput" tabindex="1"  id="userNameC" readonly="readonly" value="" type="text"/>
            </input>
          </li>
          <li><span class="fontspan">密<span style="margin-left:14px;">码</span>：</span>
            <input class="formInput" tabindex="2"  id="userpin" value="" type="password" class="text"/>
          </li>
          <li><span class="fontspan">验证码：</span>
            <input class="formInput" name="securityCode" id="securityCode" type="text">
            <a href="javascript:flushSecurityCode()" class="hyzhangdiv">
            <img id="securityCodeImg" src="${path }/system/authority/securityCode" /> 
           	</a>
          </li>
        </ul>
      </div>
      <!--loginul end-->
      <div class="clear"></div>
      <div class="buttonDiv">
            	<input id="loginBtn" onclick="submitForm();" type="button" class="btn_login"></input>  				        	       	
      </div>
      <a href="javascript:RemoteResetUserPin_Request()"  target="_self" class="forgotpassw">忘记密码</a>
    </div>
    <!--xxlfcLogin end-->    
    </form>
  </div>
  <!--xinxlfcen end-->
</div>
</body>
</html>
