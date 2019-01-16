<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		//回复验证
		jqueryUtil.formValidate({
			form:"formID",
			blockUI:false,
			rules:{
				msgTitle:{required:true,maxlength:100},
				msgBody:{required:true,maxlength:2000}
			},
			messages:{
				msgTitle:{required:"标题不能为空",maxlength:"请最多输入100位汉字!"},
				msgBody:{required:"信息不能为空",maxlength:"请最多输入2000位汉字!"}
			},
			submitHandler:function(form){
				var msgBody = $("#msgBody").val() ;
				var msgTitle=$('#msgTitle').val();
				var	replyMsgId = "${userMsgObject.id}" ;
				$.ajax({
					type:"post" ,
					url:"${path}/usermsg/userMsgReplyAdd" ,
					data:{msgTitle:msgTitle,to:"${userMsgObject.from}",msgBody:msgBody,replyMsgId:replyMsgId},
					dataType:"json",
					success:function(data) {
					  if(data.result==true) {
						  setTimeout("closeDialog()",2000);
						  top.art.dialog.tips('回复成功,窗口将自动关闭',2.0);
					  }
					}
				}) ;
			}
	 	});
	}); 
	function closeDialog(){
		if(top.quickReplayDialog){
			top.quickReplayDialog.close();
		}
		if(top.quickReplayDialog_noread){
			top.quickReplayDialog_noread.close();
		}
	}
</script>
</head>

<body>
<form id="formID" class="formular" method="post" style="margin: 5px ;">
<table  class="blues" style="margin: 0px ;">
   <tr>
			<td class="tabRight" style="width: 20%">
				信息标题
			</td>
			<td style="text-align: left;">
				<input type="text" id="msgTitle" name="msgTitle" style="text-align: left;width:80%;" />
				&nbsp;<font color="red">*必填</font>
			</td>
		</tr>
		<tr>
			<td  class="tabRight" width="20%">
				信息内容
			</td>
			<td style="text-align: left;">
				<textarea id="msgBody" name="msgBody" rows="10" style="width:80%;"></textarea>&nbsp;<font color="red">*必填</font>
			</td>
		</tr>
		<tr>
		<td colspan="2">
			<input type="submit" value="回复"/>
			<input id="clearButton" type="button" value="重置"/>
		</td>
	</tr>
	</table>
</form>
</body>
</html>