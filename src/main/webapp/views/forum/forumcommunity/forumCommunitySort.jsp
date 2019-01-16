<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
<title>论坛首页</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/forum.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js"	type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script type="text/javascript">
$(function(){ 
		sort() ;
	}); 
	
	//排序功能 
	function sort() {
		var sortable = $( ".con" ) ;
		var savebutton = $("#savebutton") ;
		//不能使用以下定义变量 ，因为在排序前jquery得到这个class为bbs_son的对象 
		//排序后，如果还是用这个变量，这个变量仍然存这排序前的对象 ，所以的得到的顺序是未排序前的。
	//	var bbs_son = $(".bbs_son") ;
		
		sortable.sortable();
		sortable.disableSelection();
		$(".bbs_son").hover(function() {
				$(this).css({cursor:"move", border: "1px dashed #FF580A"}) ;	
			},function() {
				$(this).removeAttr("style") ;	
		}) ;
		savebutton.click(function() {
			var newArray = new Array() ;
			$(".bbs_son").each(function(index) {
				newArray[index] = $(this).find("#sortID").val()+ "/" +(index+1) ;
			}) ;
			
			var url = "${path}/forumCommunity/forumCommunitySave?newArray=" + newArray ;
			$.getJSON(url,function(data){
				 if(data.result == true) {
					 $.dialog({
						 content:'保存成功！',
						 time: 2
					 });
				 }
			}) ;
		}) ;
	}
	
	function back() {
		window.location.href = "${path}/forumCommunity/back" ;
	}
</script>
<style type="text/css">
.themeTitle{
position:relative; float: left; top: -20px;    font-size: 13px;left: 0;padding-bottom: 6px;
}
.ellipsis{
	text-overflow: ellipsis; 
	overflow: hidden;
	white-space:nowrap;
	width:350px;
}
.crop_preview {
	width: 32px;
	height: 32px;
	overflow: hidden;
	display: inline-block;
}
</style>
</head>
<body>
	<div id="page">
		<div class="clearfix" id="content">
			<div id="main">
			<span>
					<input type="button" class="btn_small" value="返&nbsp;回" onClick="back()" >
					<input type="button" class="btn_small" id="savebutton" value="保&nbsp;存" >
					<br>
					<br>
			</span>
				<div class="bbs_fath">
					<h2>
						<span class="left" style="float:left;">
						<img
							src="${path}/resources/images/bbs_ico_normal_new_16.png"
							alt="论坛版块"> </span>
							<span class="title" style="float:left;">论坛版块排序(<span style="color:#FF580A; font-size: 13px;font-weight: normal;">点击论坛版块，进行拖拽排序</span>)</span> <span class="amount">${themeCount}主题，${replyCount}回帖</span>
					</h2>
					<div class="con" style="overflow:hidden;width:100%;border-top-color: white;margin:0px ;">
							<c:forEach var="ele" items="${forumCommunityList}" varStatus="s">
						    	 	<dl class="bbs_son">
						    			<dt>
						    			    <span class="crop_preview"><img id="crop_preview" src="${ele.iconPath}" style="${ele.iconStyle}" alt="${ele.name}"/></span>
						    				<input type="hidden" value="${ele.id}" id="sortID">
						    			</dt>
						    			<dd>
						    				 <strong >
						    				 	<span style=" color: #006699">${ele.name}</span>
						    				 </strong>
						    				 <span class="amount">
												<c:choose>
												<c:when test="${empty ele.themeCount}">
												0
												</c:when>
												<c:otherwise>
												${ele.themeCount}
												</c:otherwise>
												</c:choose>
												主题 /
												<c:choose>
												<c:when test="${empty ele.replyCount}">
												0
												</c:when>
												<c:otherwise>
												${ele.replyCount}
												</c:otherwise>
												</c:choose>
												回帖</span><br/>
						    			</dd>
						    		</dl>
						    </c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>