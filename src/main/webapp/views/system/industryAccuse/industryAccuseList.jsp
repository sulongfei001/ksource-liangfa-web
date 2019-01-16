<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>	
<script type="text/javascript">
$(function(){
	<c:if test="${info==1}">
		art.dialog.tips('删除成功!',2);
	</c:if>
	updateHref();
});


function add(form){
	form.action = "${path }/system/industryAccuse/addUI?industryType=${industryAccuse.industryType}&accuseId=${industryAccuse.accuseId}";
	form.submit();
}

function del(accuseId,industryType){
	top.art.dialog.confirm('确认删除吗?',function(){
		window.location.href="${path }/system/industryAccuse/delete?industryType="+industryType+"&accuseId="+accuseId;
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
<legend class="legend">行业常用罪名查询</legend>
	<form action="${path }/system/industryAccuse/search" method="post">
		<input type="hidden" id="industryType" name="industryType" value="${industryAccuse.industryType}"/>
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td width="12%" align="right">罪名</td>
				<td width="20%" align="left">
					<input type="text" name="accuseName" class="text" value="${industryAccuse.accuseName }"/>
				</td>
				<td width="36%" align="left" valign="middle">
					<input type="submit" value="查 询" class="btn_query" />
				</td>
			</tr>
		</table>
	</form>
</fieldset>
	<!-- 查询结束 -->
	<form:form action="${path }/system/industryAccuse/addUI?industryType=${industryAccuse.industryType }" method="post">
	<display:table name="list" uid="industryAccuse" cellpadding="0" cellspacing="0" requestURI="${path }/system/industryAccuse/search" keepStatus="true">
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" class="btn_small" onclick="add(this.form)"/>
		</display:caption>
		<display:column title="序号" style="width:5%">
			<c:if test="${empty page ||page==0}">
			${industryAccuse_rowNum}
			</c:if>
			<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + industryAccuse_rowNum}
		</c:if>
		</display:column>
		<display:column title="罪名"  style="text-align:left;">
			<a href="javascript:;" id="${industryAccuse.accuseId }" title="${fn:escapeXml(industryAccuse.law)}">${industryAccuse.accuseName }</a>
 				<script type="text/javascript">
				if(${not empty industryAccuse.law }){
	 				$("#${industryAccuse.accuseId }").poshytip({
			            className: 'tip-yellowsimple',
			            slide: false,
			            fade: false,
			            alignTo: 'target',
			            alignX: 'right',
			            alignY: 'center',
			            offsetX:10,
			            allowTipHover:true 
			        });
				}
				</script>
		</display:column>
		<display:column title="条款" property="clause" style="text-align:left;"></display:column>
		<display:column title="所属行业" property="industryName" style="text-align:center;"></display:column>
		<display:column title="操作" style="width:7%">
			<a href="javascript:;" onclick="del('${industryAccuse.accuseId}','${industryAccuse.industryType }');">删除</a>
		</display:column>
	</display:table>
	</form:form>
</div>

</body>
</html>