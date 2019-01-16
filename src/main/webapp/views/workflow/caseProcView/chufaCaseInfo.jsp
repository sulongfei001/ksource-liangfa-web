<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="dic" uri="dictionary" %>
<p style="padding: 5px; background-color: #c6daef; text-indent: 3em;color: 0156a9;margin-bottom: 8px;font-size: 13px;">
    <b>办理人：</b>${caseStep.assignPersonName }(${caseStep.assignPersonOrgName })&nbsp;&nbsp;&nbsp;
    <b>办理时间：</b>
    <fmt:formatDate value="${caseStep.endDate}"
                    pattern="yyyy年MM月dd日 HH:mm:ss"/>
</p>

<h3>>>行政受理</h3>
<table class="blues" style="width: 96%;margin-left: 10px;">
    <tr>
		<td width="20%" class="tabRight">案件编号：</td>
		<td width="80%" style="text-align: left;" colspan="3">${caseInfo.caseNo}</td>
    </tr>
    <tr>
		<td width="20%" class="tabRight">案件名称：</td>
		<td width="80%" style="text-align: left;" colspan="3">${caseInfo.caseName}</td>
    </tr>
    <tr>
        <td width="20%" class="tabRight">案件来源：</td>
        <td width="30%" style="text-align: left;" colspan="3">
        <dic:getDictionary var="caseSourceVar" groupCode="caseSource" dicCode="${caseInfo.recordSrc}"/>
        ${caseSourceVar.dtName }
         </td>
    </tr>
    <tr>
    	<td  width="20%" class="tabRight">案发区域：</td>
        <td  width="30%"style="text-align: left;">${caseInfo.anfaAddressName }</td>
    	<td  width="20%" class="tabRight">案发时间：</td>
        <td  width="30%"style="text-align: left;">
        <fmt:formatDate value="${caseInfo.anfaTime}" pattern="yyyy-MM-dd"/>
        </td>
    </tr>
	<tr>
		<td width="20%" class="tabRight">违法事实：</td>
		<td width="80%" style="text-align: left" colspan="3">${caseInfo.caseDetailName}</td>
	</tr>
    <tr>
        <td width="20%" class="tabRight">承办人：</td>
        <td width="30%" style="text-align: left;">${caseInfo.undertaker}</td>
        <td width="20%" class="tabRight">承办时间：</td>
        <td width="30%" style="text-align: left;">    
        <fmt:formatDate value="${caseInfo.undertakerTime}" pattern="yyyy-MM-dd"/>     
         </td>
    </tr>
	<tr>
		<td width="20%" class="tabRight">承办人意见：</td>
		<td width="80%" style="text-align: left" colspan="3">${caseInfo.undertakerSuggest}</td>
	</tr>
   	<!-- 当前登录用户为检察院时，展示疑似罪名信息 -->
   	<c:if test="${currentOrgType==2}">
   		<c:if test="${caseInfo.accuseRuleNames!=null and caseInfo.accuseRuleNames!=''}">
    		<c:if test="${caseInfo.accuseNameStr!=null and caseInfo.accuseNameStr!='' }">
    			<tr>
			    	<td width="20%" class="tabRight">疑似罪名：</td>
			        <td width="80%" style="text-align: left;color: red;" colspan="3">
			            ${caseInfo.accuseNameStr }
			    	</td>
	    		</tr>
    		</c:if>
   		</c:if>
   	</c:if>
</table>

<c:if test="${!empty CasePartyList }">
     <h3 style="margin-top: 10px;">>>当事人信息</h3>
    <c:forEach items="${CasePartyList }" var="CaseParty">
        <table class="blues" style="width: 96%;margin-left: 10px;">
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
				<td class="tabRight">户籍地：</td>
				<td style="text-align: left">${CaseParty.residence}</td>
                <td class="tabRight">籍贯：</td>
                <td style="text-align: left;">${CaseParty.birthplace}</td>
            </tr>
			<tr>
				<td class="tabRight">特殊身份：</td>
				<td style="text-align: left">
				<dict:getDictionary var="specialIdentityVar" groupCode="specialIdentity" dicCode="${CaseParty.specialIdentity }" /> 
				${specialIdentityVar.dtName}
				</td>
				<td class="tabRight">现住址：</td>
				<td style="text-align: left" colspan="3">
				${CaseParty.address }
				</td>
			</tr>	            
        </table>
    </c:forEach>
</c:if>		
<c:if test="${!empty caseCompanyList }">
    <h3 style="margin-top: 10px;">>>单位信息</h3>
    <c:forEach items="${caseCompanyList }" var="caseCompany">
        <table class="blues" style="width: 96%;margin-left: 10px;">
            <tr>
                <td class="tabRight" style="width: 14%;">统一社会信用代码：</td>
                <td style="text-align: left;width: 19%;">${caseCompany.registractionNum }</td>
                <td class="tabRight" style="width: 14%;">单位名称：</td>
                <td style="text-align: left;width: 19%;"><span
                        id="forCaseCompanyWarn${caseCompany.id}">${caseCompany.name
                        }</span>
                </td>
                <td class="tabRight" style="width: 14%;">单位性质：</td>
                <td style="text-align: left;width: 19%;"><dic:getDictionary
                        var="danweiTypeList" groupCode="danweiType"
                        dicCode="${caseCompany.companyType}"/><span>${danweiTypeList.dtName}</span>
                 </td>
            </tr> 
            <tr>                                                                              
                <td class="tabRight" style="width: 14%;">注册时间：</td>
                <td style="text-align: left;width: 19%;"><fmt:formatDate value="${caseCompany.registractionTime}"
                                                              pattern="yyyy-MM-dd"></fmt:formatDate></td>
                <td class="tabRight" style="width: 14%;">法人代表：</td>
                <td style="text-align: left;width: 19%;">${caseCompany.proxy }</td>
                <td class="tabRight" style="width: 14%;">联系电话：</td>
                <td style="text-align: left;width: 19%;">${caseCompany.tel }</td>            
            </tr>
 			<tr>
                <td class="tabRight">注册资金：</td>
                <td style="text-align: left;"><fmt:formatNumber value="${caseCompany.registractionCapital}" pattern="#,##0.00#"/>
                    &nbsp;<font color="red">(万元)</font>
                </td>
                <td class="tabRight">注册地：</td>
                <td style="text-align: left;" colspan="3" >${caseCompany.address}</td>
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
                        '&nbsp;<a style="color:red" title="当事人存在历史案件" href="javascript:showCasePartyHistoryCase(',
                        "'${path}','${CaseParty.caseId}','${CaseParty.idsNo}','${CaseParty.name}')",
                        '">${CaseParty.name}</a><img src="${path}/resources/images/infolevel.gif">' ]
                            .join(""));
    </c:forEach>
    //涉案企业预警信息
    <c:forEach items="${warnCaseCompanyList }" var="CaseCompany">
    $('#forCaseCompanyWarn${CaseCompany.id}')
            .html(
                    [
                        '&nbsp;<a style="color:red" title="涉案企业存在历史案件" href="javascript:showCaseCompanyHistoryCase(',
                        "'${path}','${CaseCompany.caseId}','${CaseCompany.registractionNum}','${CaseCompany.name}')",
                        '">${CaseCompany.name}</a><img src="${path}/resources/images/infolevel.gif">' ]
                            .join(""));
    </c:forEach>

</script>