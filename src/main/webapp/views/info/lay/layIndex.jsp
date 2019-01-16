<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery.form.js"/>"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<title>Insert title here</title>
<style type="text/css">
.indexli li{float:left;width:33%;}
</style>
<script type="text/javascript">
$(function(){
	var openTabTip ;
	var isSucess;
	var openTabTip2;
	var isSucess2;
	var caseOpenTabTip;
	var caseIsSucess;	
		$("#layIndexForm").ajaxStart(function(){
            isSucess=false;
			openTabTip = art.dialog({
				content:'正在创建索引，请稍后...',
				left: '50%',
			    top: '50%',
				resize:false,
				drag:false,
				esc:false,
				title:'',
			    icon: 'ajax-loader',
			    lock: true,
                closeFn:function(){
                    if(isSucess){
                        return true;
                    }
                    return false;
                },
                lock:true,
			    opacity: 0.2 // 透明度
			}) ; 
		}).ajaxSuccess(function(evt, request, settings){
            isSucess=true;
			openTabTip.close() ;
		}) ;
		
		$("#layIndexForm").ajaxForm({
			success:function(data){
				if(data.result==true){
					top.art.dialog.alert("索引创建成功!");
				}else{
					top.art.dialog.alert(data.msg);
				}
			},
			dataType:'json'
		});
		
		$("#contentIndexForm").ajaxStart(function(){
            isSucess2=false;
			openTabTip2 = art.dialog({
				content:'正在创建索引，请稍后...',
				left: '50%',
			    top: '50%',
				resize:false,
				drag:false,
				esc:false,
				title:'',
			    icon: 'ajax-loader',
			    lock: true,
                closeFn:function(){
                    if(isSucess){
                        return true;
                    }
                    return false;
                },
                lock:true,
			    opacity: 0.2 // 透明度
			}) ; 
		}).ajaxSuccess(function(evt, request, settings){
            isSucess2=true;
			openTabTip2.close() ;
		}) ;
		
		$("#contentIndexForm").ajaxForm({
			success:function(data){
				if(data.result==true){
					top.art.dialog.alert("索引创建成功!");
				}else{
					top.art.dialog.alert(data.msg);
				}
			},
			dataType:'json'
		});
		
		$("#caseIndexForm").ajaxStart(function(){
			caseIsSucess=false;
			caseOpenTabTip = art.dialog({
				content:'正在创建索引，请稍后...',
				left: '50%',
			    top: '50%',
				resize:false,
				drag:false,
				esc:false,
				title:'',
			    icon: 'ajax-loader',
			    lock: true,
                closeFn:function(){
                    if(isSucess){
                        return true;
                    }
                    return false;
                },
                lock:true,
			    opacity: 0.2 // 透明度
			}) ; 
		}).ajaxSuccess(function(evt, request, settings){
			caseIsSucess=true;
			caseOpenTabTip.close() ;
		}) ;
		
		$("#caseIndexForm").ajaxForm({
			success:function(data){
				if(data.result==true){
					top.art.dialog.alert("索引创建成功!");
				}else{
					top.art.dialog.alert(data.msg);
				}
			},
			dataType:'json'
		});
		
		$("#caseIndexFormStat").ajaxStart(function(){
			caseIsSucess=false;
			caseOpenTabTip = art.dialog({
				content:'正在创建索引，请稍后...',
				left: '50%',
			    top: '50%',
				resize:false,
				drag:false,
				esc:false,
				title:'',
			    icon: 'ajax-loader',
			    lock: true,
                closeFn:function(){
                    if(isSucess){
                        return true;
                    }
                    return false;
                },
                lock:true,
			    opacity: 0.2 // 透明度
			}) ; 
		}).ajaxSuccess(function(evt, request, settings){
			caseIsSucess=true;
			caseOpenTabTip.close() ;
		}) ;
		
		$("#caseIndexFormStat").ajaxForm({
			success:function(data){
				if(data.result==true){
					top.art.dialog.alert("索引创建成功!");
				}else{
					top.art.dialog.alert(data.msg);
				}
				
			},
			dataType:'json'
		});
		
});
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset" style="background-color:#f4f9fd;">
	<legend class="legend">法律法规索引</legend>
<form:form id="layIndexForm" action="${path }/lucene/createLay" method="post" modelAttribute="info">
		<table border="0" cellpadding="2" cellspacing="1" width="100%">
			<tr>
				<td style="width: 10%;text-align: right;">标题:</td>
				<td style="width: 30%;text-align: left;"><input name="title" id="title" class="text"/></td>
				<td style="width: 10%;text-align: right;">法律法规分类:</td>
				<td style="width: 30%;text-align: left;">
						<select name="typeId" id="typeId">
						<option value="">--全部--</option>
						<c:forEach var="infoType" items="${infoTypes}" >
							<option value="${infoType.typeId}">${infoType.typeName}</option>
						</c:forEach>
					</select>
				</td>
				<td style="width: 20%;"><input type="submit" class="btn_big" value="创建索引" />
				</td>
			</tr>
		</table>
	</form:form>
	</fieldset>
	
	<fieldset class="fieldset" style="background-color:#f4f9fd;">
	<legend class="legend">网站文章索引</legend>
<form:form id="contentIndexForm" action="${path }/lucene/createContent" method="post" modelAttribute="info">
		<table border="0" cellpadding="2" cellspacing="1" width="100%">
			<tr>
				<td style="width: 20%;"><input type="submit" class="btn_big" value="创建索引" />
				</td>
			</tr>
		</table>
	</form:form>
	</fieldset>
	
	<fieldset class="fieldset">
	<legend class="legend">业务版案件信息索引</legend>
	<form:form id="caseIndexForm" action="${path }/lucene/createCase" method="post" modelAttribute="info">
		<table border="0" cellpadding="2" cellspacing="1" width="100%">

			<tr>
				<td style="width: 20%;" colspan="2"><input type="submit" class="btn_big" value="创建索引" />
				</td>
			</tr>
		</table>
	</form:form>
	</fieldset>	
	
	<fieldset class="fieldset">
	<legend class="legend">分析版案件信息索引</legend>
	<form:form id="caseIndexFormStat" action="${path }/lucene/statCreateCase" method="post" modelAttribute="info">
		<table border="0" cellpadding="2" cellspacing="1" width="100%">

			<tr>
				<td style="width: 20%;" colspan="2"><input type="submit" class="btn_big" value="创建索引" />
				</td>
			</tr>
		</table>
	</form:form>
	</fieldset>		
	
	</div>
</body>
</html>