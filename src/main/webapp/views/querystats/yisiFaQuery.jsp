<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@page import="com.ksource.syscontext.Const"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet"	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"	type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript"	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript"	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	type="text/javascript"></script>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js"	type="text/javascript"></script> 
<script src="${path}/resources/script/cleaner.js"></script>
<style type="text/css">
 input.buttonanniu {
	background: #F6FAFE;
	border: 1px solid #4496EA;
	height: 25px;
	font-size:12px;
	border-radius: 5px;
	margin-right: 5px;
	padding:2px 12px;
	box-shadow: 0px 1px 2px #999;
	-webkit-box-shadow: 0px 1px 2px #999;
	-moz-box-shadow: 0px 1px 2px #999; 
}

input.buttonanniu:hover {
	border: 1px solid #1081E9;
	background: #2F95EA;
	color: #fff;	
	padding:2px 12px;
	margin-right: 5px;
	font-size:12px;
	box-shadow: 0px 1px 2px #999;
	-webkit-box-shadow: 0px 1px 2px #999;
	-moz-box-shadow: 0px 1px 2px #999;
	background: -webkit-gradient(linear, 0 0, 0 100%, from(#55A9EE),
		to(#2990F0));
	background: -moz-linear-gradient(top, #55A9EE, #2990F0);
}
table.searchform td input[type=checkbox]{ float:left; }
table.searchform td label{ display:block; float:left; margin-top:2px; margin-right:2px; }
</style>
<script type="text/javascript">
var districtZTree;
$(function(){
	//案件信息表单验证
	jqueryUtil.formValidate({
		form:"queryForm",
		rules:{
			indexInput:{required:true}
		},
		messages:{
			indexInput:{required:"请选择筛查条件"}
		},submitHandler:function(form){
		      if($("#queryForm").valid()){
		    	  form.submit();
		    	  top.closeLayer();
		    	  clearChecked();		    	  
		      }
		}
	});
	
	var minCaseInputTime = document.getElementById('minCaseInputTime');
	var maxCaseInputTime = document.getElementById('maxCaseInputTime');
	minCaseInputTime.onclick = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){top.closeLayer();clearChecked();}});
	}
	maxCaseInputTime.onclick = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){top.closeLayer();clearChecked();}});
	}
	
	hiddenDIV();
	//组织机构树
	var setting1 = {
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "upId"
				}
			},
			async: {
				enable: true,
				url: "${path}/system/org/loadChildOrgByParentId",
				autoParam: ["id"],
				otherParam: ["orgType", "1"]
			},
			callback: {
				onClick: zTreeOnClick
			}
		};
	orgZTree = $.fn.zTree.init($("#dropdownMenu"),setting1);
	
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
				onClick: districtZTreeOnClick
				}
		};
	districtZTree = $.fn.zTree.init($("#districtZtree"),setting);
	
	//鼠标点击页面其它地方，组织机构树消失
	$('html').bind("mousedown", 
		function(event){
			if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
				hideMenu();
			}
			if (!(event.target.id == "indexDIV" || event.target.id=="indexValue" || $(event.target).parents("#indexDIV").length>0)) {
				hiddenDIV();
			}
			if (!(event.target.id == "districtZtreeDiv" || $(event.target).parents("#districtZtreeDiv").length>0)) {
				hideDistrictZtreeMenu();
			}			
		}
	);
});

	function showMenu() {
		var cityObj = $("#orgName");
		var cityOffset = $("#orgName").offset();
		$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
		
	}
	function hideMenu() {
		$("#DropdownMenuBackground").fadeOut("fast");
	}

	function zTreeOnClick(event, treeId, treeNode) {
		if (treeNode) {
			$("#orgName").val(treeNode.name);
			$("#orgId").val(treeNode.id);
			$("#orgPath").val(treeNode.orgPath);
			hideMenu();
		}
	}
	function emptyOrg(){
		document.getElementById('orgName').value = '';
		document.getElementById('orgId').value = '';
		document.getElementById('orgPath').value = '';
	}
	function isClearOrg(){
			var value =$("#orgName").val();
			if($.trim(value)==""){
			     $("#orgId").val("");
			     $("#orgPath").val("");
			}
			return true ;
	}
	function clearAll() {
		$("input[type='text']").each(function() {
			$(this).val("") ;			
		}) ;
		$("select").each(function() {
			$(this).children().first().attr("selected","selected") ;
		}) ;
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
		 $("input[name='indexInput']").each(function() {
			var temp = $(this).attr("checked") ;
			if( temp == 'checked') {
				index += $(this).val()+',';
				i++ ;
			}
		 }); 
		 $('#queryForm')[0].action="${path }/query/yisiFaCase/query?indexList="+index;
		 $('#queryForm').submit();                
	}
	
	function showDistrictZtree(){
		var cityObj = $("#districtName");
		var cityOffset = $("#districtName").offset();
		$("#districtZtreeDiv").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight(true) + "px"}).slideDown("fast");
	}

	function clearDistrict() {    
		document.getElementById('districtName').value = '';
		document.getElementById('districtCode').value = '';		
	}
	function districtZTreeOnClick(event, treeId, treeNode) {
		if (treeNode) {
			$("#districtName").val(treeNode.name);
			$("#districtCode").val(treeNode.id);
			hideDistrictZtreeMenu();
			top.closeLayer();
			clearChecked();
		}
	}
	function hideDistrictZtreeMenu(){
		$("#districtZtreeDiv").fadeOut("fast");
	}	
	
	function openReportCreate(docType,fileName){
		if("dsjfx2" == docType){
			$("#districtName").rules("add",{required:true,messages:{required:"请选择行政区划"}});	
			$("input[name='indexInput']").rules("remove");
			if($("#queryForm").valid()){
				var formSerialize = $("#queryForm").serialize();
				top.openOfficeReport(docType,fileName,formSerialize);
				top.closeLayer();
				clearChecked();
			};
			$("#districtName").rules("remove");
			$("input[name='indexInput']").rules("add",{required:true,messages:{required:"请选择筛选条件"}});	
		}else if("dsjfx3" == docType){
			$("#districtName").rules("add",{required:true,messages:{required:"请选择行政区划"}});	
			if($("#queryForm").valid()){
				var formSerialize = $("#queryForm").serialize();
				var index=''; 
				$("input[name='indexInput']").each(function() {
						var temp = $(this).attr("checked") ;
						if( temp == 'checked') {
							index += $(this).val()+',';
						}
					 });
				var caseIdAry = "";
				for(var key in top.caseArrList ){
					caseIdAry += key+",";
				}
				caseIdAry = trimSufffix(caseIdAry,",");
				formSerialize+="&indexList="+index +"&caseIdAry="+caseIdAry;
				top.openOfficeReport(docType,fileName,formSerialize);
				top.closeLayer();
				clearChecked();
			}
			$("#districtName").rules("remove");
		}else{
			var formSerialize = $("#queryForm").serialize();
			top.openOfficeReport(docType,fileName,formSerialize);
			top.closeLayer();
			clearChecked();
		}
	}	
	
	function checkAll(obj){
		var districtCode = $("#districtCode").val();
		districtCode = trimEndChart(districtCode,'00');
		var districtName = $("#districtName").val();
		var errorCount = 0;
		$("[name='check']").each(function(){
			$(this).prop("checked",obj.checked);
			var caseNoVal = $(this).attr("caseNoVal");
			var caseNameVal = $(this).attr("caseNameVal");
			var districtCodeVal = $(this).attr("districtCodeVal");
			var caseId = $(this).val();
			if(obj.checked){
				var flag = false;
				for(var key in top.caseArrList){
					if(caseId == key){
						flag = true;
						break;
					}
				}
				if(!flag){
	 				if(districtCode != null && districtCode != '' && districtCodeVal.indexOf(districtCode) >= 0){
	 					top.caseArrList[caseId] = caseNoVal+"-"+caseNameVal;
	 					top.openCaseListLayer(caseId,caseNoVal);
					}else if(districtCode == null || districtCode == ''){
	 					top.caseArrList[caseId] = caseNoVal+"-"+caseNameVal;
	 					top.openCaseListLayer(caseId,caseNoVal);
					}else{
						errorCount += 1;
					}
				}
			}else{
				delete top.caseArrList[caseId];
				top.delFromCaseLayer(caseId);
			}
		});
		if(errorCount > 0){
			top.art.dialog({
			    title: '警告',
			    lock:true,
			    content: "所选案件不属于"+districtName+"，请重新筛查！",
			    yesFn: function () {
			    	clearChecked();
			    }
			});
		}
	}

	function checkCase(ischecked,caseId,caseNoVal,caseNameVal,districtCodeVal){
		if(ischecked){
			var flag = false;
			var districtCode = $("#districtCode").val();
			var districtName = $("#districtName").val();
			districtCode = trimEndChart(districtCode,'00');
			for(var key in top.caseArrList){
				if(caseId == key){
					flag = true;
					break;
				}
			}
			if(!flag){
				if(districtCode != null && districtCode != '' && districtCodeVal.indexOf(districtCode) >= 0){
						top.caseArrList[caseId] = caseNoVal+"-"+caseNameVal;
						top.openCaseListLayer(caseId,caseNoVal);
				}else if(districtCode == null || districtCode == ''){
						top.caseArrList[caseId] = caseNoVal+"-"+caseNameVal;
						top.openCaseListLayer(caseId,caseNoVal);
				}else{
					top.art.dialog({
					    title: '警告',
					    lock:true,
					    content: "所选案件不属于"+districtName+"，请重新筛查！",
					    yesFn: function () {
					    	clearChecked();
					    }
					});
				}
			}
		}else{
			delete top.caseArrList[caseId];
			top.delFromCaseLayer(caseId);
		}
	}

	function clearChecked(){
		$(":checkbox","#case").each(function(){
			$(this).prop("checked",false);
		});
	}
	
    function setQueryScope(scopeValue){
        $("#queryScope").val(scopeValue);
    }
    
    function setDistrictQueryScope(scopeValue){
        $("#districtQueryScope").val(scopeValue);
    }
    
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">大数据分析筛查</legend>
<form id="queryForm" method="post" onsubmit="return isClearOrg()">
	<input type="hidden" id="orgTopId" name="orgTopId" value="${orgTopId}"/>
	<input type="hidden" id="orgTopDistrictCode" name="orgTopDistrictCode" value="${orgTopDistrictCode}"/>
				<table width="100%" class="searchform">
				<tr>
                    <td width="15%" align="right">案件编号</td>
                    <td width="17%" align="left">
                        <input type="text" name="caseNo" id="caseNo" value="${param.caseNo }" class="text"/>
                    </td>			
					<td width="10%" align="right">案件名称</td>
                    <td width="20%" align="left">
                        <input type="text" name="caseName" value="${fn:replace(param.caseName,"\"","&quot;")}" class="text" style="width:75%;"/>
                    </td>
                    <td width="10%" align="right">行政区划</td>
					<td width="25%" align="left">
						<input type="text" class="text" id="districtName" name="districtName" value="${param.districtName}" onfocus="showDistrictZtree(); return false;" readonly="readonly"/>
						<input type="hidden" name="districtCode" id="districtCode" value="${param.districtCode}"/> <a href="#" onclick="clearDistrict()" class="aQking qingkong">清空</a>
					    <input type="hidden" name="districtQueryScope" id="districtQueryScope" value="${param.districtQueryScope}"/>
					</td>
					<td rowspan="3" valign="middle" align="left">
						<input type="button" value="查 询" class="btn_query" onclick="search()"/>
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">录入单位</td>
					<td width="25%" align="left">
						<input type="text" class="text" name="orgName" id="orgName" onfocus="showMenu(); return false;" value="${param.orgName }" readonly="readonly" />
						<input type="hidden" name="orgId" id="orgId" value="${param.orgId}" class="text"/> 
						<input type="hidden" name="orgPath" id="orgPath" value="${param.orgPath}"/>
						<input type="hidden" name="queryScope" id="queryScope" value="${param.queryScope}"/>
						<a href="javascript:void();" onclick="emptyOrg()" class="aQking qingkong">清空</a>
					</td> 
					<td width="10%" align="right">录入时间</td>
                    <td width="20%" align="left">
                       <input type="text" name="minCaseInputTime" id="minCaseInputTime" value="${param.minCaseInputTime}" style="width: 32.5%" class="text"/>
                       	 至
                        <input type="text" name="maxCaseInputTime" id="maxCaseInputTime" value="${param.maxCaseInputTime }" style="width: 32.5%" class="text"/>
                    </td> 				  
				</tr>
				<tr>
					<td  width="15%" align="right" >
						筛选条件(可多选)
					</td>
					<td width="85%" align="left" colspan="6">
					<input type="hidden" name="index" value="${index}"></input>
					<input id="A" type="checkbox" name="indexInput" value ="A" <c:if test="${fn:contains(index,'A')}">checked="checked"</c:if>/><label for="A" class="indexSpan">同一违法行为发生地</label>
					<input id="B" type="checkbox" name="indexInput" value ="B" <c:if test="${fn:contains(index,'B')}">checked="checked"</c:if>/><label for="B"  class="indexSpan">同一违法行为发生时间</label>
					<input id="C" type="checkbox" name="indexInput" value ="C" <c:if test="${fn:contains(index,'C')}">checked="checked"</c:if>/><label for="C"  class="indexSpan">同一涉案物品</label>
					<input id="D" type="checkbox" name="indexInput" value ="D" <c:if test="${fn:contains(index,'D')}">checked="checked"</c:if>/><label for="D"  class="indexSpan">同一鉴定</label>
					<input id="E" type="checkbox" name="indexInput" value ="E" <c:if test="${fn:contains(index,'E')}">checked="checked"</c:if>/><label for="E"  class="indexSpan">同一处罚对象（单位）</label> 
					<input id="F" type="checkbox" name="indexInput" value ="F" <c:if test="${fn:contains(index,'F')}">checked="checked"</c:if>/><label for="F"  class="indexSpan">同一处罚对象（个人）</label>
					</td>
				</tr>
			</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
