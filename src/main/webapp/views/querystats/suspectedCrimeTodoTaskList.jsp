<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css" />
<link  rel="stylesheet" type="text/css" href="${path }/resources/script/accuseSelector/accuseSelector.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/popover/jquery.webui-popover.min.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>	
<script src="${path }/resources/script/accuseSelector/accuseSelector.js"></script>
<script	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"	type="text/javascript"></script> 
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
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

.regionTreediv, .regionTreedivv {
	float: left;
	clear: both;
	margin: 0px 5px 10px 0px;
	display: block;
}

.regionTreediv {
	min-width: 230px;
}

.regionTreedivv {
	min-width: 194px;
}

#queryScopeFilter {
	float: left;
	width: 175px;
}

.tabRight {
	margin-top: 2px;
}
</style>
<script type="text/javascript">
	var zTree1Setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "upId",
			},
			key: {
// 				name: "districtName"
			}
		},
		async: {
			enable: true,
			url: "${path}/system/org/loadChildOrgByOrgType",
			otherParam: {"orgType":"1"}
		},
		callback: {
			onClick: zTreeOnClick
		}
	};
	var zTreeSetting = {
		async: {
			enable: true,
			url: '<c:url value="/workflow/task/getUserTree"/>',
			autoParam: ["id","isDept"],
		},
		callback: {
			beforeClick:function(treeId, treeNode){
				 if(treeNode.isDept===1 || treeNode.isDept===0){zTree.expandNode(treeNode,true,false);return false;}return true;
			 },
			onClick: function (event, treeId, treeNode) {
				 art.dialog.confirm('请确认任务分配？', function(){
					 //执行任务分派
					 var userId = treeNode.id;
					 //执行分派
					$.getJSON("<c:url value="/workflow/task/taskFenpai"/>", { taskId: window.curFenpaiTaskId, userId: userId }, function(response){
						if(response.result){
							art.dialog.tips("任务分派成功！", 2.0);
							//不是当前用户，删除当前行
							 if(userId!='<%=SystemContext.getCurrentUser(request).getUserId()%>'){
								$(window.curADom).parent('td').parent('tr').remove();
							 }
						}else{art.dialog.tips("任务分派失败！", 2.0);}
					}); 
					 var list = art.dialog.list;
					 for (var i in list) {
					     list[i].close();
					 };
				 });
			 }
		}
	};
	var districtZTreeSetting = {
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
			onClick: districtZTreeOnClick
		}
	};
