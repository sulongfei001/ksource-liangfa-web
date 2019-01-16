<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"  />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path}/resources/script/cleaner.js"></script>	
<script type="text/javascript" src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script type="text/javascript" src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/zTree/jquery.ztree-2.6.js"></script>
<script type="text/javascript">
$(function(){
	
	//日期插件时[hh],天[dd],月[mm],年[yyyy]
	var startTime = document.getElementById('startTime');
	startTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	var endTime = document.getElementById('endTime');
	endTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	
	
	$('.noUpdate').poshytip({
	    content:"已转换为线索,无法操作!",
	    className: 'tip-yellowsimple',
	    showTimeout: 1,
	    slide: false,
	    fade: false,
	    alignTo: 'target',
	    alignX: 'left',
	    alignY: 'center',
	    offsetX: 2
	});
});
</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">市长热线数据查询</legend>
<form action="${path }/hotlineInfo/list" method="post" >
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="10%" align="right">受理编号</td>
			<td width="20%" align="left"><input type="text" class="text" name="infoNo"
				id="infoNo" value="${param.infoNo }" /></td>
			<td width="10%" align="right">主题</td>
			<td width="30%" align="left"><input type="text" class="text" name="theme"
				id="theme" value="${param.theme }" /></td>
			<td width="10%" align="right">内容类型</td>
			<td width="20%" align="left">
			<dict:getDictionary	var="caseStateList" groupCode="contentType" /> 
				<select id="contentType" name="contentType" style="width: 77%">
					<option value="">--全部--</option>
						<c:forEach var="domain" items="${caseStateList }">
							<c:choose>
								<c:when test="${domain.dtCode !=param.contentType}">
								<option value="${domain.dtCode }">${domain.dtName }</option>
								</c:when>
								<c:otherwise >
								<option value="${domain.dtCode }" selected="selected">${domain.dtName }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select>
			</td>
			<td width="20%"  rowspan="3" align="left" valign="middle">
			<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
		<tr>
			<td width="10%" align="right">反映类型</td>
			<td width="20%" align="left">
			<dict:getDictionary	var="caseStateList" groupCode="hotlineType" /> 
				<select id="hotlineType" name="hotlineType" style="width: 77%">
					<option value="">--全部--</option>
						<c:forEach var="domain" items="${caseStateList }">
							<c:choose>
								<c:when test="${domain.dtCode !=param.hotlineType}">
								<option value="${domain.dtCode }">${domain.dtName }</option>
								</c:when>
								<c:otherwise >
								<option value="${domain.dtCode }" selected="selected">${domain.dtName }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select>
			</td>
			
			<td width="10%" align="right">受理时间</td>
			<td width="20%" align="left">
				<input type="text" class="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 34%" readonly="readonly"/>
				至
				<input type="text" class="text" name="endTime" id="endTime" value="${param.endTime}" style="width: 34%" readonly="readonly"/>
			</td>
			<%-- <td width="10%" align="right">办结时间</td>
			<td width="20%" align="left">
				<input type="text" class="text" name="handleStartTime" id="handleStartTime" value="${param.handleStartTime }" style="width: 36%" readonly="readonly"/>
				至
				<input type="text" class="text" name="handleEndTime" id="handleEndTime" value="${param.handleEndTime}" style="width: 36%" readonly="readonly"/>
			</td> --%>
		</tr>
	</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
<form:form method="post" action="${path }/hotlineInfo/list">
	<display:table name="hotlineInfoList" uid="hotlineInfo" cellpadding="0"
		cellspacing="0" requestURI="${path }/hotlineInfo/list" decorator="">
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${hotlineInfo_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + hotlineInfo_rowNum}
			</c:if>
		</display:column>
		<display:column title="受理编号" style="text-align:left;">
			<a href="${path }/hotlineInfo/details?infoId=${hotlineInfo.infoId}">${hotlineInfo.infoNo }</a>
		</display:column>
		<display:column title="主题" style="text-align:left;">
			<span title="${hotlineInfo.theme}">${fn:substring(hotlineInfo.theme,0,20)}${fn:length(hotlineInfo.theme)>20?'...':''}</span>
		</display:column>
		<display:column title="内容类型" style="text-align:center;">
			<dict:getDictionary var="stateVar" groupCode="contentType" dicCode="${hotlineInfo.contentType }" />
			${stateVar.dtName }
		</display:column>
		<display:column title="受理时间" style="text-align:center;">
			<fmt:formatDate value="${hotlineInfo.acceptTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<%-- <display:column title="办结时间" style="text-align:center;">
			<fmt:formatDate value="${hotlineInfo.handleTime}" pattern="yyyy-MM-dd"/>
		</display:column> --%>
		<display:column title="反映类型" style="text-align:center;">
			<dict:getDictionary var="stateVar" groupCode="hotlineType" dicCode="${hotlineInfo.hotlineType }" />
			${stateVar.dtName }
		</display:column>	
		<display:column title="操作">
			<c:choose>
				<c:when test="${hotlineInfo.hotlineState == 1}">
					<a href="javascript:void(0);"  class="message noUpdate" style="cursor: pointer;color:#C0C0C0">转换为线索</a>	
				</c:when>
				<c:otherwise>
					<a href="${path}/casehandle/clueInfo/getClueAddViewByHotline.do?hotlineId=${hotlineInfo.infoId}&infoId=${hotlineInfo.infoId}&back=2">转换为线索</a>
				</c:otherwise>
			</c:choose>
		</display:column>
	</display:table>
</form:form>
</div>
</body>
</html>