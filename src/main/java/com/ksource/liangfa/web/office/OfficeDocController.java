package com.ksource.liangfa.web.office;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UpLoadUtil;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.upload.bean.UploadBean;
import com.ksource.common.util.FileUtil;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseFilter;
import com.ksource.liangfa.domain.CaseFilterExample;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.IndustryInfo;
import com.ksource.liangfa.domain.OfficeDoc;
import com.ksource.liangfa.domain.OfficeTemplate;
import com.ksource.liangfa.domain.OfficeTemplateExample;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.stat.StatisData;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseFilterMapper;
import com.ksource.liangfa.mapper.OfficeTemplateMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.StatisDataService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.cms.CmsContentService;
import com.ksource.liangfa.service.instruction.InstructionReceiveService;
import com.ksource.liangfa.service.office.OfficeDocService;
import com.ksource.liangfa.service.office.OfficeIdentityService;
import com.ksource.liangfa.service.office.OfficeTemplateService;
import com.ksource.liangfa.service.system.DistrictService;
import com.ksource.liangfa.service.system.IndustryInfoService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/office/officeDoc")
public class OfficeDocController {
	
	@Autowired
	private OfficeDocService officeDocService;
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private PublishInfoFileMapper publishInfoFileMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private DistrictService districtService;
	@Autowired
	private CaseService caseService;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private OfficeIdentityService officeIdentityService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private OfficeTemplateService officeTemplateService;
	@Autowired
	private OfficeTemplateMapper officeTemplateMapper;
	@Autowired
	private IndustryInfoService industryInfoService;
	@Autowired
	private CmsContentService cmsContentService;
	@Autowired
	private StatisDataService statisDataService;
	@Autowired
	private InstructionReceiveService instructionReceiveService;
	@Autowired
	private CaseBasicMapper caseBasicMapper;
	
	/**
	 * 综合分析报告（总）引导页面
	 * author XT
	 * @return
	 */
	@RequestMapping(value ="zhfx1_report_guide_business")
	public ModelAndView zhfx1ReportGuideBusiness(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		ModelAndView modelAndView=new ModelAndView("office/zhfx_report_guide_business");
		User user = SystemContext.getCurrentUser(request);
		Organise organise = user.getOrganise();
		String currentDistrictCode = organise.getDistrictCode();
		District currentDistrict = districtService.selectByPrimaryKey(currentDistrictCode);
		return modelAndView.addObject("currentDistrict", currentDistrict);
	}
	
	
	/**
	 * 综合分析报告（总）引导页面
	 * author XT
	 * @return
	 */
	@RequestMapping(value ="zhfx2_report_guide_business")
	public ModelAndView zhfx2ReportGuideBusiness(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		//获取行业类型信息
		List<IndustryInfo> industryInfoList=industryInfoService.selectAll() ;
		ModelAndView modelAndView=new ModelAndView("office/zhfx2_report_guide_business");
		modelAndView.addObject("industryInfoList", industryInfoList);
		return modelAndView;
	}
	
	@RequestMapping(value = "list")
	public ModelAndView search(HttpServletRequest request,String page,OfficeDoc officeDoc) throws Exception {
		ModelAndView view = new ModelAndView("office/officeDoc/officeDocList");
		PaginationHelper<OfficeDoc> officeDocHelper = officeDocService.find(officeDoc,page,null);
		view.addObject("officeDocHelper", officeDocHelper);
		view.addObject("page", page);
		view.addObject("officeDoc", officeDoc);
		return view;
	}
	
	@RequestMapping(value = "docCreate")
	public ModelAndView docCreate(String docType,String taskId,String inputer,String caseId,HttpServletRequest request){
		ModelAndView view = new ModelAndView("office/officeDoc/docCreate");
		view.addObject("docType", docType);
		JSONArray jsonArray = new JSONArray();
		//查询fileName
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("docType", docType);
		PublishInfoFile p=publishInfoFileMapper.getByDocType(param);
		view.addObject("fileName", p.getFileName());
		//查询caseId
		Map<String, Object> pa=new HashMap<String, Object>();
		
		//建议移送传过来参数caseId，可以直接使用，避免查询
		OfficeDoc officeDoc =new OfficeDoc();
		if(StringUtils.isNotBlank(caseId)){
			view.addObject("caseId", caseId);
			officeDoc = officeDocService.getDocByCaseId(docType, caseId);
		}else{
			pa.put("taskId",taskId);
			CaseBasic c=caseService.getCaseByTaskId(pa);
			view.addObject("caseId", c.getCaseId());
			officeDoc = officeDocService.getDocByCaseId(docType, c.getCaseId());
		}
		if(officeDoc == null){
			String docNo="";//文书号
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
			//建议移送
			if(docType.equals("jyys")){
				//构建bookmark 集合，把各个书签的数据查询出来
				//TODO 1.查询发往单位
				User user = userService.selectByPk(inputer);
				String inputUnitName = user.getOrgName();
				JSONObject receiveOrgJson = new JSONObject();
				receiveOrgJson.put("bookMarkName", "inputUnit");
				receiveOrgJson.put("bookMarkValue", inputUnitName);
				jsonArray.add(receiveOrgJson);
				//TODO 2.查询建议移送的文书号
				docNo=officeIdentityService.nextId("JYYS_NO");
				receiveOrgJson = new JSONObject();
				receiveOrgJson.put("bookMarkName", "docNo");
				receiveOrgJson.put("bookMarkValue", docNo);
				jsonArray.add(receiveOrgJson);
				//TODO 3.查询当前年
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				receiveOrgJson = new JSONObject();
				receiveOrgJson.put("bookMarkName", "currentTime");
				receiveOrgJson.put("bookMarkValue", dateFormat.format(new Date()));
				jsonArray.add(receiveOrgJson);
				//TODO 4.查询当前用户行政区划名称
				User user1=userService.selectOrgDistrictByUserId(inputer);
				String districtName="";
				if(user1!=null){
					districtName=user1.getDistrictName();
				}
				receiveOrgJson = new JSONObject();
				receiveOrgJson.put("bookMarkName", "districtName");
				receiveOrgJson.put("bookMarkValue", districtName);
				jsonArray.add(receiveOrgJson);
				
				receiveOrgJson = new JSONObject();
				receiveOrgJson.put("bookMarkName", "districtNameJ");
				receiveOrgJson.put("bookMarkValue", districtName);
				jsonArray.add(receiveOrgJson);
				//查询案件详情
				Map<String, Object> paramMap=new HashMap<String, Object>();
				receiveOrgJson = new JSONObject();
				CaseBasic case1=new CaseBasic();
				if(StringUtils.isNotBlank(caseId)){
					case1=caseBasicMapper.selectByPrimaryKey(caseId);
				}else{
					paramMap.put("taskId",taskId);
					case1=caseService.getCaseByTaskId(paramMap);
				}
				receiveOrgJson.put("bookMarkName", "caseDetail");
				receiveOrgJson.put("bookMarkValue", case1.getCaseDetailName()+"\r");
				jsonArray.add(receiveOrgJson);
			}
			//通知立案
			if(docType.equals("tzla")){
				//构建bookmark 集合，把各个书签的数据查询出来
				JSONObject receiveOrgJson = new JSONObject();
				//查询通知立案的文书号
				docNo=officeIdentityService.nextId("TZLA_NO");
				receiveOrgJson.put("bookMarkName", "docNo");
				receiveOrgJson.put("bookMarkValue", docNo);
				jsonArray.add(receiveOrgJson);
				//查询当前用户行政区划名称
				User user1=userService.selectOrgDistrictByUserId(inputer);
				String districtName="";
				if(user1!=null){
					districtName=user1.getDistrictName();
				}
				receiveOrgJson = new JSONObject();
				receiveOrgJson.put("bookMarkName", "districtNameG");
				receiveOrgJson.put("bookMarkValue", districtName);
				jsonArray.add(receiveOrgJson);
				
				receiveOrgJson = new JSONObject();
				receiveOrgJson.put("bookMarkName", "districtNameJ");
				receiveOrgJson.put("bookMarkValue", districtName);
				jsonArray.add(receiveOrgJson);
				//查询当前年
				receiveOrgJson = new JSONObject();
				receiveOrgJson.put("bookMarkName", "currentTime");
				receiveOrgJson.put("bookMarkValue", dateFormat.format(new Date()));
				jsonArray.add(receiveOrgJson);
				//查询案件名称
				Map<String, Object> paramMap=new HashMap<String, Object>();
				receiveOrgJson = new JSONObject();
				paramMap.put("taskId",taskId);
				CaseBasic case1=caseService.getCaseByTaskId(paramMap);
				receiveOrgJson.put("bookMarkName", "caseName1");
				receiveOrgJson.put("bookMarkValue", case1.getCaseName());
				jsonArray.add(receiveOrgJson);
				
				receiveOrgJson = new JSONObject();
				receiveOrgJson.put("bookMarkName", "caseName2");
				receiveOrgJson.put("bookMarkValue", case1.getCaseName());
				jsonArray.add(receiveOrgJson);
				
				//查询要求说明不立案理由文书的文书号和文书生成时间
				receiveOrgJson = new JSONObject();
				paramMap=new HashMap<String, Object>();
				paramMap.put("caseId",case1.getCaseId());
				paramMap.put("docType","yqsmblaly");
				OfficeDoc odoc=officeDocService.getMaxBulianDocNoByCaseId(paramMap);
				if(odoc!=null){
					receiveOrgJson.put("bookMarkName", "bulianDocCreateTime");
					receiveOrgJson.put("bookMarkValue", dateFormat.format(odoc.getCreateTime()));
					jsonArray.add(receiveOrgJson);
					
					receiveOrgJson = new JSONObject();
					receiveOrgJson.put("bookMarkName", "bulianDocNo");
					receiveOrgJson.put("bookMarkValue", odoc.getDocNo());
					jsonArray.add(receiveOrgJson);
				}
			}
			//要求说明不立案理由
			if(docType.equals("yqsmblaly")){
				//构建bookmark 集合，把各个书签的数据查询出来
				JSONObject receiveOrgJson = new JSONObject();
				//查询要求说明不立案理由的文书号
				docNo=officeIdentityService.nextId("YQSMBLALY_NO");
				receiveOrgJson.put("bookMarkName", "docNo");
				receiveOrgJson.put("bookMarkValue", docNo);
				jsonArray.add(receiveOrgJson);
				//查询发往单位(公安)
				User user=userService.selectOrgDistrictByUserId(inputer);
				List<Organise> l=orgService.findPoliceByDistrictId(user.getDistrictCode());
				Organise org=new Organise();
				if(l!=null && l.size()>0){
					org=l.get(0);
				}
				receiveOrgJson = new JSONObject();
				receiveOrgJson.put("bookMarkName", "sendUnit");
				receiveOrgJson.put("bookMarkValue", org.getOrgName());
				jsonArray.add(receiveOrgJson);
				//查询案件名称
				Map<String, Object> paramMap=new HashMap<String, Object>();
				receiveOrgJson = new JSONObject();
				paramMap.put("taskId",taskId);
				CaseBasic case1=caseService.getCaseByTaskId(paramMap);
				receiveOrgJson.put("bookMarkName", "caseName");
				receiveOrgJson.put("bookMarkValue", case1.getCaseName());
				jsonArray.add(receiveOrgJson);
				//查询当前用户行政区划名称
				User user1=userService.selectOrgDistrictByUserId(inputer);
				String districtName="";
				if(user1!=null){
					districtName=user1.getDistrictName();
				}
				receiveOrgJson = new JSONObject();
				receiveOrgJson.put("bookMarkName", "districtName");
				receiveOrgJson.put("bookMarkValue", districtName);
				jsonArray.add(receiveOrgJson);
				//查询当前年
				receiveOrgJson = new JSONObject();
				receiveOrgJson.put("bookMarkName", "currentTime");
				receiveOrgJson.put("bookMarkValue", dateFormat.format(new Date()));
				jsonArray.add(receiveOrgJson);
			}
			view.addObject("docNo", docNo);
			view.addObject("jsonArray", jsonArray);
		}else{
			view.addObject("docId", officeDoc.getDocId());
		}
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value = "saveDocFile")
	public Integer saveDocFile(String uploadName,MultipartHttpServletRequest request){
		MultipartFile mFile = request.getFile("office_office-div_name");
		PublishInfoFile publishInfoFile = null;
		if (mFile != null && !mFile.isEmpty()) {
			UpLoadContext upLoad = new UpLoadContext(new UploadResource());
			String url = upLoad.uploadFile(mFile, null);
			String fileName = mFile.getOriginalFilename();
			Integer fileId = systemDAO.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE);
			publishInfoFile = new PublishInfoFile();
			publishInfoFile.setFileId(fileId);
			publishInfoFile.setFileName(fileName);
			publishInfoFile.setFilePath(url);
			publishInfoFile.setFileType(Const.TABLE_OFFICE_DOC);
			publishInfoFileMapper.insert(publishInfoFile);
			return fileId;
		}else{
			return 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "add")
	public Integer saveDoc(String fileName,String docType,String caseId,Integer docId,OfficeDoc officeDoc,MultipartHttpServletRequest request){
		Integer result=1;
		MultipartFile file = request.getFile("upLoadFile");
			try {
				if(docId == null){
					//根据docType查询templateId
					OfficeTemplateExample example=new OfficeTemplateExample();
					example.createCriteria().andDocTypeEqualTo(docType);
					List<OfficeTemplate> list=officeTemplateMapper.selectByExample(example);
					OfficeTemplate officeTemplate=new OfficeTemplate();
					if(list!=null && list.size()>0){
						officeTemplate=list.get(0);
					}
					officeDoc.setTemplateId(officeTemplate.getId());
					User user = SystemContext.getCurrentUser(request);
					officeDoc.setCreateUser(user.getUserId());
					officeDoc.setCreateOrg(user.getOrganise().getOrgCode());
					officeDoc.setCreateTime(new Date());
					officeDoc.setDocType(docType);
					officeDoc.setCaseId(caseId);
					officeDocService.add(officeDoc,file);
				}else{
					officeDocService.updateDocFile(docId, file);
				}
			} catch (Exception e) {
				result=0;
				e.printStackTrace();
			}
		return result;
	}
	
	@RequestMapping(value = "delete")
	public ModelAndView delete(Integer docId,HttpServletRequest request) throws Exception {
		officeDocService.deleteByPrimaryKey(docId);
		return new ModelAndView("redirect:/office/officeDoc/list");
	}
	
	/*文书预览*/
	@RequestMapping(value = "docPreview")
	public ModelAndView docPreview(Integer docId,HttpServletRequest request){
		ModelAndView view = new ModelAndView("office/officeDoc/officeDocPreview");
		view.addObject("docId", docId);
		return view;
	}
	
