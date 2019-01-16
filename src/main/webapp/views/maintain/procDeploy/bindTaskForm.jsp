<%@page import="com.ksource.syscontext.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="dict" uri="dictionary" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>" />
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
<script src="<c:url value="/resources/jquery/jquery-ui-1.8.12.custom.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery.form.js"/>"></script>
<script src="<c:url value="/resources/script/json.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/script/FormBuilder.js"/>"></script>
<script type="text/javascript">
/* 方法:Array.remove(dx)
 * 功能:删除数组元素.
 * 参数:dx删除元素的下标.
 * 返回:在原数组上修改数组
 */
//经常用的是通过遍历,重构数组.
Array.prototype.remove=function(dx)
{
  if(isNaN(dx)||dx>this.length){return false;}
  for(var i=0,n=0;i<this.length;i++)
  {
      if(this[i]!=this[dx])
      {
          this[n++]=this[i];
      }
  }
  this.length-=1;
};

//在数组中获取指定值的元素索引
Array.prototype.getIndexByValue= function(value)
{
  var index = -1;
  for (var i = 0; i<this.length; i++)
  {
      if (this[i] == value)
      {
          index = i;
          break;
      }
  }
  return index;
};

//去空格函数
String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, '');   
}
//校验是否是数字(浮点或整数)
function isNum(strNum){
	var pattern = /^\d+(\.\d+)?$/;
    if(pattern.test(strNum))
    {
	    return true;
    }
    return false;
}
//校验小数两位
function isNum2(strNum){
	var pattern = /^\d+[.]?\d{0,2}$/;
    if(pattern.test(strNum)){
     return true;
    }
    return false;
}
//校验小数两位
function isInteger(strNum){
	var pattern = /^(\d*|\-?[1-9]{1}\d*)$/;
    if(pattern.test(strNum)){
     return true;
    }
    return false;
}

	//全局流程定义ID
	var procDefId='${procDeploy.procDefId}';
	//流程图信息
	var procDiagramList = ${procDiagramListJson};
	//表单模板信息
	var formJsonViewList={
	<c:forEach items="${formDefList}" var="formDef">
		"form_${formDef.formDefId}":${formDef.jsonView},
	</c:forEach>
		"nothing-nothing":''
	};
	//taskBind集合对象(****存储任务表单和案件状态绑定信息、提交后台****)
	var taskBindList=${taskBindListJson};
	//保存任务节点ID(taskDefId)和taskBindList的对应index关系;
	var taskBindMap = {};
	$(function(){
		//初始化taskBindMap
		$.each(taskBindList, function(i,taskBind){
			var taskDefId=taskBind.taskDefId;
			taskBindMap[taskDefId]=i;
			//处理展示效果--document载入完毕后
			$('#'+taskDefId+' div').removeClass('whiteHove').addClass('greenHove');
		});
		//绑定鼠标移入任务节点的事件效果
		$('#pic .outer div').filter(function(){return !$(this).hasClass("greenHove");}).mouseover(function(){
			if(!$(this).hasClass("greenHove")){
				$(this).removeClass('whiteHove').addClass('redHove');
			}
		}).mouseout(function(){
			if(!$(this).hasClass("greenHove")){
				$(this).removeClass('redHove').addClass('whiteHove');
			}
		})
	});
	//绑定任务表单
	function bindTaskFrom(taskDefId,taskType,assignTarget,caseInd,caseIndVal){
		if(!assignTarget){
			assignTarget='';
		}
		var index=taskBindMap[taskDefId];
		if(index==undefined){
			var taskBind={taskDefId:taskDefId,procDefId:procDefId,taskType:taskType,assignTarget:assignTarget,caseInd:caseInd,caseIndVal:caseIndVal};
			taskBindMap[taskDefId]=taskBindList.length;//添加map关系
			taskBindList.push(taskBind);//添加任务表单绑定
		}else{
			var taskBind=taskBindList[index];
			taskBind.taskType=taskType;
			taskBind.assignTarget=assignTarget;
			taskBind.caseInd=caseInd;
			taskBind.caseIndVal=caseIndVal;
			
		}
		//处理页面效果
		$('#'+taskDefId+' div').removeClass('whiteHove').removeClass('redHove').addClass('greenHove');
	}
	
	//------------为任务绑定提交按钮
	var taskActionIdGen=1;//表单动作ID生成器
	//(****存储任务提交动作信息、提交后台****)
	var taskActionList=${taskActionListJson};
	var taskActionListIndexMap={};//key:'_'+actionId,value：taskActionList的taskAction索引
	var taskActionMap={};//key :taskDefId，value：[taskAction...]
	var taskActionIndexMap={};//key:'_'+actionId,value：taskActionMap.taskDefId的taskAction索引
	$(function(){
		$.each(taskActionList,function(i,taskAction){
			var taskActionListT = taskActionMap[taskAction.taskDefId];
			if(!taskActionListT){
				taskActionListT=taskActionMap[taskAction.taskDefId]=new Array();
			}
			taskActionListT.push(taskAction);
			taskActionIndexMap['_'+taskAction.actionId]=taskActionListT.length-1;
			taskActionListIndexMap['_'+taskAction.actionId]=i;
			//展示效果
			$('#taskActionNote_'+taskAction.taskDefId).html('已设置提交动作').css('color','green');
		});
	});
	
	
	
	
	//打开绑定页面
	function showBind(taskDefId){
		$('#taskDefId').val(taskDefId);
		//显示表单和状态绑定
		var index = taskBindMap[taskDefId];
		var taskBind = taskBindList[index];
		var taskType = "";
		var assignTarget = "";
		if(taskBind && taskBind.taskType){
			taskType=taskBind.taskType;
		}
		if(taskType!=''){
			$('#taskType_'+taskType).attr("checked","checked");
		}else{
			$('#taskType_3').attr("checked","checked");
		}
		if(taskBind && taskBind.assignTarget){
			assignTarget=taskBind.assignTarget;
		}
		if(assignTarget!=''){
			$('#assignTarget').val(assignTarget);
		}else{
			$('#assignTarget').val('');
		}
		//案件指标
		var caseInd=taskBind?taskBind.caseInd:"",
				caseIndVal=taskBind?taskBind.caseIndVal:"";
		$('#caseIndC').html(genCaseIndSelectHtml(caseInd,caseIndVal));
		
		//处理提交动作
		//暂时不开放使用
		//$('#taskButC').html('<a href ="javascript:;" onclick="addTaskButUI();" id="addTaskButA">添加任务提交按钮</a>&nbsp;');
		$('#taskButC').empty();
		var taskActionListT = taskActionMap[taskDefId];
		if(taskActionListT && taskActionListT.length>0){
			//初始化按钮显示
			$.each(taskActionListT,function(i,taskAction){
				addTaskButUI(taskAction);
			});
		}
		$('#taskBindDiv').dialog({
			width:500,
			height:400,
			buttons:{
				'关闭':function(){
					$(this).dialog('close');
				},
				'确定':function(){
					var taskDefId=$('#taskDefId').val();
					var assignTarget=$('#assignTarget').val();
					if(assignTarget==''){top.art.dialog.alert('请选择任务的办理机构！');return;}
					var taskType=$("input[name='taskType']:checked").val();
					var caseInd=$('#caseIndC :input[name="caseInd"]').val();
					var caseIndVal=$('#caseIndC :input[name="caseIndVal"]').val();
					//绑定表单
					bindTaskFrom(taskDefId,taskType,assignTarget,caseInd,caseIndVal);
					//设置任务表单按钮
					var taskButCs=$('#taskButC .taskBut');
					if(taskButCs.length==0){top.art.dialog.alert('请添加任务提交动作！');return;};
					var flag=true;
					taskButCs.each(function(i){
						//actionName,procVarName,procVarValue,procVarDataType , joinProc
						var actionId = $(this).find("input[name='actionId']").val().trim();
						var actionName = $(this).find("input[name='actionName']").val().trim();
						var formDefId = $(this).find(":input[name='formDefId']").val().trim();
						var procVarName = $(this).find("input[name='procVarName']").val().trim();
						var procVarValue = $(this).find("input[name='procVarValue']").val().trim();
						var procVarDataType = $(this).find(":input[name='procVarDataType']").val().trim();
						var joinProc=$(this).find("input[name='joinProc']:checked").length==1?1:0;
						var taskCaseState = $(this).find(":input[name='taskCaseState']").val().trim();
						var caseInd=$(this).find(":input[name='caseInd']").length>0?$(this).find(":input[name='caseInd']").val().trim():"";
						var caseIndVal=$(this).find(":input[name='caseIndVal']").length>0?$(this).find(":input[name='caseIndVal']").val().trim():"";
						var actionType=$(this).find(":input[name='actionType']").val().trim();
						
						var taskAction={procDefId:procDefId,taskDefId:taskDefId,actionType:actionType,caseInd:caseInd,caseIndVal:caseIndVal};
						if(actionName==''){
							top.art.dialog.alert('动作名称不能空！');return flag=false;
						}
						taskAction.actionName=actionName;
						if(formDefId==''){
							top.art.dialog.alert('请选择动作对应的表单模板！');return flag=false;
						}
						taskAction.formDefId=formDefId;
						if(taskCaseState==''){
							top.art.dialog.alert('请选择任务执行后案件的状态！');return flag=false;
						}
						taskAction.taskCaseState=taskCaseState;
						if(joinProc==1){
							if(procVarName==''){
								top.art.dialog.alert('变量名称不能空！');return flag=false;
							}
							taskAction.procVarName=procVarName;
							if(procVarDataType==''){
								top.art.dialog.alert('变量数据类型不能空！');return flag=false;
							}
							taskAction.procVarDataType=procVarDataType;
							if(procVarValue==''){
								top.art.dialog.alert('变量值不能空！');return flag=false;
							}
							taskAction.procVarValue=procVarValue;
							if(procVarDataType==2 && !isInteger(procVarValue)){//整数
									top.art.dialog.alert('变量值不是整数！');return flag=false;
							}else if(procVarDataType==3 && !isNum(procVarValue)){//数字
									top.art.dialog.alert('变量值不是数字！');return flag=false;
							}
						}
						if(actionId && actionId!=''){//更新task动作(先删除)
							removeTaskAction(actionId,taskDefId);
						}else{
							actionId=(++taskActionIdGen);
						}
						taskAction.actionId=actionId;
						//保存动作按钮对象
						taskActionList.push(taskAction);
						taskActionListIndexMap['_'+actionId]=taskActionList.length-1;
						
						var taskActionListT = taskActionMap[taskDefId];
						if(!taskActionListT){
							taskActionListT=taskActionMap[taskDefId]=new Array();
						}
						taskActionListT.push(taskAction);
						taskActionIndexMap['_'+actionId]=taskActionListT.length-1;
						//展示效果
						$('#taskActionNote_'+taskDefId).html('已设置提交动作').css('color','green');
					});
					if(flag){
						$(this).dialog('close');
					}
				}
			}
		});
	}
	
	//删除一个任务动作
	function removeTaskAction(actionId,taskDefId){
		//删除任务动作绑定taskActionLis、删除单个任务的banding集合taskActionMap
		var index1 = taskActionListIndexMap['_'+actionId];
		var index2 = taskActionIndexMap['_'+actionId];
		taskActionList.remove(index1);
		//同步index
		$.each(taskActionListIndexMap,function(_actionId,_index){
			if(_index>index1){
				taskActionListIndexMap[_actionId]= (--_index);
			}
		});
		taskActionMap[taskDefId].remove(index2);
		//同步index
		$.each(taskActionIndexMap,function(_actionId,_index){
			if(_index>index2){
				taskActionIndexMap[_actionId]= (--_index);
			}
		});
		delete taskActionListIndexMap['_'+actionId];
		delete taskActionIndexMap['_'+actionId];
	}
	
	//delTaskButUI
	function delInitTaskButUI(actionId,aC){
		var taskDefId = $('#taskDefId').val();
		removeTaskAction(actionId,taskDefId);
		if(taskActionMap[taskDefId].length==0){
			//展示效果
			$('#taskActionNote_'+taskDefId).html('未设置提交动作').css('color','red');
		}
		$(aC).parents('.taskBut').remove();
	}
	//delTaskButUI
	function delTaskButUI(aC){
		$(aC).parents('.taskBut').remove();
	}


	//生成动作类型下拉框
	function genTaskActionTypeSelectHtml(actionType){
		actionType = (actionType==undefined || actionType==null)?-1:actionType;
		var actionTypeModel=[
			[0,'0普通'],
			[1,'1提请逮捕'],
			[6,'6审查逮捕'],
			[2,'2提请起诉'],
			[3,'3提起公诉'],
			[4,'4不起诉'],
			[5,'5法院判决'],
			[7,'7案件审查（已处罚录入）'],
			[8,'8案件审查（立案录入）'],
			[9,'9案件审查（移送案件录入）'],
			[10,'10移送公安'],
			[11,'11公安立案']
		];
		var html='动作类型：<select name="actionType">';
		$.each(actionTypeModel,function(i,n){
			if(actionType==n[0]){
				html+='<option value="'+n[0]+'" selected="selected">'+n[1]+'</option>';
			}else{
				html+='<option value="'+n[0]+'">'+n[1]+'</option>';
			}
		});
		html+='</select><br/>';
		return html;
	}

	//>>>生成案件指标下拉框
	var caseIndMap = {
		//1.处罚状态2、移送状态、3立案状态、4逮捕状态、5起诉状态、6判决状态、8不立案理由状态(有点牵强了)
		ind_1:{val:1,label:'处罚状态',indVal:[[2,'已处罚'],[3,'不处罚']]},
		ind_2:{val:2,label:'移送状态',indVal:[[2,'直接移送'],[3,'建议移送'],[4,'建议移送未移送']]},
		ind_3:{val:3,label:'立案状态',indVal:[[2,'不立案'],[3,'立案'],[4,'通知立案']]},
		//交由ChufaCaseProcUtil处理ind_4:{val:4,label:'逮捕状态',indVal:[[2,'批准逮捕'],[3,'不逮捕']]},
		ind_5:{val:5,label:'起诉状态',indVal:[[2,'不起诉'],[3,'起诉']]},
		ind_6:{val:6,label:'判决状态',indVal:[[2,'已判决']]},
        ind_8:{val:8,label:'要求说明不立案理由状态',indVal:[[1,'要求说明']]},
        ind_9:{val:9,label:'说明不立案理由状态',indVal:[[1,'说明']]}
	};
	function genCaseIndSelectHtml(caseInd,caseIndVal){
		var html='设置案件指标:<select name="caseInd"  onchange="genCaseIndValSelectHtml(this);"  style="width: 100px;"><option value=""></option>';
		$.each(caseIndMap,function(p,v){
			if(p=='ind_'+caseInd){
				html+='<option value="'+v.val+'" selected="selected">'+v.label+'</option>';
			}else{
				html+='<option value="'+v.val+'">'+v.label+'</option>';
			}
		});
		html+='</select><span class="caseIndVal">'+genCaseIndValSelectHtml(null,caseInd,caseIndVal)+'</span><br/>';
		return html;
	}
	function genCaseIndValSelectHtml(caseIndSelect,caseInd,caseIndVal){//taskbind和taskaction共用、初始化和onchange共用
		if(!caseIndSelect && !caseInd){
			return "";
		}
		caseInd = caseInd||caseIndSelect.value;
		var html='<select name="caseIndVal"  style="width: 100px;"><option value=""></option>';
		var caseIndVals = caseIndMap['ind_'+caseInd].indVal;
		$.each(caseIndVals,function(i,v){
			if(v[0]==caseIndVal){
				html+='<option value="'+v[0]+'" selected="selected">'+v[1]+'</option>';
			}else{
				html+='<option value="'+v[0]+'">'+v[1]+'</option>';
			}
			
		});
		html+='</select>';
		if(caseIndSelect){
			$(caseIndSelect).next('.caseIndVal').html(html);
		}
		return html;
	}
	//<<<生成案件指标下拉框
	
	
	//生成案件状态下拉框的html文本
	function genTaskCaseStateSelectHtml(taskCaseState){
		var taskCaseStateSelectHtml='任务执行后案件的状态：<select name="taskCaseState"><option value=""></option>';
		<dict:getDictionary var="caseStateList" groupCode="${procDeploy.procDefKey}State"/>
		<c:forEach items="${caseStateList}" var="caseState">
		if(taskCaseState=='${caseState.dtCode}'){
			taskCaseStateSelectHtml+='<option value="${caseState.dtCode}" selected="selected">${caseState.dtName}</option>';	
		}else{
			taskCaseStateSelectHtml+='<option value="${caseState.dtCode}">${caseState.dtName}</option>';	
		}
		</c:forEach>
		taskCaseStateSelectHtml+='</select><br/>';
		return taskCaseStateSelectHtml;
	}
	//生成表单模板select控件 
	function genFormDefSelectHtml(formDefId){
		var html='表单模板：<select name="formDefId"><option value=""></option>';
		if(formDefId=='-1'){
			html+='<option value="-1" selected="selected">无模板(录入案件)</option>';
		}else{
			html+='<option value="-1">无模板(录入案件)</option>';
		}
		if(formDefId=='-2'){
			html+='<option value="-2" selected="selected">无模板(修改案件)</option>';
		}else{
			html+='<option value="-2">无模板(修改案件)</option>';
		}
		<c:forEach items="${formDefList}" var="formDef">
		if(formDefId=='${formDef.formDefId}'){
			html+='<option value="${formDef.formDefId}" selected="selected">${formDef.formDefName}</option>';	
		}else{
			html+='<option value="${formDef.formDefId}">${formDef.formDefName}</option>';	
		}
		</c:forEach>
		html+='</select><br/>';
		return html;
	}
	//添加任务按钮
	function addTaskButUI(taskAction){
		if(taskAction && taskAction!=''){
			var html='<div class="taskBut"><div align="right"><a href ="javascript:;" onclick="delInitTaskButUI(\''+taskAction.actionId+'\',this);">删除提交按钮</a></div>';
			html+='<input name="actionId" type="hidden" value="'+taskAction.actionId+'"/>';
			html+='按钮名称：<input type="text" name="actionName" value="'+taskAction.actionName+'" /><br />';
			html+=genTaskActionTypeSelectHtml(taskAction.actionType);
			html+=genFormDefSelectHtml(taskAction.formDefId);
			html+=genTaskCaseStateSelectHtml(taskAction.taskCaseState);
			html+=genCaseIndSelectHtml(taskAction.caseInd,taskAction.caseIndVal);
			if(taskAction.procVarName && taskAction.procVarValue && taskAction.procVarName!='' && taskAction.procVarValue!=''){
				html+='设置流程变量？：<input type="checkbox" name="joinProc"  checked="checked"/><br />';
				html+='流程变量名称：<input type="text" name="procVarName" value="'+taskAction.procVarName+'" /><br />';
				html+='流程变量数据类型：<select name="procVarDataType"><option value=""></option>'
				if(taskAction.procVarDataType==1){
					html+='<option value="1" selected="selected">字符</option>';
				}else{
					html+='<option value="1">字符</option>';
				}
				if(taskAction.procVarDataType==2){
					html+='<option value="2" selected="selected">整数</option>';
				}else{
					html+='<option value="2">整数</option>';
				}
				if(taskAction.procVarDataType==3){
					html+='<option value="3" selected="selected">数值类型</option>';
				}else{
					html+='<option value="3">数值类型</option>';
				}
				html+='</select><br/>';
				html+='流程变量值：<input type="text" name="procVarValue" value="'+taskAction.procVarValue+'" />';
			}else{
				html+='设置流程变量？：<input type="checkbox" name="joinProc" /><br />';
				html+='流程变量名称：<input type="text" name="procVarName" /><br />';
				html+='流程变量数据类型：<select name="procVarDataType"><option value=""></option>'
					+'<option value="1">字符</option><option value="2">整数</option>'
					+'<option value="3">数值类型</option></select><br/>';
				html+='流程变量值：<input type="text" name="procVarValue" /><br/>';
			}
			html+='<hr /></div>';
			$('#taskButC').append(html);
		}else{
			var html='<div class="taskBut"><div align="right"><a href ="javascript:;" onclick="delTaskButUI(this);">删除提交按钮</a></div>';
			html+='<input name="actionId" type="hidden" value="" /><br />';
			html+='按钮名称：<input type="text" name="actionName" /><br />';
			html+=genTaskActionTypeSelectHtml();
			html+=genFormDefSelectHtml();
			html+=genTaskCaseStateSelectHtml();
			html+=genCaseIndSelectHtml();
			html+='设置流程变量？：<input type="checkbox" name="joinProc" /><br />';
			html+='流程变量名称：<input type="text" name="procVarName" /><br />';
			html+='流程变量数据类型：<select name="procVarDataType"><option value=""></option>'
				+'<option value="1">字符</option><option value="2">整数</option>'
				+'<option value="3">数值类型</option></select><br/>';
			html+='流程变量值：<input type="text" name="procVarValue" /><br/>';
			html+='<hr /></div>';
			$('#taskButC').append(html);
		}
	}
	//提交验证
	function submit(){
		var flag=true;
		//验证 
		$.each(procDiagramList,function(i,procDiagram){
			var taskDefId = procDiagram.elementId;
			var taskName=procDiagram.elementName;
			//验证表单是否全部绑定
			var taskBind = taskBindList[taskBindMap[taskDefId]];
			if(!taskBind || !taskBind.assignTarget || taskBind.assignTarget==''){
				top.art.dialog.alert(taskName+'：未设置办理机构！');
				return flag=false;
			}
			//验证任务提交动作绑定
			var taskActionListT = taskActionMap[taskDefId]; 
			if(!taskActionListT || taskActionListT.length==0){
				top.art.dialog.alert(taskName+'：未设置提交动作！');
				return flag=false;
			}
		});
		if(flag){
			//提交任务-表单绑定
			$.postJSON(
				'<c:url value="/maintain/procDeploy/bindTaskForm"/>',
				taskBindList,
				function(data){
					//提交任务-提交动作绑定
					$.postJSON(
						'<c:url value="/maintain/procDeploy/bindTaskAction"/>',
						taskActionList,
						function(data){
							top.art.dialog.alert('任务设置完毕！');
						}
					);
				}
			);
		}
	}
	
	//document ready
	$(function(){
		$('input[name="joinProc"]').live('click',function(){
			var taskButC = $(this).parents('.taskBut');
			if(this.checked){
				taskButC.find("input[name='procVarName']").val('action');
				taskButC.find(":input[name='procVarDataType']").val('2');
			}else{
				taskButC.find("input[name='procVarName'],input[name='procVarValue'],:input[name='procVarDataType']").val('');
			}
		});
		$('#taskBindDiv').tabs();
	});
