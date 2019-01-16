<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript">
function setValue(){
            $("#infoDiv").html("");
			var xzqhs = document.getElementById('inUser');
			var s_xzqh = "";
			for(m=0;m<xzqhs.options.length;m++){
				if(xzqhs.options[m].value!=""){
					s_xzqh+=xzqhs.options[m].value+",";
				}				
			}
			document.getElementById('roleIds').value=s_xzqh;
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
function delloc(str1,str2)
{

  	var locs,mylocs;
      locs = MM_findObj_(str1);
      mylocs = MM_findObj_(str2);
  var roles = "";
  for(var x=mylocs.length-1;x>=0;x--)
  {
    var opt = mylocs.options[x];
    if (opt.selected)
    {
      if(opt.className=="disabledClass"){
    	 roles += opt.text+",";
      }else{
    	  mylocs.options[x] = null;
          locs.options[locs.options.length] = new Option(opt.text, opt.value, 0, 0);
      }
     
    }
  }
  if(roles!=""){
	  roles = roles.substr(0,roles.length-1);
	  $("#infoDiv").html("“"+roles+"”为岗位拥有角色，不能移除！");
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
	<form:form action="${path }/system/user/updateRole" commandName="user" >
 		<table class="blue" width="90%" align="center" >
			<tr>
				<td width="20%" class="tabRight" >
					用户账号：
				</td>
				<td width="30%"  style="text-align: left;" >
					${user.account}
				</td>
				<td width="20%"   class="tabRight">
					用户名称：
				</td>
				<td width="30%" style="text-align: left;">
					${user.userName}
				</td>
			</tr>
		</table> 
	<table  cellspacing="0" cellpadding="0" style="width: 90%;height: 1px;background-color: gray; margin-top:5px; margin-bottom: 5px;" align="center" ><tr><td></td></tr></table>
		<table width="90%" align="center"  class="blue" >
			<tr>
				<td width="45%">
					<table width="90%" align="center" class="blue">
						<tr>
							<td align="center">
								未分配角色
							</td>
						</tr>
						<tr>
							<td>
								<select id="outUser" multiple="multiple" size="10" style="width: 14em;height: 20em;border: solid 1px #A8CFEB;">

									<c:forEach items="${getRole.outUser}" var="entry">
										<option value="${entry.roleId}">
											${entry.roleName}
										</option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</table>
				</td>
				<td align="center">
					<div style="text-align: center;">
						<br/>
						<br/>
						<br/>
						<input onclick="addloc('outUser','inUser')" type="button" class="btn_small"
							value="添 加" name="AddLoc" />
						<br/>
						<br/>
						<br/>
						<input onclick="delloc('outUser','inUser')" type="button" class="btn_small"
							value="移 除" name="DelLoc" />
					</div>
				</td>
				<td nowrap="nowrap" class="tableCenter" width="45%">
					<table width="90%" align="center"  class="blue">
						<tr>
							<td align="center">
								已分配角色
							</td>
						</tr>
						<tr>
							<td>
								<select id="inUser" multiple="multiple" size="10" style="width: 14em;height: 20em;border: solid 1px #A8CFEB;">
				
									<c:forEach items="${getRole.inUserWithPost}" var="entry2">
										<option disabled="disabled" value="" class="disabledClass">
											${entry2.roleName}
										</option>
										
									</c:forEach>
									
									<c:forEach items="${getRole.inUser}" var="entry">
										<option value="${entry.roleId}">
											${entry.roleName}
										</option>
									</c:forEach>
									
								</select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<center>
						<input type="submit" value="保 存" class="btn_small" onclick="return setValue()"/>
					</center>
				</td>
			</tr>
		</table>
		<input type="hidden" id="roleIds" name="roleIds" value="" />
		<input type="hidden" name="userId" value="${user.userId }" />
		<div style="color:red" id="infoDiv">${info }</div>
	</form:form>	
	</div>
	</body>
	</html>
