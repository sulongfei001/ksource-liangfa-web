<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>LOGIN</title>
  <body>
  </body>
  <script type="text/javascript">
   var win = window.open("${path}/home/main_new","",'left=0,top=0,width='+ (screen.availWidth - 10) +',height='+ (screen.availHeight-50) +',toolbar=no,status=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes');
   window.opener = null;
   if(win==null){
	   var flag = window.confirm('系统窗口被“弹出窗口拦截程序”所阻挡,无法正常进入系统！\r\n 按【确定】查看帮助,按【取消】以普通模式进入系统。')
	   if(flag){
    	   window.location.href="${path}/views/common/help.html";
       }else{
    	   window.location.href="${path}/home/main_new";
       }
   }else{
	 window.open('','_self');
     window.close();
   }
  </script>
</html>