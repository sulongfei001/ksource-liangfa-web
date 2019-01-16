<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript">
function setValue(){
    $("#infoDiv").html("");
	var roles = document.getElementById('inPost');
	var roleId = "";
	for(m=0;m<roles.options.length;m++){	
		roleId+=roles.options[m].value+",";
	}
	document.getElementById('roleIds').value=roleId;
	return true;
}
function addloc(str1,str2)
{

	var locs,mylocs;
	locs = MM_findObj_(str1);
	mylocs = MM_findObj_(str2);
  	var i=1;
	  for(var x=locs.length-1;x>=0;x--)
	  {
	    var opt = locs.options[x];
	    if (opt.selected)
	    {
	        mylocs.options[mylocs.options.length] = new Option(opt.text, opt.value, 0, 0);
	        locs.options[x] = null;
	    }
	  }
}
function delloc(str1,str2){
  	var locs,mylocs;
    locs = MM_findObj_(str1);
    mylocs = MM_findObj_(str2);

	  for(var x=mylocs.length-1;x>=0;x--){
	    var opt = mylocs.options[x];
	    if (opt.selected)
	    {
	      mylocs.options[x] = null;
	      locs.options[locs.options.length] = new Option(opt.text, opt.value, 0, 0);
	    }
	  }
}
function MM_findObj_(n, d)
{

	var p,i,x;

	if(!d)
		d=document;

	if((p=n.indexOf("?"))>0&&parent.frames.length)
	{
		d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);  
	}
	if( !(x=d[n]) && d.all )
		x=d.all[n];

	for (i=0;!x&&i<d.forms.length;i++)
		x=d.forms[i][n];

	for(i=0;!x&&d.layers&&i<d.layers.length;i++)
		x=MM_findObj_(n,d.layers[i].document);

	if (x==null){
	  x=document.getElementsByName(n)[0];
	 }
	return x;
}

</script>
</head>
<body>
	<div class="panel"  >
		<form:form action="${path }/system/post/setrole">
			<table  width="90%" class="blue"  align="center">
				<tr>
				<td width="20%" class="tabRight" >岗位编号:</td>
				<td width="30%" style="text-align: left;" >${post.postId}</td>					
				<td width="20%" class="tabRight">岗位名称:</td>
				<td width="30%"  style="text-align: left;">${post.postName}</td>
				</tr>
			</table>
		<table  cellspacing="0" cellpadding="0" style="width: 90%;height: 1px;background-color: gray; margin-top:5px; margin-bottom: 5px;" align="center" ><tr><td></td></tr></table>
			<table  width="90%" align="center" class="blue" >
				<tr>
					<td class="tableCenter">
						<table width="90%" align="center">
							<tr>
								<td align="center">未分配角色</td>
							</tr>
							<tr>
								<td><select id="outPost" multiple="multiple" size="10"
									style="width: 14em;height: 20em;border: solid 1px #A8CFEB;">
										<c:forEach items="${notGrantedRoles}" var="role">
											<option value="${role.roleId}">${role.roleName}</option>
										</c:forEach>
								</select></td>
							</tr>
						</table>
					</td>
					<td align="center">
						<div>
							<br /> 
							<input onclick="addloc('outPost','inPost')" type="button"
								class="btn_small" value="添 加" name="AddLoc" /> 
							<br/> 							
							<br/>
							<br/>
							<input onclick="delloc('outPost','inPost')" type="button"
								class="btn_small" value="移 除" name="DelLoc" />
						</div>
					</td>
					<td nowrap="nowrap" class="tableCenter">
						<table width="90%" align="center" class="blue" >
							<tr>
								<td align="center">已分配角色</td>
							</tr>
							<tr>
								<td><select id="inPost" multiple="multiple" size="10"
									style="width: 14em;height: 20em;border: solid 1px #A8CFEB;">
										<c:forEach items="${grantedRoles}" var="role">
											<option value="${role.roleId}">${role.roleName}</option>
										</c:forEach>
								</select></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="3" align="center">
						<center>
							<input type="submit" value="保 存" class="btn_small"
								onclick="return setValue()" />
						</center>
					</td>
				</tr>
			</table>
			<input type="hidden" id="roleIds" name="roleIds" value="" />
			<input type="hidden" name="postId" value="${post.postId }" />
		</form:form>
		<div style="color: red" id="infoDiv">${info}</div>
	</div>
</body>
</html>