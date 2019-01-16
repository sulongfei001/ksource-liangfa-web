package com.ksource.liangfa.web.maintain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.domain.ProcKey;
import com.ksource.liangfa.domain.ProcKeyExample;
import com.ksource.liangfa.mapper.ProcKeyMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.syscontext.Const;

@Controller
@RequestMapping("/maintain/procKey")
public class ProcKeyController {

	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	
	//流程类型管理页面
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView view = new ModelAndView("maintain/prockey/prockey");
		List<ProcKey> procKeyList = mybatisAutoMapperService.selectByExample(ProcKeyMapper.class, new ProcKeyExample(), ProcKey.class);
		view.addObject("procKeyList", procKeyList);
		return view;
	}
	
	
	//添加流程类型
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/new",method=RequestMethod.POST)
	@ResponseBody
	public Map create(ProcKey procKey){
		ProcKey procKeyDomain =mybatisAutoMapperService.selectByPrimaryKey(ProcKeyMapper.class, procKey.getProcKey(), ProcKey.class);
		Map model = new HashMap();
		if(procKeyDomain==null){
			procKey.setUploadState(Const.STATE_INVALID);
			procKey.setDeployState(Const.STATE_INVALID);
			procKey.setCurVersion(0);
			procKey.setCurProcDefId("0");
			mybatisAutoMapperService.insert(ProcKeyMapper.class, procKey);
			model.put("success", true);
		}else{
			model.put("success", false);
			model.put("msg", "流程key已经存在！");
		}
		return model;
	}
	
	//更新流程类型
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ModelAndView update(ProcKey procKey){
		ProcKey procKeyDomain = mybatisAutoMapperService.selectByPrimaryKey(ProcKeyMapper.class, procKey.getProcKey(), ProcKey.class);
		if(procKeyDomain.getUploadState()==Const.STATE_INVALID && procKeyDomain.getDeployState()==Const.STATE_INVALID){//验证是否能修改，TODO：友好提示
			//不更新curVersion和useState字段
			procKey.setCurVersion(null);
			procKey.setUploadState(null);
			procKey.setDeployState(null);
			mybatisAutoMapperService.updateByPrimaryKeySelective(ProcKeyMapper.class, procKey);
		}
		return this.list();
	}
	
	//添加流程类型
	@RequestMapping(value="/delete")
	public ModelAndView delete(String procKey){
		mybatisAutoMapperService.deleteByPrimaryKey(ProcKeyMapper.class, procKey);
		return this.list();
	}
}
