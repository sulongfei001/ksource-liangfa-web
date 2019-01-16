<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>参与人员择器 </title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/lg/css/web.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerLayout.js" language="JavaScript" type="text/JavaScript"></script>
 <script type="text/javascript">
		var orgZTree;
		$(function(){
			//布局
			$("#defLayout").ligerLayout({
				 leftWidth: 180,
				 rightWidth: 160,
				 bottomHeight :40,
				 height: '100%',
				 allowBottomResize:false,
				 allowLeftCollapse:false,
				 allowRightCollapse:false,
				 minLeftWidth:180,
				 allowLeftResize:false
			});
			
			var height = $(".l-layout-center").height();
		    
			//初始化组织机构树
			var setting = {
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "upId"
						}
					},
					async: {
						enable: true,
						url: "${path}/system/org/loadParticipantsOrg",
						autoParam: ["id"],
						otherParam: ["orgType", "-1"]//此处传值-1到后台sql中判断查询不是行政单位类型的组织机构
					},
					callback: {
						onClick: districtTreeOnClick
					}
				};
			orgZTree = $.fn.zTree.init($("#orgFrame"),setting);
			
 		   $("#participantsListFrame").attr("src","${path}/system/user/participantsList");
		});
		
		function heightChanged(){
		    var height = $(".l-layout-center").height();
			$("#orgFrame").height(height - 43);
		}
		
		function districtTreeOnClick(event, treeId, treeNode){
			if (treeNode) {
			var url="${path}/system/user/participantsList?orgCode="+treeNode.id;
			$("#participantsListFrame").attr("src",url);
			}
		}
		
		function add(data) {
			var aryTmp=data.split("#");
			var userId=aryTmp[0];
			var len= $("#userId" + userId).length;
			if(len>0) return;
			
			var aryData=['<tr id="userId'+userId+'">',
				'<td>',
				'<input type="hidden" class="pk" name="participantsData" value="'+data +'"> ',
				aryTmp[1],
				'</td>',
				'<td><a class="link del" onclick="javascript:del(this); " ></a> </td>',
				'</tr>'];
			$("#participantsList").append(aryData.join(""));
		};
		
		function selectMulti(obj) {
			if ($(obj).attr("checked") == "checked"){
				var data = $(obj).val();
				add(data);
			}	
		};
	
		function selectAll(obj) {
			var state = $(obj).attr("checked");
			var rtn=state == undefined?false:true;
			checkAll(rtn);
		};
		
		function checkAll(checked) {
			$("#participantsListFrame").contents().find("input[type='checkbox'][class='pk']").each(function() {
				$(this).attr("checked", checked);
				if (checked) {
					var data = $(this).val();
					add(data);
				}
			});
		};	
		
		function dellAll() {
			$("#participantsList").empty();
		};
		function del(obj) {
			var tr = $(obj).parents("tr");
			$(tr).remove();
		};		
		function selectparticipants(){
			var chIds;
			chIds = $("#participantsList").find(":input[name='participantsData']");
			
			var userIds=new Array();
			var userNames=new Array();
			$.each(chIds,function(i,ch){
				var aryTmp=$(ch).val().split("#");
				if(aryTmp[0] != '') {
					userIds.push(aryTmp[0]);
					userNames.push(aryTmp[1]);
				}
			});
			
			var obj={userId:userIds.join(","),userName:userNames.join(",")};
			window.returnValue=obj;
			window.close();
		}
		
	</script>
<style type="text/css">
#select{
	margin-left:300px;
	}
</style>
</head>
<body>
<div class="panel">
	<div id="defLayout">
		<div id="leftMemu" position="left" title="查询维度" style="overflow: auto; float: left;width: 100%;">
			<div title="组织机构维度" style="overflow-y:auto;">
				<div id="orgFrame" class="ztree"></div>
			</div>
		</div>
		<div position="center" title="参与人员列表">
			 <iframe id="participantsListFrame" name="participantsListFrame" height="100%" width="100%" frameborder="0"></iframe> 
		</div>
		<div position="right" id="right" title="<a class='link del' onclick='javascript:dellAll();' >清空 </a>"
			style="overflow: auto; overflow-x:hidden; height: 420px;">
		 	<table width="145" id="participantsList" class="table-grid" cellpadding="1" cellspacing="1" >
			</table>
		</div>
		<div position="bottom" >
			<div id="select">
			<input type="button" class="btn_small" value="选&nbsp;择" onclick="selectparticipants();"/>
			<input type="button" class="btn_small" value="取&nbsp;消" onclick="window.close();"/>
			</div>
		</div>
	</div>
</div>
</body>
</html>