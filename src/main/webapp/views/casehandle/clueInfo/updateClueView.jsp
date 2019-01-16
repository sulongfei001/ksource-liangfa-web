<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/popover/jquery.webui-popover.min.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/lib/jquery.metadata.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"></script>
<script type="text/javascript" src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script type="text/javascript" src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<%-- <script type="text/javascript" src="${path }/resources/script/caseParty-min.js"></script>
<script type="text/javascript" src="${path }/resources/script/caseCompany-min.js"></script>
<script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>--%><script type="text/javascript" src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/autoresize.jquery.min.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<style type="text/css">
input.tdcchaxun {
	background-color: #2186E3;
	border: 1px solid #2186E3;
	color: #fff;
	width: 80px;
	height: 28px;
	font-size: 14px;
	font-weight: bold;
	border-radius: 3px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
}
</style>
<script type="text/javascript">
	var caseValidate;
	$(function(){
			//案件信息表单验证
				caseValidate=jqueryUtil.formValidate({
				form:"clueForm",
				rules:{
					/* 这里会到后台校验编号的唯一性 ,remote:'${path}/casehandle/case/checkPenaltyFileNo'*/
					clueNo:{required:true,maxlength:20},
					clueResource:{required:true},
					addressName:{required:true},
					content:{required:true}
				},
				messages:{
					clueNo:{required:"线索名称不能为空!",maxlength:'请最多输入20位汉字!'},
					clueResource:{required:"线索不能为空!"},
					addressName:{required:"违法行为发生地不能为空！"},
					content:{required:"线索内容不能为空！"}
				},submitHandler:function(form){
				      //如果放开invalidHandler，检验不通过进也会执行submitHandler，所以在这个地方再过滤一下
				      if($("#clueForm").valid()){
				    	  form.submit();
					      //提交按钮禁用
					      $("#saveButton").attr("disabled",true);
				      }
				      return false;
				}
			});//校验结束
		
		//autoResize.js自动扩展textarea大小
		$('#content').autoResize({
			limit:500
		});
		
		
		/* 信息保存后的弹框提示 */
		var info = "${info}";
		if(info != null && info != ""){
			if(info){
				//正确提示
				$.ligerDialog.success('线索修改成功',"",function(){window.location.href="${path}/casehandle/clueInfo/getInputClueList"});
			}else{
				//失败提示
				$.ligerDialog.error('线索修改失败',"",function(){window.location.href="${path}/casehandle/clueInfo/getInputClueList"});
			}
		}
	});
	

	
	function add(){
		$("#clueForm").submit();
	}
	
	function back(){
		window.History.back();
	}
</script>
</head>
<body>
	<div class="panel">
	<form id="clueForm" method="post" action="${path }/casehandle/clueInfo/saveUpdateClueInfo">
		<fieldset class="fieldset">
			<legend class="legend">线索修改
			</legend>
			<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;" id="clueInfoTable">
				<tr>
					<td width="21%"  class="tabRight">线索名称：</td>
					<td width="79%" style="text-align: left;" colspan="3">
						<input type="text" class="text ignore" id="clueNo" name="clueNo" 
						style="width: 91%;" value="${clueInfo.clueNo }"/>
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td width="21%"  class="tabRight" >线索来源：</td>
					<td width="79%" style="text-align: left;" colspan="3">
						<select name="clueResource" style="width: 91%;">
							<c:forEach items="${clueResourceList}" var="domain">
								<option value="${domain.dtCode}" <c:if test="${clueInfo.clueState == domain.dtCode }">selected=selected</c:if>>
								${domain.dtName}</option>
							</c:forEach>
						</select>
						&nbsp; <font color="red">*</font> 
					</td>	
				</tr>
				<!-- 线索发生地 -->	
				<tr>
					<td width="21%"  class="tabRight" >违法行为发生地：</td>
					<td width="79%" style="text-align: left;" colspan="3">
						<input type="text" id="addressName" name="addressName" class="text" onfocus="showClueAddress();" 
						readonly="readonly" value="${clueInfo.addressName }"/>&nbsp; <font color="red">*</font> 
						<input type="hidden" id="address" name="address" value="${clueInfo.address }"/>
					</td>		
				</tr>	
				<tr>
					<td width="21%"  class="tabRight" >线索内容：</td>
					<td width="79%" style="text-align: left" colspan="3">
						<textarea rows="5" id="content" name="content" style="width: 91%;">${clueInfo.content }</textarea>
						&nbsp;<font color="red">*</font>
					</td>
				</tr>				
			</table>
		<br/>
			<br/>
		</fieldset>
		<input type="hidden" name="clueId" value="${clueInfo.clueId }"/>
		</form>
		
	<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="保 存" onclick="add()" />
	<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="返回" onclick="back()" />
</div>
<jsp:include page="/views/tree/clue_address_tree.jsp"/>	
</body>
</html>