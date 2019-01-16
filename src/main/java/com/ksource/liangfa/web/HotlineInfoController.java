package com.ksource.liangfa.web;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.lucene.HotlineIndexAspect;
import com.ksource.common.poi.adapter.CaseTemVO;
import com.ksource.common.poi.adapter.CaseTemplateUtil;
import com.ksource.liangfa.domain.HotlineInfo;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.DictionaryMapper;
import com.ksource.liangfa.service.casehandle.HotlineInfoService;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("hotlineInfo")
public class HotlineInfoController {
	public static final String HOTLINE_VIEW = "casehandle/hotlineInfoImport";
	public static final String IMPORT_RESULT_VIEW = "casehandle/hotlineImportResult";
	private static final String VALID_VIEW = "casehandle/hotlineValidResult";
	private static final String HOTLINE_LIST = "casehandle/hotlineInfoList";
	private static final String HOTLINE_DETAILS = "casehandle/hotlineInfoDetails";
	private static final String HOTLINE_COUNT_DETAILS = "casehandle/hotlineInfoCountDetails";
	private static final String HOTLINE_COUNT = "casehandle/hotlineInfoCount";
	private static final String HOTLINE_COUNT_LIST = "casehandle/hotlineInfoCountList";
	private static final String HOTLINE_SEARCH = "casehandle/hotlineInfoSearch";
	private static final String HOTLINE_SEARCH_DETAILS = "casehandle/hotlineInfoSearchDetails";
	
	@Resource
	private HotlineInfoService hotlineInfoService;
	@Resource
	private DictionaryMapper dictionaryMapper;
	@Resource
	private HotlineIndexAspect hotlineInfoIndex;
	
	@RequestMapping(value = "main")
	public String main(ModelMap map) {
		return HOTLINE_VIEW;
	}

	@RequestMapping(value = "upload")
    public String upload(
            MultipartHttpServletRequest temFile, ModelMap map, HttpSession session) throws Exception {
        //接受模板信息，并解析，校验模板和数据并返回校验结果
        MultipartFile file = null;
        if (temFile != null
                && (file = temFile.getFile("temFile")) != null
                && !file.isEmpty()) {
        	//模板有误,或是模板中没有数据
        	ServiceResponse res = CaseTemplateUtil.validateTemplateInfo(file.getInputStream());   //验证模板
            if (!res.getResult()) {
                map.put("error", res.getMsg());
                return VALID_VIEW;
            }
            
            List<HotlineInfo> extList = CaseTemplateUtil.validateHotlineDate(file.getInputStream());//验证数据
            if (extList.size() != 0) {
                session.setAttribute("errorList", extList);
                map.put("dataError", extList);  //数据有误
                return VALID_VIEW;
            }
            
            CaseTemVO vo = CaseTemplateUtil.getImportHotline(file.getInputStream());
            
            //TODO:调用市长热线录入时的保存功能
            hotlineInfoService.batckInsert(vo.getHotlineInfoList());
            map.put("count", vo.getHotlineInfoList().size());
            //session.removeAttribute("errorList");
            return IMPORT_RESULT_VIEW;
        }
        map.put("error", "模板有误，请重新下载模板!");      //模板有误,或是模板中没有数据
        return VALID_VIEW;
    }
	
	@RequestMapping(value = "errorList")
    public String errorList(ModelMap map, HttpSession session) throws Exception {
        map.put("dataError", session.getAttribute("errorList")); //查询结果
        return VALID_VIEW;
    }
	
	/**
     * 市长热线查询
     * @param request
     * @param hotlineInfo
     * @param page
     * @return
     */
    @RequestMapping(value="list")
	public String list(HttpServletRequest request, ModelMap modelMap,
			HotlineInfo hotlineInfo,String page) {
		User user = SystemContext.getCurrentUser(request);
		String userId = user.getUserId();

		PaginationHelper<HotlineInfo> hotlineInfoList = hotlineInfoService
				.find(hotlineInfo,page);
		modelMap.addAttribute("hotlineInfoList", hotlineInfoList);
		modelMap.addAttribute("page", page);

		return HOTLINE_LIST;
	}
    /**
     * 市长热线查询
     * @param request
     * @param hotlineInfo
     * @param page
     * @return
     */
    @RequestMapping(value="countList")
    public String countList(HttpServletRequest request, ModelMap modelMap,
    		HotlineInfo hotlineInfo,String page,String contentType) {
    	User user = SystemContext.getCurrentUser(request);
    	String userId = user.getUserId();
    	
    	PaginationHelper<HotlineInfo> hotlineInfoCountList = hotlineInfoService
    			.find(hotlineInfo,page);
    	modelMap.addAttribute("hotlineInfoCountList", hotlineInfoCountList);
    	modelMap.addAttribute("page", page);
    	modelMap.addAttribute("contentType", contentType);
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(hotlineInfo.getStartTime() != null){
			modelMap.addAttribute("startTime",df.format(hotlineInfo.getStartTime()));
		}
		if(hotlineInfo.getEndTime() != null){
			modelMap.addAttribute("endTime", df.format(hotlineInfo.getEndTime()));
		}
    	
    	return HOTLINE_COUNT_LIST;
    }
    
