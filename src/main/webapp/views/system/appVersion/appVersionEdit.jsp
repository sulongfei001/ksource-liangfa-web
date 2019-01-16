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
<script src="${path }/resources/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<title>app版本升级添加页面</title>
<script type="text/javascript">
$(function(){
	
    jQuery.validator.addMethod("isAPKExt",function(value,element){
        return this.optional(element)|| /\.apk$/.test(value);
        },"无效的文件格式");
	   
    jqueryUtil.formValidate({
    	form:"addForm",
    	rules:{
    		versionNo:{required:true,number:true,maxlength:10},
    		upgradeUrl:{required:true,maxlength:50},
    		content:{required:true,maxlength:1000},
    		file:{required:true,isAPKExt:true}
    	},
    	messages:{
    		versionNo:{required:"请输入版本号!",number:"请输入数字",maxlength:"请最多输入10位数字!"},
    		upgradeUrl:{required:"请输入升级链接!",maxlength:"请最多输入50位字符!"},
    		content:{required:"请输入升级说明!",maxlength:"请最多输入500位字符!"},
    		file:{required:"请上传APK包！"}
    	},
    	submitHandler:function(){
	      $('#addForm')[0].submit();
    	}
    });
});
	 
    function save(){
        $("#addForm").submit();
    }
	function back(){
		window.location = "${path}/system/appVersion/query";
	}	
</script>
</head>

<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">APP版本升级</legend>
		<form id="addForm" action="${path}/system/appVersion/save" method="post" enctype="multipart/form-data">
		    <input type="hidden" name="versionId" value="${appVersion.versionId }"/>
			<table width="90%" class="table_add">
				<tr>
					<td width="20%" class="tabRight">版本号：</td>
					<td width="80%" style="text-align:left;">
					<input type="text" id="versionNo" name="versionNo" class="text" value="${appVersion.versionNo }"/>
					<font color="red">*必填</font>
					</td>
				</tr>
                <tr>
                    <td width="20%" class="tabRight">升级链接：</td>
                    <td width="80%" style="text-align:left;">
                    <input type="text" id="upgradeUrl" name="upgradeUrl" class="text" value="/download/appFile"/>
                    <font color="red">*必填</font>
                    </td>
                </tr>
                <tr>
                    <td width="20%" class="tabRight">APK包：</td>
                    <td width="80%" style="text-align:left;">
                    <input type="file" id="file" name="file" class="text"/>
                    <font color="red">*必填</font>
                    </td>
                </tr>
                <tr>
                    <td width="20%" class="tabRight">升级说明：</td>
                    <td width="80%" style="text-align:left;">
                    <textarea rows="5" cols="30" style="width: 75%;" id="content" name="content">${appVersion.upgradeUrl }</textarea>
                    <font color="red">*必填</font>
                    </td>
                </tr>
			</table>
			<input type="button" class="btn_small" style="margin-left: 42%;" value="保 存" onClick="save()"/>
			<input type="button" class="btn_small" value="返 回" onClick="back()"/>
		</form>
</fieldset>
    
</div>

</body>
</html>