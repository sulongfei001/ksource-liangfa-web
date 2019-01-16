package com.ksource.liangfa.web.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.OrgAmount;
import com.ksource.liangfa.domain.OrgAmountExample;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.OrganiseExample;
import com.ksource.liangfa.mapper.OrgAmountMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.OrgAmountService;
import com.ksource.syscontext.SystemContext;

/**
 *@author wangzhenya
 *@2012-11-22 上午11:32:23
 */
@Controller
@RequestMapping(value = "system/orgAmount")
public class OrgAmountController {

	private static final String ORGAMOUNT_MAIN= "system/orgAmount/orgAmountMain";
	private static final String ORGAMOUNT_UPDATE= "system/orgAmount/orgAmountSet";
	private static final String ORGAMOUNT_DETAIL= "system/orgAmount/orgAmountDetail";
	
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private OrgAmountService orgAmountService;
	
	@RequestMapping(value = "main")
	public String main(HttpSession session,ModelMap map,OrgAmount orgAmount,String page){
		Organise organise = SystemContext.getCurrentUser(session).getOrganise();
		
		OrganiseExample example = new OrganiseExample();
        if(organise!=null){
            example.createCriteria().andOrgCodeEqualTo(organise.getOrgCode()).andUpOrgCodeEqualTo(organise.getOrgCode());
        }
		int count = mybatisAutoMapperService.countByExample(OrganiseMapper.class, example);
		Map<String, Object> param = new HashMap<String, Object>();
		PaginationHelper<OrgAmount> orgAmounts;
        if(organise!=null){
            param.put("orgCode", organise.getOrgCode());
            map.addAttribute("orgCode", organise.getOrgCode());
        }
		if(count>1&&organise!=null){
			param.put("upOrgCode", organise.getOrgCode());
		}
        orgAmounts = orgAmountService.find(param, orgAmount, page);
		map.addAttribute("orgAmounts", orgAmounts);
		return ORGAMOUNT_MAIN;
	}
	
	@RequestMapping(value = "updateUI/{orgCode}")
	public String updateUI(@PathVariable Integer orgCode,ModelMap map){
		Organise organise = mybatisAutoMapperService.selectByPrimaryKey(
				OrganiseMapper.class, orgCode, Organise.class);
		OrgAmount orgAmount = mybatisAutoMapperService.selectByPrimaryKey(
				OrgAmountMapper.class, orgCode, OrgAmount.class);
		OrganiseExample example = new OrganiseExample();
		example.createCriteria().andUpOrgCodeEqualTo(orgCode).andIsDeptEqualTo(0);
		int count = mybatisAutoMapperService.countByExample(OrganiseMapper.class, example);
		map.addAttribute("orgAmount", orgAmount);
		map.addAttribute("organise", organise);
		map.addAttribute("count", count);
		return ORGAMOUNT_UPDATE;
	}
	
	@RequestMapping(value = "update")
	public String update(OrgAmount orgAmount){
		Integer judge=  orgAmount.getJudge()==null?1:orgAmount.getJudge();
		if(judge==0){//覆盖下级机构
			OrganiseExample example = new OrganiseExample();
			example.createCriteria().andUpOrgCodeEqualTo(orgAmount.getOrgCode());
			List<Organise> organises = mybatisAutoMapperService.selectByExample(
					OrganiseMapper.class, example, Organise.class);
			Organise org = new Organise();
			org.setOrgCode(orgAmount.getOrgCode());
			organises.add(org);
			for(Organise organise :organises){
				OrgAmountExample orgAmountExample = new OrgAmountExample();
				orgAmountExample.createCriteria().andOrgCodeEqualTo(organise.getOrgCode());
				int count = mybatisAutoMapperService.countByExample(OrgAmountMapper.class, orgAmountExample);
				orgAmount.setOrgCode(organise.getOrgCode());
				if(count>0){
					mybatisAutoMapperService.updateByPrimaryKey(OrgAmountMapper.class, orgAmount);
				}else{
					mybatisAutoMapperService.insert(OrgAmountMapper.class, orgAmount);
				}
			}
		}else{//不覆盖下级机构
			OrgAmountExample example = new OrgAmountExample();
			example.createCriteria().andOrgCodeEqualTo(orgAmount.getOrgCode());
			int count = mybatisAutoMapperService.countByExample(OrgAmountMapper.class, example);
			if(count>0){
				mybatisAutoMapperService.updateByPrimaryKey(OrgAmountMapper.class, orgAmount);
			}else{
				mybatisAutoMapperService.insert(OrgAmountMapper.class, orgAmount);
			}
		}
		
		return "redirect:/system/orgAmount/detail/"+orgAmount.getOrgCode();
	}
	@RequestMapping(value = "detail/{orgCode}")
	public String detail(@PathVariable Integer orgCode,ModelMap map){
		OrgAmountExample example = new OrgAmountExample();
		example.createCriteria().andOrgCodeEqualTo(orgCode);
		int count = mybatisAutoMapperService.countByExample(OrgAmountMapper.class, example);
		if(count>0){
			OrgAmount orgAmount = orgAmountService.findById(orgCode);
			map.addAttribute("orgAmount", orgAmount);
		}else{
			Organise organise = mybatisAutoMapperService.selectByPrimaryKey(
					OrganiseMapper.class, orgCode, Organise.class);
			map.addAttribute("organise", organise);
		}
		return ORGAMOUNT_DETAIL;
	}
}
