<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="${path}/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/css/common.css" />
<script type="text/javascript" src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path}/resources/script/jqueryUtil.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>法律法规类别详细界面</title>
<script type="text/javascript">
	function back(){
		window.location.href="${path}/system/accuseinfo/search/${accuseId}";
	}
</script>
</head>
<body>
	<div class="panel">
		<br />
		<center>
				<input type="hidden" id="id" name="id" value="${accuseInfo.id }" />
				<table class="blues" style="width: 90%">
					<tr>
						<td width="20%" class="tabRight">罪名：</td>
						<td width="80%" style="text-align: left;">${accuseInfo.name }</td>
					</tr>
					<tr>
						<td width="20%" class="tabRight">条款：</td>
						<td width="80%" style="text-align: left;">${accuseInfo.clause }</td>
					</tr>
					<tr>
						<td width="20%" class="tabRight">排序：</td>
						<td width="80%" style="text-align: left;">${accuseInfo.infoOrder }<br />
					</tr>
					<tr>
						<td width="20%" class="tabRight">违法情形：</td>
						<td width="80%" style="text-align: left;">${accuseInfo.law }</td>
					</tr>
				</table>
				<table style="width: 98%; margin-top: 5px;">
					<tr>
						<td align="center" "><button class="btn_small" onclick="back()">返&nbsp;回</button></td>
					</tr>
				</table>
		</center>
		<div style="color: red">${info}</div>
	</div>
</body>
</html>