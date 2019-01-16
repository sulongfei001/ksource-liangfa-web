<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script charset="utf-8" src="${path }/resources/script/kindeditor/kindeditor-min.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.all.js"></script>
<script type="text/javascript">
	$(function() {
		addfile() ;
		var opinionDate = document.getElementById('opinionDate');
		var anfaTime = document.getElementById('anfaTime');
		opinionDate.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		anfaTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		
		//editor
		var editor = new UE.ui.Editor({
		    toolbars:[["fullscreen","bold",
		               "italic","underline","strikethrough","forecolor",
		               "backcolor","superscript","subscript","justifyleft",
		               "justifycenter","justifyright","justifyjustify","touppercase",
		               "tolowercase","directionalityltr","directionalityrtl",
		               "indent","removeformat","formatmatch","autotypeset","customstyle",
		               "paragraph",
		               "fontfamily","fontsize","insertimage",'inserttable']],
		     elementPathEnabled : false
		     ,initialFrameHeight:320,pasteplain:true
			 }); 
		
		editor.render("content"); 
		//验证
		jqueryUtil.formValidate({
			form : "addForm",
			rules : {
				title : {
					required : true,
					maxlength :40
				}
			},
			messages : {
				title : {
					required : "请输入标题!",
					maxlength : "请最多输入40位汉字!"
				}
			},
			submitHandler : function() {
				jqueryUtil.changeHtmlFlag([ 'title' ]);
				$('#addForm')[0].submit();

			}
		});
	});
	 function addfile() {
		 var files = $("#files") ;
		 var deletefile =  $("#deletefile") ;
		 var context = "<div  style=\"cursor:pointer;\"><input type=\"file\" id=\"file\" name=\"file\">&nbsp;<span id=\"deletefile\"><img title=\"删除\" src=\"${path}/resources/images/jian1.png\" style=\" cursor:pointer ;margin: 0px;padding:0px;\"></span></div>" ;
		 $("#addfile").click(function() {
			 files.append(context) ;
		 }) ;
		 $("#deletefile").live("click",function() {
			$(this).parent("div").remove() ;
		 }) ;
		 return false;
	 }
</script>
</head>
<body>
	<div class="panel">
		<fieldset class="fieldset">
			<legend class="legend">新增其它信息</legend>
			<form:form id="addForm" method="post"
				action="${path }/opinion/otherInformation/save" enctype="multipart/form-data">
				<table width="90%" class="table_add">
					<tr>
						<td width="20%" class="tabRight">标题:</td>
						<td style="text-align: left;">
							<input type="text" id="title" name="title" class="text" />&nbsp;
							<font color="red">*</font>
						</td>
						<td class="tabRight" width="20%">信息来源:</td>
						<td style="text-align: left" >
						<input type="text" class="text" name="source" id="source"/>
						</td>
					</tr>
					<tr>
						<td width="20%" class="tabRight">
							事发时间:
						</td>
						<td style="text-align:left;">
							<input type="text" id="anfaTime" name="anfaTime" class="text"/>&nbsp;
						</td>
						<td width="20%" class="tabRight">
							采集时间:
						</td>
						<td style="text-align:left;">
							<input type="text" id="opinionDate" name="opinionDate" class="text"/>&nbsp;
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">关键词:</td>
						<td style="text-align: left"  >
						<input type="text" class="text" name="keyword" id="keyword"/>
						</td>
						<td class="tabRight" ></td>
						<td style="text-align: left"  >
						</td>
					</tr>
					<tr>
						<td width="20%" class="tabRight">内容:</td>
						<td style="text-align:left;" width="80%" colspan="3">
						<textarea style="width:90%" id="content" name="content"></textarea><br/>
						</td>
					</tr>
					<tr>
						<td width="20%" class="tabRight">附件:</td>
						<td id="files" style="text-align:left;" colspan="3">
						<input type="file" id="file" name="file" style="margin: 0px;padding:0px;"/>
						<img title="添加"  id="addfile" src="${path}/resources/images/jia1.png" style=" cursor:pointer ;margin: 0px;padding:0px;"/>
						</td>
					</tr>
				</table>
				<table style="width: 98%; margin-top: 5px;">
					<tr>
						<td align="center"><input type="submit" value="保&nbsp;存" class="btn_small" /> 
						<input type="button" value="返&nbsp;回"class="btn_small"
						onclick="javascript:window.location.href='${path}/opinion/otherInformation/main'" />
						</td>
					</tr>
				</table>
			</form:form>
		</fieldset>
	</div>
</body>
</html>