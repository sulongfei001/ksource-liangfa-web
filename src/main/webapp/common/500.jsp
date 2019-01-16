<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>


<html>
	<head>
		<title>页面出错了</title>
			<style type="text/css">
			<!--
			.STYLE10 {
				font-family: "黑体";
				font-size: 36px;
			}
			-->  
			</style>
	</head>
	<body>
	 <table width="510" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
    	<td><img src="${path }/images/error/error_top.jpg" width="510" height="80" /></td>
  	  </tr>
	  <tr>
	    <td height="200" align="center" valign="top" background="${path }/images/error/error_bg.jpg">
	    	<table width="80%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td width="34%" align="right"><img src="${path }/images/error/error.gif" width="128" height="128"></td>
	          <td width="66%" valign="top" align="center">
	          	<table width="100%">
	          		<tr height="25">
	          			<td>
	          			<span class="STYLE10">页面出错了</span>
	          			</td>
	          		</tr>
	          		<tr height="70">
	          			<td>
	          			   系统内部错误！
	          			</td>
	          		</tr>
	          		
	          		<tr height="25">
		          		<td>
			        	  <a href="javascript:history.back();">后 退</a>
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