<%@page import="com.ksource.common.bean.Paging"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="/WEB-INF/tld/dictionary.tld" prefix="dict" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>
<%
	pageContext.setAttribute("PRE_PARE", Paging.perpage);
%>