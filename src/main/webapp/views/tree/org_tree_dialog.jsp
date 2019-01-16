<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>组织机构选择 </title>
	<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
	<link href="${path }/resources/jquery/lg/css/ligerui-all.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/lg/css/web.css" />
	<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"/>
	<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerTree.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerLayout.js" language="JavaScript" type="text/JavaScript"></script>
	<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
 	<script type="text/javascript">
	    var treeNodes = [//顶级组织机构树
	                     {"id": -1, "name": "组织机构", isParent: true}
	                 ]; 	
		var isSingle='${isSingle}';
		var flag='${flag}';
		var districtTree=null; 
		var orgTree=null; 
		var accordion = null;
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
				 onHeightChanged: heightChanged,
				 minLeftWidth:180,
				 allowLeftResize:false
			});
			
			var height = $(".l-layout-center").height();
		    
			load_district_tree();
		    
 		   //heightChanged();
 		   handleSelects();
 		   $("#orgListFrame").attr("src","${path}/system/org/orgTreeList?isSingle=${isSingle }&flag=${flag }");
		});
		
		var firstAsyncSuccessFlag = 0;
		
		function heightChanged(event, treeId, treeNode, msg){
		    var height = $(".l-layout-center").height();
			$("#SEARCH_BY_DISTRICT").height(height-143);
			
			var nodes = districtTree.getNodes();
			if (nodes.length>0) {
				districtTree.expandNode(nodes[0],true,false);
			}
		}
  
		//行政区划树
		function load_district_tree(){
			districtTree=$('#SEARCH_BY_DISTRICT').zTree({
				isSimpleData:true,
				treeNodeKey : "id",
			    treeNodeParentKey : "upId",
				async: true,
				asyncUrl:"${path}/system/district/loadDistrictTree?searchRank=${searchRank}",
				asyncParam: ["id"],
				callback: {
					click: districtTreeOnClick,
					asyncSuccess: heightChanged
				}
			});
		}
		
		function districtTreeOnClick(event, treeId, treeNode){
			if (treeNode) {
			var url="${path}/system/org/orgTreeList?districtCode="+treeNode.id+"&isSingle="+isSingle+"&flag="+flag;
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
			if(isSingle == 'true'){
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
			
			var obj={orgId:orgIds.join(","),orgName:orgNames.join(",")};
			window.returnValue=obj;
			window.close();
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
	</script>
</head>
<body>
<div class="panel">
	<div id="defLayout">
		<div id="leftMemu" position="left" title="查询维度" style="overflow: auto;width: 100%;">
			<div title="行政区划维度" style="overflow-y:auto;">
				<div id="SEARCH_BY_DISTRICT" class="tree"></div>
			</div>
		</div>
		<div position="center" title="组织机构">
			 <iframe id="orgListFrame" name="orgListFrame" height="100%" width="100%" frameborder="0"></iframe> 
		</div>
		<c:if test="${isSingle eq 'false'}"> 
			
			<div position="right" id="right" title="<a class='link del' onclick='javascript:dellAll();' >清空 </a>"
				style="overflow: auto; overflow-x:hidden;">
		 		<table width="145" id="orgList" class="table-grid" cellpadding="1" cellspacing="1" >
				</table>
			</div>
		 </c:if> 
		<div position="bottom" >
			<div align="center" style="margin-top: 10px;">
			<input type="button" class="btn_small" value="选&nbsp;择" onclick="selectOrg();"/>
			<input type="button" class="btn_small" value="取&nbsp;消" onclick="window.close();"/>
			</div>
		</div>
	</div>
</div>
</body>
</html>


