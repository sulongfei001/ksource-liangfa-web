<%@page import="com.ksource.syscontext.Const"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path}/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/css/common.css" />
<link rel="stylesheet" href="${path}/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script type="text/javascript" src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path}/resources/script/sugar-1.0.min.js"></script>
<script type="text/javascript" src="${path}/resources/script/jqueryUtil.js"></script>
<script type="text/javascript">
	$(function() {
		jqueryUtil.formValidate({
			form : "orgForm",
			rules : {
				districtCode : {
					required : true
				},
				orgName : {
					required : true,
					remote : '${path}/system/org/checkName/',
					maxlength : 25
				},
				sampleName : {
					required : true,
					maxlength : 10
				},
				orgType : {
					required : true
				},
				industryType : {
					required : true
				},
				isLiangfaLeader : {
					required : true
				},
				telephone : {
					isTel : true
				},
				leader : {
					maxlength : 5
				},
				address : {
					maxlength : 100
				},
				note : {
					maxlength : 100
				}
			},
			messages : {
				districtCode : {
					required : "请选择行政区划!"
				},
				orgName : {
					required : "机构名称不能为空!",
					remote : "此名称已存在，请重新填写!",
					maxlength : "请最长输入25位汉字!"
				},
				sampleName : {
					required : "机构简称不能为空!",
					maxlength : "请最长输入10位汉字!"
				},
				orgType : {
					required : "请选择机构类型!"
				},
				industryType : {
					required : "请选择行业类型!"
				},
				isLiangfaLeader : {
					required : "请选择该单位是否是两法牵头单位!"
				},
				leader : {
					maxlength : "请最长输入5位汉字!"
				},
				address : {
					maxlength : "请最长输入100位汉字!"
				},
				telephone : {
					isTel : "请正确填写电话或手机号码!"
				},
				note : {
					maxlength : "请最长输入100位汉字!"
				}
			},
			submitHandler : function(form) {
				jqueryUtil.changeHtmlFlag([ 'orgName', 'leader', 'address',
						'note' ]);
				if($(form).find(':input[name="orgType"]').val()=='<%=Const.ORG_TYPE_XINGZHENG%>'){//行政执法机关
					var success=true;
					$.ajax({
						type: "GET",
						dataType:'json',
						url:"${path}/system/org/checkLicense",
						data:'districtCode='+$('#districtCode').val(),
						async:false,
						success:function(data){
							if(data.result){
								form.submit();
							}else{alert(data.msg);success= false;}
						}
					});
					return success;
				}else{
					form.submit();
				}
			}
		});
		
		//组织机构类型选择“行政机关”时，展示出“行业类型”下拉框并选择
		$("select[name='orgType']").change(function(){
			var value = $(this).val();
			if(value == 1){
				$("#industryTypeTd").hide();
				$("[displayType='industryType']").show();
				$("[displayType='industryType']:select").removeAttr("disabled");
			}else{
				$("[displayType='industryType']").hide();
				$("#industryTypeTd").show();
				$("[displayType='industryType']:select").attr("disabled",true);
			}
		});	

	});
	
	//一键增加用户
	function addUser(){
		$('#orgForm')[0].action="${path}/system/org/addUser";
		$('#orgForm').submit();
	}
