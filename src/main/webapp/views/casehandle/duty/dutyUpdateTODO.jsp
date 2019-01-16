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
	href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
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
	src="${path }/resources/script/caseParty-min.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/caseCompany-min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/autoresize.jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		//案件信息表单验证
		jqueryUtil.formValidate({
			form : "fileUploadForm",
			rules : {
				caseNo:{required:true,remote:'${path}/casehandle/weijicase/checkCaseNo',maxlength:25},
				caseName:{required:true,maxlength:25},
				peopleName:{required:true,maxlength:10},
				peopleDuty:{required:true,maxlength:25},
				caseNature:{maxlength:25},
				undertaker:{maxlength:25},
				recordSrc:{maxlength:25},
				approver:{maxlength:10},
				caseDetailFiles:{uploadFileLength:25},
				caseDetailName:{required:true}
				<c:if test="${needAssignTarget}">,assignTargetId:{required:true}</c:if>
			},
			messages : {
				caseNo:{required:"案件编号不能为空!",remote:'此编号已被使用，请填写其它编号!',maxlength:'请最多输入25位汉字或字母!'},
				caseName:{required:"案件名称不能为空!",maxlength:"请最多输入25位汉字!"},
				peopleName:{required:"涉案人姓名不能为空！",maxlength:"请最多输入10位汉字以内"},
				peopleDuty:{required:"涉案人职务不能为空！",maxlength:"请最多输入25位汉字以内"},
				caseNature:{maxlength:"请最多输入25位汉字!"},
				undertaker:{maxlength:"请最多输入25位汉字!"},
				recordSrc:{maxlength:"请最多输入25位汉字!"},
				approver:{maxlength:"请最多输入10位汉字!"},
				caseDetailFiles:{uploadFileLength:"案件详情附件文件名太长,必须小于25字符,请修改后再上传!"},
				caseDetailName:{required:"案件详情不能为空!"}
				<c:if test="${needAssignTarget}">,assignTargetId:{required:"请选择受理机关！"}</c:if>
			},
			submitHandler : function(form) {
				$('#casePartyJson').val($.toJSON(caseParty.casePartyArray));
				 $('#caseCompanyJson').val($.toJSON(caseCompany.caseCompanyArray)); 
				//提交表单
				form.submit();
			}
		});
		//案件当事人表单验证
		jqueryUtil.formValidate({
			form : "casePartyForm",
			rules : {
				name : {
					required : true,
					maxlength : 50
				},
				idsNo : {
					required:true,
					isIDCard : true
				},
				tel : {
					isTel : true
				},
				address : {
					maxlength : 250
				},
				birthplace : {
					maxlength : 250
				},
				profession : {
					maxlength : 250
				},
				nation : {
					maxlength : 50
				}
			},
			messages : {
				name : {
					required : "姓名不能为空",
					maxlength : "请最多输入50位汉字!"
				},
				idsNo : {
					required:"身份证号不能为空!",
					isIDCard : "请填写正确的身份证号码!"
				},
				tel : {
					isTel : "请正确填写电话或手机号码!"
				},
				address : {
					maxlength : "请最多输入250位汉字!"
				},
				birthplace : {
					maxlength : "请最多输入250位汉字!"
				},
				profession : {
					maxlength : "请最多输入250位汉字!"
				},
				nation : {
					maxlength : "请最多输入50位汉字!"
				}
			},
			submitHandler : function(form) {
				dialog.close();
				if (index !== null) {
					//2.提取数据
					caseParty.getData("addCasePartyDiv", index);
				} else {
					caseParty.getData("addCasePartyDiv").save().createTable(
							"showCasePartyTable", "casePartyFieldset");
				}
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
		  var lianTime = document.getElementById('lianTime');
		  lianTime.onfocus = function(){
			  WdatePicker({dateFmt:'yyyy-MM-dd'});
		  }	 
	var msg = "${info}";
	if(msg != null && msg != ""){
		art.dialog.tips(msg,2);
	}
	
	$('#caseDetailName').autoResize({
		limit:500
	});
	});
</script>
<style type="text/css">
legend {
	padding: 0.5em 4em;
	font-size: 16px;
}

#outDiv {
	position: relative;
	margin: 6px;
}
</style>
</head>
<body>
	<form id="fileUploadForm" method="post"
		action="${path }/casehandle/weijicase/add" enctype="multipart/form-data">
		<fieldset class="ui-widget ui-corner-all">
			<legend class="ui-widget ui-widget-header ui-corner-all">移送违纪案件</legend>
			<table class="blues">
				   <tr>
						<td width="20%" align="right" class="tabRight">案件编号：</td>
						<td width="30%" style="text-align: left;" colspan="3"><input type="text" class="text" id="caseNo"
							name="caseNo" style="width: 91%"/>&nbsp;<font color="red">*必填</font></td>
					</tr>
					<c:if test="${needAssignTarget}">
					<tr>
					<td width="20%" align="right" class="tabRight">受理机关：</td>
					<td width="80%" style="text-align: left;" colspan="3"><input
						type="text" style="width: 91%" id="assignTargetId"
						name="assignTargetId" readonly="readonly"
						onfocus="showMenu(); return false;" /> <input type="hidden"
						name="assignTarget" /><font color="red">*必填</font></td>
					</tr>
					</c:if>
					<tr>
						<td width="20%" align="right" class="tabRight">案件名称：</td>
						<td width="30%" style="text-align: left;"><input type="text" class="text" id="caseName"
							name="caseName" />&nbsp;<font color="red">*必填</font></td>
						<td width="20%" align="right" class="tabRight">案件性质：</td>
						<td width="30%" style="text-align: left;"><input type="text" class="text"
							id="caseNature" name="caseNature" />
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="tabRight">涉案人姓名：</td>
						<td width="30%" style="text-align: left;"><input type="text" class="text" id="peopleName"
							name="peopleName" />&nbsp;<font color="red">*必填</font></td>
						<td width="20%" align="right" class="tabRight">涉案人职务：</td>
						<td width="30%" style="text-align: left;"><input type="text" class="text"
							id="peopleDuty" name="peopleDuty" /><font color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="tabRight">案件来源：</td>
						<td width="30%" style="text-align: left;"><input type="text" class="text"
							id="recordSrc" name="recordSrc" />&nbsp;
						</td>
						<td width="20%" align="right" class="tabRight">承办人：</td>
						<td width="30%" style="text-align: left;"><input type="text" class="text"
							id="undertaker" name="undertaker" />&nbsp;
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="tabRight">立案时间：</td>
						<td width="30%" style="text-align: left;"><input type="text" class="text"
							id="lianTime" name="lianTime" />&nbsp;
						</td>
						<td width="20%" align="right" class="tabRight">批准人：</td>
						<td width="30%" style="text-align: left;"><input type="text" class="text"
							id="approver" name="approver" />&nbsp;
						</td>
					</tr>
					<tr>
								<td width="20%" align="right" class="tabRight">案件详情附件：</td>
								<td width="30%" style="text-align: left;" colspan="3">
								<input type="file" id="caseDetailFiles" name="caseDetailFiles" style="width: 99%"/>
								&nbsp;<font color="red">文件大小限制在70M以内</font>
								</td>
							</tr>
							<tr>
								<td width="20%" align="right" class="tabRight">案件详情：</td>
								<td width="30%" style="text-align: left" colspan="3">
								<textarea rows="5" id="caseDetailName" name="caseDetailName"></textarea>
								&nbsp;<font color="red">*必填</font>
								</td>
							</tr>
				</table>
		</fieldset>
		<input type="submit" value="保存"/>
	</form>
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
		$("html")
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
</body>
</html>