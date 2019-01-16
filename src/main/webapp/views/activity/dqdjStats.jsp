<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib uri="quiee" prefix="report" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>打侵打假案件统计(按录入单位)</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/zTree/v35/zTreeStyle.css"/>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path}/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path}/resources/script/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/zTree/v35/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${path}/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script	type="text/javascript" src="${path}/resources/script/artDialog/artDialog.iframeTools.js"></script>
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
			url: '${path}/dqdj/stats/loadOrg/',
			autoParam: ["id"],
		},
		check: {
			enable: true,
			chkboxType: {"Y":"","N":""},
		},
		callback: {
			onAsyncSuccess:zTreeOnAsyncSuccess		
		}
};
var zTree;
$(function(){
	hiddenDIV();
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
	$('html').bind("mousedown", 
			function(event){
				if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
					hideMenu();
				}
				if (!(event.target.id == "indexDIV" || $(event.target).parents("#indexDIV").length>0)) {
					hiddenDIV();
				}
			});
	//处理报表传中文出现乱码问题
	var reportHrefs=$("td[class^='report'] a");
	//alert(reportHrefs.length);
	$.each(reportHrefs,function(i,n){
		var oldhref=$(n).attr("href");
		$(n).attr("href",encodeURI(oldhref));
	});
});
	function zTreeOnAsyncSuccess(event, treeId, msg) {
		var ids="${dqdjOrgIds}";
		if(ids==""){
			return;
		}
		var idArra=ids.split(",");
		$.each(idArra,function(i,n){
			if(n!=""){
				var treeNode=zTree.getNodeByParam("id",n);
				//修改为选中状态
				treeNode.checked=true;
				//更新显示状态，即执行选中复选框动作
				zTree.updateNode(treeNode, true);
			}
		});
		hideMenu();
	}

	function showMenu() {
		var cityObj = $("#orgNames");
		var cityOffset = cityObj.offset();
		$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
		
	}
	function hideMenu() {
		$("#DropdownMenuBackground").fadeOut("fast");
		
		var nodes = zTree.getCheckedNodes();
		
	    var ids ="";
	    var names="";
	    //button:[],
	    $.each(nodes, function(i, n){
    		ids+=n.id+",";
    		names+=n.name+",";
		});
	    names = names.substr(0,names.length-1);
		$("#orgIds").val(ids);
		$("#orgNames").val(names);
	}
	function clearDiv() {    
		document.getElementById('orgIds').value = '';
		document.getElementById('orgNames').value = '';		
		//删除选中项
		zTree.checkAllNodes(false);
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
		$('#findForm')[0].action="${path}/dqdj/stats/general/";
		 $('#findForm').submit();        
		}
	
	
	function selectAllNodes() {
		zTree.checkAllNodes(true);
	}
	
</script>
<style>
.report1 { display:none}
a.aQking,a.aQxing {
	margin-left: 3px;
	padding-left: 20px;
	height: 25px;
	line-height: 25px;
	color: #333;
	text-decoration: none;
}

a.aQking:hover,a.aQxing:hover {
	text-decoration: none;
	color: #1673FF;
}

a.aQking.qingkong {
	margin-bottom: 10px;
	background: url("../../resources/images/icon_qingkong.png") no-repeat
		1px 0px;
}
a.aQxing.quanxuan {
	margin-bottom: 10px;
	background: url("../../resources/images/icon_quanxuan.png") no-repeat
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
	<legend class="legend">打侵打假(按录入单位)</legend>
<form action="" id="findForm" method="post">
<input type="hidden" name="orgIds" id="orgIds" value="${param.orgIds}"/>
	<table width="96%" class="searchform">
			<tr>
				<td width="15%" align="right">案件录入单位</td>
				<td width="25%" align="left">
					<input type="text" class="text" name="orgNames" id="orgNames"  onfocus="showMenu(); return false;" readonly="readonly"/>
				</td>
				<td>
					<div align="right">
						<a href="javascript:void(0);" onclick="selectAllNodes()"  class="aQxing quanxuan">全选</a>
						<a href="#" onclick="clearDiv()"  class="aQking qingkong">清空</a>
					</div>
				</td>
				<td width="15%" align="right">
					案件录入时间
				</td>
				<td width="25%" align="left">
					<input type="text" class="text"  name="startTime" id="startTime" value="${dqdjStartTime}" style="width:36%"/>
					到
					<input type="text" class="text"  name="endTime" id="endTime" value="${dqdjEndTime}"style="width:36%"/>
				</td>	
				<td rowspan="2" valign="middle" align="left">
					<input type="button" value="查 询" class="btn_query" onclick="search()"/>
				</td>
			</tr>
			
	</table>
</form>
</fieldset>
	<table width="100%" style="text-align: left;" >
		<tr>
			<td align="right">
				<jsp:include page="/quiee/reportJsp/toolbar.jsp" flush="false" />
				<report:html name="report1" reportFileName="dqdjo.raq"
					funcBarLocation=""
					needPageMark="yes"
					needLinkStyle="yes"
					generateParamForm="no"
					params="${parameter}"
					width="-1"
                     needSaveAsPdf="yes" needSaveAsWord="yes" pdfExportStyle="text,0"
									 excelPageStyle="0" needSaveAsExcel="yes" excelUsePaperSize="no"
									 needPrint="yes" printButtonWidth="80" needPrintPrompt="yes"
									 needSelectPrinter="yes" savePrintSetup="yes" displayNoLinkPageMark="yes" saveAsName="打侵打假(按录入单位)"
				/>
			</td>
		</tr>
	</table>
</div>
	
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px;width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<ul id="dropdownMenu" class="ztree"></ul>
</div>
<div id="indexDIV" style="display:none; position:absolute; height:200px;width:250px; background-color:white;border:1px solid;overflow-y:auto;overflow-x:auto;">
	<div align="right">
	    <a href="#" onclick="hiddenDIV()" >确定</a>&nbsp;&nbsp;
		<a href="#" onclick="selectAll()" >全选</a>&nbsp;&nbsp;
		<a href="#" onclick="clearCheckbox()">清空</a>
	</div>
	
</div>
</body>
</html>