<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
 <script type="text/javascript">
$(function(){
	$('#backButton').click(function(){
		var districtId = '${districtId1}';
		var startTime = '${startTime}';
		var endTime = '${endTime}';
		var indexList='${indexList}';
		var queryDistrictId='${queryDistrictId}';
		window.location.href="${path}/breport/districtLevelStatsByOrg?districtId="+districtId
				+"&startTime="+startTime
				+"&endTime="+endTime
				+"&indexList="+indexList
				+"&queryDistrictId="+queryDistrictId;
	});
}); 

function search(){
	 $('#findForm')[0].action="${path}/breport/districtOrgStatsDrillDown";
	 $('#findForm').submit();                
}
</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
<legend class="legend">${title}案件查询</legend>
<form action="" id="findForm" method="post">
	<input type="hidden" class="text" id="districtId" name="districtId" value="${districtId}" />
	<input type="hidden" class="text" id="orgCode" name="orgCode" value="${orgCode}" />
	<input type="hidden" class="text" id="drillDownType" name="drillDownType" value="${drillDownType}" />
	<input type="hidden" class="text" id="indexList" name="indexList" value="${indexList}" />
	<input type="hidden" class="text" id="title" name="title" value="${title}" />
	<input type="hidden" class="text" id="districtId1" name="districtId1" value="${districtId1}" />
	<input type="hidden" class="text" id="startTime" name="startTime" value="${startTime}" />
	<input type="hidden" class="text" id="endTime" name="endTime" value="${endTime}" />
	<input type="hidden" class="text" id="queryDistrictId" name="queryDistrictId" value="${queryDistrictId}" />
	<table width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">
				案件编号
			</td>
			<td width="25%" align="left">
				<input type="text" class="text" id="caseNo" name="caseNo" value="${caseNo}" />
			</td>
			 <td width="15%" align="right">
				案件名称
			</td>
			<td width="25%" align="left">
				<input type="text" class="text" id="caseName" name="caseName" value="${caseName}" />
			</td>	
			<td rowspan="2" valign="middle" align="left">
				<input type="button" value="查 询" class="btn_query" onclick="search()"/>
			</td>
		</tr>
	</table>
</form>
</fieldset>
<display:table name="caseList" uid="case" cellpadding="0"  style="width:'100%';height:'100%'"
		cellspacing="0" requestURI="${path }/breport/districtOrgStatsDrillDown">
		<display:caption class="tooltar_btn" style="text-align: center;vertical-align: middle;">
			<input id="backButton" type="button" class="btn_small" value="返 回" style="float: left;" />
		</display:caption>
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${case_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + case_rowNum}
			</c:if>
		</display:column>
		<display:column title="案件编号" style="text-align:left;">
			<a href="javascript:;" onclick="top.showCaseProcInfo('${case.caseId}');">${case.caseNo }</a>
		</display:column>
		<display:column title="案件名称" style="text-align:left;">
			<span title="${case.caseName}">${fn:substring(case.caseName,0,20)}${fn:length(case.caseName)>20?'...':''}</span>
		</display:column>
		<display:column title="录入单位" property="orgName" style="text-align:center;">
		</display:column>
		<display:column title="录入时间" style="text-align:center;">
			<fmt:formatDate value="${case.inputTime}" pattern="yyyy-MM-dd HH:mm"/>
		</display:column>
		<display:column title="最后办理时间" style="text-align:center;">
			<fmt:formatDate value="${case.latestPocessTime}" pattern="yyyy-MM-dd HH:mm"/>
		</display:column>
		<display:column title="状态" style="text-align:center;">
			<dict:getDictionary var="stateVar" groupCode="${case.procKey }State"
				dicCode="${case.caseState }" />${stateVar.dtName }
		</display:column>
	</display:table>
	</div>
</body>
</html>