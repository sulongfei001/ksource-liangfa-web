<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
	$(function() {

		$("#infoAdd").click(function() {
			window.location.href = "${path}/info/zhifa/add";
		});
	});
	function add() {
		window.location.href = "toView.do?view=${path}/info/zhifa/add";
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

	function checkAll(obj){
	jQuery("[name='check']").attr("checked",obj.checked) ; 			
	}	
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">执法动态查询</legend>
	<form:form action="${path }/info/zhifa/manage" method="post"
		modelAttribute="info">
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td style="width: 12%; text-align: right;">标题:</td>
				<td style="width: 20%; text-align: left;"><form:input
						path="title" class="text" /></td>
				<td style="width: 12%; text-align: right;">类型名称:</td>
				<td style="width: 20%; text-align: left;"><form:select
						path="typeId">
						<form:option value="">--全部--</form:option>
						<c:forEach var="zhifaInfoType" items="${zhifaInfoTypes}">
							<form:option value="${zhifaInfoType.typeId}">${zhifaInfoType.typeName}</form:option>
						</c:forEach>
					</form:select>
				</td>
				<td width="36%" align="left" rowspan="2" valign="middle">
					<input type="submit" value="查 询" class="btn_query" />
				</td>
			</tr>
		</table>
	</form:form>
</fieldset>
	<form:form id="delForm" action="${path }/info/zhifa/batch_delete" method="post">
	<display:table name="infos" uid="info" cellpadding="0" cellspacing="0"
		requestURI="${path }/info/zhifa/manage">
		<display:caption class="tooltar_btn">
			<input type="button" class="btn_small" value="添&nbsp;加" id="infoAdd" onclick="add()" />
			<input type="submit" class="btn_big" value="批量删除" name="del"onclick="return batchDelete('check')"/>
		</display:caption>
		<display:column
			title="<input type='checkbox' onclick='checkAll(this)'/>">
			<input type="checkbox" name="check" value="${info.infoId}" />
		</display:column>
		<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${info_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + info_rowNum}
			</c:if>
		</display:column>
		<display:column title="标题" style="text-align:left;width:30%" class="cutout">
			<a href="${path }/info/zhifa/view?infoId=${info.infoId}" title="${info.title}">${fn:substring(info.title,0,30)}${fn:length(info.title)>30?'...':''}</a>
		</display:column>
		<display:column title="类型名称" style="text-align:left;">${info.typeName}</display:column>
		<display:column title="发布人" property="userName"
			style="text-align:left;"></display:column>
		<display:column title="发布单位" property="orgName"
			style="text-align:left;"></display:column>
		<display:column title="发布时间" style="text-align:left;">
			<fmt:formatDate value="${info.createTime }" pattern="yyyy-MM-dd" />
		</display:column>
		<display:column title="操作">
			<a href="${path }/info/zhifa/update?infoId=${info.infoId}">修改</a>
			<a href="javascript:;"
				onclick="top.art.dialog.confirm('确认删除此执法信息吗?',function(){location.href = '${path }/info/zhifa/delete?infoId=${info.infoId}';})">删除</a>
		</display:column>
	</display:table>
	</form:form>
</div>
</body>
</html>