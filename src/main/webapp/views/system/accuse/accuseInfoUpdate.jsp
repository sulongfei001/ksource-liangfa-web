<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js"
	type="text/javascript"></script>
<!-- 引入ueditor -->
<script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.config.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>法律法规类别添加界面</title>
<script type="text/javascript">
	var editor;
	$(function() {
		    editor = new UE.ui.Editor({
	        toolbars:[["fullscreen","bold",
	                   "italic","underline","strikethrough","forecolor",
	                   "backcolor","superscript","subscript","justifyleft",
	                   "justifycenter","justifyright","justifyjustify","touppercase",
	                   "tolowercase","directionalityltr","directionalityrtl","RowSpacingTop","RowSpacingBottom",
	                   "indent","removeformat","formatmatch","autotypeset","customstyle","lineheight",
	                   "paragraph",
	                   "fontfamily","fontsize"]],
	         elementPathEnabled : false,pasteplain:true
	         ,initialFrameHeight:'99%',initialFrameWidth:'95%'
			 }); 
	     
		editor.render("law");
		
		//验证增加时输入的类型名称是否有重复的
		jqueryUtil.formValidate({
			form : "addForm",
			blockUI : false,
			rules : {
				name : {
					required : true,
					maxlength : 100,
					remote : {
						url : '${path}/system/accuseinfo/checkName',
						type : 'post',
						data : {
							id : function() {
								return $('#id').val();
							}
						}
					}
				},
				clause : {
					required : true,
					maxlength : 100
				},
				infoOrder : {
					required : true,
					appNumber : [ 5, 0 ]
				}
			},
			messages : {
				name : {
					required : "请输入罪名!",
					maxlength : "请最多输入100位汉字!",
					remote : "此名称已存在，请重新填写!"
				},
				clause : {
					required : "请输入条款!",
					maxlength : "请最多输入100位汉字!"
				},
				infoOrder : {
					required : "刑法对应的条数不能为空!",
					appNumber : "请输入数字(整数最多5位)"
				}
			},
	    	submitHandler:function(){
	  		  $('#law').val(editor.getContent());
	  	      $('#addForm')[0].submit();
	      	}
		});
	});
	function resetLaw(){
		editor.setContent('${accuseInfo.law }',false);//重置ueditor里的内容
	}
</script>
</head>
<body>
	<div class="panel"  >
		<br />
		<center>
			<form:form id="addForm" action="${path }/system/accuseinfo/update">
				<input type="hidden" id="id" name="id" value="${accuseInfo.id }" />
				<table class="blues" style="width: 90%">
					<tr>
						<td width="20%" class="tabRight">罪名：</td>
						<td width="80%" style="text-align: left;"><input
							style="width: 80%;" type="text" class="text" name="name"
							value="${accuseInfo.name }" />&nbsp;<font color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td width="20%" class="tabRight">条款：</td>
						<td width="80%" style="text-align: left;"><input
							style="width: 80%;" class="text" type="text" name="clause"
							value="${accuseInfo.clause }" />&nbsp;<font color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td width="20%" class="tabRight">排序：</td>
						<td width="80%" style="text-align: left;"><input
							style="width: 80%;"  class="text" type="text" name="infoOrder"
							value="${accuseInfo.infoOrder }" />&nbsp;<font color="red">*必填</font>
							<br />
						<font style="font-size: 1" >如刑法第115条第2款，请输入115</font></td>
					</tr>
				<tr>
					<td width="20%" class="tabRight">违法情形：</td>
					<td width="80%" style="text-align: left;">
						<%-- <textarea  name="law" rows="12" cols="20" style="width: 80%;">${accuseInfo.law }</textarea> --%>
						<textarea name="law" id="law" >${accuseInfo.law }</textarea>
					</td>
				</tr>
				</table>
				<table style="width:98%;margin-top: 5px;">
				<tr>
					<td align="center">
						<input type="submit" class="btn_small" value="保 存" />&nbsp; 
						<input type="reset" class="btn_small" value="重 置" onclick="resetLaw();"/>
					</td>
				</tr>
			</table>
			</form:form>
		</center>
		<div style="color: red">${info}</div>
	</div>
</body>
</html>