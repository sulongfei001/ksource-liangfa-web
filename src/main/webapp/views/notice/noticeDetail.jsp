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
<script type="text/javascript" src="${path }/resources/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.js"></script>
<script type="text/javascript" src="${path }/resources/script/miniui/miniui.js"></script>
<link href="<c:url value="/resources/script/miniui/themes/default/miniui.css"/>" rel="stylesheet" type="text/css" />
<link id="miniuiSkin" rel="stylesheet" type="text/css" href="${path }/resources/script/miniui/themes/blue/skin.css"/>
<link href="${path }/resources/script/miniui/themes/icons.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.css" />
<title>公告信息详述页面</title>
<style type="text/css">
	.mini-toolbar select,.mini-datagrid select{
		width: auto;
	}
	.mini-tools
	{
	    position:absolute;
	    top:3px;left:5px;
	}
    .mini-layout-region-title{
        position: relative;
        right: -20px;
    }
</style>
<script type="text/javascript">
$(function(){
	 //按钮样式
	$("input:button,input:reset,input:submit").button();
    $('.xbreadcrumbs').xBreadcrumbs();
});
</script>
</head>
<body>
  <div>
		<ul class="xbreadcrumbs" id="breadcrumbs-3" style="margin-bottom: 5px;">
		    <c:if test="${backType>=1}">
	            	<li>
	                	<a href="${path}/notice/back?backType=${backType}">通知公告列表</a>
	                </li>
	            </c:if>
		     <li class="current">通知公告详情</li>
		</ul>
		</div>
<div id="layout1" class="mini-layout" style="width: 98.5%;float:left;margin-left: 8px;">

     <div title="center" region="center" style="z-index:0;">

		<div >
			<div align="center" id="main">
				<div class="detail_body">
					<div class="detail_title"><label id="_id2">${notice.noticeTitle}</label>
					</div>
					<div class="detail_rec">
						<p>
							<strong>发布人：</strong><label id="_id4">${notice.userName}</label>&nbsp;&nbsp;
							<strong>发布部门：</strong><label id="_id4">${notice.orgName}</label>&nbsp;&nbsp;
							<strong>发布时间：</strong><label id="_id6"><fmt:formatDate value="${notice.noticeTime}" pattern="yyyy-MM-dd"/></label>&nbsp;&nbsp;
							<dic:getDictionary var="dictionary" groupCode="noticeLevel" dicCode="${notice.noticeLevel}"/>&nbsp;&nbsp;
						</p>
						<hr style="color: #CCCCCC">
					</div>
					<div class="content">
					${notice.noticeContent}
					</div>
					<div class="detail_bottom">
						<table border="0" cellpadding="0" cellspacing="0">
							<c:forEach items="${iDList}" var="noticeFile" varStatus="s">
								<c:choose>
									<c:when test="${s.index == 0}">
										<tr>
											<td style="font:italic bold 12px/30px arial,sans-serif; line-height: 20px;">附件:&nbsp;</td>
											<td align="left"><a href="${path}/download/noticeFile/${noticeFile.fileId}">${noticeFile.fileName}</a></td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td style="font:italic bold 12px/30px arial,sans-serif; line-height: 20px;">&nbsp;</td>
											<td align="left"><a href="${path}/download/noticeFile/${noticeFile.fileId}">${noticeFile.fileName}</a></td>
										</tr>
									</c:otherwise>
								</c:choose>
								
							</c:forEach>
							</table>
					</div>
				</div>
			</div>
		</div>

    </div>
      <div title="公告阅读详情" region="south" height="350px"  expanded="false">
        <div id="tabs1" class="mini-tabs" activeIndex="0" style="width:900px;height:320px;">
		    <div title="已查看通知公告的机构">
		       <div id="datagrid1" class="mini-datagrid" style="width:100%;" sizeList="[10]"
				    url="${path }/noticeRead/readNoticeList?noticeId=${noticeId}" >
				    <div property="columns">
				    	<div type="indexcolumn" headerAlign="center">序号</div>
						<div field="noticeTitle" width="200" headerAlign="center" renderer="onTitleRenderer">标题</div>
						<div field="orgName"  headerAlign="center">机构名称</div>
						<div field="userName"  headerAlign="center">用户名称</div>
						<div field="readTime"  headerAlign="center" renderer="onDataRenderer">查看时间</div>
					</div>
				</div>
		    </div>
		    <div title="未查看通知公告的机构" >
				<div id="datagrid2" class="mini-datagrid" style="width:100%;"
					allowResizeColumn="false" allowMoveColumn="false" sizeList="[10]"
				    url="${path }/noticeRead/notReadNoticeList?noticeId=${noticeId}" >
				    <div property="columns">
				    	<div type="indexcolumn" headerAlign="center">序号</div>
						<div field="noticeTitle" headerAlign="center" renderer="onTitleRenderer">标题</div>
						<div field="orgName" headerAlign="center">机构名称</div>
					</div>
				</div>
		    </div>
		</div>
    </div>
</div> 
<script type="text/javascript">
	$("#layout1").height($(window).height()-36);

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