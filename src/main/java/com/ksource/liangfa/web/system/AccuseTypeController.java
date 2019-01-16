package com.ksource.liangfa.web.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.common.util.JsTreeUtils;
import com.ksource.common.util.ServletResponseUtil;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.AccuseInfo;
import com.ksource.liangfa.domain.AccuseInfoExample;
import com.ksource.liangfa.domain.AccuseType;
import com.ksource.liangfa.domain.AccuseTypeExample;
import com.ksource.liangfa.domain.AccuseTypeExample.Criteria;
import com.ksource.liangfa.mapper.AccuseInfoMapper;
import com.ksource.liangfa.mapper.AccuseTypeMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.AccuseInfoService;
import com.ksource.liangfa.service.system.AccuseTypeService;
import com.ksource.syscontext.Const;

/**
 *@author 王振亚
 *@2012-7-19 下午5:28:56
 */
@Controller
@RequestMapping("system/accusetype")
public class AccuseTypeController {

	@Autowired
	private AccuseTypeService accuseTypeService;
	@Autowired
	private AccuseInfoService accuseInfoService;
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	
	@RequestMapping(value = "loadAccuseType")
	public void loadAccuseType(HttpServletRequest request,
			HttpServletResponse response, Integer id, Integer lev) {
		id = (id == null) ? -2: id;
		lev = (lev == null||lev == 0)?0:lev;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("level", lev);
		List<AccuseType> accuseTypes = accuseTypeService.find(map);
		String trees = JsTreeUtils.accuseTypeJsonztree(accuseTypes);
		ServletResponseUtil servletResponseUtil = new ServletResponseUtil();
		servletResponseUtil.writeJson(response, trees);
	}
	
	@RequestMapping(value = "add")
	@ResponseBody
	public AccuseType add(String accuseName,Integer accuseId,Integer order){
		AccuseType accuseType = new AccuseType();
		accuseType.setAccuseOrder(order);
		if(accuseId < 0){
			accuseType.setAccuseLevel(1);
			accuseType.setParentId(-1);
		}else{
			AccuseType type = mybatisAutoMapperService.selectByPrimaryKey(AccuseTypeMapper.class, accuseId, AccuseType.class);
			accuseType.setAccuseLevel(type.getAccuseLevel()+1);
			accuseType.setParentId(accuseId);
		}
		accuseType.setAccuseName(accuseName);
		accuseType.setAccuseId(systemDAO.getSeqNextVal(Const.TABLE_ACCUSE_TYPE));
		mybatisAutoMapperService.insertSelective(AccuseTypeMapper.class, accuseType);
		AccuseType type = mybatisAutoMapperService.selectByPrimaryKey(AccuseTypeMapper.class,accuseType.getAccuseId() , AccuseType.class);
		return type;
	}
	
	@RequestMapping(value = "update")
	@ResponseBody
	public boolean update(String accuseName,Integer accuseId,Integer order){
		AccuseType accuseType = new AccuseType();
		accuseType.setAccuseId(accuseId);
		accuseType.setAccuseName(accuseName);
		accuseType.setAccuseOrder(order);
		int result =mybatisAutoMapperService.updateByPrimaryKeySelective(AccuseTypeMapper.class, accuseType);
		if(result>0){
			return true;		
		}else{
			return false;
		}
	}
	
	@RequestMapping(value = "delete")
	@ResponseBody
	public boolean delete(Integer accuseId){
		AccuseType accuseType = mybatisAutoMapperService.selectByPrimaryKey(AccuseTypeMapper.class, accuseId, AccuseType.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("level", accuseType.getAccuseLevel());
		map.put("id", accuseId);
		int size = accuseInfoService.select(map);
		if(size>0){
			return false;
		}
		int result = mybatisAutoMapperService.deleteByPrimaryKey(AccuseTypeMapper.class,accuseId);
		if(result>0){
			return true;
		}else{
			return false;	
		}
	}
	
	@RequestMapping(value = "accuseTypeSelector")
	@ResponseBody
	public List<AccuseType> accuseTypeSelector(){
		return accuseTypeService.accuseTypeSelector();
	}
	
	@RequestMapping(value = "accuseInfoSelector")
	@ResponseBody
	public List<AccuseInfo> accuseInfoSelector(Integer accuseId,Integer accuseLevel){
		AccuseInfoExample example = new AccuseInfoExample();
		if(accuseLevel==1){
			example.createCriteria().andAccuseId1EqualTo(accuseId);
		}else if(accuseLevel==2){
			example.createCriteria().andAccuseId2EqualTo(accuseId);
		}
		example.setOrderByClause("info_order");
		List<AccuseInfo> accuseInfos = mybatisAutoMapperService.selectByExample(AccuseInfoMapper.class, example, AccuseInfo.class);
		return accuseInfos;
	}
	
	@RequestMapping(value = "/checkName")
	@ResponseBody
	public boolean checkName(String accuseName,Integer accuseId) {
		String name = accuseName.trim();
		AccuseTypeExample example = new AccuseTypeExample();
		Criteria criteria= example.createCriteria().andAccuseNameEqualTo(name);
		if(accuseId != null) {
			criteria.andAccuseIdNotEqualTo(accuseId);
		}
		int result = mybatisAutoMapperService.countByExample(AccuseTypeMapper.class, example);
		if(result > 0) {
			return false;
		}else {
			return true;
		}
	}
}
