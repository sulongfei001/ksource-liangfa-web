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
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"/>
<script src="${path }/resources/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/lib/jquery.metadata.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<title>预警规则添加页面</title>
<script type="text/javascript">
$(function(){
	jqueryUtil.formValidate({
	    form:"addForm",
	    rules:{
	        conditionTitle:{required:true,maxlength:100},
	        judgeValue:{required:true,appNumber:[10,2]},
	          warnType:{remote:'${path}/basic/warnCondition/getCountByWarType?conditionId=${warnCondition.conditionId}'}
	    },
	    messages:{
	        conditionTitle:{required:"规则名称不能为空!",maxlength:'请最多输入50位汉字!'},
	        judgeValue:{required:"规则不能为空",appNumber:"请输入数字(整数最多10位，小数最多2位)"},
	        warnType:{remote:'同一种类型的预警规则只能添加一条！'}
	    },submitHandler:function(form){
	          if($("#addForm").valid()){
	              form.submit();
	              //提交按钮禁用
	              $("#saveButton").attr("disabled",true);
	          }
	          return false;
	    }
	});
	
});

  function resetCondition(warnType){
	  $("#warnType_td").empty();
	  $("#warnType_td").append($("#warnType_"+warnType).html());
  }
  
  function saveCondition(){
	  $("#addForm").submit();
  }
  
  function back(){
	  window.location = "${path}/basic/warnCondition/list";
  }
</script>
</head>

<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">预警规则设置</legend>
		<form id="addForm" action="${path}/basic/warnCondition/save" method="post" enctype="multipart/form-data">
			<input type="hidden" name="conditionId" value="${warnCondition.conditionId }"/>
			<table width="90%" class="table_add">
				<tr>
					<td width="20%" class="tabRight">规则名称:</td>
					<td width="80%" style="text-align:left;">
					<input type="text" id="conditionTitle" name="conditionTitle" class="text" style="width: 60%;" value="${warnCondition.conditionTitle }"/>
					<font color="red">*必填</font>
					</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">预警类型:</td>
					<td width="80%" style="text-align:left;">
                        <select name="warnType" onchange="resetCondition(this.value)" style="width: 60%;">
                            <option value="1" <c:if test="${warnCondition.warnType == 1}">selected="selected"</c:if>>办理超时预警</option>
                            <option value="2" <c:if test="${warnCondition.warnType == 2}">selected="selected"</c:if>>追诉预警</option>
                            <option value="3" <c:if test="${warnCondition.warnType == 3}">selected="selected"</c:if>>滞留超时预警</option>
                        </select>
					</td>
				</tr>
                <tr>
                    <td width="20%" class="tabRight">规则: </td>
                    <td width="80%" style="text-align:left;" id="warnType_td">
                        <c:if test="${warnCondition.warnType == 1 or empty warnCondition.warnType}">
                         <div id="warnType_1">
						    <select style="width: 10%">
						        <option value="sysdate">当前时间</option>
						    </select>
						    <!-- 运算类型 -->
						    <select name="optType" style="width: 10%">
						        <option value="-" <c:if test="${warnCondition.optType eq '-'}">selected="selected"</c:if>>减去</option>
						        <option value="+" <c:if test="${warnCondition.optType eq '+'}">selected="selected"</c:if>>加上</option>
						    </select>
						    <select name="judgeCol" style="width: 10%">
						        <option value="create_time" <c:if test="${warnCondition.judgeCol eq 'create_time'}">selected="selected"</c:if>>办理时间</option>
						    </select>
						     <!-- 判断类型 -->
						    <select name="judgeType" style="width: 10%">
						        <option value="&gt;" <c:if test="${warnCondition.judgeType eq '&gt;'}">selected="selected"</c:if>>大于</option>
						        <option value="&lt;" <c:if test="${warnCondition.judgeType eq '&lt;'}">selected="selected"</c:if>>小于</option>
						        <option value="=" <c:if test="${warnCondition.judgeType eq '='}">selected="selected"</c:if>>等于</option>
						        <option value="&gt;=" <c:if test="${warnCondition.judgeType eq '&gt;='}">selected="selected"</c:if>>大于等于</option>
						         <option value="&lt;=" <c:if test="${warnCondition.judgeType eq '&lt;='}">selected="selected"</c:if>>小于等于</option>
						    </select>
						    <input type="text" name="judgeValue" style="width: 18%;" value="${warnCondition.judgeValue }"/><font style="margin-left: 5px;">天</font><font color="red">*必填</font>
						</div>
						</c:if>
						<c:if test="${warnCondition.warnType == 2 }">
						<div id="warnType_2">
						    <select name="judgeCol" style="width: 15%;">
						        <option value="AMOUNT_INVOLVED">涉案金额</option>
						    </select>
						    <!-- 判断类型 -->
						    <select name="judgeType" style="width: 15%;">
                                <option value="&gt;" <c:if test="${warnCondition.judgeType eq '&gt;'}">selected="selected"</c:if>>大于</option>
                                <option value="&lt;" <c:if test="${warnCondition.judgeType eq '&lt;'}">selected="selected"</c:if>>小于</option>
                                <option value="=" <c:if test="${warnCondition.judgeType eq '='}">selected="selected"</c:if>>等于</option>
                                <option value="&gt;=" <c:if test="${warnCondition.judgeType eq '&gt;='}">selected="selected"</c:if>>大于等于</option>
                                 <option value="&lt;=" <c:if test="${warnCondition.judgeType eq '&lt;='}">selected="selected"</c:if>>小于等于</option>
						    </select>
						    <input type="text" name="judgeValue" style="width: 25%;" value="${warnCondition.judgeValue }"/><font style="margin-left: 5px;">元</font><font color="red">*必填</font>
						</div>
						</c:if>
                        <c:if test="${warnCondition.warnType == 3}">
                         <div id="warnType_3">
                            <select style="width: 10%">
                                <option value="sysdate">当前时间</option>
                            </select>
                            <!-- 运算类型 -->
                            <select name="optType" style="width: 10%">
                                <option value="-" <c:if test="${warnCondition.optType eq '-'}">selected="selected"</c:if>>减去</option>
                                <option value="+" <c:if test="${warnCondition.optType eq '+'}">selected="selected"</c:if>>加上</option>
                            </select>
                            <select name="judgeCol" style="width: 10%">
                                <option value="yisong_time" <c:if test="${warnCondition.judgeCol eq 'yisong_time'}">selected="selected"</c:if>>移送时间</option>
                            </select>
                             <!-- 判断类型 -->
                            <select name="judgeType" style="width: 10%">
                                <option value="&gt;" <c:if test="${warnCondition.judgeType eq '&gt;'}">selected="selected"</c:if>>大于</option>
                                <option value="&lt;" <c:if test="${warnCondition.judgeType eq '&lt;'}">selected="selected"</c:if>>小于</option>
                                <option value="=" <c:if test="${warnCondition.judgeType eq '='}">selected="selected"</c:if>>等于</option>
                                <option value="&gt;=" <c:if test="${warnCondition.judgeType eq '&gt;='}">selected="selected"</c:if>>大于等于</option>
                                 <option value="&lt;=" <c:if test="${warnCondition.judgeType eq '&lt;='}">selected="selected"</c:if>>小于等于</option>
                            </select>
                            <input type="text" name="judgeValue" style="width: 18%;" value="${warnCondition.judgeValue }"/><font style="margin-left: 5px;">天</font><font color="red">*必填</font>
                        </div>
                        </c:if>						
                    </td>
                </tr>
				<tr>
					<td class="tabRight" width="20%">规则描述:</td>
					<td width="80%" style="text-align:left;">
                        <textarea rows="5" cols="40" id="conditionDesc" name="conditionDesc" style="width: 60%;">${warnCondition.conditionDesc }</textarea>
					</td>
				</tr>
			</table>
		</form>
