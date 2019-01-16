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
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script charset="utf-8" src="${path }/resources/script/kindeditor/kindeditor-min.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<title>文书号生成</title>
<script type="text/javascript">
function back(){
	window.location.href = "${path}/office/identity/list";
}
$(function(){
    jqueryUtil.formValidate({
    	form:"addForm",
    	rules:{
    		alias:{remote:'${path}/office/identity/checkAliasExisted'}
    	},
    	messages:{
    		alias:{remote:'此别名已被使用，请填写其它别名!'}
    	},
    	submitHandler:function(){
	      $('#addForm')[0].submit();
    	}
    });
});


	 
</script>
</head>

<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">流水号添加</legend>
		<form:form id="addForm" action="${path}/office/identity/add" method="post">
			<table width="90%" class="table_add">
				<tr>
					<td width="20%" class="tabRight">名称：</td>
					<td width="80%" style="text-align:left;">
						<input type="text" id="name" name="name" class="text"/>
					</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">别名：</td>
					<td width="80%" style="text-align:left;">
					<input type="text" id="alias" name="alias" class="text"/>
					</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">规则：</td>
					<td width="80%" style="text-align:left;">
						<input type="text" id="regulation" name="regulation" size="80" value="{yyyy}{MM}{dd}{NO}"  class="text"/>
						<br>
						{yyyy}{MM}{dd}{NO}
						<ul>
							<li>{yyyy}表示年份</li>
							<li>{MM}表示月份，如果月份小于10，则加零补齐，如1月份表示为01。</li>
							<li>{mm}表示月份，月份不补齐，如1月份表示为1。</li>
							<li>{DD}表示日，如果小于10号，则加零补齐，如1号表示为01。</li>
							<li>{dd}表示日，日期不补齐，如1号表示为1。</li>
							<li>{NO}表示流水号，前面补零。</li>
							<li>{no}表示流水号，后面补零。</li>
							<li>{ORG}表示当前用户所属组织的code代码</li>
						</ul>					
					</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">生成类型：</td>
					<td width="80%" style="text-align:left;">
						<input type="radio"  name="gentype" checked="checked" value="1"  />每天生成
<!-- 					<input type="radio"  name="gentype"  value="2"  />每月生成
						<input type="radio"  name="gentype"  value="3"  />每年生成 -->
						<input type="radio"  name="gentype"  value="0"  />递增
						<br>
						流水号生成规则：
						<ul>
							<li>1.每天生成。每天从初始值开始计数。</li>
							<li>2.递增，流水号一直增加。</li>
						</ul>						
					</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">流水号长度：</td>
					<td width="80%" style="text-align:left;">
					<input type="text" id="nolength" name="nolength" value="5"  class="text"/>
					<br>
					这个长度表示当前流水号的长度数，只包括流水号部分{NO},如果长度为5，当前流水号为5，则在流水号前补4个0，表示为00005。<br>
					{no}如果长度为5，当前流水号为501，则在流水号后面补5个0，表示为50100000，如果长度为1，则流水号一直递增。
					</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">初始值：</td>
					<td width="80%" style="text-align:left;">
						<input type="text" id="initvalue" name="initvalue" value="1"  class="text"/>
					</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">步长：</td>
					<td width="80%" style="text-align:left;">
						<input type="text" id="step" name="step" value="1"  class="text"/>
						<br>
						流水号每次递加的数字，默认步长为1。比如步长为2，则每次获取流水号则在原来的基础上加2。
					</td>
				</tr>									
			</table>
			<table style="width:98%;margin-top: 5px;">
				<tr>
					<td align="center">
						<input type="submit" value="保&nbsp;存" class="btn_small"/>
						<input type="button" value="返&nbsp;回" class="btn_small" onclick="back()"/>
					</td>
				</tr>
			</table>
		</form:form>
</fieldset>
</div>

</body>
</html>