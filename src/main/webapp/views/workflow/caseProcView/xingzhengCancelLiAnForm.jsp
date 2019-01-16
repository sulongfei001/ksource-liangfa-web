<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="dic" uri="dictionary" %>
<script src="<c:url value="/resources/script/jqueryUtil.js"/>"></script>

<!-- ligerUI -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>

<c:if test="${!empty showBackAndUpdate}">
<div>
  <button id="reDealButton" onclick="rollBackCase()" class="btn_big">重新办理</button>
   <script type="text/javascript">
      function rollBackCase(){
    
      $.ligerDialog.confirm("流程将回退至此步骤，确定重新办理此步骤吗？",function(oper){
    	  if(oper){
    		  $.get('${path}/workflow/task/noProcRollBack',{caseId:'${caseInfo.caseId}',rollBackType:5},function(data){
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
    <b>办理人：</b>${caseStep.assignPersonName }(${caseStep.assignPersonOrgName })&nbsp;&nbsp;&nbsp;
    <b>办理时间：</b>
    <fmt:formatDate value="${caseStep.endDate}"
                    pattern="yyyy年MM月dd日 HH:mm:ss"/>
</p>
<h3>>>行政撤案</h3>
		<table class="blues" style="width: 98%;margin-left: 10px;">
		<tr>
				<td width="20%"  class="tabRight" >承办人：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					${xingzhengCancelLiAnForm.undertaker }
				</td>
		</tr>
		<tr>
				<td width="20%"  class="tabRight" >承办时间：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					<fmt:formatDate value="${xingzhengCancelLiAnForm.undertakeTime }" pattern="yyyy-MM-dd"/>
				</td>
		</tr>
		<tr>
			<td width="20%"  class="tabRight" >撤案原因：</td>
			<td width="80%" style="text-align: left;" colspan="3">
				${xingzhengCancelLiAnForm.cancelLianReason }
			</td>
		</tr>
		<c:set var="notLiAnAttachId" value="f${xingzhengCancelLiAnForm.attachId}"></c:set>
			 <c:if test="${!empty attaMap[notLiAnAttachId]}">
			<tr>
				<td width="20%"  class="tabRight" >附件：</td>
				<td width="80%" style="text-align: left;"  colspan="3">
					${attaMap[notLiAnAttachId].attachmentName }
			        <span>
			        <c:if test="${!empty attaMap[notLiAnAttachId]}">
			        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${xingzhengCancelLiAnForm.attachId}"
			           title="下载">
			            下载
			        </a>
			        </c:if>
					<c:if test="${attaMap[notLiAnAttachId].canOnlineRead}">
			            <c:choose>
			                <c:when test="${empty attaMap[notLiAnAttachId].swfPath}">
			                    <a style="margin-left: 4px;"
			                       href="javascript:void(0);"
			                       onclick="$.ligerDialog.warn('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
			                    </a>
			                </c:when>
			                <c:when test="${!empty attaMap[notLiAnAttachId].swfPath}">
			                    <a style=" margin-left: 4px;"
			                       href="javascript:void(0);"
			                       onclick="docPreview('${xingzhengCancelLiAnForm.attachId}','${attaMap[notLiAnAttachId].attachmentName }');"
			                       title="在线查看"> 预览
			                    </a>
			                </c:when>
			            </c:choose>
			        </c:if>
					</span>					
				</td>
			</tr>
			</c:if>
			<tr>
			<td width="20%"  class="tabRight" >备注：</td>
			<td width="80%" style="text-align: left;" colspan="3">
				${xingzhengCancelLiAnForm.memo }
			</td>
		</tr>
		</table>
<script type="text/javascript">
    //附件预览
    function docPreview(attaId, attaName) {
    	$.ligerDialog.open({ url: '${path }/flexPaper/caseAttachment?id=' + attaId, height: 600,width: 700, isResize:true , title: attaName + " 预览" , buttons: [  { text: '完成', onclick: function (item, dialog) { dialog.close(); } } ] });
    };
</script>
		