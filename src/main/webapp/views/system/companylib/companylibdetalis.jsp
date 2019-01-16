<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"
	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"
	type="text/javascript"></script>
<script
	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>

</head>
<body>
	<!-- 企业库详细信息显示-->
	</br>
	<table align="center" id="peopleLibDetalisTable" class="blues"
		style="width: 600px">
		<tr>
			<td class="tabRight">统一社会信用代码</td>
			<td style="text-align: left">${company.registractionNum}</td>
			<td class="tabRight">企业名称</td>
			<td style="text-align: left">${company.name}</td>
		</tr>
		<tr>
			<td class="tabRight">法人代表</td>
			<td style="text-align: left">${company.proxy}</td>
			<td class="tabRight">注册地</td>
			<td style="text-align: left">${company.address}</td>
		</tr>
		<tr>
			<td class="tabRight">注册资金</td>
			<td style="text-align: left"><fmt:formatNumber value="${company.registractionCapital}" pattern="#,##0.0000#"/>
				&nbsp;<font color="red">(万元)</font>
			</td>
			<td class="tabRight">企业性质</td>
			<td style="text-align: left">
				<dict:getDictionary var="danweiTypeVar" groupCode="danweiType" 
				dicCode="${company.companyType }"/>${danweiTypeVar.dtName }
			</td>
		</tr>
		<tr>
			<td class="tabRight">注册时间</td>
			<td style="text-align: left" id=><fmt:formatDate
					value="${company.registractionTime}" pattern="yyyy-MM-dd" /></td>
			<td class="tabRight">联系电话</td>
			<td style="text-align: left">${company.tel}</td>
		</tr>
		<tr>
			<td class="tabRight">录入人</td>
			<td style="text-align: left">${company.inputeName}</td>
			<td class="tabRight">录入时间</td>
			<td style="text-align: left"><fmt:formatDate
					value="${company.inputTime}" pattern="yyyy-MM-dd" /></td>
		</tr>
	</table>
	<c:if test="${not empty caselist}">
		<p>&nbsp;&nbsp;此企业历史案件</p>
		<table align="center" class="blues" id="peopleLibDetalisTable"
			style="width: 600px">
			<c:forEach var="case" items="${caselist}">
				<tr>
					<td class="tabRight">案件编号</td>
					<td style="text-align: left"><a href="javascript:void();"
						onclick="top.showCaseProcInfo('${case.caseId}',null,'${case.procKey}')">${case.caseNo}</a></td>
					<td class="tabRight">案件名称</td>
					<td style="text-align: left">${case.caseName}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>