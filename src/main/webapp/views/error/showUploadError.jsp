
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
  <head><title>出错了</title></head>
  <script type="text/javascript">
    var msg = '${error_msg}';
    if(!msg){//如果是文件过大的限制
    	msg = "文件过大,最大文件为70M！";
    }
    alert(msg);
    /* history.go(-1); */
    history.back();
  </script>
  <body>
 
  </body>
</html>