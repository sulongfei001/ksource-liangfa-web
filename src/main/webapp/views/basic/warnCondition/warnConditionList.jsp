<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"   ></script> 
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>

<script type="text/javascript">
   function addConditon(){
	   window.location = "${path}/basic/warnCondition/edit";
   }
   
   function editCondition(conditionId){
	   window.location = "${path}/basic/warnCondition/edit?conditionId="+conditionId;
   }
   
   function delCondition(conditionId){
	   $.ligerDialog.confirm("确认删除预警规则吗？","提示信息",function(rtn){
		   if(rtn){
			      $.get("${path}/basic/warnCondition/delete?conditionId="+conditionId,function(obj){
			           if(obj){
			               window.location.reload();
			           }else{
			               $.ligerDialog.error("删除失败，请稍后重试或联系管理员！","提示信息");
			           }
			       }); 
		   }
	   });
   }
   
</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">案件预警规则查询</legend>
<form action="${path }/basic/warnCondition/list" method="post" >
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">规则名称</td>
			<td width="55%" align="left">
			 <input type="text" class="text" name="conditionTitle"	id="conditionTitle" value="${param.conditionTitle }" style="width: 45%;"/></td>
			<td width="20%" align="left" valign="middle">
			<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
	</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
	<display:table name="conditionList" uid="condition" cellpadding="0"
		cellspacing="0" requestURI="${path }/basic/warnCondition/list" decorator="">
        <display:caption class="tooltar_btn">
            <input type="submit" class="btn_small" value="添加" name="add" onclick="addConditon()"/>
        </display:caption>
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${condition_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + condition_rowNum}
			</c:if>
		</display:column>
		<display:column title="规则名称" style="text-align:left;" >
			<a href="javascript:;" onclick="">${condition.conditionTitle }</a>
		</display:column>
		<display:column title="规则描述" property="conditionDesc" style="text-align:left;">
		</display:column>
		<display:column title="操作">
			<a href="javascript:;" class="" onclick="editCondition(${condition.conditionId})">修改</a>
			<%-- <a href="javascript:;" class="" onclick="delCondition(${condition.conditionId})">删除</a> --%>
		</display:column>
	</display:table>
</div>
</body>
</html>