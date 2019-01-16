<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path}/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript" src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"   ></script> 
<script type="text/javascript" src="${path}/resources/jquery/ligerUI-1.3.2/js/core/base.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript">
$(function(){
	<c:if test="${info==1}">
		$.ligerDialog.success('删除成功!');
	</c:if>
	updateHref();
});


function add(form){
	var treeNode=window.parent.getSelectNode();
    if (typeof(treeNode)!="undefined"){ 
		var industryType=treeNode.id;
		if(industryType =='' ||industryType == -1){
			$.ligerDialog.warn("请选择行业类型！");
		}else{
			form.action = "${path }/system/accuseRule/addUI?industryType=${accuseRule.industryType }";
			form.submit();
		}
    }else{
		$.ligerDialog.warn("请选择行业类型！");
    }
}

function del(id,industryType){
	$.ligerDialog.confirm('确认删除吗?',function(flag){
		if(flag==true){
			window.location.href="${path }/system/accuseRule/delete/"+id+"?industryType="+industryType;
		}
	});
}

//修改超链接的参数，以防止页面添加或者修改成功后，每次点击“下一页”超链接出现信息提示框
function updateHref() {
	$(".pagebanner > a").each(function() {
		var a =  $(this) ;
		var hrefstring  = a.attr("href") ;
		$.each(['&info=1'],function(i,n) {
			var i = hrefstring.search(n) ;
			if(i != -1) {
				hrefstring = hrefstring.replace(n,"") ;
				a.attr("href",hrefstring) ;
			}
		}) ;
	}) ;
}
function analysis(accuseRuleId,industryType){
    window.location = "${path }/system/accuseRule/analysis?accuseRuleId="+accuseRuleId+"&industryType="+industryType;
}
</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
<legend class="legend">量刑标准查询</legend>
	<form action="${path }/system/accuseRule/search" method="post">
		<input type="hidden" id="industryType" name="industryType" value="${accuseRule.industryType}"/>
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td width="12%" align="right">名称</td>
				<td width="20%" align="left"><input type="text" class="text" name="name" value="${accuseRule.name }" /></td>
				 <td width="12%" align="right">所属罪名</td>
				<td width="20%" align="left">
					<input type="text" name="accuseName" class="text" value="${accuseRule.accuseName }"/>
				</td> 
				<td width="36%" align="left" rowspan="2" valign="middle">
					<input type="submit" value="查 询" class="btn_query" />
				</td>
			</tr>
			<tr>
				
			</tr> 
		</table>
	</form>
</fieldset>
	<!-- 查询结束 -->
	<form:form action="${path }/system/accuseRule/addUI?industryType=${accuseRule.industryType }" method="post">
	<display:table name="list" uid="accuseRule" cellpadding="0" cellspacing="0" requestURI="${path }/system/accuseRule/search" >
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" class="btn_small" onclick="add(this.form)"/>
		</display:caption>
		<display:column title="序号" style="width:5%">
			<c:if test="${empty page ||page==0}">
			${accuseRule_rowNum}
		</c:if>
			<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + accuseRule_rowNum}
		</c:if>
		</display:column>
		<display:column title="名称" style="text-align:left;width:45%;">
			<a href="${path }/system/accuseRule/detail/${accuseRule.id}?industryType=${accuseRule.industryType }">${accuseRule.name}</a>&nbsp;&nbsp;
		</display:column>
		<display:column title="所属罪名" property="accuseName" style="text-align:left;width:25%;">
		</display:column>
		<display:column title="所属行业" property="industryName"style="text-align:left;width:15%;"></display:column>
		<display:column title="操作" style="width:10%;">
			<a href="${path }/system/accuseRule/updateUI/${accuseRule.id}?industryType=${accuseRule.industryType }">修改</a>&nbsp;&nbsp;
			<a href="javascript:;" onclick="del('${accuseRule.id}','${accuseRule.industryType }');">删除</a>
		    <a href="javascript:;" onclick="analysis('${accuseRule.id }','${accuseRule.industryType }');">分析数据</a>
		</display:column>
	</display:table>
	</form:form>
</div>

</body>
</html>