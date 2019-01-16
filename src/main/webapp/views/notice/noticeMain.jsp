<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css"/>
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
	<script src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" language="JavaScript" type="text/JavaScript"></script>
<%-- <script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script> --%>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js" language="JavaScript" type="text/JavaScript"></script>
<script
	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<title>公告信息主页</title>
<script type="text/javascript">
	function noticeAdd() {
		window.location.href = "${path}/notice/addUI";
	}
	function upd(noticeId) {
		location.href = "${path}/notice/updateUI?noticeId=" + noticeId;
	}
	function con(noticeId) {
		art.dialog.open("${path}/notice/noticeOrg?noticeId=" + noticeId
				+ "&&redirect=false", {
			id : noticeId,
			title : '关联部门',
			height : 500,
			resize : false,
			lock : true,
			opacity : 0.1,// 透明度
			closeFn : function() {
				location.href = "${path}/notice/back";
			}
		}, false);
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
			$.ligerDialog.confirm("确认删除吗？",function(flag){
				if(flag){
					jQuery("#delForm").submit();
				}
				}
			);
		}else{
			$.ligerDialog.confirm("请选择要删除的记录!");
		}
		return false;
	}
	
	function del(noticeId){
		$.ligerDialog.confirm('确认删除吗?',function(flag){
			if(flag){
				window.location.href="${path }/notice/batch_delete?check="+noticeId;
			}
		});
	}

	function checkAll(obj){
	jQuery("[name='check']").attr("checked",obj.checked) ; 			
	}	
	
	function stop(noticeId){
		var noticeId = noticeId;
 		$.ligerDialog.confirm("确认要终止吗？", "提示信息", function(h) {
 			if(h){
				$.post("${path}/notice/noticeStop",{noticeId:noticeId},function(data){
				if(data){
					$.ligerDialog.success("终止成功！","提示信息",function(is){
						if(is){
							window.location.reload();
						}
						
					});
				}else{
					$.ligerDialog.error("终止失败！");
				}
			});
 			}
			});
	}
 	
 	function start(noticeId){
 		var noticeId = noticeId;
		$.ligerDialog.confirm("确认要生效吗？", "提示信息", function(h) {
			if(h){
			$.post("${path}/notice/noticeStart",{noticeId:noticeId},function(data){
				if(data){
					$.ligerDialog.success("生效成功！","提示信息",function(is){
						if(is){
							window.location.reload();
						}
						
					});
				}else{
					$.ligerDialog.error("生效失败！");
				}
			});
			}
		});

	}
	
</script>
<style type="text/css">
a {
    margin-right: 4px;
}
.green{
	color:green;
}
.red{
	color:red;
}
</style>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">通知公告查询</legend>
		<form action="${path}/notice/main" method="post" >
			<table border="0" cellpadding="2" cellspacing="1" width="100%"
				class="searchform">
				<tr>
					<td width="12%" align="right">标题：</td>
					<td width="20%" align="left">
						<input id="noticeTitle" name="noticeTitle" value="${param.noticeTitle}" class="text" /> 
					</td>
					<td width="36%" align="left" valign="middle">
						<input type="submit" value="查 询" class="btn_query" />
					</td>
				</tr>
			</table>
		</form>
</fieldset>
		
		<form:form id="delForm" action="${path}/notice/batch_delete" method="post">
		<display:table name="noticeList" uid="notice" cellpadding="0"
			cellspacing="0" requestURI="${path}/notice/main">
			<display:caption class="tooltar_btn">
				<input type="submit" class="btn_big" value="批量删除" name="del"onclick="return batchDelete('check')"/>
			</display:caption>
			<display:column
			title="<input type='checkbox' onclick='checkAll(this)'/>">
			<input type="checkbox" name="check" value="${notice.noticeId}" />
			</display:column>
			<display:column title="序号">
				<c:if test="${page==null || page==0}">
				${notice_rowNum}
			</c:if>
				<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + notice_rowNum}
			</c:if>
			</display:column>
			<display:column title="标题" style="text-align:left;width:45%" class="cutout">
				<a href="${path}/notice/searchDisplay?noticeId=${notice.noticeId}&backType=2" title="${notice.noticeTitle}">${fn:substring(notice.noticeTitle,0,30)}${fn:length(notice.noticeTitle)>30?'...':''}</a>
			</display:column>
			<dic:getDictionary var="dictionary" groupCode="noticeLevel"
				dicCode="${notice.noticeLevel}" />
			<display:column title="创建人" property="userName"
				style="text-align:center;width:20%"></display:column>
			<display:column title="创建时间" style="text-align:center;width:20">
				<fmt:formatDate value="${notice.noticeTime}" pattern="yyyy-MM-dd" />
			</display:column>
			<display:column title="状态" style="text-align:center;">
				<c:if test="${notice.isPublished==1}">
					<span class="green">已生效</span>
				</c:if>
				<c:if test="${notice.isPublished==2}">
					<span class="red">已终止</span>
				</c:if>
				<c:if test="${notice.isPublished==3}">
					<span class="red">未生效</span>
				</c:if>
			</display:column>
			<display:column title="操作" style="text-align:center;width:15%">
				<a href="javascript:upd(${notice.noticeId})">修改</a>
				<c:if test="${notice.isPublished==1}">
					<a href="javascript:;" onclick="stop('${notice.noticeId}')">终止</a>
				</c:if>
				<c:if test="${notice.isPublished==2}">
					<a href="javascript:;" onclick="start('${notice.noticeId}')">生效</a>
				</c:if>
				<c:if test="${notice.isPublished==3}">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
				<a href="javascript:;"
					onclick="del(${notice.noticeId})">删除</a>
			</display:column>
		</display:table>
		</form:form>
</div>
</body>
</html>