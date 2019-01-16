<%--
  User: zxl
  Date: 13-1-25
  Time: 上午11:24
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ include file="/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css"
          href="${path }/resources/css/common.css"/>
    <link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
          type="text/css"/>
    <link rel="stylesheet" type="text/css"
          href="${path }/resources/css/noticedetail.css"/>
    <script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
    <title>已删除邮件详情显示页面</title>
    <script type="text/javascript">
        $(function () {
            $('.J-delEmail').click(function () {
                top.art.dialog.confirm('确认彻底删除?', function () {
                    del()
                });
            });
        });
        function del() {
            window.location.href = '${path}/email/common/del?check=${emailMark}_${emailInfo.emailId}';
        }
    </script>
    <style type="text/css">
        .receivedUser {
            margin-right: 5px;
        }

        .attr {
            height: 32px;
            padding-top: 10px;
            padding-right: 5px;
        }
    </style>
</head>
<body>
<div id="content" class="div_mainbody_style" style="padding:0;">
    <div class="toolbg">
        <input type="button" value="彻底删除" class="btn_big J-delEmail"/>
        <input type="button" value="返回" onclick="javascript: history.go(-1);" class="btn_small"/>
    </div>
    <div class="readmailinfo">
        <div id="emailTitle">${emailInfo.subject}</div>
        <div id="tipInfo"
             style="border-bottom: 1px solid gray;">
            <div id="fromName" class="rowTip"><span
                    class="addrtitle">发件人：</span> ${emailInfo.sendUserName}(${emailInfo.sendUser})
            </div>
            <div id="dateTime" class="rowTip" ><span
                    class="addrtitle" style="margin-right: 10px;">时&nbsp;&nbsp;&nbsp;&nbsp;间：</span><fmt:formatDate value="${emailInfo.sendTime}"
                                                                                        pattern="yyyy-MM-dd"/>
            </div>

            <div id="toName" class="rowTip"><span class="addrtitle">收件人：</span>
                <c:if test="${emailMark==1}">
                    <c:forEach var="domain" items="${emailInfo.receivedUserList}">
                        <span class="receivedUser">${domain.userName}(${domain.userId})</span>
                    </c:forEach>
                </c:if>
                <c:if test="${emailMark==0}">
                    <span class="receivedUser">${emailInfo.receivedUserName}(${emailInfo.receiveUser})</span>
                </c:if>
            </div>
            <c:if test="${!empty emailInfo.mailFileList}">
                <div class="rowTip">
                    <span class="addrtitle">附&nbsp;&nbsp;&nbsp;&nbsp;件：</span>
                    <c:forEach var="atta" items="${emailInfo.mailFileList}">
                        <span class="attr">
                                         <a
                                                 href="${path}/download/mailFile/${atta.fileId}"
                                                 title="点击下载${atta.fileName}"> ${atta.fileName}
                                         </a>
                        </span>
                    </c:forEach>
                </div>
            </c:if>
        </div>
        <div id="infoBody">${emailInfo.content}</div>
    </div>
</div>
</body>
</html>