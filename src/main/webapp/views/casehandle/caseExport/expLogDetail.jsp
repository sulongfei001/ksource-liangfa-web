<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript">
	function back(){
	    window.location.href="${path }/caseExport/back?backType=${backType}";
	}
	$(function() {
		var updateContent = $("div.updateContent");
		var insertContent = $("div.insertContent");
		var deleteContent = $("div.deleteContent");
		var f = "<span class='attachement-span'><span name='attach' file='#file#'><a class='attachment' onclick='#action#;' href='javascript:;'>#name#</a></span>&nbsp;&nbsp;</span>";
		var insert = ${caseModifiedExpLog.insertContent};
		var il="";
		if(insert == null || insert == "" || typeof(insert) == 'undefined'){
			il = "无";
			insertContent.empty();
			insertContent.append(il);
		}else{
			for ( var k = 0; k < insert.length; k++) {
				var j = insert[k];
				var c = j.caseId;
				var b = j.caseNo;
				var h = f.replace("#name#", b).replace("#action#", "top.showCaseProcInfo("+c+");");
				il += h;
			}
			insertContent.empty();
			insertContent.append($(il));
		}
		
		var ul = "";
		var update = ${caseModifiedExpLog.updateContent};
		if(update == null || update == "" || typeof(update) == 'undefined'){
			ul= "无";
			updateContent.empty();
			updateContent.append(ul);
		}else{
			for ( var k = 0; k < update.length; k++) {
				var j = update[k];
				var c = j.caseId;
				var b = j.caseNo;			
				var h = f.replace("#name#", b).replace("#action#", "top.showCaseProcInfo("+c+");");
				ul += h;
			}
			updateContent.empty();
			updateContent.append($(ul));
		}
		
		var delcount = "${caseModifiedExpLog.deleteContent }";
		var dl="";
		var df = "<span class='attachement-span'><span>#name#</span>&nbsp;&nbsp;</span>";
		if(delcount == null || delcount == "" || typeof(delcount) == 'undefined' ){
			dl = "无";
			deleteContent.empty();
			deleteContent.append(dl);
		}else{
			var strAry = delcount.split(",");
			for(var i=0;i<strAry.length;i++){
				var h = df.replace("#name#", strAry[i]);
				dl += h;
			}
			deleteContent.empty();
			deleteContent.append($(dl));
		}
	});
</script>
<style type="text/css">
div.updateContent {
	float: left;
	overflow: hidden;
	padding: 3px 0 0 3px;
	white-space: nowrap
}

span.attachement-span {
	float: left;
	background: #CCCCCC;
	border: 1px solid #e1e1e1;
	margin: 5px 5px 5px 0;
	padding:1px 5px 1px 5px
}

a.attachment {
	text-decoration: none;
	font-size: 13px;
	color: #606060
}
</style>
</head>
<body>
<div class="panel">
<fieldset class="fieldset" style="padding-left: 10px;">
<legend class="legend">导出日志详情</legend>
<div style="margin: 10px;">
	<table class="blues" >
		<tr>
			<td width="15%" class="tabRight">
				导出人
			</td>
			<td width="18%" style="text-align: left;" >
				${caseModifiedExpLog.operatorName }
			</td>
			<td width="15%" class="tabRight">
				所属单位
			</td>
			<td width="18%" style="text-align: left;" >
				${caseModifiedExpLog.orgName }
			</td>
			<td width="15%" class="tabRight">
				导出时间
			</td>
			<td width="18%" style="text-align: left;" >
				<fmt:formatDate value="${caseModifiedExpLog.expTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>			
		</tr>
		<tr>
			<td width="15%" class="tabRight">
				新增案件数（件）
			</td>
			<td width="18%" style="text-align: left;">
				${caseModifiedExpLog.insertCount }
			</td>			
			<td width="15%" class="tabRight">
				修改案件数（件）
			</td>
			<td width="18%" style="text-align: left;">
				${caseModifiedExpLog.updateCount }
			</td>
			<td width="15%" class="tabRight">
				删除案件数（件）
			</td>
			<td width="18%" style="text-align: left;">
				${caseModifiedExpLog.deleteCount }
			</td>		
		</tr>		
		<tr>
			<td width="15%" class="tabRight">
				新增案件编号
			</td>
			<td width="85%" style="text-align: left;" colspan="5">
				<div  class="insertContent" ></div>	
			</td>
		</tr>
		<tr>
			<td width="15%" class="tabRight">
				修改案件编号
			</td>
			<td width="85%" style="text-align: left;" colspan="5">
				<div  class="updateContent" ></div>
			</td>
		</tr>
        <tr>
			<td width="15%" class="tabRight">
				删除案件编号
			</td>
			<td width="85%" style="text-align: left;" colspan="5">
				<div  class="deleteContent" ></div>
			</td>
		</tr>
		<tr>
			<td width="15%" class="tabRight">
				案件导出信息下载
			</td>
			<td width="85%" style="text-align: left;" colspan="5">
				<a href="${path}/download/caseExport/${caseModifiedExpLog.logId }">${caseModifiedExpLog.expFile}</a>
			</td>
		</tr>
	</table>
	</div>
</fieldset>
<br/>
<input type="button" class="btn_small" value="返 回" onclick="back()" />
</div>
</body>
</html>