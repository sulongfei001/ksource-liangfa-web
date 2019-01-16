<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/lg/css/ligerui-all.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/lucene.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/layout/jquery.layout.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/lg/ligerui.min.js"></script>

<!-- 加载插件 -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/loading/load.css" />
<script type="text/javascript" src="${path }/resources/jquery/loading/load-min.js"></script>

<title>Insert title here</title>
<script type="text/javascript">

$(function(){
	//为类型绑定事件
	$(".typeItems>li").click(function(){
		var li = $(this);
		$(".typeItems>li").removeClass("typeItems_list_on");
		li.addClass("typeItems_list_on");
		$("#typeId").val(li.val());
	});
});


function query(){
	$("#queryForm").submit();
	$.mask_fullscreen();
}


</script>
<style>
	.ZJleixingmc{
		display: inline-block;
		vertical-align: middle;
		width: 800px; 
		height: 400px;
	}
</style>
</head>
<body style="width: 100%;">
<form:form action="${path }/info/lay/laySearch" method="post" modelAttribute="info"  id="queryForm" class="Display_area">
	 <div style="display: inline-block;"></div>
	 <div class="ZJleixingmc" style="">
		<img class="logo" src=" ${path}/resources/images/legalInspection2.png" alt="法律检索" />      
    	<div class="zjLxmingcRig">
    		<ul class="typeItems">
    		    <li value="0" class="typeItems_list typeItems_list_on">全部</li>
        		<c:forEach items="${queryLayTypes}" var="type">
        			<li class="typeItems_list" value="${type.typeId}">${type.typeName}</li>
        		</c:forEach>
    		</ul>
    	</div>
    	<div class="sousuoquyu">
	      	<input id="title" name="title" type="text" class="SerchssINput" style="padding: 1px; margin-left: 0px; text-indent: 40px;" value="${info.title }"  />
		    <input id="typeId" name="typeId" type="hidden" value="0">
		    <input type="button" onclick="query()" id="frm_0" value="搜索" class="chaXun" />    		
	    </div>
     </div>
</form:form>
</body>
<script>
	window.onresize = function() {
			var wh = $(window).height();
			var body = wh-100;
			$(".Display_area").css({
					"height": body + "px",
					"line-height": body + "px"
			});
		};
		$(document).ready(function() {
			var wh = $(window).height();
			var body = wh-100;
			$(".Display_area").css({
					"height": body + "px",
					"line-height": body + "px",
					"text-align":"center"
			});
		});
</script>
</html>