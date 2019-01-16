<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/views/common/taglibs.jsp"%>
<div style="margin: 5px; font-family:'Arial','sans-serif','Verdana';font-size: 12px;">
   <table  class="blues" style="margin: 0px ;">
		<tr>
			<td class="tabRight" style="width: 30%">
				用户名
			</td>
			<td style="text-align: left;">
				${user.userName}
			</td>
		</tr>
		<tr>
			<td class="tabRight" style="width: 30%">
				组织机构名
			</td>
			<td style="text-align: left;">
				${user.orgName}
			</td>
		</tr>
		<tr>
			<td class="tabRight" style="width: 30%">
				岗位
			</td>
			<td style="text-align: left;">
				${user.postName}
			</td>
		</tr>
		<tr>
			<td class="tabRight"  width="30%">邮箱</td>
			<td  style="text-align: left;">
				${user.email}
			</td>
		</tr>
		<tr>
			<td  class="tabRight" width="30%">
				联系电话
			</td>
			<td style="text-align: left;">
				${user.telephone}
			</td>
		</tr>
		<tr>
			<td  class="tabRight" width="30%">
				联系地址
			</td>
			<td style="text-align: left;">
				${user.address}
			</td>
		</tr>
		<tr>
			<td  class="tabRight" width="30%">
				创建的主题数
			</td>
			<td style="text-align: left;">
				${createThemeCount}
			</td>
		</tr>
		<tr>
			<td  class="tabRight" width="30%">
				参与的主题数
			</td>
			<td style="text-align: left;">
				${joinThemeCount}
			</td>
		</tr>
	</table>
	</div>