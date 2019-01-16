<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/popover/jquery.webui-popover.min.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/lib/jquery.metadata.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/autoresize.jquery.min.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript">
	
	
	$(function(){
 		//初始化日期插件
		var occurrenceTime  = document.getElementById('occurrenceTime');
		occurrenceTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'investigationTime\')}'});
		}
		var investigationTime = document.getElementById('investigationTime');
		investigationTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'occurrenceTime\')}'});
		}
		
		
		
			//验证附件是否超过大小限制
			jQuery.validator.addMethod("filesize", function(value, element) {
				 var files = $(".attachMent_s");
				 var byteSize = 0;
				 for(var index = 0; index < files.length; index++){
// 					 var file = files[index].files[0];
					 var file = files[index];
					 if(file !== undefined){
			         var size = files[index].size;
			         if(!isNaN(size)){
			        	 byteSize += size;  
			         }
					 }
				 }
				 byteSize = Math.ceil(byteSize/1024/1024);
// 				 (Math.ceil(byteSize / 1024 / 1024) < 1)
				if(byteSize > 70){
// 					alert("文件超出70M");
					$.ligerDialog.warn("文件总大小超出70M");
					return  false;
				}
				return  true;
			}, "选择的材料超出限制!");
			
	        jQuery.validator.addMethod("minNumber",function(value, element){
	            var returnVal = true;
	            inputZ=value;
	            var ArrMen= inputZ.split(".");    //截取字符串
	            if(ArrMen.length==2){
	                if(ArrMen[1].length>2){    //判断小数点后面的字符串长度
	                    returnVal = false;
	                    return false;
	                }
	            }
	            return returnVal;
	        },"小数点后最多为两位");
			
			
			//案件信息表单验证
				caseValidate=jqueryUtil.formValidate({
				form:"clueForm",
				rules:{
					/* 这里会到后台校验编号的唯一性 ,remote:'${path}/casehandle/case/checkPenaltyFileNo'*/
					clueNo:{required:true,maxlength:50},
					address:{required:true},
					occurrenceTime:{required:true},
					clueResource:{required:true},
					content:{required:true,maxlength:500},
					//验证输入的是否
					allegedAmount:{minNumber:true,min:0,maxlength:13}
// 					fileTip:{filesize:true}
				},
				messages:{
					clueNo:{required:"线索名称不能为空!",maxlength:'请最多输入50位汉字!'},
					address:{required:"请填写违法行为发生地点!"},
					occurrenceTime:{required:"请选择违法行为发生时间!"},
					clueResource:{required:"请选择线索来源!"},
					content:{required:"线索内容不能为空！",maxlength:"线索内容不能超出500字"},
					allegedAmount:{min:"涉嫌金额不能小于0",maxlength:"涉嫌金额总位数不能超过13位"}
				},submitHandler:function(form){
					
					
				      //如果放开invalidHandler，检验不通过进也会执行submitHandler，所以在这个地方再过滤一下
				      if($("#clueForm").valid()){
				    	  form.submit();
					      //提交按钮禁用
					      $("#saveButton").attr("disabled",true);
				      }
				      return false;
				}
			});//校验结束
		
		//autoResize.js自动扩展textarea大小
		$('#content').autoResize({
			limit:500
		});
		$('#createUserOpinion').autoResize({
			limit:500
		});
		
		

		
		
		/* 信息保存后的弹框提示 */
		var info = '${info}';
		if(info=='saveOK'){parent.$.ligerDialog.success("保存成功！");}
		if(info=='saveFailure'){parent.$.ligerDialog.warn("保存失败！");}
		
		//为文件按钮绑定事件
		addfile();
	});
	
	//添加文件上传按钮
	function addfile() {
		var files = $("#files") ;
		 var context = "<div  style=\"cursor:pointer;\"><input type=\"file\" id=\"attachMent_s\" class=\"attachMent_s\" name=\"attachMent_s\">&nbsp;<span id=\"deletefile\" onclick=\"removeDiv(this)\"><img title=\"删除\" src=\"${path}/resources/images/jian1.png\" style=\" cursor:pointer ;margin: 0px;padding:0px;\"></span></div>" ;
		 $("#addfile").click(function() {
			 files.append(context) ;
		 }) ;
		 return false;
	}
	
	//自动填充
	function autoParseIdsNo(obj){
		if(checkIDCard($(obj).val())){
			my$Table=$(obj).parents("table");
		 	//自动填充
			var bithdayAndSexArrays = getBithdayAndSexFromIds($(obj).val());
			my$Table.find("#birthday").val(bithdayAndSexArrays[0]);
			my$Table.find("#sexForAdd option").each(function(i,n){
				if($(n).html()==bithdayAndSexArrays[1]){
					my$Table.find("#sexForAdd").val($(n).val());
				}
			});
		}
	}

	//移除文件选择按钮
	function removeDiv(element){
		var div=document.getElementById("files");
		div.removeChild(element.parentNode);
	}
	
	
	function del(fileId) {//ajax删除附件
		var element = $('#' + fileId + '_attment');
		var text = $('#' + fileId + '_a').text();
		if (confirm("确认删除" + text + "吗？")) {
			$.get("/liangfa-xinxiang/activity/supervise_case/delFile/"+fileId, function() {
				element.remove();
				if ($('#files > div').length === 0) {
					$('#attaDiv').html('无');
				}
				;
			});
		}
	}
	
	//验证输入项通过后提交表单
	function add(){
		$("#clueForm").submit();
	}
	 
	 //返回上一页
	 function back(){
		 window.location.href="${path}/casehandle/clueInfo/getInputClueList.do";
	 }
	
	 //验证所选文件的大小
	 function findSize(field_id){
            var fileInput = $("#"+field_id)[0];
            byteSize  = fileInput.files[0].fileSize;
	        return ( Math.ceil(byteSize / 1024 / 1024) ); // Size returned in MB.
	 }
	
	
	//删除tab框并删除数据库里的当事单位
	function deleteTableAndParty(name,table,partyId){
		$.ligerDialog.confirm('确定要删除当事人'+name+'吗?',function(){
 				$.post("",{'partyId':partyId},function(date){
 					if(date){
 						$.ligerDialog.warn("删除当事单位"+name+"成功","提示!","success");
						//删除
						$(table).next().andSelf().remove();
 					}else{
 						$.ligerDialog.warn("删除当事单位"+name+"失败","提示!","error");
 					}
 				});
			
		}); 
	}
	
	//返回到热线
	function backToHotLine(){
		window.location.href="${path}/hotlineInfo/list.do"
	}
	
