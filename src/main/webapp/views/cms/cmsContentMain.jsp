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
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script
	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<title>网站文章管理主页</title>
<script type="text/javascript">

	function upd(contentId) {
		location.href = "${path}/cms/content/updateUI?contentId=" + contentId;
	}
	function dtop(contentId) {
		location.href = "${path}/cms/content/top?contentId=" + contentId;
	}
	function nutop(contentId) {
		location.href = "${path}/cms/content/nutop?contentId=" + contentId;
	}
	function checkAll(obj){
		$("[name='check'][id='check']").attr("checked",obj.checked); 
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
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">文章查询</legend>	
	<form:form action="${path}/cms/content/getContentByChannelId" method="post"  modelAttribute="content">
			<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
				<tr>
					<td width="8%" align="right">标题：</td>
					<td width="20%" align="left"><input name="title" class="text" value="${content.title }" />
					<input type="hidden" name="channelId" value="${content.channelId }" />
					</td>
					<td width="12%" align="right">发布时间：</td>
				<td width="20%" align="left">
					<form:input path="createStartTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width:34%"/>
					至<form:input path="createEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width:34%"/>
				</td>
					<td width="16%" align="left" valign="middle">
						<input type="submit" value="查 询" class="btn_query" />
					</td>
				</tr>
			</table>
		</form:form>
</fieldset>
	<form:form id="delForm" action="${path}/cms/content/batch_delete" method="post">
		<display:table name="contents" uid="content" cellpadding="0"
			cellspacing="0" requestURI="${path}/cms/content/getContentByChannelId">
			<c:if test="${content.channelFrom == 0 || content.channelFrom == null  }">
				<display:caption class="tooltar_btn">
					<input type="submit" class="btn_big" value="批量删除" name="del" onclick="return batchDelete('check')"/>
				</display:caption>
			</c:if>
			<display:column	title="<input type='checkbox' onclick='checkAll(this)'/>">
				<input type="checkbox" name="check" value="${content.contentId}" 
				<c:if test="${loginUser.organise.districtCode > content.userDistriceCode }">disabled</c:if> 
				<c:if test="${loginUser.organise.districtCode <= content.userDistriceCode }">id='check'</c:if>
				<c:if test="${loginUser.organise.districtCode == null}">id='check'</c:if>/>
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
				<a href="${path}/cms/content/display?contentId=${content.contentId}"
					title="${content.title}">${fn:substring(content.title,0,30)}${fn:length(content.title)>30?'...':''}</a>
			</display:column>
			<display:column title="发布人" property="crateUserName"
				style="text-align:left;"></display:column>
			<display:column title="发布单位" property="orgName"
				style="text-align:left;"></display:column>
			<display:column title="发布时间" style="text-align:left;">
				<fmt:formatDate value="${content.createTime}" pattern="yyyy-MM-dd" />
			</display:column>
			<display:column title="操作">
			<c:if test="${content.channelFrom == 0 || content.channelFrom == null  }">
				<c:if test="${loginUser.organise.districtCode <= content.userDistriceCode || loginUser.organise.districtCode == null }">
					<a href="javascript:upd(${content.contentId})">修改</a>&nbsp;
					<a href="javascript:;"
						onclick="top.art.dialog.confirm('确认要删除吗？',function(){location.href = '${path}/cms/content/delete?contentId=${content.contentId}';})">删除</a>
					 <c:if test="${content.top == 1 }">&nbsp;<a href="javascript:nutop(${content.contentId})">取消置顶</a></c:if>
					  <c:if test="${content.top != 1 }">&nbsp;<a href="javascript:dtop(${content.contentId})">置顶</a></c:if>
				</c:if>
			</c:if>
			<c:if test="${content.channelFrom != 0 && content.channelFrom != null }"><span style="color: red">文章来自系统其他模块</span> </c:if>
			</display:column>
		</display:table>
	</form:form>
</div>

</body>
</html>