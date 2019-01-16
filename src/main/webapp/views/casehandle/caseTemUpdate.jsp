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
<link rel="stylesheet" type="text/css" href="${path }/resources/script/accuseSelector/accuseSelector.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/popover/jquery.webui-popover.min.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/placeholder/placeholder.css"/>
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
<script type="text/javascript" src="${path }/resources/script/caseParty-min.js"></script>
<script type="text/javascript" src="${path }/resources/script/caseCompany-min.js"></script>
<script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/autoresize.jquery.min.js"></script>
<script src="${path }/resources/placeholder/placeholder.js"></script>
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
	/* var caseValidate,casePartValidate,caseCompanyValidate;
	//添加自定义表单验证规则(在param[1]容器内的name为param[0]的组件的值不能一样)
	if(jQuery.validator && jQuery.Poshytip){
		jQuery.validator.addMethod("isTheOneId",function(value,element,param){
			  return this.optional(element) ||isTheOneId(value,element,param);   
			}, "同一案件的当事人信息的身份证号不能相同！"); 
	}; */
	$(function(){
			//案件信息表单验证
				caseValidate=jqueryUtil.formValidate({
				form:"caseForm",
				rules:{
					/* 这里会到后台校验编号的唯一性 ,remote:'${path}/casehandle/case/checkPenaltyFileNo'*/
					caseNo:{required:true,maxlength:50},
					caseName:{required:true,maxlength:50},
					anfaTime:{required:true},
					anfaAddressName:{required:true},
					undertaker:{required:true},
					undertakerTime:{required:false},
					recordSrc:{required:true},
					caseDetailName:{required:true},
					//caseReason:{required:true,maxlength:200},
					//amountInvolved:{required:true,appNumber:[12,2]},
					undertakerSuggest:{required:true,maxlength:500}
				},
				messages:{
					caseNo:{required:"案件编号不能为空!",maxlength:'请最多输入25位汉字!',remote:'此编号已被使用，请填写其它编号!'},
					caseName:{required:"案件名称不能为空!",maxlength:"请最多输入50位汉字!"},
					anfaTime:{required:"案发时间不能为空"},
					anfaAddressName:{required:"案发区域不能为空！"},
					undertaker:{required:"承办人不能为空!"},
					undertakerTime:{required:"承办时间不能为空!"},
					recordSrc:{required:"案件来源不能为空!"},
					caseDetailName:{required:"违法事实！"},
					//caseReason:{required:"案由不能为空！",maxlength:'请最多输入200位汉字'},
					//amountInvolved:{required:"涉案金额不能为空!",appNumber:"请输入数字(整数最多10位，小数最多2位)"},
					undertakerSuggest:{required:"承办人意见不能为空！",maxlength:'请最多输入500位汉字'}
					
				}/* ,submitHandler:function(form){
				}
				//这个地方的invalidHandler是为了调试用的，如果所有的表单都填写了，但是没法提交时，可以用这个来看看是否有一些隐藏域导致检验通不过
				,invalidHandler:function(event, validator){
					//第一次没有校验成功时，部分disabled隐藏输入域会失去disabled的属性，所以要在每一次检验失败时，都要再一次进行disabled属性初始化
					var errorList=validator.errorList;
					var invalidNames="";
					$.each(errorList,function(i,n){
						invalidNames=invalidNames+n.element.name+":"+n.message+";\n";
					});
				} */
			});//校验结束
		/* var msg = "${info}";
		if(msg != null && msg != ""){
			art.dialog.tips(msg,2);
		} */
		//autoResize.js自动扩展textarea大小
		$('#caseDetailName').autoResize({
			limit:500
		});
		
		initTimePlugin();
		
	});
	function initTimePlugin(){
		//日期插件
		var undertakerTime  = document.getElementById('undertakerTime');
		undertakerTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		var anfaTime = document.getElementById('anfaTime');
		anfaTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		} 
	}
	function add(){
		$("#caseForm").submit();
	}
	
