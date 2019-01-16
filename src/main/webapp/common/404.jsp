<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>


<html>
	<head>
		<title>您访问的页面不存在</title>
			<style type="text/css">
		
			.STYLE10 {
				font-family: "黑体";
				font-size: 36px;
			}
			a.houtuixt{ background:#FF632F; color:#fff; text-decoration:none; border-radius:3px; -moz-border-radius:3px; -webkit-border-radius:3px; padding:3px 20px;}
			a.houtuixt:hover{ background:#FF7A4E; color:#fff; text-decoration:none;border-radius:3px; -moz-border-radius:3px; -webkit-border-radius:3px;}
			
			</style>
	</head>
	<body>
	 <table width="510" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
    	<td><img src="${path }/images/error/error_top.jpg" width="510" height="80" /></td>
  	  </tr>
	  <tr>
	    <td height="200" align="center" valign="top" background="${path }/images/error/error_bg.jpg">
	    	<table width="80%" border="0" cellspacing="0" cellpadding="0" style=" margin-top:20px;">
	        <tr>
	          <td width="34%" align="left"><img src="${path }/images/error/error.gif" width="128" height="128"></td>
	          <td width="66%" valign="top" align="center">
	          	<table width="100%">
	          		<tr height="25">
	          			<td>
	          			<span class="STYLE10">404 页面不存在</span>
	          			</td>
	          		</tr>
	          		<tr height="70">
	          			<td>
	          			对不起，您访问的页面不存在！
	          			</td>
	          		</tr>
	          		
	          		<tr height="25">
		          		<td>
			        	  <a href="javascript:history.back();" class="houtuixt">后 退</a>
		          		</td>
	          		</tr>
	          	</table>
	          	
	     	 </td>
	      </table>
	      </td>
	  </tr>    	 
	  <tr>
    	<td><img src="${path }/images/error/error_bootom.jpg" width="510" height="32" /></td>
      </tr>
	</table>
	</body>
</html>