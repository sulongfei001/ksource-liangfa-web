<#setting number_format="#"><#-- 取消数字的自动逗号分隔格式-->
<#-- 文本框 -->
<#assign HTML_INPUT_TYPE_TEXT=1>
<#-- 文本框 -->
<#assign HTML_INPUT_TYPE_TEXTAREA=2>
<#-- 单选按钮 -->
<#assign HTML_INPUT_TYPE_RADIO=3>
<#-- 多选框 -->
<#assign HTML_INPUT_TYPE_CHECKBOX=4>
<#-- 下拉菜单 -->
<#assign HTML_INPUT_TYPE_SELECT=5>
<#-- 上传组件file-->
<#assign HTML_INPUT_TYPE_FILE=6>

<#-- 数据类型-字符 -->
<#assign INPUT_DATA_TYPE_STRING=1>
<#-- 数据类型-整数 -->
<#assign INPUT_DATA_TYPE_INT=2>
<#-- 数据类型-数值 -->
<#assign INPUT_DATA_TYPE_NUMBER=3>
<#-- 数据类型-日期(时间) -->
<#assign INPUT_DATA_TYPE_DATE=4>

<#-- 案件录入时机（处罚后录入） -->
<#assign CASE_INPUT_TIMING_CHUFA=1>
<#-- 案件录入时机（立案后录入） -->
<#assign CASE_INPUT_TIMING_LIAN=2>
<#-- 案件录入时机（移送案件录入） -->
<#assign CASE_INPUT_TIMING_YISONG=3>



<#-- 提交动作类型-提请逮捕；-------提交表单前置操作方法名；嫌疑人操作页面容器ID；加载嫌疑人操作页面地址 -->
<#assign TASK_ACTION_TIQINGDAIBU=1>
<#assign TIQINGDAIBU_BEFORE_FUNC="tiqingdaibuxianyirenBefore">
<#assign TIQINGDAIBU_XIANYIREN_C_ID="tiqingdaibuxianyirenC">
<#assign TIQINGDAIBU_OPT_URL="/workflow/tiqingdaibu">
<#-- 提交动作类型-审查逮捕 -->
<#assign TASK_ACTION_SHENCHADAIBU=6>
<#assign SHENCHADAIBU_BEFORE_FUNC="shenchadaibuxianyirenBefore">
<#assign SHENCHADAIBU_XIANYIREN_C_ID="shenchadaibuxianyirenC">
<#assign SHENCHADAIBU_OPT_URL="/workflow/shenchadaibu">
<#-- 提交动作类型-提请起诉 -->
<#assign TASK_ACTION_TIQINGQISU=2>
<#assign TIQINGQISU_BEFORE_FUNC="tiqingqisuxianyirenBefore">
<#assign TIQINGQISU_XIANYIREN_C_ID="tiqingqisuxianyirenC">
<#assign TIQINGQISU_OPT_URL="/workflow/tiqingqisu">
<#-- 提交动作类型-提起公诉 -->
<#assign TASK_ACTION_TIQIGONGSU=3>
<#assign TIQIGONGSU_BEFORE_FUNC="tiqigongsuxianyirenBefore">
<#assign TIQIGONGSU_XIANYIREN_C_ID="tiqigongsuxianyirenC">
<#assign TIQIGONGSU_OPT_URL="/workflow/tiqigongsu">
<#-- 提交动作类型-不起诉 -->
<#assign TASK_ACTION_BUQISU=4>
<#assign BUQISU_BEFORE_FUNC="buqisuxianyirenBefore">
<#assign BUQISU_XIANYIREN_C_ID="buqisuxianyirenC">
<#assign BUQISU_OPT_URL="/workflow/buqisu">
<#-- 提交动作类型-法院判决 -->
<#assign TASK_ACTION_FAYUANPANJUE=5>
<#assign FAYUANPANJUE_BEFORE_FUNC="fayuanpanjuexianyirenBefore">
<#assign FAYUANPANJUE_XIANYIREN_C_ID="fayuanpanjuexianyirenC">
<#assign FAYUANPANJUE_OPT_URL="/workflow/fayuanpanjue">
<#-- 提交动作类型-案件审查（已处罚录入） -->
<#assign TASK_ACTION_SHENCHA_CHUFA=7>
<#-- 提交动作类型-案件审查（立案录入） -->
<#assign TASK_ACTION_SHENCHA_LIAN=8>
<#-- 提交动作类型-案件审查（移送案件录入） -->
<#assign TASK_ACTION_SHENCHA_YISONG=9>
<#-- 提交动作类型-普通 -->
<#assign TASK_ACTION_NORMAL=0>
<#-- 提交动作类型-移送公安 -->
<#assign TASK_ACTION_YISONGGONGAN=10>
<#-- 提交动作类型-公安立案-->
<#assign TASK_ACTION_LIAN=11>
<#assign YISONGGONGAN_BEFORE_FUNC="yisonggonganBefore">
<#assign YISONGGONGAN_C_ID="yisonggonganC">
<#assign YISONGGONGAN_OPT_URL="/workflow/yisonggongan">


