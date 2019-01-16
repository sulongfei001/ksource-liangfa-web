<%--
  Created by IntelliJ IDEA.
  User: zfzone
  Date: 2010-7-26
  Time: 18:32:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
  <head><title>出错了</title></head>
  <style type="text/css">
      /** 错误信息墙样式*/
      .alert-error{
           background-color: #F2DEDE;
           border-color: #EED3D7;
           color: #B94A48;
          padding: 8px 35px 8px 14px;
            margin-bottom: 18px;
            text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
            border: 1px solid #FBEED5;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 4px;
      }
  </style>
  <body>
  <div class="alert-error" style="height:95%;">
       <strong>系统错误、请联系系统管理员！</strong>
      <p>
      ${msg }
      </p>
  </div>
  </body>
</html>