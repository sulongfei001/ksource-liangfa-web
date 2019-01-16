<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/script/prototip/css/prototip.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript">
	//删除用户操作
	function del(industryType){
		$.ligerDialog.confirm('确认删除吗?',function(flag){
			if(flag){
				window.location.href="${path}/system/industryInfo/del?industryType="+industryType;
				//window.location.reload();
			}
		});
	}
	$(function(){
		var info = "${info}";
		if(info != null && info != ""){
			if(info == 'add'){
				//行业添加成功提示
				$.ligerDialog.success('行业类型添加成功！');
			}else if(info == 'del'){
				//行业删除成功提示
				$.ligerDialog.success('行业类型删除成功！');
			}
		}
	});
	function toIndustryInfoAdd(form){
		form.action="${path}/system/industryInfo/addView";
		form.submit();
	}
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">行业类型查询</legend>
<form:form method="post" action="${path }/system/industryInfo/search" id="queryForm">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="12%" align="right">
				行业类型
			</td>
			<td width="20%" align="left" >
				<input type="text" name="industryType" value="${industryInfo.industryType}" class="text"/>
			</td>
			<td width="12%" align="right">
				行业名称
			</td>
			<td width="20%" align="left">
				<input type="text" name="industryName" value="${industryInfo.industryName}" class="text"/>
			</td>
			<td width="36%" align="left" valign="middle">
				<input type="submit" value="查 询" class="btn_query" />
			</td>
		</tr>
	</table>
	<!-- 查询结束 -->
</form:form>
</fieldset>

<form:form id="delForm" method="post" action="${path }/system/industryInfo/del">
	<display:table name="industryInfoList" uid="industryInfo" cellpadding="0"
		cellspacing="0" requestURI="${path }/system/industryInfo/search">
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" name="userAdd"  class="btn_small" onclick="toIndustryInfoAdd(this.form)" />			
		</display:caption>
		<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${industryInfo_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + industryInfo_rowNum}
			</c:if>
		</display:column>
		<display:column title="行业类型" property="industryType" style="text-align:center;"></display:column>
		<display:column title="行业名称" property="industryName" style="text-align:center;"></display:column>
		<display:column title="操作">
			<a href="<c:url value="/system/industryInfo/updateView?industryType=${industryInfo.industryType}"/>">修改</a>
			<a href="javascript:;" onclick="del('${industryInfo.industryType}')">删除</a>
			<%-- <a href="javascript:;" onclick="$.ligerDialog.confirm('确认允许删除吗?',function(){location.href = '${path}/system/industryInfo/del?industryType=${industryInfo.industryType}';})">删除</a> --%>
		</display:column>
	</display:table>
</form:form>
</div>
</body>
</html>