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
                //同步草稿箱邮件数量 (如果是0则不显示)
                var num = $(window.parent.document).find('strong').text();
                num = parseInt(num.substring(1, num.length - 1));
                var draftNum = '${draftNum}';
                var str = '';
                if (parseInt(draftNum) > 0) {
                    str = '(' + draftNum + ')';
                }
                $(window.parent.document).find('strong').text(str);
                
            $('.J-delEmail').click(function () {  // 删除和彻底删除 动作
                //判断是删除还是彻底删除动作
                var flag;
                var formObj = document.getElementById('delForm');
                if ($('[name=check]:checked').length) {
                    flag = true;
                }
                if (flag) {
                    top.art.dialog.confirm("确认删除选中的草稿吗？",
                            function () {
                                jQuery("#delForm").submit();
                            }
                    );
                } else {
                    top.art.dialog.alert("未选中任何草稿!");
                }
                return false;
            });
        });
        function checkAll(obj) {
            jQuery("[name='check']").attr("checked", obj.checked);
        }
    </script>
</head>
<body>
<div class="panel">
    <form:form id="delForm" method="post" action="${path }/email/draft/del">
        <display:table name="draftList" uid="domain" cellpadding="0" cellspacing="0"
                       requestURI="${path }/email/draft/main">
            <display:caption class="tooltar_btn">
                <input type="button" value="删除" id="delEmail" class="btn_big J-delEmail"/>
            </display:caption>
            <display:column
                    title="<input type='checkbox' onclick='checkAll(this)'/>"  style="width:50px;">
                <input type="checkbox" name="check" value="${domain.emailId}"/>
            </display:column>
            <display:column title="序号"  style="width:50px;">
                <c:if test="${page==null || page==0}">
                    ${domain_rowNum}
                </c:if>
                <c:if test="${page>0 }">
                    ${(page-1)*10 + domain_rowNum}
                </c:if>
            </display:column>
            <display:column title='<input type="button" disabled="" class="ico_mailtitle">' style="width:50px;">
                <div class="readAndAttr">
                    <c:if test="${domain.hasAttr==1}">
                        <div title="附件" class="cij Ju">&nbsp;</div>
                    </c:if>
                    <c:if test="${domain.hasAttr==0}">
                        <div title="附件" class="cij">&nbsp;</div>
                    </c:if>
                </div>
            </display:column>
            <display:column title="收件人" style="text-align:left;" >
            	<c:if test="${domain.receivedUserName == null }">(无收件人)</c:if>
            	<c:if test="${domain.receivedUserName != null }">${domain.receivedUserName}</c:if>
            </display:column>
            <display:column title="主题" style="text-align:left;">
                <c:if test="${domain.subject == null }">
                	<a href='${path}/email/draft/detail/${domain.emailId}'>(无主题)</a>
                </c:if>
            	<c:if test="${domain.subject != null }">
					<a href='${path}/email/draft/detail/${domain.emailId}'>${domain.subject}</a>
				</c:if>
            </display:column>
            <display:column title="保存时间" style="text-align:left;" >
                <fmt:formatDate value="${domain.draftTime}" pattern="yyyy-MM-dd"/>
            </display:column>
        </display:table>
    </form:form>
</div>
</body>
</html>
