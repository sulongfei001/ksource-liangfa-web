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
<script type="text/javascript">
	$(function() {
		var birthday = document.getElementById('birthdayForAdd');
		birthday.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}		
		jqueryUtil.formValidate({
			form : "peopleLibAddForm", 
			rules : {
				idsNo : {
					required : true,
					isIDCard : true,
					remote : "${path }/system/peopleLib/checkIdsNo"
				},
				name : {
					required : true,
					maxlength : 50
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
				},
				residence : {
					maxlength : 250
				}
			},
			messages : {
				idsNo : {
					required : "身份证号不能为空！",
					isIDCard : "请填写正确的身份证号码!",
					remote : "此身份证号已存在！"
				},
				name : {
					required : "姓名不能为空!",
					maxlength : "请最多输入50位汉字!"
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
				},
				residence : {
					maxlength : "请最多输入250位汉字!"
				}
			},
			submitHandler : function(form) {
				form.submit();
			}
		});
	});
	function back(){
	    window.location.href='<c:url value="/system/peopleLib/back"/>';
	}
</script>
</head>
<body>
	<!-- 新增 个人库信息-->
	<div class="panel" >
		<fieldset class="fieldset" >
			<legend class="legend">新增个人库信息</legend>
			
			<form id="peopleLibAddForm" action="${path }/system/peopleLib/add"
				method="post">
				<table id="peopleLibAddTable" class="table_add"  width="90%">
					<tr>
						<td class="tabRight" width="20%">身份证号</td>
						<td style="text-align: left" width="30%"><input type="text" class="text" name="idsNo"
							maxlength="18" id="idsNoForAdd" />&nbsp;<font color="red">*必填</font></td>
						<td class="tabRight" width="20%">姓名</td>
						<td style="text-align: left" width="30%"><input type="text" class="text" name="name" />&nbsp;<font color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">性别</td>
						<td style="text-align: left" width="30%"><dict:getDictionary
								var="sexList" groupCode="sex" /> <select name="sex"
							 	 id="sexForAdd">
								<option value="">--请选择--</option>
								<c:forEach items="${sexList}" var="domain">
									<option value="${domain.dtCode}">${domain.dtName}</option>
								</c:forEach>
						</select></td>
						<td class="tabRight" width="20%">文化程度</td>
						<td style="text-align: left" width="30%"><dict:getDictionary
								var="educationLevelList" groupCode="educationLevel" /> <select
							name="education"  >
								<option value="">--请选择--</option>
								<c:forEach items="${educationLevelList}" var="domain">
									<option value="${domain.dtCode}">${domain.dtName}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">民族</td>
						<td style="text-align: left" width="30%">
						 <dict:getDictionary
								var="nationList" groupCode="nation" />
							 <select name="nation">
								<!-- <option value="">--请选择--</option> -->
								<c:forEach items="${nationList}" var="domain">
									<option value="${domain.dtCode}">${domain.dtName}</option>
								</c:forEach>
						</select>
						</td>
						<td class="tabRight" width="20%">籍贯</td>
						<td style="text-align: left" width="30%"><input type="text" class="text"
							name="birthplace" /></td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">工作单位和职务</td>
						<td style="text-align: left" width="30%"><input type="text" class="text"
							name="profession" /></td>
						<td class="tabRight" width="20%">联系电话</td>
						<td style="text-align: left" width="30%"><input type="text" class="text" name="tel"
							title="请输入座机号(区号-座机号)或手机号"  /></td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">户籍地</td>
						<td style="text-align: left" width="30%"><input type="text" class="text"
							name="residence" /></td>
						<td class="tabRight" width="20%">特殊身份</td>
						<td style="text-align: left" width="30%">
						 <dict:getDictionary
								var="specialIdentityList" groupCode="specialIdentity" />
							 <select name="specialIdentity">
								<option value="">--请选择--</option>
								<c:forEach items="${specialIdentityList}" var="domain">
									<option value="${domain.dtCode}">${domain.dtName}</option>
								</c:forEach>
						</select>
						</td>
					</tr>					
					<tr>
						<td class="tabRight" width="20%">出生日期</td>
						<td style="text-align: left" width="30%"><input type="text" class="text"
							name="birthday" readonly="readonly" id="birthdayForAdd" /></td>
						<td class="tabRight">现住址</td>
						<td style="text-align: left"><input type="text" class="text"
							name="address" /></td>
					</tr>
				</table>
				<div style="margin-left: 37%; margin-top: 5px">
					<input type="submit" class="btn_small" value="保 存" /> 
					<input type="reset" class="btn_small" value="重 置" />
					<input type="button" class="btn_small" value="返 回" onclick="back()" />
				</div>
			</form>
		</fieldset>
	</div>
	<script type="text/javascript">
			$(function() {
				$("#idsNoForAdd")
						.blur(
								function() {
									if (checkIDCard($(this).val())) {
										//自动填充	
										var bithdayAndSexArrays = getBithdayAndSexFromIds($(
												this).val());
										$("#birthdayForAdd").val(
												bithdayAndSexArrays[0]);
										$("#sexForAdd option")
												.each(
														function() {
															if ($(this).html() == bithdayAndSexArrays[1]) {
																$("#sexForAdd")
																		.val(
																				$(
																						this)
																						.val());
															}
														});
									}
								});

			});
		</script>
</body>
</html>