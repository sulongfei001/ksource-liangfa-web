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
    <link rel="stylesheet" type="text/css"
          href="${path }/resources/css/noticedetail.css"/>
    <link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
          type="text/css"/>
    <script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
    <title>接收邮件详情显示页面</title>
    <script type="text/javascript">
        if ('${updateNum}') { //修改未读邮件数量（-1）
            var num = $(window.parent.document).find('b').text();
            num = parseInt(num.substring(1, num.length - 1));
            var str = '';
            if (num > 1) {
                str = '(' + (num - 1) + ')';
            }
            $(window.parent.document).find('b').text(str);
        }
        $(function () {
            $('.J-invalidEmail,.J-delEmail').click(function () {
                var str = '';
                var className = 'J-invalidEmail';
                if ($(this).hasClass('J-delEmail')) {
                    str = '彻底';
                    className = 'J-delEmail';
                }
                top.art.dialog.confirm('确认' + str + '删除?', function () {
                    del(className)
                });
            });
            $('.J-replayEmail').click(function () { // 回复 动作
                window.location.href = '${path}/email/send/addUI?replayUser=${emailInfo.sendUser}';
            });
            $('#backBtu').click(function(){
                window.location.href='${path}/email/received/back';
            });
        });
        function del(className) {
            var path = '${path}/email/received/invalid';
            if ($('.' + className).hasClass('J-delEmail')) {
                path = '${path}/email/received/del';
            }
            path += '?check=${emailInfo.receivedId}';
            window.location.href = path;
        }

    </script>
</head>
<body>
<div id="content" class="div_mainbody_style" style="padding:0;">
    <div class="toolbg">
        <input id="replayBtn" type="button" value="回复" class="btn_small J-replayEmail"/>
        <input type="button" value="删除" class="btn_small J-invalidEmail"/>
        <input type="button" value="彻底删除" class="btn_big J-delEmail"/>
        <input type="button" value="返回" id="backBtu" class="btn_small"/>
    </div>
    <div class="readmailinfo">
        <div id="emailTitle">${emailInfo.subject}</div>
        <div id="tipInfo"
             style="border-bottom: 1px solid gray;">
            <div id="fromName" class="rowTip"><span
                    class="addrtitle">发件人：</span> ${emailInfo.sendUserName}(${emailInfo.sendUser})
            </div>
            <div id="dateTime" class="rowTip" style="margin-right: 10px;"><span
                    class="addrtitle">时&nbsp;&nbsp;&nbsp;&nbsp;间：</span>
                <fmt:formatDate value="${emailInfo.sendTime}" pattern="yyyy-MM-dd"/>
            </div>
            <div id="toName" class="rowTip"><span class="addrtitle">收件人：</span>
                <span class="receivedUser">${emailInfo.receivedUserName}(${emailInfo.receiveUser})</span>
            </div>
        <c:if test="${!empty emailInfo.mailFileList}">
            <div class="rowTip">
                <span class="addrtitle">附&nbsp;&nbsp;&nbsp;&nbsp;件：</span>
                <c:forEach var="atta" items="${emailInfo.mailFileList}">
                                <span class="attr">
                                                 <a
                                                         href="${path}/download/mailFile/${atta.fileId}"
                                                         title="点击下载${atta.fileName}">${atta.fileName}
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