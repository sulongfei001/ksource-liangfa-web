<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
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
				orgName : {
					required : true,
					remote : {
						url : '${path}/system/org/checkName/',
						type : 'post',
						data : {
							orgId : function() {
								return $('#orgCode').val();
							}
						}
					},
					maxlength : 25
				},
				sampleName : {
					required : true,
					maxlength : 10
				},
				telephone : {
					isTel : true
				},
				isLiangfaLeader : {
					required : true
				},
				industryType : {
					required : true
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
				orgName : {
					required : "机构名称不能为空!",
					remote : "此名称已存在，请重新填写!",
					maxlength : "请最长输入25位汉字!"
				},
				sampleName : {
					required : "机构简称不能为空!",
					maxlength : "请最长输入10位汉字!"
				},
				isLiangfaLeader : {
					required : "请选择该单位是否是两法牵头单位!"
				},
				industryType : {
					required : "请选择行业类型!"
				},
				leader : {
					maxlength : "请最长输入5位汉字!"
				},
				address : {
					maxlength : "请最长输入100位汉字!"
				},
				telephone : {
					isTel : "请输入正确的电话号码!"
				},
				note : {
					maxlength : "请最长输入100位汉字!"
				}
			},
			submitHandler : function() {
				jqueryUtil.changeHtmlFlag([ 'orgName', 'leader', 'address',
						'note' ]);
				$('#orgForm')[0].submit();
			}
		});
	});
	function back(orgId) {
		window.location.href = '<c:url value="/system/org/search/"/>' + orgId;
	}
</script>
</head>
<body>
<div class="panel">
  <fieldset class="fieldset">
  	<legend class="legend">组织机构更新</legend>
	<c:url var="action" value="/system/org/update"></c:url>
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
					<td width="30%" style="text-align: left;">${org.upOrgCode} <input
						type="hidden" name="upOrgCode" value="${org.upOrgCode}" /></td>
					<td width="20%" class="tabRight">上级机构名称&nbsp;</td>
					<td width="30%" style="text-align: left;">${upOrg.orgName }</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">上级行政区划&nbsp;</td>
					<td width="30%" style="text-align: left;" colspan="3">${upDis.districtName}</td>
				</tr>
			</c:if>
			<c:if test="${empty org.orgCode}">
				<tr>
					<td width="20%" class="tabRight">上级行政区划&nbsp;</td>
					<td width="80%" style="text-align: left;" colspan="3">${upDis.districtName}</td>
				</tr>
			</c:if>
		</table>
		<br />
		<table style="width:98%" align="center" class="blues">
			<thead>
				<tr>
					<th colspan="4">组织机构更新：(机构代码：${org.orgCode})</th>
				</tr>
			</thead>
			<tr>
				<td width="20%" class="tabRight">行政区划</td>
				<td width="30%" colspan="3" style="text-align: left;">
					<input type="text" class="text" disabled="disabled" style="width: 92.5%" value="${dis.districtName }"/>
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">机构名称</td>
				<td width="30%" style="text-align: left;"><input type="text"
					class="text" name="orgName" value="${org.orgName }" /><font
					color="red">*必填</font></td>
				<td width="20%" class="tabRight">机构简称</td>
				<td width="30%" style="text-align: left;"><input type="text"
					class="text" name="sampleName" value="${org.sampleName }" /><font
					color="red">*必填</font></td>
			</tr>
			<tr>

				<td width="20%" class="tabRight">联系电话</td>
				<td width="30%" style="text-align: left;"><input type="text"
					class="text" name="telephone" value="${org.telephone }"
					title="请输入座机号(区号-座机号)或手机号" poshytip="" /></td>
				<td width="20%" class="tabRight">负责人</td>
				<td width="30%" style="text-align: left;"><input type="text"
					class="text" name="leader" value="${org.leader }" /></td>
			</tr>
			<tr>

				<td width="20%" class="tabRight">机构地址</td>
				<td width="30%" style="text-align: left;" colspan="3">
				<input type="text" class="text" name="address" value="${org.address}" style="width: 92.5%"/>
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">是否是两法牵头单位</td>
				<td colspan="3" style="text-align: left;">
				<select	name="isLiangfaLeader" id="isLiangfaLeader"	value="${org.isLiangfaLeader}" style="width: 30%">
						<option value="">--请选择--</option>
						<option value="1"
							<c:if test="${org.isLiangfaLeader==1}">selected</c:if>>是</option>
						<option value="0"
							<c:if test="${org.isLiangfaLeader==0}">selected</c:if>>不是</option>
				</select>&nbsp;<font color="red">*必填</font></td>
			</tr>
			<c:if test="${org.orgType==1}">
				<tr>
				<td width="20%" class="tabRight">行业类型</td>
				<td colspan="3" style="text-align: left;">
				<select	name="industryType" id="industryType" style="width: 30%">
					<option value="">--请选择--</option>
					<c:forEach items="${industryInfoList }" var="industryInfo">
						<option <c:if test="${org.industryType==industryInfo.industryType}">selected</c:if> value="${industryInfo.industryType }">${industryInfo.industryName }</option>
					</c:forEach>
				</select>&nbsp;<font color="red">*必填</font></td>
			</tr>
			</c:if>
			<tr>
				<td width="20%" class="tabRight">办案公安部门所属区划</td>
				<td width="80%" style="text-align: left;" colspan="3">
				<select	name="acceptDistrictCode" id="acceptDistrictCode" style="width: 93%">
					<option value="">--请选择--</option>
					<c:forEach items="${acceptDistrict }" var="dis">
						<option <c:if test="${org.acceptDistrictCode==dis.districtCode}">selected</c:if> value="${dis.districtCode }">${dis.districtName }</option>
					</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">备注</td>
				<td colspan="3" width="30%" style="text-align: left;"><textarea
						name="note" rows="2" cols="40">${org.note }</textarea></td>
			</tr>
		</table>
		<table style="width: 98%;margin-top:5px;" align="center">
			<tr>
				<td align="center">
					<input type="hidden" name="orgCode" id="orgCode" value="${org.orgCode}"></input>
					<%-- <input type="hidden" name="industryType" id="industryType" value="${org.industryType}"></input> --%>
					<input	type="submit" value="保 存" class="btn_small"/> 
					<input type="reset" value="重 置" class="btn_small"/>
				</td>
			</tr>
		</table>
	</form:form>
	</fieldset>
	</div>
</body>
</html>