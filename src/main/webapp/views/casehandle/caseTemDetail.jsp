<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/displaytagall.css"/>"/>
    <link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
          type="text/css"/>
    <script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
    <script src="<c:url value="/resources/jquery/jquery-ui-1.8.12.custom.min.js"/>"></script>
    <script src="<c:url value="/resources/script/jqueryUtil.js"/>"></script>
    <script src="<c:url value="/resources/script/FormBuilder.js"/>"></script>
    <script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
    <script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
    <script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
    <script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>
    </head>
<body>
<div class="panel">
<fieldset class="fieldset">
<legend class="legend">导入案件明细信息</legend>
<h3>>>案件基本信息</h3>
<c:if test="${caseInfo.caseType != 3 }">
<table class="blues" style="width: 80%;margin-left: 10px;border-bottom: none;">
    <tr>
		<td width="20%" align="right" class="tabRight">行政处罚决定书文号：</td>
		<td width="80%" style="text-align: left;" colspan="3">${caseInfo.penaltyFileNo}</td>
    </tr>
    <tr>
		<td width="20%" class="tabRight">行政处罚案件名称：</td>
		<td width="80%" style="text-align: left;" colspan="3">${caseInfo.caseName}</td>
    </tr>
    <tr>
        <td width="20%" class="tabRight">涉案金额：</td>
        <td width="30%" style="text-align: left;"><fmt:formatNumber value="${caseInfo.amountInvolved}" pattern="#,##0.00#"/>
            &nbsp;<font
                    color="red">(元)</font></td>
        <td width="20%" class="tabRight">案件来源：</td>
        <td width="30%" style="text-align: left;">            
        <dic:getDictionary var="caseSourceVar" groupCode="caseSource" dicCode="${caseInfo.recordSrc}"/>
        ${caseSourceVar.dtName }
         </td>
    </tr>
    <tr>
    	<td  width="20%" class="tabRight">案发区域：</td>
        <td  width="30%"style="text-align: left;" colspan="3">${caseInfo.anfaAddress}</td>
    </tr>
    <tr>
 		<td width="20%" align="right" class="tabRight">行政处罚次数：</td>
		<td width="30%" style="text-align: left;">${caseInfo.chufaTimes}</td>
		<td width="20%" align="right" class="tabRight">是否经过负责人集体讨论：</td>
		<td width="30%" style="text-align: left;">
			<c:if test="${caseInfo.isDescuss ==1}">是</c:if>
			<c:if test="${caseInfo.isDescuss ==2}">否</c:if>
		</td>
    </tr>
    <tr>
        <td width="20%" align="right" class="tabRight">涉案金额达到刑事追诉标准80%以上：</td>
        <td width="30%" style="text-align: left;" id="files">
           <c:if test="${caseInfo.isBeyondEighty == 1}">是</c:if>
           <c:if test="${caseInfo.isBeyondEighty == 2}">否</c:if>
        <td width="20%" align="right" class="tabRight">情节是否严重：</td>
		<td width="30%" style="text-align: left;">
			<c:if test="${caseInfo.isSeriousCase == 1}">是</c:if>
			<c:if test="${caseInfo.isSeriousCase == 2}">否</c:if>
		</td>
      </tr>
</table>
</c:if>
<table class="blues" style="width: 80%;margin-left: 10px;margin-top: 0px;border-top: none;">
	<tr style="border-top: none;">
		<td width="20%" align="right" class="tabRight">案情简介：</td>
		<td width="80%" style="text-align: left">${caseInfo.caseDetailName }</td>
	</tr>
</table>

