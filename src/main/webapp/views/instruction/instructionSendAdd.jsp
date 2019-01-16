<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
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
	$(function() {
		
		var editor = UE.getEditor("content",{
            toolbars:[["fullscreen","bold",
                       "italic","underline","strikethrough","forecolor",
                       "backcolor","superscript","subscript","justifyleft",
                       "justifycenter","justifyright","justifyjustify","touppercase",
                       "tolowercase","directionalityltr","directionalityrtl",
                       "indent","removeformat","formatmatch","customstyle",
                       "paragraph",
                       "fontfamily","fontsize"]],
             elementPathEnabled : false
    		 });
		
		addfile() ;
		
		<c:if test="${info eq 'add'}">
			$.ligerDialog.success('指令下发成功！');
		</c:if>
		
		var completeTime = document.getElementById('completeTime');
		completeTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}	
		
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
					required : "接收部门不能为空!"
				}
			},
			submitHandler : function(form) {
				if( editor.getContent().length == 0){
					$.ligerDialog.warn("请输入内容！");
	       		    return false;
       		 	}
	        	//验证附件名称长度
				var isError =false;
			 	$('input[name="file"]').each(
			  	function(){
				 	 var temp = $(this).val().split("\\");
					 var fileName=temp[temp.length-1];
					 var fileNameLength=fileName.lastIndexOf('.');//截取.之前的字符串长度
					 if(fileNameLength>46){
						 isError=true;
						 jqueryUtil.errorPlacement($('<label class="error" generated="true">附件文件名太长,必须小于50字,请修改后再上传!</label>'),$(this));
						 $(this).focus();
					 }else{
						 jqueryUtil.success($(this),null);
					 }
		 		});
				  if(isError){
					  return false;
				  }
				  editor.sync();
				 //jqueryUtil.changeHtmlFlag(['content']);
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
				isSingle : false,
				path : '${path }',
				callback : dlgOrgCallBack
			});
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
	function clean() {
		$("#receiveOrg").val("");
		$("#receiveOrgName").val("");
	}
	
	 function addfile() {
		 var files = $("#files") ;
		 var context = "<div  style=\"cursor:pointer;\"><input type=\"file\" id=\"file\" name=\"file\">&nbsp;<span class=\"deletefile\"><img title=\"删除\" src=\"${path}/resources/images/jian1.png\" style=\" cursor:pointer ;margin: 0px;padding:0px;\"></span></div>" ;
		 $("#addfile").click(function() {
			 files.append(context) ;
		 }) ;
		 $(document).on('click', '.deletefile', function(){
			 $(this).parent('div').remove();
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
	<div class="panel">
		<fieldset class="fieldset">
			<legend class="legend">工作指令下发</legend>
			<form id="addForm" action="${path }/instruction/instructionSend/add"
				method="post" enctype="multipart/form-data">
				<input type="hidden" name="fileId" id="fileId" value="${fileId }" />
				<table id="webserviceConfigTable" class="table_add"  width="90%">
					<tr>
						<td class="tabRight" width="20%">标题：</td>
						<td style="text-align: left" width="80%" colspan="3">
						<input type="text" class="text" name="title" value="${title}" style="width: 80%"/>&nbsp;<font color="red">*必填</font></td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">下发单位：</td>
						<td style="text-align: left" width="30%">
						<input type="text" class="text" style="width: 50%" value="${orgName }" readonly="readonly"/>&nbsp;</td>
						<td class="tabRight" width="20%">完成期限：</td>
						<td style="text-align: left" width="30%">
						<input type="text" class="text" id="completeTime" name="completeTime" style="width: 46%"/>&nbsp;</td>					
					</tr>
					<tr>
						<td class="tabRight" width="20%">接收单位：</td>
						<td style="text-align: left" width="80%" colspan="3" >
						<input type="hidden" class="text" name="receiveOrg" id="receiveOrg" value=""/>
						<textarea style="width:80%" rows="3" id="receiveOrgName" name="receiveOrgName" readonly="readonly"></textarea>
						&nbsp;<font color="red">*必填</font><br />
						<a href="javascript:choice()">选择</a>
						&nbsp;&nbsp;<a href="javascript:clean()">清空</a>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">内容：</td>
						<td style="text-align: left" width="80%" colspan="3">
						<textarea style="width:90%" id="content" name="content"><c:if test="${not empty caseId }"><a href="javascript:;" onclick="top.showCaseProcInfo('${caseId}')">案件编号：${caseNo}</a>&nbsp;</c:if></textarea>
						<font color="red">*必填</font>
						</td>
					</tr>
					<c:if test="${!empty publishInfoFiles }">
						<tr>
						<td width="20%" class="tabRight">已添加附件：</td>
						<td width="80%" style="text-align:left;" colspan="3">
							<input id="deletedFileId" name="deletedFileId" type="hidden"/>
								<div id="attaDiv">
									<c:forEach items="${publishInfoFiles }" var="publishInfoFile">
										<span id="${publishInfoFile.fileId }_Span">
											<a name="fileName" href="${path }/download/instructionFile?fileId=${publishInfoFile.fileId}">${publishInfoFile.fileName }</a>
											<a href="javascript:void(0);" onclick="del('${publishInfoFile.fileId}')" style="color: #FF6600;">删除</a>
											<br/>
										</span>
									</c:forEach>
								</div>
						</td>
						</tr>
					</c:if>
					<tr>
						<td width="20%" class="tabRight">附件：</td>
						<td width="80%" id="files" style="text-align:left;" colspan="3">
						<input type="file" id="file" name="file" style="margin: 0px;padding:0px;"/>
						<img title="添加"  id="addfile" src="${path}/resources/images/jia1.png" style=" cursor:pointer ;margin: 0px;padding:0px;"/>
						&nbsp;<font color="red">文件大小限制在70M以内</font>
						<br/>
						</td>
					</tr>
				</table>
				<table style="width: 98%;margin-top: 5px;">
					<tr>
						<td align="center"> 
							<input type="submit"  value="保 存" class="btn_small" />
						</td>
					</tr>
				</table>				
			</form>
		</fieldset>
	</div>
</body>
</html>