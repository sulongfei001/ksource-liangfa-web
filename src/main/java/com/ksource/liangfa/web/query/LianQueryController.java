package com.ksource.liangfa.web.query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.ksource.liangfa.domain.CaseFilter;
import com.ksource.liangfa.domain.CaseFilterExample;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.CaseFilterMapper;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;


/**
 * 立案监督线索
 * @author 旭辉
 *
 */
@Controller
@RequestMapping("/query/lian")
public class LianQueryController {
	
	private static final String QUERY_VIEW="querystats/lianQuery";
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
	public String query(ModelMap model,CaseBasic caseBasic,String page,HttpServletRequest request){
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
		//根据org的distictCode查找当前org的对应的district,进而找到当前org的行政区划级别
        if(!Const.SYSTEM_ADMIN_ID.equals(user.getAccount())){
            District currOrgDistrict=mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, organise.getDistrictCode(), District.class);
            //没有选机构
            if (caseBasic.getOrgId()==null) {
                //如果用户是行政机构的
                if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
                	caseBasic.setOrgId(user.getOrgId());
                }else {
                	caseBasic.setOrgDistrict(StringUtils.rightTrim0(currOrgDistrict.getDistrictCode()));
                }
            }
        }
        
        CaseFilter caseFilter=new CaseFilter();
        Map<String,Object> paramMap = new HashMap<String, Object>(); 
        //根据登录用户组织机构查询案件筛选表信息，并作为参数筛选待办案件
        CaseFilterExample example=new CaseFilterExample();
        Integer orgCode=SystemContext.getCurrentUser(request).getOrgId();
		example.createCriteria().andOrgCodeEqualTo(orgCode);
		List<CaseFilter> list=mybatisAutoMapperService.selectByExample(CaseFilterMapper.class, example, CaseFilter.class);
		int count=list.size();
		if(count>=1){
			caseFilter=list.get(0);
		}
		if(caseFilter!=null){
			if(caseFilter.getMinAmountInvolved()!=null){
				paramMap.put("minAmountInvolved", caseFilter.getMinAmountInvolved());
			}
			if(caseFilter.getMaxAmountInvolved()!=null){
				paramMap.put("maxAmountInvolved", caseFilter.getMaxAmountInvolved());
			}
			if(caseFilter.getMinCaseInputTime()!=null){
				paramMap.put("minCaseInputTime", caseFilter.getMinCaseInputTime());
			}
			if(caseFilter.getMaxCaseInputTime()!=null){
				paramMap.put("maxCaseInputTime", caseFilter.getMaxCaseInputTime());
			}
			if(caseFilter.getIsDiscussCase()!=null){
				paramMap.put("isDiscussCase", caseFilter.getIsDiscussCase());
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

		PaginationHelper<CaseBasic> caseList = caseService.findlianCaseList(caseBasic, page,paramMap);
		model.addAttribute("procKey",procKey);
		model.addAttribute("orgTopId", request.getParameter("orgTopId"));
		model.addAttribute("orgTopDistrictCode", request.getParameter("orgTopDistrictCode"));
		model.addAttribute("caseList", caseList);
		model.addAttribute("param", caseBasic);
		model.addAttribute("page", page);
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
