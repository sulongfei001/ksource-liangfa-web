<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>	
<script type="text/javascript">
$(function(){
	var sendTime = document.getElementById('sendTime');
	sendTime.onfocus = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
});
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
<legend class="legend">工作交流查询</legend>
	<form action="${path }/instruction/communionRecevie/myReceiveList" method="post">
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td width="12%" align="right">标题</td>
				<td width="20%" align="left">
					<input type="text" class="text" name="title" value="${communionReceive.title}" /></td>
				<td width="12%" align="right">发起部门</td>
				<td width="20%" align="left">
					<input type="text" name="sendOrgName" class="text" value="${communionReceive.sendOrg}"/>
				</td>
				<td width="36%" align="left" rowspan="2" valign="middle">
					<input type="submit" value="查 询" class="btn_query" />
				</td>
			</tr>
			<tr>
				<td width="12%" align="right">发起日期</td>
				<td width="20%" align="left">
					<input type="text" id="sendTime" name="sendTime" class="text"  value="<fmt:formatDate value='${communionReceive.sendTime}' pattern='yyyy-MM-dd' />" />
				</td>
			</tr> 
		</table>
	</form>
</fieldset>
	<!-- 查询结束 -->
	<display:table name="communionReceiveList" uid="communionReceive" cellpadding="0" cellspacing="0" requestURI="${path }/instruction/communionRecevie/myReceiveList" >
		<display:column title="序号" style="width:3%">
			<c:if test="${empty page ||page==0}">
			${communionReceive_rowNum}
		</c:if>
			<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + communionReceive_rowNum}
		</c:if>
		</display:column>
		<display:column title="标题" style="text-align:left;width:40%;">
			<a href="${path}/instruction/communionRecevie/detail?communionId=${communionReceive.communionId}&receiveId=${communionReceive.receiveId }&readStatus=${communionReceive.readStatus }&flag=1">${communionReceive.title }</a>
		</display:column>
		<display:column title="发起部门" property="sendOrgName" style="text-align:left;width:30%;"></display:column>
		<display:column title="发起日期" style="text-align:left;width:30%;">
			<fmt:formatDate value="${communionReceive.sendTime}" pattern="yyyy-MM-dd"/>
		</display:column>
	</display:table>
</div>
</body>
</html>