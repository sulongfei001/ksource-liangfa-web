<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css"/>
    <link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
          type="text/css"/>
    <link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-green/tip-green.css" type="text/css"/>
    <script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
    <script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
    <title>查看信息历史页面</title>
    <script type="text/javascript">
        $(function(){
          jQuery("input:button,input:reset,input:submit,button").not('.noJbu').button();
        })  ;
        //返回
        function fanhui(fromID) {
            window.location.href = "${path}/usermsg/getUser/" + fromID;
        }
    </script>
    <style type="text/css">
          .tip-yellowsimple,.tip-green{
              max-width:500px;

          }
          .tip-yellowsimple .tip-inner{
              font-weight:bold;
          }
          .greenDiv{
             margin-left:50%;
             margin-top:10px;
             margin-right:10px;
             margin-bottom:10px;
          }
    </style>
</head>
<body style="padding:30px;">
<input type="button" value="返回" onclick="fanhui('${from}')"/>

<div style="border: 1px solid #D8D8D8;overflow:auto;">
    <c:forEach items="${userMsgHistoryLists}" var="userMsg" varStatus="userMsgStatus">
        <c:if test="${userMsg.from==to}">
            <div style="margin:10px;">
                <div class="tip-yellowsimple" style="visibility: inherit;  opacity: 1;">
                    <div class="tip-inner tip-bg-image">
                       <p>时间：<fmt:formatDate value="${userMsg.dataTime}" pattern="yyyy-MM-dd hh:mm:ss"/></p>
                       <p>标题：${userMsg.msgTitle}</p>
                       <p>内容：${userMsg.msgBody}</p>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${userMsg.from!=to}">
            <div class="greenDiv">
                <div class="tip-green" style="visibility: inherit; left: 30px; top: 18px; opacity: 1;">
                    <div class="tip-inner tip-bg-image">
                        <p>时间：<fmt:formatDate value="${userMsg.dataTime}" pattern="yyyy-MM-dd hh:mm:ss"/></p>
                        <p>标题：${userMsg.msgTitle}</p>
                        <p>内容：${userMsg.msgBody}</p></div>
                </div>
            </div>
        </c:if>
    </c:forEach>
</div>
</body>
</html>