<#-- 表单html id生成规则 -->
<#function generateFormHtmlId formDefId>
	<#return "form_"+formDefId>
</#function>

<#-- 表单字段html name生成规则:！！！后台取字段值时名字一致 -->
<#function generateFieldHtmlName fieldId>
	<#return "field__"+fieldId>
</#function>

<#-- 处理步骤选择-->
<#if taskActionList?size &gt; 1 ><#-- 当只有一个提交动作时不显示提交选择按钮-->
	<script type="text/javascript">
		$(function(){
			$('#chooseButSet').buttonset();
			$('#chooseButSet input').click(function(){
				var $radio = $(this);
				var actionId = $radio.attr('actionId');
				$('div.dtForm').hide();//隐藏所有form
				var $div = $($radio.attr('toDiv'));
				
				//修改了按钮样式  xieyujun
				$div.find('form').find('.btn_small').attr('actionId',actionId);
				$div.show().find('form').find('input[name=\"actionId\"]').val(actionId);//显示form并设置当前action id值
				if($radio.attr("needAssignTarget")=="true"){
					$div.find('form').find('input[name="needAssignTarget"]').val("true");
					//设置受理机关ztree
					if(window.zTree!=undefined){
						var zTreeNodes=zTree.getNodes();
						removeNodes(zTreeNodes);
						zTree.refresh();
					}
					zTree=	$('#dropdownMenu').zTree(ztreeSettring,window["treeData_"+actionId]);
					zTree.refresh();
					$('#assignTargetC').show();
				}else{
					$div.find('form').find('input[name="needAssignTarget"]').val("");
					$('#DropdownMenuBackground,#assignTargetC').hide();
				}
				clearOrg();
				//显示嫌疑人操作页面
				$('.xianyiren_c').hide();
				if($radio.attr('xianyiren_c')){
					$($radio.attr('xianyiren_c')).show();
				}
				//动态控制“受案单位”名称
			<#--	var actionType = $radio.attr('actionType');
				if(actionType == ${TASK_ACTION_LIAN}){
					$('#unitLabel').text("立案单位");
				}else{
					$('#unitLabel').text("下一步办理单位");
				}-->
			});
			//显示操作页面
			$('#chooseButSet input:checked').click();
		});
        function removeNodes(zTreeNodes){
            if(zTreeNodes){
                zTreeNodes=zTreeNodes.concat();
                $.each(zTreeNodes,function(i,treeNode){
                    var deptNodes=treeNode.nodes;
                    if(deptNodes){
                        removeNodes(deptNodes);
                    }
                    zTree.removeNode(treeNode);
                });
            }
        }
	</script>
	<div id="chooseButSet" align="left">
	<!--radio自定义属性说明::  actionId：动作id，toForm：#表单html id，actionType：提交动作类型-->
	<#list taskActionList as taskAction> 
		<input type="radio"  actionId="${taskAction.actionId}" toDiv="#div_${generateFormHtmlId(taskAction.formDefId)}" toForm="#${generateFormHtmlId(taskAction.formDefId)}" actionType="${taskAction.actionType}" 
		<#-- 案件审查特殊处理：根据案件录入时机，默认选中对应的审核操作-->
		<#if taskAction.actionType==TASK_ACTION_SHENCHA_CHUFA>
			style="display:none;"
			<#if caseInputTiming?? && caseInputTiming==CASE_INPUT_TIMING_CHUFA>
			checked="checked"
			</#if>
		<#elseif taskAction.actionType==TASK_ACTION_SHENCHA_LIAN >
			style="display:none;"
			<#if caseInputTiming?? && caseInputTiming==CASE_INPUT_TIMING_LIAN >
			checked="checked"
			</#if>
		<#elseif taskAction.actionType==TASK_ACTION_SHENCHA_YISONG>
			style="display:none;"
			<#if caseInputTiming?? && caseInputTiming==CASE_INPUT_TIMING_YISONG >
			checked="checked"
			</#if>
		</#if>
		
		<#if taskAction.actionType==TASK_ACTION_TIQINGDAIBU>
			xianyiren_c="#${TIQINGDAIBU_XIANYIREN_C_ID}"
		<#elseif taskAction.actionType==TASK_ACTION_SHENCHADAIBU>
			xianyiren_c="#${SHENCHADAIBU_XIANYIREN_C_ID}"
		<#elseif taskAction.actionType==TASK_ACTION_TIQINGQISU>
			xianyiren_c="#${TIQINGQISU_XIANYIREN_C_ID}"
		<#elseif taskAction.actionType==TASK_ACTION_TIQIGONGSU>
			xianyiren_c="#${TIQIGONGSU_XIANYIREN_C_ID}"
		<#elseif taskAction.actionType==TASK_ACTION_BUQISU>
			xianyiren_c="#${BUQISU_XIANYIREN_C_ID}"
		<#elseif taskAction.actionType==TASK_ACTION_FAYUANPANJUE>
			xianyiren_c="#${FAYUANPANJUE_XIANYIREN_C_ID}"
		<#elseif taskAction.actionType==TASK_ACTION_YISONGGONGAN>
			xianyiren_c="#${YISONGGONGAN_C_ID}"
		</#if>
		needAssignTarget="<#if taskAction.needAssignTarget >true</#if>"		
		name="chooseBut" id="chooseBut${taskAction_index}" <#if taskAction_index ==0>checked="checked"</#if> />
		
		<#-- 案件审查特殊处理：根据案件录入时机，默认选中对应的审核操作-->
		<#if taskAction.actionType!=TASK_ACTION_SHENCHA_CHUFA && taskAction.actionType!=TASK_ACTION_SHENCHA_LIAN && taskAction.actionType!=TASK_ACTION_SHENCHA_YISONG>
			<label for="chooseBut${taskAction_index}">${taskAction.actionName}</label>
		</#if>
	</#list>
	</div>
