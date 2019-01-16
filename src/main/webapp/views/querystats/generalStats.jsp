<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib uri="quiee" prefix="report" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>案件办理环节统计(按区划)</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path }/resources/script/highcharts/highcharts.js"></script>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
var districtZTree;
$(function(){
	var startTime = document.getElementById('startTime');
	startTime.onfocus = function(){
		WdatePicker({dateFmt:'yyyy-MM',isShowToday:false});
	}		
	var endTime = document.getElementById('endTime');
	endTime.onfocus = function(){
		WdatePicker({dateFmt:'yyyy-MM',isShowToday:false});
	}
	
	//初始化行政区划树
	var setting = {
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "upId"
				}
			},
			async: {
				enable: true,
				url: "${path}/system/district/loadChildTree",
				autoParam: ["id"]
			},
			callback: {
				onClick: zTreeOnClick
				}
		};
	districtZTree = $.fn.zTree.init($("#dropdownMenu"),setting);
	
	$('html').bind("mousedown", 
			function(event){
				if (!(event.target.id == "DropdownMenuBackground" || event.target.id=="districtId"|| $(event.target).parents("#DropdownMenuBackground").length>0)) {
					hideMenu();
				}
				if (!(event.target.id == "indexDIV" || event.target.id=="indexValue" || $(event.target).parents("#indexDIV").length>0)) {
					hiddenDIV();
				}
			});

	$("#report1").attr("style","width:100%");
});
	function showMenu() {
		var cityObj = $("#districtId");
		var cityOffset = $("#districtId").offset();
		$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
		
	}
	function hideMenu() {
		$("#DropdownMenuBackground").fadeOut("fast");
	}
	function clearDiv() {    
		document.getElementById('districtId').value = '';
		document.getElementById('districtId_hidden').value = '';		
	} 	
	function zTreeOnClick(event, treeId, treeNode) {
		if (treeNode) {
			$("#districtId").val(treeNode.name);
			$("[name='districtId']").val(treeNode.id);
			hideMenu();
		}
	}


	function showList() {
		var cityObj = $("#indexValue");
		var cityOffset = $("#indexValue").offset();
		$("#indexDIV").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	}

	function clearCheckbox() {    
		var el = document.getElementsByName('indexList');     
		var len = el.length;   
		for(var i=0; i<len; i++)     {        
				el[i].checked = false;         
		}     
			
	}
	
	function selectAll() {    
		var el = document.getElementsByName('indexList');     
		var len = el.length;   
		for(var i=0; i<len; i++)     {        
				el[i].checked = true;         
		}     
	} 
	
	function hiddenDIV(){	
		var indexSpan=''; 
		$('[name=indexList]:checked').each(function(){
			indexSpan += $(this).next().html()+' ';
		});
		$("#indexValue").val(indexSpan);
		$("#indexDIV").fadeOut("fast");
	}
	
	function search(){
		 var index=''; 
		 var i = 0 ;
		 $("input[name='indexList']").each(function() {
			var temp = $(this).attr("checked") ;
			if( temp == 'checked') {
				index += $(this).val()+',';
				i++ ;
			}
		 });  
		 if(i === 0){
			 	alert("请选择显示指标！"); 
			 	return ;
		 }
		 $('#findForm')[0].action="${path}/breport/generalStats?indexList="+index;
		 $('#findForm').submit();                
	}
	$(function(){
		hiddenDIV();

		var reportHrefs=$("td[class^='report'] a");
		//alert(reportHrefs.length);
		$.each(reportHrefs,function(i,n){
			var oldhref=$(n).attr("href");
			$(n).attr("href",encodeURI(oldhref));
		});
	});
</script>
<style>
.report1 { display:none;}
.report1_3{
	background-color: #0000EE;
}
</style>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
<legend class="legend">案件办理环节统计(按区划)</legend>
<form action="" id="findForm" method="post">
	<table width="100%" class="searchform">
			<tr>
				<td width="15%" align="right">
					行政区划
				</td>
				<td width="25%" align="left">
					<input type="text" class="text" id="districtId" name="districtName" value="${districtName}" onfocus="showMenu(); return false;" readonly="readonly"/><input type="hidden" name="districtId" id="districtId_hidden" value="${districtId}"/> <a href="#" onclick="clearDiv()" class="aQking qingkong">清空</a>
				</td>
				 <td width="15%" align="right">
					案件录入时间
				</td>
				<td width="25%" align="left">
					<input class="text" type="text" name="startTime" id="startTime" value="${startTime}" style="width:36%"/>
				到
				<input class="text" type="text" name="endTime" id="endTime" value="${endTime}"style="width:36%"/>
				</td>	
				<td rowspan="2" valign="middle" align="left">
					<input type="button" value="查 询" class="btn_query" onclick="search()"/>
				</td>
			</tr>
			<tr>
			   <td  width="15%" align="right">
					显示指标(可多选)
				</td>
				<td colspan="4">
					<input type="text" class="text" style="width: 70%" id="indexValue" name="indexInput" value="${indexList}" onfocus="showList(); return false;" readonly="readonly"/>
				</td>		
			</tr>
	</table>
