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
    	<span>${penaltyLianForm.transactPerson }</span></p>
    <p class="top_text">
    	<span>办理时间：</span>
    	<span><fmt:formatDate value="${penaltyLianForm.transactTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/></span>
    </p>
</div>
<table class='bgcw mt10'>
	<tr>
		<td class="tabRight">立案号：</td>
		<td class="tabcontent" >${penaltyLianForm.lianNo}</td>
    </tr>
	<tr>
		<td class="tabRight" >案由：</td>
		<td class="tabcontent">
			${penaltyLianForm.memo }
		</td>
	</tr>
	<tr>
		<td class="tabRight" >立案时间：</td>
		<td class="tabcontent">
			<fmt:formatDate value="${penaltyLianForm.undertakeTime}"
                  pattern="yyyy-MM-dd"/>
		</td>
	</tr>
	<tr>
		<td class="tabRight" >涉案金额：</td>
		<td class="tabcontent">
			<fmt:formatNumber value="${penaltyLianForm.amountInvolved}" pattern="#,##0.00#"/>
          		&nbsp;<font color="red">(元)</font>
		</td>
	</tr>
	<tr>
		<td class="tabRight" >承办人：</td>
		<td class="tabcontent">
			${penaltyLianForm.undertaker }
		</td>
	</tr>
	<tr>
		<td class="tabRight" >领导意见：</td>
		<td class="tabcontent">
			${penaltyLianForm.leaderSuggest }
		</td>
	</tr> 
    <c:set var="lianFileKey" value="f${penaltyLianForm.lianFile}"></c:set>
    <c:if test="${!empty attaMap[lianFileKey]}">
	    <tr>
	        <td class="tabRight">立案决定书：</td>
	        <td class="tabcontent">
		        <a href="javascript:;" onclick="app.downloadCaseFile('/download/caseFile?id=${penaltyLianForm.lianFile}','${attaMap[lianFileKey].attachmentName }')">
	                ${attaMap[lianFileKey].attachmentName }
		        </a>
	        </td>
		</tr>
	</c:if>
	<tr>
		<!-- 是否采取行政强制措施(1-否，2-是) -->
		<td class="tabRight">是否采取强制措施：</td>
		<td class="tabcontent">
			<c:if test="${penaltyLianForm.isMeasures == 1 }">否</c:if>
			<c:if test="${penaltyLianForm.isMeasures == 2 }">是</c:if>
		</td>
	</tr>
	   <c:set var="measuresFileKey" value="f${penaltyLianForm.measuresFile}"></c:set>
	    <c:if test="${!empty attaMap[measuresFileKey]}">
	    <tr>
	        <td class="tabRight">强制措施材料：</td>
	        <td class="tabcontent">
                <a href="javascript:;" onclick="app.downloadCaseFile('/download/caseFile?id=${penaltyLianForm.measuresFile}','${attaMap[measuresFileKey].attachmentName }')">
                    ${attaMap[measuresFileKey].attachmentName }
	           </a>
	        </td>
		</tr>
	    </c:if>				
</table>
<table  class='bgcw mt10'>
	<tr>
		<td>案件详情</td>
		<td class="icon_down"></td>
	</tr>
	<tr class="this_content" >
		<td colspan="2"> ${penaltyLianForm.caseDetail}</td>
	</tr>