</script>
<style type="text/css">
#pic {
  position: relative;
  border: 1px solid;
}

#pic .outer {
  cursor:pointer;
  position: absolute;
}
.redHove{
  background: red;
  filter:alpha(opacity=20); /*IE*/
  -moz-opacity:0.2; /*MOZ , FF*/
  -ms-filter:"alpha(opacity=20)";
  opacity:0.2;/*CSS3, FF1.5*/
  -khtml-opacity: 0.2;
}
.greenHove{
  background: green;
  filter:alpha(opacity=20); /*IE*/
  -moz-opacity:0.2; /*MOZ , FF*/
  -ms-filter:"alpha(opacity=20)";
  opacity:0.2;/*CSS3, FF1.5*/
  -khtml-opacity: 0.2;
}
.whiteHove{
  background: white;
  filter:alpha(opacity=20); /*IE*/
  -moz-opacity:0.1; /*MOZ , FF*/
  -ms-filter:"alpha(opacity=10)";
  opacity:0.1;/*CSS3, FF1.5*/
  -khtml-opacity: 0.1;
}



#pic .note {
  /*padding: 0.2em 0.5em;*/
  text-align: center;
  /* position: absolute;left: -30000px;*/
  color: blcak;
  background-color:#ffc;
  color: red;
}

