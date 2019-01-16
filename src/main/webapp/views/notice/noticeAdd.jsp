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
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"  />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path }/resources/script/SysDialog.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="${path }/resources/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${path }/resources/ueditor/ueditor.all.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<title>公告信息添加页面</title>
<script type="text/javascript">
$(function(){
	var editor =  UE.getEditor("noticeContent",{
        toolbars:[["fullscreen","bold",
                   "italic","underline","strikethrough","forecolor",
                   "backcolor","superscript","subscript","justifyleft",
                   "justifycenter","justifyright","justifyjustify","touppercase",
                   "tolowercase","directionalityltr","directionalityrtl",
                   "indent","removeformat","formatmatch","autotypeset","customstyle",
                   "paragraph",
                   "fontfamily","fontsize","insertimage"]],
         elementPathEnabled:false
		 }); 
	
	addfile();
	
	<c:if test="${info eq true}">
		$.ligerDialog.success('发布成功！');
	</c:if>
	<c:if test="${info eq false}">
		$.ligerDialog.success('发布成功！');
	</c:if>
	
	
    jqueryUtil.formValidate({
    	form:"addForm",
    	rules:{
    		noticeLevel:{required:true},
    		noticeTitle:{required:true,maxlength:100},
    		typeId:{required:true}
    	},
    	messages:{
    		noticeLevel:{required:"请选择等级!"},
    		noticeTitle:{required:"请输入标题!",maxlength:"请最多输入100位汉字!"},
    		typeId:{required:"请选择类型!"}
    	},
    	submitHandler:function(){
    		
		 	var orgName= $("#orgNames").val();
			var isPublic = $("input[name='isPublic']:checked").val();
			if(orgName==""&&isPublic==2){
				$.ligerDialog.warn("请选择发布范围");
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
		  if(editor.getContent().length == 0){
			$.ligerDialog.warn("请输入内容！");
	    	return false;
		  }
		  if(isError){
			  return false;
		  }
		  editor.sync();
		  jqueryUtil.changeHtmlFlag(['noticeTitle']);
	      $('#addForm')[0].submit();
    	}
    });
    	initTimePlugin();
});
	 
function initTimePlugin(){
	//日期插件
	var validBeginTime  = document.getElementById('validBeginTime');
	validBeginTime.onfocus = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'validEndTime\')}'});
	}
	var validEndTime  = document.getElementById('validEndTime');
	validEndTime.onfocus = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'validBeginTime\')}'});
	}
}
	 
	function addfile() {
		var files = $("#files") ;
		 var context = "<div  style=\"cursor:pointer;\"><input type=\"file\" id=\"attachMent_s\" class=\"attachMent_s\" name=\"attachMent_s\">&nbsp;<span id=\"deletefile\" onclick=\"removeDiv(this)\"><img title=\"删除\" src=\"${path}/resources/images/jian1.png\" style=\" cursor:pointer ;margin: 0px;padding:0px;\"></span></div>" ;
		 $("#addfile").click(function() {
			 files.append(context) ;
		 }) ;
		 return false;
	}
	//移除文件选择按钮
	function removeDiv(element){
		var div=document.getElementById("files");
		div.removeChild(element.parentNode);
	}
	 function saveNotice(){
		 $("#addForm").submit();
	 }
	 
		function chooseType(obj){
			if($(obj).val() == 1){
				$("#orgTr").hide();
			}else {
				$("#orgTr").show();
			}
		 }
		
		function chooseOrg(){
			var orgIds = $("#orgIds").val();
			var orgNames = $("#orgNames").val();
			OrgTreeDialog({
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
			$("#orgIds").val(orgIds);
			$("#orgNames").val(orgNames);
		}
		
		function clearOrg(){
			$("#orgIds").val("");
			$("#orgNames").val("");
		}
		
		
		
</script>
</head>

<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">通知公告新增</legend>
		<form:form id="addForm" action="${path}/notice/add" method="post" enctype="multipart/form-data">
			<table width="90%" class="table_add">
				<tr>
					<td width="20%" class="tabRight">标题：</td>
					<td width="80%" style="text-align:left;" colspan="3">
					<input type="text" id="noticeTitle" name="noticeTitle" class="text" style="cursor:pointer;width: 81%;"/>
					<font color="red">*必填</font>
					</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">是否公开：</td>
					<td width="80%" style="text-align:left;" colspan="3">
					<input type="radio" id="isPublic_1" name="isPublic" value="1"  onclick="chooseType(this)"  checked="checked"/>
					<label for="isPublic_1" style="font-size: 15px;margin-right:20px" >是</label>
					<input type="radio" id="isPublic_2" name="isPublic" value="2"  onclick="chooseType(this)" />
					<label for="isPublic_2" style="font-size: 15px;margin-right:20px" >否</label>(公开后所有用户可见)
					</td>
				</tr>
				<tr id="orgTr"  style="display: none;"> 
					<td class="tabRight" width="20%">发布范围：</td>
					<td width="80%" style="text-align:left;" colspan="3">
						<input type="hidden" name="orgIds" id="orgIds" />
						<textarea name="orgNames" id="orgNames" readonly="readonly"  value="${orgNames }" class="text" style="cursor:pointer;width: 80.5%;height: 40px;"></textarea>
						<a onclick="chooseOrg();" href="#" class="link ok">选择</a>
						<a onclick="clearOrg();" href="#" class="link clean">清除</a>
					</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">类型：</td>
					<td width="80%" style="text-align:left; width:30%">
						<select class="select" name="typeId">
							<option value="1">通知公告</option>
					</select>
					<font color="red">*必填</font>
					</td>
					<td width="10%" class="tabRight">有效时间：</td>
					<td width="90%" style="text-align:left;">
						<input type="text" class="text" id="validBeginTime" name="validBeginTime" style="width: 28%"
					 	readonly="readonly"/>
					 	至
						<input type="text" class="text" id="validEndTime" name="validEndTime" style="width: 28.5%"
					 	readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">内容：</td>
					<td width="80%" style="text-align:left;" colspan="3">
					<textarea style="width:90.2%" id="noticeContent" name="noticeContent"></textarea>
					</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">附件：</td>
					<td width="80%" id="files" style="text-align:left;" colspan="3">
					<input type="file" id="file" name="file" style="margin: 0px;padding:0px;"/>
					<img title="添加"  id="addfile" src="${path}/resources/images/jia1.png" style=" cursor:pointer ;margin: 0px;padding:0px;">
					<br>
					</td>
				</tr>
			</table>
			<input type="button" class="btn_small" style="margin-left: 42%;" value="保 存" onClick="saveNotice()"/>
		</form:form>
</fieldset>
    
</div>

</body>
</html>