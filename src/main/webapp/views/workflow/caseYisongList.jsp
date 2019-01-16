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
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path}/resources/script/cleaner.js"></script>	
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>
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

</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">移送违纪案件查询</legend>
<form action="${path }/workflow/task/caseYisongExamine" method="post" >
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">案件编号</td>
			<td width="25%" align="left"><input type="text" class="text" name="caseNo"
				id="caseNo" value="${param.caseNo }" /></td>
			<td width="15%" align="right">案件名称</td>
			<td width="25%" align="left"><input type="text" class="text"
				name="caseName" value="${param.caseName }" /></td>
			<td width="20%"  rowspan="3" align="left" valign="middle">
			<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
		<tr>
			<td width="15%" align="right">移送时间</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 33.5%" readonly="readonly"/>
				至
				<input type="text" class="text" name="endTime" id="endTime" value="${param.endTime}" style="width: 33.5%" readonly="readonly"/>
			</td>
			<td width="15%" align="right"></td>
			<td width="25%" align="left">
			</td>
		</tr>
	</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
	<display:table name="caseYisongList" uid="caseYisongjiwei" cellpadding="0"
		cellspacing="0" requestURI="${path }/workflow/task/caseYisongExamine" decorator="">
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${caseYisongjiwei_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + caseYisongjiwei_rowNum}
			</c:if>
		</display:column>
		<display:column title="案件编号" style="text-align:left;" >
			<a href="javascript:;" onclick="top.showCaseProcInfo('${caseYisongjiwei.caseId}');">${caseYisongjiwei.caseNo }</a>
		</display:column>
		<display:column title="案件名称" style="text-align:left;">
			<span title="${caseYisongjiwei.caseName}">${fn:substring(caseYisongjiwei.caseName,0,20)}${fn:length(caseYisongjiwei.caseName)>20?'...':''}</span>
		</display:column>
		<display:column title="移送单位" property="yisongOrgName"  style="text-align:left;text-align:center">
		</display:column>
		<display:column title="移送时间" style="text-align:left; text-align:center;">
			<fmt:formatDate value="${caseYisongjiwei.yisongTime}" pattern="yyyy-MM-dd HH:MM"/>
		</display:column>
	</display:table>

</div>
</body>
</html>