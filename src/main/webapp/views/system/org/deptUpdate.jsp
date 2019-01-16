<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
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
					  data:{orgId:${dept.upOrgCode},deptId:${dept.orgCode}}}
					  ,maxlength:25},
					  telephone:{isTel:true},
					  leader:{maxlength:5},
					  address:{maxlength:100},
					  note:{maxlength:100}
			},
			messages:{
					orgName:{required:"机构名称不能为空!",remote:"此名称已存在，请重新填写!",maxlength:"请最长输入25位汉字!"},
					leader:{maxlength:"请最长输入5位汉字!"},
					address:{maxlength:"请最长输入100位汉字!"},
					telephone:{isTel:"请输入正确的电话号码!"},
					note:{maxlength:"请最长输入100位汉字!"}
			},
			submitHandler:function(){
					    jqueryUtil.changeHtmlFlag(['orgName','leader','address','note']);
			    		$('#orgForm')[0].submit();
		    	}
		 });
});
	function back(orgId){
		 window.location.href='<c:url value="/system/org/search/"/>'+orgId;
	}
</script>
</head>
<body>
<div class="panel">
	<fieldset class="fieldset">
		<legend class="legend">部门信息修改</legend>
			<c:url var="action" value="/system/org/updateDept"></c:url>
				<form:form action="${action}" method="post" id="orgForm">
					<table style="width:98%;"  class="blues" align="center">
<!-- 						<thead>
							<tr>
								<th colspan="4">
									修改部门信息
								</th>
							</tr>
						</thead> -->
						<tr>
							<td width="20%" class="tabRight">
								部门名称
							</td>
							<td width="80%" style="text-align: left;" colspan="3">
								<input type="text" class="text" name="orgName"
									value="${dept.orgName }" style="width: 93%" /><font color="red">*必填</font>
							</td>
							</tr>
						<tr>
							<td width="20%" class="tabRight">
								负 责 人
							</td>
							<td width="30%" style="text-align: left;">
								<input type="text" class="text" name="leader" value="${dept.leader }" />
							</td>
								<td width="20%" class="tabRight">
								联系电话
							</td>
							<td width="30%" style="text-align: left;">
								<input type="text" class="text" name="telephone"
									value="${dept.telephone }" title="请输入座机号(区号-座机号)或手机号"  />
							</td>
						</tr>
						<tr>
							<td width="20%" class="tabRight">
								机构地址
							</td>
							<td width="30%" style="text-align: left;" colspan="3">
								<input type="text" style="width: 93%" class="text" name="address"  value="${dept.address}" />
							</td>
						</tr>
						<tr>
							<td width="20%" class="tabRight">
								备 注
							</td>
							<td colspan="3" width="30%" style="text-align: left;" >
								<textarea name="note" rows="2" cols="40">${dept.note }</textarea>
							</td>
						</tr>
					</table>
					<table  style="width:98%; margin-top: 5px;">
						<tr>
							<td align="center">
								<input type="hidden" name="orgCode" id="orgCode" value="${dept.orgCode}"></input>
								<input type="hidden" name="industryType" value="${dept.industryType }"/>
								<input type="submit" class="btn_small" value="保 存"/>
								<input type="reset" class="btn_small" value="重 置" />
								 <input type="button" class="btn_small" value="返 回" onclick="javascript:window.location.href='${path }/system/org/deptMain/${dept.upOrgCode}';"/> 
							</td>
						</tr>
					</table>
				</form:form>
				</fieldset>
</div>
</body>
</html>