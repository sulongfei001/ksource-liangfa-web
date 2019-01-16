package com.ksource.liangfa.web.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.liangfa.domain.ClueInfo;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.DistrictExample;
import com.ksource.liangfa.domain.IndustryInfo;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.OrganiseExample;
import com.ksource.liangfa.domain.OrganiseExample.Criteria;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.ClueInfoMapper;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.instruction.InstructionSendService;
import com.ksource.liangfa.service.system.DistrictService;
import com.ksource.liangfa.service.system.IndustryInfoService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.license.LicenseInfo;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/system/org")
public class OrgController {
	/** 新增视图 */
	private static final String ADD_VIEW = "system/org/orgAdd";

	/** 重定向到 组织机构管理查询 */
	private static final String REDI_SEARCH_VIEW = "redirect:/system/org/search/";

	/** 修改视图 */
	private static final String UPDATE_VIEW = "system/org/orgUpdate";

	/** 详细信息视图 */
	private static final String DETAIL_VIEW = "system/org/orgDetail";

	/** 主界面视图 */
	private static final String MAIN_VIEW = "system/org/orgMain";
	@Autowired
	private OrgService orgService;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private DistrictService districtService;
	@Autowired
	private IndustryInfoService industryInfoService;
	@Autowired
	private InstructionSendService instructionSendService;
	@Autowired
	private ClueInfoMapper clueInfoMapper;

	@RequestMapping(value = "toMain")
	public String toMain(HttpSession session, ModelMap model) {
		User loginUser = SystemContext.getCurrentUser(session);
		Organise organise = mybatisAutoMapperService.selectByPrimaryKey(
				OrganiseMapper.class, loginUser.getOrgId(), Organise.class);
		model.put("organise", organise);

		return MAIN_VIEW;
	}

	/** 新增 组织机构 （分两步：1.添加组织机构数据2.修改父节点状态） */
	@RequestMapping(value = "add")
	public String add(Organise org,HttpServletRequest request) {
		// 1.1设置默认值
		Integer upOrgCode = (org.getUpOrgCode() == null) ? Const.TOP_ORG_ID
				: org.getUpOrgCode(); // 如果没有父组织机构，则表明此次添加的是虚拟节点(顶节点)的子节点。
		org.setIsLeaf(Const.LEAF_YES);
		org.setUpOrgCode(upOrgCode);
		org.setIsDept(Const.STATE_INVALID);
		if (org.getOrgType().equals(Const.ORG_TYPE_LIANGFALEADER)) {
			org.setIsLiangfaLeader(Const.STATE_VALID);
		}
		if(org.getIndustryType()!=null){
			IndustryInfo temp=industryInfoService.selectById(org.getIndustryType());
			if(temp!=null){
				org.setIndustryName(temp.getIndustryName());
			}
		}
		org.setCreateTime(new Date());
		org.setCreateUser(SystemContext.getCurrentUser(request).getUserId());
		orgService.add(org);

		return ResponseMessage.addIsLoadTreeForPath(
				REDI_SEARCH_VIEW + org.getOrgCode(), true);
	}

	//如果是添加行政执法机关，校验是否超过授权允许接入的行政区划和行政执法机关数量
		@RequestMapping(value = "checkLicense")
		@ResponseBody
		public ServiceResponse checkLicense(String districtCode) {
			ServiceResponse res = new ServiceResponse(true,"");
			//已接入的行政区划
			int districtCount=districtService.getUsedXingzhengCount();
			OrganiseExample example1 = new OrganiseExample();
			example1.createCriteria().andDistrictCodeEqualTo(districtCode);
			int count1 = mybatisAutoMapperService.countByExample(OrganiseMapper.class, example1);
			//已接入的行政执法机关
			OrganiseExample example = new OrganiseExample();
			example.createCriteria().andIsDeptEqualTo(com.ksource.syscontext.Const.STATE_INVALID).andOrgTypeEqualTo(com.ksource.syscontext.Const.ORG_TYPE_XINGZHENG);
			int orgCount =mybatisAutoMapperService.countByExample(OrganiseMapper.class, example);
			LicenseInfo licenseInfo = LicenseInfo.getLicenseInfo();
            /**
             *  1、不能超过 允许接入区划的数量
             2、不能超过 允许接入单位的数量
             3、已用行政区划与允许接入区划的数量相同时，新增加单位所在行政区划不能是新区划
             */
			if(licenseInfo.getDistrictCount()<districtCount
					|| licenseInfo.getXingzhengOrgCount()<=orgCount
					|| (licenseInfo.getDistrictCount()==districtCount && count1==0)){
				res.setingError("系统授权限制：\r\n允许接入的行政区划数量"+licenseInfo.getDistrictCount()+"个（已接入"+districtCount+"个）\r\n"
						+"允许接入的行政执法机关数量"+licenseInfo.getXingzhengOrgCount()+"个（已接入"+orgCount+"个）\r\n");
			}
			return res;
		}
	
