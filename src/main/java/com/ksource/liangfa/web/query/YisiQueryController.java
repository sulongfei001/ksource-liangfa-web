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
 * 疑似犯罪案件查询
 * @author 旭辉
 *
 */
@Controller
@RequestMapping("/query/yisi")
public class YisiQueryController {
	
	private static final String QUERY_VIEW="querystats/yisiQuery";
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
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("situationName", "无");
		PaginationHelper<CaseBasic> caseList = caseService.findYisiCaseList(caseBasic, page,map);
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
