<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.6.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>案件信息采集</title>
<script type="text/javascript">
//添加 案件信息
$(function(){
	var happenedTime = document.getElementById('happenedTime');
	happenedTime.onfocus = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
});
function addLawPerson() {
	window.location.href="${path}/opinion/caseInformation/add";
}

//修改法律法规类别信息
function update(infoId) {
	window.location.href="${path}/opinion/caseInformation/update?infoId="+infoId;
}
//批量刪除
function batchDelete(checkName){
	var flag ;
	var name ;
	for(var i=0;i<document.forms[1].elements.length;i++){
		
		name = document.forms[1].elements[i].name;
		if(name.indexOf(checkName) != -1){
			if(document.forms[1].elements[i].checked){
				flag = true;
				break;
			}
		}
	}   	
	if(flag){
		 top.art.dialog.confirm("确认删除吗？",
				function(){jQuery("#delForm").submit();}
		);
	}else{
		top.art.dialog.alert("请选择要删除的记录!");
	}
	return false;
}
//全选
function checkAll(obj){
jQuery("[name='check']").attr("checked",obj.checked) ; 			
}	
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">案件信息查询</legend>
	<form action="${path }/opinion/caseInformation/main" method="post" >
		<table class="searchform" width="100%" >
			<tr>
				<td width="10%" align="right">案件名称：</td>
				<td width="15%" align="left"><input type="text" class="text" name="caseName" value="${caseInformation.caseName}"/></td>
				<td width="10%" align="right">关键词：</td>
				<td width="15%" align="left"><input type="text" class="text" name="keyword" value="${caseInformation.keyword}"/></td>
				<td width="10%" align="right">案发时间:</td>
				<td width="15%" align="left">
				<input type="text" class="text" value="<fmt:formatDate value="${caseInformation.happenedTime}" pattern="yyyy-MM-dd" />" id="happenedTime" name="happenedTime" />
				</td>
				<td width="20%" align="left" valign="middle">
					<input type="submit" value="查&nbsp;询" class="btn_query">
				</td>
			</tr>
		</table>
	</form>
	</fieldset>
	<form:form id="delForm" action="${path }/opinion/caseInformation/batch_delete" method="post">
	<display:table name="caseInformationList" uid="caseInformation" cellpadding="0"
		cellspacing="0" requestURI="${path }/opinion/caseInformation/main" pagesize="10" keepStatus="true">
		<display:caption class="tooltar_btn">
			<input type="button" class="btn_small" value="添&nbsp;加" id="LayTypeAdd" onclick="addLawPerson()"/>
			<input type="submit" class="btn_big" value="批量删除" name="del"onclick="return batchDelete('check')"/>
		</display:caption>
		<display:column
		title="<input type='checkbox' onclick='checkAll(this)'/>">
		<input type="checkbox" name="check" value="${caseInformation.infoId}" />
		</display:column>
			<display:column title="序号">
				<c:if test="${page==null || page==0}">
				${caseInformation_rowNum}
			</c:if>
				<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + caseInformation_rowNum}
			</c:if>
			</display:column>
		<display:column title="案件编号" property="caseNo"style="text-align:left;"></display:column>
		<display:column title="案件名称  " property="caseName"style="text-align:left;"></display:column>
		<display:column title="信息来源" property="source"style="text-align:left;"></display:column>
		<display:column title="案发时间" style="text-align:left;">
			<fmt:formatDate value="${caseInformation.happenedTime}" pattern="yyyy-MM-dd" />
		</display:column>
		<display:column title="采集时间" style="text-align:left;">
			<fmt:formatDate value="${caseInformation.opinionDate}" pattern="yyyy-MM-dd" />
		</display:column>
		<display:column title="关键词" property="keyword"style="text-align:left;"></display:column>
		
		<display:column title="操作">
			<a href="${path }/opinion/caseInformation/detail?infoId=${caseInformation.infoId}">明细</a>&nbsp;&nbsp;
			<a href="javascript:;" onclick="update('${caseInformation.infoId}');">修改</a>&nbsp;&nbsp;
			<a href="javascript:;" onclick="top.art.dialog.confirm('确认删除  ${caseInformation.caseName} 吗？',
			function(){location.href = '${path }/opinion/caseInformation/del?infoId=${caseInformation.infoId}';
			})">删除</a>
		</display:column>
	</display:table>
	</form:form>
</div>
</body>
</html>