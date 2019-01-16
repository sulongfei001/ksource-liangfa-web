<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"  />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path}/resources/script/cleaner.js"></script>	
<script type="text/javascript" src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script type="text/javascript" src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerTip.js"></script>
<script type="text/javascript">
$(function(){
	//日期插件时[hh],天[dd],月[mm],年[yyyy]
	var startTime = document.getElementById('startTime');
	startTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	var endTime = document.getElementById('endTime');
	endTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	
	  $('.noUpdate').poshytip({
	         content:"已分派或已受理,无法操作!",
	         className: 'tip-yellowsimple',
	         showTimeout: 1,
	         slide: false,
	         fade: false,
	         alignTo: 'target',
	         alignX: 'left',
	         alignY: 'center',
	         offsetX: 2
	     });
	  
	  var info='${info}';
	  if(info=='delOK'){parent.$.ligerDialog.success('删除成功！');}
	  if(info=='delFailure'){parent.$.ligerDialog.warn('删除失败！');}
	  
	  if(info=='saveOK'){parent.$.ligerDialog.success("保存成功！");}
	  if(info=='saveFailure'){parent.$.ligerDialog.warn("保存失败！");}
});
function checkAll(obj){
	jQuery("[name='check']").attr("checked",obj.checked) ; 			
	}	
function batchDelete(){
	var	checkNum=jQuery("[name='check']:checked").length;
	if(checkNum>0){
		parent.$.ligerDialog.confirm('是否确认删除选中线索？',function(flag){
		if(flag){jQuery("#delForm").submit();}});
	}else{
		parent.$.ligerDialog.warn("请选择将要删除的线索！");
	}	
}
</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">我的线索查询</legend>
<form action="${path }/casehandle/clueInfo/getInputClueList" method="post" >
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">线索名称</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="clueNo" id="caseNo" style="width: 73%;" value="${param.clueNo }" /></td>
			<td width="15%" align="right">线索状态</td>
			<td width="25%" align="left">
			<dict:getDictionary	var="clueStateList" groupCode="clueState" /> 
				<select id="clueState" name="clueState" style="width: 77%">
					<option value="" selected>--全部--</option>
					<option value="1" <c:if test="${param.clueState == 1}">selected</c:if>>未分派</option>
					<option value="2" <c:if test="${param.clueState == 2}">selected</c:if>>已分派</option>
					<option value="3" <c:if test="${param.clueState == 3}">selected</c:if>>已受理</option>
				</select>
			</td>
			<td width="20%"  rowspan="3" align="left" valign="middle" >
			<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
		<tr>
			<td width="15%" align="right">创建时间</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 32.5%" readonly="readonly"/>
				至
				<input type="text" class="text" name="endTime" id="endTime" value="${param.endTime }" style="width: 32.5%" readonly="readonly"/>
			</td>
			<td width="15%" align="right"></td>
			<td width="25%" align="left">
			</td>
		</tr>
	</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
	<form:form id="delForm" action="${path}/casehandle/clueInfo/batchDelete" method="post">
	<display:table name="clueInfoList"   uid="clueInfo" cellpadding="0" cellspacing="0" requestURI="${path }/casehandle/clueInfo/getInputClueList.do" decorator="" excludedParams="info">
		<display:caption class="tooltar_btn"><input type="button" class="btn_big" value="批量删除" onclick="return batchDelete();"/></display:caption>
		<display:column title="<input type='checkbox' onclick='checkAll(this)'/>">
			<c:if test="${clueInfo.clueState == 1 }">
				<input type="checkbox" name="check" value="${clueInfo.clueId}" />
			</c:if>
			<c:if test="${clueInfo.clueState != 1 }">
				<input type="checkbox" name="notcheck" value="${clueInfo.clueId}" disabled="disabled"/>
			</c:if>
		</display:column>
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${clueInfo_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + clueInfo_rowNum}
			</c:if>
		</display:column>
		<display:column title="线索名称"  style="text-align:left;">
			<a href="${path }/casehandle/clueInfo/getClueDetailView?clueId=${clueInfo.clueId}&anchor=1">${clueInfo.clueNo}</a>
		</display:column>
		<display:column title="创建单位" >
			${clueInfo.createOrgName}
		</display:column>
		<display:column title="创建时间" >
			<fmt:formatDate value="${clueInfo.createTime}" pattern="yyyy-MM-dd HH:mm"/>
		</display:column>
		<display:column title="线索状态" >
			<c:choose>
				<c:when test="${clueInfo.clueState == 1}">
					未分派
				</c:when>
				<c:when test="${clueInfo.clueState == 2}">
					已分派
				</c:when>
				<c:when test="${clueInfo.clueState == 3}">
					已受理
				</c:when>
				<c:otherwise>
					未受理
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column title="操作">
			<c:if test="${clueInfo.clueState == 1 }">
				<a href="${path }/casehandle/clueInfo/getUpdateClueView?clueId=${clueInfo.clueId}">修改</a>
				<a href="javascript:void(0);"
					onclick="parent.$.ligerDialog.confirm('是否确认删除线索？',function(flag){if(flag){location.href='${path }/casehandle/clueInfo/batchDelete?check=${clueInfo.clueId}';}});"
				>删除</a>
			</c:if>
			<c:if test="${clueInfo.clueState != 1 }">
				<c:choose>
					<c:when test="${isAdmin == true }">
						<a href="${path }/casehandle/clueInfo/getUpdateClueView?clueId=${clueInfo.clueId}">修改</a>
						<a href="javascript:void(0);"
							onclick="parent.$.ligerDialog.confirm('线索已经受理，是否确认删除？',function(flag){if(flag){location.href='${path }/casehandle/clueInfo/batchDelete?check=${clueInfo.clueId}';}});"
						>删除</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:void(0);" class="message noUpdate" style="cursor: pointer;color:#C0C0C0">修改</a>
						<a href="javascript:void(0);" class="message noUpdate" style="cursor: pointer;color:#C0C0C0">删除</a>
					</c:otherwise>
				</c:choose>
			</c:if>
		</display:column>
	</display:table>
	</form:form>
</div>
</body>
</html>