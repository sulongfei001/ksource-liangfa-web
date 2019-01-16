<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib uri="quiee" prefix="report" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>自定义统计(按行业)报表页面</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path }/resources/script/highcharts/highcharts.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
	$(function(){
		// 报表样式修改
	    $("div").remove(".report1");
	    $("#report1").attr("style","width:100%");
    	 
		//处理报表乱码
		var reportHrefs=$("td[class^='report'] a");
		$.each(reportHrefs,function(i,n){
			var oldhref=$(n).attr("href");
			$(n).attr("href",encodeURI(oldhref));
		});
	});

	function back(){
		window.location.href = "${path}/breport/o_custom_query.do";	
	}
</script>
<style type="text/css">
.btn_small {
    background:#1585EF;
    width: 68px;
    height: 24px;
    border: none;
    text-align: center;
    line-height: 20px;
    vertical-align: middle;
    margin-right: 15px;
    color: #fff;
    font-size: 14px;
    font-weight: 400;
    cursor: hand;
    padding-bottom:20px;
    border-radius:4px;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px; 
}
</style>
</head>
<body>
	<div class="panel">
	<input type="button" style="margin-left: 5px;margin-top:15px;" class="btn_small" value="返 回" onclick="back();"/>
		<table align="center" style="width: 100%;">
		<tr>
			<td align="right">
				<jsp:include page="/quiee/reportJsp/toolbar.jsp" flush="false" />
				<report:html name="report1" reportFileName="custom_business_industryStats.raq"
					funcBarLocation=""
					needPageMark="yes"
					generateParamForm="no"
					needLinkStyle="yes"
					params="${parameter}"
					width="-1"
                    needSaveAsPdf="yes" needSaveAsWord="yes" pdfExportStyle="text,0"
									 excelPageStyle="0" needSaveAsExcel="yes" excelUsePaperSize="no"
									 needPrint="yes" printButtonWidth="80" needPrintPrompt="yes"
									 needSelectPrinter="yes" savePrintSetup="yes" displayNoLinkPageMark="yes" saveAsName="自定义统计(按行业)"
				/>
			</td>
		</tr>
		</table>
	</div> 
</body>
</html>