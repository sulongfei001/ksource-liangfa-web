<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
	<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript">
$(function(){
	//日期
	var startTime = document.getElementById('startTime');
	startTime.onfocus = function(){
		  WdatePicker({dateFmt : 'yyyy-MM-dd'});
	  }	 
	  var endTime = document.getElementById('endTime');
	  endTime.onfocus = function(){
		  WdatePicker({dateFmt : 'yyyy-MM-dd'});
	  }	 
});
function caseConsultationAdd(type){
	window.location.href='${path}/caseConsultation/consultationAdd?type='+type;
}

function closeConsultation(id){
         $.ligerDialog.confirm('确认结束案件咨询吗？',function(flag){
        	 if(flag){
        	 	window.location.href='${path}/caseConsultation/endConsultation/'+id;}
        	 }
         );
}
function judge(caseConsultationId){	
	$.getJSON('${path}/caseConsultation/judgeState?caseConsultationId='+caseConsultationId+"&currDate="+(new Date().getTime()),function(data){
		 if(data.result==true){
			top.changeMsgCount(caseConsultationId,1);
		 }
	 });
	 top.showCaseConsultationInfo(caseConsultationId);
}	
function setTop(id){
	window.location.href='${path}/caseConsultation/setTop?id='+id;
}
function delTop(id){
	window.location.href='${path}/caseConsultation/delTop?id='+id;
}

</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<c:if test="${type == 1 }">
	<legend class="legend">已回复咨询</legend>
	</c:if>
	<c:if test="${type == 2 }">
	<legend class="legend">待回复咨询</legend>
	</c:if>
	<c:if test="${type == 3 }">
	<legend class="legend">我的咨询</legend>
	</c:if>
<form action="${path }/caseConsultation/search" id="caseConsultationSearch" method="post">
	<input type="hidden" id="caseConsultationId" name="caseConsultationId" value=""/>
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">标题</td>
			<td width="25%" align="left"><input type="text" class="text" name="title"
				id="caseId" value="${param.title}" /></td>
			<td width="15%" align="right">状态</td>
			<td width="25%" align="left">
			<dict:getDictionary
				var="caseConsultationStateList" groupCode="caseConsultationState" /> <select id=caseConsultationState
				name="state" style="width: 78%">
				<option value="">--全部--</option>
					<c:forEach var="domain" items="${caseConsultationStateList }">
						<c:choose>
							<c:when test="${domain.dtCode !=param.state}">
								<option value="${domain.dtCode }">${domain.dtName }</option>
							</c:when>
							<c:otherwise >
								<option value="${domain.dtCode }" selected="selected">${domain.dtName }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</select></td>
			<td width="20%"  rowspan="2" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
		<tr>
			<td width="15%" align="right">发布时间</td>
			<td width="25%" align="left">
				<input type="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 34%" class="text"/>
				至
				<input type="text" name="endTime" id="endTime" value="${param.endTime }" style="width: 34%" class="text"/>
				<input type="hidden" name="type" id="type" value="${type }"/>
			</td>
		</tr>
	</table>
</form>
</fieldset>
<display:table  name="caseConsultationList"  uid="consultation" pagesize="10" cellpadding="0" cellspacing="0" keepStatus="true"
 requestURI="${path }/caseConsultation/search">

		<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${consultation_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + consultation_rowNum}
			</c:if>
		</display:column>
	<display:column style="text-align: left;" title="标题">
		<c:if test="${consultation.setTop==1 or consultation.setTop==2}">
		<img src="${path}/resources/images/forum_top.gif" alt="置顶"/>
        &nbsp;&nbsp;
		</c:if>
		<a href="javascript:judge('${consultation.id}')">${consultation.title}</a>
	</display:column>
	<display:column style="text-align: left;" title="案件名称" property="caseName"></display:column>
	<display:column style="text-align: center;" title="发布单位" property="orgName"></display:column>
	<c:if test="${type == 3 }">
	<display:column style="text-align: center;" title="发布时间">
		<fmt:formatDate value="${consultation.inputTime}" pattern="yyyy-MM-dd"/>
	</display:column>
	</c:if>
	<c:if test="${type == 1 }">
	<display:column style="text-align: center;" title="最新回复时间">
		<fmt:formatDate value="${consultation.replyTime}" pattern="yyyy-MM-dd HH:ss"/>
	</display:column>
	</c:if>
	<display:column style="text-align: center;" title="回复数" property="replyCount"></display:column>
	<display:column  style="text-align: center;" title="状态">
		<dict:getDictionary var="caseConsultationState" groupCode="caseConsultationState" dicCode="${consultation.state}" />
		<c:if test="${consultation.state==2 }"><font color="red"/></c:if>
		${caseConsultationState.dtName}
		<c:if test="${consultation.state==2 }"></font></c:if>
	</display:column>
	<c:if test="${type == 3}">
	<display:column title="操作">
		<%-- <a href="javascript:judge('${consultation.id}')">查看</a> --%>
		<c:if test="${type == 3 }">
		<c:if test="${consultation.state==1 and (consultation.inputer ==currentUserId or !empty isShowEnd)}">
				<a href="javascript:closeConsultation('${consultation.id}');" id="setState">结束</a>
		<c:if test="${consultation.inputer ==currentUserId }"> 
			<a href="javascript:;" onclick="$.ligerDialog.confirm('您确认要删除吗？',function(){location.href = '${path }/caseConsultation/delete?id=${consultation.id}';})">删除</a>
		</c:if>
		</c:if>
		</c:if>
	</display:column>
	</c:if>
</display:table>
</div>
</body>
</html>