<display:table name="caseList" uid="case" cellpadding="0"
	cellspacing="0" requestURI="${path }/query/yisiFaCase/query">
	<display:caption style="text-align:left; margin-bottom:5px;">
		<input type="button" class="buttonanniu"  value="总体报告" onclick="openReportCreate('dsjfx1','大数据分析筛查报告.doc')" />
		<c:if test="${districtLevel != 3 }">
		<input type="button" class="buttonanniu"  value="线索筛查通知书" onclick="openReportCreate('dsjfx2','大数据分析筛查案件线索通知书.doc')" />
		<input type="button" class="buttonanniu"  value="重点案件线索通知书" onclick="openReportCreate('dsjfx3','大数据分析重点案件线索通知书.doc')" />
	    </c:if>
	</display:caption>
	<display:column title="<input type='checkbox' onclick='checkAll(this)'/>">
		<input type="checkbox" name="check" value="${case.caseId}" caseNoVal="${case.caseNo}" caseNameVal="${case.caseName }" districtCodeVal="${case.districtCode }" onclick="checkCase(this.checked,'${case.caseId}','${case.caseNo}','${case.caseName}','${case.districtCode  }')"/>
	</display:column>	
	<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${case_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + case_rowNum}
			</c:if>
	</display:column>
	<display:column title="案件编号" style="text-align:left;">
		<a href="javascript:;" onclick="top.showCaseProcInfo('${case.caseId}');">${case.caseNo}</a>
	</display:column>
		<display:column title="案件名称" style="text-align:left;" class="cutout">
			<span title="${case.caseName}">${fn:substring(case.caseName,0,20)}${fn:length(case.caseName)>20?'...':''}</span>
		</display:column>
		<display:column title="录入单位" style="text-align:center;" class="cutout">
			<span title="${case.orgName}">${case.orgName}</span>
		</display:column>
		<%-- <display:column title="状态" property="caseStateName" style="text-align:left;"></display:column> --%>
		<display:column title="违法行为发生时间">
			<fmt:formatDate value="${case.anfaTime }" pattern="yyyy-MM-dd" />
		</display:column>
		<display:column title="违法行为发生地址" property="anfaAddressName">
		</display:column>		
		<display:column title="涉案物品"> 
			<span title="${case.goodsInvolved}">${fn:substring(case.goodsInvolved,0,15)}${fn:length(case.goodsInvolved)>15?'...':''}</span>
		</display:column>
