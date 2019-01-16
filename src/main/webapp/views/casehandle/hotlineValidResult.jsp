<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="dic" uri="dictionary" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css"/>
    <link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
          type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css"/>
    <link rel="stylesheet" type="text/css"
    	href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"/>

    <script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
    <script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
    <script src="${path }/resources/jquery/jquery.blockUI.js"></script>
    <script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
    <script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"
            type="text/javascript"></script>
    <script src="${path }/resources/script/jqueryUtil.js"></script>
    <script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body>
<div class="panel">
        <fieldset id="caseInfoC" class="fieldset" style="padding: 10px;">
            <legend class="legend">市长热线数据导入校验结果</legend>
            <c:if test="${!empty error}">
		        <br/>
		    	<fieldset id="caseInfoC" class="fieldset" style="padding: 10px;">
		                <legend class="legend">提示信息</legend>
		        <div class="alert alert-error">
		            <h3>${error}</h3>
		        </div>
		        </fieldset>
		    </c:if>
		    <c:if test="${!empty dataError}">
		        <display:table name="dataError" uid="hotline" cellpadding="0"
		                       cellspacing="0" requestURI="${path }/hotlineInfo/errorList" pagesize="10">
		            <display:column title="excel行号" property="excelRowNum" style="text-align:left;"></display:column>
		            <display:column title="受理编号" property="infoNo" style="text-align:left;"></display:column>
		            <display:column title="校验结果" style="width:50%;">
		                    <p style="text-align: left;color: red;">
		                    <c:forEach items="${hotline.validError}" var="err" varStatus="status">
		                        <span>${status.index+1}.${err}</span>&nbsp;&nbsp;&nbsp;&nbsp;
		                    </c:forEach>
		                    </p>
		            </display:column>
		        </display:table>
		    </c:if>
        </fieldset>
        <br></br>
        <input type="button" value="返  回" class="btn_small"
               onclick="window.location.href='${path}/hotlineInfo/main'"/>
</div>
</body>
</html>