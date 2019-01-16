<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/script/prototip/css/prototip.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type='text/javascript' src='${path }/resources/script/prototype.js'></script>
<script type='text/javascript' src='${path }/resources/script/prototip/prototip.js'></script>
<script type="text/javascript">
	//批量删除操作
	function isBatchDelete(checkName){	
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
			 top.art.dialog.confirm("确认删除吗？",function(){
				jQuery("#delForm").submit();
			 });
		}else{
			top.art.dialog.alert("请选择要删除的模版!");
		}
		return false;
	}
			
	function checkAll(obj){
	       jQuery("[name='check']").attr("checked",obj.checked) ; 			
	}		
	
	function toTemplateAdd(form){
		form.action="${path}/office/officeTemplate/addUI";
		form.submit();
	}
	
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
<legend class="legend">文书模版查询</legend>
<form:form method="post" action="${path }/office/officeTemplate/search" id="queryForm">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="12%" align="right">
				模板名称
			</td>
			<td width="20%" align="left" >
				<input type="text" name="subject" value="${officeTemplate.subject }" class="text"/>
			</td>
			<td width="12%" align="right">
				模版类型
			</td>
			<td width="20%" align="left" >
				<select id="templateType" name="templateType" >
					<option value="">--请选择--</option>
					<option value="1" <c:if test="${officeTemplate.templateType==1}">selected</c:if> >文书</option>
					<option value="2" <c:if test="${officeTemplate.templateType==2 }">selected</c:if> >报告</option>
				</select>
			</td>
			<td width="36%" align="left" valign="middle">
				<input type="submit" value="查 询" class="btn_query" />
			</td>
		</tr>
	</table>
	<!-- 查询结束 -->
</form:form>
</fieldset>

<br/>
<form:form id="delForm" method="post" action="${path }/office/officeTemplate/batchDelete">
	<display:table name="list" uid="officeTemplate" cellpadding="0" cellspacing="0" requestURI="${path }/office/officeTemplate/search">
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" name="templateAdd"  class="btn_small" onclick="toTemplateAdd(this.form)" />			
			<input type="submit" value="批量删除" name="templateDelete" onclick="return isBatchDelete('check')"  class="btn_big" />		
		</display:caption>
		<display:column title="<input type='checkbox' onclick='checkAll(this)'/>">
			<input type="checkbox" name="check" value="${officeTemplate.id}" />
		</display:column>
		<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${officeTemplate_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + officeTemplate_rowNum}
			</c:if>
		</display:column>
		<display:column title="模板名称" property="subject" style="text-align:left;"></display:column>
		<display:column title="模版类型" style="text-align:left;">
			<c:if test="${officeTemplate.templateType==1 }">文书</c:if>
			<c:if test="${officeTemplate.templateType==2 }">报告</c:if>
		</display:column>
		<display:column title="文书类型" style="text-align:left;">
			<dict:getDictionary groupCode="docType" var="docTypeList" /> 
				<c:forEach items="${docTypeList}" var="domain">
					<c:if test="${officeTemplate.docType eq domain.dtCode}">${domain.dtName}</c:if>
				</c:forEach>
		</display:column> 
		<display:column title="创建人" property="creator" style="text-align:left;"></display:column>
		<display:column title="创建时间"  style="text-align:left;">
			<fmt:formatDate value="${officeTemplate.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
		</display:column>
		<display:column title="操作">
		    <a href="javascript:;" onclick="top.art.dialog.confirm('确认删除吗?',function(){location.href = '${path}/office/officeTemplate/delete?id=${officeTemplate.id}';})">删除</a>
			<a href="<c:url value="/office/officeTemplate/updateUI?id=${officeTemplate.id}"/>">修改</a>
			<a href="javascript:;" onclick="top.preview('${officeTemplate.docType}')">预览</a>
		</display:column>
	</display:table>
</form:form>
</div>
</body>
</html>