    /**
     * 市长热线数据检索
     * @param request
     * @param hotlineInfo
     * @param page
     * @return
     */
    @RequestMapping(value="search")
    public String search(HttpServletRequest request, Map<String, Object> model,
    		HotlineInfo hotlineInfo,String page,HttpSession session) throws Exception {
    	//hotlineInfo.setContent(hotlineInfo.getTheme());
    	String backType = "";
    	PaginationHelper<HotlineInfo> hotlineInfoList = hotlineInfoIndex.queryHotlineIndex(hotlineInfo, page,
				backType, session);
    	model.put("hotlineInfoList", hotlineInfoList);
    	model.put("hotline", hotlineInfo);
    	model.put("page", page);
    	
    	return HOTLINE_SEARCH;
    }
    
    @RequestMapping(value="searchDetails")
    public String searchDetails(String infoId,HttpServletRequest request, Map<String, Object> model,HotlineInfo hotlineInfo,String contentType,String content,
    		HttpSession session) {
    	/*User user = SystemContext.getCurrentUser(request);
    	String userId = user.getUserId();
    	Integer infoId = hotlineInfo.getInfoId();*/
    	String backType = "";
    	HotlineInfo infoList = hotlineInfoIndex.searchDetail(infoId, backType, session);
    	
    	/*List<HotlineInfo> hotlineInfoDetailsList = hotlineInfoService
    			.getDetailsByInfoId(infoId);
    	modelMap.addAttribute("hotlineInfoDetailsList", hotlineInfoDetailsList);*/
    	model.put("contentType", contentType);
    	model.put("content", content);
    	model.put("infoList", infoList);
    	
    	return HOTLINE_SEARCH_DETAILS;
    }
    /**
     * 市长热线统计页面
     * @param request
     * @param startTime 
     * @return
     */
    @RequestMapping(value="count")
	public String count(HotlineInfo hotlineInfo,HttpServletRequest request,Map<String, Object> model,
			HttpSession session) {
    	//获取市长热线内容类型数量
		Map<String, Object> hotlineTypeNum = hotlineInfoService.getTypeNum(hotlineInfo);
		
		Set keySet = hotlineTypeNum.keySet(); // key的set集合  
        Iterator it = keySet.iterator();  
        Object key = "TOTAL_NUM";
        BigDecimal value = new BigDecimal(0);
        while(it.hasNext()){  
            Object k = it.next(); // key  
            Object v = hotlineTypeNum.get(k);  //value 
            //判断市长热线总数数据是否为空
            if(k.equals(key)){
            	if(v.equals(value)){
            		break;
            	}else {
            		Map<String, Object> hotlineTypePre = hotlineInfoService.getTypePre(hotlineInfo);
        			model.put("hotlineTypePre", hotlineTypePre);
				}
            }
        }  
		
		model.put("hotlineTypeNum", hotlineTypeNum);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(hotlineInfo.getStartTime() != null){
			model.put("startTime",df.format(hotlineInfo.getStartTime()));
		}
		if(hotlineInfo.getEndTime() != null){
			model.put("endTime", df.format(hotlineInfo.getEndTime()));
		}
		
		return HOTLINE_COUNT;
	}

	/**
     * 市长热线详情
     * @param request
     * @param caseYisongJiwei
     * @param page
     * @return
     */
    @RequestMapping(value="details")
    public String details(HttpServletRequest request, ModelMap modelMap,HotlineInfo hotlineInfo) {
    	User user = SystemContext.getCurrentUser(request);
    	String userId = user.getUserId();
    	Integer infoId = hotlineInfo.getInfoId();
    	List<HotlineInfo> hotlineInfoDetailsList = hotlineInfoService
    			.getDetailsByInfoId(infoId);
    	modelMap.addAttribute("hotlineInfoDetailsList", hotlineInfoDetailsList);
    	
    	return HOTLINE_DETAILS;
    }
    
    /**
     * 统计详情
     * @param request
     * @param caseYisongJiwei
     * @param page
     * @return
     */
    @RequestMapping(value="countDetails")
    public String countDetails(HttpServletRequest request, ModelMap modelMap,HotlineInfo hotlineInfo,String contentType) {
    	User user = SystemContext.getCurrentUser(request);
    	String userId = user.getUserId();
    	Integer infoId = hotlineInfo.getInfoId();
    	List<HotlineInfo> hotlineInfoDetailsList = hotlineInfoService
    			.getDetailsByInfoId(infoId);
    	modelMap.addAttribute("hotlineInfoDetailsList", hotlineInfoDetailsList);
    	modelMap.addAttribute("contentType", contentType);
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(hotlineInfo.getStartTime() != null){
			modelMap.addAttribute("startTime",df.format(hotlineInfo.getStartTime()));
		}
		if(hotlineInfo.getEndTime() != null){
			modelMap.addAttribute("endTime", df.format(hotlineInfo.getEndTime()));
		}
    	
    	return HOTLINE_COUNT_DETAILS;
    }
    
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

}