</#if>

<#-- 受理机关zTree操作 -->
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; min-width:250px; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;text-align: left;">
	<div class="tabRight">
		<a href="javascript:void();" onclick="javascript:clearOrg();">清空</a>
	</div>
	<ul id="dropdownMenu" class="tree"></ul>
</div>
<#list taskActionList as taskAction>
	<#if taskAction.actionType==TASK_ACTION_YISONGGONGAN>
		<p align="left" id="assignTargetC">
			<label id="unitLabel">受案单位</label>
			<input type="text" id="assignTargetId" readonly="readonly" class="genInputText"  onfocus="showMenu(); return false;"/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>
		</p>		
	</#if>
</#list>
<script type="text/javascript">
$(function(){
	if($('#chooseButSet input').size()==0 && $("form.dtForm:visible input[name='needAssignTarget']").val()==""){
		$('#DropdownMenuBackground,#assignTargetC').hide();
	}
});
function showMenu() {
			var cityObj = $("#assignTargetId");
			var cityOffset = $("#assignTargetId").offset();
			var parentOffset = $("#assignTargetId").offsetParent();
			var top=cityOffset.top,left=cityOffset.left;
			if(parentOffset){
				top-=parentOffset.offset().top;
				left-=parentOffset.offset().left;
			}
			$("#DropdownMenuBackground").css({left:left + "px", top:top + cityObj.outerHeight() + "px"}).slideDown("fast");
}
function hideMenu() {
	$("#DropdownMenuBackground").fadeOut("fast");
}
 function clearOrg(){
 	$("#assignTargetId,input[name='assignTarget']").val('');
 }
 $(function(){
 	$("body").bind("mousedown", function(event){
		if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
			hideMenu();
		}
	});
 });
