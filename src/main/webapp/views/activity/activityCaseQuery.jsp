<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
	<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>

<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>

<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
	<script src="${path}/resources/script/cleaner.js"></script>
	<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">
	var dqdjArt;
	
	$(function(){
		//日期
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
		
		zTree_activity=	$('#activityTree').zTree({
			isSimpleData: true,
			checkable:true,
			checkStyle : "radio",
			checkRadioType : "all",
			treeNodeKey: "id",
			treeNodeParentKey: "upId",
			async: true,
			asyncUrl:"${path}/activity/query/loadActivity/"+'${acti.id}'
		});
		
		
		$('#checkButton').click(function(){
			zTree_activity.checkAllNodes(true);
		});
		$('#unCheckButton').click(function(){
			zTree_activity.checkAllNodes(false);
		});

			
		$('html').bind("mousedown", 
				function(event){
					if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
						hideMenu();
					}
				});
		
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
	function selectAllNodes() {
		zTree.checkAllNodes(true);
	}
	
	function showActivity(caseId){
		$("#activityCaseId").val(caseId);
		user_dialog =art.dialog(
				{
					id:caseId,
					title:'专项活动案件调整归属',
					content:document.getElementById("popupDiv"),
					resize:false,
					lock:true,
					opacity: 0.1 // 透明度
				},
		false)
	}
	function saveChangeActivity(){
		var nodes = zTree_activity.getCheckedNodes();
  		var caseId=$("#activityCaseId").val();
		if(nodes == null || nodes.length == 0){
			art.dialog({
				 content:"您没有选择任何专项活动，请重新选择 ！"
				 },
				 false);
			return ;
		}
		
	    if(caseId==null || caseId==""){
	    	art.dialog({
				 content:"案件信息错误，请重新选择 ！"
				 },
				 false);
			return ;
	    }
	    var id =nodes[0].id;
	    $.post(
	    	"${path}/activity/query/saveChangeActivity", 
	    	{ activityId: id, caseId: caseId },
	    	function(data) {
	    		if(data){
	    	    	art.dialog({
	   				 content:"调整成功！"
	   				 },
	   				 true);
	    	    $("#activityCaseId").val("");
		    	user_dialog.close();
		    	window.location = "${path }/activity/query/queryCase/${acti.id}";
	    		}else{
	    	    	art.dialog({
		   				 content:"该案件不在此专项活动周期内，调整失败!"
		   				 },
		   				 true);
	    		}
	    	});
	}
	//打侵打假添加页面关闭
	top.dqdj=function(){
		dqdjArt.close();
	}
	//打开打侵打假的添加页面
	function dqdjCategoryAdd() {
		var strs = new Array();
		$("[name='check']:checked").each(
			function(key,value){
				strs.push(value.value);
			}		
		);
		if(strs.length > 0) {
			dqdjArt = art.dialog.open(
					"${path }/activity/dqdjCaseCategory/addUI?strs="+strs,
					{
						title:'转移打侵打假案件',
						height:200,
						width:450,
						resize:false,
						lock:true,
						opacity: 0.1, // 透明度
						closeFn:function(){location.href = "${path }/activity/query/queryCase/${acti.id}?page=${page}";}
					},
			false);
		}else {
			top.art.dialog.alert("请选择需要转移的案件!");
		}
	}
	//选中全部
	function checkAll(obj){
	    $("#errorDiv").html("");
	    $("[name='check']").attr("checked",obj.checked) ; 			
	}
</script>

</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">专项活动案件查询</legend>
<form action="${path }/activity/query/queryCase/${acti.id}" method="post">
	<input type="hidden" name="orgIds" id="orgIds" value="${param.orgIds}"/>
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">录入时间：</td>
			<td width="25%" align="left">
				<input type="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 36%"/>
				至
				<input type="text" name="endTime" id="endTime" value="${param.endTime }" style="width: 36%"/>
			</td>
				<td width="15%" align="right">所属机构：</td>
			<td width="25%" align="left" colspan="3"><input type="text" class="text" name="orgNames" id="orgNames"  onfocus="showMenu(); return false;" value="${param.orgNames}" readonly="readonly"/></td>
			<td width="36%" align="left" valign="middle">
				<input type="submit" value="查 询" class="btn_query" />
			</td>
		</tr>
	</table>
</form>
</fieldset>
<br/>

	<display:table name="caseList" uid="case" cellpadding="0"
		cellspacing="0" requestURI="${path }/activity/query/queryCase/${acti.id}">
		<display:caption style="text-align: center;vertical-align: middle;" class="tooltar_btn">
		
			<input type="button" value="转 移" class="btn_small" style="float: left;margin-bottom: 10px;"	onclick="dqdjCategoryAdd()" />
			<font  style="font-weight: bold;font-size: 15px" >所属专项活动：${acti.name}</font>
		</display:caption>
		<display:column title="<input type='checkbox' onclick='checkAll(this)'/>" style="width: 5%" class="myclass">
			<c:choose>
				<c:when test="${case.dqdjNum == 0 }">
					<input type="checkbox" name="check" value="${case.caseId}" />
				</c:when>
				<c:otherwise>
					<input type="checkbox" disabled="disabled" title="此案件已经转移" />
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${case_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + case_rowNum}
			</c:if>
		</display:column>
		<display:column title="案件编号" property="caseNo" style="text-align:left;"></display:column>
		<display:column title="案件名称" style="text-align:left;">
			${fn:substring(case.caseName,0,20)}${fn:length(case.caseName)>20?'...':''}
		</display:column>
		<display:column title="状态" style="text-align:left;">
			<c:if test="${case.procKey == 'chufaProc'}">
				<dict:getDictionary var="stateVar" groupCode="chufaProcState"
					dicCode="${case.caseState }" />
				${stateVar.dtName }
			</c:if>
			<c:if test="${case.procKey == 'crimeProc'}">
				<dict:getDictionary var="stateVar" groupCode="crimeProcState"
					dicCode="${case.caseState }" />
				${stateVar.dtName }
			</c:if>
		
		</display:column>
		<display:column title="录入时间" style="text-align:left;">
			<fmt:formatDate value="${case.inputTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="最后办理时间" style="text-align:left;">
			<fmt:formatDate value="${case.latestPocessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column title="操作">
			<a href="javascript:;" onclick="top.showCaseProcInfo('${case.caseId}');">案件详情</a>
			<a href="#" onclick="showActivity('${case.caseId}')">调整归属</a>
		</display:column>
	</display:table>
</div>	

	<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px;width:250px; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
	　　　<a href="javascript:void(0);" onclick="selectAllNodes()">全选</a>
		<a href="#" onclick="clearDiv()">清空</a>
	</div>
	<ul id="dropdownMenu" class="tree"></ul>
</div>


<div id="popupDiv" style="display: none">
		<div class="titleDiv" align="center">
		<font size="3" style="font-weight: bold;font-family: 宋体;">专项活动案件调整归属</font>
				<input id="activityCaseId" type="hidden"  />
		</div> 
 		<div class="treeDiv" style="border:1px solid #ABC1D1;">
				<ul id="activityTree" class="tree"></ul>
		</div> 
				
		<div class="buttonDiv">
				<input type="button" class="btn_small" value="保&nbsp;存" onclick="saveChangeActivity();"/>
		</div>
	</div>	

</body>
</html>