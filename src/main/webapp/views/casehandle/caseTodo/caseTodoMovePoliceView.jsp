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
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
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
<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/caseParty-min.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/caseCompany-min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/autoresize.jquery.min.js"></script>
<script src="${path }/resources/script/accuseSelector/accuseSelector.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/people$CompanyLib.js"></script>
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
					yisongNo:{required:true,maxlength:40,remote:'${path}/casehandle/case/checkYisongFileNo'},
					acceptOrg:{required:true},
					acceptOrgName:{required:true},
					yisongTime:{required:true},
					yisongFile1:{required:true},
					surveyFile1:{required:true},
/* 					goodsListFile1:{required:true},
					identifyFile1:{required:true},
					otherFile1:{required:true}, */
					caseDetail:{required:true}
				},
				messages:{
					yisongNo:{required:"移送文书号不能为空!",maxlength:'请最多输入40位汉字或字母!',remote:'此移送文书号已被使用，请填写其它移送文书号!'},
					acceptOrg:{required:"受案单位不能为空!"},
					acceptOrgName:{required:"受案单位不能为空!"},
					yisongTime:{required:"移送时间不能为空!"},
					yisongFile1:{required:"涉嫌犯罪案件移送书不能为空!"},
					surveyFile1:{required:"涉嫌犯罪案件调查报告不能为空!"},	
/* 					goodsListFile1:{required:"涉案物品清单不能为空!"},
					identifyFile1:{required:"检查报告或鉴定意见不能为空!"},
					otherFile1:{required:"其他涉嫌犯罪材料不能为空!"}, */
					caseDetail:{required:"涉嫌犯罪事实不能为空"}
				},submitHandler:function(form){
					//验证罪名是否填写
			    	var caseAccuseObj=$("[name='caseAccuse']").not(":disabled");
			    	var caseAccuseVal=caseAccuseObj.val();
			    	if(caseAccuseVal!=undefined&&caseAccuseVal==''){
			    		$.ligerDialog.warn("请选择涉案罪名！");
			    		return false;
			    	}
					
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
		
		//autoResize.js自动扩展textarea大小
		$('#caseDetail').autoResize({
			limit:500
		});
		initTimePlugin();
		
		$("[id='caseCompanyTable'] td:even").css("width","9%");
		$("[id='caseCompanyTable'] td:odd").css("width","24.3%");
		$("[id='casePartyTable'] td:even").css("width","9%");
		$("[id='casePartyTable'] td:odd").css("width","24.3%");
		
		//立案radio设置成一套切换卡选项
		//$("#xingzhenglianButtonSet").buttonset();
		
		accuseSelector.exec({labelC:'#yisongAccuseC',valC:'#yisongAccuse',control:'#yisongAccuseControl'});
		//保存后的提示信息
		var info = "${info}";
		var skipPath = "${skipPath}";
		if(info != null && info != ""){
			if(info == 'true'){
				//正确提示${path}/casehandle/caseTodo/caseTodoLianList
				$.ligerDialog.success('案件办理成功！',"",function(){window.location.href='${path}'+skipPath;});
			}else{
				//失败提示
				$.ligerDialog.error('案件办理失败！',"",function(){window.location.href='${path}'+skipPath;});
			}
		}
	});
	
	function initTimePlugin(){
		//日期插件
		var yisongTime = document.getElementById('yisongTime');
		yisongTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		
		 var registractionTime = document.getElementById('registractionTime');
		registractionTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}  
		
		$("[name='registractionTime']").unbind("focus");
		$("[name='registractionTime']").focus(function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		});
		
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
			registractionNum:{required:true,maxlength:30,isTheOneId:["registractionNum","addCaseCompanyDiv"],messages:{required:"统一社会信用代码不能为空!",maxlength:"请最多输入30位字符!",isTheOneId:"同一案件的单位的统一社会信用代码不能相同"}},
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
		$.ligerDialog.confirm('确认删除吗？',function(flag){
			if(flag){
				$(table_a).next().andSelf().remove();
			}
		});
	}
	//是否强制执行，隐藏强制执行材料
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
	<form id="caseForm" method="post" action="${path }/casehandle/caseTodo/saveCrimeCase" enctype="multipart/form-data">
		<fieldset class="fieldset">
			<legend class="legend">移送司法
			</legend>
			<%-- <div style="width: 98%;margin-left: 10px;margin-bottom:-10px;text-align: right;"><a style="font-weight: bold;" href="${path}/download/caseAddExplain" >[填表说明]</a></div> --%>
		<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;" id="yisonggonganTable">
			<tr>
				<td width="21%"  class="tabRight" colspan="2">移送文书号：</td>
				<td width="79%" style="text-align: left;" colspan="3">
				<input
					type="text" class="text ignore" id="yisongNo" name="yisongNo" style="width: 60%"/>
					&nbsp;<font color="red">*</font></td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" colspan="2">受案单位：</td>
				<td width="79%" style="text-align: left;" colspan="3"><input 
					type="text" class="text" id="acceptOrgName" name="acceptOrgName" style="width: 60%"
					onfocus="showAcceptOrg();" readonly="readonly"/>
					&nbsp;<font color="red">*</font>
					<input  type="hidden" name="acceptOrg" id="acceptOrg" />
				</td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" colspan="2">移送时间：</td>
				<td width="79%" style="text-align: left;" colspan="3"><input 
					type="text" class="text" id="yisongTime" name="yisongTime" style="width: 60%" readonly="readonly"/>
					&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" colspan="2">涉嫌犯罪案件移送书：</td>
				<td width="79%" style="text-align: left;" colspan="3" >
					<input type="file" id="yisongFile1" name="yisongFile1"
					style="width: 60%" /> &nbsp;<font
					color="red">*文件大小限制在70M以内</font>
				</td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" colspan="2">涉嫌犯罪案件调查报告：</td>
				<td width="79%" style="text-align: left;" colspan="3" >
					<input type="file" id="surveyFile1" name="surveyFile1"
					style="width: 60%" /> &nbsp;<font
					color="red">*文件大小限制在70M以内</font>
				</td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" colspan="2">涉案物品清单：</td>
				<td width="79%" style="text-align: left;" colspan="3" >
					<input type="file" id="goodsListFile1" name="goodsListFile1"
					style="width: 60%" /> &nbsp;<font
					color="red">文件大小限制在70M以内</font>
				</td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" colspan="2">检查报告或鉴定意见：</td>
				<td width="79%" style="text-align: left;" colspan="3" >
					<input type="file" id="identifyFile1" name="identifyFile1"
					style="width: 60%" /> &nbsp;<font
					color="red">文件大小限制在70M以内</font>
				</td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" colspan="2">其他涉嫌犯罪材料：</td>
				<td width="79%" style="text-align: left;" colspan="3" >
					<input type="file" id="otherFile1" name="otherFile1"
					style="width: 60%" /> &nbsp;<font
					color="red">文件大小限制在70M以内</font>
				</td>
			</tr>
			<tr>
                    <td width="21%"  class="tabRight" colspan="2">涉案罪名：</td>
                    <td width="79%" style="text-align: left;" colspan="3">
                    	<input type="hidden" name="caseAccuse" style="width: 400px;" id="yisongAccuse"/>
		                <div id="yisongAccuseC" style="border:1px solid #999;height: 80px;overflow: auto;width: 60%;"></div>
		                &nbsp;<font color="red">*</font>
		                <a href="javascript:void(0)" id="yisongAccuseControl">选择罪名</a>
					</td>
            </tr>
			<tr style="border-top: none;">
				<td width="21%"  class="tabRight" colspan="2">涉嫌犯罪事实：</td>
				<td width="79%" style="text-align: left" colspan="3">
				<textarea rows="5" id="caseDetail" name="caseDetail" style="width: 60%"></textarea>
				&nbsp;<font color="red">*</font>
				</td>
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
					当事人信息<a href="javascript:void(0);" onclick="addCasePartyInfo();"
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
							groupCode="sex" /> <select class="select" name="sex" id="sexForAdd" style="width:76.8%">
							<option value="">--请选择--</option>
							<c:forEach items="${sexList}" var="domain">
								<option value="${domain.dtCode}" <c:if test="${caseParty.sex==domain.dtCode }">
								selected="selected"</c:if>>${domain.dtName}</option>
								
							</c:forEach>
					</select>&nbsp;</td>
					<td class="tabRight">文化程度：</td>
					<td style="text-align: left"><dic:getDictionary
							var="educationLevelList" groupCode="educationLevel" /> <select class="select"
						name="education" style="width:76.8%">
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
						name="nation" style="width:76.8%">
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
							name="specialIdentity" style="width:76.8%">
								<option value="">--请选择--</option>
								<c:forEach items="${specialIdentityList}" var="domain">
									<option value="${domain.dtCode}" <c:if test="${caseParty.specialIdentity==domain.dtCode }">
							selected="selected"</c:if>>${domain.dtName}</option>
								</c:forEach>
						</select>&nbsp;</td>
						<td class="tabRight">现住址：</td>
						<td style="text-align: left" colspan="3"><input type="text" class="text"
							name="address" id="address" value="${caseParty.address }" style="width:89.5%"/>&nbsp;</td>
					</tr>					
			</table>
			</c:forEach>
			</div>
			</fieldset>
			
			<fieldset class="fieldset" id="caseCompanyFieldset" style="width: 98%;margin-left: 10px;">
				<legend class="legend">
					单位信息<a href="javascript:void(0);" onclick="addCaseCompanyInfo();"
						style="color: blue">[添加]</a>
				</legend>
			<div id="addCaseCompanyDiv">
			<c:forEach var="caseCompany" items="${companyList }">
			<a href="javascript:void(0);" onclick="deleteTable(this);" style="margin-left: 88%;color: blue;">删除</a>
			<table id="caseCompanyTable"  class="blues" style="width: 90%;margin-left: 6%;margin-top: 0px; margin-bottom: 10px;">
				<tr>
                    <td class="tabRight">统一社会信用代码：</td>
                    <td style="text-align: left;" >
                    <input type="text" class="text" name="registractionNum" value="${caseCompany.registractionNum }"/>
                    &nbsp;<font color="red">*</font>
                    <a onclick="callCompanyLib('${path }',this);" href="javascript:void(0);" title="调用企业信息库">调用</a>
                    </td>				
					<td class="tabRight">单位名称：</td>
					<td style="text-align: left">
					<input type="text" class="text" name="name" value="${caseCompany.name }"/>
					&nbsp;<font color="red">*</font>
					</td>
					<td class="tabRight" >法人代表：</td>
					<td style="text-align: left">
					<input type="text" class="text" name="proxy" value="${caseCompany.proxy }"/>
					&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="tabRight">单位性质：</td>
					<td style="text-align: left">
					<dic:getDictionary	var="danweiTypeList" groupCode="danweiType" />
					<select class="select" name="companyType" style="width:76.8%">
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
					<td class="tabRight">联系电话：</td>
					<td style="text-align: left"><input type="text" class="text"
					name="tel" title="请输入座机号(区号-座机号)或手机号" poshytip="" value="${caseCompany.tel }"/></td>
				</tr>
				<tr>
					<td class="tabRight">注册资金<font color="red"></font>：</td>
					<td style="text-align: left">
						<input type="text" class="text" name="registractionCapital" 
						value="<fmt:formatNumber value='${caseCompany.registractionCapital }' pattern='00.00'></fmt:formatNumber>"/>
						&nbsp;<font color="red">(万元)</font></td>
					<td class="tabRight">注册地：</td>
					<td style="text-align: left" colspan="3"><input type="text" class="text"
						name="address" value="${caseCompany.address }" style="width:89.5%"/>&nbsp;</td>
				</tr>
			</table>
			</c:forEach>
            	</div>
			</fieldset>
		</fieldset>
		</form>
		
	<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="保 存" onclick="add('add')" />
	<c:if test="${markup =='daiban' }">
		<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="返 回" 
			onclick="window.location.href='${path}/casehandle/caseTodo/list'" />
	</c:if>
	<c:if test="${markup =='lian' }">
		<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="返 回" 
			onclick="window.location.href='${path}/casehandle/caseTodo/caseTodoLianList'" />
	</c:if>
	<c:if test="${markup =='chufa' }">
		<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="返 回" 
			onclick="window.location.href='${path}/casehandle/caseTodo/caseTodoChufaList'" />
	</c:if>
	<c:if test="${markup =='suggestyisong' }">
		<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="返 回" 
			onclick="window.location.href='${path}/casehandle/caseTodo/suggestYisongList'"/>
	</c:if>
	<c:if test="${markup =='chufadone' }">
		<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="返 回" 
			onclick="window.location.href='${path}/casehandle/caseTodo/chufaDoneList'" />
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
							groupCode="sex" /> <select class="select" name="sex" id="sexForAdd" style="width:76.8%">
							<option value="">--请选择--</option>
							<c:forEach items="${sexList}" var="domain">
								<option value="${domain.dtCode}" >${domain.dtName}</option>
								
							</c:forEach>
					</select>&nbsp;</td>
					<td class="tabRight">文化程度：</td>
					<td style="text-align: left"><dic:getDictionary
							var="educationLevelList" groupCode="educationLevel" /> <select class="select"
						name="education" style="width:76.8%">
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
						name="nation" style="width:76.8%">
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
							name="specialIdentity" style="width:76.8%" >
								<option value="">--请选择--</option>
								<c:forEach items="${specialIdentityList}" var="domain">
									<option value="${domain.dtCode}">${domain.dtName}</option>
								</c:forEach>
						</select>&nbsp;</td>
						<td class="tabRight">现住址：</td>
						<td style="text-align: left" colspan="3"><input type="text" class="text"
							name="address" id="address" style="width:89.5%"/>&nbsp;</td>
					</tr>					
			</table>
		</div>
		<div id="emptyCaseCompanyTable" style="display: none;">
		<a href="javascript:void(0);" onclick="deleteTable(this);" style="margin-left: 88%;color: blue;">删除</a>
		<table id="caseCompanyTable"  class="blues" style="width: 90%;margin-left: 6%;margin-top: 0px; margin-bottom: 10px;">
			<tr>
                <td class="tabRight">统一社会信用代码：</td>
                <td style="text-align: left;" >
                <input type="text" class="text" name="registractionNum"/>
                &nbsp;<font color="red">*</font>
                <a onclick="callCompanyLib('${path }',this);" href="javascript:void(0);" title="调用企业信息库">调用</a>
                </td>			
				<td class="tabRight">单位名称：</td>
				<td style="text-align: left">
				<input type="text" class="text" name="name"/>
				&nbsp;<font color="red">*</font>
				</td>
				<td class="tabRight" >法人代表：</td>
				<td style="text-align: left">
				<input type="text" class="text" name="proxy"/>
				&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="tabRight">单位性质：</td>
				<td style="text-align: left">
				<dic:getDictionary	var="danweiTypeList" groupCode="danweiType" />
				<select class="select" name="companyType" style="width:76.8%">
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
				<td class="tabRight">联系电话：</td>
				<td style="text-align: left"><input type="text" class="text"
				name="tel" title="请输入座机号(区号-座机号)或手机号" poshytip=""/></td>
			</tr>
			<tr>
				<td class="tabRight">注册资金<font color="red"></font>：</td>
				<td style="text-align: left">
					<input type="text" class="text" name="registractionCapital"/>
					&nbsp;<font color="red">(万元)</font></td>
				<td class="tabRight">注册地：</td>
				<td style="text-align: left" colspan="3"><input type="text" class="text"
					name="address" style="width:89.5%"/>&nbsp;</td>
			</tr>
		</table>						
		</div>
</div>
<jsp:include page="/views/tree/accept_organise_tree.jsp"/>
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