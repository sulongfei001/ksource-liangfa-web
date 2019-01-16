<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"
	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"
	type="text/javascript"></script>
<script
	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript">
	$(function() {
		/* <c:if test="${info !=null}">
			art.dialog.tips("${info}",2);
		</c:if> */
		var info = "${info}";
		if(info != null && info != ""){
			if(info == 'update'){
				$.ligerDialog.success('案件筛选配置修改成功！');
			}else{
				$.ligerDialog.error('案件筛选配置修改失败！');
			}
		}
	
		var minCaseInputTime = document.getElementById('minCaseInputTime');
		var maxCaseInputTime = document.getElementById('maxCaseInputTime');
		minCaseInputTime.onclick = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'maxCaseInputTime\',{d:-1});}'});
		}
		maxCaseInputTime.onclick = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'minCaseInputTime\',{d:1});}'});
		}
		
		jqueryUtil.formValidate({
			form : "caseFilterAddForm", 
			rules : {
				minAmountInvolved:{appNumber:[10,2]},
				maxAmountInvolved:{appNumber:[10,2]},
				chufaTimes:{digits:true,maxlength:5}
				/*
				minAmountInvolved:{required:true,appNumber:[10,2]},
				maxAmountInvolved:{required:true,appNumber:[10,2]},
				minCaseInputTime:{required:true},
				maxCaseInputTime:{required:true},
				isSeriousCase:{required:true},
				isDiscussCase:{required:true},
				chufaTimes:{required:true,appNumber:[5]},
				isBeyondEighty:{required:true}
				*/
			},
			messages : {
				minAmountInvolved : {
					appNumber : "请输入数字(整数最多10位，小数最多2位！)"
				},
				maxAmountInvolved : {
					appNumber : "请输入数字(整数最多10位，小数最多2位！)"
				},
				chufaTimes : {
					digits : "请输入整数",
					maxlength: "最多5位"
				}
				/*
				minCaseInputTime : {
					required : "案件录入时间 开始时间不能为空！"
				},
				maxCaseInputTime : {
					required : "案件录入时间 结束时间不能为空！"
				},
				isSeriousCase : {
					required : "案情是否严重不能为空！"
				},
				isDiscussCase : {
					required : "案情是否经过集体讨论不能为空！"
				},
				chufaTimes : {
					required : "行政处罚次数不能为空！"
				},
				isBeyondEighty : {
					required : "涉案金额达是否达到刑事立案标准80%不能为空！"
				}
				*/
			},
			submitHandler : function(form) {
				form.submit();
			}
		});
		
		
		$("#clean_btn").click(function(){
			$("[type='text']").val("");
			$("[type='radio']:checked").attr("checked",false);
		});
	});
	
