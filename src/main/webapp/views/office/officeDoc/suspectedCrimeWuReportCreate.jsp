<script src="${path}/resources/office/LinkObjs.js"></script>
<script src="${path}/resources/office/ntkoControl.js"></script>
<script type="text/javascript">
	$(function() {
			var docType = '${docType}';
			var fileName='${fileName}';
			//初始化webOffice
			intializePage();
			ObjectDisplay(true);
			//插入模版
			insertTemplate(docType);
			//循环插入书签值
 			var docData = ${jsonArray};
			for(var i=0; i < docData.length; i++){
				insertBookMarks(docData[i],docType+"_stat");
  			} 
			//调到word的第一页
			gotoPage(1);
		});
	
</script>
<div class="panel">
		<form id="addForm" action="${path}/office/officeDoc/add" method="post">
			<input type="hidden" value="${fileName}" id="fileName" name="fileName"/>
			<input type="hidden" value="${docType}" id="docType" name="docType"/>
			<table width="100%">
				<caption align="right" style="margin:0px 0px 5px 10px;">
				</caption>
				<tr>
					<td width="100%">
					</td>
				</tr>
			</table>
			<div id="office-div" class="office-div" style="height:550px;">
			 	 <script type="text/javascript" src="${path}/resources/office/ntkoofficecontrol.js"></script>
				<script language="javascript" for="TANGER_OCX" event="OnFileCommand(cmd,canceled)">
					if (cmd == 4) {//监听另存为事件，保存本地时先删除超链接
						TANGER_OCX_OBJ = document.getElementById("TANGER_OCX");
						TANGER_OCX_OBJ.CancelLastCommand=true;	
						deleteLink();
						savedoc();
						}
				</script>
			</div>			
		</form>
</div>

