<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/displaytagall.css"/>" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet"	href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"	type="text/css" />
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript"	src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"   ></script> 
<script type="text/javascript">
$(function(){
	
	//日期插件,天[dd],月[mm],年[yyyy]
	var startTime = document.getElementById('startTime');
	startTime.onfocus = function(){
		var timeType = $("input[name='timeType']:checked").val();
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}	
	var endTime = document.getElementById('endTime');
	endTime.onfocus = function(){
		var timeType = $("input[name='timeType']:checked").val();
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
});
	
</script>
<title>待办任务列表</title>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">已办案件查询</legend>
	<form action="${path }/workflow/task/completed" method="post">
		<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
		    <td width="15%" align="right">案件编号</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="caseNo" value="${param.caseNo }" />
			</td>
			<td width="15%" align="right">案件名称</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="caseName" value="${param.caseName}" />
			</td>
			<td width="20%"  rowspan="2" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
		<tr>
			<td width="15%" align="right">录入时间</td>
			<td width="25%" align="left">
				<input type="text" name="startTime" id="startTime" value="${param.startTime }" class="text" readonly="readonly" style="width: 33.5%"/>
				至
				<input type="text" name="endTime" id="endTime" value="${param.endTime }" class="text" readonly="readonly" style="width: 33.5%"/>
			</td>
			<td width="15%" align="right">案件状态</td>
			<td width="25%" align="left">
			<dict:getDictionary	var="caseStateList" groupCode="chufaProcState" /> 
				<select id="caseState" name="caseState" style="width: 77%">
					<option value="">--全部--</option>
						<c:forEach var="domain" items="${caseStateList }">
							<c:choose>
								<c:when test="${domain.dtCode !=param.caseState}">
								<option value="${domain.dtCode }">${domain.dtName }</option>
								</c:when>
								<c:otherwise >
								<option value="${domain.dtCode }" selected="selected">${domain.dtName }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select>
			</td>
		</tr>
	</table>
	</form>
	</fieldset>
<display:table name="caseList"  uid="case" cellpadding="0" cellspacing="0"  requestURI="/workflow/task/completed">
	<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${case_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + case_rowNum}
			</c:if>
    </display:column>
    <display:column  title="案件编号" style="text-align:left;">
    	<a href="javascript:;" onclick="top.showCaseProcInfo('${case.businessKey}','','${case.procKey}');">${case.procBusinessEntityNO }</a>
    </display:column>
	<display:column  title="案件名称"  style="text-align:left;">
		<span title="${case.businessName}">${fn:substring(case.businessName,0,20)}${fn:length(case.businessName)>20?'...':''}</span>
	</display:column>
	<display:column  title="录入单位" property="inputUnitName" style="text-align:left;"/>
	<display:column title="录入时间" style="text-align:left;">
		<fmt:formatDate value="${case.inputTime}" pattern="yyyy-MM-dd HH:mm"/>
	</display:column>
	<display:column title="最后办理时间" style="text-align:left;">
		<fmt:formatDate value="${case.latestPocessTime}" pattern="yyyy-MM-dd HH:mm"/>
	</display:column>
	<display:column  title="案件状态" style="text-align:left;">
		<dict:getDictionary var="state" groupCode="${case.procKey}State" dicCode="${case.caseState }"/>${state.dtName }
	</display:column>
</display:table>
</div>
</body>
</html>