package com.ksource.liangfa.web.system;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.AccuseInfo;
import com.ksource.liangfa.domain.AccuseInfoExample;
import com.ksource.liangfa.domain.AccuseInfoExample.Criteria;
import com.ksource.liangfa.domain.AccuseType;
import com.ksource.liangfa.mapper.AccuseInfoMapper;
import com.ksource.liangfa.mapper.AccuseTypeMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.AccuseInfoService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;

/**
 *@author 王振亚
 *@2012-7-24 下午2:40:06
 */
@Controller
@RequestMapping("system/accuseinfo")
public class AccuseInfoController {
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private AccuseInfoService accuseInfoService;
	@Autowired
	private SystemDAO systemDAO;
	
	private static final String ACCUSEINFO_MAIN="/system/accuse/accuseInfoMain";
	private static final String ACCUSEINFO_ADD="/system/accuse/accuseInfoAdd";
	private static final String ACCUSEINFO_ADDUI="redirect:/system/accuseinfo/addUI";
	private static final String ACCUSEINFO_UPDATE="/system/accuse/accuseInfoUpdate";
	private static final String ACCUSEINFO_UPDATEUI="redirect:/system/accuseinfo/updateUI";
	private static final String REDIRECT_MAIN="redirect:/system/accuseinfo/search/";
	private static final String ACCUSEINFO_QUERYUI="/system/accuse/accuseInfoQuery";
	private static final String ACCUSEINFO_QUERY="redirect:/system/accuseinfo/query";
	
	@RequestMapping(value="search/{accuseId}")
	public String search(AccuseInfo accuseInfo,Map<String, Object> model,
			@PathVariable Integer accuseId,String page){
		AccuseType accuseType = mybatisAutoMapperService.selectByPrimaryKey(AccuseTypeMapper.class, accuseId, AccuseType.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("level", accuseType.getAccuseLevel());
		map.put("id", accuseId);
		PaginationHelper<AccuseInfo> accuseInfos = accuseInfoService.search(accuseInfo, page, map);
		model.put("accuseInfos", accuseInfos);
		model.put("accuseId", accuseId);
		model.put("page", page);
		model.put("accuseInfo", accuseInfo);
		if(accuseType.getAccuseLevel()==1){
			model.put("accuseName", accuseType.getAccuseName());
		}else if(accuseType.getAccuseLevel()==2){
			AccuseType type = mybatisAutoMapperService.selectByPrimaryKey(AccuseTypeMapper.class, accuseType.getParentId(), AccuseType.class);
			model.put("accuseName", type.getAccuseName());
			model.put("accuseName2", accuseType.getAccuseName());
		}
		return ACCUSEINFO_MAIN;
	}
	@RequestMapping(value="addUI")
	public String addUI(Map<String, Object> model,Integer accuseId,ResponseMessage res){
		model.put("accuseId", accuseId);
		model.put("info", ResponseMessage.parseMsg(res));
		return ACCUSEINFO_ADD;
	}
	@RequestMapping(value="add")
	public String add(AccuseInfo accuseInfo,Integer accuseId){
		accuseInfo.setId(systemDAO.getSeqNextVal(Const.TABLE_ACCUSE_INFO));
		AccuseType accuseType = mybatisAutoMapperService.selectByPrimaryKey(AccuseTypeMapper.class, accuseId, AccuseType.class);
		if(accuseType.getAccuseLevel()==1){
			accuseInfo.setAccuseId1(accuseId);
		}else if(accuseType.getAccuseLevel()==2){
			accuseInfo.setAccuseId1(accuseType.getParentId());
			accuseInfo.setAccuseId2(accuseId);
		}
		mybatisAutoMapperService.insert(AccuseInfoMapper.class, accuseInfo);
		return ResponseMessage.addPromptTypeForPath(ACCUSEINFO_ADDUI+"?accuseId="+accuseId, PromptType.add);
	}
	
	@RequestMapping(value="updateUI")
	public String updateUI(Map<String, Object> model,Integer id,ResponseMessage res){
		AccuseInfo accuseInfo = mybatisAutoMapperService.selectByPrimaryKey(AccuseInfoMapper.class,id, AccuseInfo.class);
		model.put("info", ResponseMessage.parseMsg(res));
		model.put("accuseInfo", accuseInfo);
		return ACCUSEINFO_UPDATE;
	}
	
	@RequestMapping(value="update")
	public String update(AccuseInfo accuseInfo){
		mybatisAutoMapperService.updateByPrimaryKeySelective(AccuseInfoMapper.class, accuseInfo);
		return ResponseMessage.addPromptTypeForPath(ACCUSEINFO_UPDATEUI+"?id="+accuseInfo.getId(), PromptType.update);
	}
	
	@RequestMapping(value="delete")
	public String delete(Integer id,Integer accuseId){
		mybatisAutoMapperService.deleteByPrimaryKey(AccuseInfoMapper.class, id);
		return REDIRECT_MAIN+accuseId;
	}
	
	@RequestMapping(value = "showDetail")
	public ModelAndView showDetail(HttpServletRequest request, HttpServletResponse response, Integer accuseInfoId,Integer accuseId) throws Exception {
		ModelAndView mv = new ModelAndView("/system/accuse/showDetail");
		AccuseInfo accuseInfo = mybatisAutoMapperService.selectByPrimaryKey(AccuseInfoMapper.class, accuseInfoId, AccuseInfo.class);
		mv.addObject("accuseInfo", accuseInfo);
		mv.addObject("accuseId", accuseId);
		return mv;
	}
	
	@RequestMapping(value="checkName")
	@ResponseBody
	public boolean checkName(String name,Integer id){
		String checkname = name.trim();
		AccuseInfoExample example = new AccuseInfoExample();
		Criteria criteria = example.createCriteria().andNameEqualTo(checkname);
		if(id!=null){
			criteria.andIdNotEqualTo(id);
		}
		int size = mybatisAutoMapperService.countByExample(AccuseInfoMapper.class, example);
		if(size>0){
			return false;
		}
		return true;
	}
	
	
	@RequestMapping(value="queryAccuseTypeTree")
	public String queryUI(Map<String, Object> model,Integer id,ResponseMessage res){
		return "/system/accuse/accuseTypeTree";
	}

	@RequestMapping(value="query")
	public String queryMap(Integer accuseId,AccuseInfo accuseInfo,Map<String, Object> model,String page){
		AccuseType accuseType = mybatisAutoMapperService.selectByPrimaryKey(AccuseTypeMapper.class, accuseId, AccuseType.class); 
		if(accuseType!=null && accuseType.getAccuseLevel()==1){
			accuseInfo.setAccuseId1(accuseId);
		}else if(accuseType!=null && accuseType.getAccuseLevel()==2){
			accuseInfo.setAccuseId2(accuseId);
		}
		PaginationHelper<AccuseInfo> accuseInfos = accuseInfoService.queryAccuseWithIllegal(accuseInfo, page);
		model.put("accuseInfos", accuseInfos);
		model.put("page", page);
		model.put("accuseInfo", accuseInfo);
/*		if(accuseType.getAccuseLevel()==1){
			model.put("accuseName", accuseType.getAccuseName());
		}else if(accuseType.getAccuseLevel()==2){
			AccuseType type = mybatisAutoMapperService.selectByPrimaryKey(AccuseTypeMapper.class, accuseType.getParentId(), AccuseType.class);
			model.put("accuseName", type.getAccuseName());
			model.put("accuseName2", accuseType.getAccuseName());
		}*/
		return ACCUSEINFO_QUERYUI;
	}
}