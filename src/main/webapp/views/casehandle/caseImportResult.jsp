<%--
  Date: 13-5-2
  Time: 上午11:55
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css"/>
</head>
<body>
<div class="alert alert-success" style="margin-top: 10px;width: 500px;margin-left: 50%;margin-right:auto;margin-left: auto;">
        <div ><img src="${path}/resources/images/right.png"/>   </div>
        <div ><strong>导入结果:</strong>成功导入${count}条案件。
        <input type="button" class="btn_big" id="hrefBtn" value="查看案件" onclick="window.location.href='${path}/caseTem/main'"/>  </div>
</div>
</body>
</html>