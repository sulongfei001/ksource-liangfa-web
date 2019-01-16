<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/noticedetail.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>

<script type="text/javascript" src="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.js"></script>
<script type="text/javascript" src="${path }/resources/script/miniui/miniui.js"></script>
<link href="<c:url value="/resources/script/miniui/themes/default/miniui.css"/>" rel="stylesheet" type="text/css" />
<link id="miniuiSkin" rel="stylesheet" type="text/css" href="${path }/resources/script/miniui/themes/blue/skin.css"/>
<link href="${path }/resources/script/miniui/themes/icons.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.css" />
<title>文章详细页面</title>
<style type="text/css">
	.mini-toolbar select,.mini-datagrid select{
		width: auto;
	}
	.mini-tools
	{
	    position:absolute;
	    top:3px;left:0px;
	}
</style>
<script type="text/javascript">
$(function(){
	 //按钮样式
	$("input:button,input:reset,input:submit").button();
    $('.xbreadcrumbs').xBreadcrumbs();
    
  //先隐藏图片，等比例缩小后，再显示图片
	var image = $('#image');
	var img = new Image();
	var width = 710 ;
	img.src = $('#image').attr('src');
	if(img.width>width){
		var scaling = img.height/img.width;
		image.width(width);
		image.height(width * scaling);
	}
	image.show();
});
</script>
</head>
<body>
<div id="layout1" class="mini-layout" style="width: 100%;">
     <div title="center" region="center" style="z-index:0;">
 	<div>
		<ul class="xbreadcrumbs" id="breadcrumbs-3" style="margin-bottom: 5px;">
		     <li>
			 	<a href="${path}/cms/content/back">文章列表</a>
		     </li>
		     <li class="current"><a href="javascript:void();">文章详情</a></li>
		</ul><br/>
		</div>
		<div class="div_mainbody_style">
			<div align="center" id="main">
				<div class="detail_body">
					<div class="detail_title"><label id="_id2" style="color: ${content.titleColor}; ${content.isBold eq 1?'font-weight: bold;':'' } ">${content.title}</label>
					</div>
					<div class="detail_rec">
						<p>
							<strong>发布人：</strong><label id="_id4">${content.crateUserName}</label>&nbsp;&nbsp;
							<label id="_id4">（${content.orgName}）</label>&nbsp;&nbsp;
							<strong>发布时间：</strong><label id="_id6"><fmt:formatDate value="${content.createTime}" pattern="yyyy-MM-dd"/></label>&nbsp;&nbsp;
						</p>
						<hr style="color: #CCCCCC">
					</div>
					<div class="content">
					${content.text}
					</div>
					<c:if test="${not empty content.imagePath }">
						<div style="text-align: left;">
						<img id="image" style="display: none;" src="${content.imagePath }"/>
						</div>
					</c:if>
					<c:if test="${!empty attachments}">
					<div class="cont_page" id="noPrint"  style="line-height: 24px;font-family: '宋体';color: #414141;font-size: 12px;text-align: left;">
					附件列表：
		                 <c:forEach var="atta" items="${attachments}">
		                       <a href="${path}/download/cmsContentFile/${atta.attachmentId}"
		                           title="点击下载${atta.name}">${atta.name}
		                        </a>&nbsp;&nbsp;&nbsp;&nbsp;
		                </c:forEach> 
				</div>
				</c:if>
				</div>
			</div>
		</div>
    </div>
</div> 
<script type="text/javascript">
	$("#layout1").height($(window).height());

    mini.parse();

    var layout = mini.get("layout1");
	
	var read_grid = mini.get("datagrid1");
	var notRead_grid = mini.get("datagrid2");
	read_grid.load();
	notRead_grid.load();
	
	function onDataRenderer(e){//miniUI的默认dataFormat不能解析时间（毫秒）
		var date = new Date();
		date.setTime(e.value);
		return mini.formatDate ( date, "yyyy-MM-dd" );
	}
	function onTitleRenderer(e){
		var title = e.value;
		return title.length>30?title.substring(0,30)+"...":title;
	}
</script>   
</body>
</html>