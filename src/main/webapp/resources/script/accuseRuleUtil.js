/**
 * 增加规则
 * 
 * @returns {Boolean}
 */
function addRule() {
	if (validateAccuseRule() == false)
		return false;// 校验
	var accuseRule = extractAccuseRule();// 封装规则信息
	showAccuseRuleDescription(accuseRule)// 显示规则信息
	clearRuleTable();// 清空规则table
	return true;
}

/**
 * 规则项修改
 * 
 * @param itemTrId
 * @param tdItemId
 * @returns {Boolean}
 */
function editRule(itemTrId, tdItemId) {
	if (validateAccuseRule() == false)
		return false;// 校验
	var accuseRule = extractAccuseRule();// 封装规则信息
	var accuseRuleJsonStr = JSON.stringify(accuseRule);// 把修改后的信息转换成json字符串
	$("#" + itemTrId).attr('json', accuseRuleJsonStr);// 更改所选列的json属性值
	showTdContent(accuseRule, tdItemId);// 回显td里的规则内容
	clearRuleTable();// 清空规则table
	return true;
}
/**
 * 验证字符串是否是数字
 * 
 * @param theObj
 * @returns {Boolean}
 */
function checkNumber(theObj) {
	var reg = /^[0-9]*[1-9][0-9]*$/;
	if (reg.test(theObj)) {
		return true;
	}
	return false;
}

function checkDoubleNumber(theObj){
	var reg = /^\d+\.\d+$/;
	if (reg.test(theObj)) {
		return true;
	}
	return false;
}

/**
 * 校验所填写的规则信息
 * 
 * @returns {Boolean}
 */
function validateAccuseRule() {
	var caseName = $("#caseName").val();
	var caseDetail = $("#caseDetail").val();
	var moneyNumberBefore = $("#moneyNumberBefore").val();
	if (moneyNumberBefore != "" && !checkNumber(moneyNumberBefore)) {
		art.dialog.alert("涉案金额请输入正整数！");
		return false;
	}
	var moneyNumberAfter = $("#moneyNumberAfter").val();
	if (moneyNumberAfter != "" && !checkNumber(moneyNumberAfter)) {
		art.dialog.alert("涉案金额请输入正整数！");
		return false;
	}
	if (moneyNumberBefore != "" && moneyNumberAfter != "" && (parseInt(moneyNumberBefore) > parseInt(moneyNumberAfter))) {
		art.dialog.alert("涉案金额范围不合理！");
		return false;
	}
	var goodsCountNumberBefore = $("#goodsCountNumberBefore").val();
	var goodsCountNumberAfter = $("#goodsCountNumberAfter").val();
	if (goodsCountNumberBefore != "" && !checkNumber(goodsCountNumberBefore)) {
		art.dialog.alert("涉案物品数量请输入正整数！");
		return false;
	}
	if (goodsCountNumberAfter != "" && !checkNumber(goodsCountNumberAfter)) {
		art.dialog.alert("涉案物品数量请输入正整数！");
		return false;
	}
	if (goodsCountNumberBefore != "" && goodsCountNumberAfter != "" && (parseInt(goodsCountNumberBefore) > parseInt(goodsCountNumberAfter))) {
		art.dialog.alert("涉案物品数量范围不合理！");
		return false;
	}
	if ((caseName == '' || typeof(caseName) == 'undefined') && caseDetail == '' && moneyNumberBefore == '' && moneyNumberAfter == '' && goodsCountNumberBefore == '' && goodsCountNumberAfter == '') {
		art.dialog.alert("请输入有效信息");
		return false;
	}
	var detailThreshold = $("#detailThreshold").val();
	if (detailThreshold != "" && !checkDoubleNumber(detailThreshold)) {
		art.dialog.alert("研判系数请输入小数！");
		return false;
	}
	return true;
}
/**
 * 封装规则对象
 */
