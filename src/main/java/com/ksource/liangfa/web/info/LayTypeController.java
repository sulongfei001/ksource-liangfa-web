package com.ksource.liangfa.web.info;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.InfoType;
import com.ksource.liangfa.domain.InfoTypeExample;
import com.ksource.liangfa.domain.InfoTypeExample.Criteria;
import com.ksource.liangfa.mapper.InfoTypeMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.info.LayTypeService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;

@Controller
@RequestMapping("/info/layType")
public class LayTypeController {
	
	@Autowired
	LayTypeService layTypeService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	SystemDAO systemDAO;
	// TODO WUZY对比（临时）
	//法律法规类别主界面
	private static final String LAYCATEGORY_MAIN = "/info/lay/layCategory";
	//法律法规添加界面
	private static final String LAYCATEGORY_ADD = "/info/lay/layCategoryAdd";
	//添加后跳转回主界面
	private static final String LAYCATEGORY_SEARCH = "redirect:/info/layType/main";
	//法律法规修改界面
	private static final String LAYCATEGORY_UPDATE = "/info/lay/layCategoryUpdate";
	//添加后跳转回添加页面
	private static final String LAYCATEGORY_ADDUI = "redirect:/info/layType/addUI";
	//修改后跳转回修改页面
	private static final String LAYCATEGORY_UPDATEUI = "redirect:/info/layType/updateUI";
	
	//进行进入法律法规类别管理主界面操作
	@RequestMapping(value="/main")
	public String main(InfoType infoType,Map<String,Object> model){
		List<InfoType> infoTypes = layTypeService.queryLayTypes(infoType);
		model.put("infoTypes", infoTypes);
		model.put("infoType", infoType);
		return LAYCATEGORY_MAIN;
	}
	
	//加载树
	@RequestMapping(value="/tree")
	@ResponseBody
	public List<InfoType> tree(InfoType infoType,Map<String,Object> model){
		List<InfoType> infoTypes = layTypeService.queryLayTypes(infoType);
		return infoTypes;
	}
	
	//进行进入法律法规类别添加界面操作
	@RequestMapping(value="/addUI")
	public String addUI(ModelMap map,ResponseMessage res){
        map.addAttribute("info", ResponseMessage.parseMsg(res));
		return LAYCATEGORY_ADD;
	}
	
	//进行法律法规类别添加操作
	@RequestMapping(value="/add")
	public String add(InfoType infoType){
		infoType.setTypeId(systemDAO.getSeqNextVal(Const.TABLE_INFO_TYPE));
		mybatisAutoMapperService.insert(InfoTypeMapper.class, infoType);
		return ResponseMessage.addPromptTypeForPath(LAYCATEGORY_ADDUI, PromptType.add);
	}
	
	//进行法律法规类别删除操作
	@RequestMapping(value="/delete")
	public String delete(String typeId,Map<String, Object> model){
		layTypeService.delete(typeId);
		return LAYCATEGORY_SEARCH;
	}
	
	//进行进入法律法规类别修改界面操作
	@RequestMapping(value="/updateUI")
	public String updateUI(Integer typeId,Map<String,Object> model,ResponseMessage res){
		InfoType infoType = mybatisAutoMapperService.selectByPrimaryKey(InfoTypeMapper.class, typeId, InfoType.class);
		model.put("info", ResponseMessage.parseMsg(res));
		model.put("infoType", infoType);
		return LAYCATEGORY_UPDATE;
	}
	
	//进行法律法规修改操作
	@RequestMapping(value="/update")
	public String update(InfoType infoType){
		mybatisAutoMapperService.updateByPrimaryKeySelective(InfoTypeMapper.class, infoType);
		return ResponseMessage.addPromptTypeForPath(LAYCATEGORY_UPDATEUI+"?typeId="+infoType.getTypeId(), PromptType.update);
	}
	
	//验证法律法规类别名是否重复
    @RequestMapping(value="/checkName")
	@ResponseBody
	public boolean checkName(String typeName,Integer typeId){
    	String typename = typeName.trim(); 
    	InfoTypeExample example = new InfoTypeExample();
    	Criteria cri = example.createCriteria().andTypeNameEqualTo(typename);
    	if(typeId!=null){
    		cri.andTypeIdNotEqualTo(typeId);
    	}
    	int size = mybatisAutoMapperService.countByExample(InfoTypeMapper.class, example);
    	if(size>0){
    		return false;
    	}
    	return true;
    }
}