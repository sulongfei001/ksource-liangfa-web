<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib uri="dictionary" prefix="dic"%>
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="content_main_bg"> 
	      <tr>
	        <td width="10">&nbsp;</td>
	        <td valign="top" class="content_padding">
	        	<table class="blues" style="table-layout:fixed; ">
		        	<thead>
		          	<tr >
			          	<th style="width: 50%">标题</th>
			          	<th>创建人</th>
			          	<th>创建时间</th>
			          	<th>级别</th>
		          	</tr>
		          	</thead>
	          	<tbody>
	          	<c:forEach  var="notice" items="${noticeList}" varStatus="noticeStatus">
	          	<c:if test="${noticeStatus.index%2!=0 }">
	          	 <tr class="even">
	          	</c:if>
	          	<c:if test="${noticeStatus.index%2==0 }">
	          	 <tr class="odd">
	          	</c:if>
	          	   <td nowrap class="ellipsis" > 
	          	   		<a  href="${path}/notice/searchDisplay?noticeId=${notice.noticeId}&number=3" title="${fn:replace(notice.noticeTitle,"\"","&quot;")}"> 
	          	   		${notice.noticeTitle}
	          	   		</a>
	          	   </td>
		           <td nowrap="nowrap" class="ellipsis" title="${notice.userName}">
		           		${notice.userName}
		           </td>
		           <td nowrap="nowrap" >
		           		<fmt:formatDate value="${notice.noticeTime}" pattern="yyyy-MM-dd"/>
		           </td>
		           <td nowrap="nowrap">
		           <dic:getDictionary var="dictionary" groupCode="noticeLevel" dicCode="${notice.noticeLevel}"/>
		           <c:if test="${dictionary.dtCode==1}">
				${dictionary.dtName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
				<c:if test="${dictionary.dtCode==2}">
				<font color="red">${dictionary.dtName}</font>
				<img src="${path}/resources/images/infolevel.gif" title="重要"/>
				</c:if>
		           </td>
	          	   </tr>
	          	</c:forEach>
	          	</tbody>
	          	</table>
	          	<p align="right"><a href="${path}/notice/search?number=3">更多&gt;&gt;</a></p>
	        </td>
	        <td class="content_main_right">&nbsp;</td>
	      </tr>
	    </table>
