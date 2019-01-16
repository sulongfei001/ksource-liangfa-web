<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<script src="<c:url value="/resources/script/jqueryUtil.js"/>"></script>

<!-- ligerUI -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>

<c:if test="${!empty showBackAndUpdate}">
<div>
  <button id="reDealButton" onclick="rollBackCase()" class="btn_big">重新办理</button>
   <script type="text/javascript">
      //jQuery("button").not('.noJbu').button();
      

      
      function rollBackCase(){
    	  
          $.ligerDialog.confirm("流程将回退至此步骤，确定重新办理此步骤吗？",function(oper){
        	  if(oper){
        		  $.get('${path}/workflow/task/rollBack',{caseId:'${caseStep.caseId}'},function(data){
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



<div id="${divId}${ caseStep.stepId}">
	<p style="padding:5px;background-color:#C6DAEF;text-indent: 3em;font-size: 13px;width: 96%;">
		<span name="stepName" style="display: none;"><b>案件步骤：</b>${caseStep.stepName}</span>&nbsp;&nbsp;&nbsp;
		<b>办理人：</b>${caseStep.assignPersonName}（${caseStep.assignPersonOrgName}）&nbsp;&nbsp;&nbsp;
		<b>办理时间：</b><fmt:formatDate value="${caseStep.endDate}"  pattern="yyyy年MM月dd日 HH:mm:ss"/>
	</p>
</div>
<script type="text/javascript">
var formDefJson=${formDefJson};
var fieldValueList=${fieldValueList};
new FormBuilder(formDefJson).genFormInst(fieldValueList, "${divId}${ caseStep.stepId}",'${caseStep.taskInstId}');
</script>
