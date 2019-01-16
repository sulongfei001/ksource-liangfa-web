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
	</br>
	<!-- 个人库详细信息显示-->
	<table align="center" class="blues" id="peopleLibDetalisTable"
		style="width: 600px">
		<tr>
			<td class="tabRight">身份证号</td>
			<td style="text-align: left">${people.idsNo}</td>
			<td class="tabRight">姓名</td>
			<td style="text-align: left">${people.name}</td>
		</tr>
		<tr>
			<td class="tabRight">性别</td>
			<td style="text-align: left"><dict:getDictionary var="sexVar"
					groupCode="sex" dicCode="${people.sex }" />${sexVar.dtName }</td>
			<td class="tabRight">文化程度</td>
			<td style="text-align: left"><dict:getDictionary
					var="educationVar" groupCode="educationLevel"
					dicCode="${people.education }" />${educationVar.dtName }</td>
		</tr>
		<tr>
			<td class="tabRight">民族</td>
			<td style="text-align: left">
				<dict:getDictionary
					var="nationVar" groupCode="nation"
					dicCode="${people.nation }" />${nationVar.dtName }
			<%-- ${people.nation}--%>
			</td>
			<td class="tabRight">籍贯</td>
			<td style="text-align: left">${people.birthplace}</td>
		</tr>
		<tr>
			<td class="tabRight">工作单位和职务</td>
			<td style="text-align: left">${people.profession}</td>
			<td class="tabRight">联系电话</td>
			<td style="text-align: left">${people.tel}</td>
		</tr>
		<tr>
			<td class="tabRight">出生日期</td>
			<td style="text-align: left"><fmt:formatDate
					value="${people.birthday}" pattern="yyyy-MM-dd" /></td>
			<td class="tabRight">住址</td>
			<td style="text-align: left">${people.address}</td>
		</tr>
		<tr>
			<td class="tabRight">录入人</td>
			<td style="text-align: left">${people.inputeName}</td>
			<td class="tabRight">录入时间</td>
			<td style="text-align: left"><fmt:formatDate
					value="${people.inputTime}" pattern="yyyy-MM-dd" /></td>
		</tr>
	</table>
	<c:if test="${not empty caselist}">
		<p>&nbsp;&nbsp;此人历史案件</p>
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