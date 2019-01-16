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
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css"/>
    <link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
          type="text/css"/>
    <script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
    <script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
    <script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
    <script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"
            type="text/javascript"></script>
    <script src="${path }/resources/jquery/jquery.blockUI.js"></script>
    <script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            //同步未读邮件数量 (如果是0则不显示)
            var num = $(window.parent.document).find('b').text();
            num = parseInt(num.substring(1, num.length - 1));
            var notReadNum = '${notReadNum}';
            var str = '';
            if (parseInt(notReadNum) > 0) {
                str = '(' + notReadNum + ')';
            }
            $(window.parent.document).find('b').text(str);

            $('.J-markHasRead,.J-markNotRead').click(function () {   //标记未读，已读，删除，永久删除 动作
                var state = '1';   //未读:0 已读:1
                var str = '已读';
                if ($(this).hasClass('J-markNotRead')) {
                    state = '0';
                    str = '未读';
                }
                var path = '${path}/email/received/updateReadState/' + state;
                var flag;
                var name;
                var formObj = document.getElementById('delForm');
                if ($('[name=check]:checked').length) {
                    flag = true;
                }
                if (flag) {
                    top.art.dialog.confirm("确认将选中邮件标记为" + str + "吗？",
                            function () {
                                jQuery("#delForm").attr('action', path).submit();
                            }
                    );
                } else {
                    top.art.dialog.alert("未选中任何邮件!");
                }
                return false;
            });
            $('.J-invalidEmail,.J-delEmail').click(function () {  // 删除和永久删除 动作
                //判断是删除还是彻底删除动作
                var path = '${path}/email/received/invalid';
                var str = '';
                if ($(this).hasClass('J-delEmail')) {
                    path = '${path}/email/received/del';
                    str = '彻底';
                }
                var flag;
                var name;
                var formObj = document.getElementById('delForm');
                if ($('[name=check]:checked').length) {
                    flag = true;
                }
                if (flag) {
                    top.art.dialog.confirm("确认" + str + "删除选中的邮件吗？",
                            function () {
                                jQuery("#delForm").attr('action', path);
                                jQuery("#delForm").submit();
                            }
                    );
                } else {
                    top.art.dialog.alert("未选中任何邮件!");
                }
                return false;
            });
            $('.J-check').click(function () {  //选择　动作
                var checks = $("[name='check']");
                if (this.id == 'checkAll') {
                    $('#checkbox').attr('checked', true);
                    checks.attr('checked', true);
                } else if (this.id == 'checkNo') {
                    $('#checkbox').attr('checked', false);
                    checks.attr('checked', false);
                } else if (this.id == 'checkRead') {
                    checks.attr('checked', false);
                    $('[readState="1"]').attr('checked', true);
                } else if (this.id == 'checkNotRead') {
                    checks.attr('checked', false);
                    $('[readState="0"]').attr('checked', true);
                }
            });
        });
        function checkAll(obj) {
            jQuery("[name='check']").attr("checked", obj.checked);
        }
    </script>
</head>
<body>
<div class="panel">
    <form:form id="delForm" method="post">
        <display:table name="receivedList" uid="domain" cellpadding="0" cellspacing="0"
                       requestURI="${path }/email/received/main">
            <display:caption class="tooltar_btn">
                <input type="button" value="标记为已读" class="btn_big J-markHasRead"/>
                <input type="button" value="标记为未读" class="btn_big J-markNotRead"/>
                <input type="button" value="删除" class="btn_small J-invalidEmail"/>
                <input type="button" value="彻底删除" class="btn_big J-delEmail"/>

                <div style="margin-top:5px;">
                    <span class="addrtitle">选择：</span>&nbsp;
                    <a href="#" id="checkAll" class="J-check">全部</a>&nbsp;-&nbsp;
                    <a href="#" id="checkNo" class="J-check">无</a>&nbsp;-&nbsp;
                    <a href="#" id="checkRead" class="J-check">已读</a>&nbsp;-&nbsp;
                    <a href="#" id="checkNotRead" class="J-check">未读</a>
                </div>
            </display:caption>
            <display:column
                    title="<input type='checkbox' onclick='checkAll(this)' id='checkbox'/>" style="width:50px;">
                <input type="checkbox" name="check" value="${domain.receivedId}" readState='${domain.readState}'/>
            </display:column>
            <display:column title="序号" style="width:50px;">
                <c:if test="${page==null || page==0}">
                    ${domain_rowNum}
                </c:if>
                <c:if test="${page>0 }">
                    ${(page-1)*10 + domain_rowNum}
                </c:if>
            </display:column>
            <display:column title='<input type="button" disabled="" class="ico_mailtitle">' style="width:50px;">
                <div class="readAndAttr">
                    <c:if test="${domain.readState==1}">
                        <div title="已读" class="cir Rr">&nbsp;</div>
                    </c:if>
                    <c:if test="${domain.readState==0}">
                        <div title="未读" class="cir Ru">&nbsp;</div>
                    </c:if>
                    <c:if test="${domain.hasAttr==1}">
                        <div title="附件" class="cij Ju">&nbsp;</div>
                    </c:if>
                    <c:if test="${domain.hasAttr==0}">
                        <div title="附件" class="cij">&nbsp;</div>
                    </c:if>
                </div>
            </display:column>
            <display:column title="发件人" property="sendUserName" style="text-align:left;"></display:column>
            <display:column title="主题" style="text-align:left;">
                <a href="${path}/email/received/detail/${domain.receivedId}">${domain.subject}</a>
            </display:column>
            <display:column title="发送时间" style="text-align:left;">
                <fmt:formatDate value="${domain.sendTime}" pattern="yyyy-MM-dd"/>
            </display:column>
        </display:table>
    </form:form>
</div>
</body>
</html>
