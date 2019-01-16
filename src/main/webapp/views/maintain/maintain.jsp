<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/home.css"/>" />
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
<script src="<c:url value="/resources/jquery/jquery-ui-1.8.12.custom.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/jquery/layout/jquery.layout.js"/>"></script>
<script src="<c:url value="/resources/jquery/jquery.blockUI.js"/>"></script>
<script src="<c:url value="/resources/jquery/jsTree.v.1.0rc2/jquery.jstree.js"/>"></script>
<script src="<c:url value="/resources/script/home.js"/>"></script>
<script>
	$(function() {
		//初始化布局
		homeLayout.init("body","#tabs","#accordion",7);
		
		homeLayout.addToggleLayoutBtn('#toggleLayoutBtn');
		$("#accordion" ).accordion({
			fillSpace: true
		});
		$("#accordion div").jstree({
			"themes" : {
				"theme" : "apple"
				//,"dots" : false
				//,"icons" : false
			},
			"plugins" : [ "themes", "html_data"]
		}).bind('click.jstree',function (e) {
			var dom=e.target;
			if(dom.nodeName!='A'){
				return;
			}
			var title=dom.title;
			homeLayout.addTab(title,dom.href);
			return false;
		});
	});
</script>
<title>后台维护首页</title>
</head>
<body>
	<div class="ui-layout-north" >
	<div style="width:1280px; height:90px; display:block; float:left;"></div>
				</div>
	<div class="ui-layout-west">
		<div id="accordion">
			<h3 style=""><a href="javascript:void();">流程部署维护</a></h3>
			<div>
				<ul>
					<li><a href="<c:url value="/maintain/formmanage"/>"  title="表单模板管理">表单模板管理</a></li>
					<li><a href="<c:url value="/maintain/procKey"/>"  title="流程类型(key)维护">流程类型(key)维护</a></li>
					<li><a href="<c:url value="/maintain/procDeploy"/>"  title="流程部署">流程部署</a></li>
				</ul>		
			</div>
			<h3 style=""><a href="javascript:void();">流程管理</a></h3>
			<div>
				<ul>
					<li><a href="http://www.baidu.com"  title="流程管理">流程管理</a></li>
				</ul>		
			</div>
		</div>
	</div>
	<div class="ui-layout-center">
		<div id="tabs">
			<ul>
				<!-- <li><a href="#tabs-0">首 页</a> <span class="ui-icon ui-icon-close">刪除</span></li>-->
				<li><a href="#tabs-0">首 页</a></li>
			</ul>
			<div id="tabs-0" style="padding:0px;">
				  <iframe src="http://www.baidu.com" onload="homeLayout.iframeOnload(this);" id="iframe_tabs_0"
				  width="100%" height="100%" frameborder="0" name="right_window"></iframe>
			</div>
		</div>
		<div id="toggleLayoutBtn" class="ui-state-default ui-corner-all" style="position:absolute;right:10px;top:2px;padding:4px;cursor: pointer;" title="最大化">
			<span class="ui-icon ui-icon-newwin"></span>
		</div>
	</div>
	<div class="ui-layout-south">
		<div>
			<br/>
			版权所有：金明源信息技术有限公司<br/>
		</div>
	</div>
</body>
</html>