	/*根据文书id查询文书附件*/
	@RequestMapping(value = "getByDocId")
	public void getByDocType(Integer docId,HttpServletResponse response) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("docId", docId);
		PublishInfoFile publishInfoFile=publishInfoFileMapper.getFileByDocId(map);
		if(publishInfoFile!=null){
			byte[] bytes = (byte[]) null;
			if ((bytes == null) || (bytes.length < 1)) {
				String filePath = publishInfoFile.getFilePath();
				UploadBean uploadBean = UpLoadUtil.getUpLoadBeanFromFile("upload_resource.properties");
				String fullPath=uploadBean.getUploadDir()+filePath.replace("/", File.separator);
				bytes = FileUtil.readByte(fullPath);
			}
			response.getOutputStream().write(bytes);
		}
	}
	
	@RequestMapping(value = "reportCreate")
	public ModelAndView repportCreate(String docType,String fileName,CaseBasic caseBasic, HttpServletRequest request){
		ModelAndView view = new ModelAndView("office/officeDoc/reportCreate");
		view.addObject("docType", docType);
		view.addObject("fileName", fileName);
		if(docType.equals(Const.DOC_TYPE_SXFZ_1) || docType.equals(Const.DOC_TYPE_SXFZ_2)){
			createSXFZAJXSSCBGZ(caseBasic,view,request);
		}else if(docType.equals(Const.DOC_TYPE_JGCL_Z_1)||docType.equals(Const.DOC_TYPE_JGCL_Z_3)){
			createJGCLForRespect(caseBasic,view,request);
		}else if(docType.equals(Const.DOC_TYPE_JGCL_Z_2)){
			createJGCLForPoint(caseBasic,view,request);
		}else if(docType.equals(Const.DOC_TYPE_JGCL_Z_4)){
			createGCLForAlowPoint(caseBasic,view,request);
		}else if(docType.equals(Const.DOC_TYPE_DSJFX_1)||docType.equals(Const.DOC_TYPE_DSJFX_2)){
			createDSJFX(caseBasic,view,request);
		}else if(docType.equals(Const.DOC_TYPE_DSJFX_3)){
			createDSJFX2(caseBasic,view,request);
		}else if(docType.equals("zhfx1")){
			createStatZHFX(caseBasic, view, request);//综合分析报告
		}else if(docType.equals("zhfx2")){
			createStatZHFX2(caseBasic, view, request);//综合分析报告
		}else if(docType.equals("cbtz")){
		      createCBTZ(caseBasic, view, request);//综合分析报告
	    }
		return view;
	}
	
	/**
	 * 综合分析报告
	 * author XT
	 * @param caseBasic
	 * @param view
	 * @param request
	 */
	private void createStatZHFX(CaseBasic caseBasic, ModelAndView view,
			HttpServletRequest request) {
		String regionId = request.getParameter("districtId");
		String yearStr = request.getParameter("yearStr");
		String quarterStr = request.getParameter("quarterStr");
		String monthStr = request.getParameter("monthStr");
		String timeStr="";
		if(StringUtils.isNotBlank(quarterStr)){
			timeStr="第"+quarterStr+"季度";
		}else if(StringUtils.isNotBlank(monthStr)){
			timeStr=monthStr+"月份";
		}
		User user = SystemContext.getCurrentUser(request);
		Organise organise = user.getOrganise();
		if (StringUtils.isBlank(regionId)) {
			regionId=organise.getDistrictCode();
		}
		
		if (StringUtils.isBlank(yearStr)) {
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy");
			yearStr = simpleDateFormat.format(new Date());
		}
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("regionId", regionId);
		paramMap.put("yearStr", yearStr);
		paramMap.put("quarterStr", quarterStr);
		paramMap.put("monthStr", monthStr);
		
		JSONArray jsonArray=new JSONArray();
		JSONObject titleDate = new JSONObject();
		getQueryTimeStr(paramMap);
		titleDate.put("bookMarkName", "titleDate");
		titleDate.put("bookMarkValue",yearStr+"年度"+timeStr);
		jsonArray.add(titleDate);
		JSONObject firstDate = new JSONObject();
		firstDate.put("bookMarkName", "firstDate");
		firstDate.put("bookMarkValue",getQueryTimeStr(paramMap));
		jsonArray.add(firstDate);
		
		//行政处罚录入统计按区划
		List<Map<String, Object>> xzcfInputRatioPerRegionList= caseService.queryXzcfInputRatioPerRegion(paramMap);
		JSONObject xzcfInputRatioPerRegionObject=new JSONObject();
		xzcfInputRatioPerRegionObject.put("bookMarkName", "xzcfInputRatioPerRegionList");
		xzcfInputRatioPerRegionObject.put("bookMarkValue", xzcfInputRatioPerRegionList);
		jsonArray.add(xzcfInputRatioPerRegionObject);
		//行政处罚录入统计按行业
		List<Map<String, Object>> xzcfInputRatioPerIndustryList= caseService.queryXzcfInputRatioPerIndustry(paramMap);
		JSONObject xzcfInputRatioPerIndustryObject=new JSONObject();
		xzcfInputRatioPerIndustryObject.put("bookMarkName", "xzcfInputRatioPerIndustryList");
		xzcfInputRatioPerIndustryObject.put("bookMarkValue", xzcfInputRatioPerIndustryList);
		jsonArray.add(xzcfInputRatioPerIndustryObject);
		//获得涉嫌犯罪案件处理情况按区划统计 
		List<Map<String, Object>> crimeCaseDealStatisList=caseService.queryCrimeCaseDealStatis(paramMap);
		JSONObject crimeCaseDealStatisObject=new JSONObject();
		crimeCaseDealStatisObject.put("bookMarkName", "crimeCaseDealStatisList");
		crimeCaseDealStatisObject.put("bookMarkValue", crimeCaseDealStatisList);
		jsonArray.add(crimeCaseDealStatisObject);
		
		//获得涉嫌犯罪案件处理情况按行业统计 
		List<Map<String, Object>> crimeCaseDealStatisByIndustryList=caseService.queryCrimeCaseDealStatisByIndustry(paramMap);
		JSONObject crimeCaseDealStatisByIndustryObject=new JSONObject();
		crimeCaseDealStatisByIndustryObject.put("bookMarkName", "crimeCaseDealStatisByIndustryList");
		crimeCaseDealStatisByIndustryObject.put("bookMarkValue", crimeCaseDealStatisByIndustryList);
		jsonArray.add(crimeCaseDealStatisByIndustryObject);
		
		//获得前十名罪名统计
		List<Map<String, Object>> top10AccuseList=caseService.queryTop10Accuse(paramMap);
		if(top10AccuseList.size() == 0){
			JSONObject top10AccuseNullObj = new JSONObject();
			top10AccuseNullObj.put("bookMarkName", "top10AccuseNull");
			jsonArray.add(top10AccuseNullObj);
		}else{
			JSONObject top10AccuseObject=new JSONObject();
			top10AccuseObject.put("bookMarkName", "top10AccuseList");
			top10AccuseObject.put("bookMarkValue", top10AccuseList);
			jsonArray.add(top10AccuseObject);
		}
		
		//接入单位
		District r=districtService.selectByPrimaryKey(regionId);
		Integer districtJB=r.getJb();
		districtJB = districtJB.intValue() == Const.DISTRICT_JB_2 ? null:districtJB.intValue();
		StatisData statisData = statisDataService.statisAccesOrgStatForBusiness(null,null, districtJB,paramMap);
		JSONObject joinUnitDataObj=new JSONObject();
		joinUnitDataObj.put("bookMarkName", "joinUnitData");
		joinUnitDataObj.put("bookMarkValue",getQueryTimeStr(paramMap));
		jsonArray.add(joinUnitDataObj);
		
		JSONObject XingZhengNumObj=new JSONObject();
		XingZhengNumObj.put("bookMarkName", "XingZhengNum");
		XingZhengNumObj.put("bookMarkValue",statisData.getXingZhengNum());
		jsonArray.add(XingZhengNumObj);
		
		JSONObject cityXingZhengNumObj=new JSONObject();
		cityXingZhengNumObj.put("bookMarkName", "cityXingZhengNum");
		cityXingZhengNumObj.put("bookMarkValue",statisData.getCityXingZhengNum());
		jsonArray.add(cityXingZhengNumObj);
		
		JSONObject countyXingZhengNumObj=new JSONObject();
		countyXingZhengNumObj.put("bookMarkName", "countyXingZhengNum");
		countyXingZhengNumObj.put("bookMarkValue",statisData.getCountyXingZhengNum());
		jsonArray.add(countyXingZhengNumObj);
		
		JSONObject policeNumObj=new JSONObject();
		policeNumObj.put("bookMarkName", "policeNum");
		policeNumObj.put("bookMarkValue",statisData.getPoliceNum());
		jsonArray.add(policeNumObj);
		
		JSONObject cityPoliceNumObj=new JSONObject();
		cityPoliceNumObj.put("bookMarkName", "cityPoliceNum");
		cityPoliceNumObj.put("bookMarkValue",statisData.getCityPoliceNum());
		jsonArray.add(cityPoliceNumObj);
		
		JSONObject countyPoliceNumObj=new JSONObject();
		countyPoliceNumObj.put("bookMarkName", "countyPoliceNum");
		countyPoliceNumObj.put("bookMarkValue",statisData.getCountyPoliceNum());
		jsonArray.add(countyPoliceNumObj);
		
		
		JSONObject jianChaNumObj=new JSONObject();
		jianChaNumObj.put("bookMarkName", "jianChaNum");
		jianChaNumObj.put("bookMarkValue",statisData.getJianChaNum());
		jsonArray.add(jianChaNumObj);
		
		JSONObject cityJianChaNumObj=new JSONObject();
		cityJianChaNumObj.put("bookMarkName", "cityJianChaNum");
		cityJianChaNumObj.put("bookMarkValue",statisData.getCityJianChaNum());
		jsonArray.add(cityJianChaNumObj);
		
		JSONObject countyJianChaNumObj=new JSONObject();
		countyJianChaNumObj.put("bookMarkName", "countyJianChaNum");
		countyJianChaNumObj.put("bookMarkValue",statisData.getCountyJianChaNum());
		jsonArray.add(countyJianChaNumObj);

		JSONObject judgeNumObj=new JSONObject();
		judgeNumObj.put("bookMarkName", "judgeNum");
		judgeNumObj.put("bookMarkValue",statisData.getJudgeNum());
		jsonArray.add(judgeNumObj);
		
		JSONObject cityJudgeNumObj=new JSONObject();
		cityJudgeNumObj.put("bookMarkName", "cityJudgeNum");
		cityJudgeNumObj.put("bookMarkValue",statisData.getCityJudgeNum());
		jsonArray.add(cityJudgeNumObj);
		
		JSONObject countyJudgeNumObj=new JSONObject();
		countyJudgeNumObj.put("bookMarkName", "countyJudgeNum");
		countyJudgeNumObj.put("bookMarkValue",statisData.getCountyJudgeNum());
		jsonArray.add(countyJudgeNumObj);

		
		
		String queryTimeStr=getQueryTimeStr(paramMap);
		
		//时间字符串
		JSONObject queryTimeFullStrObject=new JSONObject();
		JSONObject queryTimeFullStr1Object=new JSONObject();
		JSONObject queryTimeFullStr2Object=new JSONObject();
		queryTimeFullStrObject.put("bookMarkName", "queryTimeFullStr");
		queryTimeFullStrObject.put("bookMarkValue", queryTimeStr);
		queryTimeFullStr1Object.put("bookMarkName", "queryTimeFullStr1");
		queryTimeFullStr1Object.put("bookMarkValue", queryTimeStr);
		queryTimeFullStr2Object.put("bookMarkName", "queryTimeFullStr2");
		queryTimeFullStr2Object.put("bookMarkValue", queryTimeStr);
		jsonArray.add(queryTimeFullStrObject);
		jsonArray.add(queryTimeFullStr1Object);
		jsonArray.add(queryTimeFullStr2Object);
		
		//下发指令统计
		paramMap.put("sendOrg", organise.getOrgCode());
		Map<String, Object> instructionStatis=instructionReceiveService.queryInstructionStatis(paramMap);
		JSONObject insDataObj=new JSONObject();
		insDataObj.put("bookMarkName", "insData");
		insDataObj.put("bookMarkValue",queryTimeStr+organise.getOrgName());
		jsonArray.add(insDataObj);
		
		JSONObject insSendNumObj=new JSONObject();
		insSendNumObj.put("bookMarkName", "insSendNum");
		insSendNumObj.put("bookMarkValue",instructionStatis.get("SEND_NUM").toString());
		jsonArray.add(insSendNumObj);
		
		JSONObject insReplyNumObj=new JSONObject();
		insReplyNumObj.put("bookMarkName", "insReplyNum");
		insReplyNumObj.put("bookMarkValue",instructionStatis.get("REPLY_NUM").toString());
		jsonArray.add(insReplyNumObj);		

		JSONObject insDoNumObj=new JSONObject();
		insDoNumObj.put("bookMarkName", "insDoNum");
		insDoNumObj.put("bookMarkValue",instructionStatis.get("DO_NUM").toString());
		jsonArray.add(insDoNumObj);		
		
		JSONObject insNoReplyNumObj=new JSONObject();
		insNoReplyNumObj.put("bookMarkName", "insNoReplyNum");
		insNoReplyNumObj.put("bookMarkValue",instructionStatis.get("NO_REPLY_NUM").toString());
		jsonArray.add(insNoReplyNumObj);	

		
		//各院下发指令及办理情况统计
		List<Map<String, Object>> instructionStatisByOrgList=instructionReceiveService.queryInstructionStatisByOrg(paramMap);
		JSONObject instructionStatisByOrgObject=new JSONObject();
		instructionStatisByOrgObject.put("bookMarkName", "instructionStatisByOrgList");
		instructionStatisByOrgObject.put("bookMarkValue", instructionStatisByOrgList);
		jsonArray.add(instructionStatisByOrgObject);
		
		//行政处罚案件录入情况
		StatisData xzcfStatisData = statisDataService.statisCaseNumStatForReportForBusiness(paramMap);
		JSONObject xzcfDateObj=new JSONObject();
		xzcfDateObj.put("bookMarkName", "xzcfDate");
		xzcfDateObj.put("bookMarkValue",yearStr+"年度"+timeStr);
		jsonArray.add(xzcfDateObj);
		JSONObject xzcfRegionObj=new JSONObject();
		xzcfRegionObj.put("bookMarkName", "xzcfRegion");
		xzcfRegionObj.put("bookMarkValue",r.getDistrictName());
		jsonArray.add(xzcfRegionObj);	
		Long totalPenaltyNum = xzcfStatisData.getPenaltyNum();
		JSONObject penaltyNumObj=new JSONObject();
		penaltyNumObj.put("bookMarkName", "penaltyNum");
		penaltyNumObj.put("bookMarkValue",totalPenaltyNum);
		jsonArray.add(penaltyNumObj);		
		
		JSONObject amountInvolvedObj=new JSONObject();
		amountInvolvedObj.put("bookMarkName", "amountInvolved");
		amountInvolvedObj.put("bookMarkValue",xzcfStatisData.getAmountInvolved().toString());
		jsonArray.add(amountInvolvedObj);
				
		JSONObject cityChufaNumObj=new JSONObject();
		cityChufaNumObj.put("bookMarkName", "cityChufaNum");
		cityChufaNumObj.put("bookMarkValue",xzcfStatisData.getCityChufaNum());
		jsonArray.add(cityChufaNumObj);
		
		JSONObject cityRateObj=new JSONObject();
		cityRateObj.put("bookMarkName", "cityRate");
		cityRateObj.put("bookMarkValue",xzcfStatisData.getCityRate());
		jsonArray.add(cityRateObj);	
		
		JSONObject countyChufaNumObj=new JSONObject();
		countyChufaNumObj.put("bookMarkName", "countyChufaNum");
		countyChufaNumObj.put("bookMarkValue",xzcfStatisData.getCountyChufaNum());
		jsonArray.add(countyChufaNumObj);
		
		JSONObject countyRateObj=new JSONObject();
		countyRateObj.put("bookMarkName", "countyRate");
		countyRateObj.put("bookMarkValue",xzcfStatisData.getCountyRate());
		jsonArray.add(countyRateObj);			

		//涉嫌犯罪案件处理情况总体情况
		Map<String,Object> allCrimeCaseDealStatis = new HashMap<String,Object>();
		allCrimeCaseDealStatis = caseService.queryAllCrimeCaseDealStatis(paramMap);
		JSONObject crimeCaseDateObj=new JSONObject();
		crimeCaseDateObj.put("bookMarkName", "crimeCaseDate");
		crimeCaseDateObj.put("bookMarkValue",yearStr+"年度"+timeStr);
		jsonArray.add(crimeCaseDateObj);
		
		JSONObject crimeCaseRegionObj=new JSONObject();
		crimeCaseRegionObj.put("bookMarkName", "crimeCaseRegion");
		crimeCaseRegionObj.put("bookMarkValue",r.getDistrictName());
		jsonArray.add(crimeCaseRegionObj);	
		String suggestYiSongCount = "0";
		if(allCrimeCaseDealStatis != null){
			JSONObject zjyscntObj=new JSONObject();
			zjyscntObj.put("bookMarkName", "ZJ_YS_CNT");
			zjyscntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("ZJ_YS_CNT").toString());
			jsonArray.add(zjyscntObj);
			if(allCrimeCaseDealStatis.get("YOY_ZJ_YS_CNT") != null && allCrimeCaseDealStatis.get("YOY_ZJ_YS_CNT").toString().startsWith("-")){
				JSONObject yoyzjyscntjsObj=new JSONObject();
				yoyzjyscntjsObj.put("bookMarkName", "YOY_ZJ_YS_CNT_ZZ");
				yoyzjyscntjsObj.put("bookMarkValue","减少");
				jsonArray.add(yoyzjyscntjsObj);
				
				JSONObject yoyzjyscntObj=new JSONObject();
				yoyzjyscntObj.put("bookMarkName", "YOY_ZJ_YS_CNT");
				yoyzjyscntObj.put("bookMarkValue",com.ksource.liangfa.util.StringUtils.trimPrefix(allCrimeCaseDealStatis.get("YOY_ZJ_YS_CNT").toString(),"-"));
				jsonArray.add(yoyzjyscntObj);
			}else{
				JSONObject yoyzjyscntjsObj=new JSONObject();
				yoyzjyscntjsObj.put("bookMarkName", "YOY_ZJ_YS_CNT_ZZ");
				yoyzjyscntjsObj.put("bookMarkValue","增长");
				jsonArray.add(yoyzjyscntjsObj);
				
				JSONObject yoyzjyscntObj=new JSONObject();
				yoyzjyscntObj.put("bookMarkName", "YOY_ZJ_YS_CNT");
				yoyzjyscntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("YOY_ZJ_YS_CNT").toString());
				jsonArray.add(yoyzjyscntObj);
			}
			
			JSONObject jyyscntObj=new JSONObject();
			jyyscntObj.put("bookMarkName", "JY_YS_CNT");
			suggestYiSongCount = allCrimeCaseDealStatis.get("JY_YS_CNT").toString();
			jyyscntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("JY_YS_CNT").toString());
			jsonArray.add(jyyscntObj);
			if(allCrimeCaseDealStatis.get("YOY_JY_YS_CNT") != null && allCrimeCaseDealStatis.get("YOY_JY_YS_CNT").toString().startsWith("-")){
				JSONObject yoyjyyscntjsObj=new JSONObject();
				yoyjyyscntjsObj.put("bookMarkName", "YOY_JY_YS_CNT_ZZ");
				yoyjyyscntjsObj.put("bookMarkValue","减少");
				jsonArray.add(yoyjyyscntjsObj);
				
				JSONObject yoyjyyscntObj=new JSONObject();
				yoyjyyscntObj.put("bookMarkName", "YOY_JY_YS_CNT");
				yoyjyyscntObj.put("bookMarkValue",com.ksource.liangfa.util.StringUtils.trimPrefix(allCrimeCaseDealStatis.get("YOY_JY_YS_CNT").toString(),"-"));
				jsonArray.add(yoyjyyscntObj);
			}else{
				JSONObject yoyjyyscntjsObj=new JSONObject();
				yoyjyyscntjsObj.put("bookMarkName", "YOY_JY_YS_CNT_ZZ");
				yoyjyyscntjsObj.put("bookMarkValue","增长");
				jsonArray.add(yoyjyyscntjsObj);
				
				JSONObject yoyjyyscntObj=new JSONObject();
				yoyjyyscntObj.put("bookMarkName", "YOY_JY_YS_CNT");
				yoyjyyscntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("YOY_JY_YS_CNT").toString());
				jsonArray.add(yoyjyyscntObj);
			}
			
			JSONObject acceptcntObj=new JSONObject();
			acceptcntObj.put("bookMarkName", "ACCEPT_CNT");
			acceptcntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("ACCEPT_CNT").toString());
			jsonArray.add(acceptcntObj);
			JSONObject liancntObj=new JSONObject();
			liancntObj.put("bookMarkName", "LIAN_CNT");
			liancntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("LIAN_CNT").toString());
			jsonArray.add(liancntObj);		
			
			if(allCrimeCaseDealStatis.get("YOY_LIAN_CNT") != null && allCrimeCaseDealStatis.get("YOY_LIAN_CNT").toString().startsWith("-")){
				JSONObject yoyliancntjsObj=new JSONObject();
				yoyliancntjsObj.put("bookMarkName", "YOY_LIAN_CNT_ZZ");
				yoyliancntjsObj.put("bookMarkValue","减少");
				jsonArray.add(yoyliancntjsObj);
				
				JSONObject yoyliancntObj=new JSONObject();
				yoyliancntObj.put("bookMarkName", "YOY_LIAN_CNT");
				yoyliancntObj.put("bookMarkValue",com.ksource.liangfa.util.StringUtils.trimPrefix(allCrimeCaseDealStatis.get("YOY_LIAN_CNT").toString(),"-"));
				jsonArray.add(yoyliancntObj);			
			}else{
				JSONObject yoyliancntjsObj=new JSONObject();
				yoyliancntjsObj.put("bookMarkName", "YOY_LIAN_CNT_ZZ");
				yoyliancntjsObj.put("bookMarkValue","增长");
				jsonArray.add(yoyliancntjsObj);
				
				JSONObject yoyliancntObj=new JSONObject();
				yoyliancntObj.put("bookMarkName", "YOY_LIAN_CNT");
				yoyliancntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("YOY_LIAN_CNT").toString());
				jsonArray.add(yoyliancntObj);	
			}
			JSONObject tqdbcntObj=new JSONObject();
			tqdbcntObj.put("bookMarkName", "TQDB_CNT");
			tqdbcntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("TQDB_CNT").toString());
			jsonArray.add(tqdbcntObj);
			if(allCrimeCaseDealStatis.get("YOY_TQDB_CNT") != null && allCrimeCaseDealStatis.get("YOY_TQDB_CNT").toString().startsWith("-")){
				JSONObject yoytqdbcntjsObj=new JSONObject();
				yoytqdbcntjsObj.put("bookMarkName", "YOY_TQDB_CNT_ZZ");
				yoytqdbcntjsObj.put("bookMarkValue","减少");
				jsonArray.add(yoytqdbcntjsObj);
				
				JSONObject yoytqdbcntObj=new JSONObject();
				yoytqdbcntObj.put("bookMarkName", "YOY_TQDB_CNT");
				yoytqdbcntObj.put("bookMarkValue",com.ksource.liangfa.util.StringUtils.trimPrefix(allCrimeCaseDealStatis.get("YOY_TQDB_CNT").toString(),"-"));
				jsonArray.add(yoytqdbcntObj);	
			}else{
				JSONObject yoytqdbcntjsObj=new JSONObject();
				yoytqdbcntjsObj.put("bookMarkName", "YOY_TQDB_CNT_ZZ");
				yoytqdbcntjsObj.put("bookMarkValue","增长");
				jsonArray.add(yoytqdbcntjsObj);
				
				JSONObject yoytqdbcntObj=new JSONObject();
				yoytqdbcntObj.put("bookMarkName", "YOY_TQDB_CNT");
				yoytqdbcntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("YOY_TQDB_CNT").toString());
				jsonArray.add(yoytqdbcntObj);	
			}	
			
			JSONObject qscntObj=new JSONObject();
			qscntObj.put("bookMarkName", "QISU_CNT");
			qscntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("QISU_CNT").toString());
			jsonArray.add(qscntObj);
			if(allCrimeCaseDealStatis.get("YOY_QISU_CNT") != null && allCrimeCaseDealStatis.get("YOY_QISU_CNT").toString().startsWith("-")){
				JSONObject yoyqscntjsObj=new JSONObject();
				yoyqscntjsObj.put("bookMarkName", "YOY_QISU_CNT_ZZ");
				yoyqscntjsObj.put("bookMarkValue","减少");
				jsonArray.add(yoyqscntjsObj);
				
				JSONObject yoyqscntObj=new JSONObject();
				yoyqscntObj.put("bookMarkName", "YOY_QISU_CNT");
				yoyqscntObj.put("bookMarkValue",com.ksource.liangfa.util.StringUtils.trimPrefix(allCrimeCaseDealStatis.get("YOY_QISU_CNT").toString(),"-"));
				jsonArray.add(yoyqscntObj);			
			}else{
				JSONObject yoyqscntjsObj=new JSONObject();
				yoyqscntjsObj.put("bookMarkName", "YOY_QISU_CNT_ZZ");
				yoyqscntjsObj.put("bookMarkValue","增长");
				jsonArray.add(yoyqscntjsObj);
				
				JSONObject yoyqscntObj=new JSONObject();
				yoyqscntObj.put("bookMarkName", "YOY_QISU_CNT");
				yoyqscntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("YOY_QISU_CNT").toString());
				jsonArray.add(yoyqscntObj);	
			}	
			
			JSONObject pjcntObj=new JSONObject();
			pjcntObj.put("bookMarkName", "PANJUE_CNT");
			pjcntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("PANJUE_CNT").toString());
			jsonArray.add(pjcntObj);
			
			if(allCrimeCaseDealStatis.get("YOY_PANJUE_CNT") != null && allCrimeCaseDealStatis.get("YOY_PANJUE_CNT").toString().startsWith("-")){
				JSONObject yoypjcntjsObj=new JSONObject();
				yoypjcntjsObj.put("bookMarkName", "YOY_PANJUE_CNT_ZZ");
				yoypjcntjsObj.put("bookMarkValue","减少");
				jsonArray.add(yoypjcntjsObj);
				
				JSONObject yoypjcntObj=new JSONObject();
				yoypjcntObj.put("bookMarkName", "YOY_PANJUE_CNT");
				yoypjcntObj.put("bookMarkValue",com.ksource.liangfa.util.StringUtils.trimPrefix(allCrimeCaseDealStatis.get("YOY_PANJUE_CNT").toString(),"-"));
				jsonArray.add(yoypjcntObj);	
			}else{
				JSONObject yoypjcntjsObj=new JSONObject();
				yoypjcntjsObj.put("bookMarkName", "YOY_PANJUE_CNT_ZZ");
				yoypjcntjsObj.put("bookMarkValue","增长");
				jsonArray.add(yoypjcntjsObj);
				
				JSONObject yoypjcntObj=new JSONObject();
				yoypjcntObj.put("bookMarkName", "YOY_PANJUE_CNT");
				yoypjcntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("YOY_PANJUE_CNT").toString());
				jsonArray.add(yoypjcntObj);	
			}
		}else{
			JSONObject zjyscntObj=new JSONObject();
			zjyscntObj.put("bookMarkName", "ZJ_YS_CNT");
			zjyscntObj.put("bookMarkValue",0);
			jsonArray.add(zjyscntObj);
			JSONObject yoyzjyscntjsObj=new JSONObject();
			yoyzjyscntjsObj.put("bookMarkName", "YOY_ZJ_YS_CNT_ZZ");
			yoyzjyscntjsObj.put("bookMarkValue","增长");
			jsonArray.add(yoyzjyscntjsObj);
			
			JSONObject yoyzjyscntObj=new JSONObject();
			yoyzjyscntObj.put("bookMarkName", "YOY_ZJ_YS_CNT");
			yoyzjyscntObj.put("bookMarkValue",0);
			jsonArray.add(yoyzjyscntObj);
			
			
			JSONObject jyyscntObj=new JSONObject();
			jyyscntObj.put("bookMarkName", "JY_YS_CNT");
			jyyscntObj.put("bookMarkValue",0);
			jsonArray.add(jyyscntObj);

			JSONObject yoyjyyscntjsObj=new JSONObject();
			yoyjyyscntjsObj.put("bookMarkName", "YOY_JY_YS_CNT_ZZ");
			yoyjyyscntjsObj.put("bookMarkValue","增长");
			jsonArray.add(yoyjyyscntjsObj);
			
			JSONObject yoyjyyscntObj=new JSONObject();
			yoyjyyscntObj.put("bookMarkName", "YOY_JY_YS_CNT");
			yoyjyyscntObj.put("bookMarkValue",0);
			jsonArray.add(yoyjyyscntObj);
			
			JSONObject acceptcntObj=new JSONObject();
			acceptcntObj.put("bookMarkName", "ACCEPT_CNT");
			acceptcntObj.put("bookMarkValue",0);
			jsonArray.add(acceptcntObj);
			JSONObject liancntObj=new JSONObject();
			liancntObj.put("bookMarkName", "LIAN_CNT");
			liancntObj.put("bookMarkValue",0);
			jsonArray.add(liancntObj);		
			
			JSONObject yoyliancntjsObj=new JSONObject();
			yoyliancntjsObj.put("bookMarkName", "YOY_LIAN_CNT_ZZ");
			yoyliancntjsObj.put("bookMarkValue","增长");
			jsonArray.add(yoyliancntjsObj);
			
			JSONObject yoyliancntObj=new JSONObject();
			yoyliancntObj.put("bookMarkName", "YOY_LIAN_CNT");
			yoyliancntObj.put("bookMarkValue",0);
			jsonArray.add(yoyliancntObj);	
			
			JSONObject tqdbcntObj=new JSONObject();
			tqdbcntObj.put("bookMarkName", "TQDB_CNT");
			tqdbcntObj.put("bookMarkValue",0);
			jsonArray.add(tqdbcntObj);

			JSONObject yoytqdbcntjsObj=new JSONObject();
			yoytqdbcntjsObj.put("bookMarkName", "YOY_TQDB_CNT_ZZ");
			yoytqdbcntjsObj.put("bookMarkValue","增长");
			jsonArray.add(yoytqdbcntjsObj);
			
			JSONObject yoytqdbcntObj=new JSONObject();
			yoytqdbcntObj.put("bookMarkName", "YOY_TQDB_CNT");
			yoytqdbcntObj.put("bookMarkValue",0);
			jsonArray.add(yoytqdbcntObj);	
				
			JSONObject qscntObj=new JSONObject();
			qscntObj.put("bookMarkName", "QISU_CNT");
			qscntObj.put("bookMarkValue",0);
			jsonArray.add(qscntObj);

			JSONObject yoyqscntjsObj=new JSONObject();
			yoyqscntjsObj.put("bookMarkName", "YOY_QISU_CNT_ZZ");
			yoyqscntjsObj.put("bookMarkValue","增长");
			jsonArray.add(yoyqscntjsObj);
			
			JSONObject yoyqscntObj=new JSONObject();
			yoyqscntObj.put("bookMarkName", "YOY_QISU_CNT");
			yoyqscntObj.put("bookMarkValue",0);
			jsonArray.add(yoyqscntObj);	
				
			JSONObject pjcntObj=new JSONObject();
			pjcntObj.put("bookMarkName", "PANJUE_CNT");
			pjcntObj.put("bookMarkValue",0);
			jsonArray.add(pjcntObj);

			JSONObject yoypjcntjsObj=new JSONObject();
			yoypjcntjsObj.put("bookMarkName", "YOY_PANJUE_CNT_ZZ");
			yoypjcntjsObj.put("bookMarkValue","增长");
			jsonArray.add(yoypjcntjsObj);
			
			JSONObject yoypjcntObj=new JSONObject();
			yoypjcntObj.put("bookMarkName", "YOY_PANJUE_CNT");
			yoypjcntObj.put("bookMarkValue",0);
			jsonArray.add(yoypjcntObj);	
		}
				
		//int totalPenaltyNum = caseService.queryCaseCountForZHFX(paramMap);
		
		//涉嫌犯罪案件线索筛查情况
		JSONObject sxfzDate = new JSONObject();
		sxfzDate.put("bookMarkName", "sxfzDate");
		sxfzDate.put("bookMarkValue",getQueryTimeStr(paramMap));
		jsonArray.add(sxfzDate);
		JSONObject sxfzRegion = new JSONObject();
		sxfzRegion.put("bookMarkName", "sxfzRegion");
		sxfzRegion.put("bookMarkValue",r.getDistrictName());
		jsonArray.add(sxfzRegion);		
		JSONObject totalNumObj = new JSONObject();
		totalNumObj.put("bookMarkName", "sxfzTotal");
		totalNumObj.put("bookMarkValue",totalPenaltyNum);
		jsonArray.add(totalNumObj);
		int crimeCaseCount = caseService.queryCrimeCaseCountForZHFX(paramMap);
		JSONObject crimeCaseNumObj = new JSONObject();
		crimeCaseNumObj.put("bookMarkName", "sxfzCaseNum");
		crimeCaseNumObj.put("bookMarkValue",crimeCaseCount);		
		jsonArray.add(crimeCaseNumObj);
		//降格处理案件线索筛查
		JSONObject jgclDate = new JSONObject();
		jgclDate.put("bookMarkName", "jgclDate");
		jgclDate.put("bookMarkValue",getQueryTimeStr(paramMap));
		jsonArray.add(jgclDate);
		
		JSONObject jgclRegion = new JSONObject();
		jgclRegion.put("bookMarkName", "jgclRegion");
		jgclRegion.put("bookMarkValue",r.getDistrictName());
		jsonArray.add(jgclRegion);	
		
		JSONObject jgclTotal = new JSONObject();
		jgclTotal.put("bookMarkName", "jgclTotal");
		jgclTotal.put("bookMarkValue",totalPenaltyNum);
		jsonArray.add(jgclTotal);
		
		Map<String,Object> jgclCaseNumMap = caseService.queryJGCLCaseCountForZHFX(paramMap);
		JSONObject jgclCaseNumObj = new JSONObject();
		jgclCaseNumObj.put("bookMarkName", "jgclCaseNum");
		jgclCaseNumObj.put("bookMarkValue",jgclCaseNumMap.get("JGCL_TOTAL").toString());
		jsonArray.add(jgclCaseNumObj);
		
		JSONObject jgclFilter1Obj = new JSONObject();
		jgclFilter1Obj.put("bookMarkName", "jgclFilter1");
		jgclFilter1Obj.put("bookMarkValue",jgclCaseNumMap.get("JGCL_FILTER_1").toString());
		jsonArray.add(jgclFilter1Obj);
		
		JSONObject jgclFilter2Obj = new JSONObject();
		jgclFilter2Obj.put("bookMarkName", "jgclFilter2");
		jgclFilter2Obj.put("bookMarkValue",jgclCaseNumMap.get("JGCL_FILTER_2").toString());
		jsonArray.add(jgclFilter2Obj);
		
		JSONObject jgclFilter3Obj = new JSONObject();
		jgclFilter3Obj.put("bookMarkName", "jgclFilter3");
		jgclFilter3Obj.put("bookMarkValue",jgclCaseNumMap.get("JGCL_FILTER_3").toString());
		jsonArray.add(jgclFilter3Obj);
		
		JSONObject jgclFilter4Obj = new JSONObject();
		jgclFilter4Obj.put("bookMarkName", "jgclFilter4");
		jgclFilter4Obj.put("bookMarkValue",jgclCaseNumMap.get("JGCL_FILTER_4").toString());
		jsonArray.add(jgclFilter4Obj);
		
		JSONObject jgclFilter5Obj = new JSONObject();
		jgclFilter5Obj.put("bookMarkName", "jgclFilter5");
		jgclFilter5Obj.put("bookMarkValue",jgclCaseNumMap.get("JGCL_FILTER_5").toString());
		jsonArray.add(jgclFilter5Obj);
		
		JSONObject jgclFilter6Obj = new JSONObject();
		jgclFilter6Obj.put("bookMarkName", "jgclFilter6");
		jgclFilter6Obj.put("bookMarkValue",jgclCaseNumMap.get("JGCL_FILTER_6").toString());
		jsonArray.add(jgclFilter6Obj);
		
		JSONObject jgclFilter7Obj = new JSONObject();
		jgclFilter7Obj.put("bookMarkName", "jgclFilter7");
		jgclFilter7Obj.put("bookMarkValue",jgclCaseNumMap.get("JGCL_FILTER_7").toString());
		jsonArray.add(jgclFilter7Obj);
		
		JSONObject jgclFilter8Obj = new JSONObject();
		jgclFilter8Obj.put("bookMarkName", "jgclFilter8");
		jgclFilter8Obj.put("bookMarkValue",jgclCaseNumMap.get("JGCL_FILTER_8").toString());
		jsonArray.add(jgclFilter8Obj);
		
		//大数据分析
		JSONObject dsjfxDate = new JSONObject();
		dsjfxDate.put("bookMarkName", "dsjfxDate");
		dsjfxDate.put("bookMarkValue",getQueryTimeStr(paramMap));
		jsonArray.add(dsjfxDate);
		JSONObject dsjfxRegion = new JSONObject();
		dsjfxRegion.put("bookMarkName", "dsjfxRegion");
		dsjfxRegion.put("bookMarkValue",r.getDistrictName());
		jsonArray.add(dsjfxRegion);	
		JSONObject dsjfxTotal = new JSONObject();
		dsjfxTotal.put("bookMarkName", "dsjfxTotal");
		dsjfxTotal.put("bookMarkValue",totalPenaltyNum);
		jsonArray.add(dsjfxTotal);
		
    	Map<String,Object> filterMap = new HashMap<String, Object>();
    	filterMap.put("danwei", "Y");//同一处罚对象（单位）
    	filterMap.put("address", "Y");//同一违法行为发生地
    	filterMap.put("shijian", "Y");//同一违法行为发生时间
    	filterMap.put("wupin", "Y");//同一涉案物品
    	filterMap.put("jianding", "Y");//同一鉴定
		//paramMap.put("dangshiren", "Y");//同一处罚对象（个人）
    	//以同一处罚对象（单位）+同一违法行为发生地+同一违法行为发生时间+同一涉案物品+同一鉴定
    	Integer filterCount1 = caseService.queryDSJFXCaseCountForZHFX(paramMap,filterMap);
		JSONObject dsjfxFilter1 = new JSONObject();
		dsjfxFilter1.put("bookMarkName", "dsjfxFilter1");
		dsjfxFilter1.put("bookMarkValue", filterCount1);
		jsonArray.add(dsjfxFilter1);
		
		//同一处罚对象（单位）+同一违法行为发生地+同一违法行为发生时间+同一涉案物品
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
    	filterMap.put("address", "Y");//同一违法行为发生地
    	filterMap.put("shijian", "Y");//同一违法行为发生时间
    	filterMap.put("wupin", "Y");//同一涉案物品
    	Integer filterCount2 = caseService.queryDSJFXCaseCountForZHFX(paramMap,filterMap);
		JSONObject dsjfxFilter2 = new JSONObject();
		dsjfxFilter2.put("bookMarkName", "dsjfxFilter2");
		dsjfxFilter2.put("bookMarkValue", filterCount2);
		jsonArray.add(dsjfxFilter2);
		
		//以同一处罚对象（单位）+同一违法行为发生地+同一违法行为发生时间+同一鉴定
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
    	filterMap.put("address", "Y");//同一违法行为发生地
    	filterMap.put("shijian", "Y");//同一违法行为发生时间
    	filterMap.put("jianding", "Y");//同一鉴定
    	Integer filterCount3 = caseService.queryDSJFXCaseCountForZHFX(paramMap,filterMap);
		JSONObject dsjfxFilter3 = new JSONObject();
		dsjfxFilter3.put("bookMarkName", "dsjfxFilter3");
		dsjfxFilter3.put("bookMarkValue", filterCount3);
		jsonArray.add(dsjfxFilter3);
		
		//同一处罚对象（单位）+同一违法行为发生地+同一涉案物品+同一鉴定
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
		filterMap.put("address", "Y");//同一违法行为发生地
    	filterMap.put("wupin", "Y");//同一涉案物品
    	filterMap.put("jianding", "Y");//同一鉴定
    	Integer filterCount4 = caseService.queryDSJFXCaseCountForZHFX(paramMap,filterMap);
		JSONObject dsjfxFilter4 = new JSONObject();
		dsjfxFilter4.put("bookMarkName", "dsjfxFilter4");
		dsjfxFilter4.put("bookMarkValue", filterCount4);
		jsonArray.add(dsjfxFilter4);	
		
		//同一处罚对象（单位）+同一违法行为发生时间+同一涉案物品+同一鉴定为
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
		filterMap.put("shijian", "Y");//同一违法行为发生时间
    	filterMap.put("wupin", "Y");//同一涉案物品
    	filterMap.put("jianding", "Y");//同一鉴定
    	Integer filterCount5 = caseService.queryDSJFXCaseCountForZHFX(paramMap,filterMap);
		JSONObject dsjfxFilter5 = new JSONObject();
		dsjfxFilter5.put("bookMarkName", "dsjfxFilter5");
		dsjfxFilter5.put("bookMarkValue", filterCount5);
		jsonArray.add(dsjfxFilter5);

		//当前用户行政区划名称
		JSONObject objectJ = new JSONObject();
		District region=districtService.selectByPrimaryKey(organise.getDistrictCode());
		objectJ.put("bookMarkName", "regionNameJ");
		objectJ.put("bookMarkValue", region.getDistrictName());
		jsonArray.add(objectJ);
		
		//标题行政区划
		JSONObject titleRegionObject = new JSONObject();
		titleRegionObject.put("bookMarkName", "titleRegion");
		titleRegionObject.put("bookMarkValue", r.getDistrictName());
		jsonArray.add(titleRegionObject);
		//标题行政区划
		JSONObject titleRegion1Object = new JSONObject();
		titleRegion1Object.put("bookMarkName", "titleRegion1");
		titleRegion1Object.put("bookMarkValue", region.getDistrictName());
		jsonArray.add(titleRegion1Object);		
		
		//当前时间
		JSONObject currentTimeObj = new JSONObject();
		currentTimeObj.put("bookMarkName", "currentTime");
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		currentTimeObj.put("bookMarkValue", format.format(new Date()));
		jsonArray.add(currentTimeObj);

		//当前登录人
		JSONObject currentOrgObject=new JSONObject();
		currentOrgObject.put("bookMarkName", "currentOrg");
		currentOrgObject.put("bookMarkValue", organise.getOrgName());
		jsonArray.add(currentOrgObject);
		view.addObject("jsonArray", jsonArray);
		
		//建议移送案件数量
				if(crimeCaseCount==0){
					JSONObject sxfzZeroObj = new JSONObject();
					sxfzZeroObj.put("bookMarkName", "sxfzZero");
					jsonArray.add(sxfzZeroObj);
				}else{
					JSONObject sxfzSuggestCountObj = new JSONObject();
					sxfzSuggestCountObj.put("bookMarkName", "sxfzSuggestCount");
					sxfzSuggestCountObj.put("bookMarkValue",suggestYiSongCount);		
					jsonArray.add(sxfzSuggestCountObj);
				}
				JSONObject jgclSuggestCaseCountObj = new JSONObject();
				jgclSuggestCaseCountObj.put("bookMarkName", "jgclSuggestCount");
				jgclSuggestCaseCountObj.put("bookMarkValue",suggestYiSongCount);		
				jsonArray.add(jgclSuggestCaseCountObj);
				JSONObject dsjSuggestCaseCountObj = new JSONObject();
				dsjSuggestCaseCountObj.put("bookMarkName", "dsjSuggestCount");
				dsjSuggestCaseCountObj.put("bookMarkValue",suggestYiSongCount);		
				jsonArray.add(dsjSuggestCaseCountObj);
				
				String joinUnitRegionScope = "";
				if(region.getJb() == 2){
					joinUnitRegionScope = "全市";
				}else if(region.getJb() == 3){
					joinUnitRegionScope = "全县";
				}
				JSONObject joinUnitRegionScopeObject=new JSONObject();
				joinUnitRegionScopeObject.put("bookMarkName", "joinUnitRegionScope");
				joinUnitRegionScopeObject.put("bookMarkValue", joinUnitRegionScope);
				jsonArray.add(joinUnitRegionScopeObject);
				
				view.addObject("jsonArray", jsonArray);
				view.addObject("districtId", regionId);
				view.addObject("yearStr", yearStr);
				view.addObject("quarterStr", quarterStr);
				view.addObject("monthStr", monthStr);
	}
	
	/**
	 * 综合分析报告(按行业、部门)
	 * author XT
	 * @param caseBasicase
	 * @param view
	 * @param request
	 */
	private void createStatZHFX2(CaseBasic caseBasicase, ModelAndView view,
			HttpServletRequest request) {
		String industryType = request.getParameter("industryType");
		String yearStr = request.getParameter("yearStr");
		String quarterStr = request.getParameter("quarterStr");
		String monthStr = request.getParameter("monthStr");
		User user = SystemContext.getCurrentUser(request);
		Organise organise = user.getOrganise();
		String timeStr="";
		if(StringUtils.isNotBlank(quarterStr)){
			timeStr=quarterStr+"季度";
		}else if(StringUtils.isNotBlank(monthStr)){
			timeStr=monthStr+"月";
		}
		String regionId=organise.getDistrictCode();
		if (StringUtils.isBlank(yearStr)) {
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy");
			yearStr = simpleDateFormat.format(new Date());
		}
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("regionId", regionId);
		paramMap.put("industryType", industryType);
		paramMap.put("yearStr", yearStr);
		paramMap.put("quarterStr", quarterStr);
		paramMap.put("monthStr", monthStr);
		JSONArray jsonArray=new JSONArray();
		//某行业行政处罚录入统计按区划
		List<Map<String, Object>> caseInputInfoForIndustryList= caseService.queryCaseInputInfoForIndustry(paramMap);
		JSONObject caseInputInfoForIndustryObject=new JSONObject();
		caseInputInfoForIndustryObject.put("bookMarkName", "caseInputInfoForIndustryList");
		caseInputInfoForIndustryObject.put("bookMarkValue", caseInputInfoForIndustryList);
		jsonArray.add(caseInputInfoForIndustryObject);
		//获得某行业涉嫌犯罪案件处理情况按区划统计 
		List<Map<String, Object>> crimeCaseInfoForIndustryList=caseService.queryCrimeCaseInfoForIndustry(paramMap);
		JSONObject crimeCaseInfoForIndustryObject=new JSONObject();
		crimeCaseInfoForIndustryObject.put("bookMarkName", "crimeCaseInfoForIndustryList");
		crimeCaseInfoForIndustryObject.put("bookMarkValue", crimeCaseInfoForIndustryList);
		jsonArray.add(crimeCaseInfoForIndustryObject);
		
		//接入单位
		District r=districtService.selectByPrimaryKey(regionId);
		Integer districtJB=r.getJb();
		districtJB = districtJB.intValue() == Const.DISTRICT_JB_2 ? null:districtJB.intValue();
		StatisData statisData = statisDataService.statisAccesOrg(null,industryType, districtJB,r.getDistrictCode());
		JSONObject joinUnitDataObj=new JSONObject();
		joinUnitDataObj.put("bookMarkName", "joinUnitData");
		joinUnitDataObj.put("bookMarkValue",getQueryTimeStr(paramMap));
		jsonArray.add(joinUnitDataObj);
		
		JSONObject XingZhengNumObj=new JSONObject();
		XingZhengNumObj.put("bookMarkName", "XingZhengNum");
		XingZhengNumObj.put("bookMarkValue",statisData.getXingZhengNum());
		jsonArray.add(XingZhengNumObj);
		
		JSONObject cityXingZhengNumObj=new JSONObject();
		cityXingZhengNumObj.put("bookMarkName", "cityXingZhengNum");
		cityXingZhengNumObj.put("bookMarkValue",statisData.getCityXingZhengNum());
		jsonArray.add(cityXingZhengNumObj);
		
		JSONObject countyXingZhengNumObj=new JSONObject();
		countyXingZhengNumObj.put("bookMarkName", "countyXingZhengNum");
		countyXingZhengNumObj.put("bookMarkValue",statisData.getCountyXingZhengNum());
		jsonArray.add(countyXingZhengNumObj);
		
		//行政处罚案件录入情况
		StatisData xzcfStatisData = statisDataService.statisCaseNumStatForReportForBusiness(paramMap);
		JSONObject xzcfDateObj=new JSONObject();
		xzcfDateObj.put("bookMarkName", "xzcfDate");
		xzcfDateObj.put("bookMarkValue",getQueryTimeStr(paramMap));
		jsonArray.add(xzcfDateObj);
		
		JSONObject penaltyNumObj=new JSONObject();
		penaltyNumObj.put("bookMarkName", "penaltyNum");
		penaltyNumObj.put("bookMarkValue",xzcfStatisData.getPenaltyNum());
		jsonArray.add(penaltyNumObj);		
		
		JSONObject amountInvolvedObj=new JSONObject();
		amountInvolvedObj.put("bookMarkName", "amountInvolved");
		amountInvolvedObj.put("bookMarkValue",xzcfStatisData.getAmountInvolved());
		jsonArray.add(amountInvolvedObj);
		
		JSONObject cityChufaNumObj=new JSONObject();
		cityChufaNumObj.put("bookMarkName", "cityChufaNum");
		cityChufaNumObj.put("bookMarkValue",xzcfStatisData.getCityChufaNum());
		jsonArray.add(cityChufaNumObj);
		
		JSONObject cityRateObj=new JSONObject();
		cityRateObj.put("bookMarkName", "cityRate");
		cityRateObj.put("bookMarkValue",xzcfStatisData.getCityRate());
		jsonArray.add(cityRateObj);	
		
		JSONObject countyChufaNumObj=new JSONObject();
		countyChufaNumObj.put("bookMarkName", "countyChufaNum");
		countyChufaNumObj.put("bookMarkValue",xzcfStatisData.getCountyChufaNum());
		jsonArray.add(countyChufaNumObj);
		
		JSONObject countyRateObj=new JSONObject();
		countyRateObj.put("bookMarkName", "countyRate");
		countyRateObj.put("bookMarkValue",xzcfStatisData.getCountyRate());
		jsonArray.add(countyRateObj);	
		
		//涉嫌犯罪案件处理情况总体情况
		Map<String,Object> allCrimeCaseDealStatis = new HashMap<String,Object>();
		allCrimeCaseDealStatis = caseService.queryAllCrimeCaseDealStatis(paramMap);
		JSONObject crimeCaseDateObj=new JSONObject();
		crimeCaseDateObj.put("bookMarkName", "crimeCaseDate");
		crimeCaseDateObj.put("bookMarkValue",getQueryTimeStr(paramMap));
		jsonArray.add(crimeCaseDateObj);

		if(allCrimeCaseDealStatis != null){
			JSONObject zjyscntObj=new JSONObject();
			zjyscntObj.put("bookMarkName", "ZJ_YS_CNT");
			zjyscntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("ZJ_YS_CNT").toString());
			jsonArray.add(zjyscntObj);
			if(allCrimeCaseDealStatis.get("YOY_ZJ_YS_CNT") != null && allCrimeCaseDealStatis.get("YOY_ZJ_YS_CNT").toString().startsWith("-")){
				JSONObject yoyzjyscntjsObj=new JSONObject();
				yoyzjyscntjsObj.put("bookMarkName", "YOY_ZJ_YS_CNT_ZZ");
				yoyzjyscntjsObj.put("bookMarkValue","减少");
				jsonArray.add(yoyzjyscntjsObj);
				
				JSONObject yoyzjyscntObj=new JSONObject();
				yoyzjyscntObj.put("bookMarkName", "YOY_ZJ_YS_CNT");
				yoyzjyscntObj.put("bookMarkValue",com.ksource.liangfa.util.StringUtils.trimPrefix(allCrimeCaseDealStatis.get("YOY_ZJ_YS_CNT").toString(),"-"));
				jsonArray.add(yoyzjyscntObj);
			}else{
				JSONObject yoyzjyscntjsObj=new JSONObject();
				yoyzjyscntjsObj.put("bookMarkName", "YOY_ZJ_YS_CNT_ZZ");
				yoyzjyscntjsObj.put("bookMarkValue","增长");
				jsonArray.add(yoyzjyscntjsObj);
				
				JSONObject yoyzjyscntObj=new JSONObject();
				yoyzjyscntObj.put("bookMarkName", "YOY_ZJ_YS_CNT");
				yoyzjyscntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("YOY_ZJ_YS_CNT").toString());
				jsonArray.add(yoyzjyscntObj);
			}
			
			JSONObject jyyscntObj=new JSONObject();
			jyyscntObj.put("bookMarkName", "JY_YS_CNT");
			jyyscntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("JY_YS_CNT").toString());
			jsonArray.add(jyyscntObj);
			if(allCrimeCaseDealStatis.get("YOY_JY_YS_CNT") != null && allCrimeCaseDealStatis.get("YOY_JY_YS_CNT").toString().startsWith("-")){
				JSONObject yoyjyyscntjsObj=new JSONObject();
				yoyjyyscntjsObj.put("bookMarkName", "YOY_JY_YS_CNT_ZZ");
				yoyjyyscntjsObj.put("bookMarkValue","减少");
				jsonArray.add(yoyjyyscntjsObj);
				
				JSONObject yoyjyyscntObj=new JSONObject();
				yoyjyyscntObj.put("bookMarkName", "YOY_JY_YS_CNT");
				yoyjyyscntObj.put("bookMarkValue",com.ksource.liangfa.util.StringUtils.trimPrefix(allCrimeCaseDealStatis.get("YOY_JY_YS_CNT").toString(),"-"));
				jsonArray.add(yoyjyyscntObj);
			}else{
				JSONObject yoyjyyscntjsObj=new JSONObject();
				yoyjyyscntjsObj.put("bookMarkName", "YOY_JY_YS_CNT_ZZ");
				yoyjyyscntjsObj.put("bookMarkValue","增长");
				jsonArray.add(yoyjyyscntjsObj);
				
				JSONObject yoyjyyscntObj=new JSONObject();
				yoyjyyscntObj.put("bookMarkName", "YOY_JY_YS_CNT");
				yoyjyyscntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("YOY_JY_YS_CNT").toString());
				jsonArray.add(yoyjyyscntObj);
			}
			
			JSONObject acceptcntObj=new JSONObject();
			acceptcntObj.put("bookMarkName", "ACCEPT_CNT");
			acceptcntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("ACCEPT_CNT").toString());
			jsonArray.add(acceptcntObj);
			JSONObject liancntObj=new JSONObject();
			liancntObj.put("bookMarkName", "LIAN_CNT");
			liancntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("LIAN_CNT").toString());
			jsonArray.add(liancntObj);		
			
			if(allCrimeCaseDealStatis.get("YOY_LIAN_CNT") != null && allCrimeCaseDealStatis.get("YOY_LIAN_CNT").toString().startsWith("-")){
				JSONObject yoyliancntjsObj=new JSONObject();
				yoyliancntjsObj.put("bookMarkName", "YOY_LIAN_CNT_ZZ");
				yoyliancntjsObj.put("bookMarkValue","减少");
				jsonArray.add(yoyliancntjsObj);
				
				JSONObject yoyliancntObj=new JSONObject();
				yoyliancntObj.put("bookMarkName", "YOY_LIAN_CNT");
				yoyliancntObj.put("bookMarkValue",com.ksource.liangfa.util.StringUtils.trimPrefix(allCrimeCaseDealStatis.get("YOY_LIAN_CNT").toString(),"-"));
				jsonArray.add(yoyliancntObj);			
			}else{
				JSONObject yoyliancntjsObj=new JSONObject();
				yoyliancntjsObj.put("bookMarkName", "YOY_LIAN_CNT_ZZ");
				yoyliancntjsObj.put("bookMarkValue","增长");
				jsonArray.add(yoyliancntjsObj);
				
				JSONObject yoyliancntObj=new JSONObject();
				yoyliancntObj.put("bookMarkName", "YOY_LIAN_CNT");
				yoyliancntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("YOY_LIAN_CNT").toString());
				jsonArray.add(yoyliancntObj);	
			}
			JSONObject tqdbcntObj=new JSONObject();
			tqdbcntObj.put("bookMarkName", "TQDB_CNT");
			tqdbcntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("TQDB_CNT").toString());
			jsonArray.add(tqdbcntObj);
			if(allCrimeCaseDealStatis.get("YOY_TQDB_CNT") != null && allCrimeCaseDealStatis.get("YOY_TQDB_CNT").toString().startsWith("-")){
				JSONObject yoytqdbcntjsObj=new JSONObject();
				yoytqdbcntjsObj.put("bookMarkName", "YOY_TQDB_CNT_ZZ");
				yoytqdbcntjsObj.put("bookMarkValue","减少");
				jsonArray.add(yoytqdbcntjsObj);
				
				JSONObject yoytqdbcntObj=new JSONObject();
				yoytqdbcntObj.put("bookMarkName", "YOY_TQDB_CNT");
				yoytqdbcntObj.put("bookMarkValue",com.ksource.liangfa.util.StringUtils.trimPrefix(allCrimeCaseDealStatis.get("YOY_TQDB_CNT").toString(),"-"));
				jsonArray.add(yoytqdbcntObj);	
			}else{
				JSONObject yoytqdbcntjsObj=new JSONObject();
				yoytqdbcntjsObj.put("bookMarkName", "YOY_TQDB_CNT_ZZ");
				yoytqdbcntjsObj.put("bookMarkValue","增长");
				jsonArray.add(yoytqdbcntjsObj);
				
				JSONObject yoytqdbcntObj=new JSONObject();
				yoytqdbcntObj.put("bookMarkName", "YOY_TQDB_CNT");
				yoytqdbcntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("YOY_TQDB_CNT").toString());
				jsonArray.add(yoytqdbcntObj);	
			}	
			
			JSONObject qscntObj=new JSONObject();
			qscntObj.put("bookMarkName", "QISU_CNT");
			qscntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("QISU_CNT").toString());
			jsonArray.add(qscntObj);
			if(allCrimeCaseDealStatis.get("YOY_QISU_CNT") != null && allCrimeCaseDealStatis.get("YOY_QISU_CNT").toString().startsWith("-")){
				JSONObject yoyqscntjsObj=new JSONObject();
				yoyqscntjsObj.put("bookMarkName", "YOY_QISU_CNT_ZZ");
				yoyqscntjsObj.put("bookMarkValue","减少");
				jsonArray.add(yoyqscntjsObj);
				
				JSONObject yoyqscntObj=new JSONObject();
				yoyqscntObj.put("bookMarkName", "YOY_QISU_CNT");
				yoyqscntObj.put("bookMarkValue",com.ksource.liangfa.util.StringUtils.trimPrefix(allCrimeCaseDealStatis.get("YOY_QISU_CNT").toString(),"-"));
				jsonArray.add(yoyqscntObj);			
			}else{
				JSONObject yoyqscntjsObj=new JSONObject();
				yoyqscntjsObj.put("bookMarkName", "YOY_QISU_CNT_ZZ");
				yoyqscntjsObj.put("bookMarkValue","增长");
				jsonArray.add(yoyqscntjsObj);
				
				JSONObject yoyqscntObj=new JSONObject();
				yoyqscntObj.put("bookMarkName", "YOY_QISU_CNT");
				yoyqscntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("YOY_QISU_CNT").toString());
				jsonArray.add(yoyqscntObj);	
			}	
			
			JSONObject pjcntObj=new JSONObject();
			pjcntObj.put("bookMarkName", "PANJUE_CNT");
			pjcntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("PANJUE_CNT").toString());
			jsonArray.add(pjcntObj);
			
			if(allCrimeCaseDealStatis.get("YOY_PANJUE_CNT") != null && allCrimeCaseDealStatis.get("YOY_PANJUE_CNT").toString().startsWith("-")){
				JSONObject yoypjcntjsObj=new JSONObject();
				yoypjcntjsObj.put("bookMarkName", "YOY_PANJUE_CNT_ZZ");
				yoypjcntjsObj.put("bookMarkValue","减少");
				jsonArray.add(yoypjcntjsObj);
				
				JSONObject yoypjcntObj=new JSONObject();
				yoypjcntObj.put("bookMarkName", "YOY_PANJUE_CNT");
				yoypjcntObj.put("bookMarkValue",com.ksource.liangfa.util.StringUtils.trimPrefix(allCrimeCaseDealStatis.get("YOY_PANJUE_CNT").toString(),"-"));
				jsonArray.add(yoypjcntObj);	
			}else{
				JSONObject yoypjcntjsObj=new JSONObject();
				yoypjcntjsObj.put("bookMarkName", "YOY_PANJUE_CNT_ZZ");
				yoypjcntjsObj.put("bookMarkValue","增长");
				jsonArray.add(yoypjcntjsObj);
				
				JSONObject yoypjcntObj=new JSONObject();
				yoypjcntObj.put("bookMarkName", "YOY_PANJUE_CNT");
				yoypjcntObj.put("bookMarkValue",allCrimeCaseDealStatis.get("YOY_PANJUE_CNT").toString());
				jsonArray.add(yoypjcntObj);	
			}
		}else{
			JSONObject zjyscntObj=new JSONObject();
			zjyscntObj.put("bookMarkName", "ZJ_YS_CNT");
			zjyscntObj.put("bookMarkValue",0);
			jsonArray.add(zjyscntObj);
			JSONObject yoyzjyscntjsObj=new JSONObject();
			yoyzjyscntjsObj.put("bookMarkName", "YOY_ZJ_YS_CNT_ZZ");
			yoyzjyscntjsObj.put("bookMarkValue","增长");
			jsonArray.add(yoyzjyscntjsObj);
			
			JSONObject yoyzjyscntObj=new JSONObject();
			yoyzjyscntObj.put("bookMarkName", "YOY_ZJ_YS_CNT");
			yoyzjyscntObj.put("bookMarkValue",0);
			jsonArray.add(yoyzjyscntObj);
			
			
			JSONObject jyyscntObj=new JSONObject();
			jyyscntObj.put("bookMarkName", "JY_YS_CNT");
			jyyscntObj.put("bookMarkValue",0);
			jsonArray.add(jyyscntObj);

			JSONObject yoyjyyscntjsObj=new JSONObject();
			yoyjyyscntjsObj.put("bookMarkName", "YOY_JY_YS_CNT_ZZ");
			yoyjyyscntjsObj.put("bookMarkValue","增长");
			jsonArray.add(yoyjyyscntjsObj);
			
			JSONObject yoyjyyscntObj=new JSONObject();
			yoyjyyscntObj.put("bookMarkName", "YOY_JY_YS_CNT");
			yoyjyyscntObj.put("bookMarkValue",0);
			jsonArray.add(yoyjyyscntObj);
			
			JSONObject acceptcntObj=new JSONObject();
			acceptcntObj.put("bookMarkName", "ACCEPT_CNT");
			acceptcntObj.put("bookMarkValue",0);
			jsonArray.add(acceptcntObj);
			JSONObject liancntObj=new JSONObject();
			liancntObj.put("bookMarkName", "LIAN_CNT");
			liancntObj.put("bookMarkValue",0);
			jsonArray.add(liancntObj);		
			
			JSONObject yoyliancntjsObj=new JSONObject();
			yoyliancntjsObj.put("bookMarkName", "YOY_LIAN_CNT_ZZ");
			yoyliancntjsObj.put("bookMarkValue","增长");
			jsonArray.add(yoyliancntjsObj);
			
			JSONObject yoyliancntObj=new JSONObject();
			yoyliancntObj.put("bookMarkName", "YOY_LIAN_CNT");
			yoyliancntObj.put("bookMarkValue",0);
			jsonArray.add(yoyliancntObj);	
			
			JSONObject tqdbcntObj=new JSONObject();
			tqdbcntObj.put("bookMarkName", "TQDB_CNT");
			tqdbcntObj.put("bookMarkValue",0);
			jsonArray.add(tqdbcntObj);

			JSONObject yoytqdbcntjsObj=new JSONObject();
			yoytqdbcntjsObj.put("bookMarkName", "YOY_TQDB_CNT_ZZ");
			yoytqdbcntjsObj.put("bookMarkValue","增长");
			jsonArray.add(yoytqdbcntjsObj);
			
			JSONObject yoytqdbcntObj=new JSONObject();
			yoytqdbcntObj.put("bookMarkName", "YOY_TQDB_CNT");
			yoytqdbcntObj.put("bookMarkValue",0);
			jsonArray.add(yoytqdbcntObj);	
				
			JSONObject qscntObj=new JSONObject();
			qscntObj.put("bookMarkName", "QISU_CNT");
			qscntObj.put("bookMarkValue",0);
			jsonArray.add(qscntObj);

			JSONObject yoyqscntjsObj=new JSONObject();
			yoyqscntjsObj.put("bookMarkName", "YOY_QISU_CNT_ZZ");
			yoyqscntjsObj.put("bookMarkValue","增长");
			jsonArray.add(yoyqscntjsObj);
			
			JSONObject yoyqscntObj=new JSONObject();
			yoyqscntObj.put("bookMarkName", "YOY_QISU_CNT");
			yoyqscntObj.put("bookMarkValue",0);
			jsonArray.add(yoyqscntObj);	
				
			JSONObject pjcntObj=new JSONObject();
			pjcntObj.put("bookMarkName", "PANJUE_CNT");
			pjcntObj.put("bookMarkValue",0);
			jsonArray.add(pjcntObj);

			JSONObject yoypjcntjsObj=new JSONObject();
			yoypjcntjsObj.put("bookMarkName", "YOY_PANJUE_CNT_ZZ");
			yoypjcntjsObj.put("bookMarkValue","增长");
			jsonArray.add(yoypjcntjsObj);
			
			JSONObject yoypjcntObj=new JSONObject();
			yoypjcntObj.put("bookMarkName", "YOY_PANJUE_CNT");
			yoypjcntObj.put("bookMarkValue",0);
			jsonArray.add(yoypjcntObj);	
		}
		
		String queryTimeStr=getQueryTimeStr(paramMap);
		//查询时间字符串
		JSONObject queryTimeFullStrObject=new JSONObject();
		queryTimeFullStrObject.put("bookMarkName", "queryTimeFullStr");
		queryTimeFullStrObject.put("bookMarkValue", queryTimeStr);
		jsonArray.add(queryTimeFullStrObject);
		
		//当前时间
		JSONObject currentTimeObj = new JSONObject();
		currentTimeObj.put("bookMarkName", "currentTime");
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		currentTimeObj.put("bookMarkValue", format.format(new Date()));
		jsonArray.add(currentTimeObj);
		
		
		//行业部门
		String industryName = request.getParameter("industryName");
		JSONObject industryNameObject=new JSONObject();
		industryNameObject.put("bookMarkName", "industryName");
		industryNameObject.put("bookMarkValue", industryName);
		jsonArray.add(industryNameObject);
		JSONObject industryName1Object=new JSONObject();
		industryName1Object.put("bookMarkName", "industryName1");
		industryName1Object.put("bookMarkValue", industryName);
		jsonArray.add(industryName1Object);
		JSONObject industryName2Object=new JSONObject();
		industryName2Object.put("bookMarkName", "industryName2");
		industryName2Object.put("bookMarkValue", industryName);
		jsonArray.add(industryName2Object);
		
		
		//当前区划
		District currentRegion = districtService.selectByPrimaryKey(regionId);
		String currentRegionName = currentRegion.getDistrictName();
		JSONObject titleRegionObject=new JSONObject();
		titleRegionObject.put("bookMarkName", "titleRegion");
		titleRegionObject.put("bookMarkValue", currentRegionName);
		jsonArray.add(titleRegionObject);
		JSONObject titleRegion1Object=new JSONObject();
		titleRegion1Object.put("bookMarkName", "titleRegion1");
		titleRegion1Object.put("bookMarkValue", currentRegionName);
		jsonArray.add(titleRegion1Object);
		String joinUnitRegionScope = "";
		if(currentRegion.getJb() == 1){
			joinUnitRegionScope = "全省";
		}else if(currentRegion.getJb() == 2){
			joinUnitRegionScope = "全市";
		}else if(currentRegion.getJb() == 3){
			joinUnitRegionScope = "全县";
		}
		JSONObject joinUnitRegionScopeObject=new JSONObject();
		joinUnitRegionScopeObject.put("bookMarkName", "joinUnitRegionScope");
		joinUnitRegionScopeObject.put("bookMarkValue", joinUnitRegionScope);
		jsonArray.add(joinUnitRegionScopeObject);
		//当前登录人
		JSONObject currentOrgObject=new JSONObject();
		currentOrgObject.put("bookMarkName", "currentOrg");
		currentOrgObject.put("bookMarkValue", organise.getOrgName());
		jsonArray.add(currentOrgObject);
		view.addObject("jsonArray", jsonArray);
	}
	
	
	/**
	 * author XT
	 * @param paramMap
	 */
	private String getQueryTimeStr(Map<String, Object> paramMap) {
		StringBuffer queryTimeStrBuffer=new StringBuffer(paramMap.get("yearStr")+"年度");
		Object quarterObject=paramMap.get("quarterStr");
		String quarter = quarterObject==null?"":quarterObject.toString();
		if (StringUtils.isNotBlank(quarter)) {
			queryTimeStrBuffer.append(quarter+"季度");
		}
		Object monthObject=paramMap.get("monthStr");
		String month = monthObject==null?"":monthObject.toString();
		if (StringUtils.isNotBlank(month)) {
			queryTimeStrBuffer.append(month+"月份");
		}
		return queryTimeStrBuffer.toString();
	}
	
	
	/**
	 * 构建降格处理案件线索筛查（总1）
	 *
	 * @param docType 报告类型
	 * @param endTime 筛选时间范围
	 * @param startTime 筛选时间范围
	 * @param districtName 筛选范围
	 */
	private void createJGCLForRespect(CaseBasic caseBasic,
			ModelAndView view, HttpServletRequest request) {
        Integer districtQueryScope = null;
        if(StringUtils.isNotBlank(request.getParameter("districtQueryScope"))){
            districtQueryScope = Integer.valueOf(request.getParameter("districtQueryScope"));
        }
		JSONArray jsonArray = new JSONArray();
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
		String districtName = caseBasic.getDistrictName();
		String districtId = caseBasic.getDistrictId();
		District currDistrict = districtService.selectByPrimaryKey(organise.getDistrictCode());		
		//构建筛选范围
		if(StringUtils.isBlank(districtId)){
			districtName = currDistrict.getDistrictName();
			districtId = StringUtils.rightTrim0(organise.getDistrictCode());
		}else{
		    if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
		        districtId = StringUtils.rightTrim0(caseBasic.getDistrictId());
		    }
		}
		caseBasic.setDistrictId(districtId);
		JSONObject object = new JSONObject();
		object.put("bookMarkName", "regionName");
		object.put("bookMarkValue", districtName);
		jsonArray.add(object);
		JSONObject regionNameS = new JSONObject();
		regionNameS.put("bookMarkName", "regionNameS");
		regionNameS.put("bookMarkValue", districtName.substring(districtName.length()-1));
		jsonArray.add(regionNameS);
		//构建时间范围
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		Date startTime = caseBasic.getMinCaseInputTime();
		if(startTime == null){
			caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			startTime = caseService.queryMinCaseInputTime(caseBasic);
		}
		JSONObject startTimeObj = new JSONObject();
		startTimeObj.put("bookMarkName", "startTime");
		startTimeObj.put("bookMarkValue", dateFormat.format(startTime));
		jsonArray.add(startTimeObj);
		Date endTime = caseBasic.getMaxCaseInputTime();
		if(endTime == null){
			endTime = new Date();
		}
		JSONObject endTimeObj = new JSONObject();
		endTimeObj.put("bookMarkName", "endTime");
		endTimeObj.put("bookMarkValue", dateFormat.format(endTime));
		jsonArray.add(endTimeObj);
		//构建总数
		caseBasic.setChufaState(Const.CHUFA_STATE_YES);
		int totalNum = caseService.queryCaseCount(caseBasic);
		JSONObject totalNumObj = new JSONObject();
		totalNumObj.put("bookMarkName", "totalNum");
		totalNumObj.put("bookMarkValue", totalNum);	
		jsonArray.add(totalNumObj);
		//构建符合帅选条件的降格处理案件
		Map<String,Object> paramMap = new HashMap<String, Object>();
		CaseFilter caseFilter = new CaseFilter();
        CaseFilterExample example=new CaseFilterExample();
        Integer orgCode=SystemContext.getCurrentUser(request).getOrgId();
		example.createCriteria().andOrgCodeEqualTo(orgCode);
		List<CaseFilter> list = mybatisAutoMapperService.selectByExample(CaseFilterMapper.class, example, CaseFilter.class);
		int count = list.size();
		if(count >= 1){
			caseFilter=list.get(0);
		}
		if(caseFilter!=null){
			if(caseFilter.getIsDiscussCase()!=null){
				paramMap.put("isDescuss", caseFilter.getIsDiscussCase());
			}
			if(caseFilter.getIsSeriousCase()!=null){
				paramMap.put("isSeriousCase", caseFilter.getIsSeriousCase());
			}
			if(caseFilter.getIsBeyondEighty()!=null){
				paramMap.put("isBeyondEighty", caseFilter.getIsBeyondEighty());
			}
			if(caseFilter.getChufaTimes()!=null){
				paramMap.put("chufaTimes", caseFilter.getChufaTimes());
			}
			if(caseFilter.getIsLowerLimitMoney()!=null){
				paramMap.put("isLowerLimitMoney", caseFilter.getIsLowerLimitMoney());
			}
			if(caseFilter.getIsIdentify()!=null){
				paramMap.put("isIdentify", caseFilter.getIsIdentify());
			}
		}
    	paramMap.put("minCaseInputTime", caseBasic.getMinCaseInputTime());
    	paramMap.put("maxCaseInputTime", caseBasic.getMaxCaseInputTime());
    	paramMap.put("districtId",com.ksource.liangfa.util.StringUtils.rightTrim0(districtId));
		List<CaseBasic> caseList = caseService.queryfilingSupervisionCase(paramMap,null);
		JSONObject filterObj = new JSONObject();
		filterObj.put("bookMarkName", "filter");
		filterObj.put("bookMarkValue", caseList.size());	
		jsonArray.add(filterObj);
		List<CaseBasic> filterList_6 = new ArrayList<CaseBasic>();
		List<CaseBasic> filterList_5 = new ArrayList<CaseBasic>();
		List<CaseBasic> filterList_4 = new ArrayList<CaseBasic>();
		List<CaseBasic> filterList_3 = new ArrayList<CaseBasic>();
		List<CaseBasic> filterList_2 = new ArrayList<CaseBasic>();
		for(CaseBasic c:caseList){
			int num = 0;
			if(c.getIsDescuss() != null && c.getIsDescuss() == 1){
				num += 1;
			}
			if(c.getIsSeriousCase() != null && c.getIsSeriousCase() == 1){
				num += 1;
			}
			if(c.getIsBeyondEighty() !=null && c.getIsBeyondEighty() == 1){
				num += 1;
			}
			if(c.getChufaTimes() != null && c.getChufaTimes() > 1){
				num += 1;
			}
			if(c.getIsLowerLimitMoney() != null && c.getIsLowerLimitMoney() == 1){
				num += 1;
			}
			if(c.getIdentifyType() !=null && c.getIdentifyType() < 6){
				num += 1;
			}
			if(num == 6){
				filterList_6.add(caseBasic);
			}else if(num == 5){
				filterList_5.add(caseBasic);
			}else if(num == 4){
				filterList_4.add(caseBasic);
			}else if(num == 3){
				filterList_3.add(caseBasic);
			}else if(num == 2){
				filterList_2.add(caseBasic);
			}
		}
		JSONObject filterObj6 = new JSONObject();
		filterObj6.put("bookMarkName", "filter6");
		filterObj6.put("bookMarkValue", filterList_6.size());	
		jsonArray.add(filterObj6);
		JSONObject filterObj5 = new JSONObject();
		filterObj5.put("bookMarkName", "filter5");
		filterObj5.put("bookMarkValue", filterList_5.size());	
		jsonArray.add(filterObj5);
		JSONObject filterObj4 = new JSONObject();
		filterObj4.put("bookMarkName", "filter4");
		filterObj4.put("bookMarkValue", filterList_4.size());	
		jsonArray.add(filterObj4);
		JSONObject filterObj3 = new JSONObject();
		filterObj3.put("bookMarkName", "filter3");
		filterObj3.put("bookMarkValue", filterList_3.size());	
		jsonArray.add(filterObj3);
		JSONObject filterObj2 = new JSONObject();
		filterObj2.put("bookMarkName", "filter2");
		filterObj2.put("bookMarkValue", filterList_2.size());	
		jsonArray.add(filterObj2);
		
		JSONObject objectJ = new JSONObject();
		objectJ.put("bookMarkName", "regionNameJ");
		objectJ.put("bookMarkValue", currDistrict.getDistrictName());
		jsonArray.add(objectJ);
		
		JSONObject currentTimeObj = new JSONObject();
		currentTimeObj.put("bookMarkName", "currentTime");
		currentTimeObj.put("bookMarkValue", dateFormat.format(new Date()));
		jsonArray.add(currentTimeObj);
		
		view.addObject("jsonArray", jsonArray);		
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		view.addObject("districtId", districtId);
		view.addObject("startTime", format.format(startTime));
		view.addObject("endTime", format.format(endTime));	
		
	}
	
	private void createJGCLForPoint(CaseBasic caseBasic, ModelAndView view,
			HttpServletRequest request) {
        Integer districtQueryScope = null;
        if(StringUtils.isNotBlank(request.getParameter("districtQueryScope"))){
            districtQueryScope = Integer.valueOf(request.getParameter("districtQueryScope"));
        }
	    JSONArray jsonArray = new JSONArray();
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
		String districtName = caseBasic.getDistrictName();
		String districtId = caseBasic.getDistrictId();
		District currDistrict = districtService.selectByPrimaryKey(organise.getDistrictCode());		
		//构建筛选范围
		if(StringUtils.isBlank(districtId)){
			districtName = currDistrict.getDistrictName();
			districtId = StringUtils.rightTrim0(organise.getDistrictCode());
		}else{
		    if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
		        districtId = StringUtils.rightTrim0(districtId);
		    }
		}
		caseBasic.setDistrictId(districtId);
		JSONObject object = new JSONObject();
		object.put("bookMarkName", "regionName");
		object.put("bookMarkValue", districtName);
		jsonArray.add(object);
		//构建时间范围
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		Date startTime = caseBasic.getMinCaseInputTime();
		if(startTime == null){
			caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			startTime = caseService.queryMinCaseInputTime(caseBasic);
		}
		JSONObject startTimeObj = new JSONObject();
		startTimeObj.put("bookMarkName", "startTime");
		startTimeObj.put("bookMarkValue", dateFormat.format(startTime));
		jsonArray.add(startTimeObj);
		Date endTime = caseBasic.getMaxCaseInputTime();
		if(endTime == null){
			endTime = new Date();
		}
		JSONObject endTimeObj = new JSONObject();
		endTimeObj.put("bookMarkName", "endTime");
		endTimeObj.put("bookMarkValue", dateFormat.format(endTime));
		jsonArray.add(endTimeObj);
		//构建总数
		caseBasic.setChufaState(Const.CHUFA_STATE_YES);
		int totalNum = caseService.queryCaseCount(caseBasic);
		JSONObject totalNumObj = new JSONObject();
		totalNumObj.put("bookMarkName", "totalNum");
		totalNumObj.put("bookMarkValue", totalNum);	
		jsonArray.add(totalNumObj);
		//构建符合帅选条件的降格处理案件
		Map<String,Object> paramMap = new HashMap<String, Object>();
		CaseFilter caseFilter = new CaseFilter();
        CaseFilterExample example=new CaseFilterExample();
        Integer orgCode=SystemContext.getCurrentUser(request).getOrgId();
		example.createCriteria().andOrgCodeEqualTo(orgCode);
		List<CaseFilter> list=mybatisAutoMapperService.selectByExample(CaseFilterMapper.class, example, CaseFilter.class);
		int count=list.size();
		if(count>=1){
			caseFilter=list.get(0);
		}
		if(caseFilter!=null){
			if(caseFilter.getIsDiscussCase()!=null){
				paramMap.put("isDescuss", caseFilter.getIsDiscussCase());
			}
			if(caseFilter.getIsSeriousCase()!=null){
				paramMap.put("isSeriousCase", caseFilter.getIsSeriousCase());
			}
			if(caseFilter.getIsBeyondEighty()!=null){
				paramMap.put("isBeyondEighty", caseFilter.getIsBeyondEighty());
			}
			if(caseFilter.getChufaTimes()!=null){
				paramMap.put("chufaTimes", caseFilter.getChufaTimes());
			}
			if(caseFilter.getIsLowerLimitMoney()!=null){
				paramMap.put("isLowerLimitMoney", caseFilter.getIsLowerLimitMoney());
			}
			if(caseFilter.getIsIdentify()!=null){
				paramMap.put("isIdentify", caseFilter.getIsIdentify());
			}
		}
    	paramMap.put("minCaseInputTime", caseBasic.getMinCaseInputTime());
    	paramMap.put("maxCaseInputTime", caseBasic.getMaxCaseInputTime());
    	paramMap.put("districtId",districtId);
		Integer filterCount = caseService.queryfilingSupervisionCaseCount(paramMap,null);
		JSONObject filterObj = new JSONObject();
		filterObj.put("bookMarkName", "filter");
		filterObj.put("bookMarkValue", filterCount);	
		jsonArray.add(filterObj);


		Map<String,Object> filterMap = new HashMap<String, Object>();
		//涉案金额达到刑事追诉标准80%以上+经过负责人集体讨论+鉴定
		filterMap.put("isDescussQ", 1);
		filterMap.put("isBeyondEightyQ", 1);
		filterMap.put("isIdentifyQ", 1);
		Integer filterCount1 = caseService.queryfilingSupervisionCaseCount(paramMap,filterMap);
		JSONObject filterObj1 = new JSONObject();
		filterObj1.put("bookMarkName", "filter1");
		filterObj1.put("bookMarkValue", filterCount1);	
		jsonArray.add(filterObj1);

		//受过2次以上行政处罚 +经过负责人集体讨论+鉴定
		paramMap.remove("isDescussQ");
		paramMap.remove("isBeyondEightyQ");
		paramMap.remove("isIdentifyQ");
		filterMap.clear();
		filterMap.put("isDescussQ", 1);
		filterMap.put("chufaTimesQ",1);
		filterMap.put("isIdentifyQ", 1);
		Integer filterCount2 = caseService.queryfilingSupervisionCaseCount(paramMap,filterMap);
		JSONObject filterObj2 = new JSONObject();
		filterObj2.put("bookMarkName", "filter2");
		filterObj2.put("bookMarkValue", filterCount2);	
		jsonArray.add(filterObj2);
		
		//情节严重 +以涉案金额达到刑事追诉标准80%以上+经过负责人集体讨论+鉴定
		paramMap.remove("isDescussQ");
		paramMap.remove("chufaTimesQ");
		paramMap.remove("isIdentifyQ");
		filterMap.clear();
		filterMap.put("isDescussQ", 1);
		filterMap.put("isSeriousCaseQ", 1);
		filterMap.put("isBeyondEightyQ", 1);
		filterMap.put("isIdentifyQ", 1);
		Integer filterCount3 = caseService.queryfilingSupervisionCaseCount(paramMap,filterMap);
		JSONObject filterObj3 = new JSONObject();
		filterObj3.put("bookMarkName", "filter3");
		filterObj3.put("bookMarkValue", filterCount3);	
		jsonArray.add(filterObj3);	
		
		
		//自选条件
		paramMap.remove("isDescussQ");
		paramMap.remove("isSeriousCaseQ");
		paramMap.remove("isBeyondEightyQ");
		paramMap.remove("isIdentifyQ");
		filterMap.clear();
		String filterName = "";
		if(caseBasic.getIsDiscussCase() != null && caseBasic.getIsDiscussCase() == 1){
			filterName += "经过负责人集体讨论+";
			filterMap.put("isDescussQ", caseBasic.getIsDiscussCase());
			view.addObject("isDescuss", caseBasic.getIsDiscussCase());
		}
		if(caseBasic.getChufaTimes() != null && caseBasic.getChufaTimes() > 0){
			filterName += "处罚次数大于"+caseBasic.getChufaTimes()+"+";
			filterMap.put("chufaTimesQ", caseBasic.getChufaTimes());
			view.addObject("chufaTimes", caseBasic.getChufaTimes());
		}
		if(caseBasic.getIsSeriousCase() != null && caseBasic.getIsSeriousCase() == 1){
			filterName += "情节严重+";
			filterMap.put("isSeriousCaseQ", caseBasic.getIsSeriousCase());
			view.addObject("isSeriousCase", caseBasic.getIsSeriousCase());
		}
		if(caseBasic.getIsBeyondEighty() != null && caseBasic.getIsBeyondEighty() == 1){
			filterName += "涉案金额达到刑事追诉标准80%以上+";
			filterMap.put("isBeyondEightyQ", caseBasic.getIsBeyondEighty());
			view.addObject("isBeyondEighty", caseBasic.getIsBeyondEighty());
		}
		if(caseBasic.getIsIdentify() != null && caseBasic.getIsIdentify() == 1){
			filterName += "鉴定+";
			filterMap.put("isIdentifyQ", caseBasic.getIsIdentify());
			view.addObject("isIdentify", caseBasic.getIsIdentify());
		}
		if(caseBasic.getIsLowerLimitMoney() != null && caseBasic.getIsLowerLimitMoney() == 1){
			filterName += "低于行政处罚规定的下限金额  +";
			filterMap.put("isLowerLimitMoneyQ", caseBasic.getIsLowerLimitMoney());
			view.addObject("isLowerLimitMoney", caseBasic.getIsLowerLimitMoney());
		}	
		view.addObject("filterMapSize",filterMap.size());
		filterName = com.ksource.liangfa.util.StringUtils.trimPrefix(filterName, "+");
		filterName = com.ksource.liangfa.util.StringUtils.trimSufffix(filterName, "+");
		filterName = "“"+filterName+"”";
		JSONObject filterNameObj = new JSONObject();
		filterNameObj.put("bookMarkName", "filterName");
		filterNameObj.put("bookMarkValue", filterName);	
		jsonArray.add(filterNameObj);
		Integer filterCount4 =0;
		if(!filterMap.isEmpty()){
			filterCount4 = caseService.queryfilingSupervisionCaseCount(paramMap,filterMap);
		}
		
		JSONObject filterObj4 = new JSONObject();
		filterObj4.put("bookMarkName", "filter4");
		filterObj4.put("bookMarkValue", filterCount4);	
		jsonArray.add(filterObj4);
		
		JSONObject objectJ = new JSONObject();
		objectJ.put("bookMarkName", "regionNameJ");
		objectJ.put("bookMarkValue", currDistrict.getDistrictName());
		jsonArray.add(objectJ);
		
		JSONObject currentTimeObj = new JSONObject();
		currentTimeObj.put("bookMarkName", "currentTime");
		currentTimeObj.put("bookMarkValue", dateFormat.format(new Date()));
		jsonArray.add(currentTimeObj);
		
		view.addObject("jsonArray", jsonArray);				
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		view.addObject("districtId", districtId);
		view.addObject("startTime", format.format(startTime));
		view.addObject("endTime", format.format(endTime));
	}
	
	private void createGCLForAlowPoint(CaseBasic caseBasic,
			ModelAndView view, HttpServletRequest request) {
        Integer districtQueryScope = null;
        if(StringUtils.isNotBlank(request.getParameter("districtQueryScope"))){
            districtQueryScope = Integer.valueOf(request.getParameter("districtQueryScope"));
        }
	    JSONArray jsonArray = new JSONArray();
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
		String districtName = caseBasic.getDistrictName();
		String districtId = caseBasic.getDistrictId();
		District currDistrict = districtService.selectByPrimaryKey(organise.getDistrictCode());		
		//构建筛选范围
		if(StringUtils.isBlank(districtId)){
			districtName = currDistrict.getDistrictName();
			districtId = StringUtils.rightTrim0(organise.getDistrictCode());
		}else{
          if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
                districtId = StringUtils.rightTrim0(districtId);
            }else{
                districtId = caseBasic.getDistrictId();  
            }
		}
		caseBasic.setDistrictId(districtId);
		JSONObject object = new JSONObject();
		object.put("bookMarkName", "regionName");
		object.put("bookMarkValue", districtName);
		jsonArray.add(object);
		JSONObject regionNameS = new JSONObject();
		regionNameS.put("bookMarkName", "regionNameS");
		regionNameS.put("bookMarkValue", districtName.substring(districtName.length()-1));
		jsonArray.add(regionNameS);
		//构建时间范围
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		Date startTime = caseBasic.getMinCaseInputTime();
		if(startTime == null){
			caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			startTime = caseService.queryMinCaseInputTime(caseBasic);
		}
		JSONObject startTimeObj = new JSONObject();
		startTimeObj.put("bookMarkName", "startTime");
		startTimeObj.put("bookMarkValue", dateFormat.format(startTime));
		jsonArray.add(startTimeObj);
		Date endTime = caseBasic.getMaxCaseInputTime();
		if(endTime == null){
			endTime = new Date();
		}
		JSONObject endTimeObj = new JSONObject();
		endTimeObj.put("bookMarkName", "endTime");
		endTimeObj.put("bookMarkValue", dateFormat.format(endTime));
		jsonArray.add(endTimeObj);
		//构建总数
		caseBasic.setMinCaseInputTime(startTime);
		caseBasic.setMaxCaseInputTime(endTime);
		caseBasic.setChufaState(Const.CHUFA_STATE_YES);
		int totalNum = caseService.queryCaseCount(caseBasic);
		JSONObject totalNumObj = new JSONObject();
		totalNumObj.put("bookMarkName", "totalNum");
		totalNumObj.put("bookMarkValue", totalNum);	
		jsonArray.add(totalNumObj);
		//构建符合帅选条件的降格处理案件
		Map<String,Object> paramMap = new HashMap<String, Object>();
		CaseFilter caseFilter = new CaseFilter();
        CaseFilterExample example=new CaseFilterExample();
        Integer orgCode=SystemContext.getCurrentUser(request).getOrgId();
		example.createCriteria().andOrgCodeEqualTo(orgCode);
		List<CaseFilter> list=mybatisAutoMapperService.selectByExample(CaseFilterMapper.class, example, CaseFilter.class);
		int count=list.size();
		if(count>=1){
			caseFilter=list.get(0);
		}
		if(caseFilter!=null){
			if(caseFilter.getIsDiscussCase()!=null){
				paramMap.put("isDescuss", caseFilter.getIsDiscussCase());
			}
			if(caseFilter.getIsSeriousCase()!=null){
				paramMap.put("isSeriousCase", caseFilter.getIsSeriousCase());
			}
			if(caseFilter.getIsBeyondEighty()!=null){
				paramMap.put("isBeyondEighty", caseFilter.getIsBeyondEighty());
			}
			if(caseFilter.getChufaTimes()!=null){
				paramMap.put("chufaTimes", caseFilter.getChufaTimes());
			}
			if(caseFilter.getIsLowerLimitMoney()!=null){
				paramMap.put("isLowerLimitMoney", caseFilter.getIsLowerLimitMoney());
			}
			if(caseFilter.getIsIdentify()!=null){
				paramMap.put("isIdentify", caseFilter.getIsIdentify());
			}
		}
    	paramMap.put("minCaseInputTime", caseBasic.getMinCaseInputTime());
    	paramMap.put("maxCaseInputTime", caseBasic.getMaxCaseInputTime());
    	paramMap.put("districtId",districtId);
		
		Map<String,Object> filterMap = new HashMap<String, Object>();
		String filterName = "";
		if(caseBasic.getIsDiscussCase() != null && caseBasic.getIsDiscussCase() == 1){
			filterName += "经过负责人集体讨论+";
			filterMap.put("isDescussQ", caseBasic.getIsDiscussCase());
		}
		if(caseBasic.getChufaTimes() != null && caseBasic.getChufaTimes() > 0){
			filterName += "处罚次数大于"+caseBasic.getChufaTimes()+"+";
			filterMap.put("chufaTimesQ", caseBasic.getChufaTimes());
		}
		if(caseBasic.getIsSeriousCase() != null && caseBasic.getIsSeriousCase() == 1){
			filterName += "情节严重+";
			filterMap.put("isSeriousCaseQ", caseBasic.getIsSeriousCase());
		}
		if(caseBasic.getIsBeyondEighty() != null && caseBasic.getIsBeyondEighty() == 1){
			filterName += "涉案金额达到刑事追诉标准80%以上+";
			filterMap.put("isBeyondEightyQ", caseBasic.getIsBeyondEighty());
		}
		if(caseBasic.getIsIdentify() != null && caseBasic.getIsIdentify() == 1){
			filterName += "鉴定+";
			filterMap.put("isIdentifyQ", caseBasic.getIsIdentify());
		}
		if(caseBasic.getIsLowerLimitMoney() != null && caseBasic.getIsLowerLimitMoney() == 1){
			filterName += "低于行政处罚规定的下限金额  +";
			filterMap.put("isLowerLimitMoneyQ", caseBasic.getIsLowerLimitMoney());
		}
		filterName = com.ksource.liangfa.util.StringUtils.trimPrefix(filterName, "+");
		filterName = com.ksource.liangfa.util.StringUtils.trimSufffix(filterName, "+");
		filterName = "“"+filterName+"”";
		JSONObject filterNameObj = new JSONObject();
		filterNameObj.put("bookMarkName", "filterName");
		filterNameObj.put("bookMarkValue", filterName);	
		jsonArray.add(filterNameObj);
		List<CaseBasic> filterList = new ArrayList<CaseBasic>();
		if(!filterMap.isEmpty()){
			filterList = caseService.queryfilingSupervisionCase(paramMap,filterMap);
		}
		
		if(filterList.size() == 0){
			JSONObject jgclNullObj = new JSONObject();
			jgclNullObj.put("bookMarkName", "jgclNull");
			jsonArray.add(jgclNullObj);
		}else{
			JSONObject caseNumObj = new JSONObject();
			caseNumObj.put("bookMarkName", "jgclCaseNum");
			caseNumObj.put("bookMarkValue", filterList.size());
			jsonArray.add(caseNumObj);
			List<Map<String,String>> jgclCaseList = new ArrayList<Map<String,String>>();
			String caseIdArrStr = request.getParameter("caseIdAry");
			if(StringUtils.isNotBlank(caseIdArrStr)){
				String[] caseIdAry = caseIdArrStr.split(",");
				for(int i=0;i<caseIdAry.length;i++){
					Map<String,String> obj = new HashMap<String, String>();
					for(int j = 0 ;j<filterList.size();j++){
						String caseId = filterList.get(j).getCaseId();
						if(caseIdAry[i].equals(caseId)){
							obj.put("CASE_NO", filterList.get(j).getCaseNo());
							obj.put("CASE_NAME", filterList.get(j).getCaseName());
							break;
						}
					}
					jgclCaseList.add(obj);
				}
				JSONObject amongObj = new JSONObject();
				amongObj.put("bookMarkName", "among");
				amongObj.put("bookMarkValue", jgclCaseList.size());	
				jsonArray.add(amongObj);
			}else{
				for(int i = 0 ;i<filterList.size();i++){
					Map<String,String> obj = new HashMap<String, String>();
					obj.put("CASE_NO", filterList.get(i).getCaseNo());
					obj.put("CASE_NAME", filterList.get(i).getCaseName());
					jgclCaseList.add(obj);
				}
			}
			JSONObject caseListObj = new JSONObject();
			caseListObj.put("bookMarkName", "jgclCaseList");
			caseListObj.put("bookMarkValue", jgclCaseList);
			jsonArray.add(caseListObj);
		}
		
		JSONObject objectJ = new JSONObject();
		objectJ.put("bookMarkName", "regionNameJ");
		objectJ.put("bookMarkValue", currDistrict.getDistrictName());
		jsonArray.add(objectJ);
		
		JSONObject currentTimeObj = new JSONObject();
		currentTimeObj.put("bookMarkName", "currentTime");
		currentTimeObj.put("bookMarkValue", dateFormat.format(new Date()));
		jsonArray.add(currentTimeObj);
		
		view.addObject("jsonArray", jsonArray);		
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		view.addObject("districtId", districtId);
		view.addObject("startTime", format.format(startTime));
		view.addObject("endTime", format.format(endTime));		
		view.addObject("caseCount", filterList.size());
	}		
	
	
	private void createDSJFX(CaseBasic caseBasic, ModelAndView view,
			HttpServletRequest request) {
		User user=SystemContext.getCurrentUser(request);
        Integer districtQueryScope = null;
        if(StringUtils.isNotBlank(request.getParameter("districtQueryScope"))){
            districtQueryScope = Integer.valueOf(request.getParameter("districtQueryScope"));
        }
		Organise organise=user.getOrganise();
		String regionName = caseBasic.getDistrictName();
		String districtId = caseBasic.getDistrictCode();
		District currDistrict = districtService.selectByPrimaryKey(organise.getDistrictCode());
		JSONArray jsonArray = new JSONArray();
		//构建筛选范围
		if(StringUtils.isBlank(districtId)){
			regionName = currDistrict.getDistrictName();
			districtId = currDistrict.getDistrictCode();
			caseBasic.setDistrictId(com.ksource.liangfa.util.StringUtils.rightTrim0(organise.getDistrictCode()));
		}else{
		    if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
		        caseBasic.setDistrictId(StringUtils.rightTrim0(districtId));
		    }else{
		        caseBasic.setDistrictId(districtId);
		    }
		}
		JSONObject object = new JSONObject();
		object.put("bookMarkName", "regionName");
		object.put("bookMarkValue", regionName);
		jsonArray.add(object);
		
		JSONObject regionNameS = new JSONObject();
		regionNameS.put("bookMarkName", "regionNameS");
		regionNameS.put("bookMarkValue", regionName.substring(regionName.length()-1));
		jsonArray.add(regionNameS);	
		
		//构建时间范围
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		Date startTime = caseBasic.getMinCaseInputTime();
		if(startTime == null){
			caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			startTime = caseService.queryMinCaseInputTime(caseBasic);
		}
		JSONObject startTimeObj = new JSONObject();
		startTimeObj.put("bookMarkName", "startTime");
		startTimeObj.put("bookMarkValue", dateFormat.format(startTime));
		jsonArray.add(startTimeObj);
		Date endTime = caseBasic.getMaxCaseInputTime();
		if(endTime == null){
			endTime = new Date();
		}
		JSONObject endTimeObj = new JSONObject();
		endTimeObj.put("bookMarkName", "endTime");
		endTimeObj.put("bookMarkValue", dateFormat.format(endTime));
		jsonArray.add(endTimeObj);
		//案件总数
		caseBasic.setChufaState(Const.CHUFA_STATE_YES);
		int totalNum = caseService.queryCaseCount(caseBasic);
		JSONObject totalNumObj = new JSONObject();
		totalNumObj.put("bookMarkName", "totalNum");
		totalNumObj.put("bookMarkValue", totalNum);
		jsonArray.add(totalNumObj);
		
		Map<String,Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("minCaseInputTime",caseBasic.getMinCaseInputTime() );
    	paramMap.put("maxCaseInputTime", caseBasic.getMaxCaseInputTime());
    	paramMap.put("districtId", districtId);
    	Map<String,Object> filterMap = new HashMap<String, Object>();
    	filterMap.put("danwei", "Y");//同一处罚对象（单位）
    	filterMap.put("address", "Y");//同一违法行为发生地
    	filterMap.put("shijian", "Y");//同一违法行为发生时间
    	filterMap.put("wupin", "Y");//同一涉案物品
    	filterMap.put("jianding", "Y");//同一鉴定
		//paramMap.put("dangshiren", "Y");//同一处罚对象（个人）
    	//以同一处罚对象（单位）+同一违法行为发生地+同一违法行为发生时间+同一涉案物品+同一鉴定
    	Integer filterList1 = caseService.getyisiFaCaseCount(paramMap,filterMap);
		JSONObject filter1 = new JSONObject();
		filter1.put("bookMarkName", "filter1");
		filter1.put("bookMarkValue", filterList1);
		jsonArray.add(filter1);
		
		//同一处罚对象（单位）+同一违法行为发生地+同一违法行为发生时间+同一涉案物品
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
    	filterMap.put("address", "Y");//同一违法行为发生地
    	filterMap.put("shijian", "Y");//同一违法行为发生时间
    	filterMap.put("wupin", "Y");//同一涉案物品
		Integer filterList2 = caseService.getyisiFaCaseCount(paramMap,filterMap);
		JSONObject filter2 = new JSONObject();
		filter2.put("bookMarkName", "filter2");
		filter2.put("bookMarkValue", filterList2);
		jsonArray.add(filter2);	
		
		//以同一处罚对象（单位）+同一违法行为发生地+同一违法行为发生时间+同一鉴定
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
    	filterMap.put("address", "Y");//同一违法行为发生地
    	filterMap.put("shijian", "Y");//同一违法行为发生时间
    	filterMap.put("jianding", "Y");//同一鉴定
		Integer filterList3 = caseService.getyisiFaCaseCount(paramMap,filterMap);
		JSONObject filter3 = new JSONObject();
		filter3.put("bookMarkName", "filter3");
		filter3.put("bookMarkValue", filterList3);
		jsonArray.add(filter3);	
		
		//同一处罚对象（单位）+同一违法行为发生地+同一涉案物品+同一鉴定
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
		filterMap.put("address", "Y");//同一违法行为发生地
    	filterMap.put("wupin", "Y");//同一涉案物品
    	filterMap.put("jianding", "Y");//同一鉴定
    	Integer filterList4 = caseService.getyisiFaCaseCount(paramMap,filterMap);
		JSONObject filter4 = new JSONObject();
		filter4.put("bookMarkName", "filter4");
		filter4.put("bookMarkValue", filterList4);
		jsonArray.add(filter4);	
		
		//同一处罚对象（单位）+同一违法行为发生时间+同一涉案物品+同一鉴定为
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
		filterMap.put("shijian", "Y");//同一违法行为发生时间
    	filterMap.put("wupin", "Y");//同一涉案物品
    	filterMap.put("jianding", "Y");//同一鉴定
    	Integer filterList5 = caseService.getyisiFaCaseCount(paramMap,filterMap);
		JSONObject filter5 = new JSONObject();
		filter5.put("bookMarkName", "filter5");
		filter5.put("bookMarkValue", filterList5);
		jsonArray.add(filter5);	
		
		//同一处罚对象（单位）+同一违法行为发生地+同一违法行为发生时间
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
		filterMap.put("address", "Y");//同一违法行为发生地
		filterMap.put("shijian", "Y");//同一违法行为发生时间
		Integer filterList6 = caseService.getyisiFaCaseCount(paramMap,filterMap);
		JSONObject filter6 = new JSONObject();
		filter6.put("bookMarkName", "filter6");
		filter6.put("bookMarkValue", filterList6);
		jsonArray.add(filter6);
		
		//同一处罚对象（单位）+同一违法行为发生地+同一涉案物品
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
		filterMap.put("address", "Y");//同一违法行为发生地
		filterMap.put("wupin", "Y");//同一涉案物品
		Integer filterList7 = caseService.getyisiFaCaseCount(paramMap,filterMap);
		JSONObject filter7 = new JSONObject();
		filter7.put("bookMarkName", "filter7");
		filter7.put("bookMarkValue", filterList7);
		jsonArray.add(filter7);
		
		//同一处罚对象（单位）+同一违法行为发生地+同一鉴定
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
		filterMap.put("address", "Y");//同一违法行为发生地
		filterMap.put("jianding", "Y");//同一鉴定
		Integer filterList8 = caseService.getyisiFaCaseCount(paramMap,filterMap);
		JSONObject filter8 = new JSONObject();
		filter8.put("bookMarkName", "filter8");
		filter8.put("bookMarkValue", filterList8);
		jsonArray.add(filter8);		
		
		//同一处罚对象（单位）+同一违法行为发生时间+同一涉案物品
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
		filterMap.put("shijian", "Y");//同一违法行为发生时间
		filterMap.put("wupin", "Y");//同一涉案物品
		Integer filterList9 = caseService.getyisiFaCaseCount(paramMap,filterMap);
		JSONObject filter9 = new JSONObject();
		filter9.put("bookMarkName", "filter9");
		filter9.put("bookMarkValue", filterList9);
		jsonArray.add(filter9);
		
		//同一处罚对象（单位）+同一违法行为发生时间+同一鉴定
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
		filterMap.put("shijian", "Y");//同一违法行为发生时间
		filterMap.put("jianding", "Y");//同一鉴定
		Integer filterList10 = caseService.getyisiFaCaseCount(paramMap,filterMap);
		JSONObject filter10 = new JSONObject();
		filter10.put("bookMarkName", "filter10");
		filter10.put("bookMarkValue", filterList10);
		jsonArray.add(filter10);	
		
		//同一处罚对象（单位）+同一涉案物品+同一鉴定
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
		filterMap.put("wupin", "Y");//同一涉案物品
		filterMap.put("jianding", "Y");//同一鉴定
		Integer filterList11 = caseService.getyisiFaCaseCount(paramMap,filterMap);
		JSONObject filter11 = new JSONObject();
		filter11.put("bookMarkName", "filter11");
		filter11.put("bookMarkValue", filterList11);
		jsonArray.add(filter11);		
		
		//同一处罚对象（单位）+同一违法行为发生地
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
		filterMap.put("address", "Y");//同一违法行为发生地
		Integer filterList12 = caseService.getyisiFaCaseCount(paramMap,filterMap);
		JSONObject filter12 = new JSONObject();
		filter12.put("bookMarkName", "filter12");
		filter12.put("bookMarkValue", filterList12);
		jsonArray.add(filter12);	
		
		//同一处罚对象（单位）+同一违法行为发生时间
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
		filterMap.put("shijian", "Y");//同一违法行为发生时间
		Integer filterList13 = caseService.getyisiFaCaseCount(paramMap,filterMap);
		JSONObject filter13 = new JSONObject();
		filter13.put("bookMarkName", "filter13");
		filter13.put("bookMarkValue", filterList13);
		jsonArray.add(filter13);
		
		//同一处罚对象（单位）+同一涉案物品
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
		filterMap.put("wupin", "Y");//同一涉案物品
		Integer filterList14 = caseService.getyisiFaCaseCount(paramMap,filterMap);
		JSONObject filter14 = new JSONObject();
		filter14.put("bookMarkName", "filter14");
		filter14.put("bookMarkValue", filterList14);
		jsonArray.add(filter14);
		
		//同一处罚对象（单位）+同一违法行为发生时间
		paramMap.remove("danwei");
		paramMap.remove("address");
		paramMap.remove("shijian");
		paramMap.remove("wupin");
		paramMap.remove("jianding");
		filterMap.clear();
		filterMap.put("danwei", "Y");//同一处罚对象（单位）
		filterMap.put("jianding", "Y");//同一鉴定
		Integer filterList15 = caseService.getyisiFaCaseCount(paramMap,filterMap);
		JSONObject filter15 = new JSONObject();
		filter15.put("bookMarkName", "filter15");
		filter15.put("bookMarkValue", filterList15);
		jsonArray.add(filter15);
		
		
		JSONObject objectJ = new JSONObject();
		objectJ.put("bookMarkName", "regionNameJ");
		objectJ.put("bookMarkValue", currDistrict.getDistrictName());
		jsonArray.add(objectJ);
		
		JSONObject currentTimeObj = new JSONObject();
		currentTimeObj.put("bookMarkName", "currentTime");
		currentTimeObj.put("bookMarkValue", dateFormat.format(new Date()));
		jsonArray.add(currentTimeObj);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		view.addObject("jsonArray", jsonArray.toString());
		view.addObject("districtId", districtId);
		view.addObject("startTime", format.format(startTime));
		view.addObject("endTime", format.format(endTime));
	}	
	
	private void createDSJFX2(CaseBasic caseBasic, ModelAndView view,
			HttpServletRequest request) {
		User user=SystemContext.getCurrentUser(request);
        Integer districtQueryScope = null;
        if(StringUtils.isNotBlank(request.getParameter("districtQueryScope"))){
            districtQueryScope = Integer.valueOf(request.getParameter("districtQueryScope"));
        }
		Organise organise=user.getOrganise();
		String regionName = caseBasic.getDistrictName();
		String districtId = caseBasic.getDistrictCode();
		District currDistrict = districtService.selectByPrimaryKey(organise.getDistrictCode());
		JSONArray jsonArray = new JSONArray();
		//构建筛选范围
		if(StringUtils.isBlank(regionName)){
			regionName = currDistrict.getDistrictName();
			districtId = currDistrict.getDistrictCode();
			caseBasic.setDistrictId(com.ksource.liangfa.util.StringUtils.rightTrim0(organise.getDistrictCode()));
		}else{
		    if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
                caseBasic.setDistrictId(StringUtils.rightTrim0(districtId));
            }else{
                caseBasic.setDistrictId(districtId);
            }
		}
		JSONObject object = new JSONObject();
		object.put("bookMarkName", "regionName");
		object.put("bookMarkValue", regionName);
		jsonArray.add(object);
		//构建时间范围
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		Date startTime = caseBasic.getMinCaseInputTime();
		if(startTime == null){
			caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			startTime = caseService.queryMinCaseInputTime(caseBasic);
		}
		JSONObject startTimeObj = new JSONObject();
		startTimeObj.put("bookMarkName", "startTime");
		startTimeObj.put("bookMarkValue", dateFormat.format(startTime));
		jsonArray.add(startTimeObj);
		Date endTime = caseBasic.getMaxCaseInputTime();
		if(endTime == null){
			endTime = new Date();
		}
		JSONObject endTimeObj = new JSONObject();
		endTimeObj.put("bookMarkName", "endTime");
		endTimeObj.put("bookMarkValue", dateFormat.format(endTime));
		jsonArray.add(endTimeObj);
		//案件总数
		caseBasic.setChufaState(Const.CHUFA_STATE_YES);
		int totalNum = caseService.queryCaseCount(caseBasic);
		JSONObject totalNumObj = new JSONObject();
		totalNumObj.put("bookMarkName", "totalNum");
		totalNumObj.put("bookMarkValue", totalNum);
		jsonArray.add(totalNumObj);
		
		Map<String,Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("minCaseInputTime",startTime);
    	paramMap.put("maxCaseInputTime", endTime);
    	paramMap.put("districtId", caseBasic.getDistrictId());
    	Map<String,Object> filterMap = new HashMap<String, Object>();
		JSONObject conditionFilter = new JSONObject();
		conditionFilter.put("bookMarkName", "condition");
		String condition = "";
        String index = request.getParameter("indexList");
		if(org.apache.commons.lang.StringUtils.isNotEmpty(index)){
			if(index.contains("A")){//同一违法行为发生地
				filterMap.put("address", "Y");
				condition += "同一违法行为发生地+";
			}
			if(index.contains("B")){//同一违法行为发生时间
				filterMap.put("shijian", "Y");
				condition += "同一违法行为发生时间+";
			}
	        if(index.contains("C")){//同一涉案物品
	        	filterMap.put("wupin", "Y");
	        	condition += "同一涉案物品+";
	        }	
	        if(index.contains("D")){//同一鉴定
	        	filterMap.put("jianding", "Y");
	        	condition += "同一鉴定+";
	        }
			if(index.contains("E")){//同一处罚对象（单位）
				filterMap.put("danwei", "Y");
				condition += "同一处罚对象（单位）+";
			}
			if(index.contains("F")){//同一处罚对象（个人）
				filterMap.put("dangshiren", "Y");
				condition += "同一处罚对象（个人）+";
			}
		}
		condition = com.ksource.liangfa.util.StringUtils.trimSufffix(condition, "+");
		condition = "\""+condition+"\"";
		conditionFilter.put("bookMarkValue", condition);
		jsonArray.add(conditionFilter);
		List<CaseBasic> caseList = caseService.getyisiFaCaseList(paramMap, filterMap);
		JSONObject caseListFilter = new JSONObject();
		List<Map<String,String>> dsjfxCaseList = new ArrayList<Map<String,String>>();
		if(caseList.size() == 0){
			JSONObject dsjfxNullObj = new JSONObject();
			dsjfxNullObj.put("bookMarkName", "dsjfxNull");
			jsonArray.add(dsjfxNullObj);
		}else{
			JSONObject caseNumObj = new JSONObject();
			caseNumObj.put("bookMarkName", "dsjCaseNum");
			caseNumObj.put("bookMarkValue", caseList.size());
			jsonArray.add(caseNumObj);
			String caseIdArrStr = request.getParameter("caseIdAry");
			if(StringUtils.isNotBlank(caseIdArrStr)){
				String[] caseIdAry = caseIdArrStr.split(",");
				for(int i=0;i<caseIdAry.length;i++){
					Map<String,String> obj = new HashMap<String, String>();
					for(int j = 0 ;j<caseList.size();j++){
						String caseId = caseList.get(j).getCaseId();
						if(caseIdAry[i].equals(caseId)){
							obj.put("CASE_NO", caseList.get(j).getCaseNo());
							obj.put("CASE_NAME", caseList.get(j).getCaseName());
							break;
						}
					}
					dsjfxCaseList.add(obj);
				}
				JSONObject amongObj = new JSONObject();
				amongObj.put("bookMarkName", "among");
				amongObj.put("bookMarkValue", dsjfxCaseList.size());	
				jsonArray.add(amongObj);
			}else{
				for(int i = 0 ;i<caseList.size();i++){
					Map<String,String> obj = new HashMap<String, String>();
					obj.put("CASE_NO", caseList.get(i).getCaseNo());
					obj.put("CASE_NAME", caseList.get(i).getCaseName());
					dsjfxCaseList.add(obj);
				}
			}
			caseListFilter.put("bookMarkName", "dsjfxCaseList");
			caseListFilter.put("bookMarkValue", dsjfxCaseList);
			jsonArray.add(caseListFilter);
		}
		
		JSONObject objectJ = new JSONObject();
		objectJ.put("bookMarkName", "regionNameJ");
		objectJ.put("bookMarkValue", currDistrict.getDistrictName());
		jsonArray.add(objectJ);
		
		JSONObject currentTimeObj = new JSONObject();
		currentTimeObj.put("bookMarkName", "currentTime");
		currentTimeObj.put("bookMarkValue", dateFormat.format(new Date()));
		jsonArray.add(currentTimeObj);
		
		view.addObject("jsonArray", jsonArray.toString());			
	}
	

	/**
	 * 构建涉嫌犯罪案件线索筛查报告（总）
	 *
	 * @param docType 报告类型
	 * @param endTime 筛选时间范围
	 * @param startTime 筛选时间范围
	 * @param districtName 筛选范围
	 */
	private void createSXFZAJXSSCBGZ( CaseBasic caseBasic, ModelAndView view,HttpServletRequest request) {
		User user = SystemContext.getCurrentUser(request);
		Integer districtQueryScope = null;
        if(StringUtils.isNotBlank(request.getParameter("districtQueryScope"))){
            districtQueryScope = Integer.valueOf(request.getParameter("districtQueryScope"));
        }
		Organise organise = user.getOrganise();
		District district = districtService.selectByPrimaryKey(organise.getDistrictCode());
		JSONArray jsonArray = new JSONArray();
		String districtName="";
		String shortDistrictId = "";
		//构建筛选范围
		if(StringUtils.isBlank(caseBasic.getDistrictId())){
			caseBasic.setDistrictId(district.getDistrictCode());
			districtName = district.getDistrictName();
		}else{
	      if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
	            shortDistrictId=StringUtils.rightTrim0(caseBasic.getDistrictId());
	        }else{
	            shortDistrictId = caseBasic.getDistrictId();
	        }
			districtName=caseBasic.getDistrictName();
		}
		JSONObject object = new JSONObject();
		object.put("bookMarkName", "regionName");
		object.put("bookMarkValue", districtName);
		jsonArray.add(object);
		
		JSONObject regionNameS = new JSONObject();
		regionNameS.put("bookMarkName", "regionNameS");
		regionNameS.put("bookMarkValue", districtName.substring(districtName.length()-1));
		jsonArray.add(regionNameS);
		
		//构建时间范围
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		Date startTime = caseBasic.getMinCaseInputTime();
		CaseBasic case1=new CaseBasic();
		case1.setDistrictId(shortDistrictId);
		if(startTime == null){
			//查询某行政区划下的行政处罚案件的最小录入时间
			startTime=caseService.queryMinCaseInputTime(case1);
		}
		JSONObject startTimeObj = new JSONObject();
		startTimeObj.put("bookMarkName", "startTime");
		startTimeObj.put("bookMarkValue", dateFormat.format(startTime));
		jsonArray.add(startTimeObj);
		Date endTime = caseBasic.getMaxCaseInputTime();
		if(endTime == null){
			endTime = new Date();
		}
		JSONObject endTimeObj = new JSONObject();
		endTimeObj.put("bookMarkName", "endTime");
		endTimeObj.put("bookMarkValue", dateFormat.format(endTime));
		jsonArray.add(endTimeObj);
		//构建行政处罚案件总数
		case1.setMinCaseInputTime(startTime);
		case1.setMaxCaseInputTime(endTime);
		//查询行政处罚案件数量
		//case1.setChufaState(Const.CHUFA_STATE_YES);
		case1.setCaseState(Const.CHUFA_PROC_4);
		int totalNum = caseService.queryCaseCount(case1);
		JSONObject totalNumObj = new JSONObject();
		totalNumObj.put("bookMarkName", "totalNum");
		totalNumObj.put("bookMarkValue", totalNum);	
		jsonArray.add(totalNumObj);
		//构建筛查出来的涉嫌犯罪案件总数
		CaseBasic c=new CaseBasic();
		c.setDistrictCode(shortDistrictId);
		c.setStartTime(startTime);
		c.setEndTime(endTime);
		List<CaseBasic> caseList = caseService.querySuspectedCriminalCase(c);
		if(caseList.size() == 0){
			JSONObject sxfzNullObj = new JSONObject();
			sxfzNullObj.put("bookMarkName", "sxfzNull");
			jsonArray.add(sxfzNullObj);
		}else{
			JSONObject filterNumObj = new JSONObject();
			filterNumObj.put("bookMarkName", "filterNum");
			filterNumObj.put("bookMarkValue", caseList.size());
			jsonArray.add(filterNumObj);
			//构建筛查出来的涉嫌犯罪案件列表
			List<Map<String,String>> sxfzCaseList = new ArrayList<Map<String,String>>();
			String caseIdArrStr = request.getParameter("caseIdAry");
			if(StringUtils.isNotBlank(caseIdArrStr)){
				String[] caseIdAry = caseIdArrStr.split(",");
				for(int i=0;i<caseIdAry.length;i++){
					Map<String,String> obj = new HashMap<String, String>();
					for(int j = 0 ;j<caseList.size();j++){
						String caseId = caseList.get(j).getCaseId();
						if(caseIdAry[i].equals(caseId)){
							obj.put("CASE_NO", caseList.get(j).getCaseNo());
							obj.put("CASE_NAME", caseList.get(j).getCaseName());
							String illegalname = "";
							if(caseList.get(j).getIllegalSituationNameList() != null){
								for(String k:caseList.get(j).getIllegalSituationNameList()){
									illegalname += k+",";
								}
								illegalname = StringUtils.trimSufffix(illegalname, ",");
							}
							obj.put("ILLEGAL_NAME", illegalname);
							String accuseName = "";
							if(caseList.get(i).getAccuseNameList() != null){
								for(String k:caseList.get(j).getAccuseNameList()){
									if(accuseName.indexOf(k) == -1){
										accuseName += k+",";
									}
								}
								accuseName = StringUtils.trimSufffix(accuseName, ",");
							}
							obj.put("ACCUSE_NAME",accuseName);
							break;
						}
					}
					sxfzCaseList.add(obj);
				}
				JSONObject amongObj = new JSONObject();
				amongObj.put("bookMarkName", "among");
				amongObj.put("bookMarkValue", sxfzCaseList.size());	
				jsonArray.add(amongObj);
			}else{
					for(int i = 0 ;i<caseList.size();i++){
						Map<String,String> obj = new HashMap<String, String>();
						obj.put("CASE_NO", caseList.get(i).getCaseNo());
						obj.put("CASE_NAME", caseList.get(i).getCaseName());
						String illegalname = "";
						if(caseList.get(i).getIllegalSituationNameList() != null){
							for(String j:caseList.get(i).getIllegalSituationNameList()){
								illegalname += j+",";
							}
							illegalname = StringUtils.trimSufffix(illegalname, ",");
						}
						obj.put("ILLEGAL_NAME", illegalname);
						String accuseName = "";
						if(caseList.get(i).getAccuseNameList() != null){
							for(String k:caseList.get(i).getAccuseNameList()){
								if(accuseName.indexOf(k) == -1){
									accuseName += k+",";
								}
							}
							accuseName = StringUtils.trimSufffix(accuseName, ",");
						}
						obj.put("ACCUSE_NAME",accuseName);
						sxfzCaseList.add(obj);
					}
				}
			JSONObject caseListObj = new JSONObject();
			caseListObj.put("bookMarkName", "sxfzCaseList");
			caseListObj.put("bookMarkValue", sxfzCaseList);
			jsonArray.add(caseListObj);
		}
		//检察院所属行政区划
		JSONObject objectJ = new JSONObject();
		objectJ.put("bookMarkName", "regionNameJ");
		objectJ.put("bookMarkValue", district.getDistrictName());
		jsonArray.add(objectJ);
		
		JSONObject currentTimeObj = new JSONObject();
		currentTimeObj.put("bookMarkName", "currentTime");
		currentTimeObj.put("bookMarkValue", dateFormat.format(new Date()));
		jsonArray.add(currentTimeObj);
		
		view.addObject("jsonArray", jsonArray);
		view.addObject("caseCount", caseList.size());
	}
	
	//涉嫌犯罪案件筛查线索告知书(对下)下发信息UI
	@RequestMapping(value="instructionSendUI")
	public ModelAndView addUI(String docType,String fileName,String caseId,String caseNo,String caseName,String handleOrg,HttpServletRequest request,HttpServletResponse response){
		Integer orgId = SystemContext.getCurrentUser(request).getOrgId();
		Organise organise = mybatisAutoMapperService.selectByPrimaryKey(OrganiseMapper.class, orgId, Organise.class); 
		//OrganiseExternal organiseExternal = mybatisAutoMapperService.selectByPrimaryKey(OrganiseExternalMapper.class, handleOrg, OrganiseExternal.class);
		ModelAndView view = new ModelAndView("instruction/instructionSendAdd");
		//查询已保存的涉嫌犯罪案件筛查线索告知书(对下)报告附件
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("docType",docType);
		OfficeDoc odoc=officeDocService.getMaxBulianDocNoByCaseId(param);
			
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("docId", odoc.getDocId());
		PublishInfoFile file=publishInfoFileMapper.getFileByDocId(map);
		List<PublishInfoFile> list=new ArrayList<PublishInfoFile>();
		list.add(file);
		view.addObject("publishInfoFiles", list);
			
		view.addObject("orgName", organise.getOrgName());
		view.addObject("searchRank",Const.SEARCH_LOWER_ORG);
		//view.addObject("organiseExternal", organiseExternal);
		view.addObject("caseNo", caseNo);
		view.addObject("caseId", caseId);
		view.addObject("caseName", caseName);
		if(file!=null){
			view.addObject("fileId", file.getFileId());
		}
		fileName = com.ksource.liangfa.util.StringUtils.trimSufffix(fileName, ".doc");
		view.addObject("title", fileName);
		return view;
	}
	
	private void createCBTZ( CaseBasic caseBasic,
			ModelAndView view, HttpServletRequest request) {
		User user = SystemContext.getCurrentUser(request);
		Organise organise = user.getOrganise();
		JSONArray jsonArray = new JSONArray();
		String shortDistrictId=com.ksource.liangfa.util.StringUtils.rightTrim0(caseBasic.getDistrictId());
		caseBasic.setDistrictId(shortDistrictId);
		//接收单位行政区划名称
		JSONObject object = new JSONObject();
		String districtName = caseBasic.getDistrictName();
		object.put("bookMarkName", "acceptDistrictName");
		object.put("bookMarkValue",districtName);
		jsonArray.add(object);
		
		//构建时间范围
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		Date startTime = caseBasic.getStartTime();
		if(startTime == null){
			caseBasic.setYisongState(Const.YISONG_STATE_ZHIJIE);
			startTime = caseService.queryMinCaseInputTime(caseBasic);
		} 
		JSONObject startTimeObj = new JSONObject();
		startTimeObj.put("bookMarkName", "startTime");
		startTimeObj.put("bookMarkValue", dateFormat.format(startTime));
		jsonArray.add(startTimeObj);
		Date endTime = caseBasic.getEndTime();
		if(endTime == null){
			endTime = new Date();
		}
		JSONObject endTimeObj = new JSONObject();
		endTimeObj.put("bookMarkName", "endTime");
		endTimeObj.put("bookMarkValue", dateFormat.format(endTime));
		jsonArray.add(endTimeObj);
		//构建 行政执法机关移送公安机关涉嫌犯罪案件数
		caseBasic.setStartTime(startTime);
		caseBasic.setEndTime(endTime);
		int yisongCount = caseService.queryYisongCaseCount(caseBasic);
		JSONObject yisongNumObj = new JSONObject();
		yisongNumObj.put("bookMarkName", "yisongCount");
		yisongNumObj.put("bookMarkValue", yisongCount);
		jsonArray.add(yisongNumObj);
		
		//构建 行政执法机关移送公安机关涉嫌犯罪,公安未受理案件数
		int policeNotAcceptCount = caseService.queryPoliceNotAcceptCaseCount(caseBasic);
		
		if(policeNotAcceptCount == 0){
			JSONObject cbtzNullObj = new JSONObject();
			cbtzNullObj.put("bookMarkName", "cbtzNull");
			jsonArray.add(cbtzNullObj);
		}else{
			JSONObject policeNumObj = new JSONObject();
			policeNumObj.put("bookMarkName", "policeNotAcceptCount");
			policeNumObj.put("bookMarkValue", policeNotAcceptCount);	
			jsonArray.add(policeNumObj);
			
			//构建筛查出来的公安未受理的涉嫌犯罪案件
			List<Map<String,String>> cbtzCaseList = new ArrayList<Map<String,String>>();
			String caseNoArrStr = request.getParameter("caseNoAry");
			String caseNameArrStr = request.getParameter("caseNameAry");
			if(StringUtils.isNotBlank(caseNoArrStr)){
				String[] caseNoAry = caseNoArrStr.split(",");
				String[] caseNameAry = caseNameArrStr.split(",");
				for(int i=0;i<caseNoAry.length;i++){
					Map<String,String> obj = new HashMap<String, String>();
					obj.put("CASE_NO", caseNoAry[i]);
					obj.put("CASE_NAME",caseNameAry[i]);
					cbtzCaseList.add(obj);
				}
			}else{
				List<CaseBasic> caseList = caseService.queryPoliceNotAcceptCaseList(caseBasic);
				for(int i=0;i<caseList.size();i++){
					Map<String,String> obj = new HashMap<String, String>();
					obj.put("CASE_NO", caseList.get(i).getCaseNo());
					obj.put("CASE_NAME",caseList.get(i).getCaseName());
					cbtzCaseList.add(obj);
				}
			}
			JSONObject caseListObj = new JSONObject();
			caseListObj.put("bookMarkName", "cbtzCaseList");
			caseListObj.put("bookMarkValue",cbtzCaseList);
			jsonArray.add(caseListObj);
		}
		
		//当前用户行政区划名称
		JSONObject objectJ = new JSONObject();
		District d = districtService.selectByPrimaryKey(organise.getDistrictCode());
		objectJ.put("bookMarkName", "regionNameJ");
		objectJ.put("bookMarkValue", d.getDistrictName());
		jsonArray.add(objectJ);
    
		//当前时间
		JSONObject currentTimeObj = new JSONObject();
		currentTimeObj.put("bookMarkName", "currentTime");
		currentTimeObj.put("bookMarkValue", dateFormat.format(new Date()));
		jsonArray.add(currentTimeObj);
    
		view.addObject("jsonArray", jsonArray);    
	}
  
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
}
