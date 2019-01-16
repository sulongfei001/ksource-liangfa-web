<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/views/common/taglibs.jsp"%>
    <c:if test="${content!=null}">
    <script type="text/javascript">
		var content = ${content};
		parent.addContent(content);				
	</script>
    </c:if>
