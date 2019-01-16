<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script charset="utf-8" src="${path }/resources/script/kindeditor/kindeditor-min.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script src="${path }/resources/script/SysDialog.js" type="text/javascript"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>

<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/jsonformatter.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		addfile() ;
		//添加表单验证 start
	    // 只能输入[0-9]数字
	    jQuery.validator.addMethod("isDigits", function(value, element) {       
	         return this.optional(element) || /^\d+$/.test(value);       
	    }, "只能输入0-9数字");   
	 
	     // 手机号码验证    
	    jQuery.validator.addMethod("isMobile", function(value, element) {    
	      var length = value.length;    
	      return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));    
	    }, "请正确填写您的手机号码。");

	    // 电话号码验证    
	    jQuery.validator.addMethod("isPhone", function(value, element) {    
	      var tel = /^(\d{3,4}-?)?\d{7,9}$/g;    
	      return this.optional(element) || (tel.test(value));    
	    }, "请正确填写您的电话号码。");

	    // 联系电话(手机/电话皆可)验证   
	    jQuery.validator.addMethod("isTel", function(value,element) {   
	        var length = value.length;   
	        var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;   
	        var tel = /^(\d{3,4}-?)?\d{7,9}$/g;       
	        return this.optional(element) || tel.test(value) || (length==11 && mobile.test(value));   
	    }, "请正确填写您的联系方式"); 		
			
			//添加表单验证 end
		
		
		var completeTime = document.getElementById('licenceTime');
		completeTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		var licenceExpire = document.getElementById('licenceExpire');
		licenceExpire.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		
		//验证
		jqueryUtil.formValidate({
			form : "updateForm",
			rules : {
				name : {
					required : true,
					maxlength :100
				},
				phone:{
					isTel:true
				}
			},
			messages : {
				name : {
					required : "请输入标题!",
					maxlength : "请最多输入100位汉字!"
				},
				phone:{
					isTel:"请输入正确格式的联系方式！"
				}
			},
			submitHandler : function() {
					$('#updateForm')[0].submit();

			}
		});
	});
	$(function(){
		//按钮样式
		
		$('.jsonformat').each(function(){
			$(this).val(FormatJSON($.evalJSON($(this).val())));
		});
		$('.domainLink').click(function(){
			var ele=$(this).next('.domainC')[0];
			dialog = art.dialog({
			    title: '业务数据明细：（万一出现问题，可用于与数据库对照排查）',
			    content:ele,
			    lock:true,
				opacity: 0.1
			});
		});
	});

	jQuery(function(){
		
		
		//组织机构树
			zTree=	$('#dropdownMenu').zTree({
				isSimpleData: true,
				treeNodeKey: "id",
				treeNodeParentKey: "upId",
				async: true,
				asyncUrl:"${path}/system/org/loadChildOrgByOrgType",
				asyncParamOther:{"orgType":"1,3"},
				callback: {
					click: zTreeOnClick
				}
			});
		//鼠标点击页面其它地方，组织机构树消失
		jQuery("html").bind("mousedown", 
				function(event){
					if (!(event.target.id == "DropdownMenuBackground" || jQuery(event.target).parents("#DropdownMenuBackground").length>0)) {
						hideMenu();
					}
				});		
	});

	function showMenu() {
		var cityObj = jQuery("#orgName");
		
		var cityOffset = jQuery("#orgName").offset();
		jQuery("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	}
	function hideMenu() {
		jQuery("#DropdownMenuBackground").fadeOut("fast");
	}

	function zTreeOnClick(event, treeId, treeNode) {
		if (treeNode) {
			
			jQuery("#orgName").val(treeNode.name);
			jQuery("[name='orgId']").val(treeNode.id);
			hideMenu();
		}
	}
	function isClearOrg(){
		var value =jQuery("#orgName").val();
		if(jQuery.trim(value)==""){
		     jQuery("[name='orgId']").val("");
		}
	}
	function del(fileId) {//ajax删除附件
		var element = $('#' + fileId + '_Span');
		var text = $('[name=fileName]', element).text();
		if (confirm("确认删除" + text + "吗？")) {
			$.get("${path}/activity/supervise_case/delFile/"+fileId, function() {
				element.remove();
				if ($('#attaDiv>span').length === 0) {
					$('#attaDiv').html('无');
				}
				;
			});
		}
	}
	 function addfile() {
		 var files = $("#files") ;
		 var deletefile =  $("#deletefile") ;
		 var context = "<div  style=\"cursor:pointer;\"><input type=\"file\" id=\"file\" name=\"file\">&nbsp;<span id=\"deletefile\"><img title=\"删除\" src=\"${path}/resources/images/jian1.png\" style=\" cursor:pointer ;margin: 0px;padding:0px;\"></span></div>" ;
		 $("#addfile").click(function() {
			 files.append(context) ;
		 }) ;
		 $("#deletefile").live("click",function() {
			$(this).parent("div").remove() ;
		 }) ;
		 return false;
	 }
</script>
</head>
<body>
	<div class="panel">
		<fieldset class="fieldset">
			<legend class="legend">修改执法人员信息</legend>
			<form:form id="updateForm" method="post" action="${path }/opinion/lawperson/update" enctype="multipart/form-data">
				<table width="90%" class="table_add">
					<tr>
						<td width="18%" class="tabRight">姓名:</td>
						<td style="text-align: left;">
							<input type="text" id="name" name="name" class="text" value="${lawPerson.name}"/>&nbsp;
							<input type="hidden" id="personId" name="personId" class="text"  value="${lawPerson.personId}"/>
							<font color="red">*</font>
						</td>
						<td width="18%" class="tabRight">性别:</td>
						<td style="text-align: left;">
							<input type="radio" value="1" <c:if test="${lawPerson.sex==1 }">checked="checked"</c:if> name="sex"/>男 &nbsp;
							<input type="radio" value="2" <c:if test="${lawPerson.sex==2 }">checked="checked"</c:if> name="sex"/>女&nbsp;
						</td>
					</tr>
					<tr>
						<td width="18%" class="tabRight">
							行政区划:
						</td>
						<td style="text-align:left;">
						<input class="text" type="text" name="districtId" id="districtId" value="${lawPerson.districtId}"/>
						</td>
						<td width="18%" class="tabRight">职务:</td>
						<td style="text-align: left;">
							<input type="text" value="${lawPerson.post}" id="post" name="post" class="text" />&nbsp;
							</td>
					</tr>
					<tr>
						<td width="18%" class="tabRight">
							执法类别:
						</td>
						<td style="text-align: left;">
							<input type="text" id="lawType" name="lawType" class="text" value="${lawPerson.lawType }"/>&nbsp;
						</td>
						<td width="18%" class="tabRight">联系电话:</td>
							<td style="text-align: left;">
							<input type="text" value="${lawPerson.phone}" id="phone" name="phone" class="text" />&nbsp;
							</td>
					</tr>
					<tr>
						<td width="18%" class="tabRight">所属单位:</td>
						<td style="text-align: left"  >
						<input type="text" name="orgName" id="orgName" onclick="showMenu(); return false;" value="${lawPerson.orgName }" readonly="readonly" class="text"/>
			　	 		<input type="hidden" name="orgId" id="orgId" value="${lawPerson.orgId }" />
						</td>
						<td width="18%" class="tabRight">是否有效:</td>
						<td style="text-align: left;" >
							<input type="radio" value="1" <c:if test="${lawPerson.status==1 }">checked="checked"</c:if> name="status"/>有效 &nbsp;
							<input type="radio" value="2" <c:if test="${lawPerson.status==2 }">checked="checked"</c:if> name="status"/>无效&nbsp;
							</td>
					</tr>
					<tr>
						<td width="18%" class="tabRight">证件编号:</td>
						<td style="text-align: left;"><input type="text" value="${lawPerson.licenceCode}"
							id="licenceCode" name="licenceCode" class="text" />&nbsp;
							</td>
						<td width="18%" class="tabRight">发证机关:</td>
						<td style="text-align: left;"><input type="text" value="${lawPerson.licenceOrg}"
							id="licenceOrg" name="licenceOrg" class="text" />&nbsp;
							</td>
					</tr>
					<tr>
						<td width="18%" class="tabRight">发证时间:</td>
						<td style="text-align: left;">
							<input type="text" class="text" value="<fmt:formatDate value="${lawPerson.licenceTime}" pattern="yyyy-MM-dd" />" id="licenceTime" name="licenceTime" /></td>
						<td width="18%" class="tabRight">
							到期时间:
						</td>
						<td style="text-align:left;">
							<input type="text" value="<fmt:formatDate value="${lawPerson.licenceExpire}" pattern="yyyy-MM-dd" />"  id="licenceExpire" name="licenceExpire" class="text"/>&nbsp;
						</td>
					</tr>
					<tr>
					<td width="18%" class="tabRight">已添加附件：</td>
					<td style="text-align:left;" colspan="3">
						<c:if test="${!empty publishInfoFiles }">
							<div id="attaDiv">
								<c:forEach items="${publishInfoFiles }" var="publishInfoFile">
									<span id="${publishInfoFile.fileId }_Span">
										<a name="fileName" href="${path }/download/publishInfoFile?fileId=${publishInfoFile.fileId}">${publishInfoFile.fileName }</a>
										<a href="javascript:void(0);" onclick="del('${publishInfoFile.fileId}')" style="color: #FF6600;">删除</a>
										<br/>
									</span>
								</c:forEach>
							</div>
						</c:if>
						<c:if test="${empty publishInfoFiles }">
							无
						</c:if>
					</td>
				</tr>
				<tr>
						<td width="18%" class="tabRight">附件（执法资格证）:</td>
						<td id="files" style="text-align:left;" colspan="3">
						<input type="file" id="file" name="file" style="margin: 0px;padding:0px;"/>
						<img title="添加"  id="addfile" src="${path}/resources/images/jia1.png" style=" cursor:pointer ;margin: 0px;padding:0px;"/>
						</td>
				
				</tr>
				</table>
				<table style="width: 98%; margin-top: 5px;">
					<tr>
						<td align="center"><input type="submit" value="保&nbsp;存" class="btn_small" /> 
						<input type="button" value="返&nbsp;回"class="btn_small"
						onclick="javascript:window.location.href='${path}/opinion/lawperson/main'" />
						</td>
					</tr>
				</table>
			</form:form>
		</fieldset>
	</div>
	<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
		<div align="right">
			<a href="javascript:void();" onclick="javascript:document.getElementById('orgName').value = '';document.getElementById('orgId').value = '';">清空</a>
		</div>
		<ul id="dropdownMenu" class="tree"></ul>
	</div>
</body>
</html>