function extractAccuseRule() {
	var accuseRule = new Object();
	accuseRule.caseName = $("#caseName").val();
	accuseRule.caseDetail = $("#caseDetail").val();
	accuseRule.moneyNumberBefore = $("#moneyNumberBefore").val();
	accuseRule.moneyNumberAfter = $("#moneyNumberAfter").val();
	accuseRule.goodsCountNumberBefore = $("#goodsCountNumberBefore").val();
	accuseRule.goodsCountNumberAfter = $("#goodsCountNumberAfter").val();
	accuseRule.detailThreshold = $("#detailThreshold").val();
	return accuseRule;
}
/**
 * 显示规则
 * 
 * @param accuseRule
 */
function showAccuseRuleDescription(accuseRule) {
	var accuseRuleJsonStr = JSON.stringify(accuseRule);
	var table = $("#ruleDescription");// JSON="'+accuseRuleJsonStr+'"
	var tr = '<tr id=itemTrId' + accuseRuleItemId + ' JSON=\'' + accuseRuleJsonStr + '\'><td id="itemTdId' + accuseRuleItemId + '_' + accuseRuleItemIndex + '">规则' + accuseRuleItemIndex + ':<br/>';
	if (accuseRule.caseName != '' && typeof(accuseRule.caseName) != 'undefined') {
		tr += '&nbsp;&nbsp;&nbsp;&nbsp;案件名称包含关键字:<span name="caseName">' + accuseRule.caseName + "</span>;";
	}
	if (accuseRule.caseDetail != '') {
		tr += '&nbsp;&nbsp;&nbsp;&nbsp;案情简介包含关键字:' + accuseRule.caseDetail + ";";
	}
	if (accuseRule.detailThreshold != '') {
		tr += '（研判系数:' + accuseRule.detailThreshold + "）;";
	}
	// 涉案金额
	if (accuseRule.moneyNumberBefore != '' && accuseRule.moneyNumberAfter != '') {
		tr += '&nbsp;&nbsp;&nbsp;&nbsp;涉案金额:' + accuseRule.moneyNumberBefore + '~' + accuseRule.moneyNumberAfter + ";";
	}
	if (accuseRule.moneyNumberBefore != '' && accuseRule.moneyNumberAfter == '') {
		tr += '&nbsp;&nbsp;&nbsp;&nbsp;涉案金额大于' + accuseRule.moneyNumberBefore + ";";
	}
	if (accuseRule.moneyNumberBefore == '' && accuseRule.moneyNumberAfter != '') {
		tr += '&nbsp;&nbsp;&nbsp;&nbsp;涉案金额小于' + accuseRule.moneyNumberAfter + ";";
	}

	// 涉案物品数量
	if (accuseRule.goodsCountNumberBefore != '' && accuseRule.goodsCountNumberAfter != '') {
		tr += '&nbsp;&nbsp;&nbsp;&nbsp;涉案物品数量:' + accuseRule.goodsCountNumberBefore + '~' + accuseRule.goodsCountNumberAfter + ";";
	}
	if (accuseRule.goodsCountNumberBefore != '' && accuseRule.goodsCountNumberAfter == '') {
		tr += '&nbsp;&nbsp;&nbsp;&nbsp;涉案物品数量大于' + accuseRule.goodsCountNumberBefore + ";";
	}
	if (accuseRule.goodsCountNumberBefore == '' && accuseRule.goodsCountNumberAfter != '') {
		tr += '&nbsp;&nbsp;&nbsp;&nbsp;涉案物品数量小于' + accuseRule.goodsCountNumberAfter + ";";
	}
	tr += '</td>';
	tr += '<td class="td_option"><img title="修改" onclick="editAccuseRuleItem(this);return false" src="' + path
			+ '/resources/images/rule_edit.png" style=" cursor:pointer ; display;block; margin: 0px auto;padding:0px;max-width:18px;"/></td>';
	tr += '<td class="td_option"><img title="移除" onclick="removeRule(this);return false"  src="' + path
			+ '/resources/images/rule_delete.png" style=" cursor:pointer ; display;block; margin: 0px auto;padding:0px;max-width:18px;"/></td></tr>';

	table.append($(tr));
	accuseRuleItemIndex++;
	accuseRuleItemId++;
}
// 清空规则表单里缓存的内容
function clearRuleTable() {
	$("#caseName").val("");
	$("#caseDetail").val("");
	$("#moneyNumberBefore").val("");
	$("#moneyNumberAfter").val("");
	$("#goodsCountNumberBefore").val("");
	$("#goodsCountNumberAfter").val("");
	$("#detailThreshold").val("");
}
/**
 * 删除单个规则项
 * 
 * @param td
 */
