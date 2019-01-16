<%@ page contentType="text/html;charset=UTF-8" language="java" %>  
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>行政执法与刑事司法信息共享平台</title>
<link rel="stylesheet" href="${path }/resources/app/app.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
		$('.icon_down').click(function(){
			$(this).parent().siblings('.this_content').toggle();
			if($(this).hasClass('icon_down')){
				$(this).removeClass('icon_down');
				$(this).addClass('icon_up');
			}else{
				$(this).removeClass('icon_up');
				$(this).addClass('icon_down');
			}
		})
	})  
</script>
</head>
<body>
<div class='mt10 bgcw'>
    <p class="top_text">
    	<span>办理人：</span>
    	<span>${caseInfo.transactPerson }</span></p>
    <p class="top_text">
    	<span>办理时间：</span>
    	<span><fmt:formatDate value="${caseInfo.transactTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/></span>
    </p>
</div>
<table class='bgcw mt10'>
    <tr>
		<td class="tabRight">案件编号：</td>
		<td class="tabcontent" >${caseInfo.caseNo}</td>
    </tr>
    <tr>
		<td class="tabRight">案件名称：</td>
		<td class="tabcontent" >${caseInfo.caseName}</td>
    </tr>
    <tr>
        <td class="tabRight">案件来源：</td>
        <td class="tabcontent" >
        <dic:getDictionary var="caseSourceVar" groupCode="caseSource" dicCode="${caseInfo.recordSrc}"/>
        ${caseSourceVar.dtName }
         </td>
    </tr>
    <tr>
    	<td  class="tabRight">案发区域：</td>
        <td  class="tabcontent">${caseInfo.anfaAddressName }</td>
    </tr>
    <tr>
		<td class="tabRight">案发时间：</td>
		<td class="tabcontent" >
			 <fmt:formatDate value="${caseInfo.anfaTime}" pattern="yyyy-MM-dd"/>
		</td>
    </tr>
</table>
<table  class='bgcw mt10'>
	<tr>
		<td>违法事实</td>
		<td class="icon_down"></td>
	</tr>
	<tr class="this_content" >
		<td colspan="2" >${caseInfo.caseDetailName}</td>
	</tr>
</table>
<table  class="bgcw mt10">
    <tr>
        <td class="tabRight">承办人：</td>
        <td class="tabcontent">${caseInfo.undertaker}</td>
    </tr>
    <tr>
		<td class="tabRight">承办时间：</td>
		<td class="tabcontent">
			 <fmt:formatDate value="${caseInfo.undertakerTime}" pattern="yyyy-MM-dd"/>
		</td>
    </tr>
</table>
<table  class='bgcw mt10'>
	<tr>
		<td>承办人意见</td>
		<td class="icon_down"></td>
	</tr>
	<tr class="this_content">
		<td colspan="2">${caseInfo.undertakerSuggest}</td>
	</tr>
</table>

<c:if test="${!empty CasePartyList }">
    <h3 class="con_name"><span class="mark_line"></span>当事人信息</h3>
    <c:forEach items="${CasePartyList }" var="CaseParty">
        <table class="bgcw mt10" >
            <tr>
                <td class="tabRight">身份证号：</td>
                <td class="tabcontent">${CaseParty.idsNo }</td>
            </tr>
            <tr>
                <td class="tabRight">姓名：</td>
                <td class="tabcontent"><span id="forCasePartyWarn${CaseParty.partyId }">${CaseParty.name}</span></td>
            </tr>
            <tr>
                <td class="tabRight">联系电话：</td>
                <td class="tabcontent">${CaseParty.tel }</td>        
            </tr>
            <tr>
                <td class="tabRight">性别：</td>
                <td class="tabcontent"><dict:getDictionary var="sex" groupCode="sex" dicCode="${CaseParty.sex }"/>${sex.dtName }</td>
            </tr>
            <tr>
                <td class="tabRight">文化程度：</td>
                <td class="tabcontent"><dict:getDictionary  var="education" groupCode="educationLevel" dicCode="${CaseParty.education }"/>${education.dtName }</td>  
            </tr>
            <tr>   
                <td class="tabRight">工作单位和职务：</td>
                <td class="tabcontent">${CaseParty.profession}</td>  
            </tr>
            <tr>                                                                                  
                <td class="tabRight">民族：</td>
                <td class="tabcontent">
                	<dict:getDictionary var="nation" groupCode="nation" dicCode="${CaseParty.nation }"/>
                	${nation.dtName }
                </td>
            </tr>
            <tr>
				<td class="tabRight">户籍地：</td>
				<td class="tabcontent">${CaseParty.residence}</td>
            </tr>
            <tr>
            	<td class="tabRight">籍贯：</td>
                <td class="tabcontent">${CaseParty.birthplace}</td>
            </tr>
			<tr>
				<td class="tabRight">特殊身份：</td>
				<td class="tabcontent"><dict:getDictionary var="specialIdentityVar" groupCode="specialIdentity" dicCode="${CaseParty.specialIdentity }" /> ${specialIdentityVar.dtName}</td>
			</tr>
		    <tr>
				<td class="tabRight">现住址：</td>
				<td class="tabcontent">${CaseParty.address }</td>
			</tr>	            
        </table>
    </c:forEach>
</c:if>		
<c:if test="${!empty caseCompanyList }">
    <h3 class="con_name"><span class="mark_line"></span>单位信息</h3>
    <c:forEach items="${caseCompanyList }" var="caseCompany">
        <table class="bgcw mt10" >
            <tr>
                <td class="tabRight" >统一社会信用代码：</td>
                <td class="tabcontent">${caseCompany.registractionNum }</td>
            </tr> 
            <tr>
                <td class="tabRight" >单位名称：</td>
                <td class="tabcontent">
                	<span id="forCaseCompanyWarn${caseCompany.id}">${caseCompany.name}</span>
                </td>
            </tr> 
            <tr>
                <td class="tabRight">单位性质：</td>
                <td class="tabcontent">
                	<dic:getDictionary var="danweiTypeList" groupCode="danweiType" dicCode="${caseCompany.companyType}"/><span>${danweiTypeList.dtName}</span>
                 </td>
            </tr> 
            <tr>                                                                              
                <td class="tabRight">注册时间：</td>
                <td class="tabcontent">
               		<fmt:formatDate value="${caseCompany.registractionTime}" pattern="yyyy-MM-dd"></fmt:formatDate>
                </td>
            </tr> 
            <tr>
                <td class="tabRight" >法人代表：</td>
                <td class="tabcontent" >${caseCompany.proxy }</td>
            </tr> 
            <tr>
                <td class="tabRight" >联系电话：</td>
                <td class="tabcontent" >${caseCompany.tel }</td>            
            </tr>
 			<tr>
                <td class="tabRight">注册资金：</td>
                <td class="tabcontent"><fmt:formatNumber value="${caseCompany.registractionCapital}" pattern="#,##0.0000#"/>
                    &nbsp;<font color="red">(万元)</font>
                </td>
            </tr> 
            <tr>
                <td class="tabRight">注册地：</td>
                <td class="tabcontent">${caseCompany.address}</td>
            </tr>
        </table>
    </c:forEach>
</c:if>
</body>
</html>