	/**
	 * 进入 新增组织机构界面 在执行此方法之前，会先检查当前组织机构所在行政区划是否还有下级行政区划．
	 * 
	 * @see OrgController#checkOrgId(Integer)
	 * */
	@RequestMapping(value = "addUI/{orgId}")
	public ModelAndView addUI(String isError, @PathVariable Integer orgId) {
		ModelAndView view = new ModelAndView(ADD_VIEW);

		// 子行政区划信息
		List<District> dislist;

		// 当前行政区划信息
		District dis = null;

		// 当前组织信息
		Organise org = mybatisAutoMapperService.selectByPrimaryKey(
				OrganiseMapper.class, orgId, Organise.class);
		DistrictExample example = new DistrictExample();
		
        //公安办理区划
        List<District> acceptlist = mybatisAutoMapperService.selectByExample(DistrictMapper.class, example,District.class);
          
		if (org != null) {
			// 查询子行政区划信息
			example.createCriteria().andUpDistrictCodeEqualTo(
					org.getDistrictCode());
			// 查询当前行政区划信息
			dis = mybatisAutoMapperService
					.selectByPrimaryKey(DistrictMapper.class,
							org.getDistrictCode(), District.class);
		} else {
			// 查询子行政区划信息(如果当组织机构为空，则查询级别为１的行政区划．)
			example.createCriteria().andUpDistrictCodeIsNull();

			// TODO:查询当前行政区划信息
			List<District> districts = mybatisAutoMapperService
					.selectByExample(DistrictMapper.class, example,
							District.class);

			if ((districts != null) && !districts.isEmpty()) {
				dis = districts.get(0);
			}

			example.createCriteria().andUpDistrictCodeEqualTo(
					dis.getDistrictCode());
			
		}
		example.setOrderByClause("SERIAL,DISTRICT_CODE");
		dislist = mybatisAutoMapperService.selectByExample(
				DistrictMapper.class, example, District.class);

		view.addObject("org", org);
		view.addObject("districtCodeList", dislist);
		view.addObject("district", dis);
        view.addObject("acceptDistrict", acceptlist);

		if (isError != null) {
			String info = "true".equals(isError) ? "添加组织机构失败!" : "添加组织机构成功!";
			view.addObject("info", info);
		}
		//获取行业类型信息
		List<IndustryInfo> industryInfoList=industryInfoService.selectAll() ;
		view.addObject("industryInfoList", industryInfoList);
		return view;
	}

	// 删除 组织机构 (1.删除组织机构数据2.判断父组织机构状态)
	@RequestMapping(value = "del/{orgId}")
	@ResponseBody
	public ServiceResponse del(@PathVariable Integer orgId,
			HttpServletRequest request) {
		return orgService.del(orgId);
	}

	// 查询 组织机构
	@RequestMapping(value = "search/{orgId}")
	public ModelAndView search(@PathVariable Integer orgId, HttpSession session, ResponseMessage res) {
		ModelAndView view = new ModelAndView(DETAIL_VIEW);
		if (!orgId.equals(Const.TOP_ORG_ID)) {
			Organise org = mybatisAutoMapperService.selectByPrimaryKey(OrganiseMapper.class, orgId, Organise.class);
			District dis = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, org.getDistrictCode(), District.class);
	        District acceptdis = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, org.getAcceptDistrictCode(), District.class);