function removeRule(td) {
	var that = $(td);
	var tr = that.parent().parent();
	tr.remove();// 移除当前tr
	var ruleJson = getTableJson();// 得到json数组，再进行初始化回显
	accuseRuleItemIndex = 1;// 初始化规则项序列
	accuseRuleItemId = 1;// 初始化每列的规则ID
	if (ruleJson != '[]') {
		$("#ruleDescription").html('');// 清空表单里的内容
		var jsonArray = JSON.parse(ruleJson);// 解析json数组字符串
		for (var jsonIndex = 0; jsonIndex < jsonArray.length; jsonIndex++) {
			showAccuseRuleDescription(jsonArray[jsonIndex]);// 重新回显表单
		}
	}
}
/**
 * 规则项修改表单回显内容
 * 
 * @param jsonStr
 */
function showAccuseRuleEditDialogContent(jsonStr) {
	var accuseRule = JSON.parse(jsonStr);
	var caseDetail = accuseRule.caseDetail;
	var caseName = accuseRule.caseName;
	var goodsCountNumberAfter = accuseRule.goodsCountNumberAfter;
	var goodsCountNumberBefore = accuseRule.goodsCountNumberBefore;
	var moneyNumberAfter = accuseRule.moneyNumberAfter;
	var moneyNumberBefore = accuseRule.moneyNumberBefore;
	var detailThreshold = accuseRule.detailThreshold;
	if (caseDetail != '') {
		$("#caseDetail").val(caseDetail);
	}
	if (caseName != '' && typeof(caseName) != 'undefined') {
		$("#caseName").val(caseName);
	}
	if (goodsCountNumberAfter != '') {
		$("#goodsCountNumberAfter").val(goodsCountNumberAfter);
	}
	if (goodsCountNumberBefore != '') {
		$("#goodsCountNumberBefore").val(goodsCountNumberBefore);
	}
	if (moneyNumberAfter != '') {
		$("#moneyNumberAfter").val(moneyNumberAfter);
	}
	if (moneyNumberBefore != '') {
		$("#moneyNumberBefore").val(moneyNumberBefore);
	}
	if (detailThreshold != '') {
		$("#detailThreshold").val(detailThreshold);
	}	
}
/**
 * 更改修改后的规则项描述
 * 
 * @param accuseRule
 * @param tdItemId
 * @returns {String}
 */
