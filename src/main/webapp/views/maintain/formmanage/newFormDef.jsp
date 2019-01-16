<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>" />
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
<script src="<c:url value="/resources/jquery/jquery-ui-1.8.12.custom.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/jquery/layout/jquery.layout.js"/>"></script>
<script src="<c:url value="/resources/script/json.min.js"/>"></script>
<script type="text/javascript">
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
	

	//TODO:流程变量名称验证（只能包含字母和下划线）
	
	$(function(){
        //按钮样式
        jQuery(".btn_small").mouseover(function(){
        		jQuery(this).attr("class","btn_small_onmouseover");
        }).mouseout(function(){
        		jQuery(this).attr("class","btn_small");
        	});
		$('#preview_field_div div,#preview_action_div span').live('mouseover', function(){
			$(this).css('background-color','#E3E3E3').css('cursor','move');
		}).live('mouseout', function(){
			$(this).css('background-color','#FFFFFF').css('cursor','default');
		});
		//字段排序
		$('#preview_field_div').sortable({
			stop: function(event, ui) {
				//重置字段的index
				$('#preview_field_div div').each(function(i){
					var fieldViewId=this.id;
					var fieldDefIndex = fieldViewMap[fieldViewId];
					formDef.formFieldList[fieldDefIndex].positionIndex=i;
				});
			}			
		});
	});
	
	var fieldIdGen=1;//fieldViewId生成器
	var formDef={//表单定义
			formDefName:'',
			formFieldList:[]
	};
	var fieldViewMap={};//fieldViewId:formDef.formFieldList里formField的索引
	//提交表单定义
	function submitFormDef(){
		var formDefName = $('#formName').val().trim();
		if(formDefName==''){
			top.art.dialog.alert('表单模板名称不能空！');
			return;
		}
		if(formDef.formFieldList.length==0){
			top.art.dialog.alert('没有定义表单字段！');
			return;
		}
		formDef.formDefName=formDefName;
		$.postJSON('<c:url value="/maintain/formmanage/new"/>', formDef, function(data){
			//top.art.dialog.alert('添加成功');
			top.art.dialog.alert(data.msg);
		});
	}
	
	function showTextCreate(){
		$('#text_creater').dialog({
			buttons:{
				'确认':function(){
					createText($(this));
				}
			}
		});
	}
	function showtextareaCreate(){
		$('#textarea_creater').dialog({
			buttons:{
				'确认':function(){
					createTextarea($(this));
				}
			}
		});
	}
	function showFieldItemsCreate(inputType,title){
		$('#fieldItems_creater').dialog({
			title:title,
			width:600,
			buttons:{
				'确认':function(){
					createFieldItems($(this));
				}
			}
		}).find('input[name="inputType"]').val(inputType);
	}
	function showFileCreate(){
		$('#file_creater').dialog({
			buttons:{
				'确认':function(){
					createFile($(this));
				}
			}
		});
	}
	//--------创建文本字段
	function createText(creater){
		var inputType=creater.find("input[name='inputType']").val().trim();
		var fieldName=creater.find("input[name='fieldName']").val().trim();
		var dataType=creater.find(":input[name='dataType']").val().trim();
		var dataFormat=creater.find("input[name='dataFormat']").val().trim();
		var minVal=creater.find("input[name='minVal']").val().trim();
		var maxVal=creater.find("input[name='maxVal']").val().trim();
		var requered=creater.find("input[name='requered']:checked").length==1?1:0;
		var joinProc=creater.find("input[name='joinProc']:checked").length==1?1:0;
		var procVarName=creater.find("input[name='procVarName']").val().trim();
		
		var formField={"inputType":inputType,required:requered};
		if(fieldName==''){
			top.art.dialog.alert('字段名称不能空');
			return;
		}
		formField.label=fieldName;
		if(dataType==''){
			top.art.dialog.alert('数据类型不能空');
			return;
		}
		formField.dataType=dataType;
		if(dataType=='4' && dataFormat==''){
			top.art.dialog.alert('数据格式不能空');
			return;
		}else if(dataType=='4' && dataFormat!=''){formField.format=dataFormat;}
		
		if((dataType=='2'||dataType=='3') && minVal!='' && !isNum(minVal)){
			top.art.dialog.alert('最小值 必须是数字');
			return;
		}else if((dataType=='2'||dataType=='3') && minVal!=''){formField.min=minVal;}
		
		if((dataType=='2'||dataType=='3') && maxVal!='' && !isNum(maxVal)){
			top.art.dialog.alert('最大值 必须是数字');
			return;
		}else if((dataType=='2'||dataType=='3') && maxVal!=''){formField.max=maxVal;}
		
		if(joinProc==1 && procVarName==''){
			top.art.dialog.alert('参与流程变量，必须设置变量名称');
			return;
		}else if(joinProc==1 && procVarName!=''){formField.procVarName=procVarName;}
		//设置index
		formField.positionIndex=formDef.formFieldList.length;
		//生成预览
		var fieldViewId='fieldView'+fieldIdGen++;
		var htmlStr='<div id="'+fieldViewId+'">'+fieldName+'：<input type="text" id="'+fieldViewId+'"/></div>';
		$('#preview_field_div').append(htmlStr);
		//--保存到formDef
		formDef.formFieldList.push(formField);
		fieldViewMap[fieldViewId]=formDef.formFieldList.length-1;
		
		creater.dialog('close');
	}
	//--------创建文本域
	function createTextarea(creater){
		var inputType=creater.find("input[name='inputType']").val().trim();
		var fieldName=creater.find("input[name='fieldName']").val().trim();
		var requered=creater.find("input[name='requered']:checked").length==1?1:0;
		var joinProc=creater.find("input[name='joinProc']:checked").length==1?1:0;
		var procVarName=creater.find("input[name='procVarName']").val().trim();
		
		var formField={"inputType":inputType,dataType:'1',required:requered};
		if(fieldName==''){
			top.art.dialog.alert('字段名称不能空');
			return;
		}
		formField.label=fieldName;
		
		if(joinProc==1 && procVarName==''){
			top.art.dialog.alert('参与流程变量，必须设置变量名称');
			return;
		}else if(joinProc==1 && procVarName!=''){formField.procVarName=procVarName;}
		//设置index
		formField.positionIndex=formDef.formFieldList.length;
		//生成预览
		var fieldViewId='fieldView'+fieldIdGen++;
		var htmlStr='<div id="'+fieldViewId+'">'+fieldName+'：<textarea rows="5" cols="30" id="'+fieldViewId+'"></textarea> </div>';
		$('#preview_field_div').append(htmlStr);
		//--保存到formDef
		formDef.formFieldList.push(formField);
		fieldViewMap[fieldViewId]=formDef.formFieldList.length-1;
		
		creater.dialog('close');
	}
	//--------创建多选、下拉、单选
	function createFieldItems(creater){
		var inputType=creater.find("input[name='inputType']").val().trim();
		var fieldName=creater.find("input[name='fieldName']").val().trim();
		var dataType=creater.find(":input[name='dataType']").val().trim();
		var fieldItems=creater.find("input[name='fieldItems']").val().trim();
		var requered=creater.find("input[name='requered']:checked").length==1?1:0;
		var joinProc=creater.find("input[name='joinProc']:checked").length==1?1:0;
		var procVarName=creater.find("input[name='procVarName']").val().trim();
		
		var formField={"inputType":inputType,required:requered};
		if(fieldName==''){
			top.art.dialog.alert('字段名称不能空');
			return;
		}
		formField.label=fieldName;
		if(dataType==''){
			top.art.dialog.alert('数据类型不能空');
			return;
		}
		formField.dataType=dataType;
		
		if(fieldItems==''){
			top.art.dialog.alert('选项值不能空');
			return;
		}else{
			//TODO:界面友好的选项设置方式
			var fieldItemList=eval(fieldItems);
			formField.fieldItemList=fieldItemList;
		}
		
		if(joinProc==1 && procVarName==''){
			top.art.dialog.alert('参与流程变量，必须设置变量名称');
			return;
		}else if(joinProc==1 && procVarName!=''){formField.procVarName=procVarName;}
		//设置index
		formField.positionIndex=formDef.formFieldList.length;
		//生成预览
		var fieldViewId='fieldView'+fieldIdGen++;
		var htmlStr='<div id="'+fieldViewId+'">'+fieldName+'：';
		if(inputType=='3'){//单选
			for(i=0;i<formField.fieldItemList.length;i++){
				htmlStr+='<input type="radio"/>'+formField.fieldItemList[i].label+'&nbsp;&nbsp;';
			}
		}else if(inputType=='4'){//多选
			for(i=0;i<formField.fieldItemList.length;i++){
				htmlStr+='<input type="checkbox"/>'+formField.fieldItemList[i].label+'&nbsp;&nbsp;';
			}
		}else if(inputType=='5'){//下拉
			htmlStr+='<select>';
			for(i=0;i<formField.fieldItemList.length;i++){
				htmlStr+='<option>'+formField.fieldItemList[i].label+'</option>';
			}
			htmlStr+='</select>';
		}
		htmlStr+='</div>';
		$('#preview_field_div').append(htmlStr);
		//--保存到formDef
		formDef.formFieldList.push(formField);
		fieldViewMap[fieldViewId]=formDef.formFieldList.length-1;
		
		creater.dialog('close');
	}
	//--------创建上传文件字段
	function createFile(creater){
		var inputType=creater.find("input[name='inputType']").val().trim();
		var fieldName=creater.find("input[name='fieldName']").val().trim();
		var requered=creater.find("input[name='requered']:checked").length==1?1:0;
		
		var formField={"inputType":inputType,dataType:'-1',required:requered};//file的数据类型无实际意义
		if(fieldName==''){
			top.art.dialog.alert('字段名称不能空');
			return;
		}
		formField.label=fieldName;
		//设置index
		formField.positionIndex=formDef.formFieldList.length;
		//生成预览
		var fieldViewId='fieldView'+fieldIdGen++;
		var htmlStr='<div id="'+fieldViewId+'">'+fieldName+'：<input type="file" id="'+fieldViewId+'"/></div>';
		$('#preview_field_div').append(htmlStr);
		//--保存到formDef
		formDef.formFieldList.push(formField);
		fieldViewMap[fieldViewId]=formDef.formFieldList.length-1;
		
		creater.dialog('close');
	}
