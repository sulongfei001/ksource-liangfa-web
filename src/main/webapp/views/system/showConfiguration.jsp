<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ include file="/views/common/taglibs.jsp"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript">
</script>
</head>
<%
	String showsize = request.getParameter("showsize");
	String perpage = request.getParameter("perpage");
	if(showsize!=null&&perpage!=null){
 		if(showsize!=""&&perpage!=""){
 			Paging.pageConfiguration(showsize, perpage);
 			out.print("<script>alert('配置成功！');</script>");
 		}else{
	 		out.print("<script>alert('输入框不能为空');</script>");
 		}
	}
	request.setAttribute("showsize",Paging.showsize);
	request.setAttribute("perpage",Paging.perpage);
%>
<body>
<div class="panel">
	<fieldset class="fieldset"  >
	<legend class="legend">配置分页显示</legend>
	<form action=""  method="post">
		<table  class="table_add" style="width: 60%;" align="center">
			<tr>
				<td width="20%" class="tabRight">首页显示案件条数：</td>
				<td width="30%" style="text-align: left;"><input type="text" class="text" id = "showsize" name = "showsize" onKeyUp="this.value=this.value.replace(/\D/g,'')" value="${showsize}"></td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">分页页面显示条数：</td>
				<td width="30%" style="text-align: left;"><input type="text" class="text" id = "perpage" name = "perpage" onKeyUp="this.value=this.value.replace(/\D/g,'')" value="${perpage}"></td>
			</tr>
		</table>
		<table style="width:98%;margin-top: 5px;">
			<tr>
			<td align="center">
				<input id = "submit" type="submit" class="btn_small"  value="保 存">
			</td>
			</tr>
		</table>
	</form>
	</fieldset>
</div>
</body>
</html>