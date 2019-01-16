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
    	<span>${penaltyCaseForm.transactPerson }</span></p>
    <p class="top_text">
    	<span>办理时间：</span>
    	<span><fmt:formatDate value="${penaltyCaseForm.transactTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/></span>
    </p>
</div>
<table class="bgcw mt10">
	<tr>
		<td class="tabRight" >行政处罚决定书文号：</td>
		<td class="tabcontent">
			${penaltyCaseForm.penaltyFileNo }
		</td>
	</tr>
	<tr>
		<td class="tabRight" >承办人：</td>
		<td class="tabcontent" >
			${penaltyCaseForm.undertaker }
		</td>
	</tr>
	<tr>
		<td class="tabRight" >处罚时间：</td>
		<td class="tabcontent" >
			<fmt:formatDate value="${penaltyCaseForm.chufaTime}" pattern="yyyy-MM-dd"/>
		</td>
	</tr>
	<tr>
		<td class="tabRight" >行政处罚次数：</td>
		<td class="tabcontent" >
			${penaltyCaseForm.chufaTimes }
		</td>
	</tr>
	<tr>
		<!-- 处罚类型 -->
		<td class="tabRight">处罚类型：</td>
		<td class="tabcontent" >
			${penaltyCaseForm.chufaTypeName }
		</td>
	</tr>
	<tr>
		<!-- 是否经过负责人集体讨论(1-否，2-是) -->
		<td class="tabRight" >是否经过负责人集体讨论：</td>
		<td class="tabcontent" id="isDescuss">
			<c:if test="${penaltyCaseForm.isDescuss == 1 }">是</c:if>
			<c:if test="${penaltyCaseForm.isDescuss == 2 }">否</c:if>
		</td>
	</tr>
	<tr>
		<!-- 情节是否严重(1-否，2-是) -->
		<td class="tabRight">情节是否严重：</td>
		<td  class="tabcontent">
			<c:if test="${penaltyCaseForm.isSeriousCase == 1 }">是</c:if>
			<c:if test="${penaltyCaseForm.isSeriousCase == 2 }">否</c:if>
		</td>
	</tr>
	<tr>
		<!-- 涉案金额达到刑事追诉标准(1-否，2-是) -->
		<td class="tabRight" >涉案金额达到刑事追诉标准：</td>
		<td class="tabcontent">
			<c:if test="${penaltyCaseForm.isBeyondEighty == 1 }">是</c:if>
			<c:if test="${penaltyCaseForm.isBeyondEighty == 2}">否</c:if>
		</td>
	</tr>
	<tr>
		<!-- 是否低于行政处罚规定下限金额(1-否，2-是)-->
		<td class="tabRight">是否低于行政处罚规定的下限金额：</td>
		<td class="tabcontent" >
			<c:if test="${penaltyCaseForm.isLowerLimitMoney == 1 }">是</c:if>
			<c:if test="${penaltyCaseForm.isLowerLimitMoney == 2 }">否</c:if>
		</td>
	</tr>
</table>
<table class="bgcw mt10">

	<!-- 是否为侵权假冒案件 -->
	<tr id="isDqdjTr">
		<td class="tabRight" >是否为侵权假冒类型：</td>
		<td class="tabcontent">
			<c:if test="${penaltyCaseForm.isDqdj == 1 }">是</c:if>
			<c:if test="${penaltyCaseForm.isDqdj == 2 }">否</c:if>
		</td>
	</tr>
	<c:if test="${penaltyCaseForm.isDqdj == 1}">
		<tr >
			<td class="tabRight" >侵权假冒类型：</td>
			<td class="tabcontent">
				${penaltyCaseForm.dqdjTypeName}
			</td>
		</tr>
	</c:if>
</table>
<table  class="bgcw mt10">
	<!-- 涉案物品名称，涉案物品数量 -->
	<tr>
		<td class="tabRight" >涉案物品名称：</td>
		<td class="tabcontent">
			${penaltyCaseForm.goodsInvolved }
		</td>
	</tr>
	<tr>
		<td class="tabRight" >涉案物品数量：</td>
		<td class="tabcontent">
			${penaltyCaseForm.goodsCount }
		</td>
	</tr>
	<!-- 涉案物品处理情况 -->
	<tr>
		<td class="tabRight" >涉案物品处理情况：</td>
		<td class="tabcontent">
		${penaltyCaseForm.goodsDisposeResult }
		</td>
	</tr>
</table>
<table  class="bgcw mt10">
	<tr>
		<!-- 是否鉴定(1-否，2-是) -->
		<td class="tabRight" >是否鉴定：</td>
		<td class="tabcontent">
			<c:if test="${penaltyCaseForm.isIdentify == 1 }">是</c:if>
			<c:if test="${penaltyCaseForm.isIdentify == 2 }">否</c:if>
		</td>
	</tr>
	<!-- 鉴定种类 -->
	<c:if test="${penaltyCaseForm.isIdentify == 1 }">
		<tr >
			<td class="tabRight" >鉴定种类：</td>
			<td class="tabcontent">
				${penaltyCaseForm.identifyTypeName}
			</td>
		</tr>	
		<tr>
			<td class="tabRight" >鉴定机构：</td>
			<td class="tabcontent" >
				${penaltyCaseForm.identifyOrg}
			</td>
		</tr>
		<tr >
			<td class="tabRight" >鉴定人：</td>
			<td class="tabcontent" >
				${penaltyCaseForm.identifyPerson }
			</td>
		</tr>
		<tr >
			<td class="tabRight" >鉴定时间：</td>
			<td class="tabcontent">
				<fmt:formatDate value="${penaltyCaseForm.identifyTime}" pattern="yyyy-MM-dd"/>
			</td>
		</tr>
		<tr >
			<td class="tabRight" >鉴定意见主要内容</td>
		    <td class="icon_down"></td>
		</tr>
		<tr class="this_content">
			<td colspan="2" >
				${penaltyCaseForm.identifyResult }
			</td>
		</tr>
	</c:if>
</table>

<table  class="bgcw mt10">
	<!-- 处罚依据 -->
	<tr style="border-top: none;">
		<td class="tabRight" >处罚依据：</td>
		<td class="tabcontent">
			${penaltyCaseForm.chufaBasis }
		</td>	
	</tr>
	<!-- 处罚结果 -->
	<tr>
		<td class="tabRight" >处罚结果：</td>
		<td class="tabcontent">
			${penaltyCaseForm.chufaResult }	
	</tr>
	<c:set var="penaltyFileKey" value="f${penaltyCaseForm.penaltyFileId}"></c:set>
    <c:if test="${!empty attaMap[penaltyFileKey]}">
		<tr>
			<td class="tabRight" >行政处罚决定书：</td>
			<td class="tabcontent" >
		        <a href="javascript:;" onclick="app.downloadCaseFile('/download/caseFile?id=${penaltyCaseForm.penaltyFileId}','${attaMap[penaltyFileKey].attachmentName }')">
		            ${attaMap[penaltyFileKey].attachmentName }
		        </a>
			</td>
		</tr>
	</c:if>
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