</display:table>

<div id="indexDIV" style="display:none; position:absolute; height:210px;width:20%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
	    <a href="#" onclick="hiddenDIV()" >确定</a>&nbsp;&nbsp;
		<a href="#" onclick="selectAll()" >全选</a>&nbsp;&nbsp;
		<a href="#" onclick="clearCheckbox()">清空</a>
	</div>
	<div align="left">
		<input type="hidden" name="index" value="${index}"></input>
		<span class="tipForSelect__">
		<input type="checkbox" name="indexList" value ="A" <c:if test="${fn:contains(index,'A')}">checked="checked"</c:if>/><span  class="indexSpan">同一违法行为发生地</span> <br/>
		<input type="checkbox" name="indexList" value ="B" <c:if test="${fn:contains(index,'B')}">checked="checked"</c:if>/><span class="indexSpan">同一违法行为发生时间</span> <br/>
		<input type="checkbox" name="indexList" value ="C" <c:if test="${fn:contains(index,'C')}">checked="checked"</c:if>/><span  class="indexSpan">同一涉案物品</span> <br/>
		<input type="checkbox" name="indexList" value ="D" <c:if test="${fn:contains(index,'D')}">checked="checked"</c:if>/><span  class="indexSpan">同一鉴定</span> <br/>
		<input type="checkbox" name="indexList" value ="E" <c:if test="${fn:contains(index,'E')}">checked="checked"</c:if>/><span  class="indexSpan">同一处罚对象（单位）</span> <br/>
		<input type="checkbox" name="indexList" value ="F" <c:if test="${fn:contains(index,'F')}">checked="checked"</c:if>/><span  class="indexSpan">同一处罚对象（个人）</span> <br/>
		</span>
	</div> 
