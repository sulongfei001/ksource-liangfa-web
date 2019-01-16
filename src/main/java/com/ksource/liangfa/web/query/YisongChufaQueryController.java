package com.ksource.liangfa.web.query;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.ksource.liangfa.domain.OrganiseExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;


@Controller
@RequestMapping("/query/yisongchufa")
public class YisongChufaQueryController {
	//移送处罚案件查询页面
	private static final String YISONGCHUFA_QUERY_VIEW="querystats/yisongchufaQuery";
	/*此控制器针对移送行政案件*/
	private static final String procKey = Const.CASE_YISONGXINGZHENG_PROC_KEY ;
		
		@Autowired
		OrgService orgService;
		@Autowired
		MybatisAutoMapperService mybatisAutoMapperService;
		@Autowired
		OrganiseMapper organiseMapper;
		@Autowired
		CaseService caseService;
		
		@RequestMapping(value="query")
		public String query(ModelMap model,CaseBasic caseBasic,String page,HttpServletRequest request){
			caseBasic.setProcKey(procKey);
			User user=SystemContext.getCurrentUser(request);
			Organise organise=user.getOrganise();
			//根据org的distictCode查找当前org的对应的district,进而找到当前org的行政区划级别
            if(!Const.SYSTEM_ADMIN_ID.equals(user.getAccount())){
                District currOrgDistrict=mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, organise.getDistrictCode(), District.class);
                //没有选机构
                if (caseBasic.getOrgId()==null) {
                    //如果用户是行政机构的
                    if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
                        OrganiseExample organiseExample=new OrganiseExample();
                        organiseExample.createCriteria().andDistrictCodeEqualTo(currOrgDistrict.getDistrictCode())
                        .andOrgTypeEqualTo(Const.ORG_TYPE_JIANCHAYUAN).andIsDeptNotEqualTo(1);
                        Organise defultJIancheyuan=mybatisAutoMapperService.selectByExample(OrganiseMapper.class, organiseExample, Organise.class).get(0);
                        caseBasic.setOrgId(defultJIancheyuan.getOrgCode());
                    }
                    //不是行政机构的,那就应该是检查院
                    else {
                        //县区级
                        caseBasic.setOrgId(organise.getOrgCode());
                    }
                }
            }
			PaginationHelper<CaseBasic> caseList = caseService.findYisongChufaQuery(caseBasic, page,null);
			model.addAttribute("procKey",procKey);
			model.addAttribute("orgTopId", request.getParameter("orgTopId"));
			model.addAttribute("orgTopDistrictCode", request.getParameter("orgTopDistrictCode"));
			model.addAttribute("caseList", caseList);
			model.addAttribute("param", caseBasic);
			model.addAttribute("page", page);
			return YISONGCHUFA_QUERY_VIEW;
		}
		
		@RequestMapping(value="queryUI")
		public String queryUI(ModelMap model,HttpServletRequest request){
			return YISONGCHUFA_QUERY_VIEW;
		}
		
	    //进行日期转换格式操作
	    @InitBinder
		public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest){
			webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
					new SimpleDateFormat("yyyy-MM-dd"), true));
		}
}
