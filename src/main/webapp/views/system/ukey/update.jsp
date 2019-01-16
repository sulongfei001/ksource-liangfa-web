<%@page import="com.ksource.syscontext.SpringContext"%>
<%@page import="com.ksource.liangfa.service.system.UkeyService"%>
<%@page import="com.ksource.license.LicenseInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'update.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />	
<SCRIPT LANGUAGE="JavaScript">
<!--
//监测psa插入
function psaok()
{
	if (document.getElementById('IID_SecureWeb_I_SerialNumber').value!='')
	{
		document.getElementById('IID_SecureWeb_B_Update').disabled='';
		document.getElementById('prompt').innerHTML="PSA已插入";
	}
	if  (document.getElementById('IID_SecureWeb_I_SerialNumber').value=='')
	{
		document.getElementById('IID_SecureWeb_B_Update').disabled='disabled';
		document.getElementById('prompt').innerHTML="请插入PSA";
	}
}
//验证表单数据有效性
function goupdate()
{
	if (document.getElementById('IID_SecureWeb_I_AdminPass').value=='')
	{
		document.getElementById('prompt').innerHTML="数据填写不完整";
		return false;
	}
	else
	{
		document.getElementById('prompt').innerHTML="正在更新用户，请稍候...";
	}
}
//操作结果，根据 IID_SecureWeb_I_Status 的显示相应提示。其他状态代码请参阅开发手册
function updateok()
{
	var updateresult=document.getElementById('IID_SecureWeb_I_Status').value;
	switch (updateresult)
	{
		case '0':
			document.getElementById('prompt').innerHTML='更新成功，正写入数据库...';
			//PSA更新成功，提交表单更新数据库相应内容
			document.form1.action.value='dbupdate';
			document.form1.submit();
			break;
		case '301':
			document.getElementById('prompt').innerHTML='接口不存在，PSA需要升级。 错误代码：301';
			break;
		case '302':
			document.getElementById('prompt').innerHTML='应用名称不存在（未创建）。 错误代码：302';
			break;
		case '303':
			document.getElementById('prompt').innerHTML='应用名称与管理密码不匹配。 错误代码：303';
			break;
		case '304':
			document.getElementById('prompt').innerHTML='页面代码不符合接口规范。 错误代码：304';
			break;
		case '305':
			document.getElementById('prompt').innerHTML='应用名称已经存在。 错误代码：305';
			break;
		case '306':
			document.getElementById('prompt').innerHTML='输入元素值不能为空。 错误代码：306';
			break;
		case '307':
			document.getElementById('prompt').innerHTML='URL不符合格式要求。 错误代码：307';
			break;	
		case '308':
			document.getElementById('prompt').innerHTML='非预设登陆网站。 错误代码：308';
			break;
		case '309':
			document.getElementById('prompt').innerHTML='PSA内部错误。 错误代码：309';
			break;		
	}
}

//读取应用网站
var __url = '<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath() %>';
function getURL(){
	document.getElementById('IID_SecureWeb_I_URL').value =__url;
}
//-->
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
//UKEY页面IE11兼容代码块
var sn = "";
function ukeycheck(){
	if (document.getElementById('IID_SecureWeb_I_SerialNumber').value==sn)
		return;
		
	sn = document.getElementById('IID_SecureWeb_I_SerialNumber').value;

	if (sn !=''){
		document.getElementById('IID_SecureWeb_B_Update').disabled='';
		document.getElementById('prompt').innerHTML="PSA已插入";
		//createLoginTip("ukey已成功连接！请输入验证码后登陆。");
		//$('#userNameC,#userPwdC').val('已从设备读取...');
	}
	if  (sn ==''){
		document.getElementById('IID_SecureWeb_B_Update').disabled='disabled';
		document.getElementById('prompt').innerHTML="请插入PSA";
		//createLoginTip("请插入ukey，正确插入后，如果无提示请手动刷新本页！");
		//$('#userNameC,#userPwdC').val('');
	}
}
setInterval("ukeycheck()", 1000);
//UKEY登录页面
//setInterval("fillok()", 1000);
//UKEY绑定创建页面
//setInterval("createok()", 1000);
//UKEY绑定更新页面
setInterval("updateok()", 1000);
//UKEY绑定删除页面
//setInterval("delok()", 1000);
</SCRIPT>
  </head>
  <%
    if(request.getParameter("action")!= null && request.getParameter("action").trim().equals("dbupdate")){
       String SerialNumber = request.getParameter("IID_SecureWeb_I_SerialNumber").toString();
       String Username = request.getParameter("IID_SecureWeb_I_UserName").toString();
       String Newpassword = request.getParameter("IID_SecureWeb_I_AdminPassNew").toString();
       if(Username.trim().equals("") && Newpassword.trim().equals("")){
       	response.getWriter().print("<script>alert('用户名和新密码不能都为空！')</script>");
       }else{
    	   UkeyService service = SpringContext.getApplicationContext().getBean(UkeyService.class);
    	   service.update(SerialNumber,Username,Newpassword);
    	   response.getWriter().print("<script>alert('更新成功！')</script>");
       }
    }
  %>
