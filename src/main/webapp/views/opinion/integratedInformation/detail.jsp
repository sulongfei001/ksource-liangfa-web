<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.parse.js"></script>
<script type="text/javascript">
$(function() {
	//让ie显示ueditor中表格边框线
	uParse('#content',{
		highlightJsUrl : '${path }/resources/ueditor/third-party/SyntaxHighlighter/shCore.js'
	})
	uParse('#content',{
		highlightCssUrl : '${path }/resources/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css'
	})
	
});
</script>
</head>
<body>
	<div class="panel">
		<fieldset class="fieldset">
			<legend class="legend">综合信息详情</legend>
				<table width="90%" class="table_add">
					<tr>
						<td width="20%" class="tabRight">标题:</td>
						<td style="text-align: left;" width="30%">
							${integratedInformation.title }
						</td>
						<td width="20%" class="tabRight">信息来源:</td>
						<td style="text-align: left;" width="30%">
							${integratedInformation.source }
						</td>
					</tr>
					<tr>
						<td width="20%" class="tabRight">
							事发时间:
						</td>
						<td style="text-align:left;" width="30%">
							<fmt:formatDate value="${integratedInformation.happenedTime }" pattern="yyyy-MM-dd" />
						</td>
						<td width="20%" class="tabRight">
							采集时间:
						</td>
						<td style="text-align:left;" width="30%">
							<fmt:formatDate value="${integratedInformation.opinionDate }" pattern="yyyy-MM-dd" />
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">关键词:</td>
						<td style="text-align: left" >
						${integratedInformation.keyword }
						</td>
						<td class="tabRight" width="20%"></td>
						<td style="text-align: left" >
						</td>
					</tr>
					<tr>
						<td width="20%" class="tabRight">内容:</td>
						<td style="text-align: left" width="80%" colspan="3" id="content" >
						${integratedInformation.content }
						</td>
					</tr>
					<tr>
					<td width="20%" class="tabRight">附件：</td>
					<td style="text-align:left;" colspan="3">
						<c:if test="${!empty publishInfoFiles }">
							<div id="attaDiv">
								<c:forEach items="${publishInfoFiles }" var="publishInfoFile">
									<span id="${publishInfoFile.fileId }_Span">
										<a name="fileName" href="${path }/download/publishInfoFile?fileId=${publishInfoFile.fileId}">${publishInfoFile.fileName }</a>
										<br/>
									</span>
								</c:forEach>
							</div>
						</c:if>
						<c:if test="${empty publishInfoFiles }">
							无
						</c:if>
					</td>
				</tr>
				</table>
				<table style="width: 98%; margin-top: 5px;">
					<tr>
						<td align="center">
						<input type="button" value="返&nbsp;回"class="btn_small"
						onclick="javascript:window.location.href='${path}/opinion/integratedInformation/main'" />
						</td>
					</tr>
				</table>
		</fieldset>
	</div>
</body>
</html>