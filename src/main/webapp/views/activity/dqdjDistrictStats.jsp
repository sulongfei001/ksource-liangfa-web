<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib uri="quiee" prefix="report" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>打侵打假案件统计(按行政区划)</title>
<link rel="stylesheet" type="text/css" href="${path}/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<script type="text/javascript" src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path}/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path}/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path}/resources/script/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
var zTreeSetting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "upId",
			},
		},
		async: {
			enable: true,
			url: '${path}/system/district/loadChildTree',
			autoParam: ["id"],
		},
		callback: {
			onClick: zTreeOnClick	
		}
};
$(function(){
	$("#report1").css("width","100%");
	
	var startTime = document.getElementById('startTime');
	startTime.onfocus = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'<fmt:formatDate value="${acti.startTime}" pattern="yyyy-MM-dd"/>',maxDate:'<fmt:formatDate value="${acti.endTime}" pattern="yyyy-MM-dd"/>'});
	}
	var endTime = document.getElementById('endTime');
	endTime.onfocus = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'<fmt:formatDate value="${acti.startTime}" pattern="yyyy-MM-dd"/>',maxDate:'<fmt:formatDate value="${acti.endTime}" pattern="yyyy-MM-dd"/>'});
	}	
	
	zTree =	$.fn.zTree.init($("#dropdownMenu"),zTreeSetting);
	
	$('html').bind("mousedown", function(event){
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
		 
		 $('#findForm')[0].action="${path}/dqdj/stats/dqdjDistrict";
		 $('#findForm').submit();                
	}
	$(function(){
		hiddenDIV();
		//处理报表传过来中文参数出现乱码情况
		var reportHrefs=$("td[class^='report'] a");
		//alert(reportHrefs.length);
		$.each(reportHrefs,function(i,n){
			var oldhref=$(n).attr("href");
			$(n).attr("href",encodeURI(oldhref));
		});
	});
</script>
<style>
.report1 { display:none}
a.aQking {
	margin-left: 3px;
	padding-left: 20px;
	height: 25px;
	line-height: 25px;
	color: #333;
	text-decoration: none;
}

a.aQking:hover {
	text-decoration: none;
	color: #1673FF;
}

a.aQking.qingkong {
	margin-bottom: 10px;
	background: url("../../resources/images/icon_qingkong.png") no-repeat
		1px 0px;
}
input.text {
    width: 98%;
}
table input{ vertical-align:middle; }
</style>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
<legend class="legend">打侵打假(按行政区划)</legend>
<form action="" id="findForm" method="post">
	<table width="100%" class="searchform">
			<tr>
				<td width="15%" align="right">
					行政区划
				</td>
				<td width="18%" align="left">
						<input type="text" class="text" id="districtId" name="districtName" value="${districtName}" onfocus="showMenu(); return false;" readonly="readonly">
						<input type="hidden" name="districtId" id="districtId_hidden" value="${districtId}">						
					</td>
				<td><div class="tabRight"><a href="#" onclick="clearDiv()" class="aQking qingkong">清空</a></div>	</td>						
				<td width="15%" align="right">
					案件录入时间
				</td>
				<td width="25%" align="left">
					<input class="text" type="text" name="startTime" id="startTime" value="${dqdjStartTime}" style="width:36%"/>
				到
				<input class="text" type="text" name="endTime" id="endTime" value="${dqdjEndTime}"style="width:36%"/>
				</td>	
				<td rowspan="2" valign="middle" align="left">
					<input type="button" value="查 询" class="btn_query" onclick="search()"/>
				</td>
			</tr>
			
	</table>
</form>
	</fieldset>
	<table align="center" style="width: 100%;">
		<tr>
			<td align="right">
				<jsp:include page="/quiee/reportJsp/toolbar.jsp" flush="false" />
				<report:html name="report1" reportFileName="dqdjDistrict.raq"
					funcBarLocation=""
					needPageMark="yes"
					generateParamForm="no"
					needLinkStyle="yes"
					params="${parameter}"
					width="-1"
                    needSaveAsPdf="yes" needSaveAsWord="yes" pdfExportStyle="text,0"
									 excelPageStyle="0" needSaveAsExcel="yes" excelUsePaperSize="no"
									 needPrint="yes" printButtonWidth="80" needPrintPrompt="yes"
									 needSelectPrinter="yes" savePrintSetup="yes" displayNoLinkPageMark="yes" saveAsName="打侵打假(按行政区划)"
				/>
			</td>
		</tr>
	</table>
	
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px;width:250px; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<ul id="dropdownMenu" class="ztree"></ul>
</div>
<div id="indexDIV" style="display:none; position:absolute; height:210px;width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
	    <a href="#" onclick="hiddenDIV()" >确定</a>&nbsp;&nbsp;
		<a href="#" onclick="selectAll()" >全选</a>&nbsp;&nbsp;
		<a href="#" onclick="clearCheckbox()">清空</a>
	</div>
	
</div>
</div>
</body>
</html>