var zTree1;
var zTree;
var districtZTree;
$(function(){
	//案件信息表单验证
	jqueryUtil.formValidate({
		form:"queryForm",
		rules:{},
		messages:{}
	});
	
	$("#sub").click(function(){
		$("#queryForm").submit();
		top.closeLayer();
		clearChecked();
	});
	
	
	var accuseSelectorId=accuseSelector.exec({labelC:'#accuseC',valC:'#accuseId',control:'#accuseControl',accuseInfoNames:'#accuseInfoNames'});
	<c:if test="${not empty accuseInfos}">
		accuseSelector.result(accuseSelectorId,${accuseInfos});
	</c:if>
	var minCaseInputTime = document.getElementById('minCaseInputTime');
	var maxCaseInputTime = document.getElementById('maxCaseInputTime');
	minCaseInputTime.onclick = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){top.closeLayer();clearChecked();}});
	}
	maxCaseInputTime.onclick = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){top.closeLayer();clearChecked();}});
	}
	
	zTree1 = $.fn.zTree.init($("#dropdownMenu"),zTree1Setting);
	
	districtZTree =	$.fn.zTree.init($("#districtZtree"),districtZTreeSetting);
	//初始分派树
	zTree=$.fn.zTree.init($("#usertree"),zTreeSetting);
	
	//鼠标点击页面其它地方，组织机构树消失
	$("html").bind("mousedown", 
			function(event){
				if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
					hideMenu();
				}
				if (!(event.target.id == "districtZtreeDiv" || $(event.target).parents("#districtZtreeDiv").length>0)) {
					hideDistrictZtreeMenu();
				}
				if(!(event.target.id=="DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0
					||event.target.id=="districtZtreeDiv" || $(event.target).parents("#districtZtreeDiv").length>0
					||event.target.id=="accuseControl" || event.target.id=="accuseDropboxsid2" || $(event.target).parents("#accuseDropboxsid2").length>0)){
					showOffice();
				}else{
					hideOffice();
				}
			});
});
function fenpai(aDom,taskId,caseNo){
	window.curFenpaiTaskId = taskId;
	window.curADom = aDom;
	art.dialog({
	    id: "taskFenpai",
	    padding: 0,
	   	lock:true,
	   	height:300,
	   	opacity: 0.1, // 透明度
	    title: '任务分派：'+caseNo,
	    content: document.getElementById('popupDiv')
	});
	
	//载入所有节点
	var nodes = zTree.getNodes();
	$.each(nodes,function(i,n){
		zTree.reAsyncChildNodes(n, "refresh");
	});
}
function addSupervision(caseId,procKey){
	var addId = "add" + caseId;
	var deleteId = "delete" + caseId;
	top.art.dialog.confirm('您确认要监督此案件吗？',function(){
			$.post("${path }/casehandle/caseSupervision/supervision",{caseId:caseId,procKey:procKey},function(result){
				if(result){
					$("#"+addId).parent().append("<a id="+deleteId+" href='javascript:;' onclick='deleteSupervision("+caseId+",\""+procKey+"\");'>取消监督</a>");
					$("#"+addId).remove();
				}
			});
		});
}
function deleteSupervision(caseId,procKey){
	var tdId = "supervision" + caseId;
	var addId = "add" + caseId;
	var deleteId = "delete" + caseId;
	top.art.dialog.confirm('您确认取消此案件的监督吗？',function(){
			$.post("${path }/casehandle/caseSupervision/delete",{caseId:caseId,procKey:procKey},function(result){
				if(result){
					$("#"+deleteId).parent().append("<a id="+addId+" href='javascript:;' onclick='addSupervision("+caseId+",\""+procKey+"\");'>监督此案</a>");				
					$("#"+deleteId).remove();
					top.changeMsgCount(caseId,4);
				}
			});
		});
}
function showMenu() {
	var cityObj = $("#orgName");
	var cityOffset = $("#orgName").offset();
	$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight(true) + "px"}).slideDown("fast");
	if(typeof hideOffice != 'undefined'  
        && hideOffice instanceof Function){
		hideOffice();
	}
	
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

function showDistrictZtree(){
	var cityObj = $("#districtId");
	var cityOffset = $("#districtId").offset();
	$("#districtZtreeDiv").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight(true) + "px"}).slideDown("fast");
	if(typeof hideOffice != 'undefined'  
        && hideOffice instanceof Function){
		hideOffice();
	}
}

function clearDistrict() {    
	document.getElementById('districtId').value = '';
	document.getElementById('districtId_hidden').value = '';		
}
function districtZTreeOnClick(event, treeId, treeNode) {
	if (treeNode) {
		$("#districtId").val(treeNode.name);
		$("[name='districtId']").val(treeNode.id);
		hideDistrictZtreeMenu();
		top.closeLayer();
		clearChecked();
	}
}
function hideDistrictZtreeMenu(){
	$("#districtZtreeDiv").fadeOut("fast");
}

function openReportCreate(docType,fileName){
	var caseIdAry = "";
	if(top.caseArrList != null && typeof(top.caseArrList) != 'undefined'){
		for(var key in top.caseArrList ){
			caseIdAry += key+",";
		}
		caseIdAry = trimSufffix(caseIdAry,",");
	}
	if("sxfz2" == docType){
		$("#districtId").rules("add",{required:true,messages:{required:"请选择地市行政区划"}});	
		if($("#queryForm").valid()){
			var formSerialize = $("#queryForm").serialize();
			formSerialize += "&caseIdAry="+caseIdAry;
			top.openOfficeReport(docType,fileName,formSerialize);
			top.closeLayer();
			clearChecked();
		}
		$("#districtId").rules("remove");
	}else{
		var formSerialize = $("#queryForm").serialize();
		formSerialize += "&caseIdAry="+caseIdAry;
		top.openOfficeReport(docType,fileName,formSerialize);
		top.closeLayer();
		clearChecked();
	}
}

