<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/lg/css/web.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerTree.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerLayout.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">

/*  	var parentObj = window.dialogArguments; 
	var curFenpaiTaskId = parentObj[0];
	var caseId = parentObj[2];
	 $("#sa").val(parentObj);
	alert(parentObj[1]); */
//-------------------------------------------------------------------------
		var treeNodes = [//顶级组织机构树
	                     {"id": -1, "name": "组织机构", isParent: true}
	                 ]; 	
		var districtTree=null; 
		var orgTree=null; 
		var accordion = null;
		$(function(){
			//布局
			$("#defLayout").ligerLayout({
				 leftWidth: 200,
				 rightWidth: 200,
				 bottomHeight :40,
				 height: '100%',
				 allowBottomResize:false,
				 allowLeftCollapse:false,
				 allowRightCollapse:false,
				 minLeftWidth:180,
				 allowLeftResize:false
			});
			
			var height = $(".l-layout-center").height();
			//$("#leftMemu").ligerAccordion({ height: height-28, speed: null });
		    //accordion = $("#leftMemu").ligerGetAccordionManager();
		//	load_district_tree();
 		   //heightChanged();
 		//   handleSelects();
		});
		
		/* function heightChanged(){
		    var height = $(".l-layout-center").height();
			$("#SEARCH_BY_DISTRICT").height(height - 43);
		}
		
		//行政区划树
		function load_district_tree(){
			districtTree=$('#SEARCH_BY_DISTRICT').zTree({
				showIcon:true,
				//showLine:false,
				isSimpleData:false,
				treeNodeKey : "id",
			    treeNodeParentKey : "upId",
				async: true,
				asyncUrl:"${path}/workflow/task/getUserTree",
				asyncParam: ["id"],
				callback: {
					click: districtTreeOnClick ,
					asyncSuccess: heightChanged
				}
			});
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
	
		function selectAll1(obj) {
			alert("全选");
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
		}	 */
</script>
</head>
<body style="width:100%;height:100%;">
<div class="panel" style="width:100%;height:100%;">
	<div id="defLayout">
		<div id="leftMemu" position="left" title="查询维度">
		</div>
		<div position="center" title="组织机构">
			 <iframe id="orgListFrame" name="orgListFrame" frameborder="0"src="${path}/system/org/getChildOrg" height="100%" width="100%"></iframe> 
		</div>
		<%-- <c:if test="${isSingle==false}">  --%>
			
		<div position="right" id="right" title="<a class='link del' onclick='javascript:dellAll();' href='javascript:void(0);'>清空 </a>">
	 		<table width="145" id="orgList" class="table-grid" cellpadding="1" cellspacing="1" >
			</table>
		</div>
		<%--  </c:if>  --%>
		<div position="bottom" >
			<div align="center">
			<input type="button" class="btn_small" value="选&nbsp;择" onclick="selectOrg();"/>
			<input type="button" class="btn_small" value="取&nbsp;消" onclick="window.close();"/>
			</div>
		</div>
	</div>
</div>
</body>
</html>