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
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		var startTime = document.getElementById('startTime');
		startTime.onfocus = function(){
			  WdatePicker({dateFmt : 'yyyy-MM-dd'});
		  }	 
		  var endTime = document.getElementById('endTime');
		  endTime.onfocus = function(){
			  WdatePicker({dateFmt : 'yyyy-MM-dd'});
		  }	
	});
  function addReply(id){
	  window.location.href="${path}/caseReply/addUI?caseId="+id;
  }
  function showReply(id){
	  window.location.href="${path}/caseReply/detail?caseId="+id+'&backType=main';
  }
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">行政违法案件批复查询</legend>
<form:form  action="${path}/caseReply/main">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">
				案件编号
			</td>
			<td width="25%" align="left" >
				<input name="caseNo" value="${param.caseNo}" class="text"/>
			</td>
			<td width="15%" align="right">
				案件录入时间
			</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 37%"/>
			到
			<input type="text" class="text" name="endTime" id="endTime" value="${param.endTime }"style="width: 37%"/>
			</td>
			<td width="20%"  rowspan="3" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
		<tr>
			<td width="15%" align="right">
				案件名称
			</td>
			<td width="25%" align="left" >
				<input name="caseName" value="${param.caseName}" class="text"/>
			</td>
			<td width="15%" align="right">
				是否批复
			</td>
			<td width="25%" align="left" >
				<dict:getDictionary var="caseReplyList" groupCode="caseReply"/>
				<select id="isReply" name="isReply" >
					<option value="">--全部--</option>
					<c:forEach items="${caseReplyList}" var="reply">
					<c:choose>
					<c:when test="${reply.dtCode==param.isReply}">
					  <option value="${reply.dtCode}" selected="selected">${reply.dtName}</option>
					</c:when>
					<c:otherwise>
					   <option value="${reply.dtCode}">${reply.dtName}</option>
					</c:otherwise>
					</c:choose>
					</c:forEach>
				</select>
			</td>
		</tr>
	</table>
</form:form>
</fieldset>
<br/>
<display:table name="caseList" uid="caseInfo" cellpadding="0"  style="width:'100%';height:'100%'"
		cellspacing="0" requestURI="${path }/caseReply/main">
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${caseInfo_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + caseInfo_rowNum}
			</c:if>
		</display:column>
		<display:column title="案件编号" property="caseNo" style="text-align:left;"></display:column>
		<display:column title="案件名称" property="caseName" style="text-align:left;"></display:column>
		<display:column title="录入时间" style="text-align:left;">
			<fmt:formatDate value="${caseInfo.inputTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="是否批复" style="text-align:left;">
		<c:choose>
			<c:when test="${caseInfo.isReply==0}">
				未批复
			</c:when>
			<c:otherwise>
				已批复
			</c:otherwise>
		</c:choose>
		</display:column>
		<display:column title="操作">
			<a href="javascript:void();" onclick="addReply('${caseInfo.caseId}');return false;">批复</a>
			<c:if test="${caseInfo.isReply!=0}">
				<a href="javascript:void();" onclick="showReply('${caseInfo.caseId}');return false;">查看批复</a>
			</c:if>
			<a href="javascript:void();" onclick="top.showCaseProcInfo('${caseInfo.caseId}');">案件详情</a>
		</display:column>
	</display:table>
	</div>
</body>
</html>