<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path}/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/script/accuseSelector/accuseSelector.css" />
<script type="text/javascript" src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/ligerUI-1.3.2/js/core/base.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"></script>
<script type="text/javascript" src="${path}/resources/script/jqueryUtil.js"></script>
<script type="text/javascript" src="${path}/resources/script/accuseSelector/accuseSelector.js"></script>
<script type="text/javascript" src="${path}/resources/script/artDialog/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="${path}/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path}/resources/script/accuseRuleUtil.js"></script>
<script type="text/javascript">
	var path="${path}"
	var accuseRuleItemIndex=1;//规则项序列
	var accuseRuleItemId=1;//每列的规则ID
	$(function() {
		jqueryUtil.formValidate({
			form : "accuseRuleAddForm", 
			rules : {
				name : {
					required : true,
					maxlength : 250,
                    remote:{//验证用户名是否存在,后台进行验证返回true或false，返回false时会提示用户名已经注册
                        type:"POST",
                        url:"${pageContext.request.contextPath}/system/accuseRule/checkRuleName", //后台请求地址
                        data:{
                        	ruleName:function(){
                                   return $("#ruleName").val();
                              }
                        } 
                 	} 
				},
				accuseId : {
					required : true
				}
			},
			messages : {
				name : {
					required : "名称不能为空!",
					maxlength : "请最多输入500位汉字!",
                    remote:"规则名已存在"
				},
				accuseId : {
					required : "请选择罪名信息!"
				}
			},
			submitHandler : function(form) {
				//验证罪名是否填写
		    	var caseAccuseVal=$("#accuseId").val();
		    	if(typeof(caseAccuseVal)!='undefined' && caseAccuseVal==''){
		    		$.ligerDialog.warn("请选择罪名信息！");
		    		return false;
		    	} 
		    	getAccuseRuleJson();//把json数组封装到表单的隐藏字段
 		    	form.submit();
			}
		});
		
		accuseSelector.exec({labelC:'#accuseC',valC:'#accuseId',control:'#accuseControl'});
	});
	
	
	function back(){
	    window.location.href='<c:url value="/system/accuseRule/back?industryType=${industryType}"/>';
	}
	
</script>
<style type="text/css">
.selectType {
	width: 15%;
	margin-top: 10px;
}

.matchType {
	width: 15%;
}

.guanjianzi {
	width: 60%;
	margin-bottom: 10px;
    border: none;
    background: #eee;
    padding: 4px;
    border-bottom: 1px solid #e1e3e1;
}

.guanjianzi_xishu{
    width:8%;
    margin-bottom: 10px;
    border: none;
    background: #eee;
    padding: 4px;
    border-bottom: 1px solid #e1e3e1;
}

.number_size {
	width: 28.3%;
	margin-left: 4.5px;
	margin-bottom: 10px;
	border: none;
	background: #eee;
	padding: 4px;
	border-bottom: 1px solid #e1e3e1;
}

.table_add td {
	border-right: #C1C1C1 1px solid;
	border-top: #C1C1C1 1px solid;
	border-buttom: #C1C1C1 1px solid;
	padding-left: 8px;
	padding-right: 8px;
	padding-top: 6px;
	padding-bottom: 6px;
	empty-cells: show;
}

.table_add td font {
	display: inline-block;
	width: 110px;
}

.number_size_big {
	margin-left: 20px;
}

.td_option {
	text-align: right;
	width: 5%;
}
.table_add {
    width: 98%;
    margin: 0;
    border: #C1C1C1 1px solid;
    color: black;
    border-right: none;
    empty-cells: show;
    border-collapse: collapse;
}
</style>
</head>
<body>
	<div class="panel">
		<fieldset class="fieldset">
			<legend class="legend">新增量刑标准</legend>
			<form id="accuseRuleAddForm" action="${path }/system/accuseRule/add" method="post">
				<input type="hidden" id="industryType" name="industryType" value="${industryType}" />
				<input type="hidden" id="ruleInfo" name="ruleInfo" value="" style="width: 80%" />
				<table id="accuseRuleAddTable" class="table_add" width="90%">
					<tr>
						<td class="tabRight" width="20%">名称</td>
						<td style="text-align: left" width="80%"><input cols="50" rows="4" class="text" id="ruleName" name="name" style="width: 80%">&nbsp;<font color="red">*必填</font></td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">罪名</td>
						<td style="text-align: left;" width="80%"><input type="hidden" name="accuseId" style="width: 400px;" id="accuseId" value="" />
							<div style="overflow: hidden;">
								<div id="accuseC" style="display: inline-block; border: 1px solid #c1c1c1; height: 100px; width: 80%;"></div>
								&nbsp;<font color="red" style="display: inline-block; position: relative; top: -2px;">*必填</font>
							</div> <a href="javascript:void(0)" id="accuseControl">选择罪名</a></td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">量刑规则</td>
						<td style="text-align: left" width="80%">
							<table id="ruleDescription" class="table_add" style="width: 80%;">
							</table>
							<input type="button" onclick="addAccuseRule()" value="添加规则" style="background-color: #1B9AF7;border-color: #1B9AF7; color: #FFF;border-radius: 200px;margin-top: 8px"/>
						</td>
					</tr>
				</table>
				<div style="margin-left: 37%; margin-top: 5px">
					<input type="submit" class="btn_small" value="保 存" /> <input type="button" class="btn_small" value="返 回" onclick="back()" />
				</div>
			</form>
		</fieldset>
	</div>
	<table id="rule_add_talbe" class="table_add" style="width: 100%;display: none;">
		<tr>
            <td>
    <!--        <font style="margin-bottom: 5px;">案件名称包含:</font>
                <input type="text" name="caseName" id="caseName" value="" class="guanjianzi"></input>
                <input type="text" name="nameThreshold" id="nameThreshold" value="0.4" class="guanjianzi_xishu">
                </br> -->
                <font>案件简介包含:</font>
                <input type="text" name="caseDetail" id="caseDetail" value="" class="guanjianzi"></input>
                <input type="text" name="detailThreshold" id="detailThreshold" value="0.4" class="guanjianzi_xishu">
                </br>
                <font>涉案金额(单位:元):</font>
                大于<input type="text" id="moneyNumberBefore" value="" class="number_size " />
                小于<input type="text" id="moneyNumberAfter" value="" class="number_size" />  
                </br>
                <font>涉案物品数量:</font>
                大于<input type="text" name="goodsCountNumberBefore" id="goodsCountNumberBefore" value="" class="number_size" />
                 小于<input type="text" name="goodsCountNumberAfter" id="goodsCountNumberAfter" value="" class="number_size" /> 
            </td>		</tr>
	</table>
</body>
</html>