<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path }/resources/script/SysDialog.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.config.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<title>公告信息更新页面</title>
<script type="text/javascript">
function noticeMain(){
	window.location.href = "${path}/notice/main";
}
$(function(){
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
	editor.render("noticeContent");
	
    jqueryUtil.formValidate({
    	form:"updateForm",
    	rules:{
    		noticeTitle:{required:true,maxlength:100},
    		typeId:{required:true}
    	},
    	messages:{
    		noticeTitle:{required:"请输入标题!",maxlength:"请最多输入100位汉字!"},
    		typeId:{required:"请选择类型!"}
    	},
    	submitHandler:function(){
		 	var orgName= $("#orgNames").val();
			var isPublic = $("input[name='isPublic']:checked").val();
			if(orgName == "" && isPublic == 2){
				$.ligerDialog.warn("请选择发布范围");
				return false;
			}
			if(isPublic == 1){
				clearOrg();
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
			if(editor.getContent().length == 0){
				$.ligerDialog.warn("请输入内容！");
	    		return false;
			}else{
				$('#noticeContent').val(editor.getContent());
			    jqueryUtil.changeHtmlFlag(['noticeTitle']);
	    		$('#updateForm')[0].submit();
			}
    	}
    });
    addfile() ;
    initTimePlugin();
    
    <c:if test="${info eq true}">
    	$.ligerDialog.success('修改成功！',"",function(){window.location.href="${path}/notice/main";});
	</c:if>
	<c:if test="${info eq false}">
		$.ligerDialog.error('修改失败！',"",function(){window.location.href="${path}/notice/main";});
	</c:if>
});


	function initTimePlugin() {
		//日期插件
		var validBeginTime = document.getElementById('validBeginTime');
		validBeginTime.onfocus = function() {
			WdatePicker({
				dateFmt : 'yyyy-MM-dd'
			});
		}
		var validEndTime = document.getElementById('validEndTime');
		validEndTime.onfocus = function() {
			WdatePicker({
				dateFmt : 'yyyy-MM-dd'
			});
		}
	}
	function addfile() {
		var files = $("#files");
		var deletefile = $("#deletefile");
		var context = "<div  style=\"cursor:pointer;\"><input type=\"file\" id=\"file\" name=\"file\">&nbsp;<span id=\"deletefile\"><img title=\"删除\" src=\"${path}/resources/images/jian1.png\" style=\" cursor:pointer ;margin: 0px;padding:0px;\"></span></div>";
		$("#addfile").click(function() {
			files.append(context);
		});
		$("#deletefile").live("click", function() {
			$(this).parent("div").remove();
		});
		return false;
	}
	function del(fileId) {//ajax删除附件
		var element = $('#' + fileId + '_Span');
		var text = $('[name=fileName]', element).text();
		if (confirm("确认删除" + text + "吗？")) {
			$.get("${path}/activity/supervise_case/delFile/" + fileId,
					function() {
						element.remove();
						if ($('#attaDiv>span').length === 0) {
							$('#attaDiv').html('无');
						}
						;
					});
		}
	}

	function chooseType(obj) {
		if ($(obj).val() == 1) {
			$("#orgTr").hide();
		} else {
			$("#orgTr").show();
		}
	}

	function chooseOrg() {
		var orgIds = $("#orgIds").val();
		var orgNames = $("#orgNames").val();
		OrgTreeDialog({
			ids : orgIds,
			names : orgNames,
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
		$("#orgIds").val(orgIds);
		$("#orgNames").val(orgNames);
	}

	function clearOrg() {
		$("#orgIds").val("");
		$("#orgNames").val("");
	}
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">通知公告修改</legend>
		<form:form id="updateForm" action="${path}/notice/update" method="post" enctype="multipart/form-data">
		<input type="hidden" value="${notice.noticeId}" name="noticeId"/>
			<table width="90%" class="table_add">
				<tr>
					<td width="20%" class="tabRight">标题：</td>
					<td width="80%" style="text-align:left;" colspan="3">
					<input type="text" id="noticeTitle" name="noticeTitle" value="${notice.noticeTitle}" class="text"/><font color="red">*必填</font>
					</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">是否公开：</td>
					<td width="80%" style="text-align:left;" colspan="3">
					<input type="radio" id="isPublic_1" name="isPublic" value="1"  onclick="chooseType(this)" <c:if test="${notice.isPublic == 1 }"> checked="checked" </c:if> />
					<label for="isPublic_1" style="font-size: 15px;margin-right:20px" >是</label>
					<input type="radio" id="isPublic_2" name="isPublic" value="2"  onclick="chooseType(this)" <c:if test="${notice.isPublic == 2 }"> checked="checked" </c:if> />
					<label for="isPublic_2" style="font-size: 15px;margin-right:20px" >否</label>(公开后所有用户可见)
					</td>
				</tr>
				
				<tr id="orgTr" <c:if test="${notice.isPublic == 1 }">style="display: none;" </c:if>>
					<td class="tabRight" width="20%">发布范围：</td>
					<td width="80%" style="text-align:left;" colspan="3">
						<input type="hidden" name="orgIds" id="orgIds" value="${orgIds }"/>
						<textarea rows="3" cols="20" name="orgNames" id="orgNames" readonly="readonly" class="text" style="cursor:pointer;width: 74.5%;">${orgNames }</textarea>
						<a onclick="chooseOrg();" href="#" class="link ok">选择</a>
						<a onclick="clearOrg();" href="#" class="link clean">清除</a>
					</td>
				</tr>	
				<tr>
					<td width="20%" class="tabRight">类型：</td>
					<td width="80%" style="text-align:left; width:40%">
						<select class="select" name="typeId" id="typeId" class="inputText">
							<option value="1" <c:if test="${notice.typeId eq 1 }">selected="selected"</c:if>>通知公告</option>
					</select>
					<font color="red">*必填</font>
					</td>
					<td width="20%" class="tabRight">有效时间：</td>
					<td width="80%" style="text-align:left;">
						<input type="text" class="text" id="validBeginTime" name="validBeginTime" style="width: 18%"
						value="<fmt:formatDate value='${notice.validBeginTime }' pattern="yyyy-MM-dd"/>"
					 	readonly="readonly"/>
					 	至
						<input type="text" class="text" id="validEndTime" name="validEndTime" style="width: 18%"
						value="<fmt:formatDate value='${notice.validEndTime }' pattern="yyyy-MM-dd"/>"
					 	readonly="readonly"/>
					</td>
				</tr>							
				<tr>
					<td width="20%"  class="tabRight">内容：</td>
					<td width="80%" style="text-align:left;" colspan="3">
					<textarea style="width:80%" id="noticeContent" name="noticeContent">${notice.noticeContent}</textarea>
					</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">已添加附件：</td>
					<td width="80%" style="text-align:left;" colspan="3">
						<c:if test="${!empty publishInfoFiles }">
							<div id="attaDiv">
								<c:forEach items="${publishInfoFiles }" var="publishInfoFile">
									<span id="${publishInfoFile.fileId }_Span">
										<a name="fileName" href="${path }/download/publishInfoFile?fileId=${publishInfoFile.fileId}">${publishInfoFile.fileName }</a>
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
					<td width="80%" id="files" style="text-align:left;" colspan="3">
					<input type="file" id="file" name="file" style="margin: 0px;padding:0px;"/>
					<img title="添加"  id="addfile" src="${path}/resources/images/jia1.png" style=" cursor:pointer ;margin: 0px;padding:0px;" />
					&nbsp;<font	color="red">*文件大小限制在70M以内</font>
					<br/>
					</td>
				</tr>
			</table>
			<table style="width:98%;margin-top: 5px;">
				<tr>
					<td align="center">
						<input type="submit" value="保&nbsp;存" class="btn_small"/>
						<input type="button" value="返&nbsp;回" class="btn_small" onclick="noticeMain()"/>
					</td>
				</tr>
			</table>
		</form:form>
</fieldset>
</div>
</body>
</html>