</script>
</head>
<body>
	<div class="panel">
	<form id="caseForm" method="post" action="${path }/caseTem/update">
		<fieldset class="fieldset">
			<legend class="legend">信息补充或修改</legend>
			<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;" id="chufaTable">
				<tr>
					<td width="21%"  class="tabRight">案件编号：</td>
					<td width="79%" style="text-align: left;" colspan="3">
						<input type="text" class="text ignore" id="caseNo" name="caseNo" style="width: 92.5%;"
						 value="${caseImport.caseNo }"/>
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td width="21%"  class="tabRight">案件名称：</td>
					<td width="79%" style="text-align: left;" colspan="3">
						<input type="text" class="text" id="caseName" name="caseName" style="width: 92.5%;"
						 value="${caseImport.caseName }"/>
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
					<td width="21%"  class="tabRight" >案件来源：</td>
					<td width="29%" style="text-align: left;" colspan="3">
						<dic:getDictionary var="caseSourceList" groupCode="caseSource"/>
						<select name="recordSrc" style="width: 92.5%;">
							<c:forEach items="${caseSourceList}" var="domain">
								<option value="${domain.dtCode}" 
								<c:if test="${caseImport.recordSrc==domain.dtCode }">selected="selected"</c:if>>${domain.dtName}</option>
							</c:forEach>
						</select>
						&nbsp; <font color="red">*</font> 
					</td>					
				</tr>
				<tr>
					<td width="21%"  class="tabRight" >案发区域：</td>
					<td width="29%" style="text-align: left;">
						<input type="text" id="anfaAddressName" name="anfaAddressName" class="text" onfocus="showAnfaAddress();" 
						readonly="readonly" value="${caseImport.anfaAddressName }"/>&nbsp; <font color="red">*</font> 
						<input type="hidden" id="anfaAddress" name="anfaAddress" value="${caseImport.anfaAddress }"/>
					</td>
					<td width="21%"  class="tabRight">案发时间：</td>
					<td width="29%" style="text-align: left;">
						<input type="text" class="text" id="anfaTime" name="anfaTime" 
						 value="<fmt:formatDate value='${caseImport.anfaTime }' pattern='yyyy-MM-dd' />" readonly="readonly"/>
						 
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td width="21%"  class="tabRight" >违法事实：</td>
					<td width="79%" style="text-align: left" colspan="3">
						<textarea rows="5" id="caseDetailName" name="caseDetailName" >${caseImport.caseDetailName }</textarea>
						&nbsp;<font color="red">*</font>
					</td>
				</tr>				
				<tr>
					<td width="21%"  class="tabRight" >承办人：</td>
					<td width="29%" style="text-align: left;">
					<input type="text" id="undertaker" name="undertaker" class="text" value="${caseImport.undertaker }"/>&nbsp; <font color="red">*</font> 
					</td>
					<td width="21%"  class="tabRight" >承办时间：</td>
					<td width="29%" style="text-align: left;" >
					<input type="text" class="text" id="undertakerTime" name="undertakerTime" 
					value="<fmt:formatDate value='${caseImport.undertakerTime }' pattern='yyyy-MM-dd' />" readonly="readonly"/>
					&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td width="21%"  class="tabRight" >承办意见：</td>
					<td width="79%" style="text-align: left" colspan="3">
					<textarea rows="3" id="undertakerSuggest" name="undertakerSuggest" >${caseImport.undertakerSuggest }</textarea>
					&nbsp;<font color="red">*</font>													  
				</tr>
				<tr>
					<td width="21%"  class="tabRight" >当事人信息：</td>
					<td width="29%" style="text-align: left;">
						<input type="text" id="casePartyJson" name="casePartyJson" class="text"  
						 value="${caseImport.casePartyJson }"/>
					</td>
					<td width="21%"  class="tabRight" >企业信息：</td>
					<td width="29%" style="text-align: left;">
						<input type="text" id="caseCompanyJson" name="caseCompanyJson" class="text"  
						 value="${caseImport.caseCompanyJson }"/>
					</td>
					</td>
				</tr>
			</table>
			<input  type="hidden" value="${caseImport.importId }" name="importId"/>
			</fieldset>
		</form>
	<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="保 存" onclick="add('add')" />
	<input type="button" value="返  回" class="btn_small" onclick="window.location.href='${path}/caseTem/main'"/>
</div>
<jsp:include page="/views/tree/anfa_address_tree.jsp"/>	
</body>
</html>