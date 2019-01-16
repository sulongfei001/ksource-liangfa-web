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
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"   ></script> 
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
        <legend class="legend">嫌疑人相关案件查询</legend>
        <form id="queryForm" action="${path}/query/suspectQuery/showCaseByXianyiren" method="post">
            <input type="hidden" id="idsNo" name="idsNo" value="${idsNo}">
            <table width="100%" class="searchform">
                <tr>
                    <td width="15%" align="right">案件编号</td>
                    <td width="25%" align="left">
                    	<input type="text" name="caseNo" id="caseNo" class="text" value="${param.caseNo}"/>
                    </td>
					<td width="15%" align="right">案件名称</td>
					<td width="25%" align="left">
						<input type="text" name="caseName" id="caseName" value="${param.caseName }" class="text"/>
					</td>
                    <td width="20%" align="left"  valign="middle">
                        <input type="submit" value="查 询" class="btn_query" />
                    </td>
                </tr>
            </table>
        </form>
</fieldset>
<display:table name="caseList" uid="case" cellpadding="0"  style="width:'100%';height:'100%'"
		cellspacing="0" requestURI="${path }/query/suspectQuery/showCaseByXianyiren">
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
		<display:column title="状态" style="text-align:center;">
			<dict:getDictionary var="stateVar" groupCode="${case.procKey }State"
				dicCode="${case.caseState }" />${stateVar.dtName }
		</display:column>
	</display:table>
	</div>
</body>
</html>