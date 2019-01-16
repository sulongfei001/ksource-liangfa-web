<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
 <script type="text/javascript">
$(function(){
	$('#backButton').click(function(){
		var districtId = '${districtId}';
		var startTime = '${startTime}';
		var endTime = '${endTime}';
		var indexList='${indexList}';
		window.location.href="${path}/accuseStats/general?districtId="+districtId
				+"&startTime="+startTime
				+"&endTime="+endTime
				+"&indexList="+indexList;
	});
}); 
</script>

</head>
<body>
<div class="panel">
<c:choose>
<c:when test="${!empty caseList}">
<display:table name="caseList" uid="case" cellpadding="0"  style="width:'100%';height:'100%'"
		cellspacing="0" requestURI="${path }/accuseStats/drillDown">
		<display:caption class="tooltar_btn" style="text-align: center;vertical-align: middle;">
		<input id="backButton" type="button" class="btn_small" style="float: left;" value="返 回"/>
		<font style="font-weight: bold;font-size: 15px">${districtName}</font> 
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
	</c:when>
	<c:otherwise>
	<display:table name="xianyirenList" uid="xianyiren" cellpadding="0" cellspacing="0" 
	requestURI="${path }/stats/drillDown">
	<display:caption class="tooltar_btn"><input id="backButton" type="button" class="btn_small" value="返 回"/></display:caption>
		<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${xianyiren_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + xianyiren_rowNum}
			</c:if>
			</display:column>
		<display:column title="姓名" property="name" style="text-align:left;"></display:column>
		<display:column title="性别" style="text-align:left;">
		<dict:getDictionary var="domain" groupCode="sex" dicCode="${xianyiren.sex}"/>
			${domain.dtName}
		</display:column>
		<display:column title="文化程度" style="text-align:left;">
		<dict:getDictionary var="domain" groupCode="educationLevel" dicCode="${xianyiren.education}"/>
			${domain.dtName}
		</display:column>
		<display:column title="是否逮捕" style="text-align:left;">
			<c:if test="${xianyiren.daibuState != 3}">
				未逮捕
			</c:if>
			<c:if test="${xianyiren.daibuState == 3}">
				已逮捕
			</c:if>
		</display:column>
		<display:column title="是否判决" style="text-align:left;">
			<c:if test="${xianyiren.fayuanpanjueState == 1}">
				未判决
			</c:if>
			<c:if test="${xianyiren.fayuanpanjueState == 2}">
				已判决
			</c:if>
		</display:column>
		<display:column title="操作">
			<a href="javascript:;" onclick="top.showCaseProcInfo('${xianyiren.caseId}');">案件详情</a>
		</display:column>
	</display:table>
	</c:otherwise>
	</c:choose>
	</div>
</body>
</html>