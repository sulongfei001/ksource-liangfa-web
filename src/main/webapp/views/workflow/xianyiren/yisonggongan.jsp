<%@page import="com.ksource.syscontext.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>

<!-- ligerUI -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>

<div id="yisonggonganC" class="xianyiren_c">
		<input type="hidden"  name="yisonggonganZm" id="yisonggonganZm"/>
		<p align="left" style="float:left;margin-right:2px;">
			<label>移送公安罪名</label>
		</p>
		<div id="yisonggonganZmC" style="border:1px solid #999;width:30%; height: 100%;height:auto;min-height:80px; float:left;"></div>
		&nbsp;&nbsp;<font color="red">*</font>
		<p align="left" style="width:90%; clear:both;">
			<label></label>
			<a href="javascript:void(0)" id="yisonggonganZmControl">选择罪名</a>
		</p>
	<script type="text/javascript">
	var accuseInfoList=${accuseInfos};
	window.accuseSid_yisonggonganZm= accuseSelector.exec({labelC:'#yisonggonganZmC',valC:'#yisonggonganZm',control:'#yisonggonganZmControl'});
	accuseSelector.result(accuseSid_yisonggonganZm,accuseInfoList);
	//任务办理完毕提交前的前置校验方法
	function yisonggonganBefore(){
		var flag = false;
		var yisonggonganZm = $('#yisonggonganZm').val();
		if(yisonggonganZm==''){$.ligerDialog.wran('请填写移送罪名！');}else{
			$.ajax({
		    	async:false,//确保函数反正正确的flag值
				dataType:'json',
				url: '${path}/workflow/yisonggongan/yisonggonganZm',
				data: {caseId:caseId, yisonggonganZm: yisonggonganZm},
				success: function(data){
					flag=true;//提交动态表单前处理不起诉操作
				}
			});
		}
		return flag;
	}
	</script>
</div>