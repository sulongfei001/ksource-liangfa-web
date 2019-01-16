<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/popover/jquery.webui-popover.min.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/lib/jquery.metadata.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"></script>
<script type="text/javascript" src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script type="text/javascript" src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/autoresize.jquery.min.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript">
	$(function(){
		
		//验证附件是否超过大小限制
		jQuery.validator.addMethod("filesize", function(value, element) {
			 var files = $(".attachMent_s");
			 var byteSize = 0;
			 for(var index = 0; index < files.length; index++){
				 var file = files[index].files[0];
				 if(file !== undefined){
		         var size = files[index].files[0].size;
		         if(!isNaN(size)){
		        	 byteSize += size;  
		         }
				 }
			 }
			 byteSize = Math.ceil(byteSize/1024/1024);
//				 (Math.ceil(byteSize / 1024 / 1024) < 1)
			if(byteSize > 70){
// 					alert("文件超出70M");
				$.ligerDialog.warn("文件超出70M");
				return  false;
			}
			return  true;
		}, "选择的材料超出限制!");
		
		
			//案件信息表单验证
				caseValidate=jqueryUtil.formValidate({
				form:"clueInfoReplyForm",
				rules:{
					executorName:{required:true,maxlength:50},
					executorTime:{required:true},
					content:{maxlength:500}
// 					fileTip:{filesize:true}
				},
				messages:{
					executorName:{required:"请输入承办人姓名!",maxlength:'请最多输入50位汉字!'},
					executorTime:{required:"请选择承办时间!"},
					content:{maxlength:"输入内容超出500字!"}
				},submitHandler:function(form){
				      if($("#clueInfoReplyForm").valid()){
				    	  form.submit();
					      //提交按钮禁用
					      $("#saveButton").attr("disabled",true);
				      }
				      return false;
				}
			});//校验结束
		
		//autoResize.js自动扩展textarea大小
		$('#content').autoResize({
			limit:500
		});
		
 		//初始化日期插件
		var undertakerTime  = document.getElementById('executorTime');
		undertakerTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		
		/* 信息保存后的弹框提示 */
		var info = "${info}";
		if(info != null && info != ""){
			if(info){
				//正确提示
				$.ligerDialog.success('回复成功!');
			}else{
				//失败提示
				$.ligerDialog.error('回复失败!');
			}
		}
		
		//为文件按钮绑定事件
		addfile();
	});
	
	//添加文件上传按钮
	function addfile() {
		var files = $("#files") ;
		 var deletefile =  $("#deletefile") ;
		 var context = "<div  style=\"cursor:pointer;\"><input type=\"file\" class=\"attachMent_s\" id=\"attachMent_s\" name=\"attachMent_s\">&nbsp;<span id=\"deletefile\" onclick=\"removeDiv(this)\"><img title=\"删除\" src=\"${path}/resources/images/jian1.png\" style=\" cursor:pointer ;margin: 0px;padding:0px;\"></span></div>" ;
		 $("#addfile").click(function() {
			 files.append(context) ;
		 });
		 return false;
	}
	
	//移除文件选择按钮
	function removeDiv(element){
		var div=document.getElementById("files");
		div.removeChild(element.parentNode);
	}
	
	//验证输入项通过后提交表单
	function add(){
		$("#clueInfoReplyForm").submit();
	}
	
	function back(){
		window.location.href="${path}/casehandle/clueInfo/notDealClueList.do";
	}
	
</script>
</head>
<body>
	<div class="panel">
	<form id="clueInfoReplyForm" method="post" action="${path }/casehandle/clueInfo/reply/addReply.do" enctype="multipart/form-data">
		<fieldset class="fieldset">
			<legend class="legend">线索不予办理</legend>
			<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;" id="clueInfoTable">
				<tr>
					<td width="21%"  class="tabRight">承办人：</td>
					<td width="29%" style="text-align: left;" >
						<input type="text" class="text ignore" id="executorName" name="executorName" style="width: 91%;"/>&nbsp;<font color="red">*</font>
						<input type="hidden" name="clueInfoId" value="${clueId}"/>
						<input type="hidden" name="dispatchId" value="${dispatchId }"/>
					</td>
			
					<td width="21%"  class="tabRight" >承办时间：</td>
					<td width="29%" style="text-align: left;" >
						<input type="text" id="executorTime" name="executorTime"  class="text ignore" style="width: 91%;" />&nbsp; <font color="red">*</font> 
					</td>		
				</tr>
					
				<tr>
					<td width="21%"  class="tabRight" >处理结果：</td>
					<td width="79%" style="text-align: left" colspan="3">
						<textarea id="content" name="content" class="text"  style="width: 96.5%;resize:none;height: 150px;">${clueInfo.content}</textarea>
					</td>
				</tr>		
				<%-- <tr>
					<td width="21%"  class="tabRight" >附件：</td>
					<td width="79%" id="files" style="text-align: left" colspan="3">
						<!-- 添加 -->
						<input type="file" name="attachMent_s" class="attachMent_s" id="attachMent_s" value="选择" />
						<img title="添加"  id="addfile"  src="${path}/resources/images/jia1.png"  style=" cursor:pointer ;margin: 0px;padding:0px;" />
						<input name="fileTip" id="fileTip" style="visibility:hidden;"/><span style="color: red;">文件大小不能超过70M!</span>
					</td>
				</tr> --%>		
			
						
			</table>
		<br/>
			<br/>
			
			</fieldset>
			<div style="margin: 5px 40%">
				<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="保 存" onclick="add()" />
				<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="返回" onclick="back()" />
			</div>
			
		</form>
		</div>
		
</body>
</html>