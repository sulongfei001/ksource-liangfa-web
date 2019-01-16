<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<title>网站文章管理主页</title>
<script type="text/javascript">
	$(function() {
		$("#programaId")
		.change(
				function() {
					var programaId = $("#programaId").val();
					$("#typeId option:not(:first)").remove();
					if (programaId != "") {
						$.getJSON("${path}/website/maintain/webArticleType/webArticleTypeTree",{programaId : programaId}, callback);
					}

				});
		function callback(data) {
			var html = "";
			$.each(data,function(entryIndex,entry){
				 var option = document.createElement("OPTION");
				 $("#typeId")[0].appendChild(option);
			     option.text = entry.name;
			     option.value =entry.id;
			});
		}
	});
	function webArticleAdd() {
		window.location.href = "${path}/website/maintain/webArticle/addUI";
	}
	function upd(articleId) {
		location.href = "${path}/website/maintain/webArticle/updateUI?articleId=" + articleId;
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
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">文章查询</legend>	
		<form:form action="${path}/website/maintain/webArticle/main" method="post"
			modelAttribute="article">
			<table border="0" cellpadding="2" cellspacing="1" width="100%"
				class="searchform">
				<tr>
					<td width="8%" align="right">标题：</td>
					<td width="20%" align="left"><form:input path="articleTitle"
							class="text" /></td>
					<td width="8%" align="right">栏目：</td>
					<td width="20%" align="left">
						<form:select path="programaId">
							<form:option value="">--全部--</form:option>
							<c:forEach var="programa" items="${programas}">
								<form:option value="${programa.programaId}">${programa.programaName}</form:option>
							</c:forEach>
						</form:select>
					</td>
					<td width="8%" align="right">文章类型：</td>
					<td width="20%" align="left">
						<form:select path="typeId">
							<form:option value="">--全部--</form:option>
							<c:forEach var="articleType" items="${articleTypes}">
								<form:option value="${articleType.typeId}">${articleType.typeName}</form:option>
							</c:forEach>
						</form:select>
					</td>
					<td width="16%" align="left" valign="middle">
						<input type="submit" value="查 询" class="btn_query" />
					</td>
				</tr>
			</table>
		</form:form>
</fieldset>
	<form:form id="delForm" action="${path}/website/maintain/webArticle/batch_delete" method="post">
		<display:table name="articleList" uid="article" cellpadding="0"
			cellspacing="0" requestURI="${path}/website/maintain/webArticle/main">
			<display:caption class="tooltar_btn">
				<input type="button" class="btn_small" value="添&nbsp;加" onclick="webArticleAdd()" />
				<input type="submit" class="btn_big" value="批量删除" name="del" onclick="return batchDelete('check')"/>
			</display:caption>
			<display:column	title="<input type='checkbox' onclick='checkAll(this)'/>">
				<input type="checkbox" name="check" value="${article.articleId}" />
			</display:column>
			<display:column title="序号">
				<c:if test="${page==null || page==0}">
				${article_rowNum}
			</c:if>
				<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + article_rowNum}
			</c:if>
			</display:column>
			<display:column title="标题" style="text-align:left;width:30%"
				class="cutout">
				<a href="${path}/website/maintain/webArticle/display?articleId=${article.articleId}"
					title="${article.articleTitle}">${fn:substring(article.articleTitle,0,30)}${fn:length(article.articleTitle)>30?'...':''}</a>
			</display:column>
			<display:column title="栏目" property="programaName"
				style="text-align:left;"></display:column>
			<display:column title="文章类型" property="typeName"
				style="text-align:left;"></display:column>

			<display:column title="发布人" property="userName"
				style="text-align:left;"></display:column>
			<display:column title="发布单位" property="orgName"
				style="text-align:left;"></display:column>
			<display:column title="发布时间" style="text-align:left;">
				<fmt:formatDate value="${article.articleTime}" pattern="yyyy-MM-dd" />
			</display:column>
			<display:column title="排序" property="sort"
				style="text-align:left;"></display:column>
			<display:column title="操作">
				<a href="javascript:upd(${article.articleId})">修改</a>&nbsp;
				<a href="javascript:;"
					onclick="top.art.dialog.confirm('确认要删除吗？',function(){location.href = '${path}/website/maintain/webArticle/delete?articleId=${article.articleId}';})">删除</a>
			</display:column>
		</display:table>
	</form:form>
</div>

</body>
</html>