<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>督办案件管理</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/script/prototip/css/prototip.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
	function add() {
		window.location.href = "${path}/activity/supervise_case/v_add";
	}
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">督办案件查询</legend>
<form:form method="post" action="${path }/activity/supervise_case/main" id="queryForm">
	<table width="100%" class="searchform">
		<tr>
			<td width="12%" align="right">
				专项活动：
			</td>
			<td style="text-align: left; width: 20%;">
				<select class="text" id="typeId" name="typeId">
					<option value="">--请选择--</option>
					<c:forEach items="${specialActivities }" var="specialActivitie">
					<option value="${specialActivitie.id }" ${specialActivitie.id == superviseCase.typeId?"selected":"" }>${specialActivitie.name }</option>
					</c:forEach>
				</select>
			</td>
			<td width="12%" align="right">
				案件编号：
			</td>
			<td style="text-align: left;width: 20%;">
				<input type="text" class="text"  id="caseId"  name="caseId" value="${superviseCase.caseId }" />
			</td>
			<td width="36%" align="left" valign="middle">
				<input type="submit" value="查 询" class="btn_query" />
			</td>
		</tr>
	</table>
	<!-- 查询结束 -->
</form:form>
</fieldset>

<display:table name="superviseCases" uid="superviseCase" cellpadding="0"
	cellspacing="0" requestURI="${path }/activity/supervise_case/main" pagesize="10" keepStatus="true">
	<display:caption class="tooltar_btn">
		<input type="button" class="btn_small" value="添&nbsp;加" name="add"
			onclick="add()" />
	</display:caption>
	<display:column title="序号">
		<c:if test="${page==null || page==0}">
			${superviseCase_rowNum}
		</c:if>
		<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + superviseCase_rowNum}
		</c:if>
	</display:column>
	<display:column title="案件编号" style="text-align:left;">
		<a href="${path}/activity/supervise_case/detail?superviseId=${superviseCase.superviseId }">${superviseCase.caseId }</a>
	</display:column>
	<display:column title="案件名称" style="text-align:left;">
		<span title="${superviseCase.caseName}">${fn:substring(superviseCase.caseName,0,30)}${fn:length(superviseCase.caseName)>30?'...':''}</span>
	</display:column>
	<display:column title="专项活动" property="activityName" style="text-align:left;"></display:column>
	<display:column title="组织机构" property="orgName" style="text-align:left;"></display:column>
	<display:column title="创建时间" style="text-align:left;">
		<fmt:formatDate value="${superviseCase.createTime}" pattern="yyyy-MM-dd"/>
	</display:column>
	<display:column title="操作">
		<a href="javascript:;" onclick="window.location.href='${path}/activity/supervise_case/v_update?superviseId=${superviseCase.superviseId }'">修改</a>
		<a href="javascript:;" onclick="top.art.dialog.confirm('确认要删除此督办案件吗？',function(){window.location.href='${path}/activity/supervise_case/delete?superviseId=${superviseCase.superviseId }';})">删除</a>
	</display:column>
</display:table>
</div>
</body>
</html>