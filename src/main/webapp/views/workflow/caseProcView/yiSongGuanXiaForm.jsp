<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="dic" uri="dictionary" %>
<script src="<c:url value="/resources/script/jqueryUtil.js"/>"></script>
<c:if test="${!empty showBackAndUpdate}">
<div>
  <button id="reDealButton" onclick="rollBackCase()" class="btn_big">重新办理</button>
   <script type="text/javascript">
      function rollBackCase(){
	      $.ligerDialog.confirm("",function(oper){
	    	  if(oper){
	    		  $.get('${path}/workflow/task/noProcRollBack',{caseId:'${caseInfo.caseId}',rollBackType:4},function(data){
	                  if(data.result){
	                     //如果成功，重新加载案件详情
	                     $.ligerDialog.success("流程已经退回，请前往待办列表重新办理！");
	                     window.location=window.location.href;
	                  }else{
	                     $.ligerDialog.error(data.msg);
	                  }
	              });
	    	  }
	    	  
	      });
      };
  </script>

</div> <br/>
</c:if>

<p style="padding: 5px; background-color: #c6daef; text-indent: 3em;color: 0156a9;margin-bottom: 8px;font-size: 13px;">
    <b>办理人：${caseStep.assignPersonName }(${caseStep.assignPersonOrgName })</b>&nbsp;&nbsp;&nbsp;
    <b>办理时间：</b>
    <fmt:formatDate value="${caseStep.endDate}"
                    pattern="yyyy年MM月dd日 HH:mm:ss"/>
</p>
<h3>>>分派信息</h3>
		<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;">
		<tr>
				<td width="20%"  class="tabRight" >录入人：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					${yiSongGuanXiaInfo.undertaker }
				</td>
		</tr>
		<tr>
				<td width="20%"  class="tabRight" >移送单位：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					${yiSongGuanXiaInfo.yisongOrgName }
				</td>
		</tr>
		<tr>
				<td width="20%"  class="tabRight" >接收单位：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					${yiSongGuanXiaInfo.jieshouOrgName }
				</td>
		</tr>
		<tr>
				<td width="20%"  class="tabRight" >移送时间：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					<fmt:formatDate value="${yiSongGuanXiaInfo.yisongTime }" pattern="yyyy-MM-dd"/>
				</td>
		</tr>
		<tr>
				<td width="20%"  class="tabRight" >移送理由：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					${yiSongGuanXiaInfo.yisongReason }
				</td>
		</tr>
		<c:set var="yisongAttachId" value="f${yiSongGuanXiaInfo.fileId}"></c:set>
			<tr>
				<td width="20%"  class="tabRight" >分派材料：</td>
				<td width="80%" style="text-align: left;"  colspan="3">
					${attaMap[yisongAttachId].attachmentName }
			        <span>
			        <c:if test="${!empty attaMap[yisongAttachId]}">
			        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${yiSongGuanXiaInfo.fileId}"
			           title="下载">
			            下载
			        </a>
			        </c:if>
					<c:if test="${attaMap[yisongAttachId].canOnlineRead}">
			            <c:choose>
			                <c:when test="${empty attaMap[yisongAttachId].swfPath}">
			                    <a style="margin-left: 4px;"
			                       href="javascript:void(0);"
			                       onclick="alert('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
			                    </a>
			                </c:when>
			                <c:when test="${!empty attaMap[yisongAttachId].swfPath}">
			                    <a style=" margin-left: 4px;"
			                       href="javascript:void(0);"
			                       onclick="docPreview('${yiSongGuanXiaInfo.fileId}','${attaMap[yisongAttachId].attachmentName }');"
			                       title="在线查看"> 预览
			                    </a>
			                </c:when>
			            </c:choose>
			        </c:if>
					</span>					
				</td>
			</tr>
		<tr>
				<td width="20%"  class="tabRight" >备注：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					${yiSongGuanXiaInfo.memo }
				</td>
		</tr>
		</table>
		