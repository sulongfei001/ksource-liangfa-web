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
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>	
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">
$(function(){
	<c:if test="${info==1}">
		$.ligerDialog.success('删除成功！',"",function(){window.location.href="${path}/system/punishBasis/search?industryType="+${industryType};});
	</c:if>
	
	updateHref();
	
});


function add(form){
	var industryType = "${punishBasis.industryType}";
	if(industryType != ""){
		form.action = "${path }/system/punishBasis/addUI?industryType=${punishBasis.industryType}";
		form.submit();
	}else{
		/* art.dialog.alert("请选择行业类型！"); */
		$.ligerDialog.warn("请选择行业类型！");
	}
}

function del(basisId,industryType){
	$.ligerDialog.confirm('确认删除吗?',function(flag){
		if(flag){
			window.location.href="${path }/system/punishBasis/delete?basisId="+basisId+"&industryType="+industryType;
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
<legend class="legend">行政处罚依据查询</legend>
	<form action="${path }/system/punishBasis/search" method="post">
		<input type="hidden" id="industryType" name="industryType" value="${punishBasis.industryType}"/>
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td width="10%" align="right">行政处罚依据</td>
				<td width="30%" align="left"><input type="text" class="text" name="basisInfo" value="${punishBasis.basisInfo }" /></td>
				<td width="10%" align="right">条款</td>
				<td width="25%" align="left">
					<input type="text" name="clause" class="text" value="${punishBasis.clause}"/>
				</td>
				<td width="25%" align="left" rowspan="2" valign="middle">
					<input type="submit" value="查 询" class="btn_query" />
				</td>
			</tr>
		</table>
	</form>
</fieldset>
	<!-- 查询结束 -->
	<form:form action="${path }/system/punishBasis/addUI?industryType=${punishBasis.industryType }" method="post">
	<display:table name="list" uid="punishBasis" cellpadding="0" cellspacing="0" requestURI="${path }/system/punishBasis/search" >
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" class="btn_small" onclick="add(this.form)"/>
		</display:caption>
		<display:column title="序号" style="width:5%">
			<c:if test="${empty page ||page==0}">
			${punishBasis_rowNum}
		</c:if>
			<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + punishBasis_rowNum}
		</c:if>
		</display:column>
		<display:column title="行政处罚依据" style="text-align:left;width:40%;">
			<div id="t_${punishBasis.basisId}" title='${fn:escapeXml(punishBasis.basisInfo)}' >
				${fn:substring(punishBasis.basisInfo,0,30)}
				${fn:length(punishBasis.basisInfo)>30?'...':''} 
			</div>
			<script type="text/javascript">
				if(${not empty punishBasis.basisInfo }){
					$("#t_${punishBasis.basisId}").poshytip({
						className: 'tip-yellowsimple',
				        showTimeout: 1,
				      	slide: false,
				       	fade: false,
				       	alignTo: 'target',
				       	alignX: 'right',
				       	alignY: 'center',
				       	offsetX: 2,
				       	allowTipHover:true
					});
				}
			</script>
		</display:column>
		<display:column title="条款" property="clause" style="text-align:left;width:30%;">
		</display:column>
		<display:column title="所属行业" property="industryName"style="text-align:center;width:10%;"></display:column>
		<display:column title="操作" style="width:20%;">
			<a href="${path }/system/punishBasis/updateUI?basisId=${punishBasis.basisId}&industryType=${punishBasis.industryType }">修改</a>&nbsp;&nbsp;
			<a href="javascript:;" onclick="del('${punishBasis.basisId}','${punishBasis.industryType }');">删除</a>&nbsp;&nbsp;
			<a href="${path }/system/discretionStandard/search?basisId=${punishBasis.basisId}&industryType=${punishBasis.industryType }">裁量标准</a>
		</display:column>
	</display:table>
	</form:form>
</div>

</body>
</html>