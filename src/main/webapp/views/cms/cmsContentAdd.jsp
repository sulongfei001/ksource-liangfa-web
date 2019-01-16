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
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script
	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script src="${path }/resources/cms/color.js" type="text/javascript"></script>
	<!-- 引入ueditor -->
<script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.config.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<title>网站文章添加页面</title>
<script type="text/javascript">
$(function(){
	addAttchment();
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
         ,initialFrameHeight:380,pasteplain:true
		 }); 

	
 	createColor("#titleColor");
	editor.render("text"); 	
	
    jqueryUtil.formValidate({
    	form:"addForm",
    	rules:{
    		title:{required:true,maxlength:100},
    		channelName:{required:true}
    	},
    	messages:{
    		title:{required:"请输入标题!",maxlength:"请最多输入100位汉字!"},
    		channelName:{required:"请选择所在栏目!"}
    	},
    	submitHandler:function(){
			var isError =false;
		 	$('input[name="file"]').each(
		  	function(){
			 	 var temp = $(this).val().split("\\");
				 var fileName=temp[temp.length-1];
				 if(!judgePhoto(fileName)&&fileName.length>0){
					 isError=true;
					 jqueryUtil.errorPlacement($('<label class="error" generated="true">上传文件必须是图片类型!</label>'),$(this));
					 $(this).focus();
				 }else{
					 jqueryUtil.success($(this),null);
				 }
	 		});
		
		 if( editor.getContent().length == 0){
			 top.art.dialog.alert("请输入内容！");
		    return false;
		 }
		 
		  if(isError){
			  return false;
		  }
		  $('#txt').val(editor.getContent());
		  jqueryUtil.changeHtmlFlag(['title']);
	      $('#addForm')[0].submit();
    	}
    });

    function callback(data){
    	var html = '';
    	$.each(data,function(entryIndex,entry){
			 var option = document.createElement("OPTION");
			 $("#typeId")[0].appendChild(option);
		     option.text = entry.name;
		     option.value =entry.id;

		});
    }
    
    function judgePhoto(str){
    	var lowerStr = str.toLowerCase();
    	if(endWith(lowerStr,".jpg")||endWith(lowerStr,".bmp")||endWith(lowerStr,".gif")||endWith(lowerStr,".jpeg")||endWith(lowerStr,".png")){
    		return true;
    	}else{
    		return false;
    	}
    }
    function endWith(str1, str2){
    	 if(str1 == null || str2 == null){
    	  return false;
    	 }
    	 if(str1.length < str2.length){
    	  return false;
    	 }else if(str1 == str2){
    	  return true;
    	 }else if(str1.substring(str1.length - str2.length) == str2){
    	  return true;
    	 }
    	 return false;
    	}
});

function chooseColor(){
	$('#titleColor').val("#FDFDFD");
}

$("html").bind("mousedown", function(event){
	
	if (!(event.target.id == "titleColor" || event.target.id == "colorSelect" || $(event.target).parents("#colorSelect").length>0)) {
		$("#colorSelect").slideUp("fast");
	}
});

function showColor(){
	var cityObj = $("#titleColor");
	var cityOffset = $("#titleColor").offset();
	$("#colorSelect").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
}

function setIsBold(isChecked){
	if(isChecked){
		$('#isBold').val(1);
	}else{
		$('#isBold').val(2);
	}
}

function setTop(isChecked){
	if(isChecked){
		$('#top').val(1);
	}else{
		$('#top').val(2);
	}
}

function addAttchment() {
    var files = $("#files");
    var context = "<div  style=\"cursor:pointer;\"><input style=\"width: 95%\" type=\"file\" id=\"contentFiles\" class='uploadFileLength' name=\"contentFiles\">&nbsp;<span name=\"deletefile\"><a title=\"删除\" style=\" cursor:\"pointer ;\">删除</a></span></div>";
    $("#addfile").click(function () {
        if ($("span[name='deletefile']").length === 4) {
            top.art.dialog.alert('提示：附件最多能添加五个！');
        } else {
            files.append(context);
        }
    });
    $("span[name='deletefile']").live("click", function () {
        $(this).parent("div").remove();
    });
    return false;
}