function showTdContent(accuseRule, tdItemId) {
	var index = tdItemId.indexOf('_');
	var orignalRuleIndex = tdItemId.substring(parseInt(index + 1));// 规则项
	var td = '规则:' + orignalRuleIndex + ':<br/>';
	if (accuseRule.caseName != '' && typeof(accuseRule.caseName) != 'undefined') {
		td += '&nbsp;&nbsp;&nbsp;&nbsp;案件名称包含关键字:' + accuseRule.caseName + ";";
	}
	if (accuseRule.caseDetail != '') {
		td += '&nbsp;&nbsp;&nbsp;&nbsp;案情简介包含关键字:' + accuseRule.caseDetail + ";";
		if (accuseRule.detailThreshold != '') {
			td += '（研判系数:' + accuseRule.detailThreshold + "）;";
		}
	}
	// 涉案金额
	if (accuseRule.moneyNumberBefore != '' && accuseRule.moneyNumberAfter != '') {
		td += '&nbsp;&nbsp;&nbsp;&nbsp;涉案金额:' + accuseRule.moneyNumberBefore + '~' + accuseRule.moneyNumberAfter + ";";
	}
	if (accuseRule.moneyNumberBefore != '' && accuseRule.moneyNumberAfter == '') {
		td += '&nbsp;&nbsp;&nbsp;&nbsp;涉案金额大于' + accuseRule.moneyNumberBefore + ";";
	}
	if (accuseRule.moneyNumberBefore == '' && accuseRule.moneyNumberAfter != '') {
		td += '&nbsp;&nbsp;&nbsp;&nbsp;涉案金额小于' + accuseRule.moneyNumberAfter + ";";
	}
	// 涉案物品数量
	if (accuseRule.goodsCountNumberBefore != '' && accuseRule.goodsCountNumberAfter != '') {
		td += '&nbsp;&nbsp;&nbsp;&nbsp;涉案物品数量:' + accuseRule.goodsCountNumberBefore + '~' + accuseRule.goodsCountNumberAfter + ";";
	}
	if (accuseRule.goodsCountNumberBefore != '' && accuseRule.goodsCountNumberAfter == '') {
		td += '&nbsp;&nbsp;&nbsp;&nbsp;涉案物品数量大于' + accuseRule.goodsCountNumberBefore + ";";
	}
	if (accuseRule.goodsCountNumberBefore == '' && accuseRule.goodsCountNumberAfter != '') {
		td += '&nbsp;&nbsp;&nbsp;&nbsp;涉案物品数量小于' + accuseRule.goodsCountNumberAfter + ";";
	}
	$('#' + tdItemId).html(td);
	return td;
}
/**
 * 遍历表单里的规则项封装成json数组
 */
function getAccuseRuleJson() {
	var ruleJson = getTableJson();
	$("#ruleInfo").val(ruleJson);
}
/**
 * 把table里的json对象字符串封装成json数组字符串
 */
function getTableJson() {
	var ruleJson = "[";
	var table = $("#ruleDescription");
	var theRuleArray = new Array();
	table.find("tr").each(function(index, g) {
		if (index == 0) {
			ruleJson += $(this).attr("JSON");
		} else {
			ruleJson += "," + $(this).attr("JSON");
		}
	});
	ruleJson += "]";
	return ruleJson;
}
/**
 * 修改页面初始化
 */
