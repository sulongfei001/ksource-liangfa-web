<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"	type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script type="text/javascript">

// 返回版块列表页面 
function back(){
	window.location.href='<c:url value="/forumCommunity/back"/>';
	}
	
//处理日期 ,这个方法不再使用 
function date() {
	var date = new Date('${fcy.createTime}') ;
	var valDate = date.getFullYear() + "-" + (date.getMonth()+1) + "-"  + (date.getDate()-1) ;
	$("input[name='createTime']").val(valDate) ;
}
</script>
<style type="text/css">
.crop_preview {
	width: 32px;
	height: 32px;
	overflow: hidden;
	display: inline-block;
}
</style>
</head>
<body>
<div class="panel">
		<table  class="blues">
		<tr>
			<td class="tabRight" style="width: 20%">
				版块名称
			</td>
			<td style="text-align: left;">
				${fcy.name}
			</td>
		</tr>
		<tr>
			<td class="tabRight"  width="20%">创建时间</td>
			<td  style="text-align: left;">
				<fmt:formatDate value="${fcy.createTime}" pattern="yyyy-MM-dd"/>
			</td>
		</tr>
		<tr>
			<td  class="tabRight" width="20%">
				版块图标
			</td>
			<td style="text-align: left;">
				<c:if test="${empty fcy.iconPath}">
				无图标
				</c:if>
				<c:if test="${!empty fcy.iconPath}">
				<div>
			        <span class="crop_preview">
			        <img id="crop_preview" src="${fcy.iconPath}" style="${fcy.iconStyle}"/></span>
   			    </div>
				</c:if>
			</td>
		</tr>
		<tr>
			<td  class="tabRight" width="20%">
				版块说明
			</td>
			<td style="text-align: left;">
				${fcy.note}
			</td>
		</tr>
	</table>
	<p style="text-align: center;">
		<input type="button" class="btn_small" value="返&nbsp;回" onclick="back()" />
	</p>
</div>
</body>
</html>