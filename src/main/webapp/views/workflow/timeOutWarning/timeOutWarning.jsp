<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>

<!-- ligerUI -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>

<div class="actionWarnDivs" title="各任务预警设置">
	<ul> 
		<li><a href="#timeWarn">预警时间设置</a></li>
		<li><a href="#reminderWarn">预警提醒人设置</a></li>
	</ul>
		<div id="timeWarn">
			<div style="margin: 5px; font-family: 'Arial', 'sans-serif', 'Verdana'; font-size: 12px;height: 300px; overflow: auto;">
			<div>
				<div>
					<c:forEach items="${upProcDiagrams }" var="item">
					<fieldset class="fieldset" id="casePartyFieldset" style="width: 90%;margin-left: 10px;">
						<legend class="legend">
							${item.elementName }→${procDiagram.elementName }
						</legend>
						<div id="${item.elementId }_time" style="float: left;padding-right: 10px;"></div>
					</fieldset>
					</c:forEach>
				</div>
			</div>
			</div>
			<div align="right" style="margin: 5px;">
				<div style="float: right"><button onclick="saveTaskTimeOutAssign();">保存</button></div>
			</div>
		</div>
	<div id="reminderWarn">
		<div style="margin: 5px; font-family: 'Arial', 'sans-serif', 'Verdana'; font-size: 12px;height: 300px; overflow: auto;">
			<div class="treediv" style="width: 360px;height: 280px;overflow: scroll;margin: 0px ;padding: 0px;border: 2px #E4F0F8 solid;">
				<ul id="treeC${procDiagram.elementId }" class="tree" style="width:330px; overflow:auto;"></ul>
			</div>
		</div>
		<div align="right" style="margin: 5px;">
			<div style="float: right"><button onclick="saveTaskAssign();">保存</button></div>
		</div>
	</div>
</div>

<script type="text/javascript">


//该页面作为prototip的ajax请求结果，注意函数需要以 var someFunction = function(){....}的形式声明。还要住以jQuery命名
saveTaskTimeOutAssign=function(){
	var submitFlag=false;
	var procDefId = '${procDiagram.procDefId}';
	var taskDefId = '${procDiagram.elementId}';
	var upProcDiagrams=new Array();
	<c:forEach items="${upProcDiagrams }" var="item">
	upProcDiagrams.push("${item.elementId}");
	</c:forEach>
	jQuery.each(upProcDiagrams,function(i,n){
		var fromElementId=n;
		var dueTime = jQuery('#'+fromElementId+'_time').datetimepicker('getTimeValue');//得到设置后的超时时间
			jQuery.ajax({
				async:false,
				type:"POST",
				url:"${path}/taskAssignSetting/timeOutWarning",
				data:{
					procDefId : procDefId,
					taskDefId : taskDefId,
					fromTaskDefId:fromElementId,
					districtCode: "${districtCode}",
					dueTime : dueTime  //得到超时的时间 
				},
				success:function(){
					submitFlag=true;
				},
				error:function(){
					submitFlag=false;
				}
			});
		if(submitFlag==false) return;
	});
	
	var dialog;
	if(submitFlag==true){
		dialog = $.ligerDialog.waitting('配置完成!');
	}else{
		dialog = $.ligerDialog.waitting('配置失败，请重新设置');
	}
	setTimeout(function(){
		dialog.close();
	},2000);
	
}
saveTaskAssign = function() {
	var procDefId = '${procDiagram.procDefId}';
	var taskDefId = '${procDiagram.elementId}';
	var nodes = zTree${procDiagram.elementId}.getCheckedNodes(true);
	var userIdString = "" ;
	jQuery.each(nodes, function(i,treeNode){
		if(nodes.length == i+1) {
			if(!treeNode.isParent) {
				 userIdString += treeNode.id ;
			 }
		}else {
			if(!treeNode.isParent) {
				userIdString += treeNode.id + "&";
			 }
		 }
	});
	jQuery.post("${path}/taskAssignSetting/timeOutWarning_reminder", {
		procDefId : procDefId,
		taskDefId : taskDefId,
		userIdString : userIdString,
		districtCode: "${districtCode}"
	}, function() {
		var dialog = $.ligerDialog.waitting('配置完成!');
		setTimeout(function(){
			dialog.close();
		},2000);
	});
};
<c:forEach items="${upProcDiagrams }" var="item">
	jQuery('#${item.elementId}_time').timepicker({
		timeFormat : 'MM:dd:hh'
	});
</c:forEach>
var timeoutWarns='${timeoutWarns}';
<c:if test="${not empty timeoutWarns}">
	jQuery.each(${timeoutWarns},function(i,n){
		jQuery('#'+n.fromTaskDefId+'_time').datetimepicker('setDate',
		n.dueDate);
	});
</c:if>


	var zTree${procDiagram.elementId}= jQuery('#treeC${procDiagram.elementId}').zTree({
	isSimpleData : true,
    treeNodeKey : "id",
    treeNodeParentKey : "pId",
    checkable : true,
    checkType : { "Y": "ps", "N": "ps" },
    async: true,
	asyncUrl:"${path}/taskAssignSetting/loadReminder?TTT="+(new Date()).getTime(),  //获取节点数据的URL地址
	asyncParamOther : {"taskDefId":"${taskDefId}","procDefId":"${procDefId}","districtCode":"${districtCode}"},
	callback : {
		asyncSuccess:function(event, treeId, treeNode, msg) {
			if(msg=='null') {
				jQuery(".tree").html("请为<span style='color:#00bb00;font-weight: bolder;'>${districtName}检察院</span>添加用户！").css({ color:"red"});
			}
		}
	}
});
	jQuery(function(){
		//这个地方不能用ID，只能用其它属性，ID只会查找第一个对象，
		//而由于prototip的BUG第二个之后tip会保留上一个tip的内容，用id时会找到上一个tip中的对象
		jQuery('.actionWarnDivs').tabs();
	});
</script>