/*
#pic li:hover .formNote {
  left: 0px;
}*/

#taskButC{
	margin: 20px 2px;;
	padding: 4px;
	border: 1px solid black;
}

.taskBut{
	background-color:#F1E1FF;
	margin: 4px;
	padding: 4px;
	border: 1px solid black;
}

</style>
<title>Insert title here</title>
</head>
<body>
	<p>&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value="/maintain/procDeploy"/>">返回部署页</a><p>
	
	<h2>任务表单绑定&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<!-- 如果已经部署，不能修改 -->
	<c:if test="${procDeploy.deployState==0}">
		<button onclick="submit();">保存</button>
	</c:if>
	<c:if test="${procDeploy.deployState==1}">
		<!-- <button disabled="disabled">保存</button>(已部署、不能修改绑定) -->
		<button onclick="submit();">保存</button>(已部署、请慎重修改)
	</c:if>
	</h2>
	<hr />
	<div id="pic" >
		<img src="<c:url value="/maintain/procDeploy/pict/${procDeploy.procDefId}"/>">
		<c:forEach items="${procDiagramList }" var="procDiagram">
		<div class="outer" id="${procDiagram.elementId}" style="width:${procDiagram.width+8}px;height:${procDiagram.height+8}px;top:${procDiagram.pointY-4}px;left:${procDiagram.pointX-4}px;">
			<div class="whiteHove" onclick="showBind('${procDiagram.elementId}');" style="width:${procDiagram.width+8}px;height:${procDiagram.height+8}px;">
				<!-- whiteHove 防止div无内容、无背景情况下，导致在ie下div的鼠标移入移出事件无效 -->
			</div>
			<span id='taskActionNote_${procDiagram.elementId}' class="note">未设置提交动作</span>
		</div>
		</c:forEach>
	</div>
	<div id="taskBindDiv" title="任务表单...绑定" style="display: none;">
		<input type="hidden" value="" id="taskDefId"/>
		<ul> 
			<li><a href="#taskFormC">任务表单/案件状态</a></li> 
			<li><a href="#taskButC">任务提交动作</a></li> 
		</ul>
		<div id="taskFormC">
			任务类型（<font color="red">添加案件、修改案件节点需要特殊处理</font>）：<br/> 
			<label for="taskType_3">普通任务</label><input type="radio" name="taskType"   id="taskType_3" value="3" onclick="taskTypeChange(3)" checked="checked"/>&nbsp;&nbsp;|&nbsp;&nbsp;
			<label for="taskType_1">特殊任务（添加案件）</label><input type="radio" name="taskType"   id="taskType_1" value="1"  onclick="taskTypeChange(1)"/>&nbsp;&nbsp;|&nbsp;&nbsp;
			<label for="taskType_2">特殊任务（修改案件）</label><input type="radio" name="taskType"   id="taskType_2" value="2"  onclick="taskTypeChange(2)"/>
			<script type="text/javascript">
				function taskTypeChange(taskType){
					/* if(taskType==3){
						$('#assignTarget').val('').attr('disabled',false);
					}else{
						$('#assignTarget').val('1-1-1-1').attr('disabled','disabled');
					} */
				}
			</script>
			<br />
			<hr />
			任务办理机构：<br/>
			<select name="assignTarget" id="assignTarget">
				<option value=""></option>
				<!-- 同常量表const -->
				<option value="<%=Const.TASK_ASSGIN_EQUALS_INPUTER%>">案件录入人机构</option>
				<option value="<%=Const.TASK_ASSGIN_IS_INPUTER%>">案件录入人机构(录入人办理)</option>
				<dict:getDictionary var="orgTypeList" groupCode="orgType"/>
				<c:forEach items="${orgTypeList}" var="orgType">
					<option value="${orgType.dtCode}" selected="selected">${orgType.dtName}</option>
				</c:forEach>
			</select><br/>
			<div id="caseIndC"></div>
			</div>
			<script type="text/javascript">
			/*function showFormView(formDefId){
				$('#formView').html('表单预览<hr />');
				var formJsonView = formJsonViewList['form_'+formDefId];
				if(formJsonView){
					new FormBuilder(formJsonView).genFormPreview('formView');
				}else{
					$('#formView').append('无预览<br/><br/><br/><br/>');
				}
			}*/
			</script>
		<!-- 绑定任务的提交动作 -->
		<div id="taskButC">
			<!-- 暂时不开放使用 <a href ="javascript:;" onclick="addTaskButUI();" id="addTaskButA">添加任务提交按钮</a>&nbsp;-->
		</div>
	</div>
</body>
</html>