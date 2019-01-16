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
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link href="${path }/resources/script/accuseSelector/accuseSelector.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/popover/jquery.webui-popover.min.css"/>
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"
	type="text/javascript"></script>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/lib/jquery.metadata.js"
	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"
	type="text/javascript"></script>
<script
	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/caseParty-min.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/caseCompany-min.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/autoresize.jquery.min.js"></script>
<%-- <script src="${path }/resources/script/accuseSelector/accuseSelector.js"></script> --%>
<script src="${path }/resources/placeholder/placeholder.js"></script>
<link rel="stylesheet" type="text/css" href="${path }/resources/placeholder/placeholder.css"/>
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
	var caseValidate,casePartValidate,caseCompanyValidate;
	//添加自定义表单验证规则(在param[1]容器内的name为param[0]的组件的值不能一样)
	if(jQuery.validator && jQuery.Poshytip){
		jQuery.validator.addMethod("isTheOneId",function(value,element,param){
			  return this.optional(element) ||isTheOneId(value,element,param);   
			}, "同一案件的当事人信息的身份证号不能相同！"); 
	};
	$(function(){
				//案件信息表单验证
				caseValidate=jqueryUtil.formValidate({
				form:"caseForm",
				rules:{
					/* 这里会到后台校验编号的唯一性 ,remote:'${path}/casehandle/case/checkPenaltyFileNo'*/
					lianNo:{required:true,maxlength:100},
					undertaker:{required:true,maxlength:10},
					undertakeTime:{required:true,maxlength:50},
					lianFile1:{required:true,maxlength:40},
					isMeasures:{required:true},
					memo:{required:true},
					caseDetail:{required:true,maxlength:1800},
					leaderSuggest:{required:true,maxlength:400},
					measuresFile1:{required:true,maxlength:40},
					amountInvolved:{required:true,appNumber:[10,2]}
				},
				messages:{
					lianNo:{required:"立案号不能为空!",maxlength:'请最多输入50位汉字或字母!'},
					undertaker:{required:"承办人不能为空!",maxlength:'请最多输入10位汉字或字母!'},
					undertakeTime:{required:"立案时间不能为空!"},
					lianFile1:{required:"立案决定书不能为空！",maxlength:'附件名称最多不能超过40位汉字或字母!'},
					anfaTime:{required:"案发时间不能为空!"},	
					isMeasures:{required:"承办人不能为空!"},
					memo:{required:"案由不能为空！"},
					caseDetail:{required:"案件详情不能为空!",maxlength:'请最多输入1800位汉字或字母!'},
					leaderSuggest:{required:"领导意见不能为空!",maxlength:'请最多输入400位汉字或字母!'},
					measuresFile1:{required:"强制措施材料不能为空!",maxlength:'附件名称最多不能超过40位汉字或字母!'},
					amountInvolved:{required:"涉案金额不能为空!",appNumber:"请输入数字(整数最多10位，小数最多2位)"}
				},submitHandler:function(form){
					var casePartyArray=new Array();	
					var caseCompanyArray=new Array();
					$("#addCasePartyDiv table").each(function(i,n){
						var tmpSeriaJson=$(n).contents().find(":input").serializeArray();
						var tmp=new Object();
						$.each(tmpSeriaJson,function(i,n){
							tmp[n.name]=n.value;
						});
						casePartyArray.push(tmp);
					});
					$("#addCaseCompanyDiv table").each(function(i,n){
						var tmpSeriaJson=$(n).contents().find(":input").serializeArray();
						var tmp=new Object();
						$.each(tmpSeriaJson,function(i,n){
							tmp[n.name]=n.value;
						});
						caseCompanyArray.push(tmp);
					});
					var casePartyJson=$.toJSON(casePartyArray);
					 $('#casePartyJson').val(casePartyJson);
					 var caseCompanyJson=$.toJSON(caseCompanyArray);
					 $('#caseCompanyJson').val(caseCompanyJson);
					  //验证上传组件
					 /* var isError =false;
					  $('input[type="file"]').each(
							  function(){
							 	 var temp = $(this).val().split("\\");
								   var fileName=temp[temp.length-1];
								   var fileNameLength=fileName.lastIndexOf('.');//截取.之前的字符串长度
								  if(fileNameLength>46){
									   isError=true;
									   jqueryUtil.errorPlacement($('<label class="error" generated="true">文件名太长,必须小于50字符,请修改后再上传!</label>'),$(this));
									   $(this).focus();
								   }else{
									   jqueryUtil.success($(this),null);
								   }
								   
							  });
					  if(isError){
						  return false;
					  } */
				      //提交表单
				      //如果放开invalidHandler，检验不通过进也会执行submitHandler，所以在这个地方再过滤一下
				      if($("#caseForm").valid()){
				    	  form.submit();
					      //提交按钮禁用
					      $("#saveButton").attr("disabled",true);
				      }
				      return false;
				}
				//这个地方的invalidHandler是为了调试用的，如果所有的表单都填写了，但是没法提交时，可以用这个来看看是否有一些隐藏域导致检验通不过
				,invalidHandler:function(event, validator){
					//第一次没有校验成功时，部分disabled隐藏输入域会失去disabled的属性，所以要在每一次检验失败时，都要再一次进行disabled属性初始化
					var errorList=validator.errorList;
					var invalidNames="";
					$.each(errorList,function(i,n){
						invalidNames=invalidNames+n.element.name+":"+n.message+";\n";
					});
					//alert(invalidNames);
					//$("#caseDetailName").text(invalidNames);
				}
			});//校验结束
		var msg = "${info}";
		if(msg != null && msg != ""){
			art.dialog.tips(msg,2);
		}
		//autoResize.js自动扩展textarea大小
		$('#caseDetail').autoResize({
			limit:500
		});
		$('#leaderSuggest').autoResize({
			limit:500
		});
		$('#memo').autoResize({
			limit:500
		});
		initTimePlugin();
		
		$("[id='caseCompanyTable'] td:even").css("width","9%");
		$("[id='caseCompanyTable'] td:odd").css("width","24.3%");
		$("[id='casePartyTable'] td:even").css("width","9%");
		$("[id='casePartyTable'] td:odd").css("width","24.3%");
		
		
	});
	
	function initTimePlugin(){
		//日期插件
		var undertakeTime = document.getElementById('undertakeTime');
		undertakeTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		var undertakeTime1 = document.getElementById('undertakeTime1');
		undertakeTime1.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		/* $("input[name='registractionTime']").each(function(index,obj){
			$(obj).onfocus(function(){
				WdatePicker({dateFmt:'yyyy-MM-dd'});
			})
		}) */
		 var registractionTime = document.getElementById('registractionTime');
		registractionTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}  
		
	}
	
	//判断在同一个案件中当事人的身份证号是否唯一
	function isTheOneId(value,element,param){
		//如果有相同的返回false
		var isTheOneId =true;
		var idArray=new Array();
		jQuery("#"+param[1]).contents().find("[name='"+param[0]+"']:visible").each(function(i,n){
			var tmpVal=$(n).val();
			for(x in idArray){
				if(idArray[x]==tmpVal){
					isTheOneId=false;
					return;
				}
			}
			if(isTheOneId==false){
				return false;
			}else{
				idArray.push(tmpVal);
			}
		});
		return isTheOneId;
	}
	function add(view){
		var casePartyValidate=needValidate("casePartyTable");
		var caseCompanyValidate=needValidate("caseCompanyTable");
		
		if(casePartyValidate){
			$("#addCasePartyDiv").contents().find("[name='idsNo']:visible").rules('add',partyTableValidate.idsNo);
			$("#addCasePartyDiv").contents().find("[name='name']:visible").rules('add',partyTableValidate.name);
			$("#addCasePartyDiv").contents().find("[name='tel']:visible").rules('add',partyTableValidate.tel);
			$("#addCasePartyDiv").contents().find("[name='birthplace']:visible").rules('add',partyTableValidate.birthplace);
			$("#addCasePartyDiv").contents().find("[name='profession']:visible").rules('add',partyTableValidate.profession);
			$("#addCasePartyDiv").contents().find("[name='nation']:visible").rules('add',partyTableValidate.nation);
			$("#addCasePartyDiv").contents().find("[name='education']:visible").rules('add',partyTableValidate.education);
			$("#addCasePartyDiv").contents().find("[name='sex']:visible").rules('add',partyTableValidate.sex);
			$("#addCasePartyDiv").contents().find("[name='residence']:visible").rules('add',partyTableValidate.residence);
			$("#addCasePartyDiv").contents().find("[name='specialIdentity']:visible").rules('add',partyTableValidate.specialIdentity);
			$("#addCasePartyDiv").contents().find("[name='address']:visible").rules('add',partyTableValidate.address);
		}
		if(caseCompanyValidate){
			$("#addCaseCompanyDiv").contents().find("[name='name']:visible").rules('add',companyTableValidate.name);
			$("#addCaseCompanyDiv").contents().find("[name='registractionNum']:visible").rules('add',companyTableValidate.registractionNum);
			$("#addCaseCompanyDiv").contents().find("[name='companyType']:visible").rules('add',companyTableValidate.companyType);
			$("#addCaseCompanyDiv").contents().find("[name='proxy']:visible").rules('add',companyTableValidate.proxy);
			$("#addCaseCompanyDiv").contents().find("[name='registractionCapital']:visible").rules('add',companyTableValidate.registractionCapital);
			$("#addCaseCompanyDiv").contents().find("[name='address']:visible").rules('add',companyTableValidate.address);
			$("#addCaseCompanyDiv").contents().find("[name='registractionTime']:visible").rules('add',companyTableValidate.registractionTime);
			$("#addCaseCompanyDiv").contents().find("[name='tel']:visible").rules('add',companyTableValidate.tel);
		}
		
		//var validResult=$("#caseForm").valid();
		$("#caseForm").submit();
		//表单提交完后，完毕当前的tab页，选中之前的待办tab页，并刷新
		/* top.removeTab("行政立案"); */
	}
	
	
	var partyTableValidate={
			name:{required:true,maxlength:50,messages:{required:"姓名不能为空!",maxlength:"请最多输入50位汉字!"}},
			idsNo:{required:true,isIDCard:true,isTheOneId:["idsNo","addCasePartyDiv"],messages:{required:"身份证号不能为空！",isIDCard:"请填写正确的身份证号码!"}},
			tel:{isTel:true,messages:{isTel:"请正确填写电话或手机号码!"}},
			birthplace:{maxlength:250,messages:{maxlength:"请最多输入250位汉字!"}},
			profession:{maxlength:250,messages:{maxlength:"请最多输入250位汉字!"}},
			nation:{maxlength:50,messages:{maxlength:"请最多输入50位汉字!"}},
			education:{maxlength:50,messages:{maxlength:"请最多输入50位汉字!"}},
			sex:{maxlength:50,messages:{maxlength:"请最多输入50位汉字!"}},
			residence:{maxlength:50,messages:{maxlength:"请最多输入50位汉字!"}},
			specialIdentity:{maxlength:50,messages:{maxlength:"请最多输入50位汉字!"}},
			address:{maxlength:250,messages:{maxlength:"请最多输入250位汉字!"}}
		};
		var companyTableValidate={
			name:{required:true,maxlength:50,messages:{required:"单位名称不能为空!",maxlength:"请最多输入50位汉字!"}},
			registractionNum:{required:true,maxlength:30,isTheOneId:["registractionNum","addCaseCompanyDiv"],messages:{required:"单位登记号不能为空!",maxlength:"请最多输入30位字符!",isTheOneId:"同一案件的单位的税务登记号不能相同"}},
			companyType:{required:true,messages:{required:"单位类型不能为空！"}},
			proxy:{required:true,maxlength:25,messages:{required:"负责人不能为空!",maxlength:"请最多输入25位汉字!"}},
			address:{maxlength:250,messages:{maxlength:"请最多输入250位汉字!"}},
			registractionCapital:{appNumber:[8,4],messages:{appNumber:"请输入数字(整数最多8位，小数最多4位)"}},
			registractionTime:{maxlength:50,messages:{maxlength:"请最多输入50位汉字!"}},
			tel:{isTel:true,messages:{isTel:"请正确填写电话或手机号码!"}}
		};
	//判断table是否需要校验，只要有一个input框有值，就需要校验
	function needValidate(tableId){
		var needValidate=false;
		$("table[id='"+tableId+"']:visible").each(function(i,n){
			var tableVal=false;
			$(n).contents().find("input,select").each(function(j,m){
				var tval=$(m).val();
				if(tval!=null&&tval!=''){
					tableVal=true;
					return false;
				}
			});
			if(tableVal==true){
				needValidate=true;
			}else{
				$(n).prev().andSelf().remove();
			}
		});
		return needValidate;
	}
	
	//添加嫌疑人table
	function addCasePartyInfo(){
		var emptyCasePartyTable=$("#emptyCasePartyTable").html();
		$("#addCasePartyDiv").append(emptyCasePartyTable);
		initTimePlugin();
	}
	//添加嫌疑人公司table
	function addCaseCompanyInfo(){
		var emptyCaseCompanyTable=$("#emptyCaseCompanyTable").html();
		$("#addCaseCompanyDiv").append(emptyCaseCompanyTable);
		initTimePlugin();
	}
	
	/* $(function(){
		//刷新时可出现TABLE显示错误的问题
		$("[name='chufaState']:checked").parent("label").click();
		$("[name='yisongState']:checked").change();
	}); */
	
	//将table_a后边的table以及table_a都删掉
	function deleteTable(table_a){
		top.art.dialog.confirm('确认删除吗？',function(){
			$(table_a).next().andSelf().remove();
		});
	}
	//行政立案
	function xingzhenglianChange(that){
		var value=that.value;
		if(that.value==3){
			$("#xingzhenglianTable").show();
			$("#xingzhenglianTable").attr("disabled",false);
			$("#buxingzhenglianTable").hide();
			$("#buxingzhenglianTable").attr("disabled",true);
			
		}else{
			$("#xingzhenglianTable").hide();
			$("#xingzhenglianTable").attr("disabled",true);
			$("#buxingzhenglianTable").show();
			$("#buxingzhenglianTable").attr("disabled",false);
		}
	}
	function measureChange(){
		if($("[name='isMeasures']:checked").val()==2){
			$("#measuresFile1_").show();
		}else{
			$("#measuresFile1_").hide();
		}
	}
	
