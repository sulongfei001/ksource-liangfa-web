<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
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
<script src="${path}/resources/script/cleaner.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/SysDialog.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="${path }/resources/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${path }/resources/ueditor/ueditor.all.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript">
var deletedFileIdAry = new Array();
	$(function() {
		var editor = new UE.ui.Editor({
	        toolbars:[["fullscreen","bold",
	                   "italic","underline","strikethrough","forecolor",
	                   "backcolor","superscript","subscript","justifyleft",
	                   "justifycenter","justifyright","justifyjustify","touppercase",
	                   "tolowercase","directionalityltr","directionalityrtl",
	                   "indent","removeformat","formatmatch","autotypeset","customstyle",
	                   "paragraph",
	                   "fontfamily","fontsize","insertimage"]],
	         elementPathEnabled : false
	         ,initialFrameHeight:280,pasteplain:true
			 }); 
		editor.render("content");
		
		addfile();
		
		jqueryUtil.formValidate({
			form : "addForm", 
			rules : {
				title : {
					required : true
				},
				receiveOrgName : {
					required : true
				}
			},
			messages : {
				title : {
					required : "标题不能为空!"
				},
				receiveOrgName : {
					required : "上报部门不能为空!"
				}
			},
			submitHandler : function(form) {
				if(editor.getContent().length == 0){
					$.ligerDialog.warn("请输入内容！");
			    	return false;
				  }
	    		var isError =false;
			 	$('input[name="file"]').each(
			  	function(){
				 	 var temp = $(this).val().split("\\");
					 var fileName=temp[temp.length-1];
					 if(fileName.length>25){
						 isError=true;
						 jqueryUtil.errorPlacement($('<label class="error" generated="true">附件文件名太长,必须小于25字符,请修改后再上传!</label>'),$(this));
						 $(this).focus();
					 }else{
						 jqueryUtil.success($(this),null);
					 }
		 		});
			 	if(isError){
					  return false;
				  }				
			 	 editor.sync();
			      $('#addForm')[0].submit();
			}
		});
	});
	
	//接收部门弹出窗
	function choice() {
		var orgIds = $("#receiveOrg").val();
		var orgNames = $("#receiveOrgName").val();
		ExtOrgDialog({
			ids:orgIds,
		  	names:orgNames,
			isSingle : true,
			path : '${path }',
			searchRank:'${searchRank}',
			callback : dlgOrgCallBack
		});			
/* 		art.dialog.open("${path}/system/organiseExternal/orgExtTree?isSingle=1",
			{
			title : '选择汇报部门',
			height : 500,
			resize : false,
			lock : true,
			opacity : 0.1,// 透明度
			closeFn : function() {
			}
		}, false); */
	}
	function dlgOrgCallBack(orgIds, orgNames) {
		if (orgIds.length > 0) {
			orgIds = trimSufffix(orgIds, ",");
			orgNames = trimSufffix(orgNames, ",");
		}
		$("#receiveOrg").val(orgIds);
		$("#receiveOrgName").val(orgNames);
	}
	
	//清空接收部门
	function clean(){
		$("#receiveOrg").val("");
		$("#receiveOrgName").val("");
	}
	
	function back(){
		window.location.href='<c:url value="/instruction/workReport/list"/>';
	}
	
	function addfile() {
		 var files = $("#files") ;
		 var deletefile =  $("#deletefile") ;
		 var context = "<div  style=\"cursor:pointer;\"><input type=\"file\" id=\"file\" name=\"file\">&nbsp;<span class=\"deletefile\"><img title=\"删除\" src=\"${path}/resources/images/jian1.png\" style=\" cursor:pointer ;margin: 0px;padding:0px;\"></span></div>" ;
		 $("#addfile").click(function() {
			 files.append(context) ;
		 }) ;
		 $(document).on("click",'.deletefile',function() {
			$(this).parent("div").remove() ;
		 }) ;
		 return false;
	}
	function del(fileId) {//ajax删除附件
		var element = $('#' + fileId + '_Span');
		var text = $('[name=fileName]', element).text();
		element.remove();
		deletedFileIdAry.push(fileId);
		$("#deletedFileId").val(deletedFileIdAry.join(","));
	}
	
</script>
</head>
<body>
	<div class="panel" >
		<fieldset class="fieldset" >
			<legend class="legend">汇报工作</legend>
			<form id="addForm" action="${path }/instruction/workReport/update"
				method="post" enctype="multipart/form-data">
				<input type="hidden" name="reportId" value="${workReport.reportId }"/>
				<input type="hidden" name="sendOrg" value="${workReport.sendOrg }"/>
				<input type="hidden" name="sendOrgName" value="${workReport.sendOrgName }"/>
				<input type="hidden" name="sendTime" value="<fmt:formatDate value="${workReport.sendTime }"  pattern="yyyy-MM-dd"/>"/>
				<table id="webserviceConfigTable" class="table_add"  width="90%">
					<tr>
						<td class="tabRight" width="20%">标题：</td>
						<td style="text-align: left" width="80%">
						<input type="text" class="text" name="title" style="width: 80%" value="${workReport.title }"/>&nbsp;<font color="red">*必填</font></td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">上级部门：</td>
						<td style="text-align: left" width="80%" colspan="3" >
						<input type="hidden" class="text" name="receiveOrg" id="receiveOrg" value="${workReport.receiveOrg }" />
						<input style="width:80%" rows="3" id="receiveOrgName" name="receiveOrgName" value="${workReport.receiveOrgName}" readonly="readonly"></input>
						<font color="red">*必填</font><br />
						<a href="javascript:choice()">选择</a>
						&nbsp;&nbsp;<a href="javascript:clean()">清空</a>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">内容：</td>
						<td style="text-align: left" width="80%" colspan="3">
						<textarea style="width:90%" id="content" name="content">${workReport.content }</textarea>
						<font color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td width="20%" class="tabRight">已添加附件：</td>
						<td width="80%" style="text-align:left;">
							<input id="deletedFileId" name="deletedFileId" type="hidden"/>
							<c:if test="${!empty publishInfoFiles }">
								<div id="attaDiv">
									<c:forEach items="${publishInfoFiles }" var="publishInfoFile">
										<span id="${publishInfoFile.fileId }_Span">
											<a name="fileName" href="${path }/download/instructionFile?fileId=${publishInfoFile.fileId}">${publishInfoFile.fileName }</a>
											<a href="javascript:void(0);" onclick="del('${publishInfoFile.fileId}')" style="color: #FF6600;">删除</a>
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
					<tr>
						<td width="20%" class="tabRight">附件：</td>
						<td width="80%" id="files" style="text-align:left;">
						<input type="file" id="file" name="file" style="margin: 0px;padding:0px;"/>
						<img title="添加"  id="addfile" src="${path}/resources/images/jia1.png" style=" cursor:pointer ;margin: 0px;padding:0px;" />
						&nbsp;<font	color="red">*文件大小限制在70M以内</font>
						<br/>
						</td>
					</tr>					
				</table>
				<table style="width: 98%;margin-top: 5px;">
					<tr>
						<td align="center"> 
							<input type="submit" class="btn_small" value="保 存" />
					 		<input type="button" value="返 回" class="btn_small" onclick="back()" />
						</td>
					</tr>
				</table>				
			</form>
		</fieldset>
	</div>
</body>
</html>