</script>
<title>后台维护登录</title>
</head>
<body>
<div class="panel">

<div>
	表单模板名称：<input type="text" id="formName"/>
</div>
<div>
	<button onclick="showTextCreate();">文本框(text1)</button>&nbsp;&nbsp;&nbsp;&nbsp;
	<button onclick="showtextareaCreate();">文本域(textarea2)</button>&nbsp;&nbsp;&nbsp;&nbsp;
	<button onclick="showFieldItemsCreate(3,'添加单选按钮');">单选按钮(radio3)</button>&nbsp;&nbsp;&nbsp;&nbsp;
	<button onclick="showFieldItemsCreate(4,'添加多选按钮');">多选按钮(checkbox4)</button>&nbsp;&nbsp;&nbsp;&nbsp;
	<button onclick="showFieldItemsCreate(5,'添加下拉框');">下拉框(select5)</button>&nbsp;&nbsp;&nbsp;&nbsp;
	<button onclick="showFileCreate();">上传组件(file6)</button>&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<h3>表单预览 </h3>
<div id="preview_div" style="margin: 5px;padding:10px; border: 2px solid blue;" align="center"><!-- 预览 容器 -->
	<div id="preview_field_div"  align="center" style="margin: 10px;"></div>
	<div id="preview_action_div"  align="center"></div>
