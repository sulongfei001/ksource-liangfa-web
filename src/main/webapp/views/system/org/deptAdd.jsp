<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
			jqueryUtil.formValidate({
				form:"orgForm",
				rules:{
					orgName:{
						required:true,
						remote:{url:'${path}/system/org/checkDeptName/',
						  	  type:'post',
							  data:{orgId:${upOrg.orgCode }}
						}
						,maxlength:25
					},
				 	telephone:{isTel:true},
				 	leader:{maxlength:5},
				 	address:{maxlength:100},
				 	note:{maxlength:100}
				},
				messages:{
					orgName:{required:"机构名称不能为空!",remote:"此名称已存在，请重新填写!",maxlength:"请最长输入25位汉字!"},
					leader:{maxlength:"请最长输入5位汉字!"},
					address:{maxlength:"请最长输入100位汉字!"},
					telephone:{isTel:"请正确填写电话或手机号码!"},
					note:{maxlength:"请最长输入100位汉字!"}
				},
		    	submitHandler:function(){
					    jqueryUtil.changeHtmlFlag(['orgName','leader','address','note']);
			    		$('#orgForm')[0].submit();
		    	}
			 });
			
});

</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">部门添加</legend>
<c:url var="action" value="/system/org/addDept"></c:url>
<form:form action="${action}" method="post" id="orgForm">
	<input type="hidden" name="upOrgCode" value="${upOrg.orgCode }"></input>
	<input type="hidden" name="districtCode" value="${upOrg.districtCode }"></input>
	<input type="hidden" name="orgType" value="${upOrg.orgType }"/>
	<input type="hidden" name="industryType" value="${upOrg.industryType }"/>
	<table style="width:98%;" align="center" class="blues">
<!-- 		<thead>
			<tr>
				<th colspan="4">添加部门</th>
			</tr>
		</thead> -->
		<tr>
			<td width="20%" class="tabRight">部门名称</td>
			<td width="80%" style="text-align: left;" colspan="3"><input type="text" style="width: 93%" class="text" name="orgName" id="orgName"  value="${upOrg.orgName }两法办"
				/>&nbsp;<font color="red">*必填</font></td>
			
		</tr>
		<tr>
			<td width="20%" class="tabRight">负 责 人</td>
			<td width="30%" style="text-align: left;"><input type="text" class="text" name="leader"  id="leader"/></td>
			<td width="20%" class="tabRight">联系电话</td>
			<td width="30%" style="text-align: left;"><input type="text" class="text" id="telephone"
			name="telephone" title="请输入座机号(区号-座机号)或手机号"  /></td>
		</tr>

		 <tr>
			<td width="20%" class="tabRight">机构地址</td>
			<td width="80%" style="text-align: left;" colspan="3">
				<input type="text" style="width: 93%" class="text" name="address" id="address"/>
			</td>
		</tr>
		<tr>
			<td width="20%" class="tabRight">备   注</td>
			<td colspan="3" width="30%" style="text-align: left;" colspan="3">
			<input type="text" class="text" name="note" id="note"/></td>
		</tr>
	</table>
	<table style="width: 98%;margin-top: 5px;" align="center">
			<tr>
				<td align="center">
					<input type="submit" value="保 存" class="btn_small" id="addOrg" /> 
			  		<input type="reset" value="重 置" class="btn_small" /> 
			 		<input type="button" value="返 回" class="btn_small" onclick="javascript:window.location.href='${path }/system/org/deptMain/${upOrg.orgCode}';"/> 
			</td>
			</tr>
	</table>
</form:form>
</fieldset>
</div>
</body>
</html>