<c:if test="${!empty CasePartyList }">
    <h3 style="margin-top: 10px;">>>当事人信息</h3>
    <c:forEach items="${CasePartyList }" var="CaseParty">
        <table class="blues" style="width: 80%;margin-left: 10px;">
            <tr>
                <td class="tabRight" style="width: 14%;">身份证号：</td>
                <td style="text-align: left;width: 19%;">${CaseParty.idsNo }</td>
                <td class="tabRight" style="width: 14%;">姓名：</td>
                <td style="text-align: left;width: 19%;"><span
                        id="forCasePartyWarn${CaseParty.partyId }">${CaseParty.name
                        }</span></td>
                <td class="tabRight" style="width: 14%;">联系电话：</td>
                <td style="text-align: left;width: 19%;">${CaseParty.tel }</td>        
            </tr>
            <tr>
                        
                <td class="tabRight">性别：</td>
                <td style="text-align: left;"><dict:getDictionary 
                		var="sex"
                        groupCode="sex"
                        dicCode="${CaseParty.sex }"/>${sex.dtName }</td>
                <td class="tabRight">文化程度：</td>
                <td style="text-align: left;"><dict:getDictionary
                        var="education" groupCode="educationLevel"
                        dicCode="${CaseParty.education }"/>${education.dtName }</td>  
                <td class="tabRight">工作单位和职务：</td>
                <td style="text-align: left;">${CaseParty.profession}</td>  
            </tr>                      
            <tr>                                                                                  
                <td class="tabRight">民族：</td>
                <td style="text-align: left;"><dict:getDictionary var="nation"
                                                                  groupCode="nation"
                                                                  dicCode="${CaseParty.nation }"/>${nation.dtName }</td>
                <td class="tabRight">出生日期：</td>
                <td style="text-align: left;"><fmt:formatDate
                        value="${CaseParty.birthday }" pattern="yyyy-MM-dd"/></td>
                <td class="tabRight">籍贯：</td>
                <td colspan="5" style="text-align: left;">${CaseParty.birthplace}</td>
            </tr>
        </table>
    </c:forEach>
</c:if>
<c:if test="${!empty caseCompanyList }">
    <h3 style="margin-top: 10px;">>>单位信息</h3>
    <c:forEach items="${caseCompanyList }" var="caseCompany">
        <table class="blues" style="width: 80%;margin-left: 10px;">
            <tr>
                <td class="tabRight" style="width: 14%;">单位名称：</td>
                <td style="text-align: left;width: 19%;"><span
                        id="forCaseCompanyWarn${caseCompany.id}">${caseCompany.name
                        }</span>
                </td>
                <td class="tabRight" style="width: 14%;">单位类型：</td>
                <td style="text-align: left;width: 19%;"><dic:getDictionary
                        var="danweiTypeList" groupCode="danweiType"
                        dicCode="${caseCompany.companyType}"/><span>${danweiTypeList.dtName}</span>
                 </td>
                <td class="tabRight" style="width: 14%;">注册时间：</td>
                <td style="text-align: left;width: 19%;"><fmt:formatDate value="${caseCompany.registractionTime}"
                                                              pattern="yyyy-MM-dd"></fmt:formatDate></td>
            </tr> 
            <tr>                                                                              
                <td class="tabRight" style="width: 14%;">登记证号：</td>
                <td style="text-align: left;width: 19%;">${caseCompany.registractionNum }</td>
                <td class="tabRight" style="width: 14%;">法人代表：</td>
                <td style="text-align: left;width: 19%;">${caseCompany.proxy }</td>
                <td class="tabRight" style="width: 14%;">联系电话：</td>
                <td style="text-align: left;width: 19%;">${caseCompany.tel }</td>            
            </tr>
 			<tr>
                <td class="tabRight">注册资金：</td>
                <td style="text-align: left;"><fmt:formatNumber value="${caseCompany.registractionCapital}" pattern="#,##0.0000#"/>
                    &nbsp;<font color="red">(万元)</font>
                </td>
                <td class="tabRight">注册地：</td>
                <td style="text-align: left;" colspan="3">${caseCompany.address
                        }</td>
            </tr>
        </table>
    </c:forEach>
</c:if>
</fieldset>
</div>
</body>
</html>