</div>
<div id="text_creater" style="display: none;" title="添加文本框"><!-- text -->
	<input type="hidden" name="inputType" value="1">
	字段名称:<input type="text" name="fieldName"><br/>
	数据类型:<select name="dataType">
		<option value=""></option>
		<option value="1">字符</option>
		<option value="2">整数</option>
		<option value="3">数值类型</option>
		<option value="4">日期类型</option>
	</select><br/>
	数据格式(针对日期类型)：<input type="text" name="dataFormat"/><br/>
	最小值--最大值(针对整数和数值类型)：<input type="text" name="minVal" size="10"/> -- <input type="text" name="maxVal" size="10"/><br/>
	<input type="checkbox" name="requered">是否必填<br/>
	<input type="checkbox" name="joinProc">流程变量？<br/>
	流程变量名称：<input type="text" name="procVarName"/>
</div>
<div id="textarea_creater" style="display: none;" title="添加文本域"><!-- extarea -->
	<input type="hidden" name="inputType" value="2">
	字段名称:<input type="text" name="fieldName"><br/>
	<input type="checkbox" name="requered">是否必填？<br/>
	<input type="checkbox" name="joinProc">流程变量？<br/>
	流程变量名称：<input type="text" name="procVarName"/>
</div>
<div id="fieldItems_creater" style="display: none;" title="添加下拉框"><!-- radio、select和 checkbox -->
	<input type="hidden" name="inputType">
	字段名称:<input type="text" name="fieldName"><br/>
	数据类型:<select name="dataType">
		<option value=""></option>
		<option value="1">字符</option>
		<option value="2">整数</option>
		<option value="3">数值类型</option>
	</select><br/>
	选项值<input type="text" name="fieldItems" size=100 value="[{label:'男',value:1},{label:'女',value:2}]"/><br/>用逗号分隔各选项值(示例：[{label:'男',value:1},{label:'女',value:2}])<br/>
	<input type="checkbox" name="requered">是否必填<br/>
	<input type="checkbox" name="joinProc">流程变量？<br/>
	流程变量名称：<input type="text" name="procVarName"/>
</div>
<div id="file_creater" style="display: none;"  title="添加上传组件"><!-- file -->
	<input type="hidden" name="inputType" value="6">
	字段名称:<input type="text" name="fieldName"><br/>
	<input type="checkbox" name="requered">是否必填<br/>
</div>

<br/><br/>
<div align="center">
	<button onclick="submitFormDef();" class="btn_small">提 交</button>
    <button onclick="javascript:location.href='<c:url value="/maintain/formmanage"/>'"  class="btn_small">返 回</button>
</div>
</div>
</body>
</html>