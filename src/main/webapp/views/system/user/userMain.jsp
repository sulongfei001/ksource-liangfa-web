<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/script/prototip/css/prototip.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type='text/javascript' src='${path }/resources/script/prototype.js'></script>
<script type='text/javascript' src='${path }/resources/script/prototip/prototip.js'></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>

<script type="text/javascript">
jQuery.noConflict();
</script>
<script type="text/javascript">
jQuery(function(){
	
	
	//组织机构树
	zTree=	jQuery('#dropdownMenu').zTree({
		isSimpleData: true,
		treeNodeKey: "id",
		treeNodeParentKey: "upId",
		async: true,
		asyncUrl:"${path}/system/org/loadChildOrg",
		asyncParam: ["id"],
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
	
	jQuery('[name=tipName]').each(
			  function(i){
				  //修改id
				  var id = "seqId_"+i;
		      	  jQuery(this).attr('id',id);
		      	  //得到参数
		      	 // var param = jQuery(this).html();
		      	  var param = $(this).next("input[id=userId_a]").value;
		      	  var url='${path}/system/user/detail/' + param;
		      	  //给第一个name为tipName的组件创建tip....
				  new Tip(id, {
						title :'用户信息详情',
						ajax: {
							url: url
						},
						hideOthers:true,
						closeButton: true,
						showOn: 'click',
						hideOn: { element: 'closeButton', event: 'click'},
						stem: 'leftTop',
						hook: { target: 'rightMiddle', tip: 'topLeft' }, 
						offset: { x: 0, y: -2 },
						width: 600
					});  
			  }	
			);
			
});
	function showMenu() {
		var cityObj = jQuery("#orgId");
		var cityOffset = jQuery("#orgId").offset();
		jQuery("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	}
	function hideMenu() {
		jQuery("#DropdownMenuBackground").fadeOut("fast");
	}

	function zTreeOnClick(event, treeId, treeNode) {
		if (treeNode) {
			
			jQuery("#orgId").val(treeNode.name);
			jQuery("[name='orgId']").val(treeNode.id);
			hideMenu();
		}
	}
	function isDelete(checkName){
				var flag ;
				var name ;
				for(var i=0;i<document.forms[1].elements.length;i++){
					
					name = document.forms[1].elements[i].name;
					if(name.indexOf(checkName) != -1){
						if(document.forms[1].elements[i].checked){
							flag = true;
							break;
						}
					}
				}   	
				if(flag){
					jQuery.ligerDialog.confirm("确认冻结吗？",'提示信息',
						function(success){
						  if(success){
							  jQuery("#delForm").submit(); 
						  }
						}
					);
				}else{
					jQuery.ligerDialog.warn("请选择要冻结的记录!");
				}
				return false;
			}
	
	//删除用户操作
	function isUserDelete(checkName){	
		var flag ;
		var name ;
		for(var i=0;i<document.forms[1].elements.length;i++){
			
			name = document.forms[1].elements[i].name;
			if(name.indexOf(checkName) != -1){
				if(document.forms[1].elements[i].checked){
					flag = true;
					break;
				}
			}
		}   	
		if(flag){
			top.jQuery.ligerDialog.confirm("确认删除吗？","提示信息",
					function(success){
				if(success){
		               jQuery("#delForm").attr("action","${path }/system/user/delUser");                   
		                jQuery("#delForm").submit();
				}
			 }
			);
		}else{
			top.jQuery.ligerDialog.warn("请选择要删除的用户!");
		}
		return false;
	}
			
	function checkAll(obj){
	       jQuery("[name='check']").attr("checked",obj.checked) ; 			
	}			
	function toUserAdd(form){
		form.action="${path}/system/user/addUI";
		form.submit();
	}
	function showSetPwd(userId){
	  var width = document.documentElement.clientWidth  * 0.7;
	  var height =  document.documentElement.clientHeight * 0.7;  
	  jQuery.ligerDialog.open({
             url:"${path}/system/user/setPwdUI/"+userId,
             id:userId,
             title:'重置密码',
             resize:false,
             width:width,
             height:height
         });
	}
	
	function showSetRole(userId){
	      var width = document.documentElement.clientWidth  * 0.7;
	      var height =  document.documentElement.clientHeight * 0.7;  
	      jQuery.ligerDialog.open({
	             url:"${path}/system/user/updateRoleUI/"+userId,
	             id:userId,
	             title:'设置角色',
	             resize:false,
	             width:width,
	             height:height
	         });		
	}
	function isClearOrg(){
			var value =jQuery("#orgId").val();
			if(jQuery.trim(value)==""){
			     jQuery("[name='orgId']").val("");
			}
	}
	
	//禁止登录
	function isLogin(checkName){	
		var flag ;
		var name ;
		for(var i=0;i<document.forms[1].elements.length;i++){
			
			name = document.forms[1].elements[i].name;
			if(name.indexOf(checkName) != -1){
				if(document.forms[1].elements[i].checked){
					flag = true;
					break;
				}
			}
		}
		if(flag){
			 jQuery.ligerDialog.confirm("确认禁止登录吗？","提示信息",
					function(success){
				 if(success){
		              jQuery("#delForm").attr("action","${path }/system/user/updateUserLoginState");                  
		              jQuery("#delForm").submit(); 
				 }
			 }
			);
		}else{
			 $.ligerDialog.warn("请选择要禁止登陆的用户!");
		}
		return false;
	}
	
	function unfree(userId,userName){
		   top.jQuery.ligerDialog.confirm(
						'确认解冻用户'+userName+'吗?',
						'提示信息',
						function(success) {
							if(success){
								location.href = '${path}/system/user/updateValidity/'+userId;
							}
						});
	}
	
	function allowLogin(userId,userName){
		   top.jQuery.ligerDialog.confirm(
						'确认允许'+userName+'登录系统吗?',
						function(success) {
							if(success){
							location.href = '${path}/system/user/updateLogin/'+userId;
							}
						});
	}
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">用户查询</legend>
<form:form method="post" action="${path }/system/user/search"  onsubmit="isClearOrg()" id="queryForm">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="12%" align="right">
				用户账号
			</td>
			<td width="20%" align="left" >
				<input type="text" name="account" value="${user.account }" class="text"/>
			</td>
			<td width="12%" align="right">
				用户名
			</td>
			<td width="20%" align="left">
				<input type="text" name="userName" value="${user.userName }" class="text"/>
			</td>
			<td width="36%" align="left" rowspan="3" valign="middle">
			
				<input type="submit" value="查 询" class="btn_query" />
			</td>
		</tr>
		<tr>
			<td align="right">
				身份证号
			</td>
			<td align="left">
				<input type="text" name="idCard" value="${user.idCard }" class="text"/>
			</td>
			<td align="right">
				所属机构
			</td>
			<td align="left">
				<input type="text" name="orgName" id="orgId" onfocus="showMenu(); return false;" value="${user.orgName}" readonly="readonly" class="text"/>
			　　<input type="hidden" name="orgId" value="${user.orgId}"/>
			</td>
		</tr>
		<tr>
			<td align="right">
				用户状态
			</td>
			<td align="left">
				<dict:getDictionary var="userStateList" groupCode="userState"/>
				<select id="isValid" name="isValid" >
					<option value="">所有</option>
					<c:forEach items="${userStateList}" var="state">
					<c:choose>
					<c:when test="${state.dtCode==param.isValid}">
					  <option value="${state.dtCode}" selected="selected">${state.dtName}</option>
					</c:when>
					<c:otherwise>
					   <option value="${state.dtCode}">${state.dtName}</option>
					</c:otherwise>
					</c:choose>
					</c:forEach>
				</select>
			</td>
			<td align="right">
				&nbsp;
			</td>
			<td align="left">
				&nbsp;
			</td>
		</tr>
	</table>
	<!-- 查询结束 -->
</form:form>
	</fieldset>

<br/>
<form:form id="delForm" method="post" action="${path }/system/user/del">
	<display:table name="userList" uid="user" cellpadding="0"
		cellspacing="0" requestURI="${path }/system/user/search">
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" name="userAdd"  class="btn_small" 
				onclick="toUserAdd(this.form)" />			
			<input type="submit" value="删除" name="userDelete" onclick="return isUserDelete('check')"  class="btn_small" />		
			<input type="submit" value="冻 结" name="userDel" onclick="return isDelete('check')" class="btn_small" />
			<input type="submit" value="禁止登录 " name="userLogin" onclick="return isLogin('check')" class="btn_big" />
		</display:caption>
		<display:column
			title="<input type='checkbox' onclick='checkAll(this)'/>">
			<input type="checkbox" name="check" value="${user.userId}" />
		</display:column>
		<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${user_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + user_rowNum}
			</c:if>
		</display:column>
		<display:column title="用户账号" style="text-align:left;">
			<a href="javascript:void();" name="tipName" >${user.account}</a>
			<input type="hidden" id="userId_a" value="${user.userId }" />
		</display:column>
		<display:column title="用户名" property="userName" style="text-align:left;"></display:column>
		<display:column title="身份证号" property="idCard" style="text-align:left;"></display:column>
		<display:column title="所属组织机构" property="orgName" style="text-align:left;"></display:column>
		<display:column title="所属岗位" property="postName" style="text-align:left;"></display:column>
        <display:column title="用户类型"  style="text-align:left;">
            <dict:getDictionary var="userType" groupCode="userType" dicCode="${user.userType}"/>
            ${userType.dtName}
        </display:column>
		<display:column title="联系电话" property="telephone" style="text-align:left;"></display:column>
		<display:column title="操作">
		<c:if test="${user.isValid==1}">
			<a href="<c:url value="/system/user/updateUI/${user.userId}"/>">修改</a>
			<%-- <a href="javascript:showSetPwd('${user.userId}');">重置密码</a> --%>
			<a href="javascript:showSetRole('${user.userId}');">设置角色</a>
		</c:if>
		<c:if test="${user.isValid!=1}">
		    <a href="javascript:;" onclick="unfree('${user.userId}','${user.userName}')">解冻</a>
		</c:if>
		<c:if test="${user.isLogin==0 || user.isLogin==null}">
		    <a href="javascript:;" onclick="allowLogin('${user.userId}','${user.userName}')">允许登录</a>
		</c:if>
		</display:column>
	</display:table>
</form:form>
</div>
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void();" onclick="javascript:document.getElementById('orgId').value = '';">清空</a>
	</div>
	<ul id="dropdownMenu" class="tree"></ul>
</div>
</body>
</html>