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
		var startExpTime = document.getElementById('startExpTime');
		startExpTime.onfocus = function(){
			  WdatePicker({dateFmt : 'yyyy-MM-dd'});
		  }	 
		  var endExpTime = document.getElementById('endExpTime');
		  endExpTime.onfocus = function(){
			  WdatePicker({dateFmt : 'yyyy-MM-dd'});
		  }	
	});
  function showExpLogDetail(logId){
	  window.location.href="${path}/caseExport/expLogDetail?logId="+logId+"&backType=query";
  }
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">案件导出日志查询</legend>
<form:form  action="${path}/caseExport/queryExpLog">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="12%" align="right">
				案件导出时间
			</td>
			<td width="20%" align="left">
				<input type="text" class="text" name="startExpTime" id="startExpTime" value="${param.startExpTime }" style="width: 34%"/>
			到
			<input type="text" class="text" name="endExpTime" id="endExpTime" value="${param.endExpTime }"style="width: 34%"/>
			</td>
			<td width="36%"  rowspan="3" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
	</table>
</form:form>
</fieldset>
<br/>
<display:table name="logList" uid="log" cellpadding="0"  style="width:'100%';height:'100%'"
		cellspacing="0" requestURI="${path }/caseExport/queryExpLog">
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${log_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + log_rowNum}
			</c:if>
		</display:column>
		<display:column title="导出人" property="operatorName" style="text-align:left;"></display:column>
		<display:column title="所属单位" property="orgName" style="text-align:left;"></display:column>
		<display:column title="导出时间" style="text-align:left;">
			<fmt:formatDate value="${log.expTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column title="新增案件数（件）" property="insertCount" style="text-align:left;"></display:column>
		<display:column title="修改案件数（件）" property="updateCount" style="text-align:left;"></display:column>
		<display:column title="删除案件数（件）" property="deleteCount" style="text-align:left;"></display:column>
		<display:column title="操作">
			<a href="javascript:void();" onclick="showExpLogDetail('${log.logId}');">明细</a>
		</display:column>
	</display:table>
	</div>
</body>
</html>