var ztreeSettring={
	isSimpleData : true,
	treeNodeKey : "orgCode",
	treeNodeParentKey : "upOrgCode",
	nameCol:"orgName",
	callback: {
		beforeClick:function(treeId, treeNode){
			if(treeNode.isDept==0){return false;}
			return true;
		},
		click:function(event, treeId, treeNode) {
			var upOrg = zTree.getNodeByParam("orgCode", treeNode.upOrgCode);
			$("#assignTargetId").val(upOrg.orgName+"("+treeNode.orgName+")");
			$("form.dtForm:visible input[name='assignTarget']").val(treeNode.orgCode);
			hideMenu();
		}
	}
};
<#list taskActionList as taskAction>
	<#if taskAction.organiseList??>
		var treeData_${taskAction.actionId} = [
		<#list taskAction.organiseList as organise>
			{"orgCode":"${organise.orgCode}","upOrgCode":"${organise.upOrgCode}","orgName":"${organise.orgName}","isDept":${organise.isDept}}
			<#if organise_has_next>,</#if>
		</#list>
		];
		//数组排序(目的：同一级树部门节点排在上面，非部门在下)
		treeData_${taskAction.actionId}.sort(function(a,b){
			return b.isDept-a.isDept;
		});
	</#if>
</#list>
</script>