</script>
<style type="text/css">

#outDiv {
	position: relative;
	margin: 6px;
}
#illegalSituationAddTable{margin:5px auto;border-color:#aaa}
#illegalSituationAddTable td{border-color:#aaa; line-height:18px;}
#illegalSituationAddTable .tabRight td{padding-top:6px; padding-bottom:4px;}
.btn{padding:5px 9px; border:1px solid #6b9bc9; background-color:#eff5fe; font-size:13px; color:#1d2f79!important}
.tabtr{font-size:13px; font-weight:bold;}
.aui_inner{background:#f5fafe!important}
.aui_buttons{background:#fff!important}
#lian_yes{background: green;}
#lian_no{background: red;}
</style>
</head>
<body>
	<div class="panel">
	<form id="caseForm" method="post" action="${path }/casehandle/caseTodo/lianAdd" enctype="multipart/form-data">
		<fieldset class="fieldset">
			<legend class="legend">行政立案
			</legend>
			<%-- <div style="width: 98%;margin-left: 10px;margin-bottom:-10px;text-align: right;"><a style="font-weight: bold;" href="${path}/download/caseAddExplain" >[填表说明]</a></div> --%>
		<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;" id="xingzhenglianTable">
			<tr>
				<td width="21%"  class="tabRight">立案号：</td>
				<td width="79%" style="text-align: left;" colspan="3">
				<input
					type="text" class="text ignore" id="lianNo" name="lianNo" style="width: 60%;"
					/>&nbsp;<font color="red">*</font></td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" >案由：</td>
				<td width="79%" style="text-align: left" colspan="3">
					<textarea rows="2" id="memo" name="memo" style="width: 60%;"></textarea>&nbsp;<font color="red">*</font>
				</td>
			</tr>			
			<tr>
				<td width="21%"  class="tabRight" >立案时间：</td>
				<td width="79%" style="text-align: left;" colspan="3"><input 
					type="text" class="text" id="undertakeTime" name="undertakeTime" style="width: 60%;" readonly="readonly"
					/>&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight">涉案金额：</td>
				<td width="29%" style="text-align: left;" colspan="3">
					<input type="text" id="amountInvolved" name="amountInvolved" class="text" style="width:60%"/><font color="red">（元）&nbsp;&nbsp;*无涉案金额，请填写0</font> 
				</td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" >承办人：</td>
				<td width="79%" style="text-align: left;" colspan="3">
				<input
					type="text" class="text ignore" id="undertaker" name="undertaker" style="width: 60%;"
					/>&nbsp;<font color="red">*</font></td>
			</tr>
			<tr style="border-top: none;">
				<td width="21%"  class="tabRight" >领导意见：</td>
				<td width="79%" style="text-align: left" colspan="3">
				<textarea rows="3" id="leaderSuggest" name="leaderSuggest" style="width: 60%;"></textarea>
				&nbsp;<font color="red">*</font>
				</td>
			</tr>			
			<tr>
				<td width="21%"  class="tabRight" >立案决定书：</td>
				<td width="79%" style="text-align: left;" colspan="3" id="lian_file">
					<input type="file" id="lianFile1" name="lianFile1"
					style="width: 60%" /> &nbsp;<font
					color="red">*文件大小限制在70M以内</font>
				</td>
			</tr>
			<tr>
				<!-- 是否采取行政强制措施(1-否，2-是) -->
				<td width="21%"  class="tabRight" >是否采取强制措施：</td>
				<td width="29%" style="text-align: left;" id="isMeasures_" colspan="3">
					<span style="margin-right: 80px;">
					<input type="radio" id="isMeasures_yes" name="isMeasures" 
					 value="2" onclick="measureChange();"/>
					<label for="isMeasures_yes">是</label>
					</span>
					<span>
					<input type="radio" id="isMeasures_no" name="isMeasures" 
					 value="1" checked onclick="measureChange();"/>
					<label for="isMeasures_no">否</label>
					</span>
					&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr style="display:none;" id="measuresFile1_">
				<td width="21%"  class="tabRight" >强制措施材料：</td>
				<td width="79%" style="text-align: left;" colspan="3">
					<input type="file" id="measuresFile1" name="measuresFile1" class="text" style="width: 60%" />
					&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" >案件详情：</td>
				<td width="79%" style="text-align: left" colspan="3">
				<textarea rows="5" id="caseDetail" name="caseDetail" style="width: 60%;"></textarea>
				&nbsp;<font color="red">*</font>
			</tr>
		</table>
		<input type="hidden" id="casePartyJson" name="casePartyJson" value="" />
		<input type="hidden" id="caseCompanyJson" name="caseCompanyJson" value="" />
		<input type="hidden" id="caseId" name="caseId" value="${caseBasic.caseId }" />
		<input type="hidden" id="markup" name="markup" value="${markup }" />
		<br/>
			<br/>
			<fieldset class="fieldset" id="casePartyFieldset" style="width: 98%;margin-left: 10px;">
				<legend class="legend">
					案件当事人信息<a href="javascript:void(0);" onclick="addCasePartyInfo();"
						style="color: blue">[添加]</a>
				</legend>
				<div id="addCasePartyDiv" >
					<c:forEach var="caseParty" items="${casePartyList }">
					<a href="javascript:void(0);" onclick="deleteTable(this);" style="margin-left: 88%;color: blue;">删除</a>
					<table id="casePartyTable" class="blues" style="width: 90%;margin-left: 6%;margin-top: 0px; margin-bottom: 10px;">
					<tr>
					<td class="tabRight">身份证号：</td>
					<td style="text-align: left">
					<input onblur="autoParseIdsNo(this)" type="text" class="text" name="idsNo" 
					maxlength="18" id="idsNoForAdd" value="${caseParty.idsNo }"/>
					&nbsp;<font color="red">*</font>
					<a onclick="callPeopleLib('${path }',this);" href="javascript:void(0);" title="调用人员信息库">调用</a>
					</td>
					<td class="tabRight">姓名：</td>
					<td style="text-align: left">
					<input type="text" class="text" name="name" value="${caseParty.name }"/>
					&nbsp;<font color="red">*</font>
					</td>
					<td class="tabRight">联系电话：</td>
					<td style="text-align: left"><input type="text" class="text" name="tel"
						title="请输入座机号(区号-座机号)或手机号" poshytip="" value="${caseParty.tel }"/>&nbsp;</td>
				</tr>
				<tr>
					<td class="tabRight">性别：</td>
					<td style="text-align: left"><dic:getDictionary var="sexList"
							groupCode="sex" /> <select class="select" name="sex" id="sexForAdd">
							<option value="">--请选择--</option>
							<c:forEach items="${sexList}" var="domain">
								<option value="${domain.dtCode}" <c:if test="${caseParty.sex==domain.dtCode }">
								selected="selected"</c:if>>${domain.dtName}</option>
								
							</c:forEach>
					</select>&nbsp;</td>
					<td class="tabRight">文化程度：</td>
					<td style="text-align: left"><dic:getDictionary
							var="educationLevelList" groupCode="educationLevel" /> <select class="select"
						name="education" >
							<option value="">--请选择--</option>
							<c:forEach items="${educationLevelList}" var="domain">
								<option value="${domain.dtCode}" <c:if test="${caseParty.education==domain.dtCode }">
								selected="selected"</c:if>>${domain.dtName}</option>
							</c:forEach>
					</select>&nbsp;</td>
					<td class="tabRight">工作单位和职务：</td>
					<td style="text-align: left"><input type="text" class="text"
						name="profession" value="${caseParty.profession }"/>&nbsp;</td>
				</tr>
				<tr>
					<td class="tabRight">民族：</td>
					<td style="text-align: left">
					<!-- <input type="text" class="text" name="nation" /> -->
					<dict:getDictionary
							var="nationList" groupCode="nation" /> <select class="select"
						name="nation" >
							<option value="">--请选择--</option>
							<c:forEach items="${nationList}" var="domain">
								<option value="${domain.dtCode}" <c:if test="${caseParty.nation==domain.dtCode }">
								selected="selected"</c:if>>${domain.dtName}</option>
							</c:forEach>
					</select>&nbsp;</td>
					<td class="tabRight">户籍地：</td>
					<td style="text-align: left"><input type="text" class="text"
						name="residence" id="residence" value="${caseParty.residence }"/>&nbsp;</td>
					<td class="tabRight">籍贯：</td>
					<td style="text-align: left"><input type="text" class="text"
						name="birthplace" value="${caseParty.birthplace }"/>&nbsp;</td>
				</tr>
					<tr>
						<td class="tabRight">特殊身份：</td>
						<td style="text-align: left">
						<dict:getDictionary
								var="specialIdentityList" groupCode="specialIdentity" /> 
								<select class="select"
							name="specialIdentity" >
								<option value="">--请选择--</option>
								<c:forEach items="${specialIdentityList}" var="domain">
									<option value="${domain.dtCode}" <c:if test="${caseParty.specialIdentity==domain.dtCode }">
							selected="selected"</c:if>>${domain.dtName}</option>
								</c:forEach>
						</select>&nbsp;</td>
						<td class="tabRight">现住址：</td>
						<td style="text-align: left" colspan="3"><input type="text" class="text"
							name="address" id="address" value="${caseParty.address }"/>&nbsp;</td>
					</tr>					
			</table>
			</c:forEach>
			</div>
			</fieldset>
			
			<fieldset class="fieldset" id="caseCompanyFieldset" style="width: 98%;margin-left: 10px;">
				<legend class="legend">
					被处罚单位信息<a href="javascript:void(0);" onclick="addCaseCompanyInfo();"
						style="color: blue">[添加]</a>
				</legend>
			<div id="addCaseCompanyDiv">
			<c:forEach var="caseCompany" items="${companyList }">
			<a href="javascript:void(0);" onclick="deleteTable(this);" style="margin-left: 88%;color: blue;">删除</a>
			<table id="caseCompanyTable"  class="blues" style="width: 90%;margin-left: 6%;margin-top: 0px; margin-bottom: 10px;">
				<tr>
					<td class="tabRight">单位名称：</td>
					<td style="text-align: left">
					<input type="text" class="text" name="name" value="${caseCompany.name }"/>
					&nbsp;<font color="red">*</font>
					</td>
					<td class="tabRight">单位性质：</td>
					<td style="text-align: left">
					<dic:getDictionary	var="danweiTypeList" groupCode="danweiType" />
					<select class="select" name="companyType">
							<option value="">--请选择--</option>
							<c:forEach items="${danweiTypeList}" var="domain">
								<option value="${domain.dtCode}" 
								<c:if test="${caseCompany.companyType==domain.dtCode }">
								selected=""selected</c:if>>${domain.dtName}</option>
							</c:forEach>
					</select>
					&nbsp;<font color="red">*</font>
					</td>
					<td class="tabRight">注册时间：</td>
					<td style="text-align: left"><input type="text" class="text"
					name="registractionTime" id="registractionTime"  readonly="readonly"
					value="<fmt:formatDate value='${caseCompany.registractionTime}' pattern='yyyy-MM-dd'/>"/>&nbsp;</td>
					
				</tr>
				<tr>
					<td class="tabRight"">登记证号：</td>
					<td style="text-align: left;" >
					<input type="text" class="text" name="registractionNum" value="${caseCompany.registractionNum }"/>
					&nbsp;<font color="red">*</font>
					<a onclick="callCompanyLib('${path }',this);" href="javascript:void(0);" title="调用企业信息库">调用</a>
					</td>
					<td class="tabRight" >法人代表：</td>
					<td style="text-align: left">
					<input type="text" class="text" name="proxy" value="${caseCompany.proxy }"/>
					&nbsp;<font color="red">*</font>
					</td>
					<td class="tabRight">联系电话：</td>
					<td style="text-align: left"><input type="text" class="text"
					name="tel" title="请输入座机号(区号-座机号)或手机号" poshytip="" value="${caseCompany.tel }"/></td>
				</tr>
				<tr>
					<td class="tabRight">信用代码注册号：</td>
					<td style="text-align: left">
						<input type="text" class="text" name="creditCode" value="${caseCompany.creditCode }"/>
					</td>
					<td class="tabRight">注册资金<font color="red"></font>：</td>
					<td style="text-align: left">
						<input type="text" class="text" name="registractionCapital" value="${caseCompany.registractionCapital }"/>
						&nbsp;<font color="red">(万元)</font></td>
					<td class="tabRight">注册地：</td>
					<td style="text-align: left"><input type="text" class="text"
						name="address" value="${caseCompany.address }"/>&nbsp;</td>
				</tr>
			</table>
			</c:forEach>
            	</div>
			</fieldset>
		</fieldset>
			<!-- //根据caseId找到对应的taskId actionId,userId,assignTarget -->
			<%-- <input type="hidden" name="taskId" id="taskId" value="${taskId}"/>
			<input type="hidden" name="actionId" id="actionId" value="${actionId }"/>
			<input type="hidden" name="userId" id="userId" value="${userId }"/>
			<input type="hidden" name="assignTarget" id="assignTarget" value="${assignTarget }"/> --%>
		</form>
		
	<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="保 存" onclick="add('add')" />
	<c:if test="${markup =='lian' }">
		<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="返 回" 
			onclick="window.location.href='${path}/casehandle/caseTodo/list?caseStateFlag=0'" />
	</c:if>
	<c:if test="${markup =='daiban' }">
		<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="返 回" 
			onclick="window.location.href='${path}/casehandle/caseTodo/list'" />
	</c:if>
	<div id="emptyCasePartyTable" style="display: none;">
		<a href="javascript:void(0);" onclick="deleteTable(this);" style="margin-left: 88%;color: blue;">删除</a>
		<table id="casePartyTable" class="blues" style="width: 90%;margin-left: 6%;margin-top: 0px; margin-bottom: 10px;">
				<tr>
					<td class="tabRight">身份证号：</td>
					<td style="text-align: left">
					<input onblur="autoParseIdsNo(this)" type="text" class="text" name="idsNo" 
					maxlength="18" id="idsNoForAdd" />
					&nbsp;<font color="red">*</font>
					<a onclick="callPeopleLib('${path }',this);" href="javascript:void(0);" title="调用人员信息库">调用</a>
					</td>
					<td class="tabRight">姓名：</td>
					<td style="text-align: left">
					<input type="text" class="text" name="name" />
					&nbsp;<font color="red">*</font>
					</td>
					<td class="tabRight">联系电话：</td>
					<td style="text-align: left"><input type="text" class="text" name="tel"
						title="请输入座机号(区号-座机号)或手机号" poshytip=""/>&nbsp;</td>
				</tr>
				<tr>
					<td class="tabRight">性别：</td>
					<td style="text-align: left"><dic:getDictionary var="sexList"
							groupCode="sex" /> <select class="select" name="sex" id="sexForAdd">
							<option value="">--请选择--</option>
							<c:forEach items="${sexList}" var="domain">
								<option value="${domain.dtCode}" >${domain.dtName}</option>
								
							</c:forEach>
					</select>&nbsp;</td>
					<td class="tabRight">文化程度：</td>
					<td style="text-align: left"><dic:getDictionary
							var="educationLevelList" groupCode="educationLevel" /> <select class="select"
						name="education" >
							<option value="">--请选择--</option>
							<c:forEach items="${educationLevelList}" var="domain">
								<option value="${domain.dtCode}" >${domain.dtName}</option>
							</c:forEach>
					</select>&nbsp;</td>
					<td class="tabRight">工作单位和职务：</td>
					<td style="text-align: left"><input type="text" class="text"
						name="profession" />&nbsp;</td>
				</tr>
				<tr>
					<td class="tabRight">民族：</td>
					<td style="text-align: left">
					<!-- <input type="text" class="text" name="nation" /> -->
					<dict:getDictionary
							var="nationList" groupCode="nation" /> <select class="select"
						name="nation" >
							<option value="">--请选择--</option>
							<c:forEach items="${nationList}" var="domain">
								<option value="${domain.dtCode}" >${domain.dtName}</option>
							</c:forEach>
					</select>&nbsp;</td>
					<td class="tabRight">户籍地：</td>
					<td style="text-align: left"><input type="text" class="text"
						name="residence" id="residence" />&nbsp;</td>
					<td class="tabRight">籍贯：</td>
					<td style="text-align: left"><input type="text" class="text"
						name="birthplace" />&nbsp;</td>
				</tr>
					<tr>
						<td class="tabRight">特殊身份：</td>
						<td style="text-align: left">
						<dict:getDictionary
								var="specialIdentityList" groupCode="specialIdentity" /> 
								<select class="select"
							name="specialIdentity" >
								<option value="">--请选择--</option>
								<c:forEach items="${specialIdentityList}" var="domain">
									<option value="${domain.dtCode}">${domain.dtName}</option>
								</c:forEach>
						</select>&nbsp;</td>
						<td class="tabRight">现住址：</td>
						<td style="text-align: left" colspan="3"><input type="text" class="text"
							name="address" id="address"/>&nbsp;</td>
					</tr>					
			</table>
		</div>
		<div id="emptyCaseCompanyTable" style="display: none;">
		<a href="javascript:void(0);" onclick="deleteTable(this);" style="margin-left: 88%;color: blue;">删除</a>
		<table id="caseCompanyTable"  class="blues" style="width: 90%;margin-left: 6%;margin-top: 0px; margin-bottom: 10px;">
			<tr>
				<td class="tabRight">单位名称：</td>
				<td style="text-align: left">
				<input type="text" class="text" name="name"/>
				&nbsp;<font color="red">*</font>
				</td>
				<td class="tabRight">单位性质：</td>
				<td style="text-align: left">
				<dic:getDictionary	var="danweiTypeList" groupCode="danweiType" />
				<select class="select" name="companyType">
						<option value="">--请选择--</option>
						<c:forEach items="${danweiTypeList}" var="domain">
							<option value="${domain.dtCode}" 
							>${domain.dtName}</option>
						</c:forEach>
				</select>
				&nbsp;<font color="red">*</font>
				</td>
				<td class="tabRight">注册时间：</td>
				<td style="text-align: left"><input type="text" class="text"
				name="registractionTime" id="registractionTime"  readonly="readonly"
				/>&nbsp;</td>
			</tr>
			<tr>
				<td class="tabRight"">登记证号：</td>
				<td style="text-align: left;" >
				<input type="text" class="text" name="registractionNum"/>
				&nbsp;<font color="red">*</font>
				<a onclick="callCompanyLib('${path }',this);" href="javascript:void(0);" title="调用企业信息库">调用</a>
				</td>
				<td class="tabRight" >法人代表：</td>
				<td style="text-align: left">
				<input type="text" class="text" name="proxy"/>
				&nbsp;<font color="red">*</font>
				</td>
				<td class="tabRight">联系电话：</td>
				<td style="text-align: left"><input type="text" class="text"
				name="tel" title="请输入座机号(区号-座机号)或手机号" poshytip=""/></td>
			</tr>
			<tr>
				<td class="tabRight">信用代码注册号：</td>
				<td style="text-align: left">
					<input type="text" class="text" name="creditCode"/>
				</td>
				<td class="tabRight">注册资金<font color="red"></font>：</td>
				<td style="text-align: left">
					<input type="text" class="text" name="registractionCapital"/>
					&nbsp;<font color="red">(万元)</font></td>
				<td class="tabRight">注册地：</td>
				<td style="text-align: left"><input type="text" class="text"
					name="address"/>&nbsp;</td>
			</tr>
		</table>						
		</div>
</div>
	
	<script type="text/javascript">
	function autoParseIdsNo(obj){
		if(checkIDCard($(obj).val())){
			my$Table=$(obj).parents("table");
		 	//自动填充
			var bithdayAndSexArrays = getBithdayAndSexFromIds($(obj).val());
			my$Table.find("#birthday").val(bithdayAndSexArrays[0]);
			my$Table.find("#sexForAdd option").each(function(i,n){
				if($(n).html()==bithdayAndSexArrays[1]){
					my$Table.find("#sexForAdd").val($(n).val());
				}
			});
		}
	}
	</script>
	
</body>
</html>