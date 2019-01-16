<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ include file="/views/workflow/caseProcView/caseStepView.jsp"%>
<h3>法院判决结果：</h3>
<c:forEach items="${xianyirenList }" var="xianyiren" varStatus="xianyiren_state">
    <table class="blues" style="margin: 10px;width:96%; float: left;">
        <thead>
            <tr><th colspan="6">
        <div style="float: left;text-align:left; width: 200px;padding:5px; font-size: 13px;">
            判决结果：
        </div>
        <c:if test="${xianyiren.fayuanpanjueState==2 }">
            <div  id="panjueInfo${xianyiren.xianyirenId }" class="alert alert-success" style="clear: both;font-weight:normal;" align="left">
               <strong> 判决时间(一审)：</strong><fmt:formatDate value="${xianyiren.panjue1Time }" pattern="yyyy-MM-dd"/><br/>
               <strong> 判决罪名(一审)：</strong><c:forEach items="${xianyiren.accuseInfoList }" var="accuseInfo" varStatus="accuseInfo_state">
                    ${accuseInfo.name }(${accuseInfo.clause })&nbsp;&nbsp;<c:if test="${!accuseInfo_state.last }">，</c:if>
                </c:forEach><br/>
               <strong> 判决情况：</strong>${xianyiren.conditionName }<br/> 
               <strong> 判决结果(一审)：</strong>${xianyiren.panjue1Jg }<br/>
                <strong>判决时间(终审)：</strong><fmt:formatDate value="${xianyiren.panjue2Time }" pattern="yyyy-MM-dd"/><br/>
               <strong> 判决罪名(终审)：</strong><c:forEach items="${xianyiren.accuseInfoList2 }" var="accuseInfo" varStatus="accuseInfo_state">
                    ${accuseInfo.name }(${accuseInfo.clause })&nbsp;&nbsp;<c:if test="${!accuseInfo_state.last }">，</c:if>
                </c:forEach><br/>
               <strong> 判决结果(终审)：</strong>${xianyiren.panjue2Jg }<br/>
            </div>
        </c:if>
    </th></tr>
</thead>
<tr>
    <td class="tabRight" style="width: 135px;">姓名：</td>
    <td style="text-align: left;width: 265px;">${xianyiren.name }</td>
    <td class="tabRight" style="width:100px;">性别：</td>
    <td style="text-align: left;width:100px;"><dict:getDictionary  var="sex" groupCode="sex" dicCode="${xianyiren.sex }"/>${sex.dtName }</td>
    <td class="tabRight" style="width:100px;">民族：</td>
    <td style="text-align: left;width:100px;" ><dict:getDictionary  var="nation" groupCode="nation" dicCode="${xianyiren.nation }"/>${nation.dtName }</td>
</tr>
<tr>
    <td class="tabRight">身份证号：</td>
    <td style="text-align: left;">${xianyiren.idsNo }</td>
    <td class="tabRight">出生日期：</td>
    <td style="text-align: left;"><fmt:formatDate value="${xianyiren.birthday }" pattern="yyyy-MM-dd"/> </td>
    <td class="tabRight">文化程度：</td>
    <td style="text-align: left;"><dict:getDictionary  var="education" groupCode="educationLevel" dicCode="${xianyiren.education }"/>${education.dtName }</td>
</tr>
        <tr>
            <td class="tabRight">工作单位和职务：</td>
            <td style="text-align: left;">${xianyiren.profession }</td>
            <td class="tabRight">联系电话：</td>
            <td style="text-align: left;">${xianyiren.tel }</td>
            <td class="tabRight">特殊身份：</td>
            <td style="text-align: left;">
				<dict:getDictionary var="specialIdentityVar" groupCode="specialIdentity" dicCode="${xianyiren.specialIdentity }" /> 
				${specialIdentityVar.dtName}            
			</td>
        </tr>
        <tr>
            <td class="tabRight">籍贯：</td>
            <td style="text-align: left;">${xianyiren.birthplace }</td>
            <td class="tabRight">户籍地：</td>
            <td style="text-align: left;">${xianyiren.residence }</td>  
            <td class="tabRight">现住址：</td>
            <td style="text-align: left;">${xianyiren.address }</td>                         
        </tr>
        <tr><td class="tabRight">起诉罪名：</td><td colspan="5" style="text-align: left;">
        <c:forEach items="${xianyiren.accuseInfoList3 }" var="accuseInfo" varStatus="accuseInfo_state">
				<a id="${stepId }_accuseInfo_${accuseInfo_state.index }" title="${fn:escapeXml(accuseInfo.law)}" class="accuseInfo">${accuseInfo.name }(${accuseInfo.clause })</a>
				&nbsp;&nbsp;<c:if test="${!accuseInfo_state.last }"><br/></c:if>
        </c:forEach>
<script type="text/javascript">
	$(function () {
		<c:forEach items="${accuseInfoList}" var="accuseInfo" varStatus="accuseInfo_state">
		$("#${stepId }_accuseInfo_${accuseInfo_state.index }").poshytip({
			            className: 'tip-yellowsimple',
			            slide: false,
			            fade: false,
			            alignTo: 'target',
			            alignX: 'right',
			            alignY: 'center',
			            offsetX:10,
			            allowTipHover:true 
			        });
		</c:forEach>
	});
</script>
    </td></tr>
</table>
</c:forEach>