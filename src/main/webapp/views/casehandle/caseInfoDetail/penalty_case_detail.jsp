<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.js"></script>
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
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/caseParty-min.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/caseCompany-min.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/autoresize.jquery.min.js"></script>

<style type="text/css">
#outDiv {
	position: relative;
	margin: 6px;
}
</style>
</head>
<body>
	<div class="panel">
		<fieldset class="fieldset">
			<legend class="legend">行政违法案件明细</legend>
			<table class="blues" style="width: 98%; margin-left: 10px;">
				<tr>
					<td width="20%" align="right" class="tabRight">案件编号：</td>
					<td width="30%" style="text-align: left;">${caseBasic.caseNo}</td>
					<td width="20%" class="tabRight">案件名称：</td>
					<td width="30%" style="text-align: left;">${caseBasic.caseName }</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">涉案金额（元）：</td>
					<td width="30%" style="text-align: left;"><fmt:formatNumber value="${caseBasic.amountInvolved }" pattern="000.##"></fmt:formatNumber>
					</td>
					<td width="20%" align="right" class="tabRight">案件来源：</td>
					<td width="30%" style="text-align: left;">
					<dict:getDictionary var="recordSrc" groupCode="caseSource" dicCode="${caseBasic.recordSrc}"/>
                    ${recordSrc.dtName}
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">移送单位：</td>
					<td width="30%" style="text-align: left;">${caseBasic.yisongUnit }</td>
					<td width="20%" align="right" class="tabRight">受案单位：</td>
					<td width="30%" style="text-align: left;">${caseBasic.acceptUnit }</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">移送时间：</td>
					<td width="30%" style="text-align: left;">
						<fmt:formatDate value="${caseBasic.yisongTime }" pattern="yyyy-MM-dd" />
					</td>
					<td width="20%" align="right" class="tabRight">案发区域：</td>
					<td width="30%" style="text-align: left;">${penaltyCaseExt.anfaAddress }</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight" >行政处罚案件承办人：</td>
					<td width="30%" style="text-align: left;">${penaltyCaseExt.undertaker }</td>
					<td width="20%" align="right" class="tabRight">案发时间：</td>
					<td width="30%" style="text-align: left;"><fmt:formatDate value="${penaltyCaseExt.anfaTime }" pattern="yyyy-MM-dd" /></td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">批准报送负责人：</td>
					<td width="30%"  style="text-align: left;" >${penaltyCaseExt.submitPeople } </td>
					<td width="20%" align="right" class="tabRight">批准报送日期：</td>
					<td width="30%" style="text-align: left;" ><fmt:formatDate value='${penaltyCaseExt.submitTime }' pattern='yyyy-MM-dd' /></td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">是否经过负责人集体讨论：</td>
					<td width="30%" style="text-align: left;">
					<c:if test="${penaltyCaseExt.isDescuss == 1 }">
							<label for="isDescuss1" style="margin-right: 20%;">是</label>
					</c:if> 
					<c:if test="${penaltyCaseExt.isDescuss == 0 }">
							<label for="isDescuss0" style="margin-right: 5%;">否</label>
					</c:if>
					</td>
					<td width="20%" align="right" class="tabRight">行政处罚文书号：</td>
					<td width="30%" style="text-align: left;">${penaltyCaseExt.penaltyFileNo }</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">行政处罚决定书：</td>
					<td width="80%" style="text-align: left;" colspan="3" id="files">
						<span id="penaltyFile_span"> 
						<a href="${path}/download/caseAtta/${penaltyFile.id }">${penaltyFile.attachmentName}</a>
					</span>
					</td>
				</tr>
                <c:if test="${!empty otherFile}">
                    <tr>
                        <td width="20%" align="right" class="tabRight">其它材料：</td>
                        <td width="80%" style="text-align: left;" colspan="3">
                            <span id="otherFile_span">
                            <a href="${path}/download/caseAtta/${otherFile.id }">${otherFile.attachmentName}</a>
                        </span>
                        </td>
                    </tr>
                </c:if>
				<tr>
					<td width="20%" align="right" class="tabRight">案情简介：</td>
					<td width="30%" style="text-align: left" colspan="3">${caseBasic.caseDetailName }
					</td>
				</tr>
			</table>
			<br />
			<c:if test="${casePartys != null }">	      
			<fieldset class="fieldset" id="casePartyFieldset"
				style="width: 98%; margin-left: 10px;">
				<legend class="legend">案件当事人信息</legend>
				<c:forEach items="${casePartys }" var="caseParty"  >
				<table id="showCasePartyTable" class="blues"
					style="display: none; margin: 10px; width: 500px; float: left;">
					<tr>
						<td class="tabRight">身份证号</td>
						<td style="text-align: left;"><span name="idsNo" >${caseParty.idsNo }</span></td>
						<td class="tabRight">姓名</td>
						<td style="text-align: left;"><span name="name">${caseParty.name }</span></td>
					</tr>
					<tr>
						<td class="tabRight">性别</td>
						<td style="text-align: left;"><span name="sex">${caseParty.sex }</span></td>
						<td class="tabRight">文化程度</td>
						<td style="text-align: left;"><span name="education">${caseParty.education }</span></td>
					</tr>
					<tr>
						<td class="tabRight">民族</td>
						<td style="text-align: left;"><span name="nation">${caseParty.nation }</span></td>
						<td class="tabRight">籍贯</td>
						<td style="text-align: left;"><span name="birthplace">${caseParty.birthplace }</span></td>
					</tr>
					<tr>
						<td class="tabRight">工作单位和职务</td>
						<td style="text-align: left;"><span name="profession">${caseParty.profession }</span></td>
						<td class="tabRight">联系电话</td>
						<td style="text-align: left;"><span name="tel">${caseParty.tel }</span></td>
					</tr>
					<tr>
						<td class="tabRight">出生日期</td>
						<td style="text-align: left;"><span name="birthday"><fmt:formatDate value='${caseParty.birthday }' pattern='yyyy-MM-dd' /></span></td>
						<td class="tabRight">住址</td>
						<td style="text-align: left;"><span name="address">${caseParty.address }</span></td>
					</tr>
				</table>
				</c:forEach>
			</fieldset>
			</c:if>
			<c:if test="${caseCompanys != null }">
			<fieldset class="fieldset" id="caseCompanyFieldset"
				style="width: 98%; margin-left: 10px;">
				<legend class="legend">案件单位信息</legend>
				<c:forEach items="${caseCompanys }" var="caseCompany"></c:forEach>   
				<table id="showCaseCompanyTable" class="blues"
					style="display: none; margin: 10px; width: 500px; float: left;">
					<tr>
						<td class="tabRight">单位名称</td>
						<td style="text-align: left;"><span name="name">${caseCompany.name }</span></td>
						<td class="tabRight">单位类型</td>
						<td style="text-align: left;"><span name="companyType">${caseCompany.companyType }</span></td>

						</td>
					</tr>
					<tr>
						<td class="tabRight">登记证号</td>
						<td style="text-align: left;"><span name="registractionNum">${caseCompany.registractionNum }</span></td>
							<td class="tabRight">负责人</td>
							<td style="text-align: left;"><span name="proxy">${caseCompany.proxy }</span></td>
					</tr>
					<tr>
						<td class="tabRight">注册地</td>
						<td style="text-align: left;"><span name="address">${caseCompany.address }</span></td>

						<td class="tabRight">注册资金</td>
						<td style="text-align: left;"><span
							name="registractionCapital">${caseCompany.registractionCapital }</span> &nbsp;<font color="red">(万元)</font></td>

					</tr>
					<tr>
						<td class="tabRight">注册时间</td>
						<td style="text-align: left;"><span name="registractionTime"><fmt:formatDate value='${caseCompany.registractionTime }' pattern='yyyy-MM-dd' /></span>
						</td>
						<td class="tabRight">联系电话</td>
						<td style="text-align: left;"><span name="tel">${caseCompany.tel }</span></td>
					</tr>
				</table>
			</fieldset>
			</c:if>
	</fieldset>
	</div>
</body>
</html>