<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"	href="${path }/resources/css/common.css" />
<link rel="stylesheet"	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"	type="text/css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script	type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/lib/jquery.metadata.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"></script>
<script	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
	$(function() {
		jqueryUtil.formValidate({
			form : "discretionStandardUpdateForm", 
			rules : {
				illegalSituation : {
					required : true
				},
				standard : {
					required : true
				}
			},
			messages : {
				illegalSituation : {
					required : "违法行为表现情形不能为空!"
				},
				standard : {
					required : "行政处罚标准不能为空!"
				}
			},
			submitHandler : function(form) {
				form.submit();
			}
		});
		
	});
	
	
	function back(){
	    window.location.href='<c:url value="/system/discretionStandard/back?industryType=${industryType}&basisId=${discretionStandard.basisId}"/>';
	}
	
</script>
</head>
<body>
	<!-- 新增 行政处罚裁量标准-->
	<div class="panel" >
		<fieldset class="fieldset" >
			<legend class="legend">修改行政处罚裁量标准</legend>
			<form id="discretionStandardUpdateForm" action="${path }/system/discretionStandard/update" method="post">
				<input type="hidden" id="industryType" name="industryType" value="${industryType}"/>
				<input type="hidden" id="basisId" name="basisId" value="${discretionStandard.basisId}"/>
				<input type="hidden" id="standardId" name="standardId" value="${discretionStandard.standardId}"/>
				<table id="punishBasisUpdateTable" class="table_add"  width="90%">
					<tr>
						<td class="tabRight" width="20%">行政处罚依据项：</td>
						<td style="text-align: left" width="80%">
							<select name="termId" style="width: 50%">
								<option value="">--请选择--</option>
								<c:forEach items="${termList}" var="term">
									<option value="${term.termId}" <c:if test="${discretionStandard.termId ==term.termId }">selected</c:if> >${term.termInfo}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">裁量阶次：</td>
						<td style="text-align: left" width="80%">
							<dic:getDictionary var="discretionLevelList" groupCode="discretionLevel"/>
							<select name="discretionLevel" style="width: 50%">
								<option value="">--请选择--</option>
								<c:forEach items="${discretionLevelList}" var="domain">
									<option value="${domain.dtCode}" <c:if test="${domain.dtCode==discretionStandard.discretionLevel }">selected</c:if> >${domain.dtName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">违法行为表现情形：</td>
						<td style="text-align: left" width="80%">
							<textarea cols="50" rows="5" class="text" name="illegalSituation" style="width: 80%">${discretionStandard.illegalSituation}</textarea>
							&nbsp;<font color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">行政处罚标准：</td>
						<td style="text-align: left" width="80%">
							<textarea cols="50" rows="5" class="text" name="standard" style="width: 80%">${discretionStandard.standard}</textarea>
							&nbsp;<font color="red">*必填</font>
						</td>
					</tr>
				</table>
				<div style="margin-left: 37%; margin-top: 5px">
					<input type="submit" class="btn_small" value="保 存" /> 
					<input type="button" class="btn_small" value="返 回" onclick="back()" />
				</div>
			</form>
		</fieldset>
</div>
</body>
</html>