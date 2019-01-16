package com.ksource.liangfa.web.query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/query/general")
public class GeneralQueryController {

	@Autowired
	private CaseService caseService;
	
	@RequestMapping(value="queryUI")
	public String queryUI(HttpSession session){
		session.removeAttribute("caseBasic");
		return "querystats/generalQueryUI";
	}
	
    @RequestMapping(value = "query")
    public ModelAndView search(CaseBasic caseBasic,Integer queryScope,Integer districtQueryScope,String page,HttpSession session) {
        User user = SystemContext.getCurrentUser(session);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Organise organise=user.getOrganise();
        //给参数赋值
  		String shortDistrictCode="";
  		if(StringUtils.isNotBlank(caseBasic.getDistrictCode())){
  		  if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
  		      shortDistrictCode=StringUtils.rightTrim0(caseBasic.getDistrictCode());
  		  }else{
  		    shortDistrictCode=caseBasic.getDistrictCode();
  		  }
  		}else{
  			shortDistrictCode=StringUtils.rightTrim0(organise.getDistrictCode());
  		}
  		caseBasic.setDistrictCode(shortDistrictCode);
        if (caseBasic.getOrgId()==null) {
			//如果用户是行政机构的
			if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
			   caseBasic.setOrgPath(organise.getOrgPath());
			}
        }else{
            if(queryScope == null || queryScope == Const.STATE_VALID){
                caseBasic.setOrgId(null);
            }
        }
        //森林公安和普通公安案件进行区分
      	if(Const.ORG_TYPE_GOGNAN.equals(organise.getOrgType()) && organise.getPoliceType() != null){
      		paramMap.put("policeType", organise.getPoliceType());
      	}
        PaginationHelper<CaseBasic> caseList = caseService.generalQuery(caseBasic, page, paramMap);
        ModelAndView mv=new ModelAndView("querystats/generalQuery");
        mv.addObject("procKey", "chufaProc");
        mv.addObject("caseList", caseList);
        mv.addObject("page", page);
        //保存查询条件
        session.setAttribute("caseBasic", caseBasic);
        return mv;
    }
    
    @RequestMapping(value="back")
    public ModelAndView back(Map<String,Object> model,HttpSession session){
        // 有查询条件,按查询条件返回
    	CaseBasic caseBasic;
        if (session.getAttribute("caseBasic") != null) {
        	caseBasic = (CaseBasic) session.getAttribute("caseBasic");
        } else {
        	caseBasic = new CaseBasic();
        }
        ModelAndView mv=new ModelAndView("querystats/generalQueryUI");
        mv.addObject("caseBasic", caseBasic);
		return mv;
    }
	
    //进行日期转换格式操作
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
	
}
