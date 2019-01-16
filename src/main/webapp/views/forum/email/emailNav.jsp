<%--
  User: zxl
  Date: 13-1-25
  Time: 上午11:24
  邮件导航：发件箱，收件箱，已删除邮件
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/home.css"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/caseProc.css"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/rightMenu.css"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"/>
    <script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
    <script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
    <script src="${path }/resources/jquery/layout/jquery.layout.js"></script>
    <script src="${path }/resources/jquery/jquery.blockUI.js"></script>
    <script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
    <script type="text/javascript" language="javascript">
        $(function () {
            //初始化布局
            $("body").layout({applyDefaultStyles: true,resizable:false,closable:false});
            //修改边距
            $('.J-margin').css({margin:'5px'});
            $('.J-email').click(
                    function () {
                        //选中样式
                        $('#i-nav-menu li').removeClass('i-selected');
                        $(this).parent('li').addClass('i-selected');
                        var id = this.id;
                        var path;
                        if (id === 'newEmail') {
                            path = '${path}/email/send/addUI';
                        } else if (id === 'sendEmail') {
                            path = '${path}/email/send/main';
                        } else if (id === 'receivedEmail') {
                            path = '${path}/email/received/main';
                        } else if (id === 'delEmail') {
                            path = '${path}/email/common/invalidMain';
                        } else if (id === 'draftEmail') {
                        	path = '${path}/email/draft/main';
                        }
                        if (path) {
                            window.frames['tableFrame'].location.href = path;
                        }
                    }
            );
            //默认显示信息
            if('${receivedMail}'){
               $('#receivedEmail').click();
            }
        });
    </script>
</head>
<body>
<div class="ui-layout-west J-margin" >
    <div id="i-nav-menu" style="width:100%;">
        <dl class="i-menu-wrap i-account-mgr" style="width:100%;border:none;">
            <dt class="i-hd"><img src="${path }/resources/images/mail_06.jpg" style="margin-right: 5px;" />电子邮件</dt>
            <dd>
                <ul>
                    <li style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis;-o-text-overflow:ellipsis;"><a
                            href="javascript:;" id="newEmail" class="J-email"
                            title="${caseStep.stepName}">新建邮件
                    </a>
                    </li>
                    <li style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis;-o-text-overflow:ellipsis;"><a
                                                href="javascript:;" id="receivedEmail" class="J-email"
                                                title="${caseStep.stepName}">收件箱<b>(${notReadNum})</b></a></li>
                    <li style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis;-o-text-overflow:ellipsis;"><a
                            href="javascript:;" id="sendEmail" class="J-email"
                            title="${caseStep.stepName}">已发送</a></li>
                    <li style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis;-o-text-overflow:ellipsis;"><a
                            href="javascript:;" id="draftEmail" class="J-email"
                            title="${caseStep.stepName}">草稿箱<strong>(${draftNum })</strong></a></li>                                 
                    <li style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis;-o-text-overflow:ellipsis;"><a
                            href="javascript:;" id="delEmail" class="J-email"
                            title="${caseStep.stepName}">已删除</a></li>
                </ul>
            </dd>
        </dl>
    </div>
</div>
<div class="ui-layout-center J-margin" style="margin:5px;">
    <iframe id="postFrame" name="tableFrame" width="100%" height="90%" src="" frameborder="0"></iframe>
</div>
</body>
</html>