<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib uri="quiee" prefix="report" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>综合统计</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path }/resources/script/highcharts/highcharts.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
$(function(){
	hiddenDIV();
	//日期控件
	  var startTime = document.getElementById('startTime');
	  startTime.onfocus = function(){
		  WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'<fmt:formatDate value="${acti.startTime}" pattern="yyyy-MM-dd"/>',maxDate:'<fmt:formatDate value="${acti.endTime}" pattern="yyyy-MM-dd"/>'});
	  }
	  var endTime = document.getElementById('endTime');
	  endTime.onfocus = function(){
		  WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'<fmt:formatDate value="${acti.startTime}" pattern="yyyy-MM-dd"/>',maxDate:'<fmt:formatDate value="${acti.endTime}" pattern="yyyy-MM-dd"/>'});
	  }	 
	zTree=	$('#dropdownMenu').zTree({
		isSimpleData:true,
		treeNodeKey : "id",
	    treeNodeParentKey : "upId",
	    async: true,
		asyncUrl:"${path}/activity/query/loadActivityOrg/"+'${acti.id}',
		checkable:true
	});
	$('html').bind("mousedown", 
			function(event){
				if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
					hideMenu();
				}
				if (!(event.target.id == "indexDIV" || $(event.target).parents("#indexDIV").length>0)) {
					hiddenDIV();
				}
			});

	$("#report1").attr("style","width:100%");
});
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
		 $('#findForm')[0].action="${path}/activity/stats/general/?indexList="+index+"&activityId="+${acti.id};
		 $('#findForm').submit();                
	}
	
	function selectAllNodes() {
		zTree.checkAllNodes(true);
	}
</script>
<style>
.report1 { display:none}
</style>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">专项活动统计</legend>
<form action="" id="findForm" method="post">
<input type="hidden" name="orgIds" id="orgIds" value="${param.orgIds}"/>
	<table width="100%" class="searchform">
			<tr>
				<td width="15%" align="right">所属单位</td>
				<td width="25%" align="left"><input type="text" class="text" name="orgNames" id="orgNames"  onfocus="showMenu(); return false;" value="${param.orgNames}" readonly="readonly"/></td>
				<td width="15%" align="right">
					案件录入时间
				</td>
				<td width="25%" align="left">
					<input type="text" class="text"  name="startTime" id="startTime" value="${param.startTime}" style="width:36%"/>
					到
					<input type="text" class="text"  name="endTime" id="endTime" value="${param.endTime}"style="width:36%"/>
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
					<input type="text" class="text"  style="width:70%" id="indexValue" name="indexInput" value="${indexList}" onfocus="showList(); return false;" readonly="readonly"/>
				</td>		
			</tr>
	</table>
</form>
</fieldset>
	
	<table align="center" style="width: 100%;">
	<!--  <caption style="text-align:left;font-weight:bold;">所属专项活动：${acti.name}</caption>  -->
		<tr>
			<td align="right">
				<jsp:include page="/quiee/reportJsp/toolbar.jsp" flush="false" />
				<report:html name="report1" reportFileName="activityStats.raq"
					funcBarLocation=""
					needPageMark="yes"
					generateParamForm="no"
					needLinkStyle="yes"
					params="${parameter}"
					width="-1"
                     needSaveAsPdf="yes" needSaveAsWord="yes" pdfExportStyle="text,0"
									 excelPageStyle="0" needSaveAsExcel="yes" excelUsePaperSize="no"
									 needPrint="yes" printButtonWidth="80" needPrintPrompt="yes"
									 needSelectPrinter="yes" savePrintSetup="yes" displayNoLinkPageMark="yes" saveAsName="专项活动案件统计"
				/>
			</td>
		</tr>
	</table>
</div>
	
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px;width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
	<a href="javascript:void(0);" onclick="selectAllNodes()">全选</a>
		<a href="#" onclick="clearDiv()">清空</a>
	</div>
	<ul id="dropdownMenu" class="tree"></ul>
</div>
<div id="indexDIV" style="display:none; position:absolute; height:200px;width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
	    <a href="#" onclick="hiddenDIV()" >确定</a>&nbsp;&nbsp;
		<a href="#" onclick="selectAll()" >全选</a>&nbsp;&nbsp;
		<a href="#" onclick="clearCheckbox()">清空</a>
	</div>
	<div align="left">
		<input type="hidden" name="index" value="${index}"></input>
		<input type="checkbox" name="indexList" value ="I" <c:if test="${fn:contains(index,'I')}">checked="checked"</c:if>/><span  class="indexSpan">涉案金额</span> </br>
		<input type="checkbox" name="indexList" id="案件总数"  value ="A" <c:if test="${fn:contains(index,'A')}">checked="checked"</c:if>/><span class="indexSpan">案件总数</span> </br>
		<input type="checkbox" name="indexList" value ="B" <c:if test="${fn:contains(index,'B')}">checked="checked"</c:if>/><span  class="indexSpan">行政处罚案件数</span> </br>
		<input type="checkbox" name="indexList" value ="C" <c:if test="${fn:contains(index,'C')}">checked="checked"</c:if>/><span  class="indexSpan">涉嫌犯罪案件数</span> </br>
		<input type="checkbox" name="indexList" value ="D" <c:if test="${fn:contains(index,'D')}">checked="checked"</c:if>/><span  class="indexSpan">移送公安机关案件数</span> </br>
		<input type="checkbox" name="indexList" value ="E" <c:if test="${fn:contains(index,'E')}">checked="checked"</c:if>/><span  class="indexSpan">公安机关立案案件数</span> </br>
		<input type="checkbox" name="indexList" value ="F" <c:if test="${fn:contains(index,'F')}">checked="checked"</c:if>/><span  class="indexSpan">检察机关批准逮捕案件数</span> </br>
		<input type="checkbox" name="indexList" value ="G" <c:if test="${fn:contains(index,'G')}">checked="checked"</c:if>/><span  class="indexSpan">法院判决案件数</span> </br>
		<input type="checkbox" name="indexList" value ="H" <c:if test="${fn:contains(index,'H')}">checked="checked"</c:if>/><span  class="indexSpan">结案案件数</span> </br>
		
	</div> 
</div>
</body>
</html>