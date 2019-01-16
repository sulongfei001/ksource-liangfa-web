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
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
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
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript">
$(function(){
	function checkChange() {
		if ($.trim($('#theme').val()).length == 1) {
			$.ligerDialog.warn("为更精确的查询请至少输入一组中文词语!");
			return false;
		}
		var value = $('#changeSearch').val();
		$('#theme').attr('name', value);
		return true;
	}
});
</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">市长热线数据检索</legend>
<form:form action="${path }/hotlineInfo/search" method="post" modelAttribute="hotline" onsubmit="return checkChange()">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
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
			<td width="10%" align="right">关键词</td>
			<td style="width: 62%; text-align: left;" colspan="3">
							<input name="content" id="content" class="text" style="width: 70%"
								value="${hotline.content}" />
		<font color="red">*注：多关键词以空格分隔</font></td>
			<td width="20%"  rowspan="3" align="left" valign="middle">
			<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
		<tr>
	</table>
</form:form>
</fieldset>
<!-- 查询结束 -->
<br />
<form:form method="post" action="${path }/hotlineInfo/search">
	<display:table name="hotlineInfoList" uid="hotlineInfo" cellpadding="0"
		cellspacing="0" requestURI="${path }/hotlineInfo/search" decorator="">
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${hotlineInfo_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + hotlineInfo_rowNum}
			</c:if>
		</display:column>
		<display:column title="受理编号" style="text-align:left;">
			<a href="${path }/hotlineInfo/searchDetails?infoId=${hotlineInfo.infoId}&contentType=${hotline.contentType}&content=${hotline.content}">${hotlineInfo.infoNo }</a>
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
		<display:column title="反映类型" style="text-align:center;">
			<dict:getDictionary var="stateVar" groupCode="hotlineType" dicCode="${hotlineInfo.hotlineType }" />
			${stateVar.dtName }
		</display:column>	
		<display:column title="管理">
			<a href="${path}/casehandle/clueInfo/getClueAddViewByHotline.do?infoId=${hotlineInfo.infoId}">添加为线索</a>
		</display:column>
	</display:table>
</form:form>
</div>
</body>
</html>