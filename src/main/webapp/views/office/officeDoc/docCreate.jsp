<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>编辑 文书管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link type="text/css" rel="stylesheet" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css"/>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script src="${path}/resources/script/util.js"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path}/resources/office/LinkObjs.js"></script>
<script src="${path}/resources/office/ntkoControl.js"></script>
<style type="text/css">
.panel{
	padding-left:0px;
	padding-right:0px;	
}
 input.buttonanniu {
	background: #F6FAFE;
	border: 1px solid #4496EA;
	height: 25px;
	font-size:12px;
	border-radius: 5px;
	margin-right: 5px;
	padding:2px 12px;
	box-shadow: 0px 1px 2px #999;
	-webkit-box-shadow: 0px 1px 2px #999;
	-moz-box-shadow: 0px 1px 2px #999; 
}

input.buttonanniu:hover {
	border: 1px solid #1081E9;
	background: #2F95EA;
	color: #fff;	
	padding:2px 12px;
	margin-right: 5px;
	font-size:12px;
	box-shadow: 0px 1px 2px #999;
	-webkit-box-shadow: 0px 1px 2px #999;
	-moz-box-shadow: 0px 1px 2px #999;
	background: -webkit-gradient(linear, 0 0, 0 100%, from(#55A9EE),
		to(#2990F0));
	background: -moz-linear-gradient(top, #55A9EE, #2990F0);
}
</style>
<script type="text/javascript">
	$(function() {
			var docType = '${docType}';
			var fileName='${fileName}';
			var caseId = '${caseId}';
			//初始化webOffice
			intializePage();
			//设置高度
			var c_height =document.documentElement.clientHeight-42;
			document.getElementById("TANGER_OCX").style.height=c_height+"px";
			document.getElementById("TANGER_OCX").style.width='100%';
			//插入模版
			insertTemplate(docType,caseId);
			//循环插入书签值
			<c:if test="${not empty jsonArray}">
			var docData = ${jsonArray};
			for(var i=0; i<docData.length; i++)
  			{
				insertBookMarks(docData[i],docType);
  			}
			</c:if>
			
			gotoPage(1);
			
			$("#dataFormSave").click(function() {
				var shortRegion = readBookMarkValueFromDoc("shortRegion");
				if(shortRegion!= "" && shortRegion != null && typeof(shortRegion) != "undefined"){
					$("#docNo").val($.trim(shortRegion)+"${docNo}");
				}
				var result = saveFileToUrl();//文档保存到数据库
				if(result==1){
					//alert("文档‘"+fileName+"’保存成功！");
					savedoc();//保存到本地操作
				}else{
					alert("文档保存出错！");
				}
			});
		});
	
</script>
</head>
<body>
<div class="panel">
	<form id="addForm" action="${path}/office/officeDoc/add" method="post" enctype="multipart/form-data">
		<input type="hidden" value="${fileName}" id="fileName" name="fileName"/>
		<input type="hidden" value="${docType}" id="docType" name="docType"/>
		<input type="hidden" value="${caseId}" id="caseId" name="caseId"/>
		<input type="hidden" value="${docNo}" id="docNo" name="docNo"/>
		<input type="hidden" value="${docId}" id="docId" name="docId"/>
		<table width="100%" style="margin:5px 0px 5px 0px;">
			<tr>
				<td align="left" style="padding-left: 10px;">
				<input  type="button" value="打印预览" class="off_save buttonanniu" onclick="printDocPreview()"></input>
				<input  type="button" value="打印" class="off_save buttonanniu" onclick="printDoc(true)"></input>
				<input  type="button" value="工具栏" class="off_save buttonanniu" onclick="setToolBar()"></input>
				<input id="dataFormSave" type="button" value="下载" class="off_save buttonanniu"></input>		
				</td>
				<td align="right" style="padding-right: 10px;">
				</td>
			</tr>
		</table>
		<div id="office-div" class="office-div" >
			<script type="text/javascript" src="${path}/resources/office/ntkoofficecontrol.js"></script>
			<script language="javascript" for="TANGER_OCX" event="OnFileCommand(cmd,canceled)">
				if (cmd == 4) {//监听另存为事件，保存本地时先删除超链接
					TANGER_OCX_OBJ = document.getElementById("TANGER_OCX");
					TANGER_OCX_OBJ.CancelLastCommand=true;	
					deleteLink();
					savedoc();
					}
			</script>
		</div>			
	</form>
</div>
</body>
</html>
