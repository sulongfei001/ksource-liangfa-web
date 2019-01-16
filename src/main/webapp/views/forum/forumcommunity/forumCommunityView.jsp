<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/displaytagall.css"/>" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script type="text/javascript"  src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript"  src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript"  src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript"  src="${path}/resources/script/cleaner.js"></script>
<script	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js"	type="text/javascript"></script>
<script type="text/javascript">
$(function(){

	//日期插件,天[dd],月[mm],年[yyyy]
	var starTime = document.getElementById('starTime');
	starTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	var endTime = document.getElementById('endTime');
	endTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}	
	
	//提交查询 表单时候进行验证 
	validate() ;
});

//添加论坛版块
function add(){
	window.location.href="${path}/toView?view=/forum/forumcommunity/forumCommunityAdd";
}

//全选操作
function checkAll(obj) {
	$("input[name='check']").attr("checked",obj.checked) ;
}

//删除论坛版块
	function isDelete(){
		var checkID = new Array(); 
		var i = 0 ;
		 $("input[name='check']").each(function() {
			var checked = $(this).attr("checked") ;
			if(checked == 'checked') {
				checkID[i] = $(this).val();
				i++ ;
			}
		}) ; 
		if(i != 0){
			 top.art.dialog.confirm("确认删除吗？",
					//function(){$("#delForm").submit();} 
					function(){ajaxDel(checkID);}
			);
		}else{
			top.art.dialog.alert("请选择要删除的记录!");
		}
	}
	
	function ajaxDel(datas) {
		$.ajax({
			type: "post" ,
			url: "${path}/forumCommunity/del?check="+datas,
			success:callback		
		}) ;
	}
	
	function callback(data) {
		if(data != "")
			top.art.dialog.alert(data);
		window.location.href="${path}//forumCommunity/back" ;
	}
//清空操作 
function clearAll() {
	$(".searchform").find("input[type='text']").val("") ;
}

//提交查询 表单时候进行验证 
function validate() {
	jqueryUtil.formValidate({
		form:"ForumCommunityForm",
		rules:{
			id:{digits:true}
		},
		messages:{
			id:{digits: "只能输入整数"}
		},
		submitHandler:function(form){
			//提交表单
			form.submit();
		}
	});
}
function sortUI(){
	window.location.href="${path}/forumCommunity/forumCommunitySort";
}
</script>
<title>论坛版块管理</title>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">论坛版块查询</legend>
<div>
	<form:form  id="ForumCommunityForm" action="${path }/forumCommunity/check" method="post"  >
		<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="12%" align="right">版块名称</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="name" value="${forumCommunity.name}">
			</td>
			<td width="12%" align="right">创建时间</td>
			<td width="25%" align="left">
				<input type="text" class="starTime text" id="starTime" name="starTime" value="<fmt:formatDate value="${forumCommunity.starTime }" pattern="yyyy-MM-dd"/>" style="width:35%" />至
				<input type="text" class="endTime text" id="endTime" name="endTime" value="<fmt:formatDate value="${forumCommunity.endTime}" pattern="yyyy-MM-dd"/>" style="width:35%"  />
			</td>
			<td width="26%" align="left" valign="middle">
				<input type="submit" value="查&nbsp;询" class="btn_query" />
			</td>
		</tr>
	</table>
	</form:form>
</div>
</fieldset>

<form:form action="${path}/forumCommunity/del" method="post" id="delForm">
<display:table name="ForumCommunityControllerList"  uid="fcy" cellpadding="0" cellspacing="0">
		<display:caption class="tooltar_btn">
			<input type="button" class="btn_big" value="版块排序" id="sort" onclick="sortUI()"/>
			<input type="button" class="btn_small" value="添&nbsp;加" id="infoAdd" onclick="add()"/>
			<input type="button" class="btn_small" value="删&nbsp;除" id="infoDel" onclick="isDelete()"/>
		</display:caption>
	
	<display:column title="<input type='checkbox' onclick='checkAll(this)'/>" style="width: 5%">
	 <c:choose>
		<c:when test="${empty fcy.themeCount or fcy.themeCount == 0}">
			<input type="checkbox" name="check" value="${fcy.id}" />
		</c:when>
		<c:otherwise>
			<input type="checkbox" disabled="disabled" title="此版块下有主题,不能删除" />
		</c:otherwise>
		</c:choose>   
	</display:column>
	
	<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${fcy_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*10 + fcy_rowNum}
			</c:if>
    </display:column>
	<display:column  title="版块名称" style="text-align:left;width:30%" class="cutout">
		<span title="${fcy.name}">${fn:substring(fcy.name,0,30)}${fn:length(fcy.name)>30?'...':''}</span>
	</display:column>
	<display:column  title="创建时间"  style="text-align:left;">
		<fmt:formatDate value="${fcy.createTime}" pattern="yyyy-MM-dd"/>
	</display:column>
	<%-- <display:column  title="状态" property="state" style="text-align:left;">
		<dict:getDictionary var="forumCommunityState" groupCode="forumCommunityState" dicCode="${fcy.state}" />
		<c:if test="${fcy.state==0 }">
			<font color="red">
		</c:if>
			${forumCommunityState.dtName}
		<c:if test="${fcy.state==0 }">
			</font>
		</c:if>
	</display:column> --%>
	<display:column title="操作">
			<a href="${path }/forumCommunity/checkandupdate?id=${fcy.id}&distinction=del">查看</a>
			<%-- <a href="javascript:void();" onclick="top.art.dialog.confirm('确认修改吗？',function(){window.location.href='${path }/forumCommunity/checkandupdate?id=${fcy.id}&distinction=update'});">修改</a>  --%>
		<a href="${path }/forumCommunity/checkandupdate?id=${fcy.id}&distinction=update">修改</a>
		</display:column>
</display:table>
</form:form>
</div>
</body>
</html>