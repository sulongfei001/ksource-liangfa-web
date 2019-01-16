<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>


<!-- 规范：操作容器tiqingdaibuxianyirenC，有class=‘xianyiren_c’标示、内含前置处理方法tiqingdaibuxianyirenBefore、-->
<div id="tiqingdaibuxianyirenC" class="xianyiren_c">
	<script type="text/javascript">
	//显示添加提请逮捕操作窗口
	var tiqingdaibuDialog=null;
	
     //特殊的身份证验证(你输入0也可以通过验证)
     	jQuery.validator.addMethod("hasIDCard", function(value, element) {
          return (value==0)||(this.optional(element) || checkIDCard(value));
        }, "请填写正确的身份证号");

	//添加提请逮捕完后展示
	function showAddedDaibuXianyiren(xianyiren){
		var tiqingdaibuZm="";
		$.each(xianyiren.accuseInfoList,function(i,accuseInfo){
			tiqingdaibuZm+=accuseInfo.name+'('+accuseInfo.clause+')&nbsp;&nbsp;';
			if(i<xianyiren.accuseInfoList.length-1){tiqingdaibuZm+='，';}
		});
		var temp=[
			'<table class="blues" id="daibuXianyirenTab',xianyiren.xianyirenId,'" style="margin: 10px;width:710px; float: left;">',
			'<tr><td colspan="6" style="text-align:right;"><a href="javascript:;" onclick="updateDaibuXianyiren(\'',xianyiren.xianyirenId,'\');">修改</a>&nbsp;&nbsp;<a href="javascript:;" onclick="deleteDaibuXianyiren(\'',xianyiren.xianyirenId,'\');">删除</a></td></tr>',
			'<tr>',
				'<td style="text-align: right;background-color: #D1DDF1;width: 135px;">身份证号：</td>',
				'<td><span name="idsNo">',xianyiren.idsNo ,'</span></td>',
				'<td style="text-align: right;background-color: #D1DDF1;">姓名：</td>',
				'<td>',
					xianyiren.name ,'</td>',
				'<td style="text-align: right;background-color: #D1DDF1;">性别：</td>',
				'<td>',xianyiren.sex,'</td>',
			'</tr>',
			'<tr>',
				'<td style="text-align: right;background-color: #D1DDF1;">民族：</td>',
				'<td>',xianyiren.nation ,'</td>',
				'<td style="text-align: right;background-color: #D1DDF1;">出生日期：</td>',
				'<td>',xianyiren.birthday,'</td>',
				'<td style="text-align: right;background-color: #D1DDF1;">文化程度：</td>',
				'<td>',xianyiren.education ,'</td>',
			'</tr>',
			'<tr>',
			'<td style="text-align: right;background-color: #D1DDF1;">工作单位和职务：</td>',
			'<td >',xianyiren.profession ,'</td>',
			'<td style="text-align: right;background-color: #D1DDF1;">联系电话：</td>',
			'<td>',xianyiren.tel,'</td>',
			'<td style="text-align: right;background-color: #D1DDF1;">特殊身份：</td>',
			'<td>',xianyiren.specialIdentity,'</td>',
			'</tr>',
			'<tr>',
			'<td style="text-align: right;background-color: #D1DDF1;">籍贯：</td>',
			'<td>',xianyiren.birthplace ,'</td>',
			'<td style="text-align: right;background-color: #D1DDF1;">户籍地：</td>',
			'<td>',xianyiren.residence ,'</td>',
			'<td style="text-align: right;background-color: #D1DDF1;">现住址：</td>',
			'<td>',xianyiren.address ,'</td>',			
			'</tr>',
			'<tr><td style="text-align: right;background-color: #D1DDF1;">提请逮捕罪名：</td><td colspan="5">',tiqingdaibuZm ,'</td></tr>',
			'</table>'
		];
		$('#daibuXianyirenListC').append(temp.join(''));
	}

	function getyyyyMMddStr(milliseconds){
		if(!milliseconds){return "";}
		var date = new Date();
		date.setTime(milliseconds);
		var yyyy=date.getFullYear().toString();
		var MM = (date.getMonth()+1).toString();
		if(MM.length==1){
			MM='0'+MM;
		}
		var dd = date.getDate().toString();
		if(dd.length==1){
			dd='0'+dd;
		}
		return yyyy+'-'+MM+'-'+dd;
	}
	
	//填写移送起诉表单的字段值
	function writeTiqingdaibuXianyirenForm(xianyiren){
		if(xianyiren.xianyirenId==undefined){xianyiren.xianyirenId='';}
		$('#tiqingdaibuAddForm input[name="xianyirenId"]').val(xianyiren.xianyirenId);
		if(xianyiren.name==undefined){xianyiren.name='';}
		$('#tiqingdaibuAddForm input[name="name"]').val(xianyiren.name);
		if(xianyiren.sex==undefined){xianyiren.sex='';}
		$('#tiqingdaibuAddForm :input[name="sex"]').val(xianyiren.sex);
		if(xianyiren.nation==undefined){xianyiren.nation='';}
		$('#tiqingdaibuAddForm :input[name="nation"]').val(xianyiren.nation);
		if(xianyiren.idsNo==undefined){xianyiren.idsNo='';}
		$('#tiqingdaibuAddForm input[name="idsNo"]').val(xianyiren.idsNo);
		if(xianyiren.birthday==undefined){xianyiren.birthday='';}
		$('#tiqingdaibuAddForm input[name="birthday"]').val(getyyyyMMddStr(xianyiren.birthday));
		if(xianyiren.education==undefined){xianyiren.education='';}
		$('#tiqingdaibuAddForm :input[name="education"]').val(xianyiren.education);
		if(xianyiren.profession==undefined){xianyiren.profession='';}
		$('#tiqingdaibuAddForm input[name="profession"]').val(xianyiren.profession);
		if(xianyiren.tel==undefined){xianyiren.tel='';}
		$('#tiqingdaibuAddForm input[name="tel"]').val(xianyiren.tel);
		if(xianyiren.birthplace==undefined){xianyiren.birthplace='';}
		$('#tiqingdaibuAddForm input[name="birthplace"]').val(xianyiren.birthplace);
		
		if(xianyiren.specialIdentity==undefined){xianyiren.specialIdentity='';}
		$('#tiqingdaibuAddForm :input[name="specialIdentity"]').val(xianyiren.specialIdentity);
		if(xianyiren.residence==undefined){xianyiren.residence='';}
		$('#tiqingdaibuAddForm input[name="residence"]').val(xianyiren.residence);
		if(xianyiren.address==undefined){xianyiren.address='';}
		$('#tiqingdaibuAddForm input[name="address"]').val(xianyiren.address);		
		
		accuseSelector.result(accuseSid_tiqingdaibuZm,xianyiren.accuseInfoList);
	}

	//嫌疑人选择操作，填充表单值
	function chooseNotYetTqingdaibuXianyiren(xianyirenIndex){
		if(xianyirenIndex==undefined ||xianyirenIndex<0 || xianyirenIndex==''){
			writeTiqingdaibuXianyirenForm(new Object());//清空嫌疑人表单值
			//显示or隐藏个人库回调
			$('#callPeopleLibLink_tiqingdaibu').show();
		}else{
			$('#callPeopleLibLink_tiqingdaibu').hide();
			writeTiqingdaibuXianyirenForm(chooseXianyirenList[xianyirenIndex]);
		}
	}
	
	//修改提请逮捕
	function updateDaibuXianyiren(xianyirenId){

         var windowVar = window;
		$('#callPeopleLibLink_tiqingdaibu').hide();
		$.getJSON("${path}/workflow/tiqingdaibu/getXianyirenById", { xianyirenId: xianyirenId }, function(xianyiren){
			writeTiqingdaibuXianyirenForm(xianyiren);
            //用一全局变量　给出标志，验证时不验证本身（add by zxl）
	         windowVar.notValid_daibu=xianyiren.idsNo;
		});
		
		var tiqingdaibuAdd = $("#tiqingdaibuAdd");
		
		if(tiqingdaibuDialog != null){
			tiqingdaibuDialog.show();
			tiqingdaibuDialog.set('title','修改提请逮捕人员信息');
		}else{
			tiqingdaibuDialog = $.ligerDialog.open({target:tiqingdaibuAdd,title:"修改提请逮捕人员信息",id:'tiqingdaibuUpdate',isResize:true,height:400,width:800,
					buttons: [ 
					           { text: '确定', 
					        	   onclick: function (item, dialog) {
					        		   	$('#tiqingdaibuAddForm').submit();//提交添加提请逮捕表单
					   					return false;
					        		   } 
					           }, 
					           { text: '关闭', 
					        	   onclick: function (item, dialog) {
					        		 	//复位tiqingdaibuAddForm
					   		  			 $('#tiqingdaibuAddForm').attr('action','${path }/workflow/tiqingdaibu/addXianyiren');
						   		  		 //删除全局变量
						   	             if(windowVar.notValid_daibu){
						   	                 windowVar.notValid_daibu=null;
						   	             }
					        		   dialog.hide(); 
					        		   } 
					           } ]});
		}
		
	}

	//删除提请逮捕
	function deleteDaibuXianyiren(xianyirenId){
		$.ligerDialog.confirm("确认删除此提请逮捕嫌疑人？",function(oper){
			if(oper){
				$.ajax({
					   url: "${path }/workflow/tiqingdaibu/deleteXianyiren?xianyirenId="+xianyirenId,
					   success: function(data){
						   $('#daibuXianyirenTab'+xianyirenId).remove();
					   }
				}); 
			}
		});
	}
	
	//判断已存在的嫌疑人
	function idExist(){
		var id = $("#idsNoForAdd_tiqingdaibu").val(); 
		$.getJSON("${path }/workflow/tiqingqisu/getNotYetTiqingqisu", { caseId: caseId}, function(xianyirenList){
		$.each(xianyirenList,function(i,xianyiren){
			if(id===xianyiren.idsNo){
				$.ligerDialog.warn("嫌疑人已存在，请直接选择！");
				$("#idsNoForAdd_tiqingdaibu").val(null); 
		//自动填充
			//	$('#callPeopleLibLink_tiqingqisu').hide();
			//	writeTiqingqisuXianyirenForm(chooseXianyirenList[i]);
			}
		});
		});	
	}
	</script>

	<fieldset id="formFieldC" class="fieldset">
		<legend class="legend">提请逮捕名单  [<a href="javascript:void(0);" id="showAddDaibuForm" style="color: blue">添加</a>]</legend>
		<div id="daibuXianyirenListC"></div>
		<c:forEach items="${tiqingdaibuRenList }" var="xianyiren" varStatus="xianyiren_state">
		
		<table class="blues" id="daibuXianyirenTab${xianyiren.xianyirenId }" style="margin: 10px;width:710px; float: left;">
			<tr><td colspan="6" style="text-align:right;">
				<a href="javascript:;"  onclick="updateDaibuXianyiren('${xianyiren.xianyirenId }');">修改</a>
					&nbsp;&nbsp;<a href="javascript:;" onclick="deleteDaibuXianyiren('${xianyiren.xianyirenId }');">删除</a>
			</td></tr>
			<tr>
				<td style="text-align: right;background-color: #D1DDF1;width: 135px;">身份证号：</td>
				<td ><span name="idsNo">${xianyiren.idsNo }</span>
				</td>
				<td style="text-align: right;background-color: #D1DDF1;">姓名：</td>
				<td>${xianyiren.name } 
				</td>
				<td style="text-align: right;background-color: #D1DDF1;">性别：</td>
				<td><dict:getDictionary  var="sex" groupCode="sex" dicCode="${xianyiren.sex }"/>${sex.dtName }</td>
			</tr>
			<tr>
				<td style="text-align: right;background-color: #D1DDF1;">民族：</td>
				<td><dict:getDictionary  var="nation" groupCode="nation" dicCode="${xianyiren.nation }"/>${nation.dtName }</td>
				<td style="text-align: right;background-color: #D1DDF1;">出生日期：</td>
				<td><fmt:formatDate value="${xianyiren.birthday }" pattern="yyyy-MM-dd"/> </td>
				<td style="text-align: right;background-color: #D1DDF1;">文化程度：</td>
				<td><dict:getDictionary  var="education" groupCode="educationLevel" dicCode="${xianyiren.education }"/>${education.dtName }</td>
			</tr>
			<tr>
				<td style="text-align: right;background-color: #D1DDF1;">工作单位和职务：</td><td>${xianyiren.profession }</td>
				<td style="text-align: right;background-color: #D1DDF1;">联系电话：</td><td>${xianyiren.tel }</td>
				<td style="text-align: right;background-color: #D1DDF1;">特殊身份：</td><td><dict:getDictionary  var="specialIdentity" groupCode="specialIdentity" dicCode="${xianyiren.specialIdentity }"/>${specialIdentity.dtName }</td>
			</tr>
			<tr>
				<td style="text-align: right;background-color: #D1DDF1;">籍贯：</td><td>${xianyiren.birthplace }</td>
				<td style="text-align: right;background-color: #D1DDF1;">户籍地：</td><td>${xianyiren.birthplace }</td>
				<td style="text-align: right;background-color: #D1DDF1;">现住址：</td><td>${xianyiren.address }</td>
			</tr>
			
			
			<tr><td style="text-align: right;background-color: #D1DDF1;">涉嫌罪名：</td><td colspan="5">
				<c:forEach items="${xianyiren.accuseInfoList }" var="accuseInfo" varStatus="accuseInfo_state">
					<a id="${stepId }_accuseInfo_${accuseInfo_state.index }" title="${fn:escapeXml(accuseInfo.law)}" >${accuseInfo.name }(${accuseInfo.clause })</a>&nbsp;&nbsp;<c:if test="${!accuseInfo_state.last }">，</c:if>
				</c:forEach>
						<script type="text/javascript">
							$(function () {
								<c:forEach items="${xianyiren.accuseInfoList }" var="accuseInfo" varStatus="accuseInfo_state">
								$("#${stepId }_accuseInfo_${accuseInfo_state.index }").poshytip({
									            className: 'tip-yellowsimple',
									            slide: false,
									            fade: false,
									            alignTo: 'target',
									            alignX: 'right',
									            alignY: 'center',
									            offsetX:10,
									            allowTipHover:true 
									        });
								</c:forEach>
							});
						</script>				
			</td></tr>
		</table>
		</c:forEach>
		
		<!-- 添加提请人form -->
		<div id="tiqingdaibuAdd" style="display: none;">
			<span id="chooseNotYetTiqingdaibu"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<form id="tiqingdaibuAddForm" action="${path }/workflow/tiqingdaibu/addXianyiren" method="post">
				<input type="hidden" name="caseId" />
				<input type="hidden" name="xianyirenId" />
				<table class="blues">
					<tr>
						<td style="text-align: right;background-color: #D1DDF1;width: 135px">身份证号：</td>
						<td><input type="text" name="idsNo" maxlength="18" id="idsNoForAdd_tiqingdaibu" onchange="idExist()"/>
                        &nbsp;<font color="red">*</font>
						<a href="javascript:callPeopleLib('${path }','#tiqingdaibuAddForm');" title="调用人员信息库" id="callPeopleLibLink_tiqingdaibu">调用</a>

                        </td>
						<td style="text-align: right;background-color: #D1DDF1;width: 100px">姓名：</td>
						<td><input type="text" name="name" />&nbsp;<font color="red">*</font></td>
						<td style="text-align: right;background-color: #D1DDF1;width: 100px">性别：</td>
						<td>
							<dict:getDictionary var="sexList" groupCode="sex" />
							<select name="sex" style="width: 155px" id="sexForAdd_tiqingdaibu">
								<option value="">--请选择--</option>
								<c:forEach items="${sexList }" var="sex">
								<option value="${sex.dtCode }">${sex.dtName}</option>
								</c:forEach>
							</select>
                            &nbsp;<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;background-color: #D1DDF1;">民族：</td>
						<td>
					<dict:getDictionary
							var="nationList" groupCode="nation" /> <select
						name="nation" style="width: 155px">
							<option value="">--请选择--</option>
							<c:forEach items="${nationList}" var="domain">
								<option value="${domain.dtCode}">${domain.dtName}</option>
							</c:forEach>
					</select>&nbsp;<font color="red">*</font></td>
						<td style="text-align: right;background-color: #D1DDF1;">出生日期：</td>
						<td><input type="text" name="birthday" id="birthday_tiqingdaibu" readonly="readonly"/> &nbsp;<font color="red">*</font></td>
						<td style="text-align: right;background-color: #D1DDF1;">文化程度：</td>
						<td>
							<dict:getDictionary var="educationList" groupCode="educationLevel"/>
							<select name="education" style="width: 155px">
								<option value="">--请选择--</option>
								<c:forEach items="${educationList }" var="education">
								<option value="${education.dtCode }">${education.dtName}</option>
								</c:forEach>
							</select>
						</td>
						
					</tr>
					<tr>
						<td style="text-align: right;background-color: #D1DDF1;">工作单位和职务：</td><td><input type="text" name="profession"/></td>
                        <td style="text-align: right;background-color: #D1DDF1;">联系电话：</td><td><input type="text" name="tel" /></td>
                        <td style="text-align: right;background-color: #D1DDF1;">特殊身份:</td>
                        <td>
                        	<dict:getDictionary var="specialIdentityList" groupCode="specialIdentity" />
                        	 <select class="select" name="specialIdentity" style="width: 155px">
								<option value="">--请选择--</option>
								<c:forEach items="${specialIdentityList}" var="domain">
									<option value="${domain.dtCode}">${domain.dtName}</option>
								</c:forEach>
							</select>
                        </td>
                    </tr>
					<tr>
					<td style="text-align: right;background-color: #D1DDF1;">籍贯：</td><td><input type="text" name="birthplace" /></td>
					<td style="text-align: right;background-color: #D1DDF1;">户籍地：</td><td><input  type="text" name="residence" /></td> 
					<td style="text-align: right;background-color: #D1DDF1;">现住址：</td><td><input  type="text" name="address" /></td>
					</tr>
					<tr>
						<td style="text-align: right;background-color: #D1DDF1;"><font color="red">*</font>&nbsp;提请逮捕罪名：</td><td colspan="5">
						<input type="hidden" name="tiqingdaibuZm"  id="tiqingdaibuZm"/>
						<div id="tiqingdaibuZmC" style="border:1px solid #999;height: 60px;overflow: auto;width: 680px;"></div>
						<a href="javascript:void(0)" id="tiqingdaibuZmControl">选择罪名</a>
					</td>
					</tr>
                    <!-- <tr><td style="text-align: left;color:red;" colspan="6">身份不明填:0</td></tr> -->
				</table>
			</form>
		</div>
	</fieldset>
	<script type="text/javascript">
	$(function(){
		$("#idsNoForAdd_tiqingdaibu").blur(function(){
			if(checkIDCard($(this).val())){
				//自动填充	
				var bithdayAndSexArrays =  getBithdayAndSexFromIds($(this).val());
				$("#birthday_tiqingdaibu").val(bithdayAndSexArrays[0]);
				$("#sexForAdd_tiqingdaibu option").each(function(){
					if($(this).html()==bithdayAndSexArrays[1]){
						$("#sexForAdd_tiqingdaibu").val($(this).val());
					}
				});
			}
		});
		
		
		$('input[name="caseId"]').val(caseId);
		
		var birthday_tiqingdaibu = document.getElementById('birthday_tiqingdaibu');
		birthday_tiqingdaibu.onfocus = function(){
			var year =(new Date().getFullYear()-30).toString();
		    var date= year+'-%M-01';
			WdatePicker({dateFmt:'yyyy-MM-dd',startDate:date,alwaysUseStartDate:true});
		}	
		
		window.accuseSid_tiqingdaibuZm= accuseSelector.exec({labelC:'#tiqingdaibuZmC',valC:'#tiqingdaibuZm',control:'#tiqingdaibuZmControl'});

		$('#showAddDaibuForm').click(function(){
			$('#callPeopleLibLink_tiqingdaibu').show();
			$('#tiqingdaibuAddForm input[name="xianyirenId"]').val('');
			$.getJSON("${path }/workflow/tiqingdaibu/getNotYetTiqingdaibu", { caseId: caseId}, function(xianyirenList){
				chooseXianyirenList = xianyirenList;
				if(chooseXianyirenList.length){
					var html='选择嫌疑人：<select  onchange="chooseNotYetTqingdaibuXianyiren(this.value);"><option value="">添加新嫌疑人</option>';
					$.each(xianyirenList,function(i,xianyiren){//下拉框value保存"xianyirenId,index"形式
						html+='<option value="'+i+'">'+xianyiren.name+'</option>';
					});
					html+='</select>';
					$('#chooseNotYetTiqingdaibu').html(html);
				}else{$('#chooseNotYetTiqingdaibu').empty();}
			});
			$('#tiqingdaibuAddForm').find(':input').not('input[name="caseId"]').val('');
			accuseSelector.clear(accuseSid_tiqingdaibuZm);
			
			var tiqingdaibuAdd = $("#tiqingdaibuAdd");
			
			if(tiqingdaibuDialog != null){
				tiqingdaibuDialog.show();
				tiqingdaibuDialog.set('title','添加提请逮捕人员信息');
			}else{
	 			tiqingdaibuDialog =  $.ligerDialog.open({
	 				target:tiqingdaibuAdd,
	 				id: 'tiqingdaibuAdd',
	 				title:'添加提请逮捕人员信息',
	 				isHidden:true,
	 				width:800,
	 				height:400,
					buttons:[ 
					          { text: '确定', 
					        	  onclick: function (item, dialog) {
					        		  	$('#tiqingdaibuAddForm').submit();//提交添加提请逮捕表单
									    return false;
					        		  } 
					          },
					          { text: '取消', 
					        	  onclick: function (item, dialog) {
					        		  tiqingdaibuDialog.hide(); 
					        		  } 
					          }]});  
			}

		});
        //添加自定义表单验证规则(在param[1]容器内的name为param[0]的组件的值不能一样)
        if(jQuery.validator && jQuery.Poshytip){
            jQuery.validator.addMethod("isTheOneId_daibu",function(value,element,param){
                  return this.optional(element) ||isTheOneId_daibu(value,element,param);
                }, "同一案件的嫌疑人信息的身份证号不能相同！");
        };
         //判断在同一个案件中当事人的身份证号是否唯一
        function isTheOneId_daibu(value,element,param){
            //如果有相同的返回false
            var isTheOneId =true;
            jQuery('span[name=idsNo]','[id^='+param[0]+']').each(function(i,n){
                if(window.notValid_daibu&&window.notValid_daibu===$(this).html())return false;
                if($.trim($(this).html())===$.trim(value)){
                    isTheOneId=false;
                }
            });
            return isTheOneId;
        }
		//提请逮捕表单验证
		jqueryUtil.formValidate({
				form:"tiqingdaibuAddForm",
				blockUI:false,
				rules:{
					name:{required:true,maxlength:50},
					sex:{required:true},
					nation:{required:true,maxlength:50},
					idsNo:{required:true,hasIDCard:true,isTheOneId_daibu:['daibuXianyirenTab']},
					//birthday:{required:true},
					
/* 					education:{required:true},
					profession:{required:true},
					specialIdentity:{required:true},
					birthplace:{required:true},
					residence:{required:true},
					address:{required:true}, */
					
					tel:{isTel:true}
				},
				messages:{
					name:{required:"姓名不能空！",maxlength:"姓名长度应在1-50字以内！"},
					sex:{required:"性别不能空！"},
					nation:{required:"民族不能空！",maxlength:"民族长度在1-50字以内！"},
					idsNo:{required:"身份证号不能为空！",hasIDCard:"请填写正确的身份证号码！"},
					//birthday:{required:"出生日期不能空！"},
					
/* 					education:{required:"教育程度不能为空！"},
					profession:{required:"工作单位和职务不能为空！"},
					specialIdentity:{required:"特殊身份不能为空！"},
					birthplace:{required:"出生地不能为空！"},
					residence:{required:"户籍地不能为空！"},
					address:{required:"现住址不能为空！"}, */
					
					tel:{isTel:"请正确填写电话或手机号码!"}
				},
				submitHandler:function(form){
					if($('#tiqingdaibuZm').val()==''){
						alert('提请逮捕罪名不能空!');
						return false;
					}
					if($('#birthday_tiqingdaibu').val()==''){
						alert('出生日期不能为空!');
						return false;
					}
					$(form).ajaxSubmit({
						dataType: "json",
						success: function(xianyiren){
							//复位tiqingdaibuAddForm
					  		$('#tiqingdaibuAddForm').attr('action','${path }/workflow/tiqingdaibu/addXianyiren');
							
							$('#daibuXianyirenTab'+xianyiren.xianyirenId).remove();//移出存在的嫌疑人信息（修改提请嫌疑人后操作）
							//添加成功后显示嫌疑人信息到页面
							xianyiren.birthday=getyyyyMMddStr(xianyiren.birthday);
							xianyiren.sex = $('#tiqingdaibuAddForm :input[name="sex"] option[value="'+xianyiren.sex+'"]').html();
							if(xianyiren.education == null || '' == xianyiren.education){
								xianyiren.education='';
							}else{
								xianyiren.education=$('#tiqingdaibuAddForm :input[name="education"] option[value="'+xianyiren.education+'"]').html();
							}
							xianyiren.nation=$('#tiqingdaibuAddForm :input[name="nation"] option[value="'+xianyiren.nation+'"]').html();
							if(xianyiren.specialIdentity == null || '' == xianyiren.specialIdentity){
								xianyiren.specialIdentity = '';
							}else{
								xianyiren.specialIdentity = $("#tiqingdaibuAddForm :input[name='specialIdentity'] option[value='"+xianyiren.specialIdentity+"']").html();
							}
							showAddedDaibuXianyiren(xianyiren);
							if(tiqingdaibuDialog != null){
								tiqingdaibuDialog.hide();
							}
						}
					});
				}
		});
				
		
	});
	
	//提交任务办理前前置处理方法，存在提请逮捕嫌疑人则通过校验，可以继续执行流程
	function tiqingdaibuxianyirenBefore(){
		var flag=false;
		if($('table[id^="daibuXianyirenTab"]').length){
			flag=true;
		}
		if(!flag){
			$.ligerDialog.warn("请填写提请逮捕名单！");
		}
		return flag;
	}
	</script>
</div>