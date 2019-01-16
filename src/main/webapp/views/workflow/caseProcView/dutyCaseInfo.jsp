<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<p style="padding:5px;background-color:#c6daef;text-indent: 3em;color: 0156a9;margin-bottom: 8px;font-size: 13px;">
		<b>录入人：</b>${caseInfo.inputerName}（${caseInfo.orgName}）&nbsp;&nbsp;&nbsp;
		<b>录入时间：</b><fmt:formatDate value="${caseInfo.inputTime}"  pattern="yyyy年MM月dd日 HH:mm:ss"/>
</p>

<h3>>>案件基本信息</h3>
<table class="blues" style="margin: 10px;width:96%;">
<tr>
	<td class="tabRight">
		案件编号：
	</td>
	<td style="text-align: left;" colspan="3">
		${caseInfo.caseNo}
	</td>
</tr>
<tr>
	<td class="tabRight">
		案件名称：
	</td>
	<td style="text-align: left;" colspan="3">
		${caseInfo.caseName}
	</td>
</tr>
<tr>
	<td class="tabRight">
		案件性质：
	</td>
	<td style="text-align: left;">
		${caseInfo.caseNature }
	</td>
	<td class="tabRight">
		承办人：
	</td>
	<td style="text-align: left;">
		${caseInfo.undertaker }
	</td>
</tr>
<tr>
	<td class="tabRight">
		批准人：
	</td>
	<td style="text-align: left;">
		${caseInfo.approver}
	</td>
	<td class="tabRight">
		承办时间：
	</td>
	<td style="text-align: left;">
		<fmt:formatDate value="${caseInfo.undertakTime}" pattern="yyyy-MM-dd"/>
	</td>
</tr>
<tr>
	<td class="tabRight">
		案件详情附件：
	</td>
	<td  colspan="3" style="text-align: left;">
		<c:if test="${caseInfo.caseDetailFileName==''}">无</c:if>
	<c:if test="${caseInfo.caseDetailFileName!=null && caseInfo.caseDetailFileName!=''}">
		<a href="${path}/download/dutycaseDetail/${caseInfo.caseId}">${caseInfo.caseDetailFileName}</a>
	</c:if>
	</td>
</tr>
<tr>
	<td class="tabRight">
		案件详情：
	</td>
	<td   colspan="3" style="text-align: left;">
		${caseInfo.caseDetailName}
	</td>
</tr>
</table>

<c:if test="${!empty casePartyList }">
	<h3>>>当事人信息</h3>
	<c:forEach items="${casePartyList }" var="CaseParty">
		<table class="blues" style="margin: 10px; width: 96%;">
			<tr>
				<td class="tabRight" style="width: 135px;">身份证号：</td>
				<td style="text-align: left;">${CaseParty.idsNo }</td>
				<td class="tabRight">姓名：</td>
				<td style="text-align: left;"><span
					id="forCasePartyWarn${CaseParty.partyId }">${CaseParty.name
						}</span></td>
				<td class="tabRight" style="width: 100px;">性别：</td>
				<td style="text-align: left;"><dict:getDictionary var="sex"
						groupCode="sex" dicCode="${CaseParty.sex }" />${sex.dtName }</td>
			</tr>
			<tr>
				<td class="tabRight">民族：</td>
				<td style="text-align: left;"><dict:getDictionary var="nation"
						groupCode="nation" dicCode="${CaseParty.nation }" />${nation.dtName }</td>
				<td class="tabRight">出生日期：</td>
				<td style="text-align: left;"><fmt:formatDate
						value="${CaseParty.birthday }" pattern="yyyy-MM-dd" /></td>
				<td class="tabRight">文化程度：</td>
				<td style="text-align: left;"><dict:getDictionary
						var="education" groupCode="educationLevel"
						dicCode="${CaseParty.education }" />${education.dtName }</td>
			</tr>
			<tr>
				<td class="tabRight">工作单位和职务：</td>
				<td colspan="5" style="text-align: left;">${CaseParty.profession
					}</td>
			</tr>
			<tr>
				<td class="tabRight">联系电话：</td>
				<td colspan="5" style="text-align: left;">${CaseParty.tel }</td>
			</tr>
			<tr>
				<td class="tabRight">籍贯：</td>
				<td colspan="5" style="text-align: left;">${CaseParty.birthplace
					}</td>
			</tr>
			<tr>
				<td class="tabRight">住址：</td>
				<td colspan="5" style="text-align: left;">${CaseParty.address }</td>
			</tr>
		</table>
	</c:forEach>
</c:if>
<script type="text/javascript">
	//案件当事人预警信息
	<c:forEach items="${warnCasePartyList }" var="CaseParty">
	$('#forCasePartyWarn${CaseParty.partyId}')
			.html(
					[
							'&nbsp;<a style="color:red" title="当事人存在历史案件" href="javascript:showDutyCasePartyHistoryCase(',
							"'${path}','${CaseParty.zwfzCaseId}','${CaseParty.idsNo}','${CaseParty.name}')",
							'">${CaseParty.name}</a><img src="${path}/resources/images/infolevel.gif">' ]
							.join(""));
	</c:forEach>
</script>