</script>
</head>
<body>
	<!-- 新增 案件筛选条件 信息-->
	<div class="panel" >
		<fieldset class="fieldset" >
			<legend class="legend">案件筛选条件配置</legend>
			
			<form id="caseFilterAddForm" action="${path }/system/caseFilter/add"
				method="post">
				<input type="hidden" id="filterId" name="filterId" value="${caseFilter.filterId}" style="width: 110px"/>
				<table id="caseFilterAddTable" class="table_add"  width="90%">
					<tr>
						<td class="tabRight" width="20%">案件录入时间</td>
						<td style="text-align: left" width="30%">
						<input type="text" class="text" name="minCaseInputTime" id="minCaseInputTime" value="<fmt:formatDate value='${caseFilter.minCaseInputTime }' pattern='yyyy-MM-dd' />" readonly="readonly" style="width: 110px" /> 至 
						<input type="text" class="text" name="maxCaseInputTime" id="maxCaseInputTime" value="<fmt:formatDate value='${caseFilter.maxCaseInputTime }' pattern='yyyy-MM-dd' />" readonly="readonly" style="width: 110px"/>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">涉案金额</td>
						<td style="text-align: left" width="30%">
						<input type="text" class="text" name="minAmountInvolved" value="<fmt:formatNumber value="${caseFilter.minAmountInvolved}" pattern="###.##"/>" style="width: 110px"/> 至 
						<input type="text" class="text" name="maxAmountInvolved" value="<fmt:formatNumber value="${caseFilter.maxAmountInvolved}" pattern="###.##"/>" style="width: 110px" /> 元 
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">情节是否严重</td>
						<td style="text-align: left" width="30%">
							<span style="margin-right: 80px;">
							<input type="radio" id="isSeriousCase1" name="isSeriousCase" <c:if test='${caseFilter.isSeriousCase == 1 }'>checked='checked'</c:if> value="1"/>
							<label for="isSeriousCase1">是</label>
							</span>
							<span>
							<input type="radio" id="isSeriousCase0" name="isSeriousCase" <c:if test='${caseFilter.isSeriousCase == 0 }'>checked='checked'</c:if> value="0"/>
							<label for="isSeriousCase0">否</label>
							</span>
						</td>
					</tr>
					<tr>	
						<td class="tabRight">是否经过集体讨论</td>
						<td style="text-align: left">
							<span style="margin-right: 80px;">
							<input type="radio" id="isDiscussCase1" name="isDiscussCase" <c:if test='${caseFilter.isDiscussCase == 1 }'>checked='checked'</c:if> value="1"/>
							<label for="isDiscussCase1">是</label>
							</span>
							<span>
							<input type="radio" id="isDiscussCase0" name="isDiscussCase" <c:if test='${caseFilter.isDiscussCase == 0 }'>checked='checked'</c:if> value="0"/>
							<label for="isDiscussCase0">否</label>
							</span>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">涉案金额达是否达到刑事立案标准80%</td>
						<td style="text-align: left" width="30%">
							<span style="margin-right: 80px;">
							<input type="radio" id="isBeyondEighty1" name="isBeyondEighty" <c:if test='${caseFilter.isBeyondEighty == 1 }'>checked='checked'</c:if> value="1"/>
							<label for="isBeyondEighty1">是</label>
							</span>
							<span>
							<input type="radio" id="isBeyondEighty0" name="isBeyondEighty" <c:if test='${caseFilter.isBeyondEighty == 0 }'>checked='checked'</c:if> value="0"/>
							<label for="isBeyondEighty0">否</label>
							</span>
						</td>
					</tr>
					<tr>	
						<td class="tabRight">行政处罚次数(大于)</td>
						<td style="text-align: left">
						<input type="text" class="text" name="chufaTimes" value="${caseFilter.chufaTimes}" style="width: 240px"/>
						</td>
					</tr>
					<tr>	
						<td class="tabRight">是否低于行政处罚规定的下限金额</td>
						<td style="text-align: left">
							<span style="margin-right: 80px;">
							<input type="radio" id="isLowerLimitMoney1" name="isLowerLimitMoney" <c:if test='${caseFilter.isLowerLimitMoney == 1 }'>checked='checked'</c:if> value="1"/>
							<label for="isLowerLimitMoney1">是</label>
							</span>
							<span>
							<input type="radio" id="isLowerLimitMoney0" name="isLowerLimitMoney" <c:if test='${caseFilter.isLowerLimitMoney == 0 }'>checked='checked'</c:if> value="0"/>
							<label for="isLowerLimitMoney0">否</label>
							</span>
						</td>
					</tr>
					<tr>	
						<td class="tabRight">是否进行鉴定</td>
						<td style="text-align: left">
							<span style="margin-right: 80px;">
							<input type="radio" id="isIdentify1" name="isIdentify" <c:if test='${caseFilter.isIdentify == 1 }'>checked='checked'</c:if> value="1"/>
							<label for="isIdentify1">是</label>
							</span>
							<span>
							<input type="radio" id="isIdentify0" name="isIdentify" <c:if test='${caseFilter.isIdentify == 0 }'>checked='checked'</c:if> value="0"/>
							<label for="isIdentify0">否</label>
							</span>
						</td>
					</tr>
				</table>
				<div style="margin-left: 37%; margin-top: 5px">
					<input type="submit" class="btn_small" value="保 存" />
					<input id="clean_btn" type="button" class="btn_small" value="清 空" /> 
				</div>
			</form>
			<ul>
		    	<li>注意事项：</li>
				<li>满足任何一项条件即可出现在降格处理案件筛查的案件里面。</li>
    		</ul>
		</fieldset>
	</div>
</body>
</html>