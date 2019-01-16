<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>head</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
</head>
<body>
	<script type="text/javascript">
		function docConvert(tar){
			tar.disabled="disabled";
			$('#msg').html("后台正在执行中...这可能需要较长时间，您可先执行其它操作。");
			$.getJSON("${path}/system/docConvert/exec?tt="+(new Date()).getTime(), function(){
				$('#msg').html("");
				tar.disabled=false;
			});
		}
	</script>
	<center>
		<br/><br/><br/>
		<input type="button" id="bb" value="立即执行在线阅读所必需的文档转换服务" onclick="docConvert(this);"/>
		<span id="msg"></span>
		<c:if test="${docConvertServiceRuning }">
		<script type="text/javascript">
			document.getElementById('bb').disabled="disabled";
			$('#msg').html("后台正在执行中...这可能需要较长时间，您可先执行其它操作。");
		</script>
		</c:if>
	</center>
</body>
</html>