<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="${path}/resources/css/common.css"></link>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" language="javascript">
$(function(){
	//等待提醒
	$.blockUI({
		css: { 
			top: '50%',
			left: '50%',
			textAlign: 'left',
			marginLeft: '-320px',
			marginTop: '-145px',
			width: '600px',
			background: 'none repeat scroll 0 0 #FFFFFF',
			border: '1px solid #CCCCCC',
			display: 'none',
			margin: '0 auto',
			padding: '20px',
			width: '600px'
		},
		message: $('#timeOutDiv')
	});
	
});
function timeout(){
	var path = "${path }";
	if(path==""){path="/";}
	$.unblockUI;
	if (self != top) {
		top.location = path;
	} else {
		window.location = path;
	}
}
</script>
<title>登录状态失效</title>

<style type="text/css">
body {
	background: #FFFFFF;
	font-family: "宋体", Arial;
	font-size: 12px;
	margin: 0;
	padding: 0;
}
</style>
</head>

<body>
<div id="timeOutDiv" title="超时警告" style="display: none;">
<p>超时警告:必须重新登陆，请点击<a href="javascript:timeout()">这里</a></p>
</div>
</body>
</html>
