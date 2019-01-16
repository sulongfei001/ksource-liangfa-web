<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path}/resources/css/common.css" />
<script type="text/javascript" src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	function back() {
		window.location.href = '<c:url value="/system/accuseRule/back?industryType=${accuseRule.industryType}"/>';
	}
	//显示规则
	var i=1;
	$(document).ready(function (){
		var names=JSON.parse('${accuseRule.accuseInfo}');
		$("#accuseName").text(names[0].name+'('+names[0].clause+')');
		var ruleInfoJson=JSON.parse('${accuseRule.ruleInfo}');
		var table = $("#ruleDescription");
		for(ruleIndex in ruleInfoJson){
			var accuseRuleJsonStr=JSON.stringify(ruleInfoJson[ruleIndex]);
			i=parseInt(ruleIndex)+1;
			var tr = '<tr JSON='+accuseRuleJsonStr+'><td>规则'+i+':<br/>';
			if(ruleInfoJson[ruleIndex].caseName!=''&&typeof(ruleInfoJson[ruleIndex].caseName)!='undefined'){
			 	tr +='&nbsp;&nbsp;&nbsp;&nbsp;案件名称包含关键字:'+ruleInfoJson[ruleIndex].caseName+";";
			}
			if(ruleInfoJson[ruleIndex].caseDetail!=''&&typeof(ruleInfoJson[ruleIndex].caseDetail)!='undefined'){
			 	tr +='&nbsp;&nbsp;&nbsp;&nbsp;案件简介包含关键字:'+ruleInfoJson[ruleIndex].caseDetail+";";
	             if(ruleInfoJson[ruleIndex].detailThreshold!=''&&typeof(ruleInfoJson[ruleIndex].detailThreshold)!='undefined'){
	                    tr +='（研判系数:'+ruleInfoJson[ruleIndex].detailThreshold+"）;";
	                }
			}
			
			//涉案金额
			if(ruleInfoJson[ruleIndex].moneyNumberBefore!=''&&ruleInfoJson[ruleIndex].moneyNumberAfter!=''){
			 	tr +='&nbsp;&nbsp;&nbsp;&nbsp;涉案金额:'+ruleInfoJson[ruleIndex].moneyNumberBefore+'~'+ruleInfoJson[ruleIndex].moneyNumberAfter+";";
			}
			if(ruleInfoJson[ruleIndex].moneyNumberBefore!=''&&ruleInfoJson[ruleIndex].moneyNumberAfter==''){
			 	tr +='&nbsp;&nbsp;&nbsp;&nbsp;涉案金额大于'+ruleInfoJson[ruleIndex].moneyNumberBefore+";";
			}
			if(ruleInfoJson[ruleIndex].moneyNumberBefore==''&&ruleInfoJson[ruleIndex].moneyNumberAfter!=''){
			 	tr +='&nbsp;&nbsp;&nbsp;&nbsp;涉案金额小于'+ruleInfoJson[ruleIndex].moneyNumberAfter+";";
			}
			
			//涉案物品数量
			if(ruleInfoJson[ruleIndex].goodsCountNumberBefore!=''&&ruleInfoJson[ruleIndex].goodsCountNumberAfter!=''){
			 	tr +='&nbsp;&nbsp;&nbsp;&nbsp;涉案物品数量:'+ruleInfoJson[ruleIndex].goodsCountNumberBefore+'~'+ruleInfoJson[ruleIndex].goodsCountNumberAfter+";";
			}
			if(ruleInfoJson[ruleIndex].goodsCountNumberBefore!=''&&ruleInfoJson[ruleIndex].goodsCountNumberAfter==''){
			 	tr +='&nbsp;&nbsp;&nbsp;&nbsp;涉案物品数量大于'+ruleInfoJson[ruleIndex].goodsCountNumberBefore+";";
			}
			if(ruleInfoJson[ruleIndex].goodsCountNumberBefore==''&&ruleInfoJson[ruleIndex].goodsCountNumberAfter!=''){
			 	tr +='&nbsp;&nbsp;&nbsp;&nbsp;涉案物品数量小于'+ruleInfoJson[ruleIndex].goodsCountNumberAfter+";";
			}
			tr += '</td>';
			table.append($(tr));
		}
		i=i+1;		
	});
	function formatNum(num){
	    if(!/^(\+|-)?\d+(\.\d+)?$/.test(num)){
	         return num;
	    }
	    var re = new RegExp().compile("(\\d)(\\d{3})(,|\\.|$)");
	        num += ""; 
	        while(re.test(num))
	           num = num.replace(re, "$1,$2$3")
	        return num;
	 }	
</script>
<style type="text/css">
.selectType{
	width:15%;
	margin-top:10px;
	}
.matchType{
	width:15%;
}
.guanjianzi{
	width:66.5%;
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
    width: 120px;
}
.number_size_big{
	margin-left: 20px;
}
.td_option{
	text-align: right;
	width:5%;
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
			<legend class="legend">量刑标准详情</legend>
			<form id="accuseRuleAddForm" action="${path }/system/accuseRule/update" method="post">
				<table id="accuseRuleAddTable" class="table_add" width="90%">
					<tr>
						<td class="tabRight" width="20%">名称</td>
						<td style="text-align: left" width="80%">${accuseRule.name}&nbsp;</td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">罪名</td>
						<td style="text-align: left;" width="80%"><input type="hidden" name="accuseId" style="width: 400px;" id="accuseId" value="" />
							<span id="accuseName"></span>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">量刑标准</td>
						<td style="text-align: left" width="80%">
							<table id="ruleDescription" class="table_add"  style="width: 80%;">
							</table>
						</td>
					</tr>
				</table>
				<div style="margin-left: 37%; margin-top: 5px">
					<input type="button" class="btn_small" value="返 回" onclick="back()" />
				</div>
			</form>
		</fieldset>
	</div>

</body>
</html>