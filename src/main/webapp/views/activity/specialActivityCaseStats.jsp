<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib uri="quiee" prefix="report" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>专项立案监督活动统计</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript">

$(function(){
	$("#report1").css("width","100%");
	 /*$("td[onclick='_displayEditor()']").bind("click",function(){
		 var editbox=$("#report1_editBox")[0];
		 while(editbox==undefined){
			 editbox=$("#report1_editBox")[0];
			}
		 var h=$(editbox).css("height");
		 $(editbox).css("line-height",h);
		 _displayEditor.call(this);
	});*/
	 
	  var inputTime = document.getElementById('inputTime');
	inputTime.onfocus = function(){
		  WdatePicker({dateFmt:'yyyy-MM'});
	  }

	//表单验证
	jqueryUtil.formValidate({
		form:"findForm",
		rules:{
		    inputTime:{required:true}
		},
		messages:{
			inputTime:{required:"填报时间必填!"}
		},
		submitHandler:function(form){
			    		form.submit();
		    	}
	 });
});


function search(){
	 $('#findForm').submit();        
	}
</script>
<style>
.report1 { display:none}
</style>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">专项立案监督活动统计</legend>
<form  id="findForm" method="post" action="${path}/specialActivityCase/stats/general/">
	<table width="100%" class="searchform">
			<tr>
				<td width="15%" align="right">
					填报时间
				</td>
				<td width="25%" align="left">
					<input type="text" class="text" value="${param.inputTime}" id="inputTime" name="inputTime"/>
					&nbsp;<font color="red">*必填</font>
				</td>	
				<td rowspan="2" valign="middle" align="left">
					<input type="button" value="查 询" class="btn_query" onclick="search()"/>
				</td>
			</tr>
			
	</table>
</form>
</fieldset>
	<table align="center" width="100%" style="text-align: left;" >
		<tr>
			<td align="right">
			<jsp:include page="/quiee/reportJsp/toolbar_tianbao.jsp" flush="false" />
				<report:html name="report1" 
				reportFileName="accuseCaseStats.raq" params="${parameter}" 
				needScroll="yes" scrollWidth="700" scrollHeight="700"
				funcBarLocation=""
				needPageMark="yes" needLinkStyle="yes" generateParamForm="no" width="-1"
                needSaveAsPdf="yes" needSaveAsWord="yes" pdfExportStyle="text,0"
				excelPageStyle="0" needSaveAsExcel="yes" excelUsePaperSize="no"
				needPrint="yes" printButtonWidth="80" needPrintPrompt="yes"
				needSelectPrinter="yes" savePrintSetup="yes" displayNoLinkPageMark="yes" saveAsName="专项立案监督活动统计"
				/>
			</td>
		</tr>
	</table>
</div>
	

</body>
</html>