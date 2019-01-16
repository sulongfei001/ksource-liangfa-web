<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ include file="/views/workflow/caseProcView/caseStepView.jsp"%>
<c:if test="${!empty accuseInfoList}">
	<table class="blues" style="width: 96%; border-top:hidden; margin-top: -10px; margin-right: 10px; margin-bottom: 10px; margin-left: 10px;">
		<tr>
			<td class="tabRight" style="text-align: right;width: 135px;">移送公安罪名：</td>
			<td style="text-align: left;">
				<ul style="padding: 0px;margin: 0px;">
					<c:forEach items="${accuseInfoList}" var="accuseInfo"
						varStatus="accuseInfo_state">
						<a id="${stepId }_accuseInfo_${accuseInfo_state.index }" title="${fn:escapeXml(accuseInfo.law)}" class="accuseInfo">${accuseInfo.name }(${accuseInfo.clause })</a>&nbsp;&nbsp;<c:if test="${!accuseInfo_state.last }"><br/></c:if>
					</c:forEach>
				</ul>
			</td>
		</tr>
	</table>
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
</c:if>
<c:if test="${empty accuseInfoList}">无</c:if>