var height=1;
var width=1;
function hideOffice(){
	var officeDiv=$("#office-div");
	if(officeDiv.length==0) return;
	if(height==1){
		height=officeDiv.height();
	}
	if(width==1){
		width=officeDiv.width();
	} 
	officeDiv.height(1);
	officeDiv.width(1);
}
function showOffice(){
	var officeDiv=$("#office-div");
	if(officeDiv.length==0) return;
	if(height!=1&&width!=1){
		officeDiv.height(height);
		officeDiv.width(width);
	}
}


function checkAll(obj){
	var districtCode = $("#districtId_hidden").val();
	districtCode = trimEndChart(districtCode,'00');
	var districtName = $("#districtId").val();
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
		var districtCode = $("#districtId_hidden").val();
		var districtName = $("#districtId").val();
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
	$(":checkbox","#task").each(function(){
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
<title>涉嫌犯罪案件待办任务列表</title>
</head>
<body>
<div class="panel">
    <fieldset class="fieldset">
        <legend class="legend">涉嫌犯罪案件线索筛查</legend>
        <form id="queryForm" action="${path }/query/myTask/suspectedCrimeTodo" method="post">
            <table width="100%" class="searchform">
                <tr>
                    <td width="10%" align="right">案件编号</td>
                    <td width="20%" align="left">
                        <input type="text" name="caseNo" id="caseNo" value="${param.caseNo }" class="text"/>
                    </td>
                    <td width="10%" align="right">案件名称</td>
                    <td width="20%" align="left">
                        <input type="text" name="caseName" value="${fn:replace(param.caseName,"\"","&quot;")}" class="text" style="width:75%;"/>
                    </td>
                    <td width="10%" align="right">行政区划</td>
					<td width="20%" align="left">
						<input type="text" class="text" id="districtId" name="districtName" value="${param.districtName}" onfocus="showDistrictZtree(); return false;" readonly="readonly" style="width:60%"/>
						<input type="hidden" name="districtId" id="districtId_hidden" value="${param.districtId}"/>
						<input type="hidden" name="districtQueryScope" id="districtQueryScope" value="${param.districtQueryScope}"/>
						<a href="#" onclick="clearDistrict()" class="aQking qingkong">清空</a>
					</td>
                    <td width="10%" align="left" rowspan="2" valign="middle">
                        <input id="sub" type="button" value="查 询" class="btn_query" />
                    </td>
                </tr>
                <tr>
					<td width="10%" align="right">录入单位</td>
					<td width="20%" align="left">
						<input type="text" name="orgName" id="orgName" onfocus="showMenu(); return false;" value="${param.orgName }" readonly="readonly" class="text" style="width:70%"/>
						<input type="hidden" name="orgId" id="orgId" value="${param.orgId}" class="text"/> 
						<input type="hidden" name="orgPath" id="orgPath" value="${param.orgPath}"/>
						<input type="hidden" name="queryScope" id="queryScope" value="${param.queryScope}"/>
						<a href="javascript:void();" onclick="emptyOrg()" class="aQking qingkong">清空</a>
					</td>
					<td width="10%" align="right">录入时间</td>
                    <td width="20%" align="left">
                       <input type="text" name="minCaseInputTime" id="minCaseInputTime" value="${param.minCaseInputTime}" class="text"
                               style="width: 32.5%"/>
                       	 至
                        <input type="text" name="maxCaseInputTime" id="maxCaseInputTime" value="${param.maxCaseInputTime }" class="text" style="width: 32.5%"/>
                    </td>                      
                 </tr>
                 <tr>
                    <td width="10%" align="right">疑似罪名</td>
                    <td width="17%" align="left" colspan="3">
                      	<input type="hidden" name="accuseId" id="accuseId" value=""/>
                      	<input type="hidden" name="accuseInfoNames" id="accuseInfoNames" value=""/>
		                	<div style="overflow:hidden;">
			                	<div id="accuseC" style="display:inline-block; height: 40px;width: 92%;overflow: auto;"></div>
		               	 </div>
		               	<a href="javascript:void(0)" id="accuseControl">选择罪名</a>
                    </td>
				</tr>
            </table>
        </form>
    </fieldset>
    <!-- 查询结束 -->
<br/>
<c:choose>
<c:when test="${not empty casePagination.list }">
	<display:table name="casePagination"  uid="caseBasic"  cellpadding="0" requestURI="/query/myTask/suspectedCrimeTodo" >
	<display:caption style="text-align:left; margin-bottom:5px;">
			<input type="button" class="buttonanniu"  value="总体报告" onclick="openReportCreate('sxfz1','涉嫌犯罪案件线索筛查报告.doc')" />
			<c:if test="${districtLevel!=null and districtLevel < 3 }">
				<input type="button" class="buttonanniu"  value="筛查线索通知书" onclick="openReportCreate('sxfz2','涉嫌犯罪案件筛查线索通知书.doc')" />
			</c:if>
	</display:caption>
	<display:column title="<input type='checkbox' onclick='checkAll(this)'/>">
		<input type="checkbox" name="check" value="${caseBasic.caseId}" caseNoVal="${caseBasic.caseNo}" caseNameVal="${caseBasic.caseName }" districtCodeVal="${caseBasic.districtCode }" onclick="checkCase(this.checked,'${caseBasic.caseId}','${caseBasic.caseNo}','${caseBasic.caseName}','${caseBasic.districtCode}')"/>
	</display:column>	
	<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${caseBasic_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + caseBasic_rowNum}
			</c:if>
		</display:column>
	<display:column title="案件编号" style="text-align:left;">
		<a href="javascript:;" onclick="top.showCaseProcInfo('${caseBasic.caseId}','','${caseBasic.procKey}');">${caseBasic.caseNo }</a>
	</display:column>
	<display:column title="案件名称"  style="text-align:left;">
		<span id="t_${caseBasic.caseId}">${fn:substring(caseBasic.caseName,0,20)}${fn:length(caseBasic.caseName)>20?'...':''}</span>
		<script type="text/javascript">
				if(${not empty caseBasic.accuseNameStr }){
	 				$("#t_${caseBasic.caseId}").poshytip({
			            className: 'tip-yellowsimple',
                        content:'${caseBasic.accuseNameStr}',
			            showTimeout: 1,
                        slide: false,
                        fade: false,
                        alignTo: 'target',
                        alignX: 'right',
                        alignY: 'center',
                        offsetX: 2,
                        allowTipHover:true
			        });
				}
			</script>
	</display:column>
	<display:column title="录入单位" property="inputUnitName" style="text-align:center;"/>
	<display:column title="录入时间" style="text-align:center;">
		<fmt:formatDate value="${caseBasic.inputTime }" pattern="yyyy-MM-dd HH:mm"/>
	</display:column>
	<display:column title="案件状态" style="text-align:center;">
		<dict:getDictionary var="state" groupCode="${caseBasic.procKey}State" dicCode="${caseBasic.caseState }"/>${state.dtName }
	</display:column>	
	<display:column title="案件预警" style="text-align:left;">
		<h1 id="warnInfo${caseBasic.caseId}" title="" style="cursor: pointer;"><span style="display:none;"></span></h1>
	</display:column>
<%-- 	<display:column title="操作">
		<a href="${path }/casehandle/caseTodo/suggestYisong?caseId=${caseBasic.caseId}">建议移送</a>
	</display:column> --%>
</display:table>
</c:when>
<c:otherwise>
<%@ include file="/views/office/officeDoc/suspectedCrimeWuReportCreate.jsp" %>
</c:otherwise>
</c:choose>
<br/>
<div id="popupDiv" style="display: none;">
	 <div class="treeDiv">
	<ul id="usertree" class="ztree"></ul>
	</div>
</div>

<script type="text/javascript">
	<c:forEach items="${casePagination.list }" var="caseBasic">
	    <c:if test="${empty caseBasic.warnMap}">
			$('#warnInfo${caseBasic.caseId}').addClass('greenLight');
           	var newTipContext = '历史案件预警：正常!';
            $('#warnInfo${caseBasic.caseId}').poshytip({
                content:newTipContext,
                className: 'tip-yellowsimple',
                showTimeout: 1,
                slide: false,
                fade: false,
                alignTo: 'target',
                alignX: 'left',
                alignY: 'center',
                offsetX: 2
            });
		</c:if>
		
        <c:if test="${not empty caseBasic.warnMap}">
        	$('#warnInfo${caseBasic.caseId}').addClass('redLight').removeClass('greenLight').removeClass('yellowLight');;
        	var oldTipContext=$('#warnInfo${caseBasic.caseId}').data('tip');
        	var tipContent = "";
        	<c:forEach items="${caseBasic.warnMap['warnCaseParty']}" var="caseParty"  varStatus="casePartyStatus">
        		var cpText = '历史案件预警：案件当事人';
        			cpText+=[ '<a href="javascript:showCasePartyHistoryCase(',
                              "'${path}','${caseBasic.caseId}','${caseParty.idsNo}','${caseParty.name}')",
                             '">${caseParty.name}</a>'].join("");
                <c:if test="${!casePartyStatus.last}">
                	cpText+='，';
                </c:if>
                tipContent += cpText+'有历史案件!<br/>';
        	</c:forEach>
        	
        	<c:forEach items="${caseBasic.warnMap['warnCaseCompany']}" var="caseCompany"  varStatus="caseCompanyStatus">
	            var ccText = '历史案件预警：涉案企业';
	            	ccText+=[
	                            '<a href="javascript:showCaseCompanyHistoryCase(',
	                            "'${path}','${caseBasic.caseId}','${caseCompany.registractionNum}','${caseCompany.name}')",
	                            '">${caseCompany.name}</a>'].join("");
	            <c:if test="${!caseCompanyStatus.last}">
	            	ccText+='，';
	            </c:if>
	            tipContent += ccText+'有历史案件!<br/>';
    		</c:forEach>
    		
    		/* <c:if test="${not empty caseBasic.warnMap['beyondAmount']}">
    			var amText = '涉案金额(元)：<fmt:formatNumber value="${caseBasic.amountInvolved }" pattern="#,##0.00#"/><br/>'
    						+'预警金额(元)：<fmt:formatNumber value="${caseBasic.warnMap[\'orgAmount\'] }" pattern="#,##0.00#"/><br/>'
    						+'超出金额(元)：<fmt:formatNumber value="${caseBasic.warnMap[\'beyondAmount\'] }" pattern="#,##0.00#"/>';
    			tipContent += amText+"<br/>";
    		</c:if> */
              if(oldTipContext){
              	tipContent = oldTipContext+"<br/>"+tipContent;
                  $('#warnInfo${caseBasic.caseId}').poshytip("destroy");
              }
              $('#warnInfo${caseBasic.caseId}').data('tip',tipContent);
              $('#warnInfo${caseBasic.caseId}').poshytip({
                  content:tipContent,
                  className: 'tip-yellowsimple',
                  showTimeout: 1,
                  slide: false,
                  fade: false,
                  alignTo: 'target',
                  alignX: 'left',
                  alignY: 'center',
                  offsetX: 2
              });
         </c:if>
    </c:forEach>
</script>
</div>
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:18%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
    <span style="margin-left: 10px;">查询范围：
       <input type="radio" name="queryScopeQ" value="1" id="queryScope1"  onclick="setQueryScope(1);" <c:if test="${empty param.queryScope || param.queryScope == 1}">checked="checked"</c:if>/>
       <label for="queryScope1">包含下级</label>
       <input type="radio" name="queryScopeQ" value="2" id="queryScope2" onclick="setQueryScope(2);"  <c:if test="${param.queryScope == 2}">checked="checked"</c:if>/>
       <label for="queryScope2">本级</label>
    </span>	
	<div class="regionTreedivv"><ul id="dropdownMenu" class="ztree"></ul></div>
</div>
<div id="districtZtreeDiv" style="display:none; position:absolute; height:200px;width:18%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
    <span style="margin-left: 10px;">查询范围：
       <input type="radio" name="districtQueryScope" value="1" id="districtQueryScope1"  onclick="setDistrictQueryScope(1);" <c:if test="${empty param.districtQueryScope || param.districtQueryScope == 1}">checked="checked"</c:if>/>
       <label for="districtQueryScope1">包含下级</label>
       <input type="radio" name="districtQueryScope" value="2" id="districtQueryScope2" onclick="setDistrictQueryScope(2);"  <c:if test="${param.districtQueryScope == 2}">checked="checked"</c:if>/>
       <label for="districtQueryScope2">本级</label>
    </span>	
	<div class="regionTreedivv" style="margin-right: 0px;margin-bottom: 0px;"><ul id="districtZtree" class="ztree"></ul></div>
</div>
</body>
</html>