<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/common.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/displaytagall.css"/>" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/noticedetail.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript"
	src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<%-- <script type="text/javascript"
	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.js"></script> --%>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/jquery.form.js"></script>
<script
	src="<c:url value="/resources/jquery/jquery-ui-1.8.12.custom.min.js"/>"></script>
<script src="<c:url value="/resources/script/jqueryUtil.js"/>"></script>
<script src="${path }/resources/jquery/jquery.scrollTo.js"></script>
<script charset="utf-8" src="${path }/resources/script/kindeditor/kindeditor-min.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript">
    var appPath='${path}/';
	$(function(){
		$("#reply").click(function() {
			var content = $("#content").val();
			if(content == ""||content.trim()==""){
				$.ligerDialog.warn("请输入回复内容！");
				return false;
			}
			var id = '${caseConsultation.id }';
			$.post("${path}/caseConsultation/addContent",
					{caseConsultationId:id,content:content},
					function(data){
						if(data){
							$("#content").val("");
							var obj = eval('('+data+')');
							addContent(obj);
							//$("iframe").attr("src","${path }/instruction/instructionReply/list.ht?instructionId="+instructionId);
						}else{
							$.ligerDialog.warn("回复失败，请稍后重试！");
						}
			});
		});
		//表单验证
		/* jqueryUtil.formValidate({
		form:"contentForm",
		submitHandler:function(form){
			if(KE.isEmpty('sub_content')){
				top.art.dialog.alert("请输入内容!");
	    		return false;
			}
			//KE.sync('sub_content');
			//提交表单
			form.submit();
		}
	 }); */
/* 		 KE.show({
	         id : 'sub_content',
	         width: '80%',
	         height: '300px',
	         imageUploadJson:'${path}/upload/image'
	        });
        var scrollId = '${contentId}';
		if (scrollId) {
			jQuery('#' + scrollId).ScrollTo();
		}*/
	}); 
	function addContent(obj){
/* 	   if(isRefresh(content)){
	     return ;
	   } */
	   var  attachDiv ='';
/* 		if(content.attachmentName){
		   attachDiv = '<div style="padding-top: 5px; padding-left: 35px; height: 15px;">附件:'+
					'<a href=\''+appPath+'/download/caseConsultationContent?contentId='+content.id+'\''+
						'id="attachmentName">'+content.attachmentName+'</a>'+
				'</div>';
		} */
		var temp=[
			'<div style="width: 750px; padding-top: 20px; padding-bottom: 5px; font-size: 13px;border-bottom:#CCC 1px solid; overflow: hidden;">',
					'<div style="float: right; width: 590px;">',
					'<div style="padding-top: 20px; padding-left: 35px; ">',
						'<span style="font-size: 12px; color: #0E75DA">',
						'回复时间:','<span>',getyyyyMMddhhmmssStr(obj.participateTime),'</span>',
						'</span>',
				    '</div>',
				'<div style="padding-top: 15px; padding-left: 35px;">',obj.content,'</div>',
			'</div>',
			'<div style="width: 75px; height: 59px;  text-align: center; padding-left: 25px; padding-top: 15px">',
				'<img src="',appPath,'resources/images/msnuser.png" width="50" height="50" />',
			'</div>',
			'<div style="width: 150px; height: 30px; text-align: center;">',obj.userName,'</div>',
		'</div>'
		];
		$('#contentDiv').prepend(temp.join(''));
		$.unblockUI();
		//清空数据
		//KE.html('sub_content','');
		$('#attachment').val("");
		$('#sub_content').val("");
		location.reload();
	}
	//判断页面是不是通过浏览器刷新过来的,
	//原理是通过判断页面上是不是已经有了同一时间的信息，如果有就是刷新过来的。
	function isRefresh(content){
	var isRefresh= false;
	    var dateStr = getyyyyMMddhhmmssStr(content.participateTime);
	    $("#contentDiv span span").each(function(i){
	         if($.trim(dateStr)==$.trim($(this).html())){
	         isRefresh=true;
	         }
	    });
	    return isRefresh;
	}
	function getyyyyMMddhhmmssStr(milliseconds){
		var date = new Date();
		date.setTime(milliseconds);
		var yyyy=date.getFullYear().toString();
		var MM = (date.getMonth()+1).toString();
		if(MM.length==1){
			MM='0'+MM;
		}
		var dd = date.getDate().toString();
		if(dd.length==1){
			dd='0'+dd;
		}
		var hh = date.getHours().toString();
		if(hh.length==1){
			hh='0'+hh;
		}
		var mi = date.getMinutes().toString();
		if(mi.length==1){
			mi='0'+mi;
		}
		var ss = date.getSeconds().toString();
		if(ss.length==1){
			ss='0'+ss;
		}
		return yyyy+'-'+MM+'-'+dd+' '+hh+':'+mi+':'+ss;
	}
	function closeConsultation(id){
	     $.ligerDialog.confirm('确认结束案件咨询吗？',function(flag){
	    	 if(flag){
	      		$.getJSON('${path}/caseConsultation/endCon/'+id, function(json){
	    	  	//var obj = eval('('+json+')');
            	 $('#endButton').attr('disabled',"disabled").parent().append('<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;*咨询已结束</font>');
            	 $('a[name=editLink]').remove();
            	 $('#stateSpan').html('咨询结束');
            	 $('#postDiv').remove();
			     top.art.dialog.tips(json.msg, 2.0);
			});
	    	 }
	     });
    }
