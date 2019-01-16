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
import com.ksource.liangfa.domain.ZhifaInfoType;
import com.ksource.liangfa.domain.ZhifaInfoTypeExample;
import com.ksource.liangfa.domain.ZhifaInfoTypeExample.Criteria;
import com.ksource.liangfa.mapper.ZhifaInfoTypeMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.info.ZhifaTypeService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;

@Controller
@RequestMapping("/info/zhifaInfoType")
public class ZhifaTypeController {
	@Autowired
	private ZhifaTypeService zhifaTypeService;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private SystemDAO systemDAO;
	
	//执法动态类别主界面
	private static final String ZHIFACATEGORY_MAIN = "/info/zhifa/zhifaCategory";
	//执法动态添加界面
	private static final String ZHIFATEGORY_ADD = "/info/zhifa/zhifaCategoryAdd";
	//添加后跳转回主界面
	private static final String ZHIFACATEGORY_SEARCH = "redirect:/info/zhifaInfoType/main";
	//执法动态修改界面
	private static final String ZHIFACATEGORY_UPDATE = "/info/zhifa/zhifaCategoryUpdate";
	//添加后跳转回添加页面
	private static final String ZHIFACATEGORY_ADDUI = "redirect:/info/zhifaInfoType/addUI";
	//修改后跳转回修改页面
	private static final String ZHIFACATEGORY_UPDATEUI = "redirect:/info/zhifaInfoType/updateUI";
	
	//进行进入执法动态类别管理主界面操作
	@RequestMapping(value="/main")
	public String main(ZhifaInfoType zhifaInfoType,Map<String,Object> model){
		List<ZhifaInfoType> zhifaInfoTypes = zhifaTypeService.queryZhifaInfoTypes(zhifaInfoType);
		model.put("zhifaInfoTypes", zhifaInfoTypes);
		model.put("zhifaInfoType", zhifaInfoType);
		return ZHIFACATEGORY_MAIN;
	}
	
	//进行进入执法动态类别添加界面操作
	@RequestMapping(value="/addUI")
	public String addUI(ModelMap map,ResponseMessage res){
        map.addAttribute("info", ResponseMessage.parseMsg(res));
		return ZHIFATEGORY_ADD;
	}
	
	//进行执法动态类别添加操作
	@RequestMapping(value="/add")
	public String add(ZhifaInfoType zhifaInfoType){
		zhifaInfoType.setTypeId(systemDAO.getSeqNextVal(Const.TABLE_ZHIFA_INFO_TYPE));
		mybatisAutoMapperService.insert(ZhifaInfoTypeMapper.class, zhifaInfoType);
		return ResponseMessage.addPromptTypeForPath(ZHIFACATEGORY_ADDUI, PromptType.add);
	}
	
	//进行执法动态类别删除操作
	@RequestMapping(value="/delete")
	public String delete(Integer typeId,Map<String, Object> model){
		zhifaTypeService.delete(typeId);
		return ZHIFACATEGORY_SEARCH;
	}
	
	//进行进入执法动态类别修改界面操作
	@RequestMapping(value="/updateUI")
	public String updateUI(Integer typeId,Map<String,Object> model,ResponseMessage res){
		ZhifaInfoType zhifaInfoType = mybatisAutoMapperService.selectByPrimaryKey(ZhifaInfoTypeMapper.class, typeId, ZhifaInfoType.class);
		model.put("info", ResponseMessage.parseMsg(res));
		model.put("zhifaInfoType", zhifaInfoType);
		return ZHIFACATEGORY_UPDATE;
	}
	
	//进行执法动态修改操作
	@RequestMapping(value="/update")
	public String update(ZhifaInfoType zhifaInfoType){
		mybatisAutoMapperService.updateByPrimaryKeySelective(ZhifaInfoTypeMapper.class, zhifaInfoType);
		return ResponseMessage.addPromptTypeForPath(ZHIFACATEGORY_UPDATEUI+"?typeId="+zhifaInfoType.getTypeId(), PromptType.update);
	}
	
	//验证执法动态类别名是否重复
    @RequestMapping(value="/checkName")
	@ResponseBody
	public boolean checkName(String typeName,Integer typeId){
    	String typename = typeName.trim(); 
    	ZhifaInfoTypeExample example = new ZhifaInfoTypeExample();
    	Criteria cri = example.createCriteria().andTypeNameEqualTo(typename);
    	if(typeId != null){
    		cri.andTypeIdNotEqualTo(typeId);
    	}
    	int size = mybatisAutoMapperService.countByExample(ZhifaInfoTypeMapper.class, example);
    	if(size>0){
    		return false;
    	}
    	return true;
    }
}