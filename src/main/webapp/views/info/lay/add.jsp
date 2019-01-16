<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script charset="utf-8" src="${path }/resources/script/kindeditor/kindeditor-min.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="${path }/resources/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${path }/resources/ueditor/ueditor.all.js"></script>

<script type="text/javascript">
$(function(){
	//为初始化uediter
	var editor = UE.getEditor("content",{
        toolbars:[["fullscreen","bold",
                   "italic","underline","strikethrough","forecolor",
                   "backcolor","superscript","subscript","justifyleft",
                   "justifycenter","justifyright","justifyjustify","touppercase",
                   "tolowercase","directionalityltr","directionalityrtl",
                   "indent","removeformat","formatmatch","autotypeset","customstyle",
                   "paragraph",
                   "fontfamily","fontsize"]]
		 }); 
	
    jqueryUtil.formValidate({
    	form:"addForm",
    	rules:{
    		title:{required:true,maxlength:100},
    		typeId:{required:true},
    		publishDept:{required:true,maxlength:100},
    		createTime:{required:true}
    	},
    	messages:{
    		title:{required:"请输入标题!",maxlength:"请最多输入100位汉字!"},
    		typeId:{required:"请选择类型名称!"},
    		publishDept:{required:"请填写发布部门!",maxlength:"请最多输入100位汉字!"},
    		createTime:{required:"请填写发布时间!"}
    	},
    	submitHandler:function(form){
    		editor.sync();
			form.submit();
    	}
    });
    
	//设置时间点击事件
	$("#createTime").click(function(){
		WdatePicker(
				{dateFmt:'yyyy-MM-dd'}
				
		);
	});
	
	//设置时间点击事件
	$("#implementDate").click(function(){
		WdatePicker(
				{dateFmt:'yyyy-MM-dd'}
				
		);
	});
/*     //add editor
   	KE.show({
       id : 'content',
       width: '80%',
       height: '300px',
       imageUploadJson:'${path}/upload/image'
      }); */
});
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">法律法规新增</legend>
	<form:form id="addForm" method="post" action="${path }/info/lay/add" enctype="multipart/form-data">
		<table  width="90%" class="table_add">
			<tr>
				<td width="20%" class="tabRight">
					法律法规类型:
				</td>
				<td width="80%" style="text-align:left;" colspan="3">
					<select class="text" name="typeId"　id="typeId">
						<option value="" selected="selected">--请选择--</option>
						<c:forEach var="infoType" items="${infoTypes}" >
							<c:if test="${not empty selectTypeId }">
								<option value="${infoType.typeId}" <c:if test="${selectTypeId == infoType.typeId}">selected</c:if> >${infoType.typeName}</option>
							</c:if>
							<c:if test="${not empty info.typeId }">
								<option value="${infoType.typeId}" <c:if test="${info.typeId == infoType.typeId}">selected</c:if> >${infoType.typeName}</option>
							</c:if>
						</c:forEach>
					</select>
					&nbsp;<font color="red">*必选</font>
				</td>
			</tr>
			
			<tr>
				<td width="20%" class="tabRight">
					标题:
				</td>
				<td width="80%" style="text-align:left;" colspan="3">
					<input type="text" name="title" class="text" value="${info.title }"/>&nbsp;<font color="red">*必填</font>
					<input type="hidden" name="infoId" value="${info.infoId }"/>
					<input type="hidden" name="orgId" value="${info.orgId }"/>
					
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					发布部门:
				</td>
				<td width="80%" style="text-align:left;" colspan="3">
					<input type="text" class="text" name="publishDept" id="publishDept" value="${info.publishDept }"/>&nbsp;<font color="red">*必填</font>
				</td>
			</tr>
	
			<tr>
				<td width="15%" class="tabRight">
					发布日期:
				</td>
				<td width="35%" style="text-align:left;">
					<input type="text" class="text" name="createTime" id="createTime" readonly="readonly" value="<fmt:formatDate value="${info.createTime }"  pattern="yyyy-MM-dd"/>" />
					&nbsp;<font color="red">*必填</font>
				</td>
				<td width="15%" class="tabRight">
					实施日期:
				</td>
				<td width="35%" style="text-align:left;">
					<input type="text" class="text" name="implementDate" id="implementDate" readonly="readonly"  value="<fmt:formatDate value="${info.implementDate }"  pattern="yyyy-MM-dd"/>"/>
					
				</td>
			</tr>
			
			
			<tr>
				<td width="20%" class="tabRight">
					内容:
				</td>
				<td width="80%" style="text-align:left;" colspan="3">
					<textarea name="content" rows="3" style="width:90%" id="content">${info.content}</textarea>
<!-- 					&nbsp;<font color="red">*必填</font> -->
				</td>
			</tr>

			<tr>
				<td align="center" colspan="4">
					<input type="submit" value="保&nbsp;存"  class="btn_small"/>
					<input type="button" value="返&nbsp;回"  class="btn_small" onclick="javascript:window.location.href='${path}/info/lay/back'"/>					
				</td>
			</tr>
		</table>
	</form:form>
	</fieldset>
</div>
</body>
</html>