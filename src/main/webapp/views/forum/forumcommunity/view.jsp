<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/displaytagall.css"/>" />
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"   ></script> 
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
$(function(){
	 //按钮样式
	$("input:button,input:reset,input:submit").button();

	//日期插件,天[dd],月[mm],年[yyyy]
	var createTime = document.getElementsByName('createTime')[0];
	createTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
});
</script>
<title>待办任务列表</title>
</head>
<body>
<div>
	<form:form action="${path }/workflow/task/completed" method="post" modelAttribute="stepForm" >
		<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">版块名称</td>
			<td width="40%" align="left">
				<input type="text" class="name" name="name" value="${param.name}" />
			</td>
			
			<td width="15%" align="right">版块编号</td>
			<td width="30%" align="left">
				<input type="text" class="id" name="id" id="id" value="${param.id }" />
			</td>
			
		</tr>
		<tr>
			<td width="15%" align="right">创建时间</td>
			<td width="40%" align="left">
				<input type="text" class="createTime" name="createTime" value="${param.createTime }" />
			</td>
			<td width="15%" align="right">状态</td>
			<td width="30%" align="left">
			<dict:getDictionary var="forumCommunityStateList" groupCode="forumCommunityState" /> 
			<select id="state" name="state" style="width: 44%">
				<option value="">--全部--</option>
					<c:forEach var="domain" items="${forumCommunityStateList }">
						<c:choose>
							<c:when test="${domain.dtCode != param.state}">
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
	<%-- 	<td width="15%" align="right">录入时间区段</td>
			<td width="40%" align="left">
				<input type="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 34.5%"/>
				至
				<input type="text" name="endTime" id="endTime" value="${param.endTime }" style="width: 34.5%"/>
			</td>
		</tr> --%>
		<tr>
		
			<td colspan="4" align="center">
			<input type="hidden" name="procKey" id="procKey" value="${procKey}" />
			<input type="submit" value="查询" />
		    <input type="button" value="清空"  onclick="clearAll()"/></td>
		</tr>
	</table>
	</form:form>
</div>
<display:table name="fcsList"  uid="fcy" cellpadding="0" cellspacing="0">
		<display:caption class="right">
			<input type="button" value="添加" id="infoAdd" onclick="add()"/>
			<input type="button" value="删除" id="infoAdd" onclick="add()"/>
		</display:caption>
	
	<display:column title="<input type='checkbox' onclick='checkAll(this)'/>" style="width: 5%">
	<%-- <c:choose>
		<c:when test="${role.personCount =='0'}">
			<input type="checkbox" name="check" value="${role.roleId}" />
		</c:when>
		<c:otherwise>
			<input type="checkbox" disabled="disabled" title="此角色已使用,不能删除" />
		</c:otherwise>
		</c:choose>   --%>
	</display:column>
	
	<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${case_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*10 + case_rowNum}
			</c:if>
    </display:column>
    <display:column  title="版块编号"  property="id" style="text-align:left;"/>
	<display:column  title="版块名称"  property="name" style="text-align:left;"/>
	<display:column  title="创建时间" property="createTime" style="text-align:left;"/>
	<display:column  title="状态" property="state" style="text-align:left;">
		<dict:getDictionary var="forumCommunityState" groupCode="forumCommunityState" dicCode="${fcy.state}" />
		<c:if test="${fcy.state==0 }">
			<font color="red">
		</c:if>
			${forumCommunityState.dtName}
		<c:if test="${fcy.state==0 }">
			</font>
		</c:if>
	</display:column>
	<display:column title="操作">
			<a href="${path }/info/lay/update?infoId=${info.infoId}">查看</a>
			<a href="javascript:;" onclick="top.art.dialog.confirm('确认此法律法规删除吗？',function(){location.href = '${path }/info/lay/delete?infoId=${info.infoId}';})">修改</a>
		</display:column>
</display:table>

</body>
</html>