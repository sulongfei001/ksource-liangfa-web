<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link type="text/css" rel="stylesheet" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css"/>
<script src="${path }/resources/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
<!--[if lt IE 9]>
<script src="${path }/resources/html5/html5.js"></script> 
<script src="${path }/resources/html5/css3-mediaqueries.js"></script> 
<![endif]-->
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js" type="text/JavaScript"></script>
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery.form.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/script/FormBuilder.js"/>"></script>
<script src="<c:url value="/resources/jquery/jquery-ui-1.8.12.custom.min.js"/>"></script><script type="text/javascript">
	String.prototype.trim=function(){
	    return this.replace(/(^\s*)|(\s*$)/g, '');   
	}
	function showJsonView(formTitleName,jsonC){
		$('#jsonView').html($('#'+jsonC).html());
		$('#jsonView').dialog({
			width:800 ,
            title:'json视图-'+formTitleName
		});
	}
	//预览form
	function previewForm(formTitleName,jsonC){
		$('#formView').empty();
		var formJsonView = $.parseJSON($('#'+jsonC).html());//把字符串转换成json对象
		new FormBuilder(formJsonView).genFormPreview('formView');
		$('#formView').dialog({
			width:800,
            title:'表单视图-'+formTitleName
		});
	}
    $(function(){
        //按钮样式
        jQuery(".btn_small").mouseover(function(){
                jQuery(this).attr("class","btn_small_onmouseover");
        }).mouseout(function(){
                jQuery(this).attr("class","btn_small");
            });
    });
    
    function delForm(formDefId){
    	$.ligerDialog.confirm("是否删除表单？","提示信息",function(rtn){
    		if(rtn){
    			$.get("${path}/maintain/formmanage/del?formDefId="+formDefId,function(obj){
    				if(obj){
    					$.ligerDialog.success("删除成功!","提示信息",function(rtn){
    						window.location = "${path}/maintain/formmanage";
    					});
    				}else{
    					$.ligerDialog.warn("已绑定流程，无法删除！","提示信息");	
    				}
    			});
    		}
    	});
    }
    
    function updateForm(formDefId){
    	window.location = "${path}/maintain/formmanage/toUpdate?formDefId="+formDefId;
    }
    
</script>
<title>表单管理</title>
</head>
<body>
<div class="panel">
<display:table name="formDefList"  uid="formDef" pagesize="10" cellpadding="0" cellspacing="0"  requestURI="/maintain/formmanage">
	<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${formDef_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*10 + formDef_rowNum}
			</c:if>
	</display:column>
	<display:caption class="tooltar_btn">
		<input type="button" value="新 增" class="btn_small" onclick="window.location.href='<c:url value="/maintain/formmanage/toNew"/>'"/>
	</display:caption>
	<display:column title="表单模板名称" property="formDefName"  />
	<display:column title="操作">
        <a href="javascript:void(0);" onclick="showJsonView('${formDef.formDefName}','jsonC_${formDef.formDefId}')">json视图</a>
        <pre id="jsonC_${formDef.formDefId}" style="display: none;">${formDef.jsonView}</pre>
		<a href="javascript:void(0);" onclick="previewForm('${formDef.formDefName}','jsonC_${formDef.formDefId}')">表单视图</a>
		<a href="javascript:;" onclick="delForm('${formDef.formDefId}')">删除</a>
		<a href="javascript:;" onclick="updateForm('${formDef.formDefId}')">修改</a>
	</display:column>
</display:table>
<div title="json视图" id="jsonView">
</div>
<div title="form视图" id="formView">
</div>
    </div>
</body>
</html>