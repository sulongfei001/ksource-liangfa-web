<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css"/>
    <script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
    <script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
    <script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
    <script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
    <script src="${path}/resources/script/cleaner.js"></script>
    <script type="text/javascript">
        function add() {
            window.location.href = "${path}/system/district/v_add?upDistrictCode=${district.upDistrictCode }";
        }
        function initAuthority() {
            art.dialog.confirm('此操作会把现有组织机构、岗位和用户全部删除，再通过现有行政区划生成且不可回退，确认删除？',function(){
                $.getJSON("${path}/system/district/init", function(json){
                  if(json.result){
                      art.dialog.alert(json.msg);
                  }
             });
            });
        }

    </script>
</head>
<body>
<div class="panel">
    <fieldset class="fieldset">
        <legend class="legend">行政区划查询</legend>
        <form:form action="${path }/system/district/district_manage" method="post" modelAttribute="district">
            <input type="hidden" class="text" name="upDistrictCode" value="${district.upDistrictCode }">
            <table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
                <tr>
                    <td style="width: 12%;text-align: right;">行政区划代码:</td>
                    <td style="width: 25%;text-align: left;">
                        <input type="text" class="text" name="districtCode" value="${district.districtCode }">
                    </td>
                    <td style="width: 12%;text-align: right;">行政区划名称:</td>
                    <td style="width: 25%;text-align: left;">
                        <input type="text" class="text" name="districtName" value="${district.districtName }">
                    </td>
                    <td width="26%" align="left" rowspan="2" valign="middle">
                        <input type="submit" value="查&nbsp;询" class="btn_query"/>
                    </td>
                </tr>
            </table>
        </form:form>

    </fieldset>

    <display:table name="helpers" uid="helper" cellpadding="0"
                   cellspacing="0" requestURI="${path }/system/district/district_manage">
        <display:caption class="tooltar_btn">
            <input type="button" class="btn_small" value="添&nbsp;加" onclick="add()"/>
            <input type="button" class="btn_small" value="初始化" onclick="initAuthority()"/>
        </display:caption>
        <display:column title="序号">
            <c:if test="${page==null || page==0}">
                ${helper_rowNum}
            </c:if>
            <c:if test="${page>0 }">
                ${(page-1)*PRE_PARE + helper_rowNum}
            </c:if>
        </display:column>
        <display:column title="行政区划代码" property="districtCode" style="text-align:left;"></display:column>
        <display:column title="行政区划名称" style="text-align:left;">
            <a href="${path}/system/district/detail?districtCode=${helper.districtCode}"
               title="${district.districtName }">${helper.districtName }</a>
        </display:column>
        <display:column title="上级行政区划代码" property="upDistrictCode" style="text-align:left;"></display:column>
        <display:column title="行政区划级别" property="jb" style="text-align:left;"></display:column>
        <display:column title="排序" property="serial" style="text-align:left;"></display:column>
        <display:column title="操作">
            <a href="${path}/system/district/v_update?districtCode=${helper.districtCode}">修改</a>
            <c:if test="${helper.orgNum == 0 }">
                <a href="javascript:;"
                   onclick="top.art.dialog.confirm('确认删除此行政区划吗？',function(){location.href='${path}/system/district/delete?districtCode=${helper.districtCode}&upDistrictCode=${helper.upDistrictCode }';})">删除</a>
            </c:if>
        </display:column>
    </display:table>
</div>
</body>
</html>