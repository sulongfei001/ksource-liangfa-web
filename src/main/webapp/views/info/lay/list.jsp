<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
$(function(){
/* 
	$("#infoAdd").click(function (){
		window.location.href="${path}/info/lay/add";
	}); */
	
	$(".checkInp").change(function (){
		var checks = $(".checkInp");
		var selchcks = $(".checkInp:checked");
		if(checks.size() == selchcks.size()){
			$("#checkA").attr("checked",true);
		}else{
			$("#checkA").attr("checked",false);
		}
	});
	
});
function add(typeId){
	window.location.href="${path}/info/lay/add.do?typeId="+typeId;
}
function checkChange(){
/*    if($.trim($('#title').val()).length==1){
     top.art.dialog.alert("为更精确的查询请至少输入一组中文词语!");
     return false;
   } */
   var value =$('#changeSearch').val();
   $('#title').attr('name',value);
   return true;
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
	<legend class="legend">法律法规查询</legend>
	<form:form action="${path }/info/lay/manage" method="post" modelAttribute="info" onsubmit="return checkChange()">
		<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
		
		
<%-- 
		<td style="width: 12%;text-align: right;">对象:</td>
		<td style="width: 25%;text-align: left;">
			<select id="changeSearch" name="changeSearch">
					<option value="title"  <c:if test="${param.changeSearch eq 'title' }">selected="selected"</c:if>>标题</option>
					<option value="content" <c:if test="${param.changeSearch eq 'content'}">selected="selected"</c:if>>内容</option>
			</select> 
		</td>
--%>
			
		<td style="width: 12%;text-align: right;">
				关键词:
		</td>
		<td style="width: 48%;text-align: left;" >
				<c:choose>
				<c:when test="${empty info.title}">
				<input name="title" id="title" style="width: 70%" class="text" value="${info.content}"/>
				</c:when>
				<c:otherwise>
				<input name="title" id="title" style="width: 70%" class="text" value="${info.title}"/>
				</c:otherwise>
				</c:choose>
<!-- 				<font color="red">*注：多关键词以空格分隔</font> -->
			<input name="changeSearch" type="hidden" value="content" />
		</td>
		
		<td style="width: 10%;text-align: right;">类型名称:</td>
		<td style="width: 30%;text-align: left;">
			    <form:select path="typeId">
					<form:option value="">--全部--</form:option>
					<c:forEach var="infoType" items="${infoTypes}">
						<form:option value="${infoType.typeId}">${infoType.typeName}</form:option>
					</c:forEach>
				</form:select>
		</td>
		<td width="26%" align="left" rowspan="2" valign="middle">
			<input type="submit" value="查&nbsp;询" class="btn_query" />
		</td>
		</tr>
			<tr>
			
				
			</tr>
		</table>
	</form:form>

	</fieldset>
	
<form:form id="delForm" method="post" action="${path }/info/lay/batch_delete">
	<display:table name="infos" uid="infol" cellpadding="0"
		cellspacing="0" requestURI="${path }/info/lay/manage">
		<display:caption class="tooltar_btn">
			<input type="button" class="btn_small" value="添&nbsp;加" id="infoAdd" onclick="add(${info.typeId})"/>
			<input type="submit" class="btn_big" value="批量删除" name="del"onclick="return batchDelete('check')"/>
		</display:caption>
		<display:column title="<input type='checkbox' id='checkA' onclick='checkAll(this)'/>">
			<input type="checkbox" name="check"   class="checkInp"  value="${infol.infoId}"/>
		</display:column>
		<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${infol_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + infol_rowNum}
			</c:if>
		</display:column>
		<display:column title="标题" style="text-align:left; width:30%" class="cutout">
			<a href="${path }/info/lay/view?infoId=${infol.infoId}" title="${infol.title}">${fn:substring(infol.title,0,30)}${fn:length(infol.title)>30?'...':''}</a>
		</display:column>
		<display:column title="类型名称" >${infol.typeName}</display:column>
		<display:column title="发布人" property="userName"></display:column>
		<display:column title="发布部门" property="publishDept" ></display:column>
		<display:column title="发布日期" >
			<fmt:formatDate value="${infol.createTime }" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="操作">
			<a href="${path }/info/lay/update?infoId=${infol.infoId}">修改</a>
			<a href="javascript:;" onclick="top.art.dialog.confirm('确认此法律法规删除吗？',function(){location.href = '${path }/info/lay/delete?infoId=${infol.infoId}';})">删除</a>
		</display:column>
	</display:table>
	</form:form>
</div>
</body>
</html>