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
import com.ksource.liangfa.service.workflow.XianYiRenService;
import com.ksource.syscontext.Const;

/**
 * 提起公诉操作<br>
 * 挂载与任务办理页面，在任务办理办理完毕前，处理嫌疑人相关操作
 * @author rengeng
 *
 */
@Controller
@RequestMapping("/workflow/tiqigongsu")
public class TiQiGongSuController {
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	AccuseInfoMapper accuseInfoMapper;
	@Autowired
	XianYiRenService xianYiRenService;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	 //加载提起公诉操作页面
	@RequestMapping
	public ModelAndView load(String caseId){
		ModelAndView view = new ModelAndView("workflow/xianyiren/tiqigongsu");
		//查询所有提请起诉嫌疑人
		CaseXianyirenExample caseXianyirenExample = new CaseXianyirenExample();
		caseXianyirenExample.createCriteria().andCaseIdEqualTo(caseId)
		.andTiqingqisuStateEqualTo(Const.XIANYIREN_TIQINGQISU_STATE_YES);
		List<CaseXianyiren> tiqigongsuRenList = mybatisAutoMapperService.selectByExample(CaseXianyirenMapper.class, caseXianyirenExample, CaseXianyiren.class);
		
		//2012-7-24任庚添加
		for(CaseXianyiren caseXianyiren:tiqigongsuRenList){
			caseXianyiren.setAccuseInfoList(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_tiqigongsuZm));//查询提起公诉罪名
			caseXianyiren.setAccuseInfoList2(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_tiqingqisuZm));//查询提请起诉罪名
		}
		view.addObject("tiqigongsuRenList", tiqigongsuRenList);
		return view;
	}
	
	//不起诉
	@RequestMapping("buqisu")
	@ResponseBody
	public ServiceResponse  buqisu(CaseXianyiren xianyiren){
		ServiceResponse response = new ServiceResponse(true, "");
		xianyiren.setTiqigongsuState(Const.XIANYIREN_TIQIGONGSU_STATE_NO);
		xianYiRenService.updateXianyiren(xianyiren,false,Const.ZM_TYPE_tiqigongsuZm);
		return response;
	}
	//起诉
	@RequestMapping("qisu")
	@ResponseBody
	public ServiceResponse  qisu(CaseXianyiren xianyiren){
		ServiceResponse response = new ServiceResponse(true, "");
		xianyiren.setTiqigongsuState(Const.XIANYIREN_TIQIGONGSU_STATE_YES);
		
		xianYiRenService.updateXianyiren(xianyiren,false,null);
		return response;
	}
}