			Organise upOrg = null;
			District upDis = null;
			if (org != null) {
				upOrg = mybatisAutoMapperService.selectByPrimaryKey(OrganiseMapper.class, org.getUpOrgCode(), Organise.class);
				if (dis != null) {
					upDis = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, dis.getUpDistrictCode(), District.class);
				}
			}
			view.addObject("org", org);
			view.addObject("orgSearch_dis", dis);
			view.addObject("upDis", upDis);
	        view.addObject("acceptdis", acceptdis);
			view.addObject("upOrg", upOrg);
			view.addObject("isLoadTree", res.getIsLoadTree());
		}
		return view;
	}

	// 修改 组织机构
	@RequestMapping(value = "update")
	public String update(Organise org) {
		if(org.getIndustryType()!=null){
			IndustryInfo temp=industryInfoService.selectById(org.getIndustryType());
			if(temp!=null){
				org.setIndustryName(temp.getIndustryName());
			}
		}
		orgService.update(org);
		return REDI_SEARCH_VIEW + org.getOrgCode();
	}

	// 进入 修改组织机构界面
	@RequestMapping(value = "updateUI/{orgId}")
	public ModelAndView updateUI(@PathVariable Integer orgId) {
		ModelAndView view = new ModelAndView(UPDATE_VIEW);
		Organise org = mybatisAutoMapperService.selectByPrimaryKey(
				OrganiseMapper.class, orgId, Organise.class);
		District dis = mybatisAutoMapperService.selectByPrimaryKey(
				DistrictMapper.class, org.getDistrictCode(), District.class);
		Organise upOrg =null;
		District upDis=null;
		if(org!=null){
			upOrg = mybatisAutoMapperService.selectByPrimaryKey(
					OrganiseMapper.class, org.getUpOrgCode(), Organise.class);
			if(dis!=null){
				upDis = mybatisAutoMapperService.selectByPrimaryKey(
						DistrictMapper.class, dis.getUpDistrictCode(), District.class);
			}
		}
		
	     DistrictExample example = new DistrictExample();
        //公安办理区划
        List<District> acceptlist = mybatisAutoMapperService.selectByExample(DistrictMapper.class, example,District.class);
          
		view.addObject("org", org);
		view.addObject("dis", dis);
		view.addObject("upDis", upDis);
		view.addObject("upOrg", upOrg);
        view.addObject("acceptDistrict", acceptlist);

		//获取行业类型信息
		List<IndustryInfo> industryInfoList=industryInfoService.selectAll() ;
		view.addObject("industryInfoList", industryInfoList);
		return view;
	}

	@RequestMapping(value = "loadChildOrg")
	public void loadChildOrg(HttpServletRequest request,
			HttpServletResponse response, Integer id, boolean hasDept) {
		id = (id == null) ? Const.TOP_ORG_ID : id;
		List<Organise> orgs = orgService.find(id, hasDept);
		String trees = JsTreeUtils.orgDeptJsonztree(orgs, hasDept);
		response.setContentType("application/json");

		PrintWriter out = null;

		try {
			out = response.getWriter();
			out.print(trees);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 功能：进行案件查询时，查找“所属机构”树
	 * 
	 * @param request
	 * @param response
	 * @param orgType
	 */
	@RequestMapping(value = "loadChildOrgByOrgType")
	public void loadChildOrgByOrgType(HttpServletRequest request,
			HttpServletResponse response, String orgType) {
//		区分（行政处罚案件查询、移送涉嫌犯罪案件查询、行政机关移送案件查询）和 移送行政违法案件查询的所属机构
		List<Organise> orgs = new ArrayList<Organise>();
		// 第一次加载树数据时
		User user = SystemContext.getCurrentUser(request);
		Organise org = user.getOrganise();
		orgs = orgService.findSubordinateOrgs(org, orgType);
		String trees = JsTreeUtils.orgJsonztree(orgs, false);
		response.setContentType("application/json");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(trees);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	@RequestMapping(value = "getClueTree")
	public void getClueTree(HttpServletRequest request,
			HttpServletResponse response, String orgType) {
//		区分（行政处罚案件查询、移送涉嫌犯罪案件查询、行政机关移送案件查询）和 移送行政违法案件查询的所属机构
		List<Organise> orgs = new ArrayList<Organise>();
		// 第一次加载树数据时
		User user = SystemContext.getCurrentUser(request);
		Organise org = user.getOrganise();
		orgs = orgService.getClueTree(org,orgType);
		String trees = JsTreeUtils.orgJsonztree(orgs, false);
		response.setContentType("application/json");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(trees);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/** 得到除法院外其它组织机构 **/
	@RequestMapping(value = "loadOrgForReply")
	public void loadOrgForReply(Integer id ,HttpServletResponse response,HttpServletRequest request) {
		response.setContentType("application/json");
        Organise userOrg =SystemContext.getCurrentUser(request).getOrganise();
        String userDis;
        if(userOrg==null){
           userDis=SystemContext.getSystemInfo().getDistrict();
        }else{
           userDis=userOrg.getDistrictCode();
        }
		String districtCode = StringUtils.rightTrim0(userDis);
		OrganiseExample example = new OrganiseExample();
		example.createCriteria().andOrgTypeNotEqualTo(Const.ORG_TYPE_FAYUAN)
				.andIsDeptEqualTo(Const.STATE_INVALID).andDistrictCodeLike(districtCode+"%");
		List<Organise> orgs = mybatisAutoMapperService.selectByExample(
				OrganiseMapper.class, example, Organise.class);
		String trees = JsTreeUtils.orgJsonztree(orgs, false);
		response.setContentType("application/json");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(trees);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	// 得到符合公告的部门树
	@RequestMapping(value = "getCheckOrgTree")
	public void getCheckOrgTree(Integer id, String orgs,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User loginUser = SystemContext.getCurrentUser(request);
		Organise userOrg = loginUser.getOrganise();
		ArrayList<Integer> orgList = new ArrayList<Integer>();
		if (!orgs.isEmpty()) {
			String[] org = orgs.substring(0, orgs.length() - 1).split(",");
			for (int i = 0; i < org.length; i++) {
				Integer orgId = Integer.parseInt(org[i].trim());
				orgList.add(orgId);
			}
		}
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		if (!Const.SYSTEM_ADMIN_ID.equals(loginUser.getAccount())&&id == null) {
			id = userOrg.getOrgCode();
		}
		//机构类型是检察院时，用行政区划进行查询
		if (userOrg==null||Const.ORG_TYPE_JIANCHAYUAN.equals(userOrg.getOrgType())) {
			id = null;
            String districtCode;
            if(Const.SYSTEM_ADMIN_ID.equals(loginUser.getAccount())){
                districtCode=SystemContext.getSystemInfo().getDistrict();
            }else{
                districtCode = userOrg.getDistrictCode();
            }
			orgService.buildAllOrgTree(id, sb, orgList, StringUtils.rightTrim0(districtCode));
		} else {
			orgService.buildOrgTree(id, sb, orgList);
		}
		out.print(sb.toString());
		out.flush();
		out.close();
	}

	/** 验证数据库中是否存在指定id的组织机构信息 */
	@RequestMapping(value = "checkOrgId/{orgId}")
	@ResponseBody
	public ServiceResponse checkOrgId(@PathVariable Integer orgId) {
		ServiceResponse res = new ServiceResponse();
		Organise organise = mybatisAutoMapperService.selectByPrimaryKey(
				OrganiseMapper.class, orgId, Organise.class);

		if (organise == null) {
			res.setingError("所选组织机构已经被删除!");
		} else {
			res.setResult(true);
		}

		return res;
	}

	/** 验证数据库中是否存在指定name的组织机构信息 */
	@RequestMapping(value = "checkName")
	@ResponseBody
	public boolean checkName(String orgName, Integer orgId) {
		String orgname = orgName.trim();
		OrganiseExample example = new OrganiseExample();
		Criteria criteria = example.createCriteria().andOrgNameEqualTo(orgname);

		if (orgId != null) {
			criteria.andOrgCodeNotEqualTo(orgId);
		}

		int size = mybatisAutoMapperService.countByExample(
				OrganiseMapper.class, example);

		if (size > 0) {
			return false;
		} else {
			return true;
		}
	}

	/** 验证数据库中是否存在指定name的组织机构信息 */
	@RequestMapping(value = "checkDeptName")
	@ResponseBody
	public boolean checkDeptName(String orgName, Integer orgId, Integer deptId) {
		String orgname = orgName.trim();
		OrganiseExample example = new OrganiseExample();
		Criteria criteria = example.createCriteria().andOrgNameEqualTo(orgname)
				.andUpOrgCodeEqualTo(orgId);

		if (deptId != null) {
			criteria.andOrgCodeNotEqualTo(deptId);
		}

		int size = mybatisAutoMapperService.countByExample(
				OrganiseMapper.class, example);

		if (size > 0) {
			return false;
		} else {
			return true;
		}
	}

	@RequestMapping(value = "getOrgByDistrictId")
	@ResponseBody
	public List<Organise> getOrgByDistrictId(String districtId) {
		return orgService.findByDistrictId(districtId);
	}
	/**
	 * 查询所有可参与专项活动的组织机构
	 * @param response
	 * @param orgIds
	 * @param request
	 */
	@RequestMapping(value = "getAllMemberOrg")
	public void getAllOrg(HttpServletResponse response,String orgIds,HttpServletRequest request) {
		
		OrganiseExample example = new OrganiseExample();
		example.createCriteria().andIsDeptEqualTo(Const.STATE_INVALID);
		List<Organise> orgs =mybatisAutoMapperService.selectByExample(OrganiseMapper.class, example, Organise.class);
		String trees = JsTreeUtils.getAllOrg(orgs,orgIds);
		response.setContentType("application/json");

		PrintWriter out = null;

		try {
			out = response.getWriter();
			out.print(trees);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/**
	 * 获取专项活动的参与机构树
	 * @author: LXL
	 * @return:void
	 * @createTime:2017年10月18日 上午10:37:35
	 */
	@RequestMapping(value = "getAllMemberOrgTree")
	public ModelAndView getAllMemberOrgTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("activity/getAllMemberOrgTree");
		view.addObject("isSingle", false);
		return view;
	}

	/**
	 * 根据行政区划id查询专项互动下的组织机构
	 * 
	 * @author: LXL
	 * @return:ModelAndView
	 * @createTime:2017年10月18日 上午11:59:01
	 */
	@RequestMapping(value = "findOrgsForSpecialActByDistrictCode")
	public ModelAndView findOrgsForSpecialActByDistrictCode(HttpServletRequest request, String districtCode, boolean isSingle, String page, String flag) {
		ModelAndView mv = new ModelAndView("tree/specialAct_org_tree_list");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("isDept", 0);
		if (StringUtils.isNotEmpty(districtCode)) {
			paramMap.put("districtCode", StringUtils.rightTrim0(districtCode));
		}
		PaginationHelper<Organise> orgList = orgService.findOrgs(paramMap, page);
		mv.addObject("orgList", orgList);
		mv.addObject("districtCode", districtCode);
		mv.addObject("isSingle", isSingle);
		mv.addObject("page", page);
		mv.addObject("flag", flag);
		return mv;
	}
	

	// 部门列表页面
	@RequestMapping(value = "deptMain/{orgId}")
	public ModelAndView deptMain(@PathVariable Integer orgId) {
		ModelAndView view = new ModelAndView("system/org/deptMain");
		OrganiseExample organiseExample = new OrganiseExample();
		organiseExample.createCriteria().andUpOrgCodeEqualTo(orgId)
				.andIsDeptEqualTo(Const.STATE_VALID);
		List<Organise> organiseList = mybatisAutoMapperService.selectByExample(
				OrganiseMapper.class, organiseExample, Organise.class);
		view.addObject("organiseList", organiseList);

		Organise upOrg = mybatisAutoMapperService.selectByPrimaryKey(
				OrganiseMapper.class, orgId, Organise.class);
		view.addObject("upOrg", upOrg);
		return view;
	}

	// 部门详情
	@RequestMapping(value = "deptDetail/{deptId}")
	public ModelAndView deptDetail(@PathVariable Integer deptId, Integer success) {
		ModelAndView view = new ModelAndView("system/org/deptDetail");
		Organise dept = mybatisAutoMapperService.selectByPrimaryKey(
				OrganiseMapper.class, deptId, Organise.class);
		view.addObject("dept", dept);
		if (success != null && success == 1) {
			view.addObject("info", "添加部门信息成功！");
		} else if (success != null && success == 2) {
			view.addObject("info", "修改部门信息成功！");
		}
		return view;
	}

	// 添加部门页面
	@RequestMapping(value = "addDeptUI/{orgId}")
	public ModelAndView addDeptUI(@PathVariable Integer orgId) {
		ModelAndView view = new ModelAndView("system/org/deptAdd");
		Organise upOrg = mybatisAutoMapperService.selectByPrimaryKey(
				OrganiseMapper.class, orgId, Organise.class);
		view.addObject("upOrg", upOrg);
		return view;
	}

	// 添加部门
	@RequestMapping(value = "addDept")
	public ModelAndView addDept(Organise dept,HttpServletRequest request) {
		dept.setIsDept(Const.STATE_VALID);
		dept.setIsLeaf(Const.LEAF_YES);
		dept.setIsLiangfaLeader(Const.STATE_INVALID);
		dept.setCreateTime(new Date());
		dept.setCreateUser(SystemContext.getCurrentUser(request).getUserId());
		orgService.add(dept);
		ModelAndView view = new ModelAndView("redirect:/system/org/deptDetail/"
				+ dept.getOrgCode() + "?success=1");
		return view;
	}

	// 修改部门页面
	@RequestMapping(value = "updateDeptUI/{deptId}")
	public ModelAndView updateDeptUI(@PathVariable Integer deptId) {
		ModelAndView view = new ModelAndView("system/org/deptUpdate");
		Organise dept = mybatisAutoMapperService.selectByPrimaryKey(
				OrganiseMapper.class, deptId, Organise.class);
		view.addObject("dept", dept);
		return view;
	}

	// 修改部门
	@RequestMapping(value = "updateDept")
	public ModelAndView updateDept(Organise dept) {
		ModelAndView view = new ModelAndView("redirect:/system/org/deptDetail/"
				+ dept.getOrgCode() + "?success=2");
		mybatisAutoMapperService.updateByPrimaryKeySelective(
				OrganiseMapper.class, dept);
		return view;
	}

	// 删除部门
	@RequestMapping(value = "delDept/{deptId}")
	@ResponseBody
	public ServiceResponse delDept(@PathVariable Integer deptId) {
		return orgService.delDept(deptId);
	}
	//查询是牵头单位的组织机构
	@RequestMapping("loadLiangfaLeader")
	public void loadLiangfaLeader(HttpServletRequest request,
			HttpServletResponse response, Integer id, boolean hasDept) {
		id = (id == null) ? Const.TOP_ORG_ID : id;
		List<Organise> orgs = orgService.findAllOrgByNoDept();
		String trees = JsTreeUtils.loadLiangfaLeader(orgs);
		response.setContentType("application/json");

		PrintWriter out = null;

		try {
			out = response.getWriter();
			out.print(trees);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/** 一键增加用户功能，(分五步：1.添加组织机构数据2.增加部门3.增加岗位4.增加用户5.增加任务办理） */
	@RequestMapping(value = "addUser")
	public String addUser(HttpServletRequest request,Organise org) {
		Integer upOrgCode = (org.getUpOrgCode() == null) ? Const.TOP_ORG_ID
				: org.getUpOrgCode(); // 如果没有父组织机构，则表明此次添加的是虚拟节点(顶节点)的子节点。
		org.setIsLeaf(Const.LEAF_YES);
		org.setUpOrgCode(upOrgCode);
		org.setIsDept(Const.STATE_INVALID);
		if (org.getOrgType().equals(Const.ORG_TYPE_LIANGFALEADER)) {
			org.setIsLiangfaLeader(Const.STATE_VALID);
		}
		//查询行业名称
		if(org.getIndustryType()!=null){
			IndustryInfo temp=industryInfoService.selectById(org.getIndustryType());
			if(temp!=null){
				org.setIndustryName(temp.getIndustryName());
			}
		}
		String currentUserId=SystemContext.getCurrentUser(request).getUserId();
		orgService.addUser(org,currentUserId);
		return ResponseMessage.addIsLoadTreeForPath(
				REDI_SEARCH_VIEW + org.getOrgCode(), true);
	}
	
	//获取选中的下级组织机构
	@RequestMapping(value = "getSortList")
	public ModelAndView getSortList(Integer orgId,String redirect){
		ModelAndView view = new ModelAndView("system/org/orgSort");
		List<Organise> orgList=new ArrayList<Organise>();
		if(orgId!=null ){
			//根据orgId获取子节点组织信息
			orgList=orgService.findOrgByParentId(orgId);
		}
		view.addObject("orgList", orgList);
		return view;
	}
	
	//对组织机构进行排序
	@ResponseBody
	@RequestMapping(value = "sort")
	public boolean sort(String orgs){
		boolean result=true;
		Organise o=new Organise();
		if (StringUtils.isNotEmpty(orgs)) {
			String[] str=orgs.split(",");
			for (int i = 0; i < str.length; i++) {
				Integer orgCode = Integer.valueOf(str[i].toString());
				Integer orderNo = i + 1;
				o.setOrgCode(orgCode);
				o.setOrderNo(orderNo);
				//更新排序字段
				orgService.updateOrderNo(o);
				result=true;
			}
		}else{
			result=false;
		}
		return result;
	}
	
	
	/**
	 * 横向交流 树使用
	 */
	@RequestMapping("communionOrgTree")
	public ModelAndView extCommounionOrgTree(boolean isSingle,String searchRank,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("tree/communion_org_tree");
		mv.addObject("isSingle", isSingle);
		mv.addObject("searchRank", searchRank);
		return mv;
	}
	@RequestMapping("communionOrgTreeByLigerUI")
	public ModelAndView communionOrgTreeByLigerUI(boolean isSingle,String searchRank,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("tree/communion_org_tree_ligerUI");
		mv.addObject("isSingle", isSingle);
		mv.addObject("searchRank", searchRank);
		return mv;
	}
	@RequestMapping(value = "findCommunionByDistrictCode")
	public ModelAndView findCommunionByDistrictCode(HttpServletRequest request,String districtCode,boolean isSingle,String page,String flag,String searchRank) {
		ModelAndView mv = new ModelAndView("tree/communion_org_list");
		Map<String,Object> param = new HashMap<String,Object>();
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg = currUser.getOrganise();
		if(StringUtils.isNotBlank(districtCode)){
			param.put("districtCode", districtCode);
		}else{
		    if(StringUtils.isBlank(searchRank)){
		        param.put("districtCode", currOrg.getDistrictCode()); 
		    }
		}
		param.put("currOrg", currOrg.getOrgCode());
		param.put("orgType", Const.ORG_TYPE_XINGZHENG);
		PaginationHelper<Organise> orgList = orgService.findAllNoSelftByDistrict(param,page);
		mv.addObject("orgList", orgList);
		mv.addObject("districtCode", districtCode);
		mv.addObject("isSingle", isSingle);
		mv.addObject("page", page);
		mv.addObject("flag", flag);
		return mv;
	}	
	
	@RequestMapping("extOrgTree")
	public ModelAndView dialog(boolean isSingle,String searchRank,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("tree/ext_org_tree");
		mv.addObject("isSingle", isSingle);
		mv.addObject("searchRank", searchRank);
		return mv;
	}
	@RequestMapping("extOrgTreeByLigerUI")
	public ModelAndView extOrgTreeByLigerUI(boolean isSingle,String searchRank,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("tree/ext_org_tree_ligerUI");
		mv.addObject("isSingle", isSingle);
		mv.addObject("searchRank", searchRank);
		return mv;
	}
	
	@RequestMapping(value = "findByDistrictCode")
	public ModelAndView findByDistrictId(HttpServletRequest request,String districtCode,boolean isSingle,String page,String flag,String searchRank) {
		ModelAndView mv = new ModelAndView("tree/ext_org_list");
		Map<String,Object> param = new HashMap<String,Object>();
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg = currUser.getOrganise();
		District district = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, currOrg.getDistrictCode(), District.class);
		if(StringUtils.isNotBlank(districtCode)){
			param.put("districtCode", StringUtils.rightTrim0(districtCode));
			if(StringUtils.isNotBlank(searchRank)&& searchRank.equals(Const.SEARCH_HIGHER_ORG)){//如果是汇报工作，则查询上级行政区划的机构
				param.put("jb", district.getJb().intValue() - 1);
			}
		}else{
			param.put("districtCode", StringUtils.rightTrim0(district.getDistrictCode()));
			if(StringUtils.isNotBlank(searchRank) && Const.SEARCH_HIGHER_ORG.equals(searchRank)){
				param.put("districtCode", StringUtils.rightTrim0(district.getUpDistrictCode()));
				param.put("jb", district.getJb().intValue() - 1);
				param.put("upOrgCode", currOrg.getUpOrgCode());
			}
		}
		//权限控制
		//param.put("industryType", currOrg.getIndustryType());
		//权限控制,排除当前登陆机构
		param.put("currOrg", currOrg.getOrgCode());
		//控制非行政单位权限
		//if(!currOrg.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)){
		param.put("orgType", currOrg.getOrgType());
		//
		if(currOrg.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !Const.SEARCH_HIGHER_ORG.equals(searchRank)){
			param.put("orgPath", currOrg.getOrgPath());
		}
		//}
		//移送管辖传值searchRank为1，判断获取下级行政机关
		if(searchRank.equals("1")){
			param.put("searchRank", searchRank);
		}
		PaginationHelper<Organise> orgList = orgService.findOrgs(param,page);
		mv.addObject("orgList", orgList);
		mv.addObject("districtCode", districtCode);
		mv.addObject("isSingle", isSingle);
		mv.addObject("page", page);
		mv.addObject("flag", flag);
		return mv;
	}
	
	@RequestMapping("orgTreeDialog")
	public ModelAndView orgTreeDialog(boolean isSingle,String flag, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("tree/org_tree_dialog");
		mv.addObject("flag",flag);
		mv.addObject("isSingle", isSingle);
		return mv;
	}

	/**
	 * 行政单位移送其他
	 * 
	 * @author: LXL
	 * @return:ModelAndView
	 * @createTime:2017年11月8日 上午9:17:09
	 */
	@RequestMapping("orgTreeDialogYiSongOther")
	public ModelAndView orgTreeDialogYiSongOther(boolean isSingle,String flag, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("tree/org_tree_dialog_yisong_other");
		mv.addObject("flag",flag);
		mv.addObject("isSingle", isSingle);
		return mv;
	}
	
	@RequestMapping(value = "orgTreeList")
	public ModelAndView orgTreeList(HttpServletRequest request,String districtCode,boolean isSingle,String page,String flag) {
		ModelAndView mv = new ModelAndView("tree/org_tree_list");
		Map<String,Object> param = new HashMap<String,Object>();
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg = currUser.getOrganise();
		if(StringUtils.isNotBlank(districtCode)){
			param.put("districtCode", StringUtils.rightTrim0(districtCode));
		}else{
			District district = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, currOrg.getDistrictCode(), District.class);			
			param.put("districtCode", StringUtils.rightTrim0(district.getDistrictCode()));
		}
		//权限控制
		//param.put("industryType", currOrg.getIndustryType());
		//权限控制,排除当前登陆机构
		param.put("currOrg", currOrg.getOrgCode());
		if("yisong".equals(flag)){
			param.put("orderBy", "orgType");
		}
		
		PaginationHelper<Organise> orgList = orgService.findOrgs(param,page);
		mv.addObject("orgList", orgList);
		mv.addObject("districtCode", districtCode);
		mv.addObject("isSingle", isSingle);
		mv.addObject("page", page);
		mv.addObject("flag", flag);
		return mv;
	}	
	
	/**
	 * 行政单位移送其他组织机构树列表页面
	 * 
	 * @author: LXL
	 * @return:ModelAndView
	 * @createTime:2017年11月8日 上午9:32:13
	 */
	@RequestMapping(value = "orgTreeYiSongOtherList")
	public ModelAndView orgTreeYiSongOtherList(HttpServletRequest request, String districtCode, boolean isSingle, String page, String flag) {
		ModelAndView mv = new ModelAndView("tree/org_tree_yisong_other_list");
		Map<String, Object> param = new HashMap<String, Object>();
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg = currUser.getOrganise();
		if (StringUtils.isNotBlank(districtCode)) {
			param.put("districtCode", districtCode);
		} else {
			District district = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, currOrg.getDistrictCode(), District.class);
			if (district != null) {
				param.put("districtCode", district.getDistrictCode());
			}
		}
		// 权限控制
		// param.put("industryType", currOrg.getIndustryType());
		// 权限控制,排除当前登陆机构
		param.put("currOrg", currOrg.getOrgCode());
		if ("yisong".equals(flag)) {
			param.put("orderBy", "orgType");
		}

		PaginationHelper<Organise> orgList = orgService.findYiSongOtherOrgs(param, page);
		mv.addObject("orgList", orgList);
		mv.addObject("districtCode", districtCode);
		mv.addObject("isSingle", isSingle);
		mv.addObject("page", page);
		mv.addObject("flag", flag);
		return mv;
	}
	
	
	// 获取接收部门树
	@RequestMapping(value = "getInstructionOrgTree")
	@ResponseBody
	public List<Organise> getInstructionOrgTree(Integer orgCode,String receiveOrgs,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			User currUser = SystemContext.getCurrentUser(request);
			Organise currOrg = currUser.getOrganise();
			if(orgCode == null){
				orgCode = currOrg.getOrgCode();
			}
			//已选择的组织
			ArrayList<String> orgList = new ArrayList<String>();
			if (!receiveOrgs.isEmpty()) {
				String[] org = receiveOrgs.substring(0, receiveOrgs.length() - 1).split(",");
				for (int i = 0; i < org.length; i++) {
					String orgId = org[i].trim().toString();
					orgList.add(orgId);
				}
			}
			return orgService.buildIndutryOrgTree(orgCode,orgList);
		}	
//	/**
//	 * 获取交办单位
//	 * @param request
//	 * @param modelMap
//	 * @param org
//	 * @param page
//	 * @param caseTodo
//	 * @return
//	 */
//	@RequestMapping("getChildOrg")
//	public ModelAndView getChildOrg(HttpServletRequest request,ModelMap modelMap,
//			Organise org,String page,CaseTodo caseTodo){
//		User user = SystemContext.getCurrentUser(request);
//		org = user.getOrganise();
//		ModelAndView mv = new ModelAndView("tree/jiaoban_tree_list");
//		PaginationHelper<Organise> orgList = orgService.getChildOrg(org, page);
//		modelMap.addAttribute("orgList", orgList);
//		modelMap.addAttribute("caseTodo", caseTodo);
//		return mv;
//	}
	/**
	 * 行政立案菜单的分派
	 * @param request
	 * @param modelMap
	 * @param org
	 * @param page
	 * @param caseTodo
	 * @return
	 */
	@RequestMapping("getChildOrg")
	public ModelAndView getChildOrgFromCaseTodoLian(HttpServletRequest request,
			Organise org,String page,String fromView){
		User user = SystemContext.getCurrentUser(request);
		org = user.getOrganise();
		ModelAndView mv = new ModelAndView("tree/jiaoban_tree_list");
		PaginationHelper<Organise> orgList = orgService.getChildOrg(org, page);
		//modelMap.addAttribute("orgList", orgList);
		mv.addObject("orgList", orgList);
		mv.addObject("fromView",fromView);
		return mv;
	}
	/**
	 * 获取移送管辖单位
	 */
	@RequestMapping("getTurnOverOrg")
	public ModelAndView getTurnOverOrg(HttpServletRequest request,ModelMap modelMap,String page){
		ModelAndView mv = new ModelAndView("tree/turnover_tree_list");
		User user = SystemContext.getCurrentUser(request);
		Integer orgCode = user.getOrgId();
		Organise currOrg = user.getOrganise();
		District district = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, currOrg.getDistrictCode(), District.class);
		String districtCode = district.getDistrictCode();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orgCode", orgCode);
		paramMap.put("orgType", Const.ORG_TYPE_XINGZHENG);
		paramMap.put("isDept", Const.IS_DEPT_NUM);
		paramMap.put("districtCode", districtCode);
		PaginationHelper<Organise> orgList = orgService.getTurnOverOrg(paramMap,page);
		modelMap.addAttribute("orgList", orgList);
		return mv;
	}
	
	/**
	 * 获取移送管辖单位
	 *//*
	@RequestMapping("getTurnOverOrgFromCaseTodoLian")
	public ModelAndView getTurnOverOrgFromCaseTodoLian(HttpServletRequest request,ModelMap modelMap,String page){
		ModelAndView mv = new ModelAndView("tree/turnover_tree_list_lian");
		User user = SystemContext.getCurrentUser(request);
		Integer orgCode = user.getOrgId();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orgCode", orgCode);
		paramMap.put("orgType", Const.ORG_TYPE_XINGZHENG);
		paramMap.put("isDept", Const.IS_DEPT_NUM);
		PaginationHelper<Organise> orgList = orgService.getTurnOverOrg(paramMap,page);
		modelMap.addAttribute("orgList", orgList);
		return mv;
	}*/
	
	/**
	 * 检察院分配线索查询行政机构列表
	 * @param request
	 * @param modelMap
	 * @param page
	 * @param clueInfo 创建线索的组织机构所在行政区划id
	 * @return
	 */
	@RequestMapping("getClueOrgTree")
	public String getClueOrgTree(HttpServletRequest request,ModelMap modelMap,String page,Integer clueId){
		//查询创建机构所在行政区划所有的行政单位
		ClueInfo clue = clueInfoMapper.selectByPrimaryKey(clueId);
		Organise organise = orgService.selectByorgCode(clue.getCreateOrg());
		//组织机构
		organise.setIsDept(Const.IS_DEPT_NUM);
		//机构类别
		organise.setOrgType(Const.ORG_TYPE_XINGZHENG);
		PaginationHelper<Organise> orgList = orgService.getClueOrgTree(organise,page);
		modelMap.addAttribute("orgList", orgList);
		return "tree/clue_tree_list";
	}
	
	/**
	 * 组织机构树异步加载使用，根据父节点查询子节点组织信息
	 * @param request
	 * @param response
	 * @param orgType
	 * @param id
	 */
	@RequestMapping(value = "loadChildOrgByParentId")
	public void loadChildOrgByParentOrg(HttpServletRequest request,
			HttpServletResponse response, String orgType,Integer id) {
		List<Organise> orgs = new ArrayList<Organise>();
		User user = SystemContext.getCurrentUser(request);
		Organise org = user.getOrganise();
		orgs = orgService.findChildOrgs(org, orgType,id);
		String trees = JsTreeUtils.orgJsonztree(orgs, false);
		response.setContentType("application/json");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(trees);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/**
	 * 组织机构树异步加载使用，查询案件咨询参与人组织机构
	 * @param request
	 * @param response
	 * @param orgType
	 */
	@RequestMapping(value = "loadParticipantsOrg")
	public void loadParticipantsOrg(HttpServletRequest request,
			HttpServletResponse response,String orgType,String id){
		List<Organise> orgs = new ArrayList<Organise>();
		User user = SystemContext.getCurrentUser(request);
		Organise org = user.getOrganise();
		orgs = orgService.findParticipantsOrg(org, orgType,id);
		String trees = JsTreeUtils.orgJsonztree(orgs, false);
		response.setContentType("application/json");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(trees);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
}
