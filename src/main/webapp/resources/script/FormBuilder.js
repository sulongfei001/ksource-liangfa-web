/*
 * 表单模板生成器(依赖jquery包)
 * 表单校验使用已封装的jqueryUtil里的校验方法
 * 1.生成表单预览视图
 * 2.生成任务办理表单
 */

/**
 * formDef:表单对象，包含字段集合
 */
;
FormBuilder = function(formDef) {
	this.formDef = formDef;
	this.formDefId = formDef.formDefId;
	this.formDefName = formDef.formDefName;
	this.formFieldList = formDef.formFieldList;
	// 生成表单预览视图
	// target:填充目标容器id
	this.genFormPreview = function(targetId) {
		// 生成字段
		$
				.each(
						this.formFieldList,
						function(index, formField) {
							var fieldId = formField.fieldId;
							var label = formField.label;
							var inputType = formField.inputType;
							var dataType = formField.dataType;
							var procVarName = formField.procVarName;
							var required = formField.required;
							var max = formField.max;
							var min = formField.min;
							var fieldItemList = formField.fieldItemList;

							var inputHtml = '';// 控件html
							var noteHtml = '<span class="inutNote" style="color:red;">(';// 控件描述html
							if (inputType == 1) {// 文本框
								inputHtml = '<input type="text" class="genInput"/>';
							} else if (inputType == 2) {// 文本域
								inputHtml = '<textarea rows="5" cols="30" class="genInput"></textarea>';
							} else if (inputType == 3) {// 单选
								$.each(fieldItemList, function(itemIndex,
										fieldItem) {
									inputHtml += '<input type="radio" value="'
											+ fieldItem.value + '" />'
											+ fieldItem.label + '&nbsp;&nbsp;';
								});
							} else if (inputType == 4) {// 多选
								$
										.each(
												fieldItemList,
												function(itemIndex, fieldItem) {
													inputHtml += '<input type="checkbox" value="'
															+ fieldItem.value
															+ '" />'
															+ fieldItem.label
															+ '&nbsp;&nbsp;';
												});
							} else if (inputType == 5) {// 下拉
								inputHtml = '<select class="genInput">';
								$.each(fieldItemList, function(itemIndex,
										fieldItem) {
									inputHtml += '<option value="'
											+ fieldItem.value + '">'
											+ fieldItem.label + '</option>';
								});
								inputHtml += '</select>';
							} else if (inputType == 6) {// 上传组件
								inputHtml = '<input type="file" class="genInput"/>';
							}
							// 控件描述
							switch (dataType) {
							case 1:// 字符
								noteHtml += '数据类型：字符、';
								break;
							case 2:// 整数
								noteHtml += '数据类型：整数、';
								noteHtml += min ? '最小值：' + min + '、' : '';
								noteHtml += max ? '最大值：' + max + '、' : '';
								break;
							case 3:// 数字
								noteHtml += '数据类型：数字、';
								noteHtml += min ? '最小值：' + min + '、' : '';
								noteHtml += max ? '最大值：' + max + '、' : '';
								break;
							case 4:// 日期
								noteHtml += '数据类型：日期、';
								break;
							}
							noteHtml += required == 1 ? '是否必填：是、' : '是否必填：否、';
							noteHtml += procVarName ? '流程变量名称：' + procVarName
									+ '、' : '';
							noteHtml += ')</span>';
							var html = '<div>' + label + '<br/>' + inputHtml
									+ '&nbsp;&nbsp;&nbsp;&nbsp;' + noteHtml
									+ '</div>';
							$('#' + targetId).append(html);
						});
	};

	// 生成任务办理表单
	// taskActionList：任务动作集合（表单提交按钮）, formId:表单Id,
	// targetId::填充目标容器id,appPath：系统路径
	this.genTaskDealForm = function(taskActionList, formId, targetId, appPath) {
		this.taskActionList = taskActionList;
		var htmlStr = '';
		// 校验对象
		var checkRule = {}, checkMsg = {};
		// 生成表单字段
		$
				.each(
						this.formFieldList,
						function(index, formField) {
							var fieldId = formField.fieldId;
							var label = formField.label;
							var inputType = formField.inputType;
							var dataType = formField.dataType;
							var required = formField.required;
							var max = formField.max;
							var min = formField.min;
							var fieldItemList = formField.fieldItemList;

							var inputName = "field__" + fieldId;// 字段name生成规则
							var inputHtml = '';// 控件html
							if (inputType == 1) {// 文本框
								inputHtml = '<input type="text" name="'
										+ inputName
										+ '" class="genInputText"/>';
								if (dataType == '4') {// 日期控件
									inputHtml = '<input type="text" name="'
											+ inputName
											+ '" class="genInputText" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'});" />';
								}
							} else if (inputType == 2) {// 文本域
								inputHtml = '<textarea rows="5" cols="30" name="'
										+ inputName
										+ '" class="genInput"></textarea>';
							} else if (inputType == 3) {// 单选
								$.each(fieldItemList, function(itemIndex,
										fieldItem) {
									inputHtml += '<input type="radio" name="'
											+ inputName + '" value="'
											+ fieldItem.value + '" />'
											+ fieldItem.label + '&nbsp;&nbsp;';
								});
							} else if (inputType == 4) {// 多选
								$
										.each(
												fieldItemList,
												function(itemIndex, fieldItem) {
													inputHtml += '<input type="checkbox" name="'
															+ inputName
															+ '" value="'
															+ fieldItem.value
															+ '" />'
															+ fieldItem.label
															+ '&nbsp;&nbsp;';
												});
							} else if (inputType == 5) {// 下拉
								inputHtml = '<select class="genInput" class="genSelect" name="'
										+ inputName + '">';
								$.each(fieldItemList, function(itemIndex,
										fieldItem) {
									inputHtml += '<option value="'
											+ fieldItem.value + '">'
											+ fieldItem.label + '</option>';
								});
								inputHtml += '</select>';
							} else if (inputType == 6) {// 上传组件
								inputHtml = '<input type="file" name="'
										+ inputName
										+ '" class="genInputText" size="33" width="200px;"/>';
							}
							htmlStr += '<p align="left"><label>' + label
									+ '</label>' + inputHtml + '</p>';
							// 构造校验对象>>>
							var fieldRule = checkRule[inputName] = {}, fieldMsg = checkMsg[inputName] = {};
							// inputType dataType required max min
							if (required == '1') {
								fieldRule.required = true;
								fieldMsg.required = label + '不能空！';
							}
							if (inputType == '1' && dataType == '2') {// 整数
								fieldRule.digits = true;
								fieldMsg.digits = label + '只能是整数！';
							}
							if (inputType == '1' && dataType == '3') {// 数字
								fieldRule.appNumber = true;
								fieldMsg.appNumber = label + '只能是数字！';
							}
							if (inputType == '1'
									&& (dataType == '2' || dataType == '3')) {// 最大值最小值校验
								if (min) {
									fieldRule.min = min;
									fieldMsg.min = label + '不能小于' + min;
								}
								if (max) {
									fieldRule.max = max;
									fieldMsg.max = label + '不能大于' + max;
								}
							}
							// <<<构造校验对象
						});

		// 生成表单提交按钮
		htmlStr += '<p align="center">';
		$.each(this.taskActionList, function(index, taskAction) {
			var buttonId = "button__" + taskAction.actionId;
			var buttonName = buttonId;
			var assignTarget = taskAction.assignTarget;
			if (!assignTarget || assignTarget == '') {
				assignTarget = -1;// 无需选择机构
			}
			var buttonHtml = '<button id="' + buttonId + '" name="'
					+ buttonName
					+ '" class="genButton" onclick="submitForm__(\'' + formId
					+ '\',\'' + taskAction.actionId + '\',\'' + assignTarget
					+ '\')">' + taskAction.actionName + '</button>';

			htmlStr += buttonHtml + '&nbsp;&nbsp;&nbsp;&nbsp;';

		});
		htmlStr += '</p>';
		// 生成选择任务提交目标
		var assignTargetHtml = '<div id="assignTargetC" title="选择任务的提交目标"></div>';
		htmlStr += assignTargetHtml;
		$('#' + targetId).append(htmlStr);

		// 绑定校验checkRule和checkMsg
		window.validator = jqueryUtil
				.formValidate({
					form : formId,
					rules : checkRule,
					messages : checkMsg,
					submitHandler : function(form_) {
						if (window.MYFORMDATA.assignTarget == -1) {// 无需选择机构
							// alert('submit');
							// 提交表单,不再触发校验
							form_.submit();
						} else {
							// 生成任务提交目标html
							$('#assignTargetC')
									.html(
											'<img src="'
													+ appPath
													+ '/resources/images/loadinfo_net.gif">');
							$
									.getJSON(
											appPath
													+ '/workflow/task/getAssignTargetList?orgType='
													+ window.MYFORMDATA.assignTarget,
											function(orgList) {
												// 填充assignTargetC
												var html = '提交机构：<select id="assignTargetH" name="assignTargetH"><option value=""></option>';
												$
														.each(
																orgList,
																function(i, org) {
																	html += '<option value="'
																			+ org.orgCode
																			+ '">'
																			+ org.orgName
																			+ '</option>';
																});
												html += "</select>";
												$('#assignTargetC').html(html);
											});
							$('#assignTargetC')
									.dialog(
											{
												modal : true,
												buttons : {
													'确认提交' : function() {
														var assignTarget = $(
																'#assignTargetH')
																.val();
														if (assignTarget == '') {
															alert('请选择提交机构！');
															return false;
														} else {
															$('#assignTarget')
																	.val(
																			assignTarget);
															$(this).dialog(
																	'close');
															// alert('submit');
															// 提交表单,不再触发校验
															form_.submit();
														}
													},
													'取消' : function() {
														$(this).dialog('close');
													}
												}
											});
						}
					}
				});

	};
	/**
	 * 生成表单值（表单实例）信息 文件下载
	 * fieldValueList：字段值集合[{"fieldId":61,"fieldValue":"谁说的"},{...},...]
	 * targetId：填充目标容器Id
	 */
	this.genFormInst = function(fieldValueList, targetId, taskId) {
		// 以map形式存储。key：'field__'+ffieldId，value：{"fieldId":61,"fieldValue":"谁说的"}
		var fieldValueListMap = {};
		$.each(fieldValueList, function(index, fieldValObj) {
			fieldValueListMap['field__' + fieldValObj.fieldId] = fieldValObj;
		});
		var htmlStr = '<table class="blues" style="margin: 10px;width:96%;">';
		$
				.each(
						this.formFieldList,
						function(index, formField) {
							var fieldId = formField.fieldId;
							var label = formField.label + '：';
							var inputType = formField.inputType;
							var fieldValObj = fieldValueListMap['field__'
									+ fieldId];
							var fieldValue = '无';
							if (fieldValObj && fieldValObj.fieldValue
									&& fieldValObj.fieldValue != '') {
								fieldValue = fieldValObj.fieldValue;
								if (inputType == 6) {// file类型
									var fieldValueT = fieldValue;
									$.ajax({
										url : top.APP_PATH+'/download/fieldFileIsExist?taskId='
												+ taskId + "&fieldId=" + fieldId,
										async: false,
										success : function(data) {
											if(data){					
												fieldValue = [															
																fieldValue,
																'<a style="height: 32px;padding-top:10px;" href="',
																top.APP_PATH,
																'/download/fieldFile?taskId=',
																taskId, '&fieldId=', fieldId + '"',
																' title="下载"> 下载</a>' ]
																.join("");
												var ttt = fieldValue.split(".");
												ttt = ttt[ttt.length - 1].toUpperCase();
												if (ttt.indexOf("TXT") > -1
														|| ttt.indexOf("DOC") > -1
														|| ttt.indexOf("XLS") > -1
														|| ttt.indexOf("PPT") > -1
														|| ttt.indexOf("PDF") > -1) {
													fieldValue += [
															'<a style="margin-left: 4px;" href="javascript:void(0);" onclick="fieldInsdocPreview(\'',
															taskId
																	+ '\',\''
																	+ fieldId
																	+ '\',\''
																	+ fieldValueT
																	+ '\');"  title="在线查看">',
															' 预览',
															'</a>' ].join("");
												}
											}else{												
												fieldValue = [															
																fieldValue,
																'<font color="red"> (附件丢失！)</font>' ]
																.join("");
											}
										}
									});
									//fieldValue += '</div>';
								}
							}
							htmlStr += '<tr><td class="tabRight" style="width: 135px;">'
									+ label
									+ '</td><td style="text-align: left;">'
									+ fieldValue + '</td></tr>';
						});
		htmlStr += '</table>';
		$('#' + targetId).append(htmlStr);
	};
};

// 附件预览
function fieldInsdocPreview(taskInstId, fieldId, fieldValue) {
	$.ajax({
		url : top.APP_PATH + '/flexPaper/fieldInstanceCheckSwfPath?taskInstId='
				+ taskInstId + "&fieldId=" + fieldId,
		type : 'post',
		cache : false,
		success : function(data) {
			if (data == 1) {
				alert('系统正在生成在线文件预览，请稍后再访问！');
				return;
			} else {
				art.dialog.open(top.APP_PATH
						+ "/flexPaper/fieldInstance?taskInstId=" + taskInstId
						+ "&fieldId=" + fieldId, {
					id : "fieldInsdocPreview",
					title : fieldValue,
					resize : false,
					width : $(top.window).width()*0.7,
					height : $(top.window).height()*0.7,
					opacity : 0.1
				// 透明度
				}, false);
			}
		}
	});

}

// 表单提交验证
function submitForm__(formId, actionId, assignTarget) {
	// 设置提交动作
	$('#actionId').val(actionId);
	// 保存变量assignTarget
	window.MYFORMDATA = {};
	window.MYFORMDATA.assignTarget = assignTarget;
	// 提交表单,触发校验
	$('#' + formId).submit();
}