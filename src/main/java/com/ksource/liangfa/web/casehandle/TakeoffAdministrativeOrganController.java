package com.ksource.liangfa.web.casehandle;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.liangfa.domain.TakeoffAdministrativeOrgan;
import com.ksource.liangfa.domain.TakeoffAdministrativeOrganExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.TakeoffAdministrativeOrganMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.casehandle.TakeoffAdministrativeOrganService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;


@RequestMapping("/takeoffAdministrativeOrgan")
@Controller
public class TakeoffAdministrativeOrganController {

	@Autowired
	TakeoffAdministrativeOrganService takeoffAdministrativeOrganService ;
	@Autowired
	CaseService caseService ;
	@Autowired
	UserService userService ;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService ;
	
	@RequestMapping("/saveTakeoffAdministrativeOrgan")
	@ResponseBody
	public ServiceResponse saveTakeoffAdministrativeOrgan(TakeoffAdministrativeOrgan takeoffAdministrativeOrgan,String userIdString,HttpSession session) {
//		得到当前的用户信息
		User user = SystemContext.getCurrentUser(session) ;
		ServiceResponse serviceResponse = new ServiceResponse() ;
		takeoffAdministrativeOrgan.setTakeoffUser(user.getUserId()) ;
		takeoffAdministrativeOrgan.setTakeoffTime(new Date()) ;
		takeoffAdministrativeOrganService.saveTakeoffAdministrativeOrgan(takeoffAdministrativeOrgan, userIdString) ;
		serviceResponse.setResult(true) ;
		serviceResponse.setMsg("移送成功！") ;
		return serviceResponse ;
	}
	
	/**
	 * 查找移送目标
	 * @param caseId
	 * @param response
	 * @throws IOException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@RequestMapping("loadTakeOffTarget") 
	public void loadTakeOffTarget(String caseId,HttpServletResponse response) throws IOException, InstantiationException, IllegalAccessException {
				
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
//		1、根据caseId查找对应的行政区划代码
		String districtCode = caseService.getDistrictCode(caseId) ;
//		2、设置参数
		Map<String, Object> map = new HashMap<String, Object>() ;
		map.put("orgType", Const.ORG_TYPE_XINGZHENG) ;
		map.put("isDept", Const.IS_DEPT_NUM) ;
		map.put("districtCode", districtCode) ;
//		3、查找行政机关人名
		List<User>  list = userService.getTimeOutWarningTree(map) ;
//		4、查询市县的移送目标中对应的已经选中的人名
		TakeoffAdministrativeOrganExample takeoffAdministrativeOrganExample = new TakeoffAdministrativeOrganExample() ;
		takeoffAdministrativeOrganExample.createCriteria().andTakeoffCaseidEqualTo(caseId) ;
		List<TakeoffAdministrativeOrgan> takeoffAdministrativeOrgans = mybatisAutoMapperService.selectByExample(TakeoffAdministrativeOrganMapper.class, takeoffAdministrativeOrganExample, TakeoffAdministrativeOrgan.class) ;
//		5、生成json格式的字符串
		out.print(JsTreeUtils.timeOutWarningJsonztree(list,takeoffAdministrativeOrgans));
		out.flush() ;
		out.close() ;
	}
	
	/**
	 * 行政机关移送案件汇总
	 * @param session
	 * @return
	 */
	@RequestMapping("/findTakeoffAdministrativeOrgan")
	public ModelAndView findTakeoffAdministrativeOrgan(HttpSession session,String page) {
		ModelAndView view = new ModelAndView("casehandle/takeoffAdministrativeOrganList") ;
		User user = SystemContext.getCurrentUser(session) ;
		
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("userId", user.getUserId());
		
		PaginationHelper<TakeoffAdministrativeOrgan> takeoffAdministrativeOrgans =takeoffAdministrativeOrganService.queryTakeoffAdministrativeOrganList(page, param);
		view.addObject("takeoffAdministrativeOrgans", takeoffAdministrativeOrgans) ;
		return view ;
	}
}
