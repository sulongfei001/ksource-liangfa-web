<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@page import="com.ksource.syscontext.Const"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<base target="_self">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet"	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"	type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript"	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript"	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"	type="text/javascript"></script> 
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
function caseDetail(caseId){
	window.location.href="${path}/workflow/caseProcView/docCaseProcView.do?caseId="+caseId
			+"&requestURL=${requestURL}&indexList=${indexList}&districtId=${caseBasic.districtId}"
			+"&startTime=${startTime}&endTime=${endTime}"
			+"&monthCode=${caseBasic.monthCode}&yearCode=${caseBasic.yearCode}&quarterCode=${caseBasic.quarterCode}";
}

function search(){
	 $('#queryForm').submit(); 
}
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">案件信息查询</legend>
<form id="queryForm" method="post" action="${path }/office/officeDocLink/dsjfx">
	<input type="hidden" id="indexList" name="indexList" value="${indexList}"/>
	<input type="hidden" id="districtId" name="districtId" value="${caseBasic.districtId}"/>
	<input type="hidden" id="startTime" name="startTime" value="${startTime}" />
	<input type="hidden" id="endTime" name="endTime" value="${endTime}"/>
	<input type="hidden" id="yearCode" name="yearCode" value="${caseBasic.yearCode}"/>
	<input type="hidden" id="quarterCode" name="quarterCode" value="${caseBasic.quarterCode}"/>
	<input type="hidden" id="monthCode" name="monthCode" value="${caseBasic.monthCode}"/>
				<table width="100%" class="searchform">
				<tr>
                    <td width="15%" align="right">案件编号</td>
                    <td width="30%" align="left">
                        <input type="text" name="caseNo" id="caseNo" value="${param.caseNo }" class="text"/>
                    </td>			
					<td width="10%" align="right">案件名称</td>
                    <td width="30%" align="left">
                        <input type="text" name="caseName" value="${fn:replace(param.caseName,"\"","&quot;")}" class="text"/>
                    </td>
					<td rowspan="2" valign="middle" align="left">
						<input type="button" value="查 询" class="btn_query" onclick="search()"/>
					</td>
				</tr>
			</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
<display:table name="caseList" uid="case" cellpadding="0"
	cellspacing="0" requestURI="${path }/office/officeDocLink/dsjfx">
	<display:caption style="text-align:center;">
		<font style="font-weight: bold;font-size: 15px">${title }</font>
	</display:caption>
	<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${case_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + case_rowNum}
			</c:if>
	</display:column>
	<display:column title="案件编号" style="text-align:left;">
		<a href="javascript:;" onclick="caseDetail('${case.caseId}');">${case.caseNo}</a>
	</display:column>
		<display:column title="案件名称" style="text-align:left;" class="cutout">
			<span title="${case.caseName}">${fn:substring(case.caseName,0,15)}${fn:length(case.caseName)>15?'...':''}</span>
		</display:column>
		<display:column title="录入单位" style="text-align:left;" class="cutout">
			<span title="${case.orgName}">${case.orgName}</span>
		</display:column>
		<%-- <display:column title="状态" property="caseStateName" style="text-align:left;"></display:column> --%>
		<display:column title="违法行为发生时间">
			<fmt:formatDate value="${case.anfaTime }" pattern="yyyy-MM-dd" />
		</display:column>
		<display:column title="违法行为发生地址" property="anfaAddress">
		</display:column>
		<display:column title="涉案物品"> 
			<span title="${case.goodsInvolved}">${fn:substring(case.goodsInvolved,0,15)}${fn:length(case.goodsInvolved)>15?'...':''}</span>
		</display:column>
</display:table>
</body>
</html>