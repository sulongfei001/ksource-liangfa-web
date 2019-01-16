<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>组织机构选择 </title>
	<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
	<link href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/lg/css/web.css" />
	<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
	<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
	<script src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" language="JavaScript" type="text/JavaScript"></script>
	<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
	<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerTree.js" language="JavaScript" type="text/JavaScript"></script>
	<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerLayout.js" language="JavaScript" type="text/JavaScript"></script>
	<script type="text/javascript" src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
 	<script type="text/javascript">
		var setting = {
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "upId",
				},
			},
			view: {
				showIcon: true
			},
			async: {
				enable: true,
				url: "${path}/system/district/loadDistrictTree?searchRank=${searchRank}",
				autoParam: ["id"],
			},
			callback: {
				onClick: districtTreeOnClick,
				asyncSuccess: heightChanged
			}
		};
		var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象),主要是弹框里的页面按钮调用dialog的相关方法
	    var treeNodes = [//顶级组织机构树
	                     {"id": -1, "name": "组织机构", isParent: true}
	                 ]; 	
		var isSingle=${isSingle};
		var districtTree=null; 
		var orgTree=null; 
		var accordion = null;
		$(function(){
			//布局
			$("#defLayout").ligerLayout({
				 leftWidth: 180,
				 rightWidth: 160,
				 bottomHeight :40,
				 width:'100%',
				 height: '100%',
				 allowBottomResize:false,
				 allowLeftCollapse:false,
				 allowRightCollapse:false,
				 onHeightChanged: heightChanged,
				 minLeftWidth:180,
				 allowLeftResize:false
			});
			
			var height = $(".l-layout-center").height();
			districtTree=$.fn.zTree.init($("#SEARCH_BY_DISTRICT"),setting);
	 		handleSelects();
		});
		function heightChanged(){
		    var height = $(".l-layout-center").height();
			$("#SEARCH_BY_DISTRICT").height(height - 43);
		}
		
		function districtTreeOnClick(event, treeId, treeNode){
			if (treeNode) {
			var url="${path}/system/org/findCommunionByDistrictCode?&districtCode="+treeNode.id+"&isSingle="+isSingle+"&flag=0"+"&searchRank=${searchRank}";
			$("#orgListFrame").attr("src",url);
			}
		}
		
		function add(data) {
			var aryTmp=data.split("#");
			var orgCode=aryTmp[0];
			var len= $("#org_" + orgCode).length;
			if(len>0) return;
			
			var aryData=['<tr id="org_'+orgCode+'">',
				'<td>',
				'<input type="hidden" class="pk" name="orgData" value="'+data +'"> ',
				aryTmp[1],
				'</td>',
				'<td><a class="link del" onclick="javascript:del(this); " ></a> </td>',
				'</tr>'];
			$("#orgList").append(aryData.join(""));
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
			$("#orgListFrame").contents().find("input[type='checkbox'][class='pk']").each(function() {
				$(this).attr("checked", checked);
				if (checked) {
					var data = $(this).val();
					add(data);
				}
			});
		};	
		
		function dellAll() {
			$("#orgList").empty();
		};
		function del(obj) {
			var tr = $(obj).parents("tr");
			$(tr).remove();
		};		
		function selectOrg(){
			var chIds;
			if(isSingle==true){
				chIds = $('#orgListFrame').contents().find(":input[name='orgData'][checked]");
			
			}else{
				chIds = $("#orgList").find(":input[name='orgData']");
			}
			
			var orgIds=new Array();
			var orgNames=new Array();
			
			$.each(chIds,function(i,ch){
				var aryTmp=$(ch).val().split("#");
				if(aryTmp[0] != '') {
					orgIds.push(aryTmp[0]);
					orgNames.push(aryTmp[1]);
				}
			});
			
			if (orgIds.length == 0) {
				$.ligerDialog.warn("您没有选择任何参与人，请重新选择 ！");
				return;
			} else {
				$(window.parent.document).find("#jieshouOrg").val(orgIds);
				$(window.parent.document).find("#receiveOrgName").val(orgNames);
				dialog.close();//关闭dialog 
			}
		}
		
		var handleSelects=function(){
			var obj = window.dialogArguments;
			if(obj&&obj.length>0){
				for(var i=0,c;c=obj[i++];){
					var data = c.id+'#'+c.name;
					if(c.name!=undefined&&c.name!="undefined"&&c.name!=null&&c.name!=""){
						add(data);
					}
				}
			}
		}	
		//关闭弹窗
		function closeDialog() {
			dialog.close();//关闭dialog 
		}
	</script>
	<style type="text/css">
		.panel{
		    padding-left: 10px;
		    padding-right: 10px;
		}
	</style>
</head>
<body>
<div class="panel">
	<div id="defLayout">
		<div id="leftMemu" position="left" title="查询维度" style="overflow: auto; float: left;width: 100%;">
			<div title="行政区划维度" style="overflow-y:auto;">
				<div id="SEARCH_BY_DISTRICT" class="ztree"></div>
			</div>
		</div>
		<div position="center" title="组织机构">
			 <iframe id="orgListFrame" name="orgListFrame" height="100%" width="100%" frameborder="0"src="${path}/system/org/findCommunionByDistrictCode?isSingle=${isSingle }&searchRank=${searchRank}" ></iframe> 
		</div>
		<c:if test="${isSingle==false}"> 
			
			<div position="right" id="right" title="<a class='link del' onclick='javascript:dellAll();' >清空 </a>"
				style="overflow: auto; overflow-x:hidden; height: 420px;">
		 		<table width="145" id="orgList" class="table-grid" cellpadding="1" cellspacing="1" >
				</table>
			</div>
		 </c:if> 
		<div position="bottom" >
			<div style="text-align: center;padding-top: 10px;">
			<input type="button" class="btn_small" value="选&nbsp;择" onclick="selectOrg();"/>
			<input type="button" class="btn_small" value="取&nbsp;消" onclick="closeDialog();"/>
			</div>
		</div>
	</div>
</div>
</body>
</html>