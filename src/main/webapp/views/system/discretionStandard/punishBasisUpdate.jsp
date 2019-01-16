<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
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
<script type="text/javascript" src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
<script	type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/lib/jquery.metadata.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"></script>
<script	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
	$(function() {
		jqueryUtil.formValidate({
			form : "punishBasisUpdateForm", 
			rules : {
				clause : {
					required : true
				},
				basisInfo : {
					required : true
				}
			},
			messages : {
				clause : {
					required : "条款不能为空!"
				},
				basisInfo : {
					required : "行政处罚依据不能为空!"
				}
			},
			submitHandler : function(form) {
				var termArray=new Array();
				$("#TermInfoDiv div").each(function(i,n){
					var tmpSeriaJson=$(n).find(":input").serializeArray();
					var tmp=new Object();
					$.each(tmpSeriaJson,function(i,n){
						tmp[n.name]=n.value;
					});
					termArray.push(tmp);
				});
				
				var termJson=$.toJSON(termArray);
				$('#termJson').val(termJson);
				 
				form.submit();
			}
		});
		
	});
	
	
	function back(){
	    window.location.href='<c:url value="/system/punishBasis/back?industryType=${industryType}"/>';
	}
	
	/* function delTerm(obj,termId){
		$.ajax({
			type:"post",
			url:"${path}/system/punishBasis/deleteTerm?termId="+termId,
			success:function(result) {
				$(obj).parent().remove();
			}
		}) ;
	} */
</script>
</head>
<body>
	<!-- 修改 行政处罚依据-->
	<div class="panel" >
		<fieldset class="fieldset" >
			<legend class="legend">修改行政处罚依据</legend>
			<form id="punishBasisUpdateForm" action="${path }/system/punishBasis/update" method="post">
				<input type="hidden" id="basisId" name="basisId" value="${punishBasis.basisId}"/>
				<input type="hidden" id="industryType" name="industryType" value="${industryType}"/>
				<input type="hidden" id="termJson" name="termJson" value=""/>
				<table id="punishBasisUpdateTable" class="table_add"  width="90%">
					<tr>
						<td class="tabRight" width="20%">条款：</td>
						<td style="text-align: left" width="80%">
							<input type="text" class="text" name="clause" style="width: 80%" value="${punishBasis.clause }"/>
							&nbsp;<font color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">行政处罚依据：</td>
						<td style="text-align: left" width="80%">
							<textarea cols="50" rows="10" class="text" name="basisInfo" style="width: 80%">${punishBasis.basisInfo}</textarea>
							&nbsp;<font color="red">*必填</font>
						</td>
					</tr>
				</table>
				<br /><br />
				<fieldset class="fieldset" id="casePartyFieldset" style="width: 98%;margin-left: 10px;">
					<legend class="legend">
						行政处罚依据项：<a href="javascript:void(0);" onclick="addTermInfo();" style="color: blue">[添加]</a>
					</legend>
					<div id="TermInfoDiv" >
						<c:if test="${not empty terms}">
							<c:forEach items="${terms }" var="term">
								<div style="margin-left: 2%;margin-top:2%;" class="termDivClass">
									<input type="hidden" name="termId" value="${term.termId}"/>
									行政处罚依据项：<textarea class="text" name="termInfo" rows="3">${term.termInfo }</textarea>
									<a class="link del" onclick="$(this).parent().remove();" style="margin-left: 10px;">删除</a>
								</div>
							</c:forEach>
						</c:if>
					</div>
				</fieldset>
			
				<div style="margin-left: 37%; margin-top: 5px">
					<input type="submit" class="btn_small" value="保 存" /> 
					<input type="button" class="btn_small" value="返 回" onclick="back()" />
				</div>
			</form>
		</fieldset>
		
		<textarea id="termDiv" style="display:none;">
			<div style="margin-left: 2%;margin-top:2%;" class="termDivClass">
				<input type="hidden" name="termId" value=""/>
				行政处罚依据项：<textarea class="text" name="termInfo" rows="3"/>
				<a class="link del" onclick="$(this).parent().remove();" style="margin-left: 10px;">删除</a>
			</div>
		</textarea>
<script type="text/javascript">
		/* var subindex = 1;
		var lastIndex = $("input[name='sort']").last().val();
		if(lastIndex != null && lastIndex != ""){
			subindex = parseInt(lastIndex)+1;
		} */
		var subtpl = $.format($("#termDiv").val());
		
		function addTermInfo() {
			$("#TermInfoDiv").append(subtpl);
		}
</script>

</div>
</body>
</html>