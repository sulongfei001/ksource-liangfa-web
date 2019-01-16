<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript"	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<title>共享资源主页</title>
<script type="text/javascript">
	$(function() {
		<c:if test="${info !=null}">
			art.dialog.tips("${info}",2);
		</c:if>
		zTree=$('#dropdownMenu').zTree({
			isSimpleData:true,
			treeNodeKey:"id",
			treeNodeParentKey:"upId",
			async:true,
			asyncUrl:"${path}/system/org/loadChildOrg",
			asyncParam:["id"],
			callback: {
				click: zTreeOnClick
			}
		});
		
		//鼠标点击页面其它地方，组织机构树消失
		$("html").bind("mousedown", 
				function(event){
					if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
						hideMenu();
					}
				});
		
		var startTime = document.getElementById('startTime');
		startTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}		
		var endTime = document.getElementById('endTime');
		endTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		
		jqueryUtil.formValidate({
			form:"resourceAddForm",
			rules:{
			 	resourceFile:{required:true,uploadFileLength:50}
			},
			messages:{
			    resourceFile:{required:"请选择文件!",uploadFileLength:"附件文件名太长,必须小于50字,请修改后再上传!"}
			},
		    submitHandler:function(form){
			    dialog.close() ;
			    form.submit();
		    }
		 });
		
		updateHref() ;
	});
	
	function showMenu(){
		var cityObj=$("#uploadOrgName");
		var cityOffset=$("#uploadOrgName").offset();
		$("#DropdownMenuBackground").css({left:cityOffset.left+"px",top:cityOffset.top+cityObj.outerHeight()+"px"}).slideDown("fast");
	}
	function hideMenu(){
		$("#DropdownMenuBackground").fadeOut("fast");
	}
	
	function zTreeOnClick(event,treeId,treeNode){
		if(treeNode){
			$("#uploadOrgName").attr("value",treeNode.name);
			$("#uploadOrgId").attr("value",treeNode.id);
			hideMenu();
		}
		
	}
	
	function resourceAdd() {
		dialog = art.dialog({
		    title: '资源共享添加',
		    content: document.getElementById('resourceAddDiv'),
		    lock:true,
			opacity: 0.1,
		    yesFn: function(){
				 $('#resourceAddForm').submit();
				 return false;
		    },
		    noFn: function(){dialog.close();}
		});
	}
	function con(fileId) {
		art.dialog.open("${path}/resourceOrg/resourceOrgUI?fileId=" + fileId
				+ "&&redirect=false", {
			id : fileId,
			title : '关联部门',
			height : 505,
			resize : false,
			lock : true,
			opacity : 0.1,// 透明度
			closeFn : function() {
				location.href = "${path}/resource/back";
			}
		}, false);
	}
	function emptyOrg(){
		$("#uploadOrgName").val("");
		$("#uploadOrgId").val("");
	}
	
	function isClearOrg(){
		var uploadOrgName=$("#uploadOrgName").val();
		if($.trim(uploadOrgName)==""){
			$("#uploadOrgId").val("");
		}
	}

    //修改超链接的参数，以防止页面添加或者修改成功后，每次点击“下一页”超链接出现信息提示框
    function updateHref() {
        $(".pagebanner > a").each(function() {
            var a =  $(this) ;
            var hrefstring  = a.attr("href") ;
            $.each(['response_msg=add&','response_msg=update&','response_msg=del'],function(i,n) {
                var i = hrefstring.search(n) ;
                if(i != -1) {
                    hrefstring = hrefstring.replace(n,"") ;
                    a.attr("href",hrefstring) ;
                }
            }) ;
        }) ;
    }
	function batchDelete(checkName){
		var flag ;
		var name ;
		for(var i=0;i<document.forms[1].elements.length;i++){
			
			name = document.forms[1].elements[i].name;
			if(name.indexOf(checkName) != -1){
				if(document.forms[1].elements[i].checked){
					flag = true;
					break;
				}
			}
		}   	
		if(flag){
			 top.art.dialog.confirm("确认删除吗？",
					function(){jQuery("#delForm").submit();}
			);
		}else{
			top.art.dialog.alert("请选择要删除的记录!");
		}
		return false;
	}

	function checkAll(obj){
	jQuery("[name='check']").attr("checked",obj.checked) ; 			
	}	
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">资源共享查询</legend>
		<form:form action="${path}/resource/main" method="post" modelAttribute="fileResource" onsubmit="isClearOrg()">
			<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
				<tr>
					<td width="10%" align="right">文件名：</td>
					<td width="25%" align="left"><form:input path="fileName" class="text" /></td>
					<td width="10%" align="right">上传时间：</td>
					<td width="25%" align="left">
					<input type="text" class="text" id="startTime" name="startTime" value="<fmt:formatDate value='${fileResource.startTime }' pattern='yyyy-MM-dd'/>" style="width: 36%" /> 
					到 
					<input type="text" class="text" id="endTime" name="endTime" value="<fmt:formatDate value='${fileResource.endTime }' pattern='yyyy-MM-dd'/>" style="width: 36%" /></td>
					<td width="30%" align="left" rowspan="2" valign="middle">
						<input type="submit" value="查 询" class="btn_query" />
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">上传人：</td>
					<td width="25%" align="left"><form:input path="uploaderName" class="text" /></td>
					<c:if test="${admin == true }">
						<td width="10%" align="right">上传单位：</td>
						<td width="25%" align="left">
						<input id="uploadOrgName" class="text" readonly="readonly" name="uploadOrgName" type="text" style="width: 79%;" onfocus="showMenu();return false;" value="${fileResource.uploadOrgName }"/>
						<input id="uploadOrgId"  name="uploadOrgId" type="hidden" value="${fileResource.uploadOrgId}"/>
						<a href="javascript:void();" onclick="emptyOrg()" class="aQking qingkong">清空</a>
						</td>
					</c:if>
				</tr>
			</table>
		</form:form>
		</fieldset>
		
		<form:form id="delForm" action="${path}/resource/batch_delete" method="post">
		<display:table name="resourceList" uid="resource" cellpadding="0" cellspacing="0" requestURI="${path}/resource/main">
			<display:caption class="tooltar_btn">
				<input type="button" class="btn_small" value="添&nbsp;加" onclick="resourceAdd()" />
				<input type="submit" class="btn_big" value="批量删除" name="del"onclick="return batchDelete('check')"/>
			</display:caption>
			<display:column title="<input type='checkbox' onclick='checkAll(this)'/>">
				<input type="checkbox" name="check" value="${resource.fileId}" />
			</display:column>
			<display:column title="序号">
				<c:if test="${page==null || page==0}">
				${resource_rowNum}
			</c:if>
				<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + resource_rowNum}
			</c:if>
			</display:column>
			<display:column title="文件名" style="text-align:left; width:30%" class="cutout">
				<span title="${resource.fileName}">${fn:substring(resource.fileName,0,30)}${fn:length(resource.fileName)>30?'...':''}</span>
			</display:column>
			<display:column title="上传人" property="uploaderName" style="text-align:left;"></display:column>
			<display:column title="上传单位" property="uploadOrgName" style="text-align:left;"></display:column>
			<display:column title="上传时间" style="text-align:left;">
				<fmt:formatDate value="${resource.uploadTime}" pattern="yyyy-MM-dd" />
			</display:column>
			<display:column title="操作">
				<a href="${path }/download/resource?fileId=${resource.fileId}">下载</a>
				<a href="javascript:;" onclick="top.art.dialog.confirm('确认要删除吗？',function(){location.href = '${path}/resource/delete?fileId=${resource.fileId}';})">删除</a>
				<c:if test="${resource.resourceHaveRelevance==true}">
					<a href="javascript:con(${resource.fileId})">关联部门</a>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
				<c:if test="${resource.resourceHaveRelevance!=true}">
					<a href="javascript:con(${resource.fileId})">关联部门</a>
					<img src="${path}/resources/images/infolevel.gif" title="尚未关联部门" />
				</c:if>
			</display:column>
		</display:table>
		</form:form>
</div>

	<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:30%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<ul id="dropdownMenu" class="tree"></ul>
	</div>
	
	<!-- 添加资源信息-->
	<div style="display: none" id="resourceAddDiv">
		<form id="resourceAddForm" action="${path }/resource/addResource" method="post" enctype="multipart/form-data">
			<table id="resourceAddTable" class="table_add" style="width: 400px">
				<tr>
					<td class="tabRight" width="20%">文件:</td>
					<td style="text-align: left" width="80%">
						<input type="file" name="resourceFile" id="resourceFile"/>
						<font color="red">*必选</font>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>