</script>
</head>
<body>
	<div class="panel">
	<form id="clueForm" method="post" action="${path }/casehandle/clueInfo/saveClueInfo.do" enctype="multipart/form-data">
		<fieldset class="fieldset">
		
			<c:if test="${empty clueInfo.clueId}">
				<legend class="legend">线索登记</legend>
			</c:if>
			<c:if test="${not empty clueInfo.clueId}">
				<legend class="legend">线索修改</legend>
			</c:if>
			
			<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;" id="clueInfoTable">
				<tr>
					<td width="21%"  class="tabRight">线索名称：</td>
					<td width="29%" style="text-align: left;" colspan="3">
						<input type="text" class="text ignore" id="clueNo" name="clueNo" value="${clueInfo.clueNo}" style="width: 91%;"/>&nbsp;<font color="red">*</font>
						<input type="hidden" id="casePartyJson" name="casePartyJson" value="" />
						<input type="hidden" id="caseCompanyJson" name="caseCompanyJson" value="" />
						<input type="hidden" id="clueId" name="clueId" value="${clueInfo.clueId}" />
						<c:if test="${infoId != null or infoId != '' }">
							<input type="hidden" id="hotlineId" name="hotlineId" value="${infoId }" />
						</c:if>
						
					</td>
			
				</tr>
				<!-- 线索发生地 -->	
				<tr>
					<td width="21%"  class="tabRight" >违法行为发生地：</td>
					<td width="79%" style="text-align: left;" colspan="3">
						<input type="text" id="address" name="address" value="${clueInfo.address }" onfocus="showClueAddress(); return false;" readonly="readonly"  class="text ignore" style="width: 91%;" />&nbsp; <font color="red">*</font> 
					</td>		
				</tr>
				
				<tr>
					<td width="21%"  class="tabRight" >违法行为发生时间：</td>
					<td width="29%" style="text-align: left;">
						<input type="text" class="text" readonly="readonly" name="occurrenceTime" id="occurrenceTime"  value="<fmt:formatDate value='${clueInfo.occurrenceTime}' pattern='yyyy-MM-dd'/>"/>
						&nbsp; <font color="red">*</font> 
					</td>
					<td width="21%"  class="tabRight" >查处时间：</td>
					<td width="29%" style="text-align: left;">
						<input type="text" class="text" readonly="readonly" name="investigationTime" id="investigationTime"  value="<fmt:formatDate value="${clueInfo.investigationTime}" pattern="yyyy-MM-dd"/>"/>
					</td> 
				</tr>
				
				<tr>
				
					<td width="21%"  class="tabRight" >线索来源：</td>
					<td width="29%" style="text-align: left;" >
						<select class="select" name="clueResource" id="clueResource">
							<option value="" selected>--请选择--</option>
							<c:forEach items="${dicList}" var="dic">
								<option value="${dic.dtCode}" <c:if test="${clueInfo.clueResource eq dic.dtCode}">selected</c:if>  >${dic.dtName }</option>
							</c:forEach>
						</select>
						&nbsp; <font color="red">*</font> 
					</td>
					
					<td width="21%"  class="tabRight" >涉案金额：</td>
					<td width="29%" style="text-align: left;" >
							<input type="text" class="text" name="allegedAmount" id="allegedAmount" value="${clueInfo.allegedAmount }"/>元
					</td>
				
				</tr>
					
				<tr>
					<td width="21%"  class="tabRight" >线索内容：</td>
					<td width="79%" style="text-align: left" colspan="3">
						<textarea rows="5" id="content" name="content"  style="width: 91%;resize:none;">${clueInfo.content}</textarea>
						&nbsp;<font color="red">*</font>
					</td>
				</tr>		
						
				<tr>
					<td width="21%"  class="tabRight" >经办人意见：</td>
					<td width="79%" style="text-align: left" colspan="3">
						<textarea rows="5" id="createUserOpinion" name="createUserOpinion" style="width: 91%;resize:none;">${clueInfo.createUserOpinion }</textarea>
					</td>
				</tr>				
						
				<c:if test="${not empty clueInfo.clueId}">
					<tr>
						<td width="21%"  class="tabRight" >已添加材料：</td>
						<td width="79%"  style="text-align: left" colspan="3">
							<c:if test="${not empty clueInfo.attments}">
								<c:forEach items="${clueInfo.attments }" var="attment">
									<div id="${attment.fileId}_attment">
										<a id="${attment.fileId}_a" href="${path}/download/publishInfoFile.do?fileId=${attment.fileId}">${attment.fileName}</a><a href="javascript:del(${attment.fileId})" style="color: red;"> 删除</a>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty clueInfo.attments}">
								没有已添加的相关材料!
							</c:if>
						</td>
					</tr>	
						
				</c:if>
					<tr>
						<td width="21%"  class="tabRight" >相关材料：</td>
						<td width="59%" id="files" style="text-align: left" colspan="3">
							<!-- 添加 -->
							<input type="file" name="attachMent_s" class="attachMent_s" id="attachMent_s" value="选择" />
							<img title="添加"  id="addfile"  src="${path}/resources/images/jia1.png"  style=" cursor:pointer ;margin: 0px;padding:0px;" />
							<input name="fileTip" id="fileTip" style="visibility:hidden;"/><span style="color: red;">文件大小不能超过70M!</span>
						</td>
						
						
					</tr>		
			</table>
			<input type="hidden" id="mark" name="mark" value="${mark }" />
			<div style="margin: 0px 42%">
				<input style="margin-top: 10px;" id="saveButton" type="button" class="btn_small" value="保 存" onclick="add()" />
				<c:if test="${not empty clueInfo.clueId }">
					<input style="margin-top: 10px;" id="saveButton" type="button" class="btn_small" value="返 回" onclick="back()" />
				</c:if>
				<c:if test="${not empty back}">
					<input style="margin-top: 10px;" id="saveButton" type="button" class="btn_small" value="返 回" onclick="backToHotLine()" />
				</c:if>
			</div>
			</fieldset>
		</form>
		</div>
		
		<!-- 加载树 -->
		<jsp:include page="/views/tree/clue_address_tree.jsp"/>	
			
</body>
</html>
</html>