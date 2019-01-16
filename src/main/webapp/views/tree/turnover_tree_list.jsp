<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript">
//获取父页面的参数
var dialog = frameElement.dialog; //调用父页面的dialog对象(ligerui对象)
var parentData = dialog.get('data');
function reloadParent(){
	if(parentData.markup == 'lian'){
		parent.location.href='${path }/casehandle/caseTodo/caseTodoLianList';
	}else if(parentData.markup == 'chufadone'){
		parent.location.href='${path }/casehandle/caseTodo/chufaDoneList';
	}
}
function selectOrg(){
	var flag = true;
	//必须选择一个组织机构
	var value = $("input[name='orgData']:checked").val();
	if(value == undefined){
		flag = false;
	}
	if(flag){
		$.ligerDialog.confirm(
				"确认移送么？", 
				"移送管辖", 
				function(choose){
					if(choose){//点击确定执行这个
						$.post(
				    			"${path}/casehandle/caseTodo/saveTurnOver",
				    			{caseId:parentData.caseId,orgCode:value},
				    			function(data){
				    				if(data.result){
				    					$.ligerDialog.success("案件移送成功","",reloadParent);
				    				}else{
				    					$.ligerDialog.error("案件移送失败","",reloadParent);
				    				}
				    	})
					}
				}
		)}else{$.ligerDialog.warn("请选择组织机构");} 
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
			<input type="radio" class="pk" name="orgData" value="${org.orgCode}"/>
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
</div>
</body>
</html>