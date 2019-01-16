<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'create.jsp' starting page</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link rel="stylesheet" type="text/css" href="${path }/resourcs/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<script src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
	function openWindow(url){
		var iHeight = 400;
		var iWidth = 700;
		var iTop = (window.screen.height-30-iHeight)/2; //获得窗口的垂直位置;  
		var iLeft = (window.screen.width-10-iWidth)/2; //获得窗口的水平位置; 
		window.open(url,"create","height="+iHeight+",width="+iWidth+",top="+iTop+", left="+iLeft+",toolbar=no, menubar=no, scrollbars=no, resizable=no, location=n o, status=no");
	}
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset" style="padding-bottom: 20px;">
	<legend class="legend" style="margin-bottom: 20px;margin-left: 10px;">U-KEY管理</legend>
	&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="openWindow('create1.jsp');">初始化U-KEY</a> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="openWindow('del.jsp');">删除U-EKY信息</a>
	 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="openWindow('update.jsp');">更新U-KEY信息</a> 
</fieldset>
</div>
</body>
</html>
  