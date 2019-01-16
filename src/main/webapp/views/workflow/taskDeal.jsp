<%@page import="com.ksource.syscontext.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@taglib prefix="dForm" uri="dForm" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"/>" />
<link rel="stylesheet" href="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.css" />
<link href="${path }/resources/script/accuseSelector/accuseSelector.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery.form.js"/>"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="<c:url value="/resources/jquery/jquery-ui-1.8.12.custom.min.js"/>"></script>
<script src="<c:url value="/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"/>"></script>
<script src="<c:url value="/resources/jquery/poshytip-1.2/jquery.poshytip.js"/>"></script>
<script src="<c:url value="/resources/script/jqueryUtil.js"/>"></script>
<script type="text/javascript" src="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="<c:url value="/resources/script/FormBuilder.js"/>"></script>
<script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>
<script src="${path }/resources/script/accuseSelector/accuseSelector.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript">
	/*var formDef=${formDef.jsonView};
	var taskActionList = ${taskActionListJson};
	var appPath='${path}/';
	var forBuilder=new FormBuilder(formDef);
	$(function(){
		forBuilder.genTaskDealForm(taskActionList, 'taskForm', 'formFieldC', appPath);
		$("input:button,input:reset,input:submit,button").button();
	});*/
	var caseId = '${caseInfo.caseId}',
		  taskId = '${taskInfo.id}';
</script>
<style type="text/css">
	.genInput{
		width: 30%;
	}
	table.blues td{
		text-align: left;
	}
	#outDiv{position: relative;margin: 6px;}
	#formFieldC .genInputText{
		width: 30%;
		border:1px solid #ABC1D1; 
		background-color:#ffffe6;
	}
	#formFieldC .genSelect{width: 31%;display:inline; border: 1px solid #ABC1D1;}
	#formFieldC p{margin: 6px 0;padding-left: 20px;}
	#formFieldC p label{ width: 150px; display: inline-block;margin-right: 5px;}
	

	#caseInfoC a{color: red;}
	
	.option-set{
		display: inline-block;
		margin-right: 20px;
	    list-style: none outside none;
	    margin: 0;
		margin-left: 1.5em;
		margin-bottom: 1em;
		line-height: 1.7em;
		border: 0 none;
	    font: inherit;
	    padding: 0;
	    vertical-align: baseline;
	}
	.option-set li{
		float: left;
    	margin-bottom: 0.2em;
	}
	.option-set li a {
	    border-right: 1px solid rgba(0, 0, 0, 0.2);
	     background-color: #BBBBBB;
	    background-image: -moz-linear-gradient(-90deg, rgba(255, 255, 255, 0.5), rgba(255, 255, 255, 0));
	    display: block;
   		font-weight: bold;
    	padding: 0.4em 0.5em;
    	text-decoration: none;
	}
	.option-set li a:active {
	    background-color: #3399DD;
	    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.6) inset;
	}
	.option-set li a:hover {
	    background-color: #55BBFF;
	}
	.option-set li:first-child a {
	    border-left: medium none;
	    border-radius: 7px 0 0 7px;
	}
	.option-set li a.selected {
	    background-color: #1133FF;
	    color: white;
	    text-shadow: none;
	}
</style>
<title>Insert title here</title>
</head>
<body>
<div>
<ul class="xbreadcrumbs" id="breadcrumbs-3" style="margin-bottom: 5px;">
    <%--  <li>
          <a href="${path}/home/main_default"  class="home">首页</a>
     </li> --%>
     <li>
     <!-- optType参数表示待办案件的入口，为办理完毕后跳转的页面提供依据(从哪办理，还跳转到哪)，1：待办页面办理；6：补充调查页面办理-->
     	<c:if test="${optType!=null && optType==1}">
     		<a href="${path}/casehandle/caseTodo/list">待办案件列表</a>
     	</c:if>
     	<c:if test="${optType!=null && optType==6}">
     		<a href="${path}/casehandle/caseTodo/buChongDiaoChaList">补充调查案件列表</a>
     	</c:if>
     </li>
     <li class="current"><a href="javascript:void();">案件办理</a></li>
</ul><br/>
<script type="text/javascript">
     $(document).ready(function(){
          $('.xbreadcrumbs').xBreadcrumbs();
     });
</script>
</div>
<div style="clear:both;"></div>
<div id="outDiv">
	<fieldset id="formFieldC" class="fieldset">
		<legend class="legend">${taskInfo.name}</legend>
		<div style="margin:10px;">
			<dForm:dFormBuilder taskId="${taskInfo.id}" model="${dFormBuilderModel }" inputerId="${caseInfo.inputer}" optType="${optType}"/>
		</div>
	</fieldset>
	<br/>
	<fieldset id="caseInfoC" class="fieldset">
		<legend class="legend">案件信息</legend>
		<table class="blues" style="width: 96%;margin-left: 10px;margin-bottom: 10px;">
			<tr>
				<td style="width:20%" class="tabRight">案件编号：</td>
				<td style="text-align: left;width: 30%"><a href="javascript:;" onclick="top.showCaseProcInfo('${caseInfo.caseId}','','${caseInfo.procKey }');" title="点击查看案件详情">${caseInfo.caseNo}</a></td>
				<td style="width: 20%" class="tabRight">案件名称：</td>
				<td style="text-align: left;"  colspan="3">${caseInfo.caseName}</td>
<%-- 				<td style="width:20%" class="tabRight">案件类型：</td>
				<td style="text-align: left;">${procKey.procKeyName}</td> --%>
			</tr>
		<%--	<tr>

				<c:set var="CASE_CHUFA_PROC_KEY" value="<%=Const.CASE_CHUFA_PROC_KEY %>"></c:set>
		 <c:if test="${procKey.procKey==CASE_CHUFA_PROC_KEY }">
			<p align="left">
		监督时机：<c:choose><c:when test="${caseInfo.caseInputTiming==1}">作出行政处罚后录入系统</c:when>
				<c:when test="${caseInfo.caseInputTiming==2}">行政立案后录入系统</c:when>
				<c:when test="${caseInfo.caseInputTiming==3}">移送公安后录入系统</c:when>
			</c:choose>
		</p>
		</c:if> 
			</tr>--%>
		</table>
	</fieldset><br/>
	<fieldset id="caseStepC" class="fieldset">
		<legend class="legend">案件上一步办理步骤信息</legend>
		<div id="caseStepDiv"></div>
	</fieldset>
</div>


<script type="text/javascript">
    //加载案件步骤信息
    var stepViewC = $('#caseStepDiv');
        stepViewC.html('加载中，请稍后......');
        $.ajax({
          url: "${path}/workflow/caseProcView/caseStepView/${caseStepId}",
          success: function(html){
              stepViewC.html(html);
              $('#reDealButton').remove();
          }
        });
    jQuery(function(){
    	 //清除样式冲突
    	jQuery('button[id^=dropdownMenu]','#dropdownMenu').css({"border":"none"});
    });
</script>
</body>
</html>