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
		var districtId = '${districtCode}';
		var startTime = '${startTime}';
		var endTime = '${endTime}';
		var indexList='${indexList}';
		window.location.href="${path}/breport/situationCollectStats?districtId="+districtId
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
<c:when test="${!empty lawPersonList }">
	<display:table name="lawPersonList" uid="lawPerson" cellpadding="0"  style="width:'100%';height:'100%'"
		cellspacing="0" requestURI="${path }/breport/situationCollectDrillDown">
		<display:caption class="tooltar_btn" style="text-align: center;vertical-align: middle;">
		<input id="backButton" type="button" class="btn_small" value="返 回" style="float: left;" />
			<font style="font-weight: bold;font-size: 15px">
			${title }
			</font>
		</display:caption>
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${lawPerson_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + lawPerson_rowNum}
			</c:if>
		</display:column>
		<display:column title="姓名" property="name" style="text-align:left;"></display:column>
		<display:column title="所属单位 " property="orgName" style="text-align:left;">
		</display:column>
		<%-- <display:column title="行政区划" style="text-align:left;">
		</display:column> --%>
		<display:column title="发证机关" property="licenceOrg" style="text-align:left;">
		</display:column>
		<display:column title="执法类别" property="lawType" style="text-align:left;">
		</display:column>
		<display:column title="操作">
			<a href="javascript:;" onclick="top.showSituationCollectInfo(${lawPerson.personId},1);">明细</a>
		</display:column>
	</display:table>
</c:when>
<c:when test="${!empty caseList}">
	<display:table name="caseList" uid="case" cellpadding="0"  style="width:'100%';height:'100%'"
		cellspacing="0" requestURI="${path }/breport/situationCollectDrillDown">
		<display:caption class="tooltar_btn" style="text-align: center;vertical-align: middle;">
		<input id="backButton" type="button" class="btn_small" value="返 回" style="float: left;" />
			<font style="font-weight: bold;font-size: 15px">
			${title }
			</font>
		</display:caption>
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${case_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + case_rowNum}
			</c:if>
		</display:column>
		<display:column title="标题" property="title" style="text-align:left;"></display:column>
		<display:column title="案件编号" property="caseNo" style="text-align:left;"></display:column>
		<display:column title="案件名称" style="text-align:left;">
			<span title="${case.caseName}">${fn:substring(case.caseName,0,20)}${fn:length(case.caseName)>20?'...':''}</span>		
		</display:column>
		<display:column title="信息来源" property="source" style="text-align:left;">
		</display:column>
		<display:column title="案发时间" style="text-align:left;">
			<fmt:formatDate value="${case.happenedTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="采集时间" style="text-align:left;">
			<fmt:formatDate value="${case.opinionDate}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="关键词" property="keyword" style="text-align:left;">
		</display:column>
		<display:column title="操作">
			<a href="javascript:;" onclick="top.showSituationCollectInfo(${case.infoId},2);">明细</a>
		</display:column>
	</display:table>
</c:when>
<c:when test="${!empty publicOptionList}">
	<display:table name="publicOptionList" uid="opinion" cellpadding="0"  style="width:'100%';height:'100%'"
		cellspacing="0" requestURI="${path }/breport/situationCollectDrillDown">
		<display:caption class="tooltar_btn" style="text-align: center;vertical-align: middle;">
		<input id="backButton" type="button" class="btn_small" value="返 回" style="float: left;" />
			<font style="font-weight: bold;font-size: 15px">
			${title }
			</font>
		</display:caption>
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${opinion_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + opinion_rowNum}
			</c:if>
		</display:column>
		<display:column title="标题" property="title" style="text-align:left;"></display:column>
		<display:column title="信息来源" property="source" style="text-align:left;">
		</display:column>
		<display:column title="事发时间" style="text-align:left;">
			<fmt:formatDate value="${opinion.happenedTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="采集时间" style="text-align:left;">
			<fmt:formatDate value="${opinion.opinionDate}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="关键词" property="keyword" style="text-align:left;"></display:column>
		<display:column title="操作">
			<a href="javascript:;" onclick="top.showSituationCollectInfo(${opinion.infoId},3);">明细</a>
		</display:column>
	</display:table>
</c:when>
<c:when test="${!empty integratedInformationList}">
	<display:table name="integratedInformationList" uid="info" cellpadding="0"  style="width:'100%';height:'100%'"
		cellspacing="0" requestURI="${path }/breport/situationCollectDrillDown">
		<display:caption class="tooltar_btn" style="text-align: center;vertical-align: middle;">
		<input id="backButton" type="button" class="btn_small" value="返 回" style="float: left;" />
			<font style="font-weight: bold;font-size: 15px">
			${title }
			</font>
		</display:caption>
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${info_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + info_rowNum}
			</c:if>
		</display:column>
		<display:column title="标题" property="title" style="text-align:left;"></display:column>
		<display:column title="信息来源" property="source" style="text-align:left;">
		</display:column>
		<display:column title="事发时间" style="text-align:left;">
			<fmt:formatDate value="${info.happenedTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="采集时间" style="text-align:left;">
			<fmt:formatDate value="${info.opinionDate}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="关键词" property="keyword" style="text-align:left;"></display:column>
		<display:column title="操作">
			<a href="javascript:;" onclick="top.showSituationCollectInfo(${info.infoId},4);">明细</a>
		</display:column>
	</display:table>
</c:when>
<c:otherwise>
	<display:table name="otherInformationList" uid="other" cellpadding="0"  style="width:'100%';height:'100%'"
		cellspacing="0" requestURI="${path }/breport/situationCollectDrillDown">
		<display:caption class="tooltar_btn" style="text-align: center;vertical-align: middle;">
		<input id="backButton" type="button" class="btn_small" value="返 回" style="float: left;" />
			<font style="font-weight: bold;font-size: 15px">
			${title }
			</font>
		</display:caption>
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${other_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + other_rowNum}
			</c:if>
		</display:column>
		<display:column title="标题" property="title" style="text-align:left;"></display:column>
		<display:column title="信息来源" property="source" style="text-align:left;">
		</display:column>
		<display:column title="事发时间" style="text-align:left;">
			<fmt:formatDate value="${other.anfaTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="采集时间" style="text-align:left;">
			<fmt:formatDate value="${other.opinionDate}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="关键词" property="keyword" style="text-align:left;"></display:column>
		<display:column title="操作">
			<a href="javascript:;" onclick="top.showSituationCollectInfo(${other.infoId},5);">明细</a>
		</display:column>
	</display:table>
</c:otherwise>
</c:choose>
</div>
</body>
</html>