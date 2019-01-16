package com.ksource.liangfa.web.workflow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.CaseXianyirenExample;
import com.ksource.liangfa.mapper.AccuseInfoMapper;
import com.ksource.liangfa.mapper.CaseXianyirenMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.syscontext.Const;

/**
 * 审查逮捕操作<br>
 * 挂载与任务办理页面，在任务办理办理完毕前，处理嫌疑人相关操作
 * @author rengeng
 *
 */

@Controller
@RequestMapping("/workflow/shenchadaibu")
public class ShenChaDaiBuController {

	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	AccuseInfoMapper accuseInfoMapper;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	 //加载审查逮捕操作页面
	@RequestMapping
	public ModelAndView load(String caseId){
		ModelAndView view = new ModelAndView("workflow/xianyiren/shenchadaibu");
		//查询已提请逮捕的、批准和不予逮捕的
		CaseXianyirenExample caseXianyirenExample = new CaseXianyirenExample();
		caseXianyirenExample.createCriteria().andCaseIdEqualTo(caseId).
		andDaibuStateNotEqualTo(Const.XIANYIREN_DAIBU_STATE_NOTYET);
		List<CaseXianyiren> tiqingdaibuRenList = mybatisAutoMapperService.selectByExample(CaseXianyirenMapper.class, caseXianyirenExample, CaseXianyiren.class);
		
		//2012-7-24任庚添加
		for(CaseXianyiren caseXianyiren:tiqingdaibuRenList){
			caseXianyiren.setAccuseInfoList(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_tiqingdaibuZm));//查询罪名
		}
		
		view.addObject("tiqingdaibuRenList", tiqingdaibuRenList);
		return view;
	}
	
	//不予批准逮捕
	@RequestMapping("nopizhun")
	@ResponseBody
	public ServiceResponse  nopizhun(CaseXianyiren xianyiren){
		ServiceResponse response = new ServiceResponse(true, "");
		xianyiren.setDaibuState(Const.XIANYIREN_DAIBU_STATE_NO);
		mybatisAutoMapperService.updateByPrimaryKeySelective(CaseXianyirenMapper.class, xianyiren);
		return response;
	}
	//批准逮捕
	@RequestMapping("pizhun")
	@ResponseBody
	public ServiceResponse  pizhun(CaseXianyiren xianyiren){
		ServiceResponse response = new ServiceResponse(true, "");
		xianyiren.setDaibuState(Const.XIANYIREN_DAIBU_STATE_YES);
		mybatisAutoMapperService.updateByPrimaryKeySelective(CaseXianyirenMapper.class, xianyiren);
		return response;
	}
}
