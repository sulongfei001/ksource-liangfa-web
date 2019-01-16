<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script charset="utf-8" src="${path }/resources/script/kindeditor/kindeditor-min.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<div class="panel">
		<fieldset class="fieldset">
			<legend class="legend">执法人员信息详情</legend>
				<table width="90%" class="table_add">
					<tr>
						<td width="18%" class="tabRight">姓名:</td>
						<td style="text-align: left;">
							${lawPerson.name}
							<input type="hidden" id="personId" name="personId" class="text"  value="${lawPerson.personId}"/>
						</td>
						<td width="18%" class="tabRight">性别:</td>
						<td style="text-align: left;">
						<c:if test="${lawPerson.sex==1 }">男 </c:if>
						<c:if test="${lawPerson.sex==2}">女 </c:if>
						</td>
					</tr>
					<tr>
						<td width="18%" class="tabRight">
							行政区划:
						</td>
						<td style="text-align:left;">
						${lawPerson.districtId}
						</td>
<%-- 						<td width="18%" class="tabRight">
							执法区域是否包含下级:
						</td>
						<td style="text-align:left;">
							<c:if test="${lawPerson.includeLower==1 }">包含</c:if>
							<c:if test="${lawPerson.includeLower==2 }">不包含</c:if>
						</td> --%>
						<td width="18%" class="tabRight">职务:</td>
						<td style="text-align: left;">
							${lawPerson.post}
							</td>
					</tr>
					<tr>
						<td width="18%" class="tabRight">
							执法类别:
						</td>
						<td style="text-align: left;">
							${lawPerson.lawType }
						</td>
						<td width="18%" class="tabRight">联系电话:</td>
							<td style="text-align: left;">
							${lawPerson.phone}
							</td>
					</tr>
					<tr>
						<td width="18%" class="tabRight" width="18%">所属单位:</td>
						<td style="text-align: left">
						<input type="hidden" class="text" name="orgId" id="receiveOrg" value="${lawPerson.orgId }"/>
						${lawPerson.orgName }
						</td>
						<td width="18%" class="tabRight">是否有效:</td>
						<td style="text-align: left;" colspan="3">
							<c:if test="${lawPerson.status==1 }">有效 </c:if>
							<c:if test="${lawPerson.status==2 }">无效 </c:if>
							</td>
					</tr>
					<tr>
						<td width="18%" class="tabRight">证件编号:</td>
						<td style="text-align: left;">
							${lawPerson.licenceCode}
							</td>
						<td width="18%" class="tabRight">发证机关:</td>
						<td style="text-align: left;">
							${lawPerson.licenceOrg}
							</td>
					</tr>
					<tr>
						<td width="18%" class="tabRight">发证时间:</td>
						<td style="text-align: left;">
							<fmt:formatDate value="${lawPerson.licenceTime}" pattern="yyyy-MM-dd" />
						</td>
						<td width="18%" class="tabRight">
							到期时间:
						</td>
						<td style="text-align:left;">
							<fmt:formatDate value="${lawPerson.licenceExpire}" pattern="yyyy-MM-dd" />
						</td>
					</tr>
					<tr>
					<td width="18%" class="tabRight">附件（执法资格证）:</td>
					<td style="text-align:left;" colspan="3">
						<c:if test="${!empty publishInfoFiles }">
							<div id="attaDiv">
								<c:forEach items="${publishInfoFiles }" var="publishInfoFile">
									<span id="${publishInfoFile.fileId }_Span">
										<a name="fileName" href="${path }/download/publishInfoFile?fileId=${publishInfoFile.fileId}">${publishInfoFile.fileName }</a>
										<br/>
									</span>
								</c:forEach>
							</div>
						</c:if>
						<c:if test="${empty publishInfoFiles }">
							无
						</c:if>
					</td>
				</tr>
				</table>
				<%-- <table style="width: 98%; margin-top: 5px;">
					<tr>
						<td align="center">
						<input type="button" value="返&nbsp;回"class="btn_small"
						onclick="javascript:window.location.href='${path}/opinion/lawperson/main'" />
						</td>
					</tr>
				</table> --%>
		</fieldset>
	</div>
</body>
</html>