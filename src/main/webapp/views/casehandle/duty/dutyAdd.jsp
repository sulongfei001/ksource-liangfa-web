<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"/>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
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
	src="${path }/resources/script/caseParty.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/caseCompany-min.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/autoresize.jquery.min.js"></script>
<script type="text/javascript">
//添加自定义表单验证规则(在param[1]容器内的name为param[0]的组件的值不能一样)
if(jQuery.validator && jQuery.Poshytip){
	jQuery.validator.addMethod("isTheOneId",function(value,element,param){   
		  return this.optional(element) ||isTheOneId(value,element,param);   
		}, "同一案件的当事人信息的身份证号不能相同！"); 
};
	$(function() {
		//案件信息表单验证
		jqueryUtil.formValidate({
			form : "fileUploadForm",
			rules : {
				caseNo:{required:true,remote:'${path}/casehandle/dutycase/checkCaseNo',maxlength:25},
				caseName:{required:true,maxlength:25},
				//peopleName:{required:true,maxlength:10},
				//peopleDuty:{required:true,maxlength:25},
				caseNature:{maxlength:25},
				undertaker:{maxlength:25},
				recordSrc:{maxlength:25},
				approver:{maxlength:10},
				caseDetailFiles:{uploadFileLength:50},
				caseDetailName:{required:true}
				<c:if test="${needAssignTarget}">,assignTargetId:{required:true}</c:if>
			},
			messages : {
				caseNo:{required:"案件编号不能为空!",remote:'此编号已被使用，请填写其它编号!',maxlength:'请最多输入25位汉字或字母!'},
				caseName:{required:"案件名称不能为空!",maxlength:"请最多输入25位汉字!"},
				//peopleName:{required:"涉案人姓名不能为空！",maxlength:"请最多输入10位汉字以内"},
				//peopleDuty:{required:"涉案人职务不能为空！",maxlength:"请最多输入25位汉字以内"},
				caseNature:{maxlength:"请最多输入25位汉字!"},
				undertaker:{maxlength:"请最多输入25位汉字!"},
				recordSrc:{maxlength:"请最多输入25位汉字!"},
				approver:{maxlength:"请最多输入10位汉字!"},
				caseDetailFiles:{uploadFileLength:"案件材料文件名太长,必须小于50字,请修改后再上传!"},
				caseDetailName:{required:"案件详情不能为空!"}
				<c:if test="${needAssignTarget}">,assignTargetId:{required:"请选择受理机关！"}</c:if>
			},
			submitHandler : function(form) {
				$('#casePartyJson').val($.toJSON(caseParty.casePartyArray));

				//必须添加当事人
				if(caseParty.casePartyArray.length<1){
					jqueryUtil.errorPlacement(
							$('<label class="error" generated="true">请添加案件当事人！</label>'),
							$("#addHref")
					);
					$("#addHref").focus();
					return false;
				}
				//提交表单
				form.submit();
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
				nation:{maxlength:50},
				partyDuty:{required:true,maxlength:25}
			},
			messages:{
				name:{required:"姓名不能为空！",maxlength:"请最多输入50位汉字！"},
				idsNo:{required:"身份证号不能为空！",isIDCard:"请填写正确的身份证号码！"},
				tel:{isTel:"请正确填写电话或手机号码！"},
				address:{maxlength:"请最多输入250位汉字！"},
				birthplace:{maxlength:"请最多输入250位汉字！"},
				profession:{maxlength:"请最多输入250位汉字！"},
				nation:{maxlength:"请最多输入50位汉字！"},
				partyDuty:{required:"职务不能为空！",maxlength:"请最多输入25位汉字！"}
			},submitHandler:function(form){
			     dialog.close();
			     if(index!==null){
					     //2.提取数据
				         caseParty.getData("addCasePartyDiv",index);
				    }else{
				         caseParty.getData("addCasePartyDiv").save().createTable("showCasePartyTable","casePartyFieldset"); 
				    }

					//不再提示  添加当事人
					$("#addHref").unbind('focus',$("#addHref").data('tipFocusFn'));
				    
			      //只验证不提交数据
			      return false;
			}
		});
		//案件单位信息表单验证 
		jqueryUtil.formValidate({
			form : "caseCompanyForm",
			rules : {
				registractionNum : {
					maxlength : 30
				},
				name : {
					required : true,
					maxlength : 25
				},
				address : {
					maxlength : 250
				},
				proxy : {
					required : true,
					maxlength : 25
				},
				registractionCapital : {
					appNumber : [ 8, 4 ]
				},
				companyType : {
					maxlength : 50
				},
				tel : {
					isTel : true
				}
			},
			messages : {
				registractionNum : {
					maxlength : "请最多输入30位字符!"
				},
				name : {
					required : "姓名不能为空!",
					maxlength : "请最多输入25位汉字!"
				},
				address : {
					maxlength : "请最多输入250位汉字!"
				},
				proxy : {
					required : "法人不能为空!",
					maxlength : "请最多输入25位汉字!"
				},
				registractionCapital : {
					appNumber : "请输入数字(整数最多8位，小数最多4位)"
				},
				tel : {
					isTel : "请正确填写电话或手机号码!"
				},
				companyType : {
					maxlength : "请最多输入50位汉字!"
				}
			},
			submitHandler : function(form) {
				dialog.close();
				if (index !== null) {
					//2.提取数据
					caseCompany.getData("addCaseCompanyDiv", index);
				} else {
					caseCompany.getData("addCaseCompanyDiv").save()
							.createTable("showCaseCompanyTable",
									"caseCompanyFieldset");
				}
				//只验证不提交数据
				return false;
			}
		});
		//日期插件
		var undertakTime = document.getElementById('undertakTime');
		undertakTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		  }	 
		var birthday = document.getElementById('birthday');
		birthday.onfocus = function(){
		    var year =(new Date().getFullYear()-30).toString();
		    var date= year+'-%M-01';
			WdatePicker({dateFmt:'yyyy-MM-dd',startDate:date,alwaysUseStartDate:true});
		  }			
	var msg = "${info}";
	if(msg != null && msg != ""){
		art.dialog.tips(msg,2);
	}
	
	$('#caseDetailName').autoResize({
		limit:500
	});
	});
	
	//判断在同一个案件中当事人的身份证号是否唯一
	function isTheOneId(value,element,param){
		//如果有相同的返回false
		var isTheOneId =true;
		jQuery('span[name='+param[0]+']','[id='+param[1]+']').each(function(i,n){
			if(window.notValid!=null&&window.notValid===$('[name=update]','[id='+param[1]+']').eq(i).attr('index'))return ;
			if($.trim($(this).html())===$.trim(value)){
				isTheOneId=false;
			}
		});
		return isTheOneId;
	}
	function add(view){
		//给涉案人名称和涉案人职务赋值
		$("#peopleName").val($("span[name='name']").html());
		$("#peopleDuty").val($("span[name='partyDuty']").html());
		$("#fileUploadForm").attr("action",'${path }/casehandle/dutycase/add?view='+view).submit();
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
		dialog = art.dialog({
		    title: '当事人信息',
		    content: document.getElementById('addCasePartyDiv'),
		    lock:true,
			opacity: 0.1,
		    yesFn: function(){
				 $('#casePartyForm').submit();
				 return false;
		    },
		    noFn: function(){dialog.close();}
		});
	}
	function addAttchment() {
		 var files = $("#files") ;
		 var context = "<div  style=\"cursor:pointer;\"><input style=\"width: 75%\" type=\"file\" id=\"caseDetailFiles\" class='uploadFileLength' name=\"caseDetailFiles\">&nbsp;<span name=\"deletefile\"><img title=\"删除\" src=\"${path}/resources/images/jian1.png\" style=\" cursor:pointer ;margin: 0px;padding:0px;\"></span></div>" ;
		 $("#addfile").click(function() {
			 if($("span[name='deletefile']").length===4){
				 top.art.dialog.alert('提示：附件最多能添加五个！');
			 }else{
				 files.append(context) ;
			 }
		 }) ;
		 $("span[name='deletefile']").live("click",function() {
			$(this).parent("div").remove() ;
		 }) ;
		 return false;
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
</style>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">移送职务犯罪案件录入</legend>
	<form id="fileUploadForm" method="post" 
		enctype="multipart/form-data">
			<table width="90%" class="table_add">
				<tr>
					<td width="20%" align="right" class="tabRight">案件编号：</td>
					<td width="80%" style="text-align: left;" colspan="3"><input type="text"
						class="text" id="caseNo" name="caseNo" style="width: 92.5%"/>&nbsp;<font
						color="red">*必填</font></td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">案件名称：</td>
					<td width="80%" style="text-align: left;" colspan="3"><input
						type="text" class="text" id="caseName" name="caseName"
						style="width: 92.5%" />&nbsp;<font color="red">*必填</font></td>
				</tr>
				<c:if test="${needAssignTarget}">
					<tr>
						<td width="20%" align="right" class="tabRight">受理机关：</td>
						<td width="80%" style="text-align: left;"  colspan="3"><input style="width: 92.5%" 
							type="text" id="assignTargetId" name="assignTargetId" class="text"
							readonly="readonly" onfocus="showMenu(); return false;" /> <input
							type="hidden" name="assignTarget" /><font color="red">*必填</font></td>
				 	</tr>
				</c:if>
					<tr>
						<td width="20%" align="right" class="tabRight">案件性质：</td>
						<td width="30%" style="text-align: left;"><input type="text"
							class="text" id="caseNature" name="caseNature" /></td>
						<td width="20%" align="right" class="tabRight">承办人：</td>
					<td width="30%" style="text-align: left;"><input type="text"
						class="text" id="undertaker" name="undertaker" />&nbsp;</td>
					</tr>

				<tr>
					<td width="20%" align="right" class="tabRight">批准人：</td>
					<td width="30%" style="text-align: left;"><input type="text"
						class="text" id="approver" name="approver" />&nbsp;</td>
					<td width="20%" align="right" class="tabRight">承办时间：</td>
					<td width="30%" style="text-align: left;"><input
						type="text" class="text" id="undertakTime" name="undertakTime"
						/>&nbsp;</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">案件详情附件：</td>
					<td width="30%" style="text-align: left;" colspan="3"><input
						type="file" id="caseDetailFiles" name="caseDetailFiles"
						style="width: 93%" size="105" /> &nbsp;<font color="red">最大70M</font>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">案件详情：</td>
					<td width="30%" style="text-align: left" colspan="3"><textarea
							rows="5" id="caseDetailName" name="caseDetailName"></textarea>
						&nbsp;<font color="red">*必填</font></td>
				</tr>
				<input type="hidden" id="casePartyJson" name="casePartyJson" value="" />
				<input type="hidden" id="peopleName" name="peopleName"  />
				<input type="hidden" id="peopleDuty" name="peopleDuty"  />
			</table>
	</form>
		<fieldset class="fieldset" style="margin-bottom: 5px;margin-left:10px; width: 98%;" id="casePartyFieldset">
			<legend class="legend">案件当事人信息<a id="addHref" href="javascript:void(0);" onclick="openCasePartyInfo();"style="color: blue">[添加]</a></legend>
		</fieldset>
	</fieldset>
	<br/>
	<input type="button" value="保 存" onclick="add('search');" class="btn_small" />                                                             
	<input type="button" value="继续添加" onclick="add('add');" class="btn_big"/>                                                                  
	<input type="button" value="返 回" class="btn_small" onclick="javascript:window.location.href='<c:url value="/casehandle/dutycase/back"/>'"/>
		<div style="display: none" id="addCasePartyDiv">
		<form id="casePartyForm">
			<table id="casePartyTable" class="blues" style="width: 600px">
				<tr>
					<td class="tabRight"><font color="red">*</font>身份证号：</td>
					<td style="text-align: left"><input type="text" name="idsNo"
						maxlength="18" id="idsNoForAdd"/> <a
						href="javascript:callPeopleLib('${path }','#casePartyTable');"
						title="调用人员信息库">调用</a></td>
					<td class="tabRight"><font color="red">*</font>姓名：</td>
					<td style="text-align: left"><input type="text" name="name" />
					</td>
				</tr>
				<tr>
					<td class="tabRight">性别：</td>
					<td style="text-align: left"><dic:getDictionary var="sexList"
							groupCode="sex" /> <select name="sex" style="width: 76%" id="sexForAdd">
							<option value="">--请选择--</option>
							<c:forEach items="${sexList}" var="domain">
								<option value="${domain.dtCode}">${domain.dtName}</option>
							</c:forEach>
					</select></td>
					<td class="tabRight">文化程度：</td>
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
					<td class="tabRight">民族：</td>
					<td style="text-align: left"><%-- 
					<input type="text" name="nation" />--%>
					<dict:getDictionary
							var="nationList" groupCode="nation" /> <select
						name="nation" style="width: 76%">
							<option value="">--请选择--</option>
							<c:forEach items="${nationList}" var="domain">
								<option value="${domain.dtCode}">${domain.dtName}</option>
							</c:forEach>
					</select></td>
					<td class="tabRight">籍贯：</td>
					<td style="text-align: left"><input type="text"
						name="birthplace" /></td>
				</tr>
				<tr>
					<td class="tabRight">工作单位和职务：</td>
					<td style="text-align: left"><input type="text"
						name="profession" /></td>
					<td class="tabRight">联系电话：</td>
					<td style="text-align: left"><input type="text" name="tel"
						title="请输入座机号(区号-座机号)或手机号" poshytip="" /></td>
				</tr>
				<tr>
					<td class="tabRight">出生日期：</td>
					<td style="text-align: left"><input type="text"
						name="birthday" id="birthday"/></td>
					<td class="tabRight">住址：</td>
					<td style="text-align: left"><input type="text" name="address" /></td>
				</tr>
			</table>
		</form>
	</div>
	<table id="showCasePartyTable" class="blues"
		style="display: none; margin: 10px; width: 500px; float: left;">
		<tr>
			<td class="tabRight">操作：</td>
			<td colspan="3" style="text-align: left;"><a
				href="javascript:void(0);" name="del" onclick="delCaseParty(this);">[删除]</a>
				<a href="javascript:void(0);" name="update"
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
	
	<c:if test="${needAssignTarget}">
		<!-- 提交选择框 -->
		<div id="DropdownMenuBackground"
			style="display: none; position: absolute; height: 200px; width: 250px; background-color: white; border: 1px solid #ABC1D1; overflow-y: auto; overflow-x: auto;">
			<div class="tabRight">
				<a href="javascript:void(0);" onclick="javascript:clearOrg();">清空</a>
			</div>
			<ul id="dropdownMenu" class="tree"></ul>
		</div>
		<script type="text/javascript">
		zTree = $('#dropdownMenu')
				.zTree(
						{
							async : true,
							asyncUrl : "${path}/casehandle/case/getOrgTreeDataForAddCase?targetTaskDefId=${targetTaskDefId}&procDefId=${procDefId}",
							isSimpleData : true,
							treeNodeKey : "orgCode",
							treeNodeParentKey : "upOrgCode",
							nameCol : "orgName",
							callback : {
								beforeClick:function(treeId, treeNode){
									if(treeNode.isDept==0&&!treeNode.isParent){
										art.dialog.tips("未添加部门或未设置流程权限，请联系管理员！",3);
										return false;
										}
									return true;
								},
								asyncSuccess:function(event, treeId, treeNode, msg) {
									if(msg==null||msg=="[]"){
										art.dialog.alert("没有部门机构，请联系管理员！");
									}
								},
								click : function(event, treeId, treeNode) {
									var upOrg = zTree.getNodeByParam("orgCode",
											treeNode.upOrgCode);
									$("#assignTargetId").val(
											upOrg.orgName + "("
													+ treeNode.orgName + ")");
									$("input[name='assignTarget']").val(
											treeNode.orgCode);
									hideMenu();
								}
							}
						});
		function showMenu() {
			var cityObj = $("#assignTargetId");
			var cityOffset = $("#assignTargetId").offset();
			$("#DropdownMenuBackground").css({
				left : cityOffset.left + "px",
				top : cityOffset.top + cityObj.outerHeight() + "px"
			}).slideDown("fast");
		}
		function hideMenu() {
			$("#DropdownMenuBackground").fadeOut("fast");
		}
		function clearOrg() {
			$("#assignTargetId,input[name='assignTarget']").val('');
		}
		$("hmtl")
				.bind(
						"mousedown",
						function(event) {
							if (!(event.target.id == "DropdownMenuBackground" || $(
									event.target).parents(
									"#DropdownMenuBackground").length > 0)) {
								hideMenu();
							}
						});
	</script>
	</c:if>
	<script type="text/javascript">
	$(function(){
		$("#idsNoForAdd").blur(function(){
			if(checkIDCard($(this).val())){
				//自动填充	
				var bithdayAndSexArrays = getBithdayAndSexFromIds($(this).val());
				$("#birthday").val(bithdayAndSexArrays[0]);
				$("#sexForAdd option").each(function(){
					if($(this).html()==bithdayAndSexArrays[1]){
						$("#sexForAdd").val($(this).val());
					}
				});
			}
		});
		
	});
	</script>
	</div>
</body>
</html>