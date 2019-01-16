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
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"   ></script> 
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<title>案件单位查询</title>
<script type="text/javascript">
	$(function() {
		var startTime = document.getElementById('startTime');
		startTime.onfocus = function(){
	      WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		var endTime = document.getElementById('endTime');
		endTime.onfocus = function(){
	      WdatePicker({dateFmt:'yyyy-MM-dd'});
		}  
	});
	function clearAll() {
		$("input[type='text']").each(function() {
			$(this).val("");
		});
	}
	
	function showCaseByCompany(registractionNum,name){
 		top.addTab(registractionNum,name+"的相关案件", '${path}/query/companyQuery/showCaseByCompany?registractionNum='+registractionNum);
	}
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">案件涉案单位查询</legend>
	<form:form action="${path}/query/companyQuery/search" method="post">
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td width="10%" align="right">统一社会信用代码</td>
				<td width="20%" align="left"><input type="text" name="registractionNum"
					value="${param.registractionNum}" class="text" /></td>
				<td width="10%" align="right">单位名称</td>
				<td width="20%" align="left"><input type="text" name="name"
					value="${param.name}" class="text" />
				</td>
				<td width="10%" align="right">负责人</td>
				<td width="20%" align="left"><input type="text" name="proxy"
					value="${param.proxy}" class="text" /></td>
				<td width="30%" align="left" rowspan="2" valign="middle">
				<input type="submit" value="查 询" class="btn_query" />
				</td>		
			</tr>
			<tr>
				<td width="10%" align="right">注册时间</td>
				<td width="20%" align="left"><input type="text" class="text"
					name="startTime" id="startTime" value="${param.startTime }"
					style="width: 32%" /> 到 <input type="text" name="endTime" class="text"
					id="endTime" value="${param.endTime }" style="width: 32%" />
				</td>
				<td align="right">&nbsp;</td>
			</tr>
		</table>
	</form:form>
	</fieldset>
	<br />
	<display:table name="companyList" uid="company" cellpadding="0"
		cellspacing="0" requestURI="${path}/query/companyQuery/search">
		<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${company_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + company_rowNum}
			</c:if>
		</display:column>
		<display:column title="统一社会信用代码" property="registractionNum" style="text-align:left;" />
		<display:column title="单位名称" property="name" style="text-align:left;"/>
		<display:column title="负责人" property="proxy" style="text-align:center;" />
		<display:column title="单位类型" style="text-align:center;">
			<dic:getDictionary var="danweiTypeVar" groupCode="danweiType" 
			dicCode="${company.companyType }"/>${danweiTypeVar.dtName }
		</display:column>
		<display:column title="注册时间" style="text-align:center;">
			<fmt:formatDate value="${company.registractionTime}" pattern="yyyy-MM-dd"/> 
		</display:column>
		<display:column title="注册资金<font color=\"red\">(万元)</font>" style="text-align:right;">
			<fmt:formatNumber value="${company.registractionCapital}" pattern="#,##0.0000#"/>
		</display:column>
		<display:column title="相关案件">
			<a style="text-decoration:none;" href="javascript:showCaseByCompany('${company.registractionNum}','${company.name}')">
			相关案件（${company.caseNum}）</a>
		</display:column>
	</display:table>
	</div>
</body>
</html>