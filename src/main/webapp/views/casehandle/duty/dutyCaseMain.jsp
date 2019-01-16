<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>	
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

});
function toAdd(){
	   window.location.href="${path}/casehandle/dutycase/addUI";
	}
</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">移送职务犯罪案件查询</legend>
<form action="${path }/casehandle/dutycase/search" method="post">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">案件编号</td>
			<td width="25%" align="left"><input type="text" class="text" name="caseNo"
				id="caseNo" value="${param.caseNo }" /></td>
			<td width="15%" align="right">案件名称</td>
			<td width="25%" align="left"><input type="text" class="text"
				name="caseName" value="${fn:replace(param.caseName,"\"","&quot;")}" /></td>
			<td width="20%"  rowspan="3" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
		<tr>
			<td width="15%" align="right">录入时间</td>
			<td width="25%" align="left">
				<input type="text" name="startTime" id="startTime" value="${param.startTime }" class="text" style="width: 36%"/>
				至
				<input type="text" name="endTime" id="endTime" value="${param.endTime }" class="text" style="width: 36%"/>
			</td>
			<td width="15%" align="right">状态</td>
			<td width="25%" align="left">
			<dict:getDictionary
				var="caseStateList" groupCode="${procKey}State" /> <select id="caseState"
				name="caseState">
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
			</select></td>
		</tr>
		<tr>
			<td width="15%" align="right">涉案人名称</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="peopleName" id="peopleName" value="${param.peopleName}"/>
			</td>
		</tr>
	</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
<form:form method="post" action="${path }/casehandle/case/del">
	<display:table name="caseList" uid="case" cellpadding="0"
		cellspacing="0" requestURI="${path }/casehandle/dutycase/search">
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" class="btn_small" name="caseAdd"
				onclick="toAdd()" />
		</display:caption>
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${case_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + case_rowNum}
			</c:if>
		</display:column>
		<display:column title="案件编号" property="caseNo" style="text-align:left;"></display:column>
		<display:column title="案件名称" property="caseName" style="text-align:left;"></display:column>
		<display:column title="状态" style="text-align:left;">
			<dict:getDictionary var="stateVar" groupCode="${procKey}State"
				dicCode="${case.caseState }" />
		${stateVar.dtName }
		</display:column>
		<display:column title="录入时间" style="text-align:left;">
			<fmt:formatDate value="${case.inputTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="最后办理时间" style="text-align:left;">
			<fmt:formatDate value="${case.latestPocessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column title="操作">
			<a href="javascript:;" onclick="top.showCaseProcInfo('${case.caseId}',false,'${procKey }');">案件详情</a>
			<!--<c:if test="${case.caseState==1 }">
			<a href="javascript:void();" onclick="javascript:window.location.href='${path}/casehandle/case/updateUI?procKey=${procKey}&caseId=${case.caseId}'">修改</a>
		    </c:if>-->
		</display:column>
	</display:table>
</form:form>
</div>
</body>
</html>