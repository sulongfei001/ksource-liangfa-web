;
/**
 * 调用个人库，回填人员信息
 * @param contextPath
 * @param cSelector
 * @param options
 */
function callPeopleLib(contextPath,cSelector,options){
	var settings={
			idsNo:"idsNo",
			name:"name",
			sex:"sex",
			education:"education",
			nation:"nation",
			profession:"profession",
			tel:"tel",
			birthday:"birthday",
			birthplace:"birthplace",
			address:"address",
			specialIdentity:"specialIdentity",
			residence:"residence"
			
	};
	jQuery.extend(settings, options);
	var my$Table=jQuery(cSelector).parents("table");
	var idsNo = my$Table.find("input[name='"+settings.idsNo+"']").val();
	if(!idsNo){
		$.ligerDialog.alert("请输入身份证号码！");return;
	}
	jQuery.getJSON(contextPath+"/system/peopleLib/"+idsNo,function(peopleLibJson){
		var peopleLib = JSON.parse(peopleLibJson);    //转换为json对象
		if(peopleLib && peopleLib.idsNo){
			my$Table.find(":input[name='"+settings.name+"']").val(peopleLib.name);
			my$Table.find(":input[name='"+settings.sex+"']").val(peopleLib.sex);
			my$Table.find(":input[name='"+settings.education+"']").val(peopleLib.education);
			my$Table.find(":input[name='"+settings.nation+"']").val(peopleLib.nation);
			my$Table.find(":input[name='"+settings.profession+"']").val(peopleLib.profession);
			my$Table.find(":input[name='"+settings.tel+"']").val(peopleLib.tel);
			my$Table.find(":input[name='"+settings.birthday+"']").val(getyyyyMMddStr(peopleLib.birthday));
			my$Table.find(":input[name='"+settings.birthplace+"']").val(peopleLib.birthplace);
			my$Table.find(":input[name='"+settings.address+"']").val(peopleLib.address);
			my$Table.find(":input[name='"+settings.specialIdentity+"'] option[value='"+peopleLib.specialIdentity+"']").attr("selected","selected");
			my$Table.find(":input[name='"+settings.residence+"']").val(peopleLib.residence);
		}else{
			$.ligerDialog.alert("人口库里暂无对应人员信息，请自行录入！");
			//清空数据
			my$Table.find(":input[name='"+settings.name+"']").val('');
			my$Table.find(":input[name='"+settings.sex+"']").val('');
			my$Table.find(":input[name='"+settings.education+"']").val('');
			my$Table.find(":input[name='"+settings.nation+"']").val('');
			my$Table.find(":input[name='"+settings.profession+"']").val('');
			my$Table.find(":input[name='"+settings.tel+"']").val('');
			my$Table.find(":input[name='"+settings.birthday+"']").val('');
			my$Table.find(":input[name='"+settings.birthplace+"']").val('');
			my$Table.find(":input[name='"+settings.address+"']").val('');
			my$Table.find(":input[name='"+settings.specialIdentity+"']").val('');
			my$Table.find(":input[name='"+settings.residence+"']").val('');
		}
	});
};

/**
 * 调用企业库，回填企业信息
 * @param contextPath
 * @param cSelector
 * @param options
 */
function callCompanyLib(contextPath,cSelector,options){
	var settings={
			registractionNum:"registractionNum",
			name:"name",
			proxy:"proxy",
			address:"address",
			registractionCapital:"registractionCapital",
			companyType:"companyType",
			registractionTime:"registractionTime",
			tel:"tel"
	};
	jQuery.extend(settings, options); 
	var my$CompanyTable=jQuery(cSelector).parents("table");
	var registractionNum = my$CompanyTable.find("input[name='"+settings.registractionNum+"']").val();
	if(!registractionNum){
		$.ligerDialog.alert("请输入统一社会信用代码！");return;
	}
	jQuery.getJSON(contextPath+"/system/companyLib/"+registractionNum,function(companyLibJson){
		var companyLib = JSON.parse(companyLibJson);    //转换为json对象
		if(companyLib && companyLib.registractionNum){
			my$CompanyTable.find(":input[name='"+settings.name+"']").val(companyLib.name);
			my$CompanyTable.find(":input[name='"+settings.proxy+"']").val(companyLib.proxy);
			my$CompanyTable.find(":input[name='"+settings.address+"']").val(companyLib.address);
			my$CompanyTable.find(":input[name='"+settings.registractionCapital+"']").val(companyLib.registractionCapital);
			my$CompanyTable.find(":input[name='"+settings.companyType+"']").val(companyLib.companyType);
			my$CompanyTable.find(":input[name='"+settings.tel+"']").val(companyLib.tel);
			my$CompanyTable.find(":input[name='"+settings.registractionTime+"']").val(getyyyyMMddStr(companyLib.registractionTime));
		}else{
			$.ligerDialog.alert("企业库里暂无对应企业信息，请自行录入！");
			//清空数据
			my$CompanyTable.find(":input[name='"+settings.name+"']").val('');
			my$CompanyTable.find(":input[name='"+settings.proxy+"']").val('');
			my$CompanyTable.find(":input[name='"+settings.address+"']").val('');
			my$CompanyTable.find(":input[name='"+settings.registractionCapital+"']").val('');
			my$CompanyTable.find(":input[name='"+settings.companyType+"']").val('');
			my$CompanyTable.find(":input[name='"+settings.tel+"']").val('');
			my$CompanyTable.find(":input[name='"+settings.registractionTime+"']").val('');
		}
	});
};

function getyyyyMMddStr(milliseconds){
	if(!milliseconds){
		return "";
	}
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

/**
 * 显示异常案件当事人的历史案件
 * @param caseId	案件id
 * @param idsNo	当事人身份证号
 * @param casePartyName	当事姓名
 */

function showCasePartyHistoryCase(contextPath,caseId,idsNo,casePartyName){
	var width = document.documentElement.clientWidth  * 0.7;
	var height =  document.documentElement.clientHeight * 0.7;
	$.ligerDialog.open({
		        url:contextPath+"/casehandle/case/queryHistoryCaseBySameOrgAndCaseParty?caseId="+caseId+"&idsNo="+idsNo,
				id:"showCasePartyHistoryCase",
				title:'当事人历史案件：'+casePartyName+'('+idsNo+')',
				isResize:false,
				width:width,
				height:height
			});
}

/**
 * 显示异常案件涉案企业的历史案件
 * @param caseId	案件id
 * @param regNo	涉案企业税务登记号
 * @param caseCompanyName	涉案企业名称
 */
function showCaseCompanyHistoryCase(contextPath,caseId,regNo,caseCompanyName){
	var width = document.documentElement.clientWidth  * 0.7;
	var height =  document.documentElement.clientHeight * 0.7;		
	$.ligerDialog.open({
		        url:contextPath+"/casehandle/case/queryHistoryCaseBySameOrgAndCaseCompany?caseId="+caseId+"&regNo="+regNo,
				id:"showCasePartyHistoryCase",
				title:'涉案企业历史案件：'+caseCompanyName+'('+regNo+')',
				resize:false,
				width:width,
				height:height
			});
}
;