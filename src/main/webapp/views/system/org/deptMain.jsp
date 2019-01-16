<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />

<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript">
function addDept(){
	window.location.href="${path }/system/org/addDeptUI/${upOrg.orgCode}";
}
function delDept(deptId,deptName){
	top.art.dialog.confirm('确认删除部门'+deptName+'吗?',function(){
		$.getJSON("${path }/system/org/delDept/"+deptId, function(data){
			if(data.result){
				window.location.href="${path }/system/org/deptMain/${upOrg.orgCode}";
			}else{
				alert(data.msg);
			}
		}); 
	});
}
</script>
</head>
<body>

<display:table name="organiseList" uid="org" cellpadding="0" 
		cellspacing="0" requestURI="${path }/system/org/deptMain/${upOrg.orgCode}" keepStatus="true" >
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" id="userAdd" class="btn_small" onclick="addDept()"/>
		</display:caption>
		<display:column title="序号">
				${org_rowNum}
		</display:column>
		<display:column title="部门名称">
			<a href="${path }/system/org/deptDetail/${org.orgCode}">${org.orgName }</a>
		</display:column>
		<display:column title="负责人" property="leader"></display:column>
		<display:column title="联系电话" property="telephone"></display:column>
		<display:column title="备注" property="note"></display:column>
		<display:column title="操作">
			<a href="${path }/system/org/updateDeptUI/${org.orgCode}">修改</a>
			&nbsp;&nbsp;<a href="javascript:delDept('${org.orgCode }','${org.orgName }')">删除</a>
		</display:column>
</display:table>
</body>
</html>