</div>
</div>
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
    <span style="margin-left: 10px;">查询范围：
       <input type="radio" name="queryScopeQ" value="1" id="queryScope1"  onclick="setQueryScope(1);" <c:if test="${empty param.queryScope || param.queryScope == 1}">checked="checked"</c:if>/>
       <label for="queryScope1">包含下级</label>
       <input type="radio" name="queryScopeQ" value="2" id="queryScope2" onclick="setQueryScope(2);"  <c:if test="${param.queryScope == 2}">checked="checked"</c:if>/>
       <label for="queryScope2">本级</label>
    </span>	
	<ul id="dropdownMenu" class="ztree"></ul>
</div>
<div id="districtZtreeDiv" style="display:none; position:absolute; height:200px;width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
    <span style="margin-left: 10px;">查询范围：
       <input type="radio" name="districtQueryScope" value="1" id="districtQueryScope1"  onclick="setDistrictQueryScope(1);" <c:if test="${empty param.districtQueryScope || param.districtQueryScope == 1}">checked="checked"</c:if>/>
       <label for="districtQueryScope1">包含下级</label>
       <input type="radio" name="districtQueryScope" value="2" id="districtQueryScope2" onclick="setDistrictQueryScope(2);"  <c:if test="${param.districtQueryScope == 2}">checked="checked"</c:if>/>
       <label for="districtQueryScope2">本级</label>
    </span>	
	<ul id="districtZtree" class="ztree"></ul>
</div>
</body>
</html>