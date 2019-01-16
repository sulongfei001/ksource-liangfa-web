<%@page import="com.ksource.liangfa.domain.CrimeCaseExt"%>
<%@page import="com.ksource.liangfa.domain.CaseBasic"%>
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
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
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
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/caseParty-min.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/caseCompany-min.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/autoresize.jquery.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
<script>
$(function(){
		$.getJSON(
						'<c:url value="/casehandle/case/getCasePartyByCaseId/${caseBasic.caseId}"/>',
						function(data) {
							caseParty.showCasePartys(data,
									'showCasePartyTable', 'showCasePartyDiv');
						});
		$.getJSON(
						'<c:url value="/casehandle/case/getCaseCompanyByCaseId/${caseBasic.caseId}"/>',
						function(data) {
							caseCompany.showCaseCompanys(data,
									'showCaseCompanyTable',
									'showCaseCompanyDiv');
						});
		if ($('#attaDiv>span').length >= 5) {
			$('#fileadd').hide();
		}
	});

	function openCasePartyInfo() {
		caseParty.clearData('casePartyTable');
		index = null;//全局变量 ,用来保存索引
		if (arguments.length === 2 && arguments[0] === 'update') {
			index = $(arguments[1]).attr('index');
			caseParty.setData('addCasePartyDiv', index);
			//用一全局变量　给出标志，验证时不验证本身
			window.notValid = index;
		} else {
			if (window.notValid) {
				window.notValid = null;
			}
		}
		caseupdate_dialog = art.dialog({
			title : '当事人信息',
			content : document.getElementById('addCasePartyDiv'),
			lock : true,
			opacity : 0.1,
			yesFn : function() {
				$('#casePartyForm').submit();
				return false;
			},
			noFn : function() {
				caseupdate_dialog.close();
			}
		});
	}
	function openCaseCompanyInfo() {
		caseCompany.clearData('caseCompanyTable');
		index = null;//全局变量 ,用来保存索引
		if (arguments.length === 2 && arguments[0] === 'update') {
			index = $(arguments[1]).attr('index');
			caseCompany.setData('addCaseCompanyDiv', index);
			//用一全局变量　给出标志，验证时不验证本身
			window.notValid = index;
		} else {
			if (window.notValid) {
				window.notValid = null;
			}
		}
		dialog = art.dialog({
			title : '单位信息',
			content : document.getElementById('addCaseCompanyDiv'),
			lock : true,
			opacity : 0.1,
			yesFn : function() {
				$('#caseCompanyForm').submit();
				return false;
			},
			noFn : function() {
				dialog.close();
			}
		});
	}
