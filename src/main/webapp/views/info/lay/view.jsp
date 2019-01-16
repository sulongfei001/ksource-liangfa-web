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
<%-- <script type="text/javascript" src="${path }/resources/ckeditor/ckeditor.js"></script> --%>
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
	<c:if test="${back=='true'}">
	     <li>
	          <a href="${path}/home/main_default"  class="home">首页</a>
	     </li>
	      <li ><a href="${path}/info/lay/back?back=true&backType=${backType}">法律法规列表</a></li>
	</c:if>
     <c:if test="${back!='true'}">
    		<li ><a href="${path}/info/lay/back?backType=${backType}">法律法规列表</a></li>
     </c:if>

     	<li class="current"><a href="javascript:void();">法律法规详情</a></li>
</ul>
<div style="clear: both;"></div>
<div class="div_mainbody_style">
	<div align="center" id="main">
		<div class="detail_body">
			<div class="detail_title"><label id="_id2">${info.title }</label>
			</div>
			<div class="detail_rec">
				<p>
					<strong>分类：</strong><label id="_id4">${infoType.typeName}</label>&nbsp;&nbsp;
<%-- 					<strong>发布人：</strong><label id="_id4">${info.userName }</label>&nbsp;&nbsp; --%>
					<strong>录入部门:</strong><label id="_id4">${info.orgName }</label>&nbsp;&nbsp;
	
					<c:if test="${not empty info.publishDept }">
						<strong>发布部门:</strong><label id="_id4">${info.publishDept }</label>&nbsp;&nbsp;
					</c:if>
	
					<strong>发布日期:</strong><label id="_id6"><fmt:formatDate value="${info.createTime}" pattern="yyyy-MM-dd"/></label>&nbsp;&nbsp;
					<strong>实施日期:</strong><label id="_id6"><fmt:formatDate value="${info.implementDate }" pattern="yyyy-MM-dd"/></label>&nbsp;&nbsp;
					<c:if test="${empty info.implementDate}">尚未实施!</c:if>
					<strong>是否有效:</strong><label id="_id4">
						<c:choose>
							<c:when test="${info.isValid == 0 }">
								无效
							</c:when>
							<c:when test="${info.isValid == 1 }">
								有效
							</c:when>
							<c:otherwise>
								尚未填写!
							</c:otherwise>
						</c:choose>
					</label>&nbsp;&nbsp;
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