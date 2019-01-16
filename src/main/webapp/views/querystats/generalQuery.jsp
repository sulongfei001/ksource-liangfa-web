<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 <title>自定义案件查询</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
	function back(){
		window.location.href = "${path}/query/general/back";	
	}	
</script>
</head>
<body>
<div class="panel">
<br />
<display:table name="caseList" uid="case" cellpadding="0"
	cellspacing="0" requestURI="${path }/query/general/query">
	<display:caption class="tooltar_btn">
		<input type="button" class="btn_small" value="返 回" onclick="back();"/>
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
		<a href="javascript:;" onclick="top.showCaseProcInfo('${case.caseId}');">${case.caseNo }</a>
	</display:column>
		<display:column title="案件名称" style="text-align:left;width:20%" class="cutout">
			<span title="${case.caseName}">${fn:substring(case.caseName,0,20)}${fn:length(case.caseName)>20?'...':''}</span>
		</display:column>
		<display:column title="录入单位" property="orgName" style="text-align:center;">
		</display:column>
		<display:column title="录入时间" style="text-align:center;">
			<fmt:formatDate value="${case.inputTime}" pattern="yyyy-MM-dd HH:mm"/>
		</display:column>
		<display:column title="状态" style="text-align:center;">
			<dict:getDictionary var="stateVar" groupCode="${procKey}State"
				dicCode="${case.caseState }" />
		${stateVar.dtName }
		</display:column>
		<display:column title="涉案金额<font color=\"red\">(元)</font>" style="text-align:right;">
			<c:choose>
				<c:when test="${!empty case.amountInvolved}">
					<fmt:formatNumber value="${case.amountInvolved}" pattern="#,##0.00#"/>
				</c:when>
				<c:otherwise>
					<fmt:formatNumber value="00.00"  pattern="#,#00.00#" />
				</c:otherwise>
			</c:choose>
		</display:column> 
</display:table>
</div>
</body>
</html>