</script>
<style type="text/css">
#outDiv {
	position: relative;
	margin: 6px;
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
	<div class="panel">
	<form:form id="fileUploadForm" action="${path}/casehandle/case/o_update_crime_case"
		enctype="multipart/form-data" modelAttribute="caseBasic">
		<form:hidden path="caseId" />
		<fieldset class="fieldset">
			<legend class="legend">涉嫌犯罪案件详细信息</legend>
			<table class="blues" style="width: 98%;margin-left: 10px;">
				<tr>
					<td width="20%" align="right" class="tabRight">案件编号：</td>
					<td width="30%" style="text-align: left;">${caseBasic.caseNo }</td>
					<td width="20%" align="right" class="tabRight">案件名称：</td>
					<td width="30%" style="text-align: left;">${caseBasic.caseName }</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">涉案金额（元）：</td>
					<td width="30%" style="text-align: left;">
					<fmt:formatNumber value="${caseBasic.amountInvolved }" pattern="000.##"></fmt:formatNumber>
					</td>
					<td width="20%" align="right" class="tabRight">案件来源：</td>
					<td width="30%" style="text-align: left;">
					<dict:getDictionary var="recordSrc" groupCode="caseSource" dicCode="${caseBasic.recordSrc}"/>
                    ${recordSrc.dtName}
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">移送单位：</td>
					<td width="30%" style="text-align: left;">
					${caseBasic.yisongUnit }
					</td>
					<td width="20%" align="right" class="tabRight">受案单位：</td>
					<td width="30%" style="text-align: left;">
					${caseBasic.acceptUnitName }
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">移送时间</td>
					<td width="30%" style="text-align: left;">
						<fmt:formatDate value="${caseBasic.yisongTime }" pattern="yyyy-MM-dd" />
					</td>
					<td width="20%" align="right" class="tabRight">移送文书号：</td>
					<td width="30%" style="text-align: left;">
					${crimeCaseExt.yisongFileNo }
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">是否已作出行政处罚决定：</td>
					<td width="30%" style="text-align: left;">
						<c:if test="${crimeCaseExt.isPenalty==1 }">
							是
						</c:if>
						<c:if test="${crimeCaseExt.isPenalty==0 }">
							否
						</c:if>
					</td>
					<td width="20%" align="right" class="tabRight"></td>
					<td></td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">涉嫌犯罪案件移送书：</td>
					<td width="80%" style="text-align: left;" colspan="3" id="files">
					<c:set var="yisongFileId" value='f${crimeCaseExt.yisongFileId }'></c:set>
					<span id="yisongFile_span">
					<a href="${path}/download/caseAtta/${attaMap[yisongFileId].id }">${attaMap[yisongFileId].attachmentName}</a>
					</span>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">涉嫌犯罪案件情况的调查报告：</td>
					<td width="80%" style="text-align: left;" colspan="3" id="files">
					<!-- EL表达式取MAP的值时，不能用数字来当KEY -->
					<c:set var="detailFileId" value='f${crimeCaseExt.detailFileId }'></c:set>
					<span id="detailFile_span">
					<a href="${path}/download/caseAtta/${attaMap[detailFileId].id }">${attaMap[detailFileId].attachmentName}</a>
					</span>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">涉案物品清单：</td>
					<td width="80%" style="text-align: left;" colspan="3" id="files">
					<c:set var="goodsFileId" value='f${crimeCaseExt.goodsFileId}'></c:set>
					<span id="detailFile_span">
					<a href="${path}/download/caseAtta/${attaMap[goodsFileId].id }">${attaMap[goodsFileId].attachmentName}</a>
					</span>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">主要物证和书证目录：</td>
					<td width="80%" style="text-align: left;" colspan="3" id="files">
					<c:set var="proofFileId" value='f${crimeCaseExt.proofFileId}'></c:set>
					<span id="detailFile_span">
					<a href="${path}/download/caseAtta/${attaMap[proofFileId].id }">${attaMap[proofFileId].attachmentName}</a>
					</span>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">有关检验报告或者鉴定结论：</td>
					<td width="80%" style="text-align: left;" colspan="3" id="files">
					<c:set var="checkFileId" value='f${crimeCaseExt.checkFileId}'></c:set>
					<span id="checkFile_span">
					<a href="${path}/download/caseAtta/${attaMap[checkFileId].id }">${attaMap[checkFileId].attachmentName}</a>
					</span>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">其他有关涉嫌犯罪的材料目录：</td>
					<td width="80%" style="text-align: left;" colspan="3" id="files">
					<c:set var="otherFileId" value='f${crimeCaseExt.otherFileId}'></c:set>
					<span id="otherFile_span">
					<a href="${path}/download/caseAtta/${attaMap[otherFileId].id }">${attaMap[otherFileId].attachmentName}</a>
					</span>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">行政处罚决定书：</td>
					<td width="80%" style="text-align: left;" colspan="3" id="files">
					<c:set var="penaltyFileId" value='f${crimeCaseExt.penaltyFileId}'></c:set>
					<span id="penaltyFile_span">
					<a href="${path}/download/caseAtta/${attaMap[penaltyFileId].id }">${attaMap[penaltyFileId].attachmentName}</a>
					</span>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">案情简介：</td>
					<td width="30%" style="text-align: left" colspan="3">
					${caseBasic.caseDetailName }
					</td>
				</tr>			
				</table>
			<br/>
			<fieldset class="fieldset" id="casePartyFieldset" style="width: 98%;margin-left: 10px;">
				<legend class="legend">
					案件当事人信息
				</legend>
				<div id="showCasePartyDiv"></div>
			</fieldset>
			<fieldset class="fieldset" id="caseCompanyFieldset" style="width: 98%;margin-left: 10px;">
				<legend class="legend">
					案件单位信息
				</legend>
				<div id="showCaseCompanyDiv"></div>
			</fieldset>
			<input type="hidden" id="casePartyJson" name="casePartyJson" value="" />
			<input type="hidden" id="caseCompanyJson" name="caseCompanyJson" value="" /> 
			<input type="hidden" name="procKey" id="procKey" value="${procKey}" /> 
		</fieldset>
	</form:form>
	</div>
	<table id="showCasePartyTable" class="blues"
		style="display: none; margin: 10px; width: 500px; float: left;">
		<tr>
			<td class="tabRight">身份证号</td>
			<td style="text-align: left;"><span name="idsNo"></span></td>
			<td class="tabRight">姓名</td>
			<td style="text-align: left;"><span name="name"></span></td>
		</tr>
		<tr>
			<td class="tabRight">性别</td>
			<td style="text-align: left;"><span name="sex"></span></td>
			<td class="tabRight">文化程度</td>
			<td style="text-align: left;"><span name="education"></span></td>
		</tr>
		<tr>
			<td class="tabRight">民族</td>
			<td style="text-align: left;"><span name="nation"></span></td>
			<td class="tabRight">籍贯</td>
			<td style="text-align: left;"><span name="birthplace"></span></td>
		</tr>
		<tr>
			<td class="tabRight">工作单位和职务</td>
			<td style="text-align: left;"><span name="profession"></span></td>
			<td class="tabRight">联系电话</td>
			<td style="text-align: left;"><span name="tel"></span></td>
		</tr>
		<tr>
			<td class="tabRight">出生日期</td>
			<td style="text-align: left;"><span name="birthday"></span></td>
			<td class="tabRight">住址</td>
			<td style="text-align: left;"><span name="address"></span></td>
		</tr>
	</table>
	<table id="showCaseCompanyTable" class="blues"
		style="display: none; margin: 10px; width: 500px; float: left;">
		<tr>
			<td class="tabRight">单位名称</td>
			<td style="text-align: left;"><span name="name"></span></td>
			<td class="tabRight">单位类型</td>
			<td style="text-align: left;"><span name="companyType"></span></td>
		</tr>
		<tr>
			<td class="tabRight">登记证号</td>
			<td style="text-align: left;"><span name="registractionNum"></span>
				<td class="tabRight">负责人</td>
				<td style="text-align: left;"><span name="proxy"></span></td>
		</tr>
		<tr>
			<td class="tabRight">注册地</td>
			<td style="text-align: left;"><span name="address"></span></td>

			<td class="tabRight">注册资金</td>
			<td style="text-align: left;"><span name="registractionCapital"></span>
				&nbsp;<font color="red">(万元)</font></td>

		</tr>
		<tr>
			<td class="tabRight">注册时间</td>
			<td style="text-align: left;"><span name="registractionTime"></span>
			</td>
			<td class="tabRight">联系电话</td>
			<td style="text-align: left;"><span name="tel"></span></td>
		</tr>
	</table>
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
</body>
</html>