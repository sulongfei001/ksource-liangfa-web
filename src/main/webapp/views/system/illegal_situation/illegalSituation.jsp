<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>	
<script type="text/javascript">
$(function(){

	updateHref();
	
	var info = "${info}";
	if(info != null && info != ""){
		if(info == 'add'){
			$.ligerDialog.success('违法情形添加成功！');
		}
	    if(info == 'update'){
			$.ligerDialog.success('违法情形修改成功！');
		}
        if(info == 'del'){
            $.ligerDialog.success('违法情形删除成功！');
        }	    
	}
	
});


function add(form){
	var treeNode=window.parent.getSelectNode();
	if(typeof(treeNode)=='undefined'){
		$.ligerDialog.warn("请选择行业类型！");
	}else{
		if(treeNode.id=="-1"){
			$.ligerDialog.warn("请选择行业类型！");
		}else{
			form.action = "${path }/system/illegalSituation/addUI?industryType="+treeNode.id;
			form.submit();
			
		}
	}
}

function del(id,industryType){
	$.ligerDialog.confirm('确认删除吗?',function(flag){
		if(flag){
			window.location.href="${path }/system/illegalSituation/delete/"+id+"?industryType="+industryType;
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

</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
<legend class="legend">违法情形查询</legend>
	<form action="${path }/system/illegalSituation/search" method="post">
		<input type="hidden" id="industryType" name="industryType" value="${illegalSituation.industryType}"/>
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td width="12%" align="right">违法情形描述</td>
				<td width="20%" align="left"><input type="text" class="text" name="name" value="${illegalSituation.name }" /></td>
				<td width="12%" align="right">所属罪名</td>
				<td width="20%" align="left">
					<input type="text" name="accuseName" class="text" value="${illegalSituation.accuseName }"/>
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
	<form:form action="${path }/system/illegalSituation/addUI?industryType=${illegalSituation.industryType }" method="post">
	<display:table name="list" uid="illegalSituation" cellpadding="0" cellspacing="0" requestURI="${path }/system/illegalSituation/search" >
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" class="btn_small" onclick="add(this.form)"/>
		</display:caption>
		<display:column title="序号" style="width:5%">
			<c:if test="${empty page ||page==0}">
			${illegalSituation_rowNum}
		</c:if>
			<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + illegalSituation_rowNum}
		</c:if>
		</display:column>
		<<display:column title="违法情形描述" property="name" style="text-align:left;width:45%;"></display:column>
		<display:column title="所属罪名" property="accuseName" style="text-align:left;width:25%;">
			<%-- ${illegalSituation.accuseName} --%>
		</display:column>
		<display:column title="所属行业" property="industryName"style="text-align:center;width:15%;"></display:column>
		<display:column title="操作" style="width:10%;">
			<a href="${path }/system/illegalSituation/updateUI/${illegalSituation.id}?industryType=${illegal_Situation.industryType }">修改</a>&nbsp;&nbsp;
			<a href="javascript:;" onclick="del('${illegalSituation.id}','${illegalSituation.industryType }');">删除</a>
		</display:column>
	</display:table>
	</form:form>
</div>

</body>
</html>