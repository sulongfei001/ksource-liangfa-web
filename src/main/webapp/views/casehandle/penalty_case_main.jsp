<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>	
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript">
$(function(){
	//日期插件时[hh],天[dd],月[mm],年[yyyy]
	var startTime = document.getElementById('startTime');
	startTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	var endTime = document.getElementById('endTime');
	endTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
});
function isDelete(checkName){
				var flag ;
				var name ;
				for(var i=0;i<document.forms[1].elements.length;i++){
					
					name = document.forms[1].elements[i].name;
					if(name.indexOf(checkName) != -1){
						if(document.forms[1].elements[i].checked){
							flag = true;
							break;
						}
					}
				}   	
				if(flag){
					if(confirm("确认删除吗？")){
						return true;
					}
				}else{
					top.art.dialog.alert("请选择要删除的记录!");
				}
				return false;
			}
			
function checkAll(obj){
       $("[name='check']").attr("checked",obj.checked) ; 			
			}
			
function toAdd(){
	window.location.href="${path}/casehandle/case/addUI?procKey=${procKey}";
}

// 移送行政机关
function takeoffAdministrativeOrgan(caseId,caseName) {
// 	0、存放参数
	$.dialog.data('caseId',caseId);
	$.dialog.data('caseName',caseName);
	//$.dialog.data('caseName',$.trim($(object).closest("#case").find(".caseName").html()));
	
// 	1、弹出弹出框框
		$.dialog.open('${path}/views/casehandle/takeoffAdministrativeOrgan.jsp', {
			title: '移送行政机关',
			 width: 500,
			 height: 340,
			lock:true
		});
// 	2、加载页面
// 	3、点击移送按钮，保存到数据库
// 	4、提示移送结果
// 	5、关闭弹出框
}

</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">案件信息查询</legend>
<form action="${path }/casehandle/case/search" method="post">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">案件编号</td>
			<td width="25%" align="left"><input type="text" class="text" name="caseNo"
				id="caseNo" value="${param.caseNo }" /></td>
			<td width="15%" align="right">案件名称</td>
			<td width="25%" align="left"><input type="text" class="text"
				name="caseName" value="${param.caseName }" /></td>
			<td width="20%"  rowspan="3" align="left" valign="middle">
			<%-- <input type="hidden"	name="procKey" id="procKey" value="${procKey}" />  --%>
			<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
		<tr>
			<td width="15%" align="right">录入时间</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 36%" readonly="readonly"/>
				至
				<input type="text" class="text" name="endTime" id="endTime" value="${param.endTime }" style="width: 36%" readonly="readonly"/>
			</td>
			<td width="15%" align="right">状态</td>
			<td width="25%" align="left">
			<dict:getDictionary
				var="caseStateList" groupCode="${procKey}State" /> <select id="caseState"
				name="caseState">
				<option value="">--全部--</option>
					<c:forEach var="domain" items="${caseStateList }">
						<c:choose>
							<c:when test="${domain.dtCode !=param.caseState}">
								<option value="${domain.dtCode }">${domain.dtName }</option>
							</c:when>
							<c:otherwise >
								<option value="${domain.dtCode }" selected="selected">${domain.dtName }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<%-- <td width="15%" align="right">移送时间</td>
			<td width="25%" align="left">
				<input type="text" name="yisongStartTime" id="yisongStartTime" value="${param.yisongStartTime}" class="text" style="width: 36%"/>
				至
				<input type="text" name="yisongEndTime" id="yisongEndTime" value="${param.yisongEndTime}" class="text" style="width: 36%"/>
			</td> --%>
		</tr>
	</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
<%-- <form:form method="post" action="${path }/casehandle/case/del"> --%>
	<display:table name="caseList" uid="caseBasic" cellpadding="0"
		cellspacing="0" requestURI="${path }/casehandle/case/search">
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${caseBasic_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + caseBasic_rowNum}
			</c:if>
		</display:column>
		<display:column title="案件编号" property="caseNo" style="text-align:left;">
		</display:column>
		<display:column title="案件名称" style="text-align:left;">
			<span title="${caseBasic.caseName}">${fn:substring(caseBasic.caseName,0,20)}${fn:length(caseBasic.caseName)>20?'...':''}</span>
		</display:column>
		<display:column title="状态" style="text-align:left;">
			<dict:getDictionary var="stateVar" groupCode="${procKey}State"
				dicCode="${caseBasic.caseState }" />
		${stateVar.dtName }
		</display:column>
		<%-- <display:column title="移送时间" style="text-align:left;">
			<fmt:formatDate value="${caseBasic.yisongTime}" pattern="yyyy-MM-dd"/>
		</display:column> --%>
		<display:column title="录入时间" style="text-align:left;">
			<fmt:formatDate value="${caseBasic.inputTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="最后办理时间" style="text-align:left;">
			<fmt:formatDate value="${caseBasic.latestPocessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column title="操作">
			<a href="javascript:;" onclick="top.showCaseProcInfo('${caseBasic.caseId}');">案件详情</a>
			<!-- 
			 <a href="${path }/casehandle/case/penalty_case_detail?caseId=${caseBasic.caseId}">详细信息</a>
			--> 
			<c:if test="${(caseBasic.caseState==10 and caseBasic.caseType != 1) or caseBasic.caseState==26 }">
				<a href="${path}/casehandle/case/penalty_updateUI?procKey=${procKey}&caseId=${caseBasic.caseId}&versionNo=${caseBasic.versionNo}">修改</a>
		    </c:if>
		    <c:if test="${caseBasic.chufaState==2}">
				<a href="javascript:;" onclick="takeoffAdministrativeOrgan('${caseBasic.caseId}','${caseBasic.caseName}')">移送行政机关</a>
		    </c:if>
		</display:column>
	</display:table>
<%-- </form:form> --%>
</div>
</body>
</html>