var OFFICE_CONTROL_OBJ;//控件对象
var IsFileOpened;      //控件是否打开文档
var fileType ;
var fileTypeSimple;
var s,Sys = {},
ua = navigator.userAgent.toLowerCase(); 
(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : 
	(s = ua.match(/trident\/([\d.]+)/)) ? Sys.ie9 = s[1] : 
		(s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : 
			(s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : 
				(s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : 
					(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
					
function intializePage(fileUrl,op)
{
	OFFICE_CONTROL_OBJ=document.getElementById("TANGER_OCX");
	//initCustomMenus();
    var info = '';
    OFFICE_CONTROL_OBJ.FileNew = false;
    OFFICE_CONTROL_OBJ.FileClose = false;
    OFFICE_CONTROL_OBJ.FileOpen = true;
    OFFICE_CONTROL_OBJ.FileSave = false;
    OFFICE_CONTROL_OBJ.FileSaveAs = true;
    OFFICE_CONTROL_OBJ.ToolBars = false;
    try{
	    info = "编辑文档";
	    if(fileUrl)
	    {
	        OFFICE_CONTROL_OBJ.BeginOpenFromURL(fileUrl,true,false);
	        setFileOpenedOrClosed(true);
	    }
	    else
	    {
	        OFFICE_CONTROL_OBJ.CreateNew("Word.Document");
	        setFileOpenedOrClosed(true);
	    }
    }
    catch(err){
        var msg='不能使用微软Office软件打开文档！\n\n是否尝试使用金山WPS文字处理软件打开文档？';
        if(typeof(OFFICE_CONTROL_OBJ.BeginOpenFromURL) == 'function') {
            if($.ligerDialog.confirm(msg)){
                if(fileUrl){
                	OFFICE_CONTROL_OBJ.BeginOpenFromURL(fileUrl,true,false,"WPS.Document");
                	setFileOpenedOrClosed(true);
                }else{
                	OFFICE_CONTROL_OBJ.CreateNew("WPS.Document");
                	setFileOpenedOrClosed(true);
                }
            }
        }
    }
    finally{
    }
}
function onPageClose()
{
	if(!OFFICE_CONTROL_OBJ.activeDocument.saved)
	{
		if(confirm( "文档修改过,还没有保存,是否需要保存?"))
		{
			saveFileToUrl();
		}
	}
	window.opener.location.reload();
}
function NTKO_OCX_OpenDoc(fileUrl)
{
	OFFICE_CONTROL_OBJ.BeginOpenFromURL(fileUrl);
}
function setFileOpenedOrClosed(bool)
{
	IsFileOpened = bool;
	fileType = OFFICE_CONTROL_OBJ.DocType ;
}
function trim(str)
{ //删除左右两端的空格
　　return str.replace(/(^\s*)|(\s*$)/g, "");
}
function saveFileToUrl()
{
	var myUrl =document.forms[0].action ;
	var fileName = document.getElementById("fileName").value;
	var result = 0 ;
	if(IsFileOpened){
		switch (OFFICE_CONTROL_OBJ.doctype)
		{
			case 1:
				fileType = "Word.Document";
				break;
			case 2:
				fileType = "Excel.Sheet";
				break;
			case 3:
				fileType = "PowerPoint.Show";
				break;
			case 4:
				fileType = "Visio.Drawing";
				break;
			case 5:
				fileType = "MSProject.Project";
				break;
			case 6:
				fileType = "WPS Doc";
				break;
			case 7:
				fileType = "Kingsoft Sheet";
				break;
			default :
				fileType = "unkownfiletype";
		}
		//myUrl=myUrl+"?docType="+docType+"&caseId="+caseId;
		result = OFFICE_CONTROL_OBJ.saveToURL(myUrl,//提交到的url地址
		"upLoadFile",//文件域的id，类似<input type=file name=upLoadFile 中的name
		"fileType="+fileType,          //与控件一起提交的参数如："p1=a&p2=b&p3=c"
		fileName,    //上传文件的名称，类似<input type=file 的value
		0    //与控件一起提交的表单id，也可以是form的序列号，这里应该是0.
		);
		return result;
	}
}
function saveFileAsHtmlToUrl()
{
	var myUrl = "upLoadHtmlFile.jsp"	;
	var htmlFileName = document.getElementById("fileName").value+".html";
	var result;
	if(IsFileOpened)
	{
		result=OFFICE_CONTROL_OBJ.PublishAsHTMLToURL("upLoadHtmlFile.jsp","uploadHtml","htmlFileName="+htmlFileName,htmlFileName);
		result=trim(result);
		document.getElementById("statusBar").innerHTML="服务器返回信息:"+result;
		alert(result);
		window.close();
	}
}
function saveFileAsPdfToUrl()
{
	var myUrl = "upLoadPdfFile.jsp"	;
	var pdfFileName = document.getElementById("fileName").value+".pdf";
	if(IsFileOpened)
	{
		OFFICE_CONTROL_OBJ.PublishAsPdfToURL(myUrl,"uploadPdf","PdfFileName="+pdfFileName,pdfFileName,"","",true,false);
	}
}
function testFunction()
{
	alert(IsFileOpened);
}
function addServerSecSign()
{
	var signUrl=document.getElementById("secSignFileUrl").options[document.getElementById("secSignFileUrl").selectedIndex].value;
	if(IsFileOpened)
	{
		if(OFFICE_CONTROL_OBJ.doctype==1||OFFICE_CONTROL_OBJ.doctype==2)
		{
			try
			{
			alert("正式版本用户请插入EKEY！\r\n\r\n此为电子印章系统演示功能，请购买正式版本！");
			OFFICE_CONTROL_OBJ.AddSecSignFromURL("ntko",signUrl);}
			catch(error){}
		}
		else
		{alert("不能在该类型文档中使用安全签名印章.");}
	}	
}
function addLocalSecSign()
{
	if(IsFileOpened)
	{
		if(OFFICE_CONTROL_OBJ.doctype==1||OFFICE_CONTROL_OBJ.doctype==2)
		{
			try
			{OFFICE_CONTROL_OBJ.AddSecSignFromLocal("ntko","");}
			catch(error){}
		}
		else
		{alert("不能在该类型文档中使用安全签名印章.");}
	}	
}
function addEkeySecSign()
{
	if(IsFileOpened)
	{
		if(OFFICE_CONTROL_OBJ.doctype==1||OFFICE_CONTROL_OBJ.doctype==2)
		{
			try
			{OFFICE_CONTROL_OBJ.AddSecSignFromEkey("ntko");}
			catch(error){}
		}
		else
		{alert("不能在该类型文档中使用安全签名印章.");}
	}
}
function addHandSecSign()
{
	if(IsFileOpened)
	{
		if(OFFICE_CONTROL_OBJ.doctype==1||OFFICE_CONTROL_OBJ.doctype==2)
		{
			try
			{OFFICE_CONTROL_OBJ.AddSecHandSign("ntko");}
			catch(error){}
		}
		else
		{alert("不能在该类型文档中使用安全签名印章.");}
	}
}

function addServerSign(signUrl)
{
	if(IsFileOpened)
	{
			try
			{
				OFFICE_CONTROL_OBJ.AddSignFromURL("ntko",//印章的用户名
				signUrl,//印章所在服务器相对url
				100,//左边距
				100,//上边距 根据Relative的设定选择不同参照对象
				"ntko",//调用DoCheckSign函数签名印章信息,用来验证印章的字符串
				3,  //Relative,取值1-4。设置左边距和上边距相对以下对象所在的位置 1：光标位置；2：页边距；3：页面距离 4：默认设置栏，段落
				100,//缩放印章,默认100%
				1);   //0印章位于文字下方,1位于上方
				
			}
			catch(error){}
	}		
}

function addLocalSign()
{
	if(IsFileOpened)
	{
			try
			{
				OFFICE_CONTROL_OBJ.AddSignFromLocal("ntko",//印章的用户名
					"",//缺省文件名
					true,//是否提示选择
					100,//左边距
					100,//上边距 根据Relative的设定选择不同参照对象
					"ntko",//调用DoCheckSign函数签名印章信息,用来验证印章的字符串
					3,  //Relative,取值1-4。设置左边距和上边距相对以下对象所在的位置 1：光标位置；2：页边距；3：页面距离 4：默认设置栏，段落
					100,//缩放印章,默认100%
					1);   //0印章位于文字下方,1位于上方
			}
			catch(error){}
	}
}
function addPicFromUrl(picURL)
{
	if(IsFileOpened)
	{
		if(OFFICE_CONTROL_OBJ.doctype==1||OFFICE_CONTROL_OBJ.doctype==2)
		{
			try
			{
				OFFICE_CONTROL_OBJ.AddPicFromURL(picURL,//图片的url地址可以时相对或者绝对地址
				false,//是否浮动,此参数设置为false时,top和left无效
				100,//left 左边距
				100,//top 上边距 根据Relative的设定选择不同参照对象
				1,  //Relative,取值1-4。设置左边距和上边距相对以下对象所在的位置 1：光标位置；2：页边距；3：页面距离 4：默认设置栏，段落
				100,//缩放印章,默认100%
				1);   //0印章位于文字下方,1位于上方
				
			}
			catch(error){}
		}
		else
		{alert("不能在该类型文档中使用安全签名印章.");}
	}		
}
function addPicFromLocal()
{
	if(IsFileOpened)
	{
			try
			{
				OFFICE_CONTROL_OBJ.AddPicFromLocal("",//印章的用户名
					true,//缺省文件名
					false,//是否提示选择
					100,//左边距
					100,//上边距 根据Relative的设定选择不同参照对象
					1,  //Relative,取值1-4。设置左边距和上边距相对以下对象所在的位置 1：光标位置；2：页边距；3：页面距离 4：默认设置栏，段落
					100,//缩放印章,默认100%
					1);   //0印章位于文字下方,1位于上方
			}
			catch(error){}
	}
}

function TANGER_OCX_AddDocHeader(strHeader)
{
	if(!IsFileOpened)
	{return;}
	var i,cNum = 30;
	var lineStr = "";
	try
	{
		for(i=0;i<cNum;i++) lineStr += "_";  //生成下划线
		with(OFFICE_CONTROL_OBJ.ActiveDocument.Application)
		{
			Selection.HomeKey(6,0); // go home
			Selection.TypeText(strHeader);
			Selection.TypeParagraph(); 	//换行
			Selection.TypeText(lineStr);  //插入下划线
			// Selection.InsertSymbol(95,"",true); //插入下划线
			Selection.TypeText("★");
			Selection.TypeText(lineStr);  //插入下划线
			Selection.TypeParagraph();
			//Selection.MoveUp(5, 2, 1); //上移两行，且按住Shift键，相当于选择两行
			Selection.HomeKey(6,1);  //选择到文件头部所有文本
			Selection.ParagraphFormat.Alignment = 1; //居中对齐
			with(Selection.Font)
			{
				NameFarEast = "宋体";
				Name = "宋体";
				Size = 12;
				Bold = false;
				Italic = false;
				Underline = 0;
				UnderlineColor = 0;
				StrikeThrough = false;
				DoubleStrikeThrough = false;
				Outline = false;
				Emboss = false;
				Shadow = false;
				Hidden = false;
				SmallCaps = false;
				AllCaps = false;
				Color = 255;
				Engrave = false;
				Superscript = false;
				Subscript = false;
				Spacing = 0;
				Scaling = 100;
				Position = 0;
				Kerning = 0;
				Animation = 0;
				DisableCharacterSpaceGrid = false;
				EmphasisMark = 0;
			}
			Selection.MoveDown(5, 3, 0); //下移3行
		}
	}
	catch(err){
		alert("错误：" + err.number + ":" + err.description);
	}
	finally{
	}
}

function insertRedHeadFromUrl(headFileURL)
{
	if(OFFICE_CONTROL_OBJ.doctype!=1)//OFFICE_CONTROL_OBJ.doctype=1为word文档
	{return;}
	OFFICE_CONTROL_OBJ.ActiveDocument.Application.Selection.HomeKey(6,0);//光标移动到文档开头
	OFFICE_CONTROL_OBJ.addtemplatefromurl(headFileURL);//在光标位置插入红头文档
}

function insertTemplate(docType,caseId)
{
	if(OFFICE_CONTROL_OBJ.doctype!=1)//OFFICE_CONTROL_OBJ.doctype=1为word文档
	{return;}
	OFFICE_CONTROL_OBJ.ActiveDocument.Application.Selection.HomeKey(6,0);//光标移动到文档开头
	var url = top.APP_PATH + "/office/officeTemplate/getByDocType?docType=" + docType+"&caseId="+caseId;
	OFFICE_CONTROL_OBJ.addtemplatefromurl(url);//在光标位置插入红头文档
}

function openTemplateFileFromUrl(templateUrl){
	OFFICE_CONTROL_OBJ.openFromUrl(templateUrl);
}
function doHandSign()
{
	/*if(OFFICE_CONTROL_OBJ.doctype==1||OFFICE_CONTROL_OBJ.doctype==2)//此处设置只允许在word和excel中盖章.doctype=1是"word"文档,doctype=2是"excel"文档
	{*/
		OFFICE_CONTROL_OBJ.DoHandSign2(
									"ntko",//手写签名用户名称
									"ntko",//signkey,DoCheckSign(检查印章函数)需要的验证密钥。
									0,//left
									0,//top
									1,//relative,设定签名位置的参照对象.0：表示按照屏幕位置插入，此时，Left,Top属性不起作用。1：光标位置；2：页边距；3：页面距离 4：默认设置栏，段落（为兼容以前版本默认方式）
									100);
	//}
}
function SetReviewMode(boolvalue)
{
	if(OFFICE_CONTROL_OBJ.doctype==1)
	{
		OFFICE_CONTROL_OBJ.ActiveDocument.TrackRevisions = boolvalue;//设置是否保留痕迹
	}
} 

function setShowRevisions(boolevalue)
{
	if(OFFICE_CONTROL_OBJ.doctype==1)
	{
		OFFICE_CONTROL_OBJ.ActiveDocument.ShowRevisions =boolevalue;//设置是否显示痕迹
	}
}
function setFilePrint(boolvalue)
{
	OFFICE_CONTROL_OBJ.fileprint=boolvalue;//是否允许打印
}
function setFileNew(boolvalue)
{
	OFFICE_CONTROL_OBJ.FileNew=boolvalue;//是否允许新建
}
function setFileSaveAs(boolvalue)
{
	OFFICE_CONTROL_OBJ.FileSaveAs=boolvalue;//是否允许另存为
}

function setIsNoCopy(boolvalue)
{
	OFFICE_CONTROL_OBJ.IsNoCopy=boolvalue;//是否禁止粘贴
}
//验证文档控件自带印章功能盖的章
function DoCheckSign()
{
   if(IsFileOpened)
   {	
			var ret = OFFICE_CONTROL_OBJ.DoCheckSign
			(
			false,/*可选参数 IsSilent 缺省为FAlSE，表示弹出验证对话框,否则，只是返回验证结果到返回值*/
			"ntko"//使用盖章时的signkey,这里为"ntko"
			);//返回值，验证结果字符串
			//alert(ret);
   }	
}
//设置工具栏
function setToolBar()
{
	OFFICE_CONTROL_OBJ.ToolBars=!OFFICE_CONTROL_OBJ.ToolBars;
}
//控制是否显示所有菜单
function setMenubar()
{
		OFFICE_CONTROL_OBJ.Menubar=!OFFICE_CONTROL_OBJ.Menubar;
}
//控制”插入“菜单栏
function setInsertMemu()
{
		OFFICE_CONTROL_OBJ.IsShowInsertMenu=!OFFICE_CONTROL_OBJ.IsShowInsertMenu;
	}
//控制”编辑“菜单栏
function setEditMenu()
{
		OFFICE_CONTROL_OBJ.IsShowEditMenu=!OFFICE_CONTROL_OBJ.IsShowEditMenu;
	}
//控制”工具“菜单栏
function setToolMenu()
{
	OFFICE_CONTROL_OBJ.IsShowToolMenu=!OFFICE_CONTROL_OBJ.IsShowToolMenu;
	}
	
//自定义菜单功能函数
function initCustomMenus()
{
	var myobj = OFFICE_CONTROL_OBJ;	
	for(var menuPos=0;menuPos<3;menuPos++)
	{
		myobj.AddCustomMenu2(menuPos,"菜单"+menuPos+"(&"+menuPos+")"); 
		for(var submenuPos=0;submenuPos<10;submenuPos++)
		{
			if(1 ==(submenuPos % 3)) //主菜单增加分隔符。第3个参数是-1是在主菜单增加
			{
				myobj.AddCustomMenuItem2(menuPos,submenuPos,-1,false,"-",true);
			}
			else if(0 == (submenuPos % 2)) //主菜单增加子菜单，第3个参数是-1是在主菜单增加
			{
				myobj.AddCustomMenuItem2(menuPos,submenuPos,-1,true,"子菜单"+menuPos+"-"+submenuPos,false);
				//增加子菜单项目
				for(var subsubmenuPos=0;subsubmenuPos<9;subsubmenuPos++)
				{
					if(0 == (subsubmenuPos % 2))//增加子菜单项目
					{
						myobj.AddCustomMenuItem2(menuPos,submenuPos,subsubmenuPos,false,
							"子菜单项目"+menuPos+"-"+submenuPos+"-"+subsubmenuPos,false,menuPos*100+submenuPos*20+subsubmenuPos);
					}
					else //增加子菜单分隔
					{
						myobj.AddCustomMenuItem2(menuPos,submenuPos,subsubmenuPos,false,
							"-"+subsubmenuPos,true);
					}
					//测试禁用和启用
					if(2 == (subsubmenuPos % 4))
					{
						myobj.EnableCustomMenuItem2(menuPos,submenuPos,subsubmenuPos,false);
					}
				}				
			}
			else //主菜单增加项目，第3个参数是-1是在主菜单增加
			{
				myobj.AddCustomMenuItem2(menuPos,submenuPos,-1,false,"菜单项目"+menuPos+"-"+submenuPos,false,menuPos*100+submenuPos);
			}
			
			//测试禁用和启用
			if(1 == (submenuPos % 4))
			{
				myobj.EnableCustomMenuItem2(menuPos,submenuPos,-1,false);
			}
		}
	}
}
function insertBookMarks(conf,docType){
	//插入模板
	var BookMarkName = conf.bookMarkName;
	if(!OFFICE_CONTROL_OBJ.ActiveDocument.BookMarks.Exists(BookMarkName)){
		return false;
	}
	var bkmkObj = OFFICE_CONTROL_OBJ.ActiveDocument.BookMarks(BookMarkName);//火狐下要写成Bookmarks.Item(i)这样的形式
	if(!bkmkObj){
		return false;
	}
	var saverange = bkmkObj.Range;
	switch(BookMarkName){
		case "xzcfInputRatioPerRegionList":
			buildXzcfInputRatioPerRegionTable(conf, saverange);
			break;
		case "xzcfInputRatioPerIndustryList":
			buildXzcfInputRatioPerIndustryTable(conf, saverange);
			break;
		case "crimeCaseDealStatisList":
			buildCrimeCaseDealStatisTable(conf, saverange);
			break;
		case "crimeCaseDealStatisByIndustryList":
			buildCrimeCaseDealStatisByIndustryTable(conf, saverange);
			break;
		case "top10AccuseList":
			buildTop10AccuseTable(conf, saverange);
			break;
		case "cmsContentStatisByOrgList":
			buildCmsContentStatisByOrgTable(conf, saverange);
			break;
		case "cmsContentStatisBySubDistrictList":
			cmsContentStatisBySubDistrictTable(conf, saverange);
			break;
		case "instructionStatisByOrgList":
			buildInstructionStatisByOrgTable(conf, saverange);
			break;
		case "caseInputInfoForIndustryList":
			buildXzcfInputRatioPerRegionTable(conf, saverange);//使用综合分析报告（总），行政处罚按区划的表格构建方法
			break;
		case "crimeCaseInfoForIndustryList":
			buildCrimeCaseDealStatisTable(conf, saverange);//使用综合分析报告（总），涉嫌犯罪按区划的表格构建方法
			break;
		case "sxfzCaseList":
			buildSxfzCaseListTable(conf, saverange);
			break;
		case "jgclCaseList":
			buildJgclCaseListTable(conf, saverange);
			break;
		case "dsjfxCaseList":
			buildDsjfxCaseListTable(conf, saverange);
			break;
		case "cbtzCaseList":
			buildCbtzCaseList(conf, saverange);
			break;
		case "sxfzZero":
			bkmkObj.Select();
			OFFICE_CONTROL_OBJ.ActiveDocument.Application.Selection.Delete();
			break;
		case "sxfzNull":
			saverange.Text="未发现符合《刑法》、《刑事案件立案追诉标准的规定》及有关司法解释相关规定应予立案追诉的案件。";
			break;
		case "jgclNull":
			saverange.Text="未发现可能存在以罚代刑降格处理的案件。";
			break;
		case "dsjfxNull":
			saverange.Text="未发现疑似行政执法机关化整为零拆分案件或者行政处罚对象多地区、多行业多头受罚等情形的行政处罚案件。";
			break;
		case "cbtzNull":
			saverange.Text="未发现公安机关尚未处理案件。";
			break;
		case "top10AccuseNull":
			saverange.Text="";
			break;
		case "among":
			saverange.Text="其中重点案件"+conf.bookMarkValue+"件，案号如下：";
			break;
		default:
			if(conf.bookMarkValue != null && typeof(conf.bookMarkValue) != 'undefined'){
				saverange.Text = conf.bookMarkValue;
				OFFICE_CONTROL_OBJ.ActiveDocument.BookMarks.Add(BookMarkName,saverange);
				if(LinkObjs[docType] !=undefined && LinkObjs[docType][BookMarkName] != undefined && conf.bookMarkValue!=0){
					try{
						OFFICE_CONTROL_OBJ.ActiveDocument.Application.Selection.GoTo(-1,0,0,BookMarkName);
						var rang = OFFICE_CONTROL_OBJ.ActiveDocument.Application.Selection.Range;
						OFFICE_CONTROL_OBJ.ActiveDocument.Hyperlinks.Add(rang, LinkObjs[docType][BookMarkName], "", "","");
					}catch(err){
						console.log("错误："+err.number+":"+err.description);
					}
				}
			}
	}
	
	
	
	
/*
	if(BookMarkName.indexOf("filter") >= 0){
		if(OFFICE_CONTROL_OBJ.ActiveDocument.BookMarks.Exists(BookMarkName)){
			OFFICE_CONTROL_OBJ.ActiveDocument.Application.Selection.GoTo(-1,0,0,BookMarkName);
			var rang = OFFICE_CONTROL_OBJ.ActiveDocument.Application.Selection.Range;
			if(LinkObjs.JGCL_Z_1[BookMarkName] != undefined && conf.bookMarkValue!=0){
				OFFICE_CONTROL_OBJ.ActiveDocument.Hyperlinks.Add(rang, LinkObjs.JGCL_Z_1[BookMarkName], "", "", conf.bookMarkValue);
			}else if(LinkObjs.DSJFX_STAT_1[BookMarkName] != undefined && conf.bookMarkValue != 0){
				OFFICE_CONTROL_OBJ.ActiveDocument.Hyperlinks.Add(rang, LinkObjs.DSJFX_STAT_1[BookMarkName], "", "", conf.bookMarkValue);
			}			
		}
	}*/
}

/**
 * 行政处罚录入占比按行政统计
 * @param conf
 * @param saverange
 * @returns
 */
function buildXzcfInputRatioPerRegionTable(conf, saverange) {
	var xzcfInputRatioPerRegionList=conf.bookMarkValue;
	var mtb=OFFICE_CONTROL_OBJ.ActiveDocument.tables.add(saverange,xzcfInputRatioPerRegionList.length+1,3);
	mtb.style="网格型";
	//添加标题
	mtb.cell(1,1).range.Text="行政区划";
	mtb.cell(1,2).range.Text="录入数量";
	mtb.cell(1,3).range.Text="比例";
	for(var i=0;i<xzcfInputRatioPerRegionList.length;i++){
		var tmp=xzcfInputRatioPerRegionList[i];
		mtb.cell(i+2,1).range.Text=tmp.NAME;
		mtb.cell(i+2,2).range.Text=tmp.CNT;
		mtb.cell(i+2,3).range.Text=tmp.RATIO_PER;
	}
	mtb.Range.Font.Size=12;
	mtb.rows(1).Range.Font.Bold = true;
	mtb.Range.Cells.VerticalAlignment =1;
	
}
/**
 * 行政处罚录入占比按行业统计
 * @param conf
 * @param saverange
 * @returns
 */
function buildXzcfInputRatioPerIndustryTable(conf, saverange) {
	var xzcfInputRatioPerIndustryList=conf.bookMarkValue;
	var mtb=OFFICE_CONTROL_OBJ.ActiveDocument.tables.add(saverange,xzcfInputRatioPerIndustryList.length+1,3);
	mtb.style="网格型";
	//添加标题
	mtb.cell(1,1).range.Text="部门";
	mtb.cell(1,2).range.Text="录入数量";
	mtb.cell(1,3).range.Text="比例";
	for(var i=0;i<xzcfInputRatioPerIndustryList.length;i++){
		var tmp=xzcfInputRatioPerIndustryList[i];
		mtb.cell(i+2,1).range.Text=tmp.INDUSTRY_NAME;
		mtb.cell(i+2,2).range.Text=tmp.CNT;
		mtb.cell(i+2,3).range.Text=tmp.RATIO_PER;
	}
	mtb.Range.Font.Size=12;
	mtb.rows(1).Range.Font.Bold = true;
	mtb.Range.Cells.VerticalAlignment =1;
}

/**
 * 涉嫌犯罪案件处理情况按区划统计 
 * @param conf
 * @param saverange
 * @returns
 */
function buildCrimeCaseDealStatisTable(conf, saverange) {
	var list=conf.bookMarkValue;
	var activeDocument = OFFICE_CONTROL_OBJ.ActiveDocument;
	var mtb=activeDocument.tables.add(saverange,list.length+2,15);
	mtb.style="网格型";
	//添加标题
	mtb.cell(1,1).range.Text="行政区划";
	mtb.cell(1,2).range.Text="主动移送";
	mtb.cell(1,4).range.Text="建议移送";
	mtb.cell(1,6).range.Text="公安受理";
	mtb.cell(1,8).range.Text="公安立案";
	mtb.cell(1,10).range.Text="提请逮捕";
	mtb.cell(1,12).range.Text="移送起诉";
	mtb.cell(1,14).range.Text="法院判决";
	mtb.cell(1,14).Merge(mtb.cell(1,15));//合并单元格
	mtb.cell(1,12).Merge(mtb.cell(1,13));
	mtb.cell(1,10).Merge(mtb.cell(1,11));
	mtb.cell(1,8).Merge(mtb.cell(1,9));
	mtb.cell(1,6).Merge(mtb.cell(1,7));
	mtb.cell(1,4).Merge(mtb.cell(1,5));
	mtb.cell(1,2).Merge(mtb.cell(1,3));
	mtb.cell(2,2).range.Text="数量";
	mtb.cell(2,3).range.Text="同比";
	mtb.cell(2,4).range.Text="数量";
	mtb.cell(2,5).range.Text="同比";
	mtb.cell(2,6).range.Text="数量";
	mtb.cell(2,7).range.Text="同比";
	mtb.cell(2,8).range.Text="数量";
	mtb.cell(2,9).range.Text="同比";
	mtb.cell(2,10).range.Text="数量";
	mtb.cell(2,11).range.Text="同比";
	mtb.cell(2,12).range.Text="数量";
	mtb.cell(2,13).range.Text="同比";
	mtb.cell(2,14).range.Text="数量";
	mtb.cell(2,15).range.Text="同比";
	//添加样式
	mtb.Range.Font.Size=12;
	mtb.rows(1).Range.Font.Size=12;
	mtb.rows(1).Range.Font.Bold = true;
	mtb.rows(2).Range.Font.Size=12;
	mtb.rows(2).Range.Font.Bold = true;
	mtb.cell(1,1).Merge(mtb.cell(2,1));//位置不能放在设置宽度下面，否则宽度失效，也不能放在合并样式设置前面，否则样式不能设置
	
	//赋值
	for(var i=0;i<list.length;i++){
		var tmp=list[i];
		mtb.cell(i+3,1).range.Text=tmp.NAME;
		mtb.cell(i+3,2).range.Text=tmp.ZJ_YS_CNT;
		mtb.cell(i+3,3).range.Text=tmp.YOY_ZJ_YS_CNT;
		mtb.cell(i+3,4).range.Text=tmp.JY_YS_CNT;
		mtb.cell(i+3,5).range.Text=tmp.YOY_JY_YS_CNT;
		mtb.cell(i+3,6).range.Text=tmp.ACCEPT_CNT;
		mtb.cell(i+3,7).range.Text=tmp.YOY_ACCEPT_CNT;
		mtb.cell(i+3,8).range.Text=tmp.LIAN_CNT;
		mtb.cell(i+3,9).range.Text=tmp.YOY_LIAN_CNT;
		mtb.cell(i+3,10).range.Text=tmp.TQDB_CNT;
		mtb.cell(i+3,11).range.Text=tmp.YOY_TQDB_CNT;
		mtb.cell(i+3,12).range.Text=tmp.QISU_CNT;
		mtb.cell(i+3,13).range.Text=tmp.YOY_QISU_CNT;
		mtb.cell(i+3,14).range.Text=tmp.PANJUE_CNT;
		mtb.cell(i+3,15).range.Text=tmp.YOY_PANJUE_CNT;
	}
	mtb.Range.Cells.VerticalAlignment =1;
	setTimeout(function(){
		//表格横向展示,连续两个表格进行横向展示
			activeDocument.Range(mtb.Range.Start,mtb.Range.Start).select();
			activeDocument.ActiveWindow.Selection.MoveUp(5,1,0);
			activeDocument.Range(activeDocument.ActiveWindow.Selection.Range.Start,activeDocument.ActiveWindow.Selection.Range.Start).InsertBreak(2);//插入换行符
			activeDocument.Range(mtb.Range.End,mtb.Range.End).InsertBreak(2);//插入换行符
			mtb.Range.PageSetup.Orientation=1;//表格所在页进行横向展示
			mtb.PreferredWidthType=2;//宽度用百分比格式显示
			mtb.PreferredWidth=100;//宽度铺满
			//mtb.LeftPadding=0;
			//mtb.RightPadding=0;
			
	},0);
}

/**
 * 涉嫌犯罪案件处理情况按行业统计 
 * @param conf
 * @param saverange
 * @returns
 */
function buildCrimeCaseDealStatisByIndustryTable(conf, saverange) {
	var list=conf.bookMarkValue;
	if(list.length==0){
		return;
	}
	var mtb=OFFICE_CONTROL_OBJ.ActiveDocument.tables.add(saverange,list.length+2,15);
	mtb.style="网格型";
	//添加标题
	mtb.cell(1,1).range.Text="部门";
	mtb.cell(1,2).range.Text="主动移送";
	mtb.cell(1,4).range.Text="建议移送";
	mtb.cell(1,6).range.Text="公安受理";
	mtb.cell(1,8).range.Text="公安立案";
	mtb.cell(1,10).range.Text="提请逮捕";
	mtb.cell(1,12).range.Text="移送起诉";
	mtb.cell(1,14).range.Text="法院判决";
	mtb.cell(1,14).Merge(mtb.cell(1,15));//合并单元格
	mtb.cell(1,12).Merge(mtb.cell(1,13));
	mtb.cell(1,10).Merge(mtb.cell(1,11));
	mtb.cell(1,8).Merge(mtb.cell(1,9));
	mtb.cell(1,6).Merge(mtb.cell(1,7));
	mtb.cell(1,4).Merge(mtb.cell(1,5));
	mtb.cell(1,2).Merge(mtb.cell(1,3));
	mtb.cell(2,2).range.Text="数量";
	mtb.cell(2,3).range.Text="同比";
	mtb.cell(2,4).range.Text="数量";
	mtb.cell(2,5).range.Text="同比";
	mtb.cell(2,6).range.Text="数量";
	mtb.cell(2,7).range.Text="同比";
	mtb.cell(2,8).range.Text="数量";
	mtb.cell(2,9).range.Text="同比";
	mtb.cell(2,10).range.Text="数量";
	mtb.cell(2,11).range.Text="同比";
	mtb.cell(2,12).range.Text="数量";
	mtb.cell(2,13).range.Text="同比";
	mtb.cell(2,14).range.Text="数量";
	mtb.cell(2,15).range.Text="同比";
	//添加样式
	mtb.Range.Font.Size=12;
	mtb.rows(1).Range.Font.Size=12;
	mtb.rows(1).Range.Font.Bold = true;
	mtb.rows(2).Range.Font.Size=12;
	mtb.rows(2).Range.Font.Bold = true;
	mtb.cell(1,1).Merge(mtb.cell(2,1));//位置不能放在设置宽度下面，否则宽度失效，也不能放在合并样式设置前面，否则样式不能设置
	//赋值
	for(var i=0;i<list.length;i++){
		var tmp=list[i];
		mtb.cell(i+3,1).range.Text=tmp.INDUSTRY_NAME;
		mtb.cell(i+3,2).range.Text=tmp.ZJ_YS_CNT;
		mtb.cell(i+3,3).range.Text=tmp.YOY_ZJ_YS_CNT;
		mtb.cell(i+3,4).range.Text=tmp.JY_YS_CNT;
		mtb.cell(i+3,5).range.Text=tmp.YOY_JY_YS_CNT;
		mtb.cell(i+3,6).range.Text=tmp.ACCEPT_CNT;
		mtb.cell(i+3,7).range.Text=tmp.YOY_ACCEPT_CNT;
		mtb.cell(i+3,8).range.Text=tmp.LIAN_CNT;
		mtb.cell(i+3,9).range.Text=tmp.YOY_LIAN_CNT;
		mtb.cell(i+3,10).range.Text=tmp.TQDB_CNT;
		mtb.cell(i+3,11).range.Text=tmp.YOY_TQDB_CNT;
		mtb.cell(i+3,12).range.Text=tmp.QISU_CNT;
		mtb.cell(i+3,13).range.Text=tmp.YOY_QISU_CNT;
		mtb.cell(i+3,14).range.Text=tmp.PANJUE_CNT;
		mtb.cell(i+3,15).range.Text=tmp.YOY_PANJUE_CNT;
	}
	mtb.Range.Cells.VerticalAlignment =1;
	setTimeout(function(){
		var activeDocument = OFFICE_CONTROL_OBJ.ActiveDocument;
		//表格横向展示,连续两个表格进行横向展示
		activeDocument.Range(mtb.Range.Start,mtb.Range.Start).select();
		activeDocument.ActiveWindow.Selection.MoveUp(5,1,0);//需要从表格上面的那个文本标题开始进行横向展示
		activeDocument.Range(activeDocument.ActiveWindow.Selection.Range.Start,activeDocument.ActiveWindow.Selection.Range.Start).InsertBreak(2);//插入换行符
		activeDocument.Range(mtb.Range.End,mtb.Range.End).InsertBreak(2);//插入换行符
		mtb.Range.PageSetup.Orientation=1;//表格所在页进行横向展示
		mtb.PreferredWidthType=2;//宽度用百分比格式显示
		mtb.PreferredWidth=100;//宽度铺满
		//mtb.LeftPadding=0;
		//mtb.RightPadding=0;
	},0);
}


/**
 * 统计前十名罪名个数
 * @param conf
 * @param saverange
 * @returns
 */
function buildTop10AccuseTable(conf, saverange) {
	var list=conf.bookMarkValue;
	if(list.length==0){
		return;
	}
	var mtb=OFFICE_CONTROL_OBJ.ActiveDocument.tables.add(saverange,list.length+1,3);
	mtb.style="网格型";
	//添加标题
	mtb.cell(1,1).range.Text="序号";
	mtb.cell(1,2).range.Text="罪名";
	mtb.cell(1,3).range.Text="数量";
	for(var i=0;i<list.length;i++){
		var tmp=list[i];
		mtb.cell(i+2,1).range.Text=i+1;
		mtb.cell(i+2,2).range.Text=tmp.ACCUSE_NAME;
		mtb.cell(i+2,3).range.Text=tmp.ACCUSE_NUM;
	}
	mtb.Range.Font.Size=12;
	mtb.rows(1).Range.Font.Bold = true;
	mtb.Range.Cells.VerticalAlignment =1;
	mtb.Columns(1).PreferredWidthType=2;
	mtb.Columns(1).PreferredWidth=8;
}


/**
 * 平台网站信息发布统计按单位
 * @param conf
 * @param saverange
 * @returns
 */
function buildCmsContentStatisByOrgTable(conf, saverange) {
	var list=conf.bookMarkValue;
	if(list.length==0){
		return;
	}
	var mtb=OFFICE_CONTROL_OBJ.ActiveDocument.tables.add(saverange,list.length+1,8);
	mtb.style="网格型";
	//添加标题
	mtb.cell(1,1).range.Text="部门";
	mtb.cell(1,2).range.Text="总共";
	mtb.cell(1,3).range.Text="信息公开";
	mtb.cell(1,4).range.Text="依法行政";
	mtb.cell(1,5).range.Text="执法动态";
	mtb.cell(1,6).range.Text="法律法规";
	mtb.cell(1,7).range.Text="业务研讨";
	mtb.cell(1,8).range.Text="典型案例";
	for(var i=0;i<list.length;i++){
		var tmp=list[i];
		mtb.cell(i+2,1).range.Text=tmp.INDUSTRY_NAME;
		mtb.cell(i+2,2).range.Text=tmp.TOTAL_NUM;
		mtb.cell(i+2,3).range.Text=tmp.XXGK;
		mtb.cell(i+2,4).range.Text=tmp.YFXZ;
		mtb.cell(i+2,5).range.Text=tmp.ZFDT;
		mtb.cell(i+2,6).range.Text=tmp.FLFG;
		mtb.cell(i+2,7).range.Text=tmp.YWYT;
		mtb.cell(i+2,8).range.Text=tmp.DXAL;
	}
	mtb.Range.Font.Size=12;
	mtb.rows(1).Range.Font.Bold = true;
	mtb.Range.Cells.VerticalAlignment =1;
}

/**
 * 平台网站信息发布统计按下级区划
 * @param conf
 * @param saverange
 * @returns
 */
function cmsContentStatisBySubDistrictTable(conf, saverange) {
	var list=conf.bookMarkValue;
	if(list.length==0){
		return;
	}
	var mtb=OFFICE_CONTROL_OBJ.ActiveDocument.tables.add(saverange,list.length+1,8);
	mtb.style="网格型";
	//添加标题
	mtb.cell(1,1).range.Text="行政区划";
	mtb.cell(1,2).range.Text="总共";
	mtb.cell(1,3).range.Text="信息公开";
	mtb.cell(1,4).range.Text="依法行政";
	mtb.cell(1,5).range.Text="执法动态";
	mtb.cell(1,6).range.Text="法律法规";
	mtb.cell(1,7).range.Text="业务研讨";
	mtb.cell(1,8).range.Text="典型案例";
	for(var i=0;i<list.length;i++){
		var tmp=list[i];
		mtb.cell(i+2,1).range.Text=tmp.DISTRICT_NAME;
		mtb.cell(i+2,2).range.Text=tmp.TOTAL_NUM;
		mtb.cell(i+2,3).range.Text=tmp.XXGK;
		mtb.cell(i+2,4).range.Text=tmp.YFXZ;
		mtb.cell(i+2,5).range.Text=tmp.ZFDT;
		mtb.cell(i+2,6).range.Text=tmp.FLFG;
		mtb.cell(i+2,7).range.Text=tmp.YWYT;
		mtb.cell(i+2,8).range.Text=tmp.DXAL;
	}
	mtb.Range.Font.Size=12;
	mtb.rows(1).Range.Font.Bold = true;
	mtb.Range.Cells.VerticalAlignment =1;
}


/**
 * 各院下发指令及办理情况统计
 * @param conf
 * @param saverange
 * @returns
 */
function buildInstructionStatisByOrgTable(conf, saverange) {
	var list=conf.bookMarkValue;
	if(list.length==0){
		return;
	}
	var mtb=OFFICE_CONTROL_OBJ.ActiveDocument.tables.add(saverange,list.length+1,5);
	mtb.style="网格型";
	//添加标题
	mtb.cell(1,1).range.Text="部门";
	mtb.cell(1,2).range.Text="总共";
	mtb.cell(1,3).range.Text="已回复";
	mtb.cell(1,4).range.Text="正在办理";
	mtb.cell(1,5).range.Text="未回复";
	for(var i=0;i<list.length;i++){
		var tmp=list[i];
		mtb.cell(i+2,1).range.Text=tmp.ORG_NAME;
		mtb.cell(i+2,2).range.Text=tmp.SEND_NUM;
		mtb.cell(i+2,3).range.Text=tmp.REPLY_NUM;
		mtb.cell(i+2,4).range.Text=tmp.DO_NUM;
		mtb.cell(i+2,5).range.Text=tmp.NO_REPLY_NUM;
	}
	mtb.Range.Font.Size=12;
	mtb.rows(1).Range.Font.Bold = true;
	mtb.Range.Cells.VerticalAlignment =1;
}

/**
 * 涉嫌犯罪线索筛查报告案件表格
 * @param conf
 * @param saverange
 * @returns
 */
function buildSxfzCaseListTable(conf, saverange) {
	var list=conf.bookMarkValue;
	if(list.length==0){
		return;
	}
	var mtb=OFFICE_CONTROL_OBJ.ActiveDocument.tables.add(saverange,list.length+1,4);
	mtb.style="网格型";
	//添加标题
	mtb.cell(1,1).range.Text="案件编号";
	mtb.cell(1,2).range.Text="案件名称";
	mtb.cell(1,3).range.Text="违法情形";
	mtb.cell(1,4).range.Text="涉嫌罪名";
	for(var i=0;i<list.length;i++){
		var tmp=list[i];
		mtb.cell(i+2,1).range.Text=tmp.CASE_NO;
		mtb.cell(i+2,2).range.Text=tmp.CASE_NAME;
		mtb.cell(i+2,3).range.Text=tmp.ILLEGAL_NAME;
		mtb.cell(i+2,4).range.Text=tmp.ACCUSE_NAME;
	}
	mtb.Range.Font.Size=12;
	mtb.rows(1).Range.Font.Bold = true;
	mtb.Range.Cells.VerticalAlignment =1;
}

/**
 * 降格处理案件线索筛查报告案件表格
 * @param conf
 * @param saverange
 * @returns
 */
function buildJgclCaseListTable(conf, saverange) {
	var list=conf.bookMarkValue;
	if(list.length==0){
		return;
	}
	var mtb=OFFICE_CONTROL_OBJ.ActiveDocument.tables.add(saverange,list.length+1,3);
	mtb.style="网格型";
	//添加标题
	mtb.cell(1,1).range.Text="序号";
	mtb.cell(1,2).range.Text="案件编号";
	mtb.cell(1,3).range.Text="案件名称";
	for(var i=0;i<list.length;i++){
		var tmp=list[i];
		mtb.cell(i+2,1).range.Text=i+1;
		mtb.cell(i+2,2).range.Text=tmp.CASE_NO;
		mtb.cell(i+2,3).range.Text=tmp.CASE_NAME;
	}
	mtb.Range.Font.Size=12;
	mtb.rows(1).Range.Font.Bold = true;
	mtb.Range.Cells.VerticalAlignment =1;
	mtb.Columns(1).PreferredWidthType=2;
	mtb.Columns(1).PreferredWidth=8;
}

/**
 * 大数据分析案件线索筛查报告案件表格
 * @param conf
 * @param saverange
 * @returns
 */
function buildDsjfxCaseListTable(conf, saverange) {
	var list=conf.bookMarkValue;
	if(list.length==0){
		return;
	}
	var mtb=OFFICE_CONTROL_OBJ.ActiveDocument.tables.add(saverange,list.length+1,3);
	mtb.style="网格型";
	//添加标题
	mtb.cell(1,1).range.Text="序号";
	mtb.cell(1,2).range.Text="案件编号";
	mtb.cell(1,3).range.Text="案件名称";
	for(var i=0;i<list.length;i++){
		var tmp=list[i];
		mtb.cell(i+2,1).range.Text=i+1;
		mtb.cell(i+2,2).range.Text=tmp.CASE_NO;
		mtb.cell(i+2,3).range.Text=tmp.CASE_NAME;
	}
	mtb.Range.Font.Size=12;
	mtb.rows(1).Range.Font.Bold = true;
	mtb.Range.Cells.VerticalAlignment =1;
	mtb.Columns(1).PreferredWidthType=2;
	mtb.Columns(1).PreferredWidth=8;
}

/**
 * 催办通知（案件滞留）表格
 * @param conf
 * @param saverange
 * @returns
 */
function buildCbtzCaseList(conf, saverange) {
	var list=conf.bookMarkValue;
	if(list.length==0){
		return;
	}
	var mtb=OFFICE_CONTROL_OBJ.ActiveDocument.tables.add(saverange,list.length+1,3);
	mtb.style="网格型";
	//添加标题
	mtb.cell(1,1).range.Text="序号";
	mtb.cell(1,2).range.Text="案件编号";
	mtb.cell(1,3).range.Text="案件名称";
	for(var i=0;i<list.length;i++){
		var tmp=list[i];
		mtb.cell(i+2,1).range.Text=i+1;
		mtb.cell(i+2,2).range.Text=tmp.CASE_NO;
		mtb.cell(i+2,3).range.Text=tmp.CASE_NAME;
	}
	mtb.Range.Font.Size=12;
	mtb.rows(1).Range.Font.Bold = true;
	mtb.Range.Cells.VerticalAlignment =1;
	mtb.Columns(1).PreferredWidthType=2;
	mtb.Columns(1).PreferredWidth=8;
}

/**打印预览*/
function printDocPreview(){
	try{
		OFFICE_CONTROL_OBJ.PrintPreview();
	}catch(err){
		alert("错误："+err.number+":"+err.description);
	}
}

/**打印当前文档，isBackground 控制后台打印还是前台打印*/
function printDoc(isBackground){
    try
    {
        if (OFFICE_CONTROL_OBJ.ActiveDocument != null)
        	OFFICE_CONTROL_OBJ.printout(true);
    }
    catch(err){
    }
}

function savedoc(){
	try{
		OFFICE_CONTROL_OBJ.ShowDialog(2);
	}catch(err){
	}
}

function moveHomeKeyToHead(){
	try{
		OFFICE_CONTROL_OBJ.ActiveDocument.Application.Selection.HomeKey(6,0);
	}catch(err){
	}		
}


function insertTemplate1(docId)
{
	if(OFFICE_CONTROL_OBJ.doctype!=1)//OFFICE_CONTROL_OBJ.doctype=1为word文档
	{return;}
	OFFICE_CONTROL_OBJ.ActiveDocument.Application.Selection.HomeKey(6,0);//光标移动到文档开头
	var url = top.APP_PATH + "/office/officeDoc/getByDocId?docId=" + docId;
	OFFICE_CONTROL_OBJ.addtemplatefromurl(url);//在光标位置插入红头文档
}
function insertLinks(){
	OFFICE_CONTROL_OBJ.ActiveDocument.Hyperlinks.Add(OFFICE_CONTROL_OBJ.ActiveDocument.Range(1,2),"http://www.baidu.com/","","","下载");
}
function gotoPage(page){
	OFFICE_CONTROL_OBJ.ActiveDocument.Application.Selection.GoTo(1,1,1);
}
function cancelLastCommand(){
	OFFICE_CONTROL_OBJ.CancelLastCommand = true;
}
function ObjectDisplay(a) {
	if(a){
		var height = $(window.parent.document).find(".l-tab-content").height();
		if(height == null || height == undefined || typeof(height) == 'undefined'){
			height = "500px";
		}
	    OFFICE_CONTROL_OBJ.style.height = (height-35)+"px";
	    OFFICE_CONTROL_OBJ.style.width = "100%";
	}else{
	    OFFICE_CONTROL_OBJ.style.height = "1px";
	    OFFICE_CONTROL_OBJ.style.width = "1px";
	}
}
function readBookMarkValueFromDoc(BookMarkName){
	var mkValue = "";
	if(!OFFICE_CONTROL_OBJ.ActiveDocument.BookMarks.Exists(BookMarkName)){
		return mkValue;
	}
	var bkmkObj = OFFICE_CONTROL_OBJ.ActiveDocument.BookMarks(BookMarkName);//火狐下要写成Bookmarks.Item(i)这样的形式
	if(!bkmkObj){
		return mkValue;
	}
	mkValue = bkmkObj.Range.Text;
	return mkValue;
}


/**
 * 添加页码
 */
function insertPageFooter()
{
	/*OFFICE_CONTROL_OBJ.ActiveDocument.ActiveWindow.ActivePane.View.SeekView=10;
	//TANGER_OCX_OBJ.ActiveDocument.Sections(1).Headers(1).Range.Text="Header";
	//TANGER_OCX_OBJ.ActiveDocument.Sections(1).Footers(1).PageNumbers.Add(0,true);
	OFFICE_CONTROL_OBJ.ActiveDocument.Application.selection.TypeText("第");
	OFFICE_CONTROL_OBJ.ActiveDocument.Application.Selection.Fields.Add(OFFICE_CONTROL_OBJ.ActiveDocument.Application.Selection.Range,33);
	OFFICE_CONTROL_OBJ.ActiveDocument.Application.selection.TypeText("页 ");
	OFFICE_CONTROL_OBJ.ActiveDocument.Application.selection.TypeText("共");
	OFFICE_CONTROL_OBJ.ActiveDocument.Application.Selection.Fields.Add(OFFICE_CONTROL_OBJ.ActiveDocument.Application.Selection.Range,26);
	OFFICE_CONTROL_OBJ.ActiveDocument.Application.selection.TypeText("页");
	OFFICE_CONTROL_OBJ.ActiveDocument.ActiveWindow.ActivePane.View.SeekView = 0;*/
	//添加页码
	OFFICE_CONTROL_OBJ.ActiveDocument.Sections(1).Footers(1).PageNumbers.Add(1,true);
	//执行上面的方法后，会出现页眉有横线的情况，需要手动消失横线
	OFFICE_CONTROL_OBJ.ActiveDocument.ActiveWindow.ActivePane.View.SeekView=9;
	OFFICE_CONTROL_OBJ.ActiveDocument.Sections(1).Headers(1).Range.Borders(-3).LineStyle=0;
	OFFICE_CONTROL_OBJ.ActiveDocument.ActiveWindow.ActivePane.View.SeekView=0;
}
function deleteLink(){
var docObj = OFFICE_CONTROL_OBJ.ActiveDocument;
var count = docObj.Hyperlinks.count;
	for(var i=count;i>0;i--){
		docObj.Hyperlinks.Item(i).Delete();
	}
}