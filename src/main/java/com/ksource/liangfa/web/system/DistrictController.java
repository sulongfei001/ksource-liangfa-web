package com.ksource.liangfa.web.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.DistrictExample;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.DistrictService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;

/**
 * 此类为 行政区划控制器
 * 
 * @author zxl :)
 * @version 1.0 date 2011-8-13 time 上午09:42:43
 */
@Controller
@RequestMapping("/system/district")
public class DistrictController {
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	DistrictService districtService;
	
	@RequestMapping(value = "checkId/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ServiceResponse checkId(@PathVariable String id) {
		ServiceResponse response = new ServiceResponse(true, "");
		District district = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, id, District.class);

		if (district == null) {
			response.setingError("该行政区划已被删除");
		}
		return response;
	}

	@RequestMapping(value = "loadChild", method = RequestMethod.POST)
	public void loadChild(String id, HttpServletResponse response,HttpSession session)throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (id == null) {// TODO:市县两级管理
			id = SystemContext.getSystemInfo().getDistrict();
		}
		out.print(JsTreeUtils.districtJsonztree(districtService.getDescendants(id)));
	}

	/**
	 * 进入行政区划的管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/main")
	public String main() {
		return "system/district/tree";
	}

	/**
	 * 行政区划树管理页面
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/v_tree")
	public void vTree(String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(id)) {
			User user = SystemContext.getCurrentUser(request);
			if (Const.SYSTEM_ADMIN_ID.equals(user.getAccount())) {
				id = SystemContext.getSystemInfo().getDistrict();
			} else {
				id = user.getOrganise().getDistrictCode();
			}
			map.put("districtCode", id);
		} else {
			map.put("upDistrictCode", id);
		}
		List<District> districts = districtService.districtTreeManage(map);
		out.print(JsTreeUtils.districtTreeManage(districts));
	}

	/**
	 * 进入行政区划管理页面
	 * 
	 * @param district
	 * @param map
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/district_manage")
	public String districtManage(District district, ModelMap map, String page) {
		PaginationHelper<District> helpers = districtService.districtManage(
				district, page);
		map.addAttribute("helpers", helpers);
		map.addAttribute("district", district);
		map.addAttribute("page", page);
		return "system/district/list";
	}

	/**
	 * 进入行政区划添加页面
	 * 
	 * @param upDistrictCode
	 * @param map
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/v_add")
	public String vAdd(String upDistrictCode, ModelMap map,
			ResponseMessage message) {
		map.addAttribute("upDistrictCode", upDistrictCode);
		map.addAttribute("info", ResponseMessage.parseMsg(message));
		return "system/district/add";
	}

	/**
	 * 进行行政区划的添加操作
	 * 
	 * @param district
	 * @return
	 */
	@RequestMapping(value = "/o_add")
	public String oAdd(District district) {
		District dis = mybatisAutoMapperService.selectByPrimaryKey(
				DistrictMapper.class, district.getUpDistrictCode(),
				District.class);
		if (dis.getJb() != null) {
			district.setJb(dis.getJb() + 1);
		} else {
			district.setJb(1);
		}
		mybatisAutoMapperService.insert(DistrictMapper.class, district);
		return ResponseMessage.addPromptTypeForPath(
				"redirect:/system/district/v_add?upDistrictCode="
						+ district.getUpDistrictCode(), PromptType.add);
	}

	/**
	 * 进入行政区划修改页面
	 * 
	 * @param upDistrictCode
	 * @param map
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/v_update")
	public String vUpdate(String districtCode, ModelMap map,
			ResponseMessage message) {
		District district = mybatisAutoMapperService.selectByPrimaryKey(
				DistrictMapper.class, districtCode, District.class);
		map.addAttribute("district", district);
		map.addAttribute("info", ResponseMessage.parseMsg(message));
		return "system/district/update";
	}

	/**
	 * 进行行政区划的修改操作
	 * 
	 * @param district
	 * @return
	 */
	@RequestMapping(value = "/o_update")
	public String oUpdate(District district) {
		mybatisAutoMapperService.updateByPrimaryKeySelective(
				DistrictMapper.class, district);
		return ResponseMessage.addPromptTypeForPath(
				"redirect:/system/district/v_update?districtCode="
						+ district.getDistrictCode(), PromptType.update);
	}

	/**
	 * 进入行政区划详情页面
	 * 
	 * @param districtCode
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public String detail(String districtCode, ModelMap map) {
		District district = mybatisAutoMapperService.selectByPrimaryKey(
				DistrictMapper.class, districtCode, District.class);
		map.addAttribute("district", district);
		return "system/district/detail";
	}

	/**
	 * 行政区划删除操作
	 * 
	 * @param districtCode
	 * @param upDistrictCode
	 * @return
	 */
	@RequestMapping(value = "/delete")
	public String delete(String districtCode, String upDistrictCode) {
		mybatisAutoMapperService.deleteByPrimaryKey(DistrictMapper.class,
				districtCode);
		return "redirect:/system/district/district_manage?upDistrictCode="
				+ upDistrictCode;
	}

	@RequestMapping(value = "/init")
	@ResponseBody
	public ServiceResponse initAuthority() {
		ServiceResponse res = districtService.initAuthority();
		return res;
	}

	/**
	 * 验证行政区划代码不能重复
	 * 
	 * @param districtCode
	 * @return
	 */
	@RequestMapping(value = "/check")
	@ResponseBody
	public boolean check(String districtCode) {
		String code = districtCode.trim();
		DistrictExample example = new DistrictExample();
		example.createCriteria().andDistrictCodeEqualTo(code);
		int result = mybatisAutoMapperService.countByExample(
				DistrictMapper.class, example);
		if (result > 0) {
			return false;
		} else {
			return true;
		}
	}

