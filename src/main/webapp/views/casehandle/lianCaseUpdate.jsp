<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
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
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/caseParty-min.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/caseCompany-min.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/people$CompanyLib.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/autoresize.jquery.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
<script>
//添加自定义表单验证规则(在param[1]容器内的name为param[0]的组件的值不能一样)
if(jQuery.validator && jQuery.Poshytip){
	jQuery.validator.addMethod("isTheOneId",function(value,element,param){   
		  return this.optional(element) ||isTheOneId(value,element,param);   
		}, "同一案件的当事人信息的身份证号不能相同！"); 
};
	$(function(){
		//案件信息表单验证
		jqueryUtil.formValidate({
			form:"fileUploadForm",
			blockUI:false,
			rules:{
				caseName:{required:true,maxlength:50},
				undertaker:{required:true,maxlength:25},
				recordSrc:{required:true,maxlength:25},
				lianTime:{required:true},
				approver:{required:true,maxlength:10},
				caseDetailFiles:{uploadFileLength:25}
				
			},
			messages:{
				caseName:{required:"案件名称不能为空!",maxlength:"请最多输入50位汉字!"},
				undertaker:{required:"承办人不能为空!",maxlength:"请最多输入25位汉字!"},
				recordSrc:{required:"案件来源不能为空!",maxlength:"请最多输入25位汉字!"},
				lianTime:{required:"立案时间不能为空!"},
				approver:{required:"批准人不能为空!",maxlength:"请最多输入10位汉字!"},
				caseDetailFiles:{uploadFileLength:"案件详情附件文件名太长,必须小于25字符,请修改后再上传!"}
			},submitHandler:function(form){
			     //事先判断一下案件状态
			     $.getJSON('<c:url value="/casehandle/case/checkCaseState/${caseInfo.caseId}"/>',function(data){
			       if(data.result==true){
			          $('#casePartyJson').val($.toJSON(caseParty.casePartyArray)); 
				      $('#procKey').val('${procKey}');
				      $('#fileUploadForm')[0].submit();
			       }else{
			          art.dialog.tips(data.msg, 2.0);
			       }
			     });
			}
		});
		//案件当事人表单验证
		jqueryUtil.formValidate({
			form:"casePartyForm",
			rules:{
				name:{required:true,maxlength:50},
				idsNo:{required:true,isIDCard:true,isTheOneId:['idsNo','showCasePartyTable']},
				tel:{isTel:true},
				address:{maxlength:250},
				birthplace:{maxlength:250},
				profession:{maxlength:250},
				nation:{maxlength:50}
			},
			messages:{
				name:{required:"名称不能为空!",maxlength:"请最多输入50位汉字!"},
				idsNo:{required:"身份证号不能为空!",isIDCard:"请填写正确的身份证号码!"},
				tel:{isTel:"请正确填写电话或手机号码!"},
				address:{maxlength:"请最多输入250位汉字!"},
				birthplace:{maxlength:"请最多输入250位汉字!"},
				profession:{maxlength:"请最多输入250位汉字!"},
				nation:{maxlength:"请最多输入50位汉字!"}
			},submitHandler:function(form){
			     caseupdate_dialog.close();
			     if(index!==null){
					     //2.提取数据
				         caseParty.getData("addCasePartyDiv",index);
				    }else{
				         caseParty.getData("addCasePartyDiv").save().createTable("showCasePartyTable","casePartyFieldset"); 
				    }
			      //只验证不提交数据
			      return false;
			}
		});
			
		//案件单位信息表单验证 
		jqueryUtil.formValidate({
			form:"caseCompanyForm",
			rules:{
				registractionNum : {required:true,maxlength : 30,isTheOneId:['registractionNum','showCaseCompanyTable']},
				name:{required:true,maxlength:25},
				address:{maxlength:250},
				proxy:{required:true,maxlength:25},
				registractionCapital:{appNumber:[8,4]},
				companyType:{required:true,maxlength:50},
				tel:{isTel:true}
			},
			messages:{
				registractionNum : {
					required:"税务登记号不能为空!",maxlength : "请最多输入30位字符!",isTheOneId:"同一案件的单位的税务登记号不能相同"
				},
				name:{required:"企业名称不能为空!",maxlength:"请最多输入25位汉字!"},
				address:{maxlength:"请最多输入250位汉字!"},
				proxy:{required:"法人不能为空!",maxlength:"请最多输入25位汉字!"},
				registractionCapital:{appNumber:"请输入数字(整数最多8位，小数最多4位)"},
				tel:{isTel:"请正确填写电话或手机号码!"},
				companyType:{required:"单位类型不能为空!",maxlength:"请最多输入50位汉字!"}
			},submitHandler:function(form){
			     dialog.close();
			     if(index!==null){
					     //2.提取数据
				         caseCompany.getData("addCaseCompanyDiv",index);
				    }else{
				    	caseCompany.getData("addCaseCompanyDiv").save().createTable("showCaseCompanyTable","caseCompanyFieldset"); 
				    }
			      //只验证不提交数据
			      return false;
			}
		});
		
		
		//日期插件
		var lianTime = document.getElementById('lianTime');
		lianTime.onfocus = function(){
	      WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		var registractionTime = document.getElementById('registractionTime');
		registractionTime.onfocus = function(){
	      WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		var birthday = document.getElementById('birthday');
		birthday.onfocus = function(){
			var year =(new Date().getFullYear()-30).toString();
		    var date= year+'-%M-01';
			WdatePicker({dateFmt:'yyyy-MM-dd',startDate:date,alwaysUseStartDate:true});
		}		
		 $('#caseDetailName').autoResize({
				maxHeight:500
		});
	    $.getJSON('<c:url value="/casehandle/case/getCasePartyByCaseId/${caseInfo.caseId}"/>',function(data){
	       caseParty.showCasePartys(data,'showCasePartyTable','showCasePartyDiv');
	    });
	    $.getJSON('<c:url value="/casehandle/case/getCaseCompanyByCaseId/${caseInfo.caseId}"/>',function(data){
		       caseCompany.showCaseCompanys(data,'showCaseCompanyTable','showCaseCompanyDiv');
		    });
	});
	function back(){
		art.dialog.confirm('所填信息未保存,确认返回?',function(){
		window.location.href='<c:url value="/casehandle/case/back"/>?procKey=${procKey}';
		});
		}
	function update(){
		 $("#fileUploadForm").submit();
	}
	//判断在同一个案件中当事人的身份证号是否唯一
	function isTheOneId(value,element,param){
		//如果有相同的返回false
		var isTheOneId =true;
		jQuery('span[name='+param[0]+']','[id='+param[1]+']').each(function(i,n){
			if(window.notValid!=null&&window.notValid===$('[name=update]','id=['+param[1]+']').eq(i).attr('index'))return ;
				
			if($.trim($(this).html())===$.trim(value)){
					isTheOneId=false;
			}
		});
		return isTheOneId;
	}
	function openCasePartyInfo(){
	    caseParty.clearData('casePartyTable');
		index =null;//全局变量 ,用来保存索引
		if(arguments.length===2&&arguments[0] ==='update'){
		    index = $(arguments[1]).attr('index');
		    caseParty.setData('addCasePartyDiv',index);
		  //用一全局变量　给出标志，验证时不验证本身
		    window.notValid=index;
		}else{
			 if(window.notValid){
				 window.notValid=null;
			 }
		}
		caseupdate_dialog = art.dialog({
		    title: '当事人信息',
		    content: document.getElementById('addCasePartyDiv'),
		    lock:true,
			opacity: 0.1,
		    yesFn: function(){
				 $('#casePartyForm').submit();
				 return false;
		    },
		    noFn: function(){caseupdate_dialog.close();}
		});
	}
	function openCaseCompanyInfo(){
	    caseCompany.clearData('caseCompanyTable');
		index =null;//全局变量 ,用来保存索引
		if(arguments.length===2&&arguments[0] ==='update'){
		    index = $(arguments[1]).attr('index');
		    caseCompany.setData('addCaseCompanyDiv',index);
		  //用一全局变量　给出标志，验证时不验证本身
		    window.notValid=index;
		}else{
			 if(window.notValid){
				 window.notValid=null;
			 }
		}
		dialog = art.dialog({
		    title: '单位信息',
		    content: document.getElementById('addCaseCompanyDiv'),
		    lock:true,
			opacity: 0.1,
		    yesFn: function(){
				 $('#caseCompanyForm').submit();
				 return false;
		    },
		    noFn: function(){dialog.close();}
		});
	}
	function delCaseCompany(object){
		var index = $(object).attr('index');
		art.dialog.confirm('确认删除此单位信息吗？',function(){caseCompany.remove(index);});
	}
	function del(name,type){//ajax删除附件
		var element = $('#'+name);
		var text = element.text();
		if(confirm("确认删除"+text+"吗？")){
			$.get("${path}/casehandle/case/delFile/${caseInfo.caseId}/"+type,function(){
				$('#'+name+'Span').html('无');
			});
		}
	}
	function delCaseParty(object){
		var index = $(object).attr('index');
		top.art.dialog.confirm('确认删除此案件当事人吗？',function(){caseParty.remove(index);});
	}
</script>
<style type="text/css">
	#outDiv {
		position: relative;
		margin: 6px;
	}
	
	legend {
		padding: 0.5em 4em;
		font-size: 16px;
	}
	
	#formFieldC .genInputText {
		width: 200px;
	}
	
	#formFieldC .genSelect {
		width: 150px;
	}
	
	#formFieldC p {
		margin: 6px 0;
		padding-left: 20px;
	}
	
	#formFieldC p label {
		width: 100px;
		display: inline-block;
	}
	
	#caseInfoC {
		padding: 6px;
		position: absolute;
		top: 20px;
		right: 15px;
	}
	
	#caseInfoC a {
		color: red;
	}
