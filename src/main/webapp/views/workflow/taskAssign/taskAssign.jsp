<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>

<!-- ligerUI -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>

<div
	style="margin: 5px; font-family: 'Arial', 'sans-serif', 'Verdana'; font-size: 12px;height: 300px; overflow: auto;">
	<table class="blues">
		<thead>
			<tr>
				<th>组织机构</th>
				<th>任务分配岗位</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${organiseList }" var="org">
				<tr style="background-color: #C6D3EF;">
					<td align="left" colspan="2" style="text-align: left;">${org.orgName
						}</td>
				</tr>
				<c:forEach items="${org.orgTaskAssignList }" var="orgTaskAssign">
					<tr class="forTaskAssign">
						<td align="left" style="text-align: left;">&nbsp;&nbsp;&nbsp;└
							${orgTaskAssign.dept.orgName } <input type="hidden" name="deptId"
							value="${orgTaskAssign.dept.orgCode }" /> <input type="hidden"
							name="orgCode" value="${org.orgCode }" />
						</td>
						<td><select name="assignTarget">
								<option value=""></option>
								<c:forEach items="${orgTaskAssign.postList }" var="_post">
									<option value="${_post.postId }"
										${orgTaskAssign.taskAssign.assignGroup==_post.postId?
										'selected="selected"':'' }>${_post.postName}</option>
								</c:forEach>
						</select></td>
					</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div align="right" style="margin: 5px;">
		<div style="float: left;color: red;"><a href="javascript:selectAllFirst()">将所有办理岗位为第一个（慎用）</a></div>
		<div style="float: right"><button onclick="saveTaskAssign();">保存</button></div>
	</div>
	<script type="text/javascript">
        jQuery("input:button,input:reset,input:submit,button").not('.noJbu').button();
		jQuery('#${procDiagram.elementId}_time').timepicker({
			timeFormat : 'MM:dd:hh'
		});
		jQuery('#${procDiagram.elementId}_time').datetimepicker('setDate',
				'${dueTime}');
		//该页面作为prototip的ajax请求结果，注意函数需要以 var someFunction = function(){....}的形式声明。还要住以jQuery命名
		selectAllFirst=function(){
			jQuery('select[name="assignTarget"]').each(function(i, s) {
				jQuery(s).find("option:first").next().attr("selected","selected");
			});
		};
		saveTaskAssign = function() {
			var procDefId = '${procDiagram.procDefId}';
			var taskDefId = '${procDiagram.elementId}';
			var taskAssignListStr = "";
			jQuery('.forTaskAssign').each(
					function(i, tr) {
						var orgCode = jQuery(tr).find('input[name="orgCode"]')
								.val();
						var deptId = jQuery(tr).find('input[name="deptId"]')
								.val();
						var assignTarget = jQuery(tr).find(
								':input[name="assignTarget"]').val();
						//如果为空，把原有的设置删除掉
						if (assignTarget == '') {
							assignTarget = '-1';
						}
						
						taskAssignListStr += orgCode + "-" + deptId + '='
								+ assignTarget + ',';
					});
			if (taskAssignListStr == '') {
				$.ligerDialog.warn('请选择该任务办理对应的机构岗位！');
				return;
			}
			var dueTime = jQuery('#${procDiagram.elementId}_time')
					.datetimepicker('getTimeValue');//得到设置后的超时时间
			jQuery.post("${path}/taskAssignSetting/taskAssign", {
				procDefId : procDefId,
				taskDefId : taskDefId,
				taskAssignListStr : taskAssignListStr
			}, function() {
				var dialog = $.ligerDialog.waitting('配置完成!');
				setTimeout(function(){
					dialog.close();
				},2000);
			});
		};
	</script>