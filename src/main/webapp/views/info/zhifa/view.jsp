<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/noticedetail.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.js"></script>
<link rel="stylesheet" href="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.css" />
<script type="text/javascript">
$(function(){
	 //按钮样式
	$("input:button,input:reset,input:submit").button();
    $('.xbreadcrumbs').xBreadcrumbs();
});
</script>
</head>
<body>
<ul class="xbreadcrumbs" id="breadcrumbs-3" style="margin-bottom: 5px;margin-left: 10px;width: 98%;">
	     <li ><a href="${path}/info/zhifa/back?backType=${backType}">执法动态列表</a></li>
     	<li class="current"><a href="javascript:void();">执法动态详情</a></li>
</ul>
<div style="clear: both;"></div>
<div class="div_mainbody_style">
	<div align="center" id="main">
		<div class="detail_body">
			<div class="detail_title"><label id="_id2">${info.title }</label>
			</div>
			<div class="detail_rec">
				<p>
					<strong>分类：</strong><label id="_id4">${zhifaType.typeName}</label>&nbsp;&nbsp;
					<strong>发布人：</strong><label id="_id4">${user.userName }</label>&nbsp;&nbsp;
					<strong>发布部门：</strong><label id="_id4">${org.orgName }</label>&nbsp;&nbsp;
					<strong>发布时间：</strong><label id="_id6"><fmt:formatDate value="${info.createTime}" pattern="yyyy-MM-dd"/></label>&nbsp;&nbsp;
				</p>
				<hr style="color: #CCCCCC"/>
			</div>
			<div class="content">
			${info.content}
			</div>
			<div class="detail_bottom">
			</div>
		</div>
	</div>
</div>

</body>
</html>