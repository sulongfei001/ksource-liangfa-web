<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript">
//获取父页面的参数
var caseId = window.opener.paramObj.caseId;
function selectOrg(){
	var flag = true;
	//必须选择一个组织机构
	var value = $("input[name='orgData']:checked").val();
	if(value == undefined){
		flag = false;
	}
	if(flag){
		art.dialog.confirm("确认移送么？",function(){
			$.post(
	    			"${path}/casehandle/caseTodoLian/saveTurnOver",
	    			{caseId:caseId,orgCode:value},
	    			function(data){
	    			console.log(typeof data.result);
	    				if(data.result){
	    					console.log("案件交办成功");
	    					art.dialog.tips("案件交办成功", 2.0);
	    					//top.reload(470);
	    					window.close();
	    				}else{
	    					console.log(data.msg);
	    					art.dialog.tips(data.msg, 2.0);
	    				}
	    			}
			)})}else{
		    	/* window.location.href='${path}/workflow/task/taskFenpai?taskId=${caseTodo.taskId}'
		    	+'&caseId=${caseTodo.caseId}&orgCode='+value */
		    	console.log("errorrorororororo");
					art.dialog.tips("请选择组织机构", 2.0);
				  }
		}
</script>
</head>
<body>

<div class="panel">
	<c:set var="checkAll">
			<input onclick="window.parent.selectAll(this);" type="radio" />
	</c:set>
	<display:table name="orgList" id="org" cellpadding="0" cellspacing="0" requestURI="${path}/system/org/getTurnOverOrg">
		 <display:column >
			<input type="radio" class="pk" name="orgData" value="${org.orgCode}">
		</display:column>		
		<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${org_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + org_rowNum}
			</c:if>
		</display:column>
		<display:column title="部门名称" property="orgName" style="text-align:left;"></display:column>		
	</display:table>
	<div align="center" style="margin-top:70px;">
		<input type="button" class="btn_small" value="选&nbsp;择" onclick="selectOrg();"/>
		<input type="button" class="btn_small" value="取&nbsp;消" onclick="window.close();"/>
	</div>
</div>
</body>
</html>