</table>
<c:if test="${!empty CasePartyList }">
    <h3 class="con_name"><span class="mark_line"></span>当事人信息</h3>
    <c:forEach items="${CasePartyList }" var="caseParty">
        <table class="bgcw mt10" >
            <tr>
                <td class="tabRight">身份证号：</td>
                <td class="tabcontent">${caseParty.idsNo }</td>
            </tr>
            <tr>
                <td class="tabRight">姓名：</td>
                <td class="tabcontent"><span id="forCasePartyWarn${caseParty.partyId }">${caseParty.name}</span></td>
            </tr>
            <c:if test="${caseParty.tel!=null}">
            	<tr>
	                <td class="tabRight">联系电话：</td>
	                <td class="tabcontent">${caseParty.tel }</td>        
	            </tr>
            </c:if>
            <c:if test="${caseParty.sex!=null}">
            	<tr>
	                <td class="tabRight">性别：</td>
	                <td class="tabcontent"><dict:getDictionary var="sex" groupCode="sex" dicCode="${caseParty.sex }"/>${sex.dtName }</td>
	            </tr>
            </c:if>
            <c:if test="${caseParty.education!=null}">
            	<tr>
	                <td class="tabRight">文化程度：</td>
	                <td class="tabcontent"><dict:getDictionary  var="education" groupCode="educationLevel" dicCode="${caseParty.education }"/>${education.dtName }</td>  
	            </tr>
            </c:if>
            <c:if test="${caseParty.profession!=null}">
            	<tr>   
	                <td class="tabRight">工作单位和职务：</td>
	                <td class="tabcontent">${caseParty.profession}</td>  
	            </tr>
            </c:if>
            <c:if test="${caseParty.nation!=null}">
            	<tr>                                                                                  
	                <td class="tabRight">民族：</td>
	                <td class="tabcontent">
	                	<dict:getDictionary var="nation" groupCode="nation" dicCode="${caseParty.nation }"/>
	                	${nation.dtName }
	                </td>
	            </tr>
            </c:if>
            <c:if test="${caseParty.residence!=null}">
            	<tr>
					<td class="tabRight">户籍地：</td>
					<td class="tabcontent">${caseParty.residence}</td>
	            </tr>
            </c:if>
            <c:if test="${caseParty.birthplace!=null}">
            	<tr>
	            	<td class="tabRight">籍贯：</td>
	                <td class="tabcontent">${caseParty.birthplace}</td>
	            </tr>
            </c:if>
           <c:if test="${caseParty.specialIdentity!=null}">
            	<tr>
					<td class="tabRight">特殊身份：</td>
					<td class="tabcontent"><dict:getDictionary var="specialIdentityVar" groupCode="specialIdentity" dicCode="${caseParty.specialIdentity }" /> ${specialIdentityVar.dtName}</td>
				</tr>
            </c:if>
			<c:if test="${caseParty.address!=null}">
            	<tr>
					<td class="tabRight">现住址：</td>
					<td class="tabcontent">${caseParty.address }</td>
				</tr>
            </c:if>
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
                <td class="tabRight" >法人代表：</td>
                <td class="tabcontent" >${caseCompany.proxy }</td>
            </tr>
            <tr>
                <td class="tabRight">单位性质：</td>
                <td class="tabcontent">
                	<dic:getDictionary var="danweiTypeList" groupCode="danweiType" dicCode="${caseCompany.companyType}"/><span>${danweiTypeList.dtName}</span>
                 </td>
            </tr> 
            <c:if test="${caseCompany.registractionTime!=null}">
            	<tr>                                                                              
	                <td class="tabRight">注册时间：</td>
	                <td class="tabcontent">
	               		<fmt:formatDate value="${caseCompany.registractionTime}" pattern="yyyy-MM-dd"></fmt:formatDate>
	                </td>
	            </tr> 
            </c:if>
            <c:if test="${caseCompany.tel!=null}">
            	<tr>
	                <td class="tabRight" >联系电话：</td>
	                <td class="tabcontent" >${caseCompany.tel }</td>            
	            </tr>
            </c:if>
            <c:if test="${caseCompany.registractionCapital!=null}">
            	<tr>
	                <td class="tabRight">注册资金：</td>
	                <td class="tabcontent"><fmt:formatNumber value="${caseCompany.registractionCapital}" pattern="#,##0.0000#"/>
	                    &nbsp;<font color="red">(万元)</font>
	                </td>
	            </tr> 
            </c:if>
 			<c:if test="${caseCompany.address!=null}">
            	<tr>
	                <td class="tabRight">注册地：</td>
	                <td class="tabcontent">${caseCompany.address}</td>
	            </tr>
            </c:if>
        </table>
    </c:forEach>
</c:if>
</body>
</html>