//	// 添加行政区划时的校验，如果数据库中已经存在的行政区划个数（无论是否使用过）等于授权文件中的个数，则不能再添加了
//	@RequestMapping(value = "/checkLicense")
//	@ResponseBody
//	public ServiceResponse checkLicense() {
//		ServiceResponse res = new ServiceResponse(true, "");
//		
//		//获取已经录入的所有区划
//		DistrictExample example = new DistrictExample();
//		example.createCriteria().andJbEqualTo(Const.DISTRICT_JB_2);
//		List<District> districts = mybatisAutoMapperService.selectByExample(DistrictMapper.class, example,District.class);
//		
//		int   districtCount =districts.size();
//		
//		LicenseInfo licenseInfo = LicenseInfo.getLicenseInfo();
//		if (licenseInfo.getDistrictCount() <= districtCount) {
//			res.setResult(false);
//			res.setingError("系统授权限制：\r\n允许接入的行政区划数量"
//					+ licenseInfo.getDistrictCount() + "个（已录入" + districtCount
//					+ "个）,请使用已录入的区划添加行政机关\r\n");
//		}
//		return res;
//	}

	@RequestMapping(value = "loadChildTree", method = RequestMethod.POST)
	public void loadChildTree(String id, HttpServletResponse response,HttpSession session)throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (id == null) {// TODO:市县两级管理
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				id=SystemContext.getSystemInfo().getDistrict();
			}else {
				id=SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
		}
		out.print(JsTreeUtils.districtJsonztree(districtService.getDescendants(id)));
	}
	
	@RequestMapping(value="loadDistrictTree",method=RequestMethod.POST)
	public void loadDistrictTree(String id,String searchRank,HttpServletRequest request,HttpServletResponse response) throws IOException{
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg = currUser.getOrganise();
		Map<String,Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(id)){
			params.put("upDistrictCode", id);
		}else{
		    if(StringUtils.isBlank(searchRank)){
		        params.put("districtCode", currOrg.getDistrictCode());
		    }
		}
		List<District> districts = districtService.districtTreeManage(params);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String jsonZtree = JsTreeUtils.districtTreeManage(districts);
		out.print(jsonZtree);
	}
	
	
	@RequestMapping(value="loadDistrictTreeForInstruction",method=RequestMethod.POST)
	public void loadDistrictTreeForInstruction(String id,String searchRank,HttpServletRequest request,HttpServletResponse response) throws IOException{
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg = currUser.getOrganise();
		District district = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, currOrg.getDistrictCode(), District.class);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Map<String, Object> map = new HashMap<String, Object>();
		List<District> districts =null;
		if(StringUtils.isNotBlank(id)){
			map.put("upDistrictCode", id);
			if(searchRank.equals(Const.SEARCH_HIGHER_ORG)){//如果是汇报工作，则查询上级行政区划的机构
				map.put("jb", district.getJb().intValue() - 1);
				districts = districtService.districtTreeManage(map);
			}else if(searchRank.equals(Const.SEARCH_COMMUNION_ORG)){//如果是横向交流，则查询同级别（其他）行政区划的机构
				map.put("upDistrictCode", district.getUpDistrictCode());
				map.put("districtCode", currOrg.getDistrictCode());
				districts = districtService.districtTreeCommunion(map);
			}
		}else{
			if(district !=null){
				map.put("upDistrictCode", district.getUpDistrictCode());
			}
			if(searchRank.equals(Const.SEARCH_HIGHER_ORG)){//如果是汇报工作，则查询上级行政区划的机构
				District upDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, district.getUpDistrictCode(), District.class);
				map.put("upDistrictCode", upDistrict.getUpDistrictCode());
				map.put("jb", district.getJb().intValue() - 1);
				districts = districtService.districtTreeManage(map);
			}else if(searchRank.equals(Const.SEARCH_COMMUNION_ORG)){//如果是横向交流，则查询同级别（其他）行政区划的机构
				map.put("upDistrictCode", district.getUpDistrictCode());
				map.put("districtCode", currOrg.getDistrictCode());
				districts = districtService.districtTreeCommunion(map);
				//只显示平级用户，屏蔽子节点
				for(District de:districts){
					de.setChirdNum(0);
				}
			}else{
				map.put("upDistrictCode", district.getDistrictCode());
				districts = districtService.districtTreeManage(map);	
			}
		}
		String jsonZtree = JsTreeUtils.districtTreeManage(districts);
		out.print(jsonZtree);
	}

	/**
	 * 加载行政区划树为专项活动
	 * 
	 * @author: LXL
	 * @return:void
	 * @createTime:2017年10月18日 上午11:20:59
	 */
	@RequestMapping(value = "loadDistrictTreeForSpecialAct", method = RequestMethod.POST)
	public void loadDistrictTreeForSpecialAct(String id, String searchRank, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<District> districts = districtService.getDistrictListForSpecialAct(paramMap);
		String jsonZtree = JsTreeUtils.districtTreeManage(districts);
		out.print(jsonZtree);
	}
	
	/**
	 * 线索发生地区划树
	 * @param id
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "getClueAddressTree", method = RequestMethod.POST)
	public void getClueAddressTree(String id, HttpServletResponse response,HttpSession session)throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (id == null) {// TODO:市县两级管理
			id = SystemContext.getSystemInfo().getDistrict();
		}
		out.print(JsTreeUtils.districtJsonztree(districtService.getDescendants(id)));
	}
	
	/**
	 * 异步加载树
	 * 
	 * @author: LXL
	 * @return:List<District>
	 * @createTime:2017年9月19日 下午1:05:17
	 */
	@RequestMapping(value = "loadChildAsync")
	@ResponseBody
	public List<District> loadChildAsync(District district, String upDistrictCode, HttpServletResponse response, HttpSession session) throws IOException {
		if (district.getDistrictCode() == null) {
			district.setDistrictCode(SystemContext.getSystemInfo().getDistrict());
		}
		return districtService.loadChildAsync(district);
	}
}
