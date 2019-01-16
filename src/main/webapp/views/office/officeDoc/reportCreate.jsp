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
<script src="${path}/resources/office/LinkObjs.js"></script>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script src="${path}/resources/script/util.js"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js" language="JavaScript" type="text/JavaScript"></script>
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
			//初始化webOffice
			intializePage();
			//设置高度
			ObjectDisplay(true);
			//插入模版
			insertTemplate(docType);
			//循环插入书签值
			var docData = ${jsonArray};
			for(var i=0; i<docData.length; i++){
				insertBookMarks(docData[i],docType);
  			}
			//删除空白页 添加页码
			setTimeout(function(){
				//删除因为横向展示而产生和空白页
				OFFICE_CONTROL_OBJ.ActiveDocument.Content.Find.Execute("^b^p^p^b",false,false,false,false,false,true,1,false,"",2);
				//添加页码
				insertPageFooter();
				gotoPage(1);
				OFFICE_CONTROL_OBJ.WebFileName=fileName;
			},0);
			$("#dataFormSave").click(function() {
				var result = saveFileToUrl();//文档保存到数据库
				if(result==1){
					savedoc();//文档另存为功能
				}else{
					alert("文档保存出错！");
				}
			});
		});
	
	//涉嫌犯罪案件筛查线索告知书(对下)下发功能
	function xiafa(docType,fileName){
		deleteLink();
		var result = saveFileToUrl();//文档保存到数据库
		if(result==1){
			//打开指令下发页面
			top.openInstructionSend(docType,fileName);
		}else{
			alert("文档保存出错，无法下发，请稍后重试！");
		}
		
	}
	function openLinked(url){
		var width=window.screen.availWidth*2/3+'px';
		var height=window.screen.availHeight*2/3+'px';
		ObjectDisplay(false);
		top.openLayer({
			  title:['案件列表', 'font-size:14px;font-weight:bold;'],
			  type: 2,
			  area: [width, height],
			  maxmin: true,
			  content: url,
			  cancel:function(index){
				  ObjectDisplay(true);
				}
		});
	}	
</script>
</head>
<body>
<div class="panel">
		<form id="addForm" action="${path}/office/officeDoc/add" method="post">
			<table width="100%">
				<caption align="right" style="margin:5px 0px 5px 10px;">
					<input  type="button" value="打印预览" class="off_save buttonanniu" onclick="printDocPreview()"></input>
					<input  type="button" value="打印" class="off_save buttonanniu" onclick="printDoc(true)"></input>
					<input  type="button" value="工具栏" class="off_save buttonanniu" onclick="setToolBar()"></input>
					<c:if test="${docType eq 'sxfz2' or docType eq 'jgcl3' or docType eq 'jgcl4' or docType eq 'dsjfx2' or docType eq 'dsjfx3' or docType eq 'cbtz' }">
						<c:if test="${empty caseCount or caseCount != 0}">
							<input type="button" value="下发" onclick="xiafa('${docType}','${fileName}')" class="off_save buttonanniu"></input>
						</c:if>
					</c:if>
					<c:if test="${docType ne 'sxfz2' and docType ne 'jgcl3' and docType ne 'jgcl4' and docType ne 'dsjfx2' and docType ne 'dsjfx3' and docType ne 'cbtz' }">
						<input id="dataFormSave" type="button" value="保存" class="off_save buttonanniu"></input>
					</c:if>
				</caption>
				<tr>
					<td width="100%">
						<input type="hidden" value="${fileName}" id="fileName" name="fileName"/>
						<input type="hidden" value="${docType}" id="docType" name="docType"/>
					</td>
				</tr>
			</table>
			<div id="office-div" class="office-div">
				<script type="text/javascript" src="${path}/resources/office/ntkoofficecontrol.js"></script>
			</div>
			<script language="javascript" for="TANGER_OCX" event="OnClickHyperLink(bstrName,IsCancel)">
				TANGER_OCX_OBJ = document.getElementById("TANGER_OCX");
				TANGER_OCX_OBJ.CancelLastCommand=true;
				var url = bstrName+"&districtId=${districtId}&startTime=${startTime}&endTime=${endTime}&yearCode=${yearStr}&quarterCode=${quarterStr}&monthCode=${monthStr}";
				var filterMapSize = '${filterMapSize}';
				if(filterMapSize != null && filterMapSize != '' && typeof(filterMapSize) != 'undefined' && filterMapSize > 0){
					url += "&isDescuss=${isDescuss}&chufaTimes=${chufaTimes}&isSeriousCase=${isSeriousCase}&isBeyondEighty=${isBeyondEighty}&isIdentify=${isIdentify}&isLowerLimitMoney=${isLowerLimitMoney}";
				}
				openLinked(url);
			</script>
			<script language="javascript" for="TANGER_OCX" event="OnFileCommand(cmd,canceled)">
				if (cmd == 4) {//监听另存为事件，保存本地时先删除超链接
					TANGER_OCX_OBJ = document.getElementById("TANGER_OCX");
					TANGER_OCX_OBJ.CancelLastCommand=true;					
					deleteLink();
					savedoc();
					}
			</script>
			</form>
</div>						
</body>
</html>
