<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>督办案件添加</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/noticedetail.css" />
<link rel="stylesheet" href="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.js"></script>
<script type="text/javascript">
$(function(){
	 //按钮样式
	$("input:button,input:reset,input:submit").button();
    $('.xbreadcrumbs').xBreadcrumbs();
});
</script>
</head>
<body>
	<div>
		<ul class="xbreadcrumbs" id="breadcrumbs-3" style="margin-bottom: 5px;">
		     <li>
			 	<a href="${path}/activity/supervise_case/back">督办案件列表</a>
		     </li>
		     <li class="current"><a href="javascript:void();">督办案件详情</a></li>
		</ul>
		</div>
	<div id="layout1" class="mini-layout" style="width: 98.5%;float:left;margin-left: 8px;">

     <div title="center" region="center" style="z-index:0;">

		<div >
			<div align="center" id="main">
				<div class="detail_body">
					<div class="detail_title"><label id="_id2">${superviseCase.caseName}</label>
					</div>
					<div class="detail_rec">
						<p>
							<strong>发布人：</strong><label id="_id4">${superviseCase.userId}</label>&nbsp;&nbsp;
							<strong>发布部门：</strong><label id="_id4">${superviseCase.orgName}</label>&nbsp;&nbsp;
							<strong>发布时间：</strong><label id="_id6"><fmt:formatDate value="${superviseCase.createTime}" pattern="yyyy-MM-dd"/></label>&nbsp;&nbsp;
						</p>
						<hr style="color: #CCCCCC" />
					</div>
					<div class="content">
					${superviseCase.content}
					</div>
					<div class="detail_bottom">
						<table border="0" cellpadding="0" cellspacing="0">
							<c:forEach items="${publishInfoFiles}" var="publishInfoFile" varStatus="s">
								<c:choose>
									<c:when test="${s.index == 0}">
										<tr>
											<td style="font:italic bold 12px/30px arial,sans-serif; line-height: 20px;">附件:&nbsp;</td>
											<td align="left"><a href="${path }/download/publishInfoFile?fileId=${publishInfoFile.fileId}">${publishInfoFile.fileName}</a></td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td style="font:italic bold 12px/30px arial,sans-serif; line-height: 20px;">&nbsp;</td>
											<td align="left"><a href="${path }/download/publishInfoFile?fileId=${publishInfoFile.fileId}">${publishInfoFile.fileName}</a></td>
										</tr>
									</c:otherwise>
								</c:choose>
								
							</c:forEach>
							</table>
					</div>
				</div>
			</div>
		</div>

    </div>
    </div>
</body>
</html>