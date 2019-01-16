package com.ksource.liangfa.web.query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.PenaltyCaseForm;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;


/**
 * 疑似分案案件查询
 * @author 旭辉
 *
 */
@Controller
@RequestMapping("/query/yisiFaCase")
public class YisiFaQueryController {
	
	private static final String QUERY_VIEW="querystats/yisiFaQuery";
	/*此控制器针对处罚案件*/
	private static final String procKey = Const.CASE_CHUFA_PROC_KEY ;
	@Autowired
	OrgService orgService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	OrganiseMapper organiseMapper;
	@Autowired
	private CaseService caseService;
	
	@RequestMapping(value="query")
	public String query(ModelMap model,CaseBasic caseBasic,Integer queryScope,Integer districtQueryScope,String page,HttpServletRequest request){
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
		String shortDistrictCode = "";
		//根据org的distictCode查找当前org的对应的district,进而找到当前org的行政区划级别
        if(!Const.SYSTEM_ADMIN_ID.equals(user.getAccount())){
            District currOrgDistrict=mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, organise.getDistrictCode(), District.class);
            if(StringUtils.isNotBlank(caseBasic.getDistrictCode())){
                if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
                    caseBasic.setDistrictCode(StringUtils.rightTrim0(caseBasic.getDistrictCode()));
                }
            }else{
                caseBasic.setDistrictCode(StringUtils.rightTrim0(currOrgDistrict.getDistrictCode()));
            }

            //没有选机构
            if (caseBasic.getOrgId() == null) {
                //如果用户是行政机构的
                if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
                	caseBasic.setOrgId(user.getOrgId());
                }
            }else{
                if(queryScope == null || queryScope == Const.QUERY_SCOPE_1){
                    caseBasic.setOrgId(null);
                }
            }
        }
        String index = request.getParameter("indexList");
        Map<String,Object> paramMap = new HashMap<String, Object>(); 
        
		PaginationHelper<CaseBasic> caseList =null;
		if(org.apache.commons.lang.StringUtils.isNotEmpty(index)){
			if(index.contains("A")){//同一违法行为发生地
				paramMap.put("address", "Y");
			}
			if(index.contains("B")){//同一违法行为发生时间
				paramMap.put("shijian", "Y");
			}
	        if(index.contains("C")){//同一涉案物品
	        	paramMap.put("wupin", "Y");
	        }	
	        if(index.contains("D")){//同一鉴定
	        	paramMap.put("jianding", "Y");
	        }
			if(index.contains("E")){//同一处罚对象（单位）
				paramMap.put("danwei", "Y");
			}
			if(index.contains("F")){//同一处罚对象（个人）
				paramMap.put("dangshiren", "Y");
			}
			caseList = caseService.findYisiFaCaseList(caseBasic, page,paramMap);
		}
		model.addAttribute("procKey",procKey);
		model.addAttribute("orgTopId", request.getParameter("orgTopId"));
		model.addAttribute("index",index);
		model.addAttribute("orgTopDistrictCode", request.getParameter("orgTopDistrictCode"));
		model.addAttribute("caseList", caseList);
		model.addAttribute("param", caseBasic);
		model.addAttribute("page", page);
		model.addAttribute("index", index);
		model.addAttribute("districtLevel", user.getDistrictJB());
		return QUERY_VIEW;
	}
	
	@RequestMapping(value="queryUI")
	public String queryUI(ModelMap model,HttpServletRequest request){
		return QUERY_VIEW;
	}
	
    //进行日期转换格式操作
    @InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest){
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