<#-- 处理表单和表单脚本-->
<#assign hasGenFormDef=[]><#-- 已生成的formdefId-->
<#list taskActionList as taskAction>
	<#if !(hasGenFormDef?seq_contains(taskAction.formDefId))><#--不重复生成form-->
		<#--表单内容-->
	<div id="div_${generateFormHtmlId(taskAction.formDefId)}" class="dtForm" <#if (hasGenFormDef?size &gt; 1)>style="display:none;"</#if>>
	<form id="${generateFormHtmlId(taskAction.formDefId)}" class="dtForm"  action="${appPath}/workflow/task/taskDeal" method="post" enctype="multipart/form-data"><#-- 默认显示第一个表单-->
	<#list taskAction.formFieldList as formField>
		<p align="left">
			<label>${formField.label}</label>
			<#if formField.inputType==HTML_INPUT_TYPE_TEXT>
				<#if formField.dataType==INPUT_DATA_TYPE_DATE>
					<input type="text" name="${generateFieldHtmlName(formField.fieldId)}" class="genInputText" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
				<#else>
					<input type="text" name="${generateFieldHtmlName(formField.fieldId)}" class="genInputText"
					<#if formField.max??>placeholder="请输入不要超过${formField.max}个汉字或英文字母"</#if>
					/>
				</#if>
			<#elseif formField.inputType==HTML_INPUT_TYPE_TEXTAREA>
				<textarea rows="5" cols="30" name="${generateFieldHtmlName(formField.fieldId)}" class="genInput"></textarea>
			<#elseif formField.inputType==HTML_INPUT_TYPE_RADIO>
				<#list formField.fieldItemList as fieldItem>
					<input type="radio" name="${generateFieldHtmlName(formField.fieldId)}" value="${fieldItem.value}" />${fieldItem.label}&nbsp;&nbsp
				</#list>
			<#elseif formField.inputType==HTML_INPUT_TYPE_CHECKBOX>
				<#list formField.fieldItemList as fieldItem>
					<input type="checkbox" name="${generateFieldHtmlName(formField.fieldId)}" value="${fieldItem.value}" />${fieldItem.label}&nbsp;&nbsp
				</#list>
			<#elseif formField.inputType==HTML_INPUT_TYPE_SELECT>
				<select class="genSelect" name="${generateFieldHtmlName(formField.fieldId)}">
				<#list formField.fieldItemList as fieldItem>
					<option value="${fieldItem.value}">${fieldItem.label}</option>
				</#list>
				</select>
			<#elseif formField.inputType==HTML_INPUT_TYPE_FILE>
				<input type="file" name="${generateFieldHtmlName(formField.fieldId)}" class="genInputText" size="33" width="200px;" />
				<#-- 通知立案表单  文书生成-->
				<#if taskAction.actionId ==1447  >
					<a href="javascript:;" style="margin-left:10px;" onclick="top.openOfficeDoc('tzla','${task.id}','${inputerId}','')" >文书生成 </a>
				</#if>
				<#if taskAction.actionId ==1258  >
					<a href="javascript:;" style="margin-left:10px;" onclick="top.openOfficeDoc('yqsmblaly','${task.id}','${inputerId}')" >文书生成 </a>
				</#if>
			</#if>
			<#if formField.required==1>
				  &nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>
			</#if>
		</p>
	</#list>
	<#-- 初始化受理机关ztree -->
	<#if taskAction.organiseList??>
	<script type="text/javascript">
		if(window.zTree==undefined){
			zTree=	$('#dropdownMenu').zTree(ztreeSettring,[
			<#list taskAction.organiseList as organise>
				{"orgCode":"${organise.orgCode}","upOrgCode":"${organise.upOrgCode}","orgName":"${organise.orgName}","isDept":${organise.isDept}}
				<#if organise_has_next>,</#if>
			</#list>
			]);
		}
	</script>
	</#if>			
	<input type="hidden" name="needAssignTarget" value="<#if taskAction.needAssignTarget >true</#if>" />
	<input type="hidden" name="assignTarget" />
	<input type="hidden" name="taskId" value="${task.id}" />
	<input type="hidden" name="actionId" value="${taskAction.actionId}" />
	<input type="hidden" name="optType" value="${optType}" />
	<input type="hidden" name="inputerId" value="${inputerId}" />
	</form>
	<div id="${taskAction.actionId}" align="left" >
	</div>
	<div align="left" style="clear:both;margin-top:10px;">
	<!-- 删除了 class="submitFormBut noJbu" 样 式 -->
	<input type="button" class="btn_small" value="提 交"  actionId="${taskAction.actionId}" />
	</div>
	</div>
	<#--表单脚本-->
	<script type="text/javascript">
		//表单校验
		<#assign checkRuleVar="checkRule_"+taskAction.formDefId>
		<#assign checkMsgVar="checkMsg_"+taskAction.formDefId>
		var ${checkRuleVar}={};
		var ${checkMsgVar}={};
	<#list taskAction.formFieldList as formField>
		<#assign fieldRuleVar="fieldRule_"+formField.fieldId>
		<#assign fieldMsgVar="fieldMsg_"+formField.fieldId>
		var ${fieldRuleVar}=${checkRuleVar}['${generateFieldHtmlName(formField.fieldId)}']={};
		var ${fieldMsgVar}=${checkMsgVar}['${generateFieldHtmlName(formField.fieldId)}']={};
		<#if formField.required==1><#--非空校验 -->
			${fieldRuleVar}.required=true;
			${fieldMsgVar}.required='${formField.label}不能空！';
		</#if>
		<#if formField.inputType==HTML_INPUT_TYPE_TEXT && formField.dataType==INPUT_DATA_TYPE_INT><#--整数 -->
			${fieldRuleVar}.digits=true;
			${fieldMsgVar}.digits='${formField.label}只能是整数！';
		</#if>
		<#if formField.inputType==HTML_INPUT_TYPE_TEXT && formField.dataType==INPUT_DATA_TYPE_NUMBER><#--数字 -->
			${fieldRuleVar}.appNumber=true;
			${fieldMsgVar}.appNumber='${formField.label}只能是数字！';
		</#if>
		<#if formField.inputType==HTML_INPUT_TYPE_TEXT && 
				(formField.dataType==INPUT_DATA_TYPE_INT || formField.dataType==INPUT_DATA_TYPE_NUMBER) > <#--最大值最小值校验 -->
			<#if formField.dataType==INPUT_DATA_TYPE_INT>
				<#if formField.min?? && formField.min &gt; 0>
					${fieldRuleVar}.min=${formField.min?string("0")};
					${fieldMsgVar}.min='${formField.label}不能小于${formField.min?string("0")}';
				</#if>
				<#if formField.max?? && formField.max &gt; 0>
					${fieldRuleVar}.max=${formField.max?string("0")};
					${fieldMsgVar}.max='${formField.label}不能大于${formField.max?string("0")}';
				</#if>
			<#elseif formField.dataType==INPUT_DATA_TYPE_NUMBER>
				<#if formField.min?? && formField.min &gt; 0.00>
					${fieldRuleVar}.min=${formField.min?string("0.##")};
					${fieldMsgVar}.min='${formField.label}不能小于${formField.min?string("0.##")}';
				</#if>
				<#if formField.max?? && formField.max &gt; 0.00>
					${fieldRuleVar}.max=${formField.max?string("0.##")};
					${fieldMsgVar}.max='${formField.label}不能大于${formField.max?string("0.##")}';
				</#if>
			</#if>
		</#if>
		<#--TODO:字符串长度校验-->
		<#if formField.max?? && formField.max &gt; 0.00>
			${fieldRuleVar}.max=${formField.max?string("0.##")};
			${fieldMsgVar}.max='${formField.label}不能大于${formField.max?string("0.##")}个汉字或英文字母';
		</#if>
		<#if formField.min?? && formField.min &gt; 0.00>
			${fieldRuleVar}.max=${formField.max?string("0.##")};
			${fieldMsgVar}.max='${formField.label}不能小于${formField.min?string("0.##")}个汉字或英文字母';
		</#if>
	</#list>
		var validator_${taskAction.formDefId} = jqueryUtil.formValidate({
			form:'${generateFormHtmlId(taskAction.formDefId)}',
			rules:${checkRuleVar},
			messages:${checkMsgVar},
			submitHandler:function(form){
				var flag=true;//默认action提交前无前置方法
			<#--var needAssignTarget = $(form).find("input[name='needAssignTarget']").val();
				if(needAssignTarget=="true" && $(form).find("input[name='assignTarget']").val()==""){
					art.dialog.alert("请选择受理机关！");
					return false;
				}-->
			<#list taskActionList as taskAction>
				<#if taskAction.actionType==TASK_ACTION_YISONGGONGAN>
			var needAssignTarget = $(form).find("input[name='needAssignTarget']").val();
							if(needAssignTarget=="true" && $(form).find("input[name='assignTarget']").val()==""){
								art.dialog.alert("请选择受理单位！");
								return false;
							}		
				</#if>
			</#list>				
				try{
					//执行当前所选的提交动作的前置方法
				<#list taskActionList as taskAction>
					if(curActionId=='${taskAction.actionId}'){
						
						<#if taskAction.actionType==TASK_ACTION_TIQINGDAIBU>
						if(!${TIQINGDAIBU_BEFORE_FUNC}){flag=false;}else{flag = ${TIQINGDAIBU_BEFORE_FUNC}();}
						<#elseif taskAction.actionType==TASK_ACTION_SHENCHADAIBU>
						if(!${SHENCHADAIBU_BEFORE_FUNC}){flag=false;}else{flag = ${SHENCHADAIBU_BEFORE_FUNC}();}
						<#elseif taskAction.actionType==TASK_ACTION_TIQINGQISU>
						if(!${TIQINGQISU_BEFORE_FUNC}){flag=false;}else{  flag = ${TIQINGQISU_BEFORE_FUNC}();}
						<#elseif taskAction.actionType==TASK_ACTION_TIQIGONGSU>
						if(!${TIQIGONGSU_BEFORE_FUNC}){flag=false;}else{flag = ${TIQIGONGSU_BEFORE_FUNC}();}
						<#elseif taskAction.actionType==TASK_ACTION_BUQISU>
						if(!${BUQISU_BEFORE_FUNC}){flag=false;}else{flag = ${BUQISU_BEFORE_FUNC}();}
						<#elseif taskAction.actionType==TASK_ACTION_FAYUANPANJUE>
						if(!${FAYUANPANJUE_BEFORE_FUNC}){flag=false;}else{flag = ${FAYUANPANJUE_BEFORE_FUNC}();}
						<#elseif taskAction.actionType==TASK_ACTION_YISONGGONGAN>
						if(!${YISONGGONGAN_BEFORE_FUNC}){flag=false;}else{flag = ${YISONGGONGAN_BEFORE_FUNC}();}
						</#if>
					}
				</#list>
				}catch (e) {
					flag=false;
				}
				if(flag && confirm('确认案件办理完毕？')){
					form.submit();
				}else{return false;}
			}
		});
	</script>
	</#if>
	<#assign hasGenFormDef=hasGenFormDef+[taskAction.formDefId]>
