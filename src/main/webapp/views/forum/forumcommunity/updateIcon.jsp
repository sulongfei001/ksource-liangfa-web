<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/jquery-jcrop-0.9.9/css/jquery.Jcrop.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-jcrop-0.9.9/js/jquery.Jcrop.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		//记得放在jQuery(window).load(...)内调用，否则Jcrop无法正确初始化		
		$("#xuwanting").Jcrop({
			onChange : showPreview,
			onSelect : showPreview,
			aspectRatio : 1
		});
	});
	//简单的事件处理程序，响应自onChange,onSelect事件，按照上面的Jcrop调用	
	function showPreview(coords) {
		if (parseInt(coords.w) > 0) {
			//计算预览区域图片缩放的比例，通过计算显示区域的宽度(与高度)与剪裁的宽度(与高度)之比得到		
			var rx = $("#preview_box").width() / coords.w;
			var ry = $("#preview_box").height() / coords.h;
			//通过比例值控制图片的样式与显示	
			$("#crop_preview").css({
				width : Math.round(rx * $("#xuwanting").width()) + "px",
				//预览图片宽度为计算比例值与原图片宽度的乘积	
				height : Math.round(rx * $("#xuwanting").height()) + "px",
				//预览图片高度为计算比例值与原图片高度的乘积		
				marginLeft : "-" + Math.round(rx * coords.x) + "px",
				marginTop : "-" + Math.round(ry * coords.y) + "px"
			});
			$('#iconStyle').val($("#crop_preview").attr('style'));
		}
	}
</script>
<style type="text/css">
.crop_preview {
	position: absolute;
	left: 520px;
	top: 100px;
	width: 32px;
	height: 32px;
	overflow: hidden;
}
.iconTip{
	position: absolute;
	left: 515px;
	top: 140px;
}
.tip{
 color:red; 
}
</style>
</head>
<body>
<div class="panel">
<fieldset class="fieldset" >
    <p class="tip">
    　　  保存成功，请剪切图标...
    </p>
	<div>
    	<img id="xuwanting" src="${url}" />
        <span id="preview_box" class="crop_preview">
        <img id="crop_preview" src="${url}" />
        </span>
        <div class="iconTip">小图标</div>
        <div>原有图标</div>
    </div>
	<form action="${path}/forumCommunity/updateIcon">
		<input type="hidden" name="id" value="${id}" /> 
		<input type="hidden" id="iconStyle" name="iconStyle" />
		<input type="submit" value="保&nbsp;存" class="btn_small"/>
	</form>
	</fieldset>
</div>
</body>
</html>