function accuseRuleEditPageInit(ruleInfoJson) {
	var table = $("#ruleDescription");
	for (ruleIndex in ruleInfoJson) {
		var accuseRuleJsonStr = JSON.stringify(ruleInfoJson[ruleIndex]);
		i = parseInt(ruleIndex) + 1;
		var tr = '<tr id=itemTrId' + accuseRuleItemId + ' JSON=\'' + accuseRuleJsonStr + '\'><td id="itemTdId' + accuseRuleItemId + '_' + accuseRuleItemIndex + '">规则' + i + ':<br/>';
		if (ruleInfoJson[ruleIndex].caseName != '' && typeof (ruleInfoJson[ruleIndex].caseName) != 'undefined') {
			tr += '&nbsp;&nbsp;&nbsp;&nbsp;案件名称包含关键字:' + ruleInfoJson[ruleIndex].caseName + ";";
		}
		if (ruleInfoJson[ruleIndex].caseDetail != '' && typeof (ruleInfoJson[ruleIndex].caseDetail) != 'undefined') {
			tr += '&nbsp;&nbsp;&nbsp;&nbsp;案情简介包含关键字:' + ruleInfoJson[ruleIndex].caseDetail + ";";
			if (ruleInfoJson[ruleIndex].detailThreshold != '' && typeof (ruleInfoJson[ruleIndex].detailThreshold) != 'undefined') {
				tr += '（研判系数:' + ruleInfoJson[ruleIndex].detailThreshold + "）;";
			}
		}
		// 涉案金额
		if (ruleInfoJson[ruleIndex].moneyNumberBefore != '' && ruleInfoJson[ruleIndex].moneyNumberAfter != '') {
			tr += '&nbsp;&nbsp;&nbsp;&nbsp;涉案金额:' + ruleInfoJson[ruleIndex].moneyNumberBefore + '~' + ruleInfoJson[ruleIndex].moneyNumberAfter + ";";
		}
		if (ruleInfoJson[ruleIndex].moneyNumberBefore != '' && ruleInfoJson[ruleIndex].moneyNumberAfter == '') {
			tr += '&nbsp;&nbsp;&nbsp;&nbsp;涉案金额大于' + ruleInfoJson[ruleIndex].moneyNumberBefore + ";";
		}
		if (ruleInfoJson[ruleIndex].moneyNumberBefore == '' && ruleInfoJson[ruleIndex].moneyNumberAfter != '') {
			tr += '&nbsp;&nbsp;&nbsp;&nbsp;涉案金额小于' + ruleInfoJson[ruleIndex].moneyNumberAfter + ";";
		}

		// 涉案物品数量
		if (ruleInfoJson[ruleIndex].goodsCountNumberBefore != '' && ruleInfoJson[ruleIndex].goodsCountNumberAfter != '') {
			tr += '&nbsp;&nbsp;&nbsp;&nbsp;涉案物品数量:' + ruleInfoJson[ruleIndex].goodsCountNumberBefore + '~' + ruleInfoJson[ruleIndex].goodsCountNumberAfter + ";";
		}
		if (ruleInfoJson[ruleIndex].goodsCountNumberBefore != '' && ruleInfoJson[ruleIndex].goodsCountNumberAfter == '') {
			tr += '&nbsp;&nbsp;&nbsp;&nbsp;涉案物品数量大于' + ruleInfoJson[ruleIndex].goodsCountNumberBefore + ";";
		}
		if (ruleInfoJson[ruleIndex].goodsCountNumberBefore == '' && ruleInfoJson[ruleIndex].goodsCountNumberAfter != '') {
			tr += '&nbsp;&nbsp;&nbsp;&nbsp;涉案物品数量小于' + ruleInfoJson[ruleIndex].goodsCountNumberAfter + ";";
		}
		tr += '</td>';
		tr += '<td class="td_option"><img title="修改" onclick="editAccuseRuleItem(this);return false" src="' + path
				+ '/resources/images/rule_edit.png" style=" cursor:pointer ; display;block; margin: 0px auto;padding:0px;max-width:18px;"/></td>';
		tr += '<td class="td_option"><img title="移除" onclick="removeRule(this);return false"  src="' + path
				+ '/resources/images/rule_delete.png" style=" cursor:pointer ; display;block; margin: 0px auto;padding:0px;max-width:18px;"/></td></tr>';
		table.append($(tr));
		accuseRuleItemIndex++;
		accuseRuleItemId++;
	}
}

// 修改规则项
function editAccuseRuleItem(td) {
	var that = $(td);// 获取当前操作的对象那个
	var tdOrginal = that.parent().prev();// 查找上一个兄弟节点，不是所有的兄弟节点
	var tdItemId = tdOrginal.attr('id');// 获取规则td的id
	var tr = that.parent().parent();// 获取tr的jQuery对象
	var itemTrId = tr.attr('id');// tr的id
	var jsonStr = tr.attr('json');// tr里的json属性值
	showAccuseRuleEditDialogContent(jsonStr);// 对修改弹框里的内容进行回显
	art.dialog({
		id : 'accuseRuleEdit',
		title : '量刑标准规则项编辑',
		content : document.getElementById("rule_add_talbe"),
		lock : true,
		drag : false,
		resize : false,
		noText : '关闭',
		button : [ {
			name : '修改',
			callback : function() {
				return editRule(itemTrId, tdItemId);
			},
		}, {
			name : '关闭',
			callback : function() {
				clearRuleTable();// 清空弹框框里的表单
			}
		} ],
	});
}

// 添加规则项
function addAccuseRule() {
	art.dialog({
		id : 'accuseRuleAdd',
		title : '量刑标准规则项添加',
		content : document.getElementById("rule_add_talbe"),
		lock : true,
		drag : false,
		resize : false,
		noText : '关闭',
		button : [ {
			name : '添加',
			callback : function() {
				return addRule();
			},
		}, {
			name : '关闭',
		} ],
	});
}