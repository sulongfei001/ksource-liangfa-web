<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="l-bar-group l-bar-selectpagesize">每页记录&nbsp;
	<select id="pageSize_" name="pageSize_" onchange="changePageSize(this);" class="select_short">
		<c:forEach items="${CodeTable.PAGE_SIZE}" var="pageSize">
			<option value="${pageSize.value }"<c:if test="${pageSize.value == pager.pageSize}">selected="selected"</c:if>>${pageSize.name }</option>
		</c:forEach>
	</select>
</div>

<div class="l-bar-group">
	<div class="l-bar-button l-bar-btnfirst">
		<a href="#" onclick="first();" title="首页">
			<span class=""></span>
		</a> 
	</div>
	<div class="l-bar-button l-bar-btnprev">
		<a href="#" onclick="previous();" title="上一页">
			<span class=""></span>
		</a>
	</div>
</div>

<div class="l-bar-group">
	<span class="pcontrol"> 
		<input size="4" value="${pager.pageNumber }" style="width: 20px;text-align:center;padding-top: 0px;" maxlength="3" 
			class="inputText" type="text" id="navNum" name="navNum"/>/ <span style="line-height: 18px;height: 18px;"><%-- ${pager.totalPage} --%></span>
	</span>
</div>

<div class="l-bar-group">
	<div class="l-bar-button l-bar-btnnext">
		<a href="#" onclick="next();" title="下一页">
			<span></span>
		</a>
	</div>
	<div class="l-bar-button l-bar-btnlast">
		<a href="#" onclick="last();" title="尾页">
			<span></span>
		</a>
	</div>
</div>

<div class="l-bar-group">
	<span>
		<input type="button" id="btnGo" value="GO" class="btn-go" onclick="jumpTo('d-29849-');" >
	</span>
</div>

<div class="l-bar-group">
	<div class="l-bar-button l-bar-btnload">
		<a href="javascript:window.location.reload()">
			<span class=""></span>
		</a>
	</div>
</div>

<div class="l-bar-group" >
	<span class="l-bar-text">显示记录从${pager.getStartNum+1}到
	<c:if test="${pager.fullListSize <= pager.getEndNum}">
	${pager.fullListSize}
	</c:if>
	<c:if test="${pager.fullListSize > pager.getEndNum}">
	${pager.fullListSize}
	</c:if>
	,总数 ${pager.fullListSize}条,共${pager.totalPage}页</span>
</div>
<div class="l-clear"></div>

