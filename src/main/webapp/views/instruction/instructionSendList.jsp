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
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>	
<script type="text/javascript">
$(function(){
	var info = "${info}";
	if(info != null && info != ""){
	    if(info != null && info != ""){
	        if(info == 'del'){
	            //正确提示
	            $.ligerDialog.success('工作指令删除成功！');
	        }
	        if(info == 'update'){
	            //正确提示
	            $.ligerDialog.success('工作指令修改成功！');
	        }
	        if(info == 'add'){
	            //正确提示
	            $.ligerDialog.success('工作指令下发成功！');
	        }        
	    }
	}
	
	updateHref();
	
	var sendTime = document.getElementById('sendTime');
	sendTime.onfocus = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	
});


function del(id){
	$.ligerDialog.confirm('确认删除吗?',function(flag){
		if(flag){
			window.location.href="${path }/instruction/instructionSend/delete/"+id;
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

function showSignList(instructionId){
	$.ligerDialog.open(
			 {url:"${path }/instruction/instructionSend/signOrgList?instructionId="+instructionId,
			 title : '指令签收情况',
			 height : 500,
			 width : $(top.window).width() * 0.3,
			 resize : false,
			 lock : true,
			 opacity : 0.1// 透明度
			 }, false);
}
</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
<legend class="legend">已发指令查询</legend>
	<form action="${path }/instruction/instructionSend/search" method="post">
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td width="12%" align="right">标题</td>
				<td width="20%" align="left">
					<input type="text" class="text" name="title" value="${instructionSend.title}" />
				</td>
				<td width="12%" align="right">发布时间</td>
				<td width="20%" align="left">
					<input type="text" id="sendTime" name="sendTime" class="text"  value="<fmt:formatDate value='${instructionSend.sendTime}' pattern='yyyy-MM-dd' />" />
				</td>
				<td width="36%" align="left" rowspan="2" valign="middle">
					<input type="submit" value="查 询" class="btn_query" />
				</td>
			</tr>
		</table>
	</form>
</fieldset>
	<!-- 查询结束 -->
	<display:table name="list" uid="instructionSend" cellpadding="0" cellspacing="0" requestURI="${path }/instruction/instructionSend/search" >
		<display:column title="序号" style="width:3%">
			<c:if test="${empty page ||page==0}">
			${instructionSend_rowNum}
		</c:if>
			<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + instructionSend_rowNum}
		</c:if>
		</display:column>
		<display:column title="标题" style="text-align:left;width:35%;">
			<a href="${path }/instruction/instructionSend/detail?instructionId=${instructionSend.instructionId}">${instructionSend.title}</a>&nbsp;&nbsp;
		</display:column>
		<display:column title="发布部门" property="sendOrgName" style="text-align:left;width:15%;"></display:column>
		<display:column title="发布日期" style="text-align:left;width:15%;">
			<fmt:formatDate value="${instructionSend.sendTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="接收部门" style="text-align:left;width:20%;">
			<span title="${instructionSend.receiveOrgName}">${fn:substring(instructionSend.receiveOrgName,0,15)}${fn:length(instructionSend.receiveOrgName)>15?'...':''}</span>
		</display:column>
		<display:column title="操作" style="width:15%">
			<a href="${path }/instruction/instructionSend/updateUI/${instructionSend.instructionId}">修改</a>&nbsp;&nbsp;
			<a href="javascript:;" onclick="del('${instructionSend.instructionId}');">删除</a>&nbsp;&nbsp;
			<a href="javascript:;" onclick="showSignList('${instructionSend.instructionId}')">签收情况</a>
		</display:column>
	</display:table>
</div>

</body>
</html>