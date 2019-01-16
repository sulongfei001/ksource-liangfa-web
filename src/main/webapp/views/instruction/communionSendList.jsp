<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<%-- <script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script> --%>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>	
<script type="text/javascript">
$(function(){
	updateHref();
	
	var sendTime = document.getElementById('sendTime');
	sendTime.onfocus = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	
	//表单提交后，根据提交结果，给出提示
    var info = "${info}";
    if(info != null && info != ""){
        if(info == 'del'){
            //正确提示
            $.ligerDialog.success('横向交流删除成功！');
        }
        if(info == 'update'){
            //正确提示
            $.ligerDialog.success('横向交流修改成功！');
        }
        if(info == 'add'){
            //正确提示
            $.ligerDialog.success('横向交流添加成功！');
        }
    }
});


function del(id){
	$.ligerDialog.confirm('确认删除吗?',function(flag){
		if(flag){
			window.location.href="${path }/instruction/communionSend/delete/"+id;
		}
	});
}

//修改超链接的参数，以防止页面添加或者修改成功后，每次点击“下一页”超链接出现信息提示框
function updateHref() {
	$(".pagebanner > a").each(function() {
		var a =  $(this) ;
		var hrefstring  = a.attr("href") ;
		$.each(['&info=1'],function(i,n) {
			var i = hrefstring.search(n) ;
			if(i != -1) {
				hrefstring = hrefstring.replace(n,"") ;
				a.attr("href",hrefstring) ;
			}
		}) ;
	}) ;
}

</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
<legend class="legend">工作交流查询</legend>
	<form action="${path }/instruction/communionSend/list" method="post">
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td width="12%" align="right">标题</td>
				<td width="20%" align="left">
					<input type="text" class="text" name="title" value="${communionSend.title}" /></td>
				<td width="12%" align="right">发起日期</td>
				<td width="20%" align="left">
					<input type="text" id="sendTime" name="sendTime" class="text"  value="<fmt:formatDate value='${communionSend.sendTime}' pattern='yyyy-MM-dd' />" />
				</td>
				<td width="36%" align="left" rowspan="2" valign="middle">
					<input type="submit" value="查 询" class="btn_query" />
				</td>
			</tr>
		</table>
	</form>
</fieldset>
	<!-- 查询结束 -->
	<form:form action="${path }/instruction/communionSend/del" method="post">
	<display:table name="communionSendList" uid="communionSend" cellpadding="0" cellspacing="0" requestURI="${path }/instruction/communionSend/list" >
		<display:column title="序号" style="width:3%">
			<c:if test="${empty page ||page==0}">
			${communionSend_rowNum}
		</c:if>
			<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + communionSend_rowNum}
		</c:if>
		</display:column>
		<display:column title="标题" style="text-align:left;width:35%">
			<a href="${path}/instruction/communionSend/detail?communionId=${communionSend.communionId}">${communionSend.title }</a>
		</display:column>
		<display:column title="发起部门" property="sendOrgName" style="text-align:left;width:15%;"></display:column>
		<display:column title="发起日期" style="text-align:left;width:15%;">
			<fmt:formatDate value="${communionSend.sendTime}" pattern="yyyy-MM-dd"/>
		</display:column>
				<display:column title="接收部门" style="text-align:left;width:20%;">
			<span title="${communionSend.receiveOrgName}">${fn:substring(communionSend.receiveOrgName,0,15)}${fn:length(communionSend.receiveOrgName)>15?'...':''}</span>
		</display:column>
		<display:column title="操作" style="width:15%">
			<a href="${path }/instruction/communionSend/updateUI/${communionSend.communionId}">修改</a>&nbsp;&nbsp;
			<a href="javascript:;" onclick="del('${communionSend.communionId}');">删除</a>&nbsp;&nbsp;
		</display:column>
	</display:table>
	</form:form>
</div>
</body>
</html>