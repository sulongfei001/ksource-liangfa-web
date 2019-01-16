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
<script type="text/javascript" src="${path }/resources/script/caseParty-min.js"></script>
<script type="text/javascript" src="${path }/resources/script/caseCompany-min.js"></script>
<script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
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
					caseNo:{required:true,maxlength:50,remote:'${path}/casehandle/case/checkCaseNo'},
					caseName:{required:true,maxlength:50},
					anfaTime:{required:true},
					anfaAddressName:{required:true},
					undertaker:{required:true},
					undertakerTime:{required:true},
					recordSrc:{required:true},
					caseDetailName:{required:true},
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
					undertakerSuggest:{required:"承办人意见不能为空！",maxlength:'请最多输入500位汉字'}
					
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
				}
			});//校验结束
		//autoResize.js自动扩展textarea大小
		$('#caseDetailName').autoResize({
			limit:500
		});
		
		initTimePlugin();
		
		$("[id='caseCompanyTable'] td:even").css("width","9%");
		$("[id='caseCompanyTable'] td:odd").css("width","24.3%");
		$("[id='casePartyTable'] td:even").css("width","9%");
		$("[id='casePartyTable'] td:odd").css("width","24.3%");
		
		//表单提交后，根据提交结果，给出提示
		var info = "${info}";
		if(info != null && info != ""){
			if(info == 'true'){
				//正确提示
				$.ligerDialog.success('案件办理成功',"",function(){window.location.href="${path}/casehandle/clueInfo/notDealClueList";});
			}else{
				//失败提示
				$.ligerDialog.error('案件办理失败',"",function(){window.location.href="${path}/casehandle/clueInfo/notDealClueList";});
			}
		}
	});
	
	function initTimePlugin(){
		//日期插件
		var undertakerTime  = document.getElementById('undertakerTime');
		undertakerTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		var registractionTime = document.getElementById('registractionTime');
		registractionTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		} 
		var anfaTime = document.getElementById('anfaTime');
		anfaTime.onfocus = function(){
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
	function add(){
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
	
	//将table_a后边的table以及table_a都删掉
	function deleteTable(table_a){
		$.ligerDialog.confirm('确认删除吗？',function(flag){
			if(flag){
				$(table_a).next().andSelf().remove();
			}
		});
	}
</script>
</head>
<body>
	<div class="panel">
	<form id="caseForm" method="post" action="${path}/casehandle/clueInfo/saveCaseBasicFromClue">
		<fieldset class="fieldset">
			<legend class="legend">线索办理
			</legend>
			<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;" id="chufaTable">
				<tr>
					<td width="21%"  class="tabRight">案件编号：</td>
					<td width="79%" style="text-align: left;" colspan="3">
						<input type="text" class="text ignore" id="caseNo" name="caseNo" style="width: 91%;"/>
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td width="21%"  class="tabRight">案件名称：</td>
					<td width="79%" style="text-align: left;" colspan="3">
						<input type="text" class="text" id="caseName" name="caseName" style="width: 91%;"
							value="${caseBasic.caseName }"/>
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td width="21%"  class="tabRight" >案件来源：</td>
					<td width="29%" style="text-align: left;" colspan="3">
						<dic:getDictionary var="caseSourceList" groupCode="caseSource"/>
						<select name="recordSrc" style="width: 91%;">
							<c:forEach items="${caseSourceList}" var="domain">
								<option value="${domain.dtCode}" <c:if test="${caseBasic.recordSrc == domain.dtCode}">
									selected='selected'</c:if>>${domain.dtName}</option>
							</c:forEach>
						</select>&nbsp; <font color="red">*</font> 
					</td>					
				</tr>
				<tr>
					<td width="21%"  class="tabRight" >案发区域：</td>
					<td width="29%" style="text-align: left;">
						<input type="text" id="anfaAddressName" name="anfaAddressName" class="text" 
						onfocus="showAnfaAddress(); return false;" readonly="readonly" 
							value=""/>&nbsp; <font color="red">*</font> 
						<input type="hidden" id="anfaAddress" name="anfaAddress" value=""/>
					</td>
					<td width="21%"  class="tabRight">案发时间：</td>
					<td width="29%" style="text-align: left;">
						<input type="text" class="text" id="anfaTime" name="anfaTime" 
						 readonly="readonly" value="<fmt:formatDate value='${caseBasic.anfaTime }' pattern='yyyy-MM-dd' />"/>
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td width="21%"  class="tabRight" >违法事实：</td>
					<td width="79%" style="text-align: left" colspan="3">
						<textarea rows="5" id="caseDetailName" name="caseDetailName" style="width: 91%;">${caseBasic.caseDetailName }</textarea>
						&nbsp;<font color="red">*</font>
					</td>
				</tr>				
				<tr>
					<td width="21%"  class="tabRight" >承办人：</td>
					<td width="29%" style="text-align: left;">
					<input type="text" id="undertaker" name="undertaker" class="text" value="${caseBasic.undertaker }"/>
					&nbsp; <font color="red">*</font> 
					</td>
					<td width="21%"  class="tabRight" >承办时间：</td>
					<td width="29%" style="text-align: left;" >
					<input type="text" class="text" id="undertakerTime" name="undertakerTime" readonly="readonly"
						value="<fmt:formatDate value='${caseBasic.undertakerTime }' pattern='yyyy-MM-dd' />"/>
					&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td width="21%"  class="tabRight" >承办意见：</td>
					<td width="79%" style="text-align: left" colspan="3">
					<textarea rows="3" id="undertakerSuggest" name="undertakerSuggest" style="width: 91%;">${caseBasic.undertakerSuggest }</textarea>
					&nbsp;<font color="red">*</font>
				</tr>
			</table>
		<input type="hidden" id="casePartyJson" name="casePartyJson" value="" />
		<input type="hidden" id="caseCompanyJson" name="caseCompanyJson" value="" />
		<input type="hidden" id="dispatchId" name="dispatchId" value="${clueDispatch.dispatchId }" />
		<input type="hidden" id="clueInfoId" name="clueInfoId" value="${clueDispatch.clueId }" />
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
		</form>
		
	<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="保 存" onclick="add('${caseBasic.caseId}')" />
	<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" 
		type="button" class="btn_small" value="返 回" onclick="window.location.href='${path}/casehandle/clueInfo/notDealClueList'" />
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
<jsp:include page="/views/tree/anfa_address_tree.jsp"/>	
</body>
</html>