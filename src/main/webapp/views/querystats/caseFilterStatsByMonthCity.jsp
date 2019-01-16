<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib uri="quiee" prefix="report" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>立案监督线索统计(按月份)</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path }/resources/script/highcharts/highcharts.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
$(function(){
	zTree=	$('#dropdownMenu').zTree({
		isSimpleData:true,
		treeNodeKey : "id",
	    treeNodeParentKey : "upId",
		async: true,
		asyncUrl:"${path}/system/district/loadChildTree",
		asyncParam: ["id"],
		callback: {
			click: zTreeOnClick 
		}
			});	
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
		 $('#findForm')[0].action="${path}/breport/caseFilterStatsByMonthCity?indexList="+index;
		 $('#findForm').submit();                
	}
	$(function(){
		hiddenDIV();
		var reportHrefs=$("td[class^='report'] a");
		$.each(reportHrefs,function(i,n){
			var oldhref=$(n).attr("href");
			$(n).attr("href",encodeURI(oldhref));
		});
	});

	//返回上一级
	function report1_back(){
		var indexList='${index}';
		window.location.href="${path}/breport/caseFilterStatsByQuarterCity?yearCode="+${yearCode}+"&districtId="+${districtId}+"&indexList="+indexList;
	}
</script>
<style>
.report1 { display:none}
</style>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
<legend class="legend">立案监督线索统计(按月份)</legend>
<form action="" id="findForm" method="post">
	<table width="100%" class="searchform">
			<tr>
				<td width="15%" align="right">
					行政区划
				</td> 
				<td width="25%" align="left">
					<input type="text" class="text" id="districtId" name="districtName" value="${districtName}" onfocus="showMenu(); return false;" readonly="readonly"/>
					<a href="#" onclick="clearDiv()" class="aQking qingkong">清空</a>
				　　<input type="hidden" name="districtId" id="districtId_hidden" value="${districtId}"/>
				</td>
				 <td width="40%" align="right">
				 <input type="hidden" name="yearCode" id="yearCode" value="${yearCode}"/>
				 <input type="hidden" name="quarterCode" id="quarterCode" value="${quarterCode}"/>
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
				<report:html name="report1" reportFileName="business_caseFilterStatsByMonthCity.raq"
					funcBarLocation=""
					needPageMark="yes"
					generateParamForm="no"
					needLinkStyle="yes"
					params="${parameter}"
					width="-1"
                    needSaveAsPdf="yes" needSaveAsWord="yes" pdfExportStyle="text,0"
									 excelPageStyle="0" needSaveAsExcel="yes" excelUsePaperSize="no"
									 needPrint="yes" printButtonWidth="80" needPrintPrompt="yes"
									 needSelectPrinter="yes" savePrintSetup="yes" displayNoLinkPageMark="yes"
									  saveAsName="立案监督线索统计(按月份)"
				/>
			</td>
		</tr>
	</table>
	
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px;width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<ul id="dropdownMenu" class="tree"></ul>
</div>
<div id="indexDIV" style="display:none; position:absolute; height:210px;width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
	    <a href="#" onclick="hiddenDIV()" >确定</a>&nbsp;&nbsp;
		<a href="#" onclick="selectAll()" >全选</a>&nbsp;&nbsp;
		<a href="#" onclick="clearCheckbox()">清空</a>
	</div>
	<div align="left">
		<input type="hidden" name="index" value="${index}"></input>
		<input type="checkbox" name="indexList" value ="M" <c:if test="${fn:contains(index,'M')}">checked="checked"</c:if>/><span class="indexSpan">行政处罚案件</span> <br/>
		<input type="checkbox" name="indexList" value ="N" <c:if test="${fn:contains(index,'N')}">checked="checked"</c:if>/><span class="indexSpan">疑似犯罪案件</span> <br/>
		<input type="checkbox" name="indexList" value ="A" <c:if test="${fn:contains(index,'A')}">checked="checked"</c:if>/><span class="indexSpan">行政处罚2次以上</span> <br/>
		<input type="checkbox" name="indexList" value ="B" <c:if test="${fn:contains(index,'B')}">checked="checked"</c:if>/><span  class="indexSpan">涉案金额达到刑事追诉标准80%以上</span> <br/>
		<input type="checkbox" name="indexList" value ="C" <c:if test="${fn:contains(index,'C')}">checked="checked"</c:if>/><span  class="indexSpan">有过鉴定</span> <br/>
		<input type="checkbox" name="indexList" value ="E" <c:if test="${fn:contains(index,'E')}">checked="checked"</c:if>/><span  class="indexSpan">经过负责人集体讨论</span> <br/>
		<input type="checkbox" name="indexList" value ="F" <c:if test="${fn:contains(index,'F')}">checked="checked"</c:if>/><span  class="indexSpan">情节严重</span> <br/>
		<input type="checkbox" name="indexList" value ="D" <c:if test="${fn:contains(index,'D')}">checked="checked"</c:if>/><span  class="indexSpan">处以行政处罚规定下限金额以下罚款</span> <br/>
	</div> 
</div>
</div>
</body>
</html>