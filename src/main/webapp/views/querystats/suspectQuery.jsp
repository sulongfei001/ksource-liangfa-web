<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.textbox-hinter.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"   ></script> 
<title>嫌疑人查询</title>
<style type="text/css">
	.greyText{
		color: #B8B8B8;
	}
</style>
<script type="text/javascript">
	String.prototype.trim = function() {
		return $.trim(this);
	};
	$(function() {
		$("#btnSearch").unbind("click");
		$("#btnSearch").bind("click",function(){
			$("#searchForm").submit();
		});
	});
	
	function showCaseByXianyiren(idNo,name){
		top.addTab(idNo,name+'的相关案件','${path}/query/suspectQuery/showCaseByXianyiren?idsNo='+idNo);
	}
	
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset"><legend class="legend">案件涉案嫌疑人查询</legend>
	<form id="searchForm" action="${path}/query/suspectQuery/search" method="post">
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td width="10%" align="right">身份证号</td>
				<td width="20%" align="left">
					<input name="idsNo" id="idsNo" class="text" value="${param.idsNo}"/>
				</td>
				<td width="10%" align="right">姓名</td>
				<td width="20%"  align="left">
				<input name="name" id="name" class="text" value="${param.name}"/>
				</td>
				<td width="8%" align="right">性别</td>
				<td width="20%" align="left">
				<dic:getDictionary var="sexList"groupCode="sex" /> 
					<select name="sex">
						<option value="">--全部--</option>
						<c:forEach items="${sexList}" var="domain">
							<option value="${domain.dtCode}" <c:if test="${param.sex==domain.dtCode}">selected</c:if> >${domain.dtName}</option>
						</c:forEach>
					</select></td>
				<td width="14%" align="left" rowspan="3" valign="middle">
				<input type="submit" id="btnSearch" value="查 询" class="btn_query" />
			</td>		
			</tr>
			<tr>
				<td width="8%" align="right">民族</td>
				<td width="20%" align="left">
					<dic:getDictionary var="nationList" groupCode="nation" /> 
					<select name="nation" style="width:77%;">
						<option value="">--全部--</option>
						<c:forEach items="${nationList}" var="domain">
							<option value="${domain.dtCode}" <c:if test="${param.nation==domain.dtCode}">selected</c:if> >${domain.dtName}</option>
						</c:forEach>
					</select>
				</td>
				<td width="8%" align="right">文化程度</td>
				<td width="20%" align="left">
					<dic:getDictionary var="educationLevelList" groupCode="educationLevel" /> 
					<select name="education" style="width:77%;">
						<option value="">--全部--</option>
						<c:forEach items="${educationLevelList}" var="domain">
							<option value="${domain.dtCode}" <c:if test="${param.education==domain.dtCode}">selected</c:if> >${domain.dtName}</option>
						</c:forEach>
					</select>
				</td>
				<td align="right"> &nbsp;</td>
			</tr>
		</table>
	</form>
	</fieldset>
	<display:table name="xianyirenList" uid="xianyiren" cellpadding="0"
		cellspacing="0" requestURI="${path}/query/suspectQuery/search">
		<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${xianyiren_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + xianyiren_rowNum}
			</c:if>
		</display:column>
		<display:column title="身份证号" property="idsNo" style="text-align:left;"></display:column>
		<display:column title="姓名" property="name" style="text-align:center;"></display:column>
		<display:column title="性别" style="text-align:center;">
			<dict:getDictionary var="domain" groupCode="sex"
				dicCode="${xianyiren.sex}" />
			${domain.dtName}
		</display:column>
		<display:column title="民族" style="text-align:center;">
			<dict:getDictionary var="domain" groupCode="nation" dicCode="${xianyiren.nation}" />
			${domain.dtName}
		</display:column>
		<display:column title="文化程度" style="text-align:center;">
			<dict:getDictionary var="domain" groupCode="educationLevel" dicCode="${xianyiren.education}" />
			${domain.dtName}
		</display:column>
		<display:column title="职业" property="profession" style="text-align:center;"></display:column>
		<display:column title="相关案件">
			<a style="text-decoration:none;" href="javascript:showCaseByXianyiren('${xianyiren.idsNo}','${xianyiren.name}')">
			相关案件（${xianyiren.caseNum}）</a>
		</display:column>
	</display:table>
	</div>
</body>
</html>