</script>
<style>
#caseInfoC{padding: 6px;position: absolute;top:20px;right: 15px;}
#caseInfoC a{color: red;}
fieldset, legend{
    margin: 0;
    padding: 0;
}
legend { padding: 0.5em 4em; font-size: 16px;}
</style>
<title>案件咨询详情</title>
</head>
<body style="margin-left: 10px;">
	<div>
		<h3>>>案件咨询基本信息</h3>
	<div title="center" region="center" style="z-index:0;">
		<div >
			<div align="center" id="main">
				<div class="detail_body">
					<div class="detail_title"><label id="_id2">${caseConsultation.title}</label>
					</div>
					<div class="detail_rec" style=" border-bottom: 1px solid #d1d1d1; margin-bottom: 20px;">
						<p>
							<strong>发布人：</strong><label id="_id4">${caseConsultation.userName}</label>&nbsp;&nbsp;
							<label id="_id4">（${caseConsultation.orgName}）</label>&nbsp;&nbsp;
							<strong>发布时间：</strong><label id="_id6"><fmt:formatDate value="${caseConsultation.inputTime}" pattern="yyyy-MM-dd"/></label>&nbsp;&nbsp;
							<!--  <strong>状态：</strong><label id="_id6">
							<dic:getDictionary var="ele" groupCode="caseConsultationState"
								dicCode="${caseConsultation.state}" /><span id="stateSpan">${ele.dtName}</span>
							</label>&nbsp;&nbsp;-->
							
						</p>
					</div>
					<div class="content">
					<c:if test="${not empty caseInfo}">
					<strong>案件信息：</strong>
							<label id="_id4">${caseInfo.caseName}</label>&nbsp;&nbsp;
							<label id="_id4"><a href="javascript:;" onclick="top.showCaseProcInfo('${caseInfo.caseId}');" title="点击查看案件详情">（${caseInfo.caseNo}）</a></label>&nbsp;&nbsp;
					</br></br>
					</c:if>
					${caseConsultation.content}
					</div>
					<div class="detail_bottom">
						<table border="0" cellpadding="0" cellspacing="0">
							<c:if test="${!empty caseConsultation.attachmentName}">
							<tr>
								<td style="font:italic bold 12px/30px arial,sans-serif; line-height: 20px;">附件:&nbsp;</td>
								<td style="text-align: left;" ><a href="${path}/download/caseConsultation?consultationId=${caseConsultation.id}"
								id="attachmentName" title="点击下载附件">${caseConsultation.attachmentName}</a></td>
							</tr>
							</c:if>
						</table>
					</div>
				</div>
			</div>
		</div>
    </div>    
</div>	
	
		<c:if test="${caseConsultation.state==1}">
				<br />
			<div class="content_huifu" id="postDiv">
				<div class="cont_show" >
					<div class="text_box" style="border: 1px solid #d1d1d1;">
						<div class="text_area">
							<textarea class="pl" id="content" rows="3" cols="5" name="content" style=" width: 96%; margin: 0 2%; outline: none; border: none;"></textarea>
						</div>
						<div class="list_btn" style="border-top:1px solid #d1d1d1; height: 34px ;">
						   <input class="btn_small" style="margin:5px; float: right;" id="reply"  type="button"   value="回复" />
						</div>
					</div>
				</div>
			</div>
	</c:if>
	
 <div id="contentDiv"   class="content_huifu">
 <c:if test="${!empty contentList}">
 <br/>
	<c:forEach items="${contentList}" var="content">
		<div id="${content.id}" style="padding-bottom: 5px; font-size: 13px;  border-bottom:#CCC 1px solid;overflow: hidden;">
			<div>

				<div style="padding-top: 20px; ">
					 <span style="font-size: 12px; color: #0E75DA">回复时间:
					    <span> <fmt:formatDate value="${content.participateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
					 </span>
					 &nbsp;&nbsp;&nbsp;&nbsp;
					 <%-- <c:if test="${currentUserId==content.userId and caseConsultation.state==1}">
					  <a href="${path}/caseConsultation/updateContentUI?id=${content.id}" name="editLink">编辑</a>
					 </c:if> --%>
				</div>
               <%--  <c:if test="${!empty content.attachmentName}">
				<div style="padding-top: 5px; padding-left: 35px; height: 15px;">
                                                   附件:
					<a href="${path}/download/caseConsultationContent?contentId=${content.id}"
						id="attachmentName">${content.attachmentName}</a>
				</div>
				</c:if> --%>
				<div style="padding-top: 15px; padding-left:20px">${content.content}</div>

			</div>

			<div style="width: 100%; height: 24px; line-height:24px; padding-top: 15px">
				<img src="${path}/resources/images/msnuser.png"  width="24" height="24" style="display:block; float: left; margin-right: 15px" />
			    ${content.userName}
			</div>
			
		</div>
	</c:forEach>
	</c:if>
	</div>
</body>
</html>