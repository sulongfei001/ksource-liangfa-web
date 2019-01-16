<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ include file="/views/workflow/caseProcView/caseStepView.jsp"%>
<h3>提请逮捕名单：</h3>
<c:forEach items="${xianyirenList }" var="xianyiren" varStatus="xianyiren_state">
		<table class="blues" style="margin: 10px;width:96%;float: left;">
			<tr>
				<td class="tabRight" style="width: 135px;">姓名：</td>
				<td style="text-align: left;width:265px;">${xianyiren.name } </td>
				<td class="tabRight"  style="width:100px;">性别：</td>
				<td style="text-align: left;width: 100px;"><dict:getDictionary  var="sex" groupCode="sex" dicCode="${xianyiren.sex }"/>${sex.dtName }</td>
				<td class="tabRight" style="width:100px;">民族：</td>
				<td style="text-align: left;width:100px;"><dict:getDictionary  var="nation" groupCode="nation" dicCode="${xianyiren.nation }"/>${nation.dtName }</td>
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
				<td class="tabRight">工作单位和职务：</td><td style="text-align: left;">${xianyiren.profession }</td>
				<td class="tabRight">联系电话：</td><td style="text-align: left;">${xianyiren.tel }</td>
				<td class="tabRight">特殊身份:</td><td style="text-align: left;"><dict:getDictionary  var="specialIdentity" groupCode="specialIdentity" dicCode="${xianyiren.specialIdentity }"/>${specialIdentity.dtName }</td>
			</tr>
			<tr>
				<td class="tabRight">户籍地：</td><td style="text-align: left;">${xianyiren.residence }</td>
				<td class="tabRight">籍贯：</td><td  style="text-align: left;">${xianyiren.birthplace }</td>
				<td class="tabRight">现住址：</td><td  style="text-align: left;">${xianyiren.address }</td>
			</tr>
			<tr><td class="tabRight">提请逮捕罪名：</td><td colspan="5" style="text-align: left;">
				<c:forEach items="${xianyiren.accuseInfoList }" var="accuseInfo" varStatus="accuseInfo_state">
					<a id="${stepId }_accuseInfo_${xianyiren_state.index }_${accuseInfo_state.index }" class="accuseInfo" >${accuseInfo.name }(${accuseInfo.clause })</a>&nbsp;&nbsp;<c:if test="${!accuseInfo_state.last }"><br/></c:if>
				</c:forEach>
			</td>
			</tr>
		</table>
		</c:forEach>
		<script type="text/javascript">
			$(function () {
				<c:forEach items="${xianyirenList }" var="xianyiren" varStatus="xianyiren_state">
					<c:forEach items="${xianyiren.accuseInfoList}" var="accuseInfo" varStatus="accuseInfo_state">
					$("#${stepId }_accuseInfo_${xianyiren_state.index }_${accuseInfo_state.index }").poshytip({
									content:'${accuseInfo.law}',
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
				</c:forEach>
			});
		</script>				