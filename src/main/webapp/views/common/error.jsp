<%--
  Created by IntelliJ IDEA.
  User: zfzone
  Date: 2010-7-26
  Time: 18:32:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
  <head><title>服务异常</title></head>
  <body>
    <form:errors path="*"></form:errors><br/>
  ${exception.message}</body>
</html>