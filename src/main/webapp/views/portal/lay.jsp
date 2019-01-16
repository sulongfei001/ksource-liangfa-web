<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib uri="dictionary" prefix="dic"%>
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="content_main_bg"  > 
		      <tr>
		        <td width="10">&nbsp;</td>
		        <td valign="top" class="content_padding">
		        	<table class="blues" style="table-layout:fixed; ">
				        <thead>
				          	<tr >
					          	<th style="width: 50%">标题</th>
					          	<th>分类</th>
					          	<th>发布时间</th>
				          	</tr>
			          	</thead>
			          	<tbody>
	          	<c:forEach  var="lay" items="${layList}" varStatus="layStatus">
			          	<c:if test="${layStatus.index%2!=0}">
			          	<tr class="even">
			          	</c:if>
			          	 <c:if test="${layStatus.index%2==0}">
			          	<tr class="odd">
			          	</c:if>  
	          	   <td nowrap="nowrap"  class="ellipsis">
	          	      <a href="${path }/info/lay/view?infoId=${lay.infoId}&back=true&backType=back" title="${fn:replace(lay.title ,"\"","&quot;")}"> ${lay.title}</a>
	          	   </td>
		          	<td nowrap="nowrap" >
			          	${lay.typeName}
		          	</td>
		          	<td nowrap="nowrap">
		          		<fmt:formatDate value="${lay.createTime }" pattern="yyyy-MM-dd"/>
		          	</td>
	          	   </tr>
	          	</c:forEach>
	          	</tbody>
	          	</table>
		          	<p align="right"><a href="${path}/info/lay/laySearch?back=true">更多&gt;&gt;</a></p>
	        	</td>
		        <td class="content_main_right">&nbsp;</td>
		      </tr>
	    </table>