</script>
</head>
<body>
<div class="panel">
  <fieldset class="fieldset" >
	<legend class="legend">添加组织机构</legend>
	<c:url var="action" value="/system/org/add"></c:url>
	<form:form action="${action}" method="post" id="orgForm">
		<table style="width: 98%" align="center" class="blues">
			<thead>
				<tr>
					<th colspan="4">上级组织机构&nbsp;</th>
				</tr>
			</thead>
			<c:if test="${!empty org.orgCode}">
				<tr>
					<td width="20%" class="tabRight">上级机构代码&nbsp;</td>
					<td width="30%" style="text-align: left;">${org.orgCode } 
					<input type="hidden" name="upOrgCode" value="${org.orgCode }" />
					<input type="hidden" name="upOrgPath" value="${org.orgPath }" />
					</td>
					<td width="20%" class="tabRight">上级机构名称&nbsp;</td>
					<td width="30%" style="text-align: left;">${org.orgName }</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">上级行政区划&nbsp;</td>
					<td width="30%" style="text-align: left;" colspan="3">${district.districtName}</td>
				</tr>
			</c:if>
			<c:if test="${empty org.orgCode}">
				<tr>
					<td width="20%" class="tabRight">上级行政区划&nbsp;</td>
					<td width="80%" style="text-align: left;" colspan="3">${district.districtName}</td>
				</tr>
			</c:if>
		</table>
		<br />
		<table style="width: 98%" align="center" class="blues">
			<thead>
				<tr>
					<th colspan="4">添加组织机构</th>
				</tr>
			</thead>
			<tr>
				<td width="20%" class="tabRight">行政区划</td>
				<td width="80%" style="text-align: left;" colspan="3">
				<select	name="districtCode" id="districtCode" style="width: 93%">
						<!-- <option value="">--请选择--</option> -->
						<c:forEach var="dis" items="${districtCodeList}">
							<option value="${dis.districtCode }">${dis.districtName
								}</option>
						</c:forEach>
				</select>&nbsp;<font color="red">*必填</font></td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">机构名称</td>
				<td width="30%" style="text-align: left;"><input type="text"
					class="text" name="orgName" id="orgName" />&nbsp;<font color="red">*必填</font></td>
				<td width="20%" class="tabRight">机构简称</td>
				<td width="30%" style="text-align: left;"><input type="text"
					class="text" name="sampleName" id="sampleName" />&nbsp;<font
					color="red">*必填</font></td>

			</tr>
			<tr>
				<td width="20%" class="tabRight">联系电话</td>
				<td width="30%" style="text-align: left;"><input type="text"
					class="text" id="telephone" name="telephone"
					title="请输入座机号(区号-座机号)或手机号" poshytip="" /></td>
				<td width="20%" class="tabRight">负责人</td>
				<td width="30%" style="text-align: left;"><input type="text"
					class="text" name="leader" id="leader" /></td>
			</tr>
			<c:choose>
				<c:when test="${empty org}">
					<tr>
						<dic:getDictionary var="orgTypeList" groupCode="orgType" />
						<td width="20%" class="tabRight">组织机构类型</td>
						<td width="30%" style="text-align: left;"><select
							name="orgType">
								<!-- <option value="">--请选择--</option> -->
								<c:forEach var="ele" items="${orgTypeList}">
									<option value="${ele.dtCode }">${ele.dtName }</option>
								</c:forEach>
						</select>&nbsp;<font color="red">*必填</font></td>
						<td width="20%" class="tabRight" displayType='industryType'>行业类型</td>
						<td width="30%" style="text-align: left;" displayType='industryType'>
							<select name="industryType" id="industryType">
								<c:forEach items="${industryInfoList }" var="industryInfo">
									<option value="${industryInfo.industryType }">${industryInfo.industryName }</option>
								</c:forEach>
							</select>&nbsp;<font color="red">*必填</font>
						</td>
						<!-- 组织机构类型选择“非行政单位”时，展示的空白列 -->
						<td id="industryTypeTd" width="21%" style="text-align: right;display:none;" colspan="2" ></td>
					</tr>
					<tr>
						<td width="20%" class="tabRight">是否是两法牵头单位</td>
						<td width="30%" style="text-align: left;"><select
							name="isLiangfaLeader" id="isLiangfaLeader">
								<!-- <option value="">--请选择--</option> -->
								<option value="0">不是</option>
								<option value="1">是</option>
						</select>&nbsp;<font color="red">*必填</font></td>
						<td width="20%" class="tabRight">机构地址</td>
						<td width="30%" style="text-align: left;">
							<input type="text"	name="address" class="text" />
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<input type="hidden" name="orgType" value="${org.orgType }" />
						<input type="hidden" name="industryType" value="${org.industryType }" />
						<input type="hidden" name="orderNo" value="${org.orderNo }" />
						<td width="20%" class="tabRight">机构地址</td>
						<td width="30%" style="text-align: left;">
							<input type="text"	class="text" name="address" />
						</td>
						<td width="20%" class="tabRight">是否是两法牵头单位</td>
						<td width="30%" style="text-align: left;"><select
							name="isLiangfaLeader" id="isLiangfaLeader">
								<!-- <option value="">--请选择--</option> -->
								<option value="0">不是</option>
								<option value="1">是</option>
						</select>&nbsp;<font color="red">*必填</font></td>
					</tr>
				</c:otherwise>
			</c:choose>
			<tr>
				<td width="20%" class="tabRight">办案公安部门所属区划</td>
				<td width="80%" style="text-align: left;" colspan="3">
				<select	name="acceptDistrictCode" id="acceptDistrictCode" style="width: 93%">
						<!-- <option value="">--请选择--</option> -->
						<c:forEach var="dis" items="${acceptDistrict}">
							<option value="${dis.districtCode }">${dis.districtName
								}</option>
						</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">备注</td>
				<td colspan="3" style="text-align: left;">
				<textarea name="note" id="note" cols="30" rows="3"></textarea></td>
			</tr>
<!-- 			<tr>
				<td align="center" colspan="4">
					<input type="submit" value="保 存" id="addOrg" class="btn_small"/>
				 	<input type="reset" value="重 置"  class="btn_small"/>
				 </td>
			</tr> -->
		</table>
		<table style="width: 98%;margin-top:10px;">
			<tr>
				<td align="center"> 
					<input type="submit" value="保 存" id="addOrg" class="btn_small"/>
					<input type="reset" value="重 置"  class="btn_small"/>
					<input type="button" value="一键增加用户 " onclick="addUser()" class="btn_max"/>
				</td>
			</tr>
		</table>
	</form:form>
	</fieldset>
	</div>
</body>
</html>