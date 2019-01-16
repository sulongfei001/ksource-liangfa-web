<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%
	String _RndData = com.IA300.util.util.GenerateRandomString();
	session.setAttribute("Random", _RndData);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>行政执法与刑事司法衔接信息共享平台</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/iakey/IA300AdminJavascript.js"></script>
<script language="javascript" type="text/javascript">
var flag = false;
//页面加载时调用此函数方法
var uid ;

function OnPageLoad(){
    var browser = DetectBrowser();
    if(browser == "Unknown")
    {
        alert("不支持该浏览器， 如果您在使用傲游或类似浏览器，请切换到IE模式");
        return ;
    }
    //createAdminElementIA300() 对本页面加入IA300插件
    createAdminElementIA300();
    //DetectActiveX() 判断IA300Admin是否安装
    var create = DetectIA300AdminPlugin();
    if(create == false)
    {
        alert("IA300插件未安装!");
        return false;
    }
    
    findkey();
    
}

function findkey(){
	 var rtn = IA300_AdminCheckExist();
       if(0<rtn){
          var id = IA300_AdminGetUIDEx(0);
          $("#uid").val(id); 
       }else{
           alert("未找到USB Key!");
           return false;
       }
}


/*function OnPageLoad() {
	createAdminElementIA300();
	var create = DetectIA300AdminPlugin();
	if (create == false) {
		alert("UKEY插件未安装!");
		return false;
	}
  	uid = IA300_GetHardwareId();
	if (uid == "") {
		alert("获取UKEY硬件ID失败，错误码：" + IA300_GetLastError());
		return false;
	}else{
		$("#uid").val(uid);
	} 
	//获取别名
 	var name = IA300_GetName();
	//获取公司名称
	var company = IA300_GetDescription();
	$("#company").val(company);
	var url = IA300_GetUrl();
	$("#url").val(url);
	
	//createAdminElementIA300();
}*/
function create(){
	var seed = $("#strSeed").val();
	var deskey = $("#strPriKey").val();
	var account = $("#account").val();
	var uid = $("#uid").val();
	if(account == null || account == ''){
		alert("账号不能为空");
		return false;
	}
	$.post("${path}/system/iakey/create",{uid:uid,seed:seed,deskey:deskey,account:account},function(data){
		if(data != ""){
			IA300EditWithParameters(data);
		}else{
			alert("创建失败！");
		}
	});
}

function del(){
	var uid = $("#uid").val();
	$.post("${path}/system/iakey/del",{uid:uid},function(data){
		if(data){
			alert("删除成功！");
			window.location.href="${path}/system/iakey/main";
		}else{
			alert("删除失败！");
		}
	});
}

function IA300EditWithParameters(orgName)
{   
	var hID = $("#uid").val();
    var strSuperPin = $("#strSuperPin").val();
    var strNewSuperPin = $("#strNewSuperPin").val();
    var strUserPin = $("#strUserPin").val();
    var strSeed = $("#strSeed").val();
    var strPriKey = $("#strPriKey").val();
    var strDescripion = $("#company").val();
    var strUrl = $("#url").val();
    var strOther = orgName;//$("#other").val();
    var nUserIE = 1;
    var nEnableRemote = 0;
    var strKeyName = orgName;

    var rtn = IA300_SetParameters(strNewSuperPin, strUserPin, strSeed, strPriKey, strKeyName, strDescripion, strUrl, strOther, nUserIE, nEnableRemote);
    if(0 != rtn){
        alert("配置错误! 请重新配置参数 ");
        return false;
    }
    
    var rtn = IA300_EditWithParametersEx(hID, strSuperPin);
    if(0 != rtn){
        alert("本地写Key失败,错误码:"+IA300_AdminGetLastError());
        return false;
    }
     alert(orgName+"的UKEY绑定成功！");
}

</script>
</head>

<body onLoad="javascript:OnPageLoad();">
<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">创建用户</legend>
	<!--页面提示-->
	<form name="form1" action="${path }/iakey/create" method="post">
	  <table width="90%" class="table_add">
          <tr>
            <td width="20%" class="tabRight">超级密码:</td>
            <td width="80%" style="text-align:left;">
            <input class="text" type="text" name="strSuperPin" id="strSuperPin"  value="admin" readonly="readonly">
            </td>
          </tr>
          <tr>
            <td width="20%" class="tabRight">新超级密码:</td>
            <td width="80%" style="text-align:left;">
            <input class="text" type="text" name="strNewSuperPin" id="strNewSuperPin"  value="admin" readonly="readonly">
            </td>
          </tr>
          <tr>
            <td width="20%" class="tabRight">用户密码:</td>
            <td width="80%" style="text-align:left;">
                <input class="text" type="text" name="strUserPin" id="strUserPin"  value="12345678">
             </td>
          </tr>
          <tr>
            <td width="20%" class="tabRight">种子码:</td>
            <td width="80%" style="text-align:left;">
            <input class="text" type="text" name="strSeed" id="strSeed"  value="FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" readonly="readonly">
            </td>
          </tr>
          <tr>
            <td width="20%" class="tabRight">3DES密钥:</td>
            <td width="80%" style="text-align:left;">
            <input class="text" type="text" name="strPriKey" id="strPriKey" value="ABCDEFGhijklmn0123456789" readonly="readonly">
            </td>
          </tr>
	  	<tr>
	  		<td width="20%" class="tabRight">PIN码：</td>
	  		<td width="80%" style="text-align:left;">
	  			<input  class="text" id="uid" name="uid" size="13" maxlength="32" readonly="readonly"/>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td width="20%" class="tabRight">公司名称：</td>
	  		<td width="80%" style="text-align:left;">
	  			<input class="text" id="company" name="company" size="30" maxlength="256" type="text" value="河南金明源信息技术有限公司" />
	  		    
	  		</td>
	  	</tr>	  	
	  	<tr>
	  		<td width="20%" class="tabRight">应用网址：</td>
	  		<td width="80%" style="text-align:left;">
	  			<input class="text" id="url" name="url" size="30" maxlength="256" type="text" value="http://10.65.12.10/liangfa"/>	  			
	  		</td>
	  	</tr>
        <tr>
            <td width="20%" class="tabRight">备注：</td>
            <td width="80%" style="text-align:left;">
                <input class="text" id="other" name="other" size="30" maxlength="256" type="text" value=""/>              
            </td>
        </tr>
        
	  	<tr>
	  		<td width="20%" class="tabRight">用&nbsp;&nbsp;户&nbsp;&nbsp;名：</td>
	  		<td width="80%" style="text-align:left;">
	  			<input class="text" id="account" name="account" size="13" maxlength="128" value="">
	  			&nbsp;&nbsp;<font color="red">*</font>
	  		</td>
	  	</tr>
	  </table>
	</form>
	<input type="button" class="btn_big" value="查找ukey" onclick="findkey()"/>
	<input type="button" class="btn_big" value="创建用户" onclick="create()"/>
	<input type="button" class="btn_big" value="删除用户" onclick="del()"/>
	<!-- <input type="button" class="btn_big" value="更新用户"/> -->
</fieldset>
</div>
</body>
</html>
  