<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新乡市两法衔接信息共享平台</title>
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript">

$(function(){
	
	loadUserInfo();
	<c:if test="${error!=null}">
		alert('${error}');
	</c:if>
});



function loadUserInfo(){
	
	var data = loginOcx.GetUserInfo("");
	var xmlStr = "<xml>"+data+"</xml>";
	alert(xmlStr);
    var xmlDoc = $.parseXML(xmlStr);
    var name = $(xmlDoc).find("nam").text();
    var siUserId =  $(xmlDoc).find("uri").text();
    alert(siUserId);
    if(siUserId){
    	window.location.href="${path}/system/authority/login-sikey.do?siUserId="+siUserId;
    }else{
    	alert("未获取到用户唯一标识!");
    }
}

</script>
</head>
<object id="loginOcx" classid="CLSID:91571FEA-14D0-41B4-B4B9-7C49A9EE66F8"></object>
<body>
</body>
</html>
