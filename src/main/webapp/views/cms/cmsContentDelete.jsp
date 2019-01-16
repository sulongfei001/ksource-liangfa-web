<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script
	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<title>网站文章管理主页</title>
<script type="text/javascript">

	function turnBack(contentId) {
		location.href = "${path}/cms/contentDelete/turnBack?contentId=" + contentId;
	}
	function checkAll(obj){
		jQuery("[name='check']").attr("checked",obj.checked) ; 			
	}
	function batchDelete(checkName){
		var flag ;
		var name ;
		for(var i=0;i<document.forms[1].elements.length;i++){
			
			name = document.forms[1].elements[i].name;
			if(name.indexOf(checkName) != -1){
				if(document.forms[1].elements[i].checked){
					flag = true;
					break;
				}
			}
		}   	
		if(flag){
			 top.art.dialog.confirm("确认删除吗？",
					function(){jQuery("#delForm").submit();}
			);
		}else{
			top.art.dialog.alert("请选择要删除的记录!");
		}
		return false;
	}
	
	jQuery(function(){
		
		//栏目树
		zTree=	jQuery('#dropdownMenu').zTree({
			isSimpleData: true,
			treeNodeKey: "id",
			treeNodeParentKey: "upId",
			async: true,
			asyncUrl:"${path}/cms/content/loadChildChannel?id=-1",
			asyncParam: ["id"],
			callback: {
				click: zTreeOnClick
			}
		});	
		//鼠标点击页面其它地方，栏目树消失
		jQuery("html").bind("mousedown", 
				function(event){
					if (!(event.target.id == "DropdownMenuBackground" || jQuery(event.target).parents("#DropdownMenuBackground").length>0)) {
						hideMenu();
					}
				});
	});

	function showMenu() {
		var cityObj = jQuery("#channelName");
		var cityOffset = jQuery("#channelName").offset();
		jQuery("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	}
	function hideMenu() {
		jQuery("#DropdownMenuBackground").fadeOut("fast");
	}

	function zTreeOnClick(event, treeId, treeNode) {
		if (treeNode) {		
			jQuery("#channelName").val(treeNode.name);
			jQuery("[name='channelId']").val(treeNode.id);
			hideMenu();
		}
	}
	
	function isClearChannel(){
		var value =jQuery("#channelName").val();
		if(jQuery.trim(value)==""){
		     jQuery("[name='channelId']").val("");
		}
	}
	
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">文章查询</legend>	
	<form:form action="${path}/cms/contentDelete/search" method="post" modelAttribute="content"  onsubmit="isClearChannel()">
			<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
				<tr>
					<td width="12%" align="right">文章标题：</td>
					<td width="20%" align="left"><input name="title" class="text" value="${content.title }" />
					<td width="12%" align="right">所在栏目：</td>
					<td width="20%" align="left">
					<input type="text" name="channelName" id="channelName" value="${content.channelName }" onfocus="showMenu(); return false;"  readonly="readonly" class="text"/>
					<input type="hidden" name="channelId" value="${content.channelId }"/>
					<a href="javascript:void();" onclick="javascript:document.getElementById('channelName').value = '';" class="aQking qingkong">清空</a>
					</td>
					<td width="36%" align="left" rowspan="2" valign="middle">
						<input type="submit" value="查 询" class="btn_query" />
					</td>
				</tr>
				<tr>
				<td width="12%" align="right">发布时间：</td>
				<td width="20%" align="left">
					<form:input path="createStartTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width:34%"/>
					至<form:input path="createEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width:34%"/>
				<input type="hidden" name="status" value="${content.status }"/>
				</td>
				</tr>
				
			</table>
		</form:form>
</fieldset>
	<form:form id="delForm" action="${path}/cms/contentDelete/real_batch_delete" method="post">
		<display:table name="contents" uid="content" cellpadding="0"
			cellspacing="0" requestURI="${path}/cms/contentDelete/search">
			<display:caption class="tooltar_btn">
				<input type="submit" class="btn_big" value="批量删除" name="del" onclick="return batchDelete('check')"/>
			</display:caption>
			<display:column	title="<input type='checkbox' onclick='checkAll(this)'/>">
				<input type="checkbox" name="check" value="${content.contentId}" />
			</display:column>
			<display:column title="序号">
				<c:if test="${page==null || page==0}">
				${content_rowNum}
			</c:if>
				<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + content_rowNum}
			</c:if>
			</display:column>
			<display:column title="标题" style="text-align:left;width:30%"
				class="cutout">
				<a href="${path}/cms/contentDelete/display?contentId=${content.contentId}"
					title="${content.title}">${fn:substring(content.title,0,30)}${fn:length(content.title)>30?'...':''}</a>
			</display:column>
			<display:column title="发布人" property="crateUserName"
				style="text-align:left;"></display:column>
			<display:column title="发布单位" property="orgName"
				style="text-align:left;"></display:column>
			<display:column title="所在栏目" property="channelName"
				style="text-align:left;"></display:column>
			<display:column title="发布时间" style="text-align:left;">
				<fmt:formatDate value="${content.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
			</display:column>
			<display:column title="操作">
				<a href="javascript:;"
					onclick="top.art.dialog.confirm('确认要删除吗？',function(){location.href = '${path}/cms/contentDelete/realDelete?contentId=${content.contentId}';})">彻底删除</a>
					&nbsp;&nbsp;<a href="javascript:turnBack(${content.contentId})">还原</a>
			</display:column>
		</display:table>
	</form:form>
	</div>
	<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:15%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
		<ul id="dropdownMenu" class="tree"></ul>
	</div>
</body>
</html>