<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
<div class="panel">
分析数据完毕！
<display:table name="caseList" uid="case" cellpadding="0" cellspacing="0">
    <display:column title="序号" style="width:5%">
       ${case_rowNum}
    </display:column>
    <display:column title="分值" property="score" style="text-align:left;"></display:column>
    <display:column title="案件详情" property="caseDetailName" style="text-align:left;"></display:column>
    <display:column title="操作">
        <a href="javascript:;" onclick="top.showCaseProcInfo('${case.caseId}');">案件详情</a>
    </display:column>
</display:table>
</div>
</body>
</html>