</#list>
<script type="text/javascript">
	//加载嫌疑人操作页面共用方法
		function loadXianyirenOptPage(optUrl,actionId){
			$.ajax({
			   async:true,
			   dataType:'html',
			   url: top.APP_PATH+optUrl+"?curTTTTtimestamp="+new Date().getTime(),
			   data: { caseId: caseId, taskId: taskId},
			   success: function(data){
				   $('#'+actionId).append(data);
			   }
			}); 
		}
	//加载嫌疑人操作页面
	<#list taskActionList as taskAction>
		<#if taskAction.actionType==TASK_ACTION_TIQINGDAIBU>
		loadXianyirenOptPage('${TIQINGDAIBU_OPT_URL}','${taskAction.actionId}');
		<#elseif taskAction.actionType==TASK_ACTION_SHENCHADAIBU>
		loadXianyirenOptPage('${SHENCHADAIBU_OPT_URL}','${taskAction.actionId}');
		<#elseif taskAction.actionType==TASK_ACTION_TIQINGQISU>
		loadXianyirenOptPage('${TIQINGQISU_OPT_URL}','${taskAction.actionId}');
		<#elseif taskAction.actionType==TASK_ACTION_TIQIGONGSU>
		loadXianyirenOptPage('${TIQIGONGSU_OPT_URL}','${taskAction.actionId}');
		<#elseif taskAction.actionType==TASK_ACTION_BUQISU>
		loadXianyirenOptPage('${BUQISU_OPT_URL}','${taskAction.actionId}');
		<#elseif taskAction.actionType==TASK_ACTION_FAYUANPANJUE>
		loadXianyirenOptPage('${FAYUANPANJUE_OPT_URL}','${taskAction.actionId}');
		<#elseif taskAction.actionType==TASK_ACTION_YISONGGONGAN>
		loadXianyirenOptPage('${YISONGGONGAN_OPT_URL}','${taskAction.actionId}');
		</#if>
	</#list>
	
	//提交办理的前置检验js方法名（校验嫌疑人相关操作是否完毕）
	$('.btn_small').click(function(){
		window.curActionId = $(this).attr('actionId');//保存记录当前提交的action
		$(this).parent().prevAll('.dtForm').submit();//提交表单，触发表单校验
	});
</script>