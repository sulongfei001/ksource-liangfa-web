package com.ksource.liangfa.web.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.OrganiseExample;
import com.ksource.liangfa.domain.Relevance;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.DistrictService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.syscontext.SystemContext;


@Controller
@RequestMapping("/system/org_relevance")
public class OrgRelevanceController {
	
	@Autowired
	private OrgService orgService;
	@Autowired
	private DistrictService districtService;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;

	/**
	 * 进入关联组织机构的管理页面
	 * @return
	 */
	@RequestMapping(value = "/manage")
	public String manage() {
		return "/system/org_velevance/tree";
	}
	
	
	/**
	 * 进入组织机构关联页面
	 * @param map
	 * @param orgType
	 * @param orgCode
	 * @return
	 */
	@RequestMapping(value = "/v_relevance")
	public String vRelevance(ModelMap map,String orgCode) {
		Organise organise =new Organise();
		List<District> districtList=null;
		//获取组织机构信息(组织机构所属行政区划和组织机构类型)
		OrganiseExample example = new OrganiseExample();
		example.createCriteria().andOrgCodeEqualTo(Integer.valueOf(orgCode));
		List<Organise> orgList = mybatisAutoMapperService.selectByExample(
				OrganiseMapper.class, example, Organise.class);
		organise =orgList.get(0);
		
		if(StringUtils.isNotBlank(organise.getDistrictCode())){
			districtList = districtService.queryByParentId(organise.getDistrictCode());
		}
		Map<String, Object> param = null;
		if(districtList!=null){
			for(District district : districtList) {
				param = new HashMap<String, Object>();
				param.put("districtCode", district.getDistrictCode());
				param.put("orgType", organise.getOrgType());
				List<Organise> orgs=orgService.queryOrgByOrgType(param);
				if(orgs != null) {
					district.setOrgs(orgs);
				}
			}
		}
		
		map.addAttribute("districtList", districtList);
		map.addAttribute("orgCode", orgCode);
		return "/system/org_velevance/org_relevance";
	}
	
	/**
	 * 更新关联机构
	 * @param orgCode
	 * @param check
	 * @return
	 */
	@RequestMapping(value = "/o_relevance")
	@ResponseBody
	public boolean oRelevance(String orgCode, String check) {
		boolean flag = true;
		JSONArray jsonArray =JSONArray.fromObject(check);
		@SuppressWarnings({ "unchecked", "deprecation" })
		List<Relevance> relevances = JSONArray.toList(jsonArray, Relevance.class);
		Map<String, Object> param = null;
		for(Relevance relevance : relevances) {
			param = new HashMap<String, Object>();
			param.put("upOrgCode", orgCode);
			param.put("districtCode", relevance.getDistrictCode());
			param.put("orgCode", relevance.getOrgCode());
			
			int result = orgService.relevanceOrg(param);
			if(result == 0) {
				flag = false;
			}
		}
		return flag;
	}
}