</fieldset>
    <br/>
    <input type="button" class="btn_small" value="保 存" onClick="saveCondition()"/>
    <input type="button" class="btn_small" value="返 回" onClick="back()"/>
</div>
<div id="warnType_1" style="display: none;">
    <select style="width: 10%">
        <option value="sysdate">当前时间</option>
    </select>
    <!-- 运算类型 -->
    <select name="optType" style="width: 10%">
        <option value="-" <c:if test="${warnCondition.warnType == 1}">selected="selected"</c:if>>减去</option>
    </select>
    <select name="judgeCol" style="width: 10%">
        <option value="create_time" <c:if test="${warnCondition.judgeCol eq 'create_time'}">selected="selected"</c:if>>办理时间</option>
    </select>
     <!-- 判断类型 -->
    <select name="judgeType" style="width: 10%">
        <option value="&gt;">大于</option>
        <option value="&lt;">小于</option>
        <option value="=">等于</option>
        <option value="&gt;=">大于等于</option>
         <option value="&lt;=">小于等于</option>
    </select>
    <input type="text" name="judgeValue" style="width: 18%;"/><font style="margin-left: 5px;">天</font><font color="red">*必填</font>
</div>
<div id="warnType_2" style="display: none;">
    <select name="judgeCol" style="width: 15%;">
        <option value="AMOUNT_INVOLVED">涉案金额</option>
    </select>
    <!-- 判断类型 -->
    <select name="judgeType" style="width: 15%;">
        <option value="&gt;">大于</option>
        <option value="&lt;">小于</option>
        <option value="=">等于</option>
        <option value="&gt;=">大于等于</option>
         <option value="&lt;=">小于等于</option>
    </select>
    <input type="text" name="judgeValue" style="width: 25%;"/><font style="margin-left: 5px;">元</font><font color="red">*必填</font>
</div>
<div id="warnType_3" style="display: none;">
    <select style="width: 10%">
        <option value="sysdate">当前时间</option>
    </select>
    <!-- 运算类型 -->
    <select name="optType" style="width: 10%">
        <option value="-" <c:if test="${warnCondition.warnType == 1}">selected="selected"</c:if>>减去</option>
    </select>
    <select name="judgeCol" style="width: 10%">
        <option value="create_time" <c:if test="${warnCondition.judgeCol eq 'yisong_time'}">selected="selected"</c:if>>移送时间</option>
    </select>
     <!-- 判断类型 -->
    <select name="judgeType" style="width: 10%">
        <option value="&gt;">大于</option>
        <option value="&lt;">小于</option>
        <option value="=">等于</option>
        <option value="&gt;=">大于等于</option>
         <option value="&lt;=">小于等于</option>
    </select>
    <input type="text" name="judgeValue" style="width: 18%;"/><font style="margin-left: 5px;">天</font><font color="red">*必填</font>
</div>
</body>
</html>