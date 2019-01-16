<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
</head>
<body>
		<table width="100%" class="blues" style="font-family:'Arial','sans-serif','Verdana';font-size: 12px;">
			<tr>
				<td width="20%" class="tabRight">
					用户账号
				</td>
				<td width="30%" style="text-align: left;">
					${user.account }
				</td>
				<td width="20%" class="tabRight">
					用户姓名
				</td>
				<td width="30%" style="text-align: left;">
					${user.userName }
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					所属机构
				</td>
				<td width="30%" style="text-align: left;">
					${user.organise.orgName }
				</td>
				<td width="20%" class="tabRight">
					所属岗位
				</td>
				<td width="30%" style="text-align: left;">
					${user.post.postName }
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					身份证号
				</td>
				<td width="30%" style="text-align: left;">
					${user.idCard }
				</td>
				<td width="20%" class="tabRight">
					邮箱
				</td>
				<td width="30%" style="text-align: left;">
					${user.email }
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					联系电话
				</td>
				<td width="30%" style="text-align: left;">
					${user.telephone }
				</td>
				<td width="20%" class="tabRight">
					用户类型
				</td>
				<td width="30%" style="text-align: left;">
					<dict:getDictionary var="userType" groupCode="userType" dicCode="${user.userType}"/>
                    ${userType.dtName}
				</td>
			</tr>
            <tr>
				<td width="20%" class="tabRight">
					联系地址
				</td>
				<td colspan="3"  style="text-align: left;">
					${user.address }
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					备注
				</td>
				<td colspan="3"  style="text-align: left;">
					${user.userNote }
				</td>
			</tr>
		</table>
</body>
</html>