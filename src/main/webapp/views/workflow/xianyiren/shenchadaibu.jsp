<%@page import="com.ksource.syscontext.Const" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<div id="tiqingdaibuxianyirenC" class="xianyiren_c">
    <c:forEach items="${tiqingdaibuRenList }" var="xianyiren" varStatus="xianyiren_state">
        <table class="blues" id="shenchadaibuXianyirenTab${xianyiren.xianyirenId }"
               curdaibuState="${xianyiren.daibuState}" style="margin: 10px;width:710px; float: left;">
            <thead>
            <tr>
                <th colspan="6">
                    <div style="float: left;text-align:left; width: 200px;padding:5px; font-size: 13px;">
                        批捕状态：
					<span id="curDaibuStateInfo${xianyiren.xianyirenId }">
					<c:if test="${xianyiren.daibuState==2 }"><span class="label label-info">未审批逮捕</span></c:if>
					<c:if test="${xianyiren.daibuState==3 }"><span class="label label-success">已批准逮捕</span></c:if>
					<c:if test="${xianyiren.daibuState==4 }"><span class="label label-success">不(予)批准逮捕</span></c:if>
					</span>
                    </div>
                    <div style="float: right;text-align:right;padding:5px; width: 200px;">
                        <c:if test="${xianyiren.daibuState==2 }">
                            <span id="shenchadaibuOptSpan${xianyiren.xianyirenId  }"><a href="javascript:;"
                                                                                        onclick="pizhundaibu('${xianyiren.xianyirenId}');">批准逮捕</a></span>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <span id="nodaibuOptSpan${xianyiren.xianyirenId  }"><a href="javascript:;"
                                                                                   onclick="nopizhundaibu('${xianyiren.xianyirenId}');">不(予)批准逮捕</a></span>
                        </c:if>
                        <c:if test="${xianyiren.daibuState==3 }">
                            <span id="shenchadaibuOptSpan${xianyiren.xianyirenId  }" style="display: none;"><a
                                    href="javascript:;"
                                    onclick="pizhundaibu('${xianyiren.xianyirenId}');">批准逮捕</a></span>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <span id="nodaibuOptSpan${xianyiren.xianyirenId  }"><a href="javascript:;"
                                                                                   onclick="nopizhundaibu('${xianyiren.xianyirenId}');">不(予)批准逮捕</a></span> </c:if>
                        <c:if test="${xianyiren.daibuState==4 }">
                            <span id="shenchadaibuOptSpan${xianyiren.xianyirenId  }"><a href="javascript:;"
                                                                                        onclick="pizhundaibu('${xianyiren.xianyirenId}');">批准逮捕</a></span>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <span id="nodaibuOptSpan${xianyiren.xianyirenId  }" style="display: none;"><a
                                    href="javascript:;"
                                    onclick="nopizhundaibu('${xianyiren.xianyirenId}');">不(予)批准逮捕</a></span> </c:if>
                    </div>
                    <c:if test="${xianyiren.daibuState==2 }">
                        <div id="shenchadaibuInfo${xianyiren.xianyirenId }" class="alert alert-success"
                             style="clear: both;font-weight:normal; display: none;"
                             align="left"></div>
                    </c:if>
                    <c:if test="${xianyiren.daibuState==3 }">
                        <div id="shenchadaibuInfo${xianyiren.xianyirenId }" class="alert alert-success"
                             style="clear: both;font-weight:normal; " align="left">
                            <strong>批准逮捕时间：</strong> <fmt:formatDate value="${xianyiren.pizhundaibuTime }" pattern="yyyy-MM-dd"/><br/>
                        </div>
                    </c:if>
                    <c:if test="${xianyiren.daibuState==4 }">
                        <div id="shenchadaibuInfo${xianyiren.xianyirenId }" class="alert alert-success"
                             style="clear: both;font-weight:normal;" align="left">
                            <strong>不(予)批准逮捕时间：</strong> <fmt:formatDate value="${xianyiren.nodaibuTime }" pattern="yyyy-MM-dd"/><br/>
                        </div>
                    </c:if>
                </th>
            </tr>
            </thead>
            <tr>
                <td style="text-align: right;background-color: #D1DDF1;width: 135px;">身份证号：</td>
                <td>${xianyiren.idsNo }</td>
                <td style="text-align: right;background-color: #D1DDF1;">姓名：</td>
                <td>${xianyiren.name }</td>
                <td style="text-align: right;background-color: #D1DDF1;">性别：</td>
                <td><dict:getDictionary var="sex" groupCode="sex" dicCode="${xianyiren.sex }"/>${sex.dtName }</td>
            </tr>
            <tr>
                <td style="text-align: right;background-color: #D1DDF1;">民族：</td>
                <td><dict:getDictionary var="nation" groupCode="nation"
                                        dicCode="${xianyiren.nation }"/>${nation.dtName }</td>
                <td style="text-align: right;background-color: #D1DDF1;">出生日期：</td>
                <td><fmt:formatDate value="${xianyiren.birthday }" pattern="yyyy-MM-dd"/></td>
                <td style="text-align: right;background-color: #D1DDF1;">文化程度：</td>
                <td><dict:getDictionary var="education" groupCode="educationLevel"
                                        dicCode="${xianyiren.education }"/>${education.dtName }</td>
            </tr>
        <tr>
            <td style="text-align: right;background-color: #D1DDF1;">工作单位和职务：</td>
            <td>${xianyiren.profession }</td>
            <td style="text-align: right;background-color: #D1DDF1;">联系电话：</td>
            <td>${xianyiren.tel }</td>
            <td style="text-align: right;background-color: #D1DDF1;">特殊身份：</td>
            <td>
				<dict:getDictionary var="specialIdentityVar" groupCode="specialIdentity" dicCode="${xianyiren.specialIdentity }" /> 
				${specialIdentityVar.dtName}            
			</td>
        </tr>
        <tr>
            <td style="text-align: right;background-color: #D1DDF1;">籍贯：</td>
            <td >${xianyiren.birthplace }</td>
            <td style="text-align: right;background-color: #D1DDF1;">户籍地：</td>
            <td >${xianyiren.residence }</td>  
            <td style="text-align: right;background-color: #D1DDF1;">现住址：</td>
            <td >${xianyiren.address }</td>                         
        </tr>
        </table>
    </c:forEach>

    <!-- 批准逮捕UI -->
    <div id="pizhundaibuUI" style="display: none;">
        <div id="pizhundaibuMsg" style="color: red;"></div>
        <table class="blues" style="width:700px;">
            <tr>
                <td class="tabRight">批准逮捕时间：</td>
                <td><input id="pizhundaibuTime" name="pizhundaibuTime" type="text"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>&nbsp;<font color="red">*必填</font></td>
            </tr>
        </table>
    </div>
    <!-- 不(予)批准逮捕UI -->
    <div id="nopizhundaibuUI" style="display: none;">
        <div id="nopizhundaibuMsg" style="color: red;"></div>
        <table class="blues" style="width:700px;">
            <tr>
                <td class="tabRight" >不(予)批准逮捕时间：</td>
                <td><input id="nodaibuTime" name="nodaibuTime" type="text" 
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>&nbsp;<font color="red">*必填</font></td>
            </tr>
        </table>
    </div>
    <div style="display: none;" id="xianyirenId"></div>

    <script type="text/javascript">
        var shenchadaibuDialog = null;
        var noshenchadaibuDialog = null;
        function pizhundaibu(xianyirenId) {
            $('#pizhundaibuUI').find(':input').val('');
            $('#pizhundaibuMsg').empty();
            
            $("#xianyirenId").html(xianyirenId);
            
            if(shenchadaibuDialog != null){
            	shenchadaibuDialog. recover();
            	shenchadaibuDialog.show();
            }else{
	            shenchadaibuDialog = $.ligerDialog.open({
	            	width:750,
	            	height:110,
	                id: 'pizhundaibuUI',
	                title:'批准逮捕',
	                target:document.getElementById("pizhundaibuUI"),
	                buttons:[
	                         {text:"确定",
	                        	 onclick:function(item,dialog){
	                        		 $('#pizhundaibuMsg').empty();
	                                 var pizhundaibuTime = $('#pizhundaibuTime').val().trim();
	                                 if (pizhundaibuTime == '') {
	                                     $('#pizhundaibuMsg').append('批准逮捕时间不能空！&nbsp;&nbsp;');
	                                     return false;
	                                 }
	                                 var xianyirenId = $("#xianyirenId").html();
	                                 $.ajax({
	                                     dataType:'json',
	                                     url: '${path}/workflow/shenchadaibu/pizhun',
	                                     data: {xianyirenId:xianyirenId, pizhundaibuTime: pizhundaibuTime},
	                                     success: function(data) {
	                                         $('#shenchadaibuXianyirenTab' + xianyirenId).attr('curdaibuState', <%=Const.XIANYIREN_DAIBU_STATE_YES%>);
	                                         $('#shenchadaibuOptSpan' + xianyirenId).hide();
	                                         $('#nodaibuOptSpan' + xianyirenId).show();
	                                         $('#curDaibuStateInfo' + xianyirenId).html('<span class="label label-success">已批准逮捕</span>');
	                                         $('#shenchadaibuInfo' + xianyirenId).html(
	                                                 '<strong>批准逮捕时间：</strong>	' + pizhundaibuTime).show();
			                        		 shenchadaibuDialog.hide();
	                                     }
	                                 });
	                        	 }
	                         },
	                         {text:"关闭",
	                        	 onclick:function(item,dialog){
	                        		 shenchadaibuDialog.hide();
	                        	 }
	                         }]
	            });
            }
            
        }
        function nopizhundaibu(xianyirenId) {
            $('#nopizhundaibuUI').find(':input').val('');
            $('#nopizhundaibuMsg').empty();
            $("#xianyirenId").html(xianyirenId);
            if(noshenchadaibuDialog != null){
            	noshenchadaibuDialog. recover();
            	noshenchadaibuDialog.show();
            }else{
            	noshenchadaibuDialog = $.ligerDialog.open({
	                id: 'nopizhundaibuUI',
	                title:'不(予)批准逮捕',
	                width:750,
	            	height:110,
	                resize:false,
	                target:document.getElementById("nopizhundaibuUI"),
	                buttons:[
	                         {text:"确定",
	                        	 onclick:function(item,dialog){
	                        		 $('#nopizhundaibuMsg').empty();
	                                 var nodaibuTime = $('#nodaibuTime').val().trim();
	                                 if (nodaibuTime == '') {
	                                     $('#nopizhundaibuMsg').append('不(予)批准逮捕时间不能空！&nbsp;&nbsp;');
	                                     return false;
	                                 }
	                                 var xianyirenId = $("#xianyirenId").html();
	                                 $.ajax({
	                                     dataType:'json',
	                                     url: '${path}/workflow/shenchadaibu/nopizhun',
	                                     data: {xianyirenId:xianyirenId,  nodaibuTime: nodaibuTime},
	                                     success: function(data) {
	                                         $('#shenchadaibuXianyirenTab' + xianyirenId).attr('curdaibuState', <%=Const.XIANYIREN_DAIBU_STATE_NO%>);
	                                         $('#nodaibuOptSpan' + xianyirenId).hide();
	                                         $('#shenchadaibuOptSpan' + xianyirenId).show();
	                                         $('#curDaibuStateInfo' + xianyirenId).html('<span class="label label-success">不(予)批准逮捕</span>');
	                                         $('#shenchadaibuInfo' + xianyirenId).html(
	                                                 '<strong>不(予)批准逮捕时间：</strong>	' + nodaibuTime).show();
	                                         noshenchadaibuDialog.hide();
	                                     }
	                                 });
	                        	 }
	                         },
	                         {text:"取消",
	                        	 onclick:function(item,diaolog){
	                        		 noshenchadaibuDialog.hide();
	                        	 }
	                         }]
	            });
        	}
            
        }


        //任务办理完毕提交前的前置校验方法
        function shenchadaibuxianyirenBefore() {
            var flag = false;
            flag = $('table[curdaibuState="<%=Const.XIANYIREN_DAIBU_STATE_TIQING%>"]').length == 0;//全部审查完
            if (!flag) {
                $.ligerDialog.warn('审查逮捕未完成！');
            }
            return flag;
        }
    </script>
</div>