<body id="IID_SecureWeb_Support"  onload="getURL()">
<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">更新U-KEY信息</legend>
	<!--页面提示-->
	<div id="prompt" style="color:red;text-align:center;">请插入PSA</div>
	<form name="form1" action="" method="post">
	  <input name="action" value="" type="hidden">
	  <input id="IID_SecureWeb_I_Flag" name="IID_SecureWeb_I_Flag" value="1" type="hidden">
	  <!--PSA唯一序列号-->
	    <input id="IID_SecureWeb_I_SerialNumber" name="IID_SecureWeb_I_SerialNumber" size="20" maxlength="32" onpropertychange="psaok();" type="hidden" value="">
	    <!--返回操作结果，0表示成功，其他数值表示错误代码-->
	    <input id="IID_SecureWeb_I_Status" name="IID_SecureWeb_I_Status" size="3" maxlength="250" onpropertychange="updateok();" type="hidden">
	  <table width="90%" class="table_add">
		  	<tr>
		  		<td width="20%" class="tabRight">应用名称：</td>
		  		<td width="80%" style="text-align:left;">
		  			<input class="text" id="IID_SecureWeb_I_ApplicationName" name="IID_SecureWeb_I_ApplicationName" size="13" maxlength="128" value="<%=LicenseInfo.getLicenseInfo().getSoftName()%>">
		  			&nbsp;&nbsp;<font color="red">*</font>
		  		</td>
		  	</tr>
		  	<tr>
		  		<td width="20%" class="tabRight">管理密码：</td>
		  		<td width="80%" style="text-align:left;">
		  			<input class="text" id="IID_SecureWeb_I_AdminPass" name="IID_SecureWeb_I_AdminPass" size="13" maxlength="32"  value="123456">
		  			&nbsp;&nbsp;<font color="red">*</font>
		  		</td>
		  	</tr>	
		  	<tr>
		  		<td width="20%" class="tabRight">新 密 码： </td>
		  		<td width="80%" style="text-align:left;">
		  			<input class="text" id="IID_SecureWeb_I_AdminPassNew" name="IID_SecureWeb_I_AdminPassNew" size="13" maxlength="32">
		  			&nbsp;&nbsp;<font color="red">*</font>
		  		</td>
		  	</tr>	
		  	<tr>
		  		<td width="20%" class="tabRight">应用网址：</td>
		  		<td width="80%" style="text-align:left;">
		  			<input class="text" id="IID_SecureWeb_I_URL" name="IID_SecureWeb_I_URL" size="30" maxlength="256" value="http://xxx.xxx.xxx.xxx${path}">
		  			&nbsp;&nbsp;<font color="red">*</font>
		  		</td>
		  	</tr>	
		  	<tr>
		  		<td width="20%" class="tabRight">用  户  名：</td>
		  		<td width="80%" style="text-align:left;">
		  			<input class="text" id="IID_SecureWeb_I_UserName" name="IID_SecureWeb_I_UserName" size="13" maxlength="128" value="">
		  			&nbsp;&nbsp;<font color="red">*</font>
		  		</td>
		  	</tr>	
		  	<tr>
		  		<td align="center" colspan="2">
		  			<input type="button" id="IID_SecureWeb_B_Update" name="IID_SecureWeb_B_Update" value="更新" onclick="goupdate();"  disabled="disabled">
		  		</td>
		  	</tr>
	  </table>
	</form>
</fieldset>
</div>
</body>
</html>