</style>
<title>Insert title here</title>
</head>
<body>
	<form:form id="fileUploadForm" action="${path}/casehandle/case/update"
		enctype="multipart/form-data" modelAttribute="caseInfo">
		<form:hidden path="caseId"/>
		<fieldset class="ui-widget ui-corner-all">
			<legend class="ui-widget ui-widget-header ui-corner-all">修改案件信息</legend>
			<table class="blues">
			
				<tr>
					<td width="20%" align="right" class="tabRight">案件名称：</td>
					<td width="80%" style="text-align: left;" colspan="3"><form:input
						 class="text" path="caseName"
						style="width: 91%" />&nbsp;<font color="red">*必填</font></td>
				</tr>
				<tr>
					<td width="20%"  class="tabRight">批准人：</td>
					<td width="30%" style="text-align: left;"><form:input path="approver" class="text"/></td>
					<td width="20%"  class="tabRight">案件来源：</td>
					<td width="30%" style="text-align: left;">
					<dic:getDictionary var="caseSourceList" groupCode="caseSource"/>
						<form:select path="recordSrc">
							<form:option value="" label="--请选择--"></form:option>
							<c:forEach var="domain" items="${caseSourceList }">
								<form:option value="${domain.dtCode }">${domain.dtName }</form:option>
							</c:forEach> 
						</form:select>&nbsp;<font color="red">*必填</font>
					</td>
				</tr>
				<tr>
					<td width="20%"  class="tabRight">承办人：</td>
					<td width="30%" style="text-align: left;"><form:input path="undertaker" class="text"/></td>
					<td width="20%"  class="tabRight">立案时间：</td>
					<td width="30%" style="text-align: left;"><form:input path="lianTime"  cssClass="text" /></td>
				</tr>
				<tr>
					<td width="20%"  class="tabRight">案件详情附件：</td>
					<td width="30%" style="text-align: left;" colspan="3"><c:if
							test="${caseInfo.caseDetailFileName!=null and caseInfo.caseDetailFileName!='' }">
							<span id="caseDetailFileNameSpan">
						<a href="${path}/download/caseDetail/${caseInfo.caseId}" id="caseDetailFileName" >${caseInfo.caseDetailFileName}</a>&nbsp;&nbsp;
						<a href="javascript:void(0);" onclick="del('caseDetailFileName',1)" id="caseDetailFileNameDel" style="color: #FF6600;">删除</a>
						</span>
						</c:if> <c:if
							test="${caseInfo.caseDetailFileName==null or caseInfo.caseDetailFileName=='' }">
						无
					</c:if>
					</td>
				</tr>
				<tr>
				<td width="20%"  class="tabRight">&nbsp;
					</td>
					<td  width="30%" style="text-align: left;" colspan="3">
						<input type="file" style="width: 99%"  id="caseDetailFiles" name="caseDetailFiles" />
						&nbsp;<font color="red">*文件大小限制在70M以内</font>
					</td>
				</tr>
				<tr>
					<td width="20%"  class="tabRight">案件详情：</td>
					<td width="30%" style="text-align: left;" colspan="3"><form:textarea
							rows="3" cols="30" path="caseDetailName" /></td>
				</tr>
			</table>
			<fieldset class="ui-widget ui-corner-all" id="casePartyFieldset">
				<legend class="ui-widget ui-widget-header ui-corner-all">
					案件当事人信息<a href="javascript:void(0);" onclick="openCasePartyInfo();" style="color: blue">[添加]</a>
				</legend>
				<div id="showCasePartyDiv"></div>
			</fieldset>
			<fieldset class="ui-widget ui-corner-all" id="caseCompanyFieldset">
				<legend class="ui-widget ui-widget-header ui-corner-all">
					案件单位信息<a href="javascript:void(0);" onclick="openCaseCompanyInfo();"
						style="color: blue">[添加]</a>
				</legend>
				<div id="showCaseCompanyDiv"></div>
			</fieldset>
			<input type="hidden" id="casePartyJson" name="casePartyJson" value="" />
			<input type="hidden" id="caseCompanyJson" name="caseCompanyJson"value="" /> 
		    <input type="hidden" name="procKey" id="procKey" value="${procKey}" />
			<input type="button" value="保  存" onclick="update();"/>
			<input type="button" value="返  回" onclick="back();"/>
		</fieldset>
	</form:form>
	<div style="display: none" id="addCasePartyDiv">
		<form id="casePartyForm">
			<table id="casePartyTable" class="blues" style="width: 600px">
							<tr>
								<td  class="tabRight"><font color="red">*</font>身份证号：</td>
								<td style="text-align: left"><input type="text"
									name="idsNo" maxlength="18"/>
									<a href="javascript:callPeopleLib('${path }','#casePartyTable');" title="调用人员信息库">调用</a>
								</td>
								<td  class="tabRight"><font color="red">*</font>姓名：</td>
								<td style="text-align: left"><input type="text" name="name"
									 />
								</td>
							</tr>
							<tr>
								<td  class="tabRight">性别：</td>
								<td style="text-align: left"><dic:getDictionary
										var="sexList" groupCode="sex" /> <select name="sex" style="width: 76%">
										<option value="">--请选择--</option>
										<c:forEach items="${sexList}" var="domain">
											<option value="${domain.dtCode}">${domain.dtName}</option>
										</c:forEach>
								</select></td>
								<td  class="tabRight">文化程度：</td>
								<td style="text-align: left"><dic:getDictionary
										var="educationLevelList" groupCode="educationLevel" /> <select
									name="education" style="width: 89%">
										<option value="">--请选择--</option>
										<c:forEach items="${educationLevelList}" var="domain">
											<option value="${domain.dtCode}">${domain.dtName}</option>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
							<td  class="tabRight">民族：</td>
								<td style="text-align: left"><dic:getDictionary
										var="nationList" groupCode="nation" /> <select name="nation" style="width: 76%">
										<option value="">--请选择--</option>
										<c:forEach items="${nationList}" var="domain">
											<option value="${domain.dtCode}">${domain.dtName}</option>
										</c:forEach>
								</select></td>
								<td  class="tabRight">籍贯：</td>
								<td style="text-align: left"><input type="text"
									name="birthplace" /></td>
							</tr>
							<tr>
								<td  class="tabRight">工作单位和职务：</td>
								<td style="text-align: left"><input type="text"
									name="profession" /></td>
								<td  class="tabRight">联系电话：</td>
								<td style="text-align: left"><input type="text" name="tel"
									title="请输入座机号(区号-座机号)或手机号" poshytip="" />
								</td>
							</tr>
							<tr>
								<td  class="tabRight">出生日期：</td>
								<td  style="text-align: left"><input type="text"
									name="birthday" id="birthday"/></td>
								<td  class="tabRight">住址：</td>
								<td style="text-align: left"><input type="text"
									name="address" /></td>
							</tr>
						</table>
		</form>
	</div>
	<table id="showCasePartyTable" class="blues"
		style="display: none; margin: 10px; width: 500px; float: left;">
		<tr>
			<td class="tabRight">操作：</td>
			<td colspan="3" style="text-align: left;"><a href="javascript:void(0);" name="del"
				onclick="delCaseParty(this);">[删除]</a> <a href="javascript:void(0);" name="update"
				onclick="openCasePartyInfo('update',this);">[修改]</a></td>

		</tr>
		<tr>
			<td class="tabRight">身份证号：</td>
			<td style="text-align: left;"><span name="idsNo"></span></td>
			<td class="tabRight">姓名：</td>
			<td style="text-align: left;"><span name="name"></span></td>
		</tr>
		<tr>
			<td class="tabRight">性别：</td>
			<td style="text-align: left;"><span name="sex"></span></td>
			<td class="tabRight">文化程度：</td>
			<td style="text-align: left;"><span name="education"></span></td>
		</tr>
		<tr>
			<td class="tabRight">民族：</td>
			<td style="text-align: left;"><span name="nation"></span></td>
			<td class="tabRight">籍贯：</td>
			<td style="text-align: left;"><span name="birthplace"></span></td>
		</tr>
		<tr>
			<td class="tabRight">工作单位和职务：</td>
			<td style="text-align: left;"><span name="profession"></span></td>
			<td class="tabRight">联系电话：</td>
			<td style="text-align: left;"><span name="tel"></span></td>
		</tr>
		<tr>
			<td class="tabRight">出生日期：</td>
			<td style="text-align: left;"><span name="birthday"></span></td>
			<td class="tabRight">住址：</td>
			<td style="text-align: left;"><span name="address"></span></td>
		</tr>
	</table>
	<div style="display: none" id="addCaseCompanyDiv">
		<form id="caseCompanyForm">
			<table id="caseCompanyTable" class="blues" style="width: 600px">
				<tr>
					<td class="tabRight"><font color="red">*</font>税务登记证号：</td>
					<td style="text-align: left">
						<input type="text" name="registractionNum" />
						<a href="javascript:callCompanyLib('${path }','#caseCompanyTable');" title="调用企业信息库">调用</a>
					</td>
					<td class="tabRight"><font color="red">*</font>企业名称：</td>
					<td style="text-align: left"><input type="text" name="name" /></td>
				</tr>
				<tr>
					<td class="tabRight"><font color="red">*</font>法人代表：</td>
					<td style="text-align: left"><input type="text" name="proxy" /></td>
					<td class="tabRight">注册地：</td>
					<td style="text-align: left"><input type="text" name="address" /></td>
				</tr>
				<tr>
					<td class="tabRight">注册资金：</td>
					<td style="text-align: left"><input type="text"
						name="registractionCapital" />&nbsp;<font color="red">(万元)</font></td>
					<td class="tabRight"><font color="red">*</font>单位类型：</td>
					<td style="text-align: left"><dic:getDictionary
								var="danweiTypeList" groupCode="danweiType" /> <select
							name="companyType"  style="width: 45%">
								<option value="">--请选择--</option>
								<c:forEach items="${danweiTypeList}" var="domain">
									<option value="${domain.dtCode}">${domain.dtName}</option>
								</c:forEach>
						</select></td>
				</tr>
				<tr>
					<td class="tabRight">注册时间：</td>
					<td style="text-align: left"><input type="text"
						name="registractionTime" id="registractionTime" /></td>
					<td class="tabRight">联系电话：</td>
					<td style="text-align: left" colspan="3"><input type="text"
						name="tel" title="请输入座机号(区号-座机号)或手机号" poshytip="" /></td>

				</tr>
			</table>
		</form>
	</div>
	<table id="showCaseCompanyTable" class="blues"
		style="display: none; margin: 10px; width: 500px; float: left;">
		<tr>
			<td class="tabRight">操作：</td>
			<td colspan="3" style="text-align: left;"><a href="javascript:void(0);" name="del"
				onclick="delCaseCompany(this);">[删除]</a> <a href="javascript:void(0);" name="update"
				onclick="openCaseCompanyInfo('update',this);">[修改]</a></td>

		</tr>
		<tr>
			<td class="tabRight">税务登记证号：</td>
			<td style="text-align: left;"><span name="registractionNum"></span>
			</td>
			<td class="tabRight">企业名称：</td>
			<td style="text-align: left;"><span name="name"></span></td>
		</tr>
		<tr>
			<td class="tabRight">法人代表：</td>
			<td style="text-align: left;"><span name="proxy"></span></td>
			<td class="tabRight">注册地：</td>
			<td style="text-align: left;"><span name="address"></span></td>
		</tr>
		<tr>
			<td class="tabRight">注册资金：</td>
			<td style="text-align: left;"><span name="registractionCapital"></span>
				&nbsp;<font color="red">(万元)</font></td>
			<td class="tabRight">单位类型：</td>
			<td style="text-align: left;"><span name="companyType"></span></td>
		</tr>
		<tr>
			<td class="tabRight">注册时间：</td>
			<td style="text-align: left;"><span name="registractionTime"></span>
			</td>
			<td class="tabRight">联系电话：</td>
			<td style="text-align: left;"><span name="tel"></span></td>
		</tr>
	</table>
</body>
</html>