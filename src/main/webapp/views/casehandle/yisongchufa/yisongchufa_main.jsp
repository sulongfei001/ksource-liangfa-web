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
<script type="text/javascript">
$(function(){
	//日期
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
   window.location.href="${path}/casehandle/yisongCase/addUI";
}
</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">移送行政违法案件查询</legend>
<form action="${path }/casehandle/yisongCase/search" method="post">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">案件编号</td>
			<td width="25%" align="left"><input type="text" class="text" name="caseNo"
				id="caseNo" value="${param.caseNo }" /></td>
			<td width="15%" align="right">案件名称</td>
			<td width="25%" align="left" colspan="3"><input type="text" class="text"
				name="caseName" value="${fn:replace(param.caseName,"\"","&quot;")}" /></td>
			<td width="20%"  rowspan="2" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
		<tr>
			<td width="15%" align="right">录入时间</td>
			<td width="25%" align="left">
				<input type="text" name="startTime" id="startTime" value="${param.startTime }" class="text" style="width: 36%"/>
				至
				<input type="text" name="endTime" id="endTime" value="${param.endTime }" class="text" style="width: 36%"/>
			</td>
			<td width="15%" align="right">状态</td>
			<td width="25%" align="left">
			<dict:getDictionary
				var="caseStateList" groupCode="yisongchufaProcState" /> <select id="caseState"
				name="caseState" >
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
	</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
<form:form method="post" action="${path }/casehandle/yisongCase/del">
	<display:table name="caseBasicList" uid="caseBasic" cellpadding="0"
		cellspacing="0" requestURI="${path }/casehandle/yisongCase/search">
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" name="caseAdd" class="btn_small"
				onclick="toAdd()" />
		</display:caption>
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${caseBasic_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + caseBasic_rowNum}
			</c:if>
		</display:column>
		<display:column title="案件编号" property="caseNo" style="text-align:left;"></display:column>
		<display:column title="案件名称" property="caseName" style="text-align:left;"></display:column>
		<display:column title="状态" style="text-align:left;">
			<dict:getDictionary var="stateVar" groupCode="yisongchufaProcState"
				dicCode="${caseBasic.caseState }" />
		${stateVar.dtName }
		</display:column>
		<display:column title="移送时间" style="text-align:left;">
			<fmt:formatDate value="${caseBasic.yisongTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="录入时间" style="text-align:left;">
			<fmt:formatDate value="${caseBasic.inputTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="最后办理时间" style="text-align:left;">
			<fmt:formatDate value="${caseBasic.latestPocessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column title="操作">
			<a href="javascript:;" onclick="top.showCaseProcInfo('${caseBasic.caseId}');">案件详情</a>
			<c:if test="${caseBasic.caseState==1 }">
			<a href="${path}/casehandle/yisongCase/updateUI?caseId=${caseBasic.caseId}">修改</a>
		    </c:if>
		</display:column>
	</display:table>
</form:form>
</div>
</body>
</html>