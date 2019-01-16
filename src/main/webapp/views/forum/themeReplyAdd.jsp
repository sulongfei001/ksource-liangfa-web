<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/forum.css" />
<link rel="stylesheet" href="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/script/prototip/css/prototip.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	type="text/javascript"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script charset="utf-8" src="${path }/resources/script/kindeditor/kindeditor-min.js"></script>

<script type="text/javascript">
jQuery.noConflict();
</script>
<script type='text/javascript' src='${path }/resources/script/prototype.js'></script>
<script type='text/javascript' src='${path }/resources/script/prototip/prototip.js'></script>
<script type="text/javascript">
jQuery(function(){
		//表单验证
		jqueryUtil.formValidate({
		form:"contentForm",
		rules : {
			attachment:{uploadFileLength:50}
		},
		messages : {
			attachment:{uploadFileLength:"附件文件名太长,必须小于50字,请修改后再上传!"}
		},
		blockUI:false,
		submitHandler:function(form){
			if(KE.isEmpty('sub_content')){
				top.art.dialog.alert("请输入内容!");
	    		return false;
			}
			KE.sync('sub_content');
			//提交表单
			form.submit();
		}
	 });
	 //返回按钮动作
	 jQuery('#backButton').click(function(){
		 window.location.href="${path}/themeReply/main/${theme.id}?replyId=${quoteReply.id}&page=${page}";
	 });
	 KE.show({
         id : 'sub_content',
         width: '80%',
         height: '300px',
         imageUploadJson:'${path}/upload/image'
        });
	 
		jQuery('[name=tipName]').each(
				  function(i){
					  //修改id
					  var id = "seqId_"+i;
			      	  jQuery(this).attr('id',id);
			      	  //得到参数
			      	  var param = jQuery(this).attr('param');
			      	  var url='${path}/forumTheme/userInfo?id='+param;
			      	  //给第一个name为tipName的组件创建tip....
					  new Tip("seqId_"+i, {
							title :'用户信息详情',
							ajax: {
								url: url
							},
							hideOthers:true,
							closeButton: true,
							showOn: 'click',
							hideOn: { element: 'closeButton', event: 'click'},
							stem: 'leftTop',
							hook: { target: 'rightMiddle', tip: 'topLeft' }, 
							offset: { x: 0, y: -2 },
							width: 300
						});  
				  }	
				);
	});
</script>
<style>
	tr.replytitle {
    background: #FEFEF6;
    color: #333;
    font-size: 1.1em;
    font-weight: bold;
    text-align: center;
    height: 30px;
    line-height: 30px;
    border: 1px solid #ccc;
}
</style>
</head>
<body>
<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">回复</legend>
<form id="contentForm" method="post"
					action="${path}/themeReply/add"
					enctype="multipart/form-data">
					<input name="themeId" type="hidden" value="${theme.id}"/>
					<table width="90%" class="table_add">
						<tr>
							<td width="20%" class="tabRight">内容</td>
							<td width="80%" style="text-align: left;">
							<textarea rows="10" id="sub_content" name="content">
							<c:if test="${!empty quoteReply}">
								${quoteReply.content}
							</c:if>
							</textarea>&nbsp;<font color="red">*必填</font>
							</td>
						</tr>
						<tr>
							<td width="20%" class="tabRight">附件</td>
							<td width="80%" style="text-align: left;">
							<input type="file" id="attachment" name="attachment" style="width:80%"/>
							</td>
		
						</tr>
						<tr align="center">
							<td colspan="2"><input type="submit" value="保&nbsp;存"  class="btn_small"/>
							<input id="backButton" type="button" class="btn_small" value="返&nbsp;回"/>
							</td>
						</tr>
					</table>
					<input type="hidden" value="${caseConsultation.id}"
						name="caseConsultationId" />
				</form>
				</fieldset>
				<br /><br />
				<h3 style="text-align: center;">主题</h3>
				<table id="theme"  width="100%" style="margin: 0px; padding: 0px ;">
						<tr class="replytitle">
							<td style="border-right:1px solid #ccc;">作者</td>
							<td>内容</td>
						</tr>
						<tr>
							<td class="postauthor" style="border-left:1px solid #ccc;">
								<ul>
									<li class="name">
									<a name="tipName" param="inputer_${theme.inputer}" href="javascript:void(0);">${theme.inputerName}</a>
									</li>
									<li>录入单位 : ${theme.orgName}</li>
								</ul>
							</td>
							<td class="postcontent" align="left" style="border-right:1px solid #ccc;">
								<div class="postactions">
									<div class="description" >
										&nbsp;&nbsp; 发表时间：
										<fmt:formatDate value="${theme.createTime}" pattern="yyyy-MM-dd" />
									</div>
									<div class="links"></div>
								</div>
								<div class="postbody clearfix">${theme.content}</div>
		 						<c:if test="${!empty theme.attachmentName}">
									<div class="attachments">
										<ul>
											<li>
											<span style="text-align: left;float:left;">附件:</span>
											<a href="${path}/download/forumTheme/${theme.id}"
												id="attachmentName">${theme.attachmentName}</a></li>
										</ul>
									</div>
								</c:if>
							</td>
						</tr>
					</table>
		</div>
</body>
</html>