jQuery(function(){
	
	//栏目树
	zTree=	jQuery('#dropdownMenu').zTree({
		isSimpleData: true,
		treeNodeKey: "id",
		treeNodeParentKey: "upId",
		async: true,
		asyncUrl:"${path}/cms/content/loadChildChannel?id=-1",
		asyncParam: ["id"],
		callback: {
			click: zTreeOnClick
		}
	});	
	//鼠标点击页面其它地方，栏目树消失
	jQuery("html").bind("mousedown", 
			function(event){
				if (!(event.target.id == "DropdownMenuBackground" || jQuery(event.target).parents("#DropdownMenuBackground").length>0)) {
					hideMenu();
				}
			});		
});

function showMenu() {
	var cityObj = jQuery("#channelName");
	var cityOffset = jQuery("#channelName").offset();
	jQuery("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
}
function hideMenu() {
	jQuery("#DropdownMenuBackground").fadeOut("fast");
}

function zTreeOnClick(event, treeId, treeNode) {
	if (treeNode.channelFrom == 0) {		
		jQuery("#channelName").val(treeNode.name);
		jQuery("[name='channelId']").val(treeNode.id);
		hideMenu();
	}else{
		alert("该栏目不能添加文章！");
	}
}

</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">新增文章</legend>
		<form:form id="addForm" action="${path}/cms/content/add" method="post" enctype="multipart/form-data">
			<table width="90%" class="table_add">
		        <tr>
					<td width="10%" class="tabRight">栏目：</td>
					<td width="90%" style="text-align:left;">
					<input type="text" name="channelName" id="channelName" style="width: 30%" onfocus="showMenu(); return false;" readonly="readonly" class="text"/><font color="red">*</font>
			　	     　<input type="hidden" name="channelId" /></td>
				</tr>
				<tr>
					<td width="10%" class="tabRight">标题：</td>
					<td width="90%" style="text-align:left;">
					<input type="text" id="title" name="title" class="text" style="width: 50%"/>
					<font color="red">*</font>
					&nbsp;&nbsp;&nbsp;<input type="text" id="titleColor" name="titleColor" value="#000000" onclick="showColor();" style="width:6% !important;background-color:#000000 " class="inputText"/>&nbsp;标题颜色
					&nbsp;&nbsp;&nbsp;<input type="checkbox"  onclick="setIsBold(this.checked);">&nbsp;&nbsp;&nbsp;加粗
					&nbsp;&nbsp;&nbsp;<input type="checkbox"  onclick="setTop(this.checked);">&nbsp;&nbsp;&nbsp;置顶
					<input type="hidden" id="isBold" name="isBold" value="2"/>
					<input type="hidden" id="top" name="top" value="2"/>
					</td>
				</tr>
				<tr>
					<td width="10%" class="tabRight">内容：</td>
					<td width="90%" style="text-align:left;">
					<textarea style="width:95%" id="text" name="text"></textarea>
					</td>
				</tr>
				<tr>
					<td width="10%" class="tabRight">配图：</td>
					<td width="90%" id="iamgefiles" style="text-align:left;">
					<input type="file" id="file" name="file" style="margin: 0px;padding:0px;"/>
					<br>
					</td>
				</tr>
				<tr>
                    <td width="10%" align="right" class="tabRight">附件：</td>
                    <td width="90%" style="text-align: left;" colspan="3" id="files">
                    <input type="file" id="contentFiles" name="contentFiles" style="width: 65%"/> 
                    <a title="添加" id="addfile" style="cursor: pointer;">添加更多附件</a>
                  &nbsp;<font color="red">文件大小限制在70M以内</font> <br/>
                    </td>
                </tr>
			</table>
			<table style="width:98%;margin-top: 5px;">
				<tr>
					<td align="center">
					<input type="submit" value="保&nbsp;存" class="btn_small"/>&nbsp;
					</td>
				</tr>
			</table>
		</form:form>
</fieldset>
</div>
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
		<div align="right">
			<a href="javascript:void();" onclick="javascript:document.getElementById('channelName').value = '';">清空</a>
		</div>
		<ul id="dropdownMenu" class="tree"></ul>
	</div>
</body>
</html>