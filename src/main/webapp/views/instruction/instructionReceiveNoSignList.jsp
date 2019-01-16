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
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"   ></script> 
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript">	
$(function(){
	var sendTime = document.getElementById('sendTime');
	sendTime.onfocus = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	
	var flag='${info}';
	if('signSuccess'==flag){
		$.ligerDialog.success('签收指令成功！');
	}
	
});
	
	function sign(receiveId){
		$.ligerDialog.confirm('确认签收吗?',function(flag){
			if(flag){
				window.location.href="${path }/instruction/instructionReceive/sign?receiveId="+receiveId;
			}
		});
	}
</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
<legend class="legend">工作指令查询</legend>
	<form action="${path }/instruction/instructionReceive/noSignList" method="post">
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td width="12%" align="right">标题</td>
				<td width="20%" align="left">
					<input type="text" class="text" name="title" value="${instructionReceive.title}" /></td>
				<td width="12%" align="right">发布日期</td>
				<td width="20%" align="left">
					<input type="text" id="sendTime" name="sendTime" class="text"  value="<fmt:formatDate value='${instructionReceive.sendTime}' pattern='yyyy-MM-dd' />" />
				</td>
				<td width="36%" align="left" valign="middle">
					<input type="submit" value="查 询" class="btn_query" />
				</td>
			</tr>
		</table>
	</form>
</fieldset>
	<!-- 查询结束 -->
	<display:table name="list" uid="instructionReceive" cellpadding="0" cellspacing="0" requestURI="${path }/instruction/instructionReceive/noSignList" >
		<display:column title="序号" style="width:3%">
			<c:if test="${empty page ||page==0}">
			${instructionReceive_rowNum}
		</c:if>
			<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + instructionReceive_rowNum}
		</c:if>
		</display:column>
		<display:column title="标题" style="text-align:left;">
			<a href="${path }/instruction/instructionReceive/detail?instructionId=${instructionReceive.instructionId}">${instructionReceive.title}</a>&nbsp;&nbsp;
		</display:column>
		<display:column title="发布部门" property="sendOrgName" style="text-align:left;"></display:column>
		<display:column title="发布日期" style="text-align:left;">
			<fmt:formatDate value="${instructionReceive.sendTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="操作" style="width:10%">
			<a href="javascript:;" onclick="sign('${instructionReceive.receiveId}')">签收</a>&nbsp;&nbsp;
		</display:column>
	</display:table>
</div>
</body>
</html>