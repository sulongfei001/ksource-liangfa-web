<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path}/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/zTree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/script/accuseSelector/accuseSelector.css" />
<script type="text/javascript" src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"></script>
<script type="text/javascript" src="${path}/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript" src="${path}/resources/script/artDialog/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="${path}/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript" src="${path}/resources/script/accuseSelector/accuseSelector.js"></script>
<script type="text/javascript" src="${path}/resources/script/accuseRuleUtil.js"></script>
<script type="text/javascript">

</script>
<style type="text/css">
.selectType{
	width:15%;
	margin-top:10px;
	}
.matchType{
	width:15%;
}
.guanjianzi{
	width:70%;
    margin-bottom: 10px;
    border: none;
    background: #eee;
    padding: 4px;
    border-bottom: 1px solid #e1e3e1;
}
.number_size {
    width: 28.3%;
    margin-left: 4.5px;
    margin-bottom: 10px;
    border: none;
    background: #eee;
    padding: 4px;
    border-bottom: 1px solid #e1e3e1;
}
.table_add td {
    border-right: #C1C1C1 1px solid;
    border-top: #C1C1C1 1px solid;
    border-buttom: #C1C1C1 1px solid;
    padding-left: 8px;
    padding-right: 8px;
    padding-top: 6px;
    padding-bottom: 6px;
    empty-cells: show;
}
.table_add td font {
    display: inline-block;
    width: 110px;
}
.number_size_big{
	margin-left: 20px;
}
.td_option{
	text-align: right;
	width:5%;
}

.table_add {
    width: 98%;
    margin: 0;
    border: #C1C1C1 1px solid;
    color: black;
    border-right: none;
    empty-cells: show;
    border-collapse: collapse;
}

</style>
</head>
<body>
	<div class="panel">
		<fieldset class="fieldset">
			<legend class="legend">编辑量刑标准</legend>
			<form id="accuseRuleAddForm" action="${path }/system/accuseRule/analysisUtil" method="post">
					<table style="width: 90%;">
					<tr>
						<td class="tabRight" width="10%">内容</td>
						<td style="text-align: left" width="80%">
							<textarea  rows="10" cols="50" name="content">${param.content}</textarea>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="10%">规则</td>
						<td style="text-align: left;" width="80%">
							<textarea  rows="5" cols="50" name="keywords">${param.keywords}</textarea>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">分数</td>
						<td>${scope }</td>
					</tr>
				</table>
				<div style="margin-left: 37%; margin-top: 5px">
					<input type="submit" class="btn_small" value="提交" />
				</div>
			</form>
		</fieldset>
	</div>
</body>
</html>