</form>
	</fieldset>
	<table align="center" style="width: 100%;">
		<tr>
			<td align="right">
				<jsp:include page="/quiee/reportJsp/toolbar.jsp" flush="false" />
				<report:html name="report1" reportFileName="business_gengralStats.raq"
					funcBarLocation=""
					needPageMark="yes"
					generateParamForm="no"
					needLinkStyle="yes"
					params="${parameter}"
					width="-1"
                    needSaveAsPdf="yes" needSaveAsWord="yes" pdfExportStyle="text,0"
									 excelPageStyle="0" needSaveAsExcel="yes" excelUsePaperSize="no"
									 needPrint="yes" printButtonWidth="80" needPrintPrompt="yes"
									 needSelectPrinter="yes" savePrintSetup="yes" displayNoLinkPageMark="yes" saveAsName="案件办理环节统计(按区划)"
				/>
			</td>
		</tr>
	</table>
	
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px;width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<ul id="dropdownMenu" class="ztree"></ul>
</div>
<div id="indexDIV" style="display:none; position:absolute; height:210px;width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
	    <a href="#" onclick="hiddenDIV()" >确定</a>&nbsp;&nbsp;
		<a href="#" onclick="selectAll()" >全选</a>&nbsp;&nbsp;
		<a href="#" onclick="clearCheckbox()">清空</a>
	</div>
	<div align="left">
		<input type="hidden" name="index" value="${index}"></input>
		<c:if test="${orgType!=3}">
			<input type="checkbox" name="indexList" value ="M" <c:if test="${fn:contains(index,'M')}">checked="checked"</c:if>/><span class="indexSpan">行政受理</span> <br/>
			<input type="checkbox" name="indexList" value ="N" <c:if test="${fn:contains(index,'N')}">checked="checked"</c:if>/><span class="indexSpan">行政立案</span> <br/>
			<input type="checkbox" name="indexList" value ="O" <c:if test="${fn:contains(index,'O')}">checked="checked"</c:if>/><span class="indexSpan">不予立案</span> <br/>
		</c:if>
		<input type="checkbox" name="indexList" value ="A" <c:if test="${fn:contains(index,'A')}">checked="checked"</c:if>/><span class="indexSpan">行政处罚</span> <br/>
		<input type="checkbox" name="indexList" value ="P" <c:if test="${fn:contains(index,'P')}">checked="checked"</c:if>/><span class="indexSpan">不予处罚</span> <br/>
		<input type="checkbox" name="indexList" value ="Q" <c:if test="${fn:contains(index,'Q')}">checked="checked"</c:if>/><span class="indexSpan">行政拘留</span> <br/>
		<input type="checkbox" name="indexList" value ="R" <c:if test="${fn:contains(index,'R')}">checked="checked"</c:if>/><span class="indexSpan">不予拘留</span> <br/>
		<input type="checkbox" name="indexList" value ="B" <c:if test="${fn:contains(index,'B')}">checked="checked"</c:if>/><span  class="indexSpan">主动移送公安机关</span> <br/>
		<input type="checkbox" name="indexList" value ="C" <c:if test="${fn:contains(index,'C')}">checked="checked"</c:if>/><span  class="indexSpan">检察机关建议移送</span> <br/>
		<input type="checkbox" name="indexList" value ="D" <c:if test="${fn:contains(index,'D')}">checked="checked"</c:if>/><span  class="indexSpan">公安机关受理</span> <br/>
		<input type="checkbox" name="indexList" value ="S" <c:if test="${fn:contains(index,'S')}">checked="checked"</c:if>/><span class="indexSpan">立案监督</span> <br/>
		<input type="checkbox" name="indexList" value ="E" <c:if test="${fn:contains(index,'E')}">checked="checked"</c:if>/><span  class="indexSpan">公安机关立案</span> <br/>
		<input type="checkbox" name="indexList" value ="F" <c:if test="${fn:contains(index,'F')}">checked="checked"</c:if>/><span  class="indexSpan">公安机关提请逮捕</span> <br/>
		<input type="checkbox" name="indexList" value ="G" <c:if test="${fn:contains(index,'G')}">checked="checked"</c:if>/><span  class="indexSpan">公安机关移送起诉</span> <br/>
		<input type="checkbox" name="indexList" value ="I" <c:if test="${fn:contains(index,'I')}">checked="checked"</c:if>/><span  class="indexSpan">检察机关批准逮捕</span> <br/>
		<input type="checkbox" name="indexList" value ="J" <c:if test="${fn:contains(index,'J')}">checked="checked"</c:if>/><span  class="indexSpan">检察机关提起公诉</span> <br/>
		<input type="checkbox" name="indexList" value ="K" <c:if test="${fn:contains(index,'K')}">checked="checked"</c:if>/><span  class="indexSpan">法院判决</span> <br/>
	</div>
</div>
</div>
</body>
</html>